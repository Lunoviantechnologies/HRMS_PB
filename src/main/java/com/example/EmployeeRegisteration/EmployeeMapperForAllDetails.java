package com.example.EmployeeRegisteration;


public class EmployeeMapperForAllDetails {

    public static EmployeeSummaryDTO toSummaryDTO(Employee emp) {
        EmployeeSummaryDTO dto = new EmployeeSummaryDTO();
        dto.setId(emp.getId());
        dto.setPrefix(emp.getPrefix());
        dto.setFirstName(emp.getFirstName());
        dto.setLastName(emp.getLastName());
        dto.setEmailId(emp.getEmailId());
        dto.setContactNumber1(emp.getContactNumber1());
        dto.setGender(emp.getGender());
        dto.setDateOfBirth(emp.getDateOfBirth());
        dto.setNationality(emp.getNationality());
        dto.setWorkEmail(emp.getWorkEmail());
        dto.setJoiningDate(emp.getJoiningDate());
        dto.setHouseNo(emp.getHouseNo());
        dto.setCity(emp.getCity());
        dto.setState(emp.getState());
        dto.setPanNumber(emp.getPanNumber());
        dto.setAadharNumber(emp.getAadharNumber());
        dto.setPassportNumber(emp.getPassportNumber());
        dto.setFatherName(emp.getFatherName());
        dto.setMotherName(emp.getMotherName());
        dto.setMaritalStatus(emp.getMaritalStatus());
        dto.setPreviousCompanyName(emp.getPreviousCompanyName());
        dto.setPreviousExperience(emp.getPreviousExperience());
        dto.setDepartment(emp.getDepartment());
        dto.setDesignation(emp.getDesignation());
        dto.setPreviousCtc(emp.getPreviousCtc());
        dto.setHigherQualification(emp.getHigherQualification());
        dto.setBankName(emp.getBankName());
        dto.setAccountNo(emp.getAccountNo());
        dto.setIfscCode(emp.getIfscCode());
        dto.setBankBranch(emp.getBankBranch());
        dto.setProfilePhoto(emp.getProfilePhoto());
        dto.setBasicEmployeeSalary(emp.getBasicEmployeeSalary());
        dto.setDocument1(emp.getDocument1());
        dto.setDocument2(emp.getDocument2());
        dto.setDocument3(emp.getDocument3());

        return dto;
    }
}