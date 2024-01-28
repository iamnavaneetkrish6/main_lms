package com.hexaware.loanmanagementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.loanmanagementsystem.entity.CustomerDetails;


@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long> {

	public CustomerDetails findByCustomerName(String customerName);
	
	Optional<CustomerDetails>findByCustomerUsername(String customerUsername);
	public CustomerDetails findByCustomerUsernameAndCustomerPassword(String customerUsername, String customerPassword);

	
    @Query("select u.roles from CustomerDetails u where u.customerUsername=:name")
	public String getRoleByUserName(@Param("name") String userName);
    
    
    @Query("select u.customerName from CustomerDetails u where u.customerUsername=:name")
	public String getNameByUserName(@Param("name") String userName);
}
