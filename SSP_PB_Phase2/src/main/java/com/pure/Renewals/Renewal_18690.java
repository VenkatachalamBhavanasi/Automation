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
import com.pure.selfservice.pages.BuyPaymentPage;
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

public class Renewal_18690  extends BaseTest {
	private String webSite;
	String sheetName = "Renewal_18690_";

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

	/******************* User story 18690 Motor Policy *****************/

	@Test(description="Verify that “Total Payment” and \"Amount to pay for you policy renewal\" which is displayed in bank details screen is fetched correctly from Payment required screen and matches same in BO for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22514(String browser) throws Exception {

		String tcId = "TC_22514";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);
			
			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that payment date section is only visible when Business of this Policy was already paid by via Credit card Payment option and during renewal option if user opts monthly option for the first time for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22515(String browser) throws Exception {

		String tcId = "TC_22515";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.verifyPreferredPaymentDate(extentedReport, true),"Preferred payment date is Showed in Payment page", 
					"Preferred payment date is not Showed in Payment page", driver, extentedReport, true);	

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyPaymentDate(extentedReport, true),"Preferred payment date is Showed in Payment page", 
					"Preferred payment date is not Showed in Payment page", driver, extentedReport, true);	

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that user can capture the bank details only if they are not available and  when Business of this Policy was already paid via credit card option for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22516(String browser) throws Exception {

		String tcId = "TC_22516";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);
			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			//buyPaymentPage.enterBankName(testData.bankName, extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Add your Bank details section is read only format when Business of this Policy was already paid via premium finance option for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22517(String browser) throws Exception {

		String tcId = "TC_22517";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			//renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyBankDetailsEnabled(extentedReport, true),
					"Bank details are displayed read only format in payment page", 
					"Bank details are not displayed read only format in payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Sort Code is displayed along with hyphen in Add your Bank details section for Motor policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22518(String browser) throws Exception {

		String tcId = "TC_22518";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);


			/*Log.softAssertThat(buyPaymentPage.verifyBankDetailsEnabled(extentedReport, true),
					"Bank details are displayed read only format in payment page", 
					"Bank details are not displayed read only format in payment page", driver,
					extentedReport, true);*/

			//buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			//buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifySortCode(extentedReport, true),
					"Sort code is displayed with hyphen in add your bank details section in payment page", 
					"Sort code is not displayed with hyphen in add your bank details section in payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Sort Code is separated with hyphen in Add your Bank details section when user enters sort code for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22519(String browser) throws Exception {

		String tcId = "TC_22519";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);


			/*Log.softAssertThat(buyPaymentPage.verifyBankDetailsEnabled(extentedReport, true),
					"Bank details are displayed read only format in payment page", 
					"Bank details are not displayed read only format in payment page", driver,
					extentedReport, true);*/

			Log.softAssertThat(buyPaymentPage.verifySortCode(extentedReport, true),
					"Sort code is displayed separated with hyphen in add your bank details section in payment page", 
					"Sort code is not displayed separated with hyphen in add your bank details section in payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Account Number is shown masked except only last 4 digits when the bank details are already available for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22520(String browser) throws Exception {

		String tcId = "TC_22520";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			//buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyAccountNumberIsMasked(extentedReport, true),
					"Bank Account number is displayed with masked format except last 4 digits in add your bank details section in payment page", 
					"Bank Account number is not displayed with masked format except last 4 digits in add your bank details section in payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Account Number can remain visible when details are being entered for new plans and allows 9 digits to be entered max for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22521(String browser) throws Exception {

		String tcId = "TC_22521";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			/*Log.softAssertThat(buyPaymentPage.verifyAccountNumberIsMasked(extentedReport, true),
					"Bank Account number is displayed with masked format except last 4 digits in add your bank details section in payment page", 
					"Bank Account number is not displayed with masked format except last 4 digits in add your bank details section in payment page", driver,
					extentedReport, true);*/

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyAccountNumber(extentedReport, true),
					"Valid account number is entered into add your bank details section in the Renew Payment page", "Invalid account number is entered into add your bank details section in the Renew Payment page", driver,
					extentedReport, true);


			//buyPaymentPage.clickBuyButton(extentedReport, true);


			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Bank name is automatically displayed and bank details are validated automatically after entering sort code  and Account number for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22522(String browser) throws Exception {

		String tcId = "TC_22522";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyBankNameIsEmpty(extentedReport, true),
					"Bank name is auto populated after entering the sort code and account number in add your bank details section in payment page", 
					"Bank name is not auto populated after entering the sort code and account number in add your bank details section in payment page", driver,
					extentedReport, true);

			/*Log.softAssertThat(
					!buyPaymentPage.uielement.verifyPageElements(Arrays.asList("errorMssgforBankDetails"),buyPaymentPage),
					"Error message is not displaying after entering sort code and account number in add your bank details section",
					"Error message is displaying after entering wrong sort code and account number in add your bank details section", driver, extentedReport,
					true);*/


			//buyPaymentPage.clickBuyButton(extentedReport, true);


			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that error message is automatically displayed when invalid bank details like sort code  and Account number are entered for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22523(String browser) throws Exception {

		String tcId = "TC_22523";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("errorMssgforBankDetails"),buyPaymentPage),
					"Error message is displaying after entering wrong sort code and account number in add your bank details section",
					"Error message is not displaying after entering wrong sort code and account number in add your bank details section", driver, extentedReport,
					true);


			//buyPaymentPage.clickBuyButton(extentedReport, true);


			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that advisory message is displayed at bottom of bank details page with payment plan provider as displayed in BO for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22524(String browser) throws Exception {

		String tcId = "TC_22524";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyTextMessageOfPaymentPlan(extentedReport, true),
					"Payment plan text message is displayed below add your bank details section",
					"Payment plan text message is not displayed below add your bank details section", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyOfPaymentPlanProvider(extentedReport, true),
					"Payment plan provider is displayed in the advisory message below add your bank details section",
					"Payment plan text message is not displayed in the advisory message below add your bank details section", driver,
					extentedReport, true);


			//buyPaymentPage.clickBuyButton(extentedReport, true);


			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Buy button is disabled until mandatory fields are entered by the customer for Motor Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22525(String browser) throws Exception {

		String tcId = "TC_22525";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);

			Log.softAssertThat(!buyPaymentPage.verifyBuyButton(extentedReport, true),
					"Buy button is disabled until the customer entered mandatory fields",
					"Buy button is enabled without entering the mandatory fields by customer", driver,
					extentedReport, true);
			
			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyBuyButton(extentedReport, true),
					"Buy button is enabled after customer entering all mandatory fields",
					"Buy button is disabled after customer entering the mandatory fields", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);


			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify if user could able to make successful payment by clicking on the ‘Buy’ button in Bank details screen and Event log (System Audit History) would be generated with details of the unsuccessful payment.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22526(String browser) throws Exception {

		String tcId = "TC_22526";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//Entering the bank details
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			//buyPaymentPage.enterBankName(testData.bankName, extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			//Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(
					successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"),
							renewMyPolicyPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport,
					true);

			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"Payment successfully completed without any error while doing renewal policy",
					"Payment unsuccessfully completed while doing renewal policy ", driver,
					extentedReport, true);

			Log.message("Buy process is successfully completed and event log history generated", extentedReport);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Credit card payment screen to be displayed only when user has to pay some deposit amount which is greater than zero pounds for motor Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22527(String browser) throws Exception {

		String tcId = "TC_22527";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmountRenewal),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			/*Log.softAssertThat(buyPaymentPage.verifyDepositAmount(extentedReport, true),
					"Deposit amount is greaterthan ZERO shown in the Renew Payment page", 
					"Deposit amount is lessthan ZERO show in the Renew Payment page", driver,
					extentedReport, true);*/

			buyPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage renewalPaymentPage = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not naivgated to Credit card details page in the Renewal Payment page", driver, extentedReport,
					true);


			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Credit card payment screen should not be displayed when user has to pay some deposit amount which is less than zero pounds for motor Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22528(String browser) throws Exception {

		String tcId = "TC_22528";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			/*Log.softAssertThat(buyPaymentPage.verifyDepositAmount(extentedReport, true),
					"Deposit amount is greaterthan ZERO shown in the Renew Payment page", 
					"Deposit amount is lessthan ZERO show in the Renew Payment page", driver,
					extentedReport, true);*/

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.softAssertThat(
					!buyPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							buyPaymentPage),
					"Deposit amount is lessthan ZERO and credit card details are not shown in the Renew Payment page",
					"Deposit amount is greaterthan ZERO and credit card details are show in the Renew Payment page", driver, extentedReport,
					true);

			//Success page
			/*SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(
					successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"),
							renewMyPolicyPage),
					"Successfully navigated to success page without showing credit card details after clicking buy button",
					"Unsuccessfully navigated to success page without showing credit card details after clicking buy button", driver, extentedReport,
					true);*/

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that clicking on Back button in the bank details page will be navigated back to the SS Dashboard screen by hitting the Close button in pop up window for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22529(String browser) throws Exception {

		String tcId = "TC_22529";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			buyPaymentPage.clickBackButton(extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyMssgInClosePopup(extentedReport, true),
					"Close popup message is displayed in the Renew Payment page", 
					"Close popup message is not displayed in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.selectClose(extentedReport);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking close button in popup",
					"Unsuccessfully navigated to customer dashboard page after clicking close button in popup", driver, extentedReport,
					true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify if user could able to make successful payment of deposit amount by clicking on the ‘Buy’ button in pay by card screen for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22530(String browser) throws Exception {

		String tcId = "TC_22530";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmountRenewal),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			buyPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage RenewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					RenewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							RenewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver, extentedReport);
			
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

	//@Test(description="Verify that any new Credit card processed in buy Renewals to pay deposit amount should be saved with the client in pure for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22531(String browser) throws Exception {

		String tcId = "TC_22531";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmountRenewal),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			buyPaymentPage.clickBuyButton(extentedReport, true);

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

	//@Test(description="Verify that User must be able to pay by an existing credit card for deposit amount when the new business policy premium was paid by credit card for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22532(String browser) throws Exception {

		String tcId = "TC_22532";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmountRenewal),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			buyPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage renewalPaymentPage =new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not navigated to Credit card details page in the Renewal Payment page", driver, extentedReport,
					true);

			//Credit card page
			//CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			//WaitUtils.waitForPageLoad(driver);
			//driver.switchTo().frame("wp-cl-custom-html-iframe");

			//cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);


			//Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(
					successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"),
							renewMyPolicyPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport,
					true);

			Log.softAssertThat(
					successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport,
					true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that User would be navigated back to dashboard screen on clicking the Finish button on Confirmation screen for successful or unsuccessful renewal and Renewed policy and documents would be available on the policy details, if renewal gets successfully confirmed for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22533(String browser) throws Exception {

		String tcId = "TC_22533";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			
			RenewalPaymentPage renewalPaymentPage =new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not navigated to Credit card details page in the Renewal Payment page", driver, extentedReport,
					true);

			//Credit card page
			CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
			 

			//Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(
					successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"),
							successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport,
					true);

			Log.softAssertThat(
					successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport,
					true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dashboard page after clicking finish button in Success page", driver, extentedReport,
					true);


			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description=" Verify that User would be navigated back to dashboard screen on clicking the Finish button on Confirmation screen for successful or unsuccessful renewal and Renewed policy and documents would be available on the policy details, if renewal gets successfully confirmed for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22534(String browser) throws Exception {

		String tcId = "TC_22534";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			buyPaymentPage.clickBuyButton(extentedReport, true);

			
			RenewalPaymentPage renewalPaymentPage =new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not navigated to Credit card details page in the Renewal Payment page", driver, extentedReport,
					true);

			//Credit card page
			CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
			 

			//Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(
					successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"),
							successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport,
					true);

			Log.softAssertThat(
					successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport,
					true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dashboard page after clicking finish button in Success page", driver, extentedReport,
					true);


			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that upon a Unsuccessful Payment User would be informed with error message and the policy will move to ‘Confirmed’ status and WM task will be generated similar to Self Service MTA and Event log (System Audit History) would be generated with details of the unsuccessful payment for motor policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22535(String browser) throws Exception {

		String tcId = "TC_22535";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			buyPaymentPage.clickBuyButton(extentedReport, true);

			
			RenewalPaymentPage renewalPaymentPage =new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not navigated to Credit card details page in the Renewal Payment page", driver, extentedReport,
					true);

			//Credit card page
			CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
			 

			//Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(
					successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"),
							successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport,
					true);

			Log.softAssertThat(
					successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport,
					true);

			Log.softAssertThat(
					!successPage.getSuccessMsg(extentedReport, true),
					"Unsuccessfully completed the buy process by clicking buy button",
					"Successfully completed the buy process by clicking buy button", driver, extentedReport,
					true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dashboard page after clicking finish button in Success page", driver, extentedReport,
					true);


			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description="Verify that Payment plan provider, Deposit of First payment, Followed by <X> monthly payments, Interest rate% & APR% which is displayed in bank details screen is fetched correctly from Payment required page & matches same in BO for Motor policies", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")

	public void TC_22537(String browser) throws Exception {

		String tcId = "TC_22537";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getDepositOf(testData.depositOf),
					"Deposit amount is shown in the Renew Payment page",
					"Deposit amount is  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getFirstPaymentOf(testData.firstPayment),
					"First Payment amount is shown in the Renew Payment page",
					"First Payment amount is  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getFollowedMonthlyPayments(testData.followedMonthlyPayment),
					"Followed monthly Payment amount is shown in the Renew Payment page",
					"Followed monthly Payment amount is  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyTranscationFeeAndAPR(testData.transactionFee,extentedReport, true),
					"Transaction fee is shown in the Payment page",
					"Transaction fee is  is not shown in the Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyTranscationFeeAndAPR(testData.apr,extentedReport, true),
					"APR is shown in the Payment page",
					"APR is  is not shown in the Payment page", driver,
					extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	
	/********** User story 18690 Acceptance Criteria *****************/
	
	/********** Test Data get Expired *****************/
		
	//@Test(description="Verify that Payment Plan & Bank details of the defaulted Monthly (Premium Finance) payment method for a recommended renewal Quote group of an active renewal Quote is correctly displayed for an Insurer led Motor Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22696(String browser) throws Exception {

		String tcId = "TC_22696";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that Payment Plan & Bank details of the defaulted Monthly (Premium Finance) payment method for a recommended renewal Quote group of an active renewal Quote is correctly displayed for an Broker Led (non EDI) PPL Motor Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22697(String browser) throws Exception {

		String tcId = "TC_22697";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that Payment Plan & Bank details of the defaulted Monthly (Premium Finance) payment method for a recommended renewal Quote group of an active renewal Quote is correctly displayed for a Broker Led PPL EDI Motor policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22698(String browser) throws Exception {

		String tcId = "TC_22698";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the Payment Plan & Bank details of recommended renewal Quote group of active renewal Premium Finance Motor Quote is correctly displayed when a Motor policy is renewed with Add-ons, fees & taxes on it.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22699(String browser) throws Exception {

		String tcId = "TC_22699";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the correct Payment Plan & Bank details is displayed at ‘Payment’ tab of ‘You have Chosen to pay in installments’ page if the New Business of this Policy was paid by “monthly” i.e. via Premium Finance Payment option for a Motor Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22700(String browser) throws Exception {

		String tcId = "TC_22700";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the correct Payment Plan & Bank details is displayed at ‘Payment’ tab of ‘You have Chosen to pay in installments’ page during renewal for Monthly (Premium Finance) payment method and it matches with the BO equivalent Motor Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22701(String browser) throws Exception {

		String tcId = "TC_22701";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmountRenewal),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that no Payment Plan & Bank details are displayed if there is no preferred PF quote is available against any “Recommended” Scheme quote during renewal for a Motor Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22702(String browser) throws Exception {

		String tcId = "TC_22702";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that no Payment Plan & Bank details are displayed if there are multiple PF preferred/ saved PF quotes are available against any “Recommended” Scheme quote during renewal for a Motor Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22703(String browser) throws Exception {

		String tcId = "TC_22703";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);
			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify correct Payment Plan & Bank details is displayed if the New Business was paid via PF plan and at renewal quote the Holding PF Quote is been marked as preferred when mapping is not done in instalment scheme maintenance for a Motor Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22704(String browser) throws Exception {

		String tcId = "TC_22704";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify correct Payment Plan & Bank details is displayed if the New Business was paid via PF plan and at renewal quote by default the PF Quote is been marked as preferred as per the mapping in installment scheme maintenance for a Motor Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22705(String browser) throws Exception {

		String tcId = "TC_22705";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify correct overridden Payment Plan & Bank details of recommended renewal Quote group of active renewal Quote are displayed when the Premium is overridden for an Insurer led Motor policy that has already been invited within Back-Office.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22706(String browser) throws Exception {

		String tcId = "TC_22706";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify correct Payment Plan & Bank details are displayed for the changed active renewal Quote when the active renewal Quote for an Insurer led Motor renewal invited quote is changed in Back Office.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22707(String browser) throws Exception {

		String tcId = "TC_22707";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the Payment Plan & Bank details of recommended renewal Quote group of active renewal Premium Finance Motor Quote is correctly displayed when a Household policy is renewed with Add-ons, fees & taxes on it.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22708(String browser) throws Exception {

		String tcId = "TC_22708";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the correct Payment Plan & Bank details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘You have Chosen to pay in installments’ page when the preferred quote is changed at Renewal Quote stage – via View quote process for a Motor PF Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22709(String browser) throws Exception {

		String tcId = "TC_22709";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the correct Payment Plan & Bank details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘You have Chosen to pay in installments’ page when the preferred Quote is changed at Renewal Quote stage – via What if quote process for a Motor Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22710(String browser) throws Exception {

		String tcId = "TC_22710";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the correct Payment Plan & Bank details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘You have Chosen to pay in installments’ page when the preferred Quote is changed at Renewal Invite stage – via View quote process for a Motor Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22711(String browser) throws Exception {

		String tcId = "TC_22711";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the correct Payment Plan & Bank details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘You have Chosen to pay in installments’ page when the preferred Quote is changed at Renewal Invite stage – via What if quote process for a Motor Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22712(String browser) throws Exception {

		String tcId = "TC_22712";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the correct Payment Plan & Bank details is displayed at ‘Payment’ tab of ‘You have Chosen to pay in installments’ page along with automatically applied Add-ons when add-ons are already applied to the Motor policy and marked as ‘Apply at renewal’ during NB.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22713(String browser) throws Exception {

		String tcId = "TC_22713";
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
			//customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	/******************* User story 18690 HouseHold Policy *****************/

	@Test(description="Verify that 'Total Payment' and 'Amount to pay for you policy renewal' which is displayed in bank details screen is fetched correctly from Payment required screen and matches same in BO for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22538(String browser) throws Exception {

		String tcId = "TC_22538";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);
			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

				Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Installments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that payment date section is only visible when Business of this Policy was already paid by via Credit card Payment option and during renewal option if user opts monthly option for the first time for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22539(String browser) throws Exception {

		String tcId = "TC_22539";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.verifyPreferredPaymentDate(extentedReport, true),"Preferred payment date is Showed in Payment page", 
					"Preferred payment date is not Showed in Payment page", driver, extentedReport, true);	

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyPaymentDate(extentedReport, true),"Preferred payment date is Showed in Payment page", 
					"Preferred payment date is not Showed in Payment page", driver, extentedReport, true);	

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that user can capture the bank details only if they are not available and  when Business of this Policy was already paid via credit card option for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22540(String browser) throws Exception {

		String tcId = "TC_22540";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);


			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Add your Bank details section is read only format when Business of this Policy was already paid via premium finance option for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22541(String browser) throws Exception {

		String tcId = "TC_22541";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyBankDetailsEnabled(extentedReport, true),
					"Bank details are displayed read only format in payment page", 
					"Bank details are not displayed read only format in payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Sort Code is displayed along with hyphen in Add your Bank details section for Household policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22542(String browser) throws Exception {

		String tcId = "TC_22542";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifySortCode(extentedReport, true),
					"Sort code is displayed with hyphen in add your bank details section in payment page", 
					"Sort code is not displayed with hyphen in add your bank details section in payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Sort Code is separated with hyphen in Add your Bank details section when user enters sort code for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22543(String browser) throws Exception {

		String tcId = "TC_22543";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);


			/*Log.softAssertThat(buyPaymentPage.verifyBankDetailsEnabled(extentedReport, true),
					"Bank details are displayed read only format in payment page", 
					"Bank details are not displayed read only format in payment page", driver,
					extentedReport, true);*/

			Log.softAssertThat(buyPaymentPage.verifySortCode(extentedReport, true),
					"Sort code is displayed separated with hyphen in add your bank details section in payment page", 
					"Sort code is not displayed separated with hyphen in add your bank details section in payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Account Number is shown masked except only last 4 digits when the bank details are already available for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22544(String browser) throws Exception {

		String tcId = "TC_22544";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyAccountNumberIsMasked(extentedReport, true),
					"Bank Account number is displayed with masked format except last 4 digits in add your bank details section in payment page", 
					"Bank Account number is not displayed with masked format except last 4 digits in add your bank details section in payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Account Number can remain visible when details are being entered for new plans and allows 9 digits to be entered max for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22545(String browser) throws Exception {

		String tcId = "TC_22545";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			/*Log.softAssertThat(buyPaymentPage.verifyAccountNumberIsMasked(extentedReport, true),
					"Bank Account number is displayed with masked format except last 4 digits in add your bank details section in payment page", 
					"Bank Account number is not displayed with masked format except last 4 digits in add your bank details section in payment page", driver,
					extentedReport, true);*/

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyAccountNumber(extentedReport, true),
					"Valid account number is entered into add your bank details section in the Renew Payment page", "Invalid account number is entered into add your bank details section in the Renew Payment page", driver,
					extentedReport, true);


			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Bank name is automatically displayed and bank details are validated automatically after entering sort code and Account number for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22546(String browser) throws Exception {

		String tcId = "TC_22546";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyBankNameIsEmpty(extentedReport, true),
					"Bank name is auto populated after entering the sort code and account number in add your bank details section in payment page", 
					"Bank name is not auto populated after entering the sort code and account number in add your bank details section in payment page", driver,
					extentedReport, true);

			/*Log.softAssertThat(
					!buyPaymentPage.uielement.verifyPageElements(Arrays.asList("errorMssgforBankDetails"),buyPaymentPage),
					"Error message is not displaying after entering sort code and account number in add your bank details section",
					"Error message is displaying after entering wrong sort code and account number in add your bank details section", driver, extentedReport,
					true);*/


			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that error message is automatically displayed when invalid bank details like sort code  and Account number are entered for House Hold policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22547(String browser) throws Exception {

		String tcId = "TC_22547";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("errorMssgforBankDetails"),buyPaymentPage),
					"Error message is displaying after entering wrong sort code and account number in add your bank details section",
					"Error message is not displaying after entering wrong sort code and account number in add your bank details section", driver, extentedReport,
					true);


			//buyPaymentPage.clickBuyButton(extentedReport, true);


			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that advisory message is displayed at bottom of bank details page with payment plan provider as displayed in BO for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22548(String browser) throws Exception {

		String tcId = "TC_22548";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyTextMessageOfPaymentPlan(extentedReport, true),
					"Payment plan text message is displayed below add your bank details section",
					"Payment plan text message is not displayed below add your bank details section", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyOfPaymentPlanProvider(extentedReport, true),
					"Payment plan provider is displayed in the advisory message below add your bank details section",
					"Payment plan text message is not displayed in the advisory message below add your bank details section", driver,
					extentedReport, true);


			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Buy button is disabled until mandatory fields are entered by the customer for House Hold Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22549(String browser) throws Exception {

		String tcId = "TC_22549";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);

			Log.softAssertThat(!buyPaymentPage.verifyBuyButton(extentedReport, true),
					"Buy button is disabled until the customer entered mandatory fields",
					"Buy button is enabled without entering the mandatory fields by customer", driver,
					extentedReport, true);

			/*buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);*/
			
			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);
			

			Log.softAssertThat(buyPaymentPage.verifyBuyButton(extentedReport, true),
					"Buy button is enabled after customer entering all mandatory fields",
					"Buy button is disabled after customer entering the mandatory fields", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify if user could able to make successful payment by clicking on the ‘Buy’ button in Bank details screen and Event log (System Audit History) would be generated with details of the unsuccessful payment.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22550(String browser) throws Exception {

		String tcId = "TC_22550";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//Entering the bank details
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterBankName(testData.bankName, extentedReport, true);


			//buyPaymentPage.clickBuyButton(extentedReport, true);

			//Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(
					successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"),
							renewMyPolicyPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport,
					true);

			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"Payment successfully completed without any error while doing renewal policy",
					"Payment unsuccessfully completed while doing renewal policy ", driver,
					extentedReport, true);

			Log.message("Buy process is successfully completed and event log history generated", extentedReport);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description="Verify that Credit card payment screen to be displayed only when user has to pay some deposit amount which is greater than zero pounds for House Hold Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22551(String browser) throws Exception {

		String tcId = "TC_22551";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmountRenewal),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			/*Log.softAssertThat(buyPaymentPage.verifyDepositAmount(extentedReport, true),
					"Deposit amount is greaterthan ZERO shown in the Renew Payment page", 
					"Deposit amount is lessthan ZERO show in the Renew Payment page", driver,
					extentedReport, true);*/

			buyPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage renewalPaymentPage = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not naivgated to Credit card details page in the Renewal Payment page", driver, extentedReport,
					true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that Credit card payment screen should not be displayed when user has to pay some deposit amount which is less than zero pounds for House Hold Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22552(String browser) throws Exception {

		String tcId = "TC_22552";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			/*Log.softAssertThat(buyPaymentPage.verifyDepositAmount(extentedReport, true),
					"Deposit amount is greaterthan ZERO shown in the Renew Payment page", 
					"Deposit amount is lessthan ZERO show in the Renew Payment page", driver,
					extentedReport, true);*/
			
			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.softAssertThat(
					!buyPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							buyPaymentPage),
					"Deposit amount is lessthan ZERO and credit card details are not shown in the Renew Payment page",
					"Deposit amount is greaterthan ZERO and credit card details are show in the Renew Payment page", driver, extentedReport,
					true);

			//Success page
			/*SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(
					successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"),
							renewMyPolicyPage),
					"Successfully navigated to success page without showing credit card details after clicking buy button",
					"Unsuccessfully navigated to success page without showing credit card details after clicking buy button", driver, extentedReport,
					true);*/

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description="Verify that clicking on Back button in the bank details page will be navigated back to the SS dashboard screen by hitting the Close button in pop up window for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22553(String browser) throws Exception {

		String tcId = "TC_22553";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			buyPaymentPage.clickBackButton(extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyMssgInClosePopup(extentedReport, true),
					"Close popup message is displayed in the Renew Payment page", 
					"Close popup message is not displayed in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.selectClose(extentedReport);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking close button in popup",
					"Unsuccessfully navigated to customer dashboard page after clicking close button in popup", driver, extentedReport,
					true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify if user could able to make successful payment of deposit amount by clicking on the ‘Buy’ button in pay by card screen for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22554(String browser) throws Exception {

		String tcId = "TC_22554";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmountRenewal),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			buyPaymentPage.clickBuyButton(extentedReport, true);

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

	//@Test(description="verify that any new Credit card processed in buy Renewals to pay deposit amount should be saved with the client in pure for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22555(String browser) throws Exception {

		String tcId = "TC_22555";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmountRenewal),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			buyPaymentPage.clickBuyButton(extentedReport, true);

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

	//@Test(description="Verify that User must be able to pay by an existing credit card for deposit amount when the new business policy premium was paid by credit card for Household policies", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22556(String browser) throws Exception {

		String tcId = "TC_22556";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmountRenewal),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			buyPaymentPage.clickBuyButton(extentedReport, true);

			RenewalPaymentPage renewalPaymentPage =new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not navigated to Credit card details page in the Renewal Payment page", driver, extentedReport,
					true);

			//Credit card page
			//CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			//WaitUtils.waitForPageLoad(driver);
			//driver.switchTo().frame("wp-cl-custom-html-iframe");

			//cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);


			//Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(
					successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"),
							renewMyPolicyPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport,
					true);

			Log.softAssertThat(
					successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport,
					true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that User would be navigated back to dashboard screen on clicking the Finish button on Confirmation screen for successful or unsuccessful renewal and Renewed policy and documents would be available on the policy details, if renewal gets successfully confirmed for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22559(String browser) throws Exception {

		String tcId = "TC_22559";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			
			RenewalPaymentPage renewalPaymentPage =new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greater than ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is less than ZERO and not navigated to Credit card details page in the Renewal Payment page", driver, extentedReport,
					true);

			//Credit card page
			CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
			 

			//Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(
					successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"),
							successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport,
					true);

			Log.softAssertThat(
					successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport,
					true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dash board page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dash board page after clicking finish button in Success page", driver, extentedReport,
					true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that User would be navigated back to dashboard screen on clicking the Finish button on Confirmation screen for successful or unsuccessful renewal and Renewed policy and documents would be available on the policy details, if renewal gets successfully confirmed for Household policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22560(String browser) throws Exception {

		String tcId = "TC_22560";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			buyPaymentPage.clickBuyButton(extentedReport, true);

			
			RenewalPaymentPage renewalPaymentPage =new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not navigated to Credit card details page in the Renewal Payment page", driver, extentedReport,
					true);

			//Credit card page
			CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
			 

			//Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(
					successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"),
							successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport,
					true);

			Log.softAssertThat(
					successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport,
					true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dashboard page after clicking finish button in Success page", driver, extentedReport,
					true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that upon a Unsuccessful Payment User would be informed with error message and the policy will move to ‘Confirmed’ status and WM task will be generated similar to Self Service MTA and Event log (System Audit History) would be generated with details of the unsuccessful payment for Household policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22558(String browser) throws Exception {

		String tcId = "TC_22558";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			buyPaymentPage.clickBuyButton(extentedReport, true);

			
			RenewalPaymentPage renewalPaymentPage =new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not navigated to Credit card details page in the Renewal Payment page", driver, extentedReport,
					true);

			//Credit card page
			CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);

			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");

			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
			 

			//Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(
					successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"),
							successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport,
					true);

			Log.softAssertThat(
					successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport,
					true);

			Log.softAssertThat(
					!successPage.getSuccessMsg(extentedReport, true),
					"Unsuccessfully completed the buy process by clicking buy button",
					"Successfully completed the buy process by clicking buy button", driver, extentedReport,
					true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dashboard page after clicking finish button in Success page", driver, extentedReport,
					true);			

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description="Verify that Payment plan provider, Deposit of First payment, Followed by <X> monthly payments, Interest rate% and APR% which is displayed in bank details screen is fetched correctly from Payment required screen & matches same in BO for House Hold policies", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22557(String browser) throws Exception {

		String tcId = "TC_22557";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", 
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", 
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getDepositOf(testData.depositOf),
					"Deposit amount is shown in the Renew Payment page",
					"Deposit amount is  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getFirstPaymentOf(testData.firstPayment),
					"First Payment amount is shown in the Renew Payment page",
					"First Payment amount is  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getFollowedMonthlyPayments(testData.followedMonthlyPayment),
					"Followed monthly Payment amount is shown in the Renew Payment page",
					"Followed monthly Payment amount is  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyTranscationFeeAndAPR(testData.transactionFee,extentedReport, true),
					"Transaction fee is shown in the Payment page",
					"Transaction fee is  is not shown in the Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.verifyTranscationFeeAndAPR(testData.apr,extentedReport, true),
					"APR is shown in the Payment page",
					"APR is  is not shown in the Payment page", driver,
					extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
		
	/********** Test Data get Expired *****************/
	
	//@Test(description="Verify that Payment Plan & Bank details of the defaulted Monthly (Premium Finance) payment method for a recommended renewal Quote group of an active renewal Quote is correctly displayed for a Manual Re-broke for FCEDI Home policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22714(String browser) throws Exception {

		String tcId = "TC_22714";
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
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);
			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that Payment Plan & Bank details of the defaulted Monthly (Premium Finance) payment method for a recommended renewal Quote group of an active renewal Quote is correctly displayed for an Insurer led Household Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22715(String browser) throws Exception {

		String tcId = "TC_22715";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that Payment Plan & Bank details of the defaulted Monthly (Premium Finance) payment method for a recommended renewal Quote group of an active renewal Quote is correctly displayed for an Broker Led (non EDI) PPL Household Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22716(String browser) throws Exception {

		String tcId = "TC_22716";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that Payment Plan & Bank details of the defaulted Monthly (Premium Finance) payment method for a recommended renewal Quote group of an active renewal Quote is correctly displayed for a Broker Led PPL EDI Household policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22717(String browser) throws Exception {

		String tcId = "TC_22717";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that Payment Plan & Bank details of the defaulted Monthly (Premium Finance) payment method for a recommended renewal Quote group of an active renewal Quote is correctly displayed for a Manual Re-broke for FCEDI Household policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22718(String browser) throws Exception {

		String tcId = "TC_22718";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the correct Payment Plan & Bank details is displayed at ‘Payment’ tab of ‘You have Chosen to pay in installments’ page during renewal for Monthly (Premium Finance) payment method and it matches with the BO equivalent Household Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22719(String browser) throws Exception {

		String tcId = "TC_22719";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that no Payment Plan & Bank details are displayed if there is no preferred PF quote is available against any “Recommended” Scheme quote during renewal for a Household Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22720(String browser) throws Exception {

		String tcId = "TC_22720";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			//renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that no Payment Plan & Bank details are displayed if there are multiple PF preferred/ saved PF quotes are available against any “Recommended” Scheme quote during renewal for a Household Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22721(String browser) throws Exception {

		String tcId = "TC_22721";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			//renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify correct Payment Plan & Bank details is displayed if the New Business was paid via PF plan and at renewal quote by default the PF Quote is been marked as preferred as per the mapping in installment scheme maintenance for a Household Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22722(String browser) throws Exception {

		String tcId = "TC_22722";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify correct overridden Payment Plan & Bank details of recommended renewal Quote group of active renewal Quote are displayed when the Premium is overridden for an Insurer led Household policy that has already been invited within Back-Office.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22723(String browser) throws Exception {

		String tcId = "TC_22723";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify correct Payment Plan & Bank details are displayed for the changed active renewal Quote when the active renewal Quote for an Insurer led Household renewal invited quote is changed in Back Office.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22724(String browser) throws Exception {

		String tcId = "TC_22724";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the correct Payment Plan & Bank details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘You have Chosen to pay in installments’ page when the preferred quote is changed at Renewal Quote stage – via View quote process for a Household PF Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22725(String browser) throws Exception {

		String tcId = "TC_22725";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the correct Payment Plan & Bank details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘You have Chosen to pay in installments’ page when the preferred Quote is changed at Renewal Quote stage – via What if quote process for a Household Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22726(String browser) throws Exception {

		String tcId = "TC_22726";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the correct Payment Plan & Bank details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘You have Chosen to pay in installments’ page when the preferred Quote is changed at Renewal Invite stage – via View quote process for a Household Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22727(String browser) throws Exception {

		String tcId = "TC_22727";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the correct Payment Plan & Bank details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘You have Chosen to pay in installments’ page when the preferred Quote is changed at Renewal Invite stage – via What if quote process for a Household Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22728(String browser) throws Exception {

		String tcId = "TC_22728";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the correct Payment Plan & Bank details is displayed at ‘Payment’ tab of ‘You have Chosen to pay in installments’ page along with automatically applied Add-ons when add-ons are already applied to the Household policy and marked as ‘Apply at renewal’ during NB.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22729(String browser) throws Exception {

		String tcId = "TC_22729";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that the correct Payment Plan & Bank details is displayed at ‘Payment’ tab of ‘You have Chosen to pay in installments’ page if the New Business of this Policy was paid by “monthly” i.e. via Premium Finance Payment option for a Household Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22730(String browser) throws Exception {

		String tcId = "TC_22730";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		}
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify correct Payment Plan & Bank details is displayed if the New Business was paid via PF plan and at renewal quote the Holding PF Quote is been marked as preferred when mapping is not done in installment scheme maintenance for a Household Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_22731(String browser) throws Exception {

		String tcId = "TC_22731";
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

			renewMyPolicyPage.clickChangeButton(extentedReport, true);

			//Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page", "Total Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page", "Insurer Premium Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page", "Premium Fee Amount for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page", "Amount Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page", "Cost Of Credit for policy is not shown in the Renew Payment page", driver,
					extentedReport, true);
			
			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);
			renewalPaymentRequiredPage.clickBuyButton(extentedReport, true);

			//Buy Payment page
			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver, extentedReport,
					true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page", "Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page", "Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			//buyPaymentPage.clickBuyButton(extentedReport, true);

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
