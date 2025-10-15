package com.example.AdmineController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // ✅ Required
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.AdmineEntity.AdmineEntity;
import com.example.AdmineEntity.LoginRequest;
import com.example.AdmineEntity.leavepolicy;
import com.example.AdmineInfo.Admineinfo;
import com.example.Adminerepository.leavePolicyRepo;
import com.example.EmployeeRegisteration.Employee;
import com.example.EmployeeRegisteration.EmployeeService;
import com.example.Security.JwtUtil;

@RestController
@CrossOrigin("*")
public class AdmineControllerClass {
	
	    @Autowired
	    private Admineinfo admineinfo;
	    @Autowired
	    private EmployeeService employeeService;
	    @Autowired
	    private BCryptPasswordEncoder passwordEncoder;
	
	    @Autowired
	    leavePolicyRepo leavePolicyrepo;
	    @Autowired
	    private JwtUtil jwtUtil;    
	    

	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
	        String email = loginRequest.getEmail();
	        String password = loginRequest.getPassword();

	        // Admin login
	        AdmineEntity admin = admineinfo.getAdminByEmail(email);
	       // if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
	        if (admin != null && password.equals(admin.getPassword())) {
	            //String token = jwtUtil.generateToken(admin.getEmail(), "ADMIN", admin.getEmail());
	        	//String token = jwtUtil.generateAccessToken(admin.getEmail(), "ADMIN", admin.getEmail());
	            String token = jwtUtil.generateAccessToken(admin.getEmail(), "ADMIN");

	            return ResponseEntity.ok(new JwtResponse("Bearer " + token, "ADMIN", admin.getEmail()));
	        }

	     // Employee login
	        Employee employee = employeeService.login(email);

	        if (employee != null && passwordEncoder.matches(password, employee.getPassword())) {
	          //String token = jwtUtil.generateAccessToken(employee.getEmailId(), "EMPLOYEE", employee.getEmailId());
	            String token = jwtUtil.generateAccessToken(employee.getEmailId(), "EMPLOYEE");

	            return ResponseEntity.ok(new JwtResponse("Bearer " + token, "EMPLOYEE", employee.getEmailId()));
	        }

	        return ResponseEntity.status(401).body("invalid email or password");
	    }
	    
	    @PostMapping("/leave_policy")
	    public ResponseEntity<?> LeavePolicys(@RequestBody leavepolicy leavepolicy)
	    {
	    	com.example.AdmineEntity.leavepolicy save = leavePolicyrepo.save(leavepolicy);
	    	return ResponseEntity.status(200).body("data is added");
	    }
	    
	    // Update Existing Leave Policy
	    @PutMapping("/leave_policy/{id}")
	    public ResponseEntity<?> updateLeavePolicy(@PathVariable int id, @RequestBody leavepolicy updatedPolicy) {
	        Optional<leavepolicy> existingPolicyOpt = leavePolicyrepo.findById(id);

	        if (existingPolicyOpt.isPresent()) {
	        	leavepolicy existingPolicy = existingPolicyOpt.get();

	            existingPolicy.setAnnualLeaves(updatedPolicy.getAnnualLeaves());
	            existingPolicy.setCasualLeaves(updatedPolicy.getCasualLeaves());
	            existingPolicy.setSickLeaves(updatedPolicy.getSickLeaves());
	            existingPolicy.setMaternityLeaves(updatedPolicy.getMaternityLeaves());
	            existingPolicy.setCarryForwarelimit(updatedPolicy.getCarryForwarelimit());

	            leavepolicy saved = leavePolicyrepo.save(existingPolicy);
	            return ResponseEntity.ok(saved);
	        } else {
	            return ResponseEntity.status(404).body("Leave Policy not found with id " + id);
	        }
	    }

	    @GetMapping("/leavePolicy_table")
	    public ResponseEntity<?> getAllLeavePolicy()
	    {
	    	List<leavepolicy> all = leavePolicyrepo.findAll();
	    	return ResponseEntity.ok(all);
	    }
	}
