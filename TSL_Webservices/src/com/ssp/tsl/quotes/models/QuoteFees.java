package com.ssp.tsl.quotes.models;

public class QuoteFees {
	
	private Integer feeExcludingTax;
	private Integer feeIncludingTax;
	private Integer feePercentage;
    private String feeType;
    private Integer id;
	public Integer getFeeExcludingTax() {
		return feeExcludingTax;
	}
	public void setFeeExcludingTax(Integer feeExcludingTax) {
		this.feeExcludingTax = feeExcludingTax;
	}
	public Integer getFeeIncludingTax() {
		return feeIncludingTax;
	}
	public void setFeeIncludingTax(Integer feeIncludingTax) {
		this.feeIncludingTax = feeIncludingTax;
	}
	public Integer getFeePercentage() {
		return feePercentage;
	}
	public void setFeePercentage(Integer feePercentage) {
		this.feePercentage = feePercentage;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
