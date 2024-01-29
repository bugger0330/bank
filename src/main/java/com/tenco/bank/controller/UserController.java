package com.tenco.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	

	//회원가입 페이지
	@GetMapping("/sign-up")
	public String signUpPage() {
		return "user/signUp";
	}
	
	//회원가입 처리
	@PostMapping("/sign-up")
	public String signProc(SignUpFormDto dto) {
		
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력하세요!", HttpStatus.BAD_REQUEST);
		}else if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password를 입력해주세요!", HttpStatus.BAD_REQUEST);
		}else if(dto.getFullname() == null || dto.getFullname().isEmpty()) {
			throw new CustomRestfulException("이름을 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		userService.createUser(dto);
		System.out.println("성공?");
		return "redirect:/user/sign-up"; // 로그인 페이지 안만들어서..
	}
	
	
	
}
