package com.tenco.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignUpFormDto {

	private String username;
	private String password;
	private String fullname;
}
