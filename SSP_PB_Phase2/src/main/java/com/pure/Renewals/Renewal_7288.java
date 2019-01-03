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

public class Renewal_7288  extends BaseTest {
	private String webSite;
	String sheetName = "Renewal_7288_";

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
		String className = "Renewal_7288_" + env;
		return DataUtils.testDatabyID(testcaseId, className);
	}
	
	//******************* User story 7288 Renewal Motor Policy *****************

	@Test(description = "This Scenario is to test that Status on Self Service the 'Policy search and Dashboard' is accepted when Insurer led Motor policy is in selected stage and view my renewal is not available.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23804(String browser) throws Exception {

		String tcId = "TC_23804";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led Motor policy is in Quoted stage and view my renewal is not available.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23805(String browser) throws Exception {

		String tcId = "TC_23805";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
			Log.softAssertThat(
					customerDashboardPage.verifyDetailInPolicyribban1(policyName, policyDetails, extentedReport),
					"Policy ribbon details section has the following details - Policy No, Line Of Business, Status, Start Date, Premium",
					"Policy ribbon details section does not have anyone the following details - Policy No, Line Of Business, Insurer, Status, Start Date, Premium",
					driver, extentedReport, true);

			Log.softAssertThat(customerDashboardPage.verifyLabelInPolicyDetailSection(policyName, lstLabelToVerify,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led Motor policy is in selected stage and view my renewal is not available.(Suspended at Selected)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23806(String browser) throws Exception {

		String tcId = "TC_23806";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led Motor policy is in selected stage and view my renewal is not available.(Suspended at Quoted)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23807(String browser) throws Exception {

		String tcId = "TC_23807";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led Motor policy is in selected stage and view my renewal is not available.(Suspended at Invited)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23808(String browser) throws Exception {

		String tcId = "TC_23808";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led Motor policy is in selected stage and view my renewal is not available.(Suspended at Conformed)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23809(String browser) throws Exception {

		String tcId = "TC_23809";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led Motor policy is in Invited stage and view my renewal is available.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23810(String browser) throws Exception {

		String tcId = "TC_23810";
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
			
			Log.softAssertThat(customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is displayed in Dashborad","Renewal Policy is not displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is renewal accepted when Insurer led Motor  policy is conformed with Holding insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23811(String browser) throws Exception {

		String tcId = "TC_23811";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” when Insurer led Motor policy is conformed with alternate insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23812(String browser) throws Exception {

		String tcId = "TC_23812";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” when Insurer led Motor policy is conformed with alternate insurer through what if.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23813(String browser) throws Exception {

		String tcId = "TC_23813";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” when Insurer led Motor policy is conformed with alternate insurer through Manual rebroke.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23814(String browser) throws Exception {

		String tcId = "TC_23814";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” when Insurer led Motor policy is conformed with holding insurer through Manual rebroke.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23815(String browser) throws Exception {

		String tcId = "TC_23815";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is reverted to original if policy is revoke  from conform stage.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23816(String browser) throws Exception {

		String tcId = "TC_23816";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is displayed in Dashborad","Renewal Policy is not displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is reverted to original if policy is House kept from conform stage.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23817(String browser) throws Exception {

		String tcId = "TC_23817";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led Motor policy is Completed with Holding insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23818(String browser) throws Exception {

		String tcId = "TC_23818";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led Motor policy is completed with alternate insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23819(String browser) throws Exception {

		String tcId = "TC_23819";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led Motor policy is in selected stage and view my renewal is not available.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23820(String browser) throws Exception {

		String tcId = "TC_23820";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led Motor policy is in Quoted stage and view my renewal is not available.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23821(String browser) throws Exception {

		String tcId = "TC_23821";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led Motor policy is in selected stage and view my renewal is not available.(Suspended at Selected)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23822(String browser) throws Exception {

		String tcId = "TC_23822";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led Motor policy is in selected stage and view my renewal is not available.(Suspended at Quoted)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23823(String browser) throws Exception {

		String tcId = "TC_23823";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led Motor policy is in Invited stage and view my renewal is not available.(Suspended at Invited)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23824(String browser) throws Exception {

		String tcId = "TC_23824";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led Motor policy is in Conformed stage and view my renewal is not available.(Suspended at Conformed)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23825(String browser) throws Exception {

		String tcId = "TC_23825";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led Motor policy is in Invited stage and view my renewal is available.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23826(String browser) throws Exception {

		String tcId = "TC_23826";
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
			
			Log.softAssertThat(customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is displayed in Dashborad","Renewal Policy is not displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is renewal accepted when Broker led Motor policy is conformed with Holding insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23827(String browser) throws Exception {

		String tcId = "TC_23827";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” when Broker led Motor policy is conformed with alternate insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23828(String browser) throws Exception {

		String tcId = "TC_23828";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” when Insurer led Motor policy is conformed with holding insurer through Manual rebroke.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23829(String browser) throws Exception {

		String tcId = "TC_23829";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when broker led Motor  policy is Completed with Holding insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23830(String browser) throws Exception {

		String tcId = "TC_23830";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led Motor policy is completed with alternate insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23831(String browser) throws Exception {

		String tcId = "TC_23831";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led HH policy is in selected stage and view my renewal is not available.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23832(String browser) throws Exception {

		String tcId = "TC_23832";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led HH policy is in Quoted stage and view my renewal is not available.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23833(String browser) throws Exception {

		String tcId = "TC_23833";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led HH policy is in selected stage and view my renewal is not available.(Suspended at Selected)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23834(String browser) throws Exception {

		String tcId = "TC_23834";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led HH policy is in selected stage and view my renewal is not available.(Suspended at Quoted)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23835(String browser) throws Exception {

		String tcId = "TC_23835";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led HH policy is in selected stage and view my renewal is not available.(Suspended at Invited)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23836(String browser) throws Exception {

		String tcId = "TC_23836";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led HH policy is in selected stage and view my renewal is not available.(Suspended at Conformed)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23837(String browser) throws Exception {

		String tcId = "TC_23837";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led HH policy is in Invited stage and view my renewal is available.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23838(String browser) throws Exception {

		String tcId = "TC_23838";
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
			
			Log.softAssertThat(customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is displayed in Dashborad","Renewal Policy is not displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is renewal accepted when Insurer led HH  policy is conformed with Holding insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23839(String browser) throws Exception {

		String tcId = "TC_23839";
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
			
			Log.softAssertThat(customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is displayed in Dashborad","Renewal Policy is not displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” when Insurer led HH policy is conformed with alternate insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23840(String browser) throws Exception {

		String tcId = "TC_23840";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” when Insurer led HH policy is conformed with alternate insurer through what if.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23841(String browser) throws Exception {

		String tcId = "TC_23841";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” when Insurer led HH policy is conformed with alternate insurer through Manual rebroke.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23842(String browser) throws Exception {

		String tcId = "TC_23842";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” when Insurer led HH policy is conformed with holding insurer through Manual rebroke.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23843(String browser) throws Exception {

		String tcId = "TC_23843";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is reverted to original if policy is revoke  from conform stage.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23844(String browser) throws Exception {

		String tcId = "TC_23844";
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
			
			Log.softAssertThat(customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is displayed in Dashborad","Renewal Policy is not displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is reverted to original if policy is House kept from conform stage.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23845(String browser) throws Exception {

		String tcId = "TC_23845";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led HH policy is Completed with Holding insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23846(String browser) throws Exception {

		String tcId = "TC_23846";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Insurer led HH policy is completed with alternate insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23847(String browser) throws Exception {

		String tcId = "TC_23847";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led HH policy is in selected stage and view my renewal is not available.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23848(String browser) throws Exception {

		String tcId = "TC_23848";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led HH policy is in Quoted stage and view my renewal is not available.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23849(String browser) throws Exception {

		String tcId = "TC_23849";
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
			
			Log.softAssertThat(customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led HH policy is in selected stage and view my renewal is not available.(Suspended at Selected)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23850(String browser) throws Exception {

		String tcId = "TC_23850";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led HH policy is in selected stage and view my renewal is not available.(Suspended at Quoted)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23851(String browser) throws Exception {

		String tcId = "TC_23851";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led HH policy is in Invited stage and view my renewal is not available.(Suspended at Invited)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23852(String browser) throws Exception {

		String tcId = "TC_23852";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led HH policy is in Conformed stage and view my renewal is not available.(Suspended at Conformed)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23853(String browser) throws Exception {

		String tcId = "TC_23853";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led HH policy is in Invited stage and view my renewal is available.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23854(String browser) throws Exception {

		String tcId = "TC_23854";
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
			
			Log.softAssertThat(customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is displayed in Dashborad","Renewal Policy is not displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is renewal accepted when Broker led HH policy is conformed with Holding insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23855(String browser) throws Exception {

		String tcId = "TC_23855";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” when Broker led HH policy is conformed with alternate insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23856(String browser) throws Exception {

		String tcId = "TC_23856";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” when Insurer led HH policy is conformed with holding insurer through Manual rebroke.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23857(String browser) throws Exception {

		String tcId = "TC_23857";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when broker led HH  policy is Completed with Holding insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23858(String browser) throws Exception {

		String tcId = "TC_23858";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
	
	@Test(description = "This Scenario is to test that Status on Self Service “Policy search and Dashboard” is accepted when Broker led HH policy is completed with alternate insurer.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23859(String browser) throws Exception {

		String tcId = "TC_23859";
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
			
			Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad","Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
			
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

			Log.softAssertThat(customerDashboardPage.verifyDetailInPolicyDetailSection(policyName, expectedPolicyDetail,
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
