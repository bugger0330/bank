package com.tenco.bank.dto.openapi;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DailyBoxOffice {

	private String rnum;
	private String rank;
	private String rankInten;
	private String rankOldAndNew;
	private String movieCd;
	private String movieNm;
	private String openDt;
	private String salesAmt;
	private String salesShare;
	private String salesInten;
	private String salesChange;
	private String salesAcc;
	private String audiCnt;
	private String audiInten;
	private String audiChange;
	private String audiAcc;
	private String scrnCnt;
	private String showCnt;
}
