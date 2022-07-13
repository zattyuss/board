package com.jafa.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home(HttpServletResponse response) {
		Cookie myCookie = new Cookie("boardList", "ㅋ");
		myCookie.setMaxAge(60*60*24);
		response.addCookie(myCookie);
		return "home";
	}
}
