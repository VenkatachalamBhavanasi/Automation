package com.ssp.testscripts.smoke;

import java.io.IOException;
//import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.relevantcodes.extentreports.ExtentTest;
import com.ssp.pages.uxp.*;
import com.ssp.soapservices.SOAP_UI;
import com.ssp.support.*;

@Listeners(EmailReport.class)
public class UXP_EC_Demo  extends BaseTest{
	private String webSite;
	

	@BeforeMethod
	public void init(ITestContext context) throws IOException {
		webSite = System.getProperty("webSite") != null ? System.getProperty("webSite")
				: context.getCurrentXmlTest().getParameter("webSite");
		
	}

	public ExtentTest addTestInfo(String testCaseId, String testDesc) {

		String browserwithos = null;
		String test = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getName();

		String browsername = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
				.getParameter("browser");
		String browserversion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
				.getParameter("browser_version");
		String os = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("os").substring(0,
				1);
		String osversion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
				.getParameter("os_version");
		browserwithos = os + osversion + "_" + browsername + browserversion;

		return Log.testCaseInfo(testCaseId + " [" + test + "]",
				testCaseId + " - " + testDesc + " [" + browserwithos + "]", test, "UXP-EC-ASDA");
	}
	
	public HashMap<String, String> getTestData(String testcaseId) {
		String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
		String className = "UXP_EC_Demo_" + env;
		return DataUtils.testDatabyID(testcaseId, className);
	}
	
	@Test(description = "Create New Business Policy - Annual Payment", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_001(String browser) throws Exception {

		// Get the web driver instance
		String tcId = "TC_001";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String brandname = testData.get("Brand Name");
		String Corecover = testData.get("Cover");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite,extentedReport).get();
			Log.pass("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Login failed",
					driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search page is verified", "Search Page is not verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard page",
					"Not Verified Customer Name on Dashboard page", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			// Create New Quote
			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			// Get and Buy Quote
			newquotepage.clickGetQuote(extentedReport);
			newquotepage.clickBuy(extentedReport);
			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			newquotepage.takePayment(extentedReport);
			CardDetailsPage carddetailspage = newquotepage.selectVisacard(extentedReport);

			// Enter Card Details
			carddetailspage.enterCardNumber(testData, extentedReport, false);
			carddetailspage.selectExpiry(testData, extentedReport, false);
			carddetailspage.enterVerification(extentedReport, false);
			carddetailspage.enterName(testData, extentedReport, true);
			carddetailspage.clickbuy(extentedReport, true);
			Log.message("Entered Card Details and Navigated to Verificaion page", driver, extentedReport, true);

			// Click continue button
			newquotepage = carddetailspage.clickContinueButton(extentedReport);
			Log.softAssertThat(newquotepage.verifyPaymentTrasaction(extentedReport), "Payment was successful",
					"Payment was not successful", driver, extentedReport, true);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);
			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "Create person through soap call and search for the person in CC", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_002(String browser) throws Exception {

		// Get the web driver instance
		String tcId = "TC_002";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		XmlTest xmlTestNG = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest();
		// Get SOAP Class Instance
		SOAP_UI ui = new SOAP_UI();
		String userName = testData.get("UserName").toString();
		String password = testData.get("Password").toString();
		String foreName = testData.get("First Name").toString() + DateTimeUtility.getTimeIn_MMSS().toString();
		String surName = testData.get("Last Name").toString() + DateTimeUtility.getTimeIn_MMSS().toString();
		String description = testData.get("Description").toString();
		String customerName = "Doctor X"+ " "+foreName+" "+surName;
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		String soapSuiteName = "POC";
		String soapProjectLoc = "src/main/resources/projects/EC_SoapProject.xml";
		String soapTestStepName = "createPerson";
		String soapTestCaseName = "POC";
		String soapen = xmlTestNG.getParameter("soapenv").toString();
		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {
			
			// Created Contact through SOAP Call
			/*String URN = ui.createPersonThruSOAPCall(soapProjectLoc, soapSuiteName, soapTestCaseName, soapTestStepName,
					foreName, surName, soapen, extentedReport);*/
			/*Log.message("Web service endpoint URL: " + soapen, extentedReport);
			Log.pass("Successfully Created Contact through SOAP Call. Person Id : " + URN, extentedReport);
			// Retrieve Contact Details through SOAP call
			String lastName = ui.retrievePersonThroughSOAPCall(soapProjectLoc, soapSuiteName, soapTestCaseName,
					"retrievePerson", URN,soapen, extentedReport);		*/	
			/*System.out.println("lastName-->"+lastName);
			Log.pass("Retrieve Contact Details through SOAP call!! Person LastName : " + lastName+ " , FirstName : "+foreName, extentedReport);
			// Navigate to Login Page
*/			LoginPage loginPage = new LoginPage(driver, webSite,extentedReport).get();
			Log.pass("Navigated to Login Page", driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.message("Logged into UXP with User id :" + userName + "& Password :" + password, driver, extentedReport);
			homePage.verifyHomePage();
			Log.pass("Successfully logged into UXP Page", driver, extentedReport, true);

			homePage.clickAdminTask(extentedReport);
			homePage.clickMyBrands("All Brands", extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true); 
			// Search with Valid user names
			/*searchPage.enterLastName(lastName, extentedReport);*/
			searchPage.enterFirstName(foreName, extentedReport);
			searchPage.clickSearch(extentedReport);
			/*Log.message("Searching with the last name : " + lastName, extentedReport);*/
			searchPage.click_proceedButton(extentedReport, true);
			CustDashboardPage custdashboardPage = searchPage.verifySearchResults(extentedReport);
			Log.softAssertThat(custdashboardPage.verifyCustomerDashboardPage(),
					"Customer Dashboard Page is loaded properly", "Customer Dashboard Page is not loaded properly",
					driver, extentedReport, true);
			Log.softAssertThat(custdashboardPage.verifyContactName(customerName,extentedReport,true),
					"Successfully Searched FirstName and LastName on Customer Dashboard",
					"Not Verified Customer Details on Dashboard", driver, extentedReport, true);
			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} 
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

}
