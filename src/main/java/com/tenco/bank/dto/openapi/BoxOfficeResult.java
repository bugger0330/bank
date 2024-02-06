package com.tenco.bank.dto.openapi;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BoxOfficeResult {

	private String boxofficeType;
	private String showRange;
	private List<DailyBoxOffice> dailyBoxOfficeList;
}
