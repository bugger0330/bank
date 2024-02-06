package com.tenco.bank.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OauthToken {

	
	private String accessToken;
	private String tokenType;
	private String refreshToken;
	private Long expiresIn;
	private String scope;
	private Long refreshTokenExpiresIn;
}
