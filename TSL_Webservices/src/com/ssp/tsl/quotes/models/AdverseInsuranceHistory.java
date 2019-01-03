package com.ssp.tsl.quotes.models;

public class AdverseInsuranceHistory {
	private Integer adverseInsuranceId;
	private boolean extraPrecautions;
	private boolean increasedPremium;
	private boolean refused;
	private boolean specialConditions;
	private boolean withdrawn;
	public Integer getAdverseInsuranceId() {
		return adverseInsuranceId;
	}
	public void setAdverseInsuranceId(Integer adverseInsuranceId) {
		this.adverseInsuranceId = adverseInsuranceId;
	}
	public boolean isExtraPrecautions() {
		return extraPrecautions;
	}
	public void setExtraPrecautions(boolean extraPrecautions) {
		this.extraPrecautions = extraPrecautions;
	}
	public boolean isIncreasedPremium() {
		return increasedPremium;
	}
	public void setIncreasedPremium(boolean increasedPremium) {
		this.increasedPremium = increasedPremium;
	}
	public boolean isRefused() {
		return refused;
	}
	public void setRefused(boolean refused) {
		this.refused = refused;
	}
	public boolean isSpecialConditions() {
		return specialConditions;
	}
	public void setSpecialConditions(boolean specialConditions) {
		this.specialConditions = specialConditions;
	}
	public boolean isWithdrawn() {
		return withdrawn;
	}
	public void setWithdrawn(boolean withdrawn) {
		this.withdrawn = withdrawn;
	}
}
