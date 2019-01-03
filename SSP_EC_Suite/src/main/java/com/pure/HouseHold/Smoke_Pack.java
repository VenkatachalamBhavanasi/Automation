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
import com.generic.utils.WaitUtils;
import com.pure.selfservice.pages.AddBuildingCoverPage;
import com.pure.selfservice.pages.AddContentsCoverPage;
import com.pure.selfservice.pages.AddSpecifiedItemPage;
import com.pure.selfservice.pages.BuyPaymentPage;
import com.pure.selfservice.pages.CardDetailsPage;
import com.pure.selfservice.pages.ChangeMyPropertyPage;
import com.pure.selfservice.pages.CustomerDashboardPage;
import com.pure.selfservice.pages.CustomerServicePage;
import com.pure.selfservice.pages.CustomerSignInPage;
import com.pure.selfservice.pages.GetTestData;
import com.pure.selfservice.pages.MTAInitiatePage;
import com.pure.selfservice.pages.MTAPaymentPage;
import com.pure.selfservice.pages.MTAQuoteDetailsPage;
import com.pure.selfservice.pages.RenewMyPolicyPage;
import com.pure.selfservice.pages.RenewalPaymentPage;
import com.pure.selfservice.pages.RenewalPaymentRequiredPage;
import com.pure.selfservice.pages.SuccessPage;
import com.relevantcodes.extentreports.ExtentTest;

@Listeners(EmailReport.class)
public class Smoke_Pack extends BaseTest {
	private String webSite;
	String sheetName = "Smoke_Pack_";

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
	
	/********** Payment Method as Credit Card *********/
	
