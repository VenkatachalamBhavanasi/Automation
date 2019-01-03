package com.ssp.tsl.quotes.models;

import java.util.List;

public class AcceptedQuoteResult {
	private List<Excess> excessList;
	private List<Note> noteList;
	private String policyWordingURL;
	private Integer premium;
	private Integer quoteResultId;
	private Integer brokerAddOnAmount;
	private Integer brokerFeeAmount;
	private String  buildingRatingArea;
	private Integer buildingsPremium;
	private Integer buildingsSI;
	private Integer commission;
	private Integer contentRatingArea;
	private Integer contentsPremium;
	private Integer contentsSI;
	private List<Endorsement> endorsementList;
	public Integer getBrokerAddOnAmount() {
		return brokerAddOnAmount;
	}
	public void setBrokerAddOnAmount(Integer brokerAddOnAmount) {
		this.brokerAddOnAmount = brokerAddOnAmount;
	}
	public Integer getBrokerFeeAmount() {
		return brokerFeeAmount;
	}
	public void setBrokerFeeAmount(Integer brokerFeeAmount) {
		this.brokerFeeAmount = brokerFeeAmount;
	}
	public String getBuildingRatingArea() {
		return buildingRatingArea;
	}
	public void setBuildingRatingArea(String buildingRatingArea) {
		this.buildingRatingArea = buildingRatingArea;
	}
	public Integer getBuildingsPremium() {
		return buildingsPremium;
	}
	public void setBuildingsPremium(Integer buildingsPremium) {
		this.buildingsPremium = buildingsPremium;
	}
	public Integer getBuildingsSI() {
		return buildingsSI;
	}
	public void setBuildingsSI(Integer buildingsSI) {
		this.buildingsSI = buildingsSI;
	}
	public Integer getCommission() {
		return commission;
	}
	public void setCommission(Integer commission) {
		this.commission = commission;
	}
	public Integer getContentRatingArea() {
		return contentRatingArea;
	}
	public void setContentRatingArea(Integer contentRatingArea) {
		this.contentRatingArea = contentRatingArea;
	}
	public Integer getContentsPremium() {
		return contentsPremium;
	}
	public void setContentsPremium(Integer contentsPremium) {
		this.contentsPremium = contentsPremium;
	}
	public Integer getContentsSI() {
		return contentsSI;
	}
	public void setContentsSI(Integer contentsSI) {
		this.contentsSI = contentsSI;
	}
	public List<Endorsement> getEndorsementList() {
		return endorsementList;
	}
	public void setEndorsementList(List<Endorsement> endorsementList) {
		this.endorsementList = endorsementList;
	}
	public List<FeaturesAndBenefits> getFeaturesAndBenefitsList() {
		return featuresAndBenefitsList;
	}
	public void setFeaturesAndBenefitsList(List<FeaturesAndBenefits> featuresAndBenefitsList) {
		this.featuresAndBenefitsList = featuresAndBenefitsList;
	}
	public String getInsurerName() {
		return insurerName;
	}
	public void setInsurerName(String insurerName) {
		this.insurerName = insurerName;
	}
	public Integer getInsurerPremium() {
		return insurerPremium;
	}
	public void setInsurerPremium(Integer insurerPremium) {
		this.insurerPremium = insurerPremium;
	}
	public List<PremiumBreakdown> getPremiumBreakdownList() {
		return premiumBreakdownList;
	}
	public void setPremiumBreakdownList(List<PremiumBreakdown> premiumBreakdownList) {
		this.premiumBreakdownList = premiumBreakdownList;
	}
	public Integer getQhQuoteOutId() {
		return qhQuoteOutId;
	}
	public void setQhQuoteOutId(Integer qhQuoteOutId) {
		this.qhQuoteOutId = qhQuoteOutId;
	}
	public Integer getQuoteBinderId() {
		return quoteBinderId;
	}
	public void setQuoteBinderId(Integer quoteBinderId) {
		this.quoteBinderId = quoteBinderId;
	}
	public String getQuoteRecommendations() {
		return quoteRecommendations;
	}
	public void setQuoteRecommendations(String quoteRecommendations) {
		this.quoteRecommendations = quoteRecommendations;
	}
	public String getQuoteStatus() {
		return quoteStatus;
	}
	public void setQuoteStatus(String quoteStatus) {
		this.quoteStatus = quoteStatus;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public List<Section> getSectionList() {
		return sectionList;
	}
	public void setSectionList(List<Section> sectionList) {
		this.sectionList = sectionList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private List<FeaturesAndBenefits> featuresAndBenefitsList;
	private String insurerName;
	private Integer insurerPremium;
	private List<PremiumBreakdown> premiumBreakdownList;  
	private Integer qhQuoteOutId;
	private Integer quoteBinderId;
	private String quoteRecommendations;
	private String quoteStatus;
	private String schemeName;
	private List<Section> sectionList;
	private String  status;
	public List<Excess> getExcessList() {
		return excessList;
	}
	public void setExcessList(List<Excess> excessList) {
		this.excessList = excessList;
	}
	public List<Note> getNoteList() {
		return noteList;
	}
	public void setNoteList(List<Note> noteList) {
		this.noteList = noteList;
	}
	public String getPolicyWordingURL() {
		return policyWordingURL;
	}
	public void setPolicyWordingURL(String policyWordingURL) {
		this.policyWordingURL = policyWordingURL;
	}
	public Integer getPremium() {
		return premium;
	}
	public void setPremium(Integer premium) {
		this.premium = premium;
	}
	public Integer getQuoteResultId() {
		return quoteResultId;
	}
	public void setQuoteResultId(Integer quoteResultId) {
		this.quoteResultId = quoteResultId;
	}
}
