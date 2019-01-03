package com.purebroker.pojo;

public class QuoteRequest {

	private Integer businessUnitId;
	private Integer customerId;
	private Integer schemeId;
	private String transactionType;
	private String lineOfBusiness;
	private String risk;
	private String riskFormat;
	private String status;
	public Integer getBusinessUnitId() {
		return businessUnitId;
	}
	public void setBusinessUnitId(Integer businessUnitId) {
		this.businessUnitId = businessUnitId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getLineOfBusiness() {
		return lineOfBusiness;
	}
	public void setLineOfBusiness(String lineOfBusiness) {
		this.lineOfBusiness = lineOfBusiness;
	}
	public String getRisk() {
		return risk;
	}
	public void setRisk(String risk) {
		this.risk = risk;
	}
	public String getRiskFormat() {
		return riskFormat;
	}
	public void setRiskFormat(String riskFormat) {
		this.riskFormat = riskFormat;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
