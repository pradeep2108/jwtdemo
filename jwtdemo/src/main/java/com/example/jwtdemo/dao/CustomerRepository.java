package com.example.jwtdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jwtdemo.model.Customer;



@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	 Customer findByEmail(String email);

}
