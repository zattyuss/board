package com.jafa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.jafa.security.CustomLoginSuccessHandler;

public class SecurityBean {

	@Bean
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new CustomLoginSuccessHandler();
	}
}
