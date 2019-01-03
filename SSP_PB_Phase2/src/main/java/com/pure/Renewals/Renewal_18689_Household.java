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
import com.pure.selfservice.pages.CardDetailsPage;
import com.pure.selfservice.pages.CustomerDashboardPage;
import com.pure.selfservice.pages.CustomerServicePage;
import com.pure.selfservice.pages.CustomerSignInPage;
import com.pure.selfservice.pages.GetTestData;
import com.pure.selfservice.pages.RenewMyPolicyPage;
import com.pure.selfservice.pages.RenewalPaymentPage;
import com.pure.selfservice.pages.RenewalPaymentRequiredPage;
import com.pure.selfservice.pages.SuccessPage;
import com.relevantcodes.extentreports.ExtentTest;

@Listeners(EmailReport.class)
public class Renewal_18689_Household extends BaseTest {
	private String webSite;
	String sheetName = "Renewal_18689_";

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

	/** Sprint 09 **//**User Story 18689**/

	@Test(description = "Verify that user can navigate to pay by card screen only if Annually button is selected in  payment method screen for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22006(String browser) throws Exception {

		String tcId = "TC_22006";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that Payment Amount which is displayed in pay by card screen is same as actual amount paid by the client ie (Insurer Premium + Addons + Fees) for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22007(String browser) throws Exception {

		String tcId = "TC_22007";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			RenewalPaymentCard.getInsurerPremiumAmount(testData.premiumAmount);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}	
	
	@Test(description = "Verify that List of Existing Cards details which is displayed in BO is same that is displayed in pay by card screen(ignore any cards with a expiry date in the past) for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22008(String browser) throws Exception {

		String tcId = "TC_22008";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			RenewalPaymentCard.getInsurerPremiumAmount(testData.premiumAmount);

			//RenewalPaymentCard.selectCheckBoxForCard(testData.cardName, extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifyExistingCardName(testData.cardName, extentedReport, true),
					"Verified the Existing card Name in Payment page", "Not Verified the Existing card Name in Payment page", driver,	extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifyExistingCardNumber(testData.existingCardNumber, extentedReport, true),
					"Verified the Existing card Number in Payment page", "Not Verified the Existing card Number in Payment page", driver,	extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifyExistingCardExpiryDate(testData.cardExpiryDate, extentedReport, true),
					"Verified the Existing card Expiry date in Payment page", "Not Verified the Existing card Expiry date in Payment page", driver,	extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "Verify that If the last used Card by this customer is in the list provided in BO, and then pre selected by default in pay by card screen for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22009(String browser) throws Exception {

		String tcId = "TC_22009";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			RenewalPaymentCard.getInsurerPremiumAmount(testData.premiumAmount);

			//RenewalPaymentCard.selectCheckBoxForCard(testData.cardName, extentedReport, true);
			
			//RenewalPaymentCard.verifySelectedCheckBoxForCard(testData.cardName, extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "Verify that clicking on Cancel button in the Pay by card screen will be navigated back to the Policy Details screen by hitting the Close button for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22010(String browser) throws Exception {

		String tcId = "TC_22010";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			RenewalPaymentCard.clickCancelButton(extentedReport);
			
			Log.softAssertThat(RenewalPaymentCard.verifyMsgInCancelPopup(extentedReport, true),
					"'Your renewal has not been processed' with a ‘Close’ button is populated when user click the ‘Cancel’ button",
					"'Your renewal has not been processed' with a ‘Close’ button is not populated when user click the ‘Cancel’ button", driver, extentedReport, true);
			
			RenewalPaymentCard.selectClose(extentedReport);
			
			Log.softAssertThat(customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),	"On clicking 'CANCEL' button redirected to Policy Detail Screen",
					"On clicking 'CANCEL' button did not redirect to Policy Detail Screen", driver, extentedReport);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "Verify that ‘Buy’ button is disabled until a card is selected in pay by card screen for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22011(String browser) throws Exception {

		String tcId = "TC_22011";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			Log.softAssertThat(RenewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport, true);
			
			RenewalPaymentCard.selectCheckBoxForCard(testData.cardName, extentedReport, true);
			
			Log.softAssertThat(!RenewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is not clickable and disabled when Crdit Card is not checked",
					"Buy button is clickable and enabled when Crdit Card is checked", driver, extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description = "Verify if user could able to make successful payment by clicking on the ‘Buy’ button in pay by card screen for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22012(String browser) throws Exception {

		String tcId = "TC_22012";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			Log.softAssertThat(RenewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport, true);
			
			RenewalPaymentCard.selectCheckBoxForCard(testData.cardName, extentedReport, true);
			
			Log.softAssertThat(!RenewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is not clickable and disabled when Crdit Card is not checked",
					"Buy button is clickable and enabled when Crdit Card is checked", driver, extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description = "Verify that upon a Unsuccessful Payment User would be informed with error message and the policy will remain in Invited status until the payment gets confirmed and no transaction will be raised for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22013(String browser) throws Exception {

		String tcId = "TC_22013";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			//RenewalPaymentCard.selectCheckBoxForCard(testData.cardName, extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport, true);
			
			RenewalPaymentCard.clickBuyButton(extentedReport);
			
			SuccessPage success = new SuccessPage(driver).get();
			Log.softAssertThat(!success.getSuccessMsg(extentedReport, true), "Renewal not able to accept as LIVE",
					"Renewal policy accepted as LIVE");
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description = "Verify that any new Credit card processed in buy Renewals should be saved with the client in pure for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22014(String browser) throws Exception {

		String tcId = "TC_22014";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			RenewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);
			
			CardDetailsPage cardDetails = new CardDetailsPage(driver, extentedReport); 
			
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			cardDetails.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport,
					true);
			//cardDetails.enterVerification(extentedReport, true);
			cardDetails.clickbuy(extentedReport, false);
			
			SuccessPage success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true), "Renewal policy accepted as LIVE",
					"Renewal not able to accept as LIVE");
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "Verify that all the available credit cards on the client in pure where the expiry date has not been passed should be available for Renewal credit card payment for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22015(String browser) throws Exception {

		String tcId = "TC_22015";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			RenewalPaymentCard.getInsurerPremiumAmount(testData.premiumAmount);

			//RenewalPaymentCard.selectCheckBoxForCard(testData.cardName, extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifyExistingCardName(testData.cardName, extentedReport, true),
					"Verified the Existing card Name in Payment page", "Not Verified the Existing card Name in Payment page", driver,	extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifyExistingCardNumber(testData.existingCardNumber, extentedReport, true),
					"Verified the Existing card Number in Payment page", "Not Verified the Existing card Number in Payment page", driver,	extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifyExistingCardExpiryDate(testData.cardExpiryDate, extentedReport, true),
					"Verified the Existing card Expiry date in Payment page", "Not Verified the Existing card Expiry date in Payment page", driver,	extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description = "Verify that User must be able to pay by a new credit card for an additional Renewal Premium when the new business policy premium was paid by premium finance for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22016(String browser) throws Exception {

		String tcId = "TC_22016";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			Log.softAssertThat(!RenewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is not clickable and disabled when Crdit Card is not checked",
					"Buy button is clickable and enabled when Crdit Card is checked", driver, extentedReport, true);
			
			RenewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);
			
			CardDetailsPage cardDetails = new CardDetailsPage(driver, extentedReport); 
			
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			cardDetails.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport,
					true);
			//cardDetails.enterVerification(extentedReport, true);
			cardDetails.clickbuy(extentedReport, false);
			
			SuccessPage success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true), "Renewal policy accepted as LIVE",
					"Renewal not able to accept as LIVE");
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description = "Verify that List of Existing Cards details which is displayed in BO is same that is displayed in pay by card screen(ignore any cards with a expiry date in the past) for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22017(String browser) throws Exception {

		String tcId = "TC_22017";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			Log.softAssertThat(RenewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport, true);
			
			RenewalPaymentCard.clickBuyButton(extentedReport);
			
			SuccessPage success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true), "Renewal policy accepted as LIVE",
					"Renewal not able to accept as LIVE");
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description = "Verify that User must be able to pay by a new credit card for Renewal premium when the new business policy premium was paid by credit card for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22018(String browser) throws Exception {

		String tcId = "TC_22018";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			Log.softAssertThat(!RenewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is not clickable and disabled when Crdit Card is not checked",
					"Buy button is clickable and enabled when Crdit Card is checked", driver, extentedReport, true);
			
			RenewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);
			
			CardDetailsPage cardDetails = new CardDetailsPage(driver, extentedReport); 
			
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			cardDetails.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport,
					true);
			//cardDetails.enterVerification(extentedReport, true);
			cardDetails.clickbuy(extentedReport, false);
			
			SuccessPage success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true), "Renewal policy accepted as LIVE",
					"Renewal not able to accept as LIVE");
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "Verify that all the previously used and available credit cards with client in pure should be available to consumer when they pay Renewal premium by credit card for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22019(String browser) throws Exception {

		String tcId = "TC_22019";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			RenewalPaymentCard.getInsurerPremiumAmount(testData.premiumAmount);

			//RenewalPaymentCard.selectCheckBoxForCard(testData.cardName, extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifyExistingCardName(testData.cardName, extentedReport, true),
					"Verified the Existing card Name in Payment page", "Not Verified the Existing card Name in Payment page", driver,	extentedReport, true);
		
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description = "Verify that if consumer pay Renewal   premium by new credit card then the new credit card details should be saved with client in pure for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22020(String browser) throws Exception {

		String tcId = "TC_22020";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			Log.softAssertThat(!RenewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is not clickable and disabled when Crdit Card is not checked",
					"Buy button is clickable and enabled when Crdit Card is checked", driver, extentedReport, true);
			
			RenewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);
			
			CardDetailsPage cardDetails = new CardDetailsPage(driver, extentedReport); 
			
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			cardDetails.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport,
					true);
			//cardDetails.enterVerification(extentedReport, true);
			cardDetails.clickbuy(extentedReport, false);
			
			SuccessPage success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true), "Renewal policy accepted as LIVE",
					"Renewal not able to accept as LIVE");
			
			Log.message("Verify that new credit card details should be saved with client in pure Back Office.", extentedReport);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "Verify that removed card on client in pure should not be available for credit card payment for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22021(String browser) throws Exception {

		String tcId = "TC_22021";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			RenewalPaymentCard.getInsurerPremiumAmount(testData.premiumAmount);

			//RenewalPaymentCard.selectCheckBoxForCard(testData.cardName, extentedReport, true);
			
			Log.softAssertThat(!RenewalPaymentCard.verifyExistingCardNumber(testData.existingCardNumber, extentedReport, true),
					"Verified the Existing card Number in Payment page", "Not Verified the Existing card Number in Payment page", driver,	extentedReport, true);
		
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description = "Verify that expired card on client in pure should not be available for credit card payment.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22022(String browser) throws Exception {

		String tcId = "TC_22022";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			RenewalPaymentCard.getInsurerPremiumAmount(testData.premiumAmount);

			//RenewalPaymentCard.selectCheckBoxForCard(testData.cardName, extentedReport, true);
			
			Log.softAssertThat(!RenewalPaymentCard.verifyExistingCardExpiryDate(testData.cardExpiryDate, extentedReport, true),
					"Verified the Existing card Expiry Datein Payment page", "Not Verified the Existing card Expiry Date in Payment page", driver,	extentedReport, true);
		
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that Payment Amount on UXP must match with BO Policy Premium in Pure for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22023(String browser) throws Exception {

		String tcId = "TC_22023";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			RenewalPaymentCard.getInsurerPremiumAmount(testData.premiumAmount);
			
			Log.message("Verify that Payment Amount on UXP is match with Back office Policy Premium", extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the “Payment Amount” on pay By Card screen is fetched correctly from Quotes screen & Payment selection screen for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22024(String browser) throws Exception {

		String tcId = "TC_22024";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			RenewalPaymentCard.getInsurerPremiumAmount(testData.premiumAmount);
			
			Log.message("Verify that Payment Amount on UXP is match with Back office Policy Premium", extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description = "Verify that Buy with another card button is available in pay by card screen If the user elects to use a new card to renew policy and confirm that if new card  entered  details are stored in the Back Office (including the new token) for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22025(String browser) throws Exception {

		String tcId = "TC_22025";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			Log.softAssertThat(RenewalPaymentCard.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"'Buy with another card' button is available to select", "Buy with another card' button is not available to select", driver, extentedReport);
			
			RenewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);
			
			CardDetailsPage cardDetails = new CardDetailsPage(driver, extentedReport); 
			
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			cardDetails.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport,true);
			
			//cardDetails.enterVerification(extentedReport, true);
			cardDetails.clickbuy(extentedReport, false);
			
			SuccessPage success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true), "Renewal policy accepted as LIVE",
					"Renewal not able to accept as LIVE");
			
			Log.message("Verfiy the new card details are available in back office (including the new token)", extentedReport);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description = "Verify that User would be navigated back to dash board screen on clicking the Finish button on Confirmation screen for successful or unsuccessful renewal and Renewed policy and documents would be available on the policy details, if renewal gets successfully confirmed for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22026(String browser) throws Exception {

		String tcId = "TC_22026";
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
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page", "Property to be Insured is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy page is opend after clicking Show Quote button",
					"Renew My Policy page is not opend after clicking Show Quote button", driver, extentedReport,
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

			renewalPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
			//RenewalPaymentCard.selectCheckBoxForCard(testData.cardName, extentedReport, true);
			
			Log.softAssertThat(RenewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is clickable and disabled when Crdit Card is not checked", driver, extentedReport, true);
			
			RenewalPaymentCard.clickBuyButton(extentedReport);
			
			SuccessPage success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true), "Renewal not able to accept as LIVE",
					"Renewal policy accepted as LIVE");
			
			success.clickFinish(extentedReport, true);
			
			Log.softAssertThat(customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
					customerDashboardPage),	"On clicking 'Finish' button redirected to Policy Detail Screen",
			"On clicking 'Finish' button did not redirect to Policy Detail Screen", driver, extentedReport);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
}
