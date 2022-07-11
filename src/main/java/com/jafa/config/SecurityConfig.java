package com.jafa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.jafa.security.CustomLoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	SecurityBean securityBean;

	@Bean
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new CustomLoginSuccessHandler();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("superjojinhyeon").password("{noop}1234").roles("ADMIN");
		auth.inMemoryAuthentication()
		.withUser("jojinhyeon").password("{noop}1234").roles("MEMBER", "ADMIN");
			
	}

	 @Override
	   protected void configure(HttpSecurity http) throws Exception {
	      http.authorizeRequests()
	      .antMatchers("/security/all").permitAll()
	      .antMatchers("/security/admin").access("hasRole('ADMIN')")
	      .antMatchers("/security/member").access("hasRole('MEMBER')");
	   
	      http.formLogin()
	      .usernameParameter("loginId")
	      .passwordParameter("loginPw")
	      .loginPage("/customLogin")
	      .loginProcessingUrl("/member/login")
	      .successHandler(loginSuccessHandler());
	      
	      http.logout()
	         .logoutUrl("/customLogout")
	         .invalidateHttpSession(true)
	         .deleteCookies("remember-me" , "JSESSION_ID");
	        
	   }
}