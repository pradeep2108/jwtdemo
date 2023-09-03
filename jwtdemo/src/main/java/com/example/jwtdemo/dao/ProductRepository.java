package com.example.jwtdemo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jwtdemo.model.Product;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	List<Product> findByProductnameIgnoreCase(String productName);
	
	Product findByProductname(String productName);
	   
    @Query(value = "SELECT p.price FROM product p WHERE p.productname= :name", nativeQuery = true)
	Optional<Double> FindPriceByProductName(@Param("name") String name);
	
	
	@Modifying
	@Query(value="UPDATE product SET quantity= :quantity WHERE id = :id",nativeQuery = true )
    int updateQuantity(@Param("quantity") int quantity, @Param("id") int id);

}
