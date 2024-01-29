package com.tenco.bank.handler.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
	
	public UnAuthorizedException(String message, HttpStatus httpStatus) {
		// TODO Auto-generated constructor stub
		super(message);
		this.httpStatus = httpStatus;
	}
}
