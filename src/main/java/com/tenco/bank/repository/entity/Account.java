package com.tenco.bank.repository.entity;

import java.sql.Timestamp;
import java.text.DecimalFormat;

import org.springframework.http.HttpStatus;

import com.tenco.bank.handler.exception.CustomRestfulException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Account {

	private Integer id;
	private String number;
	private String password;
	private Long balance;
	private Integer userId;
	private Timestamp createdAt;
	
	//출금 기능
	//입금 기능
	//패스워드 체크 기능
	//잔액 여부 확인 기능
	//계좌 소유자 확인 기능
	
	//출금 기능
	public void withdraw(Long amount) {
		//방어적 코드 작성 - todo
		this.balance -= amount;
	}
	//입금 기능
	public void deposit(Long amount) {
		this.balance += amount;
	}
	
	public void passwordCheck(String password) {
		if(!this.password.equals(password)) {
			throw new CustomRestfulException("비밀번호가 다릅니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public void checkBalance(Long balance) {
		if(this.balance < balance) {
			throw new CustomRestfulException("계좌 잔액이 부족합니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	public void checkOwner(Integer principal) {
		if(this.userId != principal) {
			throw new CustomRestfulException("계좌 소유자가 아닙니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//포매터 기능 만들기
	public String formatBalance() {
		
		DecimalFormat df = new DecimalFormat("###,###");
		String formatMoney = df.format(this.balance);

		return formatMoney + "원";
	}
	
	
	
	
	
	
	
	
	
}
