package com.tencoding.bank.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignUpFormDto {

	// 화면 name태그 기준
	private String username;
	private String password;
	private String fullname;
	
	// TODO - 추후 추가 예정
}
