package com.tenco.bank.controller;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.tenco.bank.dto.Todo;

@RestController
public class RestControllerTest {

	@GetMapping("/my-test1")
	public String test(){
		// 여기서 다른 서버로 자원을 요청한 다음에
		// 다시 클라이언트에게 자원을 내려주자.
		
		// 자원 등록 요청 --> POST 방식 사용법
		// 1. URI 객체 만들기
		// https://jsonplaceholder.typicode.com/posts
		URI uri = UriComponentsBuilder
				.fromUriString("https://jsonplaceholder.typicode.com") // 다른 서버 주소
				.path("/todos")
				.encode()
				.build()
				.toUri();
		
		RestTemplate template = new RestTemplate();
		// http 통신 -> HTTP 메세지에 헤더, 바디를 구성해서 보내야 한다.
		
		// 헤더 구성
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.add("Content-type", "application/json; charset=UTF-8");
		// 바디 구성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("title", "블로그 포스트 1");
		params.add("body", "후미진 어느 언덕에서 도시락 소풍");
		params.add("userId", "1");
		
		// 헤더와 바디 결합하기
		HttpEntity<MultiValueMap<String, String>> resp  = new HttpEntity<>(params, headers);
		
		// HTTP 요청 처리 
		ResponseEntity<String> response 
				=  template.exchange(uri, HttpMethod.POST, resp, String.class); // 여기서 페이크 사이트에 요청을 한다.
		
		return response.getBody();
	}
	
	@GetMapping("/todos/{id}")
	public ResponseEntity<?> test2(@PathVariable Integer id){
		//URI uri = new URI("https://jsonplaceholder.typicode.com/" + id);
		URI uri = UriComponentsBuilder
				.fromUriString("https://jsonplaceholder.typicode.com")
				.path("/todos")
				.path("/" + id)
				.encode()
				.build()
				.toUri();
		
		RestTemplate template = new RestTemplate();
		ResponseEntity<Todo> resp = template.getForEntity(uri, Todo.class); // get 요청, 응답은 문자열
		System.out.println("++++++++++++++++++++++++++++++++");
		System.out.println(resp.getHeaders());
		System.out.println(resp.getBody());
		System.out.println("title : "+resp.getBody().getTitle());
		System.out.println(resp.getStatusCode());
		
		return new ResponseEntity<>(resp.getBody(), HttpStatus.OK);
	}
	
	@GetMapping("/update")
	public ResponseEntity<?> updateTest(){
		URI uri = UriComponentsBuilder
				.fromUriString("https://jsonplaceholder.typicode.com")
				.path("/posts/1")
				.encode()
				.build()
				.toUri();
		
		RestTemplate template = new RestTemplate();
		
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.add("Content-type", "application/json; charset=UTF-8");
		// 바디 구성
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("id", 1);
		params.add("title", "글작성");
		params.add("body", "bar");
		params.add("userid", 1);
		
		// 헤더와 바디 결합하기
		HttpEntity<MultiValueMap<String, Object>> resp  = new HttpEntity<>(params, headers);
		ResponseEntity<String> response 
		=  template.exchange(uri, HttpMethod.PUT, resp, String.class); // 여기서 페이크 사이트에 요청을 한다.

		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}
	
	
	
}














