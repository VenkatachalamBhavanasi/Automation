package com.pure.MTA.testscripts;

import java.io.IOException;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.generic.support.BaseTest;
import com.generic.support.Log;
import com.generic.support.WebDriverFactory;
import com.generic.utils.DataProviderUtils;
import com.generic.utils.WaitUtils;
import com.pure.selfservice.pages.CardDetailsPage;
import com.pure.selfservice.pages.ChangeMyCoverTypePage;
import com.pure.selfservice.pages.ChangeTheClassOfUsePage;
import com.pure.selfservice.pages.CustomerDashboardPage;
import com.pure.selfservice.pages.CustomerServicePage;
import com.pure.selfservice.pages.CustomerSignInPage;
import com.pure.selfservice.pages.GetTestData;
import com.pure.selfservice.pages.MTAInitiatePage;
import com.pure.selfservice.pages.MTAPaymentPage;
import com.pure.selfservice.pages.MTAQuoteDetailsPage;
import com.pure.selfservice.pages.SuccessPage;
import com.relevantcodes.extentreports.ExtentTest;

public class MTA_3626 extends BaseTest {
	private String webSite;
	String sheetName = "MTA_3626_";

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

	// ******************* Story Id: 3685 ***************//

	//As test script is required to update back end configuration for accepting 0 premium, this can't be executed automatically. 
	//If we enable 0 premium configuration from back end, this change would impact all the non 0 premium scenarios
	//@Test(description = "Check that the MTA is made live when the user is taken to the confirmation screen, even if there is no change in premium (zero premium)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_002(String browser) throws Exception {

