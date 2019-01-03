package com.purebroker.pojo;

import javax.xml.bind.annotation.XmlElement;

public class RiskObject {
	@XmlElement(name="SSPTRD01_POLICY_BINDER")
	private SspTrd01PolicyBinder sspTrd01PolicyBinder;
	@XmlElement(name="COVER")
	private Cover cover;
	@XmlElement(name="EQUIPMENT")
	private Equipment equipment;
	@XmlElement(name="PUBLICLIABILITYCOVER")
	private PublicLiabilityCover publicLiabilityCover;
	@XmlElement(name="EMPLOYERSLIABILITYCOVER")
	private EmployersLiabilityCover employersLiabilityCover;
	@XmlElement(name="TRAVELCOVER")
	private TravelCover travelCover;
	@XmlElement(name="BUSINESSINTERRUPTIONCOVER")
	private BusinessInterruptionCover businessInterruptionCover;
}
