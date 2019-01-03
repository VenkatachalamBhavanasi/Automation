package com.ssp.tsl.quotes.models;

public class Ncd {

	private String countryEarnedAbiCode;
	private String countryEarnedDescription;
	private Integer id;
	private String previousInsurerAbiCode;
	private String previousInsurerDescription;
	private String previousPolicyExpiryDate;
	private String previousPolicyNumber;
	private String previousPolicyPaymentFrequencyAbiCode;
	private String previousPolicyPaymentFrequencyDescription;
	private boolean protectionRequired;
	private Integer years;
	public String getCountryEarnedAbiCode() {
		return countryEarnedAbiCode;
	}
	public void setCountryEarnedAbiCode(String countryEarnedAbiCode) {
		this.countryEarnedAbiCode = countryEarnedAbiCode;
	}
	public String getCountryEarnedDescription() {
		return countryEarnedDescription;
	}
	public void setCountryEarnedDescription(String countryEarnedDescription) {
		this.countryEarnedDescription = countryEarnedDescription;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPreviousInsurerAbiCode() {
		return previousInsurerAbiCode;
	}
	public void setPreviousInsurerAbiCode(String previousInsurerAbiCode) {
		this.previousInsurerAbiCode = previousInsurerAbiCode;
	}
	public String getPreviousInsurerDescription() {
		return previousInsurerDescription;
	}
	public void setPreviousInsurerDescription(String previousInsurerDescription) {
		this.previousInsurerDescription = previousInsurerDescription;
	}
	public String getPreviousPolicyExpiryDate() {
		return previousPolicyExpiryDate;
	}
	public void setPreviousPolicyExpiryDate(String previousPolicyExpiryDate) {
		this.previousPolicyExpiryDate = previousPolicyExpiryDate;
	}
	public String getPreviousPolicyNumber() {
		return previousPolicyNumber;
	}
	public void setPreviousPolicyNumber(String previousPolicyNumber) {
		this.previousPolicyNumber = previousPolicyNumber;
	}
	public String getPreviousPolicyPaymentFrequencyAbiCode() {
		return previousPolicyPaymentFrequencyAbiCode;
	}
	public void setPreviousPolicyPaymentFrequencyAbiCode(String previousPolicyPaymentFrequencyAbiCode) {
		this.previousPolicyPaymentFrequencyAbiCode = previousPolicyPaymentFrequencyAbiCode;
	}
	public String getPreviousPolicyPaymentFrequencyDescription() {
		return previousPolicyPaymentFrequencyDescription;
	}
	public void setPreviousPolicyPaymentFrequencyDescription(String previousPolicyPaymentFrequencyDescription) {
		this.previousPolicyPaymentFrequencyDescription = previousPolicyPaymentFrequencyDescription;
	}
	public boolean isProtectionRequired() {
		return protectionRequired;
	}
	public void setProtectionRequired(boolean protectionRequired) {
		this.protectionRequired = protectionRequired;
	}
	public Integer getYears() {
		return years;
	}
	public void setYears(Integer years) {
		this.years = years;
	}
}
