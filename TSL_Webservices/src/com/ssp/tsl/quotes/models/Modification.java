package com.ssp.tsl.quotes.models;

public class Modification {
	private String abiCode;
	private String additionalInformation;
	private String description;
    private boolean dueToMedicalCondition;
    private Integer id;
	public String getAbiCode() {
		return abiCode;
	}
	public void setAbiCode(String abiCode) {
		this.abiCode = abiCode;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isDueToMedicalCondition() {
		return dueToMedicalCondition;
	}
	public void setDueToMedicalCondition(boolean dueToMedicalCondition) {
		this.dueToMedicalCondition = dueToMedicalCondition;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}
