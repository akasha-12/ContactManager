package com.smart.config;

import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter {



	/*
	 * The @Bean annotation tells Spring Boot to create an instance of these classes
	 * and add them to the application context. Spring Boot automatically detects
	 * the @Bean annotation and creates a bean from the method return type.
	 * 
	 */
	
	
	@Bean
	public UserDetailsService getUserDetailsService() {

		return new UserDetailServicesImpl();

	}

	
	  @Bean 
	  public BCryptPasswordEncoder passwordEncoder() {
	  
	  
	  return new BCryptPasswordEncoder(); 
	  }
	  
	  
	 

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
	 daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;
	}

	// configure method 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		
	auth.authenticationProvider(authenticationProvider());	
		
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN")
		.antMatchers("/user/**").hasRole("USER")
		.antMatchers("/**").permitAll().and().
		formLogin().loginPage("/signin").
		
		and().
		csrf().disable();
		
		
		
		
		
		
	}
	
	

	
	
	
	
	
	

	
	

}
