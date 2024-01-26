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
}
