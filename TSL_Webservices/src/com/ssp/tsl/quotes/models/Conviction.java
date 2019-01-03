package com.ssp.tsl.quotes.models;

public class Conviction {
	 private String  abiCode ;
	  private Integer alcoholReading ;
	  private String alcoholReadingType ;
	  private String alcoholReadingTypeDescription ;
	  private String date ;
	  private String description ;
	  private Integer disqualificationPeriod ;
	  private Integer fineAmount ;
	  private String freeTextDescription ;
	  private Integer id ;
	  private Integer penaltyPoints ;
	  private boolean prosecutionPending;
	  private boolean suspension ;
	public String getAbiCode() {
		return abiCode;
	}
	public void setAbiCode(String abiCode) {
		this.abiCode = abiCode;
	}
	public Integer getAlcoholReading() {
		return alcoholReading;
	}
	public void setAlcoholReading(Integer alcoholReading) {
		this.alcoholReading = alcoholReading;
	}
	public String getAlcoholReadingType() {
		return alcoholReadingType;
	}
	public void setAlcoholReadingType(String alcoholReadingType) {
		this.alcoholReadingType = alcoholReadingType;
	}
	public String getAlcoholReadingTypeDescription() {
		return alcoholReadingTypeDescription;
	}
	public void setAlcoholReadingTypeDescription(String alcoholReadingTypeDescription) {
		this.alcoholReadingTypeDescription = alcoholReadingTypeDescription;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getDisqualificationPeriod() {
		return disqualificationPeriod;
	}
	public void setDisqualificationPeriod(Integer disqualificationPeriod) {
		this.disqualificationPeriod = disqualificationPeriod;
	}
	public Integer getFineAmount() {
		return fineAmount;
	}
	public void setFineAmount(Integer fineAmount) {
		this.fineAmount = fineAmount;
	}
	public String getFreeTextDescription() {
		return freeTextDescription;
	}
	public void setFreeTextDescription(String freeTextDescription) {
		this.freeTextDescription = freeTextDescription;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPenaltyPoints() {
		return penaltyPoints;
	}
	public void setPenaltyPoints(Integer penaltyPoints) {
		this.penaltyPoints = penaltyPoints;
	}
	public boolean isProsecutionPending() {
		return prosecutionPending;
	}
	public void setProsecutionPending(boolean prosecutionPending) {
		this.prosecutionPending = prosecutionPending;
	}
	public boolean isSuspension() {
		return suspension;
	}
	public void setSuspension(boolean suspension) {
		this.suspension = suspension;
	}

}
