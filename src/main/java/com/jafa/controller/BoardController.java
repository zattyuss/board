package com.jafa.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping("/get")
	public String get(Long bno, Model model, @CookieValue(required = false) Cookie viewCount, 
			HttpServletResponse response, HttpServletRequest request){
		boolean isAddCount = false;
		if(viewCount != null) {
			String[] viewed = viewCount.getValue().split("/");
			List<String> viewedList = Arrays.stream(viewed).collect(Collectors.toList());
			if(!viewedList.contains(bno.toString())) {
				viewCount.setValue(viewCount.getValue()+bno+"/");
				response.addCookie(viewCount);
				isAddCount = true;
			}
		}else {
			Cookie cookie = new Cookie("viewCount", bno+"/");
			cookie.setMaxAge(60*60*24);
			response.addCookie(cookie);
			isAddCount = true;
		}
		
		model.addAttribute("board",  service.get(bno, isAddCount));
		return "board/get";
	}
	
	@PreAuthorize("principal.username == #writer")
	@PostMapping("/remove")
	public String remove(Long bno, RedirectAttributes rttr, String writer) {
		List<BoardCatVO> catList = service.getCatList(bno);
		deleteFiles(catList);
		service.remove(bno);
		rttr.addFlashAttribute("message", bno + "번 삭제함");
		return "redirect:list";
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/register")
	public String registerForm() {
		return "board/register";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/register")
	public String register(Board board, RedirectAttributes rttr) {
		service.register(board);
		rttr.addFlashAttribute("message", board.getBno() + "번 등록함");
		return "redirect:list";
	}
	
	@GetMapping("/modify")	
	public String modify(Long bno, Model model) {
		model.addAttribute("board", service.get(bno, false));
		return "board/modify";
	}
	
	@PreAuthorize("isAuthenticated() and principal.username == #board.writer")
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
