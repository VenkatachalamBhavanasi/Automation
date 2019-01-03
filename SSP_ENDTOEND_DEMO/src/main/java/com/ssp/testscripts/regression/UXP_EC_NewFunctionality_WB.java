package com.ssp.testscripts.regression;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebDriver;

import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.ssp.pages.uxp.CustDashboardPage;
import com.ssp.pages.uxp.HomePage;
import com.ssp.pages.uxp.LoginPage;
import com.ssp.pages.uxp.NewQuotePage;
import com.ssp.pages.uxp.SearchPage;
import com.ssp.support.BaseTest;
import com.ssp.support.DataProviderUtils;
import com.ssp.support.DataUtils;
import com.ssp.support.DateTimeUtility;
import com.ssp.support.EmailReport;
import com.ssp.support.Log;
import com.ssp.support.Utils;
import com.ssp.support.WaitUtils;
import com.ssp.support.WebDriverFactory;

@Listeners(EmailReport.class)
public class UXP_EC_NewFunctionality_WB extends BaseTest {
	private String webSite;
	public String monthlywebSite;
	private HashMap<String, String> customerDetails2 = new HashMap<String, String>();

	@BeforeMethod
	public void init(ITestContext context) throws IOException {
		webSite = System.getProperty("webSite") != null ? System.getProperty("webSite")
				: context.getCurrentXmlTest().getParameter("webSite");
		monthlywebSite = System.getProperty("monthlywebSite") != null ? System.getProperty("monthlywebSite")
				: context.getCurrentXmlTest().getParameter("monthlywebSite");
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
				testCaseId + " - " + testDesc + " [" + browserwithos + "]", test, "UXP-EC-NewFunctionality");
	}

	public HashMap<String, String> getTestData(String testcaseId) {
		String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
		String className = "UXP_EC_NewFunctionality_" + env + "_WB";
		return DataUtils.testDatabyID(testcaseId, className);
	}

	@Test(description = "Verify No Interested Parties message,Add Personal contact as an Interested Party,Add Interested Party Modal field verification,Add Personal contact as an Interested Party,Remove an Interested Party,Remove an Interested Party by selecting 'Yes' option", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_065_066_070_076_078_081(String browser) throws Exception {

		String tcId = "TC_065_066_070_076_078_081";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String addInterestContactType = testData.get("AddInterestContactType");
		String brandname = testData.get("Brand Name");
		String Corecover = testData.get("Cover");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			/// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
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
			searchPage.enterCustomerDetails(testData, true, extentedReport);
			String mainCustTitle = testData.get("Title");
			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			// String mainPolicyholderName = mainCustFirstName + " " +
			// mainCustLastName;
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);
			// String PolicyhlderName = mainCustFirstName + " " +
			// mainCustLastName;
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(
							testData.get("Title") + " " + mainCustFirstName + " " + mainCustLastName, extentedReport,
							true),
					"Verified FirstName and LastName on Customer Dashboard page",
					"Not Verified Customer Name on Dashboard page", driver, extentedReport, true);

			homePage = custdashboardpage.clickCompleteButton();
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			searchPage.clickCreateCustomer(true, extentedReport);
			customerDetails2 = searchPage.enterCustomerDetails(testData, true, extentedReport, true);
			customerDetails2.get("First Name");
			customerDetails2.get("Last Name");
			Log.message("Contact 2 First Name------>" + customerDetails2.get("First Name"), driver, extentedReport);
			Log.message("Contact 2 Last Name------>" + customerDetails2.get("Last Name"), driver, extentedReport);
			searchPage.confirmCreateCustomer(false, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + customerDetails2.get("First Name")
							+ " " + customerDetails2.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard page",
					"Not Verified Customer Name on Dashboard page", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			// Verify New Quote
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
			Log.softAssertThat(newquotepage.checkInterestedParties(), "Interested parties column is visible",
					"Interested parties column is not visible", driver, extentedReport, true);
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("noInterestedpartiesMsg"), newquotepage),
					"No interested parties have been added--text visible",
					"No interested parties have been added-text has not visible", driver, extentedReport, true);
			newquotepage.clickAddParties(extentedReport, true);
			Log.softAssertThat(
					!newquotepage.uielement.verifyPageElements(Arrays.asList("addInterestsearchBtn"), newquotepage),
					"Search button is not visible before selecting  contact type",
					"Search button is visible before selecting  contact type", driver, extentedReport, true);
			newquotepage.selectContactType(addInterestContactType, extentedReport);

			newquotepage.AIenterLastName(mainCustLastName, extentedReport);

			newquotepage.AIenterFirstName(mainCustFirstName, extentedReport);

			newquotepage.clicksearch(extentedReport);

			newquotepage.checkAddPartySearchResultsPane(extentedReport, true);

			newquotepage.clickselect(extentedReport, true);

			newquotepage.addInterestedPartyType(extentedReport, true);

			// newquotepage.addInterestedPartyTypeToDate(extentedReport, true);

			newquotepage.addInterestedPartyClickSave(extentedReport, true);

			Log.softAssertThat(
					newquotepage.checkaddedInterestedParty(
							mainCustTitle + " " + mainCustFirstName + " " + mainCustLastName),
					mainCustTitle + " " + mainCustFirstName + " " + mainCustLastName + "Added party is visible",
					mainCustTitle + " " + mainCustFirstName + " " + mainCustLastName + " Added party is not visible",
					driver, extentedReport, true);

			newquotepage.click_Deleteicon(extentedReport, true);

			Log.softAssertThat(newquotepage.checkremoveDescription(),
					"Interested Parties Remove description message is displayed : "
							+ newquotepage.INTERESTED_PARTIES_REMOVE_DESC1 + " and "
							+ newquotepage.INTERESTED_PARTIES_REMOVE_DESC2,
					"Interested Parties Remove description message is not displayed", driver, extentedReport, true);

			newquotepage.yesnobuttonVisible(extentedReport, true);

			newquotepage.clickyesbutton(extentedReport, true);
			Log.softAssertThat(newquotepage.checkInterestedParties(), "Interested parties column is visible",
					"Interested parties is not visible", driver, extentedReport, true);

			if (!newquotepage.verify_IntPartyName(
					testData.get("Title") + " " + testData.get("First Name") + " " + testData.get("Last Name"),
					extentedReport)) {
				Log.pass("Intersted party has been removed after clicking yes button in remove modal", driver,
						extentedReport, true);
			} else {
				Log.fail("Interested party was not removed after clicking yes button in remove modal", driver,
						extentedReport, true);
			}

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(description = "Verify No Interested Parties message,Add Personal contact as an Interested Party,Add Interested Party Modal field verification,Add Personal contact as an Interested Party,Remove an Interested Party,Remove an Interested Party by selecting 'Yes' option", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_082_083_089(String browser) throws Exception {

		String tcId = "TC_082_083_089";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String addInterestContactType = testData.get("AddInterestContactType");
		String brandname = testData.get("Brand Name");
		String Corecover = testData.get("Cover");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			/// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
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
			searchPage.enterCustomerDetails(testData, true, extentedReport);
			String mainCustTitle = testData.get("Title");
			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			// String mainPolicyholderName = mainCustFirstName + " " +
			// mainCustLastName;
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);
			// String PolicyhlderName = mainCustFirstName + " " +
			// mainCustLastName;
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(
							testData.get("Title") + " " + mainCustFirstName + " " + mainCustLastName, extentedReport,
							true),
					"Verified FirstName and LastName on Customer Dashboard page",
					"Not Verified Customer Name on Dashboard page", driver, extentedReport, true);

			homePage = custdashboardpage.clickCompleteButton();
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			searchPage.clickCreateCustomer(true, extentedReport);
			customerDetails2 = searchPage.enterCustomerDetails(testData, true, extentedReport, true);
			customerDetails2.get("First Name");
			customerDetails2.get("Last Name");
			Log.message("Contact 2 First Name------>" + customerDetails2.get("First Name"), driver, extentedReport);
			Log.message("Contact 2 Last Name------>" + customerDetails2.get("Last Name"), driver, extentedReport);
			searchPage.confirmCreateCustomer(false, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + customerDetails2.get("First Name")
							+ " " + customerDetails2.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard page",
					"Not Verified Customer Name on Dashboard page", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			// Verify New Quote
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
			Log.softAssertThat(newquotepage.checkInterestedParties(), "Interested parties column is visible",
					"Interested parties column is not visible", driver, extentedReport, true);
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("noInterestedpartiesMsg"), newquotepage),
					"No interested parties have been added--text visible",
					"No interested parties have been added-text has not visible", driver, extentedReport, true);
			newquotepage.clickAddParties(extentedReport, true);
			Log.softAssertThat(
					!newquotepage.uielement.verifyPageElements(Arrays.asList("addInterestsearchBtn"), newquotepage),
					"Search button is not visible before selecting  contact type",
					"Search button is visible before selecting  contact type", driver, extentedReport, true);
			newquotepage.selectContactType(addInterestContactType, extentedReport);

			newquotepage.AIenterLastName(mainCustLastName, extentedReport);

			newquotepage.AIenterFirstName(mainCustFirstName, extentedReport);

			newquotepage.clicksearch(extentedReport);

			newquotepage.checkAddPartySearchResultsPane(extentedReport, true);

			newquotepage.clickselect(extentedReport, true);

			newquotepage.addInterestedPartyType(extentedReport, true);

			// newquotepage.addInterestedPartyTypeToDate(extentedReport, true);

			newquotepage.addInterestedPartyClickSave(extentedReport, true);

			Log.softAssertThat(
					newquotepage.checkaddedInterestedParty(
							mainCustTitle + " " + mainCustFirstName + " " + mainCustLastName),
					mainCustTitle + " " + mainCustFirstName + " " + mainCustLastName + "Added party is visible",
					mainCustTitle + " " + mainCustFirstName + " " + mainCustLastName + " Added party is not visible",
					driver, extentedReport, true);

			newquotepage.click_Deleteicon(extentedReport, true);

			Log.softAssertThat(newquotepage.checkremoveDescription(),
					"Interested Parties Remove description message is displayed : "
							+ newquotepage.INTERESTED_PARTIES_REMOVE_DESC1 + " and "
							+ newquotepage.INTERESTED_PARTIES_REMOVE_DESC2,
					"Interested Parties Remove description message is not displayed", driver, extentedReport, true);

			newquotepage.yesnobuttonVisible(extentedReport, true);
			newquotepage.clickIntrestedpartyPopupCrossIcon(extentedReport);
			WaitUtils.waitForSpinner(driver);
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("addPartyDeleteicon"), newquotepage),
					"Remove intrested party pop up closed on clicking Cross(X) icon",
					"Remove intrested party pop up is not closed on clicking Cross(X) icon", driver, extentedReport,
					true);
			newquotepage.click_Deleteicon(extentedReport, true);
			newquotepage.clicknobutton(extentedReport, true);
			Log.softAssertThat(newquotepage.checkInterestedParties(), "Interested parties column is visible",
					"Interested parties is not visible", driver, extentedReport, true);

			if (!newquotepage.verify_IntPartyName(
					testData.get("Title") + " " + testData.get("First Name") + " " + testData.get("Last Name"),
					extentedReport)) {
				Log.pass("Intersted party has been removed after clicking yes button in remove modal", driver,
						extentedReport, true);
			} else {
				Log.fail("Interested party was not removed after clicking yes button in remove modal", driver,
						extentedReport, true);
			}
			newquotepage.click_Deleteicon(extentedReport, true);
			newquotepage.clickyesbutton(extentedReport, true);
			newquotepage.clickBuy(extentedReport);
			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("New Business Policy Created Successfully in Accepted status", driver, extentedReport,
						true);
				policyDetails.get("PolicyNo");
			}
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			Log.softAssertThat(newquotepage.checkInterestedParties(), "Interested parties column is visible",
					"Interested parties column is not visible", driver, extentedReport, true);
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("noInterestedpartiesMsg"), newquotepage),
					"No interested parties have been added--text visible",
					"No interested parties have been added-text has not visible", driver, extentedReport, true);
			newquotepage.clickAddParties(extentedReport, true);
			Log.softAssertThat(
					!newquotepage.uielement.verifyPageElements(Arrays.asList("addInterestsearchBtn"), newquotepage),
					"Search button is not visible before selecting  contact type",
					"Search button is visible before selecting  contact type", driver, extentedReport, true);
			newquotepage.selectContactType(addInterestContactType, extentedReport);

			newquotepage.AIenterLastName(mainCustLastName, extentedReport);

			newquotepage.AIenterFirstName(mainCustFirstName, extentedReport);

			newquotepage.clicksearch(extentedReport);

			newquotepage.checkAddPartySearchResultsPane(extentedReport, true);

			newquotepage.clickselect(extentedReport, true);

			newquotepage.addInterestedPartyType(extentedReport, true);

			// newquotepage.addInterestedPartyTypeToDate(extentedReport, true);

			newquotepage.addInterestedPartyClickSave(extentedReport, true);

			Log.softAssertThat(
					newquotepage.checkaddedInterestedParty(
							mainCustTitle + " " + mainCustFirstName + " " + mainCustLastName),
					mainCustTitle + " " + mainCustFirstName + " " + mainCustLastName + "Added party is visible",
					mainCustTitle + " " + mainCustFirstName + " " + mainCustLastName + " Added party is not visible",
					driver, extentedReport, true);

			newquotepage.click_Deleteicon(extentedReport, true);

			Log.softAssertThat(newquotepage.checkremoveDescription(),
					"Interested Parties Remove description message is displayed : "
							+ newquotepage.INTERESTED_PARTIES_REMOVE_DESC1 + " and "
							+ newquotepage.INTERESTED_PARTIES_REMOVE_DESC2,
					"Interested Parties Remove description message is not displayed", driver, extentedReport, true);

			newquotepage.yesnobuttonVisible(extentedReport, true);
			newquotepage.clickIntrestedpartyPopupCrossIcon(extentedReport);
			WaitUtils.waitForSpinner(driver);
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("addPartyDeleteicon"), newquotepage),
					"Remove intrested party pop up closed on clicking Cross(X) icon",
					"Remove intrested party pop up is not closed on clicking Cross(X) icon", driver, extentedReport,
					true);
			newquotepage.click_Deleteicon(extentedReport, true);
			newquotepage.clicknobutton(extentedReport, true);
			Log.softAssertThat(newquotepage.checkInterestedParties(), "Interested parties column is visible",
					"Interested parties is not visible", driver, extentedReport, true);

			if (!newquotepage.verify_IntPartyName(
					testData.get("Title") + " " + testData.get("First Name") + " " + testData.get("Last Name"),
					extentedReport)) {
				Log.pass("Intersted party has been removed after clicking yes button in remove modal", driver,
						extentedReport, true);
			} else {
				Log.fail("Interested party was not removed after clicking yes button in remove modal", driver,
						extentedReport, true);
			}

			newquotepage.clickBuyMTA(extentedReport, true);

			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);

			newquotepage.clickConfirmMTA(extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA completed",
					"Failed - Not navigated to customer dashboard page after MTA completed");

			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"MTA transaction is completed successfully - Test case completed",
					"Failed - MTA transaction is not completed - Test case completed", driver, extentedReport, true);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify No Interested Parties message,Add Personal contact as an Interested Party,Add Interested Party Modal field verification,Add Personal contact as an Interested Party,Remove an Interested Party,Remove an Interested Party by selecting 'Yes' option", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_095_097(String browser) throws Exception {

		String tcId = "TC_095_097";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String addInterestContactType = testData.get("AddInterestContactType");
		String brandname = testData.get("Brand Name");
		String Corecover = testData.get("Cover");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			/// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
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
			searchPage.enterCustomerDetails(testData, true, extentedReport);
			String mainCustTitle = testData.get("Title");
			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			// String mainPolicyholderName = mainCustFirstName + " " +
			// mainCustLastName;
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);
			// String PolicyhlderName = mainCustFirstName + " " +
			// mainCustLastName;
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(
							testData.get("Title") + " " + mainCustFirstName + " " + mainCustLastName, extentedReport,
							true),
					"Verified FirstName and LastName on Customer Dashboard page",
					"Not Verified Customer Name on Dashboard page", driver, extentedReport, true);

			homePage = custdashboardpage.clickCompleteButton();
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			searchPage.clickCreateCustomer(true, extentedReport);
			customerDetails2 = searchPage.enterCustomerDetails(testData, true, extentedReport, true);
			customerDetails2.get("First Name");
			customerDetails2.get("Last Name");
			Log.message("Contact 2 First Name------>" + customerDetails2.get("First Name"), driver, extentedReport);
			Log.message("Contact 2 Last Name------>" + customerDetails2.get("Last Name"), driver, extentedReport);
			searchPage.confirmCreateCustomer(false, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + customerDetails2.get("First Name")
							+ " " + customerDetails2.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard page",
					"Not Verified Customer Name on Dashboard page", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			// Verify New Quote
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
			Log.softAssertThat(newquotepage.checkInterestedParties(), "Interested parties column is visible",
					"Interested parties column is not visible", driver, extentedReport, true);
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("noInterestedpartiesMsg"), newquotepage),
					"No interested parties have been added--text visible",
					"No interested parties have been added-text has not visible", driver, extentedReport, true);

			newquotepage.clickAddParties(extentedReport, true);

			newquotepage.selectContactType(addInterestContactType, extentedReport);

			newquotepage.AIenterLastName(mainCustLastName, extentedReport);

			newquotepage.AIenterFirstName(mainCustFirstName, extentedReport);

			newquotepage.clicksearch(extentedReport);

			newquotepage.checkAddPartySearchResultsPane(extentedReport, true);

			newquotepage.clickselect(extentedReport, true);
			Log.softAssertThat(
					!newquotepage.uielement.verifyPageElements(Arrays.asList("addInterestpartySave"), newquotepage),
					"Save button is not visible before selecting  Intrested party type",
					"Save button is  visible before selecting  Intrested party type", driver, extentedReport, true);
			newquotepage.addInterestedPartyType(extentedReport, true);

			// newquotepage.addInterestedPartyTypeToDate(extentedReport, true);
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("addInterestpartySave"), newquotepage),
					"Save button is visible after selecting  Intrested party type",
					"Save button is not  visible after selecting  Intrested party type", driver, extentedReport, true);
			newquotepage.addInterestedPartyClickSave(extentedReport, true);

			Log.softAssertThat(
					newquotepage.checkaddedInterestedParty(
							mainCustTitle + " " + mainCustFirstName + " " + mainCustLastName),
					mainCustTitle + " " + mainCustFirstName + " " + mainCustLastName + "Added party is visible",
					mainCustTitle + " " + mainCustFirstName + " " + mainCustLastName + " Added party is not visible",
					driver, extentedReport, true);

			newquotepage.clickBuy(extentedReport);
			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("New Business Policy Created Successfully in Accepted status", driver, extentedReport,
						true);
				policyDetails.get("PolicyNo");
			}
			Log.softAssertThat(
					custdashboardpage.verifyInterestedpartiesOnCustomerDashboard(mainCustTitle, mainCustFirstName,
							mainCustLastName),
					"Added interested party displayed on customer dashboad",
					"Added interested party is not displayed on customer dashboad", driver, extentedReport, true);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Create New Business 3* Policy - Annual Payment and search the created NB policy through normal policy search", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_270_278_282(String browser) throws Exception {

		// Get the web driver instance
		String tcId = "TC_270_278_282";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String title = testData.get("Title");
		String brandname = testData.get("Brand Name");
		String Corecover = testData.get("Cover");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		String termandcondition = testData.get("TermAndCondition");
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
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

			newquotepage.verifyEditTermAndCondition_GQP(extentedReport, termandcondition);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("New Business Policy Created Successfully in Accepted status", driver, extentedReport,
						true);
				policyDetails.get("PolicyNo");

				homePage = custdashboardpage.clickCompleteButton();
				Log.softAssertThat(homePage.verifyHomePage(),
						"Successfully navigated to Home Page after clicking complete button in customer dashboard page",
						"Failed to navigate to Home Page after clicking complete button in customer dashboard page",
						driver, extentedReport, true);

				// Click on Take Call link
				homePage.clickTakeCall(extentedReport);
				homePage.clickMyBrands(brandname, extentedReport);
				// Search with Valid Policy Number
				searchPage.searchValidPolicy(policyDetails.get("PolicyNo"), true, extentedReport);
				CustDashboardPage custdashboardPage = searchPage.selectPolicy_from_SearchPage(true, extentedReport);
				Log.message("Navigated to Cutomer Dashboard after selecting policy from search page", driver,
						extentedReport, true);

				// Verifying Customer Details
				custdashboardPage.clickPassVerification(extentedReport, true);
				custdashboardPage.verifyCustomerDashboardPage();

				if (custdashboardPage.verifyContactName(
						title + " " + custDetails.get("First Name") + " " + custDetails.get("Last Name")))
					Log.pass("Verified FirstName and LastName on Customer Dashboard : " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), driver, extentedReport, true);
				else
					Log.fail("Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			} else {
				Log.fail("Failed - NB Policy Created in " + policyDetails.get("Status").toString() + " status", driver,
						extentedReport, true);
			}

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_526(String browser) throws Exception {

		String tcId = "TC_526";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);
			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);
			homePage = custdashboardpage.clickCompleteButton();
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			searchPage.clickCreateCustomer(true, extentedReport);
			customerDetails2 = searchPage.enterCustomerDetails(testData, true, extentedReport, true);
			customerDetails2.get("First Name");
			customerDetails2.get("Last Name");
			Log.message("Contact 2 First Name------>" + customerDetails2.get("First Name"), driver, extentedReport);
			Log.message("Contact 2 Last Name------>" + customerDetails2.get("Last Name"), driver, extentedReport);
			searchPage.confirmCreateCustomer(false, extentedReport);
			homePage = custdashboardpage.clickCompleteButton();
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			searchPage.enterLastName(mainCustLastName, extentedReport);
			searchPage.enterFirstName(mainCustFirstName, extentedReport);
			searchPage.clickSearch(extentedReport);
			searchPage.clickSearchedContactPolicy("contact", "", mainCustLastName, true, extentedReport);
			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			newquotepage.selectPaymentPlan(testData.get("Payment Plan"), extentedReport);
			newquotepage.selectPaymentMethod(testData.get("Payment Method"), extentedReport);
			// Payor Change
			newquotepage.clickChangePayorButton(extentedReport, true);
			String payorname = newquotepage.selectChangePayor(customerDetails2.get("Last Name"),
					customerDetails2.get("First Name"), "GU111PZ", extentedReport, true);
			if (newquotepage.verifyPayorName(payorname)) {
				Log.pass("The user is able to change the payor of the policy, once the quote reached Acceptance page : "
						+ payorname, driver, extentedReport, true);
			} else {
				Log.fail(
						"The user is not able to change the payor of the policy, once the quote reached Acceptance page : "
								+ payorname,
						driver, extentedReport, true);
			}

			newquotepage.selectPayment(testData, true, extentedReport);

			newquotepage.tickSpokenDirectlyNewPayorCheckbox(extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("New Business Policy Created Successfully in Accepted status", driver, extentedReport,
						true);
				policyDetails.get("PolicyNo");
			}

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_535(String browser) throws Exception {

		String tcId = "TC_535";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);
			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);
			/*
			 * homePage = custdashboardpage.clickCompleteButton();
			 * homePage.clickTakeCall(extentedReport);
			 * homePage.clickMyBrands(brandname, extentedReport);
			 * searchPage.clickCreateCustomer(true, extentedReport);
			 * customerDetails2 = searchPage.enterCustomerDetails(testData,
			 * true, extentedReport, true); customerDetails2.get("First Name");
			 * customerDetails2.get("Last Name");
			 * Log.message("Contact 2 First Name------>" +
			 * customerDetails2.get("First Name"), driver, extentedReport);
			 * Log.message("Contact 2 Last Name------>" +
			 * customerDetails2.get("Last Name"), driver, extentedReport);
			 * searchPage.confirmCreateCustomer(false, extentedReport); homePage
			 * = custdashboardpage.clickCompleteButton(); // Click on Take Call
			 * link homePage.clickTakeCall(extentedReport);
			 * homePage.clickMyBrands(brandname, extentedReport);
			 * searchPage.enterLastName(mainCustLastName, extentedReport);
			 * searchPage.enterFirstName(mainCustFirstName, extentedReport);
			 * searchPage.clickSearch(extentedReport);
			 * searchPage.clickSearchedContactPolicy("contact", "",
			 * mainCustLastName, true, extentedReport);
			 */
			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			newquotepage.selectPaymentPlan(testData.get("Payment Plan"), extentedReport);
			newquotepage.selectPaymentMethod(testData.get("Payment Method"), extentedReport);
			// Payor Change
			newquotepage.clickChangePayorButton(extentedReport, true);
			newquotepage.enterChangePayorDetail(testData.get("Last Name"), testData.get("First Name"), "GU111PZ",
					extentedReport, true);
			newquotepage.clickSearchBtn_ChangePayor(extentedReport);

			newquotepage.clickCreateNewPayor(extentedReport);
			String payorname = newquotepage.enterNewPayorDetails(testData, false, extentedReport);

			if (newquotepage.verifyPayorName(payorname)) {
				Log.pass("The user is able to change the payor of the policy, once the quote reached Acceptance page : "
						+ payorname, driver, extentedReport, true);
			} else {
				Log.fail(
						"The user is not able to change the payor of the policy, once the quote reached Acceptance page : "
								+ payorname,
						driver, extentedReport, true);
			}

			newquotepage.selectPayment(testData, true, extentedReport);

			newquotepage.tickSpokenDirectlyNewPayorCheckbox(extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("New Business Policy Created Successfully in Accepted status", driver, extentedReport,
						true);
				policyDetails.get("PolicyNo");
			}

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_540_664_665(String browser) throws Exception {

		String tcId = "TC_540_664_665";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);
			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);
			homePage = custdashboardpage.clickCompleteButton();
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			searchPage.clickCreateCustomer(true, extentedReport);
			customerDetails2 = searchPage.enterCustomerDetails(testData, true, extentedReport, true);
			customerDetails2.get("First Name");
			customerDetails2.get("Last Name");
			Log.message("Contact 2 First Name------>" + customerDetails2.get("First Name"), driver, extentedReport);
			Log.message("Contact 2 Last Name------>" + customerDetails2.get("Last Name"), driver, extentedReport);
			searchPage.confirmCreateCustomer(false, extentedReport);
			homePage = custdashboardpage.clickCompleteButton();
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			searchPage.enterLastName(mainCustLastName, extentedReport);
			searchPage.enterFirstName(mainCustFirstName, extentedReport);
			searchPage.clickSearch(extentedReport);
			searchPage.clickSearchedContactPolicy("contact", "", mainCustLastName, true, extentedReport);
			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			newquotepage.selectPaymentPlan(testData.get("Payment Plan"), extentedReport);
			newquotepage.selectPaymentMethod(testData.get("Payment Method"), extentedReport);
			// Payor Change
			newquotepage.clickChangePayorButton(extentedReport, true);
			String payorname = newquotepage.selectChangePayor(customerDetails2.get("Last Name"),
					customerDetails2.get("First Name"), "GU111PZ", extentedReport, true);
			if (newquotepage.verifyPayorName(payorname)) {
				Log.pass("The user is able to change the payor of the policy, once the quote reached Acceptance page : "
						+ payorname, driver, extentedReport, true);
			} else {
				Log.fail(
						"The user is not able to change the payor of the policy, once the quote reached Acceptance page : "
								+ payorname,
						driver, extentedReport, true);
			}

			newquotepage.selectPayment(testData, true, extentedReport);
			Log.softAssertThat(
					!newquotepage.uielement.verifyPageElements(Arrays.asList("btnAcceptPaymentWB"), newquotepage),
					"User not able to accept policy before clicking on Confirm payor checkbox",
					"User is able to accept policy before clicking on Confirm payor checkbox", driver, extentedReport,
					true);
			newquotepage.tickSpokenDirectlyNewPayorCheckbox(extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("New Business Policy Created Successfully in Accepted status", driver, extentedReport,
						true);
				policyDetails.get("PolicyNo");
			}

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_667(String browser) throws Exception {

		String tcId = "TC_667";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);
			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			String paymentPlan = testData.get("Payment Plan");
			String paymentMethod = testData.get("Payment Method");

			newquotepage.clickNextButtonCustomerPref(extentedReport);
			newquotepage.clickNextButtonTandC(extentedReport);

			newquotepage.selectPaymentPlan(paymentPlan, extentedReport);
			newquotepage.selectPaymentMethod(paymentMethod.toString(), extentedReport);

			if (newquotepage.verifyNoAccountDetailsErrorMessage("No Account Details Exists", extentedReport)) {
				newquotepage.selectPreferredPaymentDay("12", extentedReport, true);
				Log.softAssertThat(
						newquotepage.verifyPrefferedDayErrorMessageForNoAccount(
								"The bankAccountIdentity or creditCardIdentity is mandatory for payment methods 'Card', 'Direct debit' or 'Premium Finance'."),
						"Error message displayed on selecting preffered payment date before entering ACCnt details",
						"Error message is not displayed on selecting preffered payment date before entering ACCnt details",
						driver, extentedReport, true);

				String accNo = testData.get("Account Number");
				String sortCode = testData.get("Sort Code");
				String accName = testData.containsKey("Account Name") ? testData.get("Account Name")
						: Utils.getRandomCharacters("alpha", 5);
				String branch = (testData.containsKey("Bank Branch") && !testData.get("Bank Branch").equals(""))
						? testData.get("Bank Branch") : "Bootle Centre, Santander, Liverpool";

				newquotepage.clickAddAccountDetails(extentedReport);
				newquotepage.checkBankAccount(accNo, sortCode, extentedReport);
				newquotepage.enterAccountName(accName, extentedReport);
				newquotepage.saveAccountDetails(extentedReport);
				Log.message("Entered details for New Bank Account", driver, extentedReport, true);
				newquotepage.selectAccount(sortCode, branch, accName, accNo, extentedReport, true);
			} else {
				newquotepage.selectAccount(extentedReport);
			}
			newquotepage.tickCustomerAgreesCheckbox(extentedReport);
			WaitUtils.waitForSpinner(driver);

			newquotepage.selectPreferredPaymentDay("13", extentedReport, true);
			Log.softAssertThat(newquotepage.verifyPrefferedDayInSchedule("13"),
					" preffered payment day updated in payment schedule",
					"preffered payment day is not updated in payment schedule", driver, extentedReport, true);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("New Business Policy Created Successfully in Accepted status", driver, extentedReport,
						true);
				policyDetails.get("PolicyNo");
			}

			custdashboardpage.clickTab("Finance", extentedReport, true);
			custdashboardpage.TakePaymentInFinanceTabforMonths(2, extentedReport);
			custdashboardpage.clickTab("Policy Details", extentedReport, true);
			String DDtotalPaid = custdashboardpage.getTotalPaid(extentedReport);
			homePage = custdashboardpage.clickCompleteButton();
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			searchPage.searchValidPolicy(policyDetails.get("PolicyNo"), true, extentedReport);
			CustDashboardPage custdashboardPage = searchPage.selectPolicy_from_SearchPage(true, extentedReport);
			Log.message("Navigated to Cutomer Dashboard after selecting policy from search page", driver,
					extentedReport, true);

			// Verifying Customer Details
			custdashboardPage.clickPassVerification(extentedReport, true);
			custdashboardPage.verifyCustomerDashboardPage();
			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			custdashboardpage.clickContinueQuote(true, extentedReport);

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
			String paymentPlan1 = "Annual Payment Plan";
			String paymentMethod1 = "Card";

			newquotepage.clickNextButtonCustomerPref(extentedReport);
			newquotepage.clickNextButtonTandC(extentedReport);

			newquotepage.selectPaymentPlan(paymentPlan1, extentedReport);
			newquotepage.selectPaymentMethod(paymentMethod1.toString(), extentedReport);
			WaitUtils.waitForSpinner(driver);
			testData.replace("Payment Plan", "Direct Debit Standard 12", "Annual Payment Plan");

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			String annualTotalPaid = custdashboardpage.getTotalPaid(extentedReport);
			System.out.println(Double.parseDouble(annualTotalPaid.substring(2)));
			System.out.println(Double.parseDouble(DDtotalPaid.substring(2)));
			Boolean totalpaid = Double.parseDouble(annualTotalPaid.substring(2)) > Double
					.parseDouble(DDtotalPaid.substring(2));
			Log.softAssertThat((totalpaid), "Annual total paid is more than DD 2 months total paid-Test Passed",
					"Annual total paid is more than DD 2 months total paid-Test Fail", driver, extentedReport, true);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_680(String browser) throws Exception {

		String tcId = "TC_680";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);
			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			String paymentPlan = testData.get("Payment Plan");
			String paymentMethod = testData.get("Payment Method");

			newquotepage.clickNextButtonCustomerPref(extentedReport);
			newquotepage.clickNextButtonTandC(extentedReport);

			newquotepage.selectPaymentPlan(paymentPlan, extentedReport);
			newquotepage.selectPaymentMethod(paymentMethod.toString(), extentedReport);

			if (newquotepage.verifyNoAccountDetailsErrorMessage("No Account Details Exists", extentedReport)) {

				String accNo = testData.get("Account Number");
				String sortCode = testData.get("Sort Code");
				String accName = testData.containsKey("Account Name") ? testData.get("Account Name")
						: Utils.getRandomCharacters("alpha", 5);

				newquotepage.clickAddAccountDetails(extentedReport);
				newquotepage.checkBankAccount(accNo, sortCode, extentedReport);

				newquotepage.enterAccountName(accName, extentedReport);
				newquotepage.saveAccountDetails(extentedReport);
				Log.softAssertThat(
						newquotepage.uielement.verifyPageElements(Arrays.asList("sortCodeErrorMsg"), newquotepage),
						"Error Msg displayed for invalid sort code-Test Pass",
						"Error Msg is not displayed for invalid sort code-Test Fail", driver, extentedReport, true);
			}
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_681_688(String browser) throws Exception {

		String tcId = "TC_681";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);

			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("New Business Policy Created Successfully in Accepted status", driver, extentedReport,
						true);
				policyDetails.get("PolicyNo");
			}

			homePage = custdashboardpage.clickCompleteButton();
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			searchPage.searchValidPolicy(policyDetails.get("PolicyNo"), true, extentedReport);
			CustDashboardPage custdashboardPage = searchPage.selectPolicy_from_SearchPage(true, extentedReport);
			Log.message("Navigated to Cutomer Dashboard after selecting policy from search page", driver,
					extentedReport, true);

			// Verifying Customer Details
			custdashboardPage.clickPassVerification(extentedReport, true);
			custdashboardPage.verifyCustomerDashboardPage();
			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			custdashboardpage.clickContinueQuote(true, extentedReport);

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
			String paymentPlan = testData.get("Payment Plan");
			String paymentMethod = testData.get("Payment Method");

			newquotepage.clickNextButtonCustomerPref(extentedReport);
			newquotepage.clickNextButtonTandC(extentedReport);

			newquotepage.selectPaymentPlan(paymentPlan, extentedReport);
			newquotepage.selectPaymentMethod(paymentMethod.toString(), extentedReport);

			Log.softAssertThat(
					newquotepage.verifyAccountDetails(testData.get("Sort Code"), "Bootle Centre, Santander, Liverpool",
							mainCustFirstName + " " + mainCustLastName, testData.get("Account Number"), extentedReport),
					"Existing bank Account details are correctly Listed-Test Passed",
					"Existing bank Account details are not Listed correctly-Test Failed", driver, extentedReport, true);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_685(String browser) throws Exception {

		String tcId = "TC_685";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);

			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			String paymentPlan = testData.get("Payment Plan");
			String paymentMethod = testData.get("Payment Method");

			newquotepage.clickNextButtonCustomerPref(extentedReport);
			newquotepage.clickNextButtonTandC(extentedReport);

			newquotepage.selectPaymentPlan(paymentPlan, extentedReport);
			newquotepage.selectPaymentMethod(paymentMethod.toString(), extentedReport);

			if (newquotepage.verifyNoAccountDetailsErrorMessage("No Account Details Exists", extentedReport)) {
				String accNo = testData.get("Account Number");
				String sortCode = testData.get("Sort Code");
				String accName = testData.containsKey("Account Name") ? testData.get("Account Name")
						: Utils.getRandomCharacters("alpha", 5);

				newquotepage.clickAddAccountDetails(extentedReport);
				newquotepage.checkBankAccount(accNo, sortCode, extentedReport);
				newquotepage.enterAccountName(accName, extentedReport);
				newquotepage.saveAccountDetails(extentedReport);
				Log.message("Entered details for New Bank Account", driver, extentedReport, true);

			}
			String accNo1 = "25657377";
			String sortCode1 = "773317";
			String accName1 = Utils.getRandomCharacters("alpha", 5);
			String branch1 = "Coseley, Lloyds Bank PLC";

			newquotepage.clickAddAccountDetails(extentedReport);
			newquotepage.enterAccountNumber(accNo1);
			newquotepage.enterSortCode(sortCode1);
			// newquotepage.checkBankAccount(accNo1, sortCode1, extentedReport);
			newquotepage.enterAccountName(accName1, extentedReport);

			WaitUtils.waitForSpinner(driver);
			newquotepage.saveAccountDetails(extentedReport);
			Log.message("Entered details for New Bank Account", driver, extentedReport, true);
			String xpathAccSelect = "//table[@summary='BankDetails']//tr[.//span[text()='${SORT_CODE}']][.//span[text()='${BRANCH}']][.//span[text()='${ACC_NAME}']][.//span[text()='${ACC_NO}']]//button"
					.replaceAll("\\$\\{SORT_CODE\\}", sortCode1).replaceAll("\\$\\{BRANCH\\}", branch1)
					.replaceAll("\\$\\{ACC_NAME\\}", accName1).replaceAll("\\$\\{ACC_NO\\}", accNo1);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-250)");
			driver.findElement(By.xpath(xpathAccSelect)).click();
			// newquotepage.selectAccount(sortCode1, branch1, accName1, accNo1,
			// extentedReport, true);

			Log.softAssertThat(
					newquotepage.verifyAccountSelection(sortCode1, branch1, accName1, accNo1, extentedReport),
					"Existing bank Account details are correctly Listed-Test Passed",
					"Existing bank Account details are not Listed correctly-Test Failed", driver, extentedReport, true);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("New Business Policy Created Successfully in Accepted status", driver, extentedReport,
						true);
				policyDetails.get("PolicyNo");
			}

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_689(String browser) throws Exception {

		String tcId = "TC_689";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);

			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			newquotepage.selectPayment(testData, true, extentedReport);
			newquotepage.cancelPayment(extentedReport);
			// custdashboardpage = newquotepage.confirmPayment(testData,
			// extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Quoted")) {

				Log.message("Policy Created Successfully in Quoted status", driver, extentedReport, true);
				policyDetails.get("PolicyNo");
			}

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_690(String browser) throws Exception {

		String tcId = "TC_690";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);

			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			newquotepage.selectPayment(testData, true, extentedReport);
			WaitUtils.waitForSpinner(driver);
			newquotepage.clickCutomerAgreeButton(extentedReport);
			WaitUtils.waitForSpinner(driver);
			newquotepage.cancelPayment(extentedReport);

			// custdashboardpage = newquotepage.confirmPayment(testData,
			// extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Quoted")) {

				Log.message("Policy Created Successfully in Quoted status", driver, extentedReport, true);
				policyDetails.get("PolicyNo");
			}

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_692(String browser) throws Exception {

		String tcId = "TC_692";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);

			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("Policy Created Successfully in Accepted status", driver, extentedReport, true);
				policyDetails.get("PolicyNo");
			}

			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);

			// remove covers
			String[] coversToRemove = testData.get("coverToSelect").toString().split(",");
			for (int i = 0; i < coversToRemove.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToRemove[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToRemove[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToRemove[i].split("_");
				if (BoolVal != false) {
					Log.pass(
							coverType[2] + " " + coverType[0] + "_" + coverType[1] + " cover type removed successfully",
							driver, extentedReport, true);
				} else {
					Log.fail("Failed to " + coverType[2] + " " + coverType[0] + "_" + coverType[1]
							+ " cover not removed", driver, extentedReport, true);
				}

				// Click recalculate button
				newquotepage.clickReCalculate(extentedReport);

			}

			// Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);

			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);

			newquotepage.cancelPayment(extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA completed",
					"Failed - Not navigated to customer dashboard page after MTA completed");

			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Inactive";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"MTA transaction is completed successfully - Test case completed",
					"Failed - MTA transaction is not completed - Test case completed", driver, extentedReport, true);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_693_699(String browser) throws Exception {

		String tcId = "TC_693_699";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);

			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			newquotepage.selectPayment(testData, true, extentedReport);
			WaitUtils.waitForSpinner(driver);
			newquotepage.clickCutomerAgreeButton(extentedReport);
			WaitUtils.waitForSpinner(driver);
			Log.softAssertThat(
					!newquotepage.uielement.verifyPageElements(Arrays.asList("btnAcceptPaymentWB"), newquotepage),
					"Accept button is not displayed before checking customer gurantee checkbox",
					"Accept button is displayed before checking customer gurantee checkbox", driver, extentedReport,
					true);

			newquotepage.clickCutomerAgreeButton(extentedReport);
			WaitUtils.waitForSpinner(driver);
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("btnAcceptPaymentWB"), newquotepage),
					"Accept button is displayed after checking customer gurantee checkbox",
					"Accept button is not displayed before checking customer gurantee checkbox", driver, extentedReport,
					true);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("Policy Created Successfully in Accepted status", driver, extentedReport, true);
				policyDetails.get("PolicyNo");
			}

			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);

			// remove covers
			String[] coversToRemove = testData.get("coverToSelect").toString().split(",");
			for (int i = 0; i < coversToRemove.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToRemove[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToRemove[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToRemove[i].split("_");
				if (BoolVal != false) {
					Log.pass(
							coverType[2] + " " + coverType[0] + "_" + coverType[1] + " cover type removed successfully",
							driver, extentedReport, true);
				} else {
					Log.fail("Failed to " + coverType[2] + " " + coverType[0] + "_" + coverType[1]
							+ " cover not removed", driver, extentedReport, true);
				}

				// Click recalculate button
				newquotepage.clickReCalculate(extentedReport);

			}

			// Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);

			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);

			newquotepage.saveQuote(extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA completed",
					"Failed - Not navigated to customer dashboard page after MTA completed");

			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Inactive";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"MTA transaction is completed successfully - Test case completed",
					"Failed - MTA transaction is not completed - Test case completed", driver, extentedReport, true);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_707(String browser) throws Exception {

		String tcId = "TC_707";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);

			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("Policy Created Successfully in Accepted status", driver, extentedReport, true);
				policyDetails.get("PolicyNo");
			}

			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);

			// remove covers
			String[] coversToRemove = testData.get("coverToSelect").toString().split(",");
			for (int i = 0; i < coversToRemove.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToRemove[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToRemove[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToRemove[i].split("_");
				if (BoolVal != false) {
					Log.pass(
							coverType[2] + " " + coverType[0] + "_" + coverType[1] + " cover type removed successfully",
							driver, extentedReport, true);
				} else {
					Log.fail("Failed to " + coverType[2] + " " + coverType[0] + "_" + coverType[1]
							+ " cover not removed", driver, extentedReport, true);
				}

				// Click recalculate button
				newquotepage.clickReCalculate(extentedReport);

			}

			// Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);

			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);

			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,400)");

			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(
							Arrays.asList("radSpreadOverInstallments", "radSingleBillCard"), newquotepage),
					"Radio option o Spread over Instalments o Single Bill - Card present",
					"Radio option o Spread over Instalments o Single Bill - Card are not present");

			Log.softAssertThat(newquotepage.verifyMTAAdjustmentRadioButtons(true, extentedReport, true),
					"Radio option o Spread over Instalments o Single Bill - Card below MTA adjustment is enabled and allow to select when making MTA payment.",
					"Radio option o Spread over Instalments o Single Bill - Card below MTA adjustment is not enabled and allow to select when making MTA payment.");

			Log.softAssertThat(
					newquotepage.verifyCalculatedPremium("Spread over Instalments", policyDetails, extentedReport,
							true),
					"Spread over installment updated in paymentschedule",
					"Spread over installment is not updated in paymentschedule");

			newquotepage.selectSingleBillCardRadioButton(extentedReport);
			WaitUtils.waitForSpinner(driver);

			Log.softAssertThat(
					newquotepage.verifyCalculatedPremium("Single Bill-Card", policyDetails, extentedReport, true),
					"Spread over installment updated in paymentschedule",
					"Spread over installment is not updated in paymentschedule");

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA completed",
					"Failed - Not navigated to customer dashboard page after MTA completed");

			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Inactive";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"MTA transaction is completed successfully - Test case completed",
					"Failed - MTA transaction is not completed - Test case completed", driver, extentedReport, true);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_766_768_787(String browser) throws Exception {

		String tcId = "TC_766_768_787 ";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);

			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("Policy Created Successfully in Accepted status", driver, extentedReport, true);
				policyDetails.get("PolicyNo");
			}

			custdashboardpage.selectBAfromManagePolicyTab(extentedReport, true);
			custdashboardpage.enterBADetails(testData, extentedReport, true);
			newquotepage.selectAvalilableBillingAdjReasons("Change Payment Plan", extentedReport, true);
			String paymentPlan1 = "Annual Payment Plan";
			String paymentMethod1 = "Card";

			newquotepage.selectPaymentPlan(paymentPlan1, extentedReport);
			newquotepage.selectPaymentMethod(paymentMethod1.toString(), extentedReport);
			WaitUtils.waitForSpinner(driver);
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("panelCardDetailSection", "btnTakePayment"),
							newquotepage),
					"Card Details section displayed after changing payment method and plan",
					"Card Details section displayed after changing payment method and plan", driver, extentedReport,
					true);

			testData.replace("Payment Plan", "Direct Debit Standard 12", "Annual Payment Plan");

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_769_770(String browser) throws Exception {

		String tcId = "TC_769_770";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);

			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("Policy Created Successfully in Accepted status", driver, extentedReport, true);
				policyDetails.get("PolicyNo");
			}

			custdashboardpage.selectBAfromManagePolicyTab(extentedReport, true);
			custdashboardpage.enterBADetails(testData, extentedReport, true);

			newquotepage.cancelPayment(extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails1 = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails1.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("Policy Created Successfully in Accepted status", driver, extentedReport, true);
				policyDetails1.get("PolicyNo");
			}
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_771_772(String browser) throws Exception {

		String tcId = "TC_771_772";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);

			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("Policy Created Successfully in Accepted status", driver, extentedReport, true);
				policyDetails.get("PolicyNo");
			}

			custdashboardpage.selectBAfromManagePolicyTab(extentedReport, true);
			custdashboardpage.enterBADetails(testData, extentedReport, true);
			newquotepage.selectAvalilableBillingAdjReasons("Change Payment Plan", extentedReport, true);
			WaitUtils.waitForSpinner(driver);
			driver.findElement(By.cssSelector("button[id*='C2__BUT_7499E8C9DD09DCA41348358']")).click();

			Log.message("Clicked on 'Cancel' payment button", extentedReport);
			WaitUtils.waitForSpinner(driver);
			driver.findElement(By.cssSelector("button[id*='C2__BUT_FD051A198CB3861E3821012']")).click();
			Log.message("Clicked on 'No' in cancel payment popup", extentedReport);

			Log.softAssertThat(newquotepage.verifyTabTitles("acceptance", true, extentedReport),
					"User remains on same page-Pass ", "User doesn't remains on same page-fail");

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

	@Test(description = "Check that the user is able to change the payor of the policy, once the quote reaches the summary page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider", priority = 1)
	public void TC_809(String browser) throws Exception {

		String tcId = "TC_809";
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

			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(),
					"Successfully logged into SIAAS Home Page UserName : " + userName + ", Password : " + password,
					"Not logged in to Home page", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			searchPage.clickCreateCustomer(true, extentedReport);
			testData = searchPage.enterCustomerDetails(testData, true, extentedReport);

			String mainCustFirstName = testData.get("First Name");
			String mainCustLastName = testData.get("Last Name");
			Log.message("Contact 1 First Name------>" + mainCustFirstName, driver, extentedReport);
			Log.message("Contact 1 Last Name------>" + mainCustLastName, driver, extentedReport);

			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(false, extentedReport);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(false, extentedReport);
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
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

			// Check for the customerdashboard page tabs
			custdashboardpage.verify_ExpandCollapse_Custdashboard(extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.message("Policy Created Successfully in Accepted status", driver, extentedReport, true);
				policyDetails.get("PolicyNo");
			}

			custdashboardpage.selectSuspendBillingfromManagePolicy(extentedReport, true);
			
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("selectPreferredPaymentDay","totalPaid","totalPaid","totalBalance"), newquotepage),
					"Navigated to Customer Dashboard page", "Not navigated to Customer Dashboard page", driver,
					extentedReport, true);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.close();
		}
	}

}
