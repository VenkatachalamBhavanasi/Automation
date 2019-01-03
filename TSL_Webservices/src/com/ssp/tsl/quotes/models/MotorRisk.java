package com.ssp.tsl.quotes.models;

import java.util.List;

public class MotorRisk {
	private CarDetails carDetails;
	private String coverType;
	private List<Drivers> drivers;
	private String effectiveDate;
	private Integer maximumVolutaryExcess;
	private List<OtherInsurances> otherInsurances;
	private String permittedDrivers;
	private String permittedDriversDescription;
	private PolicyAddress policyAddress;
	public CarDetails getCarDetails() {
		return carDetails;
	}
	public void setCarDetails(CarDetails carDetails) {
		this.carDetails = carDetails;
	}
	public String getCoverType() {
		return coverType;
	}
	public void setCoverType(String coverType) {
		this.coverType = coverType;
	}
	public List<Drivers> getDrivers() {
		return drivers;
	}
	public void setDrivers(List<Drivers> drivers) {
		this.drivers = drivers;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Integer getMaximumVolutaryExcess() {
		return maximumVolutaryExcess;
	}
	public void setMaximumVolutaryExcess(Integer maximumVolutaryExcess) {
		this.maximumVolutaryExcess = maximumVolutaryExcess;
	}

	public String getPermittedDrivers() {
		return permittedDrivers;
	}
	public void setPermittedDrivers(String permittedDrivers) {
		this.permittedDrivers = permittedDrivers;
	}
	public String getPermittedDriversDescription() {
		return permittedDriversDescription;
	}
	public void setPermittedDriversDescription(String permittedDriversDescription) {
		this.permittedDriversDescription = permittedDriversDescription;
	}
	public PolicyAddress getPolicyAddress() {
		return policyAddress;
	}
	public void setPolicyAddress(PolicyAddress policyAddress) {
		this.policyAddress = policyAddress;
	}
	public List<OtherInsurances> getOtherInsurances() {
		return otherInsurances;
	}
	public void setOtherInsurances(List<OtherInsurances> otherInsurances) {
		this.otherInsurances = otherInsurances;
	}
}
