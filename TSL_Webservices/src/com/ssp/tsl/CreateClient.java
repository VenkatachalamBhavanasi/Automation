package com.ssp.tsl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ssp.tsl.clients.model.Addresses;
import com.ssp.tsl.clients.model.Client;
import com.ssp.tsl.clients.model.Country;
import com.ssp.tsl.clients.model.EmailAddresses;
import com.ssp.tsl.clients.model.MarketingPreferences;
import com.ssp.tsl.clients.model.TelephoneNumbers;

public class CreateClient {
	private static String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6WyIxIiwic2lyaXVzIl0sIlBhcnR5SWQiOjAsInJvbGUiOiJCYWNrT2ZmaWNlIiwibmJmIjoxNTM1MzY5OTg5LCJleHAiOjE1MzUzNzM1ODksImlhdCI6MTUzNTM2OTk4OSwiaXNzIjoiRXhhbXBsZUlzc3VlciIsImF1ZCI6IkV4YW1wbGVBdWRpZW5jZSJ9.-tIdhx7ZRgcTPOR6TsQrVyT2INHCxXEstlFxu2gzfKs";
	private static String emailId="venkatachalam51@mail.com";
	public static void main(String[] args) throws IOException {

		// String clientId = "";
		String userName = "sirius";
		String password = "XctqMUbg";
		// CreateQuote createQuote = new CreateQuote();
		// JsonObject obj = createQuote.login();
		// // token = json.get("token").getAsString();
		// token = obj.get("token").getAsString();
		// clientId = obj.get("clientId").getAsString();
		// userName = obj.get("username").getAsString();
		//
		// System.out.println("Token" + " = " + token);
		// System.out.println("Client ID" + " = " + clientId);
		// System.out.println("UserName" + " = " + userName);

		JsonObject clientResponse = postClient(token, userName, password);
		System.out.println(clientResponse);
		String clientId =clientResponse.get("clientId").getAsString();
		System.out.println("Client ID" + "  " + clientId);
		JsonObject accNonceResponse = getAccNonce(clientId, userName, password);
		String nonce = accNonceResponse.get("nonce").getAsString();
		System.out.println("Nonce" + "=" + nonce);
		JsonObject accNoncePutResponse = putAccNonce(nonce, userName, password);
		System.out.println(accNoncePutResponse);
		CreateClient.testMethod();

	}

	private static String formClientRequest() {
		Gson gson = new Gson();
		Client client = new Client();
		client.setTitleCode("Mr");
		client.setForename("Ven");
		client.setInitials("B");
		client.setSurname("Bhavanasi");
		client.setGenderCode("Male");
		client.setDateOfBirth("1982-08-27");
		client.setMaritalStatusCode("Married");
		client.setCorrespondenceType("4");
		MarketingPreferences marketingPreferences = new MarketingPreferences();
		marketingPreferences.setTps(true);
		marketingPreferences.setMps(true);
		marketingPreferences.setEmps(true);
		client.setMarketingPreferences(marketingPreferences);
		client.setBranchID(1);
		client.setBusinessSourceId(1);
		client.setAgentId(1383285);
		client.setConsultantId(21);
		List<Addresses> addressesList = new ArrayList<>();
		Addresses addresses = new Addresses();
		addresses.setAddress1("37 Emanuel court");
		addresses.setAddress2("Norwich5");
		addresses.setAddress3("Buckhingam5");
		addresses.setAddress4("Mark Street");
		addresses.setPostCode("LA1 1LA");
		Country country = new Country();
		country.setCode("GBR");
		country.setName("United Kingdom");
		//country.setPostCodeVisibility(true);
		addresses.setCountry(country);
		addresses.setAddressUsage("Correspondence");
		addressesList.add(addresses);
		client.setAddresses(addressesList);
		List<EmailAddresses> emailAddressesList = new ArrayList<>();
		EmailAddresses emailAddresses = new EmailAddresses();
		emailAddresses.setEmail(emailId);
		emailAddresses.setDefault(true);
		emailAddressesList.add(emailAddresses);
		client.setEmailAddresses(emailAddressesList);
		List<TelephoneNumbers> telephoneNumbersList = new ArrayList<>();
		TelephoneNumbers telephoneNumbers = new TelephoneNumbers();
		telephoneNumbers.setNumber("1122335544");
		telephoneNumbers.setDefault(true);
		telephoneNumbers.setContactType("11");
		telephoneNumbersList.add(telephoneNumbers);
		client.setTelephoneNumbers(telephoneNumbersList);

		Type type = new TypeToken<Client>() {
		}.getType();
		String requestBody = gson.toJson(client, type);
		System.out.println(requestBody);
		return requestBody;
	}

