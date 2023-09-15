package com.tencoding.bank.dto;

import lombok.Data;

@Data
public class TransferFormDto {

	// 화면 name태그 기준
	private long amount;
	private String wAccountNumber;
	private String dAccountNumber;
	private String wAccountPassword;
	
}
