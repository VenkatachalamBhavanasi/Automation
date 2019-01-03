package com.ssp.tsl.quotes.models;

public class Building {
	
	private boolean accidentalDamage;
    private Integer buildingsId;
    private Integer claimFreeYears;
    private PreviousInsurance previousInsurance;
	private Integer rebuildingCost;
	private Integer totalExcess;
	public boolean isAccidentalDamage() {
		return accidentalDamage;
	}
	public void setAccidentalDamage(boolean accidentalDamage) {
		this.accidentalDamage = accidentalDamage;
	}
	public Integer getBuildingsId() {
		return buildingsId;
	}
	public void setBuildingsId(Integer buildingsId) {
		this.buildingsId = buildingsId;
	}
	public Integer getClaimFreeYears() {
		return claimFreeYears;
	}
	public void setClaimFreeYears(Integer claimFreeYears) {
		this.claimFreeYears = claimFreeYears;
	}
	public PreviousInsurance getPreviousInsurance() {
		return previousInsurance;
	}
	public void setPreviousInsurance(PreviousInsurance previousInsurance) {
		this.previousInsurance = previousInsurance;
	}
	public Integer getRebuildingCost() {
		return rebuildingCost;
	}
	public void setRebuildingCost(Integer rebuildingCost) {
		this.rebuildingCost = rebuildingCost;
	}
	public Integer getTotalExcess() {
		return totalExcess;
	}
	public void setTotalExcess(Integer totalExcess) {
		this.totalExcess = totalExcess;
	}
}
