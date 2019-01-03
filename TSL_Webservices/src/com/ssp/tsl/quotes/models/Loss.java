package com.ssp.tsl.quotes.models;

public class Loss {
	private boolean currentAddress;
	private String date;
	private String details;
	private Integer lossId;
	private String totalCost;
	private String type;
	private String typeAbiCode;
	private String underPolicy;
	private String underPolicyAbiCode;
	public boolean isCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(boolean currentAddress) {
		this.currentAddress = currentAddress;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Integer getLossId() {
		return lossId;
	}
	public void setLossId(Integer lossId) {
		this.lossId = lossId;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeAbiCode() {
		return typeAbiCode;
	}
	public void setTypeAbiCode(String typeAbiCode) {
		this.typeAbiCode = typeAbiCode;
	}
	public String getUnderPolicy() {
		return underPolicy;
	}
	public void setUnderPolicy(String underPolicy) {
		this.underPolicy = underPolicy;
	}
	public String getUnderPolicyAbiCode() {
		return underPolicyAbiCode;
	}
	public void setUnderPolicyAbiCode(String underPolicyAbiCode) {
		this.underPolicyAbiCode = underPolicyAbiCode;
	}
}
