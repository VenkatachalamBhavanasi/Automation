package com.pure.Renewals;

import java.io.IOException;
import java.util.Arrays;

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
import com.generic.utils.WaitUtils;
import com.pure.selfservice.pages.AddContentsCoverPage;
//import com.pure.selfservice.pages.BuyPaymentPage;
import com.pure.selfservice.pages.CardDetailsPage;
import com.pure.selfservice.pages.ChangeMyCoverTypePage;
import com.pure.selfservice.pages.CustomerDashboardPage;
import com.pure.selfservice.pages.CustomerServicePage;
import com.pure.selfservice.pages.CustomerSignInPage;
import com.pure.selfservice.pages.GetTestData;
import com.pure.selfservice.pages.MTAInitiatePage;
import com.pure.selfservice.pages.MTAPaymentPage;
import com.pure.selfservice.pages.MTAQuoteDetailsPage;
import com.pure.selfservice.pages.RenewMyPolicyPage;
import com.pure.selfservice.pages.RenewalPaymentPage;
import com.pure.selfservice.pages.RenewalPaymentRequiredPage;
import com.pure.selfservice.pages.SuccessPage;
import com.relevantcodes.extentreports.ExtentTest;

@Listeners(EmailReport.class)

public class Renewal_22748_CPA  extends BaseTest {
	private String webSite;
	String sheetName = "Renewal_22748_";

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

	//******************* User story 22748 Renewal Motor Policy *****************

