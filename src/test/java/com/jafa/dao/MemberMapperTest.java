package com.jafa.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jafa.config.AppTest;
import com.jafa.dto.MemberVO;

public class MemberMapperTest extends AppTest{

	@Autowired
	MemberMapper mapper;
	
	
	@Test
	public void selectMemberTest() {
		MemberVO read = mapper.read("superjinhyeon");
		System.out.println(read);
		read.getAuthList().forEach(System.out::println);
	}

}
