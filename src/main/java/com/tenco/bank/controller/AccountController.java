package com.tenco.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.tenco.bank.dto.AccountSaveDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.entity.Account;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.service.AccountService;
import com.tenco.bank.util.Define;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private HttpSession httpSession;
	
	
	@GetMapping("/save")
	public String accountPage() {
		User principal = (User)httpSession.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인을 해주세요!", HttpStatus.UNAUTHORIZED);
		}
		return "account/saveForm";
	}
	
	@PostMapping("/save") // 계좌생성 처리
	public String accountSave(AccountSaveDto dto) {
		User principal = (User)httpSession.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인을 해주세요!", HttpStatus.UNAUTHORIZED);
		}
		
		if(dto.getNumber() == null || dto.getNumber().isEmpty()) {
			throw new CustomRestfulException("계좌번호를 입력하세요!", HttpStatus.BAD_REQUEST);
		}else if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("비밀번호를 입력해주세요!", HttpStatus.BAD_REQUEST);
		}else if(dto.getBalance() == null || dto.getBalance() < 0) {
			throw new CustomRestfulException("입금금액을 확인하세요!", HttpStatus.BAD_REQUEST);
		}
		
		dto.setUserId(principal.getId());
		accountService.createAccount(dto);
		
		return "/list";
	}
	
//	@GetMapping("/select")
//	public String accountSelect(String number, ModelAndView view) {
//		User principal = (User)httpSession.getAttribute(Define.PRINCIPAL);
//		if(principal == null) {
//			throw new UnAuthorizedException("로그인을 해주세요!", HttpStatus.UNAUTHORIZED);
//		}
//		Account account = accountService.readAccount(number);
//		view.addObject("account", account);
//		return "user/signIn";
//	}
	
	//계좌 목록 보기 페이지
	@GetMapping({"/", "/list"})
	public String listPage(org.springframework.ui.Model model) {
		User principal = (User)httpSession.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인을 해주세요!", HttpStatus.UNAUTHORIZED);
		}
		
		List<Account> list = accountService.accountListByUserId(principal.getId());
		if(list.isEmpty()) {
			model.addAttribute("accountList", null);
		}else {
			model.addAttribute("accountList", list);
		}
		
		return "account/list";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
