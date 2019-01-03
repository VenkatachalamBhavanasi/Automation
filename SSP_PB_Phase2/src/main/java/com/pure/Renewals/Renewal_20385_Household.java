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
import com.relevantcodes.extentreports.ExtentTest;

@Listeners(EmailReport.class)
public class Renewal_20385_Household extends BaseTest {
	private String webSite;
	String sheetName = "Renewal_20385_Household_";

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

	/** Sprint 08 **//** User Story 20385 **/

	@Test(description = "Verify that user can not view a summary of the risk being renewed of a Household ‘Insurer Led Led policy which has been house kept after reaching to Invited stage", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20708(String browser) throws Exception {

		String tcId = "TC_20708";
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
			Log.softAssertThat(
					!customerDashboardPage.verifyRenewalPolicyFromList(testData.policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad", "Renewal Policy is displayed in Dashborad", driver,
					extentedReport, true);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// Not testing because test data is not available for this test case
	/*
	 @Test(description =
	 "Verify that user can not view a summary of the risk being renewed of a Household Broker Led policy which has been house kept after reaching to Invited stage"
	 , dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	 public void TC_20709(String browser) throws Exception {
	 
	 String tcId = "TC_20709"; final WebDriver driver =
	 WebDriverFactory.get(browser); GetTestData testData = new
	 GetTestData(sheetName, tcId); Log.testCaseInfo(testData.description +
	 "<small><b><i>[" + browser + "]</b></i></small>");
	 
	 ExtentTest extentedReport = addTestInfo(tcId, testData.description); try {
	 
	 // Navigate to Login Page CustomerServicePage selfServicePage = new
	 CustomerServicePage(driver, webSite, extentedReport).get();
	 Log.message("Customer Self service Landing Page : " + webSite, driver,
	 extentedReport, true);
	 
	 CustomerSignInPage customerSignInPage =
	 selfServicePage.clickSignInButton(extentedReport, true);
	 CustomerDashboardPage customerDashboardPage = customerSignInPage
	 .loginToSSCustomerDashboard(testData.emailAddress, testData.password,
	 extentedReport, true);
	 
	 // Click view my policy button
	 //customerDashboardPage.clickViewMyRenewal(extentedReport, true);
	 Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(
	 testData.policyName, extentedReport, true),
	 "Renewal Policy is not displayed in Dashborad"
	 ,"Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
	 
	 Log.testCaseResult(extentedReport); } catch (Exception e) { Log.exception(e,
	 driver, extentedReport); } finally { Log.endTestCase(extentedReport);
	 driver.quit(); } }
	 */

	@Test(description = "Verify that user can not view a summary of the risk being renewed of a Household ‘Insurer Led  policy which has Claim performed on it during renewal period & it has now been moved to the suspended stage", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20710(String browser) throws Exception {

		String tcId = "TC_20710";
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
			Log.softAssertThat(
					!customerDashboardPage.verifyRenewalPolicyFromList(testData.policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad", "Renewal Policy is displayed in Dashborad", driver,
					extentedReport, true);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// Not testing because test data is not available for this test case
	/*
	 @Test(description =
	 "Verify that user can not view a summary of the risk being renewed of a Household Broker Led policy which has Claim performed on it during renewal period & it has now been moved to the suspended stage"
	 , dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	 public void TC_20711(String browser) throws Exception {
	 
	 String tcId = "TC_20711"; final WebDriver driver =
	 WebDriverFactory.get(browser); GetTestData testData = new
	 GetTestData(sheetName, tcId); Log.testCaseInfo(testData.description +
	 "<small><b><i>[" + browser + "]</b></i></small>");
	 
	 ExtentTest extentedReport = addTestInfo(tcId, testData.description); try {
	 
	 // Navigate to Login Page CustomerServicePage selfServicePage = new
	 CustomerServicePage(driver, webSite, extentedReport).get();
	 Log.message("Customer Self service Landing Page : " + webSite, driver,
	 extentedReport, true);
	 
	 CustomerSignInPage customerSignInPage =
	 selfServicePage.clickSignInButton(extentedReport, true);
	 CustomerDashboardPage customerDashboardPage = customerSignInPage
	 .loginToSSCustomerDashboard(testData.emailAddress, testData.password,
	 extentedReport, true);
	 
	 // Click view my policy button
	 //customerDashboardPage.clickViewMyRenewal(extentedReport, true);
	 Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(
	 testData.policyName, extentedReport, true),
	 "Renewal Policy is not displayed in Dashborad"
	 ,"Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
	 
	 Log.testCaseResult(extentedReport); } catch (Exception e) { Log.exception(e,
	 driver, extentedReport); } finally { Log.endTestCase(extentedReport);
	 driver.quit(); } }
	 */

	@Test(description = "Verify that user can not view a summary of the risk being renewed of a Household ‘Insurer Led policy which has MTA performed on it during renewal period & has now moved to the suspended stage", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20712(String browser) throws Exception {

		String tcId = "TC_20712";
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
			Log.softAssertThat(
					!customerDashboardPage.verifyRenewalPolicyFromList(testData.policyName, extentedReport, true),
					"Renewal Policy is not displayed in Dashborad", "Renewal Policy is displayed in Dashborad", driver,
					extentedReport, true);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// Not testing because test data is not available for this test case
	/*
	 @Test(description =
	 "Verify that user can not view a summary of the risk being renewed of a Household Broker Led policy which has MTA performed on it during renewal period & has now moved to the suspended stage"
	 , dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	 public void TC_20713(String browser) throws Exception {
	 
	 String tcId = "TC_20713"; final WebDriver driver =
	 WebDriverFactory.get(browser); GetTestData testData = new
	 GetTestData(sheetName, tcId); Log.testCaseInfo(testData.description +
	 "<small><b><i>[" + browser + "]</b></i></small>");
	 
	 ExtentTest extentedReport = addTestInfo(tcId, testData.description); try {
	 
	 // Navigate to Login Page CustomerServicePage selfServicePage = new
	 CustomerServicePage(driver, webSite, extentedReport).get();
	 Log.message("Customer Self service Landing Page : " + webSite, driver,
	 extentedReport, true);
	 
	 CustomerSignInPage customerSignInPage =
	 selfServicePage.clickSignInButton(extentedReport, true);
	 CustomerDashboardPage customerDashboardPage = customerSignInPage
	 .loginToSSCustomerDashboard(testData.emailAddress, testData.password,
	 extentedReport, true);
	 
	 // Click view my policy button
	 //customerDashboardPage.clickViewMyRenewal(extentedReport, true);
	 Log.softAssertThat(!customerDashboardPage.verifyRenewalPolicyFromList(
	 testData.policyName, extentedReport, true),
	 "Renewal Policy is not displayed in Dashborad"
	 ,"Renewal Policy is displayed in Dashborad", driver, extentedReport, true);
	 
	 Log.testCaseResult(extentedReport); } catch (Exception e) { Log.exception(e,
	 driver, extentedReport); } finally { Log.endTestCase(extentedReport);
	 driver.quit(); } }
	 */

	@Test(description = "Verify that user can view a summary of the risk being renewed of a Household ‘Insurer Led' policy whose confirmation has been revoked during renewal period and now the policy has been moved to Invited stage", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20714(String browser) throws Exception {

		String tcId = "TC_20714";
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

			Log.softAssertThat(
					renewMyPolicyPage.verifyHomeInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG),
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Not verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(
			 extentedReport, true),
			 "Renewal Message is shown in the Renew My policy page",
			 "Renewal Message is not shown in the Renew My policy page", driver,
			 extentedReport, true);
			 */

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Verified Terms and condition message in Renewal My Policy page : ",
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Not verified Terms and condition messagee in Renewal My Policy page : ",
			 driver, extentedReport, true);
			 */

			/*
			 Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(
			 extentedReport),
			 "Save Quote button is not clickable and enabled when Terms and Conditions is checked"
			 ,
			 "Save Quote button is clickable and disabled when Terms and Conditions is checked"
			 , driver, extentedReport, true);
			 */

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			renewMyPolicyPage.selectClose(extentedReport);

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

	// Not testing because test data is not available for this test case
	/*
	 @Test(description =
	 "Verify that user can view a summary of the risk being renewed of a Household Broker Led policy whose confirmation has been revoked during renewal period and now the policy has been moved to Invited stage"
	 , dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	 public void TC_20715(String browser) throws Exception {
	 
	 String tcId = "TC_20715"; final WebDriver driver =
	 WebDriverFactory.get(browser); GetTestData testData = new
	 GetTestData(sheetName, tcId); Log.testCaseInfo(testData.description +
	 "<small><b><i>[" + browser + "]</b></i></small>");
	 
	 ExtentTest extentedReport = addTestInfo(tcId, testData.description); try {
	 
	 // Navigate to Login Page CustomerServicePage selfServicePage = new
	 CustomerServicePage(driver, webSite, extentedReport).get();
	 Log.message("Customer Self service Landing Page : " + webSite, driver,
	 extentedReport, true);
	 
	 CustomerSignInPage customerSignInPage =
	 selfServicePage.clickSignInButton(extentedReport, true);
	 CustomerDashboardPage customerDashboardPage = customerSignInPage
	 .loginToSSCustomerDashboard(testData.emailAddress, testData.password,
	 extentedReport, true);
	 
	 // Click view my policy button
	 //customerDashboardPage.clickViewMyRenewal(extentedReport, true);
	 customerDashboardPage.clickViewMYPolicyButton(testData.policyName,
	 extentedReport, true);
	 
	 RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver,
	 extentedReport).get(); Log.softAssertThat(
	 renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList(
	 "txtTitleRenewMyPolicy"), renewMyPolicyPage),
	 "Renew My Policy page is opend after clicking view my policy button",
	 "Renew My Policy page is not opend after clicking view my policy button",
	 driver, extentedReport, true);
	 
	 Log.softAssertThat(
	 renewMyPolicyPage.verifyHomeInsuranceRenewalHeader(renewMyPolicyPage.
	 Home_INSURENCE_Details_MSG), renewMyPolicyPage.Home_INSURENCE_Details_MSG +
	 "Verified Home Insured Renewal deatils message in Renewal My Policy page : ",
	 renewMyPolicyPage.Home_INSURENCE_Details_MSG +
	 "Not verified Home Insured Renewal deatils message in Renewal My Policy page : "
	 , driver, extentedReport, true);
	 
	 //Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(
	 extentedReport, true), //
	 "Renewal Message is shown in the Renew My policy page",
	 "Renewal Message is not shown in the Renew My policy page", driver, //
	 extentedReport, true);
	 
	 Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData
	 .originalPolicyDate),
	 "Period of cover for original policy is shown in the Renew My policy page",
	 "Period of cover for original policy is Not shown in the Renew My policy page"
	 , driver, extentedReport, true);
	 
	 Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.
	 renewalPolicyDate),
	 "Period of cover for Renewal policy is shown in the Renew My policy page",
	 "Period of cover for Renewal policy is Not shown in the Renew My policy page"
	 , driver, extentedReport, true);
	 
	 Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.
	 thePropertyToBeInsured),
	 "Property to be Insured shown in the Renew My policy page",
	 "Property to be Insured is Not shown in the Renew My policy page", driver,
	 extentedReport, true);
	 
	 renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);
	 
	 //Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
	 renewMyPolicyPage.TERMS_AND_CONDITION_MSG + //
	 "Verified Terms and condition message in Renewal My Policy page : ",
	 renewMyPolicyPage.TERMS_AND_CONDITION_MSG + //
	 "Not verified Terms and condition message in Renewal My Policy page : ",
	 driver, extentedReport, true);
	 
	 Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(
	 extentedReport),
	 "Save Quote button is not clickable and enabled when Terms and Conditions is checked"
	 ,
	 "Save Quote button is clickable and disabled when Terms and Conditions is checked"
	 , driver, extentedReport, true);
	 
	 //renewMyPolicyPage.clickTermsAndCond(extentedReport, true);
	 
	 Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(
	 extentedReport),
	 "Save Quote button is clickable and enabled when Terms and Conditions is checked"
	 ,
	 "Save Quote button is not clickable and disabled when Terms and Conditions is checked"
	 , driver, extentedReport, true);
	 
	 renewMyPolicyPage.clickCancelButton(extentedReport);
	 renewMyPolicyPage.selectClose(extentedReport);
	 
	 Log.softAssertThat(
	 customerDashboardPage.uielement.verifyPageElements(Arrays.asList(
	 "txtCustomerName"), customerDashboardPage),
	 "On clicking 'CANCEL' button redirected to Policy Detail Screen",
	 "On clicking 'CANCEL' button did not redirect to Policy Detail Screen",
	 driver, extentedReport);
	 
	 Log.testCaseResult(extentedReport); } catch (Exception e) { Log.exception(e,
	 driver, extentedReport); } finally { Log.endTestCase(extentedReport);
	 driver.quit(); } }
	 */

	@Test(description = "Verify that user can view a summary of the risk being renewed of a Household policy for Broker Led (non EDI) PPL policy that has already been invited within BO and has not crossed its Renewal expiry date is in unsuspended state", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20716(String browser) throws Exception {

		String tcId = "TC_20716";
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

			Log.softAssertThat(
					renewMyPolicyPage.verifyHomeInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG),
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Not verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(
			 extentedReport, true),
			 "Renewal Message is shown in the Renew My policy page",
			 "Renewal Message is not shown in the Renew My policy page", driver,
			 extentedReport, true);
			 */

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Verified Terms and condition message in Renewal My Policy page : ",
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Not verified Terms and condition messagee in Renewal My Policy page : ",
			 driver, extentedReport, true);
			 */

			/*
			 Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(
			 extentedReport),
			 "Save Quote button is not clickable and enabled when Terms and Conditions is checked"
			 ,
			 "Save Quote button is clickable and disabled when Terms and Conditions is checked"
			 , driver, extentedReport, true);
			 */

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			renewMyPolicyPage.selectClose(extentedReport);

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

	@Test(description = "Verify that user is able to view the summary of correct risk details on ‘Renew My Policy’ screen when policy is transacted & renewed with Add-ons, fees & taxes for Household Renewal Quote", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20717(String browser) throws Exception {

		String tcId = "TC_20717";
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

			Log.softAssertThat(
					renewMyPolicyPage.verifyHomeInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG),
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Not verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(
			 extentedReport, true),
			 "Renewal Message is shown in the Renew My policy page",
			 "Renewal Message is not shown in the Renew My policy page", driver,
			 extentedReport, true);
			 */

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Verified Terms and condition message in Renewal My Policy page : ",
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Not verified Terms and condition messagee in Renewal My Policy page : ",
			 driver, extentedReport, true);
			 */

			/*
			 Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(
			 extentedReport),
			 "Save Quote button is not clickable and enabled when Terms and Conditions is checked"
			 ,
			 "Save Quote button is clickable and disabled when Terms and Conditions is checked"
			 , driver, extentedReport, true);
			 */

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			renewMyPolicyPage.selectClose(extentedReport);

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

	@Test(description = "Verifyt user can view a summary of risk being renewed of a Household ‘InsurerLed policy on ‘Renew My Policy’ screen on which MTA was performed during its renewal period & later this policy was moved from 'Suspended' to 'Unsuspended' state", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20718(String browser) throws Exception {

		String tcId = "TC_20718";
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

			Log.softAssertThat(
					renewMyPolicyPage.verifyHomeInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG),
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Not verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(
			 extentedReport, true),
			 "Renewal Message is shown in the Renew My policy page",
			 "Renewal Message is not shown in the Renew My policy page", driver,
			 extentedReport, true);
			 */

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Verified Terms and condition message in Renewal My Policy page : ",
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Not verified Terms and condition messagee in Renewal My Policy page : ",
			 driver, extentedReport, true);
			 */

			/*
			 Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(
			 extentedReport),
			 "Save Quote button is not clickable and enabled when Terms and Conditions is checked"
			 ,
			 "Save Quote button is clickable and disabled when Terms and Conditions is checked"
			 , driver, extentedReport, true);
			 */

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			renewMyPolicyPage.selectClose(extentedReport);

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

	@Test(description = "Verify user can view a summary of the risk being renewed of a Household ‘InsurerLed policy on ‘Renew My Policy’ screen on which a Claim was performed during its renewal period & later policy was moved from 'Suspended' to 'Unsuspended' state", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20719(String browser) throws Exception {

		String tcId = "TC_20719";
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

			Log.softAssertThat(
					renewMyPolicyPage.verifyHomeInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG),
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Not verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(
			 extentedReport, true),
			 "Renewal Message is shown in the Renew My policy page",
			 "Renewal Message is not shown in the Renew My policy page", driver,
			 extentedReport, true);
			 */

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Verified Terms and condition message in Renewal My Policy page : ",
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Not verified Terms and condition messagee in Renewal My Policy page : ",
			 driver, extentedReport, true);
			 */

			/*
			 Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(
			 extentedReport),
			 "Save Quote button is not clickable and enabled when Terms and Conditions is checked"
			 ,
			 "Save Quote button is clickable and disabled when Terms and Conditions is checked"
			 , driver, extentedReport, true);
			 */

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			renewMyPolicyPage.selectClose(extentedReport);

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

	@Test(description = "Verify that user can view a summary of the risk being renewed of the Household policy for Broker Led PPL EDI policy that has already been invited within B.O and has not crossed its Renewal expiry date & is in unsuspended State", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20720(String browser) throws Exception {

		String tcId = "TC_20720";
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

			Log.softAssertThat(
					renewMyPolicyPage.verifyHomeInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG),
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Not verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(
			 extentedReport, true),
			 "Renewal Message is shown in the Renew My policy page",
			 "Renewal Message is not shown in the Renew My policy page", driver,
			 extentedReport, true);
			 */

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Verified Terms and condition message in Renewal My Policy page : ",
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Not verified Terms and condition messagee in Renewal My Policy page : ",
			 driver, extentedReport, true);
			 */

			/*
			 Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(
			 extentedReport),
			 "Save Quote button is not clickable and enabled when Terms and Conditions is checked"
			 ,
			 "Save Quote button is clickable and disabled when Terms and Conditions is checked"
			 , driver, extentedReport, true);
			 */

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			renewMyPolicyPage.selectClose(extentedReport);

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

	@Test(description = "Verify that user is able to view Household policy details on ‘Renew My Policy’ screen for Insurer Led PPL policy quoted via Manual Re-broke that has now been invited within B.O. and has not crossed its Renewal expiry date & in unsuspended State", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20721(String browser) throws Exception {

		String tcId = "TC_20721";
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

			Log.softAssertThat(
					renewMyPolicyPage.verifyHomeInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG),
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Not verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(
			 extentedReport, true),
			 "Renewal Message is shown in the Renew My policy page",
			 "Renewal Message is not shown in the Renew My policy page", driver,
			 extentedReport, true);
			 */

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Verified Terms and condition message in Renewal My Policy page : ",
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Not verified Terms and condition messagee in Renewal My Policy page : ",
			 driver, extentedReport, true);
			 */

			/*
			 Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(
			 extentedReport),
			 "Save Quote button is not clickable and enabled when Terms and Conditions is checked"
			 ,
			 "Save Quote button is clickable and disabled when Terms and Conditions is checked"
			 , driver, extentedReport, true);
			 */

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			renewMyPolicyPage.selectClose(extentedReport);

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

	@Test(description = "Verify that user can view a summary of the risk being renewed of a Recommended Household renewal quote of the active renewal Quote group when there are multiple renewal quotes available against a Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20722(String browser) throws Exception {

		String tcId = "TC_20722";
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

			Log.softAssertThat(
					renewMyPolicyPage.verifyHomeInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG),
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Not verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(
			 extentedReport, true),
			 "Renewal Message is shown in the Renew My policy page",
			 "Renewal Message is not shown in the Renew My policy page", driver,
			 extentedReport, true);
			 */

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Verified Terms and condition message in Renewal My Policy page : ",
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Not verified Terms and condition messagee in Renewal My Policy page : ",
			 driver, extentedReport, true);
			 */

			/*
			 Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(
			 extentedReport),
			 "Save Quote button is not clickable and enabled when Terms and Conditions is checked"
			 ,
			 "Save Quote button is clickable and disabled when Terms and Conditions is checked"
			 , driver, extentedReport, true);
			 */

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			renewMyPolicyPage.selectClose(extentedReport);

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

	@Test(description = "Verify that user can view a summary of the risk being renewed of a Recommended Household renewal quote of the active renewal Quote group when there is alternate insurer renewal quotes available against a Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20723(String browser) throws Exception {

		String tcId = "TC_20723";
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

			Log.softAssertThat(
					renewMyPolicyPage.verifyHomeInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG),
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Not verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(
			 extentedReport, true),
			 "Renewal Message is shown in the Renew My policy page",
			 "Renewal Message is not shown in the Renew My policy page", driver,
			 extentedReport, true);
			 */

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Verified Terms and condition message in Renewal My Policy page : ",
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Not verified Terms and condition messagee in Renewal My Policy page : ",
			 driver, extentedReport, true);
			 */

			/*
			 Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(
			 extentedReport),
			 "Save Quote button is not clickable and enabled when Terms and Conditions is checked"
			 ,
			 "Save Quote button is clickable and disabled when Terms and Conditions is checked"
			 , driver, extentedReport, true);
			 */

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			renewMyPolicyPage.selectClose(extentedReport);

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

	@Test(description = "Verify that user is able to view a summary of correct risk details on ‘Renew My Policy’ screen when one or more versions of Household Policy are available (New Business with an Return Premium MTA on it)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20724(String browser) throws Exception {

		String tcId = "TC_20724";
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

			Log.softAssertThat(
					renewMyPolicyPage.verifyHomeInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG),
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Not verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(
			 extentedReport, true),
			 "Renewal Message is shown in the Renew My policy page",
			 "Renewal Message is not shown in the Renew My policy page", driver,
			 extentedReport, true);
			 */

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Verified Terms and condition message in Renewal My Policy page : ",
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Not verified Terms and condition messagee in Renewal My Policy page : ",
			 driver, extentedReport, true);
			 */

			/*
			 Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(
			 extentedReport),
			 "Save Quote button is not clickable and enabled when Terms and Conditions is checked"
			 ,
			 "Save Quote button is clickable and disabled when Terms and Conditions is checked"
			 , driver, extentedReport, true);
			 */

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			renewMyPolicyPage.selectClose(extentedReport);

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

	@Test(description = "Verify that user is able to view a summary of correct risk details on ‘Renew My Policy’ screen when one or more versions of Houseold Policy are available (New Business with an Additional Premium MTA on it)", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20725(String browser) throws Exception {

		String tcId = "TC_20725";
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

			Log.softAssertThat(
					renewMyPolicyPage.verifyHomeInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG),
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Not verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(
			 extentedReport, true),
			 "Renewal Message is shown in the Renew My policy page",
			 "Renewal Message is not shown in the Renew My policy page", driver,
			 extentedReport, true);
			 */

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Verified Terms and condition message in Renewal My Policy page : ",
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Not verified Terms and condition messagee in Renewal My Policy page : ",
			 driver, extentedReport, true);
			 */

			/*
			 Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(
			 extentedReport),
			 "Save Quote button is not clickable and enabled when Terms and Conditions is checked"
			 ,
			 "Save Quote button is clickable and disabled when Terms and Conditions is checked"
			 , driver, extentedReport, true);
			 */

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			renewMyPolicyPage.selectClose(extentedReport);

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

	@Test(description = "Verify that user is able to view the risk details of ‘Insurer led Household' policy on ‘Renew My Policy’ screen that has already been invited within BO through Auto Renewal process & has not crossed its Renewal expiry date & in unsuspended state", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20726(String browser) throws Exception {

		String tcId = "TC_20726"; 
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

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get(); 
			
			Log.softAssertThat(renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"), renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button", "Renew My Policy page is not opend after clicking view my policy button",
					driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifyHomeInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG), 
					renewMyPolicyPage.Home_INSURENCE_Details_MSG + "Verified Home Insured Renewal deatils message in Renewal My Policy page : ", 
					renewMyPolicyPage.Home_INSURENCE_Details_MSG + "Not verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			/*Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(extentedReport, true), "Renewal Message is shown in the Renew My policy page",
					"Renewal Message is not shown in the Renew My policy page", driver, extentedReport, true);*/

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate), "Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate), "Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured), "Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			/*Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(), renewMyPolicyPage.TERMS_AND_CONDITION_MSG +  "Verified Terms and condition message in Renewal My Policy page : ",
			renewMyPolicyPage.TERMS_AND_CONDITION_MSG + "Not verified Terms and condition messagee in Renewal My Policy page : ", driver, extentedReport, true);*/

			/*Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport), "Save Quote button is not clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is clickable and disabled when Terms and Conditions is checked", driver, extentedReport, true);*/

			//renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport), "Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver, extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			
			renewMyPolicyPage.selectClose(extentedReport);

			Log.softAssertThat(customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"), customerDashboardPage),
					"On clicking 'CANCEL' button redirected to Policy Detail Screen", "On clicking 'CANCEL' button did not redirect to Policy Detail Screen",
					driver, extentedReport);

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

	@Test(description = "Verify that ‘Show Quote’ is enabled based on the conditions in the 'Renew my policy' page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20727(String browser) throws Exception {

		String tcId = "TC_20727";
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

			/*
			 Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(
			 extentedReport),
			 "Save Quote button is not clickable and enabled when Terms and Conditions is checked"
			 ,
			 "Save Quote button is clickable and disabled when Terms and Conditions is checked"
			 , driver, extentedReport, true);
			 */

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickQuoteButton(extentedReport, true);
			;

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the 'Cancel' view risk process in the 'Renew my policy' page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20728(String browser) throws Exception {

		String tcId = "TC_20728";
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

			/*
			 Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(
			 extentedReport),
			 "Save Quote button is not clickable and enabled when Terms and Conditions is checked"
			 ,
			 "Save Quote button is clickable and disabled when Terms and Conditions is checked"
			 , driver, extentedReport, true);
			 */

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			renewMyPolicyPage.selectClose(extentedReport);

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

	@Test(description = "Verify the availability of Configurable note in the 'Renew my policy' page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20729(String browser) throws Exception {

		String tcId = "TC_20729";
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

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditionsNote(),
			 renewMyPolicyPage.TERMS_AND_CONDITION_Note +
			 "Verified Terms and condition Note in Renewal My Policy page : ",
			 renewMyPolicyPage.TERMS_AND_CONDITION_Note +
			 "Not verified Terms and condition Note in Renewal My Policy page : ", driver,
			 extentedReport, true);
			 */

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the availability of User Agreement checkbox in the 'Renew my policy' page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20730(String browser) throws Exception {

		String tcId = "TC_20730";
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

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Verified Terms and condition message in Renewal My Policy page : ",
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Not verified Terms and condition messagee in Renewal My Policy page : ",
			 driver, extentedReport, true);
			 */

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the Display note indicating the process for changing risk in the 'Renew my policy' page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20731(String browser) throws Exception {

		String tcId = "TC_20731";
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

			Log.softAssertThat(
					renewMyPolicyPage.verifyHomeInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG),
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Not verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(
			 extentedReport, true),
			 "Renewal Message is shown in the Renew My policy page -" +
			 renewMyPolicyPage.CAR_INSURENCE_POLICY_MSG,
			 "Renewal Message is not shown in the Renew My policy page -" +
			 renewMyPolicyPage.CAR_INSURENCE_POLICY_MSG, driver, extentedReport, true);
			 */

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the Documents list in the 'Renew my policy' page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20732(String browser) throws Exception {

		String tcId = "TC_20732";
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

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			Log.message("Documnets are verifyed in renwe my policy", extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the Help Text in 'Renew My Policy' page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20733(String browser) throws Exception {

		String tcId = "TC_20733";
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

			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("iconHelpHomeRenewMyPolicy"),
							renewMyPolicyPage),
					"Help icon (?) is displayed against the 'Renew My Policy' text in Renew My Policy Page",
					"Help icon (?) is not displayed against the 'Renew My Policy' text in Renew My Policy Page", driver,
					extentedReport);

			renewMyPolicyPage.hoverOnHelpIconOfRenewMyPolicyHousehold(extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.verifyHelpIconRenewMyPolicyHouseholdPopupMessage(),
					renewMyPolicyPage.POPUP_MSG_HELPICON_Renew_My_Policy
							+ " - Message is displayed when click/hover the help icon (?) against the 'Renew My Policy' text",
					renewMyPolicyPage.POPUP_MSG_HELPICON_Renew_My_Policy
							+ " - Message is not displayed when click/hover the help icon (?) against the 'Renew My Policy' text",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that user can view a summary of the risk being renewed of the Insurer led Household policy details that has already been invited within B.O. and has not crossed its Renewal expiry date and time & is in unsuspended State", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20748(String browser) throws Exception {

		String tcId = "TC_20748";
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

			Log.softAssertThat(
					renewMyPolicyPage.verifyHomeInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG),
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Not verified Home Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(
			 extentedReport, true),
			 "Renewal Message is shown in the Renew My policy page",
			 "Renewal Message is not shown in the Renew My policy page", driver,
			 extentedReport, true);
			 */

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			/*
			 Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Verified Terms and condition message in Renewal My Policy page : ",
			 renewMyPolicyPage.TERMS_AND_CONDITION_MSG +
			 "Not verified Terms and condition messagee in Renewal My Policy page : ",
			 driver, extentedReport, true);
			 */

			/*
			 Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(
			 extentedReport),
			 "Save Quote button is not clickable and enabled when Terms and Conditions is checked"
			 ,
			 "Save Quote button is clickable and disabled when Terms and Conditions is checked"
			 , driver, extentedReport, true);
			 */

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			renewMyPolicyPage.selectClose(extentedReport);

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

	@Test(description = "Verify the premium in SS dashboard for policy which was selected with Active Renewal Quote group BO", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20931(String browser) throws Exception {

		String tcId = "TC_20931";
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
			Log.softAssertThat(
					customerDashboardPage.verifyActivePremiumOfRenewalPolicy(testData.policyName,
							testData.activePremium, extentedReport, true),
					"Verified Active Renewal Quote group Premium : ",
					"Not Verified Active Renewal Quote group Premium : ", driver, extentedReport, true);

			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(
					renewMyPolicyPage.verifyCarInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG),
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Verified Car Insured Renewal deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Not verified Car Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(extentedReport, true),
					"Renewal Message is shown in the Renew My policy page",
					"Renewal Message is not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle registration number is shown in the Renew My policy page",
					"Vehicle registration number is Not shown in the Renew My policy page", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches),
					"Vehicle Matches details are shown in the Renew My policy page",
					"Vehicle Matches details are Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC Numbser details shown in the Renew My policy page",
					"Vehicle CC Numbser details are Not shown in the Renew My policy page", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),
					"Vehicle number of doors details are shown in the Renew My policy page",
					"Vehicle number of doors details are Not shown in the Renew My policy page", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType),
					"Vehicle body type details are shown in the Renew My policy page",
					"Vehicle body type details are Not shown in the Renew My policy page", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission details are shown in the Renew My policy page",
					"Vehicle Transmission details are Not shown in the Renew My policy page", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),
					"Vehicle Fuel details are shown in the Renew My policy page",
					"Vehicle Fuel details are Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
					renewMyPolicyPage.TERMS_AND_CONDITION_MSG
							+ "Verified Terms and condition message in Renewal My Policy page : ",
					renewMyPolicyPage.TERMS_AND_CONDITION_MSG
							+ "Not verified Terms and condition messagee in Renewal My Policy page : ",
					driver, extentedReport, true);

			Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is not clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			renewMyPolicyPage.selectClose(extentedReport);

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

	@Test(description = "Verify the premium in SS dashboard for policy which was selected with Recommended Quote Quote group BO", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_20932(String browser) throws Exception {

		String tcId = "TC_20932";
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
			Log.softAssertThat(
					customerDashboardPage.verifyRecommendedPremiumOfRenewalPolicy(testData.policyName,
							testData.recommendedPremium, extentedReport, true),
					"Verified Recommended Renewal Quote group Premium : ",
					"Not Verified Recommended Renewal Quote group Premium : ", driver, extentedReport, true);

			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(
					renewMyPolicyPage.verifyCarInsuranceRenewalHeader(renewMyPolicyPage.Home_INSURENCE_Details_MSG),
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Verified Car Insured Renewal deatils message in Renewal My Policy page : ",
					renewMyPolicyPage.Home_INSURENCE_Details_MSG
							+ "Not verified Car Insured Renewal deatils message in Renewal My Policy page : ",
					driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifyInsuranceRenewalDetails(extentedReport, true),
					"Renewal Message is shown in the Renew My policy page",
					"Renewal Message is not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle registration number is shown in the Renew My policy page",
					"Vehicle registration number is Not shown in the Renew My policy page", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches),
					"Vehicle Matches details are shown in the Renew My policy page",
					"Vehicle Matches details are Not shown in the Renew My policy page", driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC Numbser details shown in the Renew My policy page",
					"Vehicle CC Numbser details are Not shown in the Renew My policy page", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),
					"Vehicle number of doors details are shown in the Renew My policy page",
					"Vehicle number of doors details are Not shown in the Renew My policy page", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType),
					"Vehicle body type details are shown in the Renew My policy page",
					"Vehicle body type details are Not shown in the Renew My policy page", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission details are shown in the Renew My policy page",
					"Vehicle Transmission details are Not shown in the Renew My policy page", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),
					"Vehicle Fuel details are shown in the Renew My policy page",
					"Vehicle Fuel details are Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.clickDocumentView(driver, extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifyTermsAndConditions(),
					renewMyPolicyPage.TERMS_AND_CONDITION_MSG
							+ "Verified Terms and condition message in Renewal My Policy page : ",
					renewMyPolicyPage.TERMS_AND_CONDITION_MSG
							+ "Not verified Terms and condition messagee in Renewal My Policy page : ",
					driver, extentedReport, true);

			Log.softAssertThat(!renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is not clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			// renewMyPolicyPage.clickTermsAndCond(extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.verifySaveQuoteButtonClickable(extentedReport),
					"Save Quote button is clickable and enabled when Terms and Conditions is checked",
					"Save Quote button is not clickable and disabled when Terms and Conditions is checked", driver,
					extentedReport, true);

			renewMyPolicyPage.clickCancelButton(extentedReport);
			renewMyPolicyPage.selectClose(extentedReport);

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
