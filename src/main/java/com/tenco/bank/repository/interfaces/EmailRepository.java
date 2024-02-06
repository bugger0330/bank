package com.tenco.bank.repository.interfaces;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tenco.bank.dto.EmailRequestDto;
import com.tenco.bank.repository.entity.Email;

@Mapper
public interface EmailRepository {

	public Email emailCheck(String email);
	public int emailPasswordInsert(@Param("email") String email, @Param("password") String password);
	public int emailPasswordCheck(Email email);
	
	
}
