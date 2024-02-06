package com.tenco.bank.dto.naver;

import lombok.Data;

@Data
public class NaverAccount {

	private String resultcode;
	private String message;
	private NaverResponse response;
}
