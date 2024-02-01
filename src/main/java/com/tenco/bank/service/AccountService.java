package com.tenco.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.AccountSaveDto;
import com.tenco.bank.dto.DepositFormDto;
import com.tenco.bank.dto.TransferSaveDto;
import com.tenco.bank.dto.WithdrawFromDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.Account;
import com.tenco.bank.repository.entity.CustomHistoryEntity;
import com.tenco.bank.repository.entity.History;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.repository.interfaces.AccountRepository;
import com.tenco.bank.repository.interfaces.HistoryRepository;
import com.tenco.bank.util.Define;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private HistoryRepository historyRepository;
	
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

	//출금 기능
	@Transactional
	public void updateAccountWithdraw(WithdrawFromDto dto, Integer principalId) {
		// 계좌존재 여부 확인
		Account account = accountRepository.findByNumber(dto.getWAccountNumber());
		if(account == null) {
			throw new CustomRestfulException(Define.NOT_EXIST_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 본인계좌 여부 확인
		if(!account.getUserId().equals(principalId)) {
			throw new CustomRestfulException("본인 소유 계좌가 아닙니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 계좌 비밀번호가 정상인지
		if(!account.getPassword().equals(dto.getWAccountPassword())) {
			throw new CustomRestfulException("계좌 비밀번호가 다릅니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 잔액 여부 확인
		if(account.getBalance() < dto.getAmount()) {
			throw new CustomRestfulException("계좌 잔액이 부족합니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 출금 처리 update
		account.withdraw(dto.getAmount());
		accountRepository.updateById(account);
		// 거래내역 등록 insert
		History history = new History();
		history.setAmount(dto.getAmount());
		history.setWBalance(account.getBalance());
		history.setDBalance(null);
		history.setWAccountId(account.getId());
		history.setDAccountId(null);
		int rowResultCount = historyRepository.insert(history);
		if(rowResultCount != 1) {
			throw new CustomRestfulException("정상 처리 되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Transactional
	public void updateAccountDeposit(DepositFormDto dto, User principal) {
		// TODO Auto-generated method stub
		Account account = accountRepository.findByNumber(dto.getDAccountNumber());
		if(account == null) {
			throw new CustomRestfulException("입금 계좌번호를 확인해주세요!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 본인계좌 여부 확인
		if(!account.getNumber().equals(dto.getDAccountNumber())) {
			throw new CustomRestfulException("본인 소유 계좌가 아닙니다!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 계좌 비밀번호가 정상인지
		if(!account.getPassword().equals(dto.getDAccountPassword())) {
			throw new CustomRestfulException("입금 계좌 비밀번호를 확인하세요!!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 입금 처리 update
		account.deposit(dto.getAmount());
		accountRepository.insert(account);
		// 거래내역 등록 insert
		History history = new History();
		history.setAmount(dto.getAmount());
		history.setWBalance(null);
		history.setDBalance(account.getBalance());
		history.setWAccountId(null);
		history.setDAccountId(account.getId());
		int rowResultCount = historyRepository.insert(history);
		if(rowResultCount != 1) {
			throw new CustomRestfulException("정상 처리 되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public void updateAccountTransfer(TransferSaveDto dto, Integer principal) {
		//출금 계좌 존재여부
		Account wAccount = accountRepository.findByNumber(dto.getWAccountNumber());
		if(wAccount == null) {
			throw new CustomRestfulException("출금계좌가 존재하지 않습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//입금 계좌 존재여부
		Account dAccount = accountRepository.findByNumber(dto.getDAccountNumber());
		if(dAccount == null) {
			throw new CustomRestfulException("입금계좌가 존재하지 않습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//출금 계좌 본인 소유 확인
		if(wAccount.getUserId() != principal) {
			throw new CustomRestfulException("출금계좌 소유자가 아닙니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//출금 계좌 비밀번호
		if(!wAccount.getPassword().equals(dto.getPassword())) {
			throw new CustomRestfulException("출금계좌 비밀번호가 틀립니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//출금 계좌 잔액 확인
		if(wAccount.getBalance() <= dto.getAmount()) {
			throw new CustomRestfulException("잔액이 부족합니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//출금 계좌 잔액 수정
		wAccount.withdraw(dto.getAmount());
		int result1 = accountRepository.updateById(wAccount);
		//입금 계좌 잔액 수정
		dAccount.deposit(dto.getAmount());
		int result2 = accountRepository.updateById(wAccount);
		if(result1 != 1 && result2 != 1) {
			throw new CustomRestfulException("정상 처리가 안됬습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//거래내역 등록
		History history = new History();
		history.setAmount(dto.getAmount());
		history.setWBalance(wAccount.getBalance());
		history.setDBalance(dAccount.getBalance());
		history.setWAccountId(wAccount.getId());
		history.setDAccountId(dAccount.getId());
		int rowResultCount = historyRepository.insert(history);
		if(rowResultCount != 1) {
			throw new CustomRestfulException("정상 처리 되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//계좌 거래 내역
	public List<CustomHistoryEntity> readHistoryListByAccount(String type, Integer id) {
		return historyRepository.findByHistoryType(type, id);
	}

	public Account readByAccountId(Integer id) {
		// TODO Auto-generated method stub
		return accountRepository.findByAccountId(id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
