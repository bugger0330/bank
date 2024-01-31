package com.tenco.bank.dto;

import lombok.Data;

@Data
public class WithdrawFromDto {

	private Long amount;
	private String wAccountNumber;
	private String wAccountPassword;
	
}
