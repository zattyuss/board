package com.jafa.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jafa.dao.MemberMapper;
import com.jafa.dto.MemberVO;

public class MemberMapperTest {

	@Autowired
	MemberMapper mapper;

	@Test
	public void selectMember() {
		MemberVO read = mapper.read("admin");
		read.getAuthList().forEach(System.out::println);
	}

}
