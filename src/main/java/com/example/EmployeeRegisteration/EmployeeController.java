package com.example.EmployeeRegisteration;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.EmpDTO.EmployeeDTO;
import com.example.EmpDTO.EmployeeMapper;
import com.example.EmpDTO.EmployeeUpdateDTO;
import com.example.EmpDTO.UpdatingEmpInfo;
import com.example.Notification.NotificationService;
import com.example.employeeRegistrations.ArchivedEmployee;
import com.example.employeeRegistrations.ArchivedEmployeeRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin("*")
public class EmployeeController {

	    @Autowired private EmployeeRepository employeeRepository;
	    @Autowired private PasswordEncoder passwordEncoder;
	    @Autowired private EmployeeService employeeService;
	    
	    @Autowired ArchivedEmployeeRepository archivedEmployeeRepository;
	    
	    
	    @Autowired
	    private NotificationService notificationService;

	    private static final Path REGISTERED_DIR = Paths.get("./faces/registered");   

        @PostMapping(value = "/register", consumes = {"multipart/form-data"})
        public ResponseEntity<?> register(@ModelAttribute EmployeeDTO dto) {
        try {
            Employee emp = EmployeeMapper.toEntity(dto);
            emp.setPassword(passwordEncoder.encode(emp.getPassword()));
            emp.setRole("employee");

            Employee saved = employeeRepository.save(emp);

//            notificationService.sendToEmployee(saved.getId(),
//            		"Welcome "+saved.getFirstName()+ "! Your acount has been created sucessfully.");
//            notificationService.sendToAll("New Employee "+saved.getFirstName()+" has  joined the company!");
//           
            return ResponseEntity.ok(Map.of(
                "id", emp.getId(),
                "message", "Employee registered successfully"
            ));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Error: " + ex.getMessage());
        }
    }
    

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Employee deleted = employeeService.deleteEmployeeById(id);
            return ResponseEntity.ok(Map.of(
                    "id", deleted.getId(),
                    "message", "Employee deleted successfully"
            ));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(Map.of("message", ex.getMessage()));
        }
    }
    
    
    @GetMapping("/archieved_employees")
    public ResponseEntity<List<ArchivedEmployee>> getDeletedEmployees() {
        List<ArchivedEmployee> archivedEmployees = archivedEmployeeRepository.findAll();
        return ResponseEntity.ok(archivedEmployees);
    }   
    
    @PutMapping(value = "/update/{email}", consumes = {"multipart/form-data"})
    @Transactional
    public ResponseEntity<?> updateEmployee(
            @PathVariable String email,
            @ModelAttribute EmployeeUpdateDTO dto) {

        Employee existing = employeeRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + email));

        // --- Update only if provided ---
        if (dto.getPrefix() != null) existing.setPrefix(dto.getPrefix());
        if (dto.getFirstName() != null) existing.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) existing.setLastName(dto.getLastName());
        if (dto.getContactNumber1() != null) existing.setContactNumber1(dto.getContactNumber1());
        if (dto.getGender() != null) existing.setGender(dto.getGender());
        if (dto.getDateOfBirth() != null) existing.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getNationality() != null) existing.setNationality(dto.getNationality());
        if (dto.getWorkEmail() != null) existing.setWorkEmail(dto.getWorkEmail());
        if (dto.getJoiningDate() != null) existing.setJoiningDate(dto.getJoiningDate());
        if (dto.getHouseNo() != null) existing.setHouseNo(dto.getHouseNo());
        if (dto.getCity() != null) existing.setCity(dto.getCity());
        if (dto.getState() != null) existing.setState(dto.getState());
        if (dto.getPanNumber() != null) existing.setPanNumber(dto.getPanNumber());
        if (dto.getAadharNumber() != null) existing.setAadharNumber(dto.getAadharNumber());
        if (dto.getPassportNumber() != null) existing.setPassportNumber(dto.getPassportNumber());
        if (dto.getFatherName() != null) existing.setFatherName(dto.getFatherName());
        if (dto.getMotherName() != null) existing.setMotherName(dto.getMotherName());
        if (dto.getMaritalStatus() != null) existing.setMaritalStatus(dto.getMaritalStatus());
        if (dto.getPreviousCompanyName() != null) existing.setPreviousCompanyName(dto.getPreviousCompanyName());
        if (dto.getPreviousExperience() != null) existing.setPreviousExperience(dto.getPreviousExperience());
        if (dto.getDepartment() != null) existing.setDepartment(dto.getDepartment());
        if (dto.getDesignation() != null) existing.setDesignation(dto.getDesignation());
        if (dto.getPreviousCtc() != null) existing.setPreviousCtc(dto.getPreviousCtc());
        if (dto.getHigherQualification() != null) existing.setHigherQualification(dto.getHigherQualification());
        if (dto.getBankName() != null) existing.setBankName(dto.getBankName());
        if (dto.getAccountNo() != null) existing.setAccountNo(dto.getAccountNo());
        if (dto.getIfscCode() != null) existing.setIfscCode(dto.getIfscCode());
        if (dto.getBankBranch() != null) existing.setBankBranch(dto.getBankBranch());
        if (dto.getBasicEmployeeSalary() != null) existing.setBasicEmployeeSalary(dto.getBasicEmployeeSalary());

        try {
            if (dto.getProfilePhoto() != null && !dto.getProfilePhoto().isEmpty()) {
                existing.setProfilePhoto(dto.getProfilePhoto().getBytes());
            }
            if (dto.getDocument1() != null && !dto.getDocument1().isEmpty()) {
                existing.setDocument1(dto.getDocument1().getBytes());
            }
            if (dto.getDocument2() != null && !dto.getDocument2().isEmpty()) {
                existing.setDocument2(dto.getDocument2().getBytes());
            }
            if (dto.getDocument3() != null && !dto.getDocument3().isEmpty()) {
                existing.setDocument3(dto.getDocument3().getBytes());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error while processing file uploads"));
        }

        Employee updated = employeeRepository.save(existing);

        return ResponseEntity.ok(Map.of(
                "id", updated.getId(),
                "message", "Employee updated successfully"
        ));
    }   
    @PutMapping("/updateEmp/{email}")
    public ResponseEntity<?> updateEmp(
            @PathVariable("email") String email,
            @ModelAttribute UpdatingEmpInfo employeeData, // normal fields from form-data
            @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto) throws IOException {

        Optional<Employee> byEmailId = employeeRepository.findByEmailId(email);
        if (byEmailId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }
        Employee employee = byEmailId.get(); 
        
        employee.setCity(employeeData.getCity()) ;
        employee.setGender(employeeData.getGender());
        employee.setNationality(employeeData.getNationality());
        employee.setHouseNo(employeeData.getHouseNo());
        employee.setState(employeeData.getState());
        employee.setMaritalStatus(employeeData.getMaritalStatus());
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            employee.setProfilePhoto(profilePhoto.getBytes());
        }

        employeeRepository.save(employee);

        return ResponseEntity.ok("Employee updated successfully");
    }   
    
    @GetMapping("/check-name")
    public Map<String, Boolean> checkNameExists(
            @RequestParam String firstName,
            @RequestParam String lastName) {

    	System.out.println(firstName);
    	System.out.println(lastName);
        boolean exists = employeeRepository.existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isUnique", !exists); // true if name is unique
        return response;
    }
    @GetMapping("/findByEmp/{email}")
    public Optional<Employee> findByEmployee(@PathVariable(name="email")String email){
    	return employeeRepository.findByEmailId(email);
    }
}
