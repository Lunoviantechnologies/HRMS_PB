package com.example.Adminerepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AdmineEntity.AdmineEntity;

public interface AdmineRepo extends JpaRepository<AdmineEntity, String>{

	Optional<AdmineEntity> findByEmailAndPassword(String email,String password);
	
	 Optional<AdmineEntity> findByEmail(String email);
	 
	 List<AdmineEntity> findByRole(String role);
}	