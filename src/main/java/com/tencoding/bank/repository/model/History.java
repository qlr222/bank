package com.tencoding.bank.repository.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class History {

	private Integer id;
	private long amount;
	private long wBalance;
	private long dBalnace;
	private Integer wAccountId;
	private Integer dAccountId;
	private Timestamp createdAt;

}
