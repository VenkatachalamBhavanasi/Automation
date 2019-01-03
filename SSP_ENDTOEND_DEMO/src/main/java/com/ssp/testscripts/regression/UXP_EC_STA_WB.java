package com.ssp.testscripts.regression;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import com.ssp.support.*;


@Listeners(EmailReport.class)
public class UXP_EC_STA_WB extends BaseTest {

	private String webSite;
	public String monthlywebSite;

	String firstName = "";
	String lastName = "";
	String quoteDescription1 = "TestDescription 1";
	String quoteDescription2 = "TestDescription 2";
	String quoteDescription3 = "TestDescription 3";

	@BeforeMethod(alwaysRun = true)
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
				testCaseId + " - " + testDesc + " [" + browserwithos + "]", test, "UXP-EC-ASDA");
	}

	public HashMap<String, String> getTestData(String testcaseId) {
		String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
		String className = "UXP_EC_STA_" + env + "_WB";
		return DataUtils.testDatabyID(testcaseId, className);
	}


	@Test(groups = "Business_Scenario", description = "To verify Short-Term Adjustment(STA) is available or not", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_001(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_001";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {
				//custdashboardpage.clickManagePolicy(extentedReport);
				if (custdashboardpage.VerifySTAfromManagePolicyTab(extentedReport, true)) {

					Log.pass("STA option is displayed under Manage policy sub menu as expected", driver, extentedReport, true);
				} else {
					Log.fail("STA option isn't displayed under Manage policy sub menu unexpectedly",
							driver, extentedReport, true);
				}

			} else {
				Log.fail("Failed - NB Policy Created in " + policyDetails.get("Status").toString()
						+ " status, unable to proceed with renewal transaction", driver, extentedReport, true);
			}

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}

	@Test(groups = "Business_Scenario", description = "To verify Short-Term Adjustment(STA) prompt is available or not", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_002(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_002";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {
				//custdashboardpage.clickManagePolicy(extentedReport);
				if (custdashboardpage.VerifySTAPrompt(extentedReport, true)) {

					Log.pass("STA prompt is displayed under Manage policy sub menu as expected", driver, extentedReport, true);
				} else {
					Log.fail("STA prompt isn't displayed under Manage policy sub menu unexpectedly",
							driver, extentedReport, true);
				}

			} else {
				Log.fail("Failed - NB Policy Created in " + policyDetails.get("Status").toString()
						+ " status, unable to proceed with renewal transaction", driver, extentedReport, true);
			}

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}

	@Test(groups = "Business_Scenario", description = "To verify UI of the Short-Term Adjustment prompt", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_003(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_003";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {
				//custdashboardpage.clickManagePolicy(extentedReport);
				if (custdashboardpage.VerifyFieldsSTAPrompt(extentedReport, true)) {

					Log.pass("Expected fields are displayed in STA prompt correctly", driver, extentedReport, true);
				} else {
					Log.fail("Expected fields aren't displayed in STA prompt unexpectedly",
							driver, extentedReport, true);
				}

			} else {
				Log.fail("Failed - NB Policy Created in " + policyDetails.get("Status").toString()
						+ " status, unable to proceed with STA transaction", driver, extentedReport, true);
			}

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if user able to do STA on active policy", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_004_081(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_004_081";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Verify PolicyCover Presentation Screen
			Log.softAssertThat(newquotepage.uielement.verifyPageElements(Arrays.asList("btnAddPolicyHolder"), newquotepage),
					"Navigate to 'Policy Adjustment(PolicyCover Presentation Screen)' details page - Test case completed",
					"Not Navigate to 'Policy Adjustment(PolicyCover Presentation Screen)' details page - Test case completed", driver, extentedReport, true);
			
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if User can abort STA process by clicking cancel and close button", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_005_006(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_005_006";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail in popup
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetailsInPopup(testData, extentedReport, true);
			
			//Click cancel button
			custdashboardpage.clickCancelBtnInStaPopup("cancel", extentedReport);
			
			// Verify STA Prompt
			Log.softAssertThat(!custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"Short-Term Adjustment(STA) prompt is closed after clicking cancel button", 
					"Short-Term Adjustment(STA) prompt is not closed after clicking cancel button", driver, extentedReport, true);

			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Customer dashboard is displayed after cancelling the STA prompt", 
					"Customer dashboard is not displayed after cancelling the STA prompt", driver, extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed",
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);
			
			custdashboardpage.enterSTADetailsInPopup(testData, extentedReport, true);

			// Click close button
			custdashboardpage.clickCancelBtnInStaPopup("close", extentedReport);

			// Verify STA Prompt
			Log.softAssertThat(
					!custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"Short-Term Adjustment(STA) prompt is closed after clicking close button", "Short-Term Adjustment(STA) prompt is not closed after clicking close button",
					driver, extentedReport, true);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Customer dashboard is displayed after closing the STA prompt - Test case completed",
					"Customer dashboard is not displayed after closing the STA prompt - Test case completed", driver, extentedReport, true);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if User able to edit 'Buildings Cover' using 'Edit' buttons.", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_012(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_012";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Enter Excess value
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");
		
			boolean BoolVal = false;
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);
			String[] coverType = coversToAdd[0].split("_");

			Log.softAssertThat(BoolVal,
					"Same 'Excess(Â£)' value is displayed under building section for Excess field of the cover type : "
							+ coverType[1] + " " + coverType[0],
					"Same 'Excess(Â£)' value is not displayed under building section for Excess fieldof the cover type : "
							+ coverType[1] + " " + coverType[0],
					driver, extentedReport, true);

			Log.softAssertThat(newquotepage.uielement.verifyPageElements(Arrays.asList("btnReCalculate"), newquotepage),
					"Re-calculate button is displayed at the bottom of the Policy Adjustment page - Test case completed",
					"Re-calculate button is not displayed at the bottom of the Policy Adjustment page - Test case completed", driver,
					extentedReport, true);
			

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if User able to add Accidental Damage through Editing the Building cover", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_013(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_013";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Enter Excess value for Building
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");

			boolean BoolVal = false;
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);
			newquotepage.clickAccidentalCheckbox("Buildings");
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);
			String[] coverType = coversToAdd[0].split("_");

			Log.softAssertThat(BoolVal, "Excess Value is entered for cover type : " + coverType[0],
					"Excess Value is entered for cover type : " + coverType[0], driver, extentedReport, true);

			// Accidental Damage excess value
			String ins_RowtoInteract2 = newquotepage.SelectInsuranceItem(coversToAdd[1], true, extentedReport);
			String[] accidental = coversToAdd[1].split("_");
			String accidentalExcess = newquotepage.getExcessValue(ins_RowtoInteract2, accidental[0], testData).toString();
			
			Log.softAssertThat(accidentalExcess.equals(testData.get("BLD_ExcessAmt").toString()),
					"Same Excess Value is displayed for Accidental Damage which was entered for buildings",
					"Same Excess Value is not displayed for Accidental Damage which was entered for buildings", driver,
					extentedReport, true);

			Log.softAssertThat(newquotepage.uielement.verifyPageElements(Arrays.asList("btnReCalculate"), newquotepage),
					"Re-calculate button is displayed at the bottom of the Policy Adjustment page - Test case completed",
					"Re-calculate button is not displayed at the bottom of the Policy Adjustment page - Test case completed", driver,
					extentedReport, true);
			

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if User able to edit 'Contents cover' using 'Edit' buttons", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_017(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_017";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Enter Excess value
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");

		
			boolean BoolVal = false;
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);
			//newquotepage.clickBuildingsEditButton("Contents", false);
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);
			String[] coverType = coversToAdd[0].split("_");

			Log.softAssertThat(BoolVal,
					"Same 'Excess(Â£)' value is displayed under Contents section for Excess field of the cover type : "
							+ coverType[1] + " " + coverType[0],
					"Same 'Excess(Â£)' value is not displayed under Contents section for Excess fieldof the cover type : "
							+ coverType[1] + " " + coverType[0],
					driver, extentedReport, true);

			Log.softAssertThat(newquotepage.uielement.verifyPageElements(Arrays.asList("btnReCalculate"), newquotepage),
					"Re-calculate button is displayed at the bottom of the Policy Adjustment page - Test case completed",
					"Re-calculate button is not displayed at the bottom of the Policy Adjustment page - Test case completed", driver,
					extentedReport, false);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if User able to add Accidental Damage through editing the Contents cover", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_018(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_018";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Enter Excess value
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");

			boolean BoolVal = false;
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);
			newquotepage.clickAccidentalCheckbox("Contents");
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);
			String[] coverType = coversToAdd[0].split("_");

			Log.softAssertThat(BoolVal, "Excess Value is entered for cover type : " + coverType[0],
					"Excess Value is entered for cover type : " + coverType[0], driver, extentedReport, true);

			// Accidental Damage excess value
			String ins_RowtoInteract2 = newquotepage.SelectInsuranceItem(coversToAdd[1], true, extentedReport);
			String[] Contents = coversToAdd[1].split("_");
			String accidentalExcess = newquotepage.getExcessValue(ins_RowtoInteract2, Contents[0], testData);

			Log.softAssertThat(accidentalExcess.equals(testData.get("CNT_ExcessAmt").toString()),
					"Same Excess Value is displayed for Accidental Damage which was entered for contents",
					"Same Excess Value is not displayed for Accidental Damage which was entered for contents", driver,
					extentedReport, true);

			Log.softAssertThat(newquotepage.uielement.verifyPageElements(Arrays.asList("btnReCalculate"), newquotepage),
					"Re-calculate button is displayed at the bottom of the Policy Adjustment page - Test case completed",
					"Re-calculate button is not displayed at the bottom of the Policy Adjustment page - Test case completed", driver,
					extentedReport, false);
			

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if adjustment amount increases after adding covers(STA) in the policy adjustment page - Garden", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_097_126_137(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_097_126_137";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport); 

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Add Cover
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");

			boolean BoolVal = false;
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);
			String[] coverType = coversToAdd[0].split("_");

			Log.softAssertThat(BoolVal, "Cover added successfully" + coverType[2] + " " + coverType[0],
					"Cover not added" + coverType[2] + " " + coverType[0], driver, extentedReport, true);
			
			//Click Recalculate
			newquotepage.clickReCalculate(extentedReport);
			HashMap<String, String> financialDetail = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			
			Log.softAssertThat(
					newquotepage.verifyNewPolicyTermPremium(financialDetail.get("ExistingPolicyTermPremium"),						
							financialDetail.get("NewPolicyTermPremium"), financialDetail.get("AdjustmentAmount")),
					"The Adjustment amount field is in positive and New Policy Term Premium field is updated - Test case completed",
					"The Adjustment amount field is in not positive and New Policy Term Premium field is not updated - Test case completed",
					driver, extentedReport, true);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if Adjustment details propulated correctly in STA adjustment page (As per expected)", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_084_085(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_084_085";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Add cover
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");

			boolean BoolVal = false;
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);
			String[] coverType = coversToAdd[0].split("_");

			Log.softAssertThat(BoolVal, "Cover added successfully " + coverType[2] + " " + coverType[0],
					"Cover not added" + coverType[2] + " " + coverType[0], driver, extentedReport, true);
				
			//Click Recalculate
			newquotepage.clickReCalculate(extentedReport);	
			HashMap<String, String> financialDetail = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			
			Log.softAssertThat(!financialDetail.get("ChangeToPolicyTermPremium").equals("0.00"), "After adding cover, Changes to policy premiun is updated as :" + financialDetail.get("ChangeToPolicyTermPremium"),
					"After adding cover, Changes to policy premiun is not updated", driver, extentedReport, true);
			
			// Remove cover
			BoolVal = false;
			newquotepage.SelectInsuranceItem(coversToAdd[1], true, extentedReport);
			/*BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[1], ins_RowtoInteract2, true,
					extentedReport);*/

			newquotepage.clickReCalculate(extentedReport);	
			HashMap<String, String> financialDetail2 = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			
			Log.softAssertThat(financialDetail2.get("ChangeToPolicyTermPremium").equals("0.00"), "After removing cover, Changes to policy premiun is updated - Test case completed",
					"After removing cover, Changes to policy premiun is not updated - Test case completed", driver, extentedReport, true);
			
			

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if Premium should remain unchanged if no covers are changed", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_129(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_129";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Back to Datacapture page
			newquotepage.clickbacktoDatacapture(extentedReport);
			
			// Navigate to Price presentation page
			newquotepage.clickNextOne(extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);
			
			HashMap<String, String> financialDetailAfter = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			Log.softAssertThat(
					financialDetailAfter.get("AdjustmentAmount").contains("0.00"),
					"All the section and the fields within Cover/Price Presentation remain unaltered, as there is no change made in policy data capture - Test case completed",
					"Failed - All the section and the fields within Cover/Price Presentation is altered, after no change made in policy data capture - Test case completed",
					driver, extentedReport, true);
		

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if re calculate button displayed after any update in covers", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_062(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_062";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Enter Excess value
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");

			boolean BoolVal = false;
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);
			String[] coverType = coversToAdd[0].split("_");

			Log.softAssertThat(BoolVal, coverType[0] + " : Cover is added successfully",
					coverType[0] + " :Cover is not added successfully", driver, extentedReport, true);

			Log.softAssertThat(newquotepage.uielement.verifyPageElements(Arrays.asList("btnReCalculate"), newquotepage),
					"Re-calculate button is displayed at the bottom of the Policy Adjustment page",
					"Re-calculate button is not displayed at the bottom of the Policy Adjustment page", driver,
					extentedReport, true);

			// To confirm STA
			newquotepage.clickReCalculate(extentedReport);
			
			HashMap<String, String> financialDetailAfter = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			
			Log.softAssertThat(
					newquotepage.verifyNewPolicyTermPremium(financialDetailAfter.get("ExistingPolicyTermPremium"),
							financialDetailAfter.get("NewPolicyTermPremium"),
							financialDetailAfter.get("AdjustmentAmount")),
					"Updated premium values under 'Financials' section to the New Policy Term Premium field updated according to adjustment - Test case completed",
					"Updated premium values under 'Financials' section to the New Policy Term Premium field not updated according to adjustment - Test case completed",
					driver, extentedReport, true);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if Tarnsaction tab updated correctly after STA and Check if user can perform the STA on the  Reinstated Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_092_142(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_092_142";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);

			// Canceling a Policy
			custdashboardpage.clickCancelPolicy(testData, true, extentedReport);
			custdashboardpage.enterRequestCancellationDate(extentedReport);
			custdashboardpage.enterReason(testData.get("Cancellation Reason"), extentedReport);
			custdashboardpage.enterPremiumType(testData, extentedReport);

			custdashboardpage.clickCalculate(true, extentedReport);
			custdashboardpage.clickConfirm(true, extentedReport);

			// Verifying that policy is cancelled
			Log.softAssertThat(custdashboardpage.verifyCancellation(true, extentedReport),
					"Policy cancelled successfully", "Policy is not cancelled", driver, extentedReport, true);

			custdashboardpage.clickReinstatePolicy(true, extentedReport);
			custdashboardpage.selectReinstateReason(testData, true, extentedReport);
			custdashboardpage.clickCalculateForReinstate(true, extentedReport);
			custdashboardpage.clickConfirmForReinstate(true, extentedReport);

			// Verifying that Policy is reinstated
			if (custdashboardpage.verifyReinstation(true, extentedReport))
				Log.pass("Policy Reinstated successfully", driver, extentedReport, true);
			else
				Log.fail("Policy is not reinstated", driver, extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Enter Excess value
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");
		
			boolean BoolVal = false;
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);
			String[] coverType = coversToAdd[0].split("_");

			Log.softAssertThat(BoolVal, coverType[0] + " : Cover is added successfully",
					coverType[0] + " : Cover is added successfully", driver, extentedReport, true);

			Log.softAssertThat(newquotepage.uielement.verifyPageElements(Arrays.asList("btnReCalculate"), newquotepage),
					"Re-calculate button is displayed at the bottom of the Policy Adjustment page",
					"Re-calculate button is not displayed at the bottom of the Policy Adjustment page", driver,
					extentedReport, true);
			
			// To confirm STA
			newquotepage.clickReCalculate(extentedReport);
			newquotepage.clickBuyMTA(extentedReport, true);
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.enterTrasactionIdForSTA(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"STA is completed and Navigated to customer dashboard page",
					"STA is not completed and Not navigated to customer dashboard page", driver, extentedReport, true);
			
			// Check for Cancellation transaction status
			String expectedPosition1 = "Cancellation";
			String expectedTransactionStatus1 = "Active: Debit Full Premium";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition1, expectedTransactionStatus1,
							extentedReport, true),
					"Verified that the Cancellation transaction status is '" + expectedTransactionStatus1 + "' as expected",
					"The Cancellation transaction status is not as expected");

			// Check for Reinstatement transaction status
			String expectedPosition2 = "Reinstatement";
			String expectedTransactionStatus2 = "Active: Debit Full Premium";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition2, expectedTransactionStatus2,
							extentedReport, true),
					"Verified that the Reinstatement transaction status is '" + expectedTransactionStatus2 + "' as expected",
					"The Reinstatement transaction status is not as expected");
			
			// Check for STA transaction status
			String expectedPosition = "ShortTermAdjustment";
			String expectedTransactionStatus = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"Verified that the STA transaction status is '" + expectedTransactionStatus + "' as expected - Test case completed",
					"The STA transaction status is not as expected - Test case completed", driver, extentedReport, true);
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	// Invalid Scenario
	//@Test(description = "Check if the policy can be upgraded from 3 * to 5* during STA", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_138(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_138";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			
			newquotepage.Scheme = "ASDA";
			newquotepage.downgradePolicy(extentedReport);
			Log.softAssertThat(newquotepage.verifyDowngraded(), "Policy downgraded to 3 star",
					"Policy not downgraded to 3 star", driver, extentedReport, true);
			
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"3* NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");
			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal =false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToAdd[i].split("_");
			if (BoolVal != false) {
					Log.pass(coverType[2]+" "+ coverType[0]+"_"+coverType[1] + " cover type done successfully", driver, extentedReport, true);
				}
				else{
					Log.fail("Failed to "+coverType[2]+" "+ coverType[0]+"_"+coverType[1] + " cover", driver, extentedReport, true);
				}
			
		 	}
			
			newquotepage.upgradePolicy(extentedReport);
			Log.softAssertThat(newquotepage.verifyUpgraded(), "Policy upgraded to 5 star",
					"Policy not upgraded to 5 star", driver, extentedReport, true);
			
			
			// To confirm STA
			newquotepage.clickReCalculate(extentedReport);
			newquotepage.clickBuyMTA(extentedReport, true);
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.enterTrasactionIdForSTA(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer dashboard page and policy is downgraded during STA",
					"Not navigated to Customer dashboard page and policy is downgraded during STA", driver,
					extentedReport, true);	
			
			Log.message("Steps to verify to mail and document generation is not applicable for STA - Test case completed", driver, extentedReport);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	// Invalid Scenario
	//@Test(description = "Check if the policy can be downgraded from 5 * to 3* during STA", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_139(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_139";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get Quote
			newquotepage.clickGetQuote(extentedReport);
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			
			// Add mandatory cover for 5* policy
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");
			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal =false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToAdd[i].split("_");
			if (BoolVal != false) {
					Log.pass(coverType[2]+" "+ coverType[0]+"_"+coverType[1] + " cover type done successfully", driver, extentedReport, true);
				}
				else{
					Log.fail("Failed to "+coverType[2]+" "+ coverType[0]+"_"+coverType[1] + " cover", driver, extentedReport, true);
				}
			
		 	}
			
			// Upgrade policy
			newquotepage.Scheme = "ASDA";
			newquotepage.upgradePolicy(extentedReport);
			Log.softAssertThat(newquotepage.verifyUpgraded(), "Policy upgraded to 5 star",
					"Policy not upgraded to 5 star", driver, extentedReport, true);
			
			newquotepage.clickReCalculate(extentedReport);
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			newquotepage.downgradePolicy(extentedReport);
			
			Log.softAssertThat(newquotepage.verifyDowngraded(), "policy is downgraded during STA",
					"policy is not downgraded during STA", driver, extentedReport, true);	
			
			
			//newquotepage.clickReCalculate(extentedReport);
			newquotepage.clickBuy(extentedReport);
			newquotepage.clickPaymentNext(extentedReport);
			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer dashboard page and policy is downgraded during STA",
					"Not navigated to Customer dashboard page and policy is downgraded during STA", driver,
					extentedReport, true);	
			
			Log.message("Steps to verify to mail and document generation is not applicable for STA - Test case completed", driver, extentedReport);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if user can perform the STA on the STA Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_140(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_140";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Enter Excess value
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");
			boolean BoolVal = false;
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);
			String[] coverType = coversToAdd[0].split("_");

			Log.softAssertThat(BoolVal, coverType[0] + " : Cover is added successfully",
					coverType[0] + " : Cover is added successfully", driver, extentedReport, true);
			
			//Complete STA
			newquotepage.clickReCalculate(extentedReport);
			newquotepage.clickBuyMTA(extentedReport, true);
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.enterTrasactionIdForSTA(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);
			
			// Check for MTA transaction status
			String expectedPosition = "ShortTermAdjustment";
			String expectedTransactionStatus = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"Verified that the STA is completed", "The STA transaction is not completed");
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed",
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, true);

			// Enter 2nd STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Click buy button
			newquotepage.clickBuyMTA(extentedReport, true);
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.enterTrasactionIdForSTA(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"2nd STA is completed and Navigated to customer dashboard page - Test case completed",
					"2nd STA is not completed and Not navigated to customer dashboard page - Test case completed", driver, extentedReport,
					true);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if user able to add joint policy holder", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_043_044_045(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_043_044_045";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		HashMap<String, String> testData2 = new HashMap<String, String>(testData);
		HashMap<String, String> testData3 = new HashMap<String, String>(testData);
		HashMap<String, String> testData4 = new HashMap<String, String>(testData);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer1 Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails2 = searchPage.enterCustomerDetails(testData2, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData2.get("Title") + " " + custDetails2.get("First Name")
							+ " " + custDetails2.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails2.get("First Name") + " "
							+ custDetails2.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);
			searchPage.clickComplete(extentedReport);
			
			// Enter Customer2 Details
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails3 = searchPage.enterCustomerDetails(testData3, true, extentedReport);

			// Confirm customer details and create customer
			searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData2.get("Title") + " " + custDetails3.get("First Name")
							+ " " + custDetails3.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails3.get("First Name") + " "
							+ custDetails2.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);
			searchPage.clickComplete(extentedReport);
			
			// Enter Customer3 Details
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails4 = searchPage.enterCustomerDetails(testData4, true, extentedReport);

			// Confirm customer details and create customer
			searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData4.get("Title") + " " + custDetails4.get("First Name")
							+ " " + custDetails4.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails4.get("First Name") + " "
							+ custDetails4.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);
			searchPage.clickComplete(extentedReport);			
			
			// Enter main customer Details
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);
			
			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			newquotepage.clickAddPolicyHolderButton(extentedReport);
			
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("txtAddJointPolicyHolder"), newquotepage),
					"A popup with header 'Add a Joint Policy Holder' is opened",
					"A popup with header 'Add a Joint Policy Holder' is not opened", driver, extentedReport, true);
			
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("txtJointPolicyHolderLastName",
							"txtJointPolicyHolderFirstName", "selectJointPolicyContactType", "calJointPolicyHolderDob",
							"txtJointPolicyHolderPostCode", "btnJointPolicyHolderSearch", "btnCancelJointPolicy"),
							newquotepage),
					"A popup is opened which displays with 'Contact','First Name', 'Last Name','Date of Birth', 'Save' and 'Cancel' Button",
					"A popup is not displays with 'Contact','First Name', 'Last Name','Date of Birth', 'Save' and 'Cancel' Button",
					driver, extentedReport);

			// Add joint policy holder 1
			newquotepage.addJointPolicyHolder(testData2, extentedReport);
			
			// Add joint policy holder 2
			newquotepage.clickAddPolicyHolderButton(extentedReport);
			newquotepage.addJointPolicyHolder(testData3, extentedReport);
			
			// Add joint policy holder 3
			newquotepage.clickAddPolicyHolderButton(extentedReport);
			try {
				newquotepage.addJointPolicyHolder(testData4, extentedReport);
			} catch(Exception ex) {
				Log.message("3rd joint policy holder is not added", extentedReport);
			}
			
			Log.softAssertThat(
					newquotepage.verifyJointPolicyHolderErrorMsg(),
					"Joint policy holder is not able to add more than 3 policy holders and displayed warning message : " + newquotepage.ERROR_MSG_JOINT_POLICYHOLDER + " - Test case completed",
					"Joint policy holder is able to add more than 3 policy holders and not displayed warning message : " + newquotepage.ERROR_MSG_JOINT_POLICYHOLDER + " - Test case completed",
					driver, extentedReport, true);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if adjustment amount decreases after removing covers(STA) in the policy adjustment page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_127(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_127";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			
			// Add cover
			String[] coversToAdd = testData.get("coverToSelect_NB").toString().split(",");
			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToAdd[i].split("_");
				
				Log.softAssertThat(BoolVal,
						"Cover Added Successfully "+ coverType[2] + " " + coverType[0],
						"Cover Added Successfully "+ coverType[2] + " " + coverType[0],
						driver, extentedReport, true);
			}
			newquotepage.clickReCalculate(extentedReport);
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Enter Excess value
			String[] coversToRemove = testData.get("coverToSelect").toString().split(",");

			for (int i = 0; i < coversToRemove.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToRemove[i], true, extentedReport);
				//newquotepage.clickBuildingsEditButton("Contents", false);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToRemove[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToRemove[i].split("_");
				
				Log.softAssertThat(BoolVal,
						"Cover removed successfully "+ coverType[2] + " " + coverType[0],
						"Cover not removed successfully "+ coverType[2] + " " + coverType[0],
						driver, extentedReport, true);
			}
			
			newquotepage.clickReCalculate(extentedReport);
			HashMap<String, String> financialDetail = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			
			Log.softAssertThat(
					newquotepage.verifyNewPolicyTermPremium(financialDetail.get("ExistingPolicyTermPremium"),
							financialDetail.get("NewPolicyTermPremium"), financialDetail.get("AdjustmentAmount")),
					"Premiums is displayed as 'Existing Policy Term Premium + Adjustment Amount = New Policy Term Premium' - Test case completed",
					"Premiums is not displayed as 'Existing Policy Term Premium + Adjustment Amount = New Policy Term Premium' - Test case completed", driver, extentedReport, true);
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if user able to amend/update changes in Data Capture page during STA - Address", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_105_118(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_105_118";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,	extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			newquotepage.clickbacktoDatacapture(extentedReport);
			
			String postcodeToUpdated = "NR11XR"; 
			newquotepage.changeAddressInPropertyAndCover(postcodeToUpdated, extentedReport);
			
			Log.softAssertThat(newquotepage.verifyChangeAddress(postcodeToUpdated, extentedReport),
					"Changed address is displayed in data capture after updating",
					"Changed address is not displayed in data capture after updating", driver, extentedReport, true);
			
			// Navigate to Price presentation page
			newquotepage.clickNextOne(extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);
			
			// Complete STA
			newquotepage.clickBuyMTA(extentedReport, true);
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.enterTrasactionIdForSTA(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"STA is completed after property detail changed - Test case completed",
					"STA is not completed after property detail changed - Test case completed", driver,
					extentedReport, true);
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if user able to amend/update changes in Data Capture page during STA - View Button", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_057_058(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_057_058";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,	extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			newquotepage.clickbacktoDatacapture(extentedReport);
			
			newquotepage.clickNextOne(extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			
			Log.softAssertThat(newquotepage.uielement.verifyPageElements(Arrays.asList("btnView"), newquotepage),
					"View button is display on Declaration tab", "View button is not display on Declaration tab",
					driver, extentedReport, true);
			
			// Click View button
			newquotepage.clickView(extentedReport);			
			Log.softAssertThat(newquotepage.verifyTabInDeclarationPopup(extentedReport),
					"All the expected Tabs are available in the Declaration Tab",
					"Expected Tabs are not available in the Declaration Tab", driver, extentedReport, true);
			
			Log.softAssertThat(newquotepage.uielement.verifyPageElements(Arrays.asList("radAgree_Ele", "chkCustomerNotAgree"), newquotepage),
					"Important section with two radio buttons is displayed", 
					"Important section with two radio buttons is not displayed", driver, extentedReport, false);
			
			Log.softAssertThat(newquotepage.uielement.verifyPageElements(Arrays.asList("btnSaveDeclaration", "btnCancelDeclaration"), newquotepage),
					"Save and Cancel button is present in the popup - Test case completed", 
					"Save and Cancel button is not present in the popup - Test case completed", driver, extentedReport, false);
			
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if user able to add and edit the additional covers - Garden cover", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_024_025_026(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_024_025_026";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Open Garden contents popup
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");
			boolean BoolVal = false;
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);

			Log.softAssertThat(newquotepage.verifyCoverPopupTitle("Garden Cover"),
					"'Garden Contents' Heading is displayed in the popup",
					"'Garden Contents' Heading is not displayed in the popup",
					driver, extentedReport, true);

			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("ins_GCcover_saveBut", "btnCancelGardenContents"),
							newquotepage),
					"Garden Contents Popup is displayed with Save and Cancel button",
					"Garden Contents Popup is not displayed with Save and Cancel button", driver, extentedReport, false);			

			// Add Garden cover
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);

			Log.softAssertThat(BoolVal, "Selected limit value  is displayed in the policy adjustment page for the garden content cover",
					"Selected limit value is not displayed in the policy adjustment page for the garden content cover", driver,
					extentedReport, true);

			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(
							Arrays.asList("btnEditWBGardenCover", "btnRemoveWBGardenCover"), newquotepage),
					"Remove and Edit button is displayed for Garden content cover after adding it",
					"Remove and Edit button is not displayed for Garden content cover after adding it", driver, extentedReport,
					false);
			
			// Click Edit
			newquotepage.SelectInsuranceItem(coversToAdd[1], true, extentedReport);
			
			Log.softAssertThat(
					newquotepage.verifyDisplayedCoverLimitInPopup("Garden Cover", testData.get("GC_ItemVaue").toString()),
					"Garden Cover pop up displayed the cover limit that is saved already : " + testData.get("GC_ItemVaue").toString(),
					"Garden Cover pop up not displayed the cover limit that is saved already : " + testData.get("GC_ItemVaue").toString(),
					driver, extentedReport, true);
			
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(
							Arrays.asList("ins_GCcover_saveBut", "btnCancelGardenContents"), newquotepage),
					"Garden Contents Popup is displayed with Save and Cancel button - Test case completed",
					"Garden Contents Popup is not displayed with Save and Cancel button - Test case completed", driver, extentedReport, false);
			
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
//	@Test(description = "Check if user able to add the additional covers - Technology & Entertainment", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_027_028_029(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_027_028_029";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Open Technology & Entertainment popup
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");
			boolean BoolVal = false;
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);

			Log.softAssertThat(newquotepage.verifyCoverPopupTitle("Technology & Entertainment"),
					"'Technology & Entertainment' Heading is displayed in the popup",
					"'Technology & Entertainment' Heading is not displayed in the popup",
					driver, extentedReport, true);

			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("ins_TEcover_saveBut", "btnCancelTechnologyAndEntertainment"),
							newquotepage),
					"Technology & Entertainment Popup is displayed with Save and Cancel button",
					"Technology & Entertainment Popup is not displayed with Save and Cancel button", driver, extentedReport, false);
			

			// Add Garden cover
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);

			Log.softAssertThat(BoolVal, "Selected limit value  is displayed in the policy adjustment page for the Technology & Entertainment cover",
					"Selected limit value is not displayed in the policy adjustment page for the Technology & Entertainment cover", driver,
					extentedReport, true);

			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(
							Arrays.asList("btnEditTechnologyAndEntertainment", "btnRemoveTechnologyAndEntertainment"), newquotepage),
					"Remove and Edit button is displayed for Technology & Entertainment cover after adding it",
					"Remove and Edit button is not displayed for Technology & Entertainment cover after adding it", driver, extentedReport,
					false);
			
			//click Edit button
			newquotepage.SelectInsuranceItem(coversToAdd[1], true, extentedReport);
			
			Log.softAssertThat(
					newquotepage.verifyDisplayedCoverLimitInPopup("Technology & Entertainment", testData.get("TE_coverLimit").toString()),
					"Technology & Entertainment pop up displayed the cover limit that is saved already : " + testData.get("TE_coverLimit").toString(),
					"Technology & Entertainment pop up not displayed the cover limit that is saved already : " + testData.get("TE_coverLimit").toString(),
					driver, extentedReport, true);
			
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("ins_TEcover_saveBut", "btnCancelTechnologyAndEntertainment"),
							newquotepage),
					"Technology & Entertainment Popup is displayed with Save and Cancel button - Test case completed",
					"Technology & Entertainment Popup is not displayed with Save and Cancel button - Test case completed", driver, extentedReport, false);
			
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if user able to add the additional covers - Personal Items", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_030_031_032_033(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_030_031_032_033";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,	extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Open Technology & Entertainment popup
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");
			boolean BoolVal = false;
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);

			Log.softAssertThat(newquotepage.verifyCoverPopupTitle("Personal Items"),
					"'Personal Items' Heading is displayed in the popup",
					"'Personal Items' Heading is not displayed in the popup",
					driver, extentedReport, true);

			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(
							Arrays.asList("ins_PI_coverReq", "ins_PI_SaveBtn",
									"lnkLimitsInPersonalItemPopup", "btnCancelPersonalItem"),
							newquotepage),
					"A popup contains Cover dropdown, Limits, Save and Cancel button",
					"A popup does not contains Cover dropdown, Limits, Save and Cancel button");

			// Add personal Item cover
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);

			Log.softAssertThat(BoolVal, "Selected limit value  is displayed in the policy adjustment page for the garden content cover",
					"Selected limit value  is displayed in the policy adjustment page for the Personal item cover", driver,
					extentedReport, true);

			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(
							Arrays.asList("btnWBEditPersonalItem", "btnWBRemovePersonalItem", "btnReCalculate"), newquotepage),
					"Re-calculate button, Remove and Edit button is displayed for Personal Item cover after adding it",
					"Remove and Edit button is not displayed for Personal Item cover after adding it", driver, extentedReport,
					false);

			// Verify Saved value
			newquotepage.SelectInsuranceItem(coversToAdd[1], true, extentedReport);
			Log.softAssertThat(
					newquotepage.verifyPersonalItemcoverPopupDetails(testData, extentedReport),
					"Personal Item pop up displayed the values that is saved already" ,
					"Personal Item pop up not displayed the values that is saved already",
					driver, extentedReport, true);

			// Edit saved Value
			String valueToUpdate = "5000";
			newquotepage.enterCoverRequiredForPersonalItems(valueToUpdate);
			newquotepage.clickSaveBtnInPersonalItems();
			
			// Verify Saved value
			newquotepage.SelectInsuranceItem(coversToAdd[1], true, extentedReport);
			HashMap<String,String> savedDetail = newquotepage.getPersonalItemcoverPopupDetails(extentedReport);
			Log.softAssertThat(savedDetail.get("Cover").equals(valueToUpdate),
					"User is able to edit the saved value in Personal Item popup - ",
					"User is able to edit the saved value in Personal Item popup", driver, extentedReport,
					true);
			
			
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}

	@Test(description = "Check if user able to remove all covers for 3* policy during STA", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_149(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_149";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		HashMap<String, String> testData2 = new HashMap<String, String>(testData);
		HashMap<String, String> testData3 = new HashMap<String, String>(testData);
		HashMap<String, String> testData4 = new HashMap<String, String>(testData);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		String staAdjustReason = testData.get("STAdjReason");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer1 Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails2 = searchPage.enterCustomerDetails(testData2, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData2.get("Title") + " " + custDetails2.get("First Name")
							+ " " + custDetails2.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails2.get("First Name") + " "
							+ custDetails2.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);		
			
			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			// Add the insurance cover items
			String[] coversToAdd = testData.get("coverToSelect_NB").toString().split(",");
			System.out.println(coversToAdd.length);
			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
				System.out.println(ins_RowtoInteract);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToAdd[i].split("_");
				if (BoolVal != false) {
					Log.pass(coverType[2] + " " + coverType[0] + " cover done successfully", driver, extentedReport,
							true);
				} else {
					Log.fail("Failed to " + coverType[2] + " " + coverType[0] + " cover", driver, extentedReport, true);
				}

			}
			newquotepage.clickReCalculate(extentedReport);	
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			
			
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			NewQuotePage NewQuotePage = new NewQuotePage(driver, extentedReport);
			
			//Remove all cover
			String[] coversToRemove = testData.get("coverToSelect").toString().split(",");

			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = NewQuotePage.SelectInsuranceItem(coversToRemove[i], true, extentedReport);
				BoolVal = NewQuotePage.enterInsCoverDetails(testData, coversToRemove[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToRemove[i].split("_");
				if (BoolVal != false) {
					Log.pass(coverType[2] + " " + coverType[0] + " cover done successfully", driver, extentedReport,
							true);
				} else {
					Log.fail("Failed to " + coverType[2] + " " + coverType[0] + " cover", driver, extentedReport, true);
				}
			}
			// Click on recalculate
			NewQuotePage.clickReCalculate(extentedReport);

			// Click on buy button
			NewQuotePage.clickBuyMTA(extentedReport, true);
			NewQuotePage.selectAvalilableAdjReasons(staAdjustReason, extentedReport, false);
			NewQuotePage.clickAddSTA(extentedReport, false);
			NewQuotePage.clickAddAllSTANextButton(extentedReport, true);

			custdashboardpage = NewQuotePage.confirmPayment("STA", testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer dashboard page", "Not navigated to Customer dashboard page", driver,
					extentedReport, true);

			policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);
			} else {
				Log.fail("Failed - NB Policy Created in " + policyDetails.get("Status").toString() + " status", driver,
						extentedReport, true);
			}


		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if user able to accept the STA using annual card on NB core covers ", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_063_064(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_063_064";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		HashMap<String, String> testData2 = new HashMap<String, String>(testData);
		HashMap<String, String> testData3 = new HashMap<String, String>(testData);
		HashMap<String, String> testData4 = new HashMap<String, String>(testData);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		String staAdjustReason = testData.get("STAdjReason");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer1 Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails2 = searchPage.enterCustomerDetails(testData2, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData2.get("Title") + " " + custDetails2.get("First Name")
							+ " " + custDetails2.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails2.get("First Name") + " "
							+ custDetails2.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);
						
			
			
			
			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);
			} else 
				Log.fail("Failed - NB Policy Created in " + policyDetails.get("Status").toString() + " status", driver,
						extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, true);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			NewQuotePage NewQuotePage = new NewQuotePage(driver, extentedReport);
			
			// Add the insurance cover items
			String[] coversToAdd = testData.get("coverToSelect_NB").toString().split(",");
			System.out.println(coversToAdd.length);
			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
				System.out.println(ins_RowtoInteract);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToAdd[i].split("_");
				if (BoolVal != false) {
					Log.pass(coverType[2] + " " + coverType[0] + " cover done successfully", driver, extentedReport,
							true);
				} else {
					Log.fail("Failed to " + coverType[2] + " " + coverType[0] + " cover", driver, extentedReport, true);
				}

			}
			newquotepage.clickReCalculate(extentedReport);	
			// Click on buy button
			NewQuotePage.clickBuyMTA(extentedReport, true);
			
			NewQuotePage.selectAvalilableAdjReasons(staAdjustReason, extentedReport, false);
			NewQuotePage.clickAddSTA(extentedReport, false);
			NewQuotePage.clickAddAllSTANextButton(extentedReport, true);
			NewQuotePage.TakePayment(extentedReport);
			custdashboardpage = NewQuotePage.confirmPayment("STA", testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer dashboard page", "Not navigated to Customer dashboard page", driver,
					extentedReport, true);

			policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);
			} else {
				Log.fail("Failed - NB Policy Created in " + policyDetails.get("Status").toString() + " status", driver,
						extentedReport, true);
			}

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			driver.quit();
			Log.endTestCase(extentedReport);
			Log.testCaseResult(extentedReport);
			

		} // finally
	}

