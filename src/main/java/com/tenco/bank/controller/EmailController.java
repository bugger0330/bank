package com.tenco.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tenco.bank.config.Test1;
import com.tenco.bank.dto.EmailRequestDto;
import com.tenco.bank.service.EmailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmailController {

	private final EmailService emailService;
	private final JavaMailSender javaMailSender;

	@GetMapping("/email2")
	public String email() {
		return "user/signUp2";
	}
	
	@GetMapping("/email/send")
	public void testEmailSend() {
		// 비번 생성
		String pw = Test1.tempPassword();
		System.out.println("비번생성 : ------" +pw);
		
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("bk.kangmin1243@gmail.com"); // 받는 이메일
		message.setSubject("이메일 인증 번호를 보냅니다.");
		message.setText("이메일 인증 비밀번호는 " + pw + " 입니다.");
		message.setFrom("test@gmail.com");
		message.setReplyTo("test@gmail.com");
		javaMailSender.send(message);
	}
	
	@GetMapping("/email/check")
	public ResponseEntity<?> emailCheck(String email) {
		boolean result = emailService.emailCheck(email);
		
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@PostMapping("/email/password")
	public ResponseEntity<?> emailPasswordCheck(EmailRequestDto dto){
		boolean result = emailService.emailPasswordCheck(dto);
		System.out.println("==========================" + result);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
}




















