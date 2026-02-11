package com.example.EmployeeRegisteration;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ArchievedEmployees.ArchivedEmployee;
import com.example.ArchievedEmployees.ArchivedEmployeeRepository;
import com.example.EmpDTO.EmployeeDTO;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

	@Autowired
    private EmployeeRepository employeeRepository;
	
	@Autowired
	ArchivedEmployeeRepository archivedEmployeeRepository;

	public Employee createEmployee(Employee employee) {
	    return employeeRepository.save(employee);
	}

	public List<Employee> allEmployeeList() {
		
		return employeeRepository.findAll();
	}	

	
	public Employee getEmployeeWithLeaves(Long id) {
	    return employeeRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
	}	
	
	@Transactional
	public Employee deleteEmployeeById(Long id) {
	    Employee employee = employeeRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Employee not found"));

	    // Create archive record
	    ArchivedEmployee archived = new ArchivedEmployee();
	    archived.setOriginalEmployeeId(employee.getId());
	    archived.setFirstName(employee.getFirstName());
	    archived.setLastName(employee.getLastName());
	    archived.setEmailId(employee.getEmailId());
	    archived.setContactNumber1(employee.getContactNumber1());
	    archived.setDepartment(employee.getDepartment());
	    archived.setDesignation(employee.getDesignation());
	    archived.setJoiningDate(employee.getJoiningDate());
	    archived.setDateOfBirth(employee.getDateOfBirth());
	    archived.setProfilePhoto(employee.getProfilePhoto());
	    archived.setDeletedAt(LocalDate.now());
	   

	    archivedEmployeeRepository.save(archived);

	    // Now delete the employee
	    employeeRepository.deleteById(id);

	    return employee; // return deleted employee details
	}
	 public Employee updateEmployee(Long id, EmployeeDTO dto, PasswordEncoder passwordEncoder) {
	        Employee existingEmployee = employeeRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

	        if (dto.getPrefix() != null) existingEmployee.setPrefix(dto.getPrefix());
	        if (dto.getFirstName() != null) existingEmployee.setFirstName(dto.getFirstName());
	        if (dto.getLastName() != null) existingEmployee.setLastName(dto.getLastName());
	        if (dto.getEmailId() != null) existingEmployee.setEmailId(dto.getEmailId());
	        if (dto.getContactNumber1() != null) existingEmployee.setContactNumber1(dto.getContactNumber1());
	        if (dto.getGender() != null) existingEmployee.setGender(dto.getGender());
	        if (dto.getDateOfBirth() != null) existingEmployee.setDateOfBirth(dto.getDateOfBirth());
	        if (dto.getNationality() != null) existingEmployee.setNationality(dto.getNationality());
	        if (dto.getWorkEmail() != null) existingEmployee.setWorkEmail(dto.getWorkEmail());
	        if (dto.getJoiningDate() != null) existingEmployee.setJoiningDate(dto.getJoiningDate());
	        if (dto.getHouseNo() != null) existingEmployee.setHouseNo(dto.getHouseNo());
	        if (dto.getCity() != null) existingEmployee.setCity(dto.getCity());
	        if (dto.getState() != null) existingEmployee.setState(dto.getState());
	        if (dto.getPanNumber() != null) existingEmployee.setPanNumber(dto.getPanNumber());
	        if (dto.getAadharNumber() != null) existingEmployee.setAadharNumber(dto.getAadharNumber());
	        if (dto.getPassportNumber() != null) existingEmployee.setPassportNumber(dto.getPassportNumber());
	        if (dto.getFatherName() != null) existingEmployee.setFatherName(dto.getFatherName());
	        if (dto.getMotherName() != null) existingEmployee.setMotherName(dto.getMotherName());
	        if (dto.getMaritalStatus() != null) existingEmployee.setMaritalStatus(dto.getMaritalStatus());
	        if (dto.getPreviousCompanyName() != null) existingEmployee.setPreviousCompanyName(dto.getPreviousCompanyName());
	        if (dto.getPreviousExperience() != null) existingEmployee.setPreviousExperience(dto.getPreviousExperience());
	        if (dto.getDepartment() != null) existingEmployee.setDepartment(dto.getDepartment());
	        if (dto.getDesignation() != null) existingEmployee.setDesignation(dto.getDesignation());
	        if (dto.getPreviousCtc() != null) existingEmployee.setPreviousCtc(dto.getPreviousCtc());
	        if (dto.getHigherQualification() != null) existingEmployee.setHigherQualification(dto.getHigherQualification());
	        if (dto.getBankName() != null) existingEmployee.setBankName(dto.getBankName());
	        if (dto.getAccountNo() != null) existingEmployee.setAccountNo(dto.getAccountNo());
	        if (dto.getIfscCode() != null) existingEmployee.setIfscCode(dto.getIfscCode());
	        if (dto.getBankBranch() != null) existingEmployee.setBankBranch(dto.getBankBranch());
	        if (dto.getBasicEmployeeSalary() > 0) existingEmployee.setBasicEmployeeSalary(dto.getBasicEmployeeSalary());

	        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
	            existingEmployee.setPassword(passwordEncoder.encode(dto.getPassword()));
	        }

	        try {
	            if (dto.getProfilePhoto() != null && !dto.getProfilePhoto().isEmpty()) {
	                existingEmployee.setProfilePhoto(dto.getProfilePhoto().getBytes());
	            }
	            if (dto.getDocument1() != null && !dto.getDocument1().isEmpty()) {
	                existingEmployee.setDocument1(dto.getDocument1().getBytes());
	            }
	            if (dto.getDocument2() != null && !dto.getDocument2().isEmpty()) {
	                existingEmployee.setDocument2(dto.getDocument2().getBytes());
	            }
	            if (dto.getDocument3() != null && !dto.getDocument3().isEmpty()) {
	                existingEmployee.setDocument3(dto.getDocument3().getBytes());
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to process file uploads", e);
	        }

	        return employeeRepository.save(existingEmployee);
	    }

	public Employee login(String emailId) {
	    return employeeRepository.findByEmailId(emailId).orElse(null);
	}
	
	public Optional<Employee> findiByEmployee(String email){
		return employeeRepository.findByEmailId(email);
	}

}
