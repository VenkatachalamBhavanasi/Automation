package com.ssp.tsl.quotes.models;

public class PreviousInsurance {

	private String expiryDate;
	private String paymentFrequency;
	private String paymentFrequencyAbiCode;
	private String policyNumber;
	private Integer previousInsuranceId;
	private String previousInsurer;
	private String previousInsurerAbiCode;
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getPaymentFrequency() {
		return paymentFrequency;
	}
	public void setPaymentFrequency(String paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}
	public String getPaymentFrequencyAbiCode() {
		return paymentFrequencyAbiCode;
	}
	public void setPaymentFrequencyAbiCode(String paymentFrequencyAbiCode) {
		this.paymentFrequencyAbiCode = paymentFrequencyAbiCode;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public Integer getPreviousInsuranceId() {
		return previousInsuranceId;
	}
	public void setPreviousInsuranceId(Integer previousInsuranceId) {
		this.previousInsuranceId = previousInsuranceId;
	}
	public String getPreviousInsurer() {
		return previousInsurer;
	}
	public void setPreviousInsurer(String previousInsurer) {
		this.previousInsurer = previousInsurer;
	}
	public String getPreviousInsurerAbiCode() {
		return previousInsurerAbiCode;
	}
	public void setPreviousInsurerAbiCode(String previousInsurerAbiCode) {
		this.previousInsurerAbiCode = previousInsurerAbiCode;
	}
}
