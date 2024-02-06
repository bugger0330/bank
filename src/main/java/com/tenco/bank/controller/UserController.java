package com.tenco.bank.controller;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.tenco.bank.dto.OauthToken;
import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.dto.kakao.KakaoProfile;
import com.tenco.bank.dto.naver.NaverAccount;
import com.tenco.bank.dto.naver.NaverToken;
import com.tenco.bank.dto.openapi.BoxOfficeResult;
import com.tenco.bank.dto.openapi.OpenApiResult;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.service.UserService;
import com.tenco.bank.util.Define;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession httpSession;
	

	//회원가입 페이지
	@GetMapping("/sign-up")
	public String signUpPage() {
		return "user/signUp";
	}
	
	//로그인 페이지
	@GetMapping("/sign-in")
	public String signInPage() {
		return "user/signIn";
	}
	
	//회원가입 처리
	@PostMapping("/sign-up")
	public String signProc(SignUpFormDto dto) throws IllegalStateException, IOException {
		System.out.println("파일첨부----"+dto.getFile().getOriginalFilename());
//		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
//			throw new CustomRestfulException("username을 입력하세요!", HttpStatus.BAD_REQUEST);
//		}else if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
//			throw new CustomRestfulException("password를 입력해주세요!", HttpStatus.BAD_REQUEST);
//		}else if(dto.getFullname() == null || dto.getFullname().isEmpty()) {
//			throw new CustomRestfulException("이름을 입력하세요!", HttpStatus.BAD_REQUEST);
//		}
		
		//파일 업로드 시
		MultipartFile file = dto.getFile();
		if(file.isEmpty() == false) { // 파일이 존재함
			// 사용자가 이미지를 업로드 했다면 기능구현
			// 파일 사이즈 체크
			if(file.getSize() > Define.MAX_FILE_SIZE) {
				throw new CustomRestfulException("파일 크기는 20MB 이상 클 수 없습니다.", HttpStatus.BAD_REQUEST);
			}
			// 서버에 업로드할 폴더가 있는지 경로 확인
			String saveDerectory = Define.UPLOAD_FILE_DERECTORY;
			// 폴더가 없다면 오류발생(파일 생성시)
			File dir = new File(saveDerectory);
			if(dir.exists() == false) { // 없으면
				dir.mkdir(); // 폴더 생성
			}
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file.getOriginalFilename();
			System.out.println("파일명 변경---"+fileName);
			
			String uploadPath = Define.UPLOAD_FILE_DERECTORY + File.separator + fileName; // File.separator => 역슬러시를 만들어줌. 오류를 피하기 위해 사용
			System.out.println("전체 경로 : " + uploadPath);
			File destination = new File(uploadPath);
			file.transferTo(destination); // 실제로 파일을 경로에 넣어줌
			// 객체 상태 변경
			dto.setOriginFileName(dto.getFile().getOriginalFilename());
			dto.setUploadFileName(fileName);
		}
		
		userService.createUser(dto);
		return "redirect:/user/sign-up"; 
	}
	
	//로그인 처리
	@PostMapping("/sign-in")
	public String signInProc(SignInFormDto dto) {
		
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력하세요!", HttpStatus.BAD_REQUEST);
		}else if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password를 입력해주세요!", HttpStatus.BAD_REQUEST);
		}
		
		User user = userService.readUser(dto);
		httpSession.setAttribute(Define.PRINCIPAL, user);
		return "redirect:/account/list";
	}
	
	@GetMapping("/logout")
	public String logout() {
		httpSession.invalidate();
		return "redirect:/user/sign-in";
	}
	
	//http://localhost:80/user/kakao-callback?code=
	@SuppressWarnings("null")
	@GetMapping("/kakao-callback")
	public String kakaoCallback(@RequestParam String code) {
		// post 방식으로 던짐
		// 헤더+바디 구성
		RestTemplate rt1 = new RestTemplate();
		// 헤더 구성
		org.springframework.http.HttpHeaders headers1 = new org.springframework.http.HttpHeaders();
		headers1.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// 바디 구성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "12a14a8995ef6b6bf5235e1164edaba2");
		params.add("redirect_uri", "http://localhost/user/kakao-callback");
		params.add("code", code);
		// 헤더 + 바디 결합
		HttpEntity<MultiValueMap<String, String>> reqMsg = new HttpEntity<>(params, headers1);
		ResponseEntity<OauthToken> response = rt1.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, reqMsg, OauthToken.class);
		
		//다시 요청 - 인증토큰을 가지고 사용자 정보 요청을 해야 함
		RestTemplate rt2 = new RestTemplate();
		org.springframework.http.HttpHeaders header2 = new org.springframework.http.HttpHeaders();
		header2.add("Authorization", "Bearer " + response.getBody().getAccessToken());
		header2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		HttpEntity<MultiValueMap<String, String>> regMsg2 = new HttpEntity<>(null, header2);
		ResponseEntity<KakaoProfile> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, regMsg2, KakaoProfile.class);
		
		// 로그인 처리 - username 존재 여부 확인
		SignUpFormDto dto = SignUpFormDto.builder()
				.username("OAuth_" + response2.getBody().getProperties().getNickname())
				.fullname("Kakao")
				.password("asd1234")
				.build();
		User oldUser = userService.readUserByUsername(dto);
		
		if(oldUser == null) {
			// 회원가입 처리
			userService.createUser(dto);
			oldUser.setUsername(dto.getUsername());
			oldUser.setFullname(dto.getFullname());
			oldUser.setPassword(null);
		}
		// 로그인 처리
		httpSession.setAttribute(Define.PRINCIPAL, oldUser);
		
		return "redirect:/account/list";
	}
	
	@SuppressWarnings("null")
	@GetMapping("/naver-callback")
	public String naverCallback(@RequestParam String code, @RequestParam String state) {
		
		RestTemplate rt1 = new RestTemplate();
		// 헤더 구성
		org.springframework.http.HttpHeaders headers1 = new org.springframework.http.HttpHeaders();
		headers1.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// 바디 구성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "iTerJzJBbIvU0qMDREvC");
		params.add("client_secret", "5j7sHOHGlp");
		params.add("code", code);
		params.add("state", state);
		// 헤더 + 바디 결합
		HttpEntity<MultiValueMap<String, String>> reqMsg = new HttpEntity<>(params, headers1);
		ResponseEntity<NaverToken> response = rt1.exchange("https://nid.naver.com/oauth2.0/token", HttpMethod.POST, reqMsg, NaverToken.class);
		//다시 요청 - 인증토큰을 가지고 사용자 정보 요청을 해야 함
		RestTemplate rt2 = new RestTemplate();
		org.springframework.http.HttpHeaders header2 = new org.springframework.http.HttpHeaders();
		header2.add("Authorization", "Bearer " + response.getBody().getAccess_token());
		header2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		HttpEntity<MultiValueMap<String, String>> regMsg2 = new HttpEntity<>(null, header2);
		ResponseEntity<NaverAccount> response2 = rt2.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.POST, regMsg2, NaverAccount.class);
		
		// 로그인 처리 - username 존재 여부 확인
		SignUpFormDto dto = SignUpFormDto.builder()
				.username("OAuth_" + response2.getBody().getResponse().getNickname())
				.fullname("Naver")
				.password("asd1234")
				.build();
		User oldUser = userService.readUserByUsername(dto);
		
		if(oldUser == null) {
			// 회원가입 처리
			userService.createUser(dto);
			oldUser.setUsername(dto.getUsername());
			oldUser.setFullname(dto.getFullname());
			oldUser.setPassword(null);
		}
		// 로그인 처리
		httpSession.setAttribute(Define.PRINCIPAL, oldUser);
		
		return "redirect:/account/list";
	}
	
	
	@GetMapping("/movie")
	@ResponseBody
	public String movie() {
		RestTemplate rt1 = new RestTemplate();
		// 바디 구성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("key", "8507ca5f3c045a90e3df6a381593f338");
		params.add("targetDt", "20240101");
		params.add("itemPerPage", "10");
		params.add("multiMovieYn", "N");
		params.add("repNationCd", "K");
		params.add("wideAreaCd", "default");
		// 헤더 + 바디 결합
		HttpEntity<MultiValueMap<String, String>> reqMsg = new HttpEntity<>(params, null);
//		ResponseEntity<BoxOfficeResult> response = rt1.exchange("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=8507ca5f3c045a90e3df6a381593f338&targetDt=20240101", HttpMethod.GET, reqMsg, BoxOfficeResult.class);
		ResponseEntity<OpenApiResult> response = rt1.exchange("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=8507ca5f3c045a90e3df6a381593f338&targetDt=20240101", HttpMethod.GET, reqMsg, OpenApiResult.class);
		System.out.println(response.toString());
		return response.getBody().toString();
	}
	
	
}












