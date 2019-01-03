package com.ssp.purebroker;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.purebroker.pojo.DataSet;
import com.purebroker.pojo.Message;
import com.purebroker.pojo.QuoteRequest;


public class Test {

	public static void main(String[] args) throws JAXBException {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		QuoteRequest quoteRequest = new QuoteRequest();
		quoteRequest.setBusinessUnitId(888);
		quoteRequest.setCustomerId(88);
		quoteRequest.setSchemeId(450);
		quoteRequest.setTransactionType("NewBusiness");
		quoteRequest.setLineOfBusiness("Tradesman");
		quoteRequest.setRisk(riskXml());
		quoteRequest.setRiskFormat("XML");
		quoteRequest.setStatus("Quoted");
		Type type = new TypeToken<QuoteRequest>() {
		}.getType();
		String requestBody = gson.toJson(quoteRequest, type);
		System.out.println(requestBody);
	}

	public static String riskXml() throws JAXBException {
		DataSet dataset = new DataSet();
		dataset.setDataModelCode("SspTrd01");
		dataset.setNextOINumber("91");

		Message message = new Message();
		message.setMessageCount("7");
		message.setQuoteVersion("1");
		dataset.setMessage(message);

		JAXBContext jaxbContext = JAXBContext.newInstance(DataSet.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// // output pretty printed
		// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);

		jaxbMarshaller.marshal(dataset, out);

		String xml = new String(out.toByteArray());
		xml = xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
//		xml = xml.replaceAll("\"", "\\\\\"");
		System.out.println(xml);
		return xml;
	}

}
