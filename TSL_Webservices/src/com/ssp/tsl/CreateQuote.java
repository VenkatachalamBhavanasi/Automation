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

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ssp.tsl.quotes.models.AcceptedQuoteResult;
import com.ssp.tsl.quotes.models.Addons;
import com.ssp.tsl.quotes.models.AdverseInsuranceHistory;
import com.ssp.tsl.quotes.models.Base;
import com.ssp.tsl.quotes.models.Content;
import com.ssp.tsl.quotes.models.Conviction;
import com.ssp.tsl.quotes.models.Loss;
import com.ssp.tsl.quotes.models.MototrQuoteResults;
import com.ssp.tsl.quotes.models.OccupationDetailMain;
import com.ssp.tsl.quotes.models.PedalCycle;
import com.ssp.tsl.quotes.models.PersonalPossessions;
import com.ssp.tsl.quotes.models.PolicyHolder;
import com.ssp.tsl.quotes.models.Premises;
import com.ssp.tsl.quotes.models.PreviousInsurance;
import com.ssp.tsl.quotes.models.Quote;
import com.ssp.tsl.quotes.models.QuoteFees;
import com.ssp.tsl.quotes.models.QuoteResults;
import com.ssp.tsl.quotes.models.Risk;
import com.ssp.tsl.quotes.models.Security;
import com.ssp.tsl.quotes.models.SpecifiedItem;

public class CreateQuote {

	private static String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6WyIzMzMwMSIsImppdGVzaEBzc3AuY29tIl0sIlBhcnR5SWQiOjEzODM4ODcsInJvbGUiOiJDb25zdW1lciIsIm5iZiI6MTUzNTAzODU4NiwiZXhwIjoxNTM1MDQyMTg2LCJpYXQiOjE1MzUwMzg1ODYsImlzcyI6IkV4YW1wbGVJc3N1ZXIiLCJhdWQiOiJFeGFtcGxlQXVkaWVuY2UifQ.Y9fqhg4ovmSImosw1P-8f55NaEZQ7yOmiW0szJUgmCw";

	public static void main(String[] args) throws IOException {

		String clientId = "";
		String userName = "jitesh@ssp.com";
		String password = "Login@2018";
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

		JsonObject quoteResponse = postQuotes(token, userName, password);
		String quoteId = quoteResponse.get("quoteId").getAsString();
		JsonObject subQuotationResp = submitForQuotation(quoteId, userName, password);
		JsonObject buyResp = buyQuote(quoteId, userName, password);
	}

