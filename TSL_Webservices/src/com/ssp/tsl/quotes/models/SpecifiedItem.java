package com.ssp.tsl.quotes.models;

public class SpecifiedItem {
	private String description;
	private boolean isSpecified;
    private String riskCategory;
    private String riskCategoryAbiCode;
    private Integer specifiedItemId;
    private Integer value;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isSpecified() {
		return isSpecified;
	}
	public void setSpecified(boolean isSpecified) {
		this.isSpecified = isSpecified;
	}
	public String getRiskCategory() {
		return riskCategory;
	}
	public void setRiskCategory(String riskCategory) {
		this.riskCategory = riskCategory;
	}
	public String getRiskCategoryAbiCode() {
		return riskCategoryAbiCode;
	}
	public void setRiskCategoryAbiCode(String riskCategoryAbiCode) {
		this.riskCategoryAbiCode = riskCategoryAbiCode;
	}
	public Integer getSpecifiedItemId() {
		return specifiedItemId;
	}
	public void setSpecifiedItemId(Integer specifiedItemId) {
		this.specifiedItemId = specifiedItemId;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
}
