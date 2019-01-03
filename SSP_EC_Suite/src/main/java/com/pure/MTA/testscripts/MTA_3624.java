package com.pure.MTA.testscripts;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.generic.support.BaseTest;
import com.generic.support.Log;
import com.generic.support.WebDriverFactory;
import com.generic.utils.DataProviderUtils;
import com.generic.utils.GenericUtils;
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
import com.pure.selfservice.pages.MTAQuoteDetailsPage;
import com.relevantcodes.extentreports.ExtentTest;

public class MTA_3624 extends BaseTest {
	private String webSite;
	String sheetName = "MTA_3624_";

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

	// ******************* Story Id: 3673 ***************//

	@Test(description = "Check that by providing value of  Vehicle registration of your new car field which doesn't exist and click on Lookup button should alert with error message as 'Sorry, we haven't been able to retrieve your vehicle details, please check you've entered the registration number correctly'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
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

			// Verify Error message
			
			Log.softAssertThat(changeMyCarPage.verifyInvalidVehicleRegNumErrorMsg(changeMyCarPage.INVALID_CAR_REG_NUM_ERROR_MSG, extentedReport, true),
					testData.invalidErrorMsg
							+ " : Error message is displayed for Vehicle registration number of your new car which doesn't exist",
					testData.invalidErrorMsg
							+ " : Error message is not displayed for Vehicle registration number of your new car which doesn't exist",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that 'Vehicle registration of your new car' field is mandatory and should display with proper alert", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
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
			changeMyCarPage.clickLookUpButton(extentedReport, false);

			// Verify mandatory alert
			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("iconRegistrationNumMandatory"),
							changeMyCarPage),
					"'Vehicle registration of your new car' field is mandatory and display with proper alert",
					"Mandatory alert for 'Vehicle registration of your new car' field is not dispalyed", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that If the lookup is successful a text description of the car will be displayed under the Vehicle Registration question with header 'Your Car - XXXX' and display the information", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
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

			// Verify message
			String carNameMsg = "Your Car - " + testData.vehicleRegNo;
			Log.softAssertThat(changeMyCarPage.verifyCarNumberHeader(carNameMsg, extentedReport, false),
					carNameMsg + " : Car Registration number is displayed in the header of lookup success message",
					carNameMsg + " : Car Registration number is not displayed in the header of lookup success message",
					driver, extentedReport);

			Log.softAssertThat(changeMyCarPage.verifyLookupSuccessMsg(extentedReport, false),
					testData.SuccessMsg + " : message is displayed as lookup success message",
					testData.SuccessMsg + " : message is not displayed as lookup success message", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on Next button in General information will Collapse the General Information section and should show the next section of Data Capture Ownership information and Check that clicking on Next button in Ownership Information will Collapse the Ownership Information section and should show the next section of Data Capture Vehicle Details and Check that 'Does the vehicle have a registered Q plate?' field should contains YES & NO button and default NO button is selected, Once selected button should get changed to green colour with the tick mark and other button should be greyed out", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_012_022_030(String browser) throws Exception {

		String tcId = "TC_012_022_030";
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

			Log.softAssertThat(
					!changeMyCarPage.uielement.verifyPageElements(Arrays.asList("drpSelectDrivingMethod"),
							changeMyCarPage),
					"General Information section got collapse On clicking Next button in General information",
					"General Information section did not get collapse On clicking Next button in General information",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("drpSelectKeeperOfVehicle"),
							changeMyCarPage),
					"Ownership information Data Capturesection is displayed after clicking next button",
					"Ownership information Data Capture section is not displayed after clicking next button", driver,
					extentedReport);

			// Entering Ownership Information
			changeMyCarPage.enterOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
					testData.purchaseYear, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			Log.softAssertThat(
					!changeMyCarPage.uielement.verifyPageElements(Arrays.asList("drpSelectKeeperOfVehicle"),
							changeMyCarPage),
					"Ownership Information section got collapse On clicking Next button in ownership information",
					"Ownership Information section did not get collapse On clicking Next button in ownership information",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("fldValueOfVehicle"), changeMyCarPage),
					"Vehicle Details Data Capturesection is displayed after clicking next button",
					"Vehicle Details Data Capture section is not displayed after clicking next button", driver,
					extentedReport);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(
							Arrays.asList("lblQplate", "btnYesQPlate", "btnNoQPlate"), changeMyCarPage),
					"'Does the vehicle have a registered Q plate?' field is dispalyed and it contains YES & NO button",
					"'Does the vehicle have a registered Q plate?' field is not dispalyed and it does not contains YES & NO button",
					driver, extentedReport);

			// Verify No button selected
			Log.softAssertThat(changeMyCarPage.verifySelectedYesOrNobtnForQPlate("No", extentedReport, false),
					"'No' button is selected by default", "'No' button is not selected by default", driver,
					extentedReport);

			// Clicked on yes button
			changeMyCarPage.clickYesOrNoBtnForQplate("Yes", extentedReport, true);

			Log.softAssertThat( changeMyCarPage.verifyGreenBackgroundColorOfYesOrNoBtnOfQPlate("Yes", extentedReport, false),
					"After selecting 'Yes' button, it got changed to green colour",
					"After selecting 'Yes' button, it did not change to green colour", driver, extentedReport);

			// Verify No button selected
			Log.softAssertThat(changeMyCarPage.verifySelectedYesOrNobtnForQPlate("Yes", extentedReport, false),
					"Yes button is selected by with the tick mark", "Yes button is not selected by with the tick mark",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyCarPage.verifyGreyBackgroundColorOfYesOrNoBtnOfQPlate("No", extentedReport, false),
					"After selecting 'Yes' button, 'No' button got changed to grey colour",
					"After selecting 'Yes' button, 'No' button did not changed to grey colour", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on Next button in  Vehicle Details will Collapse the  Vehicle Details section and should show the next section of Data Capture Location Details and Check that clicking on NO button should display the fields 'Postcode of overnight address' & 'Overnight location of the vehicle' fields.selected button should get changed to green colour with the tick mark and other button should be greyed out. Configurable messages should not get displayed while clicking on NO button", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_031_036(String browser) throws Exception {

		String tcId = "TC_031_036";
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

			// Enter Vehicle Details
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish,testData.deviceDate, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			Log.softAssertThat(
					!changeMyCarPage.uielement.verifyPageElements(Arrays.asList("drpSelectPaintworkColor"),
							changeMyCarPage),
					"Vehicle Details section got collapse On clicking Next button in General information",
					"Vehicle Details section did not get collapse On clicking Next button in General information",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("drpSelectOvernightVehicleLocation"),
							changeMyCarPage),
					"Location Details Data Capturesection is displayed after clicking next button",
					"Location Details Data Capture section is not displayed after clicking next button", driver,
					extentedReport);

			// Click NO button
			changeMyCarPage.clickYesOrNoBtnForVehicleKeptInCurrentLocation("No", extentedReport, false);

			Log.softAssertThat(changeMyCarPage.uielement.verifyPageElements(
					Arrays.asList("fldOvernightPostcode", "drpSelectOvernightVehicleLocation"), changeMyCarPage),
					"On clicking NO button displays the fields 'Postcode of overnight address' & 'Overnight location of the vehicle'",
					"On clicking NO button did not display the fields 'Postcode of overnight address' & 'Overnight location of the vehicle'",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyCarPage.verifyGreenBackgroundColorOfYesOrNoBtnOfQPlate("No", extentedReport, false),
					"After selecting 'NO' button, it got changed to green colour",
					"After selecting 'NO' button, it did not change to green colour", driver, extentedReport);

			// Verify No button selected
			Log.softAssertThat(changeMyCarPage.verifyYesOrNoSelectedbtnInLocationDetail("No", extentedReport, false),
					"NO button is selected with the tick mark and Configurable messages is not displayed",
					"NO button is not selected with the tick mark and Configurable messages is not displayed", driver,
					extentedReport);

			Log.softAssertThat(
					changeMyCarPage.verifyGreenBackgroundColorOfYesOrNoBtnOfVehicleKeptInCurrentLocation("Yes",
							extentedReport, false),
					"After selecting 'NO' button, YES got changed to greycolour",
					"After selecting 'NO' button, YES did not change to grey colour", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that 'Postcode of overnight address' field should contains text box along with 'Lookup' button and should retrieve address based on post code and validation should be proper in case of invalid post code lookup.Help tool tip should get displayed for field 'Postcode of overnight address' and Check that 'Please choose the overnight address from the list' field should get listed with address based on searched postcode in 'Postcode of overnight address' field.Help tool tip should get displayed for field 'Please choose the overnight address from the list' and Check that Selecting an overnight address from list will show address in textbox format with label display 'Your chosen address details'.Help tool tip should get displayed for field 'Your chosen address details' and Check that 'Overnight location of the vehicle' field should get displayed with dropdown select option and also should display with Help tool tip icon when clicking on NO button", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_037_038_039_040(String browser) throws Exception {

		String tcId = "TC_037_038_039_040";
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

			// Enter Vehicle Details
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish,testData.deviceDate, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Click NO button
			changeMyCarPage.clickYesOrNoBtnForVehicleKeptInCurrentLocation("No", extentedReport, false);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(
							Arrays.asList("fldOvernightPostcode", "btnFindAddress"), changeMyCarPage),
					"'Postcode of overnight address' field contains the text box along with the 'Lookup' button",
					"'Postcode of overnight address' field does not contains the text box along with the 'Lookup' button",
					driver, extentedReport);

			// Enter invalid postcode
			String invalidPostcode = GenericUtils.getRandomCharacters("ALPHANUMERIC", 3);
			changeMyCarPage.enterOvernightPostcode(invalidPostcode, extentedReport, true);
			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("txtInvalidvernightPostcodeErrorMsg"),
							changeMyCarPage),
					"Validation is done properly in case of invalid post code lookup - Error message is displayed",
					"Validation is done properly in case of invalid post code lookup - Error message is not displayed",
					driver, extentedReport);

			// Enter Overnight Postcode
			changeMyCarPage.enterOvernightPostcode(testData.overnightPostcode, extentedReport, true);
			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("drpSelectOvernightVehicleLocation"),
							changeMyCarPage),
					"Address based on the post code is retrieved successfully",
					"Address based on the post code is not retrieved", driver, extentedReport);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("iconHelpOvernightPostcode"),
							changeMyCarPage),
					"Help tool tip is displayed for the field 'Postcode of overnight address'",
					"Help tool tip is not displayed for the field 'Postcode of overnight address'", driver,
					extentedReport);

			changeMyCarPage.selectOvernightAddress();

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("drpSelectOvernightVehicleLocation"),
							changeMyCarPage),
					"'Overnight location of the vehicle' field is displayed with the drop down select option and also display with the Help tool tip icon when clicking on NO button",
					"'Overnight location of the vehicle' field is not displayed with the drop down select option and also not display with the Help tool tip icon when clicking on NO button",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on Next button in Location Details will Collapse Location Details section and should show next section of Data Capture Security Devices and Check that after entering values and clicking on Save Device' button should add details and should get displayed along with EDIT & DELETE button and Check that clicking on EDIT button should display form with already saved information and modifying and saving edited value after clicking '+Save Device' button should Save newly modified values and Check that clicking on DELETE button should delete corresponding security device which is already saved and Check that after entering values and clicking on 'Save Device' button should add details and should allow user to add another device by displaying option 'Are there any other non-factory fitted security devices added to the vehicle?' with YES and NO button displayed and should contain the help tool tip", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_041_052_054_055_056(String browser) throws Exception {

		String tcId = "TC_041_052_054_055_056";
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

			// Enter Vehicle Details
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish,testData.deviceDate, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Enter Location Details
			changeMyCarPage.selectOvernightLocationOfVehicle(testData.overnightVehicleLocation, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			Log.softAssertThat(
					!changeMyCarPage.uielement.verifyPageElements(Arrays.asList("drpSelectOvernightVehicleLocation"),
							changeMyCarPage),
					"Location Details section got Collapsed On Clicking Next button in Location Details",
					"Location Details section did not Collapse On Clicking Next button in Location Details", driver,
					extentedReport);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("lblAddSecurityDevice"),
							changeMyCarPage),
					"Security Details Data Capture section is displayed after clicking next button",
					"Security Details Data Capture section is not displayed after clicking next button", driver,
					extentedReport);

			// Add Security device
			changeMyCarPage.clickYesOrNoBtnToAddSecurityDevice("Yes", extentedReport, true);
			changeMyCarPage.addSecurityDevice(testData.makeOfDevice, testData.deviceModal, testData.deviceDate,
					testData.deviceInstaller, extentedReport, true);

			Log.softAssertThat(
					changeMyCarPage.verifySecurityDeviceDetail(testData.makeOfDevice, testData.deviceModal,
							testData.deviceDate, testData.deviceInstaller, extentedReport, true),
					testData.makeOfDevice + " - Added Security Device is displayed with Edit and Delete button",
					testData.makeOfDevice + " - Added Security Device is not displayed with Edit and Delete button",
					driver, extentedReport);
			changeMyCarPage.clickSaveDeviceBtn(extentedReport, true);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("lblAnotherSecurityDeviceToAdd",
							"btnYesAnotherSecurityDeviceToAdd", "btnNoAnotherSecurityDeviceToAdd"), changeMyCarPage),
					"'Are there any other non-factory fitted security devices added to the vehicle?' with YES & NO button is displayed after adding Security Device, so user can add another security device",
					"'Are there any other non-factory fitted security devices added to the vehicle?' with YES & NO button is not displayed after adding Security Device, so user can not add another security device",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("iconHelpAddSecurityDevice"),
							changeMyCarPage),
					"Help tool tip icon is displayed", "Help tool tip icon is not displayed", driver, extentedReport);

			Log.softAssertThat(
					changeMyCarPage.verifySecurityDeviceDetail(testData.makeOfDevice, testData.deviceModal,
							testData.deviceDate, testData.deviceInstaller, extentedReport, true),
					"On clicking EDIT button display the form with already saved information",
					"On clicking EDIT button display the form with already saved information", driver, extentedReport);

			// modify security device detail
			String editedModel = "AA Protector";
			changeMyCarPage.selectMakeOfInstalledSecurityDevice(editedModel, extentedReport, false);
			changeMyCarPage.clickSaveDeviceBtn(extentedReport, true);

			Log.softAssertThat(
					changeMyCarPage.getSecurityDeviceRow(testData.makeOfDevice, editedModel, extentedReport,
							true) != null,
					editedModel + " - Edited Security Device detail is displayed ",
					editedModel + " - Edited Security Device detail is not displayed", driver, extentedReport);

			// Delete security device
			changeMyCarPage.clickSecurityDeviceDeleteBtn(testData.makeOfDevice, editedModel, extentedReport, false);

			Log.softAssertThat(
					changeMyCarPage.getSecurityDeviceRow(testData.makeOfDevice, editedModel, extentedReport,
							true) == null,
					editedModel + " - Security device is deleted succesfully ",
					editedModel + " - Security device is not deleted", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on 'NO' button should not display data capture for security device and only NO button should get selected and YES button should be greyed out and Check that selecting 'YES' button should display  Security Devices question then data capture will be shown for a new device and Check that clicking on Next button in Security Devices will Collapse Security Devices section and should show next section of Data Capture Modification Details", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_057_058_063(String browser) throws Exception {

		String tcId = "TC_057_058_063";
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

			// Enter Vehicle Details
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish,testData.deviceDate, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Enter Location Details
			changeMyCarPage.selectOvernightLocationOfVehicle(testData.overnightVehicleLocation, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			Log.softAssertThat(
					!changeMyCarPage.uielement.verifyPageElements(Arrays.asList("drpSelectOvernightVehicleLocation"),
							changeMyCarPage),
					"Location Details section got Collapsed On Clicking Next button in Location Details",
					"Location Details section did not Collapse On Clicking Next button in Location Details", driver,
					extentedReport);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("lblAddSecurityDevice"),
							changeMyCarPage),
					"Security Details Data Capture section is displayed after clicking next button",
					"Security Details Data Capture section is not displayed after clicking next button", driver,
					extentedReport);

			// click NO button
			changeMyCarPage.clickYesOrNoBtnToAddSecurityDevice("No", extentedReport, true);

			Log.softAssertThat(
					!changeMyCarPage.uielement.verifyPageElements(
							Arrays.asList("drpSelectMakeOfInstalledSecurityDevice", "fldModalOfInstalledDevice"),
							changeMyCarPage),
					"Datacapture for the security device is not displayed On Clicking NO button ",
					"Datacapture for the security device is displayed On Clicking NO button", driver, extentedReport);

			Log.softAssertThat(changeMyCarPage.verifySelectedYesOrNobtnOfAddSecurityDevice("No", extentedReport, false),
					"NO button to add securtiy device is selected", "NO button to add securtiy device is not selected",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyCarPage.verifyGreyBackgroundColorOfYesOrNoBtnOfAddSecurityDevice("Yes", extentedReport,
							false),
					"YES button to add securtiy device is greyed out",
					"YES button to add securtiy device is not greyed out", driver, extentedReport);

			// click YES button
			changeMyCarPage.clickYesOrNoBtnToAddSecurityDevice("Yes", extentedReport, true);
			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(
							Arrays.asList("drpSelectMakeOfInstalledSecurityDevice", "fldModalOfInstalledDevice"),
							changeMyCarPage),
					"Datacapture for the security device is displayed On Clicking Yes button ",
					"Datacapture for the security device is not displayed On Clicking Yes button", driver,
					extentedReport);

			// Click Next button
			changeMyCarPage.clickYesOrNoBtnToAddSecurityDevice("No", extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, false);

			Log.softAssertThat(
					!changeMyCarPage.uielement.verifyPageElements(Arrays.asList("lblAddSecurityDevice"),
							changeMyCarPage),
					"Datacapture for the security device got collapsed after clicking Next button",
					"Datacapture for the security device is not collapsed after clicking Next button", driver,
					extentedReport);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("lblAnyModification"), changeMyCarPage),
					"Datacapture for the Modification Details is displayed after clicking next button ",
					"Datacapture for the Modification Details is displayed On Clicking NO button", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that 'Add another Policy change' and ‘Quote’ buttons on any of the data capture screens will only be enabled when the complete data capture is visible and Check that clicking on 'Quote' button should redirect to Your Quote page", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_085_086(String browser) throws Exception {

		String tcId = "TC_085_086";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		String additionalInformation = GenericUtils.getRandomCharacters("ALPHA", 3);
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

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("lblAnyModification"), changeMyCarPage),
					"Modification details section displaying on change of car inititate page",
					"Modification details section displaying on change of car inititate page", driver, extentedReport);
			changeMyCarPage.clickYesOrNoBtnToModificationOfVehicle("Yes", extentedReport, false);
			changeMyCarPage.enterModificationDetails(testData.modificationType, additionalInformation, extentedReport,
					true);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("btnQuote", "btnAddAnotherPolicyChange"),
							changeMyCarPage),
					"'Quote' and 'Add another policy change' button is enabled when complete data capture section is visible",
					"'Quote' and 'Add another policy change' button is not enabled when complete data capture section is not visible",
					driver, extentedReport);

			// Click 'Quote' button
			changeMyCarPage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(mTAQuoteDetailsPage.verifyQuotePageBannerTitle(extentedReport),
					"Successfully navigated to quote page when Quote button is selected",
					" Not navigated to quote button when quote button is selected", driver, extentedReport, false);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that after entering the values and clicking on  '+Save Modification' button should add the details and should get displayed along with EDIT & DELETE button and Check that clicking on EDIT button should display the form with already saved information and modifying and saving the edited value after clicking 'Save Modification' button should Save the newly modified values and Check that clicking on DELETE button should delete the corresponding modification details which is already saved and Check that after entering the values and clicking on '+Save Modification' button should add the details and should allow the user to add another device by displaying the option 'Have any other modifications been made to the vehicles' with YES & NO button displayed and should contain the help tool tip", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_074_076_077_078(String browser) throws Exception {

		String tcId = "TC_074_076_077_078";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		String additionalInformation = GenericUtils.getRandomCharacters("ALPHA", 3);
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

			// Enter Vehicle Details
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish,testData.deviceDate, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Enter Location Details
			changeMyCarPage.selectOvernightLocationOfVehicle(testData.overnightVehicleLocation, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Security Device
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, true);
			changeMyCarPage.clickYesOrNoBtnToModificationOfVehicle("Yes", extentedReport, false);

			// Add Modification
			changeMyCarPage.addModificationDetail(testData.arrModificationType[0], additionalInformation,
					extentedReport, true);

			Log.softAssertThat(
					changeMyCarPage.verifyModificationDeleteAndEditBtn(testData.arrModificationType[0], extentedReport,
							true),
					testData.arrModificationType[0] + " - Added Modification is displayed with Edit and Delete button",
					testData.arrModificationType[0]
							+ " - Added Modification is not displayed with Edit and Delete button",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("lblanyOtherModification",
							"btnYesAnotherModificationToAdd", "btnNoAnothermodificationToAdd"), changeMyCarPage),
					"'Have any other modifications been made to the vehicles' with YES & NO button is displayed",
					"'Have any other modifications been made to the vehicles' with YES & NO button is not displayed",
					driver, extentedReport);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("iconHelpModification"),
							changeMyCarPage),
					"Help tool tip icon is displayed", "Help tool tip icon is not displayed", driver, extentedReport);

			// Click edit button
			changeMyCarPage.clickModificationEditBtn(testData.arrModificationType[0], extentedReport, true);

			Log.softAssertThat(
					changeMyCarPage.verifyModificationDetails(testData.arrModificationType[0], additionalInformation,
							extentedReport, true),
					"On clicking EDIT button display the form with already saved information",
					"On clicking EDIT button display the form with already saved information", driver, extentedReport);

			// modify security device detail
			String modificationType2 = testData.arrModificationType[1];
			changeMyCarPage.selectModificationType(modificationType2, extentedReport, true);

			changeMyCarPage.clickSaveModificationBtn(extentedReport, false);
			Log.softAssertThat(changeMyCarPage.getAddedModificationRow(modificationType2, extentedReport, true) != null,
					modificationType2 + " - Edited modification detail is displayed ",
					modificationType2 + " - Edited modification detail is not displayed", driver, extentedReport);

			// Delete security device
			changeMyCarPage.clickModificationDeleteBtn(modificationType2, extentedReport, false);

			Log.softAssertThat(changeMyCarPage.getAddedModificationRow(modificationType2, extentedReport, true) == null,
					modificationType2 + " - Security device is deleted succesfully ",
					modificationType2 + " - Security device is not deleted", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on 'Add another Policy change' button after all the changes made will redirect to change my policy main screen showing Edit Changes button in change my car option with the tick symbol", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_087(String browser) throws Exception {

		String tcId = "TC_087";
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

			// Enter Vehicle Details
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish,testData.deviceDate, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Enter Location Details
			changeMyCarPage.selectOvernightLocationOfVehicle(testData.overnightVehicleLocation, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Security Device
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, true);

			changeMyCarPage.clickAddAnotherPolicyChangeBtn(extentedReport, false);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(
							Arrays.asList("btnEditChangesOfChangeMyCar", "iconTickSymbol"), mTAInitiatePage),
					"On clicking on 'Add another Policy change' button after all the changes made redirected to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					"On clicking on 'Add another Policy change' button after all the changes made did not redirect to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on 'NO' button should not display data capture for security device and only NO button should get selected and YES button should be greyed out and Check that selecting 'YES' button should display  Security Devices question then data capture will be shown for a new device and Check that clicking on Next button in Security Devices will Collapse Security Devices section and should show next section of Data Capture Modification Details", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_060_061_062(String browser) throws Exception {

		String tcId = "TC_060_061_062";
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

			// Enter Vehicle Details
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish,testData.deviceDate, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Enter Location Details
			changeMyCarPage.selectOvernightLocationOfVehicle(testData.overnightVehicleLocation, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// click YES button
			changeMyCarPage.clickYesOrNoBtnToAddSecurityDevice("Yes", extentedReport, true);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(
							Arrays.asList("drpSelectMakeOfInstalledSecurityDevice", "fldModalOfInstalledDevice"),
							changeMyCarPage),
					"Datacapture for the security device modal displayed On Clicking Yest button ",
					"Datacapture for the security device modal not displayed On Clicking Yes button", driver,
					extentedReport);
			changeMyCarPage.addSecurityDevice(testData.makeOfDevice, testData.deviceModal, testData.deviceDate,
					testData.deviceInstaller, extentedReport, true);
			// Click No button
			changeMyCarPage.clickYesOrNoBtnToAddSecurityDevice("No", extentedReport, true);
			
			Log.softAssertThat( changeMyCarPage.uielement.verifyPageElements(
							Arrays.asList("btnYesonNoSecurityDevicePopup"), changeMyCarPage),
					"Dummy element used: Next button - pop up displayed on selecting No button",
					"Dummy element used: Next button - pop up not displayed on select No button", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on Cancel button empty the already entered values.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_051(String browser) throws Exception {

		String tcId = "TC_051";
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

			// Enter Vehicle Details
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish,testData.deviceDate, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Enter Location Details
			changeMyCarPage.selectOvernightLocationOfVehicle(testData.overnightVehicleLocation, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// click YES button
			changeMyCarPage.clickYesOrNoBtnToAddSecurityDevice("Yes", extentedReport, true);

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(
							Arrays.asList("drpSelectMakeOfInstalledSecurityDevice", "fldModalOfInstalledDevice"),
							changeMyCarPage),
					"Datacapture for the security device modal displayed On Clicking Yest button ",
					"Datacapture for the security device modal not displayed On Clicking Yes button", driver,
					extentedReport);
			changeMyCarPage.enterSecurityDeviceDetail(testData.makeOfDevice, testData.deviceModal, testData.deviceDate,
					testData.deviceInstaller, extentedReport, true);
			// Click cancel button
			changeMyCarPage.clickCancelButtonOnSecurity(extentedReport, true);
			changeMyCarPage.clickYesOrNoBtnToAddSecurityDevice("Yes", extentedReport, true);
			Log.softAssertThat(changeMyCarPage.verifySecurityDeaitls(extentedReport, true),
					"Security details removed successfully when cancel button seleccted",
					"Security details not removed when cancel button selected", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking NO button on alert message window Selecting No to the question Have any modifications been made to the vehicle? will delete the data you just added about your vahicle modifications. Are you sure you want to delete this data should cancel the operation and no record should get deleted.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_082_083_084(String browser) throws Exception {

		String tcId = "TC_082_083_084";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		String additionalInformation = GenericUtils.getRandomCharacters("ALPHA", 3);
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

			// Enter Vehicle Details
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

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("lblAnyModification"), changeMyCarPage),
					"Modification details section displaying on change of car inititate page",
					"Modification details section displaying on change of car inititate page", driver, extentedReport);
			changeMyCarPage.clickYesOrNoBtnToModificationOfVehicle("Yes", extentedReport, false);
			changeMyCarPage.enterModificationDetails(testData.modificationType, additionalInformation, extentedReport,
					true);
			changeMyCarPage.clickSaveModificationBtn(extentedReport, true);
			changeMyCarPage.clickYesOrNoBtnToModificationOfVehicle("No", extentedReport, false);
			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("btnYesonNoPopup"), changeMyCarPage),
					"Dummy element used: Yes button - pop up displayed on selecting No button",
					"Dummy element used: Yes button - pop up not displayed on select No button", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on Cancel button empty the already entered values.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_073(String browser) throws Exception {

		String tcId = "TC_073";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		String additionalInformation = GenericUtils.getRandomCharacters("ALPHA", 3);
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

			// Enter Vehicle Details
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

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("lblAnyModification"), changeMyCarPage),
					"Modification details section displaying on change of car inititate page",
					"Modification details section displaying on change of car inititate page", driver, extentedReport);
			changeMyCarPage.clickYesOrNoBtnToModificationOfVehicle("Yes", extentedReport, false);
			changeMyCarPage.enterModificationDetails(testData.modificationType, additionalInformation, extentedReport,
					true);
			changeMyCarPage.clickCancelModificationBtn(extentedReport, true);
			changeMyCarPage.clickYesOrNoBtnToModificationOfVehicle("Yes", extentedReport, false);

			Log.softAssertThat(
					!changeMyCarPage.verifyModificationDeatilsCancel(testData.modificationType, additionalInformation,
							extentedReport, true),
					"Modification details removed successfully when cancel button seleccted",
					"Modification details not removed when cancel button selected", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on EDIT change button should open the already SAVED Change my Car screen with the already Saved values restored.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_088(String browser) throws Exception {

		String tcId = "TC_088";
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

			changeMyCarPage.clickAddAnotherPolicyChangeBtn(extentedReport, false);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("btnEditChangesOfChangeMyCar"),
							mTAInitiatePage),
					"On clicking on 'Add another Policy change' button after all the changes made redirected to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					"On clicking on 'Add another Policy change' button after all the changes made did not redirect to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					driver, extentedReport);
			mTAInitiatePage.clickEditChangesBtn("Change my car", extentedReport, true);
			Log.softAssertThat(changeMyCarPage.verifyRegistrationNumber(testData.vehicleRegNo, extentedReport, true),
					"Vehicle reigistration displaying as expected:" + testData.vehicleRegNo,
					"Vehicle reigistration not displaying as expected:" + testData.vehicleRegNo);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that by editing the values in General information and click Add another policy changesbutton. Click edit changes button again and verify the values are saved properly.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_089(String browser) throws Exception {

		String tcId = "TC_089";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		String additionalInformation = GenericUtils.getRandomCharacters("ALPHA", 3);
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

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("lblAnyModification"), changeMyCarPage),
					"Modification details section displaying on change of car inititate page",
					"Modification details section displaying on change of car inititate page", driver, extentedReport);
			changeMyCarPage.clickYesOrNoBtnToModificationOfVehicle("Yes", extentedReport, false);
			changeMyCarPage.enterModificationDetails(testData.modificationType, additionalInformation, extentedReport,
					true);

			// Click add another policy change button
			changeMyCarPage.clickAddAnotherPolicyChangeBtn(extentedReport, false);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("btnEditChangesOfChangeMyCar"),
							mTAInitiatePage),
					"On clicking on 'Add another Policy change' button after all the changes made redirected to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					"On clicking on 'Add another Policy change' button after all the changes made did not redirect to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					driver, extentedReport);
			// Verifying car registration number
			mTAInitiatePage.clickEditChangesBtn("Change my car", extentedReport, true);
			Log.softAssertThat(changeMyCarPage.verifyRegistrationNumber(testData.vehicleRegNo, extentedReport, true),
					"Vehicle reigistration displaying as expected:" + testData.vehicleRegNo,
					"Vehicle reigistration not displaying :" + testData.vehicleRegNo);
			changeMyCarPage.enterVehicleRegistrationNumber(testData.vehicleRegNo, extentedReport, true);

			// Verifying General information section
			changeMyCarPage.clickLookUpButton(extentedReport, false);
			Log.softAssertThat(changeMyCarPage.verifyCarDrivingMethdod(testData.drivingType, extentedReport, true),
					"General infomation section values are restored successfully on change of my car page",
					"General information section values are not restored on change of my car page");
			changeMyCarPage.selectGeneralInformationDetails(testData.drivingType, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that by editing the values in Ownership information and click Add another policy changesbutton. Click edit changes button again and verify the values are saved properly.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_090(String browser) throws Exception {

		String tcId = "TC_090";
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

			changeMyCarPage.clickAddAnotherPolicyChangeBtn(extentedReport, false);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("btnEditChangesOfChangeMyCar"),
							mTAInitiatePage),
					"On clicking on 'Add another Policy change' button after all the changes made redirected to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					"On clicking on 'Add another Policy change' button after all the changes made did not redirect to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					driver, extentedReport);

			// Verifying car registration number
			mTAInitiatePage.clickEditChangesBtn("Change my car", extentedReport, true);
			Log.softAssertThat(changeMyCarPage.verifyRegistrationNumber(testData.vehicleRegNo, extentedReport, true),
					"Vehicle reigistration displaying as expected:" + testData.vehicleRegNo,
					"Vehicle reigistration not displaying " + testData.vehicleRegNo);
			changeMyCarPage.enterVehicleRegistrationNumber(testData.vehicleRegNo, extentedReport, true);
			changeMyCarPage.clickLookUpButton(extentedReport, false);

			// Verifying General information section
			Log.softAssertThat(changeMyCarPage.verifyCarDrivingMethdod(testData.drivingType, extentedReport, true),
					"General infomation section values are restored successfully on change of my car page",
					"General information section values are not restored on change of my car page", driver,
					extentedReport);
			changeMyCarPage.selectGeneralInformationDetails(testData.drivingType, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying Ownership Information section
			Log.softAssertThat(
					changeMyCarPage.verifyOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
							testData.purchaseYear, extentedReport, true),
					"Ownership details are restored successfully on change of my car page",
					"Ownership details are not restored properly on change of my car page", driver, extentedReport);
			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that by editing the values in Vehicle Detailsand click Add another policy changesbutton. Click edit changes button again and verify the values are saved properly", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_092(String browser) throws Exception {

		String tcId = "TC_092";
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

			changeMyCarPage.clickAddAnotherPolicyChangeBtn(extentedReport, false);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("btnEditChangesOfChangeMyCar"),
							mTAInitiatePage),
					"On clicking on 'Add another Policy change' button after all the changes made redirected to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					"On clicking on 'Add another Policy change' button after all the changes made did not redirect to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					driver, extentedReport);
			// Verifying car registration number
			mTAInitiatePage.clickEditChangesBtn("Change my car", extentedReport, true);
			Log.softAssertThat(changeMyCarPage.verifyRegistrationNumber(testData.vehicleRegNo, extentedReport, true),
					"Vehicle reigistration displaying as expected:" + testData.vehicleRegNo,
					"Vehicle reigistration not displaying as expected:" + testData.vehicleRegNo);
			changeMyCarPage.enterVehicleRegistrationNumber(testData.vehicleRegNo, extentedReport, true);

			// Verifying General information section
			changeMyCarPage.clickLookUpButton(extentedReport, false);
			Log.softAssertThat(changeMyCarPage.verifyCarDrivingMethdod(testData.drivingType, extentedReport, true),
					"General infomation section values are restored successfully on change of my car page",
					"General information section values are not restored on change of my car page");
			changeMyCarPage.clickLookUpButton(extentedReport, false);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying Ownership Information section
			Log.softAssertThat(
					changeMyCarPage.verifyOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
							testData.purchaseYear, extentedReport, true),
					"Ownership details are restored successfully on change of my car page",
					"Ownership details are not restored properly on change of my car page, due to that to resume the case we are entering values");
			changeMyCarPage.enterOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
					testData.purchaseYear, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Verifying Vehicle details section
			Log.softAssertThat(
					changeMyCarPage.verifyVehicleDetails(testData.vehicleValue, testData.odometerReading,
							testData.paintworkColor, testData.paintworkFinish, testData.deviceDate, extentedReport, true),
					"Vehicle details are restored on change of my car page",
					"Vehicle details are not restored on change of my car page");

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that by editing the values in Location Detailsand click Add another policy changesbutton. Click edit changes button again and verify the values are saved properly", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_093(String browser) throws Exception {

		String tcId = "TC_093";
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

			changeMyCarPage.clickAddAnotherPolicyChangeBtn(extentedReport, false);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("btnEditChangesOfChangeMyCar"),
							mTAInitiatePage),
					"On clicking on 'Add another Policy change' button after all the changes made redirected to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					"On clicking on 'Add another Policy change' button after all the changes made did not redirect to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					driver, extentedReport);
			// Verifying car registration number
			mTAInitiatePage.clickEditChangesBtn("Change my car", extentedReport, true);
			Log.softAssertThat(changeMyCarPage.verifyRegistrationNumber(testData.vehicleRegNo, extentedReport, true),
					"Vehicle reigistration displaying as expected:" + testData.vehicleRegNo,
					"Vehicle reigistration not displaying as expected:" + testData.vehicleRegNo);
			changeMyCarPage.enterVehicleRegistrationNumber(testData.vehicleRegNo, extentedReport, true);

			// Verifying General information section
			changeMyCarPage.clickLookUpButton(extentedReport, false);
			Log.softAssertThat(changeMyCarPage.verifyCarDrivingMethdod(testData.drivingType, extentedReport, true),
					"General infomation section values are restored successfully on change of my car page",
					"General information section values are not restored on change of my car page", driver,
					extentedReport);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying Ownership Information section
			Log.softAssertThat(
					changeMyCarPage.verifyOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
							testData.purchaseYear, extentedReport, true),
					"Ownership details are restored successfully on change of my car page",
					"Ownership details are not restored properly on change of my car page, due to that to resume the case we are entering values",
					driver, extentedReport);
			changeMyCarPage.enterOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
					testData.purchaseYear, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Verifying Vehicle details section
			Log.softAssertThat(
					changeMyCarPage.verifyVehicleDetails(testData.vehicleValue, testData.odometerReading,
							testData.paintworkColor, testData.paintworkFinish,testData.deviceDate, extentedReport, true),
					"Vehicle details are restored on change of my car page",
					"Vehicle details are not restored on change of my car page, so we are entering the test value for resume further",
					driver, extentedReport);
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish,testData.deviceDate, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);
			// Verifying vehicle location details section
			Log.softAssertThat(
					changeMyCarPage.verifyOvernightLoactionOfVehicle(testData.overnightVehicleLocation, extentedReport,
							true),
					"Overnight location details are restored on change of my car page",
					"Overnight location details are not restored on change of my car page", driver, extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
			"Smoke" }, description = "Check that by editing the values in Security devices and click Add another policy changesbutton. Click edit changes button again and verify the values are saved properly.", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_094(String browser) throws Exception {

		String tcId = "TC_094";
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

			// Entering security details section
			changeMyCarPage.clickYesOrNoBtnToAddSecurityDevice("Yes", extentedReport, true);
			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(
							Arrays.asList("drpSelectMakeOfInstalledSecurityDevice", "fldModalOfInstalledDevice"),
							changeMyCarPage),
					"Datacapture for the security device modal displayed On Clicking Yest button ",
					"Datacapture for the security device modal not displayed On Clicking Yes button", driver,
					extentedReport);
			changeMyCarPage.addSecurityDevice(testData.makeOfDevice, testData.deviceModal, testData.deviceDate,
					testData.deviceInstaller, extentedReport, true);

			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, true);

			// Click add another policy change button

			changeMyCarPage.clickAddAnotherPolicyChangeBtn(extentedReport, false);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("btnEditChangesOfChangeMyCar"),
							mTAInitiatePage),
					"On clicking on 'Add another Policy change' button after all the changes made redirected to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					"On clicking on 'Add another Policy change' button after all the changes made did not redirect to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					driver, extentedReport);
			// Verifying car registration number
			mTAInitiatePage.clickEditChangesBtn("Change my car", extentedReport, true);
			Log.softAssertThat(changeMyCarPage.verifyRegistrationNumber(testData.vehicleRegNo, extentedReport, true),
					"Vehicle reigistration displaying as expected:" + testData.vehicleRegNo,
					"Vehicle reigistration nubmber not displaying :" + testData.vehicleRegNo);
			changeMyCarPage.clickLookUpButton(extentedReport, false);
			changeMyCarPage.enterVehicleRegistrationNumber(testData.vehicleRegNo, extentedReport, true);

			// Verifying General information section
			changeMyCarPage.clickLookUpButton(extentedReport, false);
			Log.softAssertThat(changeMyCarPage.verifyCarDrivingMethdod(testData.drivingType, extentedReport, true),
					"General infomation section values are restored successfully on change of my car page",
					"General information section values are not restored on change of my car page", driver,
					extentedReport);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying Ownership Information section
			Log.softAssertThat(
					changeMyCarPage.verifyOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
							testData.purchaseYear, extentedReport, true),
					"Ownership details are restored successfully on change of my car page",
					"Ownership details are not restored properly on change of my car page, due to that to resume the case we are entering values",
					driver, extentedReport);
			changeMyCarPage.enterOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
					testData.purchaseYear, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Verifying Vehicle details section
			Log.softAssertThat(
					changeMyCarPage.verifyVehicleDetails(testData.vehicleValue, testData.odometerReading,
							testData.paintworkColor, testData.paintworkFinish,testData.deviceDate, extentedReport, true),
					"Vehicle details are restored on change of my car page",
					"Vehicle details are not restored on change of my car page, so we are entering the test value for resume further",
					driver, extentedReport);
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish,testData.deviceDate, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Verifying vehicle location details section
			Log.softAssertThat(
					changeMyCarPage.verifyOvernightLoactionOfVehicle(testData.overnightVehicleLocation, extentedReport,
							true),
					"Overnight location details are restored on change of my car page",
					"Overnight location details are not restored on change of my car page, so we are entering location values to resume further",
					driver, extentedReport);
			changeMyCarPage.selectOvernightLocationOfVehicle(testData.overnightVehicleLocation, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			Log.softAssertThat(
					changeMyCarPage.verifySecurityDeviceDetail(testData.makeOfDevice, testData.deviceModal,
							testData.deviceDate, testData.deviceInstaller, extentedReport, true),
					"Added security details are displayed correclty on change my car page",
					"Added security details are not displaying on change my car page", driver, extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that by editing the values in Modification Detailsand click Add another policy changesbutton. Click edit changes button again and verify the values are saved properly", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_095(String browser) throws Exception {

		String tcId = "TC_095";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		String additionalInformation = GenericUtils.getRandomCharacters("ALPHA", 3);
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

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("lblAnyModification"), changeMyCarPage),
					"Modification details section displaying on change of car inititate page",
					"Modification details section displaying on change of car inititate page", driver, extentedReport);
			changeMyCarPage.clickYesOrNoBtnToModificationOfVehicle("Yes", extentedReport, false);
			changeMyCarPage.enterModificationDetails(testData.modificationType, additionalInformation, extentedReport,
					true);
			changeMyCarPage.clickSaveModificationBtn(extentedReport, true);
			changeMyCarPage.selectAddtionalMedicalConditions(extentedReport, true);

			// Click addanotherpolicy change button
			changeMyCarPage.clickAddAnotherPolicyChangeBtn(extentedReport, false);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("btnEditChangesOfChangeMyCar"),
							mTAInitiatePage),
					"On clicking on 'Add another Policy change' button after all the changes made redirected to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					"On clicking on 'Add another Policy change' button after all the changes made did not redirect to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					driver, extentedReport);
			// Verifying car registration number
			mTAInitiatePage.clickEditChangesBtn("Change my car", extentedReport, true);
			Log.softAssertThat(changeMyCarPage.verifyRegistrationNumber(testData.vehicleRegNo, extentedReport, true),
					"Vehicle reigistration displaying as expected:" + testData.vehicleRegNo,
					"Vehicle reigistration not displaying:" + testData.vehicleRegNo);
			changeMyCarPage.enterVehicleRegistrationNumber(testData.vehicleRegNo, extentedReport, true);

			// Verifying General information section
			changeMyCarPage.clickLookUpButton(extentedReport, false);
			Log.softAssertThat(changeMyCarPage.verifyCarDrivingMethdod(testData.drivingType, extentedReport, true),
					"General infomation section values are restored successfully on change of my car page",
					"General information section values are not restored on change of my car page", driver,
					extentedReport);
			changeMyCarPage.selectGeneralInformationDetails(testData.drivingType, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying Ownership Information section
			Log.softAssertThat(
					changeMyCarPage.verifyOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
							testData.purchaseYear, extentedReport, true),
					"Ownership details are restored successfully on change of my car page",
					"Ownership details are not restored properly on change of my car page, due to that to resume the case we are entering values",
					driver, extentedReport);
			changeMyCarPage.enterOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
					testData.purchaseYear, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Verifying Vehicle details section
			Log.softAssertThat(
					changeMyCarPage.verifyVehicleDetails(testData.vehicleValue, testData.odometerReading,
							testData.paintworkColor, testData.paintworkFinish,testData.deviceDate, extentedReport, true),
					"Vehicle details are restored on change of my car page",
					"Vehicle details are not restored on change of my car page, so we are entering the test value for resume further",
					driver, extentedReport);
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish,testData.deviceDate, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Verifying vehicle location details section
			Log.softAssertThat(
					changeMyCarPage.verifyOvernightLoactionOfVehicle(testData.overnightVehicleLocation, extentedReport,
							true),
					"Overnight location details are restored on change of my car page",
					"Overnight location details are not restored on change of my car page, so we are entering location values to resume further",
					driver, extentedReport);
			changeMyCarPage.selectOvernightLocationOfVehicle(testData.overnightVehicleLocation, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Verifying Security device details
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, false);

			// verifying modification details
			Log.softAssertThat(
					changeMyCarPage.verifyModificationDeatils(testData.modificationType, additionalInformation,
							extentedReport, true),
					"modification details are dislaying as restored on change my car page",
					"modification details are not displaying as restored on change my car page", driver,
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
			"Smoke" }, description = "Check that by editing the values in Modification Detailsand click Add another policy changesbutton. Click edit changes button again and verify the values are saved properly", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_096(String browser) throws Exception {

		String tcId = "TC_096";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		String additionalInformation = GenericUtils.getRandomCharacters("ALPHA", 3);
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

			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(Arrays.asList("lblAnyModification"), changeMyCarPage),
					"Modification details section displaying on change of car inititate page",
					"Modification details section displaying on change of car inititate page", driver, extentedReport);
			changeMyCarPage.clickYesOrNoBtnToModificationOfVehicle("Yes", extentedReport, false);
			changeMyCarPage.enterModificationDetails(testData.modificationType, additionalInformation, extentedReport,
					true);
			changeMyCarPage.clickSaveModificationBtn(extentedReport, true);

			changeMyCarPage.selectAddtionalMedicalConditions(extentedReport, true);

			// Click addanotherpolicy change button
			changeMyCarPage.clickAddAnotherPolicyChangeBtn(extentedReport, false);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("btnEditChangesOfChangeMyCar"),
							mTAInitiatePage),
					"On clicking on 'Add another Policy change' button after all the changes made redirected to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					"On clicking on 'Add another Policy change' button after all the changes made did not redirect to change my policy main screen showing Edit Changes button in change my car option with the tick symbol",
					driver, extentedReport);
			// Verifying car registration number
			mTAInitiatePage.clickEditChangesBtn("Change my car", extentedReport, true);
			Log.softAssertThat(changeMyCarPage.verifyRegistrationNumber(testData.vehicleRegNo, extentedReport, true),
					"Vehicle reigistration displaying as expected:" + testData.vehicleRegNo,
					"Vehicle reigistration not displaying :" + testData.vehicleRegNo);
			changeMyCarPage.enterVehicleRegistrationNumber(testData.vehicleRegNo, extentedReport, true);

			// Verifying General information section
			changeMyCarPage.clickLookUpButton(extentedReport, false);
			Log.softAssertThat(changeMyCarPage.verifyCarDrivingMethdod(testData.drivingType, extentedReport, true),
					"General infomation section values are restored successfully on change of my car page",
					"General information section values are not restored on change of my car page", driver,
					extentedReport);
			changeMyCarPage.selectGeneralInformationDetails(testData.drivingType, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying Ownership Information section
			Log.softAssertThat(
					changeMyCarPage.verifyOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
							testData.purchaseYear, extentedReport, true),
					"Ownership details are restored successfully on change of my car page",
					"Ownership details are not restored properly on change of my car page, due to that to resume the case we are entering values",
					driver, extentedReport);
			changeMyCarPage.enterOwnershipInformation(testData.keeperOfVehilce, testData.purchaseMonth,
					testData.purchaseYear, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Verifying Vehicle details section
			Log.softAssertThat(
					changeMyCarPage.verifyVehicleDetails(testData.vehicleValue, testData.odometerReading,
							testData.paintworkColor, testData.paintworkFinish,testData.deviceDate, extentedReport, true),
					"Vehicle details are restored on change of my car page",
					"Vehicle details are not restored on change of my car page, so we are entering the test value for resume further",
					driver, extentedReport);
			changeMyCarPage.enterVehicleDetail(testData.vehicleValue, testData.odometerReading, testData.paintworkColor,
					testData.paintworkFinish,testData.deviceDate, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Verifying vehicle location details section
			Log.softAssertThat(
					changeMyCarPage.verifyOvernightLoactionOfVehicle(testData.overnightVehicleLocation, extentedReport,
							true),
					"Overnight location details are restored on change of my car page",
					"Overnight location details are not restored on change of my car page, so we are entering location values to resume further",
					driver, extentedReport);
			changeMyCarPage.selectOvernightLocationOfVehicle(testData.overnightVehicleLocation, extentedReport, true);
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Verifying Security device details
			changeMyCarPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, false);

			Log.softAssertThat(
					changeMyCarPage.verifyModificationDeatils(testData.modificationType, additionalInformation,
							extentedReport, true),
					"modification details are dislaying as restored on change my car page",
					"modification details are not displaying as restored on change my car page", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that after entering the values and clicking on Save Device button should add the details and should allow the user to add another device by displaying the option Are there any other non-factory fitted security devices added to the vehicle with YES & NO button displayed and should contain the help tool tip. [User can add N number of security devices following same steps", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_059(String browser) throws Exception {

		String tcId = "TC_059";
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

			// Entering security details section
			changeMyCarPage.clickYesOrNoBtnToAddSecurityDevice("Yes", extentedReport, true);
			changeMyCarPage.addSecurityDevice(testData.makeOfDevice, testData.deviceModal, testData.deviceDate,
					testData.deviceInstaller, extentedReport, true);
			Log.softAssertThat(
					changeMyCarPage.uielement.verifyPageElements(
							Arrays.asList("btnYesAddSecurityDevice", "btnNoAddSecurityDevice", "btnHelpSecurityIcon"),
							changeMyCarPage),
					"Based on Dummy element: Datacapture for the security device modal displayed with Yes , No and Helpicon buttons ",
					"Based on Dummy element: Datacapture for the security device modal not displayed with Yes , No and Helpicon buttons",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// ******************* Story Id: 3674 ***************//
	@Test(description = "Check that clicking on 'Continue' button after selecting Change my cover type option opens up Change my cover type main screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_102(String browser) throws Exception {

		String tcId = "TC_102";
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

			// Select change my cover type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			Log.softAssertThat(
					changeMyCoverTypePage.uielement.verifyPageElements(Arrays.asList("drpdwnTypeOfCover"),
							changeMyCoverTypePage),
					"On clicking on 'Continue' button after selecting 'Change my cover type' option opens up Change my cover type main screen",
					"On clicking on 'Continue' button after selecting 'Change my cover type' option did not opens up Change my cover type main screen",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the customer is shown with the Insurance Premium when try to change level of cover on motor Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_105(String browser) throws Exception {

		String tcId = "TC_105";
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

			// Select cover type
			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);

			// Click Quote button
			changeMyCoverTypePage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat( mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("lblAmtAdditional"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to change class of use on motor Policy for change type : "
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

	@Test(description = "Check that clicking on Cancel button on 'Change my cover type' main screen navigates the user to Change My Policy screen without any change", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_106(String browser) throws Exception {

		String tcId = "TC_106";
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

			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			Log.softAssertThat(
					changeMyCoverTypePage.uielement.verifyPageElements(Arrays.asList("drpdwnTypeOfCover"),
							changeMyCoverTypePage),
					"On clicking on 'Continue' button after selecting 'Change the class of use' option opens up Change the class of use main screen",
					"On clicking on 'Continue' button after selecting 'Change the class of use' option did not opens up Change the class of use main screen",
					driver, extentedReport);

			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);

			// Click cancel button
			changeMyCoverTypePage.clickCancelButton(extentedReport, false);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange"),
							mTAInitiatePage),
					"On clicking on Cancel button on 'Change the class of use' main screen navigates the user to Change My Policy screen without any change",
					"On clicking on Cancel button on 'Change the class of use' main screen did not navigates the user to Change My Policy screen without any change",
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
			"Smoke" }, description = "Check that clicking on 'Add another policy change' button within Change my cover type screen takes the user back to Change My Policy screen with the changes saved and edit button displays on the amendment and Check that the user is able to edit the cover when click on 'Edit changes' button on the change my cover type", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_107_108(String browser) throws Exception {

		String tcId = "TC_107_108";
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

			// select cover type
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyCoverTypePage changeMyCoverTypePage = new ChangeMyCoverTypePage(driver, extentedReport).get();
			changeMyCoverTypePage.selectCoverType(testData.coverName, extentedReport, true);
			changeMyCoverTypePage.selectVoluntaryExcess(testData.volantaryexcess, extentedReport, true);

			changeMyCoverTypePage.clickAddAnotherPolicyChangeButton(extentedReport, false);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChooseTypeOfChange", "btnEditChangeMyCoverType"), mTAInitiatePage),
					"On clicking on 'Add another policy change' button within 'Change the class of use' screen takes the user back to Change My Policy screen and edit button displayed on the amendment",
					"On clicking on 'Add another policy change' button within 'Change the class of use' screen did not takes the user back to Change My Policy screen and edit button displayed on the amendment",
					driver, extentedReport);

			mTAInitiatePage.clickEditChangesBtn(testData.typeOfChange, extentedReport, false);

			Log.softAssertThat(
					changeMyCoverTypePage.uielement.verifyPageElements(Arrays.asList("txtTitleChangeMyCoverType"),
							changeMyCoverTypePage),
					"User is able to edit the cover when click on 'Edit changes' button on Change my cover Type",
					"User is unable to edit the cover when click on 'Edit changes' button on Change my cover Type",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// ******************* Story Id: 3675 ***************//

	@Test(description = "Check that clicking on 'Continue' button after selecting Change the class of use option opens up Change the class of use main screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_110(String browser) throws Exception {

		String tcId = "TC_110";
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

			// Select Class of use
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeTheClassOfUsePage = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			Log.softAssertThat(
					changeTheClassOfUsePage.uielement.verifyPageElements(Arrays.asList("drpdwnChangeClassOfUse"),
							changeTheClassOfUsePage),
					"On clicking on 'Continue' button after selecting 'Change the class of use' option opens up Change the class of use main screen",
					"On clicking on 'Continue' button after selecting 'Change the class of use' option did not opens up Change the class of use main screen",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the customer is shown with the Insurance Premium when try to change class of use on motor Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_112(String browser) throws Exception {

		String tcId = "TC_112";
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

			// Select Change My Car
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			// select class of use
			ChangeTheClassOfUsePage changeTheClassOfUsePage = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeTheClassOfUsePage.selectClassOfUse(testData.classOfUse, extentedReport, true);
			changeTheClassOfUsePage.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			// Click Quote Button
			changeTheClassOfUsePage.clickQuoteButton(extentedReport, false);
			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();

			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundPremium"), mTAQuoteDetailsPage),
					"Insurance Premium amount is displayed when try to change class of use on motor Policy for change type : "
							+ testData.typeOfChange,
					"Insurance Premium amount is not displayed when try to change class of use on motor Policy for change type : "
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

	@Test(description = "Check that clicking on Cancel button on 'Change the class of use' main screen navigates the user to Change My Policy screen without any change", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_113(String browser) throws Exception {

		String tcId = "TC_113";
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

			ChangeTheClassOfUsePage changeTheClassOfUsePage = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			Log.softAssertThat(
					changeTheClassOfUsePage.uielement.verifyPageElements(Arrays.asList("drpdwnChangeClassOfUse"),
							changeTheClassOfUsePage),
					"On clicking on 'Continue' button after selecting 'Change the class of use' option opens up Change the class of use main screen",
					"On clicking on 'Continue' button after selecting 'Change the class of use' option did not opens up Change the class of use main screen",
					driver, extentedReport);

			// Click cancel button
			changeTheClassOfUsePage.clickCancelButton(extentedReport, false);

			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange"),
							mTAInitiatePage),
					"On clicking on Cancel button on 'Change the class of use' main screen navigates the user to Change My Policy screen without any change",
					"On clicking on Cancel button on 'Change the class of use' main screen did not navigates the user to Change My Policy screen without any change",
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
			"Smoke" }, description = "Check that clicking on 'Add another policy change' button within 'Change the class of use' screen takes the user back to Change My Policy screen with the changes saved and edit button displays on the amendment and Check that the user is able to edit the cover when click on 'Edit changes' button on Change the class of use", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_114_115(String browser) throws Exception {

		String tcId = "TC_114_115";
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

			// select class of use
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeTheClassOfUsePage changeTheClassOfUsePage = new ChangeTheClassOfUsePage(driver, extentedReport).get();
			changeTheClassOfUsePage.selectClassOfUse(testData.classOfUse, extentedReport, true);
			changeTheClassOfUsePage.selectAnnualPleasure(testData.Mileage_Pleasure, testData.Mileage_Business, extentedReport, true);

			changeTheClassOfUsePage.clickAddAnotherPolicyChangeButton(extentedReport, false);

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(
					Arrays.asList("txtTitleChooseTypeOfChange", "btnEditChangesOfChangeClassOfUse"), mTAInitiatePage),
					"On clicking on 'Add another policy change' button within 'Change the class of use' screen takes the user back to Change My Policy screen and edit button displayed on the amendment",
					"On clicking on 'Add another policy change' button within 'Change the class of use' screen did not takes the user back to Change My Policy screen and edit button displayed on the amendment",
					driver, extentedReport);

			mTAInitiatePage.clickEditChangesBtn(testData.typeOfChange, extentedReport, false);

			Log.softAssertThat(
					changeTheClassOfUsePage.uielement.verifyPageElements(Arrays.asList("txtTitleClassOfUse"),
							changeTheClassOfUsePage),
					"User is able to edit the cover when click on 'Edit changes' button on Change the class of use",
					"User is unable to edit the cover when click on 'Edit changes' button on Change the class of use",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// ******************* Story Id: 3676 ***************//

	@Test(description = "Check that 'Change permitted drivers' option is available in Change My Policy screen and Check that clicking on 'Continue' button after selecting Change permitted drivers option opens up Change permitted drivers main screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_117_118(String browser) throws Exception {

		String tcId = "TC_117_118";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("btnChangePermittedDrivers"),
							mTAInitiatePage),
					"'Change permitted drivers' option is available in Change My Policy screen",
					"'Change permitted drivers' option is not available in Change My Policy screen", driver,
					extentedReport, true);

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();
			Log.softAssertThat(
					changePermittedDriversPage.verifyHeader("Change Permitted Drivers")
							&& changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpDriverType"),
									changePermittedDriversPage),
					"Clicking on 'Continue' button after selecting Change permitted drivers option navigated to Change permitted drivers main screen",
					"Clicking on 'Continue' button after selecting Change permitted drivers option not navigated to Change permitted drivers main screen",
					driver, extentedReport, false);
			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
			"Smoke" }, description = "Check that the customer is shown with the Insurance Premium when try to Change/add/remove permitted drivers on motor Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_119(String browser) throws Exception {

		String tcId = "TC_119";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

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

			// Click Quote
			changePermittedDriversPage.clickQuoteBtn(extentedReport, true);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(	mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtPremium"), mTAQuoteDetailsPage),
					"The customer is shown with the Insurance Premium when try to Change/add/remove permitted drivers on motor Policy",
					"The customer is not shown with the Insurance Premium when try to Change/add/remove permitted drivers on motor Policy",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);

		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "To Check that the client is not able to Add/Remove Drivers when the value for 'Who should be allowed to drive on your policy?' is Any Driver, Any Driver–Excluding Drivers Under 21,Any Driver–Excluding Drivers Under 25,Any Driver–Excluding Drivers Under 50,Insured Only  ( Only this option is given in wireframe )", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 1)
	public void TC_121(String browser) throws Exception {

		String tcId = "TC_121";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);
			Log.softAssertThat(
					!changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAdditionalDriversYes"),
							changePermittedDriversPage),
					"Verified that Add\\Remove Drivers section is not displayed for " + testData.driverTypeList[0] + "",
					"Add\\Remove Drivers section is displayed for " + testData.driverTypeList[0] + "", driver,
					extentedReport, false);

			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[1], extentedReport, true);
			Log.softAssertThat(
					!changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAdditionalDriversYes"),
							changePermittedDriversPage),
					"Verified that Add\\Remove Drivers section is not displayed for " + testData.driverTypeList[1] + "",
					"Add\\Remove Drivers section is displayed for " + testData.driverTypeList[1] + "", driver,
					extentedReport, false);

			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[2], extentedReport, true);
			Log.softAssertThat(
					!changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAdditionalDriversYes"),
							changePermittedDriversPage),
					"Verified that Add\\Remove Drivers section is not displayed for " + testData.driverTypeList[2] + "",
					"Add\\Remove Drivers section is displayed for " + testData.driverTypeList[2] + "", driver,
					extentedReport, false);

			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[3], extentedReport, true);
			Log.softAssertThat(
					!changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAdditionalDriversYes"),
							changePermittedDriversPage),
					"Verified that Add\\Remove Drivers section is not displayed for " + testData.driverTypeList[3] + "",
					"Add\\Remove Drivers section is displayed for " + testData.driverTypeList[3] + "", driver,
					extentedReport, false);

			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[4], extentedReport, true);
			Log.softAssertThat(
					!changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAdditionalDriversYes"),
							changePermittedDriversPage),
					"Verified that Add\\Remove Drivers section is not displayed for " + testData.driverTypeList[4] + "",
					"Add\\Remove Drivers section is displayed for " + testData.driverTypeList[4] + "", driver,
					extentedReport, false);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the Delete button is displayed against the permitted driver when the number of additional drivers is equal to 1 with the following drop down selected - Insured and Civil Partner (or), Insured and Spouse and Check that the client is NOT able to 'Add driver' when the number of additional drivers is equal to 1 with the following drop down selected - Insured and Civil Partner (or) Insured and Spouse ", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 2)
	public void TC_128_129(String browser) throws Exception {

		String tcId = "TC_128_129";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			// Insured and Civil Partner
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

			Log.message("<b><u> Verification for TC_128</u></b>", driver, extentedReport);

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnDelete"),
							changePermittedDriversPage),
					"Verified that delete button is displayed  when the number of additional drivers is equal to 1 for additonal driver type'"
							+ testData.driverTypeList[0] + "'",
					"Delete button is not displayed  when the number of additional drivers is equal to 1 for '"
							+ testData.driverTypeList[0] + "'",
					driver, extentedReport, true);

			Log.message("<b><u> Verification for TC_129</u></b>", driver, extentedReport);
			Log.softAssertThat(
					changePermittedDriversPage.verifyAddDriverYesOrNoBtnDisabled("Yes", extentedReport, true),
					"Verified that Add driver option is not displayed  when the number of additional drivers is equal to 1 for '"
							+ testData.driverTypeList[0] + "'",
					"Add driver option is displayed  when the number of additional drivers is equal to 1 for '"
							+ testData.driverTypeList[0] + "'",
					driver, extentedReport, true);

			changePermittedDriversPage.clickDeleteBtnOfPermittedDriver(testData.foreName, extentedReport, false);
			changePermittedDriversPage.clickYesOrNoBtnToDeleteDriver("Yes", extentedReport, false);

			// Insured and Spouse
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[1], extentedReport, true);
			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			changePermittedDriversPage.addAdditionalDrivers(testData.sectionTypeList, testData.title, testData.foreName,
					testData.surname, testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender,
					testData.maritalStatus, testData.relationship, testData.main_occupation, testData.main_business,
					testData.main_empStatus, testData.LicenceDate, testData.LicenceType, testData.qualification,
					testData.LicenceNumber, testData.reason, testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, testData.frequencyOfUse,
					testData.isCommutingPurpose, testData.isIncreasedPremiumApplied, testData.isInsuranceRefused,
					testData.isSpecialTermsApplied, testData.hadCriminalConviction, extentedReport, true);

			Log.message("<b><u> Verification for TC_128</u></b>", driver, extentedReport);
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnDelete"),
							changePermittedDriversPage),
					"Verified that delete button is displayed  when the number of additional drivers is equal to 1for additonal driver type '"
							+ testData.driverTypeList[1] + "'",
					"Delete button is not displayed  when the number of additional drivers is equal to 1  for '"
							+ testData.driverTypeList[1] + "'",
					driver, extentedReport, true);

			Log.message("<b><u>Verification for TC_129</u></b>", driver, extentedReport);
			Log.softAssertThat(
					changePermittedDriversPage.verifyAddDriverYesOrNoBtnDisabled("Yes", extentedReport, true),
					"Verified that Add driver option is not displayed  when the number of additional drivers is equal to 1 for  '"
							+ testData.driverTypeList[1] + "'",
					"Add driver option is displayed  when the number of additional drivers is equal to 1 for '"
							+ testData.driverTypeList[1] + "'",
					driver, extentedReport, true);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the 'Delete' button is NOT displayed against the permitted driver when the number of additional drivers is equal to 0 with the following drop down selected -Insured and Civil Partner (or)Insured and Spouse and Check that the 'Delete' button is NOT displayed against the permitted driver when the number of additional drivers is equal to 0 with the following drop down selected 'Insured and Named Drivers'and Check that the client is NOT able to Edit/Remove the Main Driver", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 3)
	public void TC_130_132_147(String browser) throws Exception {

		String tcId = "TC_130_132_147";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			// changePermittedDriversPage.clickYesOrNoInRemoveAdditionalDriverPopup("Yes",
			// extentedReport, true);

			Log.message("<b> <u> Verification for TC_147 </u> </b>", extentedReport);
			Log.softAssertThat(
					!changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnEdit", "btnDelete"),
							changePermittedDriversPage),
					"Verified that Edit and Delete button is not displayed for main driver.",
					"Edit and Delete button is displayed for Main driver.", driver, extentedReport, true);

			Log.message("<b> <u> Verification for TC_130 </u> </b>", extentedReport);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);
			Log.softAssertThat(
					!changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnDelete"),
							changePermittedDriversPage),
					"Verified that Delete button is not displayed against the permitted driver for '"
							+ testData.driverTypeList[0] + "' when the number of additional drivers is equal to 0 ",
					"Delete button is displayed against the permitted driver for " + testData.driverTypeList[0]
							+ "' when the number of additional drivers is equal to 0 ",
					driver, extentedReport, true);

			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[1], extentedReport, true);
			Log.softAssertThat(
					!changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnDelete"),
							changePermittedDriversPage),
					"Verified that Delete button is not displayed against the permitted driver for '"
							+ testData.driverTypeList[1] + "'when the number of additional drivers is equal to 0 ",
					"Delete button is displayed against the permitted driver for '" + testData.driverTypeList[1]
							+ "'when the number of additional drivers is equal to 0 ",
					driver, extentedReport, true);

			Log.message("<b> <u> Verification for TC_132 </u> </b>", extentedReport);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[2], extentedReport, true);
			Log.softAssertThat(
					!changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnDelete"),
							changePermittedDriversPage),
					"Verified that Delete button is not displayed against the permitted driver for '"
							+ testData.driverTypeList[2] + "'when the number of additional drivers is equal to 0 ",
					"Delete button is displayed against the permitted driver for '" + testData.driverTypeList[2]
							+ "'when the number of additional drivers is equal to 0 ",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the client is able to 'Add driver' when the number of additional drivers is equal to 0 with the following drop down selected Insured and Civil Partner and Check that the client is able to 'Add driver' when the number of additional drivers is equal to 0 with the following drop down selected Insured and Named Drivers", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 4)
	public void TC_131_133(String browser) throws Exception {

		String tcId = "TC_131_133";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);

			Log.message("<b><u> Verification for TC_131</u></b>", driver, extentedReport);
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAdditionalDriversYes"),
							changePermittedDriversPage),
					"Verified client is able to 'Add driver' when the number of additional drivers is equal to 0 with the following drop down selected '"
							+ testData.driverTypeList[0] + "'",
					"Client is not able to 'Add driver' when the number of additional drivers is equal to 0 with the following drop down selected '"
							+ testData.driverTypeList[0] + "'",
					driver, extentedReport, true);

			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[1], extentedReport, true);
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAdditionalDriversYes"),
							changePermittedDriversPage),
					"Verified client is able to 'Add driver' when the number of additional drivers is equal to 0 with the following drop down selected '"
							+ testData.driverTypeList[1] + "'",
					"Client is not able to 'Add driver' when the number of additional drivers is equal to 0 with the following drop down selected '"
							+ testData.driverTypeList[1] + "'",
					driver, extentedReport, true);

			Log.message("<b><u> Verification for TC_133</u></b>", driver, extentedReport);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[2], extentedReport, true);
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAdditionalDriversYes"),
							changePermittedDriversPage),
					"Verified client is able to 'Add driver' when the number of additional drivers is equal to 0 with the following drop down selected '"
							+ testData.driverTypeList[2] + "'",
					"Client is not able to 'Add driver' when the number of additional drivers is equal to 0 with the following drop down selected '"
							+ testData.driverTypeList[2] + "'",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that 'The change you just made will remove the additional drivers you already have from your policy. Are you sure you want to remove all of your additional drivers?' with 'Yes' & 'No' buttons are displayed when additional drivers is greater than 0 with any of drop down selected - Insured and Civil Partner, Insured and Spouse, Insured and Named Drivers and Check that the additional drivers are removed when 'Yes' option is selected from the prompt message 'The change you just made will remove the additional drivers you already have from your policy. Are you sure you want to remove all of your additional drivers?'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 7)
	public void TC_138_139(String browser) throws Exception {
		String tcId = "TC_138_139";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		String driverName = testData.foreName + " " + testData.surname;
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			// Select 'Insured and civil partner'
			for (int i = 0; i < testData.driverTypeList.length - 1; i++) {
				changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[i], extentedReport, true);
				changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);
				changePermittedDriversPage.addAdditionalDrivers(testData.sectionTypeList, testData.title,
						testData.foreName, testData.surname, testData.dateOfBirth, testData.dateOfLivingInUk,
						testData.gender, testData.maritalStatus, testData.relationship, testData.main_occupation,
						testData.main_business, testData.main_empStatus, testData.LicenceDate, testData.LicenceType,
						testData.qualification, testData.LicenceNumber, testData.reason, testData.carCount,
						testData.addionalCarCount, testData.yearCount, testData.householdCarCount,
						testData.frequencyOfUse, testData.isCommutingPurpose, testData.isIncreasedPremiumApplied,
						testData.isInsuranceRefused, testData.isSpecialTermsApplied, testData.hadCriminalConviction,
						extentedReport, true);

				changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[i + 1], extentedReport,
						true);
				Log.message("<b> Verification for TC_138</b>", driver, extentedReport);
				Log.softAssertThat(
						changePermittedDriversPage.verifyMessageInRemoveAddtionalDriverPopup(
								changePermittedDriversPage.DELETE_POPUP, extentedReport, true)
								&& changePermittedDriversPage.uielement.verifyPageElements(Arrays
										.asList("btnYesRemoveAddtionalDriverPopup", "btnNoRemoveAddtionalDriverPopup"),
										changePermittedDriversPage),
						"Message :'" + changePermittedDriversPage.DELETE_POPUP
								+ "' with 'Yes' & 'No' buttons are displayed when additional drivers is greater than 0 for driver type : "
								+ testData.driverTypeList[i],
						"Message :'" + changePermittedDriversPage.DELETE_POPUP
								+ "' with 'Yes' & 'No' buttons are not displayed when additional drivers is greater than 0 for driver type : "
								+ testData.driverTypeList[i],
						driver, extentedReport);

				Log.message("<b> Verification for TC_139</b>", driver, extentedReport);
				changePermittedDriversPage.clickYesOrNoInRemoveAdditionalDriverPopup("Yes", extentedReport, true);
				Log.softAssertThat(
						!changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnDelete"),
								changePermittedDriversPage),
						driverName + " : Added Additional drivers are removed",
						driverName + " : Added Additional drivers are not removed", driver, extentedReport, true);
			}

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the additional drivers are NOT removed when 'No' option is selected from the prompt message 'The change you just made will remove the additional drivers you already have from your policy. Are you sure you want to remove all of your additional drivers?'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 7)
	public void TC_140(String browser) throws Exception {
		String tcId = "TC_140";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		String driverName = testData.foreName + " " + testData.surname;
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			for (int i = 0; i < testData.driverTypeList.length - 1; i++) {
				changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[i], extentedReport, true);
				changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);
				changePermittedDriversPage.addAdditionalDrivers(testData.sectionTypeList, testData.title,
						testData.foreName, testData.surname, testData.dateOfBirth, testData.dateOfLivingInUk,
						testData.gender, testData.maritalStatus, testData.relationship, testData.main_occupation,
						testData.main_business, testData.main_empStatus, testData.LicenceDate, testData.LicenceType,
						testData.qualification, testData.LicenceNumber, testData.reason, testData.carCount,
						testData.addionalCarCount, testData.yearCount, testData.householdCarCount,
						testData.frequencyOfUse, testData.isCommutingPurpose, testData.isIncreasedPremiumApplied,
						testData.isInsuranceRefused, testData.isSpecialTermsApplied, testData.hadCriminalConviction,
						extentedReport, true);

				changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[i + 1], extentedReport,
						true);
				Log.softAssertThat(
						changePermittedDriversPage.verifyMessageInRemoveAddtionalDriverPopup(
								changePermittedDriversPage.DELETE_POPUP, extentedReport, true)
								&& changePermittedDriversPage.uielement.verifyPageElements(Arrays
										.asList("btnYesRemoveAddtionalDriverPopup", "btnNoRemoveAddtionalDriverPopup"),
										changePermittedDriversPage),
						"Message :'" + changePermittedDriversPage.DELETE_POPUP
								+ "' with 'Yes' & 'No' buttons are displayed when additional drivers is greater than 0 for driver type : "
								+ testData.driverTypeList[i],
						"Message :'" + changePermittedDriversPage.DELETE_POPUP
								+ "' with 'Yes' & 'No' buttons are not displayed when additional drivers is greater than 0 for driver type : "
								+ testData.driverTypeList[i],
						driver, extentedReport);

				changePermittedDriversPage.clickYesOrNoInRemoveAdditionalDriverPopup("No", extentedReport, true);
				Log.softAssertThat(
						changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnDelete"),
								changePermittedDriversPage),
						driverName + " : Added Additional drivers are removed",
						driverName + " : Added Additional drivers are not removed", driver, extentedReport, true);

				// To delete added driver
				changePermittedDriversPage.clickDeleteBtnOfPermittedDriver(testData.foreName, extentedReport, false);
				changePermittedDriversPage.clickYesOrNoBtnToDeleteDriver("Yes", extentedReport, false);
			}

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
			"Smoke" }, description = "Check that the client is able to add drivers when changing the drop down from 'Insured Only' to - Insured and Civil Partner,Insured and Spouse,Insured and Named Drivers", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 8)
	public void TC_141(String browser) throws Exception {

		String tcId = "TC_141";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[1], extentedReport, true);
			Log.softAssertThat(changePermittedDriversPage.uielement.verifyPageElements(
					Arrays.asList("btnAdditionalDriversYes", "btnAdditionalDriversNo"), changePermittedDriversPage),
					"Verified client is able to add drivers when changing the drop down from '"
							+ testData.driverTypeList[0] + "' to '" + testData.driverTypeList[1] + "'",
					"Client is not able to add drivers when changing the drop down from '" + testData.driverTypeList[0]
							+ "' to '" + testData.driverTypeList[1] + "'",
					driver, extentedReport, true);

			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[2], extentedReport, true);
			Log.softAssertThat(changePermittedDriversPage.uielement.verifyPageElements(
					Arrays.asList("btnAdditionalDriversYes", "btnAdditionalDriversNo"), changePermittedDriversPage),
					"Verified client is able to add drivers when changing the drop down from '"
							+ testData.driverTypeList[0] + "' to '" + testData.driverTypeList[2] + "'",
					"Client is not able to add drivers when changing the drop down from '" + testData.driverTypeList[0]
							+ "' to '" + testData.driverTypeList[2] + "'",
					driver, extentedReport, true);

			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[3], extentedReport, true);
			Log.softAssertThat(changePermittedDriversPage.uielement.verifyPageElements(
					Arrays.asList("btnAdditionalDriversYes", "btnAdditionalDriversNo"), changePermittedDriversPage),
					"Verified client is able to add drivers when changing the drop down from '"
							+ testData.driverTypeList[0] + "' to '" + testData.driverTypeList[2] + "'",
					"Client is not able to add drivers when changing the drop down from '" + testData.driverTypeList[0]
							+ "' to '" + testData.driverTypeList[3] + "'",
					driver, extentedReport, true);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the client is NOT able to delete drivers when changing the drop down from - Any Driver, Any Driver – Excluding Drivers Under 21, Any Driver – Excluding Drivers Under 25, Any Driver – Excluding Drivers Under 50, Insured Only TO Insured and Civil Partner, Insured and Spouse, Insured and Named Drivers", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 8)
	public void TC_142(String browser) throws Exception {

		String tcId = "TC_142";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			for (int i = 0; i < testData.driverTypeList.length - 3; i++) {
				// changing option to 'Insured and Civil
				// Partner'
				changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[i], extentedReport, true);
				changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[5], extentedReport, true);
				Log.softAssertThat(
						!changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnDelete"),
								changePermittedDriversPage),
						"Verified that client NOT able to delete drivers when changing the drop down from '"
								+ testData.driverTypeList[i] + "' to '" + testData.driverTypeList[5],
						"The client is able to delete drivers when changing the drop down from '"
								+ testData.driverTypeList[i] + "' to '" + testData.driverTypeList[5],
						driver, extentedReport, true);

				// changing option to ' Insured and Spouse'
				changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[i], extentedReport, true);
				changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[6], extentedReport, true);
				Log.softAssertThat(
						!changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnDelete"),
								changePermittedDriversPage),
						"Verified that client NOT able to delete drivers when changing the drop down from '"
								+ testData.driverTypeList[i] + "' to '" + testData.driverTypeList[6],
						"The client is able to delete drivers when changing the drop down from '"
								+ testData.driverTypeList[i] + "' to '" + testData.driverTypeList[6],
						driver, extentedReport, true);

				// changing option to ' Insured and Named driver'
				changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[i], extentedReport, true);
				changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[7], extentedReport, true);
				Log.softAssertThat(
						!changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnDelete"),
								changePermittedDriversPage),
						"Verified that client NOT able to delete drivers when changing the drop down from '"
								+ testData.driverTypeList[i] + "' to '" + testData.driverTypeList[7],
						"The client is able to delete drivers when changing the drop down '"
								+ testData.driverTypeList[i] + "' to '" + testData.driverTypeList[7],
						driver, extentedReport, true);
			}

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the additional drivers added are displayed in the below format - Name, Relationship to Proposer, Edit Delete buttons, Add Driver Button question", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 8)
	public void TC_143(String browser) throws Exception {

		String tcId = "TC_143";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		String driverName = testData.foreName + " " + testData.surname;
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			// Select driver type
			for (int i = 0; i < testData.driverTypeList.length - 1; i++) {
				changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[i], extentedReport, true);
				changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

				changePermittedDriversPage.addAdditionalDrivers(testData.sectionTypeList, testData.title,
						testData.foreName, testData.surname, testData.dateOfBirth, testData.dateOfLivingInUk,
						testData.gender, testData.maritalStatus, testData.relationshipList[i], testData.main_occupation,
						testData.main_business, testData.main_empStatus, testData.LicenceDate, testData.LicenceType,
						testData.qualification, testData.LicenceNumber, testData.reason, testData.carCount,
						testData.addionalCarCount, testData.yearCount, testData.householdCarCount,
						testData.frequencyOfUse, testData.isCommutingPurpose, testData.isIncreasedPremiumApplied,
						testData.isInsuranceRefused, testData.isSpecialTermsApplied, testData.hadCriminalConviction,
						extentedReport, true);

				Log.softAssertThat(
						changePermittedDriversPage.verifyDriverNameContainsRelationship(driverName,
								testData.relationshipList[i], extentedReport, false),
						driverName + " - additional driver is displayed with Relationaship - "
								+ testData.relationshipList[i],
						driverName + " - additional driver is not displayed with Relationaship - "
								+ testData.relationshipList[i],
						driver, extentedReport, true);

				Log.softAssertThat(
						changePermittedDriversPage.uielement
								.verifyPageElements(
										Arrays.asList("btnDelete", "btnEdit", "btnAdditionalDriversYes",
												"btnAdditionalDriversNo", "txtAddDriverQuestion"),
										changePermittedDriversPage),
						driverName
								+ " : Added additional driver is displayed with Edit and delete buttons , Add driver Button questions format",
						driverName
								+ " : Added additional driver is not displayed with Edit and delete buttons , Add driver Button questions format",
						driver, extentedReport, true);

				changePermittedDriversPage.clickDeleteBtnOfPermittedDriver(testData.foreName, extentedReport, false);
				changePermittedDriversPage.clickYesOrNoBtnToDeleteDriver("Yes", extentedReport, false);

			}
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "To Check that the 'Delete' button is displayed against the permitted driver and able to add driver when the number of additional drivers is greater than 0 and less than 5 with the following drop down selected -Insured and Named Drivers", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 5)
	public void TC_134_135(String browser) throws Exception {

		String tcId = "TC_134_135";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverType, extentedReport, true);
			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			changePermittedDriversPage.addAdditionalDrivers(testData.sectionTypeList, testData.title, testData.foreName,
					testData.surname, testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender,
					testData.maritalStatus, testData.relationship, testData.main_occupation, testData.main_business,
					testData.main_empStatus, testData.LicenceDate, testData.LicenceType, testData.qualification,
					testData.LicenceNumber, testData.reason, testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, testData.frequencyOfUse,
					testData.isCommutingPurpose, testData.isIncreasedPremiumApplied, testData.isInsuranceRefused,
					testData.isSpecialTermsApplied, testData.hadCriminalConviction, extentedReport, true);

			Log.message("<u> <b> Verification for TC_134</b> </u>", driver, extentedReport);
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnDelete"),
							changePermittedDriversPage),
					"Verified that delete button is displayed  against the permitted driver when the number of additional drivers is greater than 0 and less than 5",
					"delete button is not displayed  against the permitted driver when the number of additional drivers is greater than 0 and less than 5",
					driver, extentedReport, true);

			Log.message("<u> <b> Verification for TC_135</b> </u>", driver, extentedReport);
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAdditionalDriversYes"),
							changePermittedDriversPage),
					"Verified that Add driver option is displayed  against the permitted driver when the number of additional drivers is greater than 0 and less than 5",
					"Add driver option is not displayed against the permitted driver when the number of additional drivers is greater than 0 and less than 5",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the 'Delete' button is displayed against the permitted driver when the number of additional drivers is equal to 4 with the following drop down selected - Insured and Named Drivers and Check that the client is NOT able to 'Add driver' when the number of additional drivers is equal to 4 with the following drop down selected - Insured and Named Drivers and Check that 'You cannot add any further additional drivers. If you choose the option 'Insured and Named Drivers' against the question 'Who should be allowed to drive on your policy' then only 5 named drivers are permitted. To allow more than 5 drivers to use your vehicle, choose the option 'Any Driver'.' is displayed when additional driver is equal to 5 with following drop down selected - Insured and Named Drivers", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 6)
	public void TC_136_137_144(String browser) throws Exception {

		String tcId = "TC_136_137_144";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			Log.message("<b><u>Verification for TC_136</u></b>", driver, extentedReport);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);
			int count = changePermittedDriversPage.getAdditionalDriverCount(extentedReport, true);

			for (int i = count; i < 4; i++) {
				changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);
				changePermittedDriversPage.addAdditionalDrivers(testData.sectionTypeList, testData.title,
						testData.foreName, testData.surname, testData.dateOfBirth, testData.dateOfLivingInUk,
						testData.gender, testData.maritalStatus, testData.relationship, testData.main_occupation,
						testData.main_business, testData.main_empStatus, testData.LicenceDate, testData.LicenceType,
						testData.qualification, testData.LicenceNumber, testData.reason, testData.carCount,
						testData.addionalCarCount, testData.yearCount, testData.householdCarCount,
						testData.frequencyOfUse, testData.isCommutingPurpose, testData.isIncreasedPremiumApplied,
						testData.isInsuranceRefused, testData.isSpecialTermsApplied, testData.hadCriminalConviction,
						extentedReport, true);
			}

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnDelete"),
							changePermittedDriversPage),
					"Verified that delete button is displayed against the permitted driver  when the number of additional drivers is equal to 4",
					"delete button is not displayed  against the permitted driver  when the number of additional drivers is equal to 4",
					driver, extentedReport, true);

			Log.message("<b><u>Verification for TC_137</u></b>", driver, extentedReport);
			Log.softAssertThat(
					changePermittedDriversPage.verifyAddDriverYesOrNoBtnDisabled("Yes", extentedReport, true),
					"Verified that Add driver option is not displayed  against the permitted driver  when the number of additional drivers is equal to 4",
					"Add driver option is displayed against the permitted driver  when the number of additional drivers is equal to 4",
					driver, extentedReport, true);

			Log.message("<b><u> Verification for TC_144</u></b>", driver, extentedReport);
			Log.softAssertThat(changePermittedDriversPage.verifyBlockMessageForDrivers(
					changePermittedDriversPage.ADDITIONAL_DRIVER_MSG, testData.driverTypeList[0], extentedReport, true),
					changePermittedDriversPage.ADDITIONAL_DRIVER_MSG
							+ " - Message is displayed when additional driver is equal to 4 for '" + testData.driverTypeList[0]
							+ "'",
					changePermittedDriversPage.ADDITIONAL_DRIVER_MSG
							+ " - Message is not displayed when additional driver is equal to 4 for '"
							+ testData.driverTypeList[0] + "'",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that 'You cannot add any further additional drivers. If you choose the option ‘Insured and Civil Partner’ against the question ‘Who should be allowed to drive on your policy’ then only 1 named driver is permitted. To allow more than 1 drivers to use your vehicle, choose the option ‘Insured and Named Drivers’.' is displayed when additional driver is equal to 1 with following drop down selected - Insured and Civil Partner", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 9)
	public void TC_145(String browser) throws Exception {

		String tcId = "TC_145";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

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

			Log.softAssertThat(
					changePermittedDriversPage.verifyBlockMessageForDrivers(
							changePermittedDriversPage.INSURED_AND_CIVILPARTNER_DRIVER_MSG, testData.driverTypeList[0],
							extentedReport, true),
					changePermittedDriversPage.INSURED_AND_CIVILPARTNER_DRIVER_MSG
							+ " : Message is displayed when additional driver is equal to 1 with following drop down selected - '"
							+ testData.driverTypeList[0] + "'",
					changePermittedDriversPage.INSURED_AND_CIVILPARTNER_DRIVER_MSG
							+ " : Message is displayed when additional driver is equal to 1 with following drop down selected - '"
							+ testData.driverTypeList[0] + "'",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that 'You cannot add any further additional drivers. If you choose the option ‘Insured and Spouse’ against the question ‘Who should be allowed to drive on your policy’ then only 1 named driver is permitted. To allow more than 1 drivers to use your vehicle, choose the option ‘Insured and Named Drivers’.' is displayed when additional driver is equal to 1 with following drop down selected - Insured and Spouse", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 9)
	public void TC_146(String browser) throws Exception {

		String tcId = "TC_146";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

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

			Log.softAssertThat(
					changePermittedDriversPage.verifyBlockMessageForDrivers(
							changePermittedDriversPage.INSURED_AND_SPOUSE_DRIVER_MSG, testData.driverTypeList[0],
							extentedReport, true),
					changePermittedDriversPage.INSURED_AND_SPOUSE_DRIVER_MSG
							+ " : Message is displayed when additional driver is equal to 1 with following drop down selected - '"
							+ testData.driverTypeList[0] + "'",
					changePermittedDriversPage.INSURED_AND_SPOUSE_DRIVER_MSG
							+ " : Message is displayed when additional driver is equal to 1 with following drop down selected - '"
							+ testData.driverTypeList[0] + "'",
					driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// ******************* Story Id: 3677 ***************//

	@Test(description = "Check that 'Adding Named Driver' section is displayed when click on 'Yes' button for 'Do you want to add any more additional drivers?' question", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_148(String browser) throws Exception {
		String tcId = "TC_148";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);
			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("fldForename", "fldSurname"),
							changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);

			Log.softAssertThat(
					changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport,
							true),
					"Adding Named driver displayed when click on Yes button",
					"Adding Named driver not displayed when click on Yes button");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the client is able to enter DOB manually or by selecting from the calendar and also verify the 'Since Birth' button of 'When did the named driver begin living in the U.K.?'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_153_154(String browser) throws Exception {
		String tcId = "TC_153_154";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);
			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			// DOB verification
			Log.softAssertThat(changePermittedDriversPage.verifyDOBInDatePicker(extentedReport),
					"Verified that the user is able to enter DOB through date picker",
					"The user is not able to enter DOB through date picker", driver, extentedReport, false);
			Log.softAssertThat(
					changePermittedDriversPage.verifyDOBFieldIsEditable(testData.dateOfBirth, extentedReport),
					"Verified that the user is able to enter DOB manually",
					"The user is not able to enter DOB manually", driver, extentedReport, false);

			Log.softAssertThat(changePermittedDriversPage.verifySinceBirth(extentedReport),
					"Verified that the 'Since Birth' button of 'When did the named driver begin living in the U.K.?' works as expected",
					"The 'Since Birth' button of 'When did the named driver begin living in the U.K.?' is not working as expected",
					driver, extentedReport, false);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
			"Smoke" }, description = "Check that 'Occupations', 'Licence Details', 'Use of Other Vehicles', 'Use of the Insured Vehicle', 'Claims', 'Convictions', 'Medical & Disability Information', 'Adverse Terms' tabs are opened when click on Next button in previous tab", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_155_169_175_180_183_209_224_193(String browser) throws Exception {
		String tcId = "TC_155_169_175_180_183_209_224_193";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);
			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			// Verifying Personal details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectTitle"),
							changePermittedDriversPage),
					"Personal details section is expanded", "Personal details section is not expanded", driver,
					extentedReport, false);

			changePermittedDriversPage.enterPersonalDetails(testData.title, testData.foreName, testData.surname,
					testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender, testData.maritalStatus,
					testData.relationship, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying occupation details section is expanded or not

			Log.softAssertThat(

					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAddOccupation"),
							changePermittedDriversPage),
					"Occupation details section is expanded", "Occupation details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.addOccupation(testData.main_occupation, testData.main_business,
							testData.main_empStatus, true, extentedReport, true),
					"Verified that the main occupation is added successfully",
					"Adding the main occupation is unsuccessful", driver, extentedReport, false);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Verifying License details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("FldLicenseDate"),
							changePermittedDriversPage),
					"License details section is expanded", "License details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.enterLicenseDetails(testData.LicenceDate, testData.LicenceType,
					testData.qualification, testData.LicenceNumber, testData.reason, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Verifying Use of other vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectHowManyOwnCars"),
							changePermittedDriversPage),
					"Use of othet vechicle details section is expanded",
					"Use of othet vechicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfOtherVehicleDetails(testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Verifying Use of insured vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectFrequencyOfUse"),
							changePermittedDriversPage),
					"Use of the insured vehicle details section is expanded",
					"Use of the insured vehicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfInsuredVehicleDetails(testData.frequencyOfUse,
					testData.isCommutingPurpose, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, true);

			// Verifying claim details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInClaims"),
							changePermittedDriversPage),
					"Claim details section is expanded", "Claim details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[5], extentedReport, true);

			// Verifying Conviction details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInConvictions"),
							changePermittedDriversPage),
					"Conviction details section is expanded", "Conviction details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[6], extentedReport, true);

			// Verifying Medical information section is expanded or not

			changePermittedDriversPage.enterMedicalandDisabilityDetails(testData.isIncreasedPremiumApplied,
					testData.isInsuranceRefused, testData.isSpecialTermsApplied, extentedReport, true);
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInMedicalInfo"),
							changePermittedDriversPage),
					"Medical details section is expanded", "Medical details section is not expanded", driver,
					extentedReport, true);
			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[7], extentedReport, true);

			changePermittedDriversPage.enterAdverseAndTermsDetails(testData.isIncreasedPremiumApplied,
					testData.isInsuranceRefused, testData.isSpecialTermsApplied, testData.hadCriminalConviction,
					extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnSaveNamedDriver"),
							changePermittedDriversPage),
					"Adverse item details section is expanded", "Adverse item details section is not expanded", driver,
					extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that \"Add a New Occupation\" screen is opened when click on 'Add Occupation' button within Occupations tab and check that the 'Save Occupation' functionality is working", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_156_161_164_167_162(String browser) throws Exception {
		String tcId = "TC_156_161_164_167_162";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);

			Log.softAssertThat(changePermittedDriversPage.clickYesAddAdditionalDriver(extentedReport),
					"Verified that the section with title starts with the text 'Adding Named Driver' is opened up",
					"The section with title starts with the text 'Adding Named Driver' is not opened up", driver,
					extentedReport, false);

			// Personal Details
			changePermittedDriversPage.enterPersonalDetails(testData.title, testData.foreName, testData.surname,
					testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender, testData.maritalStatus,
					testData.relationship, extentedReport, true);
			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Main Occupation
			Log.softAssertThat(
					changePermittedDriversPage.addOccupation(testData.main_occupation, testData.main_business,
							testData.main_empStatus, true, extentedReport, true),
					"Verified that the main occupation is added successfully",
					"Adding the main occupation is unsuccessful", driver, extentedReport, false);

			Log.softAssertThat(
					changePermittedDriversPage.verifyOccupationDetails(testData.main_occupation, extentedReport, true),
					"Verified that the main occupation is added successfully",
					"Adding the main occupation is unsuccessful", driver, extentedReport, false);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
			"Smoke" }, description = "Check that not more than 2 occupations are set for a driver and in that 2, one needs to be set as main occupation", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_163(String browser) throws Exception {
		String tcId = "TC_163";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);
			Log.softAssertThat(changePermittedDriversPage.clickYesAddAdditionalDriver(extentedReport),
					"Verified that the section with title starts with the text 'Adding Named Driver' is opened up",
					"The section with title starts with the text 'Adding Named Driver' is not opened up", driver,
					extentedReport, false);

			// Personal Details
			changePermittedDriversPage.enterPersonalDetails(testData.title, testData.foreName, testData.surname,
					testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender, testData.maritalStatus,
					testData.relationship, extentedReport, true);
			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Main Occupation
			Log.softAssertThat(
					changePermittedDriversPage.addingMultipleOccupations(testData.main_occupation,
							testData.main_business, testData.main_empStatus, true, extentedReport, true),
					"More than 2 occupation not able to add for customer on change permitted driver page",
					"Able to add more than 2 occupations for customer on change permitted driver page", driver,
					extentedReport, false);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the user is able to edit the added occupation details when selecting 'Edit' button also check that the user is able to delete the added occupation details when selecting 'Delete' button", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_165_166(String browser) throws Exception {
		String tcId = "TC_165_166";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);
			Log.softAssertThat(changePermittedDriversPage.clickYesAddAdditionalDriver(extentedReport),
					"Verified that the section with title starts with the text 'Adding Named Driver' is opened up",
					"The section with title starts with the text 'Adding Named Driver' is not opened up", driver,
					extentedReport, false);

			// Personal Details
			changePermittedDriversPage.enterPersonalDetails(testData.title, testData.foreName, testData.surname,
					testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender, testData.maritalStatus,
					testData.relationship, extentedReport, true);
			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Main Occupation
			Log.softAssertThat(
					changePermittedDriversPage.addOccupation(testData.main_occupation, testData.main_business,
							testData.main_empStatus, true, extentedReport, true),
					"Verified that the main occupation is added successfully",
					"Adding the main occupation is unsuccessful", driver, extentedReport, false);
			changePermittedDriversPage.editOccupation(testData.main_occupation, testData.main_business,
					testData.other_empStatus, extentedReport, true);
			changePermittedDriversPage.deleteOccupation(testData.main_occupation, extentedReport, true);

			Log.softAssertThat(
					!changePermittedDriversPage.verifyOccupationDetails(testData.main_occupation, extentedReport, true),
					"Occupation details are removed successfully on change permitted drivers page",
					"Occupation details are not removed successfully on change permitted drivers page");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the user is not able to add multiple occupation details after proceeding with 'No' in 'Do you want to add another occupation?'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_168(String browser) throws Exception {
		String tcId = "TC_168";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);
			Log.softAssertThat(changePermittedDriversPage.clickYesAddAdditionalDriver(extentedReport),
					"Verified that the section with title starts with the text 'Adding Named Driver' is opened up",
					"The section with title starts with the text 'Adding Named Driver' is not opened up", driver,
					extentedReport, false);

			// Personal Details
			changePermittedDriversPage.enterPersonalDetails(testData.title, testData.foreName, testData.surname,
					testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender, testData.maritalStatus,
					testData.relationship, extentedReport, true);
			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.addOccupation(testData.main_occupation, testData.main_business,
							testData.main_empStatus, true, extentedReport, true),
					"Verified that the main occupation is added successfully",
					"Adding the main occupation is unsuccessful", driver, extentedReport, false);

			Log.softAssertThat(changePermittedDriversPage.noAnotherOccupation(extentedReport, true),
					"Unable to add occupation details when No button selected",
					"Able to add occupation details when No button selected ");

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check the save and cancel button functionality in 'Add a New Claim'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_184_188_189(String browser) throws Exception {
		String tcId = "TC_184_188_189";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);

			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			// Verifying Personal details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectTitle"),
							changePermittedDriversPage),
					"Personal details section is expanded", "Personal details section is not expanded", driver,
					extentedReport, false);

			changePermittedDriversPage.enterPersonalDetails(testData.title, testData.foreName, testData.surname,
					testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender, testData.maritalStatus,
					testData.relationship, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying occupation details section is expanded or not

			Log.softAssertThat(

					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAddOccupation"),
							changePermittedDriversPage),
					"Occupation details section is expanded", "Occupation details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.addOccupation(testData.main_occupation, testData.main_business,
							testData.main_empStatus, true, extentedReport, true),
					"Verified that the main occupation is added successfully",
					"Adding the main occupation is unsuccessful", driver, extentedReport, false);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Verifying License details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("FldLicenseDate"),
							changePermittedDriversPage),
					"License details section is expanded", "License details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.enterLicenseDetails(testData.LicenceDate, testData.LicenceType,
					testData.qualification, testData.LicenceNumber, testData.reason, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Verifying Use of other vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectHowManyOwnCars"),
							changePermittedDriversPage),
					"Use of othet vechicle details section is expanded",
					"Use of othet vechicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfOtherVehicleDetails(testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Verifying Use of insured vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectFrequencyOfUse"),
							changePermittedDriversPage),
					"Use of the insured vehicle details section is expanded",
					"Use of the insured vehicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfInsuredVehicleDetails(testData.frequencyOfUse,
					testData.isCommutingPurpose, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, true);

			// Verifying claim details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInClaims"),
							changePermittedDriversPage),
					"Claim details section is expanded", "Claim details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.fillClaimDetailsAndCancel(testData.claimType, testData.claimDate,
							testData.claimCostDriver, testData.claimCostThirdParty, testData.injuryCost,
							testData.driverLostDiscount, testData.driverAtFault, testData.claimSettled,
							testData.claimMadeOnThisPolicy, extentedReport, true),
					"Verified that the claim is not added successfully after clicking 'Cancel' button",
					"The claim is added unexpectedly", driver, extentedReport, false);

			Log.softAssertThat(
					changePermittedDriversPage.addClaim(testData.claimType, testData.claimDate,
							testData.claimCostDriver, testData.claimCostThirdParty, testData.injuryCost,
							testData.driverLostDiscount, testData.driverAtFault, testData.claimSettled,
							testData.claimMadeOnThisPolicy, extentedReport, true),
					"Verified that the claim is added successfully", "Adding a claim is unsuccessful", driver,
					extentedReport, false);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check the edit and delete button functionality in 'Claims' section", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_190_191(String browser) throws Exception {
		String tcId = "TC_190_191";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);

			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			// Verifying Personal details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectTitle"),
							changePermittedDriversPage),
					"Personal details section is expanded", "Personal details section is not expanded", driver,
					extentedReport, false);

			changePermittedDriversPage.enterPersonalDetails(testData.title, testData.foreName, testData.surname,
					testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender, testData.maritalStatus,
					testData.relationship, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying occupation details section is expanded or not

			Log.softAssertThat(

					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAddOccupation"),
							changePermittedDriversPage),
					"Occupation details section is expanded", "Occupation details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.addOccupation(testData.main_occupation, testData.main_business,
							testData.main_empStatus, true, extentedReport, true),
					"Verified that the main occupation is added successfully",
					"Adding the main occupation is unsuccessful", driver, extentedReport, false);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Verifying License details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("FldLicenseDate"),
							changePermittedDriversPage),
					"License details section is expanded", "License details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.enterLicenseDetails(testData.LicenceDate, testData.LicenceType,
					testData.qualification, testData.LicenceNumber, testData.reason, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Verifying Use of other vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectHowManyOwnCars"),
							changePermittedDriversPage),
					"Use of othet vechicle details section is expanded",
					"Use of othet vechicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfOtherVehicleDetails(testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Verifying Use of insured vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectFrequencyOfUse"),
							changePermittedDriversPage),
					"Use of the insured vehicle details section is expanded",
					"Use of the insured vehicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfInsuredVehicleDetails(testData.frequencyOfUse,
					testData.isCommutingPurpose, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, true);

			// Verifying claim details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInClaims"),
							changePermittedDriversPage),
					"Claim details section is expanded", "Claim details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.addClaim(testData.claimType, testData.claimDate,
							testData.claimCostDriver, testData.claimCostThirdParty, testData.injuryCost,
							testData.driverLostDiscount, testData.driverAtFault, testData.claimSettled,
							testData.claimMadeOnThisPolicy, extentedReport, true),
					"Verified that the claim is added successfully", "Adding a claim is unsuccessful", driver,
					extentedReport, false);

			Log.softAssertThat(
					changePermittedDriversPage.editClaim(testData.claimType2, testData.claimDate,
							testData.claimCostDriver, testData.claimCostThirdParty, testData.injuryCost,
							testData.driverLostDiscount, testData.driverAtFault, testData.claimSettled,
							testData.claimMadeOnThisPolicy, extentedReport, true),
					"Verified that the claim is edited successfully", "Editing the claim is unsuccessful", driver,
					extentedReport, false);

			Log.softAssertThat(!changePermittedDriversPage.deleteClaim(extentedReport, true),
					"Verified that the claim is deleted successfully", "Deleting the claim is unsuccessful", driver,
					extentedReport, false);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check if user is able to add multiple claims", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_192(String browser) throws Exception {
		String tcId = "TC_192";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);

			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			// Verifying Personal details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectTitle"),
							changePermittedDriversPage),
					"Personal details section is expanded", "Personal details section is not expanded", driver,
					extentedReport, false);

			changePermittedDriversPage.enterPersonalDetails(testData.title, testData.foreName, testData.surname,
					testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender, testData.maritalStatus,
					testData.relationship, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying occupation details section is expanded or not

			Log.softAssertThat(

					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAddOccupation"),
							changePermittedDriversPage),
					"Occupation details section is expanded", "Occupation details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.addOccupation(testData.main_occupation, testData.main_business,
							testData.main_empStatus, true, extentedReport, true),
					"Verified that the main occupation is added successfully",
					"Adding the main occupation is unsuccessful", driver, extentedReport, false);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Verifying License details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("FldLicenseDate"),
							changePermittedDriversPage),
					"License details section is expanded", "License details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.enterLicenseDetails(testData.LicenceDate, testData.LicenceType,
					testData.qualification, testData.LicenceNumber, testData.reason, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Verifying Use of other vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectHowManyOwnCars"),
							changePermittedDriversPage),
					"Use of othet vechicle details section is expanded",
					"Use of othet vechicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfOtherVehicleDetails(testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Verifying Use of insured vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectFrequencyOfUse"),
							changePermittedDriversPage),
					"Use of the insured vehicle details section is expanded",
					"Use of the insured vehicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfInsuredVehicleDetails(testData.frequencyOfUse,
					testData.isCommutingPurpose, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, true);

			// Verifying claim details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInClaims"),
							changePermittedDriversPage),
					"Claim details section is expanded", "Claim details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.addMultipleClaims(testData.claimType, testData.claimType2,
							testData.claimDate, testData.claimCostDriver, testData.claimCostThirdParty,
							testData.injuryCost, testData.driverLostDiscount, testData.driverAtFault,
							testData.claimSettled, testData.claimMadeOnThisPolicy, extentedReport, true),
					"Verified that able to add multiple claims on change permitted driver",
					"verified that uanble to add mulitple claims on change permitted driver", driver, extentedReport,
					false);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
			"Smoke" }, description = "Check the yes and no option in 'Do you want to add any more convictions?' and also check the save and cancel button functionality in 'Add a New Conviction'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_195_203_204_205(String browser) throws Exception {
		String tcId = "TC_195_203_204_205";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);

			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			// Verifying Personal details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectTitle"),
							changePermittedDriversPage),
					"Personal details section is expanded", "Personal details section is not expanded", driver,
					extentedReport, false);

			changePermittedDriversPage.enterPersonalDetails(testData.title, testData.foreName, testData.surname,
					testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender, testData.maritalStatus,
					testData.relationship, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying occupation details section is expanded or not

			Log.softAssertThat(

					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAddOccupation"),
							changePermittedDriversPage),
					"Occupation details section is expanded", "Occupation details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.addOccupation(testData.main_occupation, testData.main_business,
							testData.main_empStatus, true, extentedReport, true),
					"Verified that the main occupation is added successfully",
					"Adding the main occupation is unsuccessful", driver, extentedReport, false);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Verifying License details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("FldLicenseDate"),
							changePermittedDriversPage),
					"License details section is expanded", "License details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.enterLicenseDetails(testData.LicenceDate, testData.LicenceType,
					testData.qualification, testData.LicenceNumber, testData.reason, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Verifying Use of other vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectHowManyOwnCars"),
							changePermittedDriversPage),
					"Use of othet vechicle details section is expanded",
					"Use of othet vechicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfOtherVehicleDetails(testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Verifying Use of insured vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectFrequencyOfUse"),
							changePermittedDriversPage),
					"Use of the insured vehicle details section is expanded",
					"Use of the insured vehicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfInsuredVehicleDetails(testData.frequencyOfUse,
					testData.isCommutingPurpose, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, true);

			// Verifying claim details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInClaims"),
							changePermittedDriversPage),
					"Claim details section is expanded", "Claim details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[5], extentedReport, true);

			// Verifying Conviction details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInConvictions"),
							changePermittedDriversPage),
					"Conviction details section is expanded", "Conviction details section is not expanded", driver,
					extentedReport, true);
			// Convictions

			Log.softAssertThat(changePermittedDriversPage.verifyNoOptionOfAddMoreConviction(extentedReport, true),
					"Verified that the section with title 'Add a New Conviction' is not shown when selecting 'No' option",
					"The section with title 'Add a New Conviction' is shown unexpectedly", driver, extentedReport,
					false);

			Log.softAssertThat(
					changePermittedDriversPage.fillConvictionDetailsAndCancel(testData.isMotorProsecutionPending,
							testData.convictionDate, testData.convictionCode, testData.penaltyPoints,
							testData.wasDriverSuspended, testData.isConvictionLinkedToClaim, testData.chooseClaim,
							testData.fineAmount, extentedReport, true),
					"Verified that the conviction is not added when cancel button is selected",
					"The conviction is added unexpectedly", driver, extentedReport, false);

			Log.softAssertThat(
					changePermittedDriversPage.addConviction(testData.isMotorProsecutionPending,
							testData.convictionDate, testData.convictionCode, testData.penaltyPoints,
							testData.wasDriverSuspended, testData.isConvictionLinkedToClaim, testData.chooseClaim,
							extentedReport, true),
					"Verified that the conviction is added successfully", "Adding a conviction is unsuccessful", driver,
					extentedReport, false);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check the edit and delete button functionality in 'Convictions' section", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_206_207(String browser) throws Exception {
		String tcId = "TC_206_207";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);

			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			// Verifying Personal details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectTitle"),
							changePermittedDriversPage),
					"Personal details section is expanded", "Personal details section is not expanded", driver,
					extentedReport, false);

			changePermittedDriversPage.enterPersonalDetails(testData.title, testData.foreName, testData.surname,
					testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender, testData.maritalStatus,
					testData.relationship, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying occupation details section is expanded or not
			Log.softAssertThat(

					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAddOccupation"),
							changePermittedDriversPage),
					"Occupation details section is expanded", "Occupation details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.addOccupation(testData.main_occupation, testData.main_business,
							testData.main_empStatus, true, extentedReport, true),
					"Verified that the main occupation is added successfully",
					"Adding the main occupation is unsuccessful", driver, extentedReport, false);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Verifying License details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("FldLicenseDate"),
							changePermittedDriversPage),
					"License details section is expanded", "License details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.enterLicenseDetails(testData.LicenceDate, testData.LicenceType,
					testData.qualification, testData.LicenceNumber, testData.reason, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Verifying Use of other vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectHowManyOwnCars"),
							changePermittedDriversPage),
					"Use of othet vechicle details section is expanded",
					"Use of othet vechicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfOtherVehicleDetails(testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Verifying Use of insured vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectFrequencyOfUse"),
							changePermittedDriversPage),
					"Use of the insured vehicle details section is expanded",
					"Use of the insured vehicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfInsuredVehicleDetails(testData.frequencyOfUse,
					testData.isCommutingPurpose, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, true);

			// Verifying claim details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInClaims"),
							changePermittedDriversPage),
					"Claim details section is expanded", "Claim details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[5], extentedReport, true);

			// Verifying Conviction details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInConvictions"),
							changePermittedDriversPage),
					"Conviction details section is expanded", "Conviction details section is not expanded", driver,
					extentedReport, true);
			// Convictions

			Log.softAssertThat(changePermittedDriversPage.verifyNoOptionOfAddMoreConviction(extentedReport, true),
					"Verified that the section with title 'Add a New Conviction' is not shown when selecting 'No' option",
					"The section with title 'Add a New Conviction' is shown unexpectedly", driver, extentedReport,
					false);

			Log.softAssertThat(
					changePermittedDriversPage.fillConvictionDetailsAndCancel(testData.isMotorProsecutionPending,
							testData.convictionDate, testData.convictionCode, testData.penaltyPoints,
							testData.wasDriverSuspended, testData.isConvictionLinkedToClaim, testData.chooseClaim,
							testData.fineAmount, extentedReport, true),
					"Verified that the conviction is not added successfully", "The conviction is added unexpectedly",
					driver, extentedReport, false);

			Log.softAssertThat(
					changePermittedDriversPage.addConviction(testData.isMotorProsecutionPending,
							testData.convictionDate, testData.convictionCode, testData.penaltyPoints,
							testData.wasDriverSuspended, testData.isConvictionLinkedToClaim, testData.chooseClaim,
							extentedReport, true),
					"Verified that the conviction is added successfully", "Adding a conviction is unsuccessful", driver,
					extentedReport, false);
			Log.softAssertThat(
					changePermittedDriversPage.editConviction(testData.isMotorProsecutionPending,
							testData.convictionDate, testData.convictionCode2, testData.penaltyPoints,
							testData.wasDriverSuspended, testData.isConvictionLinkedToClaim, testData.chooseClaim,
							extentedReport, true),
					"Verified that the conviction is edited successfully", "Editing a conviction is unsuccessful",
					driver, extentedReport, false);

			Log.softAssertThat(!changePermittedDriversPage.deleteConviction(extentedReport, true),
					"Verified that the conviction is deleted successfully", "Deleting a conviction is unsuccessful",
					driver, extentedReport, true);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check the edit and delete button functionality in 'Convictions' section", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_208(String browser) throws Exception {
		String tcId = "TC_208";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);

			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			// Verifying Personal details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectTitle"),
							changePermittedDriversPage),
					"Personal details section is expanded", "Personal details section is not expanded", driver,
					extentedReport, false);

			changePermittedDriversPage.enterPersonalDetails(testData.title, testData.foreName, testData.surname,
					testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender, testData.maritalStatus,
					testData.relationship, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying occupation details section is expanded or not
			Log.softAssertThat(

					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAddOccupation"),
							changePermittedDriversPage),
					"Occupation details section is expanded", "Occupation details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.addOccupation(testData.main_occupation, testData.main_business,
							testData.main_empStatus, true, extentedReport, true),
					"Verified that the main occupation is added successfully",
					"Adding the main occupation is unsuccessful", driver, extentedReport, false);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Verifying License details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("FldLicenseDate"),
							changePermittedDriversPage),
					"License details section is expanded", "License details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.enterLicenseDetails(testData.LicenceDate, testData.LicenceType,
					testData.qualification, testData.LicenceNumber, testData.reason, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Verifying Use of other vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectHowManyOwnCars"),
							changePermittedDriversPage),
					"Use of othet vechicle details section is expanded",
					"Use of othet vechicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfOtherVehicleDetails(testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Verifying Use of insured vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectFrequencyOfUse"),
							changePermittedDriversPage),
					"Use of the insured vehicle details section is expanded",
					"Use of the insured vehicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfInsuredVehicleDetails(testData.frequencyOfUse,
					testData.isCommutingPurpose, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, true);

			// Verifying claim details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInClaims"),
							changePermittedDriversPage),
					"Claim details section is expanded", "Claim details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[5], extentedReport, true);

			// Verifying Conviction details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInConvictions"),
							changePermittedDriversPage),
					"Conviction details section is expanded", "Conviction details section is not expanded", driver,
					extentedReport, true);
			// Convictions
			Log.softAssertThat(changePermittedDriversPage.verifyNoOptionOfAddMoreConviction(extentedReport, true),
					"Verified that the section with title 'Add a New Conviction' is not shown when selecting 'No' option",
					"The section with title 'Add a New Conviction' is shown unexpectedly", driver, extentedReport,
					false);

			Log.softAssertThat(
					changePermittedDriversPage.fillConvictionDetailsAndCancel(testData.isMotorProsecutionPending,
							testData.convictionDate, testData.convictionCode, testData.penaltyPoints,
							testData.wasDriverSuspended, testData.isConvictionLinkedToClaim, testData.chooseClaim,
							testData.fineAmount, extentedReport, true),
					"Verified that the conviction is not added successfully", "The conviction is added unexpectedly",
					driver, extentedReport, false);

			Log.softAssertThat(
					changePermittedDriversPage.addMultipleConvictions(testData.isMotorProsecutionPending,
							testData.convictionDate, testData.convictionCode, testData.convictionCode2,
							testData.penaltyPoints, testData.wasDriverSuspended, testData.isConvictionLinkedToClaim,
							testData.chooseClaim, extentedReport, true),
					"Verified that the multiple convictions details are add on change permitted driver",
					"Verified that not able to add multiple convictions details are add on change permitted driver",
					driver, extentedReport, false);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check the yes and no option in 'Do you want to add any more medical conditions?' and also check the save and cancel button functionality in 'Add a Medical Condition'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_213_218_219_220(String browser) throws Exception {
		String tcId = "TC_213_218_219_220";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);

			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			// Verifying Personal details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectTitle"),
							changePermittedDriversPage),
					"Personal details section is expanded", "Personal details section is not expanded", driver,
					extentedReport, false);

			changePermittedDriversPage.enterPersonalDetails(testData.title, testData.foreName, testData.surname,
					testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender, testData.maritalStatus,
					testData.relationship, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying occupation details section is expanded or not

			Log.softAssertThat(

					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAddOccupation"),
							changePermittedDriversPage),
					"Occupation details section is expanded", "Occupation details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.addOccupation(testData.main_occupation, testData.main_business,
							testData.main_empStatus, true, extentedReport, true),
					"Verified that the main occupation is added successfully",
					"Adding the main occupation is unsuccessful", driver, extentedReport, false);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Verifying License details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("FldLicenseDate"),
							changePermittedDriversPage),
					"License details section is expanded", "License details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.enterLicenseDetails(testData.LicenceDate, testData.LicenceType,
					testData.qualification, testData.LicenceNumber, testData.reason, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Verifying Use of other vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectHowManyOwnCars"),
							changePermittedDriversPage),
					"Use of othet vechicle details section is expanded",
					"Use of othet vechicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfOtherVehicleDetails(testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Verifying Use of insured vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectFrequencyOfUse"),
							changePermittedDriversPage),
					"Use of the insured vehicle details section is expanded",
					"Use of the insured vehicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfInsuredVehicleDetails(testData.frequencyOfUse,
					testData.isCommutingPurpose, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, true);

			// Verifying claim details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInClaims"),
							changePermittedDriversPage),
					"Claim details section is expanded", "Claim details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[5], extentedReport, true);

			// Verifying Conviction details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInConvictions"),
							changePermittedDriversPage),
					"Conviction details section is expanded", "Conviction details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[6], extentedReport, true);

			// Verifying Medical information section is expanded or not

			// changePermittedDriversPage.enterMedicalandDisabilityDetails(isIncreasedPremiumApplied,
			// isInsuranceRefused, isSpecialTermsApplied, extentedReport, true);
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInMedicalInfo"),
							changePermittedDriversPage),
					"Medical details section is expanded", "Medical details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(changePermittedDriversPage.fillConditionDetailsAndCancel(testData.medicalCondition,
					testData.medicalConditionFirstDiagnosed, testData.haveDVLABeenInformed, extentedReport, true),
					"Verified that the medical condition is added details are removed successfully",
					"Verified that the medical condition is added details are not removed", driver, extentedReport,
					false);

			Log.softAssertThat(changePermittedDriversPage.addMedicalCondition(testData.medicalCondition,
					testData.medicalConditionFirstDiagnosed, testData.medicalConditionLastOccured, testData.haveDVLABeenInformed, extentedReport, true),
					"Verified that the condition is added successfully", "Adding a condition is unsuccessful", driver,
					extentedReport, false);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check the edit and delete button functionality in 'Medical & Disability Information' section", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_221_222(String browser) throws Exception {
		String tcId = "TC_221_222";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);

			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			// Verifying Personal details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectTitle"),
							changePermittedDriversPage),
					"Personal details section is expanded", "Personal details section is not expanded", driver,
					extentedReport, false);

			changePermittedDriversPage.enterPersonalDetails(testData.title, testData.foreName, testData.surname,
					testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender, testData.maritalStatus,
					testData.relationship, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying occupation details section is expanded or not

			Log.softAssertThat(

					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAddOccupation"),
							changePermittedDriversPage),
					"Occupation details section is expanded", "Occupation details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.addOccupation(testData.main_occupation, testData.main_business,
							testData.main_empStatus, true, extentedReport, true),
					"Verified that the main occupation is added successfully",
					"Adding the main occupation is unsuccessful", driver, extentedReport, false);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Verifying License details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("FldLicenseDate"),
							changePermittedDriversPage),
					"License details section is expanded", "License details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.enterLicenseDetails(testData.LicenceDate, testData.LicenceType,
					testData.qualification, testData.LicenceNumber, testData.reason, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Verifying Use of other vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectHowManyOwnCars"),
							changePermittedDriversPage),
					"Use of othet vechicle details section is expanded",
					"Use of othet vechicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfOtherVehicleDetails(testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Verifying Use of insured vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectFrequencyOfUse"),
							changePermittedDriversPage),
					"Use of the insured vehicle details section is expanded",
					"Use of the insured vehicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfInsuredVehicleDetails(testData.frequencyOfUse,
					testData.isCommutingPurpose, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, true);

			// Verifying claim details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInClaims"),
							changePermittedDriversPage),
					"Claim details section is expanded", "Claim details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[5], extentedReport, true);

			// Verifying Conviction details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInConvictions"),
							changePermittedDriversPage),
					"Conviction details section is expanded", "Conviction details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[6], extentedReport, true);

			// Verifying Medical information section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInMedicalInfo"),
							changePermittedDriversPage),
					"Medical details section is expanded", "Medical details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(changePermittedDriversPage.addMedicalCondition(testData.medicalCondition,
					testData.medicalConditionFirstDiagnosed, testData.medicalConditionLastOccured, testData.haveDVLABeenInformed, extentedReport, true),
					"Verified that the condition is added successfully", "Adding a condition is unsuccessful", driver,
					extentedReport, false);
			Log.softAssertThat(changePermittedDriversPage.editMedicalCondition(testData.medicalCondition,
					testData.medicalConditionFirstDiagnosed, testData.medicalConditionLastOccured, testData.haveDVLABeenInformed, extentedReport, true),
					"Verified that the condition is edited successfully", "Editing a condition is unsuccessful", driver,
					extentedReport, false);

			Log.softAssertThat(changePermittedDriversPage.deleteMedicalCondition(extentedReport, true),
					"Verified that the condition is deleted successfully", "Deleting a condition is unsuccessful",
					driver, extentedReport, false);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check if user able to add multiple conditions ", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_223(String browser) throws Exception {
		String tcId = "TC_223";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(
							Arrays.asList("txtTitleChangePermittedDrivers"), changePermittedDriversPage),
					"Change Permitted Drivers Page is verified", "Change Permitted Drivers Page is not verified",
					driver, extentedReport, false);
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);

			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			// Verifying Personal details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectTitle"),
							changePermittedDriversPage),
					"Personal details section is expanded", "Personal details section is not expanded", driver,
					extentedReport, false);

			changePermittedDriversPage.enterPersonalDetails(testData.title, testData.foreName, testData.surname,
					testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender, testData.maritalStatus,
					testData.relationship, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

			// Verifying occupation details section is expanded or not

			Log.softAssertThat(

					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnAddOccupation"),
							changePermittedDriversPage),
					"Occupation details section is expanded", "Occupation details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.addOccupation(testData.main_occupation, testData.main_business,
							testData.main_empStatus, true, extentedReport, true),
					"Verified that the main occupation is added successfully",
					"Adding the main occupation is unsuccessful", driver, extentedReport, false);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

			// Verifying License details section is expanded or not

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("FldLicenseDate"),
							changePermittedDriversPage),
					"License details section is expanded", "License details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.enterLicenseDetails(testData.LicenceDate, testData.LicenceType,
					testData.qualification, testData.LicenceNumber, testData.reason, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

			// Verifying Use of other vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectHowManyOwnCars"),
							changePermittedDriversPage),
					"Use of othet vechicle details section is expanded",
					"Use of othet vechicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfOtherVehicleDetails(testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

			// Verifying Use of insured vehicle section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("drpSelectFrequencyOfUse"),
							changePermittedDriversPage),
					"Use of the insured vehicle details section is expanded",
					"Use of the insured vehicle details section is not expanded", driver, extentedReport, true);

			changePermittedDriversPage.enterUseOfInsuredVehicleDetails(testData.frequencyOfUse,
					testData.isCommutingPurpose, extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[4], extentedReport, true);

			// Verifying claim details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInClaims"),
							changePermittedDriversPage),
					"Claim details section is expanded", "Claim details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[5], extentedReport, true);

			// Verifying Conviction details section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInConvictions"),
							changePermittedDriversPage),
					"Conviction details section is expanded", "Conviction details section is not expanded", driver,
					extentedReport, true);

			changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[6], extentedReport, true);

			// Verifying Medical information section is expanded or not
			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("btnNextInMedicalInfo"),
							changePermittedDriversPage),
					"Medical details section is expanded", "Medical details section is not expanded", driver,
					extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.addMultipleMedicalConditions(testData.medicalCondition,
							testData.medicalCondition2, testData.medicalConditionFirstDiagnosed, testData.medicalConditionLastOccured, 
							testData.haveDVLABeenInformed, extentedReport, true),
					"Verified that able to add mulitple medical conditions on change permitted drivers page",
					"Verified that not able to add multiple medical conditions on change permitted drivers page",
					driver, extentedReport, false);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the driver is saved and listed under Permitted Drivers section when click on \"Save Named Driver\" button from 'Adverse Terms' tab", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_229(String browser) throws Exception {
		String tcId = "TC_229";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		String driverName = testData.foreName + " " + testData.surname;
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

			changePermittedDriversPage.addAdditionalDrivers(testData.sectionTypeList, testData.title, testData.foreName,
					testData.surname, testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender,
					testData.maritalStatus, testData.relationship, testData.main_occupation, testData.main_business,
					testData.main_empStatus, testData.LicenceDate, testData.LicenceType, testData.qualification,
					testData.LicenceNumber, testData.reason, testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, testData.frequencyOfUse,
					testData.isCommutingPurpose, testData.isIncreasedPremiumApplied, testData.isInsuranceRefused,
					testData.isSpecialTermsApplied, testData.hadCriminalConviction, extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.verifyDriverNameContainsRelationship(driverName, testData.relationship,
							extentedReport, true),
					"The new driver name is displaying in the drivers list",
					"Verified that the new driver name is not displayed in drivers list", driver, extentedReport,
					false);
			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on 'Cancel' button against the 'Adding Named Driver ' prompts a warning message Clicking cancel will remove all the edits you have just made to your named driver’s details. Are You sure you want to delete this information?", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_230(String browser) throws Exception {

		String tcId = "TC_230";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);

			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();
			changePermittedDriversPage.selectDriverTypeDropdown(testData.driverTypeList[0], extentedReport, true);

			changePermittedDriversPage.clickYesOrNoForAdditionalDriver("Yes", extentedReport, true);

			// Personal details
			changePermittedDriversPage.enterPersonalDetails(testData.title, testData.foreName, testData.surname,
					testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender, testData.maritalStatus,
					testData.relationship, extentedReport, true);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0,0);");

			changePermittedDriversPage.selectCancelOnContact(extentedReport);

			Log.softAssertThat(changePermittedDriversPage.isWarningMsgOnCntctDelete(extentedReport),
					"Warning message displaying when cancel button selected while adding new driver",
					"Warning message displaying when cancel button selected while adding new driver", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the user is able to Edit the added drivers", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
    public void TC_231(String browser) throws Exception {
           String tcId = "TC_231";
           final WebDriver driver = WebDriverFactory.get(browser);
           GetTestData testData = new GetTestData(sheetName, tcId);
           String driverName = testData.foreName + " " + testData.surname;
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

                  changePermittedDriversPage.clickEditBtnOfPermittedDriver(driverName, extentedReport, true);

                  // Verify Personal detail
                  HashMap<String, String> expectedPersonalDetail = new HashMap<String, String>();
                  expectedPersonalDetail.put("Title", testData.title);
                  expectedPersonalDetail.put("ForeName", testData.foreName);
                  expectedPersonalDetail.put("SurName", testData.surname);
                  expectedPersonalDetail.put("DOB", testData.dateOfBirth);
                  expectedPersonalDetail.put("DateOfLiving", testData.dateOfLivingInUk);
                  expectedPersonalDetail.put("Gender", testData.gender);
                  expectedPersonalDetail.put("MaritalStatus", testData.maritalStatus);
                  expectedPersonalDetail.put("Relationship", testData.relationship);

                  Log.softAssertThat(changePermittedDriversPage.verifyPersonalDetail(expectedPersonalDetail, extentedReport),
                               "All the saved data are reflected correctly and user is able to edit the data in 'Personal detail' section",
                               "All the saved data are not reflected correctly and user is unable to edit the data in 'Personal detail' section",
                               driver, extentedReport, true);
                  changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[0], extentedReport, true);

                  // Occuptation
                  Log.softAssertThat(
                               changePermittedDriversPage.verifyOccupationDetails(testData.main_occupation, extentedReport, false),
                               "All the saved data are reflected correctly and user is able to edit the data in 'Occupation detail' section",
                               "All the saved data are not reflected correctly and user is unable to edit the data in 'Occupation detail' section",
                               driver, extentedReport, true);
                  changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[1], extentedReport, true);

                  // Verify licence Detail
                  HashMap<String, String> expectedlicenceDetail = new HashMap<String, String>();
                  expectedlicenceDetail.put("LicenceDate", testData.LicenceDate);
                  expectedlicenceDetail.put("LicenceType", testData.LicenceType);
                  expectedlicenceDetail.put("AdditionalDrivingQualification", testData.qualification);
                  expectedlicenceDetail.put("DateAttained", testData.LicenceDate);

                  Log.softAssertThat(changePermittedDriversPage.verifyLicenseDetail(expectedlicenceDetail, extentedReport),
                               "All the saved data are reflected correctly and user is able to edit the data in 'licence detail' section",
                               "All the saved data are not reflected correctly and user is unable to edit the data in 'licence detail' section",
                               driver, extentedReport, true);
                  
                  changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[2], extentedReport, true);

                  // Verify Use Of Other Vehicles
                  HashMap<String, String> expectedUseOfOtherVehicles = new HashMap<String, String>();
                  expectedUseOfOtherVehicles.put("OwnCars", testData.carCount);
                  expectedUseOfOtherVehicles.put("AdditionalCars", testData.addionalCarCount);
                  //expectedUseOfOtherVehicles.put("HouseholdCar", testData.householdCarCount);
                  expectedUseOfOtherVehicles.put("YearCount", testData.yearCount);
                  System.out.println("bug");

                  Log.softAssertThat(
                               changePermittedDriversPage.verifyUseOfOtherVehiclesDetail(expectedUseOfOtherVehicles,
                                             extentedReport),
                               "All the saved data are reflected correctly and user is able to edit the data in 'Use Of Other Vehicles' detail section",
                               "All the saved data are not reflected correctly and user is unable to edit the data in 'Use Of Other Vehicles' detail section",
                               driver, extentedReport, true);
                  changePermittedDriversPage.clickNextButtonInSection(testData.sectionTypeList[3], extentedReport, true);

                  // UseOfInsuredVehicleDetails
                  Log.softAssertThat(changePermittedDriversPage.verifyFrequencyOfUse(testData.frequencyOfUse, extentedReport),
                               "All the saved data are reflected correctly and user is able to edit the data in 'Use Of Other Vehicles' detail section",
                               "All the saved data are not reflected correctly and user is unable to edit the data in 'Use Of Other Vehicles' detail section",
                               driver, extentedReport, true);

                  Log.testCaseResult(extentedReport);

           } catch (Exception e) {
                  Log.exception(e, driver, extentedReport);
           } finally {
                  Log.endTestCase(extentedReport);
                  driver.quit();
           }
    }


	// ******************* Story Id: 3678 ***************//

	@Test(description = "Check that 'Clicking delete will remove one of your named drivers. Are you sure you want to delete this driver ? with Yes and No buttons' is prompted when click on 'Delete' button against the existing drivers and Check that clicking on 'Yes' button from 'Clicking delete will remove one of your named drivers. Are you sure you want to delete this driver?' deletes that particular driver", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_234_235(String browser) throws Exception {

		String tcId = "TC_234_235";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		String driverName = testData.foreName + " " + testData.surname;
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);
			ChangePermittedDriversPage changePermittedDriversPage = new ChangePermittedDriversPage(driver,
					extentedReport).get();

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

			changePermittedDriversPage.clickDeleteBtnOfPermittedDriver(driverName, extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("mdlDeleteDriver"),
							changePermittedDriversPage),
					"'Clicking delete will remove one of your named drivers. Are you sure you want to delete this driver ? with Yes and No buttons' is prompted when click on 'Delete' button against the exsiting drivers",
					"'Clicking delete will remove one of your named drivers. Are you sure you want to delete this driver ? with Yes and No buttons' is prompted when click on 'Delete' button against the exsiting drivers",
					driver, extentedReport);

			changePermittedDriversPage.clickYesOrNoBtnToDeleteDriver("Yes", extentedReport, false);

			Log.softAssertThat(changePermittedDriversPage.getDriverNameRow(driverName, extentedReport, true) == null,
					"On clicking 'Yes' button from 'Clicking delete will remove one of your named drivers. Are you sure you want to delete this driver ?' deleted that driver name : "
							+ driverName,
					"On clicking 'Yes' button from 'Clicking delete will remove one of your named drivers. Are you sure you want to delete this driver ?' did not delete that driver name : "
							+ driverName,
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on 'No' button from 'Clicking delete will remove one of your named drivers. Are you sure you want to delete this driver ?' does not delete the driver and navigates back to the Change Permitted Drivers main screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_236(String browser) throws Exception {

		String tcId = "TC_236";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		String driverName = testData.foreName + " " + testData.surname;
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

			changePermittedDriversPage.addAdditionalDrivers(testData.sectionTypeList, testData.title, testData.foreName,
					testData.surname, testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender,
					testData.maritalStatus, testData.relationship, testData.main_occupation, testData.main_business,
					testData.main_empStatus, testData.LicenceDate, testData.LicenceType, testData.qualification,
					testData.LicenceNumber, testData.reason, testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, testData.frequencyOfUse,
					testData.isCommutingPurpose, testData.isIncreasedPremiumApplied, testData.isInsuranceRefused,
					testData.isSpecialTermsApplied, testData.hadCriminalConviction, extentedReport, true);

			changePermittedDriversPage.clickDeleteBtnOfPermittedDriver(driverName, extentedReport, true);

			changePermittedDriversPage.clickYesOrNoBtnToDeleteDriver("No", extentedReport, false);

			Log.softAssertThat(changePermittedDriversPage.getDriverNameRow(driverName, extentedReport, true) != null,
					"On clicking 'No' button from 'Clicking delete will remove one of your named drivers. Are you sure you want to delete this driver ?' does not delete that driver name : "
							+ driverName,
					"On clicking 'No' button from 'Clicking delete will remove one of your named drivers. Are you sure you want to delete this driver ?' deleted that driver name : "
							+ driverName,
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
			"Smoke" }, description = "'Check that the removed drivers are not listed on the Change Permitted Drivers main screen / removed from risk", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_237(String browser) throws Exception {

		String tcId = "TC_237";
		final WebDriver driver = WebDriverFactory.get(browser);
		GetTestData testData = new GetTestData(sheetName, tcId);
		String driverName = testData.foreName + " " + testData.surname;
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

			// Add driver
			changePermittedDriversPage.addAdditionalDrivers(testData.sectionTypeList, testData.title, testData.foreName,
					testData.surname, testData.dateOfBirth, testData.dateOfLivingInUk, testData.gender,
					testData.maritalStatus, testData.relationship, testData.main_occupation, testData.main_business,
					testData.main_empStatus, testData.LicenceDate, testData.LicenceType, testData.qualification,
					testData.LicenceNumber, testData.reason, testData.carCount, testData.addionalCarCount,
					testData.yearCount, testData.householdCarCount, testData.frequencyOfUse,
					testData.isCommutingPurpose, testData.isIncreasedPremiumApplied, testData.isInsuranceRefused,
					testData.isSpecialTermsApplied, testData.hadCriminalConviction, extentedReport, true);

			changePermittedDriversPage.clickDeleteBtnOfPermittedDriver(driverName, extentedReport, true);

			Log.softAssertThat(
					changePermittedDriversPage.uielement.verifyPageElements(Arrays.asList("mdlDeleteDriver"),
							changePermittedDriversPage),
					"Clicking Delete will remove one of your named drivers. Are you sure you want to delete this driver?",
					"Clicking Delete will remove one of your named drivers. Are you sure you want to delete this driver?",
					driver, extentedReport);

			changePermittedDriversPage.clickYesOrNoBtnToDeleteDriver("Yes", extentedReport, false);

			Log.softAssertThat(
					!changePermittedDriversPage.verifyDriverNameContainsRelationship(driverName, testData.relationship,
							extentedReport, true),
					"Verified that the new driver name is not displayed in drivers list",
					"The new driver name is displaying in the drivers list", driver, extentedReport, true);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	// ******************* Story Id: 3679 ***************//

	@Test(description = "Check that 'To change your correspondence address, email address and contact telephone numbers, click the 'Manage Contact Details' button. Alternatively, to change your policy address (the address associated with the insured item/s), click the 'Change My Policy Details' button. You can change the policy details for each active policy you have running.' text is displayed when click/hover the help icon (?) against the 'Manage Contact Details' button and Check that 'Manage Contact Details' screen is opened on clicking the 'Manage Contact Details' button from the SS dashboard", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_241_242(String browser) throws Exception {

		String tcId = "TC_241_242";
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

			Log.softAssertThat(
					customerDashboardPage.uielement.verifyPageElements(Arrays.asList("iconHelpManageContactDetail"),
							customerDashboardPage),
					"Help icon (?) is displayed against the 'Manage Contact Details' button in customer dashboard page",
					"Help icon (?) is not displayed against the 'Manage Contact Details' button in customer dashboard page",
					driver, extentedReport);

			customerDashboardPage.hoverOnHelpIconOfManageContactDetail(extentedReport, true);
			Log.message("<u> <b> Verification for TC_241</b> </u>", driver, extentedReport);
			Log.softAssertThat(customerDashboardPage.verifyHelpIconManageContactPopupMessage(),
					customerDashboardPage.POPUP_MSG_HELPICON_MANAGE_CONTACT
							+ " - Message is displayed when click/hover the help icon (?) against the 'Manage Contact Details' button",
					customerDashboardPage.POPUP_MSG_HELPICON_MANAGE_CONTACT
							+ " - Message is not displayed when click/hover the help icon (?) against the 'Manage Contact Details' button",
					driver, extentedReport);

			// Click & verify'Manage Contact Detail'
			Log.message("<u> <b> Verification for TC_242</b> </u>", driver, extentedReport);
			Log.softAssertThat(customerDashboardPage.verifyManageContactDetailsModalOpened(extentedReport),
					"'Manage Contact Details' screen is opened on clicking the 'Manage Contact Details' button from the SS dashboard",
					"'Manage Contact Details' screen is not opened on clicking the 'Manage Contact Details' button from the SS dashboard",
					driver, extentedReport);
			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the user is navigated to the 'Change my Overnight Location' data capture screen on clicking the 'Change my Overnight Location' from the Change my policy window and Check that the user is able to amend the 'Overnight Location' within the 'Change my Overnight Location' data capture screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_245_247(String browser) throws Exception {

		String tcId = "TC_245_247";
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

			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange"),
							mTAInitiatePage),
					"User is navigated to the Datacaputure page to change the policy details",
					"User is not navigated to the Datacaputure page to change the policy details", driver,
					extentedReport);

			// Select change my policy address
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyAddressPage changemyaddrpage = new ChangeMyAddressPage(driver, extentedReport).get();

			Log.message("<u> <b> Verification for TC_245</b> </u>", driver, extentedReport);
			Log.softAssertThat(
					changemyaddrpage.uielement.verifyPageElements(Arrays.asList("drpSelectOvernightLocation"),
							changemyaddrpage),
					"User is navigated to the 'Change my Overnight Location' data capture screen on clicking the 'Change my Overnight Location' from the Change my policy window",
					"user is not navigated to the 'Change my Overnight Location' data capture screen on clicking the 'Change my Overnight Location' from the Change my policy window",
					driver, extentedReport);

			changemyaddrpage.selectOverNightLocation(testData.overnightLocation, extentedReport, true);

			Log.message("<u> <b> Verification for TC_247</b> </u>", driver, extentedReport);
			Log.softAssertThat(
					changemyaddrpage.verifyOvernightLocationOfVehicle(testData.overnightLocation, extentedReport,
							false),
					"User is able to amend the 'Overnight Location' within the 'Change my Overnight Location' data capture screen",
					"User is able to amend the 'Overnight Location' within the 'Change my Overnight Location' data capture screen",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that 'Do you want to change the Correspondence Address to match the Overnight Location?' is displayed with Yes and No buttons in disabled mode when the user did not change the address and Check that the Lookup and Enter Address Manually buttons are displayed when the client clicks on 'Change Address' button and Check that the following fields are displayed when the user clicks on 'Enter Address Manually' button - Address line 1, Address line 2, Address line 3, Address line 4, Postcode of overnight address", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_248_249_250(String browser) throws Exception {

		String tcId = "TC_248_249_250";
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

			// Select change my policy address
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport);
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyAddressPage changemyaddrpage = new ChangeMyAddressPage(driver, extentedReport).get();

			Log.softAssertThat(
					changemyaddrpage.uielement.verifyPageElements(
							Arrays.asList("txtChangeCorrespondenceAddressToOvernightLocation",
									"btnYesChangeCorrespondenceAddress", "btnNoChangeCorrespondenceAddress"),
							changemyaddrpage),
					"'Do you want to change the Correspondence Address to match the Overnight Location?' is displayed with Yes and No buttons",
					"'Do you want to change the Correspondence Address to match the Overnight Location?' is not displayed with Yes and No buttons",
					driver, extentedReport);

			Log.softAssertThat(changemyaddrpage.verifyBtnDisabledForChangeCorrespondenceAddress(),
					"'Do you want to change the Correspondence Address to match the Overnight Location?' is displayed with Yes and No buttons which are in disabled mode when the user did not change the address",
					"'Do you want to change the Correspondence Address to match the Overnight Location?' is displayed with Yes and No buttons which are not in disabled mode when the user did not change the address",
					driver, extentedReport, false);

			// click 'change address' button
			changemyaddrpage.clickChangeAddressBtn(extentedReport, true);

			Log.softAssertThat(
					changemyaddrpage.uielement.verifyPageElements(
							Arrays.asList("btnEnterAddressManually", "btnFindAddress"), changemyaddrpage),
					"Find Address and Enter Address Manually buttons are displayed when the client clicks on 'Change Address' button",
					"Find Address and Enter Address Manually buttons are not displayed when the client clicks on 'Change Address' button",
					driver, extentedReport, false);

			// Click 'Enter Address Manually'
			changemyaddrpage.clickEnterAddressManuallyBtn(extentedReport, false);

			Log.softAssertThat(
					changemyaddrpage.uielement.verifyPageElements(Arrays.asList("fldPostCode", "fldAddressLine1",
							"fldAddressLine2", "fldAddressLine3", "fldAddressLine4"), changemyaddrpage),
					"Postcode of overnight address, Address Line1, Address Line2, Address Line3, and Address Line4 fields are displayed when the user clicks on 'Enter Address Manually' button",
					"Postcode of overnight address, Address Line1, Address Line2, Address Line3, and Address Line4 fields are not displayed when the user clicks on 'Enter Address Manually' button",
					driver, extentedReport);
			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the changes are saved when the user clicks on '+Save Address' button", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_252(String browser) throws Exception {

		String tcId = "TC_252";
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

			// Select change my policy address
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport);
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyAddressPage changemyaddrpage = new ChangeMyAddressPage(driver, extentedReport).get();

			// Click 'Change address' button
			changemyaddrpage.clickChangeAddressBtn(extentedReport, true);
			changemyaddrpage.clickEnterAddressManuallyBtn(extentedReport, true);

			// Enter Change Overnight location detail
			changemyaddrpage.enterAddressManuallyChangeOvernightLocationDetail("Postcode", testData.postcode,
					extentedReport, true);
			changemyaddrpage.enterAddressManuallyChangeOvernightLocationDetail("Address1", testData.arrAddress[0],
					extentedReport, true);
			changemyaddrpage.enterAddressManuallyChangeOvernightLocationDetail("Address2", testData.arrAddress[1],
					extentedReport, true);
			changemyaddrpage.enterAddressManuallyChangeOvernightLocationDetail("Address3", testData.arrAddress[2],
					extentedReport, true);
			changemyaddrpage.enterAddressManuallyChangeOvernightLocationDetail("Address4", testData.arrAddress[3],
					extentedReport, true);
			changemyaddrpage.clickSaveAddressBtn(extentedReport, false);

			Log.softAssertThat(
					changemyaddrpage.verifyOvernightLocationAddress(testData.arrAddress[0], extentedReport, true),
					"Changes are saved when the user clicks on '+Save Address' button",
					"Changes are not saved when the user clicks on '+Save Address' button", driver, extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the changes are NOT saved when the user clicks on 'Cancel' button", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_253(String browser) throws Exception {

		String tcId = "TC_253";
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

			// Select change my policy address
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport);
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyAddressPage changemyaddrpage = new ChangeMyAddressPage(driver, extentedReport).get();

			// Click 'Change address' button
			changemyaddrpage.clickChangeAddressBtn(extentedReport, true);
			changemyaddrpage.clickEnterAddressManuallyBtn(extentedReport, true);

			// Enter Change Overnight location detail
			changemyaddrpage.enterAddressManuallyChangeOvernightLocationDetail("Postcode", testData.postcode,
					extentedReport, true);
			changemyaddrpage.enterAddressManuallyChangeOvernightLocationDetail("Address1", testData.arrAddress[0],
					extentedReport, true);
			changemyaddrpage.enterAddressManuallyChangeOvernightLocationDetail("Address2", testData.arrAddress[1],
					extentedReport, true);
			changemyaddrpage.enterAddressManuallyChangeOvernightLocationDetail("Address3", testData.arrAddress[2],
					extentedReport, true);
			changemyaddrpage.enterAddressManuallyChangeOvernightLocationDetail("Address4", testData.arrAddress[3],
					extentedReport, true);
			changemyaddrpage.clickCancelBtnForOvernightLocation(extentedReport, false);

			Log.softAssertThat(
					!changemyaddrpage.verifyOvernightLocationAddress(testData.arrAddress[0], extentedReport, true),
					"Changes are NOT saved when the user clicks on 'Cancel' button",
					"Changes NOT saved when the user clicks on 'Cancel' button", driver, extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the address is listed in a box when the user clicks on 'Lookup' button and Check that the following fields are auto-populated (as returned from the service) when click on 'Lookup' button and select an address from the list : Address line 1, Address line 2, Address line 3, Address line 4, Postcode, Country", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_254_255(String browser) throws Exception {

		String tcId = "TC_254_255";
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

			// Select change my policy address
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport);
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyAddressPage changemyaddrpage = new ChangeMyAddressPage(driver, extentedReport).get();

			// click 'change address' button
			changemyaddrpage.clickChangeAddressBtn(extentedReport, true);

			// Enter postcode
			changemyaddrpage.enterAddressManuallyChangeOvernightLocationDetail("Postcode", testData.postCode,
					extentedReport, true);
			changemyaddrpage.clickFindAddressBtn(extentedReport, true);

			Log.message("<u> <b> Verification for TC_254</b> </u>", driver, extentedReport);
			Log.softAssertThat(
					changemyaddrpage.uielement.verifyPageElements(Arrays.asList("drpSelectAddressList"),
							changemyaddrpage),
					"Address is listed in a box when the user clicks on 'Find address' button",
					"Address is listed in a box when the user clicks on 'Find address' button", driver, extentedReport);

			changemyaddrpage.selectAddress(extentedReport, true);

			Log.message("<u> <b> Verification for TC_255</b> </u>", driver, extentedReport);
			Log.softAssertThat(changemyaddrpage.verifyAddressFieldsAutoPopulated(extentedReport, true),
					"Address fields are auto-populated (as returned from the service) when click on 'Lookup' button and select an address from the list",
					"Address fields are not auto-populated (as returned from the service) when click on 'Lookup' button and select an address from the list",
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
			"Smoke" }, description = "Check that the new address can be saved or cancelled by selecting '+Save Address' and 'Cancel' buttons", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_256(String browser) throws Exception {

		String tcId = "TC_256";
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

			// Select change my policy address
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport);
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyAddressPage changemyaddrpage = new ChangeMyAddressPage(driver, extentedReport).get();

			// Click 'Change address' button
			changemyaddrpage.clickChangeAddressBtn(extentedReport, true);

			// enter Change Overnight location detail
			changemyaddrpage.enterFindAddressChangeOvernightLocationDetail(testData.postcode, extentedReport, true);

			// Click Cancel button
			changemyaddrpage.clickCancelBtnForOvernightLocation(extentedReport, false);

			Log.softAssertThat(
					!changemyaddrpage.verifyOvernightLocationAddress(testData.postcode, extentedReport, true),
					"The new address is not saved by selecting 'Cancel' button",
					"The new address is saved by selecting 'Cancel' button", driver, extentedReport);

			// Click 'Change address' button
			changemyaddrpage.clickChangeAddressBtn(extentedReport, true);

			// Enter Change Overnight location detail
			changemyaddrpage.enterFindAddressChangeOvernightLocationDetail(testData.postcode, extentedReport, true);

			// Click save button
			changemyaddrpage.clickSaveAddressBtn(extentedReport, false);

			Log.softAssertThat(changemyaddrpage.verifyOvernightLocationAddress(testData.postcode, extentedReport, true),
					"The new address is saved by selecting '+Save Address' button",
					"The new address is not saved by selecting '+Save Address' button", driver, extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that 'Do you want to change the Correspondence Address to match the Overnight Location?' is displayed with No button selected by default is enabled when the 'Overnight Location' is changed and Check that 'Are you sure you want to change your Correspondence Address to match the Overnight Location? This means the address for postal correspondence will change?' is displayed with Yes and No buttons is prompted when the user selects 'YES' for the question 'Do you want to change the Correspondence Address to match the Overnight Location?' and Check that the user's correspondence address is updated when click on 'YES' to the question 'Are you sure you want to change your Correspondence Address to match the Overnight Location? This means the address for postal correspondence will change?'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_257_258_259(String browser) throws Exception {

		String tcId = "TC_257_258_259";
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

			// Select change my policy address
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport);
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyAddressPage changemyaddrpage = new ChangeMyAddressPage(driver, extentedReport).get();

			// Click 'Change address' button
			changemyaddrpage.clickChangeAddressBtn(extentedReport, true);

			// Enter Change Overnight location detail
			changemyaddrpage.enterFindAddressChangeOvernightLocationDetail(testData.postcode, extentedReport, true);
			changemyaddrpage.clickSaveAddressBtn(extentedReport, false);

			Log.softAssertThat(changemyaddrpage.verifyOvernightLocationAddress(testData.postcode, extentedReport, true),
					"Changes are saved when the user clicks on '+Save Address' button",
					"Changes are not saved when the user clicks on '+Save Address' button", driver, extentedReport);

			Log.message("<u> <b> Verification for TC_257</b> </u>", driver, extentedReport);
			Log.softAssertThat(changemyaddrpage.verifyYesOrNoBtnSelectedForChangeAddress("No", extentedReport, true),
					"'Do you want to change the Correspondence Address to match the Overnight Location?' is displayed with No button selected by default is enabled when the 'Overnight Location' is changed",
					"'Do you want to change the Correspondence Address to match the Overnight Location?' is displayed with No button is not selected by default is enabled when the 'Overnight Location' is changed",
					driver, extentedReport);

			// click 'Yes' button
			changemyaddrpage.clickYesOrNoBtnToChangeAddressToMatchOvernightLocation("Yes", extentedReport, false);

			Log.message("<u> <b> Verification for TC_258</b> </u>", driver, extentedReport);
			Log.softAssertThat(changemyaddrpage.verifyChangeCorrespondenceAddressModalMsg(extentedReport, true),
					"'Are you sure you want to change your Correspondence Address to match the Overnight Location? This means the address for postal correspondence will change?' -  modal is displayed when the user selects 'YES' for the question 'Do you want to change the Correspondence Address to match the Overnight Location?' ",
					"'Are you sure you want to change your Correspondence Address to match the Overnight Location? This means the address for postal correspondence will change?' - modal is not displayed when the user selects 'YES' for the question 'Do you want to change the Correspondence Address to match the Overnight Location?'",
					driver, extentedReport);

			Log.softAssertThat(
					changemyaddrpage.uielement.verifyPageElements(
							Arrays.asList("btnYesInChangeAddressModel", "btnNoInChangeAddressModel"), changemyaddrpage),
					"'Are you sure you want to change your Correspondence Address to match the Overnight Location? This means the address for postal correspondence will change?' is displayed with Yes and No buttons",
					"'Are you sure you want to change your Correspondence Address to match the Overnight Location? This means the address for postal correspondence will change?' is not displayed with Yes and No buttons",
					driver, extentedReport);

			// click 'Yes' in popup
			changemyaddrpage.clickYesOrNoBtnInChangeAddressPopup("Yes", extentedReport, false);
			
			Log.message("<u> <b> Verification for TC_259</b> </u>", driver, extentedReport);
			changemyaddrpage.clickAddAnotherPolicyChange(extentedReport, true);
			mTAInitiatePage.clickEditChangesBtn(testData.typeOfChange, extentedReport, true);
			Log.softAssertThat(changemyaddrpage.verifyCorrespondenceAddress(testData.postcode, extentedReport, true),
					"User's correspondence address is updated when click on 'YES' to the question 'Are you sure you want to change your Correspondence Address to match the Overnight Location? This means the address for postal correspondence will change?'",
					"User's correspondence address is not updated when click on 'YES' to the question 'Are you sure you want to change your Correspondence Address to match the Overnight Location? This means the address for postal correspondence will change?'",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the question 'Do you want to change the Correspondence Address to match the Overnight Location?' is set to 'NO' when click on 'NO' to 'Are you sure you want to change your Correspondence Address to match the Overnight Location? This means the address for postal correspondence will change?'", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_260(String browser) throws Exception {

		String tcId = "TC_260";
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

			// Select change my policy address
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport);
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyAddressPage changemyaddrpage = new ChangeMyAddressPage(driver, extentedReport).get();

			// Click 'Change address' button
			changemyaddrpage.clickChangeAddressBtn(extentedReport, true);

			// Enter Change Overnight location detail
			changemyaddrpage.enterFindAddressChangeOvernightLocationDetail(testData.postcode, extentedReport, true);
			changemyaddrpage.clickSaveAddressBtn(extentedReport, false);

			// click 'Yes' button
			changemyaddrpage.clickYesOrNoBtnToChangeAddressToMatchOvernightLocation("Yes", extentedReport, true);

			Log.softAssertThat(
					changemyaddrpage.uielement.verifyPageElements(Arrays.asList("mdlChangeCorrespondenceAddress"),
							changemyaddrpage),
					"'Are you sure you want to change your Correspondence Address to match the Overnight Location? This means the address for postal correspondence will change?' popup is displayed ",
					"'Are you sure you want to change your Correspondence Address to match the Overnight Location? This means the address for postal correspondence will change?' popup is not displayed",
					driver, extentedReport);

			// Click 'No' in popup
			changemyaddrpage.clickYesOrNoBtnInChangeAddressPopup("No", extentedReport, false);

			Log.softAssertThat(changemyaddrpage.verifyYesOrNoBtnSelectedForChangeAddress("No", extentedReport, true),
					"'Do you want to change the Correspondence Address to match the Overnight Location?' is set to 'NO' when click on 'NO' to 'Are you sure you want to change your Correspondence Address to match the Overnight Location? This means the address for postal correspondence will change?' modal",
					"'Do you want to change the Correspondence Address to match the Overnight Location?' is not set to 'NO' when click on 'NO' to 'Are you sure you want to change your Correspondence Address to match the Overnight Location? This means the address for postal correspondence will change?' modal",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on 'Add another policy change' button within Change my Overnight location screen takes the user back to Change My Policy screen with the changes saved and edit button displays on the amendment and Check that the user is able to edit the overnight location when click on 'Edit changes' button on the change my Overnight location", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_261_262(String browser) throws Exception {

		String tcId = "TC_261_262";
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

			changemyaddrpage.clickAddAnotherPolicyChange(extentedReport, true);

			Log.message("<u> <b> Verification for TC_261</b> </u>", driver, extentedReport);
			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(
					Arrays.asList("txtTitleChooseTypeOfChange", "btnEditChangeMyPolicyAddress"), mTAInitiatePage),
					"On clicking 'Add another policy change' button within Change my Overnight location screen takes the user back to Change My Policy screen with the changes saved and edit button displays on the amendment",
					"On clicking 'Add another policy change' button within Change my Overnight location screen does not takes the user back to Change My Policy screen with the changes saved and edit button is not displays on the amendment",
					driver, extentedReport);

			mTAInitiatePage.clickEditChangesBtn(testData.typeOfChange, extentedReport, true);

			Log.message("<u> <b> Verification for TC_262</b> </u>", driver, extentedReport);
			Log.softAssertThat(
					changemyaddrpage.uielement.verifyPageElements(Arrays.asList("txtTitleChangeMyOvernightLocation"),
							changemyaddrpage),
					"User is navigated to  overnight location and able to edit the overnight location when click on 'Edit changes' button on the change my Overnight location",
					"User is not navigated to  overnight location and not able to edit the overnight location when click on 'Edit changes' button on the change my Overnight location",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the user is navigated to the payment screen, on clicking the 'Quote' button from the Initiate screen/Change my policy screen", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_263(String browser) throws Exception {

		String tcId = "TC_263";
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

			// Select change my policy address
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

			changemyaddrpage.clickAddAnotherPolicyChange(extentedReport, true);

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(
					Arrays.asList("txtTitleChooseTypeOfChange", "btnEditChangeMyPolicyAddress"), mTAInitiatePage),
					"On clicking 'Add another policy change' button within Change my Overnight location screen takes the user back to Change My Policy screen with the changes saved and edit button displays on the amendment",
					"On clicking 'Add another policy change' button within Change my Overnight location screen does not takes the user back to Change My Policy screen with the changes saved and edit button is not displays on the amendment",
					driver, extentedReport);

			changemyaddrpage.clickQuoteButton(extentedReport, false);

			MTAQuoteDetailsPage mTAQuoteDetailsPage = new MTAQuoteDetailsPage(driver, extentedReport).get();
			Log.softAssertThat(
					mTAQuoteDetailsPage.uielement.verifyPageElements(Arrays.asList("txtRefundPremium"), mTAQuoteDetailsPage),
					"Navigated to Quote page after clicking on quote button",
					"Not Navigated to Quote page after clicking on quote button", driver, extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(groups = {
			"Smoke" }, description = "Check that the address in overnight location data capture screen is updated with the new address when the client mofiy their correspondence address from SS dashboard", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker")
	public void TC_264(String browser) throws Exception {

		String tcId = "TC_264";
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

			// Click 'Manage Contact Details'
			customerDashboardPage.clickManageContactDetailsBtn(extentedReport);

			// Enter postcode
			customerDashboardPage.enterAddressManually("Postcode", testData.postcode, extentedReport, true);
			customerDashboardPage.clickFindAddressBtn(extentedReport);
			customerDashboardPage.selectAddress(extentedReport, true);

			// click save button
			customerDashboardPage.clickSaveBtnInManageContactDetailPopup(extentedReport, true);
			customerDashboardPage.clickCloseBtnInContactDetailsAddressUpdateMsg(extentedReport, true);

			// Click change my policy button
			customerDashboardPage.clickPolicyFromList(testData.policyName, extentedReport, true);
			customerDashboardPage.clickChangePolicyButton(testData.policyName, extentedReport, true);

			// Select change my policy address
			MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
			mTAInitiatePage.selectChangeType(testData.typeOfChange, extentedReport, true);
			mTAInitiatePage.clickContinueButton(extentedReport, true);

			ChangeMyAddressPage changemyaddrpage = new ChangeMyAddressPage(driver, extentedReport).get();

			Log.softAssertThat(changemyaddrpage.verifyCorrespondenceAddress(testData.postcode, extentedReport, true),
					"Address in overnight location data capture screen is updated with the new address when the client mofiy their correspondence address from SS dashboard",
					"Address in overnight location data capture screen is not updated with the new address when the client mofiy their correspondence address from SS dashboard",
					driver, extentedReport);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			try {

				Log.message("<U><b>Reverting to Original Address<b><U>", extentedReport);

				ChangeMyAddressPage changemyaddrpage = new ChangeMyAddressPage(driver, extentedReport).get();
				changemyaddrpage.clickCancelbtn(extentedReport, true);

				MTAInitiatePage mTAInitiatePage = new MTAInitiatePage(driver, extentedReport).get();
				mTAInitiatePage.clickCancelButton(extentedReport, true);

				CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();

				// Click 'Manage Contact Details'
				customerDashboardPage.clickManageContactDetailsBtn(extentedReport);

				// Enter postcode
				customerDashboardPage.enterAddressManually("Postcode", "GU111PZ", extentedReport, true);
				customerDashboardPage.clickFindAddressBtn(extentedReport);
				customerDashboardPage.selectAddress(extentedReport, true);

				// click save button
				customerDashboardPage.clickSaveBtnInManageContactDetailPopup(extentedReport, true);
				customerDashboardPage.clickCloseBtnInContactDetailsAddressUpdateMsg(extentedReport, true);
			} catch (Exception e) {
				Log.message("Error while reverting to original address", driver, extentedReport, true);
			}

			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the customer is shown with the Insurance Premium when try to change overnight location/risk address of motor Policy", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_268(String browser) throws Exception {

		String tcId = "TC_268";
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

			changemyaddrpage.clickAddAnotherPolicyChange(extentedReport, true);

			Log.softAssertThat(mTAInitiatePage.uielement.verifyPageElements(
					Arrays.asList("txtTitleChooseTypeOfChange", "btnEditChangeMyPolicyAddress"), mTAInitiatePage),
					"On clicking 'Add another policy change' button within Change my Overnight location screen takes the user back to Change My Policy screen with the changes saved and edit button displays on the amendment",
					"On clicking 'Add another policy change' button within Change my Overnight location screen does not takes the user back to Change My Policy screen with the changes saved and edit button is not displays on the amendment",
					driver, extentedReport);

			changemyaddrpage.clickQuoteButton(extentedReport, true);
			MTAQuoteDetailsPage mtaquotedetail = new MTAQuoteDetailsPage(driver, extentedReport).get();
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0,0);");
			Log.softAssertThat(mtaquotedetail.uielement.verifyPageElements(Arrays.asList("txtRefundPremium"), mtaquotedetail),
					"Premium is present in quote details page : "+mtaquotedetail.getPremiumAmt(extentedReport, true), "Premium is not available in quote details page : "+mtaquotedetail.getPremiumAmt(extentedReport, true),
					driver, extentedReport, false);

			Log.testCaseResult(extentedReport);
		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that clicking on Cancel button on 'Change my Overnight location' main screen navigates the user to Change My Policy screen without any change", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_269(String browser) throws Exception {

		String tcId = "TC_269";
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

			changemyaddrpage.clickCancelbtn(extentedReport, true);
			Log.softAssertThat(
					mTAInitiatePage.uielement.verifyPageElements(Arrays.asList("txtTitleChooseTypeOfChange"),
							mTAInitiatePage),
					"User is navigated to the Datacaputure page to change the policy details",
					"User is not navigated to the Datacaputure page to change the policy details", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the system displays proper alert messge when entering script tags into all the free format text boxes", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_271(String browser) throws Exception {

		String tcId = "TC_271";
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

			customerDashboardPage.clickManageContactDetailsBtn(extentedReport);

			// Postcode
			customerDashboardPage.enterAddressManually("Postcode", "<", extentedReport, true);
			Log.softAssertThat(!customerDashboardPage.verifyPostcode("<"),
					"Script Tag is not able to enter in the Postcode text field",
					"Script Tag is able to enter in the AddrPostcode text field", driver, extentedReport);

			// AddressLine 1
			customerDashboardPage.enterAddressManually("Address1", "<", extentedReport, true);
			Log.softAssertThat(!customerDashboardPage.verifyAddressLine("Address1", "<"),
					"Script Tag is not able to enter in the Addressline 1 text field",
					"Script Tag is able to enter in the Addressline 1 text field", driver, extentedReport);

			// AddressLine 2
			customerDashboardPage.enterAddressManually("Address2", "<", extentedReport, true);
			Log.softAssertThat(!customerDashboardPage.verifyAddressLine("Address2", "<"),
					"Script Tag is not able to enter in the Addressline 2 text field",
					"Script Tag is able to enter in the Addressline 2 text field", driver, extentedReport);

			// AddressLine 3
			customerDashboardPage.enterAddressManually("Address3", "<", extentedReport, true);
			Log.softAssertThat(!customerDashboardPage.verifyAddressLine("Address3", "<"),
					"Script Tag is not able to enter in the Addressline 3 text field",
					"Script Tag is able to enter in the Addressline 3 text field", driver, extentedReport);

			// AddressLine 4
			customerDashboardPage.enterAddressManually("Address4", "<", extentedReport, true);
			Log.softAssertThat(!customerDashboardPage.verifyAddressLine("Address4", "<"),
					"Script Tag is not able to enter in the Addressline 4 text field",
					"Script Tag is able to enter in the Addressline 4 text field", driver, extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}

	@Test(description = "Check that the system displays proper error message/alignment is displayed correctly when entering lengthy text in text boxes", dataProviderClass = DataProviderUtils.class, dataProvider = "PureBroker", priority = 0)
	public void TC_273(String browser) throws Exception {

		String tcId = "TC_273";
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

			customerDashboardPage.clickManageContactDetailsBtn(extentedReport);

			// Enter Postcode
			String postcode1 = GenericUtils.getRandomCharacters("ALPHANUMERIC", 256);
			customerDashboardPage.enterAddressManually("Postcode", postcode1, extentedReport, true);
			Log.softAssertThat(customerDashboardPage.verifyPostcode(postcode1),
					postcode1 + " - Lengthy value is entered in postcode textfield which has length 256",
					postcode1 + " - Lengthy value is not entered in postcode textfield which has length 256", driver,
					extentedReport);

			// Enter Postcode
			String postcode2 = GenericUtils.getRandomCharacters("ALPHANUMERIC", 257);
			customerDashboardPage.enterAddressManually("Postcode", postcode2, extentedReport, true);
			Log.softAssertThat(!customerDashboardPage.verifyPostcode(postcode2),
					postcode2 + " - Lengthy value is not entered in postcode textfield which has length 257",
					postcode2 + " - Lengthy value is not entered in postcode textfield which has length 257", driver,
					extentedReport);

			Log.testCaseResult(extentedReport);

		} catch (Exception e) {
			Log.exception(e, driver, extentedReport);
		} finally {
			Log.endTestCase(extentedReport);
			driver.quit();
		}
	}
}