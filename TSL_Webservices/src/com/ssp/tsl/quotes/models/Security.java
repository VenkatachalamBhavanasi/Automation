package com.ssp.tsl.quotes.models;

public class Security {

	private boolean alarmApproved;
	private boolean alarmInstalled;
	private boolean approvedLocksFitted;
	private boolean exteriorSecurityLighting;
	private boolean neighborhoodWatchScheme;
	private boolean safeInstalled;
	private boolean securityActivated;
	private boolean securityDiscountRequired;
	private Integer securityId;
	private boolean smokeDetectors;
	private String typeOfAlarm;
	private String typeOfAlarmAbiCode;
	public boolean isAlarmApproved() {
		return alarmApproved;
	}
	public void setAlarmApproved(boolean alarmApproved) {
		this.alarmApproved = alarmApproved;
	}
	public boolean isAlarmInstalled() {
		return alarmInstalled;
	}
	public void setAlarmInstalled(boolean alarmInstalled) {
		this.alarmInstalled = alarmInstalled;
	}
	public boolean isApprovedLocksFitted() {
		return approvedLocksFitted;
	}
	public void setApprovedLocksFitted(boolean approvedLocksFitted) {
		this.approvedLocksFitted = approvedLocksFitted;
	}
	public boolean isExteriorSecurityLighting() {
		return exteriorSecurityLighting;
	}
	public void setExteriorSecurityLighting(boolean exteriorSecurityLighting) {
		this.exteriorSecurityLighting = exteriorSecurityLighting;
	}
	public boolean isNeighborhoodWatchScheme() {
		return neighborhoodWatchScheme;
	}
	public void setNeighborhoodWatchScheme(boolean neighborhoodWatchScheme) {
		this.neighborhoodWatchScheme = neighborhoodWatchScheme;
	}
	public boolean isSafeInstalled() {
		return safeInstalled;
	}
	public void setSafeInstalled(boolean safeInstalled) {
		this.safeInstalled = safeInstalled;
	}
	public boolean isSecurityActivated() {
		return securityActivated;
	}
	public void setSecurityActivated(boolean securityActivated) {
		this.securityActivated = securityActivated;
	}
	public boolean isSecurityDiscountRequired() {
		return securityDiscountRequired;
	}
	public void setSecurityDiscountRequired(boolean securityDiscountRequired) {
		this.securityDiscountRequired = securityDiscountRequired;
	}
	public Integer getSecurityId() {
		return securityId;
	}
	public void setSecurityId(Integer securityId) {
		this.securityId = securityId;
	}
	public boolean isSmokeDetectors() {
		return smokeDetectors;
	}
	public void setSmokeDetectors(boolean smokeDetectors) {
		this.smokeDetectors = smokeDetectors;
	}
	public String getTypeOfAlarm() {
		return typeOfAlarm;
	}
	public void setTypeOfAlarm(String typeOfAlarm) {
		this.typeOfAlarm = typeOfAlarm;
	}
	public String getTypeOfAlarmAbiCode() {
		return typeOfAlarmAbiCode;
	}
	public void setTypeOfAlarmAbiCode(String typeOfAlarmAbiCode) {
		this.typeOfAlarmAbiCode = typeOfAlarmAbiCode;
	}
}
