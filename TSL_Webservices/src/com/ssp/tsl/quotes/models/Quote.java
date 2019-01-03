package com.ssp.tsl.quotes.models;

import java.util.List;

public class Quote {
	private String accountHandler;
    private List<Addons> addons;
    private String branch;
    private String businessSource;
    private Integer clientId;
    private String clientRequirements;
    private String dateCreated;
    private String description;
    private Integer ediMessageSent;
    private String endDate;
    private String externalQuoteReference;
    private Integer financeBalance;
    private Integer id;
    private Integer insuranceFileId;
    private String insuredName;
    private String insurer;
    private String interestedParty;
    private MototrQuoteResults motorQuoteResults;
    private MotorRisk motorRisk;
    private String owner;
    private String paymentMethod;
    private Integer premium;
    private String product;
    private String productDescription;
    private List<QuoteFees> quoteFees;
    private QuoteResults quoteResults;
    private String reference;
    private String reviewDate;
    private Risk risk;
    private String scheme;
    private String startDate;
    private String status;
    private String subBranch;
    private String term;
    private String thirdParty;
	public String getAccountHandler() {
		return accountHandler;
	}
	public void setAccountHandler(String accountHandler) {
		this.accountHandler = accountHandler;
	}

	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getBusinessSource() {
		return businessSource;
	}
	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public String getClientRequirements() {
		return clientRequirements;
	}
	public void setClientRequirements(String clientRequirements) {
		this.clientRequirements = clientRequirements;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getEdiMessageSent() {
		return ediMessageSent;
	}
	public void setEdiMessageSent(Integer ediMessageSent) {
		this.ediMessageSent = ediMessageSent;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getExternalQuoteReference() {
		return externalQuoteReference;
	}
	public void setExternalQuoteReference(String externalQuoteReference) {
		this.externalQuoteReference = externalQuoteReference;
	}
	public Integer getFinanceBalance() {
		return financeBalance;
	}
	public void setFinanceBalance(Integer financeBalance) {
		this.financeBalance = financeBalance;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInsuranceFileId() {
		return insuranceFileId;
	}
	public void setInsuranceFileId(Integer insuranceFileId) {
		this.insuranceFileId = insuranceFileId;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getInsurer() {
		return insurer;
	}
	public void setInsurer(String insurer) {
		this.insurer = insurer;
	}
	public String getInterestedParty() {
		return interestedParty;
	}
	public void setInterestedParty(String interestedParty) {
		this.interestedParty = interestedParty;
	}
	public MototrQuoteResults getMotorQuoteResults() {
		return motorQuoteResults;
	}
	public void setMotorQuoteResults(MototrQuoteResults motorQuoteResults) {
		this.motorQuoteResults = motorQuoteResults;
	}
	public MotorRisk getMotorRisk() {
		return motorRisk;
	}
	public void setMotorRisk(MotorRisk motorRisk) {
		this.motorRisk = motorRisk;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Integer getPremium() {
		return premium;
	}
	public void setPremium(Integer premium) {
		this.premium = premium;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public List<QuoteFees> getQuoteFees() {
		return quoteFees;
	}
	public void setQuoteFees(List<QuoteFees> quoteFees) {
		this.quoteFees = quoteFees;
	}
	public QuoteResults getQuoteResults() {
		return quoteResults;
	}
	public void setQuoteResults(QuoteResults quoteResults) {
		this.quoteResults = quoteResults;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
	public Risk getRisk() {
		return risk;
	}
	public void setRisk(Risk risk) {
		this.risk = risk;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubBranch() {
		return subBranch;
	}
	public void setSubBranch(String subBranch) {
		this.subBranch = subBranch;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getThirdParty() {
		return thirdParty;
	}
	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}
	public List<Addons> getAddons() {
		return addons;
	}
	public void setAddons(List<Addons> addons) {
		this.addons = addons;
	}
}
