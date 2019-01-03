package com.ssp.tsl.quotes.models;

public class Claim {
	private boolean atFault;
	private boolean claimOnThisRisk;
	private boolean claimSettled;
	private Integer costOwnDamage ;
	private Integer costPersonalInjury ;
	private Integer costThirdParty ;
	private String date ;
	private String description;
	private String freeTextDescription;
	private Integer id ;
	private String insurer ;
	private String insurerDescription ;
	private boolean ncdLost;
	private boolean ncdProtected;
	private String policyNumber ;
	private String type;
	public boolean isAtFault() {
		return atFault;
	}
	public void setAtFault(boolean atFault) {
		this.atFault = atFault;
	}
	public boolean isClaimOnThisRisk() {
		return claimOnThisRisk;
	}
	public void setClaimOnThisRisk(boolean claimOnThisRisk) {
		this.claimOnThisRisk = claimOnThisRisk;
	}
	public boolean isClaimSettled() {
		return claimSettled;
	}
	public void setClaimSettled(boolean claimSettled) {
		this.claimSettled = claimSettled;
	}
	public Integer getCostOwnDamage() {
		return costOwnDamage;
	}
	public void setCostOwnDamage(Integer costOwnDamage) {
		this.costOwnDamage = costOwnDamage;
	}
	public Integer getCostPersonalInjury() {
		return costPersonalInjury;
	}
	public void setCostPersonalInjury(Integer costPersonalInjury) {
		this.costPersonalInjury = costPersonalInjury;
	}
	public Integer getCostThirdParty() {
		return costThirdParty;
	}
	public void setCostThirdParty(Integer costThirdParty) {
		this.costThirdParty = costThirdParty;
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
	public String getInsurer() {
		return insurer;
	}
	public void setInsurer(String insurer) {
		this.insurer = insurer;
	}
	public String getInsurerDescription() {
		return insurerDescription;
	}
	public void setInsurerDescription(String insurerDescription) {
		this.insurerDescription = insurerDescription;
	}
	public boolean isNcdLost() {
		return ncdLost;
	}
	public void setNcdLost(boolean ncdLost) {
		this.ncdLost = ncdLost;
	}
	public boolean isNcdProtected() {
		return ncdProtected;
	}
	public void setNcdProtected(boolean ncdProtected) {
		this.ncdProtected = ncdProtected;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
