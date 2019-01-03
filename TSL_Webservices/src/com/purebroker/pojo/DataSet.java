package com.purebroker.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DATA_SET")
public class DataSet {

	@XmlAttribute(name = "DataModelCode")
	private String dataModelCode;
	@XmlAttribute(name = "NextOINumber")
	private String nextOINumber;
	@XmlElement(name = "MESSAGE")
	private Message message;
	@XmlElement(name="USER")
	private User user;
	@XmlElement(name="LOMESSAGEGONDETAILS")
	private LoMessageGonDetails loMessageGonDetails;
	@XmlElement(name= "RISK_OBJECTS")
	private RiskObject riskObject;
	@XmlElement(name="DELETED_OBJECTS")
	private String deletedObjects;
	@XmlElement(name="QUOTES")
	private String quotes;
	
	public void setDataModelCode(String dataModelCode) {
		this.dataModelCode = dataModelCode;
	}

	public void setNextOINumber(String nextOINumber) {
		this.nextOINumber = nextOINumber;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
