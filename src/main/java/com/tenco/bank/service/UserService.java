package com.tenco.bank.service;

import org.springframework.http.HttpStatus;
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
	
	//@Autowired 안쓰고 생성자를 이용해서 주입함
	public UserService(UserRepository userRepository) {
		// TODO Auto-generated constructor stub
		this.userRepository = userRepository;
	}
	
	@Transactional
	public void createUser(SignUpFormDto dto) {
		User user = User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.fullname(dto.getFullname())
				.build();
		int result = userRepository.insert(user);
		if(result != 1) {
			throw new CustomRestfulException("회원 가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public User readUser(SignInFormDto dto) {
		User userEntity = User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.build();
		User user = userRepository.findByUsernameAndPassword(userEntity);
		if(user == null) {
			throw new UnAuthorizedException("인증된 사용자가 아닙니다.", HttpStatus.BAD_REQUEST);
		}
		return user;
	}
	
	
}
