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
import com.pure.selfservice.pages.CustomerDashboardPage;
import com.pure.selfservice.pages.CustomerServicePage;
import com.pure.selfservice.pages.CustomerSignInPage;
import com.pure.selfservice.pages.GetTestData;
import com.pure.selfservice.pages.RenewMyPolicyPage;
import com.pure.selfservice.pages.RenewalPaymentRequiredPage;
import com.relevantcodes.extentreports.ExtentTest;

@Listeners(EmailReport.class)
public class Renewal_18688_Household extends BaseTest {
	private String webSite;
	String sheetName = "Renewal_18688_";

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

	/** Sprint 09 **//**User Story 18688**/

	@Test(description = "Verify that by Default “Annually” Payment method must appear selected on ‘Payment screen’ if the New Business of this Policy was paid by “Annually” .i.e. Credit Card Payment option for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21954(String browser) throws Exception {

		String tcId = "TC_21954";
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
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtPaymentRequired"),
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

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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

	@Test(description = "Verify that if there is no preferred PF quote is available against any “Recommended” Scheme quote then “Monthly” option will not be available at payment screen for that particular scheme quote during renewal for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21955(String browser) throws Exception {

		String tcId = "TC_21955";
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


			Log.softAssertThat(!renewalPaymentPage.verifyMonthlyButton(extentedReport, true),"Monthly option is not Showed in Payment page", 
					"Monthly option is Showed in Payment page", driver, extentedReport, true);

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

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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

	@Test(description = "Verify that if there is multiple PF preferred/ saved PF quotes are available against any “Recommended” Scheme quote then “Monthly” option will not be available at payment screen for that particular scheme quote during renwal for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21956(String browser) throws Exception {

		String tcId = "TC_21956";
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


			Log.softAssertThat(!renewalPaymentPage.verifyMonthlyButton(extentedReport, true),"Monthly option is not Showed in Payment page", 
					"Monthly option is Showed in Payment page", driver, extentedReport, true);

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

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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

	/*@Test(description = "Verify that if default PF plan is not configured in BO (for new plans) then “Monthly” option will not be available at ‘Your Quote’ tab for that particular scheme quote during renewal for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21957(String browser) throws Exception {

		String tcId = "TC_21957";
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

			Log.softAssertThat(!renewalPaymentPage.verifyMonthlyButton(extentedReport, true),"Monthly option is not Showed in Payment page", 
					"Monthly option is Showed in Payment page", driver, extentedReport, true);

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

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	}*/

	@Test(description = "Verify that by Default “Monthly” Payment method must appear selected on  payment screen if the New Business of this Policy was paid by “monthly” i.e. via Premium Finance Payment option for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21958(String browser) throws Exception {

		String tcId = "TC_21958";
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

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),"Monthly option is Showed in Payment page", 
					"Monthly option is not Showed in Payment page", driver, extentedReport, true);

			renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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

	@Test(description = "Verify that preferred payment date section is not displayed when when user selects Anually button in your prefered payment choice section for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21959(String browser) throws Exception {

		String tcId = "TC_21959";
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

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),"Monthly option is Showed in Payment page", 
					"Monthly option is not Showed in Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickAnnualButton(extentedReport, true);

			Log.softAssertThat(!renewalPaymentPage.verifyPreferredPaymentDate(extentedReport, true),"Preferred payment date is Showed in Payment page", 
					"Preferred payment date is not Showed in Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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

	@Test(description = "Verify that preferred payment date section is only visible when Business of this Policy was already paid by via Credit card Payment option and during renewal option if user opts monthly option for the first time for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21960(String browser) throws Exception {

		String tcId = "TC_21960";
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

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),"Monthly option is Showed in Payment page", 
					"Monthly option is not Showed in Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyPreferredPaymentDate(extentedReport, true),"Preferred payment date is Showed in Payment page", 
					"Preferred payment date is not Showed in Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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

	//Not Applicable
	/*@Test(description = "Verify that preferred payment date section is only visible for new plans when Business of this Policy  will be paid via monthly option for the first time for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21961(String browser) throws Exception {

		String tcId = "TC_21961";
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

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),"Monthly option is Showed in Payment page", 
					"Monthly option is not Showed in Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyPreferredPaymentDate(extentedReport, true),"Preferred payment date is Showed in Payment page", 
					"Preferred payment date is not Showed in Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	}*/

	@Test(description = "Verify that At a given time, only one of the button can remain selected. So, selecting the Anually will deselect the Monthy for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21962(String browser) throws Exception {

		String tcId = "TC_21962";
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

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),"Monthly option is Showed in Payment page", 
					"Monthly option is not Showed in Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(!renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true),
					"Annual button is deselected when Monthly button is selected", "Annual button is not deselected when Monthly button is selected", driver, extentedReport, true);
			
			renewalPaymentPage.clickAnnualButton(extentedReport, true);
			
			Log.softAssertThat(!renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true),
					"Monthly button is deselected when Annual button is selected", "Monthly button is not deselected when Annual button is selected", driver, extentedReport, true);
			/*			
			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyPreferredPaymentDate(extentedReport, true),"Preferred payment date is Showed in Payment page", 
					"Preferred payment date is not Showed in Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"On clicking 'CANCEL' button redirected to Policy Detail Screen",
					"On clicking 'CANCEL' button did not redirect to Policy Detail Screen", driver, extentedReport);*/

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "Verify that the premium amount being displayed gets correctly updated as soon as payment method selection is changed from Anually to Monthly for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21963(String browser) throws Exception {

		String tcId = "TC_21963";
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
			
			renewalPaymentPage.clickAnnualButton(extentedReport, true);
			
			Log.softAssertThat(!renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true),
					"Monthly button is deselected when Annual button is selected", "Monthly button is not deselected when Annual button is selected", driver, extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),"Monthly option is Showed in Payment page", 
					"Monthly option is not Showed in Payment page", driver, extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.arrTotalpremiumAmount[0]),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);
			
			renewalPaymentPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(!renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true),
					"Annual button is deselected when Monthly button is selected", "Annual button is not deselected when Monthly button is selected", driver, extentedReport, true);
									
			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.arrTotalpremiumAmount[1]),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	
	@Test(description = "Verify that clicking on Cancel button in the Payment Selection process will be navigated back to the Policy Details screen by hitting the Close button for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21964(String browser) throws Exception {

		String tcId = "TC_21964";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);

			Log.softAssertThat(renewalPaymentPage.verifyMsgInCancelPopup(extentedReport, true),
					"'Your renewal has not been processed' with a ‘Close’ button is populated when user click the ‘Cancel’ button",
					"'Your renewal has not been processed' with a ‘Close’ button is not populated when user click the ‘Cancel’ button", driver, extentedReport, true);
			
			renewalPaymentPage.selectClose(extentedReport);

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

	@Test(description = "Verify that premium breakdown details (i.e. Premium, Fees & Add-ons) of the defaulted payment method for a recommended renewal Quote group of an active renewal Quote is correctly displayed for an Insurer Led Household Policy that has already been invited within Back-Office for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21965(String browser) throws Exception {

		String tcId = "TC_21965";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	
	@Test(description = "Verify that premium breakdown details (i.e. Premium, Fees & Add-ons) of the defaulted payment method for a recommended renewal Quote group of an active renewal Quote is correctly displayed for an Broker Led (non EDI) PPL Policy that has already been invited within Back-Office for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21966(String browser) throws Exception {

		String tcId = "TC_21966";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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

	@Test(description = "Verify that premium breakdown details (i.e. Premium, Fees & Add-ons) of the defaulted payment method for a recommended renewal Quote group of an active renewal Quote is correctly displayed for an Broker Led PPL EDI policy that has already been invited within Back-Office for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21967(String browser) throws Exception {

		String tcId = "TC_21967";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	
	@Test(description = "Verify that premium breakdown details (i.e. Premium, Fees & Add-ons) of the defaulted payment method for a recommended renewal Quote group of an active renewal Quote is correctly displayed for an Manual Re-broke for FCEDI policy that has already been invited within Back-Office for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21968(String browser) throws Exception {

		String tcId = "TC_21968";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	
	@Test(description = "Verify that premium breakdown details (i.e. Premium, Fees & Add-ons) is correctly displayed for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21969(String browser) throws Exception {

		String tcId = "TC_21969";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	
	/*@Test(description = "Verify that by Default “Monthly” Payment method must appear selected at ‘Payment’ tab of ‘Payment Required’ page if the New Business of this Policy was paid by “monthly” i.e. via Premium Finance Payment option for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21970(String browser) throws Exception {

		String tcId = "TC_21970";
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
			
			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),"Monthly option is Showed in Payment page", 
					"Monthly option is not Showed in Payment page", driver, extentedReport, true);

			renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page", "Total Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page", "Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page", "Premium Fee Amuont for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	}*/
	
	@Test(description = "Verify that premium breakdown details (i.e. Premium, Fees & Add-ons) of recommended renewal Quote group of active renewal Quote is correctly displayed when one or more versions of House Hold Policy are available with Cash Payment Method (New Business with an Additional Premium MTA on it) for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21971(String browser) throws Exception {

		String tcId = "TC_21971";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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

	@Test(description = "Verify that premium breakdown details (i.e. Premium, Fees & Add-ons) of recommended renewal Quote group of active renewal Quote is correctly displayed when one or more versions of House Hold Policy are available with PF Payment Method (New Business with an Additional Premium MTA on it) for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21972(String browser) throws Exception {

		String tcId = "TC_21972";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	
	@Test(description = "Verify that premium breakdown details (i.e. Premium, Fees & Add-ons) of recommended renewal Quote group of active renewal Quote is correctly displayed when one or more versions of House Hold Policy are available with Cash Payment Method (New Business with an return Premium MTA on it) for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21973(String browser) throws Exception {

		String tcId = "TC_21973";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	
	/*@Test(description = "Verify that premium breakdown details (i.e. Premium, Fees & Add-ons) of recommended renewal Quote group of active renewal Quote is correctly displayed when one or more versions of House Hold Policy are available with P F Payment Method (New Business with an return Premium MTA on it) for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21974(String browser) throws Exception {

		String tcId = "TC_21974";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	}*/

	@Test(description = "Verify that premium breakdown details (i.e. Premium, Fees & Add-ons) of recommended renewal Quote group of active renewal Quote is correctly displayed when policy is transacted & renewed with Add-ons, fees & taxes for Motor Renewal Quote for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21975(String browser) throws Exception {

		String tcId = "TC_21975";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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

	/*@Test(description = "Verify that the correct Premium breakdown details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘Payment Required’ page when the preferred Quote is changed at Renewal Quote stage – via View quote process for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21976(String browser) throws Exception {

		String tcId = "TC_21976";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	}*/

	/*@Test(description = "Verify that the correct Premium breakdown details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘Payment Required’ page when the preferred Quote is changed at Renewal Invite stage – via View quote process for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21977(String browser) throws Exception {

		String tcId = "TC_21977";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	}*/
	
	/*@Test(description = "Verify that the correct Premium breakdown details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘Payment Required’ page when the preferred Quote is changed at Renewal Invite stage – via View quote process for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21978(String browser) throws Exception {

		String tcId = "TC_21978";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	}*/
	
	/*@Test(description = "Verify that the correct Premium breakdown details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘Payment Required’ page when the preferred Quote is changed at Renewal Invite stage – via What if quote process for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21979(String browser) throws Exception {

		String tcId = "TC_21979";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	}*/
	
	//Not Applicable
	/*@Test(description = "Verify that the correct Premium breakdown details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘Payment Required’ page when the preferred Quote is changed at Renewal Invite stage – via What if quote process for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21980(String browser) throws Exception {

		String tcId = "TC_21980";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	}*/
	
	@Test(description = "Verify that the correct Premium breakdown details is displayed at ‘Payment’ tab of ‘Payment Required’ page along with automatically applied Add-ons when add-ons are already applied to the policy and marked as ‘Apply at renewal’ during NB for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21981(String browser) throws Exception {

		String tcId = "TC_21981";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	
	@Test(description = "Verify that the correct Premium breakdown details is displayed at ‘Payment’ tab of ‘Payment Required’ page along with automatically added Add-ons when add-ons configured for renewal process which is set as fixed amount and marked opt in automatically within BO for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21982(String browser) throws Exception {

		String tcId = "TC_21982";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	
	@Test(description = "Verify that the correct Premium breakdown details is displayed at ‘Payment’ tab of ‘Payment Required’ page but the Add-ons are not displayed automatically when add-ons configured for renewal process and not marked opt in automatically within BO for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21983(String browser) throws Exception {

		String tcId = "TC_21983";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
	
	@Test(description = "Verify that the correct Premium breakdown details is displayed at ‘Payment’ tab of ‘Payment Required’ page along with automatically added Add-ons when add-ons configured for renewal process which is set as % and marked opt in automatically within BO.(differed for Endsleigh) for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21984(String browser) throws Exception {

		String tcId = "TC_21984";
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
			
			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

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
		
}