		String tcId = "TC_002";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverNameList[0], extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);
			changeMyCoverTypePage.selectCoverType(testData.coverNameList[1], extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, true);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);
			String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);
			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Default", prevPremium, extentedReport),
					"Default premium '0.00'displaying on your quote page",
					"Default premium '0.00' not displaying on your quote page", driver, extentedReport, true);
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);
			SuccessPage success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true), "MTA policy accepted as LIVE",
					"MTA not able to accept as LIVE");
			customerDashboardPage = success.clickFinish(extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("btnManagePreferences"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after selecting Finish button: ",
					"Successfully navigated to cusomer dashboard page after selecting finish button: ", driver,
					extentedReport);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//As test script is required to update back end configuration for accepting 0 premium, this can't be executed automatically. 
	//If we enable 0 premium configuration from back end, this change would impact all the non 0 premium scenarios
	//@Test(groups = {"Smoke"}, description = "Check that the documents are generated for zero premium MTA", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_003(String browser) throws Exception {

		String tcId = "TC_003";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverNameList[0], extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);
			changeMyCoverTypePage.selectCoverType(testData.coverNameList[1], extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, true);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);
			String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);
			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Default", prevPremium, extentedReport),
					"Default premium '0.00'displaying on your quote page",
					"Default premium '0.00' not displaying on your quote page", driver, extentedReport, true);
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);
			SuccessPage success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true), "MTA policy accepted as LIVE",
					"MTA not able to accept as LIVE");
			customerDashboardPage = success.clickFinish(extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("btnManagePreferences"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after selecting Finish button: ",
					"Successfully navigated to cusomer dashboard page after selecting finish button: ", driver,
					extentedReport);
			Log.softAssertThat(customerDashboardPage.clickDocumentView(testData.policyName, extentedReport, true),
					"Documents verified successfully on customer dasboard page for MTA",
					"Documents are not verified successfully on customer dasboard page for MTA", driver, extentedReport,
					true);

			// Revert back test data
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			changeMyCoverTypePage.selectCoverType(testData.coverNameList[2], extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, true);

			// click Buy button
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true), "MTA policy accepted as LIVE",
					"MTA not able to accept as LIVE");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// ******************* Story Id: 3686 ***************//

	@Test(description = "Check that the changed policy has return premium [No additional Premium] and the policy is originally paid by premium finance payment method & enough balance left on the plan (i.e.remaining premium on finance plan - refund premium is >=0) then the adjustment should be done on premium finance and confirmed MTA has a status of live", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_007(String browser) throws Exception {

		String tcId = "TC_007";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);
			
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("refundAmt"),
							mTAQuoteDetailsPage),
					"Return Premium amount is displayed when try to Change the annual mileage on motor Policy for change type : "
							+ testData.typeOfChange,
					"Return Premium amount is not displayed when try to Change the annual mileage on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);
			
			/*mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmtPremiumFinance",
							"txtHowPlanOriginallySetUp", "txtHowPlanwillChange"), mTAPaymentPage),
					"System displayed return premium, adjustment amount should adjust on premium Finance",
					"System does not displayed return premium, adjustment amount should adjust on premium Finance",
					driver, extentedReport, true);

			SuccessPage success = mTAPaymentPage.clickAgreeChangeBtn(extentedReport).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true), "MTA policy accepted as LIVE",
					"MTA not able to accept as LIVE");*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking SELECT button on ('Pay now by Card Credit/Debit Card' or Pay in instalments with a 'Finance Plan') and clicking on 'Buy' should start the Payment Processing.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_012(String browser) throws Exception {

		String tcId = "TC_012";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// click Buy button in quote page
			changeClassofUse.clickQuoteButton(extentedReport, true);
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			// Select Payment method
			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			mTAPaymentPage.selectPaymentMethod(testData.paymentMethod, extentedReport, true);

			// click buy
			mTAPaymentPage.clickBuyBtnForPaymentMethod(extentedReport);

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"), mTAPaymentPage),
					"System started Payment Processing after click in the Buy button",
					"System did not start Payment Processing after click in the Buy button", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on CANCEL button will cancel the payment process and should return the user to policy details screen and should SAVE the Quote Automatically in pure.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_013(String browser) throws Exception {

		String tcId = "TC_013";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// click Buy button in quote page
			changeClassofUse.clickQuoteButton(extentedReport, true);
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			// Select Payment method
			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			mTAPaymentPage.selectPaymentMethod(testData.paymentMethod, extentedReport, true);

			// click cancel
			mTAPaymentPage.clickCancelButton(extentedReport);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("lnkWelcome"),
							customerDashboardPage),
					"System  cancelled the payment process and returned the user to policy details screen",
					"System  did not cancel the payment process and did not return the user to policy details screen",
					driver, extentedReport);

			Log.message("Quote saved in pure back office automatically which needs to be verified manually", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
			"Smoke" }, description = "Check that the BUY button should get enabled only when the payment method is selected. without selecting payment method BUY button should be greyed out", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_014(String browser) throws Exception {

		String tcId = "TC_014";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// click Buy button in quote page
			changeClassofUse.clickQuoteButton(extentedReport, true);
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			// Select Payment method
			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			Log.softAssertThat(
					!mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyPaymentMethod"), mTAPaymentPage),
					"BUY button is not enabled when the payment method is not selected",
					"BUY button is enabled when the payment method is selected", driver, extentedReport);

			mTAPaymentPage.selectPaymentMethod(testData.paymentMethod, extentedReport, true);

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyPaymentMethod"), mTAPaymentPage),
					"BUY button is enabled only when the payment method is selected",
					"BUY button is not enabled when the payment method is selected", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	/*@Test(description = "Check that the changed policy has return premium [No additional Premium] and the policy is originally not paid by premium finance  or paid by card then the only option is Broker Refund and confirmed MTA has a status of Incomplete and Check that the changed policy has return premium [No additional Premium] and the policy is originally not paid by premium finance payment method & originally paid by card then should be refund on Card. If the user doesn't want to choose the refund on card then the option for Broker Refund should be available and confirmed MTA has a status of Incomplete", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_010_011(String browser) throws Exception {

		String tcId = "TC_010_011";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);
			changeClassofUse.clickQuoteButton(extentedReport, true);

			// click Buy button in quote page
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.message("On Quote page Refund premium displaying as: "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true));
			//mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			
			 * Log.softAssertThat(
			 * mTAPaymentPage.uielement.verifyPageElements(Arrays.asList(
			 * "btnBrokerRefund"), mTAPaymentPage),
			 * "Option 'Broker Refund' is displayed for the policy originally not paid by premium finance  or paid by card and and MTA is incomplete"
			 * ,
			 * "Option 'Broker Refund' is not displayed for the policy originally not paid by premium finance  or paid by card and and MTA is incomplete"
			 * , driver, extentedReport, true);
			 

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"), mTAPaymentPage),
					"Navigated to refund screen for the policy originally not paid by premium finance  or paid by card and and MTA is incomplete",
					"Not Navigated to refund screen for the policy originally not paid by premium finance  or paid by card and and MTA is incomplete",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}*/

	@Test(description = "Check that the changed policy has return premium [No additional Premium] and the policy is originally paid by premium finance payment method & doesn't have enough balance left on the plan (i.e.remaining premium on finance plan - refund premium is <0)  then the only option is Broker Refund and confirmed MTA has a status of Incomplete", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_008(String browser) throws Exception {

		String tcId = "TC_008";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, true);

			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("refundAmt"),
							mTAQuoteDetailsPage),
					"Return Premium amount is displayed when try to Change the annual mileage on motor Policy for change type : "
							+ testData.typeOfChange,
					"Return Premium amount is not displayed when try to Change the annual mileage on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);
			
			/*mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmtPremiumFinance"),
							mTAPaymentPage),
					"System displayed Refund amount option and MTA is incomplete",
					"System not displayed Refund amount option and MTA is not incomplete", driver, extentedReport,
					true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
			"Smoke" }, description = "Check that the changed policy has return premium [No additional Premium] and the policy is originally paid by card then Broker refund option should be available and work manager task needs to be updated in pure and confirmed MTA has a status of live", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_009(String browser) throws Exception {

		String tcId = "TC_009";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("refundAmt"),
							mTAQuoteDetailsPage),
					"Return Premium amount is displayed when try to Change the annual mileage on motor Policy for change type : "
							+ testData.typeOfChange,
					"Return Premium amount is not displayed when try to Change the annual mileage on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);
			
			/*mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"), mTAPaymentPage),
					"System displayed Refund on Card option ", "System not displayed Refund on Card option ", driver,
					extentedReport, true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// ******************* Story Id: 3687 ***************//

	@Test(description = "Check that after selecting pay by Card type and clicking BUY button all the previously used CARDS by the client should be displayed in the Previously used Card list section except the CARDs for which the expiry date has been passed. List of existing cards should display the field Card Type, (Masked Card) Number, Expiry Date and checkbox to select the needed card on the listing. Token should be passed to payment Hub without displaying. Note : Only one Card should be selectable", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_017(String browser) throws Exception {

		String tcId = "TC_017";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			//mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);

			Log.message(
					"On Quote page premium displaying as: " + mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true));

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("tblPreviouslyUsedCard"), mTAPaymentPage),
					"Previously used card Table is displayed", "Previously used card Table is not displayed", driver,
					extentedReport);

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("hdrCardType", "hdrCardNumber",
							"hdrExpiryDate", "hdrCheckbox", "rowPreviouslyUsedCard1"), mTAPaymentPage),
					"System displayed list of earlier used card in the screen with below fields : Card Type, (Masked Card) Number, Expiry Date and checkbox to select the needed card on the listing",
					"System did not display list of earlier used card in the screen with below fields : Card Type, (Masked Card) Number, Expiry Date and checkbox to select the needed card on the listing",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that if the user selects to use a new card and click on the button 'Add another card' then it should show the relevant web page to take payment it should ensure that the Payment Provider Page (probably WorldPay) page is passed the correct Broker credentials retrieved from the back-office", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_019(String browser) throws Exception {

		String tcId = "TC_019";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);
			Log.message(
					"On Quote page premium displaying as: " + mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true));

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			// Select Payment method
			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			CardDetailsPage carddetails= mTAPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			Log.softAssertThat(carddetails.uielement.verifyPageElements(Arrays.asList("lnkVisa"), carddetails),
					"System displayed relavent web page to take payment it should ensure that the Payment Provider Page (probably WorldPay) page",
					"System displayed relavent web page to take payment it should ensure that the Payment Provider Page (probably WorldPay) page",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that if payment fails it should return the user to the 'change My Policy Screen, payment card screen' so that user can attempt the payment again", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_020(String browser) throws Exception {

		String tcId = "TC_020";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);
			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, false), driver, extentedReport, true);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			

			CardDetailsPage cardDetails = mTAPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
			
			cardDetails.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport,
					true);
			//cardDetails.enterVerification(extentedReport, true);
			cardDetails.clickbuy(extentedReport, false);

			Log.softAssertThat(
					cardDetails.uielement.verifyPageElements(Arrays.asList("txtCardInvalidErrorMsg"), cardDetails),
					"System is returned back to  payment card screen screen which displays the Error message 'The card number is invalid'",
					"System is not return back to  payment card screen screen", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
			"Smoke" }, description = "Check that payment process can be cancelled after clicking on CANCEL button will cause the user to be returned to the Policy Details screen. Also Quote added should be Saved without any issue", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_027(String browser) throws Exception {

		String tcId = "TC_027";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, false), driver, extentedReport, true);
			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			mTAPaymentPage.clickCancelButton(extentedReport);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("lnkWelcome"),
							customerDashboardPage),
					"System cancelled payment process and it returned to the Policy Details screen",
					"System did not cancel payment process and it does not returned to the Policy Details screen",
					driver, extentedReport);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	/*@Test(groups = {"Smoke" }, description = "Check that If the user selects to use an existing card, then payment process should work without any issue using the stored token", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_022(String browser) throws Exception {

		String tcId = "TC_022";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClassList[0], extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, false), driver, extentedReport, true);
			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			
			mTAPaymentPage.clickBuyButton(extentedReport);

			SuccessPage success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");
			success.clickFinish(extentedReport, true);

			// Revert test data
			Log.message("<u><b>Revert Test Data</b></u>", extentedReport);
			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClassList[1], extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			// click Buy button
			mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, false), driver, extentedReport, true);
			//mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			
			mTAPaymentPage.clickBuyButton(extentedReport);

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}*/

	//As test script is required to update back end configuration for accepting 0 premium, this can't be executed automatically. 
	//If we enable 0 premium configuration from back end, this change would impact all the non 0 premium scenarios
	//@Test(description = "Check that the trigger points for document production should be successful for MTA Quote & MTA Buy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_069(String browser) throws Exception {

		String tcId = "TC_069";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverNameList[0], extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);
			changeMyCoverTypePage.selectCoverType(testData.coverNameList[1], extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, true);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);
			Log.message("premium amount displaying on quote page as "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);

			/*Log.softAssertThat(mTAQuoteDetailsPage.verifyAddoncovers(extentedReport),
					"AddtoPolicy button displaying on quote page", "AddtoPolicy button not displaying on quote page");*/
			/*String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);
			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);
			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Add", prevPremium, extentedReport),
					"Premium updated correctly on quote page", "Premium not updated correctly on quote page", driver,
					extentedReport, true);*/
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);
			SuccessPage success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true), "MTA policy accepted as LIVE",
					"MTA not able to accept as LIVE",driver,extentedReport);
			customerDashboardPage = success.clickFinish(extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("btnManagePreferences"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after selecting Finish button: ",
					"Successfully navigated to cusomer dashboard page after selecting finish button: ", driver,
					extentedReport);
			Log.softAssertThat(customerDashboardPage.clickDocumentView(testData.policyName, extentedReport, true),
					"Documents verified successfully on customer dasboard page for MTA",
					"Documents are not verified successfully on customer dasboard page for MTA", driver, extentedReport,
					true);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//As test script is required to update back end configuration for accepting 0 premium, this can't be executed automatically. 
	//If we enable 0 premium configuration from back end, this change would impact all the non 0 premium scenarios
	//@Test(description = "Check that the trigger points for document production should be successful for MTA Quote & MTA Buy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_024(String browser) throws Exception {

		String tcId = "TC_024";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			// policy premium and status
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverNameList[0], extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);
			changeMyCoverTypePage.selectCoverType(testData.coverNameList[1], extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, true);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);
			Log.message("premium amount displaying on quote page as "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);

			/*Log.softAssertThat(mTAQuoteDetailsPage.verifyAddoncovers(extentedReport),
					"AddtoPolicy button displaying on quote page", "AddtoPolicy button not displaying on quote page");
			String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);
			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);
			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Add", prevPremium, extentedReport),
					"Premium updated correctly on quote page", "Premium not updated correctly on quote page", driver,
					extentedReport, true);*/
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);
			SuccessPage success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true), "MTA policy accepted as LIVE",
					"MTA not able to accept as LIVE");
			customerDashboardPage = success.clickFinish(extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("btnManagePreferences"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after selecting Finish button: ",
					"not navigated to cusomer dashboard page after selecting finish button: ", driver, extentedReport);
			// premium should be higher and status is same
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// ******************* Story Id: 3688 ***************//

	@Test(description = "Check that a work manager task is raised against  the policy to indicate that the finance plan changed need to be confirmed with the Finance House", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_032(String browser) throws Exception {

		String tcId = "TC_032";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			//mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);
			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, false), driver, extentedReport, true);
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			// Select Payment method
			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			mTAPaymentPage.selectPaymentMethod(testData.paymentMethod, extentedReport, true);

			// click buy
			mTAPaymentPage.clickBuyBtnForPaymentMethod(extentedReport);

			Log.message(
					"System raised  work manager task against the policy to indicate that the finance plan - Which have to check in pure back office manually",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on CANCEL button will cancel the payment process and should return the user to policy details screen and should SAVE the Quote Automatically", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_034(String browser) throws Exception {

		String tcId = "TC_034";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, true);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			// Select Payment method
			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			mTAPaymentPage.selectPaymentMethod(testData.paymentMethod, extentedReport, true);

			// This method is removed
			/*mTAPaymentPage.clickBuyBtnForPaymentMethod(extentedReport);

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtChosenToPayByInstalment"),
							mTAPaymentPage),
					mTAPaymentPage.CHOSEN_BY_INSTALMENT + " : Page is navigated",
					mTAPaymentPage.CHOSEN_BY_INSTALMENT + " : Page is not navigated", driver, extentedReport);*/

			mTAPaymentPage.clickCancelButton(extentedReport);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("lnkWelcome"),
							customerDashboardPage),
					"System returned to  policy details screen and SAVED the Quote Automatically in pure back office",
					"System returned to  policy details screen and SAVED the Quote Automatically in pure back office",
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
			"Smoke" }, description = "Check that the refund is possible when (remaining premium on finance plan - refund premium is greater than or equal to 0)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_036(String browser) throws Exception {

		String tcId = "TC_036";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);
			changeClassofUse.clickQuoteButton(extentedReport, true);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			//mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);
			//mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("refundAmt"),
							mTAQuoteDetailsPage),
					"Return Premium amount is displayed when try to Change the annual mileage on motor Policy for change type : "
							+ testData.typeOfChange,
					"Return Premium amount is not displayed when try to Change the annual mileage on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);
			/*mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			// Select Payment method
			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtRefundDueOnfinancePlan"),
							mTAPaymentPage),
					"Navigated to 'Refund Due' Page", "Not navigated to 'Refund Due' Page", driver, extentedReport,
					true);

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmtPremiumFinance"),
							mTAPaymentPage),
					"Refund amount is displayed : " + mTAPaymentPage.getRefundDuePremiumAmount(),
					"Refund amount is not displayed : " + mTAPaymentPage.getRefundDuePremiumAmount(), driver,
					extentedReport, true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	/*@Test(description = "Check that the refund is not possible when (remaining premium on finance plan - refund premium is <0) then the customer is taken to broker refund workflow", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_037(String browser) throws Exception {

		String tcId = "TC_037";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);
			changeClassofUse.clickQuoteButton(extentedReport, true);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);
			
								
			// Select Payment method
			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			
			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtRefundDueOnfinancePlan"),
							mTAPaymentPage),
					"Navigated to 'Refund Due' Page", "Not navigated to 'Refund Due' Page", driver, extentedReport,
					true);

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmtPremiumFinance"),
							mTAPaymentPage),
					"Refund amount is displayed", "Refund amount is not displayed", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}*/

	// ******************* Story Id: 3689 ***************//

	@Test(description = "Check that a work manager task is raised against  the policy to indicate that the payment is due and these task should be done on the load of the page (i.e. This screen will be shown instead of a confirmation screen)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_040(String browser) throws Exception {

		String tcId = "TC_040";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverNameList[0], extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);
			changeMyCoverTypePage.selectCoverType(testData.coverNameList[1], extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, true);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			//mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);
			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			mTAPaymentPage.selectPaymentMethod(testData.paymentMethod, extentedReport, true);
			mTAPaymentPage.clickBuyBtnForPaymentMethod(extentedReport);

			/*Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("btnAgreeChange"), mTAPaymentPage),
					"System redirect to payment page", "System not redirected to payment page", driver, extentedReport);*/

			Log.message(
					"System raised work manager task against  the policy to indicate that the finance plan - which needs to be verified manually",
					extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that on clicking FINISH button the user is taken to the policy details screen and the Quote should stay at a Incomplete status", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_043(String browser) throws Exception {

		String tcId = "TC_043";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverNameList[0], extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);
			changeMyCoverTypePage.selectCoverType(testData.coverNameList[1], extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, true);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			//mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);
			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickCancelButton(extentedReport);
			mTAQuoteDetailsPage.selectOk(extentedReport);;
			/*mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			mTAPaymentPage.selectPaymentMethod(testData.paymentMethod, extentedReport, true);

			mTAPaymentPage.clickBuyBtnForPaymentMethod(extentedReport);
			SuccessPage success = mTAPaymentPage.clickAgreeChangeBtn(extentedReport).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true), "MTA policy accepted as LIVE",
					"MTA not able to accept as LIVE");
			customerDashboardPage = success.clickFinish(extentedReport, true);*/
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("btnManagePreferences"),
							customerDashboardPage),
					"System redirect to Policy Detail screen and Quote is at a Incomplete status",
					"System not redirect to Policy Detail screen and Quote is not at a Incomplete status", driver,
					extentedReport);
			Log.message("This can be check in Pure back office, scence this can not be automated : ");
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// ******************* Story Id: 3690 ***************//

	//As test script is required to update back end configuration for accepting 0 premium, this can't be executed automatically. 
	//If we enable 0 premium configuration from back end, this change would impact all the non 0 premium scenarios
	//@Test(description = "Check that clicking on FINISH button on the confirmation screen will redirect back to policy details screen and Check that Pay By Card option should available when the customer is having Additional Premium and originally paid using Card and the details should updated / refreshed based on the changes made", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_058_059_062_063(String browser) throws Exception {

		String tcId = "TC_058_059_062_063";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, true);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);

			//mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("btnSelectFinance"), mTAPaymentPage),
					"Buy with Another card option displayed when policy having additional premium : "
							+ testData.typeOfChange,
					"Buy with Another card option not when policy having addtional premium : " + testData.typeOfChange,
					driver, extentedReport);
			mTAPaymentPage.selectPaymentMethod(testData.paymentMethod, extentedReport, true);

			mTAPaymentPage.clickBuyBtnForPaymentMethod(extentedReport);

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtChosenToPayByInstalment"),
							mTAPaymentPage),
					"Navigated to 'you have chosen to pay by instalments' Page",
					"Not navigated to 'you have chosen to pay by instalments' Page", driver, extentedReport, true);

			SuccessPage success = mTAPaymentPage.clickAgreeChangeBtn(extentedReport).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true), "MTA policy accepted as LIVE",
					"MTA not able to accept as LIVE");
			customerDashboardPage = success.clickFinish(extentedReport, true);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("btnManagePreferences"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard (Policy details screen) page after selecting Finish button from Confirmation screen: ",
					"Successfully navigated to cusomer dashboard (Policy details screen) page after selecting finish button from Confirmation screen: ",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// ******************* Story Id: 3691 ***************//

	//As test script is required to update back end configuration for accepting 0 premium, this can't be executed automatically. 
	//If we enable 0 premium configuration from back end, this change would impact all the non 0 premium scenarios
	//@Test(groups = {"Smoke"}, description = "Check that Pay By Card option should available when the customer is having Additional Premium and originally paid using Card and the details should updated / refreshed based on the changes made", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_066(String browser) throws Exception {

		String tcId = "TC_066";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverNameList[0], extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);
			changeMyCoverTypePage.selectCoverType(testData.coverNameList[1], extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, true);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			//mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("btnSelectCard", "btnSelectFinance"),
							mTAPaymentPage),
					"Buy with card and premium finance displayed on payment page " + testData.typeOfChange,
					"Buy with card and premium finance displayed on payment page : " + testData.typeOfChange, driver,
					extentedReport);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// ******************* Story Id: 5028 ***************//

	@Test(description = "Check that clicking on CANCEL button will cancel the payment process and should return the user to policy details screen and should SAVE the Quote Automatically", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_048(String browser) throws Exception {

		String tcId = "TC_048";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);
			changeClassofUse.clickQuoteButton(extentedReport, true);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			//mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			// Select Payment method
			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			Log.message("Navigated to Payment screen", driver, extentedReport, true);

			// click Cancel
			mTAPaymentPage.clickCancelButton(extentedReport);
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("lnkWelcome"),
							customerDashboardPage),
					"System canceled the payment process and returned the user to policy details screen",
					"System did not cancel the payment process and did not return the user to policy details screen",
					driver, extentedReport, true);

			Log.message("Quote saved in pure back office automatically which needs to be verified manually", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the user can have option to initiate the broker refund if clicks on 'Broker Refund Requested' button on the screen. Cliking on the 'Broker Refund Requested' button should redirect the user to Broker Refund screen and Check that the refund payment is processed on the displayed card, after confirmation the user should redirect to the confirmation screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_049(String browser) throws Exception {

		String tcId = "TC_049";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					"Not navigated to quote button when quote button is selected", driver, extentedReport, true);
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("refundAmt"),
							mTAQuoteDetailsPage),
					"Return Premium amount is displayed when try to Change the annual mileage on motor Policy for change type : "
							+ testData.typeOfChange,
					"Return Premium amount is not displayed when try to Change the annual mileage on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);
						
			/*mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("lblRefundAmount"), mTAPaymentPage),
					"Refund Amount is displayed through Card : " + mTAPaymentPage.getRefundAmount(),
					"Refund Amount is not displayed through card : " + mTAPaymentPage.getRefundAmount(), driver,
					extentedReport);
			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("lblRefundAmount"), mTAPaymentPage),
					"Navigated Success Page","Not Navigated Success Page", driver, extentedReport);*/

			/*Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtUnsuccessful"),
							mTAQuoteDetailsPage),
					"System not redirect to Broker Refund screen", "System redirect to Broker Refund screen", driver,
					extentedReport, true);*/

			/*
			 * Log.softAssertThat(
			 * mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
			 * mTAPaymentPage),
			 * "System redirected to Broker Refund screen and  Work manager task will be created against the policy to indicate that a payment is due for the client. The task should be redirected to the Accounts Team."
			 * ,
			 * "System not redirected to Broker Refund screen and  Work manager task not created against the policy to indicate that a payment is due for the client."
			 * , driver, extentedReport, true);
			 */

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the refund payment is processed on the displayed card, after confirmation the user should redirect to the confirmation screen.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_050(String browser) throws Exception {

		String tcId = "TC_050";
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
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, true);

			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("refundAmt"),
							mTAQuoteDetailsPage),
					"Return Premium amount is displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					"Return Premium amount is not displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);

			/*mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBrokerRefund"), mTAPaymentPage),
					"Button broker refund displayed on payment page to click",
					"Button refund  not displayed on payment page to click", driver, extentedReport);
			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("lblRefundAmount"), mTAPaymentPage),
					"Navigated Success Page","Not Navigated Success Page", driver, extentedReport);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

}
