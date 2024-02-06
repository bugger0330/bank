package com.tenco.bank.repository.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

	private Integer id;
	private String username;
	private String password;
	private String fullname;
	private Timestamp createdAt;
	
	private String originFileName;
	private String uploadFileName;
	
	// 사용자가 회원가입시에 이미지 첨부 여부에 따라 처리
	public String setupUserImage() {
		return uploadFileName == null ?
				"https://picsum.photos/id/1/350" :
				"/images/upload/" + uploadFileName;
	}
}
