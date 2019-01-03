package com.ssp.tsl.quotes.models;

public class OtherInsurances {
	
	private String expiryDate;
	private Integer id;
    private String insurerAbiCode;
    private String insurerDescription;
    private Integer numberOfYearsClaimFree;
    private String typeAbiCode;
    private String typeDescription;
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInsurerAbiCode() {
		return insurerAbiCode;
	}
	public void setInsurerAbiCode(String insurerAbiCode) {
		this.insurerAbiCode = insurerAbiCode;
	}
	public String getInsurerDescription() {
		return insurerDescription;
	}
	public void setInsurerDescription(String insurerDescription) {
		this.insurerDescription = insurerDescription;
	}
	public Integer getNumberOfYearsClaimFree() {
		return numberOfYearsClaimFree;
	}
	public void setNumberOfYearsClaimFree(Integer numberOfYearsClaimFree) {
		this.numberOfYearsClaimFree = numberOfYearsClaimFree;
	}
	public String getTypeAbiCode() {
		return typeAbiCode;
	}
	public void setTypeAbiCode(String typeAbiCode) {
		this.typeAbiCode = typeAbiCode;
	}
	public String getTypeDescription() {
		return typeDescription;
	}
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}
    

}
