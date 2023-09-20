package com.tencoding.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.bank.dto.DepositFormDto;
import com.tencoding.bank.dto.SaveFormDto;
import com.tencoding.bank.dto.TransferFormDto;
import com.tencoding.bank.dto.WithDrawFormDto;
import com.tencoding.bank.handler.exception.CustomRestfulException;
import com.tencoding.bank.repository.interfaces.AccountRepository;
import com.tencoding.bank.repository.interfaces.HistoryRepository;
import com.tencoding.bank.repository.model.Account;
import com.tencoding.bank.repository.model.History;

@Service // IoC 대상 + 싱글톤 패턴으로 -> 스프링 컨테이너 메모리에 객체가 생성
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private HistoryRepository historyRepository;
	
	@Transactional
	public void creatAccount(SaveFormDto saveFormDto, Integer principalId) {
		// 등록 처리 - insert
		Account account = new Account();
		account.setNumber(saveFormDto.getNumber());
		account.setPassword(saveFormDto.getPassword());
		account.setBalance(saveFormDto.getBalance());
		account.setUserId(principalId);
		
		int resultRowCount = accountRepository.insert(account);
		if(resultRowCount != 1) {
			throw new CustomRestfulException("계좌 생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 계좌 목록 보기 ( 로그인 된 사용자 )
	 * @param userId
	 * @return
	 */
	@Transactional
	public List<Account> readAccountList(Integer userId) {
		List<Account> list = accountRepository.findByUserId(userId);
		return list;
	}

	// 출금 기능 로직을 고민해보기
	// 1. 계좌 존재 여부 확인 - select query
	// 2. 본인 계좌 여부 확인 - select query
	// 3. 계좌 비밀번호 확인 - select query
	// 4. 계좌 잔액 여부 확인 - select query
	// 5. 출금 처리 --> update query
	// 6. 거래 내역 등록 -> insert query - history_tb
	// 7. 트랜잭션 처리
	
	@Transactional
	public void updateAccountWithdraw(WithDrawFormDto withDrawFormDto, Integer id) {
		Account accountEntity = accountRepository.findByNumber(withDrawFormDto.getWAccountNumber());
		// 1
		if(accountEntity == null) {
			throw new CustomRestfulException("해당 계좌가 없습니다.", HttpStatus.BAD_REQUEST);
		}
		// 2
		if(accountEntity.getUserId() != id) {
			throw new CustomRestfulException("본인 소유 계좌가 아닙니다.", HttpStatus.BAD_REQUEST);
		}
		// 3
		if(accountEntity.getPassword().equals(withDrawFormDto.getWAccountPassword()) == false) {
			throw new CustomRestfulException("출금 계좌 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
		}
		// 4
		if(accountEntity.getBalance() < withDrawFormDto.getAmount()) {
			throw new CustomRestfulException("계좌 잔액이 부족합니다.", HttpStatus.BAD_REQUEST);
		}
		// 5 -> update query (모델 객체 상태 변경 -> 객체 다시 던지기)
		accountEntity.withdraw(withDrawFormDto.getAmount());
		// 쿼리 던지기
		accountRepository.updateById(accountEntity);
		// 6 - 거래 내역 등록 History 객체 생성
		History history = new History();
		history.setAmount(withDrawFormDto.getAmount());
		// 출금 시점에 해당 계좌에 잔액을 입력
		history.setWBalance(accountEntity.getBalance());
		history.setDBalance(null);
		history.setWAccountId(accountEntity.getId());
		history.setDAccountId(null);
		
		int resultRowCount = historyRepository.insert(history);
		if(resultRowCount != 1) {
			throw new CustomRestfulException("정상적으로 처리되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 입금 기능 로직 생각해보기
	@Transactional
	public void updateAccountDeposit(DepositFormDto depositFormDto) {
		// 1. 계좌 존재 여부 확인 --> select query
		// 2. 입금 처리 --> update query
		// 3. 거래 내역 등록 --> insert query history_tb
		// 4. 트랜잭션 처리
		Account accountEntity = accountRepository.findByNumber(depositFormDto.getDAccountNumber());
		// 1. 계좌 존재 여부 확인
		if(accountEntity == null) {
			throw new CustomRestfulException("해당 계좌가 없습니다.", HttpStatus.BAD_REQUEST);
		}
		
		// 2. 입금 처리
		// 객체 상태값 변경 처리
		accountEntity.deposit(depositFormDto.getAmount());
		
		accountRepository.updateById(accountEntity);
		
		// 3. 거래 내역 등록
		History history = new History();
		history.setAmount(depositFormDto.getAmount());
		
		
		history.setWBalance(null);
		// 현재 입금 되었을 때 잔액을 기록
		history.setDBalance(accountEntity.getBalance());
		history.setWAccountId(null);
		history.setDAccountId(accountEntity.getId());
		
		int resultRowCount = historyRepository.insert(history);
		if(resultRowCount != 1) {
			throw new CustomRestfulException("정상적으로 처리되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 이체 로직
	// 1. 출금 계좌 존재 여부 확인 --> select query
	// 2. 입금 계좌 존재 여부 확인 --> select query
	// 3. 출금 계좌 본인 소유 확인 --> 객체 선택값 확인
	// 4. 출금 계좌 비밀번호 확인 --> transferFormDto(비밀번호) / 모델(비밀번호)
	// 5. 출금 계좌 잔액 여부 확인 --> DTO / 모델
	// 6. 출금 계좌 잔액 - update
	// 7. 이체 계좌 잔액 - update
	// 8. 거래 내역 등록 - insert
	// 9. 트랙잭션 처리
	
	@Transactional
	public void updateAccountTransfer(TransferFormDto transferFormDto, Integer id) {
		// 1
		Account withdrawAccountEntity = accountRepository.findByNumber(transferFormDto.getWAccountNumber());
		if(withdrawAccountEntity == null) {
			throw new CustomRestfulException("출금 계좌가 존재하지 않습니다", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 2
		Account depositAccountEntity = accountRepository.findByNumber(transferFormDto.getDAccountNumber());
		if(depositAccountEntity == null) {
			throw new CustomRestfulException("입금 계좌가 존재하지 않습니다", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 3
		withdrawAccountEntity.checkOwner(id);
		
		// 4
		withdrawAccountEntity.checkPassword(transferFormDto.getWAccountPassword());
		
		// 5
		withdrawAccountEntity.checkBalance(transferFormDto.getAmount());
		
		// 6
		withdrawAccountEntity.withdraw(transferFormDto.getAmount());
		
		// 7
		depositAccountEntity.deposit(transferFormDto.getAmount());
		
		// 8
		accountRepository.updateById(withdrawAccountEntity);
		accountRepository.updateById(depositAccountEntity);
		
		History history = new History();
		history.setAmount(transferFormDto.getAmount());
		history.setWAccountId(withdrawAccountEntity.getId());
		history.setDAccountId(depositAccountEntity.getId());
		history.setWBalance(withdrawAccountEntity.getBalance());
		history.setDBalance(depositAccountEntity.getBalance());
		
		int resultRowCount = historyRepository.insert(history);
		if(resultRowCount != 1) {
			throw new CustomRestfulException("정상 처리 되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
