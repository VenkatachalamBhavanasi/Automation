package com.purebroker.pojo;

import javax.xml.bind.annotation.XmlAttribute;

public class Message {

	@XmlAttribute(name = "QUOTEVERSION")
	private String quoteVersion;
	@XmlAttribute(name = "MESSAGECOUNT")
	private String messageCount;

	public void setQuoteVersion(String quoteVersion) {
		this.quoteVersion = quoteVersion;
	}

	public void setMessageCount(String messageCount) {
		this.messageCount = messageCount;
	}
}