	private static JsonObject postClient(String token, String userName, String password) throws IOException {

		String url = "http://vm-s4b-blddpy19:5001/api/Clients";
		URL obj = new URL(url);
		// URL obj = new URL(URLEncoder.encode(url,
		// StandardCharsets.UTF_8.name()));

		String authString = userName + ":" + password;
		System.out.println("auth string: " + authString);
		byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
		String authStringEnc = new String(authEncBytes);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		 //con.setRequestProperty("Authorization", "bearer "+token);
		con.setRequestProperty("Authorization", "Basic " + authStringEnc);

		String requestBody = formClientRequest();

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(requestBody);
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED ) {
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			con.disconnect();
			String res = response.toString();

			System.out.println(res);

			JsonObject json = (JsonObject) new JsonParser().parse(res);

			return json;

		}
		return null;
	}

	public static JsonObject getAccNonce(String clientId, String userName, String password) throws IOException {

		String url = "http://vm-s4b-blddpy19:5001/api/auth/accountNonce";
		URL obj = new URL(url);
		// URL obj = new URL(URLEncoder.encode(url,
		// StandardCharsets.UTF_8.name()));

		String authString = userName + ":" + password;
		System.out.println("auth string: " + authString);
		byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
		String authStringEnc = new String(authEncBytes);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		// con.setRequestProperty("Authorization", "bearer "+token);
		con.setRequestProperty("Authorization", "Basic " + authStringEnc);

		String requestBody = "";
		JsonObject request = new JsonObject();
		request.addProperty("clientId", clientId);
		request.addProperty("emailAddress", emailId);
		request.addProperty("nonceType", "RegisterConsumer");
		requestBody = request.toString();
		System.out.println(requestBody);

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(requestBody);
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) {
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			con.disconnect();
			String res = response.toString();

			System.out.println(res);

			JsonObject json = (JsonObject) new JsonParser().parse(res);

			return json;

		}
		return null;

	}

	public static JsonObject putAccNonce(String nonce, String userName, String password) throws IOException {

		String url = "http://vm-s4b-blddpy19:5001/api/auth/accountNonce";
		URL obj = new URL(url);
		// URL obj = new URL(URLEncoder.encode(url,
		// StandardCharsets.UTF_8.name()));

		String authString = userName + ":" + password;
		System.out.println("auth string: " + authString);
		byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
		String authStringEnc = new String(authEncBytes);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("PUT");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		//con.setRequestProperty("Authorization", "bearer "+token);
		con.setRequestProperty("Authorization", "Basic " + authStringEnc);

		String requestBody = "";
		JsonObject request = new JsonObject();
		request.addProperty("nonce", nonce);
		request.addProperty("nonceType", "RegisterConsumer");
		request.addProperty("dateOfBirth", "1982-08-27");
		request.addProperty("password", "Login@2018");
		request.addProperty("confirmPassword", "Login@2018");
		requestBody = request.toString();
		System.out.println(requestBody);

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(requestBody);
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) {
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			con.disconnect();
			String res = response.toString();

			System.out.println(res);

			JsonObject json = (JsonObject) new JsonParser().parse(res);

			return json;

		}
		return null;

	}

	public static void testMethod() {
		
		System.setProperty("webdriver.chrome.driver", "D:\\SSP\\SSP\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		 driver.get("http://ald-edgesrv:8091/EIS-SSC/");
         driver.manage().window().maximize();
         
         driver.findElementById("C2__BUT_E14D31D21EBB8E7F24944").click();
         //driver.FindElement(By.Id(""));
         // driver.FindElement(By.Id(""));
         //Type user details
         WebDriverWait waitForElement = new WebDriverWait(driver, 30);
         waitForElement.until(ExpectedConditions.visibilityOfElementLocated(By.id("C2__sign_in")));
         driver.findElementById("C2__email_address").sendKeys(emailId);
         driver.findElementById("C2__password").sendKeys("Login@2018");

         //click the login button
         driver.findElementById("C2__sign_in").click();
		
		
	}
}
