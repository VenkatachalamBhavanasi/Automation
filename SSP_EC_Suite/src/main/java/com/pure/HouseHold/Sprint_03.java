package com.pure.HouseHold;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

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
import com.pure.selfservice.pages.AddBuildingCoverPage;
import com.pure.selfservice.pages.AddContentsCoverPage;
import com.pure.selfservice.pages.AddSpecifiedItemPage;
import com.pure.selfservice.pages.CustomerDashboardPage;
import com.pure.selfservice.pages.CustomerServicePage;
import com.pure.selfservice.pages.CustomerSignInPage;
import com.pure.selfservice.pages.GetTestData;
import com.pure.selfservice.pages.MTAInitiatePage;
import com.pure.selfservice.pages.MTAQuoteDetailsPage;
import com.relevantcodes.extentreports.ExtentTest;

@Listeners(EmailReport.class)
public class Sprint_03 extends BaseTest {
	private String webSite;
	String sheetName = "Sprint_03_";

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
	
	/** 10000 Story **/

	@Test(groups = {
	"Smoke" },description = "Verfiy that 'Add building' option is available in Change My Policy screen and  clicking on 'Continue' button after selecting Add building option opens up Add building main screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_019(String browser) throws Exception {

		String tcId = "TC_10956_10957";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("changeMyProperty"), mTAInitiatePage),
					"Change My Property cover is displayed in MTA intiate page", "Change My Property cover is not displayed in MTA intiate page", driver, extentedReport, true);

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addBuildingsCover"), mTAInitiatePage),
					"Add Buildings Cover cover is displayed in MTA intiate page", "Add Buildings Cover cover is not displayed in MTA intiate page", driver, extentedReport, true);

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addSpecifiedItems"), mTAInitiatePage),
					"Add Specified Items cover is displayed in MTA intiate page", "Add Specified Items cover is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"),
					addBuildingCoverPage), "Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
	"Smoke" },description = "Verify that user is able to click Quote button is enable after entering the mandatory test data and check  Insurance Premium shone in quote page when try to change Change my proprty", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_020(String browser) throws Exception {

		String tcId = "TC_10958_10961_10962";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"),
					addBuildingCoverPage), "Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance,
					extentedReport, true);

			// Click Quote button
			addBuildingCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
					mTAQuoteDetailsPage), "Insurance Premium amount is displayed when try to Change my proprty on House hold Policy for change type : "
					+ testData.typeOfChange, "Insurance Premium amount is not displayed when try to Change my proprty on House hold Policy for change type : "
					+ testData.typeOfChange, driver, extentedReport);

			mTAQuoteDetailsPage.clickCancelButton(extentedReport);
			mTAQuoteDetailsPage.selectOk(extentedReport);

			Log.softAssertThat(customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"), 
					customerDashboardPage), "On clicking 'CANCEL' button redirected to Policy Detail Screen", 
					"On clicking 'CANCEL' button did not redirect to Policy Detail Screen", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
	"Smoke" },description = "Verfiy that clicking on Cancel button on 'Add building' main screen navigates the user to Change My Policy screen without any changes", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_021(String browser) throws Exception {

		String tcId = "TC_10959_10960_15572";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat( addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"),
					addBuildingCoverPage), "Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport, true);
			addBuildingCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addBuildingCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue, extentedReport, true);

			// Click Quote button
			addBuildingCoverPage.clickCancelButton(extentedReport, true);

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange"), mTAInitiatePage),
					"On clicking on Cancel button on 'Change my proprty' main screen navigates the user to Change My Policy screen without any change",
					"On clicking on Cancel button on 'Change my proprty' main screen did not navigates the user to Change My Policy screen without any change",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
	"Smoke" },description = "Check that clicking on 'Add another policy change' button within 'Add building' screen takes the user back to Change My Policy screen with the changes saved and edit button is displays", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_022(String browser) throws Exception {

		String tcId = "TC_10963_10964";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"), addBuildingCoverPage),
					"Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance,
					extentedReport, true);

			// Click Quote button
			addBuildingCoverPage.clickAddAnotherPolicyChangeButton(extentedReport, true);

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange", "btnEditAddBuildingsCover"), mTAInitiatePage),
					"On clicking on 'Add another policy change' button within 'Change my proprty' screen takes the user back to Change My Policy screen and edit button displayed on the amendment",
					"On clicking on 'Add another policy change' button within 'Change my proprty' screen did not takes the user back to Change My Policy screen and edit button displayed on the amendment",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that the help text for each field is available", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_023(String browser) throws Exception {

		String tcId = "TC_10966";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"),
					addBuildingCoverPage), "Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpTitleAddBuildingCover"),
					addBuildingCoverPage), "Help icon (?) is displayed against the 'Title Add Buildings Cover' text in Add Buildings Cover",
					"Help icon (?) is not displayed against the 'Title Add Buildings Cover' text in Add Buildings Cover",
					driver, extentedReport);

			addBuildingCoverPage.hoverOnHelpIconOfTitleAddBuildingCover(extentedReport, true);
			
			Log.softAssertThat(addBuildingCoverPage.verifyHelpIconTitleAddBuildingCoverPopupMessage(),
					addBuildingCoverPage.POPUP_MSG_HELPICON_Title_Add_Building_Cover
					+ " - Message is displayed when click/hover the help icon (?) against the 'Title Add Buildings Cover' text",
					addBuildingCoverPage.POPUP_MSG_HELPICON_Title_Add_Building_Cover
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Title Add Buildings Cover' text",
					driver, extentedReport);

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpRebuildCost"),
					addBuildingCoverPage), "Help icon (?) is displayed against the 'Rebuild Cost' field in Add Buildings Cover",
					"Help icon (?) is not displayed against the 'Title Add Buildings Cover' field in Add Buildings Cover",
					driver, extentedReport);

			addBuildingCoverPage.hoverOnHelpIconOfRebuildCost(extentedReport, true);
			
			Log.softAssertThat(addBuildingCoverPage.verifyHelpIconRebuildCostPopupMessage(),
					addBuildingCoverPage.POPUP_MSG_HELPICON_Rebuild_Cost
					+ " - Message is displayed when click/hover the help icon (?) against the 'Rebuild Cost' field",
					addBuildingCoverPage.POPUP_MSG_HELPICON_Rebuild_Cost
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Rebuild Cost' field",
					driver, extentedReport);

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpAccidentalDamage"),
					addBuildingCoverPage), "Help icon (?) is displayed against the 'Accidental Damage' button in Add Buildings Cover",
					"Help icon (?) is not displayed against the 'Accidental Damage' button in Add Buildings Cover",
					driver, extentedReport);

			addBuildingCoverPage.hoverOnHelpIconOfAccidentalDamage(extentedReport, true);
			
			Log.softAssertThat(addBuildingCoverPage.verifyHelpIconAccidentalDamagePopupMessage(),
					addBuildingCoverPage.POPUP_MSG_HELPICON_Accidental_Damage
					+ " - Message is displayed when click/hover the help icon (?) against the 'Accidental Damage' button",
					addBuildingCoverPage.POPUP_MSG_HELPICON_Accidental_Damage
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Accidental Damage' button",
					driver, extentedReport);

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpTotalExcessRequired"),
					addBuildingCoverPage), "Help icon (?) is displayed against the 'Total Excess Requirede' field in Add Buildings Cover",
					"Help icon (?) is not displayed against the 'Total Excess Requirede' field in Add Buildings Cover",
					driver, extentedReport);

			addBuildingCoverPage.hoverOnHelpIconOfTotalExcessRequired(extentedReport, true);
			
			Log.softAssertThat(addBuildingCoverPage.verifyHelpIconTotalExcessRequiredePopupMessage(),
					addBuildingCoverPage.POPUP_MSG_HELPICON_Total_Excess_Required
					+ " - Message is displayed when click/hover the help icon (?) against the 'Total Excess Requirede' field",
					addBuildingCoverPage.POPUP_MSG_HELPICON_Total_Excess_Required
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Total Excess Requirede' field",
					driver, extentedReport);

			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport, true);

			Log.softAssertThat( addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpPreviousBuildingInsurance"), addBuildingCoverPage),
					"Help icon (?) is displayed against the 'Previous Building Insurance' button in Add Buildings Cover",
					"Help icon (?) is not displayed against the 'Previous Building Insurance' button in Add Buildings Cover",
					driver, extentedReport);

			addBuildingCoverPage.hoverOnHelpIconOfPreviousBuildingInsurance(extentedReport, true);
			
			Log.softAssertThat(addBuildingCoverPage.verifyHelpIconPreviousBuildingInsurancePopupMessage(),
					addBuildingCoverPage.POPUP_MSG_HELPICON_Previous_Building_Insurance
					+ " - Message is displayed when click/hover the help icon (?) against the 'Previous Building Insurance' button",
					addBuildingCoverPage.POPUP_MSG_HELPICON_Previous_Building_Insurance
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Previous Building Insurance' button",
					driver, extentedReport);

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpNameOfPreviousInsurer"),
					addBuildingCoverPage), "Help icon (?) is displayed against the 'Name Of Previous Insurer' field in Add Buildings Cover",
					"Help icon (?) is not displayed against the 'Name Of Previous Insurer' field in Add Buildings Cover",
					driver, extentedReport);

			addBuildingCoverPage.hoverOnHelpIconOfNameOfPreviousInsurer(extentedReport, true);
			
			Log.softAssertThat(addBuildingCoverPage.verifyHelpIconNameOfPreviousInsurerPopupMessage(),
					addBuildingCoverPage.POPUP_MSG_HELPICON_Name_Of_Previous_Insurer
					+ " - Message is displayed when click/hover the help icon (?) against the 'Name Of Previous Insurer' field",
					addBuildingCoverPage.POPUP_MSG_HELPICON_Name_Of_Previous_Insurer
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Name Of Previous Insurer' field",
					driver, extentedReport);

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpPolicyNumberOfPreviousInsurance"), addBuildingCoverPage),
					"Help icon (?) is displayed against the 'Number Of Previous Insurance' field in Add Buildings Cover",
					"Help icon (?) is not displayed against the 'Number Of Previous Insurance' field in Add Buildings Cover",
					driver, extentedReport);

			addBuildingCoverPage.hoverOnHelpIconOfPolicyNumberOfPreviousInsurance(extentedReport, true);
			
			Log.softAssertThat(addBuildingCoverPage.verifyHelpIconPolicyNumberOfPreviousInsurancePopupMessage(),
					addBuildingCoverPage.POPUP_MSG_HELPICON_Policy_Number_Of_Previous_Insurance
					+ " - Message is displayed when click/hover the help icon (?) against the 'Number Of Previous Insurance' field",
					addBuildingCoverPage.POPUP_MSG_HELPICON_Policy_Number_Of_Previous_Insurance
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Number Of Previous Insurance' field",
					driver, extentedReport);

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpExpiryDateOfPreviousInsurance"), addBuildingCoverPage),
					"Help icon (?) is displayed against the 'Expiry Date Of Previous Insurance' field in Add Buildings Cover",
					"Help icon (?) is not displayed against the 'Expiry Date Of Previous Insurance' field in Add Buildings Cover",
					driver, extentedReport);

			addBuildingCoverPage.hoverOnHelpIconOfExpiryDateOfPreviousInsurance(extentedReport, true);
			
			Log.softAssertThat(addBuildingCoverPage.verifyHelpIconExpiryDateOfPreviousInsurancePopupMessage(),
					addBuildingCoverPage.POPUP_MSG_HELPICON_Expiry_Date_Of_Previous_Insurance
					+ " - Message is displayed when click/hover the help icon (?) against the 'Expiry Date Of Previous Insurance' field",
					addBuildingCoverPage.POPUP_MSG_HELPICON_Expiry_Date_Of_Previous_Insurance
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Expiry Date Of Previous Insurance' field",
					driver, extentedReport);

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpPayForYourPreviousInsurance"), addBuildingCoverPage),
					"Help icon (?) is displayed against the 'Pay For Your Previous Insurance' field in Add Buildings Cover",
					"Help icon (?) is not displayed against the 'Pay For Your Previous Insurance' field in Add Buildings Cover",
					driver, extentedReport);

			addBuildingCoverPage.hoverOnHelpIconOfPayForYourPreviousInsurance(extentedReport, true);
			
			Log.softAssertThat(addBuildingCoverPage.verifyHelpIconPayForYourPreviousInsurancePopupMessage(),
					addBuildingCoverPage.POPUP_MSG_HELPICON_Pay_For_Your_Previous_Insurance
					+ " - Message is displayed when click/hover the help icon (?) against the 'Pay For Your Previous Insurance' field",
					addBuildingCoverPage.POPUP_MSG_HELPICON_Pay_For_Your_Previous_Insurance
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Pay For Your Previous Insurance' field",
					driver, extentedReport);

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpNoOfYearsWithoutClaiming"),
					addBuildingCoverPage),"Help icon (?) is displayed against the 'No Of Years Without Claiming' field in Add Buildings Cover",
					"Help icon (?) is not displayed against the 'No Of Years Without Claiming' field in Add Buildings Cover",
					driver, extentedReport);

			addBuildingCoverPage.hoverOnHelpIconOfNoOfYearsWithoutClaiming(extentedReport, true);
			
			Log.softAssertThat(addBuildingCoverPage.verifyHelpIconNoOfYearsWithoutClaimingPopupMessage(),
					addBuildingCoverPage.POPUP_MSG_HELPICON_No_Of_Years_Without_Claiming
					+ " - Message is displayed when click/hover the help icon (?) against the 'No Of Years Without Claiming' field",
					addBuildingCoverPage.POPUP_MSG_HELPICON_No_Of_Years_Without_Claiming
					+ " - Message is not displayed when click/hover the help icon (?) against the 'No Of Years Without Claiming' field",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that selecting 'YES' button in 'Have you previously held buildings insurance with a different provider?' should display the buildinginsurance details question then user should abel to enter the detailsand verfiy that selecting on 'No' all details are cleared", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_024(String browser) throws Exception {

		String tcId = "TC_10967_10968";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList("27034728", extentedReport, true);
			customerDashboardPage.clickChangePolicyButton("27034728", extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(
					addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"),
							addBuildingCoverPage),
					"Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.arrPreviousBuildingInsurance[0],
					extentedReport, true);
			addBuildingCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addBuildingCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance,
					extentedReport, true);
			addBuildingCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance,
					extentedReport, true);
			addBuildingCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport,
					true);
			addBuildingCoverPage.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.arrPreviousBuildingInsurance[1],
					extentedReport, true);
			Log.softAssertThat(!addBuildingCoverPage.VerfiyPreviouslyBuildingInsurancesQeustions(extentedReport, true),
					"Previously Contents Insurances Qeustions are dispalyed after clicking no button",
					"Previously Contents Insurances Qeustions are not dispalyed after clicking no button", driver,
					extentedReport, true);

			Log.softAssertThat(!addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("drpdwnNameOfPreviousInsurer",
							"fldPolicyNumberOfPreviousInsurance", "fldExpiryDateOfPreviousInsurance", "drpdwnPayForYourPreviousInsurance", 
							"drpdwnNoOfYearsWithoutClaiming"), addBuildingCoverPage),
					"On clicking on 'NO' button of 'previously held buildings insurance with a different provider' within 'Change my proprty' screen doesn't display the previously held buildings insurance details",
					"On clicking on 'NO' button of 'previously held buildings insurance with a different provider' within 'Change my proprty' screen  display the 'Name of previous insurer' dropdown",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verfiy that 'Add building' details are updated correctly in Puer back office. When policy in MTA Quote", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_025(String browser) throws Exception {

		String tcId = "TC_15435";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"),
					addBuildingCoverPage), "Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport, true);

			// Click Quote button
			addBuildingCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Change my proprty on House hold Policy for change type : " + testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to Change my proprty on House hold Policy for change type : " + testData.typeOfChange,
					driver, extentedReport);

			mTAQuoteDetailsPage.clickCancelButton(extentedReport);
			mTAQuoteDetailsPage.selectOk(extentedReport);

			Log.softAssertThat(customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"), customerDashboardPage),
					"On clicking 'CANCEL' button redirected to Policy Detail Screen",
					"On clicking 'CANCEL' button did not redirect to Policy Detail Screen", driver, extentedReport);

			Log.message("Verfiy that Building details are updened with Contents details in Backoffice and Building details are shown correctly");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
	"Smoke" },description = "Verify that user is able to edit the saved details in Add buildings cover page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_026(String browser) throws Exception {

		String tcId = "TC_10965";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"),
					addBuildingCoverPage),"Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport, true);
			addBuildingCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addBuildingCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue, extentedReport, true);

			// Click Add Another Policy Change button
			addBuildingCoverPage.clickAddAnotherPolicyChangeButton(extentedReport, true);

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange", "btnEditAddBuildingsCover"), mTAInitiatePage),
					"On clicking on 'Add another policy change' button within 'Change my proprty' screen takes the user back to Change My Policy screen and edit button displayed on the amendment",
					"On clicking on 'Add another policy change' button within 'Change my proprty' screen did not takes the user back to Change My Policy screen and edit button displayed on the amendment",
					driver, extentedReport);

			mTAInitiatePage.clickEditChangesBtn("Add buildings cover", extentedReport, true);

			HashMap<String, String> expectedAddBuildingsDetails = new HashMap<String, String>();
			expectedAddBuildingsDetails.put("RebuildCost", testData.rebuildCost);
			expectedAddBuildingsDetails.put("ExcessValue", testData.excessvalue);
			expectedAddBuildingsDetails.put("NameOfPreviousInsurance", testData.nameOfPreviousInsurer);
			expectedAddBuildingsDetails.put("NumberOfPreviousInsurance", testData.numberOfPreviousInsurance);
			expectedAddBuildingsDetails.put("ExpiryDateOfPreviousInsurance", testData.expiryDateOfPreviousInsurance);
			expectedAddBuildingsDetails.put("PayForYourPreviousInsurance", testData.payForYourPreviousInsurance);
			expectedAddBuildingsDetails.put("NoOfYearsWithoutClaimingValue", testData.noOfYearsWithoutClaimingValue);

			Log.softAssertThat(addBuildingCoverPage.verifyDetailInAddBuildingsDetailSection(expectedAddBuildingsDetails, extentedReport),
					"Add building details are verified", "Add building details are not verified", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the maximum field length for What is the rebuild cost of your home", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_092(String browser) throws Exception {

		String tcId = "TC_19286";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			addBuildingCoverPage.verifyMaximumFieldLength(extentedReport, true);
			
			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("fldRebuildCost"),
					addBuildingCoverPage), "Maximum field length for What is the rebuild cost of your home is validated",
					"maximum field length for What is the rebuild cost of your home is not validated", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the Date value entry format for Expiry date of previous insurance", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_093(String browser) throws Exception {

		String tcId = "TC_19290";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"),
					addBuildingCoverPage), "Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport, true);
			addBuildingCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addBuildingCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance, extentedReport, true);
			
			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("fldExpiryDateOfPreviousInsurance"), 
					addBuildingCoverPage), "Date value entry format for Expiry date of previous insurance is validated",
					"Date value entry format for Expiry date of previous insurance is not validated", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that user is shown with the Additional premium when try to change Add Buildings Cover", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_101(String browser) throws Exception {

		String tcId = "TC_19292";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"),
					addBuildingCoverPage), "Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport, true);

			// Click Quote button
			addBuildingCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Additional Premium amount is displayed when try to Change my proprty on House hold Policy for change type : " + testData.typeOfChange,
					"Additional Premium amount is not displayed when try to Change my proprty on House hold Policy for change type : " + testData.typeOfChange,
					driver, extentedReport);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the Add building cover for PPL house hold policy with 'Broker Led’", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_104(String browser) throws Exception {

		String tcId = "TC_19279";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"), addBuildingCoverPage),
					"Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport, true);

			// Click Quote button
			addBuildingCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Broker Led Premium amount is displayed when try to Change my proprty on House hold Policy for change type : "
					+ testData.typeOfChange, "Broker Led Premium amount is not displayed when try to Change my proprty on House hold Policy for change type : "
					+ testData.typeOfChange, driver, extentedReport);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "Verify the Add building cover for PPL house hold policy with 'Insurer Led'.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_106(String browser) throws Exception {

		String tcId = "TC_19278";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"), addBuildingCoverPage),
					"Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport, true);

			// Click Quote button
			addBuildingCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurer Led Premium amount is displayed when try to Change my proprty on House hold Policy for change type : "
					+ testData.typeOfChange, "Insurer Led Premium amount is not displayed when try to Change my proprty on House hold Policy for change type : "
					+ testData.typeOfChange, driver, extentedReport);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "Verify the Add building cover when user buy the MTA quote from pure application where it has been submitted from web", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_109(String browser) throws Exception {

		String tcId = "TC_19277";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"), addBuildingCoverPage),
					"Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport, true);

			// Click Quote button
			addBuildingCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Additional Premium amount is displayed when try to Change my proprty on House hold Policy for change type : "
					+ testData.typeOfChange, "Additional Premium amount is not displayed when try to Change my proprty on House hold Policy for change type : "
					+ testData.typeOfChange, driver, extentedReport);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	/** 9896 Story **/

	@Test(description = "Verify excess details in Quote details after Adding Building cover", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_049(String browser) throws Exception {

		String tcId = "TC_10974";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"), addBuildingCoverPage),
					"Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport, true);
			addBuildingCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addBuildingCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue, extentedReport, true);

			// Click Add Another Policy Change button
			addBuildingCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();

			Log.softAssertThat(mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
					+ testData.typeOfChange, "Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
					+ testData.typeOfChange, driver, extentedReport);

			mTAQuoteDetailsPage.getPolicyExcessesTableDetails(extentedReport, true);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);

			mTAQuoteDetailsPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(description = "Verify excess details in Quote details after Adding Add Contents cover", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_050(String browser) throws Exception {

		String tcId = "TC_10975";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"), addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button", "Add Contents cover page is not opens after clicking Continue button", 
					driver, extentedReport, true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems, extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired, extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance, extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport, true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome, extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, extentedReport, true);

			// Click Quote button
			addContentsCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
					+ testData.typeOfChange, "Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
					+ testData.typeOfChange, driver, extentedReport);

			mTAQuoteDetailsPage.getPolicyExcessesTableDetails(extentedReport, true);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);

			mTAQuoteDetailsPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the details in Quote details after Adding house hold policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_051(String browser) throws Exception {

		String tcId = "TC_10969_10970_10971_10972";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			AddSpecifiedItemPage addSpecifiedItemPage = new AddSpecifiedItemPage(driver, extentedReport).get();

			Log.softAssertThat(addSpecifiedItemPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddSpecifiedItems"),
					addSpecifiedItemPage), "Add Specified Items cover page is opens after clicking Continue button",
					"Add Specified Items cover page is not opens after clicking Continue button", driver, extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome, extentedReport, true);
			addSpecifiedItemPage.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport, true);

			addSpecifiedItemPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, extentedReport, true);

			addSpecifiedItemPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Spcified Item Cover on House hold Policy for change type : "
					+ testData.typeOfChange, "Insurance Premium amount is not displayed when try to Add Spcified Item on House hold Policy for change type : "
					+ testData.typeOfChange, driver, extentedReport);

			mTAQuoteDetailsPage.getPolicyExcessesTableDetails(extentedReport, true);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);

			mTAQuoteDetailsPage.clickBuyButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(description = "Verify generated MTA Quote details in Back office", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_052(String browser) throws Exception {

		String tcId = "TC_10977_15436";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(
					addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"),
							addBuildingCoverPage),
					"Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport, true);
			addBuildingCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addBuildingCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue, extentedReport, true);

			// Click Add Another Policy Change button
			addBuildingCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();

			Log.softAssertThat(mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
					+ testData.typeOfChange, "Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
					+ testData.typeOfChange, driver, extentedReport);

			mTAQuoteDetailsPage.getPolicyExcessesTableDetails(extentedReport, true);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);

			mTAQuoteDetailsPage.clickBuyButton(extentedReport, true);

			Log.message("Verifye the Quote details are updated in Back office");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(description = "Verify Help Text in Quote details", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_053(String browser) throws Exception {

		String tcId = "TC_10973";
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

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Building details
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"),
					addBuildingCoverPage), "Change my proprty page is opens after clicking Continue button",
					"Change my proprty page is not opens after clicking Continue button", driver, extentedReport, true);

			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, true);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport, true);
			addBuildingCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addBuildingCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport, true);
			addBuildingCoverPage.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue, extentedReport, true);

			// Click Add Another Policy Change button
			addBuildingCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();

			Log.softAssertThat(mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : " + testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "+ testData.typeOfChange,
					driver, extentedReport);

			Log.softAssertThat(mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("iconHelpQuoteDetails"), mTAQuoteDetailsPage),
					"Help icon (?) is displayed against the 'Title Quote Details' text in Quote Details",
					"Help icon (?) is not displayed against the 'Title Quote Details' text in Quote Details", driver, extentedReport);

			mTAQuoteDetailsPage.hoverOnHelpIQuoteDetails(extentedReport, true);
			
			Log.softAssertThat(mTAQuoteDetailsPage.verifyHelpIconQuoteDetails(),
					mTAQuoteDetailsPage.POPUP_MSG_HELPICON_Quote_Details
					+ " - Message is displayed when click/hover the help icon (?) against the 'Title Quote Details' text",
					mTAQuoteDetailsPage.POPUP_MSG_HELPICON_Quote_Details
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Title Quote Details' text",
					driver, extentedReport);

			Log.softAssertThat(mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("iconHelpYourPolicyExcesses"), mTAQuoteDetailsPage),
					"Help icon (?) is displayed against the 'Your Policy Excesses' text in Quote Details",
					"Help icon (?) is not displayed against the 'Your Policy Excesses' text in Quote Details", driver, extentedReport);

			mTAQuoteDetailsPage.hoverOnHelpIYourPolicyExcesses(extentedReport, true);
			
			Log.softAssertThat(mTAQuoteDetailsPage.verifyHelpIconYourPolicyExcesses(), mTAQuoteDetailsPage.POPUP_MSG_HELPICON_Your_Policy_Excesses
					+ " - Message is displayed when click/hover the help icon (?) against the 'Your Policy Excesses' text",
					mTAQuoteDetailsPage.POPUP_MSG_HELPICON_Your_Policy_Excesses
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Your Policy Excesses' text", driver, extentedReport);

			Log.softAssertThat(mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("iconHelpTermsAndConditions"), mTAQuoteDetailsPage), 
					"Help icon (?) is displayed against the 'Terms And Conditions' text in Quote Details",
					"Help icon (?) is not displayed against the 'Terms And Conditions' text in Quote Details", driver, extentedReport);

			mTAQuoteDetailsPage.hoverOnHelpITermsAndConditions(extentedReport, true);
			
			Log.softAssertThat(mTAQuoteDetailsPage.verifyHelpIconTermsAndConditions(), mTAQuoteDetailsPage.POPUP_MSG_HELPICON_Terms_And_Conditions
					+ " - Message is displayed when click/hover the help icon (?) against the 'Terms And Conditions' text",
					mTAQuoteDetailsPage.POPUP_MSG_HELPICON_Terms_And_Conditions
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Terms And Conditions' text", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

}