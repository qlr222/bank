package com.tencoding.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tencoding.bank.dto.SignUpFormDto;
import com.tencoding.bank.handler.exception.CustomRestfulException;
import com.tencoding.bank.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	// 회원 가입 페이지 요청
	// http://localhost:80/user/sign-up

	@GetMapping("/sign-up")
	public String signUp() {
		return "user/signUp";
	}

	// 로그인 페이지 요청
	// http://localhost:80/user/sign-in
	@GetMapping("/sign-in")
	public String signIn() {
		return "user/signIn";
	}
	
	// 회원 가입 처리 
	// @param signUpFormDto
	// @return 리다이렉트 처리 - 로그인 페이지
	
	@PostMapping("/sign-up")
	public String signUpProc(SignUpFormDto signUpFormDto) {
		// 1. 유효성 검사
		if(signUpFormDto.getUsername() == null
				|| signUpFormDto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력하세요", HttpStatus.BAD_REQUEST);
		}
		
		// 2. 유효성 검사 2
		if(signUpFormDto.getPassword() == null
				|| signUpFormDto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password을 입력하세요", HttpStatus.BAD_REQUEST);
		}
		
		// 3. 유효성 검사 3
		if(signUpFormDto.getFullname() == null
				|| signUpFormDto.getFullname().isEmpty()) {
			throw new CustomRestfulException("fullname을 입력하세요", HttpStatus.BAD_REQUEST);
		}
		
		
		// 로직 추가 -- 서비스 호출
		userService.signUp(signUpFormDto);
		return "redirect:/user/sign-in";
	}

}
