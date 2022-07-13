package com.jafa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.jafa.security.CustomAccessDeniedHandler;
import com.jafa.security.CustomLoginSuccessHandler;

@Configuration
@EnableWebSecurity
@Import(value = {SecurityBean.class})
@ComponentScan("com.jafa.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	AuthenticationSuccessHandler loginSuccessHandler; 

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	@Qualifier(value = "뽈롱")
	PasswordEncoder passwordEncoder; 

	@Autowired
	AuthenticationFailureHandler failureHandler; 
	
	@Autowired
	PersistentTokenRepository persistentTokenRepository;

	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
//		auth.inMemoryAuthentication()
//			.withUser("superjojinhyeon").password("{noop}1234").roles("ADMIN");
//		auth.inMemoryAuthentication()
//		.withUser("jojinhyeon").password("{noop}1234").roles("MEMBER", "ADMIN");
			
	}

	 @Override
	   protected void configure(HttpSecurity http) throws Exception {
		 
		 CharacterEncodingFilter filter = new CharacterEncodingFilter();
		 filter.setEncoding("UTF-8");
		 filter.setForceEncoding(true);
		 http.addFilterBefore(filter, CsrfFilter.class);
		 
		  http
		 	  .csrf()
		 	  .ignoringAntMatchers("/uploadAjaxAction", "/deleteFile");
		 
	      http.authorizeRequests()
	      	  .antMatchers("/security/all").permitAll()
	          .antMatchers("/security/admin").access("hasRole('ADMIN')")                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
	          .antMatchers("/security/member").access("hasRole('MEMBER')").
	   
	      and().formLogin()
	          .usernameParameter("loginId")
	          .passwordParameter("loginPw")
	          .loginPage("/customLogin")
	          .loginProcessingUrl("/member/login")
//	          .successHandler(loginSuccessHandler)
	          .failureHandler(failureHandler);
	      
	      http.rememberMe().key("project")
	      	  .tokenRepository(persistentTokenRepository)
	          .tokenValiditySeconds(604800);
	      
	      http.logout()
	          .logoutUrl("/customLogout")
	          .invalidateHttpSession(true)
	          .deleteCookies("remember-me" , "JSESSION_ID");
	        
	      http.exceptionHandling()
	      	  .accessDeniedHandler(new CustomAccessDeniedHandler());
	   }

}