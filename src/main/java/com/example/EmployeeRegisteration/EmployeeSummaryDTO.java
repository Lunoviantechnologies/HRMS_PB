package com.example.EmployeeRegisteration;

import java.time.LocalDate;
import java.util.Arrays;

public class EmployeeSummaryDTO {
	
    private Long id;
    private String prefix;
    private String firstName;
    private String lastName;
    private String emailId;
    private String contactNumber1;
    private String gender;
    private LocalDate dateOfBirth;
    private String nationality;
    private String workEmail;
    private LocalDate joiningDate;
    private String houseNo;
    private String city;
    private String state;

    private String panNumber;
    private String aadharNumber;
    private String passportNumber;

    private String fatherName;
    private String motherName;
    private String maritalStatus;

    private String previousCompanyName;
    private String previousExperience;
    private String department;
    private String designation;
    private String previousCtc;

    private String higherQualification;

    private String bankName;
    private String accountNo;
    private String ifscCode;
    private String bankBranch;

    private long basicEmployeeSalary;
   
    public long getBasicEmployeeSalary() {
		return basicEmployeeSalary;
	}
	public void setBasicEmployeeSalary(long basicEmployeeSalary) {
		this.basicEmployeeSalary = basicEmployeeSalary;
	}
	private byte[] profilePhoto;
    private byte[] document1;
    private byte[] document2;
    private byte[] document3;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
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
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getWorkEmail() {
		return workEmail;
	}
	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}
	public LocalDate getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}
	public String getHouseNo() {
		return houseNo;
	}
	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getPreviousCompanyName() {
		return previousCompanyName;
	}
	public void setPreviousCompanyName(String previousCompanyName) {
		this.previousCompanyName = previousCompanyName;
	}
	public String getPreviousExperience() {
		return previousExperience;
	}
	public void setPreviousExperience(String previousExperience) {
		this.previousExperience = previousExperience;
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
	public String getPreviousCtc() {
		return previousCtc;
	}
	public void setPreviousCtc(String previousCtc) {
		this.previousCtc = previousCtc;
	}
	public String getHigherQualification() {
		return higherQualification;
	}
	public void setHigherQualification(String higherQualification) {
		this.higherQualification = higherQualification;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public byte[] getProfilePhoto() {
		return profilePhoto;
	}
	public void setProfilePhoto(byte[] profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
	public byte[] getDocument1() {
		return document1;
	}
	public void setDocument1(byte[] document1) {
		this.document1 = document1;
	}
	public byte[] getDocument2() {
		return document2;
	}
	public void setDocument2(byte[] document2) {
		this.document2 = document2;
	}
	public byte[] getDocument3() {
		return document3;
	}
	public void setDocument3(byte[] document3) {
		this.document3 = document3;
	}
	@Override
	public String toString() {
		return "EmployeeSummaryDTO [id=" + id + ", prefix=" + prefix + ", firstName=" + firstName + ", lastName="
				+ lastName + ", emailId=" + emailId + ", contactNumber1=" + contactNumber1 + ", gender=" + gender
				+ ", dateOfBirth=" + dateOfBirth + ", nationality=" + nationality + ", workEmail=" + workEmail
				+ ", joiningDate=" + joiningDate + ", houseNo=" + houseNo + ", city=" + city + ", state=" + state
				+ ", panNumber=" + panNumber + ", aadharNumber=" + aadharNumber + ", passportNumber=" + passportNumber
				+ ", fatherName=" + fatherName + ", motherName=" + motherName + ", maritalStatus=" + maritalStatus
				+ ", previousCompanyName=" + previousCompanyName + ", previousExperience=" + previousExperience
				+ ", department=" + department + ", designation=" + designation + ", previousCtc=" + previousCtc
				+ ", higherQualification=" + higherQualification + ", bankName=" + bankName + ", accountNo=" + accountNo
				+ ", ifscCode=" + ifscCode + ", bankBranch=" + bankBranch + ", basicEmployeeSalary="
				+ basicEmployeeSalary + ", profilePhoto=" + Arrays.toString(profilePhoto) + ", document1="
				+ Arrays.toString(document1) + ", document2=" + Arrays.toString(document2) + ", document3="
				+ Arrays.toString(document3) + ", getBasicEmployeeSalary()=" + getBasicEmployeeSalary() + ", getId()="
				+ getId() + ", getPrefix()=" + getPrefix() + ", getFirstName()=" + getFirstName() + ", getLastName()="
				+ getLastName() + ", getEmailId()=" + getEmailId() + ", getContactNumber1()=" + getContactNumber1()
				+ ", getGender()=" + getGender() + ", getDateOfBirth()=" + getDateOfBirth() + ", getNationality()="
				+ getNationality() + ", getWorkEmail()=" + getWorkEmail() + ", getJoiningDate()=" + getJoiningDate()
				+ ", getHouseNo()=" + getHouseNo() + ", getCity()=" + getCity() + ", getState()=" + getState()
				+ ", getPanNumber()=" + getPanNumber() + ", getAadharNumber()=" + getAadharNumber()
				+ ", getPassportNumber()=" + getPassportNumber() + ", getFatherName()=" + getFatherName()
				+ ", getMotherName()=" + getMotherName() + ", getMaritalStatus()=" + getMaritalStatus()
				+ ", getPreviousCompanyName()=" + getPreviousCompanyName() + ", getPreviousExperience()="
				+ getPreviousExperience() + ", getDepartment()=" + getDepartment() + ", getDesignation()="
				+ getDesignation() + ", getPreviousCtc()=" + getPreviousCtc() + ", getHigherQualification()="
				+ getHigherQualification() + ", getBankName()=" + getBankName() + ", getAccountNo()=" + getAccountNo()
				+ ", getIfscCode()=" + getIfscCode() + ", getBankBranch()=" + getBankBranch() + ", getProfilePhoto()="
				+ Arrays.toString(getProfilePhoto()) + ", getDocument1()=" + Arrays.toString(getDocument1())
				+ ", getDocument2()=" + Arrays.toString(getDocument2()) + ", getDocument3()="
				+ Arrays.toString(getDocument3()) + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
