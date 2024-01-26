package com.tenco.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.handler.exception.CustomRestfulException;

@Controller
@RequestMapping("/test")
public class HomeController {

	@GetMapping("/main")
	public void home() {
		throw new CustomRestfulException("에러발생", HttpStatus.NOT_FOUND);
		//return "layout/main";
	}
}
