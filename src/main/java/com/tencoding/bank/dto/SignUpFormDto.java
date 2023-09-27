package com.tencoding.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpFormDto {

	// 화면 name태그 기준
	private String username;
	private String password;
	private String fullname;
	
	// TODO - 추후 추가 예정
}
