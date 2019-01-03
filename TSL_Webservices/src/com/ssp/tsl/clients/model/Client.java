package com.ssp.tsl.clients.model;

import java.util.List;

public class Client {

	private String titleCode;
	private String forename;
	private String initials;
	private String surname;
	private String genderCode;
	private String dateOfBirth;
	private String maritalStatusCode;
	private String correspondenceType;
	private MarketingPreferences marketingPreferences;
	private Integer branchID;
	private Integer businessSourceId;
	private Integer agentId;
	private Integer consultantId;
	private List<Addresses> addresses;
	private List<EmailAddresses> emailAddresses;
	private List<TelephoneNumbers> telephoneNumbers;
	public String getTitleCode() {
		return titleCode;
	}
	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}
	public String getForename() {
		return forename;
	}
	public void setForename(String forename) {
		this.forename = forename;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getGenderCode() {
		return genderCode;
	}
	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getMaritalStatusCode() {
		return maritalStatusCode;
	}
	public void setMaritalStatusCode(String maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}
	public String getCorrespondenceType() {
		return correspondenceType;
	}
	public void setCorrespondenceType(String correspondenceType) {
		this.correspondenceType = correspondenceType;
	}
	public MarketingPreferences getMarketingPreferences() {
		return marketingPreferences;
	}
	public void setMarketingPreferences(MarketingPreferences marketingPreferences) {
		this.marketingPreferences = marketingPreferences;
	}
	public Integer getBranchID() {
		return branchID;
	}
	public void setBranchID(Integer branchID) {
		this.branchID = branchID;
	}
	public Integer getBusinessSourceId() {
		return businessSourceId;
	}
	public void setBusinessSourceId(Integer businessSourceId) {
		this.businessSourceId = businessSourceId;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public Integer getConsultantId() {
		return consultantId;
	}
	public void setConsultantId(Integer consultantId) {
		this.consultantId = consultantId;
	}
	public List<Addresses> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Addresses> addresses) {
		this.addresses = addresses;
	}
	public List<EmailAddresses> getEmailAddresses() {
		return emailAddresses;
	}
	public void setEmailAddresses(List<EmailAddresses> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}
	public List<TelephoneNumbers> getTelephoneNumbers() {
		return telephoneNumbers;
	}
	public void setTelephoneNumbers(List<TelephoneNumbers> telephoneNumbers) {
		this.telephoneNumbers = telephoneNumbers;
	}

	
}
