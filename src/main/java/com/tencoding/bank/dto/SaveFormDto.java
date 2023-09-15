package com.tencoding.bank.dto;

import lombok.Data;

@Data
public class SaveFormDto {
	
	// 화면 name태그 기준
	private String number;
	private String password;
	private int balance;

}
