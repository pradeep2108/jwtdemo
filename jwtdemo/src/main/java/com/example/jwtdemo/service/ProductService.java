package com.example.jwtdemo.service;

import java.util.List;
import java.util.Optional;

import com.example.jwtdemo.model.Product;




public interface ProductService {
	List<Product> getAllProduct();
	
	List<Product> getByProductName(String ProductName);
	
	void saveProduct(Product product);
	
	void updateProduct(Product product);
	
	void deleteProduct(int id);
	
	Product getById(int id);
	
	int getAvailableQuantity(int id);
	
	int updateQuantity(int quantity, int id);

	
	
}
