package com.tenco.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.tenco.bank.dto.AccountSaveDto;
import com.tenco.bank.dto.DepositFormDto;
import com.tenco.bank.dto.TransferSaveDto;
import com.tenco.bank.dto.WithdrawFromDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.entity.Account;
import com.tenco.bank.repository.entity.CustomHistoryEntity;
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
	
	//출금 페이지
	@GetMapping("/withdraw")
	public String withdrawPage() {
		User principal = (User)httpSession.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인을 해주세요!", HttpStatus.UNAUTHORIZED);
		}
		return "account/withdraw";
	}
	
	//출금 기능
	@PostMapping("/withdraw")
	public String withdrawProc(WithdrawFromDto dto) {
		User principal = (User)httpSession.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인을 해주세요!", HttpStatus.UNAUTHORIZED);
		}
		
		if(dto.getAmount() == null || dto.getAmount().longValue() <= 0) {
			throw new CustomRestfulException("금액를 입력하세요!", HttpStatus.BAD_REQUEST);
		}else if(dto.getWAccountNumber() == null || dto.getWAccountNumber().isEmpty()) {
			throw new CustomRestfulException("계좌번호를 입력해주세요!", HttpStatus.BAD_REQUEST);
		}else if(dto.getWAccountPassword() == null || dto.getWAccountPassword().isEmpty()) {
			throw new CustomRestfulException("비밀번호를 확인하세요!", HttpStatus.BAD_REQUEST);
		}
		
		accountService.updateAccountWithdraw(dto, principal.getId());
		
		return "redirect:/account/list";
	}
	
	//입금 페이지
	@GetMapping("/deposit")
	public String depositPage() {
		User principal = (User)httpSession.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인을 해주세요!", HttpStatus.UNAUTHORIZED);
		}
		return "account/deposit";
	}
	
	//입금 기능
	public String depositProc(DepositFormDto dto) {
		User principal = (User)httpSession.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인을 해주세요!", HttpStatus.UNAUTHORIZED);
		}
		
		if(dto.getAmount() == null || dto.getAmount() <= 0) {
			throw new CustomRestfulException("입금금액을 확인하세요!", HttpStatus.BAD_REQUEST);
		}else if(dto.getDAccountNumber() == null || dto.getDAccountNumber().isEmpty()) {
			throw new CustomRestfulException("입금 계좌번호를 확인하세요!", HttpStatus.BAD_REQUEST);
		}else if(dto.getDAccountPassword() == null || dto.getDAccountPassword().isEmpty()) {
			throw new CustomRestfulException("입금 계좌 비밀번호를 확인하세요!", HttpStatus.BAD_REQUEST);
		}
		
		accountService.updateAccountDeposit(dto, principal);
		
		return "redirect:/account/list";
	}
	
	//이체 페이지
	@GetMapping("/transfer")
	public String transferPage() {
		User principal = (User)httpSession.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인을 해주세요!", HttpStatus.UNAUTHORIZED);
		}
		return "account/transfer";
	}
	
	//이체 기능
	@PostMapping("/transfer")
	public String transferProc(TransferSaveDto dto) {
		User principal = (User)httpSession.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인을 해주세요!", HttpStatus.UNAUTHORIZED);
		}
		if(dto.getAmount() == null || dto.getAmount() <= 0) {
			throw new CustomRestfulException("이체 금액을 확인하세요!", HttpStatus.BAD_REQUEST);
		}else if(dto.getWAccountNumber() == null || dto.getWAccountNumber().isEmpty()) {
			throw new CustomRestfulException("출금 계좌번호를 확인하세요!", HttpStatus.BAD_REQUEST);
		}else if(dto.getDAccountNumber() == null || dto.getDAccountNumber().isEmpty()) {
			throw new CustomRestfulException("이체 계좌번호를 확인하세요!", HttpStatus.BAD_REQUEST);
		}else if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("비밀번호를 확인하세요!", HttpStatus.BAD_REQUEST);
		}
		
		accountService.updateAccountTransfer(dto, principal.getId());
		return "redirect:/account/list";
	}
	
	// 계좌 상세 보기 페이지 -- 전체(입출금), 입금, 출금
		// http://localhost:80/account/detail/1
		@GetMapping("/detail/{id}")
		public String detail(@PathVariable Integer id, 
				@RequestParam(name = "type", 
							  defaultValue = "all", required = false) String type, 
				org.springframework.ui.Model model) {
				
			// 1. 인증 검사
			User principal = (User) httpSession.getAttribute(Define.PRINCIPAL); // 다운 캐스팅
			if (principal == null) {
				throw new UnAuthorizedException(Define.ENTER_YOUR_LOGIN, HttpStatus.UNAUTHORIZED);
			}
			
			Account account = accountService.readByAccountId(id);
			
			// 서스비 호출
			List<CustomHistoryEntity> historyList = accountService.readHistoryListByAccount(type, id);
			System.out.println("list : " + historyList.toString());
			
			model.addAttribute("account", account);
			model.addAttribute("historyList", historyList);
			
			return "account/detail";
		}
	
	
	
	
	
	
	
	
}
