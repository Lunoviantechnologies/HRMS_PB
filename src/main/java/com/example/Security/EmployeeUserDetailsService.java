package com.example.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.EmployeeRegisteration.Employee;
import com.example.EmployeeRegisteration.EmployeeRepository;

import java.util.Collections;   // ✅ CORRECT

@Service
public class EmployeeUserDetailsService  implements org.springframework.security.core.userdetails.UserDetailsService{

    @Autowired
    private EmployeeRepository employeeRepository;
    
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee emp = employeeRepository.findByEmailId(email)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found with email: " + email));

        String role = emp.getRole() != null ? emp.getRole().toUpperCase() : "EMPLOYEE";

        return org.springframework.security.core.userdetails.User
                .withUsername(emp.getEmailId())
                .password(emp.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)))
                .build();
    }
 }