@Test(description = "Check if user able to remove all covers for 5* policy during STA", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_150(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_150";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		HashMap<String, String> testData2 = new HashMap<String, String>(testData);
		HashMap<String, String> testData3 = new HashMap<String, String>(testData);
		HashMap<String, String> testData4 = new HashMap<String, String>(testData);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		String staAdjustReason = testData.get("STAdjReason");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer1 Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails2 = searchPage.enterCustomerDetails(testData2, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData2.get("Title") + " " + custDetails2.get("First Name")
							+ " " + custDetails2.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails2.get("First Name") + " "
							+ custDetails2.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);
						
			
			
			
			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			// Add the insurance cover items
						String[] coversToAdd = testData.get("coverToSelect_NB").toString().split(",");
						System.out.println(coversToAdd.length);
						for (int i = 0; i < coversToAdd.length; i++) {
							boolean BoolVal = false;
							String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
							System.out.println(ins_RowtoInteract);
							BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
									extentedReport);
							String[] coverType = coversToAdd[i].split("_");
							if (BoolVal != false) {
								Log.pass(coverType[2] + " " + coverType[0] + " cover done successfully", driver, extentedReport,
										true);
							} else {
								Log.fail("Failed to " + coverType[2] + " " + coverType[0] + " cover", driver, extentedReport, true);
							}

						}
						newquotepage.clickReCalculate(extentedReport);	
						newquotepage.Scheme="WB";
						newquotepage.upgradePolicy(extentedReport);
						System.out.println(newquotepage.Scheme);
						newquotepage.clickReCalculate(extentedReport);
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);
			} else 
				Log.fail("Failed - NB Policy Created in " + policyDetails.get("Status").toString() + " status", driver,
						extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, true);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			NewQuotePage NewQuotePage = new NewQuotePage(driver, extentedReport);
			
			//Remove all cover
			String[] coversToRemove = testData.get("coverToSelect").toString().split(",");

			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = NewQuotePage.SelectInsuranceItem(coversToRemove[i], true, extentedReport);
				BoolVal = NewQuotePage.enterInsCoverDetails(testData, coversToRemove[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToRemove[i].split("_");
				if (BoolVal != false) {
					Log.pass(coverType[2] + " " + coverType[0] + " cover done successfully", driver, extentedReport,
							true);
				} else {
					Log.fail("Failed to " + coverType[2] + " " + coverType[0] + " cover", driver, extentedReport, true);
				}
			}
			// Click on recalculate
			NewQuotePage.clickReCalculate(extentedReport);
			
			NewQuotePage.Scheme="WB";
			NewQuotePage.downgradePolicy(extentedReport);
			NewQuotePage.clickReCalculate(extentedReport);
			// Click on buy button
			NewQuotePage.clickBuyMTA(extentedReport, true);
			NewQuotePage.selectAvalilableAdjReasons(staAdjustReason, extentedReport, false);
			NewQuotePage.clickAddSTA(extentedReport, false);
			NewQuotePage.clickAddAllSTANextButton(extentedReport, true);

			custdashboardpage = NewQuotePage.confirmPayment("STA", testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer dashboard page", "Not navigated to Customer dashboard page", driver,
					extentedReport, true);

			policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);
			} else {
				Log.fail("Failed - NB Policy Created in " + policyDetails.get("Status").toString() + " status", driver,
						extentedReport, true);
			}
			
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			driver.quit();
			Log.endTestCase(extentedReport);
			Log.testCaseResult(extentedReport);
			

		} // finally
	}

