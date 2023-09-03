package com.example.jwtdemo.model;

public class ErrorClass {
	
	private int id;
	private String errorMessage;
	
	
	public ErrorClass(int id, String errorMessage) {
		super();
		this.id = id;
		this.errorMessage = errorMessage;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
