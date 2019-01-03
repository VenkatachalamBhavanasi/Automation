package com.ssp.tsl.quotes.models;

public class Incident {

	private Claim claim;
	private Conviction conviction;
	private Integer id;
	private String incidentDate;
	public Claim getClaim() {
		return claim;
	}
	public void setClaim(Claim claim) {
		this.claim = claim;
	}
	public Conviction getConviction() {
		return conviction;
	}
	public void setConviction(Conviction conviction) {
		this.conviction = conviction;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIncidentDate() {
		return incidentDate;
	}
	public void setIncidentDate(String incidentDate) {
		this.incidentDate = incidentDate;
	}
	

}
