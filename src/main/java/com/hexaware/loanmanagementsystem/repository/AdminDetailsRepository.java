package com.hexaware.loanmanagementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.loanmanagementsystem.entity.AdminDetails;



@Repository
public interface AdminDetailsRepository extends JpaRepository<AdminDetails, Long> {
 
	Optional<AdminDetails>findByAdminName(String adminName);
	Optional<AdminDetails>findByAdminUsername(String adminUsername);
	public AdminDetails findByAdminUsernameAndPassword(String adminUsername, String password);
	
	
	 @Query("select a.roles from AdminDetails a where a.adminUsername=:name")
	 public String getRoleByAdminName(@Param("name") String adminName);
	
}
