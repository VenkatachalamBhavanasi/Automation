package com.ssp.tsl.quotes.models;

import java.util.List;

public class PersonalPossessions {
	private Integer creditCard;
	private Integer frozenFood;
	private Integer legalExpenses;
	private List<PedalCycle> pedalCycles;
	private Integer personalMoney;
	private Integer personalPossessionsId;
	private List<SpecifiedItem> specifiedItemList;
	public Integer getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(Integer creditCard) {
		this.creditCard = creditCard;
	}
	public Integer getFrozenFood() {
		return frozenFood;
	}
	public void setFrozenFood(Integer frozenFood) {
		this.frozenFood = frozenFood;
	}
	public Integer getLegalExpenses() {
		return legalExpenses;
	}
	public void setLegalExpenses(Integer legalExpenses) {
		this.legalExpenses = legalExpenses;
	}
	public List<PedalCycle> getPedalCycles() {
		return pedalCycles;
	}
	public void setPedalCycles(List<PedalCycle> pedalCycles) {
		this.pedalCycles = pedalCycles;
	}
	public Integer getPersonalMoney() {
		return personalMoney;
	}
	public void setPersonalMoney(Integer personalMoney) {
		this.personalMoney = personalMoney;
	}
	public Integer getPersonalPossessionsId() {
		return personalPossessionsId;
	}
	public void setPersonalPossessionsId(Integer personalPossessionsId) {
		this.personalPossessionsId = personalPossessionsId;
	}
	public List<SpecifiedItem> getSpecifiedItemList() {
		return specifiedItemList;
	}
	public void setSpecifiedItemList(List<SpecifiedItem> specifiedItemList) {
		this.specifiedItemList = specifiedItemList;
	}
}
