package com.ssp.tsl.quotes.models;

import java.util.List;

public class DeclineQuoteResult {
	private List<Note> noteList;
	private String insurerName;
	private Integer  qhQuoteOutId;
	private Integer  quoteBinderId;
    private String  reason;
    private String  schemeName;

	public List<Note> getNoteList() {
		return noteList;
	}

	public void setNoteList(List<Note> noteList) {
		this.noteList = noteList;
	}

	public String getInsurerName() {
		return insurerName;
	}

	public void setInsurerName(String insurerName) {
		this.insurerName = insurerName;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
}
