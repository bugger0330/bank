package com.tenco.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tenco.bank.handler.AuthIntercepter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	// 도메인 검사를 한다

	@Autowired
	private AuthIntercepter authIntercepter;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authIntercepter)
					.addPathPatterns("/account/**");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// 리소스 등록 처리
	// 서버 컴퓨터에 위치한 리소스를 활용하는 방법
	// 프로젝트 외부 폴더 접근 방법
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 가짜경로
		registry.addResourceHandler("/images/upload/**")
		.addResourceLocations("file:///C:\\wok_spring\\upload/"); // 실제 경로
		// 사용자는 가짜경로가 보인다.
	}
	
}
