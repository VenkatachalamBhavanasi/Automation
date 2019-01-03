package com.ssp.tsl.quotes.models;

public class Occupation {
	private String abiCode;
	private String business;
	private String businessDescription;
	private String description;
	private String employmentStatus;
	private String employmentStatusDescription;
    private Integer id;
    private boolean isFullTimeEmployment;
    private boolean mainOccupation;
	public String getAbiCode() {
		return abiCode;
	}
	public void setAbiCode(String abiCode) {
		this.abiCode = abiCode;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getBusinessDescription() {
		return businessDescription;
	}
	public void setBusinessDescription(String businessDescription) {
		this.businessDescription = businessDescription;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEmploymentStatus() {
		return employmentStatus;
	}
	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}
	public String getEmploymentStatusDescription() {
		return employmentStatusDescription;
	}
	public void setEmploymentStatusDescription(String employmentStatusDescription) {
		this.employmentStatusDescription = employmentStatusDescription;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public boolean isFullTimeEmployment() {
		return isFullTimeEmployment;
	}
	public void setFullTimeEmployment(boolean isFullTimeEmployment) {
		this.isFullTimeEmployment = isFullTimeEmployment;
	}
	public boolean isMainOccupation() {
		return mainOccupation;
	}
	public void setMainOccupation(boolean mainOccupation) {
		this.mainOccupation = mainOccupation;
	}
    

}
