package com.pure.MTA.testscripts;

import java.io.IOException;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.generic.support.BaseTest;
import com.generic.support.Log;
import com.generic.support.WebDriverFactory;
import com.generic.utils.DataProviderUtils;
//import com.pure.selfservice.pages.CardDetailsPage;
import com.pure.selfservice.pages.ChangeMyAddressPage;
import com.pure.selfservice.pages.ChangeMyCarPage;
import com.pure.selfservice.pages.ChangeMyCoverTypePage;
import com.pure.selfservice.pages.ChangePermittedDriversPage;
import com.pure.selfservice.pages.ChangeTheClassOfUsePage;
import com.pure.selfservice.pages.CustomerDashboardPage;
import com.pure.selfservice.pages.CustomerServicePage;
import com.pure.selfservice.pages.CustomerSignInPage;
import com.pure.selfservice.pages.GetTestData;
import com.pure.selfservice.pages.MTAInitiatePage;
//import com.pure.selfservice.pages.MTAPaymentPage;
import com.pure.selfservice.pages.MTAQuoteDetailsPage;
//import com.pure.selfservice.pages.SuccessPage;
import com.relevantcodes.extentreports.ExtentTest;

public class MTA_3625 extends BaseTest {
	private String webSite;
	String sheetName = "MTA_3625_";

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

	// ******************* Story Id: 3681 ***************//

	@Test(groups = {
			"Smoke" }, description = "Check that the customer is shown with the Insurance Premium when try to change level of cover on motor Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_003_004_011_023(String browser) throws Exception {

		String tcId = "TC_003_004_011_023";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);

		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			// customerDashboardPage.clickDocumentView(policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.voluntaryExcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);
			Log.message("Premium amount displaying on quote page as "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);

			/*Log.softAssertThat(mTAQuoteDetailsPage.verifyAddoncovers(extentedReport),
					"AddtoPolicy button displaying on quote page", "AddtoPolicy button not displaying on quote page");
			String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);
			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);
			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Add", prevPremium, extentedReport),
					"Premium updated correctly on quote page", "Premium not updated correctly on quote page", driver,
					extentedReport, true);*/

