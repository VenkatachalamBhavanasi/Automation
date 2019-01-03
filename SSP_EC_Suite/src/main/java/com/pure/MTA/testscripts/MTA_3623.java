package com.pure.MTA.testscripts;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.generic.support.BaseTest;
import com.generic.support.Log;
import com.generic.support.WebDriverFactory;
import com.generic.utils.DataProviderUtils;
import com.pure.selfservice.pages.ChangeMyCarPage;
import com.pure.selfservice.pages.ChangeMyCoverTypePage;
import com.pure.selfservice.pages.ChangeTheClassOfUsePage;
import com.pure.selfservice.pages.CustomerDashboardPage;
import com.pure.selfservice.pages.CustomerServicePage;
import com.pure.selfservice.pages.CustomerSignInPage;
import com.pure.selfservice.pages.GetTestData;
import com.pure.selfservice.pages.MTAInitiatePage;
import com.pure.selfservice.pages.MTAQuoteDetailsPage;
import com.relevantcodes.extentreports.ExtentTest;

public class MTA_3623 extends BaseTest {
	private String webSite;
	String sheetName = "MTA_3623_";

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

	// ******************* Story Id: 3688 & 3699 ***************//
	@Test(groups = {
	"Smoke" },description = "Check that Renewal days are configurable.If Renewal days is configured as '30' then the alert message 'Please contact your Broker on XXXXX as this Policy is within 30 days of Renewal'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_004(String browser) throws Exception {
		String tcId = "TC_004";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData =new GetTestData(sheetName,tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.verifyRenewalErrorMsgInCustDashboard(customerDashboardPage.ERR_MSG_RENEWAL_POPUP),
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

	@Test(description = "Check that client who have been marked as being Fraudulent (in Pure) with flag 'FREEZE' are warned and prevented from creating an MTA when they click on Change My Policy button and Check that FREEZE flag is mapped from the client record in pure broker application to the client record on the self service portal. Also, FREEZE flag for the policy is available in Self service portals policy data and Check that the message 'Please Contact your broker on XXXXX XXXXXX if you wish to amend your policy.' is displayed for the policy is under validation with FREEZE flag enabled and User not able to perform MTA", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_006_007_008(String browser) throws Exception {

		String tcId = "TC_006_007_008";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData =new GetTestData(sheetName,tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.verifyErrorMsgInCustDashboard(customerDashboardPage.ERR_MSG_FREEZE_POPUP),
					"Verified Freeze error message in Customer dashboard page : "
							+ customerDashboardPage.ERR_MSG_FREEZE_POPUP,
					"Not verified Freeze error message in Customer dashboard page : "
							+ customerDashboardPage.ERR_MSG_FREEZE_POPUP,
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// ******************* Story Id: 3670 ***************/
	@Test(description = "Check that clicking CANCEL button should redirect to Policy Detail Screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_011(String browser) throws Exception {

		String tcId = "TC_011";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData =new GetTestData(sheetName,tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress,
					testData.password, extentedReport, true);

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
	public void TC_012(String browser) throws Exception {

		String tcId = "TC_012";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData =new GetTestData(sheetName,tcId);
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
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress,
					testData.password, extentedReport, true);

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
					!mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("btnContinueDisabled"), mTAInitiatePage),
					"'Continue' button is not available, if none of the change type option is selected",
					"'Continue' button is available, if none of the change type option is selected", driver,
					extentedReport);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.verifyContinueBtnEnabled(),
					"'Continue' button is available, after selecting change type option : " + testData.typeOfChange,
					"'Continue' button is not available, after selecting change type option : " + testData.typeOfChange, driver,
					extentedReport);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the client is taken to the data capture page of the particular change when select an amendment and click continue and Check that the client is navigated to MTA overlay when click on 'add another policy change' button from the data capture page and Check that the MTA overlay displays the following details when the user clicks on  'add another policy change' button - Filled in data capture icon highlighted, Continue button hidden, Quote button available, Date and time fields hidden, The user can highlight another area of cover and click on ‘Add another policy change’ to open the data capture for that section", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_016_017_018(String browser) throws Exception {

		String tcId = "TC_016_017_018";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData =new GetTestData(sheetName,tcId);		
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress,
					testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change my cover type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChangeValue[0], extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, false);

			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			Log.softAssertThat(
					changeMyCoverTypePage.uielement.verifyPageElements(Arrays.asList("drpdwnTypeOfCover"),
							changeMyCoverTypePage),
					"Navigate to data capture page of the particular change ( " + testData.typeOfChangeValue[0]
							+ " ) when select an amendment and click continue",
					"Not Navigate to data capture page of the particular change ( " + testData.typeOfChangeValue[0]
							+ " ) when select an amendment and click continue",
					driver, extentedReport);
			changeMyCoverTypePage.selectCoverType(testData.coverTypeOption[0], extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess("100", extentedReport, true);
			changeMyCoverTypePage.clickAddAnotherPolicyChangeButton(extentedReport, false);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChooseTypeOfChange", "btnEditChangeMyCoverType"), mTAInitiatePage),
					"Navigated to MTA overlay when click on 'add another policy change' button from the data capture page",
					"Not Navigated to MTA overlay when click on 'add another policy change' button from the data capture page",
					driver, extentedReport);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("iconChangeMyCoverSelected"),
							mTAInitiatePage),
					"Filled in data capture icon is highlighted in MTA overlay after the user clicks on 'add another policy change' button in data capture page",
					"Filled in data capture icon is not highlighted in MTA overlay after the user clicks on 'add another policy change' button in data capture page",
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

			ChangeMyCarPage changeMyCarPage = new ChangeMyCarPage(driver, extentedReport).get();
			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("btnLookUp"), changeMyCarPage),
					"Navigate to data capture page of the particular change ( " + testData.typeOfChangeValue[1]
							+ " ) after clicking on 'Add another policy change' button",
					"Navigate to data capture page of the particular change ( " + testData.typeOfChangeValue[1]
							+ " ) after clicking on 'Add another policy change' button",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
			"Smoke" }, description = "Check that clicking on QUOTE button should redirects to Quote page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
 	public void TC_028(String browser) throws Exception {

		String tcId = "TC_028";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData =new GetTestData(sheetName,tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress,
					testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Registration number
			ChangeMyCarPage changeMyCarPage = new ChangeMyCarPage(driver, extentedReport).get();
			changeMyCarPage.enterVehicleRegistrationNumber(testData.vehicleRegNo, extentedReport, true);
			changeMyCarPage.clickLookUpButton(extentedReport, false);
			// Enter General Information details
			changeMyCarPage.selectGeneralInformationDetails("Left Hand Drive", extentedReport, true);
			changeMyCarPage.clickNextButtonInSection("General Information", extentedReport, false);
			// Enter Ownership Information details
			changeMyCarPage.enterOwnershipInformation("Proposer", "January", "2017", extentedReport, true);
			changeMyCarPage.clickNextButtonInSection("Ownership Information", extentedReport, false);
			// Enter Vehicle Information details
			changeMyCarPage.enterVehicleDetail("12000", "1000", "Gold", "Metallic","19/07/2017", extentedReport, true);
			changeMyCarPage.clickNextButtonInSection("Vehicle Details", extentedReport, false);
			// Enter Location Information details
			changeMyCarPage.selectOvernightLocationOfVehicle("Car Park", extentedReport, true);
			changeMyCarPage.clickNextButtonInSection("Location Details", extentedReport, true);
			// Click Next in Security Devices
			changeMyCarPage.clickNextButtonInSection("Security Devices", extentedReport, true);
			// Click Add another policy change button
			changeMyCarPage.clickAddAnotherPolicyChangeBtn(extentedReport, true);

			// Click Quote button
			mTAInitiatePage.clickQuoteButton(extentedReport, true);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"), mTAQuoteDetailsPage),
					"On Clicking QUOTE button redirected to Quote page. [After Saving and clicking on Add Another policy change button in Data capture page] and Premium is generated : "
							+ mTAQuoteDetailsPage.getRefundPremiumAmt(),
					"On Clicking  QUOTE button on success redirected to Quote page but premium not generated : "
							+ mTAQuoteDetailsPage.getRefundPremiumAmt(),
					driver, extentedReport);

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
	public void TC_023(String browser) throws Exception {

		String tcId = "TC_023";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData =new GetTestData(sheetName,tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress,
					testData.password, extentedReport, true);

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
	public void TC_024(String browser) throws Exception {

		String tcId = "TC_024";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData =new GetTestData(sheetName,tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress,
					testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Enter MTA effective date
			MTAInitiatePage mtaInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mtaInitiatePage.enterDateForChangeTakeEffect(testData.mtaEffectiveDate, extentedReport, true);

			// Select change type
			Log.softAssertThat(mtaInitiatePage.verifyMTADateErrorMsg(mtaInitiatePage.MTA_END_DATE_GREATER_ERR_MSG, extentedReport,
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

	@Test(description = "Check that clicking on CANCEL button in Initiate page will redirect the user to the Policy Detail screen and displays the message 'You have decided not to proceed with your ", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_029(String browser) throws Exception {

		String tcId = "TC_029";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData =new GetTestData(sheetName,tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress,
					testData.password, extentedReport, true);

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

	// ******************* Story Id: 3671 ***************//

	@Test(groups = {
			"Smoke" }, description = "Check that the ’Quote’ and ‘+Add another policy change’ buttons are enabled only when the full data capture is displayed", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_035(String browser) throws Exception {

		String tcId = "TC_035";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData =new GetTestData(sheetName,tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress,
					testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, false);

			ChangeMyCarPage changeMyCarPage = new ChangeMyCarPage(driver, extentedReport).get();
			Log.softAssertThat(
					!changeMyCarPage.uielement.verifyPageElements(
							Arrays.asList("btnQuote", "btnAddAnotherPolicyChange"), changeMyCarPage),
					"Quote and Add another policy change button is not displayed if mandatory informations are not entered",
					"Quote and Add another policy change button is displayed even if mandatory informations are not entered",
					driver, extentedReport);

			// Enter Vehicle Registration Number
			changeMyCarPage.enterVehicleRegistrationNumber(testData.vehicleRegNo, extentedReport, true);
			changeMyCarPage.clickLookUpButton(extentedReport, false);
			// Enter General Information details
			changeMyCarPage.selectGeneralInformationDetails("Left Hand Drive", extentedReport, true);
			changeMyCarPage.clickNextButtonInSection("General Information", extentedReport, false);
			// Enter Ownership Information details
			changeMyCarPage.enterOwnershipInformation("Proposer", "January", "2017", extentedReport, true);
			changeMyCarPage.clickNextButtonInSection("Ownership Information", extentedReport, false);
			// Enter Vehicle Information details
			changeMyCarPage.enterVehicleDetail("12000", "1000", "Gold", "Metallic","19/07/2017", extentedReport, true);
			changeMyCarPage.clickNextButtonInSection("Vehicle Details", extentedReport, false);
			// Enter Location Information details
			changeMyCarPage.selectOvernightLocationOfVehicle("Car Park", extentedReport, true);
			changeMyCarPage.clickNextButtonInSection("Location Details", extentedReport, true);
			// Click Next in Security Devices
			changeMyCarPage.clickNextButtonInSection("Security Devices", extentedReport, true);
			// Click Add another policy change button
			changeMyCarPage.clickAddAnotherPolicyChangeBtn(extentedReport, true);
			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("btnQuote", "btnAddAnotherPolicyChange"),
							changeMyCarPage),
					"Quote and Add another policy change button is displayed after mandatory informations are entered",
					"Quote and Add another policy change button is not displayed after mandatory informations are entered",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
			"Smoke" }, description = "Check that Clicking  QUOTE button on success will redirects to the quote details page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_039(String browser) throws Exception {

		String tcId = "TC_039";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData =new GetTestData(sheetName,tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress,
					testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, false);

			// select class of use
			ChangeTheClassOfUsePage changeTheClassOfUsePage = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeTheClassOfUsePage.selectClassOfUse(testData.classOfUse, extentedReport, true);
			changeTheClassOfUsePage.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeTheClassOfUsePage.clickQuoteButton(extentedReport, false);
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtPremium"),
							mTAQuoteDetailsPage),
					"On Clicking  QUOTE button on success will redirects to the quote details page",
					"On Clicking  QUOTE button on success not redirected to the quote details page", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that Clicking on CANCEL button will redirect to the initiate MTA screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_040(String browser) throws Exception {

		String tcId = "TC_040";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData =new GetTestData(sheetName,tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress,
					testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyCarPage changeMyCarPage = new ChangeMyCarPage(driver, extentedReport).get();
			changeMyCarPage.clickCancelButton(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("btnContinue"), mTAInitiatePage),
					"On Clicking CANCEL button in data capture page redirect to MTA initiate page",
					"On Clicking  Cancel button not redirects to the MTA initiate page", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking CANCEL button in initiate page should SAVE a Quote without forcing validation if already the details are SAVED in Data capture page [i.e. Displayed with EDIT changes icon]", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_038(String browser) throws Exception {

		String tcId = "TC_038";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData =new GetTestData(sheetName,tcId);

		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress,
					testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyCarPage changeMyCarPage = new ChangeMyCarPage(driver, extentedReport).get();
			changeMyCarPage.enterVehicleRegistrationNumber(testData.vehicleRegNo, extentedReport, true);
			changeMyCarPage.clickLookUpButton(extentedReport, false);

			// Enter General Information details
			changeMyCarPage.selectGeneralInformationDetails("Left Hand Drive", extentedReport, true);
			changeMyCarPage.clickNextButtonInSection("General Information", extentedReport, false);
			// Enter Ownership Information details
			changeMyCarPage.enterOwnershipInformation("Proposer", "January", "2017", extentedReport, true);
			changeMyCarPage.clickNextButtonInSection("Ownership Information", extentedReport, false);
			// Enter Vehicle Information details
			changeMyCarPage.enterVehicleDetail("12000", "1000", "Gold", "Metallic","19/07/2017", extentedReport, true);
			changeMyCarPage.clickNextButtonInSection("Vehicle Details", extentedReport, false);
			// Enter Location Information details
			changeMyCarPage.selectOvernightLocationOfVehicle("Car Park", extentedReport, true);
			changeMyCarPage.clickNextButtonInSection("Location Details", extentedReport, true);
			// Click Next in Security Devices
			changeMyCarPage.clickNextButtonInSection("Security Devices", extentedReport, true);
			// Click Add another policy change button
			changeMyCarPage.clickAddAnotherPolicyChangeBtn(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("btnEditChangesOfChangeMyCar"),
							mTAInitiatePage),
					"Edit changes button is displayed after clicking cancel button in initiate page of change my car",
					"Edit changes button is not displayed after clicking cancel button in initiate page of change my car",
					driver, extentedReport);
			mTAInitiatePage.clickCancelButton(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("btnManageContactDetails"),
							customerDashboardPage),
					"On clicking CANCEL button navigated to Customer Dashboard page",
					"On clicking CANCEL button not navigated to Customer Dashboard page", driver, extentedReport, true);

			Log.message(
					" Not covered Pure Back office verification : Verifying the quote is getting saved in pure back office");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// ******************* Story Id: 5011 ***************//
	@Test(description = "Check that If the Client is marked as‘Suspected Fraudulent’in the back-office (triggered by flag“FIT Freeze”) the user should not able to login and alert message 'Please Contact your broker on XXXXX XXXXXX if you wish to login' should get displayed", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_047(String browser) throws Exception {

		String tcId = "TC_047";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData =new GetTestData(sheetName,tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.enterEmailAddress(testData.emailAddress, extentedReport, false);
			customerSignInPage.enterPassword(testData.password, extentedReport, false);
			customerSignInPage.clickSignInButton(extentedReport, false);

			Log.softAssertThat(customerSignInPage.verifyErrorAlertMsg(customerSignInPage.ALERT_ERR_FRAUD_MSG),
					"Verified Renewal error message in Customer dash board page : "
							+ customerSignInPage.ALERT_ERR_FRAUD_MSG,
					"Not verified Renewal error message in Customer dash board page : "
							+ customerSignInPage.ALERT_ERR_FRAUD_MSG,
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
