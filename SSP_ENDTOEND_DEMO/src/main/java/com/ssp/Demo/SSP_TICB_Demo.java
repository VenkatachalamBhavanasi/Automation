package com.ssp.Demo;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.ssp.data.WebjourneyDataDriver;
import com.ssp.pages.selfservice.CustomerDashBoardPage;
import com.ssp.pages.uxp.CustDashboardPage;
import com.ssp.pages.uxp.HomePage;
import com.ssp.pages.uxp.LoginPage;
import com.ssp.pages.uxp.NewQuotePage;
import com.ssp.pages.uxp.SearchPage;
import com.ssp.pages.webjourney.GetQuotePage;
import com.ssp.soapservices.SOAP_UI;
import com.ssp.support.*;
import com.ssp.testscripts.smoke.UXP_WJ;

@Listeners(EmailReport.class)
public class SSP_TICB_Demo extends BaseTest {
	private String webSite;
	public String monthlywebSite;
	public static String quoteNumber;
	
	
	
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

	@Test(description = "Create 3* annual in WJ - Store customer details in CustomerInfo desktop application - Verifying policy details in Self-service - Enabling credentials through SoapUI - Performing MTA to add additonal cover ", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
	public void TC_01 (String browser, Method method) throws Exception {
		
		
		final WebDriver driver = WebDriverFactory.get(browser);
		HashMap<String, String> testData = getTestData("TC_009");
		String description = testData.get("Description");
		String brandname = testData.get("Brand Name");
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
	    	
        //Loading the test data from excel using the test case id and class
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_SS_" + env;
        
        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData("tcHoodSS006", className);

        String customername = dataObject.title + " " + dataObject.firstname + " " + dataObject.lastname;
        
        Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
		
		ExtentTest extentedReport = addTestInfo("TC_01", description);				
        
        try {
        	
        	//Step-1: Create 3* annual policy in Webjourney
	    	UXP_WJ.createAnnualPolicy(driver, extentedReport, dataObject);
	    	quoteNumber  = UXP_WJ.quoteNumber;
	    	
	    	//Step-2: Storing customer details Customer Info Manager Free desktop application
	    	Log.message("Calling AutoIt scripts !!!", driver, extentedReport);
            Runtime.getRuntime().exec("D:\\Installed\\CustomerInfoManagerFree\\CustInfoManager.exe");
            Log.message("Storing customer data in to Desktop applicatin using AutoIt scripts",driver,extentedReport );
                           
            Runtime.getRuntime().exec("E:\\AutoIt\\Mandatoryfields.exe "+dataObject.firstname+" "+dataObject.lastname+" "+dataObject.postcode+" "+dataObject.phone+" "+dataObject.email+" "+webSite.split(",")[0]+"");   
            Log.message("customer details entered succesfully in to Desktop applicatin using AutoIt scripts",driver,extentedReport );
	      	        
            // Step-3: Navigate to Self Service Home Page
	    	dataObject.webSite = dataObject.webSite.replace("HomeInsurance", "SIaaS-SSC");
	        com.ssp.pages.selfservice.HomePage homePage = new com.ssp.pages.selfservice.HomePage(driver, dataObject.webSite).get();
	        Assert.assertEquals(homePage.verifyHomePage(), true, "Self Service Home Page is not loaded");
            Log.message("Self Service Home Page Loaded successfully!!", driver, extentedReport);
       
            // Step-4: Click on Sign In Link
            com.ssp.pages.selfservice.LoginPage loginPage = homePage.clickSignIn();
            Log.message("Clicked on SignIn Link!!", extentedReport);

            // Step-5: Verify Login Page
            Assert.assertEquals(loginPage.verifyLoginPage(), true);
            Log.message("Navigated to Login Page Successfully!!", driver, extentedReport);

            // Step-6: Enter email ID
            loginPage.enterEmailID(dataObject.email);
            Log.message("Entered Email id :" + dataObject.email, extentedReport);

            // Step-7: Enter password
            loginPage.enterPassword(dataObject.password);
            Log.message("Entered Password: " + dataObject.password, extentedReport);

            // Step-8: Click on Sign in button
            CustomerDashBoardPage customerPage = loginPage.clickSignIn();
            Assert.assertEquals(customerPage.verifyCustomerDashboardPage(customername), true, "Verifying Customer Dashboard is failed.");
           	Log.message("Logged into Self Service Application and Verified Name of the customer!", driver, extentedReport);
            
            //Verify the created quote does exist in the customer dashboard or not
           	Assert.assertEquals(customerPage.VerifyQuoteExists(quoteNumber), true, "Policy created in WebJourney does not exist in Self Service application.");
            Log.message("The recently created policy annual does exist in the Customer Dashboard", driver, extentedReport);

            // Step-9: Click on New Quote and Verify your details page
            GetQuotePage getquotepage = customerPage.clickNewQuote();
            getquotepage.verifyGetQuotePage();
            Log.message("Navigated to your details page successfully", extentedReport);

            // Step-10: Verify details on Your Details Page
            if (!getquotepage.verifyDetails(customername))
                Log.fail("Customer Details is not matching with the expected value", driver, extentedReport, true);
            else
            	Log.pass("Created annual policy in WJ and Verified the presence on SS in detail successfully", driver, extentedReport, true);            

            // Step-11: Enable credentials through SoapUI
            SOAP_UI ui = new SOAP_UI(); 
            Log.message("Calling SOAP services", driver, extentedReport);
			String URN = ui.createPersonThruSOAPCall(soapProjectLoc, soapSuiteName, soapTestCaseName, soapTSNewP,foreName, surName, email, soapen, extentedReport); 
		    String userName = ui.saveSystemUserThroughSOAPCall(soapProjectLoc, soapSuiteName, soapTestCaseName, soapTSSystemUser,URN, pricipalId,soapen, extentedReport); 
		    String password = "Passw0rd";
		    
		    Log.message("Credentials created through SOAPUI- "+"UserName: " +URN +" Password: " +password, driver, extentedReport );
 
            ui.saveUnderwriterThroughSOAPCall(soapProjectLoc, soapSuiteName, soapTestCaseName, soapTSUnderWritter,URN,soapen, extentedReport); 
			//Step-12:  Navigate to Login Page
            dataObject.webSite = dataObject.webSite.replace("SIaaS-SSC", "SIaaS");
			LoginPage ECloginPage = new LoginPage(driver, dataObject.webSite, extentedReport).get();
			Log.message("Engagement Centre Landing Page : " + dataObject.webSite, driver, extentedReport, true);

			//Step-13: Login to the application
			HomePage EChomePage = ECloginPage.loginToSSP(userName, password, false, extentedReport);
			Log.message("Successfully logged into UXP Home Page",driver, extentedReport, true);
			Log.message("Logged in User id:" + userName + "& Password:" + password, driver, extentedReport);
			
			//step-14: Enter main customer Details
			EChomePage.clickTakeCall(extentedReport);
			EChomePage.clickMyBrands(brandname, extentedReport);
			SearchPage searchPage = new SearchPage(driver, extentedReport).get(); // added this line for demo
			searchPage.searchValidPolicy(quoteNumber, true, extentedReport);
			
			//Step-15: SearchPage.searchValidPolicy(quoteNumber, true, extentedReport);
			CustDashboardPage custdashboardpage = searchPage.selectPolicy_from_SearchPage(true, extentedReport);
			
			// Verifying Customer dashboard page
			custdashboardpage.clickPassVerification(extentedReport, true);
			custdashboardpage.verifyCustomerDashboardPage();

			
			// Select MTA
			custdashboardpage.clickManagePolicy(extentedReport);
			custdashboardpage.clickMidTermAdjustment(extentedReport);
			custdashboardpage.enterDateForMTA(extentedReport);
			custdashboardpage.selectMidTermAdjReason(testData.get("MTAdjReason").toString(), extentedReport);
			custdashboardpage.clickMidTermContinue(extentedReport);
			
			NewQuotePage newquotepage = new NewQuotePage(driver,extentedReport);
			
			//Adding HE cover to the policy
			newquotepage.addHECover(driver);
			//Click Buy button
			newquotepage.clickBuyMTA(extentedReport, true);	
			
			// Click Confirm button
			newquotepage.selectMTAReasonPayment(extentedReport);
			newquotepage.enterTrasactionIdForMTA(extentedReport);
			newquotepage.clickConfirmMTA(extentedReport);
			
			Log.softAssertThat(
					custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"), custdashboardpage),
					"Navigated to customer dashboard page after MTA is completed",
					"Failed - Not navigated to customer dashboard page after MTA is completed", driver, extentedReport,
					true);
			
			HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
			if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {
				Log.pass("MTA performed succesfully on NB accepted policy", driver, extentedReport, true);
			} else {
				Log.fail("MTA not performed succesfully on NB accepted policy " + policyDetails.get("Status").toString() + " status",
						driver, extentedReport, true);
			}
			
        }// try
        catch (AssertionError e) {
        	Log.exception(e, driver, extentedReport);        	
        }// catch
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        
        finally {
        	driver.quit();
        	Log.endTestCase(extentedReport);
            Log.testCaseResult();            
        }// finally


	 }
}

	
	
