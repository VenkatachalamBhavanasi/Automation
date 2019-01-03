package com.pure.HouseHold;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.generic.support.BaseTest;
import com.generic.support.EmailReport;
import com.generic.support.Log;
import com.generic.support.WebDriverFactory;
import com.generic.utils.DataProviderUtils;
import com.generic.utils.DataUtils;
import com.pure.selfservice.pages.AddBuildingCoverPage;
import com.pure.selfservice.pages.AddSpecifiedItemPage;
import com.pure.selfservice.pages.CustomerDashboardPage;
import com.pure.selfservice.pages.CustomerServicePage;
import com.pure.selfservice.pages.CustomerSignInPage;
import com.pure.selfservice.pages.GetTestData;
import com.pure.selfservice.pages.MTAInitiatePage;
import com.relevantcodes.extentreports.ExtentTest;

@Listeners(EmailReport.class)
public class Sprint_02 extends BaseTest {
	private String webSite;
	String sheetName = "Sprint_02_";

	@BeforeMethod(alwaysRun = true)
	public void init(ITestContext context) throws IOException {
		webSite = System.getProperty("webSite") != null ? System.getProperty("webSite")
				: context.getCurrentXmlTest().getParameter("webSite");
	}

	public ExtentTest addTestInfo(String testCaseId, String testDesc) {
		String test = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getName();

		String browsername = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
				.getParameter("browser");
		String browserversion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
				.getParameter("browser_version");
		String os = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("os").substring(0,
				1);
		String osversion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
				.getParameter("os_version");
		String browserwithos = os + osversion + "_" + browsername + browserversion;

		return Log.testCaseInfo(testCaseId + " [" + test + "]",
				testCaseId + " - " + testDesc + " [" + browserwithos + "]", test);
	}

	public HashMap<String, String> getTestData(String testcaseId) {
		String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
		String className = "Sprint_02_" + env;
		return DataUtils.testDatabyID(testcaseId, className);
	}

	/** 9893 Story **/

