package com.example.EmpDTO;

import java.time.LocalDate;

public class UpdatingEmpInfo {


	    private String gender;
	    private String nationality;	  
	    private String houseNo;
	    private String city;
	    private String state;
	    
	    private String maritalStatus;

	    
		public String getMaritalStatus() {
			return maritalStatus;
		}
		public void setMaritalStatus(String maritalStatus) {
			this.maritalStatus = maritalStatus;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public String getNationality() {
			return nationality;
		}
		public void setNationality(String nationality) {
			this.nationality = nationality;
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
		@Override
		public String toString() {
			return "UpdatingEmpInfo [gender=" + gender + ", nationality=" + nationality + ", houseNo=" + houseNo
					+ ", city=" + city + ", state=" + state + "]";
		}
	    
	    
	 
}
