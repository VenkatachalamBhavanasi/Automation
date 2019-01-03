package com.ssp.testscripts.smoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.xmlbeans.XmlException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.ssp.workflows.webjourney.webjourneyWorkflow;
//import com.eviware.soapui.support.SoapUIException;
import com.relevantcodes.extentreports.ExtentTest;
import com.ssp.data.WebjourneyDataDriver;
import com.ssp.pages.selfservice.HomePage;
import com.ssp.pages.uxp.CustDashboardPage;
import com.ssp.pages.uxp.LoginPage;
import com.ssp.pages.uxp.SearchPage;
import com.ssp.support.BaseTest;
import com.ssp.support.DataProviderUtils;
import com.ssp.support.EmailReport;
import com.ssp.support.Log;
import com.ssp.support.WebDriverFactory;


@Listeners(EmailReport.class)
public class UXP_WJ extends BaseTest {
    private String webSite;
    public static String quoteNumber;
    
    @BeforeTest
    public void init(ITestContext context) throws IOException, XmlException {
        webSite = System.getProperty("webSite") != null ? System.getProperty("webSite") : context.getCurrentXmlTest().getParameter("webSite");
    }

    @Test(description = "Create 3* policy in Webjourney - PCL and verify it in EC", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ001(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {
        	//Create 3* monthly policy
        	createMonthlyPolicy(driver, extentedReport, dataObject);
        	webSite=webSite.replace("HomeInsurance", "SIaaS");
        
          // Navigate to Login Page
 			LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
 			Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport);
 			
 			// Login to the application
 			com.ssp.pages.uxp.HomePage homePage = loginPage.loginToSSP("jayabalan", "Passw0rd", false, extentedReport);
 			Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page", "Login failed",
 					driver, extentedReport, true);
 			Log.message("Logged in User id:" + "jayabalan" + "& Password:" + "Passw0rd", driver, extentedReport);
        	
 		// Click on Take Call link
 					homePage.clickTakeCall(extentedReport);
 					homePage.clickMyBrands("ASDA", extentedReport);
 					SearchPage searchPage = new SearchPage(driver, extentedReport).get();
 					Log.softAssertThat(searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
 							"Search page is verified", "Search Page is not verified", driver, extentedReport, true);
 					searchPage.searchValidPolicy(quoteNumber, true, extentedReport);
 					CustDashboardPage custdashboardPage = searchPage.selectPolicy_from_SearchPage(true, extentedReport);
 					Log.message("Navigated to Cutomer Dashboard after selecting policy from search page", driver,
 							extentedReport);

 					// Verifying Customer Details
 					custdashboardPage.clickPassVerification(extentedReport, true);
 					custdashboardPage.verifyCustomerDashboardPage();

 					HashMap<String, String> policyDetails = custdashboardPage.getPolicyURN(extentedReport);
 					if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {

 						Log.message("New Business Policy Created Successfully in Accepted status in EC via WJ", driver, extentedReport);
 						policyDetails.get("PolicyNo");
 					}
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
        	Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);
	    
        }// finally

    }// tcCBTWJ001
    
    @Test(description = "Create 3* policy in Webjourney - Annual", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ002(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {
        	//Create 3* annual policy
        	createAnnualPolicy(driver, extentedReport, dataObject);
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
        	Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);
        
        }// finally

    }// tcHoodWJ002

    @Test(description = "Create 5* policy in Webjourney - PCL", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ003(String browser, Method method) throws Exception {

	final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {

        	//Create 5* monthly policy
        	createMonthlyPolicy(driver, extentedReport, dataObject);
        	  
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
        	Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);
        
        }// finally

    }// tcCBTWJ003
    
    @Test(description = "Create 5* policy in Webjourney - Annual", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ004(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {

        	//Create 5* annual policy
        	createAnnualPolicy(driver, extentedReport, dataObject);
        	
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
        	Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);
        
        }// finally

    }// tcHoodWJ004

    @Test(description = "Create 3 star annual policy with unspecified covers with promo code", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ005(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {
        	//Create 3* annual policy
        	createAnnualPolicy(driver, extentedReport, dataObject);
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
        	Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);
        
        }// finally

    }// tcHoodWJ005

    @Test(description = "To create 5 star annual policy with unspecified covers with NCD Value", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ006(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {
        	//Create 5* annual policy
        	createAnnualPolicy(driver, extentedReport, dataObject);
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
        	Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);            
        }// finally

    }// tcHoodWJ006

    @Test(description = "To create 3 star monthly policy with unspecified covers with NCD Value", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ007(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {

        	//Create 3* monthly policy
        	createMonthlyPolicy(driver, extentedReport, dataObject);
        	  
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
        	Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);
            	    
        }// finally

    }// tcCBTWJ007

    @Test(description = "To create 5 star monthly policy with unspecified covers with promo code", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ008(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {

        	//Create 5* monthly policy
        	createMonthlyPolicy(driver, extentedReport, dataObject);
        	  
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
        	Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);
            
        }// finally

    }// tcCBTWJ008

    @Test(description = "Create 3 star annual policy with unspecified covers with promo code", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ009(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {
        	//Create 3* annual policy
        	createAnnualPolicy(driver, extentedReport, dataObject);
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
            driver.quit();
	        Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);

        }// finally

    }// tcHoodWJ009

    @Test(description = "To create 5 star annual policy with unspecified and specified covers", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ010(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {
        	//Create 3* annual policy
        	createAnnualPolicy(driver, extentedReport, dataObject);
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
	        Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);
            
        }// finally

    }// tcHoodWJ010

    @Test(description = "To create 3 star monthly policy with unspecified and specified covers", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ011(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {

        	//Create 5* monthly policy
        	createMonthlyPolicy(driver, extentedReport, dataObject);
        	  
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
	        Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);

        }// finally

    }// tcCBTWJ011

    @Test(description = "To create 5 star monthly policy with unspecified and specified covers", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ012(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {

        	//Create 5* monthly policy
        	createMonthlyPolicy(driver, extentedReport, dataObject);
        	  
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
	        Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);

        }// finally

    }// tcCBTWJ012

    @Test(description = "To create 3 star annual policy with core cover with joint policy holder and claims", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ013(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {
        	//Create 3* annual policy
        	createAnnualPolicy(driver, extentedReport, dataObject);
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
	        Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);

        }// finally

    }// tcHoodWJ013

    @Test(description = "To create 5 star annual policy with core cover with joint policy holder and claims", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ014(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {
        	//Create 5* annual policy
        	createAnnualPolicy(driver, extentedReport, dataObject);
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
            driver.quit();
            Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);
            
        }// finally

    }// tcHoodWJ014

    @Test(description = "To create 3 star monthly policy with core cover with joint policy holder and claims", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ015(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {

        	//Create 3* monthly policy
        	createMonthlyPolicy(driver, extentedReport, dataObject);
        	  
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
            driver.quit();
	        Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);

        }// finally

    }// tcCBTWJ015

    @Test(description = "To create 5 star monthly policy with core cover with joint policy holder and claims", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ016(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {

        	//Create 5* monthly policy
        	createMonthlyPolicy(driver, extentedReport, dataObject);
        	  
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
	        Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);

        }// finally

    }// tcCBTWJ016

    @Test(description = "Save Quote for new user", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ017(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {
        	//Initialize workflow
        	webjourneyWorkflow policyCreate = new webjourneyWorkflow(driver, extentedReport);

        	//Initialize test data for workflow
        	policyCreate._dataObject = dataObject;
        	
        	//Enter data in the Quote details page
        	policyCreate.EnterQuoteDetails();

        	//Submit the Quote
        	policyCreate.SubmitQuote();

        	//Save the created Quote
        	policyCreate.SaveQuote();

        	//Enter User info for save Quote
        	policyCreate.EnterUserInfoSaveQuote();

        	//Save User info for save Quote
        	policyCreate.SubmitUserInfoSaveQuote();
        	
        	//Verify the quote is Saved or not
        	policyCreate.VerifyQuoteSaved();     
        	  
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
	        Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);

        }// finally

    }// tcCBTWJ017

    @Test(description = "Create 3 Star core cover Annual Policy for existing customer", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ018(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {

        	//Create 3* annual policy
        	createAnnualPolicy(driver, extentedReport, dataObject);
        	  
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
	        Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);

        }// finally

    }// tcCBTWJ018

    @Test(description = "Create 3 Star core cover Monthly Policy for existing customer", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ020(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

        try {

        	//Create 3* annual policy
        	createMonthlyPolicy(driver, extentedReport, dataObject);
        	  
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
	        Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);

        }// finally

    }// tcCBTWJ020

	@Test(description = "Save Quote for existing user", dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
    public void tcHoodWJ019(String browser, Method method) throws Exception {

		final WebDriver driver = WebDriverFactory.get(browser);

        // Loading the test data from excel using the test case id and class
        // name
        String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
        String className = "UXP_WJ_" + env;

        WebjourneyDataDriver dataObject = new WebjourneyDataDriver();
        dataObject.getTestData(method.getName(), className);
        
        ExtentTest extentedReport = Log.testCaseInfo(dataObject.description, "WJ - "+ method.getName() + " - " + dataObject.description+ " [" + dataObject.browserwithos + "]" , dataObject.test, "UXP_NFR Team");				

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
        	     
        	  
        }// try
        catch (Exception e) {
            Log.exception(e, driver, extentedReport);
        }// catch
        finally {
        	driver.quit();
	        Log.endTestCase(extentedReport);
            Log.testCaseResult(extentedReport);

        }// finally

    }// tcCBTWJ019

    public static void createAnnualPolicy(WebDriver _driver, ExtentTest _extentedReport, WebjourneyDataDriver _dataObject) throws Exception {

        try {
        	
        	//Initialize Annual workflow
        	webjourneyWorkflow annual = new webjourneyWorkflow(_driver, _extentedReport);
        	
        	//Initialize test data for Annual workflow
        	annual._dataObject = _dataObject;
        	
        	//Enter data in the Quote details page
        	annual.EnterQuoteDetails();

        	//Submit the Quote
        	annual.SubmitQuote();

        	//Enter data in the Cover details page
        	annual.EnterCoverDetails();

        	//Submit the Quote
        	annual.SubmitCover();
        	
        	//Add cover data and more in Summary Page
        	annual.AddSummary();
        	
        	//Select Annual Policy type, Contact and Payment Info 
        	annual.MakeAnnualPayment();
        	
        	//Verify the quote is created or not
        	quoteNumber = annual.VerifyQuoteCreated();      
        	
        }// try
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }// catch
        finally {                        
        }// finally

    }//createAnnualPolicy
	
	public static void createMonthlyPolicy(WebDriver _driver, ExtentTest _extentedReport, WebjourneyDataDriver _dataObject) throws Exception {

        try {
        	
        	//Initialize Monthly workflow
        	webjourneyWorkflow PCLPolicy = new webjourneyWorkflow(_driver, _extentedReport);

        	//Initialize test data for monthly workflow
        	PCLPolicy._dataObject = _dataObject;
        	
        	//Enter data in the Quote details page
        	PCLPolicy.EnterQuoteDetails();

        	//Submit the Quote
        	PCLPolicy.SubmitQuote();

        	//Enter data in the Cover details page
        	PCLPolicy.EnterCoverDetails();

        	//Submit the Quote
        	PCLPolicy.SubmitCover();
        	
        	//Add cover data and more in Summary Page
        	PCLPolicy.AddSummary();
        	
        	//Select Policy type, Contact and Payment Info
        	PCLPolicy.MakeMonthlyPayment();
        	
        	//Confirm the monthly payment
        	PCLPolicy.confirmMonthlyQuote();
        	
        	//Verify the quote is created or not
        	quoteNumber = PCLPolicy.VerifyQuoteCreated();   
        	
        }// try
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }// catch
        finally {      
        }// finally

    }//createMonthlyPolicy
	
}