	@Test(description = "Check that the policies and quotes ribbon details ", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_001(String browser) throws Exception {

		String tcId = "TC_10564_10566_10579_10581_10595";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.pass("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, false);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.verifyPolicyHeaderDetails(testData.get("Policy_Name"), extentedReport),
					"Policy ribbon details displaying correctly on customer dashboard page",
					"Policy ribbon details not displaying correctly on customer dashboard page", driver, extentedReport,
					true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that no quotes are shown on the SS dashboard ", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_002(String browser) throws Exception {

		String tcId = "TC_10565_10574";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.pass("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, false);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);
			Log.softAssertThat(customerDashboardPage.verifyAllPolicyTypeStatus(extentedReport),
					"All polices Household accepted policies", "Policies displaying not Hosuehold accepted policies",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that no Lapes policy are shown on the SS dashboard", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_054(String browser) throws Exception {

		String tcId = "TC_10578";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		String pName = testData.get("Policy_Name");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.pass("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, false);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);
			Log.softAssertThat(!customerDashboardPage.verifyPolicyFromList(pName, extentedReport, true),
					// (testData.policyName, extentedReport, true),
					"Lapes policy is Not Displayed", "Lapes policy is Displayed", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	/** 9894 Story **/

	@Test(description = "Check that the policy information is updated when the user selects a different policy from the list", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_003(String browser) throws Exception {

		String tcId = "TC_10568_10569_10570_10571";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		String policyName = testData.get("Policy_Number");
		String PolicyDetails = testData.get("policyDetails");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.pass("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, false);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, false);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));

			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"TC_10568_10569_10570_10571_Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"TC_10568_10569_10570_10571_Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that both broker and insurer add-ons are displayed on SS dashboard if the client has included any add-on to the policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_004(String browser) throws Exception {

		String tcId = "TC_10601";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.pass("Self service Landing Page : " + webSite, driver, extentedReport, true);
			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, false);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);
			customerDashboardPage.clickExpand(extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("tabAddons"),
							customerDashboardPage),
					"Additional Products is Verified", "Additional product is not Verified", driver, extentedReport,
					true);
			Log.softAssertThat(customerDashboardPage.getMultipleAddOnsLength() == 0,
					"Pure Addons are not available and Insurer add-ons not available :"
							+ customerDashboardPage.getMultipleAddOnsLength(),
							"Pure Addons are available and Insurer add-ons not available :"
									+ customerDashboardPage.getMultipleAddOnsLength(),
									driver, extentedReport, true);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that Multiple policy details are in collapsed mode by default", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_005(String browser) throws Exception {

		String tcId = "TC_10585_10586";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.pass("Self service Landing Page : " + webSite, driver, extentedReport, true);
			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, false);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);

			Log.softAssertThat(customerDashboardPage.verifyCollapses(extentedReport, true),
					"All the Policies are in collapsed state", "All the Policies are not in collapsed state", driver,
					extentedReport, true);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the policy details section has the following details Policy No, Line Of Business, Insurer, Status,  Insured Item, Policy Term, Expiry Date, Renewal Date", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_006(String browser) throws Exception {

		String tcId = "TC_10567";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.pass("Self service Landing Page : " + webSite, driver, extentedReport, true);
			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, false);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.verifyLabelInPolicyDetailSection(policyName, lstLabelToVerify,
							extentedReport),
					"Policy details section has the following Labels - Policy No, Line Of Business, Insurer, Status, Policy Term, Expiry Date, Renewal Date",
					"Policy details section does not have anyone the following Labels - Policy No, Line Of Business, Insurer, Status, Policy Term, Expiry Date, Renewal Date",
					driver, extentedReport, true);

			HashMap<String, String> expectedPolicyDetail = new HashMap<String, String>();
			expectedPolicyDetail.put("PolicyNumber", testData.get("Policy_Number"));
			expectedPolicyDetail.put("LineOfBussiness", testData.get("Line_Of_Bussiness"));
			expectedPolicyDetail.put("Insurer", testData.get("Insurer"));
			expectedPolicyDetail.put("Status", testData.get("Status"));
			expectedPolicyDetail.put("InsuredItem", testData.get("Insured_Item"));
			expectedPolicyDetail.put("PolicyTerm", testData.get("Policy_Term"));
			expectedPolicyDetail.put("ExpiryDate", testData.get("Expiry_Date"));
			expectedPolicyDetail.put("RenewalDate", testData.get("Renewal_Date"));

			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
							extentedReport),
					"Policy details section has the following details - Policy No, Line Of Business, Insurer, Status, Policy Term, Expiry Date, Renewal Date",
					"Policy details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Policy Term, Expiry Date, Renewal Date",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "To Check that the document is opened in a new window when 'View' button is clicked", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_007(String browser) throws Exception {

		String tcId = "TC_17577";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(customerDashboardPage.clickFirstViewButtonAndVerifyContent(extentedReport, true),
					"Document is viewed in new window", "Document is not viewed in new window", driver, extentedReport,
					true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the client is only allowed to view the latest version of each document (eg. NB, MTA completed for a policy, then client should only be able to view MTA doc)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_008(String browser) throws Exception {

		String tcId = "TC_17578";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Name");
		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.pass("Self service Landing Page : " + webSite, driver, extentedReport, true);
			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, false);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);
			customerDashboardPage.clickExpand(extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("tab_document"),
							customerDashboardPage),
					"Document Tab is present in the CustomerDashBoard Page",
					"Document tab is not present in the customerdashboard page", driver, extentedReport, true);
			customerDashboardPage.clickDocumentView(policyName, extentedReport, true);
			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the Documents section has the following columns - Document Name, Date Created, View Button, Upload New Document button", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_009(String browser) throws Exception {

		String tcId = "TC_17579";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			if (browser.contains("Android")) {

				Log.softAssertThat(
						customerDashboardPage.uielement.verifyPageElements(
								Arrays.asList("hdrDocumentName_andrd", "hdrDateCreated_andrd", "hdrViewBtn_andrd"),
								customerDashboardPage),
						"Documents section contain columns - Document Name, Date Created, View Button",
						"Feature Not Implemented : Application Failure - Documents section does not contain columns - Document Name, Date Created, View Button",
						driver, extentedReport, true);
			} else {
				Log.softAssertThat(customerDashboardPage.uielement.verifyPageElements(
						Arrays.asList("hdrDocumentName", "hdrDateCreated", "hdrViewBtn"), customerDashboardPage),
						"Documents section contain columns - Document Name, Date Created, View Button",
						"Feature Not Implemented : Application Failure - Documents section does not contain columns - Document Name, Date Created, View Button",
						driver, extentedReport, true);
			}

			Log.message(
					"Feature Not Implemented : Documents section does not contain Upload New Document button (Still not implemented in application)",
					extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Ensure that Self service not displaying the Transferred policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_055(String browser) throws Exception {

		String tcId = "TC_10584";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		String pName = testData.get("Policy_Name");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.pass("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, false);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);
			Log.softAssertThat(!customerDashboardPage.verifyPolicyFromList(pName, extentedReport, true),
					// (testData.policyName, extentedReport, true),
					"Transferred policy is Not Displayed", "Transferred policy is Displayed", driver, extentedReport,
					true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "Ensure that Self service display the Policy details of latest live version where Temporary MTA has been done on the policy using back office", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_056(String browser) throws Exception {

		String tcId = "TC_10583";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String Label = testData.get("LabelName");
		String pName = testData.get("Policy_Name");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.pass("Self service Landing Page : " + webSite, driver, extentedReport, true);
			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, false);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.verifyLabelInPolicyDetailSection(pName, lstLabelToVerify, extentedReport),
					"Policy details section has the following Labels - Policy No, Line Of Business, Insurer, Status, Policy Term, Expiry Date, Renewal Date",
					"Policy details section does not have anyone the following Labels - Policy No, Line Of Business, Insurer, Status, Policy Term, Expiry Date, Renewal Date",
					driver, extentedReport, true);

			HashMap<String, String> expectedPolicyDetail = new HashMap<String, String>();
			expectedPolicyDetail.put("ExpiryDate", testData.get("Expiry_Date"));

			Log.softAssertThat(
					customerDashboardPage.verifyExpireDateInPolicyDetailSection(pName, expectedPolicyDetail,
							extentedReport),
					"Expiry date is verified in Policy details section",
					"Expiry date is not verified in Policy details section", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that Renewal days are configurable.If Renewal days is configured as '30' then the alert message 'Please contact your Broker on XXXXX as this Policy is within 30 days of Renewal'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_057(String browser) throws Exception {
		String tcId = "TC_10577";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);

		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage
					.verifyRenewalErrorMsgInCustDashboard(customerDashboardPage.ERR_MSG_RENEWAL_POPUP),
					"Verified Renewal error message in Customer dashboard page : "
							+ customerDashboardPage.ERR_MSG_RENEWAL_POPUP,
							"Not verified Renewal error message in Customer dashboard page : "
									+ customerDashboardPage.ERR_MSG_RENEWAL_POPUP,
									driver, extentedReport, true);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	/** 9895 Story **/

	@Test(description = "Check that clicking CANCEL button should redirect to Policy Detail Screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_010(String browser) throws Exception {

		String tcId = "TC_17580";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage
					.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Click Cancel button
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.clickCancelButton(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"On clicking 'CANCEL' button redirected to Policy Detail Screen",
					"On clicking 'CANCEL' button did not redirect to Policy Detail Screen", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
	"Smoke" }, description = "Check that Date and Time provided for all your policy changes to take effect should be in future date and atleast one risk must be selected to Continue", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_011(String browser) throws Exception {

		String tcId = "TC_17581";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String changeDate = dateFormat.format(tomorrow);

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage
					.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Click Cancel button
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.enterDateForChangeTakeEffect(changeDate, extentedReport, false);

			Log.softAssertThat(mTAInitiatePage.verifyDateForChangeTakeEffect(changeDate, extentedReport, false),
					"Future date is entered for Date and Time provided for policy changes to take effect : "
							+ changeDate,
							"Future date is not entered for Date and Time provided for policy changes to take effect : "
									+ changeDate,
									driver, extentedReport);

			Log.softAssertThat(
					!mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("btnContinueDisabled"),
							mTAInitiatePage),
					"'Continue' button is not available, if none of the change type option is selected",
					"'Continue' button is available, if none of the change type option is selected", driver,
					extentedReport);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			Log.softAssertThat(mTAInitiatePage.verifyContinueBtnEnabled(),
					"'Continue' button is available, after selecting change type option : " + testData.typeOfChange,
					"'Continue' button is not available, after selecting change type option : " + testData.typeOfChange,
					driver, extentedReport);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the client is taken to the data capture page of the particular change when select an amendment and click continue", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_012(String browser) throws Exception {

		String tcId = "TC_10588";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage
					.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change my cover type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, false);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
	"Smoke" }, description = "Check that the message 'Effective date should be after' is displayed for the scenarios - MTA Effective Date&Time less than Policy Start Date and MTA Effective Date&Time greater Most Recent MTA Date&Time", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_013(String browser) throws Exception {

		String tcId = "TC_10599_10702";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage
					.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Enter MTA effective date
			MTAInitiatePage mtaInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mtaInitiatePage.enterDateForChangeTakeEffect(testData.mtaEffectiveDate, extentedReport, true);
			// Select change type
			mtaInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			Log.softAssertThat(
					mtaInitiatePage.verifyMTADateErrorMsg(mtaInitiatePage.MTA_START_DATE_LESSER_ERR_MSG, extentedReport,
							false),
					mtaInitiatePage.MTA_START_DATE_LESSER_ERR_MSG
					+ " - message is displayed for entering MTA Effective Date & Time less than Policy Start Date",
					mtaInitiatePage.MTA_START_DATE_LESSER_ERR_MSG
					+ " - message is not displayed for entering MTA Effective Date & Time less than Policy Start Date",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the message 'Effective date should be Before' is displayed when MTA Effective Date&Time is greater than Policy End Date", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_014(String browser) throws Exception {

		String tcId = "TC_10598_10701";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage
					.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Enter MTA effective date
			MTAInitiatePage mtaInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mtaInitiatePage.enterDateForChangeTakeEffect(testData.mtaEffectiveDate, extentedReport, true);

			// Select change type
			Log.softAssertThat(
					mtaInitiatePage.verifyMTADateErrorMsg(mtaInitiatePage.MTA_END_DATE_GREATER_ERR_MSG, extentedReport,
							false),
					mtaInitiatePage.MTA_END_DATE_GREATER_ERR_MSG
					+ " - message is displayed for entering MTA Effective Date & Time greater than Policy Expiry Date",
					mtaInitiatePage.MTA_END_DATE_GREATER_ERR_MSG
					+ " - message is not displayed for entering MTA Effective Date & Time greater than Policy Expiry Date",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on CANCEL button in Initiate page will redirect the user to the Policy Detail screen and displays the message 'You have decided not to proceed with your change. No changes will be made to your policy.'[After clicking on Cancel button]", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_015(String browser) throws Exception {

		String tcId = "TC_17582";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage
					.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			// Click cancel button
			mTAInitiatePage.clickCancelButton(extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("btnManageContactDetails"),
							customerDashboardPage),
					"On clicking CANCEL button navigated to Customer Dashboard page",
					"On clicking CANCEL button not navigated to Customer Dashboard page", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that Add Building, Change My Property and Add Specified Item amendments are displayed in the MTA initiate page for Content policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_016(String browser) throws Exception {

		String tcId = "TC_10587_10589_10593_10705";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage
					.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addBuildingsCover"), mTAInitiatePage),
					"Add Buildings Cover cover is displayed in MTA intiate page",
					"Add Buildings Cover cover is not displayed in MTA intiate page", driver, extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("iconHelpAddBuildingsCover"),
							mTAInitiatePage),
					"Help icon (?) is displayed against the 'Add Buildings Cover' button in MTA Initiate Page",
					"Help icon (?) is not displayed against the 'Add Buildings Cover' button in MTA Initiate Page",
					driver, extentedReport);

			mTAInitiatePage.hoverOnHelpIconOfAddBuildingsCover(extentedReport, true);
			Log.softAssertThat(mTAInitiatePage.verifyHelpIconAddBuildingsCoverPopupMessage(),
					mTAInitiatePage.POPUP_MSG_HELPICON_Add_Buildings_Cover
					+ " - Message is displayed when click/hover the help icon (?) against the 'Add Buildings Cover' button",
					mTAInitiatePage.POPUP_MSG_HELPICON_Add_Buildings_Cover
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Add Buildings Cover' button",
					driver, extentedReport);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("changeMyProperty"), mTAInitiatePage),
					"Change My Property cover is displayed in MTA intiate page",
					"Change My Property cover is not displayed in MTA intiate page", driver, extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("iconHelpChangeMyProperty"),
							mTAInitiatePage),
					"Help icon (?) is displayed against the 'Change My Property' button in MTA Initiate Page",
					"Help icon (?) is not displayed against the 'Change My Property' button in MTA Initiate Page",
					driver, extentedReport);

			mTAInitiatePage.hoverOnHelpIconOfChangeMyProperty(extentedReport, true);
			Log.softAssertThat(mTAInitiatePage.verifyHelpIconChangeMyPropertyPopupMessage(),
					mTAInitiatePage.POPUP_MSG_HELPICON_Change_My_Property
					+ " - Message is displayed when click/hover the help icon (?) against the 'Change My Property' button",
					mTAInitiatePage.POPUP_MSG_HELPICON_Change_My_Property
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Change My Property' button",
					driver, extentedReport);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addSpecifiedItems"), mTAInitiatePage),
					"Add Specified Items cover is displayed in MTA intiate page",
					"Add Specified Items cover is not displayed in MTA intiate page", driver, extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("iconHelpAddSpecifiedItems"),
							mTAInitiatePage),
					"Help icon (?) is displayed against the 'Add Specified Items' button in MTA Initiate Page",
					"Help icon (?) is not displayed against the 'Add Specified Items' button in MTA Initiate Page",
					driver, extentedReport);

			mTAInitiatePage.hoverOnHelpIconOfAddSpecifiedItems(extentedReport, true);
			Log.softAssertThat(mTAInitiatePage.verifyHelpIconAddSpecifiedItemsPopupMessage(),
					mTAInitiatePage.POPUP_MSG_HELPICON_Add_Buildings_Cover
					+ " - Message is displayed when click/hover the help icon (?) against the 'Add Specified Items' button",
					mTAInitiatePage.POPUP_MSG_HELPICON_Add_Buildings_Cover
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Add Specified Items' button",
					driver, extentedReport);

			// Click cancel button
			mTAInitiatePage.clickCancelButton(extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("btnManageContactDetails"),
							customerDashboardPage),
					"On clicking CANCEL button navigated to Customer Dashboard page",
					"On clicking CANCEL button not navigated to Customer Dashboard page", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that Change My Property and Add Spcifed Itmes amendments are displayed in MTA intiate page for Building & Content policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_017(String browser) throws Exception {

		String tcId = "TC_10591_10594_10703_10706";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage
					.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("changeMyProperty"), mTAInitiatePage),
					"Change My Property cover is displayed in MTA intiate page",
					"Change My Property cover is not displayed in MTA intiate page", driver, extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("iconHelpChangeMyProperty"),
							mTAInitiatePage),
					"Help icon (?) is displayed against the 'Change My Property' button in MTA Initiate Page",
					"Help icon (?) is not displayed against the 'Change My Property' button in MTA Initiate Page",
					driver, extentedReport);

