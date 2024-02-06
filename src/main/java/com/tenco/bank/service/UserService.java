package com.tenco.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.repository.interfaces.UserRepository;

@Service
public class UserService {

	//@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//@Autowired 안쓰고 생성자를 이용해서 주입함
	public UserService(UserRepository userRepository) {
		// TODO Auto-generated constructor stub
		this.userRepository = userRepository;
	}
	
	@Transactional
	public void createUser(SignUpFormDto dto) {
		User user = User.builder()
				.username(dto.getUsername())
				.password(passwordEncoder.encode(dto.getUsername()))
				.fullname(dto.getFullname())
				.originFileName(dto.getOriginFileName())
				.uploadFileName(dto.getUploadFileName())
				.build();
		System.out.println("회원가입--- " + dto.toString());
		System.out.println("회원가입--- " + user.toString());
		int result = userRepository.insert(user);
		if(result != 1) {
			throw new CustomRestfulException("회원 가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public User readUser(SignInFormDto dto) {
		User userEntity = userRepository.findByUsername(dto.getUsername());
		if(userEntity == null) {
			throw new CustomRestfulException("아이디가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
		}
		
		boolean isPwdMatched = passwordEncoder.matches(dto.getPassword(), userEntity.getPassword());
		if(isPwdMatched == false) {
			throw new CustomRestfulException("비밀번호가 틀립니다.", HttpStatus.BAD_REQUEST);
		}
		
		return userEntity;
	}
	
	// 사용자 이름만 가지고 정보 조회
	public User readUserByUsername(SignUpFormDto dto) {
		
		// null , User()
		return userRepository.findByUsername(dto.getUsername());
	}
	
	
}
