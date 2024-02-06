package com.tenco.bank.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomPageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
	// 생성자를 만들지 않으면 --> 컴파이러가 자동으로 넣어 준다 ---> 기본자 
	// 사용자 정의 생성자를 만들게 되면 ---> 컴파일러는 기본 생성자를 만들어 주지 않는다 
	// --> 생성자 --- 강제성 
	public CustomPageException(String message, HttpStatus status) {
		super(message);
		this.httpStatus = status;
	}
	
	
}
