package com.ssp.tsl.quotes.models;

public class MedicalCondition {
	private String dateLastOccurred;
	private String dateSustained;
    private Integer id;
    private String  infrimityCode;
    private String  infrimityCodeDescription;
    private boolean isDvlaNotified;
    private boolean isLicenceRestrictedByDvla;
    private Integer licenceRestrictedMonths;
	public String getDateLastOccurred() {
		return dateLastOccurred;
	}
	public void setDateLastOccurred(String dateLastOccurred) {
		this.dateLastOccurred = dateLastOccurred;
	}
	public String getDateSustained() {
		return dateSustained;
	}
	public void setDateSustained(String dateSustained) {
		this.dateSustained = dateSustained;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInfrimityCode() {
		return infrimityCode;
	}
	public void setInfrimityCode(String infrimityCode) {
		this.infrimityCode = infrimityCode;
	}
	public String getInfrimityCodeDescription() {
		return infrimityCodeDescription;
	}
	public void setInfrimityCodeDescription(String infrimityCodeDescription) {
		this.infrimityCodeDescription = infrimityCodeDescription;
	}
	public boolean isDvlaNotified() {
		return isDvlaNotified;
	}
	public void setDvlaNotified(boolean isDvlaNotified) {
		this.isDvlaNotified = isDvlaNotified;
	}
	public boolean isLicenceRestrictedByDvla() {
		return isLicenceRestrictedByDvla;
	}
	public void setLicenceRestrictedByDvla(boolean isLicenceRestrictedByDvla) {
		this.isLicenceRestrictedByDvla = isLicenceRestrictedByDvla;
	}
	public Integer getLicenceRestrictedMonths() {
		return licenceRestrictedMonths;
	}
	public void setLicenceRestrictedMonths(Integer licenceRestrictedMonths) {
		this.licenceRestrictedMonths = licenceRestrictedMonths;
	}
    
    

}
