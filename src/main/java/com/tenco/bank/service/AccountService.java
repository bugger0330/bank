package com.tenco.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.AccountSaveDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.Account;
import com.tenco.bank.repository.interfaces.AccountRepository;
import com.tenco.bank.util.Define;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Transactional
	public void createAccount(AccountSaveDto dto) {
		
		//계좌번호 중복 확인 findByUserId
		if(readAccount(dto.getNumber()) != null) {
			throw new CustomRestfulException(Define.EXIST_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		Account accountSave = Account.builder()
				.number(dto.getNumber())
				.password(dto.getPassword())
				.balance(dto.getBalance())
				.userId(dto.getUserId())
				.build();
		int result = accountRepository.insert(accountSave);
		if(result != 1) {
			throw new CustomRestfulException(Define.FAIL_TO_CREATE_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//단일 계좌 검색기능
	public Account readAccount(String number) {
		return accountRepository.findByNumber(number.trim());
	}
	
	//계좌 목록 보기 기능(1인 다 계좌)
	public List<Account> accountListByUserId(Integer principal){
		return accountRepository.findAllByUserId(principal);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
