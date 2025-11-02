package com.example.EmpDTO;

import java.time.LocalDate;

public class EmployeeResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String contactNumber1;
    private String gender;
    private String department;
    private String designation;
    private LocalDate joiningDate;
    private String workEmail;

    private String profilePic;
    private String document1;
    private String document2;
    private String document3;

    private String role;
    public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	private long basicEmployeeSalary;    // Getters and Setters

  
	public long getBasicEmployeeSalary() {
		return basicEmployeeSalary;
	}
	public void setBasicEmployeeSalary(long basicEmployeeSalary) {
		this.basicEmployeeSalary = basicEmployeeSalary;
	}
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public String getContactNumber1() {
        return contactNumber1;
    }
    public void setContactNumber1(String contactNumber1) {
        this.contactNumber1 = contactNumber1;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public LocalDate getJoiningDate() {
        return joiningDate;
    }
    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }
    public String getWorkEmail() {
        return workEmail;
    }
    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }
    public String getProfilePic() {
        return profilePic;
    }
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
    public String getDocument1() {
        return document1;
    }
    public void setDocument1(String document1) {
        this.document1 = document1;
    }
    public String getDocument2() {
        return document2;
    }
    public void setDocument2(String document2) {
        this.document2 = document2;
    }
    public String getDocument3() {
        return document3;
    }
    public void setDocument3(String document3) {
        this.document3 = document3;
    }
}
