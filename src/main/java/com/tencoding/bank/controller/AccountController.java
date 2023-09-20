package com.tencoding.bank.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tencoding.bank.dto.DepositFormDto;
import com.tencoding.bank.dto.SaveFormDto;
import com.tencoding.bank.dto.TransferFormDto;
import com.tencoding.bank.dto.WithDrawFormDto;
import com.tencoding.bank.handler.exception.CustomRestfulException;
import com.tencoding.bank.handler.exception.UnAuthorizedException;
import com.tencoding.bank.repository.model.Account;
import com.tencoding.bank.repository.model.User;
import com.tencoding.bank.service.AccountService;
import com.tencoding.bank.util.Define;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private HttpSession session;
	@Autowired
	private AccountService accountService;
	
	// 계좌 목록 페이지
	// http://localhost:80/account/list
	
	@GetMapping("/list")
	public String list(Model model) {
		User user = (User)session.getAttribute(Define.PRINCIPAL);;
		if(user == null) {
			throw new UnAuthorizedException("로그인을 먼저 해주세요.", HttpStatus.UNAUTHORIZED);
		}
		
		List<Account> accountList = accountService.readAccountList(user.getId());
		
		if(accountList.isEmpty()) {
			model.addAttribute("accountList", null);
		} else {
			model.addAttribute("accountList", accountList);
		}
		
		return "account/list";
	}
	
	
	// 계좌 생성 페이지
	// http://localhost:80/account/save
	// /account/save - 화면 이동
	/**
	 * 계좌 생성 페이지 이동
	 */
	@GetMapping("/save")
	public String save() {
		// 1. 인증 여부 확인
		
		User user = (User)session.getAttribute(Define.PRINCIPAL);;
		if(user == null) {
			throw new UnAuthorizedException("로그인을 먼저 해주세요.", HttpStatus.UNAUTHORIZED);
		}
		
		return "account/save";
	}
	
	/**
	 * 계좌 생성 로직 구현
	 * @return
	 */
	@PostMapping("/save")
	public String saveProc(SaveFormDto saveFormDto) {
		// 1. 인증 검사
		User user = (User)session.getAttribute(Define.PRINCIPAL);;
		if(user == null) {
			throw new UnAuthorizedException("로그인을 먼저 해주세요.", HttpStatus.UNAUTHORIZED);
		}
		// 2. 유효성 검사
		if(saveFormDto.getNumber() == null
				|| saveFormDto.getNumber().isEmpty()) {
			throw new CustomRestfulException("계좌번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		
		if(saveFormDto.getPassword() == null
				|| saveFormDto.getPassword().isEmpty()) {
			throw new CustomRestfulException("비밀번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		
		if(saveFormDto.getBalance() == null 
				|| saveFormDto.getBalance() < 0) {
			throw new CustomRestfulException("잘못된 입력입니다.", HttpStatus.BAD_REQUEST);
		}
		// 3. 서비스 호출
		accountService.creatAccount(saveFormDto, user.getId());
		return "redirect:/account/list";
	}
	
	// 출금 페이지
	// http://localhost:80/account/withdraw
	
	@GetMapping("/withdraw")
	public String withdraw() {
		// 1. 인증 여부 확인
		User user = (User)session.getAttribute(Define.PRINCIPAL);;
		if(user == null) {
			throw new UnAuthorizedException("로그인을 먼저 해주세요.", HttpStatus.UNAUTHORIZED);
		}
		
		return "account/withdraw";
	}
	
	// body -> String --> amount=1000&wAccountId=10&......
	@PostMapping("/withdraw")
	public String withdrawProc(WithDrawFormDto withDrawFormDto) {
		// 1. 인증 여부 확인
		User user = (User)session.getAttribute(Define.PRINCIPAL);;
		if(user == null) {
			throw new UnAuthorizedException("로그인을 먼저 해주세요.", HttpStatus.UNAUTHORIZED);
		}
		
		// 2. 유효성 검사
		if(withDrawFormDto.getAmount() == null) {
			throw new CustomRestfulException("금액을 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		if(withDrawFormDto.getAmount() <= 0) {
			throw new CustomRestfulException("잘못된 금액입니다.", HttpStatus.BAD_REQUEST);
		}
		if(withDrawFormDto.getWAccountNumber() == null
				|| withDrawFormDto.getWAccountNumber().isEmpty()) {
			throw new CustomRestfulException("출금 계좌번호를 확인해주세요.", HttpStatus.BAD_REQUEST);
		}
		if(withDrawFormDto.getWAccountPassword() == null
				|| withDrawFormDto.getWAccountPassword().isEmpty()) {
			throw new CustomRestfulException("출금 계좌 비밀번호를 확인해주세요.", HttpStatus.BAD_REQUEST);
		}
		
		accountService.updateAccountWithdraw(withDrawFormDto, user.getId());
		
		
		return "redirect:/account/list";
	}
	
	// 입금 페이지
	// http://localhost:80/account/deposit
	
	@GetMapping("/deposit")
	public String deposit() {
		// 1. 인증 여부 확인
		User user = (User)session.getAttribute(Define.PRINCIPAL);;
		if(user == null) {
			throw new UnAuthorizedException("로그인을 먼저 해주세요.", HttpStatus.UNAUTHORIZED);
		}
		return "account/deposit";
	}
	
	@PostMapping("/deposit")
	public String depositProc(DepositFormDto depositFormDto) {
		// 1. 인증 여부 확인
		User user = (User)session.getAttribute(Define.PRINCIPAL);;
		if(user == null) {
			throw new UnAuthorizedException("로그인을 먼저 해주세요.", HttpStatus.UNAUTHORIZED);
		}
		// 2. 유효성 검사
		if(depositFormDto.getAmount() == null) {
			throw new CustomRestfulException("금액을 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		if(depositFormDto.getAmount() <= 0) {
			throw new CustomRestfulException("잘못된 금액입니다.", HttpStatus.BAD_REQUEST);
		}
		if(depositFormDto.getDAccountNumber() == null
				|| depositFormDto.getDAccountNumber().isEmpty()) {
			throw new CustomRestfulException("입금 계좌번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		
		accountService.updateAccountDeposit(depositFormDto);
		return "redirect:/account/list";
	}
	
	// 이체 페이지
	// http://localhost:80/account/transfer
	
	@GetMapping("/transfer")
	public String transfer() {
		// 1. 인증 여부 확인
		User user = (User)session.getAttribute(Define.PRINCIPAL);;
		if(user == null) {
			throw new UnAuthorizedException("로그인을 먼저 해주세요.", HttpStatus.UNAUTHORIZED);
		}
		return "account/transfer";
	}
	
	
	// 1. 이체 금액 0원 이상 입력 여부 확인
	// 2. 출금 계좌 번호 입력 여부 확인
	// 3. 입금 계좌 번호 입력 여부 확인
	// 4. 출금 계좌 비밀 번호 입력 여부 확인
	
	@PostMapping("/transfer")
	public String transferProc(TransferFormDto transferFormDto) {
		// 1. 인증 여부 확인
		User user = (User)session.getAttribute(Define.PRINCIPAL);;
		if(user == null) {
			throw new UnAuthorizedException("로그인을 먼저 해주세요.", HttpStatus.UNAUTHORIZED);
		}
		
		// 2. 유효성 검사
		if(transferFormDto.getAmount() == null) {
			throw new CustomRestfulException("이체 금액을 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		if(transferFormDto.getAmount() <= 0) {
			throw new CustomRestfulException("이체 금액이 0원 이하일 수 없습니다.", HttpStatus.BAD_REQUEST);
		}
		if(transferFormDto.getWAccountNumber() == null
				|| transferFormDto.getWAccountNumber().isEmpty()) {
			throw new CustomRestfulException("출금 계좌번호를 확인해주세요.", HttpStatus.BAD_REQUEST);
		}
		if(transferFormDto.getDAccountNumber() == null
				|| transferFormDto.getDAccountNumber().isEmpty()) {
			throw new CustomRestfulException("입금 계좌번호를 확인해주세요.", HttpStatus.BAD_REQUEST);
		}
		if(transferFormDto.getWAccountPassword() == null
				|| transferFormDto.getWAccountPassword().isEmpty()) {
			throw new CustomRestfulException("출금 계좌 비밀번호를 확인해주세요.", HttpStatus.BAD_REQUEST);
		}
		// 3. 서비스 호출
		accountService.updateAccountTransfer(transferFormDto, user.getId());
		
		return "redirect:/account/list";
	}
	
	// TODO - 수정하기
	// 상세 보기 페이지
	// http://localhost:80/account/detail/1
	
	@GetMapping("/detail")
	public String detail() {
		return "account/detail";
	}
}