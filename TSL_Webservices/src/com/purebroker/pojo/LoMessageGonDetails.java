package com.purebroker.pojo;

import javax.xml.bind.annotation.XmlAttribute;

public class LoMessageGonDetails {
	
	@XmlAttribute(name="NUDATETIME")
	private String nuDateTime;
	@XmlAttribute(name="COVERENDTIME")
	private String coverEndTime;
	@XmlAttribute(name="COVERSTARTTIME")
	private String coverStartTime;
	@XmlAttribute(name="COVERSTARTDATE")
	private String coverStartDate;
	@XmlAttribute(name="COVERENDDATE")
	private String coverEndDate;
	@XmlAttribute(name="ENDPOINTID")
	private String endPointId;
	@XmlAttribute(name="INTERGRATORID")
	private String intergratorId;
}
