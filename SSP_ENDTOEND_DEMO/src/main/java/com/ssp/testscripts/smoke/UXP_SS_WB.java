package com.ssp.testscripts.smoke;


import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

//import com.eviware.soapui.support.SoapUIException;
import com.relevantcodes.extentreports.ExtentTest;
import com.ssp.data.WebjourneyDataDriver;
import com.ssp.pages.selfservice.CustomerDashBoardPage;
import com.ssp.pages.selfservice.HomePage;
import com.ssp.pages.selfservice.LoginPage;
import com.ssp.pages.webjourney.GetQuotePage;
import com.ssp.pages.webjourney.YourQuotePage;
import com.ssp.support.BaseTest;
import com.ssp.support.DataProviderUtils;
import com.ssp.support.DataUtils;
import com.ssp.support.EmailReport;
import com.ssp.support.Log;
import com.ssp.support.WebDriverFactory;
import com.ssp.workflows.webjourney.webjourneyWorkflow;
import com.ssp.testscripts.smoke.UXP_WJ;;

@Listeners(EmailReport.class)
public class UXP_SS_WB extends BaseTest {
    private String webSite;
	public String monthlywebSite;

    @BeforeTest
    public void init(ITestContext context) throws IOException {
        webSite = System.getProperty("webSite") != null ? System.getProperty("webSite") : context.getCurrentXmlTest().getParameter("webSite");
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
				testCaseId + " - " + testDesc + " [" + browserwithos + "]", test, "UXP-SS-WB");
	}

	public HashMap<String, String> getTestData(String testcaseId) {
		String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
		String className = "UXP_SS_WB_" + env;
		return DataUtils.testDatabyID(testcaseId, className);
	}

    @Test(description = "Login to UXP Self Service", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodSS001(String browser) throws Exception {
    	String tcId = "tcHoodSS001";
        final WebDriver driver = WebDriverFactory.get(browser);
        HashMap<String, String> testData = getTestData(tcId);
      
       
        String description = testData.get("Description").toString();
        String username = testData.get("UserName").toString();
        String password = testData.get("Password").toString();
        String customername = testData.get("Customer Name").toString();
        Log.testCaseInfo("UXP_EC_SMOKE_TC_001" + description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);
          
         try {

            // Step-1: Navigate to Webjourney Home Page
            HomePage homePage = new HomePage(driver, webSite).get();
            if (!homePage.verifyHomePage()) {
                Log.fail("Self Serve Home Page is not loaded", driver, extentedReport, true);
            } else {
                Log.pass("Step-1: Self Service Home Page Loaded successfully!!", driver, extentedReport, true);
            }

            // Step-2: Click on Sign In Link
            LoginPage loginPage = homePage.clickSignIn();
            Log.pass("Step-2: Clicked on SignIn Link!!", extentedReport);

            // Step-3: Verify Login Page
            loginPage.verifyLoginPage();
            Log.pass("Step-3: Navigated to Login Page Successfully!!", driver, extentedReport, true);

            // Step-4: Verify Login Page
            loginPage.enterEmailID(username);
            Log.pass("Step-4: Entered Email id : " + username, extentedReport);

            // Step-5: Verify Login Page
            loginPage.enterPassword(password);
            Log.pass("Step-5: Entered Password :" + password, extentedReport);

            // Step-6: Verify Login Page
            CustomerDashBoardPage customerPage = loginPage.clickSignIn();
            if (!customerPage.verifyCustomerDashboardPage(customername)) {
                Log.fail("Step 6: Customer Details is not matching with the expected value", driver, extentedReport, true);
            } else {
                Log.pass("Step-6: Logged into Self Service Application successfully", driver, extentedReport, true);
            }

        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
            Log.endTestCase(extentedReport);
            driver.quit();
            Log.testCaseResult();
        }// finally
    }

   @Test(description = "Login to UXP Self Service and Verify Customer Details", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodSS002(String browser) throws Exception {

        String tcId = "tcHoodSS002";
        final WebDriver driver = WebDriverFactory.get(browser);
        HashMap<String, String> testData = getTestData(tcId);
      
       
        String description = testData.get("Description").toString();
        String username = testData.get("UserName").toString();
        String password = testData.get("Password").toString();
        String customername = testData.get("Customer Name").toString();
        Log.testCaseInfo("UXP_EC_SMOKE_TC_002" + description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);

        
        try {

            // Step-1: Navigate to Webjourney Home Page
            HomePage homePage = new HomePage(driver, webSite).get();
            if (!homePage.verifyHomePage()) {
                Log.fail("Self Serve Home Page is not loaded", driver, extentedReport, true);
            } else {
                Log.pass("Step-1: Self Service Home Page Loaded successfully!!", driver, extentedReport, true);
            }

            // Step-2: Click on Sign In Link
            LoginPage loginPage = homePage.clickSignIn();
            Log.pass("Step-2: Clicked on SignIn Link!!", extentedReport);

            // Step-3: Verify Login Page
            Assert.assertEquals(loginPage.verifyLoginPage(), true);
            Log.pass("Step-3: Navigated to Login Page Successfully!!", driver, extentedReport, true);

            // Step-4: Verify Login Page
            loginPage.enterEmailID(username);
            Log.pass("Step-4: Entered Email id :" + username, extentedReport);

            // Step-5: Verify Login Page
            loginPage.enterPassword(password);
            Log.pass("Step-5: Entered Password: " + password, extentedReport);

            // Step-6: Verify Login Page
            CustomerDashBoardPage customerPage = loginPage.clickSignIn();
            if (!customerPage.verifyCustomerDashboardPage(customername)) {
                Log.fail("Step 6: Customer Details is not matching with the expected value", driver, extentedReport, true);
            } else {
                Log.pass("Step-6: Logged into Self Service Application and Verified Name of the customer!", driver, extentedReport, true);
            }

            // Step-7: Click on New Quote and Verify your details page
            GetQuotePage getquotepage = new GetQuotePage(driver);
            		customerPage.clickNewQuoteWB();
           
            customerPage.clickagreecheckBox();
            
            customerPage.startYourQuoteWB();
            getquotepage.verifyGetQuotePageWB();
            Log.pass("Step-7: Navigated to your details page successfully", extentedReport);

            // Step-8: Verify details on Your Details Page
            if (!getquotepage.verifyDetailsWB(customername)) {
                Log.fail("Step 6: Customer Details is not matching with the expected value", driver, extentedReport, true);
            } else {
                Log.pass("Step-7: Customer Details Verified!-- Test Case completed", driver, extentedReport, true);
            }

        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
            Log.endTestCase(extentedReport);
            driver.quit();
            Log.testCaseResult();
        }// finally

    }

    @Test(description = "Go to Continue Quote and Verify Quote", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodSS003(String browser) throws Exception {

        String tcId = "tcHoodSS003";
        final WebDriver driver = WebDriverFactory.get(browser);
        HashMap<String, String> testData = getTestData(tcId);
      
       
        String description = testData.get("Description").toString();
        String username = testData.get("UserName").toString();
        String password = testData.get("Password").toString();
        String customername = testData.get("Customer Name").toString();
        String quotenumber = testData.get("Quote Number").toString();
        Log.testCaseInfo("UXP_EC_SMOKE_TC_003" + description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, description);

       
        try {

            // Step-1: Navigate to Webjourney Home Page
            HomePage homePage = new HomePage(driver, webSite).get();
            if (!homePage.verifyHomePage()) {
                Log.fail("Self Serve Home Page is not loaded", driver, extentedReport, true);
            } else {
                Log.pass("Step-1: Self Service Home Page Loaded successfully!!", driver, extentedReport, true);
            }

            // Step-2: Click on Sign In Link
            LoginPage loginPage = homePage.clickSignIn();
            Log.pass("Step-2: Clicked on SignIn Link!!", extentedReport);

            // Step-3: Verify Login Page
            loginPage.verifyLoginPage();
            Log.pass("Step-3: Navigated to Login Page Successfully!!", driver, extentedReport, true);

            // Step-4: Verify Login Page
            loginPage.enterEmailID(username);
            Log.pass("Step-4: Entered Email id : " + username, extentedReport);

            // Step-5: Verify Login Page
            loginPage.enterPassword(password);
            Log.pass("Step-5: Entered Password :" + password, extentedReport);

            // Step-6: Verify Login Page
            CustomerDashBoardPage customerPage = loginPage.clickSignIn();
            if (!customerPage.verifyCustomerDashboardPage(customername)) {
                Log.fail("Step 6: Customer Details is not matching with the expected value", driver, extentedReport, true);
            } else {
                Log.pass("Step-6: Logged into Self Service Application successfully", driver, extentedReport, true);
            }

            // Step-7: Continue Quote
            YourQuotePage yourquotepage = customerPage.continueQuote(quotenumber);
            Log.pass("Step-7: Clicked on Continue Quote", extentedReport);

            // Step-8: Verify Your Quote Page
            if (!yourquotepage.verifyQuotePage()) {
                Log.fail("Step 8: Quote Number and Premium details are missing", driver, extentedReport, true);
            } else {
                Log.pass("Step-8: Verified Premium Value and Premium amount successfully", driver, extentedReport, true);
            }

            // Step-9: Verify Quote Details
            if (!yourquotepage.verifyQuoteNumber(quotenumber)) {
                Log.fail("Step 9: Quote Number is not matching with the expected", extentedReport);
            } else {
                Log.pass("Step-9: Verified Quote Number Successfully", extentedReport);
            }
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
            Log.endTestCase(extentedReport);
            // driver.quit();
        }// finally
    }

    @Test(description = "View Documents", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodSS004(String browser) throws Exception {

        final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_SS_" + env;
        HashMap<String, String> testData = DataUtils.testDatabyID("tcHoodSS004", className);
        String description = testData.get("Description").toString();
        String username = testData.get("UserName").toString();
        String password = testData.get("Password").toString();
        String customername = testData.get("Customer Name").toString();
        String policynumber = testData.get("Policy Number").toString();

        String test = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getName();

        String browserwithos = null;
        String test_type = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("type");
        if ("device".equalsIgnoreCase(test_type)) {
            String browsername = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
            String os = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("os").substring(0, 1);
            String device = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("device");
            browserwithos = os + "_" + browsername + "_" + device;
        } else {
            String browsername = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
            String browserversion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser_version");
            String os = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("os").substring(0, 1);
            String osversion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("os_version");
            browserwithos = os + osversion + "_" + browsername + browserversion;

        }
        ExtentTest extentedReport = Log.testCaseInfo("Self Service - tcHoodSS004" + "[" + test + "]", "SS - tcHoodSS004 - " + description + " [" + browserwithos + "]", test, "UXP_NFR Team");

        try {

            // Step-1: Navigate to Webjourney Home Page
            HomePage homePage = new HomePage(driver, webSite).get();
            if (!homePage.verifyHomePage()) {
                Log.fail("Self Serve Home Page is not loaded", driver, extentedReport, true);
            } else {
                Log.pass("Step-1: Self Service Home Page Loaded successfully!!", driver, extentedReport, true);
            }

            // Step-2: Click on Sign In Link
            LoginPage loginPage = homePage.clickSignIn();
            Log.pass("Step-2: Clicked on SignIn Link!!", extentedReport);

            // Step-3: Verify Login Page
            loginPage.verifyLoginPage();
            Log.pass("Step-3: Navigated to Login Page Successfully!!", driver, extentedReport, true);

            // Step-4: Verify Login Page
            loginPage.enterEmailID(username);
            Log.pass("Step-4: Entered Email id : " + username, extentedReport);

            // Step-5: Verify Login Page
            loginPage.enterPassword(password);
            Log.pass("Step-5: Entered Password :" + password, extentedReport);

            // Step-6: Verify Login Page
            CustomerDashBoardPage customerPage = loginPage.clickSignIn();
            if (!customerPage.verifyCustomerDashboardPage(customername)) {
                Log.fail("Step 6: Customer Details is not matching with the expected value", driver, extentedReport, true);
            } else {
                Log.pass("Step-6: Logged into Self Service Application successfully", driver, extentedReport, true);
            }

            // Step-7: Verify Login Page
            customerPage.clickViewDocument(policynumber);
            Log.pass("Step-7: Verified View Document Successfully", driver, extentedReport, true);

        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
            Log.endTestCase(extentedReport);
            // driver.quit();
        }// finally
    }
    
    @Test(description = "Create 3* PCL policy in WJ and verify the presence on SS in detail", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodSS005(String browser, Method method) throws Exception {

    	final WebDriver driver = WebDriverFactory.get(browser);
    	
        // Loading the test data from excel using the test case id and class
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_SS_" + env;
        
        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        dataObject.webSite = dataObject.webSite.replace("SIaaS-SSC", "HomeInsurance");
        String customername = dataObject.title + " " + dataObject.firstname + " " + dataObject.lastname;

        ExtentTest extentedReport = Log.testCaseInfo("Webjourney - " + dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				
        
        try {
    		
	    	//Step-1: Create 3* monthly policy in Webjourney
	    	UXP_WJ.createMonthlyPolicy(driver, extentedReport, dataObject);
    	
            // Step-2: Navigate to Self Service Home Page
	    	dataObject.webSite = dataObject.webSite.replace("HomeInsurance", "SIaaS-SSC");
	        HomePage homePage = new HomePage(driver, dataObject.webSite).get();
	        Assert.assertEquals(homePage.verifyHomePage(), true, "Self Service Home Page is not loaded");
            Log.message("Self Service Home Page Loaded successfully!!", driver, extentedReport);
            
            // Step-3: Click on Sign In Link
            LoginPage loginPage = homePage.clickSignIn();
            Log.message("Clicked on SignIn Link!!", extentedReport);

            // Step-4: Verify Login Page
            Assert.assertEquals(loginPage.verifyLoginPage(), true);
            Log.message("Navigated to Login Page Successfully!!", driver, extentedReport);

            // Step-5: Enter email ID
            loginPage.enterEmailID(dataObject.email);
            Log.message("Entered Email id :" + dataObject.email, extentedReport);

            // Step-6: Enter password
            loginPage.enterPassword(dataObject.password);
            Log.message("Entered Password: " + dataObject.password, extentedReport);

            // Step-7: Click on Sign in button
            CustomerDashBoardPage customerPage = loginPage.clickSignIn();
            Assert.assertEquals(customerPage.verifyCustomerDashboardPage(customername), true, "Verifying Customer Dashboard is failed.");
            Log.message("Logged into Self Service Application and Verified Name of the customer!", driver, extentedReport);
            
            //Verify the created quote does exist in the customer dashboard or not
            Assert.assertEquals(customerPage.VerifyQuoteExists(UXP_WJ.quoteNumber), true, "Policy created in WebJourney does not exist in Self Service application.");
            Log.message("The recently created policy does exist in the Customer Dashboard", driver, extentedReport);

            Assert.assertEquals(customerPage.VerifyDocuments(UXP_WJ.quoteNumber), true, "Customer Details is not matching with the expected value.");
            Log.message("The document validation for created policy is done.", driver, extentedReport);

            // Step-8: Click on New Quote and Verify your details page
            GetQuotePage getquotepage = customerPage.clickNewQuote();
            getquotepage.verifyGetQuotePage();
            Log.message("Navigated to your details page successfully", extentedReport);

            // Step-9: Verify details on Your Details Page
            if (!getquotepage.verifyDetails(customername))
                Log.fail("Customer Details is not matching with the expected value", driver, extentedReport, true);
            else
            	Log.pass("Created PCL policy in WJ and Verified the presence on SS in detail successfully", driver, extentedReport, true);            

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

    @Test(description = "Create 3* annual in WJ and verify the presence on SS in detail", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodSS006(String browser, Method method) throws Exception {

    	final WebDriver driver = WebDriverFactory.get(browser);
    	
        // Loading the test data from excel using the test case id and class
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_SS_" + env;
        
        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        dataObject.webSite = dataObject.webSite.replace("SIaaS-SSC", "HomeInsurance");
        String customername = dataObject.title + " " + dataObject.firstname + " " + dataObject.lastname;

        ExtentTest extentedReport = Log.testCaseInfo("Webjourney - " + dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				
        
        try {
    		
	    	//Step-1: Create 3* annual policy in Webjourney
	    	UXP_WJ.createAnnualPolicy(driver, extentedReport, dataObject);
    	
            // Step-2: Navigate to Self Service Home Page
	    	dataObject.webSite = dataObject.webSite.replace("HomeInsurance", "SIaaS-SSC");
	        HomePage homePage = new HomePage(driver, dataObject.webSite).get();
	        Assert.assertEquals(homePage.verifyHomePage(), true, "Self Service Home Page is not loaded");
            Log.message("Self Service Home Page Loaded successfully!!", driver, extentedReport);
            
            // Step-3: Click on Sign In Link
            LoginPage loginPage = homePage.clickSignIn();
            Log.message("Clicked on SignIn Link!!", extentedReport);

            // Step-4: Verify Login Page
            Assert.assertEquals(loginPage.verifyLoginPage(), true);
            Log.message("Navigated to Login Page Successfully!!", driver, extentedReport);

            // Step-5: Enter email ID
            loginPage.enterEmailID(dataObject.email);
            Log.message("Entered Email id :" + dataObject.email, extentedReport);

            // Step-6: Enter password
            loginPage.enterPassword(dataObject.password);
            Log.message("Entered Password: " + dataObject.password, extentedReport);

            // Step-7: Click on Sign in button
            CustomerDashBoardPage customerPage = loginPage.clickSignIn();
            Assert.assertEquals(customerPage.verifyCustomerDashboardPage(customername), true, "Verifying Customer Dashboard is failed.");
           	Log.message("Logged into Self Service Application and Verified Name of the customer!", driver, extentedReport);
            
            //Verify the created quote does exist in the customer dashboard or not
           	Assert.assertEquals(customerPage.VerifyQuoteExists(UXP_WJ.quoteNumber), true, "Policy created in WebJourney does not exist in Self Service application.");
            Log.message("The recently created policy annual does exist in the Customer Dashboard", driver, extentedReport);

            Assert.assertEquals(customerPage.VerifyDocuments(UXP_WJ.quoteNumber), true, "The document validation for created annual policy is failed.");
            Log.message("The document validation for created policy is done.", driver, extentedReport);

            // Step-8: Click on New Quote and Verify your details page
            GetQuotePage getquotepage = customerPage.clickNewQuote();
            getquotepage.verifyGetQuotePage();
            Log.message("Navigated to your details page successfully", extentedReport);

            // Step-9: Verify details on Your Details Page
            if (!getquotepage.verifyDetails(customername))
                Log.fail("Customer Details is not matching with the expected value", driver, extentedReport, true);
            else
            	Log.pass("Created annual policy in WJ and Verified the presence on SS in detail successfully", driver, extentedReport, true);            

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

    @Test(description = "Save a quote in WJ, Do Continue the same Quote in SS and validate in WJ then", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodSS007(String browser, Method method) throws Exception {

    	final WebDriver driver = WebDriverFactory.get(browser);
    	
        // Loading the test data from excel using the test case id and class
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_SS_" + env;
        
        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        dataObject.webSite = dataObject.webSite.replace("SIaaS-SSC", "HomeInsurance");
        String customername = dataObject.title + " " + dataObject.firstname + " " + dataObject.lastname;

        ExtentTest extentedReport = Log.testCaseInfo("Webjourney - " + dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				
        
        try {
    		
        	// Step-1:Initialize workflow
        	webjourneyWorkflow policyCreate = new webjourneyWorkflow(driver, extentedReport);

        	// Step-2:Initialize test data for workflow
        	policyCreate._dataObject = dataObject;
        	
        	// Step-3:Enter data in the Quote details page
        	policyCreate.EnterQuoteDetails();

        	// Step-4:Submit the Quote
        	policyCreate.SubmitQuote();

        	// Step-5:Save the created Quote
        	policyCreate.SaveQuote();

        	// Step-6:Enter User info for save Quote
        	policyCreate.EnterUserInfoSaveQuote();

        	// Step-7:Save User info for save Quote
        	policyCreate.SubmitUserInfoSaveQuote();
        	
        	// Step-8:Verify the quote is Saved or not
        	UXP_WJ.quoteNumber = policyCreate.VerifyQuoteSaved();     
        	  
            // Step-9: Navigate to Self Service Home Page
	    	dataObject.webSite = dataObject.webSite.replace("HomeInsurance", "SIaaS-SSC");
	        HomePage homePage = new HomePage(driver, dataObject.webSite).get();
	        Assert.assertEquals(homePage.verifyHomePage(), true, "Self Service Home Page is not loaded");
            Log.message("Self Service Home Page Loaded successfully!!", driver, extentedReport);
            
            // Step-3: Click on Sign In Link
            LoginPage loginPage = homePage.clickSignIn();
            Log.message("Clicked on SignIn Link!!", extentedReport);

            // Step-4: Verify Login Page
            Assert.assertEquals(loginPage.verifyLoginPage(), true);
            Log.message("Navigated to Login Page Successfully!!", driver, extentedReport);

            // Step-5: Enter email ID
            loginPage.enterEmailID(dataObject.email);
            Log.message("Entered Email id :" + dataObject.email, extentedReport);

            // Step-6: Enter password
            loginPage.enterPassword(dataObject.password);
            Log.message("Entered Password: " + dataObject.password, extentedReport);

            // Step-7: Click on Sign in button
            CustomerDashBoardPage customerPage = loginPage.clickSignIn();
            Assert.assertEquals(customerPage.verifyCustomerDashboardPage(customername), true, "Verifying Customer Dashboard is failed.");
            	
            Log.message("Logged into Self Service Application and Verified Name of the customer!", driver, extentedReport);
            
            //Verify the created quote does exist in the customer dashboard or not
            Assert.assertEquals(customerPage.VerifyQuoteExists(UXP_WJ.quoteNumber), true, "Policy created in WebJourney does not exist in Self Service application.");
                 
            Log.message("The recently created policy annual does exist in the Customer Dashboard", driver, extentedReport);

            Assert.assertEquals(customerPage.VerifyDocuments(UXP_WJ.quoteNumber), true, "The document validation for created annual policy is failed.");
            
            Log.message("The document validation for created policy is done.", driver, extentedReport);

            // Step-8: Continue Quote
            YourQuotePage yourquotepage = customerPage.continueQuote(UXP_WJ.quoteNumber);
            Log.message("Clicked on Continue Quote", extentedReport);

            // Step-9: Verify Your Quote Page
            Assert.assertEquals(yourquotepage.verifyQuotePage(), true, "The document validation for created annual policy is failed.");
            Log.message("Verified Premium Value and Premium amount successfully", driver, extentedReport, true);
            
            // Step-10: Verify Quote Details
            Assert.assertEquals(yourquotepage.verifyQuoteNumber(UXP_WJ.quoteNumber), true, "Quote Number is not matching with the expected");
            Log.pass("Saved a quote in WJ, Continued the same Quote in SS and Validated in WJ then Successfully", driver, extentedReport, true);
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

    @Test(description = "Create 5* PCL policy in WJ and verify the presence on SS in detail", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodSS008(String browser, Method method) throws Exception {

    	final WebDriver driver = WebDriverFactory.get(browser);
    	
        // Loading the test data from excel using the test case id and class
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_SS_" + env;
        
        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        dataObject.webSite = dataObject.webSite.replace("SIaaS-SSC", "HomeInsurance");
        String customername = dataObject.title + " " + dataObject.firstname + " " + dataObject.lastname;

        ExtentTest extentedReport = Log.testCaseInfo("Webjourney - " + dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				
        
        try {
    		
	    	//Step-1: Create 3* monthly policy in Webjourney
	    	UXP_WJ.createMonthlyPolicy(driver, extentedReport, dataObject);
    	
            // Step-2: Navigate to Self Service Home Page
	    	dataObject.webSite = dataObject.webSite.replace("HomeInsurance", "SIaaS-SSC");
	        HomePage homePage = new HomePage(driver, dataObject.webSite).get();
	        Assert.assertEquals(homePage.verifyHomePage(), true, "Self Service Home Page is not loaded");
            Log.message("Self Service Home Page Loaded successfully!!", driver, extentedReport);
            
            // Step-3: Click on Sign In Link
            LoginPage loginPage = homePage.clickSignIn();
            Log.message("Clicked on SignIn Link!!", extentedReport);

            // Step-4: Verify Login Page
            Assert.assertEquals(loginPage.verifyLoginPage(), true);
            Log.message("Navigated to Login Page Successfully!!", driver, extentedReport);

            // Step-5: Enter email ID
            loginPage.enterEmailID(dataObject.email);
            Log.message("Entered Email id :" + dataObject.email, extentedReport);

            // Step-6: Enter password
            loginPage.enterPassword(dataObject.password);
            Log.message("Entered Password: " + dataObject.password, extentedReport);

            // Step-7: Click on Sign in button
            CustomerDashBoardPage customerPage = loginPage.clickSignIn();
            Assert.assertEquals(customerPage.verifyCustomerDashboardPage(customername), true, "Verifying Customer Dashboard is failed.");
            Log.message("Logged into Self Service Application and Verified Name of the customer!", driver, extentedReport);
            
            //Verify the created quote does exist in the customer dashboard or not
            Assert.assertEquals(customerPage.VerifyQuoteExists(UXP_WJ.quoteNumber), true, "Policy created in WebJourney does not exist in Self Service application.");
            Log.message("The recently created policy does exist in the Customer Dashboard", driver, extentedReport);

            Assert.assertEquals(customerPage.VerifyDocuments(UXP_WJ.quoteNumber), true, "Customer Details is not matching with the expected value.");
            Log.message("The document validation for created policy is done.", driver, extentedReport);

            // Step-8: Click on New Quote and Verify your details page
            GetQuotePage getquotepage = customerPage.clickNewQuote();
            getquotepage.verifyGetQuotePage();
            Log.message("Navigated to your details page successfully", extentedReport);

            // Step-9: Verify details on Your Details Page
            if (!getquotepage.verifyDetails(customername))
                Log.fail("Customer Details is not matching with the expected value", driver, extentedReport, true);
            else
            	Log.pass("Created PCL policy in WJ and Verified the presence on SS in detail successfully", driver, extentedReport, true);            

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

    @Test(description = "Create 5* annual in WJ and verify the presence on SS in detail", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodSS009(String browser, Method method) throws Exception {

    	final WebDriver driver = WebDriverFactory.get(browser);
    	
        // Loading the test data from excel using the test case id and class
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_SS_" + env;
        
        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        dataObject.webSite = dataObject.webSite.replace("SIaaS-SSC", "HomeInsurance");
        String customername = dataObject.title + " " + dataObject.firstname + " " + dataObject.lastname;

        ExtentTest extentedReport = Log.testCaseInfo("Webjourney - " + dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				
        
        try {
    		
	    	//Step-1: Create 3* annual policy in Webjourney
	    	UXP_WJ.createAnnualPolicy(driver, extentedReport, dataObject);
    	
            // Step-2: Navigate to Self Service Home Page
	    	dataObject.webSite = dataObject.webSite.replace("HomeInsurance", "SIaaS-SSC");
	        HomePage homePage = new HomePage(driver, dataObject.webSite).get();
	        Assert.assertEquals(homePage.verifyHomePage(), true, "Self Service Home Page is not loaded");
            Log.message("Self Service Home Page Loaded successfully!!", driver, extentedReport);
            
            // Step-3: Click on Sign In Link
            LoginPage loginPage = homePage.clickSignIn();
            Log.message("Clicked on SignIn Link!!", extentedReport);

            // Step-4: Verify Login Page
            Assert.assertEquals(loginPage.verifyLoginPage(), true);
            Log.message("Navigated to Login Page Successfully!!", driver, extentedReport);

            // Step-5: Enter email ID
            loginPage.enterEmailID(dataObject.email);
            Log.message("Entered Email id :" + dataObject.email, extentedReport);

            // Step-6: Enter password
            loginPage.enterPassword(dataObject.password);
            Log.message("Entered Password: " + dataObject.password, extentedReport);

            // Step-7: Click on Sign in button
            CustomerDashBoardPage customerPage = loginPage.clickSignIn();
            Assert.assertEquals(customerPage.verifyCustomerDashboardPage(customername), true, "Verifying Customer Dashboard is failed.");
           	Log.message("Logged into Self Service Application and Verified Name of the customer!", driver, extentedReport);
            
            //Verify the created quote does exist in the customer dashboard or not
           	Assert.assertEquals(customerPage.VerifyQuoteExists(UXP_WJ.quoteNumber), true, "Policy created in WebJourney does not exist in Self Service application.");
            Log.message("The recently created policy annual does exist in the Customer Dashboard", driver, extentedReport);

            Assert.assertEquals(customerPage.VerifyDocuments(UXP_WJ.quoteNumber), true, "The document validation for created annual policy is failed.");
            Log.message("The document validation for created policy is done.", driver, extentedReport);

            // Step-8: Click on New Quote and Verify your details page
            GetQuotePage getquotepage = customerPage.clickNewQuote();
            getquotepage.verifyGetQuotePage();
            Log.message("Navigated to your details page successfully", extentedReport);

            // Step-9: Verify details on Your Details Page
            if (!getquotepage.verifyDetails(customername))
                Log.fail("Customer Details is not matching with the expected value", driver, extentedReport, true);
            else
            	Log.pass("Created annual policy in WJ and Verified the presence on SS in detail successfully", driver, extentedReport, true);            

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