package com.tenco.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountSaveDto {

	private String number;
	private String password;
	private Long balance;
	private Integer userId;
}
