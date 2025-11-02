package com.example.EmpDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.EmployeeRegisteration.Employee;

public class EmployeeMapper {

    // Directory to save profile photos
    private static final String PROFILE_UPLOAD_DIR = "uploads/profilePhotos/";


    public static Employee toEntity(EmployeeDTO dto) throws Exception {
        Employee emp = new Employee();

        emp.setPrefix(dto.getPrefix());
        emp.setFirstName(dto.getFirstName());
        emp.setLastName(dto.getLastName());
        emp.setEmailId(dto.getEmailId());
        emp.setContactNumber1(dto.getContactNumber1());
        emp.setGender(dto.getGender());
        emp.setDateOfBirth(dto.getDateOfBirth());
        emp.setNationality(dto.getNationality());
        emp.setWorkEmail(dto.getWorkEmail());
        emp.setJoiningDate(dto.getJoiningDate());
        emp.setHouseNo(dto.getHouseNo());
        emp.setCity(dto.getCity());
        emp.setState(dto.getState());
        emp.setPanNumber(dto.getPanNumber());
        emp.setAadharNumber(dto.getAadharNumber());
        emp.setPassportNumber(dto.getPassportNumber());
        emp.setFatherName(dto.getFatherName());
        emp.setMotherName(dto.getMotherName());
        emp.setMaritalStatus(dto.getMaritalStatus());
        emp.setPreviousCompanyName(dto.getPreviousCompanyName());
        emp.setPreviousExperience(dto.getPreviousExperience());
        emp.setDepartment(dto.getDepartment());
        emp.setDesignation(dto.getDesignation());
        emp.setPreviousCtc(dto.getPreviousCtc());
        emp.setHigherQualification(dto.getHigherQualification());
        emp.setBankName(dto.getBankName());
        emp.setAccountNo(dto.getAccountNo());
        emp.setIfscCode(dto.getIfscCode());
        emp.setBankBranch(dto.getBankBranch());
        emp.setPassword(dto.getPassword()); 
        emp.setBasicEmployeeSalary(dto.getBasicEmployeeSalary());

        if (dto.getProfilePhoto() != null && !dto.getProfilePhoto().isEmpty()) {
            emp.setProfilePhoto(dto.getProfilePhoto().getBytes());
        }
        if (dto.getDocument1() != null && !dto.getDocument1().isEmpty()) {
            emp.setDocument1(dto.getDocument1().getBytes());
        }
        if (dto.getDocument2() != null && !dto.getDocument2().isEmpty()) {
            emp.setDocument2(dto.getDocument2().getBytes());
        }
        if (dto.getDocument3() != null && !dto.getDocument3().isEmpty()) {
            emp.setDocument3(dto.getDocument3().getBytes());
        }
        if (dto.getImageDir() != null && !dto.getImageDir().isEmpty()) {
            emp.setImageDir(dto.getImageDir().getBytes());
        }

        return emp;
    }

    // Convert Entity to Response DTO
    public static EmployeeResponseDTO toResponseDTO(Employee emp) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(emp.getId());
      //  dto.setPrefix(emp.getPrefix());
        dto.setFirstName(emp.getFirstName());
        dto.setLastName(emp.getLastName());
        dto.setEmailId(emp.getEmailId());
        dto.setContactNumber1(emp.getContactNumber1());
        dto.setGender(emp.getGender());
        dto.setDepartment(emp.getDepartment());
        dto.setDesignation(emp.getDesignation());
        dto.setJoiningDate(emp.getJoiningDate());
        dto.setWorkEmail(emp.getWorkEmail());
        dto.setBasicEmployeeSalary(emp.getBasicEmployeeSalary());
        dto.setRole(emp.getRole());
        //dto.setImageDir(emp.getImageDir()); // path for frontend
        return dto;
    }

    // Password validation
    private static boolean isValidPassword(String password) {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$";
        return password.matches(pattern);
    }
}
