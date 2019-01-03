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
public class Sprint_04 extends BaseTest {
	private String webSite;
	String sheetName = "Sprint_04_";

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
	
	/** 10001 Story **/

	@Test(groups = {
	"Smoke" },description = "Verfiy that 'Add Contents cover' option is available in Change My Policy screen and  clicking on 'Continue' button after selecting Add Contents cover option opens up Add Contents cover main screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_027(String browser) throws Exception {

		String tcId = "TC_15782";
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

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("changeMyProperty"), mTAInitiatePage),
					"Change My Property cover is displayed in MTA intiate page",
					"Change My Property cover is not displayed in MTA intiate page", driver, extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addContentsCover"), mTAInitiatePage),
					"Add Contents Cover cover is displayed in MTA intiate page",
					"Add Contents Cover cover is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Add Contents cover details
			AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
	"Smoke" },description = "Verify that user is able to click Quote button is enable after entering the mandatory test data and check  Insurance Premium shone in quote page when try to change Add Contents cover", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_028(String browser) throws Exception {

		String tcId = "TC_15783_15784_15785_15787_15803_15835";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, 
					extentedReport, true);

			// Click Quote button
			addContentsCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
									+ testData.typeOfChange,
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
	"Smoke" },description = "Verfiy that clicking on Cancel button on 'Add Contents cover' main screen navigates the user to Change My Policy screen without any changes", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_029(String browser) throws Exception {

		String tcId = "TC_15786_15792_15793_15794__15795_15796_15797_15798_15799_15800";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addContentsCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance,
					extentedReport, true);
			addContentsCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance,
					extentedReport, true);
			addContentsCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport,
					true);
			addContentsCoverPage.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);

			// Click Cancel button
			addContentsCoverPage.clickCancelButton(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange"),
							mTAInitiatePage),
					"On clicking on Cancel button on 'Add contents cover' main screen navigates the user to Change My Policy screen without any change",
					"On clicking on Cancel button on 'Add contents cover' main screen did not navigates the user to Change My Policy screen without any change",
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
	"Smoke" },description = "Check that clicking on 'Add another policy change' button within 'Add Contents cover' screen takes the user back to Change My Policy screen with the changes saved and edit button is displays", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_030(String browser) throws Exception {

		String tcId = "TC_15801_15802_15804_15806_15807_15809_15813_15816_15817_15819_15823";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH,
					testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(
					testData.addPersonalPossessionsOutsideHome, extentedReport, true);
			addContentsCoverPage.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);

			// Click Add Another policy change
			addContentsCoverPage.clickAddAnotherPolicyChangeButton(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChooseTypeOfChange", "btnEditAddContentsCover"), mTAInitiatePage),
					"On clicking on 'Add another policy change' button within 'Add Contents Cover' screen takes the user back to Change My Policy screen and edit button displayed on the amendment",
					"On clicking on 'Add another policy change' button within 'Add Contents Cover' screen did not takes the user back to Change My Policy screen and edit button displayed on the amendment",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that user is able to add more then one item in Specified item within home and outside the home", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_041(String browser) throws Exception {

		String tcId = "TC_15812_15814_15822_15824";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.arrpreviouslyContentsInsurance[0],
					extentedReport, true);
			addContentsCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addContentsCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance,
					extentedReport, true);
			addContentsCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance,
					extentedReport, true);
			addContentsCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport,
					true);
			addContentsCoverPage.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.arrpreviouslyContentsInsurance[1],
					extentedReport, true);

			Log.softAssertThat(addContentsCoverPage.VerfiyPreviouslyContentsInsurancesQeustions(extentedReport),
					"Previously Contents Insurances Qeustions are not dispalyed after clicking no button",
					"Previously Contents Insurances Qeustions are dispalyed after clicking no button", driver,
					extentedReport, true);

			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.addSpecifiedItemwithinHome(testData.arritemTypeWH[0], testData.arritemValueWH[0],
					testData.arritemDecsriptionWH[0], testData.arrsaveAndCancelWH[0], extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[0], extentedReport,
							true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[0],
					extentedReport, true);
			addContentsCoverPage.addSpecifiedItemwithinHome(testData.arritemTypeWH[1], testData.arritemValueWH[1],
					testData.arritemDecsriptionWH[1], testData.arrsaveAndCancelWH[1], extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[1], extentedReport,
							true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[1],
					extentedReport, true);
			addContentsCoverPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(
					testData.addPersonalPossessionsOutsideHome, extentedReport, true);
			addContentsCoverPage.addSpecifiedItemOutsideHome(testData.arritemTypeOH[0], testData.arritemValueOH[0],
					testData.arritemDecsriptionOH[0], testData.arrsaveAndCancelOH[0], extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInOutsideHome(testData.arritemNameOH[0], extentedReport,
							true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInOutsideHome(testData.arraddAnotherItemInOutsideHome[0],
					extentedReport, true);

			addContentsCoverPage.addSpecifiedItemOutsideHome(testData.arritemTypeOH[1], testData.arritemValueOH[1],
					testData.arritemDecsriptionOH[1], testData.arrsaveAndCancelOH[1], extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInOutsideHome(testData.arritemNameOH[1], extentedReport,
							true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInOutsideHome(testData.arraddAnotherItemInOutsideHome[1],
					extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that 'Add Contents cover' details are updated correctly in Pure back office. When policy in MTA Quote", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_042(String browser) throws Exception {

		String tcId = "TC_15790_15791_15825";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addContentsCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance,
					extentedReport, true);
			addContentsCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance,
					extentedReport, true);
			addContentsCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport,
					true);
			addContentsCoverPage.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH,
					testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(
					testData.addPersonalPossessionsOutsideHome, extentedReport, true);
			addContentsCoverPage.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);

			// Click Quote button
			addContentsCoverPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);

			mTAQuoteDetailsPage.clickCancelButton(extentedReport);
			mTAQuoteDetailsPage.selectOk(extentedReport);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"On clicking 'CANCEL' button redirected to Policy Detail Screen",
					"On clicking 'CANCEL' button did not redirect to Policy Detail Screen", driver, extentedReport);

			Log.message(
					"Verify that Content details are updated with Building details in Back office and Content details are shown correctly");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
	"Smoke" },description = "Verify that clicking on 'Edit' button in table should open the added specified Item to modify or change under Specified Items in within and outside the Home", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_043(String browser) throws Exception {

		String tcId = "TC_15810_15820";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.addSpecifiedItemwithinHome(testData.arritemTypeWH[0], testData.arritemValueWH[0],
					testData.arritemDecsriptionWH[0], testData.arrsaveAndCancelWH[0], extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[0], extentedReport,
							true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addContentsCoverPage.editAddItemWithinHome(testData.arritemNameWH[0], testData.arritemTypeWH[1],
					testData.arritemValueWH[1], testData.arritemDecsriptionWH[1], testData.arrsaveAndCancelWH[1],
					extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[1], extentedReport,
							true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(
					testData.addPersonalPossessionsOutsideHome, extentedReport, true);
			addContentsCoverPage.addSpecifiedItemOutsideHome(testData.arritemTypeOH[0], testData.arritemValueOH[0],
					testData.arritemDecsriptionOH[0], testData.arrsaveAndCancelOH[0], extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInOutsideHome(testData.arritemNameOH[0], extentedReport,
							true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addContentsCoverPage.editAddItemOutsideHome(testData.arritemNameOH[0], testData.arritemTypeOH[1],
					testData.arritemValueOH[1], testData.arritemDecsriptionOH[1], testData.arrsaveAndCancelOH[1],
					extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInOutsideHome(testData.arritemNameOH[1], extentedReport,
							true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
	"Smoke" },description = "Verify that clicking on 'Delete' button in table should delete the added specified Item under Specified Items within and Outside the Home", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_044(String browser) throws Exception {

		String tcId = "TC_15811_15821";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH,
					testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addContentsCoverPage.deleteAddItemInWithinHome(testData.itemNameWH, testData.deleteItemWH, extentedReport,
					true);

			Log.softAssertThat(
					!addContentsCoverPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is Deleted in Within Home", "Add Specified item is not Deleted within Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(
					testData.addPersonalPossessionsOutsideHome, extentedReport, true);
			addContentsCoverPage.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addContentsCoverPage.deleteAddItemInOutsideHome(testData.itemNameOH, testData.deleteItemOH, extentedReport,
					true);

			Log.softAssertThat(
					!addContentsCoverPage.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is Deleted outside Home ", "Add Specified item is not Deleted outside Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verfiy that user should able to expand and collapse the Contents information session after giveing all Mandatory filed and try to edit Contents information", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_045(String browser) throws Exception {

		String tcId = "TC_15826_15827_15828_15829_15830_15834";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH,
					testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(
					testData.addPersonalPossessionsOutsideHome, extentedReport, true);
			addContentsCoverPage.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);

			addContentsCoverPage.verifyExpandAndCollapseTheAcadians(testData.addAnotherItemInWithinHome, extentedReport,
					true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verfiy that user should able to selecte cancel while adding the specified items", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_046(String browser) throws Exception {

		String tcId = "TC_15805_15808_15815_15818";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH,
					testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					!addContentsCoverPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);
			addContentsCoverPage.headersVerificationForTableInWithinHome(extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(
					testData.addPersonalPossessionsOutsideHome, extentedReport, true);
			addContentsCoverPage.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					!addContentsCoverPage.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addContentsCoverPage.headersVerificationForTableInOutsideHome(extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
	"Smoke" },description = "Verify that user is able to edit the saved details in Add Contents cover page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_047(String browser) throws Exception {

		String tcId = "TC_15788";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addContentsCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance,
					extentedReport, true);
			addContentsCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance,
					extentedReport, true);
			addContentsCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport,
					true);
			addContentsCoverPage.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH,
					testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(
					testData.addPersonalPossessionsOutsideHome, extentedReport, true);
			addContentsCoverPage.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);

			// Click Add Another Policy Change button
			addContentsCoverPage.clickAddAnotherPolicyChangeButton(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChooseTypeOfChange", "btnEditAddContentsCover"), mTAInitiatePage),
					"On clicking on 'Add another policy change' button within 'Add Contents Cover' screen takes the user back to Change My Policy screen and edit button displayed on the amendment",
					"On clicking on 'Add another policy change' button within 'Add Contents Cover' screen did not takes the user back to Change My Policy screen and edit button displayed on the amendment",
					driver, extentedReport);

			mTAInitiatePage.clickEditChangesBtn("Add contents cover", extentedReport, true);

			HashMap<String, String> expectedAddContentsDetails = new HashMap<String, String>();
			expectedAddContentsDetails.put("ValueOfHomeContents", testData.valueOfHomeContents);
			expectedAddContentsDetails.put("ValueOfPreciousOrCostlyItems", testData.valueOfPreciousOrCostlyItems);
			expectedAddContentsDetails.put("ValueOfMostExpensiveItem", testData.valueOfMostExpensiveItem);
			expectedAddContentsDetails.put("TypeOfHomeContentsCoverRequired", testData.typeOfHomeContentsCoverRequired);
			expectedAddContentsDetails.put("ExcessValue", testData.excessvalue);
			expectedAddContentsDetails.put("NameOfPreviousInsurer", testData.nameOfPreviousInsurer);
			expectedAddContentsDetails.put("NumberOfPreviousInsurance", testData.numberOfPreviousInsurance);
			expectedAddContentsDetails.put("ExpiryDateOfPreviousInsurance", testData.expiryDateOfPreviousInsurance);
			expectedAddContentsDetails.put("PayForYourPreviousInsurance", testData.payForYourPreviousInsurance);
			expectedAddContentsDetails.put("NoOfYearsWithoutClaimingValue", testData.noOfYearsWithoutClaimingValue);
			expectedAddContentsDetails.put("CashInYourHomeToBeInsured", testData.cashInYourHomeToBeInsured);
			expectedAddContentsDetails.put("CreditCardsToBeInsured", testData.creditCardsToBeInsured);
			expectedAddContentsDetails.put("FrozenFoodToBeInsured", testData.frozenFoodToBeInsured);

			Log.softAssertThat(
					addContentsCoverPage.verifyDetailInAddContentsDetailSection(expectedAddContentsDetails,
							extentedReport),
					"Add contents details are verified", "Add contents details are not verified", driver,
					extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home while Editing the details",
					"Add Specified item is not verified within Home while Editing the details", driver, extentedReport,
					true);
			
			addContentsCoverPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified in Within Home while Editing the details",
					"Add Specified item is not verified within Home while Editing the details", driver, extentedReport,
					true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that the help text for each field is available in Add Contents cover", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_048(String browser) throws Exception {

		String tcId = "TC_15789";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpAddContentsCover"),
							addContentsCoverPage),
					"Help icon (?) is displayed against the 'Title Add Buildings Cover' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'Title Add Buildings Cover' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOfAddContentsCover(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconAddContentsCoverPopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Add_Contents_Cover
					+ " - Message is displayed when click/hover the help icon (?) against the 'Title Add Contents Cover' text",
					addContentsCoverPage.POPUP_MSG_HELPICON_Add_Contents_Cover
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Title Add Contents Cover' text",
					driver, extentedReport);
			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpvalueOfHomeContents"),
							addContentsCoverPage),
					"Help icon (?) is displayed against the 'Value Of Home Contents' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'Value Of Home Contents' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOfvalueOfHomeContents(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconvalueOfHomeContentsPopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Value_Of_Home_Contents
					+ " - Message is displayed when click/hover the help icon (?) against the 'Value Of Home Contents' text",
					addContentsCoverPage.POPUP_MSG_HELPICON_Value_Of_Home_Contents
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Value Of Home Contents' text",
					driver, extentedReport);

			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpvalueOfPreciousOrCostlyItems"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'value Of Precious Or Costly Items' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'value Of Precious Or Costly Items' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOfvalueOfPreciousOrCostlyItems(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconvalueOfPreciousOrCostlyItemsPopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Value_Of_Precious_Or_Costly_Items
					+ " - Message is displayed when click/hover the help icon (?) against the 'value Of Precious Or Costly Items",
					addContentsCoverPage.POPUP_MSG_HELPICON_Value_Of_Precious_Or_Costly_Items
					+ " - Message is not displayed when click/hover the help icon (?) against the 'value Of Precious Or Costly Items",
					driver, extentedReport);

			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpvalueOfMostExpensiveItem"),
							addContentsCoverPage),
					"Help icon (?) is displayed against the 'value Of Most Expensive Item' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'value Of Most Expensive Item' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOfvalueOfMostExpensiveItem(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconvalueOfMostExpensiveItemPopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_value_Of_Most_Expensive_Item
					+ " - Message is displayed when click/hover the help icon (?) against the 'value Of Most Expensive Item",
					addContentsCoverPage.POPUP_MSG_HELPICON_value_Of_Most_Expensive_Item
					+ " - Message is not displayed when click/hover the help icon (?) against the 'value Of Most Expensive Item",
					driver, extentedReport);

			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelptypeOfHomeContentsCoverRequired"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'type Of Home Contents Cover Required' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'type Of Home Contents Cover Required' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOftypeOfHomeContentsCoverRequired(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIcontypeOfHomeContentsCoverRequiredPopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Type_Of_Home_Contents_Cover_Required
					+ " - Message is displayed when click/hover the help icon (?) against the 'type Of Home Contents Cover Required",
					addContentsCoverPage.POPUP_MSG_HELPICON_Type_Of_Home_Contents_Cover_Required
					+ " - Message is not displayed when click/hover the help icon (?) against the 'type Of Home Contents Cover Required",
					driver, extentedReport);

			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelptotalExcessRequired"),
							addContentsCoverPage),
					"Help icon (?) is displayed against the 'total Excess Required' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'total Excess Required' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOftotalExcessRequired(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIcontotalExcessRequiredPopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Total_Excess_Required
					+ " - Message is displayed when click/hover the help icon (?) against the 'total Excess Required",
					addContentsCoverPage.POPUP_MSG_HELPICON_Total_Excess_Required
					+ " - Message is not displayed when click/hover the help icon (?) against the 'total Excess Required",
					driver, extentedReport);

			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpcontentsSumRepresentReplacementAmount"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'contents Sum Represent Replacement Amount' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'contents Sum Represent Replacement Amount' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOfcontentsSumRepresentReplacementAmount(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconcontentsSumRepresentReplacementAmountPopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Contents_Sum_Represent_Replacement_Amount
					+ " - Message is displayed when click/hover the help icon (?) against the 'contents Sum Represent Replacement Amount",
					addContentsCoverPage.POPUP_MSG_HELPICON_Contents_Sum_Represent_Replacement_Amount
					+ " - Message is not displayed when click/hover the help icon (?) against the 'contents Sum Represent Replacement Amount",
					driver, extentedReport);

			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.arrpreviouslyContentsInsurance[0],
					extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelppreviouslyContentsInsurance"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'previously Contents Insurance' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'previously Contents Insurance' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOfpreviouslyContentsInsurance(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconpreviouslyContentsInsurancePopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Previously_Contents_Insurance
					+ " - Message is displayed when click/hover the help icon (?) against the 'previously Contents Insurance",
					addContentsCoverPage.POPUP_MSG_HELPICON_Previously_Contents_Insurance
					+ " - Message is not displayed when click/hover the help icon (?) against the 'previously Contents Insurance",
					driver, extentedReport);
			
			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpnameOfPreviousInsurer"),
							addContentsCoverPage),
					"Help icon (?) is displayed against the 'name Of Previous Insurer' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'name Of Previous Insurer' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOfnameOfPreviousInsurer(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconnameOfPreviousInsurerPopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Name_Of_Previous_Insurer
					+ " - Message is displayed when click/hover the help icon (?) against the 'name Of Previous Insurer",
					addContentsCoverPage.POPUP_MSG_HELPICON_Name_Of_Previous_Insurer
					+ " - Message is not displayed when click/hover the help icon (?) against the 'name Of Previous Insurer",
					driver, extentedReport);
			
			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpPolicyNumberOfPreviousInsurance"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'Policy Number Of Previous Insurance' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'Policy Number Of Previous Insurance' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOfPolicyNumberOfPreviousInsurance(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconPolicyNumberOfPreviousInsurancePopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Policy_Number_Of_Previous_Insurance
					+ " - Message is displayed when click/hover the help icon (?) against the 'Policy Number Of Previous Insurance",
					addContentsCoverPage.POPUP_MSG_HELPICON_Policy_Number_Of_Previous_Insurance
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Policy Number Of Previous Insurance",
					driver, extentedReport);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpExpiryDateOfPreviousInsurance"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'Expiry Date Of Previous Insurance' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'Expiry Date Of Previous Insurance' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOfExpiryDateOfPreviousInsurance(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconExpiryDateOfPreviousInsurancePopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Expiry_Date_Of_Previous_Insurance
					+ " - Message is displayed when click/hover the help icon (?) against the 'Expiry Date Of Previous Insurance",
					addContentsCoverPage.POPUP_MSG_HELPICON_Expiry_Date_Of_Previous_Insurance
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Expiry Date Of Previous Insurance",
					driver, extentedReport);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpPayForYourPreviousInsurance"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'Pay For Your Previous Insurance' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'Pay For Your Previous Insurance' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOfPayForYourPreviousInsurance(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconPayForYourPreviousInsurancePopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Pay_For_Your_Previous_Insurance
					+ " - Message is displayed when click/hover the help icon (?) against the 'Pay For Your Previous Insurance",
					addContentsCoverPage.POPUP_MSG_HELPICON_Pay_For_Your_Previous_Insurance
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Pay For Your Previous Insurance",
					driver, extentedReport);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpNoOfYearsWithoutClaiming"),
							addContentsCoverPage),
					"Help icon (?) is displayed against the 'No Of Years Without Claiming' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'Pay For Your Previous Insurance' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOfNoOfYearsWithoutClaiming(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconNoOfYearsWithoutClaimingPopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_No_Of_Years_Without_Claiming
					+ " - Message is displayed when click/hover the help icon (?) against the 'No Of Years Without Claiming",
					addContentsCoverPage.POPUP_MSG_HELPICON_No_Of_Years_Without_Claiming
					+ " - Message is not displayed when click/hover the help icon (?) against the 'No Of Years Without Claiming",
					driver, extentedReport);
			
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.arrpreviouslyContentsInsurance[1],
					extentedReport, true);

			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpcashInYourHomeToBeInsured"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'cash In Your Home To Be Insured' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'cash In Your Home To Be Insured' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOfcashInYourHomeToBeInsured(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconcashInYourHomeToBeInsuredPopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Cash_In_Your_Home_To_Be_Insured
					+ " - Message is displayed when click/hover the help icon (?) against the 'cash In Your Home To Be Insured",
					addContentsCoverPage.POPUP_MSG_HELPICON_Cash_In_Your_Home_To_Be_Insured
					+ " - Message is not displayed when click/hover the help icon (?) against the 'cash In Your Home To Be Insured",
					driver, extentedReport);

			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpcreditCardsToBeInsured"),
							addContentsCoverPage),
					"Help icon (?) is displayed against the 'credit Cards To Be Insured' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'credit Cards To Be Insured' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOfcreditCardsToBeInsured(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconcreditCardsToBeInsuredPopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Credit_Cards_To_Be_Insured
					+ " - Message is displayed when click/hover the help icon (?) against the 'credit Cards To Be Insured",
					addContentsCoverPage.POPUP_MSG_HELPICON_Credit_Cards_To_Be_Insured
					+ " - Message is not displayed when click/hover the help icon (?) against the 'credit Cards To Be Insured",
					driver, extentedReport);

			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("iconHelpfrozenFoodToBeInsured"),
							addContentsCoverPage),
					"Help icon (?) is displayed against the 'frozen Food To Be Insured' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'frozen Food To Be Insured' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIconOffrozenFoodToBeInsured(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconfrozenFoodToBeInsuredPopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Frozen_Food_To_Be_Insured
					+ " - Message is displayed when click/hover the help icon (?) against the 'frozen Food To Be Insured",
					addContentsCoverPage.POPUP_MSG_HELPICON_Frozen_Food_To_Be_Insured
					+ " - Message is not displayed when click/hover the help icon (?) against the 'frozen Food To Be Insured",
					driver, extentedReport);

			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpaddPersonalPossessionsWithinHome"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'add Personal Possessions Within Home' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'add Personal Possessions Within Home' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIaddPersonalPossessionsWithinHome(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconaddPersonalPossessionsWithinHomePopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Add_Personal_Possessions_Within_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'add Personal Possessions Within Home",
					addContentsCoverPage.POPUP_MSG_HELPICON_Add_Personal_Possessions_Within_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'add Personal Possessions Within Home",
					driver, extentedReport);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpaddAnotherItemInWithinHome"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'add Another Item In Within Home' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'add Another Item In Within Home' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIaddAnotherItemInWithinHome(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconaddAnotherItemInWithinHomePopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Add_Another_Item_In_Within_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'add Another Item In Within Home",
					addContentsCoverPage.POPUP_MSG_HELPICON_Add_Another_Item_In_Within_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'add Another Item In Within Home",
					driver, extentedReport);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpItemtypeNewSpecifiedItemWithinHome"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'Itemtype New Specified Item Within Home' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'Itemtype New Specified Item Within Home' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIItemtypeNewSpecifiedItemWithinHome(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconItemtypeNewSpecifiedItemWithinHome(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Within_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Itemtype New Specified Item Within Home",
					addContentsCoverPage.POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Within_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Itemtype New Specified Item Within Home",
					driver, extentedReport);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpItemvalueNewSpecifiedItemWithinHome"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'Itemvalue New Specified Item Within Home' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'Itemvalue New Specified Item Within Home' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIItemvalueNewSpecifiedItemWithinHome(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconItemvalueNewSpecifiedItemWithinHome(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Within_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Itemvalue New Specified Item Within Home",
					addContentsCoverPage.POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Within_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Itemvalue New Specified Item Within Home",
					driver, extentedReport);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpDescriptionOfItemNewSpecifiedItemWithinHome"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'Description Of Item New Specified Item Within Home' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'Description Of Item New Specified Item Within Home' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIDescriptionOfItemNewSpecifiedItemWithinHome(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconDescriptionOfItemNewSpecifiedItemWithinHome(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Within_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Description Of Item New Specified Item Within Home",
					addContentsCoverPage.POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Within_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Description Of Item New Specified Item Within Home",
					driver, extentedReport);

			addContentsCoverPage.clickCancelNewSpecifiedItemWithinHome(extentedReport, true);;

			addContentsCoverPage.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(
					testData.addPersonalPossessionsOutsideHome, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpaddPersonalPossessionsOutsideHome"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'add Personal Possessions Outside Home' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'add Personal Possessions Outside Home' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIaddPersonalPossessionsOutsideHome(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconaddPersonalPossessionsOutsideHome(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Add_Personal_Possessions_Outside_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'add Personal Possessions Outside Home",
					addContentsCoverPage.POPUP_MSG_HELPICON_Add_Personal_Possessions_Outside_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'add Personal Possessions Outside Home",
					driver, extentedReport);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpaddAnotherItemInOutsideHome"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'add Another Item In Outside Home' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'add Another Item In Outside Home' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIaddAnotherItemInOutsideHome(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconaddAnotherItemInOutsideHomePopupMessage(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Add_Another_Item_In_Outside_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'add Another Item In Outside Home",
					addContentsCoverPage.POPUP_MSG_HELPICON_Add_Another_Item_In_Outside_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'add Another Item In Outside Home",
					driver, extentedReport);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpItemtypeNewSpecifiedItemOutsideHome"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'Itemtype New Specified Item Outside Home' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'Itemtype New Specified Item Outside Home' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIItemtypeNewSpecifiedItemOutsideHome(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconItemtypeNewSpecifiedItemOutsideHome(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Outside_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Itemtype New Specified Item Outside Home",
					addContentsCoverPage.POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Outside_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Itemtype New Specified Item Outside Home",
					driver, extentedReport);

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpItemvalueNewSpecifiedItemOutsideHome"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'Itemvalue New Specified Item Outside Home' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'Itemvalue New Specified Item Outside Home' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIItemvalueNewSpecifiedItemOutsideHome(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconItemvalueNewSpecifiedItemOutsideHome(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Outside_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Itemvalue New Specified Item Outside Home",
					addContentsCoverPage.POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Outside_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Itemvalue New Specified Item Outside Home",
					driver, extentedReport);

			Log.softAssertThat(addContentsCoverPage.uielement.verifyPageElements(
					Arrays.asList("iconHelpDescriptionOfItemNewSpecifiedItemOutsideHome"), addContentsCoverPage),
					"Help icon (?) is displayed against the 'Description Of Item New Specified Item Outside Home' text in Add Contents Cover",
					"Help icon (?) is not displayed against the 'Description Of Item New Specified Item Outside Home' text in Add Contents Cover",
					driver, extentedReport);

			addContentsCoverPage.hoverOnHelpIDescriptionOfItemNewSpecifiedItemOutsideHome(extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.verifyHelpIconDescriptionOfItemNewSpecifiedItemOutisdeHome(),
					addContentsCoverPage.POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Outside_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Description Of Item New Specified Item Outside Home",
					addContentsCoverPage.POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Outside_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Description Of Item New Specified Item Outside Home",
					driver, extentedReport);

			addContentsCoverPage.clickCancelNewSpecifiedItemOutsideHome(extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the maximum field length for Approximate value of cash kept in your home to be insureds under Additional cover", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_094(String browser) throws Exception {

		String tcId = "TC_20340";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addContentsCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance,
					extentedReport, true);
			addContentsCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance,
					extentedReport, true);
			addContentsCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport,
					true);
			addContentsCoverPage.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.verifyMaximumFieldLength("5", extentedReport, true);
			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("fldCashKeptInYourHomeToBeInsured"),
							addContentsCoverPage),
					"Maximum field length for Approximate value of cash kept in your home to be insured field length is validated",
					"Maximum field length for Approximate value of cash kept in your home to be insured field length is not validated",
					driver, extentedReport, true);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the maximum field length for Approximate value of credit cards to be insured under Additional cover", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_095(String browser) throws Exception {

		String tcId = "TC_20341";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addContentsCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance,
					extentedReport, true);
			addContentsCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance,
					extentedReport, true);
			addContentsCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport,
					true);
			addContentsCoverPage.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.verifyMaximumFieldLength("5", extentedReport, true);
			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("fldCreditCardsToBeInsured"),
							addContentsCoverPage),
					"Maximum field length for Approximate value of credit cards to be insured field length is validated",
					"Maximum field length for Approximate value of credit cards to be insured field length is not validated",
					driver, extentedReport, true);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the maximum field length for Approximate value of frozen food to be insured under Additional cover", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_096(String browser) throws Exception {

		String tcId = "TC_20342";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addContentsCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance,
					extentedReport, true);
			addContentsCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance,
					extentedReport, true);
			addContentsCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport,
					true);
			addContentsCoverPage.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.verifyMaximumFieldLength("5", extentedReport, true);
			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("fldFrozenFoodToBeInsured"),
							addContentsCoverPage),
					"Maximum field length for Approximate value of frozen food to be insured field length is validated ",
					"Maximum field length for Approximate value of frozen food to be insured field length is not validated",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the maximum field length for Total value of precious or costly items under Contents information", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_097(String browser) throws Exception {

		String tcId = "TC_20338";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.verifyMaximumFieldLength("7", extentedReport, true);
			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(
							Arrays.asList("fldTotalValueOfPreciousOrCostlyItems"), addContentsCoverPage),
					"Maximum field length for Total value of precious or costly items field length is validated ",
					"Maximum field length for Total value of precious or costly items field length is not validated",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the maximum field length for Value of most expensive item under Contents information", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_098(String browser) throws Exception {

		String tcId = "TC_20339";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.verifyMaximumFieldLength("7", extentedReport, true);
			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("fldValueOfMostExpensiveItem"),
							addContentsCoverPage),
					"Maximum field length for Value of most expensive item field length is validated ",
					"Maximum field length for Value of most expensive item field length is not validated", driver,
					extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the maximum field length for 'Item value' in Specified item with in Home", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_099(String browser) throws Exception {

		String tcId = "TC_20343";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("fldItemvalueNewSpecifiedItemWithinHome"),
					addContentsCoverPage), "Maximum field length for'Item value' in Specified item with in Home is validated ",
					"Maximum field length for 'Item value' in Specified item with in Home is not validated", driver, extentedReport, true);
			addContentsCoverPage.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH,
					testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);
			addContentsCoverPage.verifyMaximumFieldLength("5", extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify the maximum field length for 'Item value' in Specified item Outside in Home", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_100(String browser) throws Exception {

		String tcId = "TC_20345";
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

			Log.softAssertThat(
					addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"),
							addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button",
					"Add Contents cover page is not opens after clicking Continue button", driver, extentedReport,
					true);

			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addContentsCoverPage.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH,
					testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					addContentsCoverPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addContentsCoverPage.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome,
					extentedReport, true);
			addContentsCoverPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, extentedReport, true);
			Log.softAssertThat(addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("fldItemvalueNewSpecifiedItemOutsideHome"),
					addContentsCoverPage), "Maximum field length for'Item value' in Specified item Outside in Home is validated ",
					"Maximum field length for 'Item value' in Specified item Outside in Home is not validated", driver,	extentedReport, true);
			addContentsCoverPage.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);
			addContentsCoverPage.verifyMaximumFieldLength("5", extentedReport, true);
		
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	/** 10002 Story **/

	@Test(groups = {
	"Smoke" },description = "Verfiy that 'Add Specified Item' option is available in Change My Policy screen and  clicking on 'Continue' button after selecting Add specified item option opens up Add specified item main screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_031(String browser) throws Exception {

		String tcId = "TC_15840_15841_15871";
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

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addSpecifiedItems"), mTAInitiatePage),
					"Add Specified Items cover is displayed in MTA intiate page",
					"Add Specified Items cover is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			AddSpecifiedItemPage addSpecifiedItemPage = new AddSpecifiedItemPage(driver, extentedReport).get();

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddSpecifiedItems"),
							addSpecifiedItemPage),
					"Add Specified Items cover page is opens after clicking Continue button",
					"Add Specified Items cover page is not opens after clicking Continue button", driver,
					extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(description = "Check help icon is available for all the fields in the 'Add specified items' page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_032(String browser) throws Exception {

		String tcId = "TC_15843";
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

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddSpecifiedItems"),
							addSpecifiedItemPage),
					"Add Specified Items cover page is opens after clicking Continue button",
					"Add Specified Items cover page is not opens after clicking Continue button", driver,
					extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(Arrays.asList("iconHelpAddSpecifiedItems"),
							addSpecifiedItemPage),
					"Help icon (?) is displayed against the 'Add Specified Items' text in Add Specified item",
					"Help icon (?) is not displayed against the 'Add Specified Items' text in Add Specified item",
					driver, extentedReport);

			addSpecifiedItemPage.hoverOnHelpIconOfAddSpecifiedItems(extentedReport, true);
			Log.softAssertThat(addSpecifiedItemPage.verifyHelpIconAddSpecifiedItemsPopupMessage(),
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Add_Specified_Items
					+ " - Message is displayed when click/hover the help icon (?) against the 'Add Specified Items",
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Add_Specified_Items
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Add Specified Items",
					driver, extentedReport);

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpNoSpecifiedItemsAddedToYourPolicy"), addSpecifiedItemPage),
					"Help icon (?) is displayed against the 'No Specified Items Added To Your Policy' text in Add Specified item",
					"Help icon (?) is not displayed against the 'No Specified Items Added To Your Policy' text in Add Specified item",
					driver, extentedReport);

			addSpecifiedItemPage.hoverOnHelpIconOfNoSpecifiedItemsAddedToYourPolicy(extentedReport, true);
			Log.softAssertThat(addSpecifiedItemPage.verifyHelpIconNoSpecifiedItemsAddedToYourPolicyPopupMessage(),
					addSpecifiedItemPage.POPUP_MSG_HELPICON_No_Specified_Items_Added_To_Your_Policy
					+ " - Message is displayed when click/hover the help icon (?) against the 'No Specified Items Added To Your Policy",
					addSpecifiedItemPage.POPUP_MSG_HELPICON_No_Specified_Items_Added_To_Your_Policy
					+ " - Message is not displayed when click/hover the help icon (?) against the 'No Specified Items Added To Your Policy",
					driver, extentedReport);

			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpaddPersonalPossessionsWithinHome"), addSpecifiedItemPage),
					"Help icon (?) is displayed against the 'add Personal Possessions Within Home' text in Add Specified item",
					"Help icon (?) is not displayed against the 'add Personal Possessions Within Home' text in Add Specified item",
					driver, extentedReport);

			addSpecifiedItemPage.hoverOnHelpIaddPersonalPossessionsWithinHome(extentedReport, true);
			Log.softAssertThat(addSpecifiedItemPage.verifyHelpIconaddPersonalPossessionsWithinHomePopupMessage(),
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Add_Personal_Possessions_Within_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'add Personal Possessions Within Home",
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Add_Personal_Possessions_Within_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'add Personal Possessions Within Home",
					driver, extentedReport);
			
			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpaddAnotherItemInWithinHome"), addSpecifiedItemPage),
					"Help icon (?) is displayed against the 'add Another Item In Within Home' text in Add Specified item",
					"Help icon (?) is not displayed against the 'add Another Item In Within Home' text in Add Specified item",
					driver, extentedReport);

			addSpecifiedItemPage.hoverOnHelpIaddAnotherItemInWithinHome(extentedReport, true);
			Log.softAssertThat(addSpecifiedItemPage.verifyHelpIconaddAnotherItemInWithinHomePopupMessage(),
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Add_Another_Item_In_Within_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'add Another Item In Within Home",
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Add_Another_Item_In_Within_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'add Another Item In Within Home",
					driver, extentedReport);

			addSpecifiedItemPage.selectItemTypeOfSpecifiedItemsWithinHome(testData.itemTypeWH, extentedReport, true);
			
			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpItemtypeNewSpecifiedItemWithinHome"), addSpecifiedItemPage),
					"Help icon (?) is displayed against the 'Itemtype New Specified Item Within Home' text in Add Specified item",
					"Help icon (?) is not displayed against the 'Itemtype New Specified Item Within Home' text in Add Specified item",
					driver, extentedReport);

			addSpecifiedItemPage.hoverOnHelpIItemtypeNewSpecifiedItemWithinHome(extentedReport, true);
			Log.softAssertThat(addSpecifiedItemPage.verifyHelpIconItemtypeNewSpecifiedItemWithinHome(),
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Within_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Itemtype New Specified Item Within Home",
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Within_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Itemtype New Specified Item Within Home",
					driver, extentedReport);
			
			addSpecifiedItemPage.enterItemValueOfSpecifiedItemsWithinHome(testData.itemValueWH, extentedReport, true);
			
			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpItemvalueNewSpecifiedItemWithinHome"), addSpecifiedItemPage),
					"Help icon (?) is displayed against the 'Itemvalue New Specified Item Within Home' text in Add Specified item",
					"Help icon (?) is not displayed against the 'Itemvalue New Specified Item Within Home' text in Add Specified item",
					driver, extentedReport);

			addSpecifiedItemPage.hoverOnHelpIItemvalueNewSpecifiedItemWithinHome(extentedReport, true);
			Log.softAssertThat(addSpecifiedItemPage.verifyHelpIconItemvalueNewSpecifiedItemWithinHome(),
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Within_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Itemvalue New Specified Item Within Home",
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Within_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Itemvalue New Specified Item Within Home",
					driver, extentedReport);

			addSpecifiedItemPage.enterDescriptionOfItemOfSpecifiedItemsWithinHome(testData.itemDecsriptionWH, extentedReport, true);
			
			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpDescriptionOfItemNewSpecifiedItemWithinHome"), addSpecifiedItemPage),
					"Help icon (?) is displayed against the 'Description Of Item New Specified Item Within Home' text in Add Specified item",
					"Help icon (?) is not displayed against the 'Description Of Item New Specified Item Within Home' text in Add Specified item",
					driver, extentedReport);

			addSpecifiedItemPage.hoverOnHelpIDescriptionOfItemNewSpecifiedItemWithinHome(extentedReport, true);
			Log.softAssertThat(addSpecifiedItemPage.verifyHelpIconDescriptionOfItemNewSpecifiedItemWithinHome(),
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Within_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Description Of Item New Specified Item Within Home",
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Within_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Description Of Item New Specified Item Within Home",
					driver, extentedReport);

			addSpecifiedItemPage.clickCancelNewSpecifiedItemWithinHome(extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome,
					extentedReport, true);
			addSpecifiedItemPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsOutsideHome(
					testData.addPersonalPossessionsOutsideHome, extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpaddPersonalPossessionsOutsideHome"), addSpecifiedItemPage),
					"Help icon (?) is displayed against the 'add Personal Possessions Outside Home' text in Add Specified item",
					"Help icon (?) is not displayed against the 'add Personal Possessions Outside Home' text in Add Specified item",
					driver, extentedReport);

			addSpecifiedItemPage.hoverOnHelpIaddPersonalPossessionsOutsideHome(extentedReport, true);
			Log.softAssertThat(addSpecifiedItemPage.verifyHelpIconaddPersonalPossessionsOutsideHome(),
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Add_Personal_Possessions_Outside_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'add Personal Possessions Outside Home",
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Add_Personal_Possessions_Outside_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'add Personal Possessions Outside Home",
					driver, extentedReport);

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpaddAnotherItemInOutsideHome"), addSpecifiedItemPage),
					"Help icon (?) is displayed against the 'add Another Item In Outside Home' text in Add Specified item",
					"Help icon (?) is not displayed against the 'add Another Item In Outside Home' text in Add Specified item",
					driver, extentedReport);

			addSpecifiedItemPage.hoverOnHelpIaddAnotherItemInOutsideHome(extentedReport, true);
			Log.softAssertThat(addSpecifiedItemPage.verifyHelpIconaddAnotherItemInOutsideHomePopupMessage(),
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Add_Another_Item_In_Outside_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'add Another Item In Outside Home",
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Add_Another_Item_In_Outside_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'add Another Item In Outside Home",
					driver, extentedReport);
			
			addSpecifiedItemPage.selectItemTypeOfSpecifiedItemsOutsideHome(testData.itemTypeOH, extentedReport, true);
			
			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpItemtypeNewSpecifiedItemOutsideHome"), addSpecifiedItemPage),
					"Help icon (?) is displayed against the 'Itemtype New Specified Item Outside Home' text in Add Specified item",
					"Help icon (?) is not displayed against the 'Itemtype New Specified Item Outside Home' text in Add Specified item",
					driver, extentedReport);

			addSpecifiedItemPage.hoverOnHelpIItemtypeNewSpecifiedItemOutsideHome(extentedReport, true);
			Log.softAssertThat(addSpecifiedItemPage.verifyHelpIconItemtypeNewSpecifiedItemOutsideHome(),
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Outside_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Itemtype New Specified Item Outside Home",
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Outside_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Itemtype New Specified Item Outside Home",
					driver, extentedReport);
			
			addSpecifiedItemPage.enterItemValueOfSpecifiedItemsOutsideHome(testData.itemValueOH, extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(
							Arrays.asList("iconHelpItemvalueNewSpecifiedItemOutsideHome"), addSpecifiedItemPage),
					"Help icon (?) is displayed against the 'Itemvalue New Specified Item Outside Home' text in Add Specified item",
					"Help icon (?) is not displayed against the 'Itemvalue New Specified Item Outside Home' text in Add Specified item",
					driver, extentedReport);

			addSpecifiedItemPage.hoverOnHelpIItemvalueNewSpecifiedItemOutsideHome(extentedReport, true);
			Log.softAssertThat(addSpecifiedItemPage.verifyHelpIconItemvalueNewSpecifiedItemOutsideHome(),
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Outside_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Itemvalue New Specified Item Outside Home",
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Outside_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Itemvalue New Specified Item Outside Home",
					driver, extentedReport);
			
			addSpecifiedItemPage.enterDescriptionOfItemOfSpecifiedItemsOutsideHome(testData.itemDecsriptionOH, extentedReport, true);

			Log.softAssertThat(addSpecifiedItemPage.uielement.verifyPageElements(
					Arrays.asList("iconHelpDescriptionOfItemNewSpecifiedItemOutsideHome"), addSpecifiedItemPage),
					"Help icon (?) is displayed against the 'Description Of Item New Specified Item Outside Home' text in Add Specified item",
					"Help icon (?) is not displayed against the 'Description Of Item New Specified Item Outside Home' text in Add Specified item",
					driver, extentedReport);

			addSpecifiedItemPage.hoverOnHelpIDescriptionOfItemNewSpecifiedItemOutsideHome(extentedReport, true);
			Log.softAssertThat(addSpecifiedItemPage.verifyHelpIconDescriptionOfItemNewSpecifiedItemOutisdeHome(),
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Outside_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Description Of Item New Specified Item Outside Home",
					addSpecifiedItemPage.POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Outside_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Description Of Item New Specified Item Outside Home",
					driver, extentedReport);

			addSpecifiedItemPage.clickCancelNewSpecifiedItemOutsideHome(extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(description = "Verify that user is able to add specified itams with in home and outside the home", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_033(String browser) throws Exception {

		String tcId = "TC_15844_15846_5847__15848_15849_15850_15851_15852_15853_15854_15855_15856_15857_15858_15859_15862_15870";
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

			// Enter Specified Item
			AddSpecifiedItemPage addSpecifiedItemPage = new AddSpecifiedItemPage(driver, extentedReport).get();

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddSpecifiedItems"),
							addSpecifiedItemPage),
					"Add Specified Items cover page is opens after clicking Continue button",
					"Add Specified Items cover page is not opens after clicking Continue button", driver,
					extentedReport, true);

			// Add Specified item with in home
			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);

			addSpecifiedItemPage.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH,
					testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInWithinHome("No", extentedReport, true);

			addSpecifiedItemPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsOutsideHome("Yes", extentedReport, true);

			addSpecifiedItemPage.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInOutsideHome("No", extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(description = "Verify that user is able to delete the added specified itams with in home and outside the home", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_034(String browser) throws Exception {

		String tcId = "TC_15868";
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

			// Add Specified item
			AddSpecifiedItemPage addSpecifiedItemPage = new AddSpecifiedItemPage(driver, extentedReport).get();

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddSpecifiedItems"),
							addSpecifiedItemPage),
					"Add Specified Items cover page is opens after clicking Continue button",
					"Add Specified Items cover page is not opens after clicking Continue button", driver,
					extentedReport, true);

			// Add specified item with in home
			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addSpecifiedItemPage.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH,
					testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome,
					extentedReport, true);
			addSpecifiedItemPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsOutsideHome("Yes", extentedReport, true);
			addSpecifiedItemPage.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInOutsideHome("No", extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(groups = {
	"Smoke" },description = "Check items added newly can be edited and deleted but the existing items cannot", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_035(String browser) throws Exception {

		String tcId = "TC_15863";
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

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddSpecifiedItems"),
							addSpecifiedItemPage),
					"Add Specified Items cover page is opens after clicking Continue button",
					"Add Specified Items cover page is not opens after clicking Continue button", driver,
					extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addSpecifiedItemPage.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH,
					testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.editAddItemWithinHome(testData.itemNameWH, testData.itemTypeWH, testData.itemValueWH,
					testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);

			addSpecifiedItemPage.deleteAddItemInWithinHome(testData.itemNameWH, testData.deleteItemWH, extentedReport,
					true);
			
			Log.softAssertThat(
					!addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInWithinHome("No", extentedReport, true);

			addSpecifiedItemPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, extentedReport, true);

			addSpecifiedItemPage.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInOutsideHome("No", extentedReport, true);

			addSpecifiedItemPage.editAddItemOutsideHome(testData.itemNameOH, testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			// addSpecifiedItemPage.clickCancelNewSpecifiedItemOutsideHome(extentedReport,
			// true);

			addSpecifiedItemPage.deleteAddItemInOutsideHome(testData.itemNameOH, testData.deleteItemOH, extentedReport,
					true);

			// addSpecifiedItemPage.clickYesOrNoBtnToDeleteItemInOutsideHome("Yes",
			// extentedReport, true);

			Log.softAssertThat(
					!addSpecifiedItemPage.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(groups = {
	"Smoke" },description = "Verify that user is able to click Quote button is enable after entering the mandatory test data", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_036(String browser) throws Exception {

		String tcId = "TC_15864";
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

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addSpecifiedItems"), mTAInitiatePage),
					"Add Specified Items cover is displayed in MTA intiate page",
					"Add Specified Items cover is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			AddSpecifiedItemPage addSpecifiedItemPage = new AddSpecifiedItemPage(driver, extentedReport).get();

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddSpecifiedItems"),
							addSpecifiedItemPage),
					"Add Specified Items cover page is opens after clicking Continue button",
					"Add Specified Items cover page is not opens after clicking Continue button", driver,
					extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addSpecifiedItemPage.addSpecifiedItemwithinHome(testData.arritemTypeWH[0], testData.arritemValueWH[0],
					testData.arritemDecsriptionWH[0], testData.arrsaveAndCancelWH[0], extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[0], extentedReport,
							true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.editAddItemWithinHome(testData.arritemNameWH[0], testData.arritemTypeWH[1],
					testData.arritemValueWH[1], testData.arritemDecsriptionWH[1], testData.arrsaveAndCancelWH[1],
					extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[1], extentedReport,
							true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInWithinHome("No", extentedReport, true);

			addSpecifiedItemPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, extentedReport, true);

			addSpecifiedItemPage.addSpecifiedItemOutsideHome(testData.arritemTypeOH[0], testData.arritemValueOH[0],
					testData.arritemDecsriptionOH[0], testData.arrsaveAndCancelOH[0], extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInOutsideHome(testData.arritemNameOH[0], extentedReport,
							true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.editAddItemOutsideHome(testData.arritemNameOH[0], testData.arritemTypeOH[1],
					testData.arritemValueOH[1], testData.arritemDecsriptionOH[1], testData.arrsaveAndCancelOH[1],
					extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInOutsideHome(testData.arritemNameOH[1], extentedReport,
							true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInOutsideHome("No", extentedReport, true);

			addSpecifiedItemPage.clickQuoteButton(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(groups = {
	"Smoke" },description = "Verify that clicking on Cancel button on 'Add Specified Items' main screen navigates the user to Change My Policy screen without any changes", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_037(String browser) throws Exception {

		String tcId = "TC_15866";
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

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addSpecifiedItems"), mTAInitiatePage),
					"Add Specified Items cover is displayed in MTA intiate page",
					"Add Specified Items cover is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			AddSpecifiedItemPage addSpecifiedItemPage = new AddSpecifiedItemPage(driver, extentedReport).get();

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddSpecifiedItems"),
							addSpecifiedItemPage),
					"Add Specified Items cover page is opens after clicking Continue button",
					"Add Specified Items cover page is not opens after clicking Continue button", driver,
					extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addSpecifiedItemPage.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH,
					testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInWithinHome("No", extentedReport, true);

			addSpecifiedItemPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsOutsideHome("Yes", extentedReport, true);

			addSpecifiedItemPage.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInOutsideHome("No", extentedReport, true);

			addSpecifiedItemPage.clickCancelButton(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange"),
							mTAInitiatePage),
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
	"Smoke" },description = "Check that clicking on 'Add another policy change' button within 'Add Specified item' screen takes the user back to Change My Policy screen with the changes saved and edit button is displays", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_038(String browser) throws Exception {

		String tcId = "TC_15867";
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

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addSpecifiedItems"), mTAInitiatePage),
					"Add Specified Items cover is displayed in MTA intiate page",
					"Add Specified Items cover is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			AddSpecifiedItemPage addSpecifiedItemPage = new AddSpecifiedItemPage(driver, extentedReport).get();

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddSpecifiedItems"),
							addSpecifiedItemPage),
					"Add Specified Items cover page is opens after clicking Continue button",
					"Add Specified Items cover page is not opens after clicking Continue button", driver,
					extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			addSpecifiedItemPage.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH,
					testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			// addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsWithinHome("No",
			// extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoWantToAddAnotherItemWithinHome("No", extentedReport, true);

			addSpecifiedItemPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsOutsideHome("Yes", extentedReport, true);

			addSpecifiedItemPage.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInOutsideHome("No", extentedReport, true);

			addSpecifiedItemPage.clickAddAnotherPolicyChangeButton(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChooseTypeOfChange", "btnEditAddSepecifiedItems"), mTAInitiatePage),
					"On clicking on 'Add another policy change' button within 'Add Contents Cover' screen takes the user back to Change My Policy screen and edit button displayed on the amendment",
					"On clicking on 'Add another policy change' button within 'Add Contents Cover' screen did not takes the user back to Change My Policy screen and edit button displayed on the amendment",
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
	"Smoke" },description = "Verify the existing specified item shown in Add specified item page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_039(String browser) throws Exception {

		String tcId = "TC_15842_15845_5860_15861";
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

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddSpecifiedItems"),
							addSpecifiedItemPage),
					"Add Specified Items cover page is opens after clicking Continue button",
					"Add Specified Items cover page is not opens after clicking Continue button", driver,
					extentedReport, true);

			addSpecifiedItemPage.verifyExistingSpecifiedItemsAlreadyAdded(testData, extentedReport, true);

			addSpecifiedItemPage.getAddedItemRowInWithinHomeForAlreadyExistingItem(testData.itemTypeWH, extentedReport,
					true);

			addSpecifiedItemPage.getAddedItemRowInWithinHomeForAlreadyExistingItem(testData.itemValueWH, extentedReport,
					true);

			addSpecifiedItemPage.getAddedItemRowInWithinHomeForAlreadyExistingItem(testData.itemDecsriptionWH,
					extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElementsDisabled(Arrays.asList("btnCallUsToChange"),
							addSpecifiedItemPage),
					"Call us to change is disabled", "Call us to change is enabled", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(description = "Verify that 'Add Specified item' details are updated correctly in Pure back office. When policy in MTA Quote", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_040(String browser) throws Exception {

		String tcId = "TC_15865_15869";
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
			
			Log.softAssertThat(
					addSpecifiedItemPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddSpecifiedItems"),
							addSpecifiedItemPage),
					"Add Specified Items cover page is opens after clicking Continue button",
					"Add Specified Items cover page is not opens after clicking Continue button", driver,
					extentedReport, true);

			// Add Specified item with in home
			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);

			addSpecifiedItemPage.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH,
					testData.itemDecsriptionWH, testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInWithinHome("No", extentedReport, true);

			addSpecifiedItemPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsOutsideHome("Yes", extentedReport, true);

			addSpecifiedItemPage.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					addSpecifiedItemPage.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInOutsideHome("No", extentedReport, true);

			addSpecifiedItemPage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Spcified Item Cover on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Add Spcified Item on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);

			mTAQuoteDetailsPage.clickCancelButton(extentedReport);
			mTAQuoteDetailsPage.selectOk(extentedReport);

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
