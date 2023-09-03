package com.example.jwtdemo.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtdemo.configuration.JWTTokenHelper;
import com.example.jwtdemo.model.Customer;
import com.example.jwtdemo.model.LoginCredentials;
import com.example.jwtdemo.service.CustomerUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@CrossOrigin("http://localhost:3000")
@RestController
public class CustomerController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomerUserDetailsService customerService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	
	private  SecurityContextRepository securityContextRepository = 
			new HttpSessionSecurityContextRepository();
	
	@PostMapping("/public/registration")
	public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {
		
		String password = customer.getPassword();
		String hashPassword = passwordEncoder.encode(password);
		customer.setPassword(hashPassword);
		customer.setRole("user");
		customer = customerService.registerCustomer(customer);
		try{
			
			if(customer.getId()>0) {
				return new ResponseEntity<String>("Registered successfully", HttpStatus.OK);
			}else {
				
				return new ResponseEntity<String>("User not registered", HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
			
		}catch(Exception e){
			
			
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	
			 
		}

	}
	
	
	@PostMapping("/public/login")
	public ResponseEntity<?> login(@RequestBody LoginCredentials lc, HttpServletRequest request, HttpServletResponse response) throws InvalidKeySpecException, NoSuchAlgorithmException{
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(lc.getEmail(), lc.getPassword()));
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		SecurityContextHolder.getContext () .setAuthentication (authentication);
		
		String jwtToken=jwtTokenHelper.generateToken(lc.getEmail(), authorities);
		
		return ResponseEntity.ok (jwtToken) ;
		
//		SecurityContext context = SecurityContextHolder.createEmptyContext();
//		context.setAuthentication(authentication);
//		securityContextRepository.saveContext(context, request, response);
//		
//		//boolean authenticated = authentication.isAuthenticated();
//		System.out.println(authentication.getPrincipal());
//		if (authentication.isAuthenticated()) {
//			
//			return new ResponseEntity<Object> (authentication.getPrincipal(), HttpStatus.OK);
//			
//		}else {
//			
//			return new ResponseEntity<String> ("Invalid credentials", HttpStatus.UNAUTHORIZED);
//		}
		
		
	}
	
	@GetMapping("/logoutsuccess")
	public ResponseEntity<?> logout(HttpServletRequest request){
		
		System.out.println("Inside logout");
		HttpSession session = request.getSession();
		session.invalidate();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SecurityContextHolder.clearContext();
		authentication.setAuthenticated(false);
		return new ResponseEntity<String> ("Logged out Successfully", HttpStatus.OK);	
		
	}
	
	

}
