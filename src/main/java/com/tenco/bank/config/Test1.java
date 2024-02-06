package com.tenco.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Test1 {
	
	@Autowired
	private JavaMailSender javaMailSender;

	public static void main(String[] args) {
		String pw = tempPassword();
		System.out.println("[ indiefliker ] 임시비밀번호 안내 이메일 입니다.");
		System.out.println("안녕하세요. indiefliker 임시비밀번호 안내 관련 이메일 입니다." +
		           " 회원님의 임시 비밀번호는 " + pw + " 입니다." +
		           " 로그인 후에 비밀번호를 변경을 해주세요!");
		
		
		
	}// 메인
//====================================================================================================================
	// 임시번호 10자리 랜덤생성
	public static String tempPassword(){
		char[] charSet = new char[] {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
				'W', 'X', 'Y', 'Z' };
		String password = "";
		// 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
		int j = 0;
		for (int i = 0; i < 10; i++) {
			j = (int) (charSet.length * Math.random());
			password += charSet[j];
		}
		return password;
	}
	

	
	
	
	
}
