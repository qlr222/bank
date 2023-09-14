package com.tencoding.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tencoding.bank.repository.model.User;


// ibatis -> 2.4 버전 이후로 MyBatis로 이름 변경 됨
@Mapper // Mapper 반드시 기술을 해주어야 동작한다.
public interface UserRepository {
	// 뱅크 앱
	public int insert(User user);
	public int updateById(User user);
	public int deleteById(Integer id);
	public User findById(Integer id);
	// 관리자 - 회원정보 리스를 보고 싶다면?
	public List<User> findAll();
	
}