	@Test(description = "To adding the Contents cover to existing Building cover policy through BUY option in SS and selecting payment method as Credit card ", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_001(String browser) throws Exception {

		String tcId = "TC_001";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addContentsCover"), mTAInitiatePage),
					"Add Contents Cover is displayed in MTA intiate page", "Add Contents Cover is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Add Contents cover details
			AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"), addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button", "Add Contents cover page is not opens after clicking Continue button", driver, extentedReport, true);
			
			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems, extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired, extentedReport, true);
			
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance, extentedReport, true);
			
			addContentsCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addContentsCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance, extentedReport,true);
			addContentsCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance, extentedReport,true);
			addContentsCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport,true);
			addContentsCoverPage.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue, extentedReport, true);
						
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);
			// enter Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);
			
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome, extentedReport, true);
			
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, extentedReport, true);
			
			addContentsCoverPage.clickQuoteButton(extentedReport, true);
			
			//Your Quote Page
			MTAQuoteDetailsPage mtaQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"),	mtaQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : " + testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : " + testData.typeOfChange,
					driver, extentedReport);
			
			mtaQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mtaQuoteDetailsPage.clickBuyButton(extentedReport, true);
			
			//Payment page
			MTAPaymentPage mtaPaymentPage =new MTAPaymentPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"), mtaPaymentPage),
					"MTA Payment Page  is opens after clicking Buy  button", "MTA Payment Page is not opens after clicking Buy button", driver, extentedReport, true);
			
			//mtaPaymentPage.clickBuyButton(extentedReport);
			
			Log.softAssertThat(mtaPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mtaPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = mtaPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			/*cardDetailsPage.clickbuy(extentedReport, true);
						
			//Success page
			SuccessPage successPage =new SuccessPage(driver).get();
			
			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment throwing any error");
			
			successPage.clickFinish(extentedReport, true);*/
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "To adding the Building cover to existing Contents cover policy through BUY option in SS and selecting payment method as Credit card  ", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_002(String browser) throws Exception {

		String tcId = "TC_002";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addBuildingsCover"), mTAInitiatePage),
					"Add Building Cover cover is displayed in MTA intiate page", "Add Building Cover cover is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Add Contents cover details
			//AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();
			
			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"), addBuildingCoverPage),
					"Add Building cover page is opens after clicking Continue button", "Add Building cover page is not opens after clicking Continue button", driver, extentedReport,true);
			
			// Enter details for BUilding cover information
			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, false);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, false);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, false);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport, false);
			
			//Enter Previous Insurance details
			addBuildingCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, false);
			addBuildingCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance, extentedReport, false);	
			addBuildingCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance, extentedReport, false);	
			addBuildingCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport, false);
			addBuildingCoverPage.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue, extentedReport, true);
			addBuildingCoverPage.clickQuoteButton(extentedReport, true);
			
			//Your Quote Page
			MTAQuoteDetailsPage mtaQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mtaQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Building Cover on House hold Policy for change type : " + testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to Add Building Cover on House hold Policy for change type : " + testData.typeOfChange,
					driver, extentedReport);
						
			mtaQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mtaQuoteDetailsPage.clickBuyButton(extentedReport, true);
			
			//Payment page
			MTAPaymentPage mtaPaymentPage =new MTAPaymentPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"), mtaPaymentPage),
					"MTA Payment Page  is opens after clicking Buy button", "MTA Payment Page is not opens after clicking Buy button", driver, extentedReport, true);
			
			//mtaPaymentPage.clickBuyButton(extentedReport);
			
			Log.softAssertThat(mtaPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mtaPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);
						
			// Entering the another card details
			CardDetailsPage cardDetailsPage = mtaPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			/*cardDetailsPage.clickbuy(extentedReport, true);
						
			//Success page
			SuccessPage successPage =new SuccessPage(driver).get();
			
			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment throwing any error");
			
			successPage.clickFinish(extentedReport, true);*/
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "To adding the specified items to existing Building cover & Contents cover policy through BUY option in SS and selecting payment method as Credit card ", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_003(String browser) throws Exception {

		String tcId = "TC_003";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			
			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addSpecifiedItems"), mTAInitiatePage),
					"Add Specified Items page is displayed in MTA intiate page", "Add Specified Items page is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			AddSpecifiedItemPage addSpecifiedItemPage =new AddSpecifiedItemPage(driver, extentedReport).get();
			
			Log.softAssertThat(addSpecifiedItemPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddSpecifiedItems"), addSpecifiedItemPage),
					"Add Specified Items page is opens after clicking Continue button", "Add Specified Items page is not opens after clicking Continue button", driver, extentedReport,	true);
						
			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome, extentedReport, true);
			addSpecifiedItemPage.addSpecifiedItemwithinHome(testData.arritemTypeWH[0], testData.arritemValueWH[0],
					testData.arritemDecsriptionWH[0], testData.arrsaveAndCancelWH[0], extentedReport, true);
			
			/*Log.softAssertThat(	addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[0], extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);*/
			
			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[0], extentedReport, true);
			addSpecifiedItemPage.addSpecifiedItemwithinHome(testData.arritemTypeWH[1], testData.arritemValueWH[1],
					testData.arritemDecsriptionWH[1], testData.arrsaveAndCancelWH[1], extentedReport, true);
			
			/*Log.softAssertThat(	addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[1], extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);*/
		
			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[1], extentedReport, true);
			addSpecifiedItemPage.addSpecifiedItemwithinHome(testData.arritemTypeWH[2], testData.arritemValueWH[2],
					testData.arritemDecsriptionWH[2], testData.arrsaveAndCancelWH[2], extentedReport, true);
			
			/*Log.softAssertThat(	addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[2], extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);*/
			
			//addSpecifiedItemPage.headersVerificationForTableInWithinHome(extentedReport, true);
			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[2], extentedReport, true);
			addSpecifiedItemPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);

			//Enter Specified items for outside home
			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,extentedReport, true);
			
			addSpecifiedItemPage.selectItemTypeOfSpecifiedItemsOutsideHome(testData.itemTypeOH, extentedReport, false);
			addSpecifiedItemPage.enterItemValueOfSpecifiedItemsOutsideHome(testData.itemValueOH, extentedReport, false);
			addSpecifiedItemPage.enterDescriptionOfItemOfSpecifiedItemsOutsideHome(testData.itemDecsriptionOH, extentedReport, false);
			addSpecifiedItemPage.clickSaveItemNewSpecifiedItemOutsideHome(extentedReport, true);
			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome, extentedReport, false);
			
			//addSpecifiedItemPage.clicknext
			addSpecifiedItemPage.clickQuoteButton(extentedReport, true);
			
			//Quote Page
			MTAQuoteDetailsPage mtaQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mtaQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Specified items on House hold Policy for change type : "	+ testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to Add Specified itmes on House hold Policy for change type : " + testData.typeOfChange,
					driver, extentedReport);
			
			
			mtaQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mtaQuoteDetailsPage.clickBuyButton(extentedReport, true);
			
			//Payment Page
			MTAPaymentPage mtaPaymentPage =new MTAPaymentPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"), mtaPaymentPage),
					"MTA Payment Page  is opens after clicking Buy button", "MTA Payment Page is not opens after clicking Buy button", driver, extentedReport, true);
			
			//mtaPaymentPage.clickBuyButton(extentedReport);
			
			Log.softAssertThat(mtaPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mtaPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = mtaPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			/*cardDetailsPage.clickbuy(extentedReport, true);
			
			//Success page	
			SuccessPage successPage =new SuccessPage(driver).get();
			
			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment throwing any error");
			
			successPage.clickFinish(extentedReport, true);*/
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "To changing from existing Building cover policy to Contents cover policy and verifying the Occupation detials entered in BO through BUY option in SS and selecting payment method as Credit card ", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_004(String browser) throws Exception {

		String tcId = "TC_004";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("changeMyProperty"), mTAInitiatePage),
					"Change My Property cover is displayed in MTA intiate page", "Change My Property cover is not displayed in MTA intiate page", driver, extentedReport, true);

			
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			//Enter the Contents cover policy details 
			
			ChangeMyPropertyPage changeMyPropertyPage = new ChangeMyPropertyPage(driver, extentedReport).get();
			
			Log.softAssertThat(changeMyPropertyPage.uielement.verifyPageElements(Arrays.asList("txtTitleYourProperty"), changeMyPropertyPage),
					"Building cover page is opens after clicking Continue button", "Building cover page is not opens after clicking Continue button", driver, extentedReport, true);
			
			changeMyPropertyPage.selectChangeType(testData.sectionType, extentedReport, false);
			changeMyPropertyPage.clickNextButton(extentedReport, true);
			
			//Enter the Building details
					
			changeMyPropertyPage.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyPropertyPage.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyPropertyPage.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyPropertyPage.clickYesButtonInOverlay(extentedReport, true);
			
			// Property details
			changeMyPropertyPage.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.clickNextSecurityDetails(extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
						
			//enter the Property details
			changeMyPropertyPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyPropertyPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems, extentedReport, true);
			changeMyPropertyPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyPropertyPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired, extentedReport, true);
			changeMyPropertyPage.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyPropertyPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			
			changeMyPropertyPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance, extentedReport, true);
			
			changeMyPropertyPage.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyPropertyPage.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport, true);
			changeMyPropertyPage.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport, true);
			changeMyPropertyPage.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport, true);
			changeMyPropertyPage.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue, extentedReport, true);
			changeMyPropertyPage.clickNextContentsInformation(extentedReport, true);
			
			// enter Additional covers
			changeMyPropertyPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,true);
			changeMyPropertyPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyPropertyPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyPropertyPage.clickNextAdditionalCovers(extentedReport, true);

			//Enter specified item details
			changeMyPropertyPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome, extentedReport, true);
			
			changeMyPropertyPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,extentedReport, true);
			
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			// Verifying two Occupation details
			changeMyPropertyPage.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true);
			
			changeMyPropertyPage.clickNextOccupation(extentedReport, true);
			
			// Residents details
			changeMyPropertyPage.clickNextResidents(extentedReport, true);

			// Quote Page
			changeMyPropertyPage.clickQuoteButton(extentedReport, true);
			MTAQuoteDetailsPage mtaQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
		
			Log.softAssertThat(mtaQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"), mtaQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Change My Property on House hold Policy for change type : " + testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to Change My Property on House hold Policy for change type : " + testData.typeOfChange,
					driver, extentedReport);
			
			mtaQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			/*mtaQuoteDetailsPage.clickBuyButton(extentedReport, true);
						
			//Success page		
			SuccessPage successPage =new SuccessPage(driver).get();
			
			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment throwing any error");
			
			successPage.clickFinish(extentedReport, true);*/
			
			Log.testCaseResult(extentedReport);
				
		}
		catch (Exception e) {
				Log.exception(e, driver, extentedReport);
		} finally {
				Log.endTestCase(extentedReport);
				driver.quit();
		}
		
	}
	
	@Test(description = "To changing existing Contents cover policy to Building cover policy through BUY option in SS and selecting payment method as Credit card", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_005(String browser) throws Exception {

		String tcId = "TC_005";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("changeMyProperty"), mTAInitiatePage),
					"Change My Property is displayed in MTA intiate page", "Change My Property is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			//Enter the Contents cover policy details 
			
			ChangeMyPropertyPage changeMyPropertyPage = new ChangeMyPropertyPage(driver, extentedReport).get();
			
			Log.softAssertThat(changeMyPropertyPage.uielement.verifyPageElements(Arrays.asList("txtTitleYourProperty"), changeMyPropertyPage),
					"Contents cover page is opens after clicking Continue button", "Contents cover page is not opens after clicking Continue button", driver, extentedReport, true);
			
			changeMyPropertyPage.selectChangeType(testData.sectionType, extentedReport, false);
			changeMyPropertyPage.clickNextButton(extentedReport, true);
			
			// Enter details for Contents cover information
			changeMyPropertyPage.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyPropertyPage.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyPropertyPage.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyPropertyPage.clickYesButtonInOverlay(extentedReport, true);
			
			changeMyPropertyPage.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.clickNextSecurityDetails(extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			//enter Building  cover details
			changeMyPropertyPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, false);
			changeMyPropertyPage.selectExcessValueB(testData.excessvalue, extentedReport, true);
			
			changeMyPropertyPage.clickNextBuildingsCoverDetails(extentedReport, true);
			
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			// Occupation details
			changeMyPropertyPage.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,true);
						
			changeMyPropertyPage.clickNextOccupation(extentedReport, true);
						
			// Residents details
			changeMyPropertyPage.clickNextResidents(extentedReport, true);
			
			// Quote Page
			changeMyPropertyPage.clickQuoteButton(extentedReport, true);
						
			//Your Quote page
			MTAQuoteDetailsPage mtaQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"), mtaQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Change My Property on House hold Policy for change type : " + testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to Change My Property on House hold Policy for change type : " + testData.typeOfChange,
					driver, extentedReport);
			
			mtaQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			/*mtaQuoteDetailsPage.clickBuyButton(extentedReport, true);
						
			//Success page		
			SuccessPage successPage =new SuccessPage(driver).get();
			
			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment throwing any error");
			
			successPage.clickFinish(extentedReport, true);*/
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "To change the existing Contents cover policy to Building cover policy with Occupation details and selecting payment method as Credit card", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_006(String browser) throws Exception {

		String tcId = "TC_006";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("changeMyProperty"), mTAInitiatePage),
					"Change My Property cover is displayed in MTA intiate page", "Change My Property cover is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			//Enter the Contents cover policy details 
			
			ChangeMyPropertyPage changeMyPropertyPage = new ChangeMyPropertyPage(driver, extentedReport).get();
			
			Log.softAssertThat(changeMyPropertyPage.uielement.verifyPageElements(Arrays.asList("txtTitleYourProperty"), changeMyPropertyPage),
					"Contents cover page is opens after clicking Continue button", "Contents cover page is not opens after clicking Continue button", driver, extentedReport, true);
			
			changeMyPropertyPage.selectChangeType(testData.sectionType, extentedReport, false);
			changeMyPropertyPage.clickNextButton(extentedReport, true);
			
			// Enter details for Contents cover information
			changeMyPropertyPage.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyPropertyPage.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyPropertyPage.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyPropertyPage.clickYesButtonInOverlay(extentedReport, true);
			
			changeMyPropertyPage.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.clickNextSecurityDetails(extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			//enter Building  cover details
			changeMyPropertyPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, false);
			changeMyPropertyPage.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyPropertyPage.clickNextBuildingsCoverDetails(extentedReport, true);
			
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			// Occupation details
			changeMyPropertyPage.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,true);
			changeMyPropertyPage.addOccupation(testData.occupation, testData.business, testData.employmentStatus, testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);
			
			changeMyPropertyPage.clickNextOccupation(extentedReport, true);
						
			// Residents details
			changeMyPropertyPage.clickNextResidents(extentedReport, true);
			
			changeMyPropertyPage.clickQuoteButton(extentedReport, true);
			
			//Your Quote page
			MTAQuoteDetailsPage mtaQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"), mtaQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Change My Property on House hold Policy for change type : " + testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to Change My Property on House hold Policy for change type : " + testData.typeOfChange,
					driver, extentedReport);
			
			mtaQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			/*mtaQuoteDetailsPage.clickBuyButton(extentedReport, true);
						
			//Success page		
			SuccessPage successPage =new SuccessPage(driver).get();
			
			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment throwing any error");
			
			successPage.clickFinish(extentedReport, true);*/
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	@Test(description = "To changing from existing Buildings & Content cover policy to Contents cover with Specified items detials and selecting payment method as Credit card", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")

	public void TC_007(String browser) throws Exception {

		String tcId = "TC_007";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("changeMyProperty"), mTAInitiatePage),
					"Change My Property cover is displayed in MTA intiate page", "Change My Property cover is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			//Enter the Contents cover policy details 
			
			ChangeMyPropertyPage changeMyPropertyPage = new ChangeMyPropertyPage(driver, extentedReport).get();
			
			Log.softAssertThat(changeMyPropertyPage.uielement.verifyPageElements(Arrays.asList("txtTitleYourProperty"), changeMyPropertyPage),
					"Building cover page is opens after clicking Continue button", "Building cover page is not opens after clicking Continue button", driver, extentedReport, true);
			
			changeMyPropertyPage.selectChangeType(testData.sectionType, extentedReport, false);
			changeMyPropertyPage.clickNextButton(extentedReport, true);
			
			//Enter the Building details
					
			changeMyPropertyPage.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyPropertyPage.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyPropertyPage.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyPropertyPage.clickYesButtonInOverlay(extentedReport, true);
			
			// Property details
			changeMyPropertyPage.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.clickNextSecurityDetails(extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
						
			//enter the Property details
			changeMyPropertyPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyPropertyPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems, extentedReport, true);
			changeMyPropertyPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyPropertyPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired, extentedReport, true);
			changeMyPropertyPage.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyPropertyPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyPropertyPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance, extentedReport, true);
			changeMyPropertyPage.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyPropertyPage.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport, true);
			changeMyPropertyPage.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport, true);
			changeMyPropertyPage.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport, true);
			changeMyPropertyPage.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue, extentedReport, true);
			changeMyPropertyPage.clickNextContentsInformation(extentedReport, true);
			
			// enter Additional covers
			changeMyPropertyPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,true);
			changeMyPropertyPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyPropertyPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyPropertyPage.clickNextAdditionalCovers(extentedReport, true);

			//Enter specified item details
			changeMyPropertyPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome, extentedReport, true);
			changeMyPropertyPage.addSpecifiedItemwithinHome(testData.arritemTypeWH[0], testData.arritemValueWH[0],
					testData.arritemDecsriptionWH[0], testData.arrsaveAndCancelWH[0], extentedReport, true);
			
			/*Log.softAssertThat(
					changeMyPropertyPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[0], extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);*/
			
			changeMyPropertyPage.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[0], extentedReport, true);
			changeMyPropertyPage.addSpecifiedItemwithinHome(testData.arritemTypeWH[1], testData.arritemValueWH[1],
					testData.arritemDecsriptionWH[1], testData.arrsaveAndCancelWH[1], extentedReport, true);
			
			/*Log.softAssertThat(
					changeMyPropertyPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[1], extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);*/
			changeMyPropertyPage.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[1], extentedReport, true);	
			
			changeMyPropertyPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			
			changeMyPropertyPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, extentedReport, false);
			changeMyPropertyPage.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH, testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);
			changeMyPropertyPage.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome, extentedReport, false);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			// Occupation details
			changeMyPropertyPage.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,true);
			
			changeMyPropertyPage.clickNextOccupation(extentedReport, true);
			
			// Residents details
			changeMyPropertyPage.clickNextResidents(extentedReport, true);
			
			changeMyPropertyPage.clickQuoteButton(extentedReport, true);
			
			// Quote Page
			MTAQuoteDetailsPage mtaQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundAmount"), mtaQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Change My Property on House hold Policy for change type : " + testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to Change My Property on House hold Policy for change type : " + testData.typeOfChange,
					driver, extentedReport);
			
			mtaQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			/*mtaQuoteDetailsPage.clickBuyButton(extentedReport, true);
						
			//Success page		
			SuccessPage successPage =new SuccessPage(driver).get();
			
			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment throwing any error");
			
			successPage.clickFinish(extentedReport, true);*/
			
			Log.testCaseResult(extentedReport);
				
		}
		catch (Exception e) {
				Log.exception(e, driver, extentedReport);
		} finally {
				Log.endTestCase(extentedReport);
				driver.quit();
		}
		
	}
	
	/********** Payment Method as Premium Finance *********/
	
	//@Test(description = "To adding the Contents cover to existing Building cover policy through BUY option in SS and selecting payment method as premium finance", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_008(String browser) throws Exception {

		String tcId = "TC_008";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addContentsCover"), mTAInitiatePage),
					"Add Contents Cover is displayed in MTA intiate page", "Add Contents Cover is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Add Contents cover details
			AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();

			Log.softAssertThat(addContentsCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddContentsCover"), addContentsCoverPage),
					"Add Contents cover page is opens after clicking Continue button", "Add Contents cover page is not opens after clicking Continue button", driver, extentedReport, true);
			
			// Enter details in Contents information
			addContentsCoverPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			addContentsCoverPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems,extentedReport, true);
			addContentsCoverPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			addContentsCoverPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired,extentedReport, true);
			
			addContentsCoverPage.selectTotalExcessRequired(testData.excessvalue, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount(testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			addContentsCoverPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance,extentedReport, true);
			
			addContentsCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, true);
			addContentsCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance, extentedReport,true);
			addContentsCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance, extentedReport,true);
			addContentsCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport,true);
			addContentsCoverPage.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue,extentedReport, true);
						
			addContentsCoverPage.clickNextContentsInformation(extentedReport, true);
			// enter Additional covers
			addContentsCoverPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,true);
			addContentsCoverPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			addContentsCoverPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			addContentsCoverPage.clickNextAdditionalCovers(extentedReport, true);
			
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,extentedReport, true);
			
			addContentsCoverPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, extentedReport, true);
			addContentsCoverPage.clickQuoteButton(extentedReport, true);
			
			//Your Quote Page
			MTAQuoteDetailsPage mtaQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtPaymentRequired"), mtaQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Contents Cover on House hold Policy for change type : " + testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to Add Contents Cover on House hold Policy for change type : " + testData.typeOfChange,
					driver, extentedReport);
			
			mtaQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mtaQuoteDetailsPage.clickBuyButton(extentedReport, true);
						
			//Payment page
			MTAPaymentPage mtaPaymentPage =new MTAPaymentPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaPaymentPage.uielement.verifyPageElements(Arrays.asList("txtPleaseChoosePaymentmethod"), mtaPaymentPage),
					"MTA Payment Page  is opens after clicking Buy button", "MTA Payment Page is not opens after clicking Buy button", driver, extentedReport,true);
			
			mtaPaymentPage.selectPaymentMethod(testData.paymentMethod, extentedReport, true);
			
			mtaPaymentPage.clickBuyButton(extentedReport);
			
			Log.softAssertThat(mtaPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mtaPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = mtaPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			cardDetailsPage.clickbuy(extentedReport, true);
						
			//Success page
			SuccessPage successPage =new SuccessPage(driver).get();
			
			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment throwing any error");
			
			successPage.clickFinish(extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description = "To adding the Building cover to existing Contents cover policy through BUY option in SS and selecting payment method as premium finance", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_009(String browser) throws Exception {

		String tcId = "TC_009";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addBuildingsCover"), mTAInitiatePage),
					"Add Building Cover cover is displayed in MTA intiate page", "Add Building Cover cover is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Add Contents cover details
			//AddContentsCoverPage addContentsCoverPage = new AddContentsCoverPage(driver, extentedReport).get();
			AddBuildingCoverPage addBuildingCoverPage = new AddBuildingCoverPage(driver, extentedReport).get();
			
			Log.softAssertThat(addBuildingCoverPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddBuildingCover"), addBuildingCoverPage),
					"Add Building cover page is opens after clicking Continue button", "Add Building cover page is not opens after clicking Continue button", driver, extentedReport, true);
			
			// Enter details for BUilding cover information
			addBuildingCoverPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, false);
			addBuildingCoverPage.clickYesOrNoRequireAccidentalDamage(testData.accidentalDamage, extentedReport, false);
			addBuildingCoverPage.selectExcessValue(testData.excessvalue, extentedReport, false);
			addBuildingCoverPage.clickYesOrNoPreviousBuildingInsurance(testData.previousBuildingInsurance, extentedReport, false);
			
			//Enter Previous Insurance details
			addBuildingCoverPage.selectNameOfPreviousInsurer(testData.nameOfPreviousInsurer, extentedReport, false);
			addBuildingCoverPage.enterPolicyNumberOfPreviousInsurance(testData.numberOfPreviousInsurance, extentedReport, false);	
			addBuildingCoverPage.enterExpiryDateOfPreviousInsurance(testData.expiryDateOfPreviousInsurance, extentedReport, false);	
			addBuildingCoverPage.selectPayForYourPreviousInsurance(testData.payForYourPreviousInsurance, extentedReport, false);
			addBuildingCoverPage.selectBuildingsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue, extentedReport, true);
			
			addBuildingCoverPage.clickQuoteButton(extentedReport, true);
						
			//Your Quote page
			MTAQuoteDetailsPage mtaQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
						
			Log.softAssertThat( mtaQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtPaymentRequired"), mtaQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Building Cover on House hold Policy for change type : " + testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to Add Building Cover on House hold Policy for change type : " + testData.typeOfChange,
					driver, extentedReport);
			
			mtaQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mtaQuoteDetailsPage.clickBuyButton(extentedReport, true);
			
			//Payment page
			MTAPaymentPage mtaPaymentPage =new MTAPaymentPage(driver, extentedReport).get();
			
			Log.softAssertThat(
					mtaPaymentPage.uielement.verifyPageElements(Arrays.asList("txtPleaseChoosePaymentmethod"),
							mtaPaymentPage),
					"MTA Payment Page  is opens after clicking Buy button",
					"MTA Payment Page is not opens after clicking Buy button", driver, extentedReport,true);
			
			mtaPaymentPage.selectPaymentMethod(testData.paymentMethod, extentedReport, true);
			
			mtaPaymentPage.clickBuyButton(extentedReport);
			
			Log.softAssertThat(mtaPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mtaPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = mtaPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			cardDetailsPage.clickbuy(extentedReport, true);
						
			//Success page
			SuccessPage successPage =new SuccessPage(driver).get();
			
			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment throwing any error");
			
			successPage.clickFinish(extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description = "To adding the specified items to existing Building cover & Contents cover policy through BUY option in SS and selecting payment method as premium finance", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_010(String browser) throws Exception {

		String tcId = "TC_010";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			
			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addSpecifiedItems"), mTAInitiatePage),
					"Add Specified Items page is displayed in MTA intiate page", "Add Specified Items page is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Add Specified Item details
			AddSpecifiedItemPage addSpecifiedItemPage =new AddSpecifiedItemPage(driver, extentedReport).get();
			
			Log.softAssertThat(addSpecifiedItemPage.uielement.verifyPageElements(Arrays.asList("txtTitleAddSpecifiedItems"), addSpecifiedItemPage),
					"Add Specified Items page is opens after clicking Continue button", "Add Specified Items page is not opens after clicking Continue button", driver, extentedReport, true);
					
			// Enter Specified items details
			
			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome,extentedReport, true);
			addSpecifiedItemPage.addSpecifiedItemwithinHome(testData.arritemTypeWH[0], testData.arritemValueWH[0],
								testData.arritemDecsriptionWH[0], testData.arrsaveAndCancelWH[0], extentedReport, true);
						
			/*Log.softAssertThat(	addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[0], extentedReport, true),
								"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
								driver, extentedReport, true);*/
						
			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[0], extentedReport, true);
			addSpecifiedItemPage.addSpecifiedItemwithinHome(testData.arritemTypeWH[1], testData.arritemValueWH[1],
								testData.arritemDecsriptionWH[1], testData.arrsaveAndCancelWH[1], extentedReport, true);
						
			/*Log.softAssertThat(	addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[1], extentedReport, true),
								"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
								driver, extentedReport, true);*/
						
			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[1], extentedReport, true);
			addSpecifiedItemPage.addSpecifiedItemwithinHome(testData.arritemTypeWH[2], testData.arritemValueWH[2],
								testData.arritemDecsriptionWH[2], testData.arrsaveAndCancelWH[2], extentedReport, true);
						
			/*Log.softAssertThat(	addSpecifiedItemPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[2], extentedReport, true),
								"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
								driver, extentedReport, true);*/
						
			//addSpecifiedItemPage.headersVerificationForTableInWithinHome(extentedReport, true);
			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[2], extentedReport, true);
			addSpecifiedItemPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			
			//Enter Specified items for outside home
			addSpecifiedItemPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome,extentedReport, true);
			
			addSpecifiedItemPage.selectItemTypeOfSpecifiedItemsOutsideHome(testData.itemTypeOH, extentedReport, false);
			addSpecifiedItemPage.enterItemValueOfSpecifiedItemsOutsideHome(testData.itemValueOH, extentedReport, false);
			addSpecifiedItemPage.enterDescriptionOfItemOfSpecifiedItemsOutsideHome(testData.itemDecsriptionOH, extentedReport, false);
			addSpecifiedItemPage.clickSaveItemNewSpecifiedItemOutsideHome(extentedReport, true);
			addSpecifiedItemPage.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome, extentedReport, false);
			
			//addSpecifiedItemPage.clicknext
			addSpecifiedItemPage.clickQuoteButton(extentedReport, true);
			
			//Quote Page
			MTAQuoteDetailsPage mtaQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
						
								
			mtaQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mtaQuoteDetailsPage.clickBuyButton(extentedReport, true);
			
			//Payment Page
			MTAPaymentPage mtaPaymentPage =new MTAPaymentPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaPaymentPage.uielement.verifyPageElements(Arrays.asList("txtPleaseChoosePaymentmethod"), mtaPaymentPage),
					"MTA Payment Page  is opens after clicking Buy button", "MTA Payment Page is not opens after clicking Buy button", driver, extentedReport,true);
			
			mtaPaymentPage.selectPaymentMethod(testData.paymentMethod, extentedReport, true);
			
			mtaPaymentPage.clickBuyButton(extentedReport);
			
			Log.softAssertThat(mtaPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mtaPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = mtaPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			cardDetailsPage.clickbuy(extentedReport, true);
						
			//Success page
			SuccessPage successPage =new SuccessPage(driver).get();
			
			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment throwing any error");
			
			successPage.clickFinish(extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description = "To changing from existing Building cover policy to Contents cover policy and verifying the two Occupation detials entered in BO through BUY option in SS and selecting payment method as premium finance", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_011(String browser) throws Exception {

		String tcId = "TC_011";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("changeMyProperty"), mTAInitiatePage),
					"Change My Property cover is displayed in MTA intiate page", "Change My Property cover is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			//Enter the Contents cover policy details 
			
			ChangeMyPropertyPage changeMyPropertyPage = new ChangeMyPropertyPage(driver, extentedReport).get();
			
			Log.softAssertThat(changeMyPropertyPage.uielement.verifyPageElements(Arrays.asList("txtTitleYourProperty"), changeMyPropertyPage),
					"Building cover page is opens after clicking Continue button", "Building cover page is not opens after clicking Continue button", driver, extentedReport, true);
			
			changeMyPropertyPage.selectChangeType(testData.sectionType, extentedReport, false);
			changeMyPropertyPage.clickNextButton(extentedReport, true);
			
			//Enter the Building details
					
			changeMyPropertyPage.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyPropertyPage.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyPropertyPage.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyPropertyPage.clickYesButtonInOverlay(extentedReport, true);
			
			// Property details
			changeMyPropertyPage.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.clickNextSecurityDetails(extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
						
			//enter the Property details
			changeMyPropertyPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyPropertyPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems, extentedReport, true);
			changeMyPropertyPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyPropertyPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired, extentedReport, true);
			changeMyPropertyPage.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyPropertyPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount( testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyPropertyPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance, extentedReport, true);
			changeMyPropertyPage.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyPropertyPage.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport, true);
			changeMyPropertyPage.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport, true);
			changeMyPropertyPage.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport, true);
			changeMyPropertyPage.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue, extentedReport, true);
			changeMyPropertyPage.clickNextContentsInformation(extentedReport, true);
			
			// enter Additional covers
			changeMyPropertyPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,true);
			changeMyPropertyPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyPropertyPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyPropertyPage.clickNextAdditionalCovers(extentedReport, true);

			//Enter specified item details
			changeMyPropertyPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome, extentedReport, true);
			
			changeMyPropertyPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, extentedReport, true);
			
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			// Verifying two Occupation details
			changeMyPropertyPage.verifyDeleteAndEditBtnInOccupation(testData.occupationType, extentedReport, true);
			
			changeMyPropertyPage.clickNextOccupation(extentedReport, true);
			
			// Residents details
			changeMyPropertyPage.clickNextResidents(extentedReport, true);

			changeMyPropertyPage.clickQuoteButton(extentedReport, true);
			
			// Quote Page
			MTAQuoteDetailsPage mtaQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("refundAmt"), mtaQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Change My Property on House hold Policy for change type : " + testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to Change My Property on House hold Policy for change type : " + testData.typeOfChange,
					driver, extentedReport);
			
			mtaQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mtaQuoteDetailsPage.clickBuyButton(extentedReport, true);
						
			//Success page		
			SuccessPage successPage =new SuccessPage(driver).get();
			
			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment throwing any error");
			
			successPage.clickFinish(extentedReport, true);
			
			Log.testCaseResult(extentedReport);
				
		}
		catch (Exception e) {
				Log.exception(e, driver, extentedReport);
		} finally {
				Log.endTestCase(extentedReport);
				driver.quit();
		}
		
	}

	//@Test(description = "To changing existing Contents cover policy to Building cover policy through BUY option in SS and selecting payment method as premium finance", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_012(String browser) throws Exception {

		String tcId = "TC_012";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("changeMyProperty"), mTAInitiatePage),
					"Change My Property cover is displayed in MTA intiate page", "Change My Property cover is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			//Enter the Contents cover policy details 
			
			ChangeMyPropertyPage changeMyPropertyPage = new ChangeMyPropertyPage(driver, extentedReport).get();
			
			Log.softAssertThat(changeMyPropertyPage.uielement.verifyPageElements(Arrays.asList("txtTitleYourProperty"), changeMyPropertyPage),
					"Contents cover page is opens after clicking Continue button", "Contents cover page is not opens after clicking Continue button", driver, extentedReport, true);
			
			changeMyPropertyPage.selectChangeType(testData.sectionType, extentedReport, false);
			changeMyPropertyPage.clickNextButton(extentedReport, true);
			
			// Enter details for Contents cover information
			changeMyPropertyPage.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyPropertyPage.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyPropertyPage.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyPropertyPage.clickYesButtonInOverlay(extentedReport, true);
			
			changeMyPropertyPage.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.clickNextSecurityDetails(extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			//enter Building  cover details
			changeMyPropertyPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, false);
			changeMyPropertyPage.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyPropertyPage.clickNextBuildingsCoverDetails(extentedReport, true);
			
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			// Occupation details
			changeMyPropertyPage.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,true);
						
			changeMyPropertyPage.clickNextOccupation(extentedReport, true);
						
			// Residents details
			changeMyPropertyPage.clickNextResidents(extentedReport, true);
			
			changeMyPropertyPage.clickQuoteButton(extentedReport, true);
						
			//Your Quote page
			MTAQuoteDetailsPage mtaQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtPaymentRequired"), mtaQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Building Cover on House hold Policy for change type : " + testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to Add Building Cover on House hold Policy for change type : " + testData.typeOfChange,
					driver, extentedReport);
			
			mtaQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mtaQuoteDetailsPage.clickBuyButton(extentedReport, true);
			
			//Payment page			
			MTAPaymentPage mtaPaymentPage =new MTAPaymentPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"), mtaPaymentPage),
					"MTA Payment Page  is opens after clicking Buy button", "MTA Payment Page is not opens after clicking Buy button", driver, extentedReport,true);
			
			mtaPaymentPage.selectPaymentMethod(testData.paymentMethod, extentedReport, true);
			
			mtaPaymentPage.clickBuyButton(extentedReport);
			
			Log.softAssertThat(mtaPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mtaPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = mtaPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
							
			cardDetailsPage.clickbuy(extentedReport, true);
						
			//Success page
			/*SuccessPage successPage =new SuccessPage(driver).get();
			
			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment throwing any error");
			
			successPage.clickFinish(extentedReport, true);*/
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description = "To add the Occupation details to existing Contents cover policy and converting to Building cover policy through BUY option in SS and selecting payment method as premium finance", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_013(String browser) throws Exception {

		String tcId = "TC_013";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("changeMyProperty"), mTAInitiatePage),
					"Change My Property cover is displayed in MTA intiate page", "Change My Property cover is not displayed in MTA intiate page", driver, extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			//Enter the Contents cover policy details 
			
			ChangeMyPropertyPage changeMyPropertyPage = new ChangeMyPropertyPage(driver, extentedReport).get();
			
			Log.softAssertThat(changeMyPropertyPage.uielement.verifyPageElements(Arrays.asList("txtTitleYourProperty"), changeMyPropertyPage),
					"Contents cover page is opens after clicking Continue button", "Contents cover page is not opens after clicking Continue button", driver, extentedReport, true);
			
			changeMyPropertyPage.selectChangeType(testData.sectionType, extentedReport, false);
			changeMyPropertyPage.clickNextButton(extentedReport, true);
			
			// Enter details for Contents cover information
			changeMyPropertyPage.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyPropertyPage.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyPropertyPage.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyPropertyPage.clickYesButtonInOverlay(extentedReport, true);
			
			changeMyPropertyPage.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.clickNextSecurityDetails(extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			//enter Building  cover details
			changeMyPropertyPage.enterRebuildCostOfHome(testData.rebuildCost, extentedReport, false);
			changeMyPropertyPage.selectExcessValueB(testData.excessvalue, extentedReport, true);
			changeMyPropertyPage.clickNextBuildingsCoverDetails(extentedReport, true);
			
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			// Occupation details
			changeMyPropertyPage.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,true);
			changeMyPropertyPage.addOccupation(testData.occupation, testData.business, testData.employmentStatus, testData.mainOccupation, testData.saveAndCancelO, extentedReport, true);
			
			changeMyPropertyPage.clickNextOccupation(extentedReport, true);
						
			// Residents details
			changeMyPropertyPage.clickNextResidents(extentedReport, true);
			
			changeMyPropertyPage.clickQuoteButton(extentedReport, true);
			
			//Your Quote page
			MTAQuoteDetailsPage mtaQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtPaymentRequired"), mtaQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Add Building Cover on House hold Policy for change type : " + testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to Add Building Cover on House hold Policy for change type : " + testData.typeOfChange,
					driver, extentedReport);
						
			mtaQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mtaQuoteDetailsPage.clickBuyButton(extentedReport, true);
			
			//Payment page			
			MTAPaymentPage mtaPaymentPage =new MTAPaymentPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"), mtaPaymentPage),
					"MTA Payment Page  is opens after clicking Buy button", "MTA Payment Page is not opens after clicking Buy button", driver, extentedReport,true);
			
			mtaPaymentPage.selectPaymentMethod(testData.paymentMethod, extentedReport, true);
			
			mtaPaymentPage.clickBuyButton(extentedReport);
			
			Log.softAssertThat(mtaPaymentPage.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page", "Continuous Payments Question is not displayed in Card details page",
					driver, extentedReport, true);

			Log.softAssertThat(!mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is not enable when the Continuous Payments Question is not answered", 
					"Buy with Another Card is enable when the Continuous Payments Question is not answered", driver, extentedReport, true);

			mtaPaymentPage.clickNoButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(mtaPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = mtaPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
					
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			cardDetailsPage.clickbuy(extentedReport, true);
						
			//success page
			SuccessPage successPage =new SuccessPage(driver).get();
			
			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment throwing any error");
			
			successPage.clickFinish(extentedReport, true);
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	//@Test(description = "To changing from existing Buildings & Content cover policy to Contents cover policy with Specified items detials through BUY option in SS and selecting payment method as premium finance", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_014(String browser) throws Exception {

		String tcId = "TC_014";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			CustomerDashboardPage customerDashboardPage = customerSignInPage.
					loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("changeMyProperty"), mTAInitiatePage),
					"Change My Property cover is displayed in MTA intiate page", "Change My Property cover is not displayed in MTA intiate page", driver, extentedReport, true);

			/*Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("addSpecifiedItems"), mTAInitiatePage),
					"Add Specified Items page is displayed in MTA intiate page",
					"Add Specified Items page is not displayed in MTA intiate page", driver, extentedReport, true);*/

			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			//Enter the Contents cover policy details 
			
			ChangeMyPropertyPage changeMyPropertyPage = new ChangeMyPropertyPage(driver, extentedReport).get();
			
			Log.softAssertThat(changeMyPropertyPage.uielement.verifyPageElements(Arrays.asList("txtTitleYourProperty"), changeMyPropertyPage),
					"Building cover page is opens after clicking Continue button", "Building cover page is not opens after clicking Continue button", driver, extentedReport, true);
			
			changeMyPropertyPage.selectChangeType(testData.sectionType, extentedReport, false);
			changeMyPropertyPage.clickNextButton(extentedReport, true);
			
			//Enter the Building details
					
			changeMyPropertyPage.enterAddressOfPropToBeInsured(testData.postCodeH, extentedReport, true);
			changeMyPropertyPage.verifySavedAddress(testData.postCodeH, extentedReport, true);

			changeMyPropertyPage.clickYesButtonUnderYourCorrespondenceAddress(extentedReport, true);
			changeMyPropertyPage.clickYesButtonInOverlay(extentedReport, true);
			
			// Property details
			changeMyPropertyPage.enterOtherdetailsPropToBeInsured(testData.dateOfResidence, testData.yearOfBuilt, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.enterDetailsforBuildingConstruction(testData.perOfPropRoof, extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			changeMyPropertyPage.clickNextSecurityDetails(extentedReport, true);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			//enter the Property details
			changeMyPropertyPage.enterTotalValueOfYourHomeContents(testData.valueOfHomeContents, extentedReport, true);
			changeMyPropertyPage.enterTotalValueOfPreciousOrCostlyItems(testData.valueOfPreciousOrCostlyItems, extentedReport, true);
			changeMyPropertyPage.enterValueOfMostExpensiveItem(testData.valueOfMostExpensiveItem, extentedReport, true);
			changeMyPropertyPage.selectTypeOfHomeContentsCoverRequired(testData.typeOfHomeContentsCoverRequired, extentedReport, true);
			changeMyPropertyPage.selectTotalExcessRequiredC(testData.excessvalue, extentedReport, true);
			changeMyPropertyPage.clickYesOrNoBtnContentsSumRepresentReplacementAmount( testData.contentsSumRepresentReplacementAmount, extentedReport, true);
			changeMyPropertyPage.clickYesOrNoBtnPreviouslyContentsInsurances(testData.previouslyContentsInsurance, extentedReport, true);
			changeMyPropertyPage.selectNameOfPreviousInsurerC(testData.nameOfPreviousInsurer, extentedReport, true);
			changeMyPropertyPage.enterPolicyNumberOfPreviousInsuranceC(testData.numberOfPreviousInsurance, extentedReport, true);
			changeMyPropertyPage.enterExpiryDateOfPreviousInsuranceC(testData.expiryDateOfPreviousInsurance, extentedReport, true);
			changeMyPropertyPage.selectPayForYourPreviousInsuranceC(testData.payForYourPreviousInsurance, extentedReport, true);
			changeMyPropertyPage.selectContentsInsuranceWithoutClaiming(testData.noOfYearsWithoutClaimingValue, extentedReport, true);
			changeMyPropertyPage.clickNextContentsInformation(extentedReport, true);
			
			// enter Additional covers
			changeMyPropertyPage.enterCashKeptInYourHomeToBeInsured(testData.cashInYourHomeToBeInsured, extentedReport,true);
			changeMyPropertyPage.entercreditCardsToBeInsured(testData.creditCardsToBeInsured, extentedReport, true);
			changeMyPropertyPage.enterfrozenFoodToBeInsured(testData.frozenFoodToBeInsured, extentedReport, true);
			changeMyPropertyPage.clickNextAdditionalCovers(extentedReport, true);

			//Enter specified item details
			changeMyPropertyPage.clickYesOrNoAddPersonalPossessionsWithinHome(testData.addPersonalPossessionsWithinHome, extentedReport, true);
			changeMyPropertyPage.addSpecifiedItemwithinHome(testData.arritemTypeWH[0], testData.arritemValueWH[0],
					testData.arritemDecsriptionWH[0], testData.arrsaveAndCancelWH[0], extentedReport, true);
			
			/*Log.softAssertThat(
					changeMyPropertyPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[0], extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);*/
			
			changeMyPropertyPage.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[0],
					extentedReport, true);
			changeMyPropertyPage.addSpecifiedItemwithinHome(testData.arritemTypeWH[1], testData.arritemValueWH[1],
					testData.arritemDecsriptionWH[1], testData.arrsaveAndCancelWH[1], extentedReport, true);
			
			/*Log.softAssertThat(
					changeMyPropertyPage.verifyDeleteAndEditBtnInWithinHome(testData.arritemNameWH[1], extentedReport, true),
					"Add Specified item is verified in Within Home", "Add Specified item is not verified within Home",
					driver, extentedReport, true);*/
			changeMyPropertyPage.clickYesOrNoAddAnotherItemInWithinHome(testData.arraddAnotherItemInWithinHome[1], extentedReport, true);	
			
			changeMyPropertyPage.clickNextNewSpecifiedItemWithinHome(extentedReport, true);
			
			changeMyPropertyPage.clickYesOrNoAddPersonalPossessionsOutsideHome(testData.addPersonalPossessionsOutsideHome, extentedReport, false);
			changeMyPropertyPage.addSpecifiedItemOutsideHome(testData.itemTypeOH, testData.itemValueOH, testData.itemDecsriptionOH, testData.saveAndCancelOH, extentedReport, true);
			changeMyPropertyPage.clickYesOrNoAddAnotherItemInOutsideHome(testData.addAnotherItemInOutsideHome, extentedReport, false);
			changeMyPropertyPage.clickContinue(extentedReport, true);
			
			// Occupation details
			changeMyPropertyPage.clickYesOrNoAddAnotherOccupation(testData.addAnotherOccupation, extentedReport,true);
			
			changeMyPropertyPage.clickNextOccupation(extentedReport, true);
			
			// Residents details
			changeMyPropertyPage.clickNextResidents(extentedReport, true);
			
			changeMyPropertyPage.clickQuoteButton(extentedReport, true);
			
			// Quote Page
			MTAQuoteDetailsPage mtaQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			
			Log.softAssertThat(mtaQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("refundAmt"), mtaQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to Change My Property on House hold Policy for change type : " + testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to Change My Property on House hold Policy for change type : " + testData.typeOfChange,
					driver, extentedReport);
			
			mtaQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mtaQuoteDetailsPage.clickBuyButton(extentedReport, true);
						
			//Success page		
			SuccessPage successPage =new SuccessPage(driver).get();
			
			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment throwing any error");
			
			successPage.clickFinish(extentedReport, true);
			
			Log.testCaseResult(extentedReport);
				
		}
		catch (Exception e) {
				Log.exception(e, driver, extentedReport);
		} finally {
				Log.endTestCase(extentedReport);
				driver.quit();
		}
		
	}

	/********** Renewal Buy Scenarios for Motor *********/

	@Test(description = "Verify that the new card details will be set as default if user doesn't have any existing cards and selected 'Yes' for the CPA question", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_015(String browser) throws Exception {

		String tcId = "TC_015";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType),
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),
					"Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches),
					"Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			// Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage renewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							renewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver,
					extentedReport);

			Log.softAssertThat(renewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page",
					"Continuous Payments Question is not displayed in Card details page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page",
					"Select Card Session is not displayed in Card details page", driver, extentedReport, true);

			renewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(renewalPaymentCard.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = renewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			/*cardDetailsPage.clickbuy(extentedReport, true);

			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");
			success.clickFinish(extentedReport, true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that the new card details will not be set as default if user doesn't have any existing cards and selected 'NO' for the CPA question", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_016(String browser) throws Exception {

		String tcId = "TC_016";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType),
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),
					"Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches),
					"Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			// Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage renewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							renewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver,
					extentedReport);

			Log.softAssertThat(renewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page",
					"Continuous Payments Question is not displayed in Card details page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page",
					"Select Card Session is not displayed in Card details page", driver, extentedReport, true);

			renewalPaymentCard.clickNoButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(renewalPaymentCard.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = renewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			/*cardDetailsPage.clickbuy(extentedReport, true);

			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");
			success.clickFinish(extentedReport, true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "If existing card is selected ‘Buy’ button will be enabled to allow the user to pay with the existing card and the existing card will not be set as the default if processed", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_017(String browser) throws Exception {

		String tcId = "TC_017";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType),
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),
					"Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches),
					"Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);
			// Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage renewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							renewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver,
					extentedReport);

			Log.softAssertThat(renewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page",
					"Continuous Payments Question is not displayed in Card details page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page",
					"Select Card Session is not displayed in Card details page", driver, extentedReport, true);

			renewalPaymentCard.clickNoButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(renewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport, true);
			
			/*renewalPaymentCard.clickBuyButton(extentedReport, true);
			
			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "If existing card is selected ‘Buy’ button will be enabled to allow the user to pay with the existing card and the existing card will be set as the default if processed", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_018(String browser) throws Exception {

		String tcId = "TC_018";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType),
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),
					"Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches),
					"Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);
			// Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage renewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							renewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver,
					extentedReport);

			Log.softAssertThat(renewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page",
					"Continuous Payments Question is not displayed in Card details page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page",
					"Select Card Session is not displayed in Card details page", driver, extentedReport, true);

			renewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(renewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport, true);
			
			/*renewalPaymentCard.clickBuyButton(extentedReport, true);
			
			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description = "Verify that user can capture the bank details only if they are not available and  when Business of this Policy was already paid via credit card option for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_019(String browser) throws Exception {

		String tcId = "TC_019";
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

			// Click view my policy button
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType),
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),
					"Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches),
					"Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			renewMyPolicyPage.selectChange(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);
			
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page",
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(
					renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickRenewButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),
							buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page",
					"Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);
			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			// buyPaymentPage.enterBankName(testData.bankName, extentedReport,
			// true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page",
					"Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			/*buyPaymentPage.clickBuyButton(extentedReport, true);
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"), successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport, true);

			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport, true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dashboard page after clicking finish button in Success page",
					driver, extentedReport, true);*/
			
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description = "Verify that for PF the new card details will be set as default if user doesn't have any existing cards and selected 'Yes' for the CPA question with deposit amount for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_020(String browser) throws Exception {

		String tcId = "TC_020";
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

			// Click view my policy button
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType),
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),
					"Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches),
					"Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			renewMyPolicyPage.selectChange(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page",
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(
					renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickRenewButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),
							buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page",
					"Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page",
					"Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			/*buyPaymentPage.clickBuyButton(extentedReport, true);
			RenewalPaymentPage renewalPaymentPage = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not navigated to Credit card details page in the Renewal Payment page",
					driver, extentedReport, true);

			// Credit card page
			renewalPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			cardDetailsPage.clickbuy(extentedReport, true);

			// Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"), successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport, true);

			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport, true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dashboard page after clicking finish button in Success page",
					driver, extentedReport, true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description = "Verify that for PF the new card details will not be set as default if user doesn't have any existing cards and selected 'No' for the CPA question with deposit amount for Motor policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_021(String browser) throws Exception {

		String tcId = "TC_021";
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

			// Click view my policy button
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType),
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),
					"Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches),
					"Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			renewMyPolicyPage.selectChange(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page",
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(
					renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickRenewButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),
							buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page",
					"Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page",
					"Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			/*buyPaymentPage.clickBuyButton(extentedReport, true);
			RenewalPaymentPage renewalPaymentPage = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not navigated to Credit card details page in the Renewal Payment page",
					driver, extentedReport, true);

			// Credit card page
			renewalPaymentPage.clickNoButtonContinuousPayments(extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			cardDetailsPage.clickbuy(extentedReport, true);

			// Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"), successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport, true);

			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport, true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dashboard page after clicking finish button in Success page",
					driver, extentedReport, true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description = "If existing card is selected ‘Buy’ button will be enabled to allow the user to pay with the existing card and the existing card will be set as the default if processed with PF", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_022(String browser) throws Exception {

		String tcId = "TC_022";
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

			// Click view my policy button
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType),
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),
					"Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches),
					"Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			renewMyPolicyPage.selectChange(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page",
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(
					renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickRenewButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),
							buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page",
					"Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page",
					"Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			/*buyPaymentPage.clickBuyButton(extentedReport, true);
			RenewalPaymentPage renewalPaymentPage = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greater than ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is less than ZERO and not navigated to Credit card details page in the Renewal Payment page",
					driver, extentedReport, true);

			// Credit card page
			renewalPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			cardDetailsPage.clickbuy(extentedReport, true);

			// Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"), successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport, true);

			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport, true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dashboard page after clicking finish button in Success page",
					driver, extentedReport, true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description = "If existing card is selected ‘Buy’ button will be enabled to allow the user to pay with the existing card and the existing card will not be set as the default if processed with PF", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_023(String browser) throws Exception {

		String tcId = "TC_023";
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

			// Click view my policy button
			// customerDashboardPage.clickViewMyRenewal(extentedReport, true);
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);
			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);
			Log.softAssertThat(renewMyPolicyPage.getVehicleBodyType(testData.vehicleBodyType),
					"Vehicle body type is show in the Renew My Policy page",
					"Vehicle body type is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleCCNumber(testData.vehicleCCNumber),
					"Vehicle CC number is show in the Renew My Policy page",
					"Vehicle CC number is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleFuel(testData.vehicleFuel),
					"Vehicle Fuel is show in the Renew My Policy page",
					"Vehicle Fuel is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleMatches(testData.vehicleMatches),
					"Vehicle Matches is show in the Renew My Policy page",
					"Vehicle Matches is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleNumberOfDoors(testData.vehicleNumberOfDoors),
					"Vehicle Number of Doors is show in the Renew My Policy page",
					"Vehicle Number of Doors is not show in the Renew My Policy page", driver, extentedReport, false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleRegistrationNumber(testData.vehicleRegistrationNumber),
					"Vehicle Registration Number is show in the Renew My Policy page",
					"Vehicle Registration Number is not show in the Renew My Policy page", driver, extentedReport,
					false);

			Log.softAssertThat(renewMyPolicyPage.getVehicleTransmission(testData.vehicleTransmission),
					"Vehicle Transmission is show in the Renew My Policy page",
					"Vehicle Transmission is not show in the Renew My Policy page", driver, extentedReport, false);

			renewMyPolicyPage.selectChange(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page",
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(
					renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickRenewButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),
							buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page",
					"Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page",
					"Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			/*buyPaymentPage.clickBuyButton(extentedReport, true);
			RenewalPaymentPage renewalPaymentPage = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not navigated to Credit card details page in the Renewal Payment page",
					driver, extentedReport, true);

			// Credit card page
			renewalPaymentPage.clickNoButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyBuyWithOtherCardButtonClickable(extentedReport),
					"Buy with Another Card is enable when the Continuous Payments Question is answered", 
					"Buy with Another Card is not enable when the Continuous Payments Question is answered", driver, extentedReport, true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			cardDetailsPage.clickbuy(extentedReport, true);

			// Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"), successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport, true);

			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport, true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dashboard page after clicking finish button in Success page",
					driver, extentedReport, true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	/********** Renewal Buy Scenarios for HH *********/

	@Test(description = "Verify that the new card details will be set as default if user doesn't have any existing cards and selected 'Yes' for the CPA question", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_024(String browser) throws Exception {

		String tcId = "TC_024";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			// Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage renewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							renewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver,
					extentedReport);

			Log.softAssertThat(renewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page",
					"Continuous Payments Question is not displayed in Card details page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page",
					"Select Card Session is not displayed in Card details page", driver, extentedReport, true);

			renewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = renewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			/*cardDetailsPage.clickbuy(extentedReport, true);

			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");
			success.clickFinish(extentedReport, true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Verify that the new card details will not be set as default if user doesn't have any existing cards and selected 'NO' for the CPA question", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_025(String browser) throws Exception {

		String tcId = "TC_025";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			// Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage renewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							renewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver,
					extentedReport);

			Log.softAssertThat(renewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page",
					"Continuous Payments Question is not displayed in Card details page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page",
					"Select Card Session is not displayed in Card details page", driver, extentedReport, true);

			renewalPaymentCard.clickNoButtonContinuousPayments(extentedReport, true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = renewalPaymentCard.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			/*cardDetailsPage.clickbuy(extentedReport, true);

			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");
			success.clickFinish(extentedReport, true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "If existing card is selected ‘Buy’ button will be enabled to allow the user to pay with the existing card and the existing card will not be set as the default if processed", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_026(String browser) throws Exception {

		String tcId = "TC_026";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);
			// Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage renewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							renewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver,
					extentedReport);

			Log.softAssertThat(renewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page",
					"Continuous Payments Question is not displayed in Card details page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page",
					"Select Card Session is not displayed in Card details page", driver, extentedReport, true);

			renewalPaymentCard.clickNoButtonContinuousPayments(extentedReport, true);
			
			Log.softAssertThat(renewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport, true);
			
			/*renewalPaymentCard.clickBuyButton(extentedReport, true);
			
			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "If existing card is selected ‘Buy’ button will be enabled to allow the user to pay with the existing card and the existing card will be set as the default if processed", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_027(String browser) throws Exception {

		String tcId = "TC_027";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);
			// Renewal Payment Required page
			RenewalPaymentRequiredPage renewalPaymentPage = new RenewalPaymentRequiredPage(driver, extentedReport)
					.get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentPage.verifyAnnualButtonSelected(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amuont for policy is shown in the Renew Payment page",
					"Total Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amuont for policy is shown in the Renew Payment page",
					"Insurer Premium Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amuont for policy is shown in the Renew Payment page",
					"Premium Fee Amuont for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			renewalPaymentPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentPage.clickRenewButton(extentedReport, true);

			RenewalPaymentPage renewalPaymentCard = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentCard.uielement.verifyPageElements(Arrays.asList("txtTitlePleaseChoosePaymentCard"),
							renewalPaymentCard),
					"On clicking 'Buy' button redirected to Policy payment  card details Screen",
					"On clicking 'Buy' button did not redirected to Policy payment  card details Screen", driver,
					extentedReport);

			Log.softAssertThat(renewalPaymentCard.verifyContinuousPaymentsQuestion(extentedReport),
					"Continuous Payments Question is displayed in Card details page",
					"Continuous Payments Question is not displayed in Card details page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentCard.verifySelectCardSession(extentedReport),
					"Select Card Session is displayed in Card details page",
					"Select Card Session is not displayed in Card details page", driver, extentedReport, true);

			renewalPaymentCard.clickYesButtonContinuousPayments(extentedReport, true);
			
			Log.softAssertThat(renewalPaymentCard.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport, true);
			
			/*renewalPaymentCard.clickBuyButton(extentedReport, true);
			
			SuccessPage success = new SuccessPage(driver).get();

			success = new SuccessPage(driver).get();
			Log.softAssertThat(success.getSuccessMsg(extentedReport, true),
					"System accepted payment without throwing any error",
					"System not accepted payment without throwing any error");*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description = "Verify that user can capture the bank details only if they are not available and  when Business of this Policy was already paid via credit card option for HH policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_028(String browser) throws Exception {

		String tcId = "TC_028";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page",
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(
					renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickRenewButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),
							buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page",
					"Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);
			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			// buyPaymentPage.enterBankName(testData.bankName, extentedReport,
			// true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page",
					"Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			/*buyPaymentPage.clickBuyButton(extentedReport, true);
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"), successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport, true);

			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport, true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dashboard page after clicking finish button in Success page",
					driver, extentedReport, true);*/

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description = " Verify that for PF the new card details will be set as default if user doesn't have any existing cards and selected 'Yes' for the CPA question with deposit amount for HH policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_029(String browser) throws Exception {

		String tcId = "TC_029";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page",
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(
					renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickRenewButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),
							buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page",
					"Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page",
					"Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			/*buyPaymentPage.clickBuyButton(extentedReport, true);
			RenewalPaymentPage renewalPaymentPage = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not navigated to Credit card details page in the Renewal Payment page",
					driver, extentedReport, true);

			// Credit card page
			renewalPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);
		
			Log.softAssertThat(renewalPaymentPage.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport,
					true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			cardDetailsPage.clickbuy(extentedReport, true);

			// Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"), successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport, true);

			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport, true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dashboard page after clicking finish button in Success page",
					driver, extentedReport, true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description = "Verify that for PF the new card details will not be set as default if user doesn't have any existing cards and selected 'No' for the CPA question with deposit amount for HH policies.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_030(String browser) throws Exception {

		String tcId = "TC_030";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);
			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page",
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(
					renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickRenewButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),
							buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page",
					"Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page",
					"Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			/*buyPaymentPage.clickBuyButton(extentedReport, true);
			RenewalPaymentPage renewalPaymentPage = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not navigated to Credit card details page in the Renewal Payment page",
					driver, extentedReport, true);

			// Credit card page
			renewalPaymentPage.clickNoButtonContinuousPayments(extentedReport, true);
			
			Log.softAssertThat(renewalPaymentPage.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport,
					true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			cardDetailsPage.clickbuy(extentedReport, true);

			// Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"), successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport, true);

			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport, true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dashboard page after clicking finish button in Success page",
					driver, extentedReport, true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description = "If existing card is selected ‘Buy’ button will be enabled to allow the user to pay with the existing card and the existing card will be set as the default if processed with PF", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_031(String browser) throws Exception {

		String tcId = "TC_031";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page",
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(
					renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickRenewButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),
							buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page",
					"Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page",
					"Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			/*buyPaymentPage.clickBuyButton(extentedReport, true);
			RenewalPaymentPage renewalPaymentPage = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not navigated to Credit card details page in the Renewal Payment page",
					driver, extentedReport, true);

			// Credit card page
			renewalPaymentPage.clickYesButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport,
					true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			cardDetailsPage.clickbuy(extentedReport, true);

			// Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"), successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport, true);

			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport, true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dashboard page after clicking finish button in Success page",
					driver, extentedReport, true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	//@Test(description = "If existing card is selected ‘Buy’ button will be enabled to allow the user to pay with the existing card and the existing card will not be set as the default if processed with PF", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_032(String browser) throws Exception {

		String tcId = "TC_032";
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

			// Click view my policy button
			customerDashboardPage.clickViewMYPolicyButton(testData.policyName, extentedReport, true);

			RenewMyPolicyPage renewMyPolicyPage = new RenewMyPolicyPage(driver, extentedReport).get();
			Log.softAssertThat(
					renewMyPolicyPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewMyPolicyPage),
					"Renew My Policy page is opend after clicking view my policy button",
					"Renew My Policy page is not opend after clicking view my policy button", driver, extentedReport,
					true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForOriginalPolicy(testData.originalPolicyDate),
					"Period of cover for original policy is shown in the Renew My policy page",
					"Period of cover for original policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getPeriodCoverForRenwalPolicy(testData.renewalPolicyDate),
					"Period of cover for Renewal policy is shown in the Renew My policy page",
					"Period of cover for Renewal policy is Not shown in the Renew My policy page", driver,
					extentedReport, true);

			Log.softAssertThat(renewMyPolicyPage.getThePropertyToBeInsured(testData.thePropertyToBeInsured),
					"Property to be Insured shown in the Renew My policy page",
					"Property to be Insured is Not shown in the Renew My policy page", driver, extentedReport, true);

			renewMyPolicyPage.selectChange(extentedReport, true);

			RenewalPaymentRequiredPage renewalPaymentRequiredPage = new RenewalPaymentRequiredPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					renewalPaymentRequiredPage.uielement.verifyPageElements(Arrays.asList("txtTitleRenewMyPolicy"),
							renewalPaymentRequiredPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			renewalPaymentRequiredPage.verifyMonthlyButtonSelected(extentedReport, true);
			renewalPaymentRequiredPage.clickMonthlyButton(extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getTotalPremiumAmount(testData.totalpremiumAmount),
					"Total Premium Amount for policy is shown in the Renew Payment page",
					"Total Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(
					renewalPaymentRequiredPage.getpremiumAmountToBePaidInInstalmentsOf(testData.paidInInstalments),
					"Premium Amount To Be Paid In Instalments for policy is shown in the Renew Payment page",
					"Premium Amount To Be Paid In Instalments for policy is not shown in the Renew Payment page",
					driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getInsurerPremiumAmount(testData.premiumAmount),
					"Insurer Premium Amount for policy is shown in the Renew Payment page",
					"Insurer Premium Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			Log.softAssertThat(renewalPaymentRequiredPage.getPremiumFee(testData.premiumFeesAmount),
					"Premium Fee Amount for policy is shown in the Renew Payment page",
					"Premium Fee Amount for policy is not shown in the Renew Payment page", driver, extentedReport,
					true);

			renewalPaymentRequiredPage.verifyAddedExtraFeatures(testData.addedExtraFeaturesValue,
					testData.addedExtraFeaturesName, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getAmountOfCredit(testData.amountOfCreditCost),
					"Amount Of Credit for policy is shown in the Renew Payment page",
					"Amount Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			Log.softAssertThat(renewalPaymentRequiredPage.getCostOfCredit(testData.costOfCreditAmount),
					"Cost Of Credit for policy is shown in the Renew Payment page",
					"Cost Of Credit for policy is not shown in the Renew Payment page", driver, extentedReport, true);

			renewalPaymentRequiredPage.clickTermsAndCond(extentedReport, true);

			renewalPaymentRequiredPage.clickRenewButton(extentedReport, true);

			BuyPaymentPage buyPaymentPage = new BuyPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					buyPaymentPage.uielement.verifyPageElements(Arrays.asList("txtTitleChosenToPayInInstalment"),
							buyPaymentPage),
					"Renew My Policy Payment page page is opend after clicking Show Quote button",
					"Renew My Policy Payment page is not opend after clicking Show Quote button", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getAmountToPayForRenewal(testData.totalpremiumAmount),
					"Amount to pay for your policy renewal is shown in the Renew Payment page",
					"Amount to pay for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			Log.softAssertThat(buyPaymentPage.getTotalPremiumAmount(testData.amountOfCreditCost),
					"Total Amount for your policy renewal is shown in the Renew Payment page",
					"Total Amount for your policy renewal  is not shown in the Renew Payment page", driver,
					extentedReport, true);

			buyPaymentPage.enterSortCode(testData.sortCode, extentedReport, true);
			buyPaymentPage.enterAccountNumber(testData.accountNumber, extentedReport, true);
			buyPaymentPage.enterAccountHolderName(testData.accountHolderName, extentedReport, true);

			/*buyPaymentPage.clickBuyButton(extentedReport, true);
			RenewalPaymentPage renewalPaymentPage = new RenewalPaymentPage(driver, extentedReport).get();

			Log.softAssertThat(
					renewalPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							renewalPaymentPage),
					"Deposit amount is greaterthan ZERO and navigated to Credit card details page in the Renewal Payment page",
					"Deposit amount is lessthan ZERO and not navigated to Credit card details page in the Renewal Payment page",
					driver, extentedReport, true);

			// Credit card page
			renewalPaymentPage.clickNoButtonContinuousPayments(extentedReport, true);

			Log.softAssertThat(renewalPaymentPage.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Crdit Card is checked",
					"Buy button is not clickable and disabled when Crdit Card is not checked", driver, extentedReport,
					true);
			
			// Entering the another card details
			CardDetailsPage cardDetailsPage = renewalPaymentPage.clickBuyWithOtherCard(extentedReport, true);
			// Switching frame
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("wp-cl-custom-html-iframe");
						
			cardDetailsPage.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport, true);
								
			cardDetailsPage.clickbuy(extentedReport, true);
			
			// Success page
			SuccessPage successPage = new SuccessPage(driver).get();

			Log.softAssertThat(successPage.uielement.verifyPageElements(Arrays.asList("txtLblSuccessMsg"), successPage),
					"Successfully navigated to success page for clicking buy button",
					"Unsuccessfully navigated to success page for clicking buy button", driver, extentedReport, true);

			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),
					"Successfully completed the buy process by clicking buy button",
					"Unsuccessfully completed the buy process by clicking buy button", driver, extentedReport, true);

			successPage.clickFinish(extentedReport, true);

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("txtCustomerName"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page after clicking finish button in Success page",
					"Unsuccessfully navigated to customer dashboard page after clicking finish button in Success page",
					driver, extentedReport, true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
}