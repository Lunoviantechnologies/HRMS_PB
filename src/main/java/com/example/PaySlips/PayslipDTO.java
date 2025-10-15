package com.example.PaySlips;

public class PayslipDTO {

	private String employeeName;
    private String employeeEmail;
    private Long employeeId;
    private int presentDays;
    private int workingDays;
    private long monthlySalary;
    private long basicSalary;
    private long finalSalary;
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public int getPresentDays() {
		return presentDays;
	}
	public void setPresentDays(int presentDays) {
		this.presentDays = presentDays;
	}
	public int getWorkingDays() {
		return workingDays;
	}
	public void setWorkingDays(int workingDays) {
		this.workingDays = workingDays;
	}
	
	public long getMonthlySalary() {
		return monthlySalary;
	}
	public void setMonthlySalary(long monthlySalary) {
		this.monthlySalary = monthlySalary;
	}
	public long getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(long basicSalary) {
		this.basicSalary = basicSalary;
	}
	public long getFinalSalary() {
		return finalSalary;
	}
	public void setFinalSalary(long finalSalary) {
		this.finalSalary = finalSalary;
	}
    
    

}
