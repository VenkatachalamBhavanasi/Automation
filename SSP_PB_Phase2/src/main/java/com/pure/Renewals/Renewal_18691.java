package com.pure.Renewals;

import java.io.IOException;
import java.util.Arrays;
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
import com.pure.selfservice.pages.CustomerDashboardPage;
import com.pure.selfservice.pages.CustomerServicePage;
import com.pure.selfservice.pages.CustomerSignInPage;
import com.relevantcodes.extentreports.ExtentTest;

@Listeners(EmailReport.class)

public class Renewal_18691  extends BaseTest {
	private String webSite;
	String sheetName = "Renewal_18691_";

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
		String className = "Renewal_18691_" + env;
		return DataUtils.testDatabyID(testcaseId, className);
	}
	
	//******************* User story 22748 Renewal Motor Policy *****************

	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for an Insurer led Motor policy when it moves from Invited stage to conformed stage. (Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23338(String browser) throws Exception {

		String tcId = "TC_23338";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for a Broker led Motor policy when it moves from Invited stage to conformed stage. (Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23340(String browser) throws Exception {

		String tcId = "TC_23340";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that Documents are correctly produce (i.e. Premium, Fees & Add-ons) for an Broker led Motor policy when it moves from conformed stage to Complete renewal through BO", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23341(String browser) throws Exception {

		String tcId = "TC_23341";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for an Insurer led Motor policy when it moves from Invited stage to conformed stage. (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23342(String browser) throws Exception {

		String tcId = "TC_23342";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for a Broker led Motor policy when it moves from Invited stage to conformed stage. (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23344(String browser) throws Exception {

		String tcId = "TC_23344";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that Documents are correctly produce (i.e. Premium, Fees & Add-ons) for an Broker led Motor policy when it moves from conformed stage to Complete renewal through BO", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23345(String browser) throws Exception {

		String tcId = "TC_23345";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that Documents  are correctly produce (i.e. Premium, Fees & Add-ons) for an broker led Motor policy when it moves from conformed stage to Complete renewal through BO and permanent MTA is processed on policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23347(String browser) throws Exception {

		String tcId = "TC_23347";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that Documents  are correctly produce (i.e. Premium, Fees & Add-ons) for an broker led Motor policy when it moves from conformed stage to Complete renewal through BO and permanent MTA is processed on policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23348(String browser) throws Exception {

		String tcId = "TC_23348";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Insurer led Motor policy when it moves from Invited stage to conformed stage. (Payment method Premium finance with deposit)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23349(String browser) throws Exception {

		String tcId = "TC_23349";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for a Broker led Motor policy when it moves from Invited stage to conformed stage. (Payment method premium finance with deposit)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23350(String browser) throws Exception {

		String tcId = "TC_23350";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

			Log.softAssertThat(	customerDashboardPage.verifyLabelInPolicyDetailSection(policyName, lstLabelToVerify, extentedReport),
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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that Documents are correctly produce (i.e. Premium, Fees & Add-ons)  for an Broker led Motor policy when it moves from conformed stage to Complete renewal through BO", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23351(String browser) throws Exception {

		String tcId = "TC_23351";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Insurer led Motor policy when it moves from Invited stage to conformed stage and permanent  MTA is processed on policy (Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23352(String browser) throws Exception {

		String tcId = "TC_23352";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Insurer led Motor policy when it moves from Invited stage to conformed stage and open claim is available on policy (Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23354(String browser) throws Exception {

		String tcId = "TC_23354";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Insurer led Motor policy when it moves from Invited stage to conformed stage and permanent  MTA is processed on policy (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23356(String browser) throws Exception {

		String tcId = "TC_23356";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Insurer led Motor policy when it moves from Invited stage to conformed stage and open claim is available on policy (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23357(String browser) throws Exception {

		String tcId = "TC_23357";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for an Broker led Motor policy when it moves from Invited stage to conformed stage and permanent MTA is processed on policy (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23359(String browser) throws Exception {

		String tcId = "TC_23359";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for an Broker led Motor policy when it moves from Invited stage to conformed stage and open claim is available on policy (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23360(String browser) throws Exception {

		String tcId = "TC_23360";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that Documents  are correctly produce (i.e. Premium, Fees & Add-ons) for an broker led Motor policy when it moves from conformed stage to Complete renewal through BO and open claim is available on policy. (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23361(String browser) throws Exception {

		String tcId = "TC_23361";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for an Broker led Motor policy when it moves from Invited stage to conformed stage and permanent MTA is processed on policy (Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23362(String browser) throws Exception {

		String tcId = "TC_23362";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for an Broker led Motor policy when it moves from Invited stage to conformed stage and open claim is available on policy (Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23363(String browser) throws Exception {

		String tcId = "TC_23363";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that Documents are correctly produce (i.e. Premium, Fees & Add-ons) for an broker led Motor policy when it moves from conformed stage to Complete renewal through BO and open claim is available on policy.(Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23364(String browser) throws Exception {

		String tcId = "TC_23364";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for an Insurer led Motor policy when it moves from Invited stage to conformed stage and permanent MTA is processed on policy (Payment method  premium finance  with deposit)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23366(String browser) throws Exception {

		String tcId = "TC_23366";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Insurer led Motor policy when it moves from Invited stage to conformed stage and open claim is available on policy (Payment method premium finance with deposit)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23368(String browser) throws Exception {

		String tcId = "TC_23368";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for an broker led Motor policy when it moves from Invited stage to conformed stage with alternate insurer. (Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23371(String browser) throws Exception {

		String tcId = "TC_23371";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for an broker led Motor policy when it moves from Invited stage to conformed stage with alternate insurer and MTA is processed on policy. (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23375(String browser) throws Exception {

		String tcId = "TC_23375";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for an broker led Motor policy when it moves from Invited stage to conformed stage with alternate insurer and open claim is available on policy. (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23378(String browser) throws Exception {

		String tcId = "TC_23378";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Insurer led Household policy when it moves from Invited stage to conformed stage. (Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23379(String browser) throws Exception {

		String tcId = "TC_23379";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for a Broker led Household policy when it moves from Invited stage to conformed stage. (Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23381(String browser) throws Exception {

		String tcId = "TC_23381";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that Documents are correctly produce (i.e. Premium, Fees & Add-ons) for an Broker led Household policy when it moves from conformed stage to Complete renewal through BO", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23382(String browser) throws Exception {

		String tcId = "TC_23382";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for an Insurer led Household policy when it moves from Invited stage to conformed stage. (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23383(String browser) throws Exception {

		String tcId = "TC_23383";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for a Broker led Household policy when it moves from Invited stage to conformed stage. (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23385(String browser) throws Exception {

		String tcId = "TC_23385";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that Documents are correctly produce (i.e. Premium, Fees & Add-ons) for an Broker led Household policy when it moves from conformed stage to Complete renewal through BO", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23386(String browser) throws Exception {

		String tcId = "TC_23386";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that Documents are correctly produce (i.e. Premium, Fees & Add-ons) for an broker led Household policy when it moves from conformed stage to Complete renewal through BO and permanent MTA is processed on policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23388(String browser) throws Exception {

		String tcId = "TC_23388";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that Documents  are correctly produce (i.e. Premium, Fees & Add-ons) for an broker led Household policy when it moves from conformed stage to Complete renewal  through  BO and permanent MTA is processed on policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23389(String browser) throws Exception {

		String tcId = "TC_23389";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for a Broker led Household policy when it moves from Invited stage to conformed stage. (Payment method premium finance with deposit)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23390(String browser) throws Exception {

		String tcId = "TC_23390";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that Documents are correctly produce (i.e. Premium, Fees & Add-ons)  for an Broker led Household policy when it moves from conformed stage to Complete renewal  through  BO", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23391(String browser) throws Exception {

		String tcId = "TC_23391";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Insurer led Household policy when it moves from Invited stage to conformed stage and permanent  MTA is processed on policy (Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23392(String browser) throws Exception {

		String tcId = "TC_23392";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Insurer led Household policy when it moves from Invited stage to conformed stage and open claim is available on policy (Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23393(String browser) throws Exception {

		String tcId = "TC_23393";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Insurer led Household policy when it moves from Invited stage to conformed stage and permanent  MTA is processed on policy (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23395(String browser) throws Exception {

		String tcId = "TC_23395";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Insurer led Household policy when it moves from Invited stage to conformed stage and open claim is available on policy (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23396(String browser) throws Exception {

		String tcId = "TC_23396";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Broker led Household policy when it moves from Invited stage to conformed stage and permanent  MTA is processed on policy (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23398(String browser) throws Exception {

		String tcId = "TC_23398";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Broker led Household policy when it moves from Invited stage to conformed stage and open claim is available on policy (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23399(String browser) throws Exception {

		String tcId = "TC_23399";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that Documents  are correctly produce (i.e. Premium, Fees & Add-ons) for an broker led Household policy when it moves from conformed stage to Complete renewal  through  BO and open claim is available on policy. (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23400(String browser) throws Exception {

		String tcId = "TC_23400";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Broker led Household policy when it moves from Invited stage to conformed stage and permanent  MTA is processed on policy (Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23401(String browser) throws Exception {

		String tcId = "TC_23401";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Broker led Household policy when it moves from Invited stage to conformed stage and open claim is available on policy (Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23402(String browser) throws Exception {

		String tcId = "TC_23402";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that Documents  are correctly produce (i.e. Premium, Fees & Add-ons) for an broker led Household policy when it moves from conformed stage to Complete renewal  through  BO and open claim is available on policy. (Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23403(String browser) throws Exception {

		String tcId = "TC_23403";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Insurer led Household policy when it moves from Invited stage to conformed stage and open claim is available on policy (Payment method premium finance with deposit)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23404(String browser) throws Exception {

		String tcId = "TC_23404";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Insurer led Household policy when it moves from Invited stage to conformed stage and permanent  MTA is processed on policy (Payment method  premium finance  with deposit)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23406(String browser) throws Exception {

		String tcId = "TC_23406";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an Insurer led Household policy when it moves from Invited stage to conformed stage and open claim is available on policy (Payment method premium finance with deposit)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23407(String browser) throws Exception {

		String tcId = "TC_23407";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an broker led Household policy when it moves from Invited stage to conformed stage with alternate insurer. (Payment method old premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23410(String browser) throws Exception {

		String tcId = "TC_23410";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons)  is correctly displayed for an broker led Household policy when it moves from Invited stage to conformed stage with alternate insurer and MTA is processed on policy. (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23416(String browser) throws Exception {

		String tcId = "TC_23416";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "This Scenario is to test that account posting and Documents (i.e. Premium, Fees & Add-ons) is correctly displayed for an broker led Household policy when it moves from Invited stage to conformed stage with alternate insurer and open claim is available on policy. (Payment method new premium finance)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23418(String browser) throws Exception {

		String tcId = "TC_23418";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("Policy_Number");
		String Label = testData.get("LabelName");
		String description = testData.get("TC_Description");
		String PolicyDetails = testData.get("policyDetails");

		List<String> lstLabelToVerify = Arrays.asList(Label.split(","));

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
			
			customerDashboardPage.clickPolicyFromList(policyName, extentedReport, true);
			List<String> policyDetails = Arrays.asList(PolicyDetails.split(","));
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

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
			
			customerDashboardPage.clickDocumentView(policyName, driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
}
