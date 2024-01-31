package com.tenco.bank.dto;

import lombok.Data;

@Data
public class TransferSaveDto {

	private Long amount;
	private String wAccountNumber;
	private String dAccountNumber;
	private String password;
}
