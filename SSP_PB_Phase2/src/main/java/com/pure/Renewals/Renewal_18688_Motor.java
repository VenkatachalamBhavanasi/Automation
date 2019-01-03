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
public class Renewal_18688_Motor extends BaseTest {

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

	// ************** User Story 18688 ***************

	@Test(description = "Verify that by Default “Annually” Payment method must appear selected on ‘Payment screen’ if the New Business of this Policy was paid by “Annually” .i.e. Credit Card Payment option for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21905(String browser) throws Exception {

		String tcId = "TC_21905";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that if there is no preferred PF quote is available against any “Recommended” Scheme quote then “Monthly” option will not be available at payment screen for that particular scheme quote during renewal for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21906(String browser) throws Exception {

		String tcId = "TC_21906";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId); 
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description); try {

		// Navigate to Login Page 
		CustomerServicePage selfServicePage = new	 CustomerServicePage(driver, webSite, extentedReport).get();
		Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

		CustomerSignInPage customerSignInPage =	selfServicePage.clickSignInButton(extentedReport, true);
		CustomerDashboardPage customerDashboardPage = customerSignInPage.
				loginToSSCustomerDashboard(testData.emailAddress, testData.password,extentedReport, true);

		// Click view my policy button
		//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
		customerDashboardPage.clickViewMYPolicyButton(testData.policyName,extentedReport, true);

		RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver,	extentedReport).get();
		
		Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
				"Renew My Policy page is opend after clicking view my policy button","Renew My Policy page is not opend after clicking view my policy button",
				driver, extentedReport, true);
		
		Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),"Period of cover for original policy is shown in the Renew My policy page",
				"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

		Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),"Period of cover for Renewal policy is shown in the Renew My policy page",
				"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);

		Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
				"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

		Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
				"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

		Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),"Vehicle Fuel is show in the Renew My Policy page",
				"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

		Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
				"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

		Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),"Vehicle Number of Doors is show in the Renew My Policy page",
				"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

		Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),"Vehicle Registration Number is show in the Renew My Policy page",
				"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

		Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),"Vehicle Transmission is show in the Renew My Policy page",
				"Vehicle Transmission is not show in the Renew My Policy page", driver,	extentedReport, false);

		//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

		renewMyPolicyPage.clickQuoteButton(extentedReport, true);

		RenewalPaymentRequiredPage renewalPaymentPage = new
		RenewalPaymentRequiredPage(driver, extentedReport).get();

		Log.softAssertThat(renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
				"Renew My Policy Payment page page is opend after clicking Show Quote button","Renew My Policy Payment page is not opend after clicking Show Quote button",
				driver, extentedReport, true);

		Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport,true), "The Monthly Button is displaying in Renew My Policy Payment page",
				"The Monthly Button is not displaying in Renew My Policy Payment page", driver, extentedReport, true);

		renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

		Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),"Total Premium Amuont for policy is shown in the Renew Payment page",
				"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

		Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),"Insurer Premium Amuont for policy is shown in the Renew Payment page",
				"Insurer Premium Amuont for policy is not shown in the Renew Payment page",	driver, extentedReport, true);

		Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),"Premium Fee Amuont for policy is shown in the Renew Payment page",
				"Premium Fee Amuont for policy is not shown in the Renew Payment page",	driver, extentedReport, true);

		renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue, testData.addedExtraFeaturesName, extentedReport, true);

		Log.testCaseResult(extentedReport);
		}
		catch (Exception e) 
		{ 
			Log.exception(e, driver, extentedReport); 
			
		} 
		finally 
		{ 
			Log.endTestCase(extentedReport);
			driver.quit(); 
		} 
	}

	//@Test(description="Verify that if there is multiple PF preferred/ saved PF quotes are available against any “Recommended” Scheme quote then “Monthly” option will not be available at payment screen for that particular scheme quote during renewal for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21907(String browser) throws Exception {

		String tcId = "TC_21907"; 
		final WebDriver driver = WebDriverFactory.get(browser); 
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description); try {

			// Navigate to Login Page 
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			//Click view my policy button
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName,extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get(); 
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button","Renew My Policy page is not opend after clicking view my policy button",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page","Period of cover for original policy is Not shown in the Renew My policy page",
					driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page","Period of cover for Renewal policy is Not shown in the Renew My policy page",
					driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),"Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData. vehicleRegistrationNumber),"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button","Renew My Policy Payment page is not opend after clicking Show Quote button",
					driver, extentedReport, true);

			Log.softAssertThat(!renewalPaymentPage.verifyMonthlyButton(extentedReport,true),"The Monthly Button is not displaying in Renew My Policy Payment page",
					"The Monthly Button is displaying in Renew My Policy Payment page", driver, extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page",driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.testCaseResult(extentedReport);
		}
		catch (Exception e) 
		{ 
			Log.exception(e, driver, extentedReport); 
		}
		finally 
		{ 
			Log.endTestCase(extentedReport);
			driver.quit(); 
		}
	}
	
	//@Test(description="Verify that if default PF plan is not configured in BO (for new plans) then “Monthly” option will not be available at ‘Your Quote’ tab for that particular scheme quote during renewal for Motor policies.",dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21908(String browser) throws Exception {

		String tcId = "TC_21908";
		final WebDriver driver = WebDriverFactory.get(browser); 
		GetTestData testData = new GetTestData(sheetName, tcId); 
		Log.testCaseInfo(testData.description +"<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description); 
		try {

			// Navigate to Login Page 
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage =	selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click view my policy button
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get(); 
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button","Renew My Policy page is not opend after clicking view my policy button",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate), "Period of cover for original policy is shown in the Renew My policy page",	
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),"Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver,	extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver,	extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new	RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button","Renew My Policy Payment page is not opend after clicking Show Quote button",
					driver, extentedReport, true);

			Log.softAssertThat(!renewalPaymentPage.verifyMonthlyButton(extentedReport,true),"The Monthly Button is not displaying in Renew My Policy Payment page",
					"The Monthly Button is displaying in Renew My Policy Payment page", driver,	extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page",	driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.testCaseResult(extentedReport); 
		}
		catch (Exception e) 
		{ 
			Log.exception(e,driver, extentedReport); 
		}
		finally 
		{ 
			Log.endTestCase(extentedReport);
			driver.quit(); 
		} 
	}
	
	//@Test(description="Verify that by Default “Monthly” Payment method must appear selected on  payment screen if the New Business of this Policy was paid by “monthly” i.e. via Premium Finance Payment option for Motor policies.",dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21909(String browser) throws Exception {

		String tcId = "TC_21909"; final WebDriver driver = WebDriverFactory.get(browser); 
		GetTestData testData = new GetTestData(sheetName, tcId); 
		Log.testCaseInfo(testData.description +	"<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description); 
		try {

			// Navigate to Login Page 
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage =	selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click view my policy button
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver,	extentedReport).get(); 
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button","Renew My Policy page is not opend after clicking view my policy button",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate), "Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),"Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver,	extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver,	extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new	RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button","Renew My Policy Payment page is not opend after clicking Show Quote button",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport,true), "The Monthly Button is displaying in Renew My Policy Payment page",
					"The Monthly Button is not displaying in Renew My Policy Payment page", driver, extentedReport, true);

			renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount), "Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount), "Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount), "Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue, testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost),"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.testCaseResult(extentedReport); 
		}
		catch (Exception e) 
		{ 
			Log.exception(e,driver, extentedReport); 
		}
		finally 
		{ 
			Log.endTestCase(extentedReport);
			driver.quit(); 
		}
	}
	
	@Test(description = "Verify that preferred payment date section is not displayed when user selects Annually button in your prefered payment choice section for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21910(String browser) throws Exception {

		String tcId = "TC_21910";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),
					"Monthly option is Showed in Payment page", "Monthly option is not Showed in Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.clickAnnualButton(extentedReport, true);

			Log.softAssertThat(!renewalPaymentPage.verifyPreferredPaymentDate(extentedReport, true),
					"Preferred payment date is Showed in Payment page",
					"Preferred payment date is not Showed in Payment page", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that preferred payment date section is only visible when Business of this Policy was already paid by via Credit card Payment option and during renewal option if user opts monthly option for the first time for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21911(String browser) throws Exception {

		String tcId = "TC_21911";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),
					"Monthly option is Showed in Payment page", "Monthly option is not Showed in Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyPreferredPaymentDate(extentedReport, true),
					"Preferred payment date is Showed in Payment page",
					"Preferred payment date is not Showed in Payment page", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// Not Applicable
	//@Test(description="Verify that preferred payment date section is only visible for new plans when Business of this Policy  will be paid via monthly option for the first time for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21912(String browser) throws Exception {

		String tcId = "TC_21912"; 
		final WebDriver driver = WebDriverFactory.get(browser); 
		GetTestData testData = new GetTestData(sheetName, tcId); 
		Log.testCaseInfo(testData.description +"<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description); try {

			// Navigate to Login Page 
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage =	selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			//Click view my policy button
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName,extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver,	extentedReport).get(); 

			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button","Renew My Policy page is not opend after clicking view my policy button",
					driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver,	extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver,	extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new	RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button","Renew My Policy Payment page is not opend after clicking Show Quote button",
					driver, extentedReport, true);

			//renewalPaymentPage.verifyYourPreferredPaymentChoice(extentedReport, true);

			renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page",	driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost),"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,	extentedReport, true);

			Log.testCaseResult(extentedReport); 
		}
		catch (Exception e) 
		{ 
			Log.exception(e, driver, extentedReport); 

		}
		finally 
		{ 
			Log.endTestCase(extentedReport);
			driver.quit(); 
		}
	}

	@Test(description = "Verify that At a given time, only one of the button can remain selected. So, selecting the Annually will deselect the Monthly for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21913(String browser) throws Exception {

		String tcId = "TC_21913";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),
					"Monthly option is Showed in Payment page", "Monthly option is not Showed in Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(!renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true),
					"Annual button is deselected when Monthly button is selected",
					"Annual button is not deselected when Monthly button is selected", driver, extentedReport, true);

			renewalPaymentPage.clickAnnualButton(extentedReport, true);

			Log.softAssertThat(!renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true),
					"Monthly button is deselected when Annual button is selected",
					"Monthly button is not deselected when Annual button is selected", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that the premium amount being displayed gets correctly updated as soon as payment method selection is changed from Annually to Monthly for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21914(String browser) throws Exception {

		String tcId = "TC_21914";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.clickAnnualButton(extentedReport, true);

			Log.softAssertThat(!renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true),
					"Monthly button is deselected when Annual button is selected",
					"Monthly button is not deselected when Annual button is selected", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),
					"Monthly option is Showed in Payment page", "Monthly option is not Showed in Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.arrTotalpremiumAmount[0]),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(!renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true),
					"Annual button is deselected when Monthly button is selected",
					"Annual button is not deselected when Monthly button is selected", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.arrTotalpremiumAmount[1]),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that clicking on Cancel button in the Payment Selection process will be navigated back to the Policy Details screen by hitting the Close button for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21915(String browser) throws Exception {

		String tcId = "TC_21915";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);

			Log.softAssertThat(renewalPaymentPage.verifyMsgInCancelPopup(extentedReport, true),
					"'Your renewal has not been processed' with a ‘Close’ button is populated when user click the ‘Cancel’ button",
					"'Your renewal has not been processed' with a ‘Close’ button is not populated when user click the ‘Cancel’ button",
					driver, extentedReport, true);

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

	@Test(description = "Verify that premium breakdown details (i.e. Premium, Fees & Add-ons) is correctly displayed for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21916(String browser) throws Exception {

		String tcId = "TC_21916";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description="Verify that the correct Premium breakdown details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘Payment Required’ page when the preferred Quote is changed at Renewal Quote stage – via View quote process for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21917(String browser) throws Exception {

		String tcId = "TC_21917"; 
		final WebDriver driver = WebDriverFactory.get(browser); 
		GetTestData testData = new GetTestData(sheetName, tcId); 
		Log.testCaseInfo(testData.description +	"<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description); 
		try {

			// Navigate to Login Page 
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage =	selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click view my policy button
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver,	extentedReport).get(); 
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button", "Renew My Policy page is not opend after clicking view my policy button",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate), "Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),	"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver,	extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver,	extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new	RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button", "Renew My Policy Payment page is not opend after clicking Show Quote button",
					driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport,true), "Monthly option is Showed in Payment page",
					"Monthly option is not Showed in Payment page", driver, extentedReport,true);

			renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount), "Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount), "Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page",	driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount), "Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page",	driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost), "Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),	"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport); 
		}
		catch (Exception e) 
		{ 
			Log.exception(e, driver, extentedReport); 
		} 
		finally 
		{ 
			Log.endTestCase(extentedReport);
			driver.quit(); 
		} 
	}

	//@Test(description="Verify that the correct Premium breakdown details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘Payment Required’ page when the preferred Quote is changed at Renewal Quote stage – via What if quote process for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21918(String browser) throws Exception {

		String tcId = "TC_21918"; 
		final WebDriver driver = WebDriverFactory.get(browser); 
		GetTestData testData = new	GetTestData(sheetName, tcId); 
		Log.testCaseInfo(testData.description +	"<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description); 
		try {
			
			// Navigate to Login Page 
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage =	selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click view my policy button
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver,	extentedReport).get(); 
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button", "Renew My Policy page is not opend after clicking view my policy button",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate), "Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),	"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),"Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver,	extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver,	extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new	RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button", "Renew My Policy Payment page is not opend after clicking Show Quote button",
					driver, extentedReport, true);

			renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount), "Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount), "Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount), "Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page",	driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue, testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost), "Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),	"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,	extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport); 
			
		} 
		catch (Exception e) 
		{ 
			Log.exception(e, driver, extentedReport); 
		} 
		finally 
		{ 
			Log.endTestCase(extentedReport);
			driver.quit(); 
		} 
	}

	//@Test(description="Verify that the correct Premium breakdown details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘Payment Required’ page when the preferred Quote is changed at Renewal Invite stage – via View quote process for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21919(String browser) throws Exception {

		String tcId = "TC_21919"; 
		final WebDriver driver = WebDriverFactory.get(browser); 
		GetTestData testData = new GetTestData(sheetName, tcId); 
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description); 
		try {
			
			// Navigate to Login Page 
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage =	selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click view my policy button
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get(); 
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button", "Renew My Policy page is not opend after clicking view my policy button",
					driver, extentedReport, true);
					
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate), "Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),	"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver,	extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new	RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button", "Renew My Policy Payment page is not opend after clicking Show Quote button",
					driver, extentedReport, true);

			renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount), "Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount), "Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page",	driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount), "Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", "Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost), "Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount), "Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,	extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport); 
		
		} 
		catch (Exception e) 
		{ 
			Log.exception(e, driver, extentedReport); 
			
		}
		finally 
		{ 
			Log.endTestCase(extentedReport);
			driver.quit(); 
		}
	}

	@Test(description = "Verify that by Default “Monthly” Payment method must appear selected at ‘Payment’ tab of ‘Payment Required’ page if the New Business of this Policy was paid by “monthly” i.e. via Premium Finance Payment option for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21920(String browser) throws Exception {

		String tcId = "TC_21920";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			// renewalPaymentPage.verifyYourPreferredPaymentChoice(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),
					"Monthly option is Showed in Payment page", "Monthly option is not Showed in Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that premium breakdown details (Premium,Fees,Addons) of the defaulted payment method for a recommended renewal Quote group of an active renewal Quote is correctly showed for an Insurer led Motor policy been invited within BO Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21921(String browser) throws Exception {

		String tcId = "TC_21921";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			// renewalPaymentPage.verifyYourPreferredPaymentChoice(extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the premium breakdown details (Premium,Fees,Addons) of the defaulted payment method for a recommended renewal Quote group of an active renewal Quote is showed displayed for an Broker Led(non EDI)PPL Policy invited within BO Motorpolicies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21922(String browser) throws Exception {

		String tcId = "TC_21922";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.arrTotalpremiumAmount[0]),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = " Verify that premium breakdown details (Premium,Fees,Add-ons) of the defaulted payment method for a recommended renewal Quote group of an active renewal Quote is correctly displayed for an Broker Led PPL EDI policy invited within BO Motorpolicies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21923(String browser) throws Exception {

		String tcId = "TC_21923";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.arrTotalpremiumAmount[0]),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that premium breakdown details (Premium,Fees,Addons) of the defaulted payment method for a recommended renewal Quote group of an active renewal Quote is correctly showed for Manual Re-broke for FCEDI policy invited within BO Motorpolicies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21924(String browser) throws Exception {

		String tcId = "TC_21924";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.arrTotalpremiumAmount[0]),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that premium breakdown details (Premium,Fees,Addons) of recommended renewal Quote group of active renewal Quoteis correctly showed when one/more versions of Motor Policy are available with CashPaymentMethod (NB with an AdditionalPremium MTA on it) Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21925(String browser) throws Exception {

		String tcId = "TC_21925";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.arrTotalpremiumAmount[0]),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that premium breakdown details (Premium,Fees,Addons) of recommended renewal Quote group of active renewal Quote is correctly showed when one/more versions of Motor Policy are available with PF Payment Method (NB with an Additional Premium MTA on it) Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21926(String browser) throws Exception {

		String tcId = "TC_21926";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),
					"Monthly option is Showed in Payment page", "Monthly option is not Showed in Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that premium breakdown details (Premium,Fees,Addons) of recommended renewal Quote group of active renewal Quote is correctly showed when one/more versions of Motor Policy are available with Cash Payment Method (NB with an return Premium MTA on it) Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21927(String browser) throws Exception {

		String tcId = "TC_21927";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that premium breakdown details (Premium,Fees,Addons) of recommended renewal Quote group of active renewal Quote is correctly showed when one/more versions of Motor Policy are available with PF Payment Method (NB with an return Premium MTA on it) Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21928(String browser) throws Exception {

		String tcId = "TC_21928";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),
					"Monthly option is Showed in Payment page", "Monthly option is not Showed in Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that premium breakdown details (Premium,Fees,Addons) of recommended renewal Quote group of active renewal Quote is correctly showed when policy is transacted & renewed with Addons, fees&taxes for Motor Renewal Quote Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21929(String browser) throws Exception {

		String tcId = "TC_21929";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// Not Applicable
	//@Test(description="Verify that the correct Premium breakdown details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘Payment Required’ page when the preferred Quote is changed at Renewal Invite stage–via What if quote process for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21930(String browser) throws Exception {

		String tcId = "TC_21930"; 
		final WebDriver driver = WebDriverFactory.get(browser); 
		GetTestData testData = new GetTestData(sheetName, tcId); 
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description); 
		try {
			
			// Navigate to Login Page 
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage =	selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click view my policy button
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver,	extentedReport).get(); 
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button", "Renew My Policy page is not opend after clicking view my policy button",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate), "Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate), "Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver,	extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button", "Renew My Policy Payment page is not opend after clicking Show Quote button",
					driver, extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount), "Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount), "Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount), "Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page",	driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue, testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport); 
			
		} 
		catch (Exception e) 
		{ 
			Log.exception(e, driver, extentedReport); 
		}
		finally 
		{ 
			Log.endTestCase(extentedReport);
			driver.quit(); 
		} 
	}
	
	//@Test(description="Verify that the correct Premium breakdown details of changed Preferred Quote scheme is displayed at ‘Payment’ tab of ‘Payment Required’ page when the preferred Quote is changed at Renewal Invite stage–via What if quote process for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21931(String browser) throws Exception {

		String tcId = "TC_21931"; 
		final WebDriver driver = WebDriverFactory.get(browser); 
		GetTestData testData = new GetTestData(sheetName, tcId); 
		Log.testCaseInfo(testData.description +	"<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {
			
			// Navigate to Login Page 
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click view my policy button
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get(); 
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button","Renew My Policy page is not opend after clicking view my policy button",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate), "Period of cover for Renewal policy is shown in the Renew My policy page", 
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
							
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver,	extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new	RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button", "Renew My Policy Payment page is not opend after clicking Show Quote button",
					driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport,true),"Monthly option is Showed in Payment page",
					"Monthly option is not Showed in Payment page", driver, extentedReport, true);

			renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount), "Total Premium Amuont for policy is shown in the Renew Payment page", 
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page",	driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount), "Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page",	driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments), "Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page", 
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost), "Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),	"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport); 
			
		} 
		catch (Exception e) 
		{ 
			Log.exception(e, driver, extentedReport); 
		}
		finally 
		{ 
			Log.endTestCase(extentedReport);
			driver.quit(); 
		}
	}

	@Test(description = "Verify that the correct Premium breakdown details is showed at ‘Payment’ tab of ‘Payment Required’ page along with automatically applied Addons when addons are already applied to the policy and marked as ‘Apply at renewal’ during NB for Motorpolicies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21932(String browser) throws Exception {

		String tcId = "TC_21932";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that the correct Premium breakdown details is showed at ‘Payment’ tab of 'Payment Required’ page along with automatically added Addons when addons configured for renewal process which is set as fixed amount and marked opt in automatically with in BO for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21933(String browser) throws Exception {

		String tcId = "TC_21933";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that the correct Premium breakdown details is displayed at ‘Payment’ tab of ‘PaymentRequired’ page but the Addons are not displayed automatically when addons configured for renewal process and not marked opt in automatically with in BO for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21934(String browser) throws Exception {

		String tcId = "TC_21934";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that the correct Premium breakdown details is showed at ‘Payment’ tab of ‘Payment Required’ page along with automatically added Addons when addons configured for renewal process which is set as % and marked opt in automatically with in BO (differed for Endsleigh) Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21935(String browser) throws Exception {

		String tcId = "TC_21935";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description="Verify that if the New Business finance Plan was left in Saved Updated state then at renewal the NB Scheme quotes must be available to choose so as to offer new Finance Planand by default the PF Quote will be marked as preferred as per default finance plan configuration in DB.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21936(String browser) throws Exception {

		String tcId = "TC_21936"; 
		final WebDriver driver = WebDriverFactory.get(browser); 
		GetTestData testData = new GetTestData(sheetName, tcId); 
		Log.testCaseInfo(testData.description +	"<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description); 
		try {

			// Navigate to Login Page 
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage =	selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click view my policy button
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver,	extentedReport).get(); 

			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button", "Renew My Policy page is not opend after clicking view my policy button",
					driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page", "Period of cover for original policy is Not shown in the Renew My policy page",
					driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page", "Period of cover for Renewal policy is Not shown in the Renew My policy page",
					driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),"Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver,	extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new	RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button", "Renew My Policy Payment page is not opend after clicking Show Quote button",
					driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),"Monthly option is Showed in Payment page",
					"Monthly option is not Showed in Payment page", driver, extentedReport,	true);

			renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount), "Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount), "Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page","Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue, testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost), "Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount), "Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,	extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);

			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport); 

		} 
		catch (Exception e) 
		{ 
			Log.exception(e,driver, extentedReport); 

		} 
		finally
		{ 
			Log.endTestCase(extentedReport);
			driver.quit(); 
		} 
	}

	@Test(description = "Verify that if the New Business finance Plan was cancelled then at renewal the NB Scheme quotes must be available to choose so as to offer new Finance Plan and by default the PF Quote will be marked as preferred as per default finance plan configurationin DB.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21937(String browser) throws Exception {

		String tcId = "TC_21937";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),
					"Monthly option is Showed in Payment page", "Monthly option is not Showed in Payment page", driver,
					extentedReport, true);

			renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description="Verify that if the NB was paid via PF plan then at renewal the Renewal Scheme quotes must be available to choose so as to renew the Finance Plan and by default the Holding PF Quote will be marked as preferred when mapping is not done in instalment scheme maintenance", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21938(String browser) throws Exception {

		String tcId = "TC_21938"; 
		final WebDriver driver = WebDriverFactory.get(browser); 
		GetTestData testData = new GetTestData(sheetName, tcId); 
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description); 
		try {

			// Navigate to Login Page 
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click view my policy button
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver,	extentedReport).get(); 
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button", "Renew My Policy page is not opend after clicking view my policy button",
					driver, extentedReport, true);
					
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate), "Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate), "Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver,extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new	RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button", "Renew My Policy Payment page is not opend after clicking Show Quote button",
					driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport, true),"Monthly option is Showed in Payment page",
					"Monthly option is not Showed in Payment page", driver, extentedReport, true);

			renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount), "Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount), "Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount), "Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments), "Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost), "Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount), "Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver,	extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport); 
			
		}
		catch (Exception e) 
		{
			Log.exception(e, driver, extentedReport); 
		}
		finally 
		{ 
			Log.endTestCase(extentedReport);
			driver.quit(); 
		}
	}

	//@Test(description="Verify that if the New Business was paid via PF plan then at renewal the Renewal Scheme quotes must be available to choose so as to renew the Finance Plan and by default the PF Quote will be marked as preferred as per the mapping in instalment scheme maintenance.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21939(String browser) throws Exception {

		String tcId = "TC_21939"; 
		final WebDriver driver = WebDriverFactory.get(browser); 
		GetTestData testData = new GetTestData(sheetName, tcId); 
		Log.testCaseInfo(testData.description +	"<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description); 
		try {
			
			// Navigate to Login Page 
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click view my policy button
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver,	extentedReport).get(); 
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button", "Renew My Policy page is not opend after clicking view my policy button",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate), "Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate), "Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
					
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver,	extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new	RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button", "Renew My Policy Payment page is not opend after clicking Show Quote button",
					driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyMonthlyButton(extentedReport,true), "Monthly option is Showed in Payment page",
					"Monthly option is not Showed in Payment page", driver, extentedReport, true);

			renewalPaymentPage.verifyMonthlyButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount), "Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount), "Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData. premiumFeesAmount), "Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue, testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getAmountOfCredit(testData.amountOfCreditCost), "Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getCostOfCredit(testData.costOfCreditAmount), "Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport); 
			
		} 
		catch (Exception e) 
		{ 
			Log.exception(e, driver, extentedReport); 
			
		} 
		finally 
		{ 
			Log.endTestCase(extentedReport);
			driver.quit(); 
		} 
	}

	//@Test(description="Verify that if the New Business was paid by card then at renewal the NB Scheme quotes must be available to choose so as to offer new Finance Plan and by default the PF Quote will be marked as preferred as per finance plan configurationin DB.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21940(String browser) throws Exception {

		String tcId = "TC_21940"; 
		final WebDriver driver = WebDriverFactory.get(browser); 
		GetTestData testData = new GetTestData(sheetName, tcId); 
		Log.testCaseInfo(testData.description +	"<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description); 
		try {
			
			// Navigate to Login Page 
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click view my policy button
			//customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get(); 
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button", "Renew My Policy page is not opend after clicking view my policy button",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate), "Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate), "Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport).get();

			Log.softAssertThat(renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button", "Renew My Policy Payment page is not opend after clicking Show Quote button",
					driver, extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount), "Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount), "Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount), "Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue, testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport); 
			
		}
		catch (Exception e) 
		{ 
			Log.exception(e, driver, extentedReport); 
		}
		finally 
		{ 
			Log.endTestCase(extentedReport);
			driver.quit(); 
		}
	}

	@Test(description = "Verify that correct overridden Premium Breakdown details (i.e. premium, excesses & Add-ons) of recommended renewal Quote group of active renewal Quote are displayed when the Premium is Overridden for an Insurer led Motor policy invited with in BO.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21941(String browser) throws Exception {

		String tcId = "TC_21941";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that correct Premium Breakdown details are displayed for the changed active renewal Quote & the changed active renewal Quote group when the active renewal Quote Group & active renewal quote for an Insurer led Motor policy renewal invited Quote are changed in BO.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21942(String browser) throws Exception {

		String tcId = "TC_21942";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that premium breakdown details (Premium,Fees,Addons) of recommended renewal Quote group of active renewal Quote is correctly displayed when a temporary MTA version of a Motor Policy is available (NB with an Additional Premium MTA on it)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_21943(String browser) throws Exception {

		String tcId = "TC_21943";
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
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
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickCancelButton(extentedReport);
			renewalPaymentPage.selectClose(extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	/************** User Story 22757 **************/
	
	@Test(description = "Verify that  Protected NCD section which is displayed in Renewal details screen for manual as well as automated renewal matches same as displayed in BO for motor policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23306(String browser) throws Exception {

		String tcId = "TC_23306";
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
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate), "Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),	"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitlePayment"), renewMyPolicyPage),
					"Payment block is displayed for Auto renewal policy", "Payment block is not displayed for Auto renewal policy");

			Log.softAssertThat(renewMyPolicyPage.getNumberOfYearsNoClaims(testData.numberOfYearsNoClaimsAccrued), "Number of years No Claims accrued is shown in the Renew My policy page",
					"Number of years No Claims accrued is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getAdditionalCostOfNoClaims(testData.additionalCostOfNoClaims),
					"Additional cost of No Claims Protection on your Policy is shown in the Renew My policy page",
					"Additional cost of No Claims Protection on your Policy is Not shown in the Renew My policy page",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.verifyNoClaimsDiscountProtectionHeader(renewMyPolicyPage.txtForNoClaimsDiscountProtection),
					renewMyPolicyPage.txtForNoClaimsDiscountProtection + "Verified NCD Header deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.txtForNoClaimsDiscountProtection + "Not verified NCD Header deatils message in Renewal My Policy page : ", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifyClaimsDiscountProtectionHeader(extentedReport, true),
					"NCD Header is Showed in Payment page", "NCD Header is not Showed in Payment page", driver, extentedReport, true);
			
			renewMyPolicyPage.expandNCDTable(extentedReport, true);
			
			renewMyPolicyPage.verifyNoClaimsDiscountawardedTable(testData.numberOfYears, testData.averageNCD, testData.numberOfYearsTable, testData.averageNCDTable, extentedReport, true);
			
			renewMyPolicyPage.verifyEffectOfClaimsOnYourNoClaimsDiscountTable(testData.numberOfClaims, testData.withNCDProtection, extentedReport, true);
			
			renewMyPolicyPage.verifyEffectOfClaimsWithoutNCDProtectionTable(testData.numberOfClaimsNCD,	testData.withOutNCDProtection, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that NCD Protection detail is not displayed at ‘Renewal details screen’ tab when the user has not requested NCD Protection and is also not granted in the quotation while renewing a non EDI Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23321(String browser) throws Exception {

		String tcId = "TC_23321";
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
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,	extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitlePayment"), renewMyPolicyPage), "Payment block is displayed for Auto renewal policy",
					"Payment block is not displayed for Auto renewal policy");
			
			Log.softAssertThat(!renewMyPolicyPage.verifyClaimsDiscountProtectionHeader(extentedReport, true), "NCD Header is not Showed in Payment page", 
					"NCD Header is  Showed in Payment page", driver, extentedReport, true);
			
			renewMyPolicyPage.selectChange(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that “Terms and Conditions” text and check box should be available at the bottom of payment required page for motor policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23318(String browser) throws Exception {

		String tcId = "TC_23318";
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
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate), "Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,	extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),	"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitlePayment"), renewMyPolicyPage),
					"Payment block is displayed for Auto renewal policy", "Payment block is not displayed for Auto renewal policy");
			
			Log.softAssertThat(!renewMyPolicyPage.verifyClaimsDiscountProtectionHeader(extentedReport, true), "NCD Header is not Showed in Payment page", 
					"NCD Header is Showed in Payment page", driver, extentedReport, true);
			
			renewMyPolicyPage.selectChange(extentedReport, true);
			
			renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that “Terms and Conditions” text and check box should be available at the bottom of payment required page for Houshold policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
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
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate), "Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,	extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),	"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured), "Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitlePayment"), renewMyPolicyPage),
					"Payment block is displayed for Auto renewal policy", "Payment block is not displayed for Auto renewal policy");
			
			renewMyPolicyPage.selectChange(extentedReport, true);
			
			renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that NCD Protection detail of recommended renewal Quote group of active renewal Quote is correctly displayed at ‘Renewal details screen’ tab when the user requested NCD Protection is granted in the quotation while renewing an EDI Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23322(String browser) throws Exception {

		String tcId = "TC_23322";
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
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate), "Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate), "Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getNumberOfYearsNoClaims(testData.numberOfYearsNoClaimsAccrued), "Number of years No Claims accrued is shown in the Renew My policy page",
					"Number of years No Claims accrued is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getAdditionalCostOfNoClaims(testData.additionalCostOfNoClaims),
					"Additional cost of No Claims Protection on your Policy is shown in the Renew My policy page",
					"Additional cost of No Claims Protection on your Policy is Not shown in the Renew My policy page",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.verifyNoClaimsDiscountProtectionHeader(renewMyPolicyPage.txtForNoClaimsDiscountProtection),
					renewMyPolicyPage.txtForNoClaimsDiscountProtection + "Verified NCD Header deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.txtForNoClaimsDiscountProtection + "Not verified NCD Header deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.verifyClaimsDiscountProtectionHeader(extentedReport, true), "NCD Header is Showed in Payment page", 
					"NCD Header is not Showed in Payment page", driver,	extentedReport, true);
			
			renewMyPolicyPage.expandNCDTable(extentedReport, true);
			
			renewMyPolicyPage.verifyNoClaimsDiscountawardedTable(testData.numberOfYears, testData.averageNCD, testData.numberOfYearsTable, testData.averageNCDTable, extentedReport, true);
			
			renewMyPolicyPage.verifyEffectOfClaimsOnYourNoClaimsDiscountTable(testData.numberOfClaims, testData.withNCDProtection, extentedReport, true);
			
			renewMyPolicyPage.verifyEffectOfClaimsWithoutNCDProtectionTable(testData.numberOfClaimsNCD,	testData.withOutNCDProtection, extentedReport, true);
			
			
			renewMyPolicyPage.selectRenewButton(extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that NCD Protection detail is not displayed at ‘Renewal details screen’ tab when the user has requested NCD Protection but not granted in the quotation while renewing an EDI Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23309(String browser) throws Exception {

		String tcId = "TC_23309";
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
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,	extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);
			
			Log.softAssertThat(	renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitlePayment"), renewMyPolicyPage),
					"Payment block is displayed for Auto renewal policy", "Payment block is not displayed for Auto renewal policy");
			
			Log.softAssertThat(!renewMyPolicyPage.verifyClaimsDiscountProtectionHeader(extentedReport, true), "NCD Header is not Showed in Payment page", 
					"NCD Header is  Showed in Payment page", driver, extentedReport, true);
			
			renewMyPolicyPage.selectChange(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that number of years No claims accured/Additional cost of No clames protection your policy details displayed in policy details screen matches same as in BO for motor policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23307(String browser) throws Exception {

		String tcId = "TC_23307";
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
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,	extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitlePayment"), renewMyPolicyPage),
					"Payment block is displayed for Auto renewal policy", "Payment block is not displayed for Auto renewal policy");

			Log.softAssertThat(renewMyPolicyPage.getNumberOfYearsNoClaims(testData.numberOfYearsNoClaimsAccrued), "Number of years No Claims accrued is shown in the Renew My policy page",
					"Number of years No Claims accrued is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getAdditionalCostOfNoClaims(testData.additionalCostOfNoClaims),
					"Additional cost of No Claims Protection on your Policy is shown in the Renew My policy page",
					"Additional cost of No Claims Protection on your Policy is Not shown in the Renew My policy page",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.verifyClaimsDiscountProtectionHeader(extentedReport, true), "NCD Header is Showed in Payment page", 
					"NCD Header is not Showed in Payment page", driver, extentedReport, true);
			
			renewMyPolicyPage.expandNCDTable(extentedReport, true);
			
			renewMyPolicyPage.verifyNoClaimsDiscountawardedTable(testData.numberOfYears, testData.averageNCD, testData.numberOfYearsTable, testData.averageNCDTable, extentedReport, true);
			
			renewMyPolicyPage.verifyEffectOfClaimsOnYourNoClaimsDiscountTable(testData.numberOfClaims, testData.withNCDProtection, extentedReport, true);
			
			renewMyPolicyPage.verifyEffectOfClaimsWithoutNCDProtectionTable(testData.numberOfClaimsNCD, testData.withOutNCDProtection, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that Protected NCD section not to be displayed at the bottom of Renewal details screen for manual as well as automated renewal when Protected NCD is not applied on the policy for Motor policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23305(String browser) throws Exception {

		String tcId = "TC_23305";
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
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,	extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitlePayment"), renewMyPolicyPage),
					"Payment block is displayed for Auto renewal policy", "Payment block is not displayed for Auto renewal policy");
			
			Log.softAssertThat(!renewMyPolicyPage.verifyClaimsDiscountProtectionHeader(extentedReport, true), "NCD Header is not Showed in Payment page", 
					"NCD Header is  Showed in Payment page", driver, extentedReport, true);
			
			renewMyPolicyPage.selectChange(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that Protected NCD section to be added at the bottom of Renewal details screen for manual as well as automated renewal (for Motor policies only) and This section should be visible only when Protected NCD is applied on the policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23304(String browser) throws Exception {

		String tcId = "TC_23304";
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
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate), "Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,	extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate), "Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitlePayment"), renewMyPolicyPage),
					"Payment block is displayed for Auto renewal policy", "Payment block is not displayed for Auto renewal policy");

			Log.softAssertThat(renewMyPolicyPage.getNumberOfYearsNoClaims(testData.numberOfYearsNoClaimsAccrued),
					"Number of years No Claims accrued is shown in the Renew My policy page",
					"Number of years No Claims accrued is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getAdditionalCostOfNoClaims(testData.additionalCostOfNoClaims),
					"Additional cost of No Claims Protection on your Policy is shown in the Renew My policy page",
					"Additional cost of No Claims Protection on your Policy is Not shown in the Renew My policy page",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.verifyNoClaimsDiscountProtectionHeader(renewMyPolicyPage.txtForNoClaimsDiscountProtection),
					renewMyPolicyPage.txtForNoClaimsDiscountProtection + "Verified NCD Header deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.txtForNoClaimsDiscountProtection + "Not verified NCD Header deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.verifyClaimsDiscountProtectionHeader(extentedReport, true), "NCD Header is Showed in Payment page", 
					"NCD Header is not Showed in Payment page", driver, extentedReport, true);
			
			renewMyPolicyPage.expandNCDTable(extentedReport, true);
			
			renewMyPolicyPage.verifyNoClaimsDiscountawardedTable(testData.numberOfYears, testData.averageNCD, testData.numberOfYearsTable, testData.averageNCDTable, extentedReport, true);
			
			renewMyPolicyPage.verifyEffectOfClaimsOnYourNoClaimsDiscountTable(testData.numberOfClaims, testData.withNCDProtection, extentedReport, true);
			
			renewMyPolicyPage.verifyEffectOfClaimsWithoutNCDProtectionTable(testData.numberOfClaimsNCD, testData.withOutNCDProtection, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that NCD Protection detail is correctly displayed at ‘Renewal details screen’ tab when the user has not requested NCD Protection but still granted in the quotation while renewing an EDI Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23312(String browser) throws Exception {

		String tcId = "TC_23312";
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
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitlePayment"), renewMyPolicyPage),
					"Payment block is displayed for Auto renewal policy", "Payment block is not displayed for Auto renewal policy");

			Log.softAssertThat(renewMyPolicyPage.getNumberOfYearsNoClaims(testData.numberOfYearsNoClaimsAccrued),
					"Number of years No Claims accrued is shown in the Renew My policy page",
					"Number of years No Claims accrued is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getAdditionalCostOfNoClaims(testData.additionalCostOfNoClaims),
					"Additional cost of No Claims Protection on your Policy is shown in the Renew My policy page",
					"Additional cost of No Claims Protection on your Policy is Not shown in the Renew My policy page",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.verifyNoClaimsDiscountProtectionHeader(renewMyPolicyPage.txtForNoClaimsDiscountProtection),
					renewMyPolicyPage.txtForNoClaimsDiscountProtection + "Verified NCD Header deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.txtForNoClaimsDiscountProtection + "Not verified NCD Header deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.verifyClaimsDiscountProtectionHeader(extentedReport, true), "NCD Header is Showed in Payment page", 
					"NCD Header is not Showed in Payment page", driver, extentedReport, true);
			
			renewMyPolicyPage.expandNCDTable(extentedReport, true);
			
			renewMyPolicyPage.verifyNoClaimsDiscountawardedTable(testData.numberOfYears, testData.averageNCD, testData.numberOfYearsTable, testData.averageNCDTable, extentedReport, true);
			
			renewMyPolicyPage.verifyEffectOfClaimsOnYourNoClaimsDiscountTable(testData.numberOfClaims, testData.withNCDProtection, extentedReport, true);
			
			renewMyPolicyPage.verifyEffectOfClaimsWithoutNCDProtectionTable(testData.numberOfClaimsNCD, testData.withOutNCDProtection, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that NCD Protection detail is not displayed at ‘Renewal details screen’ tab when the user has not requested NCD Protection and is also not granted in the quotation while renewing a non EDI Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23310(String browser) throws Exception {

		String tcId = "TC_23310";
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
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitlePayment"), renewMyPolicyPage),
					"Payment block is displayed for Auto renewal policy", "Payment block is not displayed for Auto renewal policy");
			
			Log.softAssertThat(!renewMyPolicyPage.verifyClaimsDiscountProtectionHeader(extentedReport, true),"NCD Header is not Showed in Payment page",
					"NCD Header is  Showed in Payment page", driver, extentedReport, true);
			
			renewMyPolicyPage.selectChange(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that NCD Protection detail is not displayed at ‘Renewal details screen’ tab when the user has not requested NCD Protection in the quotation while renewing a Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23311(String browser) throws Exception {

		String tcId = "TC_23311";
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
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitlePayment"), renewMyPolicyPage),
					"Payment block is displayed for Auto renewal policy", "Payment block is not displayed for Auto renewal policy");
			
			Log.softAssertThat(!renewMyPolicyPage.verifyClaimsDiscountProtectionHeader(extentedReport, true),"NCD Header is not Showed in Payment page",
					"NCD Header is  Showed in Payment page", driver, extentedReport, true);
			
			renewMyPolicyPage.selectChange(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that NCD Protection detail of recommended renewal Quote group of active renewal Quote is correctly displayed at ‘Renewal details screen’ tab when the user requested NCD Protection is granted in the quotation while renewing an EDI Policy.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_23308(String browser) throws Exception {

		String tcId = "TC_23308";
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
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType), "Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber), "Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel), "Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches), "Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors), "Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber), "Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission), "Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getNumberOfYearsNoClaims(testData.numberOfYearsNoClaimsAccrued),
					"Number of years No Claims accrued is shown in the Renew My policy page",
					"Number of years No Claims accrued is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getAdditionalCostOfNoClaims(testData.additionalCostOfNoClaims),
					"Additional cost of No Claims Protection on your Policy is shown in the Renew My policy page",
					"Additional cost of No Claims Protection on your Policy is Not shown in the Renew My policy page",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.verifyNoClaimsDiscountProtectionHeader(renewMyPolicyPage.txtForNoClaimsDiscountProtection),
					renewMyPolicyPage.txtForNoClaimsDiscountProtection	+ "Verified NCD Header deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.txtForNoClaimsDiscountProtection	+ "Not verified NCD Header deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.verifyClaimsDiscountProtectionHeader(extentedReport, true), "NCD Header is Showed in Payment page",
					"NCD Header is not Showed in Payment page", driver,	extentedReport, true);
			
			renewMyPolicyPage.expandNCDTable(extentedReport, true);
			
			renewMyPolicyPage.verifyNoClaimsDiscountawardedTable(testData.numberOfYears, testData.averageNCD, testData.numberOfYearsTable, testData.averageNCDTable, extentedReport, true);
			
			renewMyPolicyPage.verifyEffectOfClaimsOnYourNoClaimsDiscountTable(testData.numberOfClaims, testData.withNCDProtection, extentedReport, true);
			
			renewMyPolicyPage.verifyEffectOfClaimsWithoutNCDProtectionTable(testData.numberOfClaimsNCD, testData.withOutNCDProtection, extentedReport, true);
			
			renewMyPolicyPage.selectRenewButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

}
