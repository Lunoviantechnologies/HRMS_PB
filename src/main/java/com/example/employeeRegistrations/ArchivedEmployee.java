package com.example.employeeRegistrations;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "hrms_archived_employees")
public class ArchivedEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long archiveId;

    private Long originalEmployeeId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String contactNumber1;
    private String department;
    private String designation;
    private LocalDate joiningDate;
    private LocalDate dateOfBirth;
    private LocalDate deletedAt;

    @Lob
    @Column(name = "profile_photo")
    private byte[] profilePhoto;   
   
    public Long getArchiveId() { return archiveId; }
    public void setArchiveId(Long archiveId) { this.archiveId = archiveId; }
    public Long getOriginalEmployeeId() { return originalEmployeeId; }
    public void setOriginalEmployeeId(Long originalEmployeeId) { this.originalEmployeeId = originalEmployeeId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }
    public String getContactNumber1() { return contactNumber1; }
    public void setContactNumber1(String contactNumber1) { this.contactNumber1 = contactNumber1; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public LocalDate getJoiningDate() { return joiningDate; }
    public void setJoiningDate(LocalDate joiningDate) { this.joiningDate = joiningDate; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public LocalDate getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDate deletedAt) { this.deletedAt = deletedAt; }
    public byte[] getProfilePhoto() { return profilePhoto; }
    public void setProfilePhoto(byte[] profilePhoto) { this.profilePhoto = profilePhoto; }
}
