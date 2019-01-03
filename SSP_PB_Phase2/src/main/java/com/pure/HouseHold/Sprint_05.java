package com.pure.HouseHold;

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
import com.generic.utils.WaitUtils;
import com.pure.selfservice.pages.CardDetailsPage;
import com.pure.selfservice.pages.ChangeMyPropertyPage;
import com.pure.selfservice.pages.CustomerDashboardPage;
import com.pure.selfservice.pages.CustomerServicePage;
import com.pure.selfservice.pages.CustomerSignInPage;
import com.pure.selfservice.pages.GetTestData;
import com.pure.selfservice.pages.MTAInitiatePage;
import com.pure.selfservice.pages.MTAPaymentPage;
import com.pure.selfservice.pages.MTAQuoteDetailsPage;
import com.pure.selfservice.pages.SuccessPage;
import com.relevantcodes.extentreports.ExtentTest;

@Listeners(EmailReport.class)
public class Sprint_05 extends BaseTest {
	private String webSite;
	String sheetName = "Sprint_05_";

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

	/** Sprint 05 **/

	@Test(description = "Perform Change my property on Contents policy by selecting Buildings&Contentc and add specified item, add second occupation and procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_059(String browser) throws Exception {

		String tcId = "TC_59";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);
			
			changeMyProperty.clickNoButtonUnderYourCorrespondenceAddress(extentedReport, true);

