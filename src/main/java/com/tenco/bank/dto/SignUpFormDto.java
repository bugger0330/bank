package com.tenco.bank.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SignUpFormDto {

	private String username;
	private String password;
	private String fullname;
	
	//파일 받을때
	private MultipartFile file;
	private String originFileName;
	private String uploadFileName;
}
