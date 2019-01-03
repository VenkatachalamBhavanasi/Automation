package com.pure.selfservice.testscripts;

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
import com.generic.utils.GenericUtils;
import com.pure.selfservice.pages.CustomerDashboardPage;
import com.pure.selfservice.pages.CustomerServicePage;
import com.pure.selfservice.pages.CustomerSignInPage;
import com.relevantcodes.extentreports.ExtentTest;

@Listeners(EmailReport.class)
public class SS_1507 extends BaseTest {
	private String webSite;

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
		String className = "SS_1507_" + env;
		return DataUtils.testDatabyID(testcaseId, className);
	}

	/** 1518 Story **/

	@Test(description = "Check that the personal details section on the SS dashboard and Check that Analytic Client Data", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_032_36(String browser) throws Exception {

		String tcId = "TC_032_36";
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
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Customer Name is Verified : " + customerDashboardPage.getCustomerName(),
					"Customer Name is not Verified : " + customerDashboardPage.getCustomerName(), driver,
					extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustAddr"),
							customerDashboardPage),
					"Customer Address is Verified", "Customer Address is not Verified", driver, extentedReport, true);
			Log.softAssertThat(customerDashboardPage.verifyContactDetails(testData, extentedReport),
					"Contact details are verified", "Contact details not verified");
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtDateOfBirth"),
							customerDashboardPage),
					"Customer Date of Birth is Verified", "Customer Data of Birth is not Verified", driver,
					extentedReport, true);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the first character for the field Work Telephone Number in the Correspondence preference popup  is in upper case", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_037(String browser) throws Exception {

		String tcId = "TC_037";
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
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);
		
			Log.softAssertThat(customerDashboardPage.verifyTelphoneLabel(extentedReport),
					"Telephone feild is displayed in Correspondance preference",
					"Telephone feild is not displayed in Correspondance preference as it is not selected in Pure Back office", driver,
					extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	/** 1519 Story **/

	@Test(description = "Check that the policies and quotes ribbon details ", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_038_054(String browser) throws Exception {

		String tcId = "TC_038_054";
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
					customerDashboardPage.verifyPolicyHeaderDetails(testData.get("PolicyName"), extentedReport),
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
	public void TC_039_040(String browser) throws Exception {

		String tcId = "TC_039_040";
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
					"All polices motor accepted policies", "Policies displaying not motor accepted policies", driver,
					extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	/** 1520 Story **/

	@Test(description = "Check that the policy information is updated when the user selects a different policy from the list", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_053(String browser) throws Exception {

		String tcId = "TC_053";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		String policyName = testData.get("PolicyName");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.pass("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, false);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, false);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);
			customerDashboardPage.clickExpand(extentedReport, true);
			Log.softAssertThat(customerDashboardPage.getPolicyDetails("Policy No").contains(policyName),
					"Policy number value Verified", "Policy number value not Verified", driver, extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtBusinessValue"),
							customerDashboardPage),
					"Line of business value Verified : " + customerDashboardPage.getPolicyDetails("Business"),
					"Line of business value not Verified : " + customerDashboardPage.getPolicyDetails("Business"),
					driver, extentedReport, true);
			Log.softAssertThat(customerDashboardPage.getPolicyDetails("Policy Status").contains("Accepted"),
					"Status of the policy Verified : " + customerDashboardPage.getPolicyDetails("Policy Status"),
					"Status of the policy not Verified : " + customerDashboardPage.getPolicyDetails("Policy Status"),
					driver, extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtStartDate"),
							customerDashboardPage),
					"Start Date Verified : " + customerDashboardPage.getPolicyDetails("Start Date"),
					"Start Date not Verified : " + customerDashboardPage.getPolicyDetails("Start Date"), driver,
					extentedReport, true);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that both broker and insurer add-ons are displayed on SS dashboard if the client has included any add-on to the policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_056(String browser) throws Exception {

		String tcId = "TC_056";
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
			/*Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("tabAddons"),
							customerDashboardPage),
					"Additional Products is Verified", "Additional product is not Verified", driver, extentedReport,
					true);
			Log.softAssertThat(customerDashboardPage.getMultipleAddOnsLength() == 1,
					"Pure Addons are available and Insurer add-ons not available :"
							+ customerDashboardPage.getMultipleAddOnsLength(),
					"Application Failure - Pure & Insurer Addons are not displayed :"
							+ customerDashboardPage.getMultipleAddOnsLength(),
					driver, extentedReport, true);*/
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that Multiple policy details are in collapsed mode by default", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void Adh_4(String browser) throws Exception {

		String tcId = "Adh_4";
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
	public void TC_050(String browser) throws Exception {

		String tcId = "TC_050";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("PolicyName");
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
			//expectedPolicyDetail.put("InsuredItem", testData.get("Insured_Item"));
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

	/** 2514 Story **/

	@Test(description = "Check that 'Manage Contact Details' modal is opened when click on Manage Contact Details button from the SS dashboard", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_075(String browser) throws Exception {
		String tcId = "TC_075";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(emailAddress,
					password, extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Customer Dashboardpage is verified", "Customer Dashboardpage is not Verified", driver,
					extentedReport, true);
			Log.softAssertThat(customerDashboardPage.verifyManageContactDetailsModalOpened(extentedReport),
					"Verified that the 'Manage Contact Details' modal is opened",
					"The 'Manage Contact Details' modal is not opened", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that 'Correspondence Preference' modal is opened when click on Manage Preferences button from the SS dashboard", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_076(String browser) throws Exception {
		String tcId = "TC_076";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(emailAddress,
					password, extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Customer Dashboardpage is verified", "Customer Dashboardpage is not Verified", driver,
					extentedReport, true);
			Log.softAssertThat(customerDashboardPage.verifyCorrespondencePreferenceModalOpened(extentedReport),
					"Verified that the 'Correspondence Preference' modal is opened",
					"The 'Correspondence Preference' modal is not opened", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the customer is able to edit their mailing address within Manage Contact Details modal", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_077(String browser) throws Exception {
		String tcId = "TC_077";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(emailAddress,
					password, extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Customer Dashboardpage is verified", "Customer Dashboardpage is not Verified", driver,
					extentedReport, true);
			Log.softAssertThat(customerDashboardPage.verifyManageContactDetailsModalOpened(extentedReport),
					"Verified that the 'Manage Contact Details' modal is opened",
					"The 'Manage Contact Details' modal is not opened", driver, extentedReport, true);
			Log.softAssertThat(customerDashboardPage.verifyMailingAddressUpdated(extentedReport),
					"Verified that the mailing address is updated successfully", "The mailing address is not updated",
					driver, extentedReport, true);
			Log.message("Check analytic client data manually.", driver, extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the system does not allow more than one unique email id in Manage Conact details screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_085(String browser) throws Exception {
		String tcId = "TC_085";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(emailAddress,
					password, extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Customer Dashboardpage is verified", "Customer Dashboardpage is not Verified", driver,
					extentedReport, true);
			Log.softAssertThat(customerDashboardPage.verifyManageContactDetailsModalOpened(extentedReport),
					"Verified that the 'Manage Contact Details' modal is opened",
					"The 'Manage Contact Details' modal is not opened", driver, extentedReport, true);
			String existingEmailAddress = testData.get("ExistingEmailAddress");
			customerDashboardPage.verifyEmailAddressChanged(existingEmailAddress, extentedReport);
			customerDashboardPage.enterPassword(password, extentedReport, true);
			customerDashboardPage.clickOkButton(extentedReport, true);
			/*Log.softAssertThat(!customerDashboardPage.verifyEmailAddressChanged(existingEmailAddress, extentedReport),
					"Verified that the modal is not closed as a token of accepting the change of email address",
					"The 'Manage Contact Details' modal is closed as a token of accepting the change of email address",
					driver, extentedReport, true);*/
			Log.softAssertThat(customerDashboardPage.verifyChangeEmailAddressErrorMsg(extentedReport),
					"Verified that the error message is displayed successfully",
					"Application Error : The error message is not verified successfully", driver, extentedReport, true);
			Log.message("Limitation :: PURE BROKER STEPS WERE NOT COVERED/AUTOMATED", extentedReport);
			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the user is able to login with New email id and existing password when the user changes the email id", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_086(String browser) throws Exception {

		String tcId = "TC_086";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(emailAddress,
					password, extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Customer Dashboardpage is verified", "Customer Dashboardpage is not Verified", driver,
					extentedReport, true);

			Log.softAssertThat(customerDashboardPage.verifyManageContactDetailsModalOpened(extentedReport),
					"Verified that the 'Manage Contact Details' modal is opened",
					"The 'Manage Contact Details' modal is not opened", driver, extentedReport, true);
			String invalidEmailAddress = GenericUtils.getRandomCharacters("ALPHANUMERIC", 4) + "@com";
			Log.softAssertThat(customerDashboardPage.validateEmailAddress(invalidEmailAddress, extentedReport),
					"Validated the email address field successfully : " + invalidEmailAddress,
					"Not validated the email address : " + invalidEmailAddress, driver, extentedReport, true);
			Log.softAssertThat(customerDashboardPage.verifyInvalidEmailAddressErrorMsg(extentedReport),
					"Error message displayed correctly for Invalid email address",
					"Error message not displayed correctly for Invalid email address");

			String newEmailAddress = GenericUtils.getRandomCharacters("ALPHANUMERIC", 6) + "@test.com";
			Log.softAssertThat(customerDashboardPage.verifyEmailAddressChanged(newEmailAddress, extentedReport),
					"Verified the mailing address is updated successfully : " + newEmailAddress,
					"The mailing address is not updated : " + newEmailAddress, driver, extentedReport, true);
			customerDashboardPage.enterPassword(password, extentedReport, true);
			customerDashboardPage.clickOkButton(extentedReport, true);
			//customerDashboardPage.clickOkInChangeAdressPopUp(extentedReport);
			Log.message("Check analytic client data manually.", driver, extentedReport);

			// Verification of updated email
			selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(newEmailAddress, password,
					extentedReport, true);
			Log.message("Able to login with new email address : " + newEmailAddress, extentedReport);

			// Reverting the email
			Log.message("Reverting the email address", extentedReport);
			Log.softAssertThat(customerDashboardPage.verifyManageContactDetailsModalOpened(extentedReport),
					"Verified that the 'Manage Contact Details' modal is opened",
					"The 'Manage Contact Details' modal is not opened", driver, extentedReport, true);
			Log.softAssertThat(customerDashboardPage.verifyEmailAddressChanged(emailAddress, extentedReport),
					"The mailing address is reverted successfully", "The mailing address is not reverted", driver,
					extentedReport, true);
			customerDashboardPage.enterPassword(password, extentedReport, true);
			customerDashboardPage.clickOkButton(extentedReport, true);
			//customerDashboardPage.clickOkInChangeAdressPopUp(extentedReport);
			selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(emailAddress, password,
					extentedReport, true);

			Log.message("Able to login with old email address : " + emailAddress, extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that Manage Contact details after entering the Invalid postcode is always in loading and unable to perform further actions.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void ADH_005_006(String browser) throws Exception {
		String tcId = "ADH_005_006";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(emailAddress,
					password, extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Customer Dashboardpage is verified", "Customer Dashboardpage is not Verified", driver,
					extentedReport, true);

			Log.softAssertThat(customerDashboardPage.verifyManageContactDetailsModalOpened(extentedReport),
					"Verified that the 'Manage Contact Details' modal is opened",
					"The 'Manage Contact Details' modal is not opened", driver, extentedReport, true);
			String invalidPostcode = "LA1 1LA GA";
			Log.softAssertThat(!customerDashboardPage.verifyFindAddressValue(invalidPostcode, extentedReport),
					"Verified that the address is not found for the invalid post code",
					"The address is found for the invalid postcode", driver, extentedReport, true);
			Log.softAssertThat(customerDashboardPage.verifyPostcodeValidationErrorMsg(extentedReport),
					"Verified that the correct validation error message is displayed for the invalid postcode",
					"The validation error message is incorrect for the invalid postcode", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that 'X' button is displayed on the Manage contact details popup and Correspondence preference popup and check valid Phone number is updated", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_087(String browser) throws Exception {
		String tcId = "TC_087";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String homePhone = testData.get("Home_Phone");
		String mobilePhone = testData.get("Mobile_Phone");
		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(emailAddress,
					password, extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Customer Dashboardpage is verified", "Customer Dashboardpage is not Verified", driver,
					extentedReport, true);

			Log.softAssertThat(customerDashboardPage.verifyCorrespondencePreferenceModalOpened(extentedReport),
					"Verified that the 'Correspondence Preference' modal is opened",
					"The 'Correspondence Preference' modal is not opened", driver, extentedReport, true);
			Log.softAssertThat(customerDashboardPage.verifyCancelIcon(extentedReport),
					"Verified that the cancel icon on 'Correspondence Preference' modal pop up",
					"cancel icon not displaying 'Correspondence Preference' modal pop up", driver, extentedReport,
					true);
			customerDashboardPage.clickCancelIconInManagePreference(extentedReport);
			customerDashboardPage.clickManageContactDetailsBtn(extentedReport);
			customerDashboardPage.enterPhoneNumber("Home", homePhone, extentedReport);
			customerDashboardPage.enterPhoneNumber("Mobile", mobilePhone, extentedReport);
			customerDashboardPage.clickSaveInPhoneEmail(extentedReport);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Correspondance modal is closed", "Correspondance modal is not closed", driver,
					extentedReport, true);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that 'Marketing Preference' inside Correspondence preference is updated when selecting a checkbox and click on Save button", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_079_093(String browser) throws Exception {
		String tcId = "TC_079_093";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(emailAddress,
					password, extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Customer Dashboardpage is verified", "Customer Dashboardpage is not Verified", driver,
					extentedReport, true);

			// 1st verification (i.e., 'Yes' if 'No', vice versa)
			Log.softAssertThat(customerDashboardPage.verifyCorrespondencePreferenceModalOpened(extentedReport),
					"Verified that the 'Correspondence Preference' modal is opened",
					"The 'Correspondence Preference' modal is not opened", driver, extentedReport, true);
			Log.softAssertThat(customerDashboardPage.verifyMarketingPreferenceUpdated(extentedReport),
					"Verified that the 'Marketing Preference' update is successful",
					"The 'Marketing Preference' update is unsuccessful", driver, extentedReport, true);

			// 2nd verification
			Log.softAssertThat(customerDashboardPage.verifyManageContactDetailsModalOpened(extentedReport),
					"Verified that the 'Manage Contact Details' modal is opened",
					"The 'Manage Contact Details' modal is not opened", driver, extentedReport, true);
			Log.softAssertThat(customerDashboardPage.verifyMailingAddressUpdated(extentedReport),
					"Verified that the mailing address is updated successfully", "The mailing address is not updated",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

}
