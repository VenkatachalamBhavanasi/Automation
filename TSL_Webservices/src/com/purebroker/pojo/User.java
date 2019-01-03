package com.purebroker.pojo;

import javax.xml.bind.annotation.XmlAttribute;

public class User {
	
	@XmlAttribute(name="USERID")
	private String userId;
	@XmlAttribute(name="USERNAME")
	private String userName;
	@XmlAttribute(name="COMPANYNAME")
	private String companyName;
	@XmlAttribute(name="FAXNUMBER")
	private String faxNumber;
	@XmlAttribute(name="EXTENSIONNUMBER")
	private String extensionNumber; 
	@XmlAttribute(name="TITLE")
	private String title;
	@XmlAttribute(name="FULLNAME")
	private String fullName;
	@XmlAttribute(name="EMAILADDRESS")
	private String emailAddress;
	@XmlAttribute(name="MOBILENUMBER")
	private String mobileNumber;
	@XmlAttribute(name="TELEPHONENUMBER")
	private String telephoneNUmber;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public void setExtensionNumber(String extensionNumber) {
		this.extensionNumber = extensionNumber;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public void setTelephoneNUmber(String telephoneNUmber) {
		this.telephoneNUmber = telephoneNUmber;
	}
	
	
	

}
