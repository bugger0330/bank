package com.tenco.bank.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.tenco.bank.handler.exception.CustomPageException;

/*
 * View 렌더링을 위해 ModelView 객체를 반환 하도록 설정 되어 있다.
 * 예외처리 Page 를 리턴할때 사용한다.0
 */
@ControllerAdvice
public class MyPageExceptionHandler {

	@ExceptionHandler(CustomPageException.class)
	public ModelAndView handlerRuntimeException(CustomPageException exception) {
		System.out.println("예외 발생");
		ModelAndView view = new ModelAndView("errorPage");
		view.addObject("statusCode", HttpStatus.NOT_FOUND.value());
		view.addObject("message", exception.getMessage());
		return view; // 페이지 + 데이터 반환
	}
}