			mTAQuoteDetailsPage.clickCancelButton(extentedReport);
			//mTAQuoteDetailsPage.selectClose(extentedReport);
			mTAQuoteDetailsPage.selectOk(extentedReport);
			Log.message(
					"Note : Added policy addon covers on quote page and selected Cancel button, so need to verify manually in Pure Broker office whether covers got saved to policy or not?");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that by performing 'Change the class of use' amendment and Saving the quote the price in Web UI and pure  should remains same.  [Note : Check by adding and removing Add-ons]", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
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
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);

			Log.message(
					"On Quote page premium displaying as: " + mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true),
					driver, extentedReport, true);

			mTAQuoteDetailsPage.clickCancelButton(extentedReport);
			//mTAQuoteDetailsPage.selectClose(extentedReport);
			mTAQuoteDetailsPage.selectOk(extentedReport);
			
			Log.message(
					"Note: Premium displayed on quote page when change class of use performed, so need to verify premium details in Pure back office");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that by performing \"Change My car\" amendment and Saving the quote the price in Web UI and pure  should remains same and Chck that the Quote Saved with the correct premiums including charges and add-ons while performing \"Change My car\" amendment and Saving the quote. [Note : Check add-ons are able to ADD & REMOVE].  If the CANCEL button clicked on Quote details/payment page add-ons added will not be saved, But it will get SAVED when the customer completes the payment process", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_010_017(String browser) throws Exception {

		String tcId = "TC_010_017";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Car
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Vehicle Registration Number
			ChangeMyCarPage changeMyCarPage = new ChangeMyCarPage(driver, extentedReport).get();
			changeMyCarPage.enterVehicleRegistrationNumber(testData.vehicleRegNo, extentedReport, true);
			changeMyCarPage.clickLookUpButton(extentedReport, false);

			// Selecting Driving method
			changeMyCarPage.selectGeneralInformationDetails(testData.drivingType, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Entering Ownership Information
			changeMyCarPage.enterOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
					testData.purchaseYear, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Entering Vehicle information
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish,testData.deviceDate, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Enter Location Details
			changeMyCarPage.selectOvernightLocationOfVehicle(testData.overnightVehicleLocation, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Click No button
			changeMyCarPage.clickYesOrNoBtnToAddSecurityDevice("No", extentedReport, true);
			// Click Next button
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, false);
			changeMyCarPage.clickQuoteButton(extentedReport, false);
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote page when quote button is selected", driver, extentedReport, false);
			String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);
			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("DefaultCar", prevPremium, extentedReport),
					"Premium updated correctly on quote page after changing my car type amendment",
					"Premium not updated correctly on quote page after changing car type amendment", driver,
					extentedReport, true);
			Log.message(
					"Note : Premium displayed on quote page when change of car performed, so please verify the same premium displaying in Pure back office",extentedReport);
			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that by performing Change Permitted Drivers amendment and Saving the quote the price in Web UI and pure should remains same", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
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
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Click Change permitted driver
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);
			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			// Add additional driver
			changePermittedDriversPage.addAdditionalDrivers(testData.sectionTypeList, testData.title, testData.foreName,
					testData.surname, testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender,
					testData.maritalStatus, testData.relationship, testData.main_occupation, testData.main_business,
					testData.main_empStatus, testData.LicenceDate, testData.LicenceType, testData.qualification,
					testData.LicenceNumber, testData.reason, testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, testData.frequencyOfUse,
					testData.isCommutingPurpose, testData.isIncreasedPremiumApplied, testData.isInsuranceRefused,
					testData.isSpecialTermsApplied, testData.hadCriminalConviction, extentedReport, true);
			changePermittedDriversPage.clickQuoteBtn(extentedReport, true);
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, true);
			String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);
			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("DefaultDriver", prevPremium, extentedReport),
					"Premium updated correctly on quote page when new driver is added",
					"Premium not updated correctly on quote page when new driver is added", driver, extentedReport,
					true);

			Log.message(
					"Note: Premium displayed on quote page when change permitted driver performed, so please verify the same premium displaying in Pure back office to this policy:");
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Chck that the Quote Saved with the correct premiums including charges and add-ons while performing 'Change My Cover Type' amendment and Saving the quote.[Note : Check add-ons are able to ADD & REMOVE]. If the CANCEL button clicked on Quote details add-ons added will not be saved, But it will get SAVED when the customer reaches the BUY Screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
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
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// select change my cover type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChangeValue[0], extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// change cover type
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);
			changeMyCoverTypePage.clickAddAnotherPolicyChangeButton(extentedReport, true);

			// change class of use
			mTAInitiatePage.selectChangeType(testData.typeOfChangeValue[1], extentedReport, true);
			//mTAInitiatePage.clickContinueButton(extentedReport, true);
			mTAInitiatePage.clickAddAnotherPolicyChangeButton(extentedReport, true);
			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);
			changeClassofUse.clickAddAnotherPolicyChangeButton(extentedReport, true);
			mTAInitiatePage.selectChangeType(testData.typeOfChangeValue[2], extentedReport, true);
			mTAInitiatePage.clickAddAnotherPolicyChangeButton(extentedReport, true);
			//mTAInitiatePage.clickContinueButton(extentedReport, true);

			// change my car
			ChangeMyCarPage changeMyCarPage = new ChangeMyCarPage(driver, extentedReport).get();
			changeMyCarPage.enterVehicleRegistrationNumber(testData.vehicleRegNo, extentedReport, true);
			changeMyCarPage.clickLookUpButton(extentedReport, false);

			// Selecting Driving method
			changeMyCarPage.selectGeneralInformationDetails(testData.drivingType, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeListCar[0], extentedReport, true);

			// Entering Ownership Information
			changeMyCarPage.enterOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
					testData.purchaseYear, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeListCar[1], extentedReport, true);

			// Entering Vehicle information
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish, testData.deviceDate, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeListCar[2], extentedReport, true);

			// Enter Location Details
			changeMyCarPage.selectOvernightLocationOfVehicle(testData.overnightVehicleLocation, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeListCar[3], extentedReport, true);

			// Entering security details section
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeListCar[4], extentedReport, true);

			changeMyCarPage.clickAddAnotherPolicyChangeBtn(extentedReport, true);

			mTAInitiatePage.selectChangeType(testData.typeOfChangeValue[3], extentedReport, true);
			//mTAInitiatePage.clickContinueButton(extentedReport, true);
			mTAInitiatePage.clickAddAnotherPolicyChangeButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			// Add additional driver
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);
			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);
			changePermittedDriversPage.addAdditionalDrivers(testData.sectionTypeList, testData.title, testData.foreName,
					testData.surname, testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender,
					testData.maritalStatus, testData.relationship, testData.main_occupation, testData.main_business,
					testData.main_empStatus, testData.LicenceDate, testData.LicenceType, testData.qualification,
					testData.LicenceNumber, testData.reason, testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, testData.frequencyOfUse,
					testData.isCommutingPurpose, testData.isIncreasedPremiumApplied, testData.isInsuranceRefused,
					testData.isSpecialTermsApplied, testData.hadCriminalConviction, extentedReport, true);

			changePermittedDriversPage.clickQuoteBtn(extentedReport, true);

			MTAQuoteDetailsPage mtaquotedetail = new MTAQuoteDetailsPage(driver, extentedReport).get();

			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mtaquotedetail.getPremiumAmt(extentedReport, true), driver, extentedReport);

			// Click Cancel button
			mtaquotedetail.clickCancelButton(extentedReport);
			//mtaquotedetail.selectClose(extentedReport);
			mtaquotedetail.selectOk(extentedReport);
			Log.message("Clicked on Cancel button in quote page to save the quote", driver, extentedReport);

			Log.message("Verification of Quote price detail in Pure back office need to do manually", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the Quote Saved with the correct premiums including charges and add-ons while performing performing Change the class of use amendment and Saving the quote. [Note : Check add-ons are able to ADD & REMOVE]. If the CANCEL button clicked on Quote details add-ons added will not be saved, But it will get SAVED when the customer reaches the BUY Screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
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
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, true);
			Log.message("On Quote page Return premium displaying as : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true));			
			
			/*Log.softAssertThat(mTAQuoteDetailsPage.verifyAddoncovers(extentedReport),
					"AddtoPolicy button displaying on quote page", "AddtoPolicy button not displaying on quote page");
			Log.message("Premium amount displaying on quote page as : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);

			String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);

			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);

			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Add", prevPremium, extentedReport),
					"Premium updated correctly on quote page", "Premium not updated correctly on quote page", driver,
					extentedReport, true);
			prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);
			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Remove", extentedReport, true);
			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Remove", prevPremium, extentedReport),
					"Premium updated correctly on quote page", "Premium not updated correctly on quote page", driver,
					extentedReport, true);*/
			
			mTAQuoteDetailsPage.clickCancelButton(extentedReport);
			//mTAQuoteDetailsPage.selectClose(extentedReport);
			mTAQuoteDetailsPage.selectOk(extentedReport);
			
			/*Log.softAssertThat(customerDashboardPage.getMultipleAddOnsLength() == 1,
					"On clicking Cancel on Quote details add-ons added is not saved and not displayed in Customer dashboard page : "
							+ customerDashboardPage.getMultipleAddOnsLength(),
					"On clicking Cancel on Quote details add-ons added is saved and displayed in Customer dashboard page : "
							+ customerDashboardPage.getMultipleAddOnsLength(),
					driver, extentedReport, true);*/
			Log.message(
					"Note: Premium displayed on quote page when change class of use performed, so need to verify premium details in Pure back office",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Chck that the Quote Saved with the correct premiums including charges and add-ons while performing 'Change Permitted Drivers' amendment and Saving the quote. [Note : Check add-ons are able to ADD & REMOVE]. If the CANCEL button clicked on Quote details add-ons added will not be saved, But it will get SAVED when the customer reaches the BUY Screen.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
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
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change Permitted Drivers
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			// Add additional driver
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);
			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);
			changePermittedDriversPage.addAdditionalDrivers(testData.sectionTypeList, testData.title, testData.foreName,
					testData.surname, testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender,
					testData.maritalStatus, testData.relationship, testData.main_occupation, testData.main_business,
					testData.main_empStatus, testData.LicenceDate, testData.LicenceType, testData.qualification,
					testData.LicenceNumber, testData.reason, testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, testData.frequencyOfUse,
					testData.isCommutingPurpose, testData.isIncreasedPremiumApplied, testData.isInsuranceRefused,
					testData.isSpecialTermsApplied, testData.hadCriminalConviction, extentedReport, true);

			// Click Quote button
			changePermittedDriversPage.clickQuoteBtn(extentedReport, true);
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					"Not navigated to quote button when quote button is selected", driver, extentedReport, false);

			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport);

			/*String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);
			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);
			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Add", prevPremium, extentedReport),
					"Premium updated correctly on quote page", "Premium not updated correctly on quote page", driver,
					extentedReport, true);*/

			// Click cancel button
			mTAQuoteDetailsPage.clickCancelButton(extentedReport);
			//mTAQuoteDetailsPage.selectClose(extentedReport);
			mTAQuoteDetailsPage.selectOk(extentedReport);
			
			Log.message("Quote with values is saved by clicking cancel button in quote page", driver, extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
			"Smoke" }, description = "Check that by performing multiple amendment changes like 'change My car' & 'Change my overnight location' and Saving the quote the price in Web UI and pure  should remains same.  [Note : Check by adding and removing Add-ons]", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
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
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Car
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChangeValue[0], extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Vehicle Registration Number
			ChangeMyCarPage changeMyCarPage = new ChangeMyCarPage(driver, extentedReport).get();
			changeMyCarPage.enterVehicleRegistrationNumber(testData.vehicleRegNo, extentedReport, true);
			changeMyCarPage.clickLookUpButton(extentedReport, false);

			// Selecting Driving method
			changeMyCarPage.selectGeneralInformationDetails(testData.drivingType, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Entering Ownership Information
			changeMyCarPage.enterOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
					testData.purchaseYear, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Entering Vehicle information
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish,testData.deviceDate, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Enter Location Details
			changeMyCarPage.selectOvernightLocationOfVehicle(testData.overnightVehicleLocation, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Entering security details section
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, true);

			changeMyCarPage.clickAddAnotherPolicyChangeBtn(extentedReport, true);

			// Select Change My overnight location
			mTAInitiatePage.selectChangeType(testData.typeOfChangeValue[1], extentedReport, true);
			mTAInitiatePage.clickAddAnotherPolicyChangeButton(extentedReport, true);
			ChangeMyAddressPage changemyaddrpage = new ChangeMyAddressPage(driver, extentedReport).get(); 

			// click change address button
			changemyaddrpage.clickChangeAddressBtn(extentedReport, true);

			// Enter Change Overnight location detail
			changemyaddrpage.enterFindAddressChangeOvernightLocationDetail(testData.postcode, extentedReport, true);

			// Click save button
			changemyaddrpage.clickSaveAddressBtn(extentedReport, true);

			// click quote button
			changemyaddrpage.clickQuoteButton(extentedReport, true);
			MTAQuoteDetailsPage mtaquotedetail = new MTAQuoteDetailsPage(driver, extentedReport).get();
			String prevPremium = mtaquotedetail.getPremiumAmt(extentedReport, true);
			Log.softAssertThat(mtaquotedetail.VerifyPolicyPremium("DefaultCar", prevPremium, extentedReport),
					"Premium updated correctly on quote page after changing Car and Overnightlocation ",
					"Premium not updated correctly on quote page after changing Car and Overnightlocation ", driver,
					extentedReport, true);
			Log.message("The Premium Amount dispalyed before adding addons in the Quote Page : "
					+ mtaquotedetail.getPremiumAmt(extentedReport, true), driver, extentedReport);

			// Click Cancel button
			mtaquotedetail.clickCancelButton(extentedReport);
			//mtaquotedetail.selectClose(extentedReport);
			mtaquotedetail.selectOk(extentedReport);
			Log.message("Clicked on Cancel button in quote page to save the quote", driver, extentedReport);

			Log.message(
					"Need to verify same premium generated in pure back office for the Car and Overnight location address amendment",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Chck that the Quote Saved with the correct premiums including charges and add-ons while performing 'Change My Cover Type' amendment and Saving the quote.[Note : Check add-ons are able to ADD & REMOVE]. If the CANCEL button clicked on Quote details add-ons added will not be saved, But it will get SAVED when the customer reaches the BUY Screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
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
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// select change my cover type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverType, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);

			changeMyCoverTypePage.clickQuoteButton(extentedReport, true);

			MTAQuoteDetailsPage mtaquotedetail = new MTAQuoteDetailsPage(driver, extentedReport).get();

			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mtaquotedetail.getPremiumAmt(extentedReport, true), driver, extentedReport);
			/*String prevPremium = mtaquotedetail.getPremiumAmt(extentedReport, true);
			mtaquotedetail.selectAddOns(testData.addonList, "Add", extentedReport, true);
			Log.softAssertThat(mtaquotedetail.VerifyPolicyPremium("Add", prevPremium, extentedReport),
					"Premium updated correctly on quote page", "Premium not updated correctly on quote page", driver,
					extentedReport, true);*/

			// Click Cancel button
			mtaquotedetail.clickCancelButton(extentedReport);
			//mtaquotedetail.selectClose(extentedReport);
			mtaquotedetail.selectOk(extentedReport);
			Log.message("Clicked on Cancel button in quote page to save the quote", driver, extentedReport);

			Log.message("Verification of Quote price detail in Pure back office need to do manually", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that by performing 'Change my overnight location' amendment and Saving the quote the price in Web UI and pure  should remains same. [Note : Check by adding and removing Add-ons] ", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
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
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My overnight location
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangeMyAddressPage changemyaddrpage = new ChangeMyAddressPage(driver, extentedReport).get();

			// click change address button
			changemyaddrpage.clickChangeAddressBtn(extentedReport, true);

			// Enter Change Overnight location detail
			changemyaddrpage.enterFindAddressChangeOvernightLocationDetail(testData.postcode, extentedReport, true);

			// Click save button
			changemyaddrpage.clickSaveAddressBtn(extentedReport, false);

			// click quote button
			changemyaddrpage.clickQuoteButton(extentedReport, true);
			MTAQuoteDetailsPage mtaquotedetail = new MTAQuoteDetailsPage(driver, extentedReport).get();

			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mtaquotedetail.getPremiumAmt(extentedReport, true), driver, extentedReport);

			// Click Cancel button
			mtaquotedetail.clickCancelButton(extentedReport);
			//mtaquotedetail.selectClose(extentedReport);
			mtaquotedetail.selectOk(extentedReport);
			Log.message("Clicked on Cancel button in quote page to save the quote", driver, extentedReport);

			Log.message("Verification of Quote price detail in Pure back office need to do manually", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "'Chck that the Quote Saved with the correct premiums including charges and add-ons while performing Change my overnight location amendment and Saving the quote.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
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
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My overnight location
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyAddressPage changemyaddrpage = new ChangeMyAddressPage(driver, extentedReport).get();

			// click change address button
			changemyaddrpage.clickChangeAddressBtn(extentedReport, true);

			// Enter Change Overnight location detail
			changemyaddrpage.enterFindAddressChangeOvernightLocationDetail(testData.postcode, extentedReport, true);

			// Click save button
			changemyaddrpage.clickSaveAddressBtn(extentedReport, false);

			// click quote button
			changemyaddrpage.clickQuoteButton(extentedReport, true);
			MTAQuoteDetailsPage mtaquotedetail = new MTAQuoteDetailsPage(driver, extentedReport).get();
			String prevPremium = mtaquotedetail.getPremiumAmt(extentedReport, true);
			Log.softAssertThat(mtaquotedetail.VerifyPolicyPremium("Default", prevPremium, extentedReport),
					"Premium updated correctly on quote page after  Overnightlocation got changed ",
					"Premium not updated correctly on quote page after Overnightlocation got changed ", driver,
					extentedReport, true);
			Log.message("The Premium Amount dispalyed before adding addons in the Quote Page : "
					+ mtaquotedetail.getPremiumAmt(extentedReport, true), driver, extentedReport);

			// Add Addons
			/*mtaquotedetail.selectAddOns(testData.addonList, "Add", extentedReport, true);
			Log.softAssertThat(mtaquotedetail.VerifyPolicyPremium("Add", prevPremium, extentedReport),
					"Premium updated correctly on quote page after changing cover type",
					"Premium not updated correctly on quote page after changing cover type", driver, extentedReport,
					true);*/

			// Click Cancel button
			mtaquotedetail.clickCancelButton(extentedReport);
			//mtaquotedetail.selectClose(extentedReport);
			mtaquotedetail.selectOk(extentedReport);
			Log.message("Clicked on Cancel button in quote page to save the quote", driver, extentedReport);

			Log.message("Verification of Quote price detail in Pure back office need to do manually", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Chck that the Quote Saved with the correct premiums including charges and add-ons while performing multiple amendment changes like  'change My car' & 'Change my overnight location' and Saving the quote. [Note : Check add-ons are able to ADD & REMOVE]. If the CANCEL button clicked on Quote details add-ons added will not be saved, But it will get SAVED when the customer reaches the BUY Screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
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
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Car
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChangeValue[0], extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Enter Vehicle Registration Number
			ChangeMyCarPage changeMyCarPage = new ChangeMyCarPage(driver, extentedReport).get();
			changeMyCarPage.enterVehicleRegistrationNumber(testData.vehicleRegNo, extentedReport, true);
			changeMyCarPage.clickLookUpButton(extentedReport, false);

			// Selecting Driving method
			changeMyCarPage.selectGeneralInformationDetails(testData.drivingType, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Entering Ownership Information
			changeMyCarPage.enterOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
					testData.purchaseYear, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Entering Vehicle information
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish, testData.deviceDate,extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Enter Location Details
			changeMyCarPage.selectOvernightLocationOfVehicle(testData.overnightVehicleLocation, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Entering security details section
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, true);

			changeMyCarPage.clickAddAnotherPolicyChangeBtn(extentedReport, true);

			// Select Change My overnight location
			mTAInitiatePage.selectChangeType(testData.typeOfChangeValue[1], extentedReport, true);
			mTAInitiatePage.clickAddAnotherPolicyChangeButton(extentedReport, true);

			ChangeMyAddressPage changemyaddrpage = new ChangeMyAddressPage(driver, extentedReport).get();

			// click change address button
			changemyaddrpage.clickChangeAddressBtn(extentedReport, true);

			// Enter Change Overnight location detail
			changemyaddrpage.enterFindAddressChangeOvernightLocationDetail(testData.postcode, extentedReport, true);

			// Click save button
			changemyaddrpage.clickSaveAddressBtn(extentedReport, false);

			// click quote button
			changemyaddrpage.clickQuoteButton(extentedReport, true);
			MTAQuoteDetailsPage mtaquotedetail = new MTAQuoteDetailsPage(driver, extentedReport).get();

			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mtaquotedetail.getPremiumAmt(extentedReport, true), driver, extentedReport);
			String prevPremium = mtaquotedetail.getPremiumAmt(extentedReport, true);
			Log.softAssertThat(mtaquotedetail.VerifyPolicyPremium("Default", prevPremium, extentedReport),
					"Premium generated on quote page", "Premium not generated on quote page", driver,
					extentedReport, true);
			// Click Cancel button
			mtaquotedetail.clickCancelButton(extentedReport);
			Log.message("Clicked on Cancel button in quote page to save the quote", driver, extentedReport);

			Log.message("Verification of Quote price detail in Pure back office need to be performed manually", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// ******************* Story Id: 3682 ***************//

	@Test(description = "Check that the customer is shown with the Insurance Premium when try to change level of cover on motor Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_034_035(String browser) throws Exception {

		String tcId = "TC_034_035";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.voluntaryExcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);
			Log.message("Premium amount displaying on quote page as "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);

			Log.softAssertThat(mTAQuoteDetailsPage.verifyPolicyExcessColumnLabels(extentedReport, true),
					"Policy excess column labels are displaying as expected on your quote page",
					"Policy excess column labels are not displaying as expected on your quote page");
			Log.softAssertThat(mTAQuoteDetailsPage.verifyPolicyExcessRowLabels(extentedReport, true),
					"Policy excess Row labels are displaying as expected on your quote page",
					"Policy excess Row labels are not displaying as expected on your quote page");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the customer is shown with the Insurance Premium when try to change level of cover on motor Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_038_041(String browser) throws Exception {

		String tcId = "TC_038_041";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.voluntaryExcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);
			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			Log.softAssertThat(mTAQuoteDetailsPage.verifyBuyButtonClickable(extentedReport),
					"Buy button is clickable and enabled when Terms and Conditions is checked",
					"Buy button is not clickable and disabled when Terms and Conditions is checked");
			mTAQuoteDetailsPage.clickBuyButton(extentedReport, true);
			Log.softAssertThat(mTAQuoteDetailsPage.verifyBuyPageBannerTitle(extentedReport),
					"Successfully navigated to payment page when buy button selected",
					"Unable to navigate to payment page when buy button selected", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the customer is shown with the Insurance Premium when try to change level of cover on motor Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_042(String browser) throws Exception {

		String tcId = "TC_042";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.voluntaryExcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);

			mTAQuoteDetailsPage.clickCancelButton(extentedReport);
			//mTAQuoteDetailsPage.selectClose(extentedReport);
			mTAQuoteDetailsPage.selectOk(extentedReport);
			
			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("btnManagePreferences"),
							customerDashboardPage),
					"Successfully navigated to customer dashboard page",
					"Unable to navigate to customer dashbaord page ", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "check that pricing information for the Quote is displayed correctly in Quote details page.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_044(String browser) throws Exception {

		String tcId = "TC_044";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.voluntaryExcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);
			Log.message("Premium amount displaying on quote page as "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);

			String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);
			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Default", prevPremium, extentedReport),
					"Premium updated correctly on quote page", "Premium not updated correctly on quote page", driver,
					extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that with the customer logged in having the option to add Broker add on and Insurer addon display in Quote details page.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_045_046(String browser) throws Exception {

		String tcId = "TC_045_046";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.voluntaryExcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);
			Log.message("Premium amount displaying on quote page as "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);

			/*Log.softAssertThat(mTAQuoteDetailsPage.verifyAddoncovers(extentedReport),
					"AddtoPolicy button displaying on quote page to add addon covers",
					"AddtoPolicy button not displaying on quote page to add addon covers");*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that with the customer logged in having the option to add Broker add on and Insurer addon display in Quote details page.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_047(String browser) throws Exception {

		String tcId = "TC_047";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.voluntaryExcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, true);
			Log.message("Premium amount displaying on quote page as "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);
			Log.softAssertThat(
					!mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("btnRemove"), mTAQuoteDetailsPage),
					"NoAddon covers displaying as added on quote details page: " + testData.typeOfChange,
					"Addon covers displaying as added on quote details page : " + testData.typeOfChange, driver,
					extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that for return premium value the Caption should be displayed as Return Premium. [i.e. while adding the cover and premium is in negative value.] & also based on the payment type.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_033_052_053(String browser) throws Exception {

		String tcId = "TC_033_052_053";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);
			//Log.softAssertThat(!mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true).contains("0.00"), "Premium caption is displayed as Return Premium. [i.e. while adding the cover and premium is in negative value.]", "Premium caption is displayed as Return Premium. [i.e. while adding the cover and premium is in negative value.]",driver,extentedReport);
			/*String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);
			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);

			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Add", prevPremium, extentedReport),
					"Premium updated correctly on quote page when addon covers added ",
					"Premium not updated correctly on quote page when addon covers added", driver, extentedReport,
					true);*/

			Log.message(
					"Note: Premium displayed on quote page when change class of use performed, so need to verify premium details in Pure back office");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that Pay By Card option should available when the customer is having Additional Premium and originally paid using Card and the details should updated / refreshed based on the changes made", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_055(String browser) throws Exception {

		String tcId = "TC_055";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);

		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, true);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);
			/*String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);

			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);

			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Add", prevPremium, extentedReport),
					"Premium updated correctly on quote page when addon covers added ",
					"Premium not updated correctly on quote page when addon covers added", driver, extentedReport,
					true);

			mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"), mTAPaymentPage),
					"Buy with Another card option displayed when policy having additional premium : "
							+ testData.typeOfChange,
					"Buy with Another card option not when policy having addtional premium : " + testData.typeOfChange,
					driver, extentedReport);
			mTAPaymentPage.clickBuyWithOtherCard(extentedReport, false);
			CardDetailsPage cardDetails = mTAPaymentPage.selectVisacard(extentedReport);
			cardDetails.enterCardDetails(testData.year, testData.cardHolderName, testData.cardNumber, extentedReport,
					true);
			Log.softAssertThat(
					cardDetails.uielement.verifyPageElements(Arrays.asList("btnBuy"), cardDetails),
					"Pay By Card option is available when the customer is having Additional Premium and originally paid using Card and the details should updated / refreshed based on the changes made",
					"Pay By Card option is not available when the customer is having Additional Premium and originally paid using Card and the details should updated / refreshed based on the changes made",
					driver, extentedReport);
			cardDetails.clickbuy(extentedReport, true);
			SuccessPage success = cardDetails.clickContinueButton(extentedReport);
			success.getSuccessMsg(extentedReport, true);
			SuccessPage successPage = new SuccessPage(driver).get();
			successPage.getSuccessMsg(extentedReport, true);
			Log.softAssertThat(successPage.getSuccessMsg(extentedReport, true),"Success Page is dispalyed", "Success page is not displayed", driver, extentedReport);*/
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that Payment Required section should display with 'Payment due' title with message You originally paid for your policy via a one-off payment, So your method of payment will be same for the policy update. Payment will be taken on the next page & Amount to pay ", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_056(String browser) throws Exception {

		String tcId = "TC_056";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);

		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, true);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"On Clicking QUOTE button redirected to Quote page. [After Saving and clicking on Add Another policy change button in Data capture page] and Premium is generated : "
							+ mTAQuoteDetailsPage.getRefundPremiumAmt(),
					"On Clicking  QUOTE button on success redirected to Quote page but premium not generated : "
							+ mTAQuoteDetailsPage.getRefundPremiumAmt(),
					driver, extentedReport);
			/*String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);

			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);

			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Add", prevPremium, extentedReport),
					"Premium updated correctly on quote page when addon covers added ",
					"Premium not updated correctly on quote page when addon covers added", driver, extentedReport,
					true);

			Log.softAssertThat(mTAQuoteDetailsPage.verifyPaymentRequiredSection(extentedReport, true),
					"Payment required section verified successfully on quote page",
					"Payment required section not verified successfully on quote page");*/
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that Pay By Card option should available when the customer is having Additional Premium and originally paid using Card and the details should updated / refreshed based on the changes made", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_057(String browser) throws Exception {

		String tcId = "TC_057";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);

		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, true);

			// click Buy button
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.message("The Premium Amount dispalyed in the Quote Page : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);
			/*String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);

			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);

			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Add", prevPremium, extentedReport),
					"Premium updated correctly on quote page when addon covers added ",
					"Premium not updated correctly on quote page when addon covers added", driver, extentedReport,
					true);*/

			/*mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("btnBuyWithAnotherCard"),
							mTAPaymentPage),
					"Buy with card and premium finance displayed on payment page " + testData.typeOfChange,
					"Buy with card and premium finance not displayed on payment page : " + testData.typeOfChange, driver,
					extentedReport);*/
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that Broker refund option should display when the customer is having return premium and originally paid using Card Payment and the details should updated / refreshed based on changes made and MTA should be in LIVE status", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_060(String browser) throws Exception {

		String tcId = "TC_060";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);
			Log.message("Premium amount displaying on quote page as "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);
			Log.message("On Quote page Return premium displaying as : "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true));
			
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"On Clicking QUOTE button redirected to Quote page. [After Saving and clicking on Add Another policy change button in Data capture page] and Premium is generated : "
							+ mTAQuoteDetailsPage.getRefundPremiumAmt(),
					"On Clicking  QUOTE button on success redirected to Quote page but premium not generated : "
							+ mTAQuoteDetailsPage.getRefundPremiumAmt(),
					driver, extentedReport);

			/*mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAPaymentPage.uielement.verifyPageElements(Arrays.asList("lblRefundAmount"), mTAPaymentPage),
					"Refund Amount is displayed through Card : "+mTAPaymentPage.getRefundAmount(),						
					"Refund Amount is not displayed through card : "+mTAPaymentPage.getRefundAmount(),
					driver, extentedReport);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
	"Smoke" },description = "Check that Refund by finance plan section should display when the customer is having return premium and originally paid using finance and the details should update / Refreshed based on the changes made and MTA should be in LIVE status..", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_062(String browser) throws Exception {

		String tcId = "TC_062";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeClassofUse = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeClassofUse.selectClassOfUse(testData.nameofClass, extentedReport, true);
			changeClassofUse.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeClassofUse.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);
			
			String additionalPremium = mTAQuoteDetailsPage.getAdditionalPremiumAmt();
			String[] amtToPay = mTAQuoteDetailsPage.getAmtToPayAmt().split("Total");
			String amtToPayAmt = amtToPay[0].substring(14);
			Log.message("Additional Premium : "+additionalPremium+ " and Amount To Pay : "+amtToPayAmt,extentedReport);
			
			/*mTAQuoteDetailsPage.clickTermsandCond(extentedReport, true);
			mTAQuoteDetailsPage.clickBuyButton(extentedReport);

			MTAPaymentPage mTAPaymentPage = new MTAPaymentPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAPaymentPage.verifyPremiumFinanceTitleAndAdditionalAmt() && additionalPremium.contains(mTAPaymentPage.getAdditionalPremiumAmountByCard()) && amtToPayAmt.contains(mTAPaymentPage.getAdditionalAmtToBePaid()) ,
					"Finance Premium amount to be paid by card is : "+mTAPaymentPage.getAdditionalPremiumAmountByCard()+ " and Amount to be paid by Premium Finance : "+mTAPaymentPage.getAdditionalAmtToBePaid()+" are same in Quote and Payment page",						
					"Finance Premium amount to be paid by card is not displayed : "+mTAPaymentPage.getAdditionalPremiumAmountByCard()+ " and Amount to be paid by Premium Finance not displayed as : "+mTAPaymentPage.getAdditionalAmtToBePaid()+" are not same between Quote and Payment page",
					driver, extentedReport);
			*/

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.testCaseResult(extentedReport);
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
	
	// ******************* Story Id: 3683 ***************//

	@Test(description = "Check that the logged in customer (with no add-ons) during NB  should get displayed without any available Add-ons in Quote details page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_067(String browser) throws Exception {

		String tcId = "TC_067";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton("1", extentedReport, true);

			// Select Change My address
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.voluntaryExcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			//MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();

			/*Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtAlreadyAddedFeatureMsg"),
							mTAQuoteDetailsPage),
					"Added addon cover section displaying on quote page: " + testData.typeOfChange,
					"Added addon cover section not displaying on quote page : " + testData.typeOfChange, driver,
					extentedReport);

			mTAQuoteDetailsPage.verifyAddOns(extentedReport, true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the logged in customer (with no add-ons) should get displayed without any available Add-ons in Quote details page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_068(String browser) throws Exception {

		String tcId = "TC_068";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.voluntaryExcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);

			Log.softAssertThat(
					!mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("btnRemove"), mTAQuoteDetailsPage),
					"NoAddon covers displaying as added on quote details page: " + testData.typeOfChange,
					"Addon covers displaying as added on quote details page : " + testData.typeOfChange, driver,
					extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
			"Smoke" }, description = "Check that add-ons suggested should be added and Payment Required section on the top of the screen should updated accordingly", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_069(String browser) throws Exception {

		String tcId = "TC_069";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.voluntaryExcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);

			/*Log.softAssertThat(mTAQuoteDetailsPage.verifyAddoncovers(extentedReport),
					"AddtoPolicy button displaying on quote page", "AddtoPolicy button not displaying on quote page");
			String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);

			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);
			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Add", prevPremium, extentedReport),
					"Premium updated correctly on quote page when addon covers added ",
					"Premium not updated correctly on quote page when addon covers added", driver, extentedReport,
					true);*/

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that added add-ons should reflect back to pure while SAVE and user should get alert message that tell them that the change is not processed when the payment is not done.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_072_074(String browser) throws Exception {

		String tcId = "TC_072_074";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.voluntaryExcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);

			/*Log.softAssertThat(mTAQuoteDetailsPage.verifyAddoncovers(extentedReport),
					"AddtoPolicy button displaying on quote page", "AddtoPolicy button not displaying on quote page");

			String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);
			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);

			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Add", prevPremium, extentedReport),
					"Premium updated correctly on quote page when addon covers added ",
					"Premium not updated correctly on quote page when addon covers added", driver, extentedReport,
					true);*/

			mTAQuoteDetailsPage.clickCancelButton(extentedReport);
			//mTAQuoteDetailsPage.selectClose(extentedReport);
			mTAQuoteDetailsPage.selectOk(extentedReport);
			Log.message(
					"Note: Premium displayed on quote page when change cover type performed, so need to verify premium details in Pure back office :");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that suggested add-ons already added should be Removed and Payment Required section on the top of the screen should updated accordingly.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_070(String browser) throws Exception {

		String tcId = "TC_070";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.voluntaryExcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);

			Log.message("Premium amount displaying on quote page as "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);

			/*Log.softAssertThat(mTAQuoteDetailsPage.verifyAddoncovers(extentedReport),
					"AddtoPolicy button displaying on quote page", "AddtoPolicy button not displaying on quote page");

			String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);

			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);

			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Add", prevPremium, extentedReport),
					"Premium updated correctly on quote page when addon covers added ",
					"Premium not updated correctly on quote page when addon covers added", driver, extentedReport,
					true);
			prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);

			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Remove", extentedReport, true);
			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Remove", prevPremium, extentedReport),
					"Premium updated correctly on quote page when addon covers removed",
					"Premium not updated correctly on quote page when addon covers are removed", driver, extentedReport,
					true);*/
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// ******************* Story Id: 3684 ***************//
	@Test(groups = {
			"Smoke" }, description = "Check that the quote details page is re-conditioned based on increase in premium and decrease in premium", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_076(String browser) throws Exception {

		String tcId = "TC_076";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		Log.testCaseInfo(testData.description + "<small><b><i>[" + browser + "]</b></i></small>");

		ExtentTest extentedReport = addTestInfo(tcId, testData.description);
		try {

			// Navigate to Login Page
			CustomerServicePage selfServicePage = new CustomerServicePage(driver, webSite, extentedReport).get();
			Log.message("Customer Self service Landing Page : " + webSite, driver, extentedReport, true);

			CustomerSignInPage customerSignInPage = selfServicePage.clickSignInButton(extentedReport, true);
			customerSignInPage.loginToSSCustomerDashboard(testData.emailAddress, testData.password, extentedReport,
					true);
			CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select Change My Cover Type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// Click Quote Button
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.voluntaryExcess, extentedReport, true);
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);

			Log.message("Premium amount displaying on quote page as "
					+ mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true), driver, extentedReport, true);

			/*String prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);

			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Add", extentedReport, true);

			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Add", prevPremium, extentedReport),
					"Premium updated correctly on quote page", "Premium not updated correctly on quote page", driver,
					extentedReport, true);
			prevPremium = mTAQuoteDetailsPage.getPremiumAmt(extentedReport, true);
			mTAQuoteDetailsPage.selectAddOns(testData.addonList, "Remove", extentedReport, true);
			Log.softAssertThat(mTAQuoteDetailsPage.VerifyPolicyPremium("Remove", prevPremium, extentedReport),
					"Premium updated correctly on quote page", "Premium not updated correctly on quote page", driver,
					extentedReport, true);*/
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
}
