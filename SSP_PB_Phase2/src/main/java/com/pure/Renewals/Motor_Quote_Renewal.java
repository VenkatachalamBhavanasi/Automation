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
import com.pure.selfservice.pages.RenewalQuoteDetailsPage;
import com.relevantcodes.extentreports.ExtentTest;

@Listeners(EmailReport.class)
public class Motor_Quote_Renewal extends BaseTest {
	private String webSite;
	String sheetName = "Motor_QUote_Renewal_";

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
		
	/** Sprint 08 **//**User Story 18686**/

	@Test(description = "This scenario is to test that the summary risk details of the ‘Active Quote Group’ is displayed to user available against a Motor Policy that has already been invited within BO & not crossed its Renewalexpirydate&time, is in unsuspended state", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20551(String browser) throws Exception {

		String tcId = "TC_20551";
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
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle registration number is shown in the Renew My policy page", "Vehicle registration number is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches),
					"Vehicle Matches details are shown in the Renew My policy page", "Vehicle Matches details are Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC Numbser details shown in the Renew My policy page", "Vehicle CC Numbser details are Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),
					"Vehicle number of doors details are shown in the Renew My policy page", "Vehicle number of doors details are Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType),
					"Vehicle body type details are shown in the Renew My policy page", "Vehicle body type details are Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission details are shown in the Renew My policy page", "Vehicle Transmission details are Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),
					"Vehicle Fuel details are shown in the Renew My policy page", "Vehicle Fuel details are Not shown in the Renew My policy page", driver,
					extentedReport, true);
			
			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);
			
			/*Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
					"Verified Terms and condition message in Renewal My Policy page : ", renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
					"Not verified Terms and condition messagee in Renewal My Policy page : ", driver, extentedReport, true);
			
			Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is not clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is clickable and disabled when Terms and Conditions is checked", driver, extentedReport, true);*/
			
			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);
			
			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver, extentedReport, true);
			
			renewMyPolicyPage.clickQuoteButton(extentedReport, true);
			
			RenewalQuoteDetailsPage renewalQuoteDetailsPage = new RenewalQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(
					renewalQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewQuotePage"),
							renewalQuoteDetailsPage),
					"Renew Quote page is opend after clicking Show Quote button",
					"Renew Quote page is not opend after clicking Show Quote button", driver, extentedReport,
					true);
			
			/*Log.softAssertThat(renewalQuoteDetailsPage.getTypeOfInsurance(testData.typeOfInsurance),
					"Type of Insurance is shown in the Quote Page", "Type of Insurance is not shown in the Quote Page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalQuoteDetailsPage.getTypeOfScheme(testData.typeOfSubScheme),
					"Type of Scheme is shown in the Quote Page", "Type of Scheme is not shown in the Quote Page", driver,
					extentedReport, true);
			
			Log.softAssertThat(renewalQuoteDetailsPage.getPremiumAmount(testData.totalPremium),
					"Type of Scheme is shown in the Quote Page", "Type of Scheme is not shown in the Quote Page", driver,
					extentedReport, true);*/
			
			Log.softAssertThat(renewalQuoteDetailsPage.verifyAnnualButton(),
					"Premium type is selected as Annual", "Premium type is not Verifyed", driver,extentedReport, true);
			
			Log.softAssertThat(renewalQuoteDetailsPage.verifyTermsAndConditions(),renewalQuoteDetailsPage.TERMS_AND_CONDITION_MSG +
					"Verified Terms and condition message in Renewal Quote page : ", renewalQuoteDetailsPage.TERMS_AND_CONDITION_MSG +
					"Not verified Terms and condition messagee in Renewal Quote page : ", driver, extentedReport, true);
			
			Log.softAssertThat(!renewalQuoteDetailsPage.verifyBuyButtonClickable(extentedReport),
					"Buy button is not clickable and enabled when Terms and Conditions is checked",
					"Buy button is clickable and disabled when Terms and Conditions is checked", driver, extentedReport, true);
			
			renewalQuoteDetailsPage.clickTermsAndCond(extentedReport, true);
			
			Log.softAssertThat(renewalQuoteDetailsPage.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Terms and Conditions is checked",
					"Buy button is not clickable and disabled when Terms and Conditions is checked", driver, extentedReport, true);
			
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