			mTAInitiatePage.hoverOnHelpIconOfChangeMyProperty(extentedReport, true);
			Log.softAssertThat(mTAInitiatePage.verifyHelpIconChangeMyPropertyPopupMessage(),
					mTAInitiatePage.POPUP_MSG_HELPICON_Change_My_Property
					+ " - Message is displayed when click/hover the help icon (?) against the 'Change My Property' button",
					mTAInitiatePage.POPUP_MSG_HELPICON_Change_My_Property
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Change My Property' button",
					driver, extentedReport);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addSpecifiedItems"), mTAInitiatePage),
					"Add Specified Items cover is displayed in MTA intiate page",
					"Add Specified Items cover is not displayed in MTA intiate page", driver, extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("iconHelpAddSpecifiedItems"),
							mTAInitiatePage),
					"Help icon (?) is displayed against the 'Add Specified Items' button in MTA Initiate Page",
					"Help icon (?) is not displayed against the 'Add Specified Items' button in MTA Initiate Page",
					driver, extentedReport);

			mTAInitiatePage.hoverOnHelpIconOfAddSpecifiedItems(extentedReport, true);
			Log.softAssertThat(mTAInitiatePage.verifyHelpIconAddSpecifiedItemsPopupMessage(),
					mTAInitiatePage.POPUP_MSG_HELPICON_Add_Buildings_Cover
					+ " - Message is displayed when click/hover the help icon (?) against the 'Add Specified Items' button",
					mTAInitiatePage.POPUP_MSG_HELPICON_Add_Buildings_Cover
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Add Specified Items' button",
					driver, extentedReport);

			// Click cancel button
			mTAInitiatePage.clickCancelButton(extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("btnManageContactDetails"),
							customerDashboardPage),
					"On clicking CANCEL button navigated to Customer Dashboard page",
					"On clicking CANCEL button not navigated to Customer Dashboard page", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that Add Content and Change My Property amendments are displayed in MTA intiate page for Building policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_018(String browser) throws Exception {

		String tcId = "TC_10590_10592_10596_10704";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage
					.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addContentsCover"), mTAInitiatePage),
					"Add Contents Cover cover is displayed in MTA intiate page",
					"Add Contents Cover cover is not displayed in MTA intiate page", driver, extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("iconHelpAddContentsCover"),
							mTAInitiatePage),
					"Help icon (?) is displayed against the 'Add Contents Cover' button in MTA Initiate Page",
					"Help icon (?) is not displayed against the 'Add Contents Cover' button in MTA Initiate Page",
					driver, extentedReport);

			mTAInitiatePage.hoverOnHelpIconOfAddContentsCover(extentedReport, true);
			Log.softAssertThat(mTAInitiatePage.verifyHelpIconAddContentsCoverPopupMessage(),
					mTAInitiatePage.POPUP_MSG_HELPICON_Add_Contents_Cover
					+ " - Message is displayed when click/hover the help icon (?) against the 'Add Contents Cover' button",
					mTAInitiatePage.POPUP_MSG_HELPICON_Add_Contents_Cover
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Add Contents Cover' button",
					driver, extentedReport);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("changeMyProperty"), mTAInitiatePage),
					"Change My Property cover is displayed in MTA intiate page",
					"Change My Property cover is not displayed in MTA intiate page", driver, extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("iconHelpChangeMyProperty"),
							mTAInitiatePage),
					"Help icon (?) is displayed against the 'Change My Property' button in MTA Initiate Page",
					"Help icon (?) is not displayed against the 'Change My Property' button in MTA Initiate Page",
					driver, extentedReport);

			mTAInitiatePage.hoverOnHelpIconOfChangeMyProperty(extentedReport, true);
			Log.softAssertThat(mTAInitiatePage.verifyHelpIconChangeMyPropertyPopupMessage(),
					mTAInitiatePage.POPUP_MSG_HELPICON_Change_My_Property
					+ " - Message is displayed when click/hover the help icon (?) against the 'Change My Property' button",
					mTAInitiatePage.POPUP_MSG_HELPICON_Change_My_Property
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Change My Property' button",
					driver, extentedReport);

			// Click cancel button
			mTAInitiatePage.clickCancelButton(extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("btnManageContactDetails"),
							customerDashboardPage),
					"On clicking CANCEL button navigated to Customer Dashboard page",
					"On clicking CANCEL button not navigated to Customer Dashboard page", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that user is able to select more than one option and proceed", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_058(String browser) throws Exception {

		String tcId = "TC_10597";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage
					.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change my cover type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChangeValue[0], extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, false);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(
					addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"),
							addBuildingCoverPage),
					"Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance,
					extentedReport, true);

			// Click Quote button
			addBuildingCoverPage.clickAddAnotherPolicyChangeButton(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChooseTypeOfChange", "btnEditAddBuildingsCover"), mTAInitiatePage),
					"On clicking on 'Add another policy change' button within 'Change my proprty' screen takes the user back to Change My Policy screen and edit button displayed on the amendment",
					"On clicking on 'Add another policy change' button within 'Change my proprty' screen did not takes the user back to Change My Policy screen and edit button displayed on the amendment",
					driver, extentedReport);

			// Checking availability of 'Continue' & 'Date & Time field'
			Log.softAssertThat(
					!mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("btnContinue", "fldChangeDate"),
							mTAInitiatePage),
					"'Continue' button and 'Date and time' fields is hidden in MTA overlay after the user clicks on 'add another policy change' button in data capture page of the particular change",
					"'Continue' button is hidden in MTA overlay after the user clicks on 'add another policy change' button in data capture page of the particular change",
					driver, extentedReport);

			// Checking availability of 'Quote'
			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("btnQuote"), mTAInitiatePage),
					"'Quote' button is available in MTA overlay after the user clicks on 'add another policy change' button in data capture page of the particular change",
					"'Quote' button is not available in MTA overlay after the user clicks on 'add another policy change' button in data capture page of the particular change",
					driver, extentedReport);

			// Selecting Change type
			mTAInitiatePage.selectChangeType(testData.typeOfChangeValue[1], extentedReport, false);
			mTAInitiatePage.clickAddAnotherPolicyChangeButton(extentedReport, true);

			AddSpecifiedItemPage addSpecifiedItemPage = new AddSpecifiedItemPage(driver, extentedReport).get();
			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddSpecifiedItems"),
							addSpecifiedItemPage),
					"Add Specified Items cover page is opens after clicking Continue button",
					"Add Specified Items cover page is not opens after clicking Continue button", driver,
					extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

}
