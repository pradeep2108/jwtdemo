package  com.example.jwtdemo.service;


import java.util.LinkedList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jwtdemo.dao.CustomerRepository;
import com.example.jwtdemo.model.Customer;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerDao;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Customer customer = customerDao.findByEmail(email);
		if(customer==null) {
			throw new  UsernameNotFoundException("User details not found");
		}else {
			List<GrantedAuthority> authorities = new LinkedList();
			authorities.add(new SimpleGrantedAuthority(customer.getRole()));
			return new User(customer.getEmail(),customer.getPassword(),authorities);
		}
			
	}
	
	
	public Customer registerCustomer(Customer customer) {
		return customerDao.save(customer);
		
	}
	
	
	public Customer customerLogin(String email) {
		return customerDao.findByEmail(email);
		
	}
	
	
	
}
