package com.example.AdmineEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="leave_policy")
public class leavepolicy {

	@Id
	private int id;
	private Long annualLeaves;
	private Long casualLeaves;
	private Long sickLeaves;
	private Long maternityLeaves;
	private Long carryForwarelimit;
	public Long getAnnualLeaves() {
		return annualLeaves;
	}
	public void setAnnualLeaves(Long annualLeaves) {
		this.annualLeaves = annualLeaves;
	}
	public Long getCasualLeaves() {
		return casualLeaves;
	}
	public void setCasualLeaves(Long casualLeaves) {
		this.casualLeaves = casualLeaves;
	}
	public Long getSickLeaves() {
		return sickLeaves;
	}
	public void setSickLeaves(Long sickLeaves) {
		this.sickLeaves = sickLeaves;
	}
	public Long getMaternityLeaves() {
		return maternityLeaves;
	}
	public void setMaternityLeaves(Long maternityLeaves) {
		this.maternityLeaves = maternityLeaves;
	}
	public Long getCarryForwarelimit() {
		return carryForwarelimit;
	}
	public void setCarryForwarelimit(Long carryForwarelimit) {
		this.carryForwarelimit = carryForwarelimit;
	}
	
	
	
}