	public static JsonObject submitForQuotation(String quoteId, String userName, String password) throws IOException {

		String url = "http://vm-s4b-blddpy19:5001/api/Quotes/" + quoteId + "/submitForQuotation";
		URL obj = new URL(url);
		// URL obj = new URL(URLEncoder.encode(url,
		// StandardCharsets.UTF_8.name()));

		String authString = userName + ":" + password;
		System.out.println("auth string: " + authString);
		byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
		String authStringEnc = new String(authEncBytes);

		///HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		HttpURLConnection con= (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		// con.setRequestProperty("Authorization", "bearer "+token);
		con.setRequestProperty("Authorization", "Basic " + authStringEnc);

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes("");
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

	private static JsonObject postQuotes(String token, String userName, String password) throws IOException {

		String url = "http://vm-s4b-blddpy19:5001/api/Quotes";
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

		String requestBody = formQuoteRequest();

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

	private static String formQuoteRequest() {
		Gson gson = new Gson();

		Base base = new Base();
		Quote quote = new Quote();
		quote.setAccountHandler("");

		/*
		 * List<Addons> addonsList = new ArrayList<Addons>(); Addons addons =
		 * new Addons(); addons.setAddonCnt(0); addons.setAddonDescription(
		 * "Sample Addon"); addonsList.add(addons); quote.setAddons(addonsList);
		 */

		quote.setBranch("Head Office");
		quote.setBusinessSource("");
		quote.setClientId(1383887);
		quote.setClientRequirements("");
		quote.setDateCreated("2018-08-24T12:51:32.942Z");
		quote.setDescription("8 Dapdune Road");
		quote.setEdiMessageSent(0);
		quote.setEndDate("2019-08-24T12:51:32.942Z");
		quote.setExternalQuoteReference("");
		quote.setFinanceBalance(0);
		quote.setId(0);
		quote.setInsuranceFileId(0);
		quote.setInsuredName("Mr T AugustTwentyFour");
		quote.setInsurer("");
		quote.setInterestedParty("");
		quote.setMotorQuoteResults(null);
		quote.setMotorRisk(null);
		quote.setOwner("Mr T AugustTwentyFour");
		quote.setPaymentMethod("");
		quote.setPremium(0);
		quote.setProduct("GEMINI IIH");
		quote.setProductDescription("Household");

		List<QuoteFees> quoteFeesList = new ArrayList<QuoteFees>();
		QuoteFees quoteFees = new QuoteFees();
		quoteFees.setFeeExcludingTax(10);
		quoteFees.setFeeIncludingTax(10);
		quoteFees.setFeePercentage(0);
		quoteFees.setFeeType("Policy Fee");
		quoteFees.setId(236113);
		quoteFeesList.add(quoteFees);

		quote.setQuoteFees(quoteFeesList);

		QuoteResults quoteResults = new QuoteResults();
		quoteResults.setAcceptedQuoteResultList(null);
		quoteResults.setDeclineQuoteResultList(null);

		quote.setQuoteResults(quoteResults);
		quote.setReference("");
		quote.setReviewDate("2018-08-24T12:51:32.942Z");

		Risk risk = new Risk();

		Content content = new Content();
		content.setClaimFreeYears(0);
		content.setContentsId(10);
		content.setContentsValue(30000);
		content.setCover("Indemnity");
		content.setCoverAbiCode("3");
		content.setFullReplacement(true);
		content.setMostExpensiveItem(1000);

		PreviousInsurance previousInsurance = new PreviousInsurance();
		previousInsurance.setExpiryDate("2016-05-01T00:00:00");
		previousInsurance.setPaymentFrequency("Annually");
		previousInsurance.setPaymentFrequencyAbiCode("A");
		previousInsurance.setPolicyNumber("1");
		previousInsurance.setPreviousInsuranceId(2849511);
		previousInsurance.setPreviousInsurer("A A Mutual International");
		previousInsurance.setPreviousInsurerAbiCode("159");

		content.setPreviousInsurance(previousInsurance);
		content.setSpecifiedItemList(new ArrayList<SpecifiedItem>());
		content.setTotalExcess(100);
		content.setValuables(100);

		risk.setContents(content);
		risk.setCoverStartDate("2018-08-24T12:51:32.942Z");
		risk.setHouseholdRiskId(null);
		risk.setInsuranceFolderId(0);
		risk.setJointPolicyHolder(null);
		risk.setLossList(new ArrayList<Loss>());

		PersonalPossessions personalPossessions = new PersonalPossessions();
		personalPossessions.setCreditCard(0);
		personalPossessions.setFrozenFood(0);
		personalPossessions.setLegalExpenses(0);
		personalPossessions.setPedalCycles(new ArrayList<PedalCycle>());
		personalPossessions.setPersonalMoney(0);
		personalPossessions.setPersonalPossessionsId(0);
		personalPossessions.setSpecifiedItemList(new ArrayList<SpecifiedItem>());
		risk.setPersonalPossessions(personalPossessions);

		PolicyHolder policyHolder = new PolicyHolder();
		policyHolder.setAdverseFinancialHistory("");
		policyHolder.setAdverseFinancialHistoryAbiCode("");

		AdverseInsuranceHistory adverseInsuranceHistory = new AdverseInsuranceHistory();
		adverseInsuranceHistory.setAdverseInsuranceId(0);
		adverseInsuranceHistory.setAdverseInsuranceId(0);
		adverseInsuranceHistory.setExtraPrecautions(false);
		adverseInsuranceHistory.setIncreasedPremium(false);
		adverseInsuranceHistory.setRefused(false);
		adverseInsuranceHistory.setSpecialConditions(false);
		adverseInsuranceHistory.setWithdrawn(false);
		policyHolder.setAdverseInsuranceHistory(adverseInsuranceHistory);
		policyHolder.setConvictionList(new ArrayList<Conviction>());
		policyHolder.setDateOfBirth("1982-01-01T00:00:00");
		policyHolder.setFirstTimeBuyer(false);
		policyHolder.setForenames("TwelveFiftyOneThirty");
		policyHolder.setId(5);
		policyHolder.setSmoker(false);
		policyHolder.setMaritalStatus("Single");
		policyHolder.setMaritalStatusAbiCode("S");
		OccupationDetailMain occupationDetailMain = new OccupationDetailMain();
		occupationDetailMain.setEmployersBusiness("Lawyers");
		occupationDetailMain.setEmployersBusinessAbiCode("453");
		occupationDetailMain.setEmploymentStatus("Employed");
		occupationDetailMain.setEmploymentStatusAbiCode("E");
		occupationDetailMain.setFullTimeOccupation(true);
		occupationDetailMain.setOccupation("Accountant");
		occupationDetailMain.setOccupationAbiCode("A01");
		occupationDetailMain.setOccupationId(0);
		policyHolder.setOccupationDetailMain(occupationDetailMain);
		policyHolder.setOccupationDetailOther(null);
		policyHolder.setRelationship(null);
		policyHolder.setRelationshipAbiCode("");
		policyHolder.setSex("Male");
		policyHolder.setSexAbiCode("M");
		policyHolder.setSurname("AugustTwentyFour");
		policyHolder.setTitle("Mr");
		policyHolder.setTitleAbiCode("Mr");
		risk.setPolicyHolder(policyHolder);

		Premises premises = new Premises();
		premises.setAddress1("173 Emanuel Court");
		premises.setAddress2("Norwich6");
		premises.setAddress3("Buckhingam5");
		premises.setAddress4("Mark Street");
		premises.setBusinessUse(false);
		premises.setClericalUse(false);
		premises.setExtension(false);
		premises.setFlatRoofPerc(0);
		premises.setFreeFromFlooding(true);
		premises.setFreeFromSubsidence(true);
		premises.setGoodRepairSoMaintained(true);
		premises.setUnderpinned(false);
		premises.setListed("Not Listed");
		premises.setListedAbiCode("3");
		premises.setMaximumDaysUnoccupied(14);
		premises.setNearRiverbank(false);
		premises.setNumberOfBaths(1);
		premises.setNumberOfBeds(3);
		premises.setOccupancy("Permanent Home");
		premises.setOccupancyAbiCode("PH");
		premises.setOccupantsTot(1);
		premises.setOccupantsUnder18(0);
		premises.setOwnership("Owned - Not Let");
		premises.setOwnershipAbiCode("08");
		premises.setPostCode("B37 7YE");
		premises.setPremisesId(4);
		premises.setPropertyType("Semi-Detached Bungalow");
		premises.setPropertyTypeAbiCode("09");
		premises.setResident("2001-05-01T00:00:00");
		premises.setRoofType("Standard Construction");
		premises.setRoofTypeAbiCode("11");
		premises.setSelfContainedLockable(true);
		premises.setSoleOccupancy(true);
		premises.setTreeInVicinity(false);
		premises.setUnoccupiedDaily(true);
		premises.setUnoccupiedNightly(false);
		premises.setWallType("Standard Construction");
		premises.setWallTypeAbiCode("15");
		premises.setYearBuilt(2001);
		risk.setPremises(premises);

		Security security = new Security();
		security.setAlarmApproved(false);
		security.setAlarmInstalled(false);
		security.setApprovedLocksFitted(true);
		security.setExteriorSecurityLighting(true);
		security.setNeighborhoodWatchScheme(true);
		security.setSafeInstalled(false);
		security.setSecurityActivated(true);
		security.setSecurityDiscountRequired(false);
		security.setSecurityId(9);
		security.setSmokeDetectors(false);
		security.setTypeOfAlarm("");
		security.setTypeOfAlarmAbiCode("");

		risk.setSecurity(security);
		quote.setRisk(risk);
		quote.setScheme("ZURICH ADVANTAGE 20");
		quote.setStartDate("2018-08-24T12:51:32.942Z");
		quote.setStatus("Quote");
		quote.setSubBranch("Head Office");
		quote.setTerm("Annually");
		quote.setThirdParty("");
		base.setQuote(quote);

		/*
		 * MototrQuoteResults motorQuoteResults = new MototrQuoteResults();
		 * List<AcceptedQuoteResult> acceptedQuoteResult = new
		 * ArrayList<AcceptedQuoteResult>(); AcceptedQuoteResult
		 * acceptedQuoteResult1 = new AcceptedQuoteResult();
		 * acceptedQuoteResult1.setPremium(1);
		 * motorQuoteResults.setAcceptedQuoteResultList(acceptedQuoteResult);
		 * acceptedQuoteResult.add(acceptedQuoteResult1);
		 * quote.setMotorQuoteResults(motorQuoteResults);
		 */

		Type type = new TypeToken<Base>() {
		}.getType();
		String requestBody = gson.toJson(base, type);
		System.out.println(requestBody);
		return requestBody;
	}

	public JsonObject login() throws IOException {

		String url = "http://vm-s4b-blddpy19:5001/api/auth/login";
		URL obj = new URL(url);
		// URL obj = new URL(URLEncoder.encode(url,
		// StandardCharsets.UTF_8.name()));

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");

		// con.setRequestProperty("bearer", "");

		String requestBody = "{\"username\":\"" + "jitesh@ssp.com" + "\",\"password\":\"" + "Login@2018" + "\"}";

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

	public static JsonObject buyQuote(String quoteId, String userName, String password) throws IOException {

		String url = "http://vm-s4b-blddpy19:5001/api/Quotes/" + quoteId + "/buy";
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
		request.addProperty("quoteResultId", 0);
		request.addProperty("paymentMethod", "");
		request.addProperty("processDebitTransaction", false);
		request.addProperty("processPayment", true);
		request.addProperty("cardExpiryDate", "");
		request.addProperty("cardNumber", "");
		request.addProperty("cardSchemeName", "");
		request.addProperty("cardTokenExpiryDate", "");
		request.addProperty("cardTokenId", "");
		request.addProperty("continuousPaymentAuthority", true);
		request.addProperty("defaultCardTokenId", "");
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
}
