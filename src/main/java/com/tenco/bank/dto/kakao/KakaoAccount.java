package com.tenco.bank.dto.kakao;

import lombok.Data;

@Data
public class KakaoAccount {

	private boolean profile_nickname_needs_agreement;
	private boolean profile_image_needs_agreement;
	private Profile profile;
}
