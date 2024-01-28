package com.hexaware.loanmanagementsystem.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.loanmanagementsystem.dto.CustomerDetailsDTO;
import com.hexaware.loanmanagementsystem.entity.AdminDetails;
import com.hexaware.loanmanagementsystem.entity.CustomerDetails;
import com.hexaware.loanmanagementsystem.exception.CustomerAccountDetailsNotFoundException;
import com.hexaware.loanmanagementsystem.exception.CustomerDetailsNotFoundException;
import com.hexaware.loanmanagementsystem.exception.UserNotFoundException;
import com.hexaware.loanmanagementsystem.repository.AdminDetailsRepository;
import com.hexaware.loanmanagementsystem.repository.CustomerDetailsRepository;

@Service
public class CustomerDetailsServiceImp implements ICustomerDetailsService {

	@Autowired
	private CustomerDetailsRepository customerDetailsRepository;
	
	@Autowired
	private AdminDetailsRepository adminDetailsRepository;
	
	@Autowired
	private  PasswordEncoder passwordEncoder;


	Logger logger = LoggerFactory.getLogger(CustomerDetailsServiceImp.class);
	
	@Override
	public CustomerDetails customerRegisteration(CustomerDetailsDTO customer) {

		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setCustomerName(customer.getCustomerName());
		customerDetails.setCustomerUsername(customer.getCustomerUsername());
		customerDetails.setCustomerPassword(passwordEncoder.encode(customer.getCustomerPassword()));
		customerDetails.setCustomerAddress(customer.getCustomerAddress());
		customerDetails.setCustomerState(customer.getCustomerState());
		customerDetails.setCustomerState(customer.getCustomerState());
		customerDetails.setCustomerCountry(customer.getCustomerCountry());
		customerDetails.setCustomerEmailId(customer.getCustomerEmailId());

		return customerDetailsRepository.save(customerDetails);
	}

	@Override
	public CustomerDetails updateCustomerDetails(CustomerDetailsDTO customer, long id) {
		CustomerDetails customerDetails = customerDetailsRepository.findById(id).orElseThrow(()->new CustomerAccountDetailsNotFoundException(HttpStatus.NOT_FOUND, "No Customer  details linked with customer Id " + id + " found"));
		
		customerDetails.setCustomerId(customerDetails.getCustomerId());
		customerDetails.setCustomerName(customer.getCustomerName());
		customerDetails.setCustomerUsername(customer.getCustomerUsername());
		customerDetails.setCustomerPassword(passwordEncoder.encode(customer.getCustomerPassword()));
		customerDetails.setCustomerAddress(customer.getCustomerAddress());
		customerDetails.setCustomerState(customer.getCustomerState());
		customerDetails.setCustomerState(customer.getCustomerState());
		customerDetails.setCustomerCountry(customer.getCustomerCountry());
		customerDetails.setCustomerEmailId(customer.getCustomerEmailId());

		return customerDetailsRepository.save(customerDetails);
	}

	@Override
	public void deleteCustomer(long customerId) {
	    customerDetailsRepository.deleteById(customerId);
	}

	@Override
	public CustomerDetails getCustomerDetailsById(long customerId) {

		return customerDetailsRepository.findById(customerId)
				.orElseThrow(()->new CustomerAccountDetailsNotFoundException(HttpStatus.NOT_FOUND, "No Customer  details linked with customer Id " + customerId + " found"));
	}
	@Override
	public List<CustomerDetails> getAllCustomerDetails() {

		List<CustomerDetails> customerDetails = customerDetailsRepository.findAll();
		
		if( customerDetails.isEmpty()) {
			throw new CustomerDetailsNotFoundException(HttpStatus.NOT_FOUND, "Customerdetails not found ");
		}
		
		return customerDetails;
	}

	@Override
	public String getLoginRole(String name) {

		String role;
		System.out.println(name);
		String adminInfo =  adminDetailsRepository.getRoleByAdminName(name);
		
		   if (adminInfo!=null) {
		        role=adminInfo;
		    }
		   else {
		        String userInfo = customerDetailsRepository.getRoleByUserName(name);

		        if (userInfo!=null) {
		        	role=userInfo;
		        }
	            else {
	                throw new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found: " + name);
	            }
	        }
		
		return role;
	}

	@Override
	public long getLoginId(String name) {
		
		long id;
		Optional<AdminDetails> adminInfo = adminDetailsRepository.findByAdminUsername(name);
		
		if (adminInfo.isPresent()) {
			AdminDetails admin = adminInfo.get();
            id=admin.getAdminId();
        } else {
            Optional<CustomerDetails> userInfo = customerDetailsRepository.findByCustomerUsername(name);

            if (userInfo.isPresent()) {
            	CustomerDetails user = userInfo.get();
                id=user.getCustomerId();
            } else {
            	throw new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found: " + name);
            }
        }
		
		return id;
	}

	@Override
	public String getNameByUserName(String name) {
	
		String customerName =  customerDetailsRepository.getNameByUserName(name);
		
		
		return customerName;
	}

}
