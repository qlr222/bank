package com.tencoding.bank.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;


// 클래스 정의
@Getter // IoC 대상이 아니다. (필요할 때 직접 new 할 예정)
public class CustomRestfulException extends RuntimeException{
	
	private HttpStatus status;
	
	public CustomRestfulException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
	

}
