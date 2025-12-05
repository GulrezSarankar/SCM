package com.scm.Helper;

public class ResourceNotFoundException extends RuntimeException {
	
//	 public ResourceNotFoundException(String message) {
//	        super(message);
//	    }
//	 
//	 public ResourceNotFoundException() {
//	        super("Resource not found");
//	    }
	
	public ResourceNotFoundException(String messsage) {
		super(messsage);
	}
	
	public ResourceNotFoundException() {
		super("Resource Not found");
	}

}