@Test(description = "Check if user able to accept the STA using annual card on NB core covers ", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_072(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_072";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		HashMap<String, String> testData2 = new HashMap<String, String>(testData);
		HashMap<String, String> testData3 = new HashMap<String, String>(testData);
		HashMap<String, String> testData4 = new HashMap<String, String>(testData);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		String staAdjustReason = testData.get("STAdjReason");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer1 Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails2 = searchPage.enterCustomerDetails(testData2, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData2.get("Title") + " " + custDetails2.get("First Name")
							+ " " + custDetails2.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails2.get("First Name") + " "
							+ custDetails2.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);
						
			
			
			
			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);
			} else 
				Log.fail("Failed - NB Policy Created in " + policyDetails.get("Status").toString() + " status", driver,
						extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, true);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			NewQuotePage NewQuotePage = new NewQuotePage(driver, extentedReport);
			
			// Add the insurance cover items
			String[] coversToAdd = testData.get("coverToSelect_NB").toString().split(",");
			System.out.println(coversToAdd.length);
			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
				System.out.println(ins_RowtoInteract);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToAdd[i].split("_");
				if (BoolVal != false) {
					Log.pass(coverType[2] + " " + coverType[0] + " cover done successfully", driver, extentedReport,
							true);
				} else {
					Log.fail("Failed to " + coverType[2] + " " + coverType[0] + " cover", driver, extentedReport, true);
				}

			}
			newquotepage.clickReCalculate(extentedReport);	
			// Click on buy button
			NewQuotePage.clickBuyMTA(extentedReport, true);
			
			NewQuotePage.selectAvalilableAdjReasons(staAdjustReason, extentedReport, false);
			NewQuotePage.clickAddSTA(extentedReport, false);
			NewQuotePage.clickAddAllSTANextButton(extentedReport, true);
			NewQuotePage.TakePayment(extentedReport);
			custdashboardpage = NewQuotePage.confirmPayment("STA", testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer dashboard page", "Not navigated to Customer dashboard page", driver,
					extentedReport, true);

			policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);
			} else {
				Log.fail("Failed - NB Policy Created in " + policyDetails.get("Status").toString() + " status", driver,
						extentedReport, true);
			}
			
			


		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			driver.quit();
			Log.endTestCase(extentedReport);
			Log.testCaseResult(extentedReport);
			

		} // finally
	}
	
	@Test(description = "Check if user able to amend/update changes in Data Capture page during STA", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_047_051_052_053_054_055_056_060_086_105_125(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_047_051_052_053_054_055_056_060_086_105_125";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		HashMap<String, String> testData2 = new HashMap<String, String>(testData);
		HashMap<String, String> testData3 = new HashMap<String, String>(testData);
		HashMap<String, String> testData4 = new HashMap<String, String>(testData);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		String staAdjustReason = testData.get("STAdjReason");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer1 Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails2 = searchPage.enterCustomerDetails(testData2, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData2.get("Title") + " " + custDetails2.get("First Name")
							+ " " + custDetails2.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails2.get("First Name") + " "
							+ custDetails2.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);
						
			
			
			
			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			// Add the insurance cover items
						String[] coversToAdd = testData.get("coverToSelect_NB").toString().split(",");
						System.out.println(coversToAdd.length);
						for (int i = 0; i < coversToAdd.length; i++) {
							boolean BoolVal = false;
							String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
							System.out.println(ins_RowtoInteract);
							BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
									extentedReport);
							String[] coverType = coversToAdd[i].split("_");
							if (BoolVal != false) {
								Log.pass(coverType[2] + " " + coverType[0] + " cover done successfully", driver, extentedReport,
										true);
							} else {
								Log.fail("Failed to " + coverType[2] + " " + coverType[0] + " cover", driver, extentedReport, true);
							}

						}
						
						newquotepage.clickReCalculate(extentedReport);
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);
			} else 
				Log.fail("Failed - NB Policy Created in " + policyDetails.get("Status").toString() + " status", driver,
						extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, true);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			NewQuotePage NewQuotePage = new NewQuotePage(driver, extentedReport);
			
			NewQuotePage.clickbacktoDatacapture(extentedReport);
			NewQuotePage.verifyDataCapturePage(testData, true, extentedReport);
