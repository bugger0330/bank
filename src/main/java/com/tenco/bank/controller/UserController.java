package com.tenco.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.service.UserService;
import com.tenco.bank.util.Define;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession httpSession;
	

	//회원가입 페이지
	@GetMapping("/sign-up")
	public String signUpPage() {
		return "user/signUp";
	}
	
	//로그인 페이지
	@GetMapping("/sign-in")
	public String signInPage() {
		return "user/signIn";
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
		return "redirect:/user/sign-up"; 
	}
	
	//로그인 처리
	@PostMapping("/sign-in")
	public String signInProc(SignInFormDto dto) {
		
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력하세요!", HttpStatus.BAD_REQUEST);
		}else if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password를 입력해주세요!", HttpStatus.BAD_REQUEST);
		}
		
		User user = userService.readUser(dto);
		httpSession.setAttribute(Define.PRINCIPAL, user);
		return "redirect:/account/list";
	}
	
	@GetMapping("/logout")
	public String logout() {
		httpSession.invalidate();
		return "redirect:/user/sign-in";
	}
	
}












