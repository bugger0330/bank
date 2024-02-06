package com.tenco.bank.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tenco.bank.config.Test1;
import com.tenco.bank.dto.EmailRequestDto;
import com.tenco.bank.repository.entity.Email;
import com.tenco.bank.repository.interfaces.EmailRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final EmailRepository emailRepository;
	private final JavaMailSender javaMailSender;
	
	public boolean emailCheck(String email) {
		Email emailEntity = emailRepository.emailCheck(email);
		if(emailEntity == null) {
			String password = Test1.tempPassword();
			int result = emailRepository.emailPasswordInsert(email, password);
			if(result != 0) {
				// 메일 보내기
				
				
				SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(email); // 받는 이메일
				message.setSubject("이메일 인증 번호를 보냅니다.");
				message.setText("이메일 인증 비밀번호는 " + password + " 입니다.");
				message.setFrom("test@gmail.com");
				message.setReplyTo("test@gmail.com");
				javaMailSender.send(message);
				
			}
			return true;
		}
		return false;
	}
	
	public boolean emailPasswordCheck(EmailRequestDto dto) {
		
		Email email = Email.builder()
				.email(dto.getEmail())
				.password(dto.getPassword())
				.build();
		System.out.println("서비스-------------" + email.toString());
		int result = emailRepository.emailPasswordCheck(email);
		return result != 0;
	}
}






