NewQuotePage.selectNumberofBedrooms("4");
			
			Log.message("No of bedrooms : 4" , extentedReport);
			
			Thread.sleep(200);
			driver.findElement(By.cssSelector("input[name*='YEAROFBUILD']")).click();
			driver.findElement(By.cssSelector("input[name*='YEAROFBUILD']")).clear();
			driver.findElement(By.cssSelector("input[name*='YEAROFBUILD']")).sendKeys("2005");
			Log.message("Year of Build : 2005 ", extentedReport);
			driver.findElement(By.cssSelector("input[name*='YEAROFBUILD']")).click();
			driver.findElement(By.cssSelector("input[name*='YEAROFBUILD']")).sendKeys(Keys.TAB);
			NewQuotePage.clickNextOne(extentedReport);
			//Select PI radio button
			NewQuotePage.enter_PIDetailsFormoreThan1500(testData, true);
			NewQuotePage.enter_PedalBiCycleDetailsFormoreThan1500(testData, true);
			NewQuotePage.enter_HighRiskDetailsFormoreThan1500(testData, true);
			NewQuotePage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get and Buy Quote
			newquotepage.clickGetQuote(extentedReport);
			
			newquotepage.clickBuyMTA(extentedReport, true);
			NewQuotePage.selectAvalilableAdjReasons(staAdjustReason, extentedReport, false);
			NewQuotePage.clickAddSTA(extentedReport, false);
			NewQuotePage.clickAddAllSTANextButton(extentedReport, true);
			NewQuotePage.TakePayment(extentedReport);
			custdashboardpage = NewQuotePage.confirmPayment("STA", testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to Customer dashboard page", "Not navigated to Customer dashboard page", driver,
					extentedReport, true);

			policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

				Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);
			} else {
				Log.fail("Failed - NB Policy Created in " + policyDetails.get("Status").toString() + " status", driver,
						extentedReport, true);
			}

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			driver.quit();
			Log.endTestCase(extentedReport);
			Log.testCaseResult(extentedReport);
			
		} // finally
	}
	
	@Test(description = "Check if user able to add the additional covers - Pedal Cycles", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_034_035_036_037(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_034_035_036_037";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Open Technology & Entertainment popup
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");
			boolean BoolVal = false;
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);

			Log.softAssertThat(newquotepage.verifyCoverPopupTitle("Pedal Cycle"),
					"'Pedal Cycle' Heading is displayed in the popup",
					"'GPedal Cycles' Heading is not displayed in the popup",
					driver, extentedReport, true);

			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("ins_PCcover_SaveBut", "btnCancelPedalCycle"),
							newquotepage),
					"Pedal Cycle Popup is displayed with Save and Cancel button",
					"Pedal Cycle Popup is not displayed with Save and Cancel button", driver, extentedReport, true);		

			// Add Pedal Cycle
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);

			Log.softAssertThat(BoolVal, "Selected limit value  is displayed in the policy adjustment page for the Pedal Cycle cover",
					"Selected limit value is not displayed in the policy adjustment page for the Pedal Cycle cover", driver,
					extentedReport, true);
			
			// Verify Saved value
			newquotepage.SelectInsuranceItem(coversToAdd[1], true, extentedReport);
			Log.softAssertThat(newquotepage.verifyPedalCyclePopupDetails(testData, extentedReport),
					"Pedal Cycle pop up displayed the values that is saved already",
					"Pedal Cycle pop up not displayed the values that is saved already", driver, extentedReport,
					true);

			// Edit saved Value
			String valueToUpdate = "2";
			newquotepage.enterCycelCountForUnspecifiedBicycle(valueToUpdate);
			newquotepage.clickSaveBtnInPedalcycle();

			// Verify Saved value
			newquotepage.SelectInsuranceItem(coversToAdd[1], true, extentedReport);
			HashMap<String, String> savedDetail = newquotepage.getUnspecifiedBicyclePopupDetails(extentedReport);
			Log.softAssertThat(savedDetail.get("CycleCount").equals(valueToUpdate),
					"User is able to edit the saved value in Pedal Cycle popup - Test case completed",
					"User is not able to edit the saved value in Pedal Cycle popup - Test case completed", driver, extentedReport, true);

			
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if user able to save from acceptance page during STA", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_076(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_076";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed");

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			newquotepage.clickBuyMTA(extentedReport, true);
			newquotepage.selectMTAReasonPayment(extentedReport);
			
			// click 'Save Quote' button
			newquotepage.clickSaveQuoteMta(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page and user able to save from acceptance page during STA ",
					"Not navigated to customer dashboard page and user not able to save from acceptance page during STA",
					driver, extentedReport, true);
			
			// Check for STA Status
			String expectedPosition1 = "ShortTermAdjustment";
			String expectedTransactionStatus1 = "Inactive";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition1, expectedTransactionStatus1,
							extentedReport, true),
					"Verified that the STA transaction status is '" + expectedTransactionStatus1 + "' as expected (Quoted) - Test case completed",
					"The STA transaction status is not as expected (Quoted) - Test case completed");
			
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if user able to buy the STA from adjustment page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_088(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_088";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed");

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			newquotepage.clickbacktoDatacapture(extentedReport);
			
			// Change No.Of bedroom
			newquotepage.selectNumberofBedrooms("4");
			newquotepage.clickNextOne(extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);
			
			HashMap<String, String> financialDetail = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);

			Log.softAssertThat(!financialDetail.get("AdjustmentAmount").contains("0.00"),
					"New values displayed showing adjusted premium",
					"New values not displayed showing adjusted premium", driver, extentedReport, true);
			
			// Click Buy
			newquotepage.clickBuyMTA(extentedReport, true);	
			Log.softAssertThat(newquotepage.uielement.verifyPageElements(Arrays.asList("sel_MTAReason"), newquotepage),
					"USer is able to start Payment process - Test case completed", 
					"USer is not able to start Payment process - Test case completed", driver, extentedReport, false);
			
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if user able to remove joint policy holder", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_046(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_046";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		HashMap<String, String> testData2 = new HashMap<String, String>(testData);
	
		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer1 Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails2 = searchPage.enterCustomerDetails(testData2, true, extentedReport);
			String jointPolicyFirstName = custDetails2.get("First Name");
			String jointPolicyLastName = custDetails2.get("Last Name");
			String jointPolicyName = jointPolicyFirstName + " " + jointPolicyLastName;

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData2.get("Title") + " " + custDetails2.get("First Name")
							+ " " + custDetails2.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails2.get("First Name") + " "
							+ custDetails2.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);
			searchPage.clickComplete(extentedReport);		
			
			// Enter main customer Details
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);
			
			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium		 
			newquotepage.clickAddPolicyHolderButton(extentedReport);
			// Add joint policy holder 1
			newquotepage.addJointPolicyHolder(testData2, extentedReport);
			
			// Add covers
			String[] coversToAdd = testData.get("coverToSelect_NB").toString().split(",");
			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal =false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToAdd[i].split("_");
			if (BoolVal != false) {
					Log.pass(coverType[2]+" "+ coverType[0]+"_"+coverType[1] + " cover type done successfully", driver, extentedReport, true);
				}
				else{
					Log.fail("Failed to "+coverType[2]+" "+ coverType[0]+"_"+coverType[1] + " cover", driver, extentedReport, true);
				}
			
		 	}
					
			// click buy
			newquotepage.clickReCalculate(extentedReport);
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed");

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Remove joint policy
			newquotepage.clickYesToRemovePolicy(jointPolicyName, true, extentedReport);
			
			Log.softAssertThat(!newquotepage.verifyPolicyHolderContactName(jointPolicyName, true, extentedReport), 
					jointPolicyName + " - is removed using remove Joint Policyholders feature",
					jointPolicyName + " - is not removed using remove Joint Policyholders feature", 
					driver, extentedReport, true);
			
			// verify covers
			String[] coversToVerify = testData.get("coverToSelect_NB").toString().split(",");
			for (int i = 0; i < coversToVerify.length; i++) {
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToVerify[i], true, extentedReport);

				String[] coverType = coversToVerify[i].split("_");

				Log.softAssertThat(ins_RowtoInteract != null,
						coverType[0] + "_" + coverType[1] + " cover type not removed - Test case completed",
						coverType[0] + "_" + coverType[1] + " cover type removed - Test case completed", driver,
						extentedReport, true);
			}
			

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if user can perform an STA when the previous STA is in IR status", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_098(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_098";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,	extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed");

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Open personal popup
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");
			boolean BoolVal = false;
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);

			// Add personal Item cover
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);

			Log.softAssertThat(BoolVal, "Selected limit value  is displayed in the policy adjustment page for the personal Item cover",
					"Selected limit value is not displayed in the policy adjustment page for the personal Item cover", driver,
					extentedReport, true);
			
			newquotepage.clicksave(extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after clicking save button during STA",
					"Not navigated to customer dashboard page after clicking save button during STA", driver,
					extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed",
					"The Short-Term Adjustment prompt is not displayed", driver, extentedReport, false);

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			// Open garden cover popup
			BoolVal = false;
			String ins_RowtoInteract2 = newquotepage.SelectInsuranceItem(coversToAdd[1], true, extentedReport);

			// Add garden cover cover
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[1], ins_RowtoInteract2, true,
					extentedReport);

			Log.softAssertThat(BoolVal,
					"Selected limit value  is displayed in the policy adjustment page for the garden content cover",
					"Selected limit value is not displayed in the policy adjustment page for the garden content cover",
					driver, extentedReport, true);
			
			// To confirm STA
			newquotepage.clickReCalculate(extentedReport);
			newquotepage.clickBuyMTA(extentedReport, true);
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.enterTrasactionIdForSTA(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"User can perform an STA when the previous STA is in IR status",
					"User can not perform an STA when the previous STA is in IR status", driver, extentedReport, true);
			
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if Adjustment amount and Change to Policy term premium is same", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_130(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_130";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed");

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			HashMap<String, String> financialDetailBefore = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String beforeNewPolicyTermPremium = financialDetailBefore.get("NewPolicyTermPremium").toString();
			
			newquotepage.clickbacktoDatacapture(extentedReport);
			
			// Change No.Of bedroom
			newquotepage.selectCover("Buildings");
			newquotepage.clickNextOne(extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);
			
			HashMap<String, String> financialDetailAfter = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String afterNewPolicyTermPremium = financialDetailAfter.get("NewPolicyTermPremium");
			
			Log.softAssertThat(!beforeNewPolicyTermPremium.equals(afterNewPolicyTermPremium),
					"The Price Presentage page is displayed with the new changes",
					"The Price Presentage page is not displayed with the new changes", driver, extentedReport, true);

			Log.softAssertThat(financialDetailAfter.get("AdjustmentAmount").equals(financialDetailAfter.get("ChangeToPolicyTermPremium")),
					"Adjustment amount and Change to Policy term premium are same",
					"Adjustment amount and Change to Policy term premium are same", driver, extentedReport, true);
			
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally	
	}
	
	@Test(description = "Check if user able to edit and save the STA from adjustment page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_087(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_087";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

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
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);

			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("STAPrompt"), custdashboardpage),
					"The Short-Term Adjustment prompt is displayed", 
					"The Short-Term Adjustment prompt is not displayed");

			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);
			
			newquotepage.clickbacktoDatacapture(extentedReport);
			
			// Change No.Of bedroom
			newquotepage.selectNumberofBedrooms("5");
			newquotepage.clickNextOne(extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);
			
			HashMap<String, String> financialDetail = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);

			Log.softAssertThat(!financialDetail.get("AdjustmentAmount").contains("0.00"),
					"New values displayed showing adjusted premium",
					"New values not displayed showing adjusted premium", driver, extentedReport, true);
			
			// Click Save
			newquotepage.clicksave(extentedReport);
			
			// Check for STA transaction status
			String expectedPosition = "ShortTermAdjustment";
			String expectedTransactionStatus = "Inactive";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"STA transaction status is '" + expectedTransactionStatus
							+ "' as expected (Quoted), User can save STA from data capture page - Test case completed",
					"STA transaction status is not as expected (Quoted), User can save STA from data capture page - Test case completed",
					driver, extentedReport, true);
			
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check if user can save STA from Adjustment page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_061(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_061";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

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
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get and save Quote
			newquotepage.clickGetQuote(extentedReport);
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickSaveBtnInPricePresentation(extentedReport);
			
			Log.softAssertThat(newquotepage.uielement.verifyPageElements(Arrays.asList("btnCancelSaveQuote", "btnContinueSaveQuote"), newquotepage),
					"Save Quote Warning popup is displayed with cancel and continue button", 
					"Save Quote Warning popup is not displayed with cancel and continue button");

			Log.softAssertThat(
					newquotepage.verifySaveQuoteWarningMsg(),
					newquotepage.WARNING_MSG_SAVE_QUOTE + " - Message is displayed - Test case completed",
					newquotepage.WARNING_MSG_SAVE_QUOTE + " - Message is not displayed - Test case completed", driver, extentedReport, true);
			
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
}
