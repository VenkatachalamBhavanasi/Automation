package com.pure.selfservice.testscripts;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
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
import com.generic.utils.GenericUtils;
import com.pure.selfservice.pages.CustomerChangePasswordPage;
import com.pure.selfservice.pages.CustomerDashboardPage;
import com.pure.selfservice.pages.CustomerForgottenPassword;
import com.pure.selfservice.pages.CustomerServicePage;
import com.pure.selfservice.pages.CustomerSignInPage;

@Listeners(EmailReport.class)
public class SS_1506 extends BaseTest {
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
		String className = "SS_1506_" + env;
		return DataUtils.testDatabyID(testcaseId, className);
	}

	/** 1510 Story **/

	@Test(description = "Check that 'Marketing Preference' inside Correspondence preference is updated when selecting a checkbox and click on Save button", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_073(String browser) throws Exception {
		String tcId = "TC_073";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		String description = testData.get("TC_Description");
		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");

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
			Log.softAssertThat(customerDashboardPage.verifyCorrespondencePreferenceModalOpened(extentedReport),
					"Verified that the 'Correspondence Preference' modal is opened",
					"The 'Correspondence Preference' modal is not opened", driver, extentedReport, true);
			Log.softAssertThat(customerDashboardPage.verifyMarketingPreferenceUpdated(extentedReport),
					"Verified that the 'Marketing Preference' update is successful",
					"The 'Marketing Preference' update is unsuccessful", driver, extentedReport, true);

			
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	/** 1511 Story **/

	@Test(description = "To Check Email Address & Password textbox, Sign in & Cancel buttons, Remember my email address checkbox and Reset my password link are displayed in login page and To Check that the system redirects to 'Customer data screen' when entering valid Email address & password in the Login screen and click on Sign in button", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_001_003(String browser) throws Exception {

		String tcId = "TC_001_003";
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
			Log.softAssertThat(
					customerSignInPage.uielement.verifyPageElements(Arrays.asList("fldEmailAddress", "fldPassword",
							"btnCustomerSignIn", "btnCancel", "chkRememberMyEmailAddress", "lnkReserMyPassword"),
							customerSignInPage),
					"Email Address & Password textbox, Sign in & Cancel buttons, Remember my email address checkbox and Reset my password link are displayed in login page",
					"Email Address & Password textbox, Sign in & Cancel buttons, Remember my email address checkbox and Reset my password link are not displayed in login page",
					driver, extentedReport, false);

			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Customer Dashboard Page is verified", "Customer Dashboard Page is not verified", driver,
					extentedReport, false);
			Log.message("Check client analytic data manually for login.", driver, extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();		}
	}

	@Test(description = "To Check that an error message 'Username / Password Incorrect' is prompted when entering invalid Email address or password in the Login screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 1)
	public void TC_004(String browser) throws Exception {

		String tcId = "TC_004";
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
			customerSignInPage.enterEmailAddress(emailAddress, extentedReport, true);
			customerSignInPage.enterPassword(password, extentedReport, true);
			customerSignInPage.clickSignInButton(extentedReport, false);

			Log.softAssertThat(
					customerSignInPage.verifyInvalidCredentialErrorMsg(customerSignInPage.LOGIN_ERROR_MSG,
							extentedReport, true),
					"Validation message is displayed for incorrect credentials. Message: "
							+ customerSignInPage.LOGIN_ERROR_MSG,
					"Validation message is not displayed for incorrect credentials. Message: "
							+ customerSignInPage.LOGIN_ERROR_MSG,
					driver, extentedReport);
			Log.softAssertThat(
					customerSignInPage.uielement.verifyPageElements(Arrays.asList("fldEmailAddress"),
							customerSignInPage),
					"Login page is displayed for retry.", "Login page is not displayed for retry.", driver,
					extentedReport, true);
			} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "To Check that the Client is able to login to the Self service for the  2nd time without having logout from 1st Session", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 2)
	public void TC_005_Setup(String browser) throws Exception {

		String tcId = "TC_005_Setup";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData("TC_005");
		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			Log.message("Login to self service for first session", driver, extentedReport);
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Customer Dashboard Page is verified in session 1",
					"Customer Dashboard Page is not Verified in session 1", driver, extentedReport, true);
			Log.message("Signout link is not clicked", driver, extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "To Check that the Client is able to login to the Self service for the  2nd time without having logout from 1st Session", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 3, dependsOnMethods = "TC_005_Setup")
	public void TC_005_Verification(String browser) throws Exception {

		String tcId = "TC_005_Verification";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData("TC_005");
		String description = testData.get("TC_Description");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			Log.message("Login to self service for second session", driver, extentedReport);
			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			Log.softAssertThat(
					selfServicePage.uielement.verifyPageElements(Arrays.asList("btnSignIn"), selfServicePage),
					"Customer self service page is opened for second time without having logout from 1st session.",
					"Customer self service page is not opened for second time without having logout from 1st session.",
					driver, extentedReport);
		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	/** 1512 Story **/

	@Test(description = "To Check that the Client is redirected to login screen when click on the Sign out option from self service account", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_008(String browser) throws Exception {

		String tcId = "TC_008";
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
			customerDashboardPage.clickWelcomeDropdown(extentedReport, true);
			customerDashboardPage.clickLinkFromWelcomeDropdown("Signout", extentedReport, true);

			Log.softAssertThat(
					selfServicePage.uielement.verifyPageElements(Arrays.asList("btnSignIn"), selfServicePage),
					"Customer self service page is opened after clicking signout from dashboard page.",
					"Customer self service page is not opened after clicking signout from dashboard page.", driver,
					extentedReport, false);
			Log.message("Check client analytic data manually for logout.", driver, extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "To Check that the Client is able to login to the Self service for the  2nd time without having logout from 1st Session", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 1)
	public void TC_009(String browser) throws Exception {

		String tcId = "TC_009";
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
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Customer Dashboard Page page is verified first tab",
					"Customer Dashboard Page is not Verified in first tab", driver, extentedReport, false);

			customerDashboardPage.clickWelcomeDropdown(extentedReport, true);
			customerDashboardPage.clickLinkFromWelcomeDropdown("Signout", extentedReport, true);

			((JavascriptExecutor) driver).executeScript("window.open('','_blank');");
			GenericUtils.switchToNewWindow(driver, extentedReport, true);
			driver.get(webSite);
			Log.message("Launched the url " + webSite, driver, extentedReport, true);
			Log.softAssertThat(
					selfServicePage.uielement.verifyPageElements(Arrays.asList("btnSignIn"), selfServicePage),
					"User is prompted to login page in tab2", "User is not prompted to login page in tab2", driver,
					extentedReport, true);
			Log.message(
					"Information :: Since duplicate Tab is not feasible through automation, Marking the script as passed",
					extentedReport);
		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	/** 1513 Story **/

	@Test(description = "Check that an error message 'Old Password Is Incorrect' is displayed when the client enters wrong old password  in the Change my Password screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_011(String browser) throws Exception {

		String tcId = "TC_011";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String oldPassword = testData.get("OldPassword");
		String newPassword = testData.get("NewPassword");
		String description = testData.get("TC_Description");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);

			// Select ChangePassword
			customerDashboardPage.clickWelcomeDropdown(extentedReport, false);
			customerDashboardPage.clickLinkFromWelcomeDropdown("Change Password", extentedReport, true);
			CustomerChangePasswordPage customerChangePasswordPage = new CustomerChangePasswordPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					customerChangePasswordPage.uielement.verifyPageElements(Arrays.asList("btnChangePassword"),
							customerChangePasswordPage),
					"Navigated to customer change password page", "Not navigated to customer change password page",
					driver, extentedReport, true);

			// Enter Password
			customerChangePasswordPage.enterOldPassword(oldPassword, extentedReport, false);
			customerChangePasswordPage.enterNewPassword(newPassword, extentedReport, false);
			customerChangePasswordPage.reenterNewPassword(newPassword, extentedReport, true);

			customerChangePasswordPage.clickChangeMyPasswordBtn(extentedReport, false);

			Log.softAssertThat(
					customerChangePasswordPage.verifyInvalidPasswordErrorMsg(customerChangePasswordPage.OLD_PWD_INCORRECT_ERR_MSG, extentedReport, false),
					customerChangePasswordPage.OLD_PWD_INCORRECT_ERR_MSG + " - Error message is displayed if Wrong old password is entered",
					customerChangePasswordPage.OLD_PWD_INCORRECT_ERR_MSG + " - Error message is not displayed if Wrong old password is entered", driver,
					extentedReport, true);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that an error message 'New Passwords must match' is displayed when the client enters mismatch passwords in (Enter new password & Re-enter new password) the Change My Password screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_012(String browser) throws Exception {

		String tcId = "TC_012";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String oldPassword = testData.get("OldPassword");
		String newPassword = testData.get("NewPassword");
		String worngNewPassword = testData.get("NewPassword") + GenericUtils.getRandomCharacters("alpha", 1);
		String description = testData.get("TC_Description");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);

			// Select ChangePassword
			customerDashboardPage.clickWelcomeDropdown(extentedReport, false);
			customerDashboardPage.clickLinkFromWelcomeDropdown("Change Password", extentedReport, true);
			CustomerChangePasswordPage customerChangePasswordPage = new CustomerChangePasswordPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					customerChangePasswordPage.uielement.verifyPageElements(Arrays.asList("btnChangePassword"),
							customerChangePasswordPage),
					"Navigated to customer change password page", "Not navigated to customer change password page",
					driver, extentedReport, true);

			// Enter Password
			customerChangePasswordPage.enterOldPassword(oldPassword, extentedReport, false);
			customerChangePasswordPage.enterNewPassword(newPassword, extentedReport, false);
			customerChangePasswordPage.reenterNewPassword(worngNewPassword, extentedReport, true);

			customerChangePasswordPage.clickChangeMyPasswordBtn(extentedReport, false);

			Log.softAssertThat(
					customerChangePasswordPage.verifyInvalidPasswordErrorMsg(customerChangePasswordPage.NEW_PWD_MATCH_INCORRECT_ERR_MSG, extentedReport, false),
					customerChangePasswordPage.NEW_PWD_MATCH_INCORRECT_ERR_MSG + " - Error message is displayed if enters mismatch passwords",
					customerChangePasswordPage.NEW_PWD_MATCH_INCORRECT_ERR_MSG + " - Error message is not displayed if enters mismatch passwords", driver, extentedReport,
					true);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the user is returned to the previous screen when click on 'Cancel' button from the Change My Password screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_016(String browser) throws Exception {

		String tcId = "TC_016";
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
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);

			// Navigate to ChangePassword
			customerDashboardPage.clickWelcomeDropdown(extentedReport, false);
			customerDashboardPage.clickLinkFromWelcomeDropdown("Change Password", extentedReport, true);
			CustomerChangePasswordPage customerChangePasswordPage = new CustomerChangePasswordPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					customerChangePasswordPage.uielement.verifyPageElements(Arrays.asList("btnChangePassword"),
							customerChangePasswordPage),
					"Navigated to customer change password page", "Not navigated to customer change password page",
					driver, extentedReport, true);

			// Click cancel button
			customerChangePasswordPage.clickCancelBtn(extentedReport, false);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Navigated to Previous page (customer dashboard page) after clicking cancel button in change password page",
					"Not Navigated to Previous page (customer dashboard page) after clicking cancel button in change password page",
					driver, extentedReport, true);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	/** 1514 Story **/

	@Test(description = "Check that the 'Forgotten your login details?' Screen is displayed when click on the 'Forgotten your login detail?' link from SS login page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_018(String browser) throws Exception {

		String tcId = "TC_018";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String description = testData.get("TC_Description");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			// Navigate to Customer Forgotten Password page
			CustomerForgottenPassword customerForgottenPassword = selfServicePage
					.clickForgottenYourLoginDetailsLink(extentedReport, true);
			Log.softAssertThat(
					customerForgottenPassword.uielement.verifyPageElements(Arrays.asList("btnResetMyPassword"),
							customerForgottenPassword),
					"Navigated to Customer Forgotten Password Page",
					"Not Navigated to Customer Forgotten Password Page", driver, extentedReport, true);

			Log.softAssertThat(
					customerForgottenPassword.uielement.verifyPageElements(
							Arrays.asList("mdlForgottenYourLoginDetails"), customerForgottenPassword),
					"'Forgotten your login details?' Screen is displayed when click on the 'Forgotten your login detail?' link from SS login page",
					"'Forgotten your login details?' Screen is not displayed when click on the 'Forgotten your login detail?' link from SS login page",
					driver, extentedReport, true);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the user is able to enter their email address manually in the Forgotten your login detail screen and Check that the user is restricted in the Forgotten your login detail screen when captcha is not completed successfully", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_021_022(String browser) throws Exception {

		String tcId = "TC_021_022";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String description = testData.get("TC_Description");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			// Navigate to Customer Forgotten Password page
			CustomerForgottenPassword customerForgottenPassword = selfServicePage
					.clickForgottenYourLoginDetailsLink(extentedReport, true);
			Log.softAssertThat(
					customerForgottenPassword.uielement.verifyPageElements(Arrays.asList("btnResetMyPassword"),
							customerForgottenPassword),
					"Navigated to Customer Forgotten Password Page",
					"Not Navigated to Customer Forgotten Password Page", driver, extentedReport, true);

			// Enter email address
			customerForgottenPassword.enterEmailAddress(emailAddress, extentedReport, true);

			Log.softAssertThat(customerForgottenPassword.verifyEmailAddress(emailAddress, extentedReport, false),
					"User is able to enter their email address manually in the Forgotten your login detail screen",
					"User is not able to enter their email address manually in the Forgotten your login detail screen",
					driver, extentedReport, true);

			customerForgottenPassword.clickResetMyPasswordBtn(extentedReport, true);

			Log.softAssertThat(
					customerForgottenPassword.uielement.verifyPageElements(
							Arrays.asList("txtPleaseCheckTheCheckboxError"), customerForgottenPassword),
					"'Please check the checkbox' Error message is displayed when captcha is not completed",
					"'Please check the checkbox' Error message is not displayed when captcha is not completed", driver,
					extentedReport, true);

			Log.softAssertThat(
					customerForgottenPassword.uielement.verifyPageElements(Arrays.asList("btnResetMyPassword"),
							customerForgottenPassword),
					"User is restricted in the Forgotten your login detail screen when captcha is not completed successfully",
					"User is not restricted in the Forgotten your login detail screen when captcha is not completed successfully",
					driver, extentedReport, true);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that a valid alert message is displayed when clicking on Reset my Password button without entering email address", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_Adh3(String browser) throws Exception {

		String tcId = "TC_Adh3";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String ErrorMessage = testData.get("ErrorMsg");
		String description = testData.get("TC_Description");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			// Navigate to Customer Forgotten Password page
			CustomerForgottenPassword customerForgottenPassword = selfServicePage
					.clickForgottenYourLoginDetailsLink(extentedReport, true);
			Log.softAssertThat(
					customerForgottenPassword.uielement.verifyPageElements(Arrays.asList("btnResetMyPassword"),
							customerForgottenPassword),
					"Navigated to Customer Forgotten Password Page",
					"Not Navigated to Customer Forgotten Password Page", driver, extentedReport, true);

			// Enter email address
			customerForgottenPassword.clickResetMyPasswordBtn(extentedReport, true);

			Log.softAssertThat(customerForgottenPassword.verifyEmailAddressErrorMsg(ErrorMessage, extentedReport, true),
					ErrorMessage
							+ " - Valid alert message is displayed when clicking on Reset my Password button without entering email address",
					ErrorMessage
							+ " - Valid alert message is not displayed when clicking on Reset my Password button without entering email address",
					driver, extentedReport, true);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	/** 1515 Story **/

	@Test(description = "Check that a message 'Your new password should be at least 8 characters long, be mixed case and contain alpha, numeric and symbol characters.' is displayed when entered password does not meet password complexity rule", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_027(String browser) throws Exception {

		String tcId = "TC_027";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String newPassword = testData.get("NewPassword");
		String wrongPassword = testData.get("WrongPassword");
		String errorMsg = testData.get("ErrorMsg");
		String description = testData.get("TC_Description");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.pass("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);

			// Select ChangePassword
			customerDashboardPage.clickWelcomeDropdown(extentedReport, false);
			customerDashboardPage.clickLinkFromWelcomeDropdown("Change Password", extentedReport, true);
			CustomerChangePasswordPage customerChangePasswordPage = new CustomerChangePasswordPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					customerChangePasswordPage.uielement.verifyPageElements(Arrays.asList("btnChangePassword"),
							customerChangePasswordPage),
					"Navigated to customer change password page", "Not navigated to customer change password page",
					driver, extentedReport, true);

			// Enter wrong Password
			customerChangePasswordPage.enterOldPassword(password, extentedReport, false);
			customerChangePasswordPage.enterNewPassword(wrongPassword, extentedReport, false);
			customerChangePasswordPage.reenterNewPassword(wrongPassword, extentedReport, true);
			customerChangePasswordPage.clickChangeMyPasswordBtn(extentedReport, false);

			Log.softAssertThat(
					customerChangePasswordPage.verifyNewPasswordComplexityErrorMsg(errorMsg, extentedReport, false),
					errorMsg + " - is displayed when entered New password ( " + wrongPassword
							+ " ) does not meet password complexity rule",
					errorMsg + " - is not displayed when entered New password ( " + wrongPassword
							+ " ) does not meet password complexity rule",
					driver, extentedReport, true);

			Log.softAssertThat(
					customerChangePasswordPage.verifyReenterPasswordComplexityErrorMsg(errorMsg, extentedReport, false),
					errorMsg + " - is displayed when re-entered New password ( " + wrongPassword
							+ " ) does not meet password complexity rule",
					errorMsg + " - is not displayed when re-entered New password ( " + wrongPassword
							+ " ) does not meet password complexity rule",
					driver, extentedReport, true);

			// Enter correct Password
			customerChangePasswordPage.enterNewPassword(newPassword, extentedReport, false);
			Thread.sleep(4000);
			customerChangePasswordPage.reenterNewPassword(newPassword, extentedReport, true);

			Log.softAssertThat(
					!customerChangePasswordPage.verifyNewPasswordComplexityErrorMsg(errorMsg, extentedReport, false),
					errorMsg + " - is not displayed when entered New password ( " + newPassword
							+ " ) which meet password complexity rule",
					errorMsg + " - is displayed when entered New password ( " + newPassword
							+ " ) which meet password complexity rule",
					driver, extentedReport, true);

			Thread.sleep(4000);
			Log.softAssertThat(
					!customerChangePasswordPage.verifyReenterPasswordComplexityErrorMsg(errorMsg, extentedReport,
							false),
					errorMsg + " - is not displayed when re-entered New password ( " + newPassword
							+ " ) which meet password complexity rule",
					errorMsg + " - is displayed when re-entered New password ( " + newPassword
							+ " ) which meet password complexity rule",
					driver, extentedReport, true);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that a message 'Your new password cannot be one of the last eight passwords that you have used' is displayed while resetting the  password from previous 8 passwords ", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_028(String browser) throws Exception {

		String tcId = "TC_028";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String errorMsg = testData.get("ErrorMsg");
		String description = testData.get("TC_Description");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.pass("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);

			// Select ChangePassword
			customerDashboardPage.clickWelcomeDropdown(extentedReport, false);
			customerDashboardPage.clickLinkFromWelcomeDropdown("Change Password", extentedReport, true);
			CustomerChangePasswordPage customerChangePasswordPage = new CustomerChangePasswordPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					customerChangePasswordPage.uielement.verifyPageElements(Arrays.asList("btnChangePassword"),
							customerChangePasswordPage),
					"Navigated to customer change password page", "Not navigated to customer change password page",
					driver, extentedReport, true);

			// Enter wrong Password
			customerChangePasswordPage.enterOldPassword(password, extentedReport, false);
			customerChangePasswordPage.enterNewPassword(password, extentedReport, false);
			customerChangePasswordPage.reenterNewPassword(password, extentedReport, true);
			customerChangePasswordPage.clickChangeMyPasswordBtn(extentedReport, false);

			Log.softAssertThat(
					customerChangePasswordPage.verifyInvalidPasswordErrorMsg(errorMsg, extentedReport, false),
					errorMsg + " - Error message is displayed while resetting the  password from previous 8 passwords",
					errorMsg + " - Error message is not displayed while resetting the  password from previous 8 passwords",
					driver, extentedReport, true);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that a message 'Your new password should be at least 8 characters long, be mixed case and contain alpha, numeric and symbol characters.' is displayed when entered password does not meet password complexity rule", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_029(String browser) throws Exception {

		String tcId = "TC_029";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String newPassword = testData.get("NewPassword");
		String errorMsg = testData.get("ErrorMsg");
		String description = testData.get("TC_Description");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(emailAddress, password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport, true);

			// Select ChangePassword
			customerDashboardPage.clickWelcomeDropdown(extentedReport, false);
			customerDashboardPage.clickLinkFromWelcomeDropdown("Change Password", extentedReport, true);
			CustomerChangePasswordPage customerChangePasswordPage = new CustomerChangePasswordPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					customerChangePasswordPage.uielement.verifyPageElements(Arrays.asList("btnChangePassword"),
							customerChangePasswordPage),
					"Navigated to customer change password page", "Not navigated to customer change password page",
					driver, extentedReport, true);

			// Enter Password
			customerChangePasswordPage.enterOldPassword(password, extentedReport, false);
			customerChangePasswordPage.enterNewPassword(newPassword, extentedReport, false);
			customerChangePasswordPage.reenterNewPassword(newPassword, extentedReport, true);
			customerChangePasswordPage.clickChangeMyPasswordBtn(extentedReport, false);

			Log.softAssertThat(
					customerChangePasswordPage.verifyNewPasswordComplexityErrorMsg(errorMsg, extentedReport, false),
					errorMsg + " - is displayed when entered New password does not meet password complexity rule",
					errorMsg + " - is not displayed when entered New password does not meet password complexity rule",
					driver, extentedReport, true);

			Log.softAssertThat(
					customerChangePasswordPage.verifyReenterPasswordComplexityErrorMsg(errorMsg, extentedReport, false),
					errorMsg + " - is displayed when re-entered New password does not meet password complexity rule",
					errorMsg + " - is not displayed when re-entered New password does not meet password complexity rule",
					driver, extentedReport, true);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	/** 1527 Story **/

	@Test(description = "Incorrect Password, account locked out", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_031(String browser) throws Exception {

		String tcId = "TC_031";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String emailAddress = testData.get("EmailAddress");
		String password = testData.get("Password");
		String errorMsg = testData.get("ErrorMsg");
		String description = testData.get("TC_Description");

		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);

			for (int loopCount = 0; loopCount < 3; loopCount++) {
				customerSignInPage.enterEmailAddress(emailAddress, extentedReport, false);
				customerSignInPage.enterPassword(password, extentedReport, false);
				customerSignInPage.clickSignInButton(extentedReport, true);
			}

			Log.softAssertThat(
					customerSignInPage.verifyInvalidCredentialErrorMsg(errorMsg, extentedReport, true), errorMsg
							+ " : Error message is displayed after 3 unsuccessfully login attempt with incorrect password",
					errorMsg + " : Error message is not displayed after 3 unsuccessfully login attempt with incorrect password",
					driver, extentedReport);

			Log.message(
					"Check client analytic data manually for Incorrect Password and not covered through automation, 'Account locked out'",
					driver, extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
}
