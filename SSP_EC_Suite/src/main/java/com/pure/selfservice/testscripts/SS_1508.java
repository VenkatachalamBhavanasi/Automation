package com.pure.selfservice.testscripts;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.generic.support.BaseTest;
import com.generic.support.EmailReport;
import com.generic.support.Log;
import com.generic.support.WebDriverFactory;
import com.generic.utils.DataProviderUtils;
import com.generic.utils.DataUtils;
import com.pure.selfservice.pages.CustomerDashboardPage;
import com.pure.selfservice.pages.CustomerServicePage;
import com.pure.selfservice.pages.CustomerSignInPage;

@Listeners(EmailReport.class)
public class SS_1508 extends BaseTest {
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
		String className = "SS_1508_" + env;
		return DataUtils.testDatabyID(testcaseId, className);
	}

	/** 1522 Story **/

	@Test(description = "To Check that the document is opened in a new window when 'View' button is clicked", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_088(String browser) throws Exception {

		String tcId = "TC_088";
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

	/** 1521 Story **/

	@Test(description = "Check that the client is only allowed to view the latest version of each document (eg. NB, MTA completed for a policy, then client should only be able to view MTA doc)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_060(String browser) throws Exception {

		String tcId = "TC_060";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String policyName = testData.get("PolicyName");
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
	public void TC_059(String browser) throws Exception {

		String tcId = "TC_059";
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

}
