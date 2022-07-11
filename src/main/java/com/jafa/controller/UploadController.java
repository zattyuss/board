package com.jafa.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jafa.dto.BoardCatVO;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
public class UploadController {
	
	@GetMapping("/uploadForm")
	public void uploadForm() {
		
	}
	
	@PostMapping("uploadFormAction")
	public void uploadFormAction(MultipartFile[] uploadFile, Model model) {
		for(MultipartFile file : uploadFile) {
			System.out.println("===========================");
			System.out.println("파일 이름 " + file.getOriginalFilename());
			System.out.println("파일 크기 " + file.getSize());
			
			File saveFile = new File("C://storage", file.getOriginalFilename());
			try {
				file.transferTo(saveFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {}
	
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardCatVO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		List<BoardCatVO> list = new ArrayList<BoardCatVO>();
		File uploadPath = new File("C:/storage", getFolder()); 
		if(!uploadPath.exists()) {
			uploadPath.mkdirs(); // "C:/storage"\\2022\\06\\22
		}
		for(MultipartFile multipartFile : uploadFile) {
			BoardCatVO CatVO = new BoardCatVO(); //객체생성
			String uploadFileName = multipartFile.getOriginalFilename();
			
			CatVO.setFileName(uploadFileName); //uuid 문자열 적용전
			UUID uuid = UUID.randomUUID();
			uploadFileName  = uuid.toString() + "_" + uploadFileName;

			File savefile = new File(uploadPath, uploadFileName);
			try {
				multipartFile.transferTo(savefile);
				CatVO.setUuid(uuid.toString());
				CatVO.setUploadPath(getFolder());
				
				if (checkImageType(savefile)) {
					CatVO.setFileType(true);
					FileOutputStream tumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), tumbnail, 100, 100);
				}
				list.add(CatVO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<List<BoardCatVO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		File file = new File("C:\\storage\\" + fileName);
		ResponseEntity<byte[]> result = null;
		
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.add("content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(
					FileCopyUtils.copyToByteArray(file),
					headers,
					HttpStatus.OK); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(
			@RequestHeader("User-Agent") String userAgent, String fileName){
		Resource resource = new FileSystemResource("C:\\storage\\" + fileName);
		HttpHeaders headers = new HttpHeaders();
		if(!resource.exists()) {
			System.out.println("파일이 존재하지 않음");
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		String resourceName = resource.getFilename();
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		String downloadName = null;
		System.out.println(userAgent);
		
		try {
			downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8");
			headers.add("Content-Disposition", "attachment;fileName=" + downloadName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);

		
	}
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		File file;
		try {
			file = new File("C:\\storage\\" + URLDecoder.decode(fileName, "utf-8"));
			file.delete();
			if(type.equals("image")) {
				String originFileName = file.getAbsolutePath().replace("s_", "");
				file = new File(originFileName);
				file.delete();
			}
			}catch(UnsupportedEncodingException e) {
				e.printStackTrace();
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("delete", HttpStatus.OK);
	}
	

	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(new Date());
		return str.replace("-", File.separator);
	}
	
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
