package com.jafa.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jafa.dto.Board;
import com.jafa.dto.BoardCatVO;
import com.jafa.dto.Criteria;
import com.jafa.dto.PageMaker;
import com.jafa.service.BoardService;

@Controller
@RequestMapping("/board/")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	
	@GetMapping("/list")
	public String getBoardList(Criteria criteria, Model model) {
		PageMaker pageMaker = new PageMaker(criteria, service.totalCount(criteria));
		model.addAttribute("pageMaker",pageMaker);
		
		List<Board> list = service.getList(criteria);
		model.addAttribute("list", list);
		return "board/list";
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(Long bno, Model model) {
		Board board = service.get(bno);
		model.addAttribute("board",  board);
		return "board/get";
	}
	
	@PostMapping("/remove")
	public String remove(Long bno, RedirectAttributes rttr) {
		List<BoardCatVO> catList = service.getCatList(bno);
		deleteFiles(catList);
		service.remove(bno);
		rttr.addFlashAttribute("message", bno + "번 삭제함");
		return "redirect:list";
	}
	
	

	@GetMapping("/register")
	public String registerForm() {
		return "board/register";
	}
	@PostMapping("/register")
	public String register(Board board, RedirectAttributes rttr) {
		service.register(board);
		rttr.addFlashAttribute("message", board.getBno() + "번 등록함");
		return "redirect:list";
	}
	
	@GetMapping("/modify")
	public String modify(Long bno, Model model) {
		model.addAttribute("board", service.get(bno));
		return "board/modify";
	}
	@PostMapping("/modify")
	public String modify(Board board, RedirectAttributes rttr) {
		service.modify(board);
		rttr.addFlashAttribute("msg", board.getBno());
		return "redirect:list";
	}
	
	@GetMapping(value = "/getCatList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardCatVO>> getCatList(Long bno){
		List<BoardCatVO> catList = service.getCatList(bno);
		return new ResponseEntity<>(catList, HttpStatus.OK);
	}
	
	private void deleteFiles(List<BoardCatVO> catList) {
		if(catList==null || catList.size()==0) return;
		
		catList.forEach(cat -> {
			
			Path file = Paths.get("C:/storage/" + cat.getUploadPath()+"/"+cat.getUuid()+"_"+ cat.getFileName());
			System.out.println(file);
			try {
				Files.deleteIfExists(file);
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumbNail = Paths.get("C:/storage/" + cat.getUploadPath()+"/s_"+cat.getUuid()+"_"+ cat.getFileName());
					Files.deleteIfExists(thumbNail);
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		});
	}
	
	
//	@PostMapping("/update")
//	public String update(Board board) {
//		service.update(board);
//		return "redirect:list";
//	}
}
