package com.example.jwtdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtdemo.model.ErrorClass;
import com.example.jwtdemo.model.Product;
import com.example.jwtdemo.service.ProductService;




@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/products")
public class HelloController  {
	
	@Autowired
	ProductService productService;
	
	public HelloController(){	
		System.out.println("<<<<<<<<<HelloController is instantiated>>>>>>>>>");	
	}

	@GetMapping("/allproducts")
//	@RequestMapping(method = RequestMethod.GET, value = "/hello")
	public List<Product> getAllProduct() {	
		return productService.getAllProduct();
	}
	
	@GetMapping("get/{id}")
	public ResponseEntity<?> getProduct(@PathVariable int id){
		Product product = productService.getById(id);
		if (product==null) {
			ErrorClass errorClass = new ErrorClass(200, "Product does not exist");
            return new ResponseEntity<ErrorClass>(errorClass, HttpStatus.OK);
		}else {
			
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		}
	}
	
	@GetMapping("/{productName}")
	public List<Product> getByProductName(@PathVariable String productName) {
		
		return productService.getByProductName(productName);
		
	}
	
	@PostMapping("/addproduct")
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		try {
			productService.saveProduct(product);
			return new ResponseEntity<Void> (HttpStatus.OK);
		} catch (Exception e) {
			ErrorClass errorclass = new ErrorClass(500, e.getMessage());
			if (e.getMessage().contains("variant_unique"))
				errorclass = new ErrorClass(500, "This variant already exist in database");
			
				return new ResponseEntity<ErrorClass>(errorclass, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@PutMapping("/update")
	public void updateProduct(@RequestBody Product product) {
		productService.updateProduct(product);
		
	}
	
	@DeleteMapping("delete/{id}")
	public void deleteProduct(@PathVariable int id) {
		productService.deleteProduct(id);
		
	}
}
