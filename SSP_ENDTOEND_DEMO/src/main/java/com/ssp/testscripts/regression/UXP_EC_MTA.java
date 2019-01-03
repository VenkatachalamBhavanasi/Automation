package com.ssp.testscripts.regression;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.ssp.pages.uxp.CustDashboardPage;
import com.ssp.pages.uxp.HomePage;
import com.ssp.pages.uxp.LoginPage;
import com.ssp.pages.uxp.NewQuotePage;
import com.ssp.pages.uxp.SearchPage;
import com.ssp.soapservices.SOAP_UI;
import com.ssp.support.*;

@Listeners(EmailReport.class)
public class UXP_EC_MTA extends BaseTest {
	private String webSite;
	public String monthlywebSite;
	
	String firstName = "";
	String lastName = "";
	String quoteDescription1 = "TestDescription 1";
	String quoteDescription2 = "TestDescription 2";
	String quoteDescription3 = "TestDescription 3";

	@BeforeMethod(alwaysRun = true)
	public void init(ITestContext context) throws IOException {
		webSite = System.getProperty("webSite") != null ? System.getProperty("webSite")
				: context.getCurrentXmlTest().getParameter("webSite");
		monthlywebSite = System.getProperty("monthlywebSite") != null ? System.getProperty("monthlywebSite")
				: context.getCurrentXmlTest().getParameter("monthlywebSite");
	}

	public ExtentTest addTestInfo(String testCaseId, String testDesc) {

		String browserwithos = null;
		String test = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getName();

		String browsername = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
				.getParameter("browser");
		String browserversion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
				.getParameter("browser_version");
		String os = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("os").substring(0,
				1);
		String osversion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
				.getParameter("os_version");
		browserwithos = os + osversion + "_" + browsername + browserversion;

		return Log.testCaseInfo(testCaseId + " [" + test + "]",
				testCaseId + " - " + testDesc + " [" + browserwithos + "]", test, "UXP-EC-MTA");
	}

	public HashMap<String, String> getTestData(String testcaseId) {
		String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
		String className = "UXP_EC_MTA_" + env;
		return DataUtils.testDatabyID(testcaseId, className);
	}

	@Test(description = "Do MTA by changing cover type from Contents only to Building and content, remove all content covers from policy including HE AND LE", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_001(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_001";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Failed - Login failed",
					driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", " FAILED - Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Failed - Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);

			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					" FAILED - Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			Log.message("Cover : " + Corecover, extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get and Buy Quote
			newquotepage.clickGetQuote(extentedReport);
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			
			// Add covers
			String[] coversToAdd = testData.get("coverToSelect_NB").toString().split(",");
			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToAdd[i].split("_");
				if (BoolVal != false) {
					Log.pass(coverType[2] + " " + coverType[0] + "_" + coverType[1] + " cover type done successfully",
							driver, extentedReport, true);
				} else {
					Log.fail("Failed to " + coverType[2] + " " + coverType[0] + "_" + coverType[1] + " cover", driver,
							extentedReport, true);
				}

			}
			
