package com.hexaware.loanmanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {

	public UserNotFoundException(HttpStatus status,String msg) {
		super(status,msg); 

} 

}