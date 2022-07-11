package com.jafa.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jafa.config.AppTest;

public class PasswordEncoderTest extends AppTest{

	@Autowired
	PasswordEncoder pwEncoder;
	
	@Test
	public void testEncode() {
		String str = "member";
		String enStr = pwEncoder.encode(str);
		
		System.out.println(enStr);
	}

}
