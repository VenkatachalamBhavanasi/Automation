package com.ssp.tsl.quotes.models;

import java.util.List;

public class Content {
	
	private Integer claimFreeYears;
	private Integer contentsId;
	private Integer contentsValue;
	private String cover;
	private String coverAbiCode;
	private boolean fullReplacement;
    private Integer mostExpensiveItem;
    private PreviousInsurance previousInsurance;
    private List<SpecifiedItem> specifiedItemList;
    private Integer totalExcess;
    private Integer valuables;
	public Integer getClaimFreeYears() {
		return claimFreeYears;
	}
	public void setClaimFreeYears(Integer claimFreeYears) {
		this.claimFreeYears = claimFreeYears;
	}
	public Integer getContentsId() {
		return contentsId;
	}
	public void setContentsId(Integer contentsId) {
		this.contentsId = contentsId;
	}
	public Integer getContentsValue() {
		return contentsValue;
	}
	public void setContentsValue(Integer contentsValue) {
		this.contentsValue = contentsValue;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getCoverAbiCode() {
		return coverAbiCode;
	}
	public void setCoverAbiCode(String coverAbiCode) {
		this.coverAbiCode = coverAbiCode;
	}
	public boolean isFullReplacement() {
		return fullReplacement;
	}
	public void setFullReplacement(boolean fullReplacement) {
		this.fullReplacement = fullReplacement;
	}
	public Integer getMostExpensiveItem() {
		return mostExpensiveItem;
	}
	public void setMostExpensiveItem(Integer mostExpensiveItem) {
		this.mostExpensiveItem = mostExpensiveItem;
	}
	public PreviousInsurance getPreviousInsurance() {
		return previousInsurance;
	}
	public void setPreviousInsurance(PreviousInsurance previousInsurance) {
		this.previousInsurance = previousInsurance;
	}
	public List<SpecifiedItem> getSpecifiedItemList() {
		return specifiedItemList;
	}
	public void setSpecifiedItemList(List<SpecifiedItem> specifiedItemList) {
		this.specifiedItemList = specifiedItemList;
	}
	public Integer getTotalExcess() {
		return totalExcess;
	}
	public void setTotalExcess(Integer totalExcess) {
		this.totalExcess = totalExcess;
	}
	public Integer getValuables() {
		return valuables;
	}
	public void setValuables(Integer valuables) {
		this.valuables = valuables;
	}
}
