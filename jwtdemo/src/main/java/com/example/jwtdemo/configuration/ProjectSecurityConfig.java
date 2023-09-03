package com.example.jwtdemo.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.jwtdemo.service.CustomerUserDetailsService;

@Configuration
public class ProjectSecurityConfig {
	
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration configuration) throws Exception{
				return configuration.getAuthenticationManager();
		
	}
	

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.sessionManagement((config)-> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.authorizeHttpRequests(
				(requests) ->
				{   requests.requestMatchers("/public/**").permitAll()
					.requestMatchers(HttpMethod.OPTIONS).permitAll()
					.anyRequest().authenticated();
				
		//requests.anyRequest().permitAll();
//		try {
//		requests.requestMatchers(HttpMethod.POST,"/addproducts").authenticated()
//				.requestMatchers("/public/**").permitAll()
//				.requestMatchers("/public/registration").permitAll();   
//		}catch(Exception e){
//			e.printStackTrace();
//		}
				});
		http.addFilterBefore(new JWTAuthenticationFilter(customerUserDetailsService, jwtTokenHelper)
				, UsernamePasswordAuthenticationFilter.class);
		
		http.csrf((csrf)->csrf.disable());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();	
	}
	
	

////	@Bean
////	public UserDetailsService userDetailsService(Da4taSource datasource) {
//////		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//////		manager.createUser(User.withDefaultPasswordEncoder()
//////				.username("pradeep").password("root").roles("user").build());
//////		return manager;
////		return new  JdbcUserDetailsManager(datasource);
//		
////	}
	
//	@Bean
//	public PasswordEncoder getPasswordEncoder() {
//		return NoOpPasswordEncoder.get);
//		
//	}
	
	
	

}
