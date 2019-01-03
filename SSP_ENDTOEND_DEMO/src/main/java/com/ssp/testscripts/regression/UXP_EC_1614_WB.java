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
import com.ssp.support.Utils;
import com.ssp.support.WebDriverFactory;

@Listeners(EmailReport.class)
public class UXP_EC_1614_WB extends BaseTest {

  private String webSite;
  public String monthlywebSite;
  String policyNumber = "";
  String firstName = "";
  String lastName_TC001 = "";
  String contactName = "";
  String quoteDescription1 = "TestDescription 1";
  String quoteDescription2 = "TestDescription 2";
  String quoteDescription3 = "TestDescription 3";
  String renewQuoteDesc = "Renewal Quote1";

  @BeforeMethod(alwaysRun = true)
  public void init(ITestContext context) throws IOException {
    webSite = System.getProperty("webSite") != null ? System.getProperty("webSite")
        : context.getCurrentXmlTest().getParameter("webSite");
    monthlywebSite =
        System.getProperty("monthlywebSite") != null ? System.getProperty("monthlywebSite")
            : context.getCurrentXmlTest().getParameter("monthlywebSite");
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
        testCaseId + " - " + testDesc + " [" + browserwithos + "]", test, "UXP-EC-ASDA");
  }

  public HashMap<String, String> getTestData(String testcaseId) {
    String env =
        Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
    String className = "UXP_EC_1614_WB_" + env;
    return DataUtils.testDatabyID(testcaseId, className);
  }

  @Test(
      description = "Check that the Quote description popup window is prompted for quote description (Save/Cancel button), user lands in the the Acceptance page, user is allowed to return to Data Capture and verify the status of Renewed Quote and user should return to datacapture when cancel button clicked in description pop up",
      dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
  public void TC_831_832_833_836_838_835(String browser) throws Exception {

    // Get the web driver instance
    String tcId = "TC_831_832_833_836_838_835";
    final WebDriver driver = WebDriverFactory.get(browser);
    HashMap<String, String> testData = getTestData(tcId);

    String userName = testData.get("UserName");
    String password = testData.get("Password");
    String description = testData.get("Description");
    Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
    String brandname = testData.get("Brand Name");

    ExtentTest extentedReport = addTestInfo(tcId, description);
    String Corecover = testData.get("Cover");

    try {

      // Navigate to Login Page
      LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
      Log.message("Engagement Centre Landing Page : " + webSite, driver, extentedReport, true);

      // Login to the application
      HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
      Log.softAssertThat(homePage.verifyHomePage(), "Successfully logged into UXP Home Page",
          "Login failed", driver, extentedReport, true);
      Log.message("Logged in User id:" + userName + "& Password:" + password, driver,
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
      HashMap<String, String> custDetails =
          searchPage.enterCustomerDetails(testData, true, extentedReport);

      // Confirm customer details and create customer
      CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
          true);
      Log.softAssertThat(
          custdashboardpage.verifyContactName(testData.get("Title") + " "
              + custDetails.get("First Name") + " " + custDetails.get("Last Name"), extentedReport,
              true),
          "Verified FirstName and LastName on Customer Dashboard",
          "Not Verified Customer Name on Dashboard", driver, extentedReport, true);

      // Create New Quote
      custdashboardpage.clickNewQuote(true, extentedReport);
      custdashboardpage.enterNewQuotePastDate(5, 1, extentedReport, true);
      // Past date for renewal
      custdashboardpage.selectNewQuoteDetails(testData, extentedReport, true);
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
      // Select Payment
      newquotepage.selectPayment(testData, true, extentedReport);


      custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
          true);

      // check for policy status
      HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
      if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {
        Log.message("NB Policy Created Successfully in accepted status", driver, extentedReport,
            true);
        custdashboardpage.performRenewals_LapseRenewal("Invite Renewal", extentedReport, true);
        newquotepage.clickNextOne(extentedReport);
        newquotepage.clickNextTwo(extentedReport);
        newquotepage.clickView(extentedReport);
        newquotepage.clickAgree(extentedReport);
        newquotepage.clickGetQuote(extentedReport);
        newquotepage.clickQuoteSave("Renew", "Renewal Quote", extentedReport);

        Log.softAssertThat(
            custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
                custdashboardpage),
            "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
            true);

        Log.softAssertThat(
            custdashboardpage.verify_reviewStatus("Review Required", extentedReport, true),
            "Review Required status was displayed after saving a renewal quote",
            "Review Required status was not displayed after saving a renewal quote");

        BuyQuotePage buyQuotePage =
            custdashboardpage.performRenewals_QuoteVariation(extentedReport, true);

        // To verify that acceptance tab selected by default or not
        Log.softAssertThat(buyQuotePage.verifyDefaultTab("Acceptance", extentedReport, true),
            "Acceptance tab was selected by default when clicked on quote variations",
            "Acceptance tab was not selected as default tab when clicked on quote variations",
            driver, extentedReport, true);

        buyQuotePage.checkQuotePage_traverse(testData.get("tabToClick"), extentedReport, true);
        Log.message("Naviagated to " + testData.get("tabToClick") + " page successfully", driver,
            extentedReport, true);

        Log.softAssertThat(buyQuotePage.clickDataCaptureandVerify(extentedReport, true),
            "Successfully navigated to data catpure page",
            "Failed to navigate to data capture from price presentation page", driver,
            extentedReport, true);

        // Click Save in data capture page and verify
        buyQuotePage.clickNextOne(extentedReport);
        buyQuotePage.clickNextTwo(extentedReport);
        buyQuotePage.clickView(extentedReport);
        buyQuotePage.clickAgree(extentedReport);
        buyQuotePage.clickSaveDataCapture(extentedReport, true);
        Log.softAssertThat(buyQuotePage.enterQuoteDescVerifybuttons("cancel", extentedReport, true),
            "After clicking cancel button in variation description dialog, user returned to data capture page",
            "After clicking cancel button in variation description dialog, user was not taken to data capture page",
            driver, extentedReport, true);

      } else {
        Log.fail(
            "Failed - NB Policy Created in " + policyDetails.get("Status").toString()
                + " status, unable to proceed with renewal transaction",
            driver, extentedReport, true);
      }
    } catch (Exception e) {
      Log.exception(e, driver, extentedReport);
    } finally {
      Log.endTestCase(extentedReport);
      driver.quit();
      Log.testCaseResult(extentedReport);
    }
  }

  @Test(description = "Check that the user is allowed to Review/Amend T’s and C’s details",
      dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
  public void TC_842(String browser) throws Exception {

    // Get the web driver instance
    String tcId = "TC_842";
    final WebDriver driver = WebDriverFactory.get(browser);
    HashMap<String, String> testData = getTestData(tcId);

    String userName = testData.get("UserName");
    String password = testData.get("Password");
    String description = testData.get("Description");
    Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
    String brandname = testData.get("Brand Name");
    String policyToSelect = testData.get("Policy Name");
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
      Log.message("Logged in User id:" + userName + "& Password:" + password, driver,
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
      HashMap<String, String> custDetails =
          searchPage.enterCustomerDetails(testData, true, extentedReport);

      // Confirm customer details and create customer
      CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
          true);
      Log.softAssertThat(
          custdashboardpage.verifyContactName(testData.get("Title") + " "
              + custDetails.get("First Name") + " " + custDetails.get("Last Name"), extentedReport,
              true),
          "Verified FirstName and LastName on Customer Dashboard",
          "Not Verified Customer Name on Dashboard", driver, extentedReport, true);

      // Create New Quote
      custdashboardpage.clickNewQuote(true, extentedReport);
      custdashboardpage.enterNewQuotePastDate(5, 1, extentedReport, true);
      // Past date for renewal
      custdashboardpage.selectNewQuoteDetails(testData, extentedReport, true);
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
      // Select Payment
      newquotepage.selectPayment(testData, true, extentedReport);

      custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
          true);

      // check for policy status
      HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
      if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {
        Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);
        custdashboardpage.performRenewals_LapseRenewal("Invite Renewal", extentedReport, true);
        newquotepage.clickNextOne(extentedReport);
        newquotepage.clickNextTwo(extentedReport);
        newquotepage.clickView(extentedReport);
        newquotepage.clickAgree(extentedReport);
        newquotepage.clickGetQuote(extentedReport);
        newquotepage.clickQuoteSave("Renew", "Renewal Quote", extentedReport);

        Log.softAssertThat(
            custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
                custdashboardpage),
            "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
            true);

        Log.softAssertThat(
            custdashboardpage.verify_reviewStatus("Review Required", extentedReport, true),
            "Review Required status was displayed after saving a renewal quote",
            "Review Required status was not displayed after saving a renewal quote");

        BuyQuotePage buyQuotePage =
            custdashboardpage.performRenewals_QuoteVariation(extentedReport, true);

        // Verify Acceptance page is landed
        Log.softAssertThat(buyQuotePage.verifyDefaultTab("Acceptance", extentedReport, true),
            "Acceptance tab was selected by default when clicked on quote variations",
            "Acceptance tab was not selected as default tab when clicked on quote variations",
            driver, extentedReport, true);
        // Edit T& C
        // Log.softAssertThat(buyQuotePage.verifyEditTermAndCondition(extentedReport),
        // "Verified Edit Terms and Conditions", "Not able to verify
        // Edit Terms and Conditions", driver,
        // extentedReport, true);
        buyQuotePage.clickTermsAndConditionSection(extentedReport);
        buyQuotePage.clickAttachTermAndConditionButton(extentedReport);

        buyQuotePage.selectTandCPolicy(policyToSelect, extentedReport, true);

        // Verify T&C is added
        Log.softAssertThat(
            buyQuotePage.verifyTandCPolicyadded(policyToSelect, extentedReport, false),
            policyToSelect + " - T&C is added under terms & condition section",
            policyToSelect + " - T&C is not added under terms & condition section", driver,
            extentedReport, true);

      } else {
        Log.fail(
            "Failed - NB Policy Created in " + policyDetails.get("Status").toString()
                + " status, unable to proceed with renewal transaction",
            driver, extentedReport, true);
      }

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

  @Test(
      description = "Verify the user is able to update the Change payor details, Check that the warning message is prompted when user performs lapse from the customer dashboard and user should return to the customer dashboard when click cancel in the warning message and verify that quotes are lapsed when clicked continue button",
      dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
  public void TC_844_870_871_872(String browser) throws Exception {

    // Get the web driver instance
    String tcId = "TC_844_870_871_872";
    final WebDriver driver = WebDriverFactory.get(browser);
    HashMap<String, String> testData = getTestData(tcId);
    HashMap<String, String> testData2 = new HashMap<String, String>(testData);
    String userName = testData.get("UserName");
    String password = testData.get("Password");
    String description = testData.get("Description");
    String brandname = testData.get("Brand Name");

    String ChangePayorfirstName = Utils.getRandomCharacters("alpha", 4) + "test";
    String ChangePayorlastName = Utils.getRandomCharacters("alpha", 4) + "test";

    testData2.put("First Name", ChangePayorfirstName);
    testData2.put("Last Name", ChangePayorlastName);

    Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");

    ExtentTest extentedReport = addTestInfo(tcId, description);
    try {

      // Navigate to Login Page
      LoginPage loginPage = new LoginPage(driver, webSite, extentedReport).get();
      Log.pass("SIAAS Landing Page : " + webSite, driver, extentedReport, true);

      // Login to the application
      HomePage homePage = loginPage.loginToSSP(userName, password, false, extentedReport);
      Log.softAssertThat(homePage.verifyHomePage(),
          "Successfully logged into Home Page with Username :  " + userName + " & Password :"
              + password,
          "Login failed into Home Page with Username :  " + userName + " & Password :" + password,
          driver, extentedReport, true);

      homePage.clickTakeCall(extentedReport);
      homePage.clickMyBrands(brandname, extentedReport);
      SearchPage searchPage = new SearchPage(driver, extentedReport).get();

      // Create Customer1 for change payor
      searchPage.clickCreateCustomer(true, extentedReport);
      searchPage.enterCustomerDetails(testData2, true, extentedReport);
      // Confirm customer details and create customer
      CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
      homePage = custdashboardpage.clickCompleteButton();
      homePage.clickTakeCall(extentedReport);
      homePage.clickMyBrands(brandname, extentedReport);
      searchPage.clickCreateCustomer(true, extentedReport);
      HashMap<String, String> custDetails =
          searchPage.enterCustomerDetails(testData, true, extentedReport);
      custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
      // homePage = custdashboardpage.clickCompleteButton();

      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
          true);
      Log.softAssertThat(
          custdashboardpage.verifyContactName(testData.get("Title") + " "
              + custDetails.get("First Name") + " " + custDetails.get("Last Name"), extentedReport,
              true),
          "Verified FirstName and LastName on Customer Dashboard",
          "Not Verified Customer Name on Dashboard", driver, extentedReport, true);

      // Create New Quote
      custdashboardpage.clickNewQuote(true, extentedReport);
      custdashboardpage.enterNewQuotePastDate(5, 1, extentedReport, true);
      // Past date for renewal
      custdashboardpage.selectNewQuoteDetails(testData, extentedReport, true);
      NewQuotePage newquotepage = custdashboardpage.clickContinueQuote(true, extentedReport);

      // Create New Quote
      Log.softAssertThat(newquotepage.verifyNewQuotePage(true, extentedReport),
          "Verified NewQuotePage ", "Not verified NewQuotePage", driver, extentedReport, true);
      newquotepage.enterPolicyDetails(testData, true, extentedReport);
      newquotepage.clickNextOne(extentedReport);
      newquotepage.enterCustomerDetails(testData, true, testData.get("Cover"), extentedReport);
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
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
          true);

      // check for policy status
      HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
      if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {
        Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);
        custdashboardpage.performRenewals_LapseRenewal("Invite Renewal", extentedReport, true);
        newquotepage.clickNextOne(extentedReport);
        newquotepage.clickNextTwo(extentedReport);
        newquotepage.clickView(extentedReport);
        newquotepage.clickAgree(extentedReport);
        newquotepage.clickGetQuote(extentedReport);
        newquotepage.clickQuoteSave("Renew", "Renewal Quote", extentedReport);

        Log.softAssertThat(
            custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
                custdashboardpage),
            "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
            true);

        Log.softAssertThat(
            custdashboardpage.verify_reviewStatus("Review Required", extentedReport, true),
            "Review Required status was displayed after saving a renewal quote",
            "Review Required status was not displayed after saving a renewal quote");

        BuyQuotePage buyQuotePage =
            custdashboardpage.performRenewals_QuoteVariation(extentedReport, true);

        // Verify Acceptance page is landed
        Log.softAssertThat(buyQuotePage.verifyDefaultTab("Acceptance", extentedReport, true),
            "Acceptance tab was selected by default when clicked on quote variations",
            "Acceptance tab was not selected as default tab when clicked on quote variations",
            driver, extentedReport, true);

        // update payment method
        // FIXME Comments the below two lines because user is unable to update the payment plan and
        // payment method right now.
        /*
         * buyQuotePage.selectPaymentPlan(testData.get("Payment Plan"), extentedReport);
         * buyQuotePage.selectPaymentMethod(testData.get("Payment Method"), extentedReport, true);
         */

        buyQuotePage.clickChangePayorButton(extentedReport, true);
        // buyQuotePage.clickCheckboxinPayment(extentedReport, true);

        String payorname = newquotepage.enterChangePayorDetails(ChangePayorlastName,
            ChangePayorfirstName, testData.get("Post Code"), extentedReport, true);
        Log.softAssertThat(newquotepage.verifyPayorName(payorname),
            "The user is able to change the payor of the policy, once the quote reached Acceptance page : "
                + payorname,
            "The user is not able to change the payor of the policy, once the quote reached Acceptance page : "
                + payorname,
            driver, extentedReport, true);
        Log.softAssertThat(!buyQuotePage.verifyAcceptButton(extentedReport),
            "Accept button is disabled by default as expected",
            "Accept button is enabled by default unexpectedly", driver, extentedReport, true);

        if (newquotepage.verifyPayorName(payorname))
          buyQuotePage.clickCheckboxinPayment(extentedReport, true);
        // buyQuotePage.clicksave_LapsebuttoninAcceptancepage("Reviewed", "QuoteDescription",
        // "Save", extentedReport);

        Log.softAssertThat(!buyQuotePage.verifyAcceptButton(extentedReport),
            "Accept button is disabled by default as expected",
            "Accept button is enabled by default unexpectedly", driver, extentedReport, true);

        buyQuotePage.enterTrasactionIDForRenewal(extentedReport);

        Log.softAssertThat(buyQuotePage.verifyAcceptButton(extentedReport),
            "Accept button isn't enabled after entering transaction id",
            "Accept button is enabled as expected after entering transaction ID", driver,
            extentedReport, true);

        buyQuotePage.clickAcceptBtn(extentedReport);
        Log.softAssertThat(
            custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
                custdashboardpage),
            "Customer Dashboard page is verified", "Customer Dashboard page not verified", driver,
            extentedReport, true);

        // Click and Verify Quote Lapse option in customer dashboard page
        custdashboardpage.performRenewals_LapseRenewal("Lapse Quote", extentedReport, true);
        Log.softAssertThat(custdashboardpage.VerifyLapseRenewalModal(extentedReport, true),
            "Lapse Quote button was clicked and verified the warning message / continue / Cancel button",
            "Warning message / continue / Cancel button verification during Lapse Quote was not successful",
            driver, extentedReport, true);

        custdashboardpage.clickLapseCancelContinue("cancel", extentedReport, true);

        Log.softAssertThat(
            custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
                custdashboardpage),
            "User returned to Customer Dashboard page on clicking cancel button in Lapse quote warning modal",
            "User not returned to Customer Dashboard page on clicking cancel button in Lapse quote warning modal",
            driver, extentedReport, true);

        // Perform Lapse quote from customer dashboard page
        custdashboardpage.performRenewals_LapseRenewal("Lapse Quote", extentedReport, true);
        Log.softAssertThat(custdashboardpage.VerifyLapseRenewalModal(extentedReport, true),
            "Lapse Quote button was clicked and verified the warning message / continue / Cancel button",
            "Warning message / continue / Cancel button verification during Lapse Quote was not successful",
            driver, extentedReport, true);

        custdashboardpage.clickLapseCancelContinue("continue", extentedReport, true);
        Log.softAssertThat(
            custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
                custdashboardpage),
            "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
            true);
        // Add steps to verify all the4 quotes are lapsed
        HashMap<String, String> hmQuotedetails =
            custdashboardpage.getQuoteVariations(extentedReport, true);
        if (hmQuotedetails.get("quoteStatus_0").toString().contentEquals("L")
            && hmQuotedetails.get("quoteStatus_1").toString().contentEquals("L"))
          Log.pass(
              "Quote variations in customer dashboard page are listed as review required and lapsed",
              extentedReport);

        else
          Log.fail(
              "Quote variations in customer dashboard were not listed listed as review required and lapsed",
              extentedReport);

      } else {
        Log.fail(
            "Failed - NB Policy Created in " + policyDetails.get("Status").toString()
                + " status, unable to proceed with renewal transaction",
            driver, extentedReport, true);
      }

    } catch (Exception e) {
      Log.exception(e, driver, extentedReport);
    } finally {
      Log.endTestCase(extentedReport);
      driver.quit();
      Log.testCaseResult(extentedReport);
    }
  }

  @Test(
      description = "Verify the quote variation status when save at price presentation page and Renewal Quote variation tab has Review required and lapse quotes in drop down option and check that user is returned to Price presentation, when clicked on 'Cancel' or 'X' options in the popup window",
      dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
  public void TC_839_840(String browser) throws Exception {

    // Get the web driver instance
    String tcId = "TC_839_840";
    final WebDriver driver = WebDriverFactory.get(browser);
    HashMap<String, String> testData = getTestData(tcId);

    String userName = testData.get("UserName");
    String password = testData.get("Password");
    String description = testData.get("Description");
    Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
    String brandname = testData.get("Brand Name");

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
      Log.message("Logged in User id:" + userName + "& Password:" + password, driver,
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
      HashMap<String, String> custDetails =
          searchPage.enterCustomerDetails(testData, true, extentedReport);

      // Confirm customer details and create customer
      CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
          true);
      Log.softAssertThat(
          custdashboardpage.verifyContactName(testData.get("Title") + " "
              + custDetails.get("First Name") + " " + custDetails.get("Last Name"), extentedReport,
              true),
          "Verified FirstName and LastName on Customer Dashboard",
          "Not Verified Customer Name on Dashboard", driver, extentedReport, true);

      // Create New Quote
      custdashboardpage.clickNewQuote(true, extentedReport);
      custdashboardpage.enterNewQuotePastDate(5, 1, extentedReport, true);
      // Past date for renewal
      custdashboardpage.selectNewQuoteDetails(testData, extentedReport, true);
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
      // Select Payment
      newquotepage.selectPayment(testData, true, extentedReport);

      custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
          true);

      // check for policy status
      HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
      if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {
        Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);
        custdashboardpage.performRenewals_LapseRenewal("Invite Renewal", extentedReport, true);
        newquotepage.clickNextOne(extentedReport);
        newquotepage.clickNextTwo(extentedReport);
        newquotepage.clickView(extentedReport);
        newquotepage.clickAgree(extentedReport);
        newquotepage.clickGetQuote(extentedReport);
        newquotepage.clickQuoteSave("Renew", "Quote1", extentedReport);

        Log.softAssertThat(
            custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
                custdashboardpage),
            "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
            true);

        Log.softAssertThat(
            custdashboardpage.verify_reviewStatus("Review Required", extentedReport, true),
            "Review Required status was displayed after saving a renewal quote",
            "Review Required status was not displayed after saving a renewal quote");

        BuyQuotePage buyQuotePage =
            custdashboardpage.performRenewals_QuoteVariation(extentedReport, true);

        // To verify that acceptance tab selected by default or not
        Log.softAssertThat(buyQuotePage.verifyDefaultTab("Acceptance", extentedReport, true),
            "Acceptance tab was selected by default when clicked on quote variations",
            "Acceptance tab was not selected as default tab when clicked on quote variations",
            driver, extentedReport, true);

        buyQuotePage.checkQuotePage_traverse(testData.get("tabToClick"), extentedReport, true);
        Log.pass("Naviagated to " + testData.get("tabToClick") + " page successfully", driver,
            extentedReport, true);

        Log.softAssertThat(
            buyQuotePage.clickCancelandClosebuttonppPageandVerify("cancel", extentedReport, true),
            "Quote descritpion modal closed after clicking Cancel button and user remains in price presentation page",
            "Quote descritpion modal not closed after clicking Cancel button", driver,
            extentedReport, true);
        Log.softAssertThat(
            buyQuotePage.clickCancelandClosebuttonppPageandVerify("close", extentedReport, true),
            "Quote descritpion modal closed after clicking Close button and user remains in price presentation page",
            "Quote descritpion modal not closed after clicking Close button", driver,
            extentedReport, true);

        buyQuotePage.clickSavebuttonForRenewalQuoteinPPpage("Quote2", extentedReport);

        Log.softAssertThat(
            custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
                custdashboardpage),
            "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
            true);

        HashMap<String, String> hmQuotedetails =
            custdashboardpage.getQuoteVariations(extentedReport, true);
        if (hmQuotedetails.get("quoteStatus_0").toString().contentEquals("R")
            && hmQuotedetails.get("quoteStatus_1").toString().contentEquals("L"))
          Log.pass(
              "Quote variations in customer dashboard page are listed as review required and lapsed",
              extentedReport);
        else
          Log.fail(
              "Quote variations in customer dashboard were not listed listed as review required and lapsed",
              extentedReport);

        // Verify Acceptance page is landed

      } else {
        Log.fail(
            "Failed - NB Policy Created in " + policyDetails.get("Status").toString()
                + " status, unable to proceed with renewal transaction",
            driver, extentedReport, true);
      }
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

  @Test(
      description = "Check the warning message when lapse the renewal quote variation from Acceptance page and check that renewal quote variation is set as a lapse",
      dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
  public void TC_854_855(String browser) throws Exception {

    // Get the web driver instance
    String tcId = "TC_854_855";
    final WebDriver driver = WebDriverFactory.get(browser);
    HashMap<String, String> testData = getTestData(tcId);

    String userName = testData.get("UserName");
    String password = testData.get("Password");
    String description = testData.get("Description");
    Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
    String brandname = testData.get("Brand Name");

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
      Log.message("Logged in User id:" + userName + "& Password:" + password, driver,
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
      HashMap<String, String> custDetails =
          searchPage.enterCustomerDetails(testData, true, extentedReport);

      // Confirm customer details and create customer
      CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
          true);
      Log.softAssertThat(
          custdashboardpage.verifyContactName(testData.get("Title") + " "
              + custDetails.get("First Name") + " " + custDetails.get("Last Name"), extentedReport,
              true),
          "Verified FirstName and LastName on Customer Dashboard",
          "Not Verified Customer Name on Dashboard", driver, extentedReport, true);

      // Create New Quote
      custdashboardpage.clickNewQuote(true, extentedReport);
      custdashboardpage.enterNewQuotePastDate(5, 1, extentedReport, true);
      // Past date for renewal
      custdashboardpage.selectNewQuoteDetails(testData, extentedReport, true);
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
      // Select Payment
      newquotepage.selectPayment(testData, true, extentedReport);

      custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
          true);

      // check for policy status
      HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
      if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {
        Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);
        custdashboardpage.performRenewals_LapseRenewal("Invite Renewal", extentedReport, true);
        newquotepage.clickNextOne(extentedReport);
        newquotepage.clickNextTwo(extentedReport);
        newquotepage.clickView(extentedReport);
        newquotepage.clickAgree(extentedReport);
        newquotepage.clickGetQuote(extentedReport);
        newquotepage.clickQuoteSave("Renew", "Renewal Quote", extentedReport);

        Log.softAssertThat(
            custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
                custdashboardpage),
            "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
            true);

        Log.softAssertThat(
            custdashboardpage.verify_reviewStatus("Review Required", extentedReport, true),
            "Review Required status was displayed after saving a renewal quote",
            "Review Required status was not displayed after saving a renewal quote");

        BuyQuotePage buyQuotePage =
            custdashboardpage.performRenewals_QuoteVariation(extentedReport, true);

        // To verify that acceptance tab selected by default or not
        Log.softAssertThat(buyQuotePage.verifyDefaultTab("Acceptance", extentedReport, true),
            "Acceptance tab was selected by default when clicked on quote variations",
            "Acceptance tab was not selected as default tab when clicked on quote variations",
            driver, extentedReport, true);
        // Click lapse button in acceptance page
        buyQuotePage.clicksave_LapsebuttoninAcceptancepage("", "", "Lapse", extentedReport);
        Log.softAssertThat(
            custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
                custdashboardpage),
            "Navigated to custdashboardpage successfully after quote lapse",
            "Failed to Navigate to custdashboardpage after quote lapse", driver, extentedReport,
            true);
        // Verify the lapsed status of all the quotes
        HashMap<String, String> hmQuotedetails =
            custdashboardpage.getQuoteVariations(extentedReport, true);
        if (hmQuotedetails.get("quoteStatus_0").toString().contentEquals("L")
            && hmQuotedetails.get("quoteStatus_1").toString().contentEquals("L"))
          Log.pass(
              "Quote variations in customer dashboard page are listed as Lapsed after clicking lapse from acceptance page",
              extentedReport);
        else
          Log.fail(
              "Quote variations in customer dashboard were not listed as Lapsed after clicking lapse from acceptance page",
              extentedReport);

      } else {
        Log.fail(
            "Failed - NB Policy Created in " + policyDetails.get("Status").toString()
                + " status, unable to proceed with renewal transaction",
            driver, extentedReport, true);
      }
    } catch (Exception e) {
      Log.exception(e, driver, extentedReport);
    } finally {
      Log.endTestCase(extentedReport);
      driver.quit();
      Log.testCaseResult(extentedReport);
    }
  }

  @Test(
      description = "Check that the renewal quote is saved as a new variation with a status of Reviewed, when it is saved from Acceptance page",
      dataProviderClass = DataProviderUtils.class, dataProvider = "ssBVTDataProvider")
  public void TC_852(String browser) throws Exception {

    // Get the web driver instance
    String tcId = "TC_852";
    final WebDriver driver = WebDriverFactory.get(browser);
    HashMap<String, String> testData = getTestData(tcId);

    String userName = testData.get("UserName");
    String password = testData.get("Password");
    String description = testData.get("Description");
    Log.testCaseInfo(description + "<small><b><i>[" + browser + "]</b></i></small>");
    String brandname = testData.get("Brand Name");

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
      Log.message("Logged in User id:" + userName + "& Password:" + password, driver,
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
      HashMap<String, String> custDetails =
          searchPage.enterCustomerDetails(testData, true, extentedReport);

      // Confirm customer details and create customer
      CustDashboardPage custdashboardpage = searchPage.confirmCreateCustomer(true, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
          true);
      Log.softAssertThat(
          custdashboardpage.verifyContactName(testData.get("Title") + " "
              + custDetails.get("First Name") + " " + custDetails.get("Last Name"), extentedReport,
              true),
          "Verified FirstName and LastName on Customer Dashboard",
          "Not Verified Customer Name on Dashboard", driver, extentedReport, true);

      // Create New Quote
      custdashboardpage.clickNewQuote(true, extentedReport);
      // Past date for renewal
      custdashboardpage.enterNewQuotePastDate(5, 1, extentedReport, true);
      custdashboardpage.selectNewQuoteDetails(testData, extentedReport, true);
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
      // Select Payment
      newquotepage.selectPayment(testData, true, extentedReport);

      custdashboardpage = newquotepage.confirmPayment(testData, extentedReport);
      Log.softAssertThat(
          custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
              custdashboardpage),
          "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
          true);

      // check for policy status
      HashMap<String, String> policyDetails = custdashboardpage.getPolicyURN(extentedReport);
      if (policyDetails.get("Status").toString().equalsIgnoreCase("Accepted")) {
        Log.pass("NB Policy Created Successfully in accepted status", driver, extentedReport, true);
        custdashboardpage.performRenewals_LapseRenewal("Invite Renewal", extentedReport, true);
        newquotepage.clickNextOne(extentedReport);
        newquotepage.clickNextTwo(extentedReport);
        newquotepage.clickView(extentedReport);
        newquotepage.clickAgree(extentedReport);
        newquotepage.clickGetQuote(extentedReport);
        newquotepage.clickQuoteSave("Renew", "Renewal Quote", extentedReport);

        Log.softAssertThat(
            custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
                custdashboardpage),
            "Custdashboardpage Verified", "Custdashboardpage not Verified", driver, extentedReport,
            true);

        Log.softAssertThat(
            custdashboardpage.verify_reviewStatus("Review Required", extentedReport, true),
            "Review Required status was displayed after saving a renewal quote",
            "Review Required status was not displayed after saving a renewal quote");

        BuyQuotePage buyQuotePage =
            custdashboardpage.performRenewals_QuoteVariation(extentedReport, true);

        // To verify that acceptance tab selected by default or not
        Log.softAssertThat(buyQuotePage.verifyDefaultTab("Acceptance", extentedReport, true),
            "Acceptance tab was selected by default when clicked on quote variations",
            "Acceptance tab was not selected as default tab when clicked on quote variations",
            driver, extentedReport, true);
        // Click save button in acceptance page
        buyQuotePage.clicksave_LapsebuttoninAcceptancepage("Review Required", "quoteDescrption",
            "Save", extentedReport);
        Log.softAssertThat(
            custdashboardpage.uielement.verifyPageElements(Arrays.asList("lnkEditDetails"),
                custdashboardpage),
            "Navigated to custdashboardpage successfully after quote lapse",
            "Failed to Navigate to custdashboardpage after quote lapse", driver, extentedReport,
            true);
        // Verify the lapsed status of all the quotes
        HashMap<String, String> hmQuotedetails =
            custdashboardpage.getQuoteVariations(extentedReport, true);
        if (hmQuotedetails.get("quoteStatus_0").toString().contentEquals("R")
            && hmQuotedetails.get("quoteStatus_1").toString().contentEquals("L"))
          Log.pass(
              "Renew Quote variations in customer dashboard page are listed as review required and lapsed",
              extentedReport);
        else
          Log.fail(
              "Renew Quote variations in customer dashboard were not listed listed as review required and lapsed",
              extentedReport);

      } else {
        Log.fail(
            "Failed - NB Policy Created in " + policyDetails.get("Status").toString()
                + " status, unable to proceed with renewal transaction",
            driver, extentedReport, true);
      }

    } catch (Exception e) {
      Log.exception(e, driver, extentedReport);
    } finally {
      Log.endTestCase(extentedReport);
      driver.quit();
      Log.testCaseResult(extentedReport);
    }
  }
}
