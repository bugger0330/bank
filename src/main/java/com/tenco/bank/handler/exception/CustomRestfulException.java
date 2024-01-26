package com.tenco.bank.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomRestfulException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
	
	public CustomRestfulException(String msg, HttpStatus status) {
		// TODO Auto-generated constructor stub
		super(msg);
		this.httpStatus = status;
	}
}