			// Property details
			Log.softAssertThat(changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true),
					"TCNO_17710_17711_17712_17713_17714_Verified the Other Details of property to be insured questions",
					"TCNO_17710_17711_17712_17713_17714_Not Verified the Other Details of property to be insured questions",
					driver, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);
			changeMyProperty.clickNextSecurityDetails(extentedReport, true);
			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Buildings policy by selecting Buildings&Contentc and procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_060(String browser) throws Exception {

		String tcId = "TC_60";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			// Select change type
			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInSecuritySectionIsPropertyOccupiedInDay(extentedReport, true),
					"TCNO_17715_The Next button is clicked in Building construction section",
					"TCNO_17715_The Next button is not clicked in Building construction section", driver,
					extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("fldRebuildCost"), changeMyProperty),
					"TCNO_17730_The Next button is clicked in Security section",
					"TCNO_17730_The Next button is clicked in Security section", driver, extentedReport);

			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Contents policy by selecting Contents and Add Specified item, add second occupation and procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_061(String browser) throws Exception {

		String tcId = "TC_61";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("typeOfWall"), changeMyProperty),
					"TCNO_17716_Verify the availability of the question 'Type of wall construction' in the page Building Construction",
					"TCNO_17716_Not verify the availability of the question 'Type of wall construction' in the page Building Construction",
					driver, extentedReport);
			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);
			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Buildings policy by selecting Contents and procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_062(String browser) throws Exception {

		String tcId = "TC_62";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			
			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("typeOfRoof"), changeMyProperty),
					"TCNO_17717_Verified The Type of roof construction question",
					"TCNO_17717_Not Verified The Type of roof consturction question", driver,
					extentedReport);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Contents policy by selecting Contents and Verify the existing specified item and occupation and procced forQuote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_063(String browser) throws Exception {

		String tcId = "TC_63";
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
					"TCNO_17681_Change My Property cover is displayed in MTA intiate page",
					"TCNO_17681_Change My Property cover is not displayed in MTA intiate page", driver, extentedReport,
					true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("buildingsContents", "buildings", "contents"), changeMyProperty),
					"TCNO_17682_Building&Contents, Buildings and Contents options are available in Change my property page",
					"TCNO_17682_Building&Contents, Buildings and Contents options are not available in Change my property page",
					driver, extentedReport, true);

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("txtPerOfPropRoof"), changeMyProperty),
					"TCNO_17718_Verified The Percentage of the property's roof question",
					"TCNO_17718_Not Verified Percentage of the property's roof question", driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInBuildingConstructionPropertyExtended(extentedReport, true),
					"The value for Has the property been extended? is restored",
					"The value for Has the property been extended? is not restored", driver, extentedReport, true);
			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[0],
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[1],
					extentedReport, true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.arraddAnotherItemInOutsideHome[0],
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.arraddAnotherItemInOutsideHome[1],
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Buildings policy by selecting Buildings and add occupation and procced forQuote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_064(String browser) throws Exception {

		String tcId = "TC_64";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("typeOfListedBuilding"),
							changeMyProperty),
					"TCNO_17719_Verified The Type of the listed building if appropriate question",
					"TCNO_17719_Not Verified The Type of the listed building if appropriate question", driver,
					extentedReport);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);
			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Contents policy by selecting Buildings and procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_065(String browser) throws Exception {

		String tcId = "TC_65";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInBuildingConstructionPropertyExtended(extentedReport, true),
					"The value for Has the property been extended? is restored",
					"The value for Has the property been extended? is not restored", driver, extentedReport, true);
			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);
			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Contents policy by selecting Buildings&Contentc, click cancel button in people and perform the same and procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_066(String browser) throws Exception {

		String tcId = "TC_66";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);

			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInBuildingConstructionPropertyUsedforBusiness(extentedReport, true),
					"The value for Is the property used for business purposes? is restored",
					"The value for Is the property used for business purposes? is restored", driver, extentedReport,
					true);
			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);
			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Cancel policy
			changeMyProperty.clickCancelButton(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange"),
							mTAInitiatePage),
					"On clicking on Cancel button on 'Change my proprty' main screen navigates the user to Change My Policy screen without any change",
					"On clicking on Cancel button on 'Change my proprty' main screen did not navigates the user to Change My Policy screen without any change",
					driver, extentedReport);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInBuildingConstructionPropertyUsedforBusiness(extentedReport, true),
					"The value for Is the property used for business purposes? is restored",
					"The value for Is the property used for business purposes? is not restored", driver, extentedReport,
					true);
			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);
			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Buildings policy by selecting Buildings, click cancel button in people and perform the same and procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_067(String browser) throws Exception {

		String tcId = "TC_67";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("fldRebuildCost"), changeMyProperty),
					"TCNO_17692_Building Details are showned", "TCNO_17692_Building Details are not showned", driver,
					extentedReport);

			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			Log.softAssertThat(
					!changeMyProperty.uielement.verifyPageElements(Arrays.asList("fldTotalValueOfYourHomeContents"),
							changeMyProperty),
					"TCNO_17685_Content Details are not showned", "TCNO_17685_Content Details are showned", driver,
					extentedReport);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Cancel policy
			changeMyProperty.clickCancelButton(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange"),
							mTAInitiatePage),
					"On clicking on Cancel button on 'Change my proprty' main screen navigates the user to Change My Policy screen without any change",
					"On clicking on Cancel button on 'Change my proprty' main screen did not navigates the user to Change My Policy screen without any change",
					driver, extentedReport);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Select change type
			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);
			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Contents policy by selecting Contents, click cancel button in people and perform the same and procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_068(String browser) throws Exception {

		String tcId = "TC_68";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInBuildingConstructionPropertyFreeFromSubsidence(extentedReport,
							true),
					"The value for Is the property free from subsidence? is restored",
					"The value for Is the property free from subsidence? is not restored", driver, extentedReport,
					true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Cancel Policy
			changeMyProperty.clickCancelButton(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange"),
							mTAInitiatePage),
					"On clicking on Cancel button on 'Change my proprty' main screen navigates the user to Change My Policy screen without any change",
					"On clicking on Cancel button on 'Change my proprty' main screen did not navigates the user to Change My Policy screen without any change",
					driver, extentedReport);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Select change type
			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInBuildingConstructionPropertyFreeFromSubsidence(extentedReport,
							true),
					"The value for Is the property free from subsidence? is restored",
					"The value for Is the property free from subsidence? is not restored", driver, extentedReport,
					true);
			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInSecuritySectionIsPropertyOccupiedInDay(extentedReport, true),
					"The value for Is the property occupied during the day? is restored",
					"The value for Is the property occupied during the day? is not restored", driver, extentedReport,
					true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Contents policy by selecting Buildings&Contentc, procced for Quote & verify Premium and buy the Quote", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_069(String browser) throws Exception {

		String tcId = "TC_69";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInBuildingConstructionPropertyFreeFromFlooding(extentedReport, true),
					"The value for Is the property free from flooding? is restored",
					"The value for Is the property free from flooding? is not restored", driver, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);
			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"TCNO_17778_Add Specified item is verified in Within Home",
					"TCNO_17778_Add Specified item is not verified within Home", driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(description = "Perform Change my property on Buildings&Contentc policy by selecting Contents, procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_070(String browser) throws Exception {

		String tcId = "TC_70";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInBuildingConstructionPropertyEverBeenUnderpinned(extentedReport,
							true),
					"The value for Has the property ever been underpinned? is restored",
					"The value for Has the property ever been underpinned? is not restored", driver, extentedReport,
					true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);

			mTAQuoteDetailsPage.clickCancelButton(extentedReport);
			mTAQuoteDetailsPage.selectOk(extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(description = "Perform Change my property on Buildings&Contentc policy by selecting Buildings&Contentc, and add specified item and occupation then procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_071(String browser) throws Exception {

		String tcId = "TC_71";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInBuildingConstructionPropertyWithin400MetresOfWater(extentedReport,
							true),
					"The value for Is the property within 400 metres (¼ mile)of water? is restored",
					"The value for Is the property within 400 metres (¼ mile)of water? is NOT restored", driver,
					extentedReport, true);
			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);
			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"TCNO_17851_Add Occupation is verified in People page",
					"TCNO_17851_Add Occupation is not verified People page", driver, extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Contents policy by selecting Contents, procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_072(String browser) throws Exception {

		String tcId = "TC_72";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			Log.softAssertThat(changeMyProperty.verifyRestoreInBuildingConstructionAnyTrees(extentedReport, true),
					"The value for Are there any trees or shrubs taller than 5 metres(16ft) within 7 metres (22ft) of your property? is restored",
					"The value for Are there any trees or shrubs taller than 5 metres(16ft) within 7 metres (22ft) of your property? is not restored",
					driver, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Buildings policy by selecting Contents, click cancel button in people and perform the same and procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_073(String browser) throws Exception {

		String tcId = "TC_73";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInBuildingConstructionIsPropertyGoodState(extentedReport, true),
					"The value for Is the property in a good state of repair? is restored",
					"The value for Is the property in a good state of repair? is not restored", driver, extentedReport,
					true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"TCNO_17780_Add Specified item is verified in Within Home",
					"TCNO_17780_Add Specified item is not verified within Home", driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Cancel Policy
			changeMyProperty.clickCancelButton(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange"),
							mTAInitiatePage),
					"On clicking on Cancel button on 'Change my proprty' main screen navigates the user to Change My Policy screen without any change",
					"On clicking on Cancel button on 'Change my proprty' main screen did not navigates the user to Change My Policy screen without any change",
					driver, extentedReport);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Select change type
			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);
			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Buildings&Contentc policy by selecting Buildings&Contentc, click cancel button in people and perform the same and procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_074(String browser) throws Exception {

		String tcId = "TC_74";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInBuildingConstructionIsPropertySelfContained(extentedReport, true),
					"The value for Is the property self-contained and lockable? is restored",
					"The value for Is the property self-contained and lockable? is not restored", driver,
					extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("fldRebuildCost"), changeMyProperty),
					"TCNO_17684_17691_Building Details are showned", "TCNO_17692_Building Details are not showned",
					driver, extentedReport);

			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("fldTotalValueOfYourHomeContents"),
							changeMyProperty),
					"TCNO_17684_17691_Content Details are not showned", "TCNO_17685_Content Details are showned",
					driver, extentedReport);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"TCNO_17840_Add Occupation is verified in People page",
					"TCNO_17840_Add Occupation is not verified People page", driver, extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Cancel policy
			changeMyProperty.clickCancelButton(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange"),
							mTAInitiatePage),
					"On clicking on Cancel button on 'Change my proprty' main screen navigates the user to Change My Policy screen without any change",
					"On clicking on Cancel button on 'Change my proprty' main screen did not navigates the user to Change My Policy screen without any change",
					driver, extentedReport);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);
			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Buildings&Contentc policy by selecting Buildings, and add occupation then procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_075(String browser) throws Exception {

		String tcId = "TC_75";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("txtPostcodeOfProperty"),
							changeMyProperty),
					"TCNO_17695_Verified the availability of the 'Post code of the property to be insured' under 'The property to be insured'",
					"TCNO_17695_Not verified the availability of the 'Post code of the property to be insured' under 'The property to be insured'",
					driver, extentedReport);

			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);
			
			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);

			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			Log.softAssertThat(changeMyProperty.verifyRestoreInSecuritySectionIsSafeInstalled(extentedReport, true),
					"The value for Is there a safe installed within the property? is restored",
					"The value for Is there a safe installed within the property? is not restored", driver,
					extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);
			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Buildings&Contentc policy by selecting Contents, click cancel button in people and perform the same and procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_076(String browser) throws Exception {

		String tcId = "TC_76";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickNoButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInSecuritySectionIsPropertyOccupiedInDay(extentedReport, true),
					"The value for Is the property occupied during the day? is restored",
					"The value for Is the property occupied during the day? is not restored", driver, extentedReport,
					true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Cancel Policy
			changeMyProperty.clickCancelButton(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange"),
							mTAInitiatePage),
					"On clicking on Cancel button on 'Change my proprty' main screen navigates the user to Change My Policy screen without any change",
					"On clicking on Cancel button on 'Change my proprty' main screen did not navigates the user to Change My Policy screen without any change",
					driver, extentedReport);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Select change type
			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);
			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Buildings policy by selecting Contents, procced for Quote & verify Premium and buy the Quote", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_077(String browser) throws Exception {

		String tcId = "TC_77";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInSecuritySectionIsPropertyOccupiedInNight(extentedReport, true),
					"The value for Is the property occupied at night? is restored",
					"The value for Is the property occupied at night? is not restored", driver, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			Log.softAssertThat(
					!changeMyProperty.uielement.verifyPageElements(Arrays.asList("fldRebuildCost"), changeMyProperty),
					"TCNO_17686_Building Details are not showned", "TCNO_17686_Building Details are showned", driver,
					extentedReport);

			changeMyProperty.clickContinue(extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("fldTotalValueOfYourHomeContents"),
							changeMyProperty),
					"TCNO_17693_Content Details are showned", "TCNO_17693_Content Details are Not showned", driver,
					extentedReport);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	//@Test(description = "Perform Change my property on Buildings & Contents policy by selecting Buildings & Contents, procced for Quote & verify Premium and buy the Quote", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_078(String browser) throws Exception {

		String tcId = "TC_78";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			Log.softAssertThat(changeMyProperty.verifyRestoreInSecuritySectionIsAlarmInstalled(extentedReport, true),
					"The value for Does the property have an alarm installed? is restored",
					"The value for Does the property have an alarm installed? is not restored", driver, extentedReport,
					true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);
			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(description = "Perform Change my property on Buildings&Contentc policy by selectinging Contents cover and add specified item, add second occupation then procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_079(String browser) throws Exception {

		String tcId = "TC_79";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInSecuritySectionIsPropertyOccupiedInDay(extentedReport, true),
					"The value for Is the property occupied during the day? is restored",
					"The value for Is the property occupied during the day? is not restored", driver, extentedReport,
					true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"TCNO_17790_Add Specified item is verified outside Home ",
					"TCNO_17790_Add Specified item is not verified outside Home", driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Perform Change my property on Buildings policy by selectinging Contents cover, and procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_080(String browser) throws Exception {

		String tcId = "TC_80";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			// Select change type
			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			Log.softAssertThat(changeMyProperty.verifyRestoreInSecuritySectionIsBritishKitemark(extentedReport, true),
					"The value for Are British Standard Kitemark (BS3621) approved locks fitted? is restored",
					"The value for Are British Standard Kitemark (BS3621) approved locks fitted? is not restored",
					driver, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	@Test(description = "Check the user is able to selecting save and cancel buttons in the property address bfore added", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_081(String browser) throws Exception {

		String tcId = "TC_81";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			// Select change type
			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			changeMyProperty.enterAddressOfPropToBeInsured(testData.arrPostCodeH[0], extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.arrPostCodeH[0], extentedReport, true);

			changeMyProperty.editAddedAddressOfProp(testData.arrPostCodeH[1], extentedReport, true);
			
			changeMyProperty.clickChangeAddressBtn(extentedReport, true);
			changeMyProperty.clickEnterManualAddressBtn(extentedReport, true);
			
			changeMyProperty.enterAddressManually("Postcode", testData.arrPostCodeH[2],
					extentedReport, true);
			changeMyProperty.enterAddressManually("Address1", testData.arrAddressH[0],
					extentedReport, true);
			changeMyProperty.enterAddressManually("Address2", testData.arrAddressH[1],
					extentedReport, true);
			changeMyProperty.enterAddressManually("Address3", testData.arrAddressH[2],
					extentedReport, true);
			changeMyProperty.enterAddressManually("Address4", testData.arrAddressH[3],
					extentedReport, true);
			
			changeMyProperty.clickSaveAddressBtn(extentedReport, true);
			
			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("lblAddressAdded"), changeMyProperty),
					"Added Address is verified : ", "Added Address is Not verified : ", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(description = "Verify that user is able to click cancel on proerty page of Change my property and navigate back to MTA initiate page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_082(String browser) throws Exception {

		String tcId = "TC_82";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			// Select change type
			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInSecuritySectionAreLocksActivatedAtNight(extentedReport, true),
					"The value for Are all locks activated at night and when the property is unoccupied? is restored",
					"The value for Are all locks activated at night and when the property is unoccupied? is not restored",
					driver, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInSecuritySectionDoesPropertyHaveExternalLight(extentedReport, true),
					"The value for Does the property have external security lighting? is restored",
					"The value for Does the property have external security lighting? is not restored", driver,
					extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickCancelButton(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange"),
							mTAInitiatePage),
					"On clicking on Cancel button on 'Chage My Property' main screen navigates the user to Change My Policy screen without any change",
					"On clicking on Cancel button on 'Chage My Property' main screen did not navigates the user to Change My Policy screen without any change",
					driver, extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that user is able to click cancel on Contents page of Change my property and navigate back to MTA initiate page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_083(String browser) throws Exception {

		String tcId = "TC_83";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			// Select change type
			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			Log.softAssertThat(changeMyProperty.verifyRestoreInSecuritySectionAreYouANeighbour(extentedReport, true),
					"The value for Are you a member of an active Neighbourhood Watch Scheme? is restored",
					"The value for Are you a member of an active Neighbourhood Watch Scheme? is not restored", driver,
					extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);

			changeMyProperty.clickCancelButton(extentedReport, true);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange"),
							mTAInitiatePage),
					"On clicking on Cancel button on 'Chage My Property' main screen navigates the user to Change My Policy screen without any change",
					"On clicking on Cancel button on 'Chage My Property' main screen did not navigates the user to Change My Policy screen without any change",
					driver, extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(groups = {
	"Smoke" },description = "Perform Change my property on Buildings&Contents policy by selecting Contents and Verify the existing specified item and occupation and procced for Quote & verify Premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_084(String browser) throws Exception {

		String tcId = "TC_84";
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
					"TCNO_17681_Change My Property cover is displayed in MTA intiate page",
					"TCNO_17681_Change My Property cover is not displayed in MTA intiate page", driver, extentedReport,
					true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyRestoreInSecuritySectionIsPropertyFittedSmoke(extentedReport, true),
					"The value for Is the property fitted with a smoke detector? is restored",
					"The value for Is the property fitted with a smoke detector? is not restored", driver,
					extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
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

	//@Test(groups = {"Smoke" },description = "Perform Change my property on Buildings policy by selecting Contents and add specified item and occupation, Buy the policy. Verify that change my property details are updated in Back office", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_085(String browser) throws Exception {

		String tcId = "TC_85";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.itemNameWH, extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.itemNameOH, extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);
			
			Log.message(
					"TCNO_17690_Verify that selected contents option for the policy is updated in Back office, then the existing Content data is overwritten");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	//@Test(groups = {"Smoke" },description = "Perform Change my property on Buildings policy by selecting Contents, Buy the policy. Verify that change my property details are updated in Back office", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_086(String browser) throws Exception {

		String tcId = "TC_86";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);
			
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	//@Test(groups = {"Smoke" },description = "Perform Change my property on Contents policy by selecting Buildings&Contents and add specified item and occupation, Buy the policy. Verify that change my property details are updated in Back office", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_087(String browser) throws Exception {

		String tcId = "TC_87";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);
			changeMyProperty.clickNextSecurityDetails(extentedReport, true);
			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			
			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),
							mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Insurance Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);
			
			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			
			mTAPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);
			
			CardDetailsPage cardDetails = mTAPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
			
			cardDetails.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport,
					true);
			//cardDetails.enterVerification(extentedReport, true);
			cardDetails.clickbuy(extentedReport, false);
			
			SuccessPage success = new SuccessPage(driver).get();
			
			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");
			
			Log.message(
					"TCNO_17687_Verify that selected buildings option for the policy is updated in Back office, then the existing contents data is overwritten");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	//@Test(groups = {"Smoke" },description = "Perform Change my property on Contents policy by selecting Buildings and add occupation, Buy the policy. Verify that change my property details are updated in Back office", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_088(String browser) throws Exception {

		String tcId = "TC_88";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);
			// Enter building details
			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);
			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);
			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);
			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			changeMyProperty.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyProperty.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"),
							mTAQuoteDetailsPage),
					"Refund Premium amount is displayed when try to Change My Property on House hold Policy for change type : "
							+ testData.typeOfChange,
							"Refund Premium amount is not displayed when try to Change My Property on House hold Policy for change type : "
									+ testData.typeOfChange,
									driver, extentedReport);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			Log.message(
					"TCNO_17687_17688_Verify that selected buildings option for the policy is updated in Back office, then the existing contents data is overwritten");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(description = "Verify that user is able to add more then one specified item, and able to edit and delete the add specified items", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_089(String browser) throws Exception {

		String tcId = "TC_89";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);
			changeMyProperty.clickNextSecurityDetails(extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);
			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);
			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);
			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// enter Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);
			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Specified Items details
			// Enter details in Specified Covers
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.arritemTypeWH[0], testData.itemValueWH,
					testData.arritemDecsriptionWH[0], testData.arrsaveAndCancelWH[0], extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[0], extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[0],
					extentedReport, true);
			changeMyProperty.addSpecifiedItemwithinHome(testData.arritemTypeWH[1], testData.itemValueWH,
					testData.arritemDecsriptionWH[1], testData.arrsaveAndCancelWH[1], extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[1], extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.editAddItemWithinHome(testData.arritemNameWH[0], testData.arritemTypeWH[2],
					testData.itemValueWH, testData.arritemDecsriptionWH[2], testData.arrsaveAndCancelWH[2],
					extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[2], extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);

			changeMyProperty.deleteAddItemInWithinHome(testData.arritemNameWH[2], testData.deleteItemWH, extentedReport,
					true);

			Log.softAssertThat(
					!changeMyProperty.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[2], extentedReport, true),
					"Add Specified item is Deleted in Within Home", "Add Specified item is not Deleted within Home",
					driver, extentedReport, true);

			changeMyProperty.headersVerificationForTableInWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[1],
					extentedReport, true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.arritemTypeOH[0], testData.itemValueOH,
					testData.arritemDecsriptionOH[0], testData.arrsaveAndCancelOH[0], extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.arritemNameOH[0], extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.arraddAnotherItemInOutsideHome[0],
					extentedReport, true);
			changeMyProperty.addSpecifiedItemOutsideHome(testData.arritemTypeOH[1], testData.itemValueOH,
					testData.arritemDecsriptionOH[1], testData.arrsaveAndCancelOH[1], extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.arritemNameOH[1], extentedReport, true),
					"TCNO_17785_Add Specified item is verified outside Home ",
					"TCNO_17785_Add Specified item is not verified outside Home", driver, extentedReport, true);

			changeMyProperty.editAddItemOutsideHome(testData.arritemNameOH[0], testData.arritemTypeOH[2],
					testData.itemValueOH, testData.arritemDecsriptionOH[2], testData.arrsaveAndCancelOH[2],
					extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.arritemNameOH[2], extentedReport, true),
					"Add Specified item is verified outside Home ", "Add Specified item is not verified outside Home",
					driver, extentedReport, true);

			changeMyProperty.deleteAddItemInOutsideHome(testData.arritemNameOH[2], testData.deleteItemOH, extentedReport,
					true);

			Log.softAssertThat(
					!changeMyProperty.verifyDeleteAndEditBtnInOutsideHome(testData.arritemNameOH[2], extentedReport, true),
					"Add Specified item is Deleted outside Home ", "Add Specified item is not Deleted outside Home",
					driver, extentedReport, true);

			changeMyProperty.headersVerificationForTableInOutsideHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.arraddAnotherItemInOutsideHome[1],
					extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(description = "Verify the help text in your property page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_090(String browser) throws Exception {

		String tcId = "TC_90";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpTitleYourProperty"),
							changeMyProperty),
					"TCNO_10744_Help icon (?) is displayed against the 'Title Your Property' text in Change my property",
					"Help icon (?) is not displayed against the 'Title Your Property' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconTitleYourProperty(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconTitleYourProperty(),
					changeMyProperty.POPUP_MSG_HELPICON_Title_Your_Property
					+ " - Message is displayed when click/hover the help icon (?) against the 'Title Your Property' text",
					changeMyProperty.POPUP_MSG_HELPICON_Title_Your_Property
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Title Your Property' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpBuildingsContents"),
							changeMyProperty),
					"TCNO_10745_Help icon (?) is displayed against the 'Buildings & Contents' text in Change my property",
					"TCNO_10745_Help icon (?) is not displayed against the 'Buildings & Contents' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconBuildingsContents(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconBuildingsContents(),
					changeMyProperty.POPUP_MSG_HELPICON_Buildings_Contents
					+ " - Message is displayed when click/hover the help icon (?) against the 'Buildings & Contents' text",
					changeMyProperty.POPUP_MSG_HELPICON_Buildings_Contents
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Buildings & Contents' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpBuildings"), changeMyProperty),
					"TCNO_10746_Help icon (?) is displayed against the 'Buildings' text in Change my property",
					"TCNO_10746_Help icon (?) is not displayed against the 'Buildings' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconBuildings(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconBuildings(),
					changeMyProperty.POPUP_MSG_HELPICON_Buildings
					+ " - Message is displayed when click/hover the help icon (?) against the 'Buildings' text",
					changeMyProperty.POPUP_MSG_HELPICON_Buildings
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Buildings' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpContents"), changeMyProperty),
					"TCNO_10747_Help icon (?) is displayed against the 'Contents' text in Change my property",
					"TCNO_10747_Help icon (?) is not displayed against the 'Contents' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconContents(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconContents(),
					changeMyProperty.POPUP_MSG_HELPICON_Contents
					+ " - Message is displayed when click/hover the help icon (?) against the 'Contents' text",
					changeMyProperty.POPUP_MSG_HELPICON_Contents
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Contents' text",
					driver, extentedReport);

			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			// Enter address
			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpAddressofPropertyToBeInsured"),
							changeMyProperty),
					"TCNO_10756_Help icon (?) is displayed against the 'Address of Property To Be Insured' text in Change my property",
					"TCNO_10756_Help icon (?) is not displayed against the 'Address of Property To Be Insured' text in Change my property",
					driver, extentedReport);
			
			changeMyProperty.hoverOnHelpIconPostcodeOfProperty(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconAddressofPropertyToBeInsured(),
					changeMyProperty.POPUP_MSG_HELPICON_Postcode_Of_Property
					+ " - Message is displayed when click/hover the help icon (?) against the 'Postcode Of Property' text",
					changeMyProperty.POPUP_MSG_HELPICON_Postcode_Of_Property
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Postcode Of Property' text",
					driver, extentedReport);

			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpYourCorrespondenceAddress"),
							changeMyProperty),
					"TCNO_10757_Help icon (?) is displayed against the 'Your Correspondence Address' text in Change my property",
					"TCNO_10757_Help icon (?) is not displayed against the 'Your Correspondence Address' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconCorrespondenceAddressToUpdate(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconCorrespondenceAddressToUpdate(),
					changeMyProperty.POPUP_MSG_HELPICON_Correspondence_Address_To_Update
					+ " - Message is displayed when click/hover the help icon (?) against the 'Correspondence Address To Update' text",
					changeMyProperty.POPUP_MSG_HELPICON_Correspondence_Address_To_Update
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Correspondence Address To Update' text",
					driver, extentedReport);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpTypeOfProperty"),
							changeMyProperty),
					"TCNO_10759_Help icon (?) is displayed against the 'Type Of Property' text in Change my property",
					"TCNO_10759_Help icon (?) is not displayed against the 'Type Of Property' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconTypeOfProperty(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconTypeOfProperty(),
					changeMyProperty.POPUP_MSG_HELPICON_Type_Of_Property
					+ " - Message is displayed when click/hover the help icon (?) against the 'Type Of Property' text",
					changeMyProperty.POPUP_MSG_HELPICON_Type_Of_Property
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Type Of Property' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpDateOfResidence"),
							changeMyProperty),
					"TCNO_10760_Help icon (?) is displayed against the 'Date Of Residence' text in Change my property",
					"TCNO_10760_Help icon (?) is not displayed against the 'Date Of Residence' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconDateOfResidence(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconDateOfResidence(),
					changeMyProperty.POPUP_MSG_HELPICON_Date_Of_Residence
					+ " - Message is displayed when click/hover the help icon (?) against the 'Date Of Residence' text",
					changeMyProperty.POPUP_MSG_HELPICON_Date_Of_Residence
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Date Of Residence' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpYearYourPropertyWasBuilt"),
							changeMyProperty),
					"TCNO_10761_Help icon (?) is displayed against the 'Year Your Property Was Built' text in Change my property",
					"TCNO_10761_Help icon (?) is not displayed against the 'Year Your Property Was Built' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconYearYourPropertyWasBuilt(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconYearYourPropertyWasBuilt(),
					changeMyProperty.POPUP_MSG_HELPICON_Year_Your_Property_Was_Built
					+ " - Message is displayed when click/hover the help icon (?) against the 'Year Your Property Was Built' text",
					changeMyProperty.POPUP_MSG_HELPICON_Year_Your_Property_Was_Built
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Year Your Property Was Built' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpNumberOfBathRooms"),
							changeMyProperty),
					"TCNO_10762_Help icon (?) is displayed against the 'Number Of Bath Rooms' text in Change my property",
					"TCNO_10762_Help icon (?) is not displayed against the 'Number Of Bath Rooms' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconNumberOfBathRooms(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconNumberOfBathRooms(),
					changeMyProperty.POPUP_MSG_HELPICON_Number_Of_Bath_Rooms
					+ " - Message is displayed when click/hover the help icon (?) against the 'Number Of Bath Rooms' text",
					changeMyProperty.POPUP_MSG_HELPICON_Number_Of_Bath_Rooms
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Number Of Bath Rooms' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpNumberOfBedrooms"),
							changeMyProperty),
					"TCNO_10763_Help icon (?) is displayed against the 'Number Of bedrooms' text in Change my property",
					"TCNO_10763_Help icon (?) is not displayed against the 'Number Of bedrooms' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconNumberOfBedrooms(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconNumberOfBedrooms(),
					changeMyProperty.POPUP_MSG_HELPICON_Number_Of_Bedrooms
					+ " - Message is displayed when click/hover the help icon (?) against the 'Number Of bedrooms' text",
					changeMyProperty.POPUP_MSG_HELPICON_Number_Of_Bedrooms
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Number Of bedrooms' text",
					driver, extentedReport);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpTypeOfWallConstruction"),
							changeMyProperty),
					"TCNO_10764_Help icon (?) is displayed against the 'Type of wall construction' text in Change my property",
					"TCNO_10764_Help icon (?) is not displayed against the 'Type of wall construction' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconTypeOfWallConstruction(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconTypeOfWallConstruction(),
					changeMyProperty.POPUP_MSG_HELPICON_Type_Of_Wall_Construction
					+ " - Message is displayed when click/hover the help icon (?) against the 'Type of wall construction' text",
					changeMyProperty.POPUP_MSG_HELPICON_Type_Of_Wall_Construction
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Type of wall construction' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpTypeOfRoofConstruction"),
							changeMyProperty),
					"TCNO_10765_Help icon (?) is displayed against the 'Type of roof construction' text in Change my property",
					"TCNO_10765_Help icon (?) is not displayed against the 'Type of roof construction' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconTypeOfRoofConstruction(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconTypeOfRoofConstruction(),
					changeMyProperty.POPUP_MSG_HELPICON_Type_Of_Roof_Construction
					+ " - Message is displayed when click/hover the help icon (?) against the 'Type of roof construction' text",
					changeMyProperty.POPUP_MSG_HELPICON_Type_Of_Roof_Construction
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Type of roof construction' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpRoofThatIsFlat"),
							changeMyProperty),
					"TCNO_10766_Help icon (?) is displayed against the 'Roof That Is Flat' text in Change my property",
					"TCNO_10766_Help icon (?) is not displayed against the 'Roof That Is Flat' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconRoofThatIsFlat(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconRoofThatIsFlat(),
					changeMyProperty.POPUP_MSG_HELPICON_Roof_That_Is_Flat
					+ " - Message is displayed when click/hover the help icon (?) against the 'Roof That Is Flat' text",
					changeMyProperty.POPUP_MSG_HELPICON_Roof_That_Is_Flat
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Roof That Is Flat' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpTypeOfListedBuildingIfAppropriate"), changeMyProperty),
					"TCNO_10767_Help icon (?) is displayed against the 'Type of listed building if appropriate' text in Change my property",
					"TCNO_10767_Help icon (?) is not displayed against the 'Type of listed building if appropriate' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconTypeOfListedBuildingIfAppropriate(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconTypeOfListedBuildingIfAppropriate(),
					changeMyProperty.POPUP_MSG_HELPICON_Type_Of_Listed_Building_If_Appropriate
					+ " - Message is displayed when click/hover the help icon (?) against the 'Type of listed building if appropriate' text",
					changeMyProperty.POPUP_MSG_HELPICON_Type_Of_Listed_Building_If_Appropriate
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Type of listed building if appropriate' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpPropertyBeenExtended"),
							changeMyProperty),
					"TCNO_10768_Help icon (?) is displayed against the 'property been extended' text in Change my property",
					"TCNO_10768_Help icon (?) is not displayed against the 'property been extended' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconPropertyBeenExtended(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPropertyBeenExtended(),
					changeMyProperty.POPUP_MSG_HELPICON_Property_Been_Extended
					+ " - Message is displayed when click/hover the help icon (?) against the 'property been extended' text",
					changeMyProperty.POPUP_MSG_HELPICON_Property_Been_Extended
					+ " - Message is not displayed when click/hover the help icon (?) against the 'property been extended' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpPropertyUsedForBusinessPurposes"), changeMyProperty),
					"TCNO_10769_Help icon (?) is displayed against the 'property used for business purposes' text in Change my property",
					"TCNO_10769_Help icon (?) is not displayed against the 'property used for business purposes' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconPropertyUsedForBusinessPurposes(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPropertyUsedForBusinessPurposes(),
					changeMyProperty.POPUP_MSG_HELPICON_Property_Used_For_Business_Purposes
					+ " - Message is displayed when click/hover the help icon (?) against the 'property used for business purposes' text",
					changeMyProperty.POPUP_MSG_HELPICON_Property_Used_For_Business_Purposes
					+ " - Message is not displayed when click/hover the help icon (?) against the 'property used for business purposes' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpPropertyFreeFromSubsidence"),
							changeMyProperty),
					"TCNO_10771_Help icon (?) is displayed against the 'property free from subsidence' text in Change my property",
					"TCNO_10771_Help icon (?) is not displayed against the 'property free from subsidence' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconPropertyFreeFromSubsidence(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPropertyFreeFromSubsidence(),
					changeMyProperty.POPUP_MSG_HELPICON_Property_Free_From_Subsidence
					+ " - Message is displayed when click/hover the help icon (?) against the 'property free from subsidence' text",
					changeMyProperty.POPUP_MSG_HELPICON_Property_Free_From_Subsidence
					+ " - Message is not displayed when click/hover the help icon (?) against the 'property free from subsidence' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpPropertyFreeFromFlooding"),
							changeMyProperty),
					"Help icon (?) is displayed against the 'property free from Flooding' text in Change my property",
					"Help icon (?) is not displayed against the 'property free from Flooding' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconPropertyFreeFromFlooding(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPropertyFreeFromFlooding(),
					changeMyProperty.POPUP_MSG_HELPICON_Property_Free_From_Flooding
					+ " - Message is displayed when click/hover the help icon (?) against the 'property free from Flooding' text",
					changeMyProperty.POPUP_MSG_HELPICON_Property_Free_From_Flooding
					+ " - Message is not displayed when click/hover the help icon (?) against the 'property free from Flooding' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpPropertyEverBeenUnderpinned"),
							changeMyProperty),
					"TCNO_10772_Help icon (?) is displayed against the 'property Ever Been Underpinned' text in Change my property",
					"TCNO_10772_Help icon (?) is not displayed against the 'property Ever Been Underpinned' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconPropertyEverBeenUnderpinned(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPropertyEverBeenUnderpinned(),
					changeMyProperty.POPUP_MSG_HELPICON_Property_Ever_Been_Underpinned
					+ " - Message is displayed when click/hover the help icon (?) against the 'property Ever Been Underpinned' text",
					changeMyProperty.POPUP_MSG_HELPICON_Property_Ever_Been_Underpinned
					+ " - Message is not displayed when click/hover the help icon (?) against the 'property Ever Been Underpinned' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpPropertyWithin400MetresOfWater"), changeMyProperty),
					"TCNO_10773_Help icon (?) is displayed against the 'property within 400 metres (¼ mile) of water' text in Change my property",
					"TCNO_10773_Help icon (?) is not displayed against the 'property within 400 metres (¼ mile) of water' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconPropertyWithin400MetresPfWater(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPropertyWithin400MetresOfWater(),
					changeMyProperty.POPUP_MSG_HELPICON_Property_Within_400_Metres_Of_Water
					+ " - Message is displayed when click/hover the help icon (?) against the 'property within 400 metres (¼ mile) of water' text",
					changeMyProperty.POPUP_MSG_HELPICON_Property_Within_400_Metres_Of_Water
					+ " - Message is not displayed when click/hover the help icon (?) against the 'property within 400 metres (¼ mile) of water' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpTreesTallerThan5Metres"),
							changeMyProperty),
					"TCNO_10774_Help icon (?) is displayed against the 'trees or shrubs taller than 5 metres(16ft) within 7 metres (22ft) of your property' text in Change my property",
					"TCNO_10774_Help icon (?) is not displayed against the 'trees or shrubs taller than 5 metres(16ft) within 7 metres (22ft) of your property' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconTreesTallerThan5Metres(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconTreesTallerThan5Metres(),
					changeMyProperty.POPUP_MSG_HELPICON_Trees_Taller_Than_5_Metres
					+ " - Message is displayed when click/hover the help icon (?) against the 'trees or shrubs taller than 5 metres(16ft) within 7 metres (22ft) of your property' text",
					changeMyProperty.POPUP_MSG_HELPICON_Trees_Taller_Than_5_Metres
					+ " - Message is not displayed when click/hover the help icon (?) against the 'trees or shrubs taller than 5 metres(16ft) within 7 metres (22ft) of your property' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpPropertyInAGoodStateOfRepair"),
							changeMyProperty),
					"TCNO_10775_Help icon (?) is displayed against the 'property in a good state of repair' text in Change my property",
					"TCNO_10775_Help icon (?) is not displayed against the 'property in a good state of repair' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconPropertyInAGoodStateOfRepair(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPropertyInAGoodStateOfRepair(),
					changeMyProperty.POPUP_MSG_HELPICON_Property_In_A_Good_State_Of_Repair
					+ " - Message is displayed when click/hover the help icon (?) against the 'property in a good state of repair' text",
					changeMyProperty.POPUP_MSG_HELPICON_Property_In_A_Good_State_Of_Repair
					+ " - Message is not displayed when click/hover the help icon (?) against the 'property in a good state of repair' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpPropertySelfContainedAndLockable"), changeMyProperty),
					"TCNO_10776_Help icon (?) is displayed against the 'property self-contained and lockable' text in Change my property",
					"TCNO_10776_Help icon (?) is not displayed against the 'property self-contained and lockable' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconPropertySelfContainedAndLockable(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPropertySelfContainedAndLockable(),
					changeMyProperty.POPUP_MSG_HELPICON_Property_Self_Contained_And_Lockable
					+ " - Message is displayed when click/hover the help icon (?) against the 'property self-contained and lockable' text",
					changeMyProperty.POPUP_MSG_HELPICON_Property_Self_Contained_And_Lockable
					+ " - Message is not displayed when click/hover the help icon (?) against the 'property self-contained and lockable' text",
					driver, extentedReport);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpOccupiedDuringTheDay"),
							changeMyProperty),
					"TCNO_10777_Help icon (?) is displayed against the 'property occupied during the day' text in Change my property",
					"TCNO_10777_Help icon (?) is not displayed against the 'property occupied during the day' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconPropertyOccupiedDuringTheDay(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPropertyOccupiedDuringTheDay(),
					changeMyProperty.POPUP_MSG_HELPICON_Property_Occupied_During_The_Day
					+ " - Message is displayed when click/hover the help icon (?) against the 'property occupied during the day' text",
					changeMyProperty.POPUP_MSG_HELPICON_Property_Occupied_During_The_Day
					+ " - Message is not displayed when click/hover the help icon (?) against the 'property occupied during the day' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpOccupiedAtNight"),
							changeMyProperty),
					"TCNO_10779_Help icon (?) is displayed against the 'property occupied at night' text in Change my property",
					"TCNO_10779_Help icon (?) is not displayed against the 'property occupied at night' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconPropertyOccupiedAtNight(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPropertyOccupiedAtNight(),
					changeMyProperty.POPUP_MSG_HELPICON_Property_Occupied_At_Night
					+ " - Message is displayed when click/hover the help icon (?) against the 'property occupied at night' text",
					changeMyProperty.POPUP_MSG_HELPICON_Property_Occupied_At_Night
					+ " - Message is not displayed when click/hover the help icon (?) against the 'property occupied at night' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpPropertyHaveAnAlarmInstalled"),
							changeMyProperty),
					"TCNO_10780_Help icon (?) is displayed against the 'property have an alarm installed' text in Change my property",
					"TCNO_10780_Help icon (?) is not displayed against the 'property have an alarm installed' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconPropertyHaveAnAlarmInstalled(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPropertyHaveAnAlarmInstalled(),
					changeMyProperty.POPUP_MSG_HELPICON_Property_Have_An_Alarm_Installed
					+ " - Message is displayed when click/hover the help icon (?) against the 'property have an alarm installed' text",
					changeMyProperty.POPUP_MSG_HELPICON_Property_Have_An_Alarm_Installed
					+ " - Message is not displayed when click/hover the help icon (?) against the 'property have an alarm installed' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpBritishStandardKitemark"),
							changeMyProperty),
					"TCNO_10784_Help icon (?) is displayed against the 'Are British Standard Kitemark (BS3621) approved locks fitted' text in Change my property",
					"TCNO_10784_Help icon (?) is not displayed against the 'Are British Standard Kitemark (BS3621) approved locks fitted' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconBritishStandardKitemark(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconBritishStandardKitemark(),
					changeMyProperty.POPUP_MSG_HELPICON_British_Standard_Kitemark
					+ " - Message is displayed when click/hover the help icon (?) against the 'Are British Standard Kitemark (BS3621) approved locks fitted' text",
					changeMyProperty.POPUP_MSG_HELPICON_British_Standard_Kitemark
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Are British Standard Kitemark (BS3621) approved locks fitted' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpPropertyHaveExternalSecurityLighting"), changeMyProperty),
					"TCNO_10785_Help icon (?) is displayed against the 'property have external security lighting' text in Chage my property Details",
					"TCNO_10785_Help icon (?) is not displayed against the 'property have external security lighting' text in Chage my property Details",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconPropertyHaveExternalSecurityLighting(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPropertyHaveExternalSecurityLighting(),
					changeMyProperty.POPUP_MSG_HELPICON_Property_Have_External_Security_Lighting
					+ " - Message is displayed when click/hover the help icon (?) against the 'property have external security lighting' text",
					changeMyProperty.POPUP_MSG_HELPICON_Property_Have_External_Security_Lighting
					+ " - Message is not displayed when click/hover the help icon (?) against the 'property have external security lighting' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpLocksActivatedAtNight"),
							changeMyProperty),
					"TCNO_10786_Help icon (?) is displayed against the 'Are all locks activated at night and when the property is unoccupied' text in Chage my property Details",
					"TCNO_10786_Help icon (?) is not displayed against the 'Are all locks activated at night and when the property is unoccupied' text in Chage my property Details",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconLocksActivatedAtNight(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconLocksActivatedAtNight(),
					changeMyProperty.POPUP_MSG_HELPICON_Locks_Activated_At_Night
					+ " - Message is displayed when click/hover the help icon (?) against the 'Are all locks activated at night and when the property is unoccupied' text",
					changeMyProperty.POPUP_MSG_HELPICON_Locks_Activated_At_Night
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Are all locks activated at night and when the property is unoccupied' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpNeighbourhoodWatchScheme"),
							changeMyProperty),
					"TCNO_10787_Help icon (?) is displayed against the 'Are you a member of an active Neighbourhood Watch Scheme' text in Chage my property Details",
					"TCNO_10787_Help icon (?) is not displayed against the 'Are you a member of an active Neighbourhood Watch Scheme' text in Chage my property Details",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconNeighbourhoodWatchScheme(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconNeighbourhoodWatchScheme(),
					changeMyProperty.POPUP_MSG_HELPICON_Neighbourhood_Watch_Scheme
					+ " - Message is displayed when click/hover the help icon (?) against the 'Are you a member of an active Neighbourhood Watch Scheme' text",
					changeMyProperty.POPUP_MSG_HELPICON_Neighbourhood_Watch_Scheme
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Are you a member of an active Neighbourhood Watch Scheme' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpPropertyFittedWithASmokeDetector"), changeMyProperty),
					"TCNO_10788_Help icon (?) is displayed against the 'property fitted with a smoke detector' text in Chage my property Details",
					"TCNO_10788_Help icon (?) is not displayed against the 'property fitted with a smoke detector' text in Chage my property Details",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconPropertyFittedWithASmokeDetector(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPropertyFittedWithASmokeDetector(),
					changeMyProperty.POPUP_MSG_HELPICON_Property_Fitted_With_A_Smoke_Detector
					+ " - Message is displayed when click/hover the help icon (?) against the 'property fitted with a smoke detector' text",
					changeMyProperty.POPUP_MSG_HELPICON_Property_Fitted_With_A_Smoke_Detector
					+ " - Message is not displayed when click/hover the help icon (?) against the 'property fitted with a smoke detector' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpSafeInstalledWithinTheProperty"), changeMyProperty),
					"TCNO_10789_Help icon (?) is displayed against the 'safe installed within the property' text in Chage my property Details",
					"TCNO_10789_Help icon (?) is not displayed against the 'safe installed within the property' text in Chage my property Details",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconSafeInstalledWithinTheProperty(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconSafeInstalledWithinTheProperty(),
					changeMyProperty.POPUP_MSG_HELPICON_Safe_Installed_Within_The_Property
					+ " - Message is displayed when click/hover the help icon (?) against the 'safe installed within the property' text",
					changeMyProperty.POPUP_MSG_HELPICON_Safe_Installed_Within_The_Property
					+ " - Message is not displayed when click/hover the help icon (?) against the 'safe installed within the property' text",
					driver, extentedReport);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);

			changeMyProperty.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, true);
			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpRebuildCost"),
							changeMyProperty),
					"TCNO_10790_Help icon (?) is displayed against the 'Rebuild Cost' field in Change my property",
					"TCNO_10790_Help icon (?) is not displayed against the 'Rebuild Cost' field in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfRebuildCost(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconRebuildCostPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Rebuild_Cost
					+ " - Message is displayed when click/hover the help icon (?) against the 'Rebuild Cost' field",
					changeMyProperty.POPUP_MSG_HELPICON_Rebuild_Cost
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Rebuild Cost' field",
					driver, extentedReport);

			changeMyProperty.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, true);
			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpAccidentalDamage"),
							changeMyProperty),
					"TCNO_10791_Help icon (?) is displayed against the 'Accidental Damage' button in Change my property",
					"TCNO_10791_Help icon (?) is not displayed against the 'Accidental Damage' button in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfAccidentalDamage(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconAccidentalDamagePopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Accidental_Damage
					+ " - Message is displayed when click/hover the help icon (?) against the 'Accidental Damage' button",
					changeMyProperty.POPUP_MSG_HELPICON_Accidental_Damage
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Accidental Damage' button",
					driver, extentedReport);

			changeMyProperty.selectExcessValueB(testData.excessvalue, extentedReport, true);
			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpTotalExcessRequired"),
							changeMyProperty),
					"TCNO_10792_Help icon (?) is displayed against the 'Total Excess Requirede' field in Change my property",
					"TCNO_10792_Help icon (?) is not displayed against the 'Total Excess Requirede' field in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfTotalExcessRequired(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconTotalExcessRequiredePopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Total_Excess_Required_B
					+ " - Message is displayed when click/hover the help icon (?) against the 'Total Excess Requirede' field",
					changeMyProperty.POPUP_MSG_HELPICON_Total_Excess_Required_B
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Total Excess Requirede' field",
					driver, extentedReport);

			changeMyProperty.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport,
					true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpPreviousBuildingInsurance"),
							changeMyProperty),
					"TCNO_10794_Help icon (?) is displayed against the 'Previous Building Insurance' button in Change my property",
					"TCNO_10794_Help icon (?) is not displayed against the 'Previous Building Insurance' button in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfPreviousBuildingInsurance(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPreviousBuildingInsurancePopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Previous_Building_Insurance
					+ " - Message is displayed when click/hover the help icon (?) against the 'Previous Building Insurance' button",
					changeMyProperty.POPUP_MSG_HELPICON_Previous_Building_Insurance
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Previous Building Insurance' button",
					driver, extentedReport);

			changeMyProperty.selectNameOfPreviousInsurerB(testData.nameOfPreviousInsurer, extentedReport, true);
			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpNameOfPreviousInsurer"),
							changeMyProperty),
					"TCNO_10795_Help icon (?) is displayed against the 'Name Of Previous Insurer' field in Change my property",
					"TCNO_10795_Help icon (?) is not displayed against the 'Name Of Previous Insurer' field in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfNameOfPreviousInsurer(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconNameOfPreviousInsurerPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Name_Of_Previous_Insurer_B
					+ " - Message is displayed when click/hover the help icon (?) against the 'Name Of Previous Insurer' field",
					changeMyProperty.POPUP_MSG_HELPICON_Name_Of_Previous_Insurer_B
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Name Of Previous Insurer' field",
					driver, extentedReport);

			changeMyProperty.enterPolicyNumberOfPreviousInsuranceB(testData.numberOfPreviousInsurance, extentedReport,
					true);
			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpPolicyNumberOfPreviousInsuranceB"), changeMyProperty),
					"TCNO_10796_Help icon (?) is displayed against the 'Number Of Previous Insurance' field in Change my property",
					"TCNO_10796_Help icon (?) is not displayed against the 'Number Of Previous Insurance' field in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfPolicyNumberOfPreviousInsuranceB(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPolicyNumberOfPreviousInsuranceBPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Policy_Number_Of_Previous_Insurance_B
					+ " - Message is displayed when click/hover the help icon (?) against the 'Number Of Previous Insurance' field",
					changeMyProperty.POPUP_MSG_HELPICON_Policy_Number_Of_Previous_Insurance_B
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Number Of Previous Insurance' field",
					driver, extentedReport);

			changeMyProperty.enterExpiryDateOfPreviousInsuranceB(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpExpiryDateOfPreviousInsuranceB"), changeMyProperty),
					"TCNO_10797_Help icon (?) is displayed against the 'Expiry Date Of Previous Insurance' field in Change my property",
					"TCNO_10797_Help icon (?) is not displayed against the 'Expiry Date Of Previous Insurance' field in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfExpiryDateOfPreviousInsuranceB(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconExpiryDateOfPreviousInsuranceBPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Expiry_Date_Of_Previous_Insurance_B
					+ " - Message is displayed when click/hover the help icon (?) against the 'Expiry Date Of Previous Insurance' field",
					changeMyProperty.POPUP_MSG_HELPICON_Expiry_Date_Of_Previous_Insurance_B
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Expiry Date Of Previous Insurance' field",
					driver, extentedReport);

			changeMyProperty.selectPayForYourPreviousInsuranceB(testData.payForYourPreviousInsurance, extentedReport,
					true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpPayForYourPreviousInsuranceB"),
							changeMyProperty),
					"TCNO_10798_Help icon (?) is displayed against the 'Pay For Your Previous Insurance' field in Change my property",
					"TCNO_10798_Help icon (?) is not displayed against the 'Pay For Your Previous Insurance' field in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfPayForYourPreviousInsuranceB(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPayForYourPreviousInsuranceBPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Pay_For_Your_Previous_Insurance_B
					+ " - Message is displayed when click/hover the help icon (?) against the 'Pay For Your Previous Insurance' field",
					changeMyProperty.POPUP_MSG_HELPICON_Pay_For_Your_Previous_Insurance_B
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Pay For Your Previous Insurance' field",
					driver, extentedReport);

			changeMyProperty.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);
			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpNoOfYearsWithoutClaimingB"),
							changeMyProperty),
					"TCNO_10793_Help icon (?) is displayed against the 'No Of Years Without Claiming' field in Change my property",
					"TCNO_10793_Help icon (?) is not displayed against the 'No Of Years Without Claiming' field in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfNoOfYearsWithoutClaimingB(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconNoOfYearsWithoutClaimingBPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_No_Of_Years_Without_Claiming_B
					+ " - Message is displayed when click/hover the help icon (?) against the 'No Of Years Without Claiming' field",
					changeMyProperty.POPUP_MSG_HELPICON_No_Of_Years_Without_Claiming_B
					+ " - Message is not displayed when click/hover the help icon (?) against the 'No Of Years Without Claiming' field",
					driver, extentedReport);

			changeMyProperty.clickNextBuildingsCoverDetails(extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}

	@Test(description = "Verify the help text in Contents page and Occupation page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_091(String browser) throws Exception {

		String tcId = "TC_91";
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

			// Select change type
			ChangeMyPropertyPage changeMyProperty = new ChangeMyPropertyPage(driver, extentedReport).get();

			// Select change type
			changeMyProperty.selectChangeType(testData.typeOfCover, extentedReport, true);
			changeMyProperty.clickNextButton(extentedReport, true);

			changeMyProperty.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyProperty.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyProperty.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyProperty.clickYesButtonInOverlay(extentedReport, true);

			// Property details
			changeMyProperty.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);

			changeMyProperty.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);

			Log.softAssertThat(changeMyProperty.verifyRestoreInSecuritySectionAreYouANeighbour(extentedReport, true),
					"The value for Are you a member of an active Neighbourhood Watch Scheme? is restored",
					"The value for Are you a member of an active Neighbourhood Watch Scheme? is not restored", driver,
					extentedReport, true);

			changeMyProperty.clickNextSecurityDetails(extentedReport, true);
			changeMyProperty.clickContinue(extentedReport, true);

			// Enter Contents Details
			changeMyProperty.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpvalueOfTitleYourContents"),
							changeMyProperty),
					"TCNO_10799_Help icon (?) is displayed against the 'Title Of Home Contents' text in Change my property",
					"TCNO_10799_Help icon (?) is not displayed against the 'Title Of Home Contents' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconTitleYourContents(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconTitleYourContents(),
					changeMyProperty.POPUP_MSG_HELPICON_Title_Your_Contents
					+ " - Message is displayed when click/hover the help icon (?) against the 'Title Of Home Contents' text",
					changeMyProperty.POPUP_MSG_HELPICON_Title_Your_Contents
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Title Of Home Contents' text",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpvalueOfHomeContents"),
							changeMyProperty),
					"TCNO_10800_Help icon (?) is displayed against the 'Value Of Home Contents' text in Change my property",
					"TCNO_10800_Help icon (?) is not displayed against the 'Value Of Home Contents' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfvalueOfHomeContents(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconvalueOfHomeContentsPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Value_Of_Home_Contents
					+ " - Message is displayed when click/hover the help icon (?) against the 'Value Of Home Contents' text",
					changeMyProperty.POPUP_MSG_HELPICON_Value_Of_Home_Contents
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Value Of Home Contents' text",
					driver, extentedReport);

			changeMyProperty.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,
					extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpvalueOfPreciousOrCostlyItems"),
							changeMyProperty),
					"TCNO_10800_Help icon (?) is displayed against the 'value Of Precious Or Costly Items' text in Change my property",
					"TCNO_10800_Help icon (?) is not displayed against the 'value Of Precious Or Costly Items' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfvalueOfPreciousOrCostlyItems(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconvalueOfPreciousOrCostlyItemsPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Value_Of_Precious_Or_Costly_Items
					+ " - Message is displayed when click/hover the help icon (?) against the 'value Of Precious Or Costly Items",
					changeMyProperty.POPUP_MSG_HELPICON_Value_Of_Precious_Or_Costly_Items
					+ " - Message is not displayed when click/hover the help icon (?) against the 'value Of Precious Or Costly Items",
					driver, extentedReport);

			changeMyProperty.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpvalueOfMostExpensiveItem"),
							changeMyProperty),
					"TCNO_10801_Help icon (?) is displayed against the 'value Of Most Expensive Item' text in Change my property",
					"TCNO_10801_Help icon (?) is not displayed against the 'value Of Most Expensive Item' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfvalueOfMostExpensiveItem(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconvalueOfMostExpensiveItemPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_value_Of_Most_Expensive_Item
					+ " - Message is displayed when click/hover the help icon (?) against the 'value Of Most Expensive Item",
					changeMyProperty.POPUP_MSG_HELPICON_value_Of_Most_Expensive_Item
					+ " - Message is not displayed when click/hover the help icon (?) against the 'value Of Most Expensive Item",
					driver, extentedReport);

			changeMyProperty.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,
					extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelptypeOfHomeContentsCoverRequired"), changeMyProperty),
					"TCNO_10803_Help icon (?) is displayed against the 'type Of Home Contents Cover Required' text in Change my property",
					"TCNO_10803_Help icon (?) is not displayed against the 'type Of Home Contents Cover Required' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOftypeOfHomeContentsCoverRequired(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIcontypeOfHomeContentsCoverRequiredPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Type_Of_Home_Contents_Cover_Required
					+ " - Message is displayed when click/hover the help icon (?) against the 'type Of Home Contents Cover Required",
					changeMyProperty.POPUP_MSG_HELPICON_Type_Of_Home_Contents_Cover_Required
					+ " - Message is not displayed when click/hover the help icon (?) against the 'type Of Home Contents Cover Required",
					driver, extentedReport);

			changeMyProperty.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelptotalExcessRequired"),
							changeMyProperty),
					"Help icon (?) is displayed against the 'total Excess Required' text in Change my property",
					"Help icon (?) is not displayed against the 'total Excess Required' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOftotalExcessRequired(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIcontotalExcessRequiredPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Total_Excess_Required
					+ " - Message is displayed when click/hover the help icon (?) against the 'total Excess Required",
					changeMyProperty.POPUP_MSG_HELPICON_Total_Excess_Required
					+ " - Message is not displayed when click/hover the help icon (?) against the 'total Excess Required",
					driver, extentedReport);

			changeMyProperty.clickYesOrNoBtnContentsSumRepresentReplacementAmount(
					testData.contentsSumRepresentReplacementAmount, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpcontentsSumRepresentReplacementAmount"), changeMyProperty),
					"TCNO_10804_Help icon (?) is displayed against the 'contents Sum Represent Replacement Amount' text in Change my property",
					"TCNO_10804_Help icon (?) is not displayed against the 'contents Sum Represent Replacement Amount' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfcontentsSumRepresentReplacementAmount(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconcontentsSumRepresentReplacementAmountPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Contents_Sum_Represent_Replacement_Amount
					+ " - Message is displayed when click/hover the help icon (?) against the 'contents Sum Represent Replacement Amount",
					changeMyProperty.POPUP_MSG_HELPICON_Contents_Sum_Represent_Replacement_Amount
					+ " - Message is not displayed when click/hover the help icon (?) against the 'contents Sum Represent Replacement Amount",
					driver, extentedReport);

			changeMyProperty.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,
					extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelppreviouslyContentsInsurance"),
							changeMyProperty),
					"TCNO_10805_Help icon (?) is displayed against the 'previously Contents Insurance' text in Change my property",
					"TCNO_10805_Help icon (?) is not displayed against the 'previously Contents Insurance' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfpreviouslyContentsInsurance(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconpreviouslyContentsInsurancePopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Previously_Contents_Insurance
					+ " - Message is displayed when click/hover the help icon (?) against the 'previously Contents Insurance",
					changeMyProperty.POPUP_MSG_HELPICON_Previously_Contents_Insurance
					+ " - Message is not displayed when click/hover the help icon (?) against the 'previously Contents Insurance",
					driver, extentedReport);

			changeMyProperty.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpnameOfPreviousInsurer"),
							changeMyProperty),
					"TCNO_10806_Help icon (?) is displayed against the 'name Of Previous Insurer' text in Change my property",
					"TCNO_10806_Help icon (?) is not displayed against the 'name Of Previous Insurer' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfnameOfPreviousInsurer(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconnameOfPreviousInsurerPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Name_Of_Previous_Insurer
					+ " - Message is displayed when click/hover the help icon (?) against the 'name Of Previous Insurer",
					changeMyProperty.POPUP_MSG_HELPICON_Name_Of_Previous_Insurer
					+ " - Message is not displayed when click/hover the help icon (?) against the 'name Of Previous Insurer",
					driver, extentedReport);

			changeMyProperty.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport,
					true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpPolicyNumberOfPreviousInsuranceC"), changeMyProperty),
					"TCNO_10807_Help icon (?) is displayed against the 'Policy Number Of Previous Insurance' text in Change my property",
					"TCNO_10807_Help icon (?) is not displayed against the 'Policy Number Of Previous Insurance' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfPolicyNumberOfPreviousInsurance(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPolicyNumberOfPreviousInsurancePopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Policy_Number_Of_Previous_Insurance
					+ " - Message is displayed when click/hover the help icon (?) against the 'Policy Number Of Previous Insurance",
					changeMyProperty.POPUP_MSG_HELPICON_Policy_Number_Of_Previous_Insurance
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Policy Number Of Previous Insurance",
					driver, extentedReport);

			changeMyProperty.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport,
					true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpExpiryDateOfPreviousInsuranceC"), changeMyProperty),
					"TCNO_10808_Help icon (?) is displayed against the 'Expiry Date Of Previous Insurance' text in Change my property",
					"TCNO_10808_Help icon (?) is not displayed against the 'Expiry Date Of Previous Insurance' text in Add Contents CoverChange my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfExpiryDateOfPreviousInsurance(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconExpiryDateOfPreviousInsurancePopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Expiry_Date_Of_Previous_Insurance
					+ " - Message is displayed when click/hover the help icon (?) against the 'Expiry Date Of Previous Insurance",
					changeMyProperty.POPUP_MSG_HELPICON_Expiry_Date_Of_Previous_Insurance
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Expiry Date Of Previous Insurance",
					driver, extentedReport);

			changeMyProperty.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport,
					true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpPayForYourPreviousInsuranceC"),
							changeMyProperty),
					"TCNO_10809_Help icon (?) is displayed against the 'Pay For Your Previous Insurance' text in Change my property",
					"TCNO_10809_Help icon (?) is not displayed against the 'Pay For Your Previous Insurance' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfPayForYourPreviousInsurance(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPayForYourPreviousInsurancePopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Pay_For_Your_Previous_Insurance
					+ " - Message is displayed when click/hover the help icon (?) against the 'Pay For Your Previous Insurance",
					changeMyProperty.POPUP_MSG_HELPICON_Pay_For_Your_Previous_Insurance
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Pay For Your Previous Insurance",
					driver, extentedReport);

			changeMyProperty.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,
					extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpNoOfYearsWithoutClaimingC"),
							changeMyProperty),
					"Help icon (?) is displayed against the 'No Of Years Without Claiming' text in Change my property",
					"Help icon (?) is not displayed against the 'Pay For Your Previous Insurance' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfNoOfYearsWithoutClaiming(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconNoOfYearsWithoutClaimingPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_No_Of_Years_Without_Claiming
					+ " - Message is displayed when click/hover the help icon (?) against the 'No Of Years Without Claiming",
					changeMyProperty.POPUP_MSG_HELPICON_No_Of_Years_Without_Claiming
					+ " - Message is not displayed when click/hover the help icon (?) against the 'No Of Years Without Claiming",
					driver, extentedReport);

			changeMyProperty.clickNextContentsInformation(extentedReport, true);

			// Enter details in Additional covers
			changeMyProperty.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,
					true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpcashInYourHomeToBeInsured"),
							changeMyProperty),
					"TCNO_10810_Help icon (?) is displayed against the 'cash In Your Home To Be Insured' text in Change my property",
					"TCNO_10810_Help icon (?) is not displayed against the 'cash In Your Home To Be Insured' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfcashInYourHomeToBeInsured(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconcashInYourHomeToBeInsuredPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Cash_In_Your_Home_To_Be_Insured
					+ " - Message is displayed when click/hover the help icon (?) against the 'cash In Your Home To Be Insured",
					changeMyProperty.POPUP_MSG_HELPICON_Cash_In_Your_Home_To_Be_Insured
					+ " - Message is not displayed when click/hover the help icon (?) against the 'cash In Your Home To Be Insured",
					driver, extentedReport);

			changeMyProperty.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpcreditCardsToBeInsured"),
							changeMyProperty),
					"TCNO_10812_Help icon (?) is displayed against the 'credit Cards To Be Insured' text in Change my property",
					"TCNO_10812_Help icon (?) is not displayed against the 'credit Cards To Be Insured' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOfcreditCardsToBeInsured(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconcreditCardsToBeInsuredPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Credit_Cards_To_Be_Insured
					+ " - Message is displayed when click/hover the help icon (?) against the 'credit Cards To Be Insured",
					changeMyProperty.POPUP_MSG_HELPICON_Credit_Cards_To_Be_Insured
					+ " - Message is not displayed when click/hover the help icon (?) against the 'credit Cards To Be Insured",
					driver, extentedReport);

			changeMyProperty.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpfrozenFoodToBeInsured"),
							changeMyProperty),
					"TCNO_10811_Help icon (?) is displayed against the 'frozen Food To Be Insured' text in Change my property",
					"TCNO_10811_Help icon (?) is not displayed against the 'frozen Food To Be Insured' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOffrozenFoodToBeInsured(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconfrozenFoodToBeInsuredPopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Frozen_Food_To_Be_Insured
					+ " - Message is displayed when click/hover the help icon (?) against the 'frozen Food To Be Insured",
					changeMyProperty.POPUP_MSG_HELPICON_Frozen_Food_To_Be_Insured
					+ " - Message is not displayed when click/hover the help icon (?) against the 'frozen Food To Be Insured",
					driver, extentedReport);

			changeMyProperty.clickNextAdditionalCovers(extentedReport, true);

			// Enter details in Specified Covers
			changeMyProperty.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,
					extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpaddPersonalPossessionsWithinHome"), changeMyProperty),
					"TCNO_10814_Help icon (?) is displayed against the 'add Personal Possessions Within Home' text in Change my property",
					"TCNO_10814_Help icon (?) is not displayed against the 'add Personal Possessions Within Home' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIaddPersonalPossessionsWithinHome(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconaddPersonalPossessionsWithinHomePopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Add_Personal_Possessions_Within_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'add Personal Possessions Within Home",
					changeMyProperty.POPUP_MSG_HELPICON_Add_Personal_Possessions_Within_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'add Personal Possessions Within Home",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpaddAnotherItemInWithinHome"),
							changeMyProperty),
					"TCNO_10815_Help icon (?) is displayed against the 'add Another Item In Within Home' text in Change my property",
					"TCNO_10815_Help icon (?) is not displayed against the 'add Another Item In Within Home' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIaddAnotherItemInWithinHome(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconaddAnotherItemInWithinHomePopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Add_Another_Item_In_Within_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'add Another Item In Within Home",
					changeMyProperty.POPUP_MSG_HELPICON_Add_Another_Item_In_Within_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'add Another Item In Within Home",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpItemtypeNewSpecifiedItemWithinHome"), changeMyProperty),
					"TCNO_10816_Help icon (?) is displayed against the 'Itemtype New Specified Item Within Home' text in Change my property",
					"TCNO_10816_Help icon (?) is not displayed against the 'Itemtype New Specified Item Within Home' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIItemtypeNewSpecifiedItemWithinHome(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconItemtypeNewSpecifiedItemWithinHome(),
					changeMyProperty.POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Within_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Itemtype New Specified Item Within Home",
					changeMyProperty.POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Within_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Itemtype New Specified Item Within Home",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpItemvalueNewSpecifiedItemWithinHome"), changeMyProperty),
					"TCNO_10817_Help icon (?) is displayed against the 'Itemvalue New Specified Item Within Home' text in Change my property",
					"TCNO_10817_Help icon (?) is not displayed against the 'Itemvalue New Specified Item Within Home' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIItemvalueNewSpecifiedItemWithinHome(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconItemvalueNewSpecifiedItemWithinHome(),
					changeMyProperty.POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Within_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Itemvalue New Specified Item Within Home",
					changeMyProperty.POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Within_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Itemvalue New Specified Item Within Home",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpDescriptionOfItemNewSpecifiedItemWithinHome"), changeMyProperty),
					"TCNO_10818_Help icon (?) is displayed against the 'Description Of Item New Specified Item Within Home' text in Change my property",
					"TCNO_10818_Help icon (?) is not displayed against the 'Description Of Item New Specified Item Within Home' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIDescriptionOfItemNewSpecifiedItemWithinHome(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconDescriptionOfItemNewSpecifiedItemWithinHome(),
					changeMyProperty.POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Within_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Description Of Item New Specified Item Within Home",
					changeMyProperty.POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Within_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Description Of Item New Specified Item Within Home",
					driver, extentedReport);

			changeMyProperty.addSpecifiedItemwithinHome(testData.itemTypeWH, testData.itemValueWH, testData.itemDecsriptionWH,
					testData.saveAndCancelWH, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInWithinHome(testData.addAnotherItemInWithinHome, extentedReport,
					true);
			changeMyProperty.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			changeMyProperty.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,
					extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpaddPersonalPossessionsOutsideHome"), changeMyProperty),
					"TCNO_10819_Help icon (?) is displayed against the 'add Personal Possessions Outside Home' text in Change my property",
					"TCNO_10819_Help icon (?) is not displayed against the 'add Personal Possessions Outside Home' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIaddPersonalPossessionsOutsideHome(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconaddPersonalPossessionsOutsideHome(),
					changeMyProperty.POPUP_MSG_HELPICON_Add_Personal_Possessions_Outside_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'add Personal Possessions Outside Home",
					changeMyProperty.POPUP_MSG_HELPICON_Add_Personal_Possessions_Outside_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'add Personal Possessions Outside Home",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpaddAnotherItemInOutsideHome"),
							changeMyProperty),
					"TCNO_10820_Help icon (?) is displayed against the 'add Another Item In Outside Home' text in Change my property",
					"TCNO_10820_Help icon (?) is not displayed against the 'add Another Item In Outside Home' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIaddAnotherItemInOutsideHome(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconaddAnotherItemInOutsideHomePopupMessage(),
					changeMyProperty.POPUP_MSG_HELPICON_Add_Another_Item_In_Outside_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'add Another Item In Outside Home",
					changeMyProperty.POPUP_MSG_HELPICON_Add_Another_Item_In_Outside_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'add Another Item In Outside Home",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpItemtypeNewSpecifiedItemOutsideHome"), changeMyProperty),
					"TCNO_10821_Help icon (?) is displayed against the 'Itemtype New Specified Item Outside Home' text in Change my property",
					"TCNO_10821_Help icon (?) is not displayed against the 'Itemtype New Specified Item Outside Home' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIItemtypeNewSpecifiedItemOutsideHome(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconItemtypeNewSpecifiedItemOutsideHome(),
					changeMyProperty.POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Outside_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Itemtype New Specified Item Outside Home",
					changeMyProperty.POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Outside_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Itemtype New Specified Item Outside Home",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpItemvalueNewSpecifiedItemOutsideHome"), changeMyProperty),
					"TCNO_10822_Help icon (?) is displayed against the 'Itemvalue New Specified Item Outside Home' text in Change my property",
					"TCNO_10822_Help icon (?) is not displayed against the 'Itemvalue New Specified Item Outside Home' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIItemvalueNewSpecifiedItemOutsideHome(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconItemvalueNewSpecifiedItemOutsideHome(),
					changeMyProperty.POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Outside_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Itemvalue New Specified Item Outside Home",
					changeMyProperty.POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Outside_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Itemvalue New Specified Item Outside Home",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpDescriptionOfItemNewSpecifiedItemOutsideHome"), changeMyProperty),
					"TCNO_10823_Help icon (?) is displayed against the 'Description Of Item New Specified Item Outside Home' text in Change my property",
					"TCNO_10823_Help icon (?) is not displayed against the 'Description Of Item New Specified Item Outside Home' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIDescriptionOfItemNewSpecifiedItemOutsideHome(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconDescriptionOfItemNewSpecifiedItemOutisdeHome(),
					changeMyProperty.POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Outside_Home
					+ " - Message is displayed when click/hover the help icon (?) against the 'Description Of Item New Specified Item Outside Home",
					changeMyProperty.POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Outside_Home
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Description Of Item New Specified Item Outside Home",
					driver, extentedReport);

			changeMyProperty.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH,
					testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);

			changeMyProperty.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome,
					extentedReport, true);

			changeMyProperty.clickContinue(extentedReport, true);

			// Occupation details
			changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,
					true);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpTitleOfPeople"),
							changeMyProperty),
					"TCNO_10824_Help icon (?) is displayed against the 'Title Of People page' text in Change my property",
					"TCNO_10824_Help icon (?) is not displayed against the 'Title Of People page' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconTitleOfPeople(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconTitleOfPeople(),
					changeMyProperty.POPUP_MSG_HELPICON_Title_Of_People
					+ " - Message is displayed when click/hover the help icon (?) against the 'Title Of People Page'",
					changeMyProperty.POPUP_MSG_HELPICON_Title_Of_People
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Title Of People Page'",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpOccupation"),
							changeMyProperty),
					"TCNO_10826_Help icon (?) is displayed against the 'Add Occupation' text in Change my property",
					"TCNO_10826_Help icon (?) is not displayed against the 'Add Occupation' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconOccupation(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconOccupation(),
					changeMyProperty.POPUP_MSG_HELPICON_Occupation
					+ " - Message is displayed when click/hover the help icon (?) against the 'Add Occupation'",
					changeMyProperty.POPUP_MSG_HELPICON_Occupation
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Add Occupation'",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpBusiness"), changeMyProperty),
					"TCNO_10827_Help icon (?) is displayed against the 'Add Business' text in Change my property",
					"TCNO_10827_Help icon (?) is not displayed against the 'Add Business' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconBusiness(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconBusiness(),
					changeMyProperty.POPUP_MSG_HELPICON_Business
					+ " - Message is displayed when click/hover the help icon (?) against the 'Add Business'",
					changeMyProperty.POPUP_MSG_HELPICON_Business
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Add Business'",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpEmploymentStatus"),
							changeMyProperty),
					"TCNO_10828_Help icon (?) is displayed against the 'Employment Status' text in Change my property",
					"TCNO_10828_Help icon (?) is not displayed against the 'Employment Status' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconEmploymentStatus(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconEmploymentStatus(),
					changeMyProperty.POPUP_MSG_HELPICON_Employment_Status
					+ " - Message is displayed when click/hover the help icon (?) against the 'Employment Status'",
					changeMyProperty.POPUP_MSG_HELPICON_Employment_Status
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Employment Status'",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpMainOccupation"),
							changeMyProperty),
					"TCNO_10829_Help icon (?) is displayed against the 'Main Occupation' text in Change my property",
					"TCNO_10829_Help icon (?) is not displayed against the 'Main Occupation' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconMainOccupation(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconMainOccupation(),
					changeMyProperty.POPUP_MSG_HELPICON_Main_Occupation
					+ " - Message is displayed when click/hover the help icon (?) against the 'Main Occupation'",
					changeMyProperty.POPUP_MSG_HELPICON_Main_Occupation
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Main Occupation'",
					driver, extentedReport);

			changeMyProperty.addOccupation(testData.occupation, testData.business, testData.employmentStatus,
					testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);

			Log.softAssertThat(
					changeMyProperty.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true),
					"Add Occupation is verified in People page", "Add Occupation is not verified People page", driver,
					extentedReport, true);

			// changeMyProperty.clickYesOrNoAddAnotherOccupation(testData.arrAddAnotherOccupation[1],
			// extentedReport, true);
			changeMyProperty.clickNextOccupation(extentedReport, true);

			// Residents details
			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpTypeOfOwnership"),
							changeMyProperty),
					"TCNO_10835_Help icon (?) is displayed against the 'Type Of Ownership' text in Change my property",
					"TCNO_10835_Help icon (?) is not displayed against the 'Type Of Ownership' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconTypeOfOwnership(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconTypeOfOwnership(),
					changeMyProperty.POPUP_MSG_HELPICON_Type_Of_Ownership
					+ " - Message is displayed when click/hover the help icon (?) against the 'Type Of Ownership'",
					changeMyProperty.POPUP_MSG_HELPICON_Type_Of_Ownership
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Type Of Ownership'",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(Arrays.asList("iconHelpTypeOfOccupancy"),
							changeMyProperty),
					"TCNO_10836_Help icon (?) is displayed against the 'Type Of Occupancy' text in Change my property",
					"TCNO_10836_Help icon (?) is not displayed against the 'Type Of Occupancy' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconTypeOfOccupancy(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconTypeOfOccupancy(),
					changeMyProperty.POPUP_MSG_HELPICON_Type_Of_Occupancy
					+ " - Message is displayed when click/hover the help icon (?) against the 'Type Of Occupancy'",
					changeMyProperty.POPUP_MSG_HELPICON_Type_Of_Occupancy
					+ " - Message is not displayed when click/hover the help icon (?) against the 'Type Of Occupancy'",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpPeopleNormallyLiveInTheProperty"), changeMyProperty),
					"TCNO_10837_Help icon (?) is displayed against the 'How many people in total normally live in the property' text in Change my property",
					"TCNO_10837_Help icon (?) is not displayed against the 'How many people in total normally live in the property' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconPeopleNormallyLiveInTheProperty(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconPeopleNormallyLiveInTheProperty(),
					changeMyProperty.POPUP_MSG_HELPICON_People_Normally_Live_In_The_Property
					+ " - Message is displayed when click/hover the help icon (?) against the 'How many people in total normally live in the property'",
					changeMyProperty.POPUP_MSG_HELPICON_People_Normally_Live_In_The_Property
					+ " - Message is not displayed when click/hover the help icon (?) against the 'How many people in total normally live in the property'",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyProperty.uielement.verifyPageElements(
							Arrays.asList("iconHelpChildrenUnder18LiveInTheProperty"), changeMyProperty),
					"TCNO_10838_Help icon (?) is displayed against the 'How many children under 18 live in the property' text in Change my property",
					"TCNO_10838_Help icon (?) is not displayed against the 'How many children under 18 live in the property' text in Change my property",
					driver, extentedReport);

			changeMyProperty.hoverOnHelpIconChildrenUnder18LiveInTheProperty(extentedReport, true);
			Log.softAssertThat(changeMyProperty.verifyHelpIconChildrenUnder18LiveInTheProperty(),
					changeMyProperty.POPUP_MSG_HELPICON_Children_Under_18_Live_In_The_Property
					+ " - Message is displayed when click/hover the help icon (?) against the 'How many children under 18 live in the property'",
					changeMyProperty.POPUP_MSG_HELPICON_Children_Under_18_Live_In_The_Property
					+ " - Message is not displayed when click/hover the help icon (?) against the 'How many children under 18 live in the property'",
					driver, extentedReport);

			changeMyProperty.clickNextResidents(extentedReport, true);
			
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}

	}
	
}
