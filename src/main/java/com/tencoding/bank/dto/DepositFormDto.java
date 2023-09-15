package com.tencoding.bank.dto;

import lombok.Data;

@Data
public class DepositFormDto {

	// 화면 name태그 기준
	private long amount;
	private String dAccountNumber;
	
}
