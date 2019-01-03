package com.ssp.testscripts.regression;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
// import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import com.ssp.pages.uxp.BuyQuotePage;
import com.ssp.pages.uxp.CustDashboardPage;
import com.ssp.pages.uxp.HomePage;
import com.ssp.pages.uxp.LoginPage;
import com.ssp.pages.uxp.NewQuotePage;
import com.ssp.pages.uxp.SearchPage;
import com.ssp.support.BaseTest;
import com.ssp.support.DataProviderUtils;
import com.ssp.support.DataUtils;
import com.ssp.support.EmailReport;
import com.ssp.support.Log;
import com.ssp.support.WebDriverFactory;

@Listeners(EmailReport.class)
public class UXP_EC_1699_WB extends BaseTest {

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
    String os = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
        .getParameter("os").substring(0, 1);
    String osversion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
        .getParameter("os_version");
    browserwithos = os + osversion + "_" + browsername + browserversion;
    return Log.testCaseInfo(testCaseId + " [" + test + "]",
        testCaseId + " - " + testDesc + " [" + browserwithos + "]", test, "");
  }

  public HashMap<String, String> getTestData(String testcaseId) {
    String env =
        Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
    String className = "UXP_EC_1699_WB_" + env;
    return DataUtils.testDatabyID(testcaseId, className);
  }

  @Test(
      description = "To validate that system should display 'Automatic review', 'automatic renewal' checked and 'block renewal' unchecked by default while create STA of the policy. To validate that CCA can change the flag values to checked/unchecked while doing the MTA of the policy if the MTA is already performed on it. To validate that User able to check 'Block Renewal' checkbox while doing MTA and Blocked Renewal Notification displayed in the Customer Dashboard page To validate If Block Renewal is checked by the CCA then 'B' with Red mark will display in the Customer Dashboard when when the first invite renewal job is run To validate Blocked Renewal icon will removed from policy if another MTA has been created with the same policy",
      dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
  public void TC_414_417_421_422_423(String browser) throws Exception {
    String tcId = "TC_414_417_421_422_423";
    final WebDriver driver = WebDriverFactory.get(browser);
    HashMap<String, String> testData = getTestData(tcId);
    String userName = testData.get("UserName");
    String password = testData.get("Password");
    String description = testData.get("Description");
    String brandname = testData.get("Brand Name");
    String title = testData.get("Title");


    ExtentTest extentedReport = addTestInfo(tcId, description);
    String Corecover = testData.get("Cover");

    try {
      // Navigate to Login Page
      LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
      Log.pass("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

      // Login to the application
      HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
      Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page",
          "Login failed", driver, extentedReport, true);
      Log.message("Logged in with User id:" + userName + "& Password:" + password, driver,
          extentedReport);
      // Click on Take Call link
      homePage.clickTakeCall(extentedReport);
      homePage.clickMyBrands(brandname, extentedReport);
      SearchPage searchPage = new SearchPage(driver, extentedReport).get();
      Log.softAssertThat(
          searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
          "Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

      // Enter Customer Details
      searchPage.clickCreateCustomer(true, extentedReport);
      testData = searchPage.enterCustomerDetails(testData, true, extentedReport);

      // Confirm customer details and create customer
      CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "custdashboardpage Verified", "custdashboardpage not Verified", driver, extentedReport,
          true);

      Log.softAssertThat(
          custdashboardpage.verifyContactName(
              title + " " + testData.get("First Name") + " " + testData.get("Last Name"),
              extentedReport, true),
          "Verified Searched FirstName and LastName on Customer Dashboard",
          "Not Verified Customer name on Dashboard", driver, extentedReport, true);
      // Create New Quote
      custdashboardpage.clickNewQuote(true, extentedReport);
      // Past date for renewal
      custdashboardpage.enterNewQuotePastDate(5, 1, extentedReport, true);
      custdashboardpage.selectNewQuoteDetails(testData, extentedReport, true);
      // custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
      NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

      // Create New Quote
      Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport),
          "Verified NewQuotePage ", "Not verified NewQuotePage", driver, extentedReport, true);
      newquotepage.enterPolicyDetails(testData, true, extentedReport);
      newquotepage.clickNextOne(extentedReport);
      newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
      newquotepage.clickNextTwo(extentedReport);
      newquotepage.clickView(extentedReport);
      newquotepage.clickAgree(extentedReport);

      // Get and Buy Quote
      newquotepage.clickGetQuote(extentedReport);
      newquotepage.clickBuy(extentedReport);
      newquotepage.clickCustomerPreferenceNext(extentedReport);

      // Select Payment
      newquotepage.selectPayment(testData, true, extentedReport);
      custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "custdashboardpage Verified", "custdashboardpage not Verified", driver, extentedReport,
          true);

      // check for policy status and STA
      HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
      if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {
        Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);

        custdashboardpage.clickManagePolicy(extentedReport);
        custdashboardpage.clickShortTermAdjustment(extentedReport);

        testData.put("InceptionDate", policyDetails.get("InceptionDate"));
        testData.put("ExpiryDate", policyDetails.get("ExpiryDate"));
        custdashboardpage.enterSTADetailsInPast(testData, extentedReport, true);
        NewQuotePage newQuotePage = new NewQuotePage(driver, extentedReport);
        // Test case 414 & 417
        Log.softAssertThat(
            newQuotePage.validateReviewRenewalFields("Automatic Review", "checked", extentedReport,
                true),
            "Automatic review are checked as expected",
            "Automatic review are unchecked. This is unexpected");
        Log.softAssertThat(
            newQuotePage.validateReviewRenewalFields("Automatic Renewal", "checked", extentedReport,
                true),
            "Automatic Renewal are checked as expected",
            "Automatic Renewal are unchecked. This is unexpected");
        Log.softAssertThat(
            !newQuotePage.validateReviewRenewalFields("Block Renewal", "checked", extentedReport,
                true),
            "Block Renewal field unchecked as expected",
            "Block Renewal field checked which is not expected, it should be unchecked by default if no flags set");

        // Navigate to customer dashboard again
        newQuotePage.clickBackToDashboard(extentedReport);

        // Select MTA
        custdashboardpage.clickManagePolicy(extentedReport);
        custdashboardpage.clickMidTermAdjustment(extentedReport);
        Log.pass("Clicked on MTA menu successfully", driver, extentedReport, true);
        // Fill up MTa data
        testData.put("MTAdjReason", testData.get("AdjustmentReason"));
        custdashboardpage.enterMTADetails(testData, extentedReport, true);
        Log.pass("Entered MTA Details and Clicked on Confirm", driver, extentedReport, true);
        newQuotePage.verifyNewQuotePage(false, extentedReport);
        Log.pass("MTA page loaded successfully", driver, extentedReport, true);
        Log.softAssertThat(
            newQuotePage.validateReviewRenewalFields("Automatic Review", "enabled", extentedReport,
                true),
            "Automatic Review field is enabled and user can modify which is expected behaviour",
            "Automatic Review field is disbaled which is not expected");
        newQuotePage.clickPolicyFlags("Automatic Review", extentedReport, true);
        Log.softAssertThat(
            newQuotePage.validateReviewRenewalFields("Automatic Renewal", "enabled", extentedReport,
                true),
            "Automatic Renewal field is enabled and user can modify which is expected behaviour",
            "Automatic Renewal field is disbaled which is not expected");
        newQuotePage.clickPolicyFlags("Automatic Renewal", extentedReport, true);
        Log.softAssertThat(
            newQuotePage.validateReviewRenewalFields("Block Renewal", "enabled", extentedReport,
                true),
            "Block Renewal field is enabled and user can modify which is expected behaviour",
            "Block Renewal field is disbaled which is not expected");
        newQuotePage.clickPolicyFlags("Block Renewal", extentedReport, true);
        Log.pass("Block renewal flag clicked", driver, extentedReport, true);

        // TC 421 & 422
        newQuotePage.clickBuyMTA(extentedReport, true);
        Log.message("Clicked on Buy Quote", driver, extentedReport);
        newQuotePage.selectMTAReasonPayment(extentedReport);
        if (newQuotePage.VerifyAdjustmentAmtOnAcceptacePage(extentedReport)) {
          BuyQuotePage buyQuotePage = new BuyQuotePage(driver, extentedReport);
          buyQuotePage.enterTrasactionID(extentedReport);
        }
        newQuotePage.clickConfirmMTA(extentedReport);

        // check for policy status

        policyDetails = custdashboardpage.getPolicyURN(extentedReport);
        if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"))
          Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport,
              true);

        if (policyDetails.get("notification_plcy").toString().equalsIgnoreCase("B"))
          Log.pass("Blocked renewal is displayed on customer dashboard page", driver,
              extentedReport, true);

        Log.softAssertThat(
            custdashboardpage.checkCustomerDashBoardStatus(true, extentedReport,
                "Blocked Renewal Status"),
            "Customer dashboard status changed to Blocked renewal as expected",
            "Customer dashboard status not changed to Blocked renewal");

        // TC 423
        // Select MTA
        custdashboardpage.clickManagePolicy(extentedReport);
        custdashboardpage.clickMidTermAdjustment(extentedReport);
        Log.pass("Clicked on MTA menu successfully", driver, extentedReport, true);
        // Fill up MTa data
        testData.put("MTAdjReason", testData.get("AdjustmentReason"));
        custdashboardpage.enterMTADetails(testData, extentedReport, true);
        Log.pass("Entered MTA Details and Clicked on Confirm", driver, extentedReport, true);
        newQuotePage.verifyNewQuotePage(false, extentedReport);
        Log.pass("MTA page loaded successfully", driver, extentedReport, true);
        newQuotePage.clickPolicyFlags("Block Renewal", extentedReport, true);
        Log.pass("Block renewal flag clicked", driver, extentedReport, true);
        newQuotePage.clickBuyMTA(extentedReport, true);
        Log.message("Clicked on Buy Quote", driver, extentedReport);
        newQuotePage.selectMTAReasonPayment(extentedReport);
        if (newQuotePage.VerifyAdjustmentAmtOnAcceptacePage(extentedReport)) {
          BuyQuotePage buyQuotePage = new BuyQuotePage(driver, extentedReport);
          buyQuotePage.enterTrasactionID(extentedReport);
        }
        newQuotePage.clickConfirmMTA(extentedReport);

        // check for policy status

        policyDetails = custdashboardpage.getPolicyURN(extentedReport);
        if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"))
          Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport,
              true);

        if (!policyDetails.get("notification_plcy").toString().equalsIgnoreCase("B"))
          Log.pass("Blocked renewal is not displayed on customer dashboard page", driver,
              extentedReport, true);

      } else {
        Log.fail("NB Policy has failed to Create in Accepted status, unable to procced with STA",
            driver, extentedReport, true);
      }
      Log.testCaseResult(extentedReport);
    } catch (

    Exception e) {
      Log.exception(e, driver, extentedReport);
    } finally {
      Log.endTestCase(extentedReport);
      driver.quit();
    }

  }

  @Test(
      description = "To validate if below Policy Flag are set as Unchecked in previously then again it will display same in the Policy Adjustment screen. To validate if Automatic Review is unchecked by the CCA then 'R' with Red mark will display in the Customer Dashboard when Policy enter in the Renewal cycle  ",
      dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
  public void TC_416_420(String browser) throws Exception {
    String tcId = "TC_416_420";
    final WebDriver driver = WebDriverFactory.get(browser);
    HashMap<String, String> testData = getTestData(tcId);
    String userName = testData.get("UserName");
    String password = testData.get("Password");
    String description = testData.get("Description");
    String brandname = testData.get("Brand Name");
    String title = testData.get("Title");


    ExtentTest extentedReport = addTestInfo(tcId, description);
    String Corecover = testData.get("Cover");

    try {
      // Navigate to Login Page
      LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
      Log.pass("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

      // Login to the application
      HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
      Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page",
          "Login failed", driver, extentedReport, true);
      Log.message("Logged in with User id:" + userName + "& Password:" + password, driver,
          extentedReport);
      // Click on Take Call link
      homePage.clickTakeCall(extentedReport);
      homePage.clickMyBrands(brandname, extentedReport);
      SearchPage searchPage = new SearchPage(driver, extentedReport).get();
      Log.softAssertThat(
          searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
          "Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

      // Enter Customer Details
      searchPage.clickCreateCustomer(true, extentedReport);
      testData = searchPage.enterCustomerDetails(testData, true, extentedReport);

      // Confirm customer details and create customer
      CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "custdashboardpage Verified", "custdashboardpage not Verified", driver, extentedReport,
          true);

      Log.softAssertThat(
          custdashboardpage.verifyContactName(
              title + " " + testData.get("First Name") + " " + testData.get("Last Name"),
              extentedReport, true),
          "Verified Searched FirstName and LastName on Customer Dashboard",
          "Not Verified Customer name on Dashboard", driver, extentedReport, true);
      // Create New Quote
      custdashboardpage.clickNewQuote(true, extentedReport);
      // Past date for renewal
      custdashboardpage.enterNewQuotePastDate(5, 1, extentedReport, true);
      custdashboardpage.selectNewQuoteDetails(testData, extentedReport, true);
      // custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
      NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

      // Create New Quote
      Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport),
          "Verified NewQuotePage ", "Not verified NewQuotePage", driver, extentedReport, true);
      newquotepage.enterPolicyDetails(testData, true, extentedReport);
      newquotepage.clickNextOne(extentedReport);
      newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
      newquotepage.clickNextTwo(extentedReport);
      newquotepage.clickView(extentedReport);
      newquotepage.clickAgree(extentedReport);

      // Get and Buy Quote
      newquotepage.clickGetQuote(extentedReport);
      newquotepage.clickBuy(extentedReport);
      newquotepage.clickCustomerPreferenceNext(extentedReport);

      // Select Payment
      newquotepage.selectPayment(testData, true, extentedReport);
      custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "custdashboardpage Verified", "custdashboardpage not Verified", driver, extentedReport,
          true);

      // check for policy status
      HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
      if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {
        Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);

        NewQuotePage newQuotePage = new NewQuotePage(driver, extentedReport);

        // Select MTA
        custdashboardpage.clickManagePolicy(extentedReport);
        custdashboardpage.clickMidTermAdjustment(extentedReport);
        Log.pass("Clicked on MTA menu successfully", driver, extentedReport, true);
        // Fill up MTa data
        testData.put("MTAdjReason", testData.get("AdjustmentReason"));
        custdashboardpage.enterMTADetails(testData, extentedReport, true);
        Log.pass("Entered MTA Details and Clicked on Confirm", driver, extentedReport, true);
        newQuotePage.verifyNewQuotePage(false, extentedReport);
        Log.pass("MTA page loaded successfully", driver, extentedReport, true);
        if (newQuotePage.validateReviewRenewalFields("Automatic Review", "checked", extentedReport,
            true))
          newQuotePage.clickPolicyFlags("Automatic Review", extentedReport, true);
        Thread.sleep(1000);
        if (newQuotePage.validateReviewRenewalFields("Automatic Renewal", "checked", extentedReport,
            true)) {
          Thread.sleep(1000);
          newQuotePage.clickPolicyFlags("Automatic Renewal", extentedReport, true);
        }
        Thread.sleep(1000);
        if (newQuotePage.validateReviewRenewalFields("Block Renewal", "checked", extentedReport,
            true)) {
          Thread.sleep(1000);
          newQuotePage.clickPolicyFlags("Block Renewal", extentedReport, true);
        }
        newQuotePage.clickBuyMTA(extentedReport, true);
        Log.message("Clicked on Buy Quote", driver, extentedReport);
        newQuotePage.selectMTAReasonPayment(extentedReport);
        if (newQuotePage.VerifyAdjustmentAmtOnAcceptacePage(extentedReport)) {
          BuyQuotePage buyQuotePage = new BuyQuotePage(driver, extentedReport);
          buyQuotePage.enterTrasactionID(extentedReport);
        }
        newQuotePage.clickConfirmMTA(extentedReport);

        // check for policy status

        policyDetails = custdashboardpage.getPolicyURN(extentedReport);
        if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted"))
          Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport,
              true);

        // TC 420
        if (policyDetails.get("notification_plcy").toString().equalsIgnoreCase("R"))
          Log.pass("R is displayed on customer dashboard page", driver, extentedReport, true);

        // Select MTA
        custdashboardpage.clickManagePolicy(extentedReport);
        custdashboardpage.clickMidTermAdjustment(extentedReport);
        Log.pass("Clicked on MTA menu successfully", driver, extentedReport, true);
        // Fill up MTa data
        testData.put("MTAdjReason", testData.get("AdjustmentReason"));
        custdashboardpage.enterMTADetails(testData, extentedReport, true);
        Log.pass("Entered MTA Details and Clicked on Confirm", driver, extentedReport, true);
        newQuotePage.verifyNewQuotePage(false, extentedReport);
        Log.pass("MTA page loaded successfully", driver, extentedReport, true);
        Log.softAssertThat(
            !newQuotePage.validateReviewRenewalFields("Automatic Review", "checked", extentedReport,
                true),
            "Automatic review are unchecked as expected",
            "Automatic review are checked. This is unexpected");
        Log.softAssertThat(
            !newQuotePage.validateReviewRenewalFields("Automatic Renewal", "checked",
                extentedReport, true),
            "Automatic Renewal are unchecked as expected",
            "Automatic Renewal are checked. This is unexpected");
        Log.softAssertThat(
            !newQuotePage.validateReviewRenewalFields("Block Renewal", "checked", extentedReport,
                true),
            "Block Renewal field unchecked as expected",
            "Block Renewal field checked which is not expected");
        Log.testCaseResult(extentedReport);
      }
    } catch (

    Exception e) {
      Log.exception(e, driver, extentedReport);
    } finally {
      Log.endTestCase(extentedReport);
      driver.quit();
    }
  }

  @Test(
      description = "To check CCA not able to change Flag once policy entered in the Renewal Cycle",
      dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
  public void TC_424(String browser) throws Exception {
    String tcId = "TC_424";
    final WebDriver driver = WebDriverFactory.get(browser);
    HashMap<String, String> testData = getTestData(tcId);
    String userName = testData.get("UserName");
    String password = testData.get("Password");
    String description = testData.get("Description");
    String brandname = testData.get("Brand Name");
    String title = testData.get("Title");


    ExtentTest extentedReport = addTestInfo(tcId, description);
    String Corecover = testData.get("Cover");

    try {
      // Navigate to Login Page
      LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
      Log.pass("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

      // Login to the application
      HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
      Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page",
          "Login failed", driver, extentedReport, true);
      Log.message("Logged in with User id:" + userName + "& Password:" + password, driver,
          extentedReport);
      // Click on Take Call link
      homePage.clickTakeCall(extentedReport);
      homePage.clickMyBrands(brandname, extentedReport);
      SearchPage searchPage = new SearchPage(driver, extentedReport).get();
      Log.softAssertThat(
          searchPage.uielement.verifyPageElements(Arrays.asList("txtPolicyNumber"), searchPage),
          "Search Page Verified", "Search Page not Verified", driver, extentedReport, true);

      // Enter Customer Details
      searchPage.clickCreateCustomer(true, extentedReport);
      testData = searchPage.enterCustomerDetails(testData, true, extentedReport);

      // Confirm customer details and create customer
      CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "custdashboardpage Verified", "custdashboardpage not Verified", driver, extentedReport,
          true);

      Log.softAssertThat(
          custdashboardpage.verifyContactName(
              title + " " + testData.get("First Name") + " " + testData.get("Last Name"),
              extentedReport, true),
          "Verified Searched FirstName and LastName on Customer Dashboard",
          "Not Verified Customer name on Dashboard", driver, extentedReport, true);
      // Create New Quote
      custdashboardpage.clickNewQuote(true, extentedReport);
      // Past date for renewal
      custdashboardpage.enterNewQuotePastDate(5, 1, extentedReport, true);
      custdashboardpage.selectNewQuoteDetails(testData, extentedReport, true);
      // custdashboardpage.enterQuoteDetails(testData, true, extentedReport);
      NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

      // Create New Quote
      Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport),
          "Verified NewQuotePage ", "Not verified NewQuotePage", driver, extentedReport, true);
      newquotepage.enterPolicyDetails(testData, true, extentedReport);
      newquotepage.clickNextOne(extentedReport);
      newquotepage.enterCustomerDetails(testData, true, Corecover, extentedReport);
      newquotepage.clickNextTwo(extentedReport);
      newquotepage.clickView(extentedReport);
      newquotepage.clickAgree(extentedReport);

      // Get and Buy Quote
      newquotepage.clickGetQuote(extentedReport);
      newquotepage.clickBuy(extentedReport);
      newquotepage.clickCustomerPreferenceNext(extentedReport);

      // Select Payment
      newquotepage.selectPayment(testData, true, extentedReport);
      custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "custdashboardpage Verified", "custdashboardpage not Verified", driver, extentedReport,
          true);

      // check for policy status
      HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
      if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {
        Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);

        NewQuotePage newQuotePage = new NewQuotePage(driver, extentedReport);

        Log.softAssertThat(
            custdashboardpage.uielement.verifyPageElements(Arrays.asList("drpManageRenewals"),
                custdashboardpage),
            "Manage renewal tab is dispalyed on customer dashaboard page",
            "Manage renewal tab is not dispalying on customer dashboard page");

        custdashboardpage.performRenewals_LapseRenewal("Invite Renewal", extentedReport, true);

        newquotepage.clickNextOne(extentedReport);
        newquotepage.clickNextTwo(extentedReport);
        newquotepage.clickView(extentedReport);
        newquotepage.clickAgree(extentedReport);
        newquotepage.clickGetQuote(extentedReport);
        newquotepage.clickQuoteSave("Renew", "Quote 1", extentedReport);

        // Select MTA
        custdashboardpage.clickManagePolicy(extentedReport);
        custdashboardpage.clickMidTermAdjustment(extentedReport);
        Log.pass("Clicked on MTA menu successfully", driver, extentedReport, true);
        // Fill up MTa data
        testData.put("MTAdjReason", testData.get("AdjustmentReason"));
        custdashboardpage.enterMTADetails(testData, extentedReport, true);
        Log.pass("Entered MTA Details and Clicked on Confirm", driver, extentedReport, true);
        newQuotePage.verifyNewQuotePage(false, extentedReport);
        Log.pass("MTA page loaded successfully", driver, extentedReport, true);
        Log.softAssertThat(
            newQuotePage.validateReviewRenewalFields("Automatic Review", "disabled", extentedReport,
                true),
            "Automatic review are disabled as expected",
            "Automatic review are enabled. This is unexpected");
        Log.softAssertThat(
            newQuotePage.validateReviewRenewalFields("Automatic Renewal", "disabled",
                extentedReport, true),
            "Automatic Renewal are disabled as expected",
            "Automatic Renewal are enabled. This is unexpected");
        Log.softAssertThat(
            newQuotePage.validateReviewRenewalFields("Block Renewal", "disabled", extentedReport,
                true),
            "Block Renewal field disabled as expected",
            "Block Renewal field enabled which is not expected");
        Log.testCaseResult(extentedReport);
      }
    } catch (Exception e) {
      Log.exception(e, driver, extentedReport);
    } finally {
      Log.endTestCase(extentedReport);
      driver.quit();
    }
  }
}
