package com.tenco.bank.repository.entity;

import java.sql.Timestamp;
import java.text.DecimalFormat;

import com.tenco.bank.util.TimeUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomHistoryEntity {

	private Integer id;
	private Long amount;
	private Long balance;
	private Timestamp createdAt;
	private String sender;
	private String receiver;
	
	public String formatCreatedAt() {
		return TimeUtils.timestampToString(this.createdAt);
	}
	
	public String formatBalance() {
		DecimalFormat format = new DecimalFormat("###,###");
		String st = format.format(this.balance);
		return st + "원";
	}
	
}