			newquotepage.clickReCalculate(extentedReport);
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					" PASSED - Navigated to customer dashboard page", " FAILED - Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					" PASSED - NB Policy Created in " + policyDetails.get("Status"),
					" FAILED - NB Policy Created in " + policyDetails.get("Status"), driver, extentedReport, true);

			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			
			HashMap<String, String> financialDetailBefore = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String beforeNewPolicyTermPremium = financialDetailBefore.get("NewPolicyTermPremium").toString();
			
			// remove covers
			String[] coversToRemove = testData.get("coverToSelect").toString().split(",");
			for (int i = 0; i < coversToRemove.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToRemove[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToRemove[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToRemove[i].split("_");
				if (BoolVal != false) {
					Log.pass(
							coverType[2] + " " + coverType[0] + "_" + coverType[1] + "",
							driver, extentedReport, true);
				} else {
					Log.fail("Failed to " + coverType[2] + " " + coverType[0] + "_" + coverType[1]
							+ " ", driver, extentedReport, true);
				}

				// Click recalculate button
				newquotepage.clickReCalculate(extentedReport);
				HashMap<String, String> financialDetailAfterRemove = newquotepage
						.getFinancialsDetailFromPricePresentation(extentedReport, true);
				String NewPolicyTermPremium = financialDetailAfterRemove.get("NewPolicyTermPremium").toString();
				Log.message("Old Premium : " + beforeNewPolicyTermPremium + " ,New Premium :" + NewPolicyTermPremium, extentedReport);

				if (coversToRemove[i].contains("High Risk")) {
					Log.softAssertThat(
							newquotepage.verifyNewPolicyTermPremiumChange(beforeNewPolicyTermPremium,
									NewPolicyTermPremium, "Equal"),
							" PASSED - Premium did not change after removing the cover : " + coverType[0],
							" FAILED - Premium changed after removing the cover : " + coverType[0], driver, extentedReport, true);	
				} else {
				Log.softAssertThat(
						newquotepage.verifyNewPolicyTermPremiumChange(beforeNewPolicyTermPremium,
								NewPolicyTermPremium, "Reduced"),
						" PASSED - Premium got reduced after removing the cover : " + coverType[0],
						" FAILED - Premium not reduced after removing the cover : " + coverType[0], driver, extentedReport, true);
				}

				beforeNewPolicyTermPremium = NewPolicyTermPremium;
			}
			
			// Back to Datacapture page
			newquotepage.clickbacktoDatacapture(extentedReport);
			
			// Change cover to 'Buildings and Contents'
			newquotepage.selectCover("Buildings and Contents");
			
			// Navigate to Price presentation page
			newquotepage.clickNextOne(extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);
			
			// Verify negative value
		/*HashMap<String, String> financialDetail = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
		String adjustmentvalue = financialDetail.get("AdjustmentAmount");
			Log.softAssertThat(
					adjustmentvalue.contains("-"),
					" PASSED - Adjustment amount is displayed as negative in the Accept adjustment popup window",
					" FAILED - Adjustment amount is not displayed as negative in the Accept adjustment popup window", driver, extentedReport, true);
			*/
			//Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);	
			
			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.enterTrasactionIdForSTA(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					" PASSED - Navigated to customer dashboard page after MTA completed",
					" FAILED - Not navigated to customer dashboard page after MTA completed");

			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					" PASSED - MTA transaction is completed successfully - Test case completed",
					" FAILED - MTA transaction is not completed - Test case completed",
					driver, extentedReport, true);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check Premium should remain unchanged if no covers are changed while doing MTA,check MTA policy can be cancel and Restate again to same position and MTA can be done to Restate policy", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_005(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_005";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Failed - Login failed",
					driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Failed - Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Failed - Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);

			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Failed - Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get and Buy Quote
			newquotepage.clickGetQuote(extentedReport);
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Failed - Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					" PASSED - NB Policy Created in " + policyDetails.get("Status"),
					" FAILED - NB Policy Created in " + policyDetails.get("Status"), driver, extentedReport, true);
			
			// Cancel a Policy
			custdashboardpage.clickCancelPolicy(testData, true, extentedReport);
			custdashboardpage.enterRequestCancellationDate(extentedReport);
			custdashboardpage.enterReason(testData.get("Cancellation Reason"), extentedReport);
			custdashboardpage.enterPremiumType(testData, extentedReport);

			custdashboardpage.clickCalculate(true, extentedReport);
			custdashboardpage.clickConfirm(true, extentedReport);

			// Verifying that policy is cancelled
			Log.softAssertThat(custdashboardpage.verifyCancellation(true, extentedReport),
					"Policy cancelled successfully", "Failed - Policy is not cancelled", driver, extentedReport, true);

			custdashboardpage.clickReinstatePolicy(true, extentedReport);
			custdashboardpage.selectReinstateReason(testData, true, extentedReport);
			custdashboardpage.clickCalculateForReinstate(true, extentedReport);
			custdashboardpage.clickConfirmForReinstate(true, extentedReport);

			// Verifying that Policy is reinstated
			if (custdashboardpage.verifyReinstation(true, extentedReport))
				Log.pass("Policy Reinstated successfully", driver, extentedReport, true);
			else
				Log.fail("Policy is not reinstated", driver, extentedReport, true);

			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			
			HashMap<String, String> financialDetail = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String oldPremium = financialDetail.get("NewPolicyTermPremium");
			
			// Back to Datacapture page
			newquotepage.clickbacktoDatacapture(extentedReport);
			
			// Navigate to Price presentation page
			newquotepage.clickNextOne(extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);
			
			// Verify negative value
			HashMap<String, String> financialDetailAfter = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String adjustmentvalue = financialDetailAfter.get("AdjustmentAmount");
			String newPremium = financialDetailAfter.get("NewPolicyTermPremium");
			
			Log.softAssertThat(
					adjustmentvalue.equals("0.00"),
					" PASSED - Adjustment amount is not changed and remains same",
					" FAILED - Adjustment amount is changed and not remains same", driver, extentedReport, true);
			
			Log.softAssertThat(
					newPremium.equals(oldPremium),
					" PASSED - Premium remains same as no change made in policy data capture",
					" FAILED - Premium does not remains same after no change made in policy data capture");
			
			
			//Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);	
			
			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					" PASSED- Navigated to customer dashboard page after MTA completed",
					" FAILED - Not navigated to customer dashboard page after MTA completed");

			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					" PASSED - MTA transaction is completed successfully and displayed under Transactions tab",
					" FAILED - MTA transaction is not completed and not displayed under Transactions tab",
					driver, extentedReport, true);
			
			// Check for Cancellation transaction status
			String expectedPosition1 = "Cancellation";
			String expectedTransactionStatus1 = "Active: Debit Full Premium";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition1, expectedTransactionStatus1,
							extentedReport, true),
					" PASSED - Verified that the Cancellation transaction status is dispalyed under Transactions tab",
					" FAILED - Cancellation transaction status is not dispalyed under Transactions tab");

			// Check for Reinstatement transaction status
			String expectedPosition2 = "Reinstatement";
			String expectedTransactionStatus2 = "Active: Debit Full Premium";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition2, expectedTransactionStatus2,
							extentedReport, true),
					" PASSED - Verified that the Reinstatement status is dispalyed under Transactions tab - Test case completed",
					" FAILED - Reinstatement transaction status is not dispalyed under Transactions tab - Test case completed");

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "To verify changing cover type from Buildings to B&C only during MTA and To perform the MTA on the Reinstated Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_006(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_006";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Failed - Login failed",
					driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Failed - Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Failed - Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);

			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Failed - Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get and Buy Quote
			newquotepage.clickGetQuote(extentedReport);
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Failed - Not navigated to customer dashboard page");

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"), driver, extentedReport, true);
			
			// Cancel a Policy
			custdashboardpage.clickCancelPolicy(testData, true, extentedReport);
			custdashboardpage.enterRequestCancellationDate(extentedReport);
			custdashboardpage.enterReason(testData.get("Cancellation Reason"), extentedReport);
			custdashboardpage.enterPremiumType(testData, extentedReport);

			custdashboardpage.clickCalculate(true, extentedReport);
			custdashboardpage.clickConfirm(true, extentedReport);

			// Verifying that policy is cancelled
			Log.softAssertThat(custdashboardpage.verifyCancellation(true, extentedReport),
					"Policy cancelled successfully", "Failed - Policy is not cancelled", driver, extentedReport, true);

			custdashboardpage.clickReinstatePolicy(true, extentedReport);
			custdashboardpage.selectReinstateReason(testData, true, extentedReport);
			custdashboardpage.clickCalculateForReinstate(true, extentedReport);
			custdashboardpage.clickConfirmForReinstate(true, extentedReport);

			// Verifying that Policy is reinstated
			if (custdashboardpage.verifyReinstation(true, extentedReport))
				Log.pass("Policy Reinstated successfully", driver, extentedReport, true);
			else
				Log.fail("Policy is not reinstated", driver, extentedReport, true);

			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			
			// Back to Datacapture page
			newquotepage.clickbacktoDatacapture(extentedReport);
			
			// Change cover to 'Buildings and Contents'
			newquotepage.selectCover("Buildings and Contents");

			// Navigate to Price presentation page
			newquotepage.clickNextOne(extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);
			
			//Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);	
			
			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA is completed", "Failed - Not navigated to customer dashboard page after MTA is completed");
			
			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					" PASSED - MTA on the Reinstated Policy is completed successfully - Test case completed",
					" FAILED - MTA on the Reinstated Policy is not completed - Test case completed",
					driver, extentedReport, true);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "To perform the MTA on the MTA Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_013(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_013";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Failed - Login failed",
					driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Failed - Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Failed - Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);

			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Failed - Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get and Buy Quote
			newquotepage.clickGetQuote(extentedReport);
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Failed - Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"), driver, extentedReport, true);

			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			
			//Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);	
			
			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA is completed", "Failed - Not navigated to customer dashboard page after MTA is completed");
			
			// Do 2nd time MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);

			// Back to Datacapture page
			newquotepage.clickbacktoDatacapture(extentedReport);
			
			// Change year building
			Integer year = Integer.parseInt(testData.get("YOB").toString());
			String newYear = Integer.toString(year + 1);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");
			newquotepage.enterBuildingYear(newYear, extentedReport);

			// Navigate to Price presentation page
			newquotepage.clickNextOne(extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);
			
			//Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);	
			
			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.enterTrasactionIdForSTA(extentedReport);			
			newquotepage.clickConfirmMTA(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA is completed - Test case completed",
					"Failed - Not navigated to customer dashboard page after MTA is completed - Test case completed", driver, extentedReport,
					true);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "To perform the MTA on the STA Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_014(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_014";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Failed - Login failed",
					driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Failed - Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Failed - Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);

			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Failed - Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get and Buy Quote
			newquotepage.clickGetQuote(extentedReport);
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Failed - Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"), driver, extentedReport, true);
			
			// Select STA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickShortTermAdjustment(extentedReport);
			
			// Enter STA Detail
			testData.put("InceptionDate", policyDetails.get("InceptionDate"));
			testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
			custdashboardpage.enterSTADetails(testData, extentedReport, true);

			// To confirm STA
			newquotepage.clickBuyMTA(extentedReport, true);
			newquotepage.selectMTAReasonPayment(extentedReport);
			
			newquotepage.clickConfirmMTA(extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"STA is completed and Navigated to customer dashboard page",
					"STA is not completed and Not navigated to customer dashboard page", driver, extentedReport, true);

			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			
			// Specified bicycle
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");
			String[] cover = coversToAdd[0].split("_");
			String[] sepecifiedDescription = testData.get("PC_specifiedItems_Desc").toString().split(",");
			String[] sepecifiedValue = testData.get("PC_specifiedItems_coverAmt").toString().split(",");

			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);
			WaitUtils.waitForSpinner(driver);
			// Add 2 specified cycle
			newquotepage.enterCycelCountForUnspecifiedBicycle(testData.get("PC_UnspecifiedItems").toString());
			newquotepage.enterUnspecifiedCycleCover(testData.get("PC_UnspecifiedItems_coverAmt").toString());
			newquotepage.selectYesOrNoForSpecifiedCycle("Yes");
			newquotepage.selectSpecifiedCycleCount(testData.get("PC_specifiedItems"));
		
			newquotepage.addSpecifiedCycleDetail(sepecifiedDescription, sepecifiedValue, Integer.parseInt(testData.get("PC_specifiedItems").toString()));
			
			newquotepage.clickSaveBtnInPedalcycle();
			
			// Get excess value
			String displayedExcess = newquotepage.getExcessValue(ins_RowtoInteract, cover[0], testData).trim();	
			int valuetochk = Integer.parseInt(sepecifiedValue[0]) + Integer.parseInt(sepecifiedValue[1])+ Integer.parseInt(testData.get("PC_UnspecifiedItems_coverAmt"));
			
		
			Log.softAssertThat(displayedExcess.equals(Integer.toString(valuetochk)),
					cover[0] + " : Cover is added successfully",
					"Failed - " + cover[0] + " : Cover is not added successfully",
					driver, extentedReport, true);
			

			// Click recalculate button
			newquotepage.clickReCalculate(extentedReport);
			HashMap<String, String> financialDetailAfterRemove = newquotepage
					.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String adjustmentAmount = financialDetailAfterRemove.get("AdjustmentAmount").toString();
			
			Log.softAssertThat(!adjustmentAmount.equals("0.00"),
					"Adjustment amount is updated after adding cover",
					"Failed - Adjustment amount is not updated after adding cover",
					driver, extentedReport, true);
			
			//Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);	
			
			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.enterTrasactionIdForSTA(extentedReport);			
			newquotepage.clickConfirmMTA(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA is completed", "Failed - Not navigated to customer dashboard page after MTA is completed");
			
			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"MTA is completed successfully on STA policy - Test case completed",
					"Failed - MTA is not completed on STA policy - Test case completed", driver, extentedReport, true);


		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Do MTA by changing cover type from content to building only", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_012(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_012";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Failed - Login failed",
					driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Failed - Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Failed - Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);

			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Failed - Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get and Buy Quote
			newquotepage.clickGetQuote(extentedReport);
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Failed - Not navigated to customer dashboard page");

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"), driver, extentedReport, true);

			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			
			// Back to Datacapture page
			newquotepage.clickbacktoDatacapture(extentedReport);
			
			// Change cover to 'Buildings and Contents'
			newquotepage.selectCover("Buildings and Contents");
			
			// Navigate to Price presentation page
			newquotepage.clickNextOne(extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);
			
			HashMap<String, String> financialDetail = newquotepage
					.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String adjustmentAmount = financialDetail.get("AdjustmentAmount").toString();
			
			Log.softAssertThat(!adjustmentAmount.equals("0.00"),
					"Adjustment amount is changed after Policy updated to building only",
					"Failed - Adjustment amount is not changed after Policy not updated to building only",
					driver, extentedReport, true);
			
			//Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);	
			
			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.enterTrasactionIdForSTA(extentedReport);			
			newquotepage.clickConfirmMTA(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA is completed", "Failed - Not navigated to customer dashboard page after MTA is completed");
			
			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"MTA is completed successfully after policy updated to building only - Test case completed",
					"Failed - MTA not completed after policy not updated to building only - Test case completed",
					driver, extentedReport, true);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	// can not do MTA for Monthly policy in ASDA  
	//@Test(description = "Check MTA for monthly policy by editing or add covers one by one", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_002(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_002";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Failed - Login failed",
					driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Failed - Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Failed - Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);

			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Failed - Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get and Buy Quote
			newquotepage.clickGetQuote(extentedReport);
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Failed - Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"), driver, extentedReport, true);

			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			
			HashMap<String, String> financialDetailBefore = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String oldNewPolicyTermPremium = financialDetailBefore.get("NewPolicyTermPremium").toString();
			
			// For contents
			boolean BoolVal = false;
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");
					
			// get Contents default excess value
			String[] contents = coversToAdd[0].split("_");
			String ins_Row = newquotepage.SelectInsuranceItem("Contents_CNT_NoEdit", true, extentedReport);
			String defaultContentExcess = newquotepage.getExcessValue(ins_Row, contents[0], testData);
			
			Log.softAssertThat(defaultContentExcess.equals("100"),
					"By default Excess 100 is added for content",
					"By default Excess 100 is not added for content", driver,
					extentedReport, true);
			
			// Click the checkbox
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);
			newquotepage.clickAccidentalCheckbox("Contents");
			
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(
							Arrays.asList("lnkLimitsInContents", "drpVoluntaryExcess"), newquotepage),
					"Content cover popup contains Volunatry Excess and limits",
					"Failed - Content cover popup does not contains Volunatry Excess and limits", driver, extentedReport, true);
			
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);

			Log.softAssertThat(BoolVal, "Excess Value is entered for cover type : " + contents[0],
					"Excess Value is entered for cover type : " + contents[0], driver, extentedReport, true);

			// Accidental Damage excess value
			String[] accidentalDamage = coversToAdd[1].split("_");
			String ins_RowtoInteract2 = newquotepage.SelectInsuranceItem(coversToAdd[1], true, extentedReport);
			String accidentalExcess = newquotepage.getExcessValue(ins_RowtoInteract2, accidentalDamage[0], testData);

			Log.softAssertThat(accidentalExcess.equals(testData.get("CNT_ExcessAmt").toString()),
					"Same Excess Value is displayed for Accidental Damage which was entered for contents",
					"Same Excess Value is not displayed for Accidental Damage which was entered for contents", driver,
					extentedReport, true);
			
			newquotepage.clickReCalculate(extentedReport);
			HashMap<String, String> financialDetail = newquotepage
					.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String NewPolicyTermPremium = financialDetail.get("NewPolicyTermPremium").toString();
			Log.message("Old Premium : " + oldNewPolicyTermPremium + " ,New Premium :" + NewPolicyTermPremium, extentedReport);
			
			Log.softAssertThat(
					newquotepage.verifyNewPolicyTermPremiumChange(oldNewPolicyTermPremium,
							NewPolicyTermPremium, "Increased"),
					"Premium got increased after adding the cover : " + contents[0],
					"Failed - Premium not increased after adding the cover : " + contents[0], driver, extentedReport, true);
			
			
			String NewPolicyTermPremiumBeforeAdd = NewPolicyTermPremium;
			
			// add covers
			for (int i = 2; i < coversToAdd.length; i++) {
				BoolVal = false;
				String ins_RowtoInteract3 = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract3, true,
						extentedReport);
				String[] cover = coversToAdd[i].split("_");
				if (BoolVal != false) {
					Log.pass(
							cover[2] + " " + cover[0] + "_" + cover[1] + " cover type removed successfully",
							driver, extentedReport, true);
				} else {
					Log.fail("Failed to " + cover[2] + " " + cover[0] + "_" + cover[1]
							+ " cover not removed", driver, extentedReport, true);
				}

				// Click recalculate button
				newquotepage.clickReCalculate(extentedReport);
				HashMap<String, String> financialDetailAfterAdd = newquotepage
						.getFinancialsDetailFromPricePresentation(extentedReport, true);
				String NewPolicyTermPremiumAfterAdd = financialDetailAfterAdd.get("NewPolicyTermPremium").toString();
				Log.message("Old Premium : " + NewPolicyTermPremiumBeforeAdd + " ,New Premium :" + NewPolicyTermPremiumAfterAdd, extentedReport);

				Log.softAssertThat(
						newquotepage.verifyNewPolicyTermPremiumChange(NewPolicyTermPremiumBeforeAdd,
								NewPolicyTermPremiumAfterAdd, "Increased"),
						"Premium got increased after adding the cover : " + cover[0],
						"Failed - Premium not increased after adding the cover : " + cover[0], driver, extentedReport, true);

				NewPolicyTermPremiumBeforeAdd = NewPolicyTermPremiumAfterAdd;
			}
			
			//Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);	
			
			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.clickTerms(extentedReport);
			newquotepage.clickAcceptbtn(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA completed", "Failed - Not navigated to customer dashboard page after MTA completed");		

			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"MTA transaction is completed successfully",
					"Failed - MTA transaction is not completed",
					driver, extentedReport, true);
			
			// Verify Tax, Premium, Payable in transaction table
			HashMap<String, String> transactionValue = custdashboardpage.getTransactionTableValue(expectedPosition,
					extentedReport);

			String Tax = transactionValue.get("Tax");
			String Premium = transactionValue.get("Premium");
			String Payable = transactionValue.get("Payable");
		
			//Log.softAssertThat(AdjustmentAmount.equals(Premium), Tax + " Correct Premium value is displayed in transaction table", "Failed - " + Premium + " Premium value is not displayed in transaction table");
			 
			Log.softAssertThat(custdashboardpage.verifyTaxInTransactionTable(Tax, Premium),
					Tax + " Correct Tax value is displayed in transaction table",
					"Failed - " + Tax + " Tax value is not displayed in transaction table");

			Log.softAssertThat(custdashboardpage.verifyPayableInTransactionTable(Tax, Premium, Payable),
					Payable + " Correct payable value is displayed in transaction table - Test case completed",
					"Failed - " + Payable
							+ " Payable value is not displayed in transaction table - Test case completed",
					driver, extentedReport, true);
	
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check close and cancel button for MTA and change building and content to building only type", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_010(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_010";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Failed - Login failed",
					driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Failed - Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Failed - Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);

			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Failed - Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get and Buy Quote
			newquotepage.clickGetQuote(extentedReport);
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			
			// Add covers
			String[] coversToAdd = testData.get("coverToSelect_NB").toString().split(",");
			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToAdd[i].split("_");
				if (BoolVal != false) {
					Log.pass(coverType[2] + " " + coverType[0] + "_" + coverType[1] + " cover type done successfully",
							driver, extentedReport, true);
				} else {
					Log.fail("Failed to " + coverType[2] + " " + coverType[0] + "_" + coverType[1] + " cover", driver,
							extentedReport, true);
				}

			}
			
			newquotepage.clickReCalculate(extentedReport);
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Failed - Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"), driver, extentedReport, true);

			// Select MTA & click cancel
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);			
			custdashboardpage.clickCancelBtnInMtaPopup("cancel", extentedReport);
			
			// Verify MTA Prompt
			Log.softAssertThat(
					!custdashboardpage.uielement.verifyPageElements(Arrays.asList("cmbMidTermAdjReason"), custdashboardpage),
					"MTA Window is closed after clicking cancel button",
					"MTA Window is not closed after clicking cancel button", driver,
					extentedReport, true);
			
			// Select MTA & click close 
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.clickCancelBtnInMtaPopup("close", extentedReport);

			// Verify MTA Prompt
			Log.softAssertThat(
					!custdashboardpage.uielement.verifyPageElements(Arrays.asList("cmbMidTermAdjReason"),
							custdashboardpage),
					"MTA Window is closed after clicking close button",
					"MTA Window is not closed after clicking close button", driver, extentedReport, true);
			
			
			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			
			HashMap<String, String> financialDetailBefore = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String beforeNewPolicyTermPremium = financialDetailBefore.get("NewPolicyTermPremium").toString();
			
			// remove covers
			String[] coversToRemove = testData.get("coverToSelect").toString().split(",");
			for (int i = 0; i < coversToRemove.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToRemove[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToRemove[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToRemove[i].split("_");
				if (BoolVal != false) {
					Log.pass(
							coverType[0] + "_" + coverType[1] + " cover type removed successfully",
							driver, extentedReport, true);
				} else {
					Log.fail("Failed to " + coverType[0] + "_" + coverType[1]
							+ " cover not removed", driver, extentedReport, true);
				}

				// Click recalculate button
				newquotepage.clickReCalculate(extentedReport);
				HashMap<String, String> financialDetailAfterRemove = newquotepage
						.getFinancialsDetailFromPricePresentation(extentedReport, true);
				String NewPolicyTermPremium = financialDetailAfterRemove.get("NewPolicyTermPremium").toString();
				Log.message("Old Premium : " + beforeNewPolicyTermPremium + " ,New Premium : " + NewPolicyTermPremium, extentedReport);

				
				if (coversToRemove[i].contains("High Risk")) {
					Log.softAssertThat(
							newquotepage.verifyNewPolicyTermPremiumChange(beforeNewPolicyTermPremium,
									NewPolicyTermPremium, "Equal"),
							" PASSED - Premium did not change after removing the cover : " + coverType[0],
							" FAILED - Premium changed after removing the cover : " + coverType[0], driver, extentedReport, true);	
				} else {
				
				Log.softAssertThat(
						newquotepage.verifyNewPolicyTermPremiumChange(beforeNewPolicyTermPremium,
								NewPolicyTermPremium, "Reduced"),
						"Premium got reduced after removing the cover : " + coverType[0],
						"Failed - Premium not reduced after removing the cover : " + coverType[0], driver, extentedReport, true);
				}

				beforeNewPolicyTermPremium = NewPolicyTermPremium;
			}
			
			// Back to Datacapture page
			newquotepage.clickbacktoDatacapture(extentedReport);
			
			// Change cover to 'Buildings'
			newquotepage.selectCover("Buildings");
			
			// Navigate to Price presentation page
			newquotepage.clickNextOne(extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);
			
			// Verify negative value
			HashMap<String, String> financialDetail = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String adjustmentvalue = financialDetail.get("AdjustmentAmount");
			Log.softAssertThat(
					adjustmentvalue.contains("-"),
					"Adjustment amount is displayed as negative in the Accept adjustment popup window",
					"Failed - Adjustment amount is not displayed as negative in the Accept adjustment popup window", driver, extentedReport, true);
			
			//Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);	
			
			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA completed", "Failed - Not navigated to customer dashboard page after MTA completed");

			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"MTA transaction is completed successfully - Test case completed",
					"Failed - MTA transaction is not completed - Test case completed",
					driver, extentedReport, true);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Do MTA by changing cover type from Building and content to building only and adding joint policy holder", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_009(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_009";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		HashMap<String, String> testData2 = new HashMap<String, String>(testData);
		HashMap<String, String> testData3 = new HashMap<String, String>(testData);
		HashMap<String, String> testData4 = new HashMap<String, String>(testData);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Login failed",
					driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			
					// Enter main customer Details
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get(); // added this line for demo
		//	searchPage.searchValidPolicy(policyDetails.get("PolicyNo"), true, extentedReport);
			CustDashboardPage custdashboardpage = searchPage.selectPolicy_from_SearchPage(true, extentedReport);
			
			// Verifying Customer Details
			custdashboardpage.clickPassVerification(extentedReport, true);
			custdashboardpage.verifyCustomerDashboardPage();

			
			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			
			NewQuotePage newquotepage = new NewQuotePage(driver,extentedReport);
			// Back to Datacapture page
			newquotepage.clickbacktoDatacapture(extentedReport);

			// Change cover to 'Buildings'
			newquotepage.selectCover("Buildings");

			// Navigate to Price presentation page
			newquotepage.clickNextOne(extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);
			
			// Verify Accidental Damage for contents
			String contentsAccidentalDamage = "Accidental Damage_CNT_Button";
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(contentsAccidentalDamage, true, extentedReport);
			
			Log.softAssertThat(
					ins_RowtoInteract == null,
					"Contents Accidential Damage cover is not displayed",
					"Failed - Contents Accidential Damage cover is displayed", driver, extentedReport, true);
			
			
			//Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);	
			
			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA is completed",
					"Failed - Not navigated to customer dashboard page after MTA is completed", driver, extentedReport,
					true);
			
			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"MTA transaction is completed successfully after changing cover type from Building and content  to building only and adding joint policy holder - Test case completed",
					"Failed - MTA transaction is not completed  changing cover type from Building and content  to building only and adding joint policy holder - Test case completed", driver, extentedReport, true);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}

	@Test(description = "Check save Quote functionality while doing MTA", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_016(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_016";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Login failed",
					driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);
			
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Not Verified Customer Name on Dashboard", driver, extentedReport, true);
			
			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get and Buy Quote
			newquotepage.clickGetQuote(extentedReport);
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
						
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					" FAILED - NB Policy Created in " + policyDetails.get("Status"),
					driver,		extentedReport, true);
			
			
			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			
			// Add covers
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");
			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToAdd[i].split("_");
				if (BoolVal != false) {
					Log.pass(coverType[0] + "_" + coverType[1] + " cover type added successfully",
							driver, extentedReport, true);
				} else {
					Log.fail("Failed to " + coverType[0] + "_" + coverType[1] + " ", driver,
							extentedReport, true);
				}

			}
			newquotepage.clickReCalculate(extentedReport);
			
			//Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);	
			
			// Click Save Quote button
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.clickSaveQuoteMta(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA is completed",
					" FAILED - Not navigated to customer dashboard page after MTA is completed", driver, extentedReport,
					true);
			
			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Inactive";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					" PASSED - MTA as 'Inactive' status is dispalyed and Quote is saved - Test case completed",
					" FAILED - MTA as 'Inactive' status is dispalyed and Quote is not saved - Test case completed", driver, extentedReport, true);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	// can not do MTA for Monthly policy in ASDA  
	//@Test(description = "To verify MTA Monthly premium finance for all cover types for 5 *policy, Removing all covers one by one and check the premium get reduced from each removal of covers", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_015(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_015";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Failed - Login failed",
					driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Failed - Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Failed - Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);

			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Failed - Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get Quote and upgrade
			newquotepage.clickGetQuote(extentedReport);
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			
			// Add covers
			String[] coversToAdd = testData.get("coverToSelect_NB").toString().split(",");
			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToAdd[i].split("_");
				if (BoolVal != false) {
					Log.pass(coverType[0] + "_" + coverType[1] + " cover type added successfully",
							driver, extentedReport, true);
				} else {
					Log.fail("Failed " + coverType[0] + "_" + coverType[1] + " cover not added", driver,
							extentedReport, true);
				}

			}
			
			newquotepage.Scheme = "WB";
			newquotepage.upgradePolicy(extentedReport);
			
			newquotepage.clickReCalculate(extentedReport);
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Failed - Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"), driver, extentedReport, true);

			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			
			HashMap<String, String> financialDetailBefore = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String beforeNewPolicyTermPremium = financialDetailBefore.get("NewPolicyTermPremium").toString();
			
			// remove covers
			String[] coversToRemove = testData.get("coverToSelect").toString().split(",");
			for (int i = 0; i < coversToRemove.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToRemove[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToRemove[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToRemove[i].split("_");
				if (BoolVal != false) {
					Log.pass(
							coverType[2] + " " + coverType[0] + "_" + coverType[1] + " cover type removed successfully",
							driver, extentedReport, true);
				} else {
					Log.fail("Failed to " + coverType[2] + " " + coverType[0] + "_" + coverType[1]
							+ " cover not removed", driver, extentedReport, true);
				}

				// Click recalculate button
				newquotepage.clickReCalculate(extentedReport);
				HashMap<String, String> financialDetailAfterRemove = newquotepage
						.getFinancialsDetailFromPricePresentation(extentedReport, true);
				String NewPolicyTermPremium = financialDetailAfterRemove.get("NewPolicyTermPremium").toString();
				String adjustmentValue = financialDetailAfterRemove.get("AdjustmentAmount").toString();
				Log.message("Old Premium : " + beforeNewPolicyTermPremium + " ,New Premium :" + NewPolicyTermPremium, extentedReport);
				Log.message("Adjustment Value : " + adjustmentValue, extentedReport);

				Log.softAssertThat(
						newquotepage.verifyNewPolicyTermPremiumChange(beforeNewPolicyTermPremium,
								NewPolicyTermPremium, "Reduced"),
						"Premium got reduced after removing the cover : " + coverType[0],
						"Failed - Premium not reduced after removing the cover : " + coverType[0], driver, extentedReport, true);
				
				Log.softAssertThat(adjustmentValue.contains("-"),
						"Adjustement amount is in negative after removing the cover : " + coverType[0],
						"Failed - Adjustement amount is not in negative after removing the cover  : " + coverType[0], driver, extentedReport, true);

				beforeNewPolicyTermPremium = NewPolicyTermPremium;
			}
			
			// Verify negative value
			HashMap<String, String> financialDetail = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String adjustmentvalue = financialDetail.get("ChangeToPolicyTermPremium");
			Log.softAssertThat(
					adjustmentvalue.contains("-"),
					"ChangeToPolicyTerm Premium is displayed as negative in the Accept adjustment popup window",
					"Failed - ChangeToPolicyTerm Premium is not displayed as negative in the Accept adjustment popup window", driver, extentedReport, true);
			
			//Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);	
			
			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.clickTerms(extentedReport);
			newquotepage.clickAcceptbtn(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA completed", "Failed - Not navigated to customer dashboard page after MTA completed");

			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"MTA transaction is completed successfully - Test case completed",
					"Failed - MTA transaction is not completed - Test case completed",
					driver, extentedReport, true);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Do MTA by change cover type from Building only to content only and Check can do MTA for policy which has already MTA in IR status and check Quote status MTA can be buy from EC", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_007(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_007";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);
		//String userName = testData.get("UserName");
		//String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		String soapProjectLoc = "src/main/resources/projects/CreateTestUsers-soapui-project.xml"; 
		String soapTSNewP = "PersonSave";
		String soapTestCaseName = "CreateUsersTestCase";
		String soapTSSystemUser = "SystemUserSave";
		String soapTSUnderWritter = "UnderwriterSave";
		String soapen = "https://hood-test.ssp-hosting.com/HGUAT/TechnologyFrameworksWeb"; 
		String soapSuiteName = "CreateUsersTestSuite"; 
		String email = "lucky";
		String foreName = "Harsh" + Utils.getRandomCharacters("alpha", 5);
		String surName = "tester" + Utils.getRandomCharacters("alpha", 5);
		String pricipalId = foreName+Utils.getRandomCharacters("alpha", 5);

		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {
			SOAP_UI ui = new SOAP_UI(); 
			String URN = ui.createPersonThruSOAPCall(soapProjectLoc, soapSuiteName, soapTestCaseName, soapTSNewP,
					foreName, surName, email, soapen, extentedReport); 
		String userName = ui.saveSystemUserThroughSOAPCall(soapProjectLoc, soapSuiteName, soapTestCaseName, soapTSSystemUser,
					URN, pricipalId,soapen, extentedReport); 
		String password = "Passw0rd";
           System.out.println(userName);
           ui.saveUnderwriterThroughSOAPCall(soapProjectLoc, soapSuiteName, soapTestCaseName, soapTSUnderWritter,
					URN,soapen, extentedReport); 
           
           
           
			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Failed - Login failed",
					driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Failed - Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Failed - Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);

			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Failed - Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get Quote and upgrade
			newquotepage.clickGetQuote(extentedReport);
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			
			// Add covers
			String[] coversToAdd = testData.get("coverToSelect_NB").toString().split(",");
			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToAdd[i].split("_");
				if (BoolVal != false) {
					Log.pass(coverType[0] + "_" + coverType[1] + " cover type added successfully",
							driver, extentedReport, true);
				} else {
					Log.fail("Failed " + coverType[0] + "_" + coverType[1] + " cover not added", driver,
							extentedReport, true);
				}

			}
			
			newquotepage.clickReCalculate(extentedReport);
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Failed - Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"), driver, extentedReport, true);

			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			
			// Navigate back to dashboard
			newquotepage.clickbacktoDashboard(extentedReport);
			
			// Check for IR status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Inactive";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"MTA is IR Status",
					"Failed - MTA is not IR Status", driver, extentedReport, true);
			
			// click complete button
			custdashboardpage.clickCompleteButton();
			
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Failed - Search Page not Verified", driver, extentedReport, true);
			
			// Search the policy
			searchPage.searchValidPolicy(policyDetails.get("PolicyNo"), true, extentedReport);
			searchPage.selectPolicy_from_SearchPage(true, extentedReport);
			
			// Verifying Customer Details
			custdashboardpage.clickPassVerification(extentedReport, true);
			custdashboardpage.verifyCustomerDashboardPage();
			
			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			
			// Open Personal Item popup
			String[] coversToMTA = testData.get("coverToSelect").toString().split(",");
			newquotepage.SelectInsuranceItem(coversToMTA[0], true, extentedReport);
			
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(Arrays.asList("ins_PI_coverReq", "ins_PI_SaveBtn",
							"lnkLimitsInPersonalItemPopup", "btnCancelPersonalItem"), newquotepage),
					"A popup contains Cover dropdown, Limits, Save and Cancel button",
					"A popup does not contains Cover dropdown, Limits, Save and Cancel button");
			
			Log.softAssertThat(
					newquotepage.verifyPersonalItemcoverPopupDetails(testData, extentedReport),
					"Personal Item pop up displayed the default values that is saved already" ,
					"Personal Item pop up not displayed the default values that is saved already",
					driver, extentedReport, true);
			
			newquotepage.clickCancelBtnInPersonalItemPopup(extentedReport);
			
			// Add Pedal cycle
			boolean BoolVal = false;
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToMTA[1], true, extentedReport);
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToMTA[1], ins_RowtoInteract, true,
					extentedReport);
			String[] coverType = coversToMTA[1].split("_");
			if (BoolVal != false) {
				Log.pass(coverType[0] + "_" + coverType[1] + " cover type added successfully",
						driver, extentedReport, true);
			} else {
				Log.fail("Failed to " + coverType[2] + " " + coverType[0] + "_" + coverType[1] + " cover", driver,
						extentedReport, true);
			}
			
			// Click Recalculate button
			newquotepage.clickReCalculate(extentedReport);
			
			// Verify Adjustment value
			HashMap<String, String> financialDetail = newquotepage
					.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String adjustmentvalue = financialDetail.get("AdjustmentAmount");
			Log.softAssertThat(!adjustmentvalue.equals("0.00"),
					"Adjustment amount got increased",
					"Failed - Adjustment amount not got increased",
					driver, extentedReport, true);
			
			// Back to Datacapture page
			newquotepage.clickbacktoDatacapture(extentedReport);

			// Change cover to 'Buildings and Contents'
			newquotepage.selectCover("Contents");

			// Navigate to Price presentation page
			newquotepage.clickNextOne(extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);
			
			// Verify for Accidental Damage for building
			String buildingAccidentalDamage = "Accidental Damage_BLD_Button";
			String  accidentalDamage = newquotepage.SelectInsuranceItem(buildingAccidentalDamage, true, extentedReport);
			
			Log.softAssertThat(
					accidentalDamage == null,
					"Buildings Accidential Damage cover is not displayed",
					"Failed - Buildings Accidential Damage cover is displayed", driver, extentedReport, true);
			
			// Click save button
			newquotepage.clicksave(extentedReport);		
			
			// Check for IR status
			String expectedPosition2 = "MidTermAdjustment";
			String expectedTransactionStatus2 = "Inactive";
			Log.softAssertThat(
					custdashboardpage.getTransactionStatusCount(extentedReport, expectedPosition2, expectedTransactionStatus2).equals("2"),
					"2nd MTA is IR Status", "Failed - 2nd MTA is not IR Status", driver, extentedReport, true);
			
			// click complete button
			custdashboardpage.clickCompleteButton();

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Failed - Search Page not Verified", driver, extentedReport, true);

			// Search the policy
			searchPage.searchValidPolicy(policyDetails.get("PolicyNo"), true, extentedReport);
			searchPage.selectPolicy_from_SearchPage(true, extentedReport);
			custdashboardpage.clickPassVerification(extentedReport, true);
			
			//Click 'Edit transaction' button
			custdashboardpage.selectTransactionRow(extentedReport, "Inactive");
			custdashboardpage.clickEditTransactionButton(extentedReport, true);
			
			//Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);	
			
			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"MTA transaction is completed successfully - Test case completed",
					"Failed - Not navigated to customer dashboard page after MTA completed");
			
			// Check for IR status
			String expectedPosition3 = "MidTermAdjustment";
			String expectedTransactionStatus3 = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition3, expectedTransactionStatus3,
							extentedReport, true),
					"MTA transaction is completed successfully - Test case completed", "Failed - MTA transaction is not completed - Test case completed", driver,
					extentedReport, true);
			

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Do MTA by changing 5 to 3 star and do another MTA for back dated, check transaction section showing policy position properly", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_004(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_004";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Failed - Login failed",
					driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Failed - Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Failed - Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);

			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Failed - Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get Quote and upgrade
			newquotepage.clickGetQuote(extentedReport);
			//newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
			
			// Add covers
			String[] coversToAdd = testData.get("coverToSelect_NB").toString().split(",");
			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToAdd[i].split("_");
				if (BoolVal != false) {
					Log.pass(coverType[0] + "_" + coverType[1] + " cover type added successfully",
							driver, extentedReport, true);
				} else {
					Log.fail("Failed " + coverType[0] + "_" + coverType[1] + " cover not added", driver,
							extentedReport, true);
				}

			}
			
			newquotepage.Scheme = "ASDA";
			newquotepage.upgradePolicy(extentedReport);
			
			newquotepage.clickReCalculate(extentedReport);
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Failed - Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"), driver, extentedReport, true);

			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			
			HashMap<String, String> financialDetailBefore = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String beforeNewPolicyTermPremium = financialDetailBefore.get("NewPolicyTermPremium").toString();
			
			// Edit and Add Garden cover
			testData.put("GC_ItemVaue", "2000");
			String[] coversToMta = testData.get("coverToSelect").toString().split(",");
			for (int i = 0; i < coversToMta.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToMta[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToMta[i], ins_RowtoInteract, true,
						extentedReport);
				String[] cover = coversToMta[i].split("_");
				if (BoolVal != false) {
					Log.pass(cover[0] + "_" + cover[1] + " cover type added successfully", driver,
							extentedReport, true);
				} else {
					Log.fail("Failed " + cover[0] + "_" + cover[1] + " cover not added", driver, extentedReport,
							true);
				}

			}
			
			// downgrade policy
			newquotepage.Scheme = "ASDA";
			newquotepage.downgradePolicy(extentedReport);
			newquotepage.clickReCalculate(extentedReport);
			
			// Verify negative value
			HashMap<String, String> financialDetail = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			
			String adjustmentvalue = financialDetail.get("AdjustmentAmount");
			Log.softAssertThat(adjustmentvalue.contains("-"), "Adjustment amount is in negative",
					"Failed - Adjustment amount is not in negative", driver, extentedReport, true);
			
			String afterNewPolicyTermPremium = financialDetail.get("NewPolicyTermPremium").toString();
			Log.message("Old Premium : " + beforeNewPolicyTermPremium + " ,New Premium : " + afterNewPolicyTermPremium, extentedReport);

			Log.softAssertThat(
					newquotepage.verifyNewPolicyTermPremiumChange(beforeNewPolicyTermPremium,
							afterNewPolicyTermPremium, "Reduced"),
					"Premium got reduced after downgrade",
					"Failed - Premium not reduced after downgrade", driver, extentedReport, true);
			
			//Navigate to datacapture page
			newquotepage.clickbacktoDatacapture(extentedReport);
			
			// Change No.Of bedroom
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");
			newquotepage.selectNumberofBedrooms("5");
			// Change year building
			Integer year = Integer.parseInt(testData.get("YOB").toString());
			String newYear = Integer.toString(year - 1);
			newquotepage.enterBuildingYear(newYear, extentedReport);
			
			// Navigate to Price presentation page
			newquotepage.clickNextOne(extentedReport);
			
			Log.softAssertThat(
					newquotepage.verifyYesBtnSelectedForHighRiskOver1500(extentedReport),
					"High risk item set to yes",
					"Failed - High risk item is not set to yes", driver, extentedReport, true);
			
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);
			
			HashMap<String, String> newFinancialDetail = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String adjustmentAmount = newFinancialDetail.get("AdjustmentAmount").toString();
			String changeToPolicyTermPremium = newFinancialDetail.get("ChangeToPolicyTermPremium").toString();
			
			Log.softAssertThat(!(adjustmentAmount.equals(changeToPolicyTermPremium)),
					"Adjustment amount and Change to Policy term premium is not same in trascation section",
					"Failed - Adjustment amount and Change to Policy term premium is  same in trascation section", driver, extentedReport, true);

			// Navigate back to dashboard
			newquotepage.clickbacktoDashboard(extentedReport);

			// click complete button
			custdashboardpage.clickCompleteButton();	
			
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
				
			// Search the policy
			searchPage.searchValidPolicy(policyDetails.get("PolicyNo"), true, extentedReport);
			searchPage.selectPolicy_from_SearchPage(true, extentedReport);

			// Verifying Customer Details
			custdashboardpage.clickPassVerification(extentedReport, true);
			custdashboardpage.verifyCustomerDashboardPage();

			// Check for MTA status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Inactive";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"MTA status is found in transaction table", "Failed - MTA status is not found in transaction table", driver, extentedReport, true);
			
			// Check for MTA status
			String expectedPosition2 = "NewBusiness";
			String expectedTransactionStatus2 = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition2, expectedTransactionStatus2,
							extentedReport, true),
					expectedPosition2 + " - status is found in transaction table", "Failed - " + expectedPosition2 + "  status is not found in transaction table",
					driver, extentedReport, true);
			
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}
	
	@Test(description = "Check MTA for 3 star annual policy by Adding all covers", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void TC_008(String browser) throws Exception {
        // Get the web driver instance
        String tcId = "TC_008";
        final WebDriver driver = WebDriverFactory.get(browser);
        HashMap<String, String> testData = getTestData(tcId);

        String userName = testData.get("UserName");
        String password = testData.get("Password");
        String description = testData.get("Description");
        String Corecover = testData.get("Cover");
        String brandname = testData.get("Brand Name");
        Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

        ExtentTest extentedReport = addTestInfo(tcId, description);

        try {

            // Navigate to Login Page
            LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
            Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

            // Login to the application
            HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
            Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Failed - Login failed",
                    driver, extentedReport, true);
            Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
            // Click on Take Call link
            homePage.clickTakeCall(extentedReport);
            homePage.clickMyBrands(brandname, extentedReport);
            SearchPage searchPage = new SearchPage(driver, extentedReport).get();
            Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
                    "Search Page Verified", "Failed - Search Page not Verified", driver, extentedReport, true);

            // Enter Customer Details
            searchPage.clickCreateCustomer(true, extentedReport);
            HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

            // Confirm customer details and create customer
            CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
            
            Log.softAssertThat(
                    custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
                            + " " + custDetails.get("Last Name"), extentedReport, true),
                    "Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
                            + custDetails.get("Last Name"),
                    "Failed - Not Verified Customer Name on Dashboard", driver, extentedReport, true);

            // Create New Quote
            custdashboardpage.clickNewQuote(true, extentedReport);
            custdashboardpage.enterQuoteDetails(testData, true, extentedReport);

            NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

            Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
                    "Failed - Not verified NewQuotePage", driver, extentedReport, true);
            newquotepage.enterPolicyDetails(testData, true, extentedReport);
            newquotepage.clickNextOne(extentedReport);
            newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
            newquotepage.clickNextTwo(extentedReport);
            newquotepage.clickView(extentedReport);
            newquotepage.clickAgree(extentedReport);

            // Get and Buy Quote
            newquotepage.clickGetQuote(extentedReport);
        
            newquotepage.clickBuy(extentedReport);

            // Select Payment
            newquotepage.selectPayment(testData, true, extentedReport);
            custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
            Log.softAssertThat(
                    custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
                    "Navigated to customer dashboard page", "Failed - Not navigated to customer dashboard page", driver,
                    extentedReport, true);

            // check for policy status
            HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
            
            Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
                    "NB Policy Created in " + policyDetails.get("Status"),
                    "Failed - NB Policy Created in " + policyDetails.get("Status"), driver, extentedReport, true);

            testData.put("InceptionDate", policyDetails.get("InceptionDate"));
            testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
            
            //  MTA 
            custdashboardpage.clickManagePolicy(extentedReport);
            custdashboardpage.clickMidTermAdjustment(extentedReport);
            custdashboardpage.enterMTADetails(testData, extentedReport, true);
            
            Log.softAssertThat(
                    newquotepage.uielement.verifyPageElements(Arrays.asList("policystartDateatPricepresentationpage","policyEndDateatPricepresentationpage","adjustmentStartDateatPricepresentationpage","adjustmentStartDateatPricepresentationpage"), newquotepage),
                    "policy details verified at price presentation page", "Failed to display policy details at price presentation page", driver,
                    extentedReport, true);
            
               
            HashMap<String, String> financialDetailBefore = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
            String beforeNewPolicyTermPremium = financialDetailBefore.get("NewPolicyTermPremium").toString();
            
            // Add covers
			String[] coversToAdd = testData.get("coverToSelect_NB").toString().split(",");
			for (int i = 0; i < coversToAdd.length; i++) {
				boolean BoolVal = false;
				String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
				BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
						extentedReport);
				String[] coverType = coversToAdd[i].split("_");
				if (BoolVal != false) {
					Log.pass(coverType[2] + " " + coverType[0] + "_" + coverType[1] + " cover type done successfully",
							driver, extentedReport, true);
					
					// Click recalculate button
	                newquotepage.clickReCalculate(extentedReport);
	                HashMap<String, String> financialDetailAfterRemove = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
	                String NewPolicyTermPremium = financialDetailAfterRemove.get("NewPolicyTermPremium").toString();
	                Log.message("Old Premium : " + beforeNewPolicyTermPremium + " ,New Premium :" + NewPolicyTermPremium, extentedReport);
	              //  String adjustedAmt =financialDetailAfterRemove.get("AdjustmentAmount").toString();
	                String ChangedAmtPP =financialDetailAfterRemove.get("ChangeToPolicyTermPremium").toString();
	                
	                
	                if (coversToAdd[i].contains("High Risk")) {
						Log.softAssertThat(
								newquotepage.verifyNewPolicyTermPremiumChange(beforeNewPolicyTermPremium,NewPolicyTermPremium, "Equal"),
								" PASSED - Premium did not increase after adding the cover : " + coverType[0],
								" FAILED - Premium increase after adding the cover : " + coverType[0], driver, extentedReport, true);	
					} else {
	                
	                Log.softAssertThat(
	                        newquotepage.verifyNewPolicyTermPremiumChange(beforeNewPolicyTermPremium,NewPolicyTermPremium, "Increased"),
	                        "Premium got incresed after adding the cover : " + coverType[0],
	                        "Failed - Premium not increased after adding the cover : " + coverType[0], driver, extentedReport, true);
					}
					Log.softAssertThat(
							newquotepage.verifyNewPolicyTermPremium(
									financialDetailAfterRemove.get("ExistingPolicyTermPremium"),
									financialDetailAfterRemove.get("NewPolicyTermPremium"),
									ChangedAmtPP),
							"NewPolicyTermPremium is sum of Existing premium and adjustment amt",
							"NewPolicyTermPremium is not an addition of Existing premium and adjustment amt",
							driver, extentedReport, true);
	                
	                
	               /* double oldpremium=Double.parseDouble(financialDetailAfterRemove.get("ExistingPolicyTermPremium"));
	                double adjustpre=Double.parseDouble(adjustedAmt);
	              
	                DecimalFormat df = new DecimalFormat("##.00");
	                
	                double Updatedpremium=(oldpremium +adjustpre);
	                  
	              
	              String s=df.format(Updatedpremium);
	              Updatedpremium =Double.parseDouble(s);
	          
	                Boolean value =Updatedpremium==(Double.parseDouble(NewPolicyTermPremium));
	                System.out.println(value);
	                Log.softAssertThat(value, "NewPolicyTermPremium is sum of Existing premium and adjustment amt", "NewPolicyTermPremium is not an addition of Existing premium and adjustment amt");
	             */
	                beforeNewPolicyTermPremium = NewPolicyTermPremium;
	                
				} else {
					Log.fail("Failed to " + coverType[2] + " " + coverType[0] + "_" + coverType[1] + " cover", driver,
							extentedReport, true);
				}				}

		
            //Click Buy button
            newquotepage.clickBuyMTA(extentedReport, true);    
            
            // Click Confirm button
            newquotepage.selectMTAReasonPayment(extentedReport);
            newquotepage.enterTrasactionIdForSTA(extentedReport);			
			newquotepage.clickConfirmMTA(extentedReport);
            
            Log.softAssertThat(
                    custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
                    "Navigated to customer dashboard page after MTA completed", "Failed - Not navigated to customer dashboard page after MTA completed");

            // Check for MTA transaction status
            String expectedPosition = "MidTermAdjustment";
            String expectedTransactionStatus = "Active: Debit And Issue Policy";
            Log.softAssertThat(
                    custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
                            extentedReport, true),
                    "MTA transaction is completed successfully - Test case completed",
                    "Failed - MTA transaction is not completed - Test case completed",
                    driver, extentedReport, true);

        } // try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        } // catch
        finally {
            Log.endTestCase(extentedReport);
            driver.quit();
            Log.testCaseResult(extentedReport);

        } // finally
    }
 
 @Test(description = "Do MTA by changing cover type from Building and content to conent ", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_011(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_011";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String Corecover = testData.get("Cover");
		String brandname = testData.get("Brand Name");
		Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);

		try {

			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Failed - Login failed",
					driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Failed - Search Page not Verified", driver, extentedReport, true);

			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
							+ " " + custDetails.get("Last Name"), extentedReport, true),
					"Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
							+ custDetails.get("Last Name"),
					"Failed - Not Verified Customer Name on Dashboard", driver, extentedReport, true);

			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);

			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Failed - Not verified NewQuotePage", driver, extentedReport, true);
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);

			// Get and Buy Quote
			newquotepage.clickGetQuote(extentedReport);	
			newquotepage.clickBuy(extentedReport);

			// Select Payment
			newquotepage.selectPayment(testData, true, extentedReport);
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page", "Failed - Not navigated to customer dashboard page", driver,
					extentedReport, true);

			// check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
					"NB Policy Created in " + policyDetails.get("Status"),
					"Failed - NB Policy Created in " + policyDetails.get("Status"), driver, extentedReport, true);

			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			// Back to Datacapture page
			newquotepage.clickbacktoDatacapture(extentedReport);
			newquotepage.verifyDataCapturePage(testData, true, extentedReport);
			// Change cover to 'Buildings and Contents'
			newquotepage.selectCover("Contents");
			
							// Navigate to Price presentation page
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enter_PIDetailsFormoreThan1500(testData, true);
			newquotepage.enter_PedalBiCycleDetailsFormoreThan1500(testData, true);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);
			
			
			//Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);	
			
			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA completed", "Failed - Not navigated to customer dashboard page after MTA completed");

			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"MTA transaction is completed successfully - Test case completed",
					"Failed - MTA transaction is not completed - Test case completed",
					driver, extentedReport, true);

		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} // catch
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
			Log.testCaseResult(extentedReport);

		} // finally
	}

 @Test(description = "Do MTA by changing 3 to 5 star and update data capture page", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void TC_003(String browser) throws Exception {
        // Get the web driver instance
        String tcId = "TC_003";
        final WebDriver driver = WebDriverFactory.get(browser);
        HashMap<String, String> testData = getTestData(tcId);

        String userName = testData.get("UserName");
        String password = testData.get("Password");
        String description = testData.get("Description");
        String Corecover = testData.get("Cover");
        String brandname = testData.get("Brand Name");
        Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

        ExtentTest extentedReport = addTestInfo(tcId, description);

        try {

            // Navigate to Login Page
            LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
            Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

            // Login to the application
            HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
            Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Failed - Login failed",
                    driver, extentedReport, true);
            Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
            // Click on Take Call link
            homePage.clickTakeCall(extentedReport);
            homePage.clickMyBrands(brandname, extentedReport);
            SearchPage searchPage = new SearchPage(driver, extentedReport).get();
            Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
                    "Search Page Verified", "Failed - Search Page not Verified", driver, extentedReport, true);

            // Enter Customer Details
            searchPage.clickCreateCustomer(true, extentedReport);
            HashMap<String, String> custDetails = searchPage.enterCustomerDetails(testData, true, extentedReport);

            // Confirm customer details and create customer
            CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
            
            Log.softAssertThat(
                    custdashboardpage.verifyContactName(testData.get("Title") + " " + custDetails.get("First Name")
                            + " " + custDetails.get("Last Name"), extentedReport, true),
                    "Verified FirstName and LastName on Customer Dashboard" + custDetails.get("First Name") + " "
                            + custDetails.get("Last Name"),
                    "Failed - Not Verified Customer Name on Dashboard", driver, extentedReport, true);

            // Create New Quote
            custdashboardpage.clickNewQuote(true, extentedReport);
            custdashboardpage.enterQuoteDetails(testData, true, extentedReport);

            NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

            Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
                    "Failed - Not verified NewQuotePage", driver, extentedReport, true);
            newquotepage.enterPolicyDetails(testData, true, extentedReport);
            newquotepage.clickNextOne(extentedReport);
            newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
            newquotepage.clickNextTwo(extentedReport);
            newquotepage.clickView(extentedReport);
            newquotepage.clickAgree(extentedReport);

            // Get and Buy Quote
            newquotepage.clickGetQuote(extentedReport);
            //newquotepage.verifyPremiumGenerated(extentedReport); //for Zero premium
            newquotepage.clickBuy(extentedReport);

            // Select Payment
            newquotepage.selectPayment(testData, true, extentedReport);
            custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
            Log.softAssertThat(
                    custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
                    "Navigated to customer dashboard page", "Failed - Not navigated to customer dashboard page", driver,
                    extentedReport, true);

            // check for policy status
            HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
            
            Log.softAssertThat(policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"),
                    "NB Policy Created in " + policyDetails.get("Status"),
                    "Failed - NB Policy Created in " + policyDetails.get("Status"), driver, extentedReport, true);

            testData.put("InceptionDate", policyDetails.get("InceptionDate"));
            testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
            
            //  MTA 
            custdashboardpage.clickManagePolicy(extentedReport);
            custdashboardpage.clickMidTermAdjustment(extentedReport);
            custdashboardpage.enterMTADetails(testData, extentedReport, true);
            
            Log.softAssertThat(
                    newquotepage.uielement.verifyPageElements(Arrays.asList("policystartDateatPricepresentationpage","policyEndDateatPricepresentationpage","adjustmentStartDateatPricepresentationpage","adjustmentStartDateatPricepresentationpage"), newquotepage),
                    "policy details verified at price presentation page", "Failed to display policy details at price presentation page", driver,
                    extentedReport, true);
            
               
            HashMap<String, String> financialDetailBefore = newquotepage.getFinancialsDetailFromPricePresentation(extentedReport, true);
            String beforeNewPolicyTermPremium = financialDetailBefore.get("NewPolicyTermPremium").toString();
            
            
            // For contents
            // get Contents default excess value
			
			boolean BoolVal = false;
			String[] coversToAdd = testData.get("coverToSelect").toString().split(",");

			// get Contents default excess value
			String[] contents = coversToAdd[0].split("_");
			
				
			// Click the checkbox
			String ins_RowtoInteract = newquotepage.SelectInsuranceItem(coversToAdd[0], true, extentedReport);
			newquotepage.clickAccidentalCheckbox("Contents");
			
			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(
							Arrays.asList("lnkLimitsInContents", "drpContentExcess"), newquotepage),
					"Content cover popup contains Volunatry Excess and limits",
					"Failed - Content cover popup does not contains Volunatry Excess and limits", driver, extentedReport, true);
			
			BoolVal = newquotepage.enterInsCoverDetails(testData, coversToAdd[0], ins_RowtoInteract, true,
					extentedReport);

			Log.softAssertThat(BoolVal, "Excess Value is entered for cover type : " + contents[0],
					"Excess Value is entered for cover type : " + contents[0], driver, extentedReport, true);
			
			
			
			// Click recalculate button
            newquotepage.clickReCalculate(extentedReport);
            HashMap<String, String> financialDetailAfterRemove = newquotepage
                    .getFinancialsDetailFromPricePresentation(extentedReport, true);
            String NewPolicyTermPremium = financialDetailAfterRemove.get("NewPolicyTermPremium").toString();
            Log.message("Old Premium : " + beforeNewPolicyTermPremium + " ,New Premium :" + NewPolicyTermPremium, extentedReport);

            Log.softAssertThat(
                    newquotepage.verifyNewPolicyTermPremiumChange(beforeNewPolicyTermPremium,
                            NewPolicyTermPremium, "Increased"),
                    "Premium got incresed after adding the cover : " + contents[0],
                    "Failed - Premium not increased after adding the cover : " + contents[0], driver, extentedReport, true);
			
            beforeNewPolicyTermPremium=NewPolicyTermPremium;

		      // For Buildings
            				
			boolean BoolVal1 = false;
			String[] coversToAdd1 = testData.get("coverToSelect").toString().split(",");
			
			// get Contents default excess value
			String[] contents1 = coversToAdd[1].split("_");
		
			
			// Click the checkbox
			String ins_RowtoInteract1 = newquotepage.SelectInsuranceItem(coversToAdd1[1], true, extentedReport);
			newquotepage.clickAccidentalCheckbox("Buildings");

			Log.softAssertThat(
					newquotepage.uielement.verifyPageElements(
							Arrays.asList("lnkLimitsInBuildings", "drpBuildingExcess"), newquotepage),
					"Building cover popup contains Volunatry Excess and limits",
					"Failed - Building cover popup does not contains Volunatry Excess and limits", driver,
					extentedReport, true);

			BoolVal1 = newquotepage.enterInsCoverDetails(testData, coversToAdd1[1], ins_RowtoInteract1, true,
					extentedReport);

			Log.softAssertThat(BoolVal1, "Excess Value is entered for cover type : " + contents1[0],
					"Excess Value is entered for cover type : " + contents1[0], driver, extentedReport, true);

			// Click recalculate button
			newquotepage.clickReCalculate(extentedReport);
			HashMap<String, String> financialDetailAfterRemove1 = newquotepage
					.getFinancialsDetailFromPricePresentation(extentedReport, true);
			String NewPolicyTermPremium1 = financialDetailAfterRemove1.get("NewPolicyTermPremium").toString();
			Log.message("Old Premium : " + beforeNewPolicyTermPremium + " ,New Premium :" + NewPolicyTermPremium1,
					extentedReport);

			Log.softAssertThat(
					newquotepage.verifyNewPolicyTermPremiumChange(beforeNewPolicyTermPremium, NewPolicyTermPremium1,
							"Increased"),
					"Premium got incresed after adding the cover : " + contents1[0],
					"Failed - Premium not increased after adding the cover : " + contents1[0], driver, extentedReport,
					true);

			beforeNewPolicyTermPremium = NewPolicyTermPremium;

		
			String[] coversToAdd2 = testData.get("coverToSelect_NB").toString().split(",");
			for (int i = 0; i < coversToAdd2.length; i++) {
				boolean BoolVal2 = false;
				String ins_RowtoInteract2 = newquotepage.SelectInsuranceItem(coversToAdd2[i], true, extentedReport);
				BoolVal2 = newquotepage.enterInsCoverDetails(testData, coversToAdd2[i], ins_RowtoInteract2, true,
						extentedReport);
				String[] coverType = coversToAdd2[i].split("_");
				if (BoolVal2 != false) {
					Log.pass(coverType[2] + " " + coverType[0] + "_" + coverType[1] + " cover type done successfully",
							driver, extentedReport, true);

					newquotepage.clickReCalculate(extentedReport);
					HashMap<String, String> financialDetailAfterRemove2 = newquotepage
							.getFinancialsDetailFromPricePresentation(extentedReport, true);
					String NewPolicyTermPremium2 = financialDetailAfterRemove2.get("NewPolicyTermPremium").toString();
					Log.message(
							"Old Premium : " + beforeNewPolicyTermPremium + " ,New Premium :" + NewPolicyTermPremium2,
							extentedReport);

					Log.softAssertThat(
							newquotepage.verifyNewPolicyTermPremiumChange(beforeNewPolicyTermPremium,
									NewPolicyTermPremium2, "Increased"),
							"Premium got incresed after adding the cover : " + contents1[0],
							"Failed - Premium not increased after adding the cover : " + contents1[0], driver,
							extentedReport, true);

				} else {
					Log.fail("Failed to " + coverType[2] + " " + coverType[0] + "_" + coverType[1] + " cover", driver,
							extentedReport, true);
				}
			}

			// Back to Datacapture page
			newquotepage.clickbacktoDatacapture(extentedReport);
			newquotepage.verifyDataCapturePage(testData, true, extentedReport);

			// Navigate to Price presentation page
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enter_PIDetailsFormoreThan1500(testData, true);
			newquotepage.enter_PedalBiCycleDetailsFormoreThan1500(testData, true);
			newquotepage.enter_HighRiskDetailsFormoreThan1500(testData, true);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			newquotepage.clickGetQuote(extentedReport);

			// Change unspecified item value to 5000

			String[] coversToAdd3 = testData.get("coverToSelect").toString().split(",");

			newquotepage.SelectInsuranceItem(coversToAdd3[2], true, extentedReport);
			newquotepage.enterCoverRequiredForPersonalItems(testData.get("PI_Requiredcover").toString());
			newquotepage.clickSaveBtnInPersonalItems();

			// Click recalculate button
			newquotepage.clickReCalculate(extentedReport);
			newquotepage.Scheme = "ASDA";
		
			newquotepage.upgradePolicy(extentedReport);
			newquotepage.clickReCalculate(extentedReport);
			// Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.clickbacktoPricePresentationpage(extentedReport);
			newquotepage.clickBuyMTA(extentedReport, true);
			// Click Confirm button
			// newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.enterTrasactionIdForSTA(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);

			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA completed",
					"Failed - Not navigated to customer dashboard page after MTA completed");

			// Check for MTA transaction status
			String expectedPosition = "MidTermAdjustment";
			String expectedTransactionStatus = "Active: Debit And Issue Policy";
			Log.softAssertThat(
					custdashboardpage.verifyTransactionStatus(expectedPosition, expectedTransactionStatus,
							extentedReport, true),
					"MTA transaction is completed successfully - Test case completed",
					"Failed - MTA transaction is not completed - Test case completed", driver, extentedReport, true);

        }
        // try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        } // catch
        finally {
            Log.endTestCase(extentedReport);
            driver.quit();
            Log.testCaseResult(extentedReport);

        } // finally
    }

}
