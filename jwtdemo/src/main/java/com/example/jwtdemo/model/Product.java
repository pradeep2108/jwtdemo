package com.example.jwtdemo.model;

import java.util.Objects;

import org.springframework.stereotype.Repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Product {
	@Id
	@SequenceGenerator(name = "product_id_seq", sequenceName="product_id_seq", initialValue = 1, allocationSize = 1) 
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	                generator = "product_id_seq")
	private int id;
	private String productname;
	private double price;
	private int quantity;
	private String variant;
	
	public Product() {}
	
	public Product(String productName, double price, int quantity,String variant) {
		super();
		this.productname = productName;
		this.price = price;
		this.quantity = quantity;
		this.variant = variant;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getProductName() {
		return productname;
	}


	public void setProductName(String productName) {
		this.productname = productName;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productname=" + productname + ", price=" + price + ", quantity=" + quantity
				+ ", variant=" + variant + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, price, productname, quantity, variant);
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return id == other.id && Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(productname, other.productname) && quantity == other.quantity
				&& Objects.equals(variant, other.variant);
	}
	
	
	
}