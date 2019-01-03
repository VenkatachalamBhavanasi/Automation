package com.ssp.testscripts.regression;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.ssp.pages.uxp.CardDetailsPage;
import com.ssp.pages.uxp.ComplaintPage;
import com.ssp.pages.uxp.CustDashboardPage;
import com.ssp.pages.uxp.HomePage;
import com.ssp.pages.uxp.LoginPage;
import com.ssp.pages.uxp.NewQuotePage;
import com.ssp.pages.uxp.SearchPage;
import com.ssp.support.*;

@Listeners(EmailReport.class)
public class UXP_EC_1721 extends BaseTest {

	private String webSite;
	
	@BeforeMethod(alwaysRun = true)
	public void init(ITestContext context) throws IOException {
		webSite = System.getProperty("webSite") != null ? System.getProperty("webSite")
				: context.getCurrentXmlTest().getParameter("webSite");

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
				testCaseId + " - " + testDesc + " [" + browserwithos + "]", test, "UXP-EC-ASDA");
	}

	public HashMap<String, String> getTestData(String testcaseId) {
		String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
		String className = "UXP_EC_1721_" + env;
		return DataUtils.testDatabyID(testcaseId, className);
	}

	@Test(description = "Check that the banner title text as 'Engagement Centre' and NOT as 'Contact Centre'", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_439(String browser) throws Exception {
		// Get the web driver instance
		String tcId = "TC_439";
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData(tcId);

		String userName = testData.get("UserName");
		String password = testData.get("Password");
		String description = testData.get("Description");
		String brandname = testData.get("Brand Name");
	    Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		ExtentTest extentedReport = addTestInfo(tcId, description);
		String Corecover = testData.get("Cover");
		try {
			// Navigate to Login Page
			LoginPage loginPage = new LoginPage(driver, webSite,extentedReport).get();
			Log.pass("UXP Contact Centre Landing Page : " + webSite, driver, extentedReport, true);

			// Login to the application
			HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Login failed",
					driver, extentedReport, true);
			Log.message("Logged into UXP with User id:" + userName + "& Password:" + password, driver, extentedReport);

			Log.softAssertThat(homePage.verifyECbannertitle(extentedReport),
					"home page has the banner title - Engagement Centre",
					"Home page failed to have banner title as Engagement Centre", driver, extentedReport, true);

			// Click on Take Call link
			homePage.clickTakeCall(extentedReport);
			homePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get();
			Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
					"Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

			Log.softAssertThat(searchPage.ECbannertitleCheck(extentedReport),
					"Search Page has the banner title as Engagement Centre",
					"Search page failed to have banner title as Engagement Centre", driver, extentedReport, true);

			
			// Enter Customer Details
			searchPage.clickCreateCustomer(true, extentedReport);
			searchPage.enterCustomerDetails(testData, true, extentedReport);

			
			// Confirm customer details and create customer
			CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"custdashboardpage Verified", "custdashboardpage not Verified", driver, extentedReport, true);
			
			Log.softAssertThat(custdashboardpage.ECbannertitleCheck(extentedReport),
					"Customer dashboard Page has the banner title as Engagement Centre",
					"customer dashboard page failed to have banner title as Engagement Centre", driver, extentedReport,
					true);

			
			// Create New Quote
			custdashboardpage.clickNewQuote(true, extentedReport);
			custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
			NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

			// Verify New Quote
			Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport), "Verified NewQuotePage ",
					"Not verified NewQuotePage", driver, extentedReport, true);

			Log.softAssertThat(newquotepage.ECbannertitleCheck(extentedReport),
					"New Quote Page has the banner title as Engagement Centre",
					"New Quote page failed to have banner title as Engagement Centre", driver, extentedReport, true);
            //Enter policy details
			newquotepage.enterPolicyDetails(testData, true, extentedReport);
			Log.softAssertThat(newquotepage.ECbannertitleCheck(extentedReport),
					"Data capture page has the banner title as Engagement Centre",
					"Data capture page failed to have banner title as Engagement Centre", driver, extentedReport, true);
					
			newquotepage.clickNextOne(extentedReport);
			newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
			newquotepage.clickNextTwo(extentedReport);
			newquotepage.clickView(extentedReport);
			newquotepage.clickAgree(extentedReport);
			
	     	// Get and Buy Quote
			newquotepage.clickGetQuote(extentedReport);
			Log.softAssertThat(newquotepage.ECbannertitleCheck(extentedReport),
					"Price presentation page has the banner title as Engagement Centre",
					"Price presentation page failed to have banner title as Engagement Centre", driver, extentedReport, true);
					
			 newquotepage.clickBuy(extentedReport);
			
			// Select Payment type
			newquotepage.selectPayment(testData, true, extentedReport);
			Log.softAssertThat(newquotepage.ECbannertitleCheck(extentedReport),
					"Payment Page has the banner title as Engagement Centre",
					"Payment page failed to have banner title as Engagement Centre", driver, extentedReport, true);
			
			newquotepage.takePayment(extentedReport);
			CardDetailsPage carddetailspage = newquotepage.selectVisacard(extentedReport);

			Log.pass("Selected VISA Card", driver, extentedReport, true);

			//Enter Card Details
			carddetailspage.enterCardNumber(testData,extentedReport, true);
			carddetailspage.selectExpiry(testData,extentedReport, true);
			carddetailspage.enterVerification(extentedReport,true);
			carddetailspage.enterName(testData,extentedReport,true);
			carddetailspage.clickbuy(extentedReport,true);
			Log.pass("Entered Card Details and Navigated to Verificaion page", driver, extentedReport, true);

			// Click continue button
			newquotepage = carddetailspage.clickContinueButton(extentedReport);
			
			//Verify payment transaction
			Log.softAssertThat(newquotepage.verifyPaymentTrasaction(extentedReport), "Payment was successful",
					"Payment was not successful", driver, extentedReport, true);

			//Confirm payment
			custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Customer Dashboard page verified", "Customer Dashboard page not verified", driver,
					extentedReport, true);

			custdashboardpage.ECbannertitleCheck(extentedReport);

			//check for policy status
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").equalsIgnoreCase("Accepted")) {

				Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);
					
				
				//MTA	
				
				custdashboardpage.clickfromManagePolicyDropdown(testData, true, extentedReport);
				Log.pass(" Clicked on MTA menu successfully", driver, extentedReport, true);
				// Fill up MTa data
				custdashboardpage.enterMTADetails(testData, extentedReport, true);

				Log.pass("Entered Quote Details and Clicked on Confirm", driver, extentedReport, true);
				NewQuotePage NewQuotePage = new NewQuotePage(driver, extentedReport);
				NewQuotePage.verifyNewQuotePage(false, extentedReport);
				Log.pass("Get quote page loaded successfully", driver, extentedReport, true);
				Log.softAssertThat(NewQuotePage.ECbannertitleCheck(extentedReport),
						"Get Quote Page has the banner title as Engagement Centre",
						"Get Quote page failed to have banner title as Engagement Centre", driver, extentedReport, true);
				
				// Loop to add items to the existing ins cover
				String[] coversToAdd = testData.get("coverToSelect").toString().split(",");

				for (int i = 0; i < coversToAdd.length; i++) {
					boolean BoolVal = false;
					String ins_RowtoInteract = NewQuotePage.SelectInsuranceItem(coversToAdd[i], true, extentedReport);
					BoolVal = NewQuotePage.enterInsCoverDetails(testData, coversToAdd[i], ins_RowtoInteract, true,
							extentedReport);
					String[] coverType = coversToAdd[i].split("_");
					if (BoolVal != false) {
						Log.pass(coverType[2] + " " + coverType[0] + "_" + coverType[1]
								+ " cover type done successfully", driver, extentedReport, true);
					} else {
						Log.fail("Failed to " + coverType[2] + " " + coverType[0] + "_" + coverType[1] + " cover",
								driver, extentedReport, true);
					}

				}

				// Click on recalculate
				NewQuotePage.clickReCalculate(extentedReport);
				Log.message("Clicked on Re-Calculate", driver, extentedReport);

				// Click on buy button
				NewQuotePage.clickBuy(extentedReport);
				Log.message("Clicked on Buy Quote", driver, extentedReport);
			

				NewQuotePage.selectMTAReasonPayment(extentedReport);

				// Select card type
				NewQuotePage.takePayment(extentedReport);
				carddetailspage = NewQuotePage.selectVisacard(extentedReport);
				Log.pass("Selected VISA Card", driver, extentedReport, true);

				// Enter Card Details
				carddetailspage.enterCardNumber(testData,extentedReport, true);
				carddetailspage.selectExpiry(testData,extentedReport, true);
				carddetailspage.enterVerification(extentedReport,true);
				carddetailspage.enterName(testData,extentedReport,true);
				carddetailspage.clickbuy(extentedReport,true);
				Log.pass("Entered Card Details and Navigated to Verificaion page", driver, extentedReport, true);

				// Click continue button
				NewQuotePage = carddetailspage.clickContinueButton_getQuote(extentedReport);
				Log.softAssertThat(NewQuotePage.verifyPaymentTrasaction(extentedReport), "Payment was successful",
						"Payment was not successful", driver, extentedReport, true);
				
				
				Log.softAssertThat(NewQuotePage.ECbannertitleCheck(extentedReport),
						"Payment Page has the banner title as Engagement Centre",
						"Payment page failed to have banner title as Engagement Centre", driver, extentedReport, true);

				custdashboardpage = NewQuotePage.confirmPayment("MTA",testData, extentedReport);
				Log.softAssertThat(custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
								custdashboardpage),
						"custdashboardpage Verified", "custdashboardpage not Verified", driver, extentedReport, true);

				// check for policy status
				policyDetails = custdashboardpage.getPolicyURN(extentedReport);
				
				Log.softAssertThat(NewQuotePage.ECbannertitleCheck(extentedReport),
						"Customer dashboard Page has the banner title as Engagement Centre",
						"Customer dashboard page failed to have banner title as Engagement Centre", driver, extentedReport, true);
				
				if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

					Log.pass("MTA Policy Created Successfully in accepted status", driver, extentedReport, true);
					
				} else {
					Log.fail("Failed to create MTA policy in accepted status,it stands in following status:"
							+ policyDetails.get("Status").toString() + " status", driver, extentedReport, true);
				}

		//STA
				custdashboardpage.selectSTAfromManagePolicyTab(extentedReport, true);
				custdashboardpage.enterSTADetails(testData, extentedReport, true);
				NewQuotePage = custdashboardpage.verifyNewQuotePage(extentedReport);
				
				Log.softAssertThat(NewQuotePage.ECbannertitleCheck(extentedReport),
						"Get Quote Page has the banner title as Engagement Centre during STA transaction",
						"Get Quote page failed to have banner title as Engagement Centre during STA transaction", driver, extentedReport, true);
				NewQuotePage.clickBuy(extentedReport);
				Log.message("Clicked on Buy Quote", driver, extentedReport);
				NewQuotePage.selectMTAReasonPayment(extentedReport);
				
				Log.softAssertThat(NewQuotePage.ECbannertitleCheck(extentedReport),
						"Payment Page has the banner title as Engagement Centre",
						"Payment page failed to have banner title as Engagement Centre", driver, extentedReport, true);

				custdashboardpage = NewQuotePage.confirmPayment("STA",testData, extentedReport);
				
				Log.softAssertThat(
						custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
								custdashboardpage),
						"custdashboardpage Verified", "custdashboardpage not Verified", driver, extentedReport, true);
                 policyDetails = custdashboardpage.getPolicyURN(extentedReport);
				
				Log.softAssertThat(NewQuotePage.ECbannertitleCheck(extentedReport),
						"Customer dashboard Page has the banner title as Engagement Centre",
						"Customer dashboard page failed to have banner title as Engagement Centre", driver, extentedReport, true);
				
				if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted") && policyDetails.get("Position").toString().equalsIgnoreCase("ShortTermAdjustment ")) {

					Log.pass("STA Policy Created Successfully in accepted status", driver, extentedReport, true);
					
				} else {
					Log.fail("Failed to create STA policy in accepted status,it stands in following status:"
							+ policyDetails.get("Status").toString() + " status, Position :"+ policyDetails.get("Position").toString(), driver, extentedReport, true);
				}
				
			//complaints page
		
				ComplaintPage complaintPage = custdashboardpage.selectNewcomplaint_ComplaintsTab(extentedReport, true);
				Log.softAssertThat(complaintPage.ECbannertitleCheck(extentedReport),
						"Complaint Page has the banner title as Engagement Centre",
						"Complaint page failed to have banner title as Engagement Centre", driver, extentedReport, true);
				
				
			} else {
				Log.fail("NB Policy has failed to Create in Accepted status, unable to procced with MTA", driver,
						extentedReport, true);

			} // Perform MTA

			Log.testCaseResult(extentedReport);
		} // try
		catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		}
		
		finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}


}
