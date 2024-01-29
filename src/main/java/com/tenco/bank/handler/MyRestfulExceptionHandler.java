package com.tenco.bank.handler;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.handler.exception.UnAuthorizedException;

@Order(1)
@RestControllerAdvice
public class MyRestfulExceptionHandler {

	@ExceptionHandler(Exception.class)
	public void exception(Exception e) {
		System.out.println("-----------------");
		System.out.println(e.getClass().getName());
		System.out.println(e.getMessage());
		System.out.println("-----------------");
	}
	
	@ExceptionHandler(CustomRestfulException.class)
	public String basicException(CustomRestfulException e) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<script>");
		buffer.append("alert('" + e.getMessage() + "');");
		buffer.append("window.history.back();");
		buffer.append("</script>");
		return buffer.toString();
	}
	
	@ExceptionHandler(UnAuthorizedException.class)
	public String UnAuthorizedException(com.tenco.bank.handler.exception.UnAuthorizedException e) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<script>");
		buffer.append("alert('" + e.getMessage() + "');");
		buffer.append("window.location.href='/user/sign-in';");
		buffer.append("</script>");
		return buffer.toString();
	}
	
	
	
}
