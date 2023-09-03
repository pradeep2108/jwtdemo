package com.example.jwtdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.jwtdemo.dao.ProductRepository;
import com.example.jwtdemo.model.Product;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	
	@Autowired
	private ProductRepository prodDao;
	
	
	
	
    @Autowired
	public ProductServiceImpl(ProductRepository prodDao) {
		super();
		this.prodDao = prodDao;
	}

	@Override
	public List<Product> getAllProduct() {
		// TODO Auto-generated method stub
		return prodDao.findAll();
	}

	@Override
	public List<Product> getByProductName(String ProductName) {
		
		return prodDao.findByProductnameIgnoreCase(ProductName);
	}

	
	@Override
	public void saveProduct(Product product) {
	    	prodDao.save(product);	
	}

	@Override
	public void updateProduct(Product product) {
	         prodDao.save(product);	
	}

	@Override
	public void deleteProduct(int id) {
		prodDao.deleteById(id);


	}

	@Override
	public Product getById(int id) {
		Optional <Product> prod =  prodDao.findById(id);
		if (prod.isPresent()) {
			Product p = prod.get();
			return p;
		}else {
			return null;
		}
		
	}

	@Override
	public int getAvailableQuantity(int id) {
		Optional<Product> product = prodDao.findById(id);
		
		if (product.isPresent()) {
			return product.get().getQuantity();
			
		}else {
			return 0;
		}
	}


	@Override
	public int updateQuantity(int quantity, int id) {
		return prodDao.updateQuantity(quantity,  id);	
	}


	
	
	

}