	@Test(description="Verify 'Do you give consent for this payment card to be set up for continuous payments?' question is displayed in card selection page with 'Yes' and 'No' button", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23464(String browser) throws Exception {

		String tcId = "TC_23464";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), 
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page", 
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), 
					"Vehicle Fuel is show in the Renew My Policy page", 
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), 
					"Vehicle Matches is show in the Renew My Policy page", 
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), 
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), 
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);
			
			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description="Verify the help text displayed when click/hover the help icon (?) against the 'Continuous Payment Authority' (CPA)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23465(String browser) throws Exception {

		String tcId = "TC_23465";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), 
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page", 
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), 
					"Vehicle Fuel is show in the Renew My Policy page", 
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), 
					"Vehicle Matches is show in the Renew My Policy page", 
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), 
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), 
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);
			
			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);
			
			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("iconHelpContinuousPayments"),
							RenewalPaymentCard),
					"Help icon (?) is displayed against the 'Continuous Payments Question' text in Card payment page",
					"Help icon (?) is not displayed against the 'Continuous Payments Question' text in Card payment page",
					driver, extentedReport);

			RenewalPaymentCard.hoverOnHelpIconOfContinuousPayments(extentedReport, true);
			Log.softAssertThat(RenewalPaymentCard.verifyHelpIconContinuousPaymentsPopupMessage(),
					RenewalPaymentCard.POPUP_MSG_HELPICON_Continuous_Payments
					+ " - Message is displayed when click/hover the help icon (?) against the 'Continuous Payments Question' text",
					RenewalPaymentCard.POPUP_MSG_HELPICON_Continuous_Payments
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Continuous Payments Question' text",
					driver, extentedReport);
			
			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify the 'Buy with Another Card' buttons are not enable until user selects the 'Yes' or 'No' for 'Do you give consent for this payment card to be set up for continuous payments?' question", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23466(String browser) throws Exception {

		String tcId = "TC_23466";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), 
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page", 
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), 
					"Vehicle Fuel is show in the Renew My Policy page", 
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), 
					"Vehicle Matches is show in the Renew My Policy page", 
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), 
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), 
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);
			
			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);
			
			Log.softAssertThat(!RenewalPaymentCard.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);
			
			RenewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description="Verify that a new section heading above the existing check boxes which reads ‘Select Card’ in Previously used card session", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23467(String browser) throws Exception {

		String tcId = "TC_23467";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), 
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page", 
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), 
					"Vehicle Fuel is show in the Renew My Policy page", 
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), 
					"Vehicle Matches is show in the Renew My Policy page", 
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), 
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), 
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);
			
			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description="Verify that user is unable to proceed if the mandatory CPA question has not been answered", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23470(String browser) throws Exception {

		String tcId = "TC_23470";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), 
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page", 
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), 
					"Vehicle Fuel is show in the Renew My Policy page", 
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), 
					"Vehicle Matches is show in the Renew My Policy page", 
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), 
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), 
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);
			
			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);
			
			Log.softAssertThat(!RenewalPaymentCard.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);
			
			Log.message("User is unable to proceed if the mandatory CPA question has not been answered", extentedReport);
			
			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description="If 'Buy with Another card' is selected, a mandatory pop-up will be displayed before proceeding to payment screen – 'Do you want to set this new card as the default to be used for continuous payments?'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23472(String browser) throws Exception {

		String tcId = "TC_23472";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), 
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page", 
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), 
					"Vehicle Fuel is show in the Renew My Policy page", 
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), 
					"Vehicle Matches is show in the Renew My Policy page", 
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), 
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), 
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);
			
			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);
			
			Log.softAssertThat(!RenewalPaymentCard.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);
			
			RenewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);
			
			RenewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.getPopupMessage(testData.popupMessage),
					"Mandatory pop-up is shown when the user selectes 'Buy with Another Card'", "Mandatory pop-up is not shown when the user selectes 'Buy with Another Card'", 
					driver,	extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description="Verify the help text displayed when click/hover the help icon (?) against the 'Previously used card'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23476(String browser) throws Exception {

		String tcId = "TC_23476";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), 
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page", 
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), 
					"Vehicle Fuel is show in the Renew My Policy page", 
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), 
					"Vehicle Matches is show in the Renew My Policy page", 
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), 
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), 
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);
			
			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);
			
			Log.softAssertThat(!RenewalPaymentCard.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);
			
			RenewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);
			
			RenewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.getPopupMessage(testData.popupMessage),
					"Mandatory pop-up is shown when the user selectes 'Buy with Another Card'", "Mandatory pop-up is not shown when the user selectes 'Buy with Another Card'", 
					driver,	extentedReport, true);
			
			RenewalPaymentCard.clickNoButtonPopupMessage(extentedReport, true);
			
			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("iconHelpPreviouslyUsedCards"),
							RenewalPaymentCard),
					"Help icon (?) is displayed against the 'Previously Used Cards' text in Card payment page",
					"Help icon (?) is not displayed against the 'Previously Used Cards' text in Card payment page",
					driver, extentedReport);

			RenewalPaymentCard.hoverOnHelpIconOfPreviouslyUsedCards(extentedReport, true);
			Log.softAssertThat(RenewalPaymentCard.verifyHelpIconPreviouslyUsedCardsPopupMessage(),
					RenewalPaymentCard.POPUP_MSG_HELPICON_Previously_Used_Cards
					+ " - Message is displayed when click/hover the help icon (?) against the 'Previously Used Cards' text",
					RenewalPaymentCard.POPUP_MSG_HELPICON_Previously_Used_Cards
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Previously Used Cards' text",
					driver, extentedReport);
			
			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="If no is selected, a warning message should be displayed to the user to select an existing card as the default and taken back to the card screen - 'Please select an existing card as the default to be used for continuous payments.'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23477(String browser) throws Exception {

		String tcId = "TC_23477";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), 
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page", 
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), 
					"Vehicle Fuel is show in the Renew My Policy page", 
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), 
					"Vehicle Matches is show in the Renew My Policy page", 
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), 
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), 
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);
			
			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);
			
			Log.softAssertThat(!RenewalPaymentCard.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);
			
			RenewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);
			
			RenewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.getPopupMessage(testData.popupMessage),
					"Mandatory pop-up is shown when the user selectes 'Buy with Another Card'", "Mandatory pop-up is not shown when the user selectes 'Buy with Another Card'", 
					driver,	extentedReport, true);
			
			RenewalPaymentCard.clickNoButtonPopupMessage(extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.getErrorMessage(testData.errorMessage),
					"Warning message is shown when the user selectes 'No' in popup", "Warning message is not shown when the user selectes 'No' in popup", 
					driver,	extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description="Default checkbox should be hidden and thus not selected", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23478(String browser) throws Exception {

		String tcId = "TC_23478";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), 
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page", 
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), 
					"Vehicle Fuel is show in the Renew My Policy page", 
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), 
					"Vehicle Matches is show in the Renew My Policy page", 
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), 
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), 
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);
			
			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);
			
			RenewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifyDefaultSession(extentedReport),
					"Default Session is displayed in Card details page", "Default Session is not displayed in Card details page",
					driver, extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description="Verify that the new card details will be set as default if user doesn't have any existing cards and selected 'Yes' for the CPA question", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23471(String browser) throws Exception {

		String tcId = "TC_23471";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), 
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page", 
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), 
					"Vehicle Fuel is show in the Renew My Policy page", 
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), 
					"Vehicle Matches is show in the Renew My Policy page", 
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), 
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), 
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);
			
			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);
			
			RenewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);
			
			RenewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);
			
			CardDetailsPage cardDetails = new CardDetailsPage(driver, extentedReport).get(); 

			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			cardDetails.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport,
					true);
			//cardDetails.enterVerification(extentedReport, true);
			cardDetails.clickbuy(extentedReport, false);

			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");
			
			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description="If existing card is selected ‘Buy’ button will be enabled to allow the user to pay with the existing card and the existing card will be set as the default if processed", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23475(String browser) throws Exception {

		String tcId = "TC_23475";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), 
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page", 
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), 
					"Vehicle Fuel is show in the Renew My Policy page", 
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), 
					"Vehicle Matches is show in the Renew My Policy page", 
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), 
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), 
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);
			
			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);
			
			RenewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);
			
			Log.softAssertThat(!RenewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is not clickable and disabled when Crdit Card is not checked",
					"Buy button is clickable and enabled when Crdit Card is checked", driver, extentedReport, true);
						
			RenewalPaymentCard.selectCheckBoxForCard(testData.cardName, extentedReport, true);
			
			RenewalPaymentCard.defaultCheckBoxForCard(testData.cardName, extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport, true);
			
			RenewalPaymentCard.clickBuyButton(extentedReport);
			
			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");
			
			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	

	//******************* User story 22748 Renewal HouseHold Policy *************

	@Test(description="Verify “Do you give consent for this payment card to be set up for continuous payments?” question is displayed in card selection page with 'Yes' and 'No' button", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23490(String browser) throws Exception {

		String tcId = "TC_23490";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify the help text displayed when click/hover the help icon (?) against the 'Continuous Payment Authority' (CPA)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23491(String browser) throws Exception {

		String tcId = "TC_23491";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("iconHelpContinuousPayments"),
							RenewalPaymentCard),
					"Help icon (?) is displayed against the 'Continuous Payments Question' text in Card payment page",
					"Help icon (?) is not displayed against the 'Continuous Payments Question' text in Card payment page",
					driver, extentedReport);

			RenewalPaymentCard.hoverOnHelpIconOfContinuousPayments(extentedReport, true);
			Log.softAssertThat(RenewalPaymentCard.verifyHelpIconContinuousPaymentsPopupMessage(),
					RenewalPaymentCard.POPUP_MSG_HELPICON_Continuous_Payments
					+ " - Message is displayed when click/hover the help icon (?) against the 'Continuous Payments Question' text",
					RenewalPaymentCard.POPUP_MSG_HELPICON_Continuous_Payments
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Continuous Payments Question' text",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify the 'Buy with Another Card' buttons are not enable until user selects the 'Yes' or 'No' for 'Do you give consent for this payment card to be set up for continuous payments?' question", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23492(String browser) throws Exception {

		String tcId = "TC_23492";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!RenewalPaymentCard.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			RenewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(RenewalPaymentCard.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that a new section heading above the existing check boxes which reads ‘Select Card’ in Previously used card session", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23493(String browser) throws Exception {

		String tcId = "TC_23493";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(RenewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that user is unable to proceed if the mandatory CPA question has not been answered", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23496(String browser) throws Exception {

		String tcId = "TC_23496";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!RenewalPaymentCard.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			Log.message("User is unable to proceed if the mandatory CPA question has not been answered", extentedReport);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="If 'Buy with Another card' is selected, a mandatory pop-up will be displayed before proceeding to payment screen – 'Do you want to set this new card as the default to be used for continuous payments?'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23498(String browser) throws Exception {

		String tcId = "TC_23498";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!RenewalPaymentCard.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			RenewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);

			RenewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);

			Log.softAssertThat(RenewalPaymentCard.getPopupMessage(testData.popupMessage),
					"Mandatory pop-up is shown when the user selectes 'Buy with Another Card'", "Mandatory pop-up is not shown when the user selectes 'Buy with Another Card'", 
					driver,	extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify the help text displayed when click/hover the help icon (?) against the 'Previously used card'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23502(String browser) throws Exception {

		String tcId = "TC_23502";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!RenewalPaymentCard.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			RenewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);

			RenewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);

			Log.softAssertThat(RenewalPaymentCard.getPopupMessage(testData.popupMessage),
					"Mandatory pop-up is shown when the user selectes 'Buy with Another Card'", "Mandatory pop-up is not shown when the user selectes 'Buy with Another Card'", 
					driver,	extentedReport, true);

			RenewalPaymentCard.clickNoButtonPopupMessage(extentedReport, true);

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("iconHelpPreviouslyUsedCards"),
							RenewalPaymentCard),
					"Help icon (?) is displayed against the 'Previously Used Cards' text in Card payment page",
					"Help icon (?) is not displayed against the 'Previously Used Cards' text in Card payment page",
					driver, extentedReport);

			RenewalPaymentCard.hoverOnHelpIconOfPreviouslyUsedCards(extentedReport, true);
			Log.softAssertThat(RenewalPaymentCard.verifyHelpIconPreviouslyUsedCardsPopupMessage(),
					RenewalPaymentCard.POPUP_MSG_HELPICON_Previously_Used_Cards
					+ " - Message is displayed when click/hover the help icon (?) against the 'Previously Used Cards' text",
					RenewalPaymentCard.POPUP_MSG_HELPICON_Previously_Used_Cards
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Previously Used Cards' text",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="If no is selected, a warning message should be displayed to the user to select an existing card as the default and taken back to the card screen - 'Please select an existing card as the default to be used for continuous payments.'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23503(String browser) throws Exception {

		String tcId = "TC_23503";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!RenewalPaymentCard.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			RenewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);

			RenewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);

			Log.softAssertThat(RenewalPaymentCard.getPopupMessage(testData.popupMessage),
					"Mandatory pop-up is shown when the user selectes 'Buy with Another Card'", "Mandatory pop-up is not shown when the user selectes 'Buy with Another Card'", 
					driver,	extentedReport, true);

			RenewalPaymentCard.clickNoButtonPopupMessage(extentedReport, true);

			Log.softAssertThat(RenewalPaymentCard.getErrorMessage(testData.errorMessage),
					"Warning message is shown when the user selectes 'No' in popup", "Warning message is not shown when the user selectes 'No' in popup", 
					driver,	extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Default checkbox should be hidden and thus not selected", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23504(String browser) throws Exception {

		String tcId = "TC_23504";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(RenewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);

			RenewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(RenewalPaymentCard.verifyDefaultSession(extentedReport),
					"Default Session is displayed in Card details page", "Default Session is not displayed in Card details page",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the new card details will be set as default if user doesn't have any existing cards and selected 'Yes' for the CPA question", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23497(String browser) throws Exception {

		String tcId = "TC_23497";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);
			
			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);
			
			RenewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);
			
			RenewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);
			
			CardDetailsPage cardDetails = new CardDetailsPage(driver, extentedReport).get(); 

			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			cardDetails.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport,
					true);
			//cardDetails.enterVerification(extentedReport, true);
			cardDetails.clickbuy(extentedReport, false);

			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");
			
			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description="If existing card is selected ‘Buy’ button will be enabled to allow the user to pay with the existing card and the existing card will be set as the default if processed", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23501(String browser) throws Exception {

		String tcId = "TC_23501";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);
			
			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(RenewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);
			
			RenewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);
			
			Log.softAssertThat(!RenewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is not clickable and disabled when Crdit Card is not checked",
					"Buy button is clickable and enabled when Crdit Card is checked", driver, extentedReport, true);
						
			RenewalPaymentCard.selectCheckBoxForCard(testData.cardName, extentedReport, true);
			
			RenewalPaymentCard.defaultCheckBoxForCard(testData.cardName, extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport, true);
			
			RenewalPaymentCard.clickBuyButton(extentedReport);
			
			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");
			
			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//******************* User story 22748 HouseHold MTA Policy *****************

	@Test(description="Verify “Do you give consent for this payment card to be set up for continuous payments?” question is displayed in card selection page with 'Yes' and 'No' button", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23420(String browser) throws Exception {

		String tcId = "TC_23420";
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
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, 
					extentedReport, true);

			// Click Quote button
			addContentsCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);
			
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify the help text displayed when click/hover the help icon (?) against the 'Continuous Payment Authority' (CPA)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23421(String browser) throws Exception {

		String tcId = "TC_23421";
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
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, 
					extentedReport, true);

			// Click Quote button
			addContentsCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);
			
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("iconHelpContinuousPayments"),
							mTAPaymentPage),
					"Help icon (?) is displayed against the 'Continuous Payments Question' text in Card payment page",
					"Help icon (?) is not displayed against the 'Continuous Payments Question' text in Card payment page",
					driver, extentedReport);

			mTAPaymentPage.hoverOnHelpIconOfContinuousPayments(extentedReport, true);
			Log.softAssertThat(mTAPaymentPage.verifyHelpIconContinuousPaymentsPopupMessage(),
					mTAPaymentPage.POPUP_MSG_HELPICON_Continuous_Payments
					+ " - Message is displayed when click/hover the help icon (?) against the 'Continuous Payments Question' text",
					mTAPaymentPage.POPUP_MSG_HELPICON_Continuous_Payments
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Continuous Payments Question' text",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify the 'Buy with Another Card' buttons are not enable until user selects the 'Yes' or 'No' for 'Do you give consent for this payment card to be set up for continuous payments?' question", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23422(String browser) throws Exception {

		String tcId = "TC_23422";
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
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, 
					extentedReport, true);

			// Click Quote button
			addContentsCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);
			
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mTAPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mTAPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that a new section heading above the existing check boxes which reads ‘Select Card’ in Previously used card session", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23423(String browser) throws Exception {

		String tcId = "TC_23423";
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
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, 
					extentedReport, true);

			// Click Quote button
			addContentsCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);
			
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that user is unable to proceed if the mandatory CPA question has not been answered", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23426(String browser) throws Exception {

		String tcId = "TC_23426";
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
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, 
					extentedReport, true);

			// Click Quote button
			addContentsCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);
			
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mTAPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			Log.message("User is unable to proceed if the mandatory CPA question has not been answered", extentedReport);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="If 'Buy with Another card' is selected, a mandatory pop-up will be displayed before proceeding to payment screen – 'Do you want to set this new card as the default to be used for continuous payments?'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23428(String browser) throws Exception {

		String tcId = "TC_23428";
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
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, 
					extentedReport, true);

			// Click Quote button
			addContentsCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);
			
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mTAPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mTAPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			mTAPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.getPopupMessage(testData.popupMessage),
					"Mandatory pop-up is shown when the user selectes 'Buy with Another Card'", "Mandatory pop-up is not shown when the user selectes 'Buy with Another Card'", 
					driver,	extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify the help text displayed when click/hover the help icon (?) against the 'Previously used card'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23432(String browser) throws Exception {

		String tcId = "TC_23432";
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
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, 
					extentedReport, true);

			// Click Quote button
			addContentsCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);
			
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mTAPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mTAPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			mTAPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.getPopupMessage(testData.popupMessage),
					"Mandatory pop-up is shown when the user selectes 'Buy with Another Card'", "Mandatory pop-up is not shown when the user selectes 'Buy with Another Card'", 
					driver,	extentedReport, true);

			mTAPaymentPage.clickNoButtonPopupMessage(extentedReport, true);

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("iconHelpPreviouslyUsedCards"),
							mTAPaymentPage),
					"Help icon (?) is displayed against the 'Previously Used Cards' text in Card payment page",
					"Help icon (?) is not displayed against the 'Previously Used Cards' text in Card payment page",
					driver, extentedReport);

			mTAPaymentPage.hoverOnHelpIconOfPreviouslyUsedCards(extentedReport, true);
			Log.softAssertThat(mTAPaymentPage.verifyHelpIconPreviouslyUsedCardsPopupMessage(),
					mTAPaymentPage.POPUP_MSG_HELPICON_Previously_Used_Cards
					+ " - Message is displayed when click/hover the help icon (?) against the 'Previously Used Cards' text",
					mTAPaymentPage.POPUP_MSG_HELPICON_Previously_Used_Cards
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Previously Used Cards' text",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="If no is selected, a warning message should be displayed to the user to select an existing card as the default and taken back to the card screen - 'Please select an existing card as the default to be used for continuous payments.'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23433(String browser) throws Exception {

		String tcId = "TC_23433";
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
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, 
					extentedReport, true);

			// Click Quote button
			addContentsCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);
			
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mTAPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mTAPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			mTAPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.getPopupMessage(testData.popupMessage),
					"Mandatory pop-up is shown when the user selectes 'Buy with Another Card'", "Mandatory pop-up is not shown when the user selectes 'Buy with Another Card'", 
					driver,	extentedReport, true);

			mTAPaymentPage.clickNoButtonPopupMessage(extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.getErrorMessage(testData.errorMessage),
					"Warning message is shown when the user selectes 'No' in popup", "Warning message is not shown when the user selectes 'No' in popup", 
					driver,	extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Default checkbox should be hidden and thus not selected", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23434(String browser) throws Exception {

		String tcId = "TC_23434";
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
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, 
					extentedReport, true);

			// Click Quote button
			addContentsCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);
			
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);

			mTAPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.verifyDefaultSession(extentedReport),
					"Default Session is displayed in Card details page", "Default Session is not displayed in Card details page",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description="Verify that the new card details will be set as default if user dosen't have any existing cards and selected 'Yes' for the CPA question", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23427(String browser) throws Exception {

		String tcId = "TC_23427";
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
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, 
					extentedReport, true);

			// Click Quote button
			addContentsCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);
			
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);

			mTAPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			CardDetailsPage cardDetails = mTAPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			cardDetails.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport,
					true);
			//cardDetails.enterVerification(extentedReport, true);
			cardDetails.clickbuy(extentedReport, false);

			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="If existing card is selected ‘Buy’ button will be enabled to allow the user to pay with the existing card and the existing card will be set as the default if processed", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23431(String browser) throws Exception {

		String tcId = "TC_23431";
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
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, 
					extentedReport, true);

			// Click Quote button
			addContentsCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);
			
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);

			mTAPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);
			
			mTAPaymentPage.selectCheckBoxForCard(testData.cardName, extentedReport, true);
			
			mTAPaymentPage.defaultCheckBoxForCard(testData.cardName, extentedReport, true);
			
			Log.softAssertThat(mTAPaymentPage.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport, true);
			
			mTAPaymentPage.clickBuyButton(extentedReport);
			
			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");
			

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//******************* User story 22748 Motor MTA Policy *****************

	@Test(description="Verify “Do you give consent for this payment card to be set up for continuous payments?” question is displayed in card selection page with 'Yes' and 'No' button", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23442(String browser) throws Exception {

		String tcId = "TC_23442";
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

			// Select cover type
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);

			// Click Quote button
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat( mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify the help text displayed when click/hover the help icon (?) against the 'Continuous Payment Authority' (CPA)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23443(String browser) throws Exception {

		String tcId = "TC_23443";
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

			// Select cover type
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);

			// Click Quote button
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat( mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("iconHelpContinuousPayments"),
							mTAPaymentPage),
					"Help icon (?) is displayed against the 'Continuous Payments Question' text in Card payment page",
					"Help icon (?) is not displayed against the 'Continuous Payments Question' text in Card payment page",
					driver, extentedReport);

			mTAPaymentPage.hoverOnHelpIconOfContinuousPayments(extentedReport, true);
			Log.softAssertThat(mTAPaymentPage.verifyHelpIconContinuousPaymentsPopupMessage(),
					mTAPaymentPage.POPUP_MSG_HELPICON_Continuous_Payments
					+ " - Message is displayed when click/hover the help icon (?) against the 'Continuous Payments Question' text",
					mTAPaymentPage.POPUP_MSG_HELPICON_Continuous_Payments
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Continuous Payments Question' text",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify the 'Buy with Another Card' buttons are not enable until user selects the 'Yes' or 'No' for 'Do you give consent for this payment card to be set up for continuous payments?' question", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23444(String browser) throws Exception {

		String tcId = "TC_23444";
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

			// Select cover type
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);

			// Click Quote button
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat( mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mTAPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mTAPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that a new section heading above the existing check boxes which reads ‘Select Card’ in Previously used card session", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23445(String browser) throws Exception {

		String tcId = "TC_23445";
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

			// Select cover type
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);

			// Click Quote button
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat( mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that user is unable to proceed if the mandatory CPA question has not been answered", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23448(String browser) throws Exception {

		String tcId = "TC_23448";
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

			// Select cover type
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);

			// Click Quote button
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat( mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mTAPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			Log.message("User is unable to proceed if the mandatory CPA question has not been answered", extentedReport);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="If 'Buy with Another card' is selected, a mandatory pop-up will be displayed before proceeding to payment screen – 'Do you want to set this new card as the default to be used for continuous payments?'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23450(String browser) throws Exception {

		String tcId = "TC_23450";
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

			// Select cover type
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);

			// Click Quote button
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat( mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mTAPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mTAPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			mTAPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.getPopupMessage(testData.popupMessage),
					"Mandatory pop-up is shown when the user selectes 'Buy with Another Card'", "Mandatory pop-up is not shown when the user selectes 'Buy with Another Card'", 
					driver,	extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify the help text displayed when click/hover the help icon (?) against the 'Previously used card'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23454(String browser) throws Exception {

		String tcId = "TC_23454";
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

			// Select cover type
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);

			// Click Quote button
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat( mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mTAPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mTAPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			mTAPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.getPopupMessage(testData.popupMessage),
					"Mandatory pop-up is shown when the user selectes 'Buy with Another Card'", "Mandatory pop-up is not shown when the user selectes 'Buy with Another Card'", 
					driver,	extentedReport, true);

			mTAPaymentPage.clickNoButtonPopupMessage(extentedReport, true);

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("iconHelpPreviouslyUsedCards"),
							mTAPaymentPage),
					"Help icon (?) is displayed against the 'Previously Used Cards' text in Card payment page",
					"Help icon (?) is not displayed against the 'Previously Used Cards' text in Card payment page",
					driver, extentedReport);

			mTAPaymentPage.hoverOnHelpIconOfPreviouslyUsedCards(extentedReport, true);
			Log.softAssertThat(mTAPaymentPage.verifyHelpIconPreviouslyUsedCardsPopupMessage(),
					mTAPaymentPage.POPUP_MSG_HELPICON_Previously_Used_Cards
					+ " - Message is displayed when click/hover the help icon (?) against the 'Previously Used Cards' text",
					mTAPaymentPage.POPUP_MSG_HELPICON_Previously_Used_Cards
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Previously Used Cards' text",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="If no is selected, a warning message should be displayed to the user to select an existing card as the default and taken back to the card screen - 'Please select an existing card as the default to be used for continuous payments.'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23455(String browser) throws Exception {

		String tcId = "TC_23455";
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

			// Select cover type
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);

			// Click Quote button
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat( mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mTAPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mTAPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			mTAPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.getPopupMessage(testData.popupMessage),
					"Mandatory pop-up is shown when the user selectes 'Buy with Another Card'", "Mandatory pop-up is not shown when the user selectes 'Buy with Another Card'", 
					driver,	extentedReport, true);

			mTAPaymentPage.clickNoButtonPopupMessage(extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.getErrorMessage(testData.errorMessage),
					"Warning message is shown when the user selectes 'No' in popup", "Warning message is not shown when the user selectes 'No' in popup", 
					driver,	extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Default checkbox should be hidden and thus not selected", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23456(String browser) throws Exception {

		String tcId = "TC_23456";
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

			// Select cover type
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);

			// Click Quote button
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat( mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);

			mTAPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.verifyDefaultSession(extentedReport),
					"Default Session is displayed in Card details page", "Default Session is not displayed in Card details page",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the new card details will be set as default if user doesn't have any existing cards and selected 'Yes' for the CPA question", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23449(String browser) throws Exception {

		String tcId = "TC_23449";
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

			// Select cover type
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);

			// Click Quote button
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat( mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);

			mTAPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			CardDetailsPage cardDetails = mTAPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			cardDetails.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport,
					true);
			//cardDetails.enterVerification(extentedReport, true);
			cardDetails.clickbuy(extentedReport, false);

			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="If existing card is selected ‘Buy’ button will be enabled to allow the user to pay with the existing card and the existing card will be set as the default if processed", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23453(String browser) throws Exception {

		String tcId = "TC_23453";
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

			// Select cover type
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);

			// Click Quote button
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat( mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							mTAPaymentPage),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.softAssertThat(mTAPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(mTAPaymentPage.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page", "Select Card Session is not displayed in Card details page",
					driver, extentedReport, true);

			mTAPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			mTAPaymentPage.selectCheckBoxForCard(testData.cardName, extentedReport, true);
			
			mTAPaymentPage.defaultCheckBoxForCard(testData.cardName, extentedReport, true);
			
			Log.softAssertThat(mTAPaymentPage.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport, true);
			
			mTAPaymentPage.clickBuyButton(extentedReport);
			
			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
}
