package com.ssp.tsl.quotes.models;

import java.util.List;

public class Risk {

	private Building buildings;
	private Content contents;
	private String coverStartDate;
	private Integer householdRiskId;
	private Integer insuranceFolderId;
	private JointPolicyHolder jointPolicyHolder;
	private List<Loss> lossList;
	private PersonalPossessions personalPossessions;
	private PolicyHolder policyHolder;
	private Premises premises;
	private Security security;
	public Building getBuildings() {
		return buildings;
	}
	public void setBuildings(Building buildings) {
		this.buildings = buildings;
	}
	public Content getContents() {
		return contents;
	}
	public void setContents(Content contents) {
		this.contents = contents;
	}
	public String getCoverStartDate() {
		return coverStartDate;
	}
	public void setCoverStartDate(String coverStartDate) {
		this.coverStartDate = coverStartDate;
	}
	public Integer getHouseholdRiskId() {
		return householdRiskId;
	}
	public void setHouseholdRiskId(Integer householdRiskId) {
		this.householdRiskId = householdRiskId;
	}
	public Integer getInsuranceFolderId() {
		return insuranceFolderId;
	}
	public void setInsuranceFolderId(Integer insuranceFolderId) {
		this.insuranceFolderId = insuranceFolderId;
	}
	public JointPolicyHolder getJointPolicyHolder() {
		return jointPolicyHolder;
	}
	public void setJointPolicyHolder(JointPolicyHolder jointPolicyHolder) {
		this.jointPolicyHolder = jointPolicyHolder;
	}
	public PersonalPossessions getPersonalPossessions() {
		return personalPossessions;
	}
	public void setPersonalPossessions(PersonalPossessions personalPossessions) {
		this.personalPossessions = personalPossessions;
	}
	public PolicyHolder getPolicyHolder() {
		return policyHolder;
	}
	public void setPolicyHolder(PolicyHolder policyHolder) {
		this.policyHolder = policyHolder;
	}
	public Premises getPremises() {
		return premises;
	}
	public void setPremises(Premises premises) {
		this.premises = premises;
	}
	public Security getSecurity() {
		return security;
	}
	public void setSecurity(Security security) {
		this.security = security;
	}
	public List<Loss> getLossList() {
		return lossList;
	}
	public void setLossList(List<Loss> lossList) {
		this.lossList = lossList;
	}
}
