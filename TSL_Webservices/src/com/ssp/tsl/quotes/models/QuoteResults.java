package com.ssp.tsl.quotes.models;

import java.util.List;

public class QuoteResults {
	
    private List<AcceptedQuoteResult> acceptedQuoteResultList;          
    private List<DeclineQuoteResult> declineQuoteResultList;
    private List<ReferredQuoteResult> referredQuoteResultList;
	public List<AcceptedQuoteResult> getAcceptedQuoteResultList() {
		return acceptedQuoteResultList;
	}
	public void setAcceptedQuoteResultList(List<AcceptedQuoteResult> acceptedQuoteResultList) {
		this.acceptedQuoteResultList = acceptedQuoteResultList;
	}
	public List<DeclineQuoteResult> getDeclineQuoteResultList() {
		return declineQuoteResultList;
	}
	public void setDeclineQuoteResultList(List<DeclineQuoteResult> declineQuoteResultList) {
		this.declineQuoteResultList = declineQuoteResultList;
	}
	public List<ReferredQuoteResult> getReferredQuoteResultList() {
		return referredQuoteResultList;
	}
	public void setReferredQuoteResultList(List<ReferredQuoteResult> referredQuoteResultList) {
		this.referredQuoteResultList = referredQuoteResultList;
	}                           
                                 
}
