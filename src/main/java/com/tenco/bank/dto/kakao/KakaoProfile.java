package com.tenco.bank.dto.kakao;

import lombok.Data;

@Data
public class KakaoProfile {

	private Long id;
	private String connected_at;
	private Properties properties;
	private KakaoAccount kakao_account;
	
}

