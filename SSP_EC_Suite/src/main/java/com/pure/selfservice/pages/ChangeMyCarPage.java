package com.pure.selfservice.pages;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

import com.generic.support.Log;
import com.generic.utils.ElementLayer;
import com.generic.utils.GenericUtils;
import com.generic.utils.WaitUtils;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * ChangeMyCarPage contains Customer contact details
 * 
 */
public class ChangeMyCarPage extends LoadableComponent<ChangeMyCarPage> {
	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;
	
	public final String INVALID_CAR_REG_NUM_ERROR_MSG = "Sorry, we can’t find that vehicle, please check the registration number is correct. If you don't know your vehicle registration number or your car details don't appear when you click 'Lookup', please contact us."; 

	public String greenBtnBackgroundcolor = "rgba(0, 174, 129, 1)";
	public String greyBtnBackgroundcolor = "rgba(0, 0, 0, 0) rgba(255,255,255,1)";
	public String cssEditDeviceBtn = "#C2__C1__BUT_8BC4536991D26B832539_R";
	public String cssDeletDeviceBtn = "#C2__C1__BUT_8BC4536991D26B832541_R";
	public String cssEditModificationBtn = "#C2__C1__BUT_8BC4536991D26B832577_R";
	public String cssDeleteModificationBtn = "#C2__C1__BUT_8BC4536991D26B832579_R";
	public String editedExpectedMsg = "2007-2009RENAULTSCENICDYNDCI106MPV.6SpeedManualDiesel,Diesel";
	public String cssCancelModificationBtn = "#C2__C1__BUT_8BC4536991D26B832593";

	@FindBy(css = "#C2__C1__HEAD_C9F1C74F1BF8604D238380")
	WebElement txtTitleChangeMyCar;

	@FindBy(css = "input[name*='VEHICLEREG']")
	WebElement fldVehicleRegistrationNum;

	@FindBy(css = "button[id*='CHANGE_CAR_LOOKUP_BTN']")
	WebElement btnLookUp;

	@FindBy(css = "div[id*='C2__C1__row_HEAD_FDADB25F51A08B74974718' i]")
	WebElement txtInvalidVehicleRegistrationNumErrorMsg;

	@FindBy(css = "#C2__C1__QUE_8BC4536991D26B832451_ERRORMESSAGE")
	WebElement iconRegistrationNumMandatory;

	@FindBy(css = "button[title='Quote']")
	WebElement btnQuote;

	@FindBy(css = "button[value='Add another policy change']")
	WebElement btnAddAnotherPolicyChange;

	@FindBy(css = ".back-button[value='Cancel']")
	WebElement btnCancel;

	@FindBy(css = "#C2__C1__row_HEAD_FDADB25F51A08B74974760>div[id*='FDADB25F51A08B74974760']>div")
	WebElement txtLookupSuccessMsg;

	@FindBy(css = "#C2__C1__HEAD_C9F1C74F1BF8604D239820")
	WebElement txtCarNumberHeader;

	// ***General information****//

	@FindBy(css = "#C2__C1__FMT_42BCEBDA914B21B9122417")
	WebElement secGeneralInformation;

	@FindBy(css = "select[name*='DRIVINGMETHOD']")
	WebElement drpSelectDrivingMethod;

	@FindBy(css = "select[name*='numberofseats' i] ")
	WebElement drpSelectnoofSeat;

	@FindBy(css = "#C2__C1__row_ID_CHANGE_CAR_GENERAL_INFO_BTN button:nth-child(1)")
	WebElement btnNextInGeneralInformation;

	// ***Ownership Information****//

	@FindBy(css = "#C2__C1__FMT_42BCEBDA914B21B9122447")
	WebElement secOwnershipInformation;

	@FindBy(css = "select[name*='ownerofthevehicle' i]")
	WebElement drpOwnerofVehicle;

	@FindBy(css = "select[name*='KEEPEROFTHEVEHICLE']")
	WebElement drpSelectKeeperOfVehicle;

	@FindBy(css = "select[name*='VEHICLEPURCAHSEDATE.MONTH()']")
	WebElement drpSelectPurchaseMonth;

	@FindBy(css = "select[name*='VEHICLEPURCAHSEDATE.YEAR()']")
	WebElement drpSelectPurchaseYear;

	@FindBy(css = "button[id*='change' i][id*='car' i][id*='own' i]")
	WebElement btnNextInOwnershipInformation;

	// ***Vehicle Details****//

	@FindBy(css = "#C1__C1__FMT_42BCEBDA914B21B9129145")
	WebElement secVehicleDetails;

	@FindBy(css = "input[name*='VALUEOFTHEVEHICLE']")
	WebElement fldValueOfVehicle;

	@FindBy(css = "input[name*='VEHICLEDETAILS' i][name*='ANNUALMILEAGEFORPLEASURE' i]")
	WebElement txtValueofPleasure;

	@FindBy(css = "input[name*='VEHICLEDETAILS' i][id*='C2__C1__QUE_8BC4536991D26B832489']")
	WebElement txtValueofBusiness;

	@FindBy(css = "#C2__C1__BUT_8BC4536991D26B832499")
	WebElement btnNextInvehicleDetails;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832497 label:nth-child(1)")
	WebElement btnYesQPlate;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832497 label:nth-child(2)")
	WebElement btnNoQPlate;

	@FindBy(css = "#C2__C1__p1_QUE_8BC4536991D26B832497")
	WebElement lblQplate;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832497 label:nth-child(1) input")
	WebElement btnRadioYesQPlate;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832497 label:nth-child(2) input")
	WebElement btnRadioNoQPlate;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832497 label:nth-child(1) span")
	WebElement btnYesQPlatecolor;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832497 label:nth-child(2) span")
	WebElement btnNoQPlatecolor;

	@FindBy(css = "select[name*='PAINTWORKCOLOR']")
	WebElement drpSelectPaintworkColor;

	@FindBy(css = "select[name*='PAINTWORKFINISH']")
	WebElement drpSelectPaintworkFinish;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832497 label:nth-child(2) span")
	WebElement chkNoQplate;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832497 label:nth-child(1) span")
	WebElement chkYesQplate;

	@FindBy(css = "button[id*='change' i][id*='car' i][id*='vehicle' i]")
	WebElement btnNextInVehicleDetails;

	@FindBy(css = "input[name*='ODOMETERREADING']")
	WebElement fldOdometerReading;
	
	@FindBy(css = "input[name*='DEVICEFITTEDDATE']")
	WebElement fldDateVehicleMileageTaken;
	
	// ***Location Details****//

	@FindBy(css = "#C2__C1__FMT_42BCEBDA914B21B9129166")
	WebElement secLocationDetails;

	@FindBy(css = "input[name*='NEWADDRESS[1].POSTCODE' i]")
	WebElement fldOvernightPostcode;

	@FindBy(css = "button[title='Find Address']")
	WebElement btnFindAddress;

	@FindBy(css = "select[name*='ADDRESS[1].ADDRESSLIST']")
	WebElement drpSelectOvernightAddress;

	@FindBy(css = "input[name*='NEWADDRESS[1].ADDRESSLINE1']")
	WebElement fldAddressLine1;

	@FindBy(css = "select[name*='OVERNIGHTLOCATIONOFVEHICLE']")
	WebElement drpSelectOvernightVehicleLocation;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832503 label:nth-child(2)")
	WebElement btnIsVehiclekeptAtcurrentAddrNO;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832503 label:nth-child(1)")
	WebElement btnIsVehiclekeptAtcurrentAddrYes;

	@FindBy(css = "button[id*='change' i][id*='car' i][id*='location' i]")
	WebElement btnNextInLocationDetails;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832503 label:nth-child(1)")
	WebElement btnYesVehicleKeptAtCurrentAddress;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832503 label:nth-child(2)")
	WebElement btnNoVehicleKeptAtCurrentAddress;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832503 label:nth-child(1) span")
	WebElement btnYesVehicleKeptAtCurrentAddresscolor;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832503 label:nth-child(2) span")
	WebElement btnNoVehicleKeptAtCurrentAddresscolor;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832503 label:nth-child(1) input")
	WebElement btnRadioYesVehicleKeptAtCurrentAddress;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832503 label:nth-child(2) input")
	WebElement btnRadioNoVehicleKeptAtCurrentAddress;

	@FindBy(css = "a[id*='HELP_QUE_CCE7E08A37BF764D13129' i]")
	WebElement iconHelpOvernightPostcode;

	@FindBy(css = "span[id*='QUE_CCE7E08A37BF764D13129_ERRORMESSAGE' i]")
	WebElement txtInvalidvernightPostcodeErrorMsg;

	@FindBy(css = "[data-original-title='Please choose the overnight address from the list']")
	WebElement iconHelpOvernightAdress;

	@FindBy(css = "#C2__C1__p1_QUE_8BC4536991D26B832511")
	WebElement lblYourChosenAddress;

	@FindBy(css = "textarea[name*='CHOSENADDRESS']")
	WebElement txtAreaChosenAddress;

	@FindBy(css = "[data-original-title='Your chosen address details']")
	WebElement iconHelpYourChosenAddress;

	@FindBy(css = "#C2__C1__p1_QUE_8BC4536991D26B832513")
	WebElement lblOvernightLocation;

	@FindBy(css = "[data-original-title='Current Address']")
	WebElement iconHelpCurrentAddress;

	// ***Security devices****//

	@FindBy(css = "#C2__C1__FMT_42BCEBDA914B21B9129187")
	WebElement secSecurityDevices;

	@FindBy(css = "#C2__C1__p1_QUE_8BC4536991D26B832519")
	WebElement lblAddSecurityDevice;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832519 label:nth-child(1)")
	WebElement btnYesAddSecurityDevice;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832519 label:nth-child(2)")
	WebElement btnNoAddSecurityDevice;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832561 label:nth-child(1)")
	WebElement btnYesAddModificationDetails;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832561 label:nth-child(2)")
	WebElement btnNoAddModificationDetails;

	@FindBy(css = "select[name*='MAKE' i][name*='INSTALLED' i][name*='SECURITYDEVICE' i]")
	WebElement drpSelectMakeOfInstalledSecurityDevice;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832543 label:nth-child(2)")
	WebElement btnAddMoreDevicesNo;

	@FindBy(css = "button[title = 'edit' i]")
	WebElement btnEditSecurity;

	@FindBy(css = "select[name*='who' i][name*='INSTALLED' i][name*='DEVICE' i]")
	WebElement fldModalOfInstalledDevice;

	@FindBy(css = "#C2__C1__QUE_8BC4536991D26B832551")
	WebElement fldDeviceDate;

	@FindBy(css = "[id*='date-picker-C2__C1__QUE_8BC4536991D26B832527_R']")
	WebElement fldDeviceDateAfterClickedEditBtn;

	@FindBy(css = "select[name*='who' i][name*='INSTALLED' i][name*='DEVICE' i]")
	WebElement drpSelectwhoInstalledDevice;

	@FindBy(css = "button[title='Save Device' i]:not([disabled])")
	WebElement btnSaveDvice;

	@FindBy(css = "button[id*='change' i][id*='car' i][id*='security' i][id*='details_btn' i]")
	WebElement btnNextInSecurityDevice;

	@FindBy(css = "[id*='C2__C1__row_QUE_8BC4536991D26B832535_R']")
	List<WebElement> lstAddedSecurityDevice;

	@FindBy(css = "#C2__C1__p1_QUE_8BC4536991D26B832543")
	WebElement lblAnotherSecurityDeviceToAdd;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832543 label:nth-child(1)")
	WebElement btnYesAnotherSecurityDeviceToAdd;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832543 label:nth-child(2)")
	WebElement btnNoAnotherSecurityDeviceToAdd;

	@FindBy(css = "#C2__C1__HELP_QUE_8BC4536991D26B832519")
	WebElement iconHelpAddSecurityDevice;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832519 label:nth-child(1) input")
	WebElement btnRadioYesAddSecutiyDevice;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832519 label:nth-child(2) input")
	WebElement btnRadioNoAddSecutiyDevice;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832519 label:nth-child(1) span")
	WebElement btnYesAddSecutiyDevicecolor;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832519 label:nth-child(2) span")
	WebElement btnNoAddSecutiyDevicecolor;

	@FindBy(css = "#C2__C1__HELP_QUE_8BC4536991D26B832519")
	WebElement btnHelpSecurityIcon;

	// ***Modification details****//

	@FindBy(css = "#C2__C1__FMT_42BCEBDA914B21B9129208")
	WebElement secModificationDetails;

	@FindBy(css = "#C2__C1__p1_QUE_8BC4536991D26B832561")
	WebElement lblAnyModification;

	@FindBy(css = "select[name*='MODIFICATIONTYPE']:not([disabled])")
	WebElement drpSelectModificationType;

	@FindBy(css = "button[title ='edit' i][id*='C2__C1__BUT_8BC4536991D26B832577' i]")
	WebElement btnEditModificationDetails;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832587 label:nth-child(2)")
	WebElement btnModificationDuetoMedicalCondition;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832581 label:nth-child(2)")
	WebElement btnNoAdditionalModification;

	@FindBy(css = "textarea[name*='ADDITIONALINFO']:not([disabled])")
	WebElement txtAreaAdditonalInfo;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832581 label:nth-child(1)")
	WebElement btnYesAnyModification;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832581 label:nth-child(2)")
	WebElement btnNoAnyModification;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832587 label:nth-child(1)")
	WebElement btnYesMedicalCondition;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832587 label:nth-child(2)")
	WebElement btnNoMedicalCondition;

	@FindBy(css = "button[title='Save Modification']")
	WebElement btnSaveModification;

	@FindBy(css = "#C2__C1__p1_QUE_8BC4536991D26B832581")
	WebElement lblanyOtherModification;

	@FindBy(css = "[id*='C2__C1__FMT_C9F1C74F1BF8604D269190_R']")
	List<WebElement> lstModification;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832581 label:nth-child(1)")
	WebElement btnYesAnotherModificationToAdd;

	@FindBy(css = "#C2__C1__p4_QUE_8BC4536991D26B832581 label:nth-child(2)")
	WebElement btnNoAnothermodificationToAdd;

	@FindBy(css = "#C2__C1__HELP_QUE_8BC4536991D26B832561")
	WebElement iconHelpModification;

	@FindBy(css = "#C2__C1__BUT_8BC4536991D26B832557")
	WebElement btnCancelSecurityDetails;

	@FindBy(css = "#C2__C1__BUT_8BC4536991D26B832593")
	WebElement btnCancelModificationDetails;

	@FindBy(css = "[id*='MTA_COVER_DATE_ERRORMESSAGE']")
	WebElement txtChangeCarErrorMsg;

	@FindBy(css = "#C2__C1__BUT_87FBFB51ABB82CBB85311")
	WebElement btnYesonNoPopup;

	@FindBy(css = "#C2__C1__p4_BUT_4D552BA8A634C0FA447374")
	WebElement btnYesonNoSecurityDevicePopup;
	
	
	/**
	 * 
	 * Constructor class for ChangeMyCarPage Here we initializing the driver for
	 * page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public ChangeMyCarPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(WaitUtils.waitForElement(driver, txtTitleChangeMyCar)))) {
			Log.fail("User is not navigated to ChangeMyCarPage ", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {
		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
	}

	/**
	 * Enter Vehicle Registration Number
	 * 
	 * @param vehicleRegNo
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterVehicleRegistrationNumber(String vehicleRegNo, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldVehicleRegistrationNum);
			fldVehicleRegistrationNum.clear();
			fldVehicleRegistrationNum.sendKeys(vehicleRegNo);
			Log.message(vehicleRegNo + " - Vehicle Registration Number is enterd in the ChangeMyCar Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Vehicle Registration Number : " + e);
		}

	}

	/**
	 * Verify Vehicle Registration Number
	 * 
	 * @param vehicleRegNo
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRegistrationNumber(String vehicleRegNo, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean status = true;
			WaitUtils.waitForElement(driver, fldVehicleRegistrationNum);
			if (fldVehicleRegistrationNum.getAttribute("value").equals(vehicleRegNo)) {
				Log.message(vehicleRegNo + " - Vehicle Registration Number displayed in the ChangeMyCar Page", driver,
						extentedReport, screenshot);
				return status;
			} else {
				status = false;
				Log.message(
						fldVehicleRegistrationNum.getAttribute("value")
								+ "-Vehicle Registration Number displaying as incorrect",
						driver, extentedReport, screenshot);
				return status;
			}

		} catch (Exception e) {
			throw new Exception("Error while verifying Vehicle Registration Number : " + e);
		}
	}

	/**
	 * click LookUp Button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickLookUpButton(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			btnLookUp.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("LookUp button is clicked in the Change My car Page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking LookUp Button in Change My Car page : " + e);
		}

	}

	/**
	 * To verify Invalid Vehicle Registration Number Error Message
	 * 
	 * @param expectedMsg
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * @return boolean
	 * 
	 */
	public boolean verifyInvalidVehicleRegNumErrorMsg(String expectedMsg, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		boolean status = false;
		try {

			WaitUtils.waitForElement(driver, txtInvalidVehicleRegistrationNumErrorMsg);
			if (GenericUtils.verifyWebElementTextContains(txtInvalidVehicleRegistrationNumErrorMsg, expectedMsg)) {
				status = true;
			}
			return status;
		} catch (Exception e) {
			throw new Exception(
					"Error while verify Invalid Vehicle Registration Number Error Message in ChangeMyCar page : " + e);
		}
	}

	/**
	 * To verify Car Number in Header
	 * 
	 * @param expectedCarNum
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * @return boolean
	 * 
	 */
	public boolean verifyCarNumberHeader(String expectedCarNum, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		boolean status = false;
		try {

			WaitUtils.waitForElement(driver, txtCarNumberHeader);
			if (GenericUtils.verifyWebElementTextContains(txtCarNumberHeader, expectedCarNum)) {
				status = true;
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while verify Car Number in Header of ChangeMyCar page : " + e);
		}
	}

	/**
	 * To verify Look up Success Message
	 * 
	 * @param expectedMsg
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyLookupSuccessMsg(ExtentTest extentedReport, boolean screenshot) throws Exception {
		boolean status = false;
		try {
			WaitUtils.waitForElement(driver, txtLookupSuccessMsg);
			String displayedMsg = txtLookupSuccessMsg.getText().replaceAll("\\r\\n|\\r|\\n|\\s+", "");
			if (displayedMsg.contains(editedExpectedMsg)) {
				status = true;
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while verifying Lookup Success Message in ChangeMyCar page : " + e);
		}
	}

	/**
	 * To click Cancel button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickCancelButton(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForelementToBeClickable(driver, btnCancel,
					"Cancel button is not found in 'change my cover type' page");
			btnCancel.click();
			Log.message("Clicked on Cancel button in 'Change My Car' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking cancel button in 'change my car' page : " + e);
		}
	}

	/**
	 * To click on Next button
	 * 
	 * @param sectionType
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextButtonInSection(String sectionType, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (sectionType.trim()) {
			case "General Information":
				//btnNextInGeneralInformation.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextInGeneralInformation);
				WaitUtils.waitForSpinner(driver);
				break;

			case "Ownership Information":
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextInOwnershipInformation);
				//btnNextInOwnershipInformation.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "Vehicle Details":
				WaitUtils.waitForElement(driver, btnNextInVehicleDetails);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextInVehicleDetails);
				//btnNextInVehicleDetails.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "Location Details":
				WaitUtils.waitForElementPresent(driver, btnNextInLocationDetails,
						"Next button displayed on location details section");
				WaitUtils.waitForelementToBeClickable(driver, btnNextInLocationDetails,
						"Next button able to clickable");
				WaitUtils.waitForElement(driver, btnNextInLocationDetails);
				GenericUtils.scrollIntoView(driver, btnNextInLocationDetails);
				GenericUtils.clickElementJS(driver, btnNextInLocationDetails);
				WaitUtils.waitForSpinner(driver);
				break;

			case "Security Devices":
				WaitUtils.waitForElement(driver, btnNextInSecurityDevice);
				WaitUtils.waitForElementPresent(driver, btnNextInSecurityDevice,
						"Next button displayed on security details section");
				//btnNextInSecurityDevice.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextInSecurityDevice);
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked on Next button in '" + sectionType + "' section", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking next button : " + e);
		}
	}

	/**
	 * To click Quote button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickQuoteButton(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnQuote);
			btnQuote.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Quote' button in 'Change My Car' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Quote' button in 'Change My Car type' page : " + e);
		}
	}

	/**
	 * To click AddAnotherPolicyChange
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickAddAnotherPolicyChangeBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnAddAnotherPolicyChange);
			btnAddAnotherPolicyChange.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Add Another Policy Change button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking Add Another Policy Change button : " + e);
		}
	}

	// *************************General Information******************//

	/**
	 * To Select Car Driving Method
	 * 
	 * @param drivingType
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectGeneralInformationDetails(String drivingType, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			Select noofseats = new Select(drpSelectnoofSeat);
			noofseats.selectByVisibleText("5");
			Select selectTitle = new Select(drpSelectDrivingMethod);
			selectTitle.selectByVisibleText(drivingType);
			Log.message("Selected Driving Method : " + drivingType + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while selecting driving Type : " + drivingType + " : " + e);
		}
	}

	/**
	 * To verify Car Driving Method
	 * 
	 * @param drivingType
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */

	public boolean verifyCarDrivingMethdod(String drivingType, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		String drivingMethod = null;

		try {
			boolean status = true;
			Select selectTitle = new Select(drpSelectDrivingMethod);
			drivingMethod = selectTitle.getFirstSelectedOption().getText();
			if (drivingMethod.equals(drivingType)) {
				Log.message("Selected driving method restored on Change of my car page", driver, extentedReport,
						screenshot);
				return status;
			} else {
				status = false;
				Log.message("Selected driving method" + drivingType + "is not displaying", driver, extentedReport,
						screenshot);
				return status;
			}

		} catch (Exception e) {
			throw new Exception("Error while verifying driving Type : " + drivingMethod + ": " + e);
		}
	}

	// *************************Ownership Information******************//

	/**
	 * To Select Owner Of Vehicle
	 * 
	 * @param keeperType
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */

	public void selectOwnerOfVehicle(String owner, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpOwnerofVehicle);
			Select selectKeeperOfVehicle = new Select(drpOwnerofVehicle);
			selectKeeperOfVehicle.selectByVisibleText(owner);
			Log.message("Selected Ownerof the Vehicle : " + owner + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while selecting Owner type : " + owner + " : " + e);
		}
	}

	/**
	 * To Select Keeper Of Vehicle
	 * 
	 * @param keeperType
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectKeeperOfVehicle(String keeperType, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpSelectKeeperOfVehicle);
			Select selectKeeperOfVehicle = new Select(drpSelectKeeperOfVehicle);
			selectKeeperOfVehicle.selectByVisibleText(keeperType);
			Log.message("Selected keeper Type of the vehicle : " + keeperType + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while selecting Keeper : " + keeperType + " : " + e);
		}
	}

	/**
	 * To Select Purchase month
	 * 
	 * @param month
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectPurchaseMonth(String month, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpSelectPurchaseMonth);
			Select selectPurchaseMonth = new Select(drpSelectPurchaseMonth);
			selectPurchaseMonth.selectByVisibleText(month);
			Log.message("Selected Purchase month : " + month + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while selecting Purchase month : " + month + " : " + e);
		}
	}

	/**
	 * To Select Purchase Year
	 * 
	 * @param year
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectPurchaseYear(String year, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpSelectPurchaseYear);
			Select selectPurchaseYear = new Select(drpSelectPurchaseYear);
			selectPurchaseYear.selectByVisibleText(year);
			Log.message("Selected Purchase year : " + year + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while selecting Purchase Year : " + year + " : " + e);
		}
	}

	/**
	 * To Enter Ownership Information
	 * 
	 * @param keeperType
	 * @param month
	 * @param year
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void enterOwnershipInformation(String keeperType, String month, String year, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			selectOwnerOfVehicle(keeperType, extentedReport, false);
			selectKeeperOfVehicle(keeperType, extentedReport, false);
			selectPurchaseMonth(month, extentedReport, false);
			selectPurchaseYear(year, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Ownership Information : " + e);
		}
	}

	/**
	 * To verify Ownership Information
	 * 
	 * @param keeperType
	 * @param month
	 * @param year
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public boolean verifyOwnershipInformation(String keeperType, String month, String year, ExtentTest extentedReport,
			boolean screenshot) throws Exception {

		boolean status = true;
		try {

			WaitUtils.waitForElement(driver, drpSelectKeeperOfVehicle);
			Select selectKeeperOfVehicle = new Select(drpSelectKeeperOfVehicle);
			Select selectPurchaseYear = new Select(drpSelectPurchaseYear);
			if (selectKeeperOfVehicle.getFirstSelectedOption().getText().equals(keeperType)
					& (selectPurchaseYear.getFirstSelectedOption().getText().equals(year))) {
				Log.message("Ownership details are restored on change of my car page", driver);
				return status;
			} else {
				Log.message("Ownership details are restored on change of my car page", driver, true);
				status = false;
				return status;
			}
		} catch (Exception e) {
			Log.message("Error while entering Ownership Information:" + e);
			status = false;
			return status;
		}

	}
	
	// *************************Vehicle details******************//

	/**
	 * To verify yes/no button got selected for Q-Plate
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifySelectedYesOrNobtnForQPlate(String buttonName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean btnSelected = false;

			if (buttonName.equals("Yes")) {
				if (btnRadioYesQPlate.getAttribute("checked").equals("true")) {
					btnSelected = true;
				}
			} else {
				if (btnRadioNoQPlate.getAttribute("checked").equals("true")) {
					btnSelected = true;
				}
			}
			return btnSelected;

		} catch (Exception e) {
			throw new Exception("Error while verifying : " + buttonName + " button is selected in for Q-Plate : " + e);
		}
	}

	/**
	 * To click Yes/No button for Q-Plate
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoBtnForQplate(String buttonName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {

			if (buttonName.equals("Yes")) {
				btnYesQPlate.click();
			} else {
				btnNoQPlate.click();
			}
			Log.message("Clicked on button : " + buttonName + " for Q-Plate", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking : " + buttonName + " button n Vehicle details : " + e);
		}
	}

	/**
	 * To verify green background color of yes/no button for Q-Plate
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyGreenBackgroundColorOfYesOrNoBtnOfQPlate(String buttonName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			boolean isGreencolor = false;
			if (buttonName.equals("Yes")) {
				WaitUtils.waitForElement(driver, btnYesQPlatecolor);

				if (btnYesQPlatecolor.getCssValue("background-color").equals(greenBtnBackgroundcolor)) {
					isGreencolor = true;
				}
			} else {
				if (btnNoQPlatecolor.getCssValue("background-color").equals(greenBtnBackgroundcolor)) {
					isGreencolor = true;
				}
			}
			Log.message("color of the button " + buttonName + " is green : " + greenBtnBackgroundcolor, driver,
					extentedReport, screenshot);
			return isGreencolor;
		} catch (Exception e) {
			throw new Exception("Error while verifying : " + buttonName + " button green background color : " + e);
		}
	}

	/**
	 * To verify grey background color of yes/no button in Vehicle details
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyGreyBackgroundColorOfYesOrNoBtnOfQPlate(String buttonName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		boolean isGreycolor = false;
		try {

			if (buttonName.equals("Yes")) {
				WaitUtils.waitForElement(driver, btnYesQPlatecolor);
				if (btnYesQPlatecolor.getCssValue("background-color").equals(greyBtnBackgroundcolor)) {
					isGreycolor = true;
					return isGreycolor;
				}
			} else {

				if (greyBtnBackgroundcolor.contains(btnNoQPlatecolor.getCssValue("background-color"))) {
					Log.message(
							"color of the button " + buttonName + " of Q-Plate is grey : "
									+ btnNoQPlatecolor.getCssValue("background-color"),
							driver, extentedReport, screenshot);
					isGreycolor = true;
					return isGreycolor;
				}
			}
		} catch (Exception e) {
			isGreycolor = false;
			Log.message("Error while verifying : " + buttonName + " button grey background color : " + e);
			return isGreycolor;
		}
		return isGreycolor;
	}

	/**
	 * To Select Paint work Color
	 * 
	 * @param colorName
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectPaintworkColor(String colorName, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			Select selectPaintworkColor = new Select(drpSelectPaintworkColor);
			selectPaintworkColor.selectByVisibleText(colorName);
			Log.message("Selected color : " + colorName + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while selecting Paintwork color : " + colorName + " : " + e);
		}
	}

	/**
	 * To Select Paint work Finish
	 * 
	 * @param FinishName
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectPaintworkFinish(String FinishName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			Select selectPaintworkFinish = new Select(drpSelectPaintworkFinish);
			selectPaintworkFinish.selectByVisibleText(FinishName);
			Log.message("Selected Paintwork Finish : " + FinishName + "", driver, extentedReport, screenshot);
			WaitUtils.waitForElement(driver, chkNoQplate);
			chkNoQplate.click();
			Log.message("Qplate 'No button selected on vehicle details section", driver);
			WaitUtils.waitForSpinner(driver);
		} catch (Exception e) {
			throw new Exception("Error while selecting Paintwork Finish : " + FinishName + " : " + e);
		}
	}

	/**
	 * To enter Value of vehicle
	 * 
	 * @param vehicleValue
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterValueOfVehicle(String vehicleValue, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForelementToBeClickable(driver, fldValueOfVehicle,
					"Field to enter value of vehicle is not found");
			fldValueOfVehicle.clear();
			fldValueOfVehicle.sendKeys(vehicleValue);
			Log.message("Entered Current Value of vehicle : " + vehicleValue + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering current value of vehicle : " + vehicleValue + " : " + e);
		}
	}

	/**
	 * To enter vehicleAnnualMileageValue
	 * 
	 * @param vehicleValue
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterAnnualMileageforPleasure(String vehicleValue, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForelementToBeClickable(driver, txtValueofPleasure,
					"Field to enter value of vehicle is not found");
			txtValueofPleasure.clear();
			txtValueofPleasure.sendKeys(vehicleValue);
			Log.message("Entered Annual Mileage for Pleasure of vehicle : " + vehicleValue + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Value of vehicle : " + vehicleValue + " : " + e);
		}
	}

	/**
	 * To enter Mileage of Business
	 * 
	 * @param vehicleValue
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */

	public void enterAnnualMileageforBusiness(String vehicleValue, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForelementToBeClickable(driver, txtValueofBusiness,
					"Feild to enter value of vehicle is not found");
			txtValueofBusiness.clear();
			txtValueofBusiness.sendKeys(vehicleValue);
			Log.message("Entered Annual Mileage of vehicle : " + vehicleValue + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Value of vehicle : " + vehicleValue + " : " + e);
		}
	}

	/**
	 * To enter Odometer value
	 * 
	 * @param odometerReading
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterOdometerReading(String odometerReading, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			fldOdometerReading.clear();
			fldOdometerReading.sendKeys(odometerReading);
			Log.message("Entered Vehicle Mileage : " + odometerReading + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Vehicle Mileage : " + odometerReading + " : " + e);
		}
	}

	/**
	 * To Enter Vehicle Detail
	 * 
	 * @param vehicleValue
	 * @param paintworkColor
	 * @param paintworkFinish
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void enterVehicleDetail(String vehicleValue, String odometerReading, String paintworkColor,
			String paintworkFinish, String deviceDate,ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			enterValueOfVehicle(vehicleValue, extentedReport, false);
			enterAnnualMileageforPleasure(vehicleValue, extentedReport, false);
			enterAnnualMileageforBusiness(vehicleValue,extentedReport, false);
			enterDateVehicleMileageTaken(deviceDate,extentedReport, false);
			enterOdometerReading(odometerReading, extentedReport, true);
		} catch (Exception e) {
			throw new Exception("Error while entering vehicle details : " + e);
		}
	}
	
	/**
	 * To enter Value of vehicle
	 * 
	 * @param vehicleValue
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterDateVehicleMileageTaken(String deviceDate,ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForelementToBeClickable(driver, fldDateVehicleMileageTaken,
					"Field to enter value of Date vehicle mileage taken is not found");
			fldDateVehicleMileageTaken.clear();
			
			/*Date cur_dt = new Date();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
			String date = dateFormat.format(cur_dt);*/
			
			fldDateVehicleMileageTaken.sendKeys(deviceDate);
			Log.message("Entered Date vehicle mileage taken Value : " + deviceDate + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering value of Date vehicle mileage taken : " + e);
		}
	}
	
	

	/**
	 * To Verify the Vehicle Detail
	 * 
	 * @param vehicleValue
	 * @param paintworkColor
	 * @param paintworkFinish
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */

	public boolean verifyVehicleDetails(String vehicleValue, String odometerReading, String paintworkColor,
			String paintworkFinish, String deviceDate, ExtentTest extentedReport, boolean screenshot) throws Exception {
		boolean status = true;
		try {

			WaitUtils.waitForElement(driver, fldValueOfVehicle);
			if ((fldValueOfVehicle.getAttribute("value").replace(",", "").contains(vehicleValue))
					&& (txtValueofPleasure.getAttribute("value").replace(",", "").contains(vehicleValue))) {
				Log.message("Vehicle details are restored successfully on change of my car page", driver);
				return status;
			} else {
				status = false;
				Log.message("Vehicle details are not restored on change of my car page", driver);
				return status;
			}
		} catch (Exception e) {
			Log.message("Error while entering vehicle details : " + e);
			status = false;
			return status;
		}
	}

	// *************************Location details******************//

	/**
	 * To enter Overnight Postcode
	 * 
	 * @param postcode
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterOvernightPostcode(String postcode, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {

			fldOvernightPostcode.clear();
			fldOvernightPostcode.sendKeys(postcode);
			btnFindAddress.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Entered Overnight Postcode : " + postcode + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Overnight Postcode : " + postcode + " : " + e);
		}
	}

	/**
	 * To click Yes/No button For Vehicle Kept In your Current Location
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoBtnForVehicleKeptInCurrentLocation(String buttonName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnYesVehicleKeptAtCurrentAddress);
			if (buttonName.equals("Yes")) {
				btnYesVehicleKeptAtCurrentAddress.click();
			} else {
				btnNoVehicleKeptAtCurrentAddress.click();
			}
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on button ' " + buttonName + " ' in Location details", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking : " + buttonName
					+ " button for Vehicle Kept In your Current Location : " + e);
		}
	}

	/**
	 * To verify green background color of yes/no button in Location details
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyGreenBackgroundColorOfYesOrNoBtnOfVehicleKeptInCurrentLocation(String buttonName,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			boolean isGreencolor = false;
			if (buttonName.equals("Yes")) {

				if (greyBtnBackgroundcolor
						.contains(btnRadioYesVehicleKeptAtCurrentAddress.getCssValue("background-color"))) {
					isGreencolor = true;
					return isGreencolor;

				}
			} else {
				if (btnYesVehicleKeptAtCurrentAddresscolor.getCssValue("background-color")
						.equals(greenBtnBackgroundcolor)) {
					isGreencolor = true;
					return isGreencolor;

				}
			}
			Log.message(
					"color of the button " + buttonName + " for Vehicle Kept In your Current Location is grey: "
							+ btnNoVehicleKeptAtCurrentAddresscolor.getCssValue("background-color"),
					driver, extentedReport, screenshot);
			return isGreencolor;
		} catch (Exception e) {
			throw new Exception("Error while verifying : " + buttonName
					+ " button green background color in Location details : " + e);
		}
	}

	/**
	 * To verify grey background color of yes/no button in Location details
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyGreyBackgroundColorOfYesOrNoBtnOfVehicleKeptInCurrentLocation(String buttonName,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			boolean isGreycolor = false;
			if (buttonName.equals("Yes")) {
				if (btnYesVehicleKeptAtCurrentAddresscolor.getCssValue("color").equals(greyBtnBackgroundcolor)) {
					isGreycolor = true;
				}
			} else {

				if (btnNoVehicleKeptAtCurrentAddresscolor.getCssValue("color").equals(greyBtnBackgroundcolor)) {
					isGreycolor = true;
				}
			}
			Log.message("color of the button " + buttonName + " for Vehicle Kept In your Current Location is grey : "
					+ greenBtnBackgroundcolor, driver, extentedReport, screenshot);
			return isGreycolor;
		} catch (Exception e) {
			throw new Exception("Error while verifying : " + buttonName
					+ " button grey background color in Location detail : " + e);
		}
	}

	/**
	 * To verify button got selected In LocationDetail
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyYesOrNoSelectedbtnInLocationDetail(String buttonName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			boolean btnSelected = false;

			if (buttonName.equals("Yes")) {
				if (btnRadioYesVehicleKeptAtCurrentAddress.getAttribute("checked").equals("true")) {
					btnSelected = true;
				}
			} else {
				if (btnRadioNoVehicleKeptAtCurrentAddress.getAttribute("checked").equals("true")) {
					btnSelected = true;
				}
			}

			return btnSelected;

		} catch (Exception e) {
			throw new Exception("Error while verifying : " + buttonName
					+ " button is selected for Vehicle Kept In your Current Location : " + e);
		}
	}

	/**
	 * To Select Overnight Address
	 * 
	 * @param FinishName
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectOvernightAddress() throws Exception {
		try {
			Select selectOvernightAddress = new Select(drpSelectOvernightAddress);
			selectOvernightAddress.selectByIndex(0);
			Log.message("Selected Overnight Address : " + selectOvernightAddress.getFirstSelectedOption().getText(),
					driver, extentedReport);
			WaitUtils.waitForElement(driver, fldAddressLine1);
			WaitUtils.waitForSpinner(driver);
		} catch (Exception e) {
			throw new Exception("Error while selecting Overnight Address : " + e);
		}
	}

	/**
	 * To Select Overnight Location of vehicle
	 * 
	 * @param location
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectOvernightLocationOfVehicle(String location, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpSelectOvernightVehicleLocation);
			Select selectOvernightLocation = new Select(drpSelectOvernightVehicleLocation);
			selectOvernightLocation.selectByVisibleText(location);
			Log.message("Selected Overnight Location of vehicle : " + location + "", driver, extentedReport,
					screenshot);

			btnIsVehiclekeptAtcurrentAddrYes.click();
			Log.message("Vehicle kept at current address selected as Yes", driver);

		} catch (Exception e) {
			throw new Exception("Error while selecting Overnight Location of vehicle : " + location + " : " + e);
		}
	}

	/**
	 * To verify Overnight Location of vehicle
	 * 
	 * @param location
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public boolean verifyOvernightLoactionOfVehicle(String location, ExtentTest extentedReport, boolean screenshot)
			throws Exception {

		boolean status = true;
		try {

			Select selectOvernightLocation = new Select(drpSelectOvernightVehicleLocation);

			if (selectOvernightLocation.getFirstSelectedOption().getText().equals(location)) {
				Log.message("Overnigt location details are stored successfully on change of my car page", driver);
				return status;
			} else {
				status = false;
				Log.message("overnigt loaction details are not restored on change of my car page", driver);
				return status;
			}
		} catch (Exception e) {
			Log.message("Error while selecting Overnight Location of vehicle : " + location + " : " + e);
			status = false;
			return status;
		}
	}

	// *************************Security Devices******************//

	/**
	 * To click Yes/No button To Add Security Device
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoBtnToAddSecurityDevice(String buttonName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {

			if (buttonName.equals("Yes")) {
				WaitUtils.waitForElement(driver, btnYesAddSecurityDevice);
				WaitUtils.waitForSpinner(driver);
				btnYesAddSecurityDevice.click();
				WaitUtils.waitForSpinner(driver);
			} else {
				WaitUtils.waitForElement(driver, btnNoAddSecurityDevice);
				WaitUtils.waitForSpinner(driver);
				btnNoAddSecurityDevice.click();
				WaitUtils.waitForSpinner(driver);
			}
			Log.message("Clicked on button ' " + buttonName + " ' to add security device", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking : " + buttonName + " to add security device : " + e);
		}
	}

	/**
	 * To verify Yes/No button To Add Security Device
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */

	public boolean VerifyYesOrNoBtnToAddSecurityDevices(String buttonName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		boolean status = true;
		WaitUtils.waitForElement(driver, btnYesAddSecurityDevice);
		try {

			if (btnYesAddSecurityDevice.getAttribute("checked").equals("checked") & buttonName.equals("Yes")) {
				Log.message("Yes button displaying as selected in securiy device information", driver, extentedReport,
						screenshot);
				return status;

			} else {
				status = false;
				Log.message("No button displaying as selected in security device information", driver, extentedReport,
						screenshot);
				return status;
			}

		} catch (Exception e) {
			Log.message("Button displaying as unchecked" + buttonName + "your quote page");
			status = false;
			return status;

		}
	}

	/**
	 * To select Make Of Installed Security Device
	 * 
	 * @param device
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectMakeOfInstalledSecurityDevice(String makeOfDevice, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpSelectMakeOfInstalledSecurityDevice);
			Select selectMakeOfInstalledSecurityDevice = new Select(drpSelectMakeOfInstalledSecurityDevice);
			selectMakeOfInstalledSecurityDevice.selectByVisibleText(makeOfDevice);
			Log.message("Selected Overnight Location of vehicle : " + makeOfDevice + "", driver, extentedReport,
					screenshot);

		} catch (Exception e) {
			throw new Exception(
					"Error while selecting Make Of Installed Security Device : " + makeOfDevice + " : " + e);
		}
	}

	/**
	 * To enter Modal Of Installed Security Device
	 * 
	 * @param Modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterModalOfInstalledSecurityDevice(String modal, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForelementToBeClickable(driver, fldModalOfInstalledDevice,
					"Field to enter Modal Of Installed Security Device is not found");
			fldModalOfInstalledDevice.clear();
			fldModalOfInstalledDevice.sendKeys(modal);
			Log.message("Entered Modal Of Installed Security Device : " + modal + "", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Modal Of Installed Security Device : " + modal + " : " + e);
		}
	}

	/**
	 * To enter date the Device was fitted
	 * 
	 * @param date
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterDeviceDate(String date, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			fldDeviceDate.clear();
			fldDeviceDate.sendKeys(date);
			fldDeviceDate.click();
			Log.message("Entered the Date the Device was fitted : " + date, driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering date the Device was fitted : " + e);
		}
	}

	/**
	 * To select Who Installed Device
	 * 
	 * @param device
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectWhoInstalledDevice(String installer, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			Select selectWhoInstalledDevice = new Select(drpSelectwhoInstalledDevice);
			selectWhoInstalledDevice.selectByVisibleText(installer);
			Log.message("Selected 'Who Installed Device' value : " + installer + "", driver, extentedReport,
					screenshot);

		} catch (Exception e) {
			throw new Exception("Error while selecting Who Installed Device : " + installer + " : " + e);
		}
	}

	/**
	 * To enter Security Device Detail
	 * 
	 * @param makeOfDevice
	 * @param modal
	 * @param date
	 * @param installer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterSecurityDeviceDetail(String makeOfDevice, String modal, String date, String installer,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			selectMakeOfInstalledSecurityDevice(makeOfDevice, extentedReport, false);
			enterDeviceDate(date, extentedReport, true);
			selectWhoInstalledDevice(installer, extentedReport, true);
			Log.message("Security device details entered successfully", driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while entering Security Device Detail : " + e);
		}
	}

	/**
	 * To verify Security Device Detail
	 * 
	 * @param makeOfDevice
	 * @param modal
	 * @param date
	 * @param installer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */

	public boolean verifySecurityDeviceDetail(String makeOfDevice, String modal, String date, String installer,
			ExtentTest extentedReport, boolean screenshot) throws Exception {

		boolean status = true;
		try {

			WaitUtils.waitForElement(driver, btnEditSecurity);
			btnEditSecurity.click();
			WaitUtils.waitForSpinner(driver);
			Select selectMakeOfInstalledSecurityDevice = new Select(drpSelectMakeOfInstalledSecurityDevice);
			Select selectWhoInstalledDevice = new Select(drpSelectwhoInstalledDevice);

			if ((selectMakeOfInstalledSecurityDevice.getFirstSelectedOption().getText().equals(makeOfDevice))
					&& (selectWhoInstalledDevice.getFirstSelectedOption().getText().equals(installer))) {
				Log.message("Security details are restored successfully on change my car page", driver, extentedReport,
						true);
				return status;
			} else {
				status = false;
				Log.message("Security details are not resotred on change my car page", driver, extentedReport, true);
				return status;
			}
		} catch (Exception e) {
			Log.message("Error while verifying Security Device Detail : " + e);
			status = false;
			return status;
		}
	}

	/**
	 * To add Security Device
	 * 
	 * @param makeOfDevice
	 * @param modal
	 * @param date
	 * @param installer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void addSecurityDevice(String makeOfDevice, String modal, String date, String installer,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			enterSecurityDeviceDetail(makeOfDevice, modal, date, installer, extentedReport, screenshot);
			clickSaveDeviceBtn(extentedReport, screenshot);
			WaitUtils.waitForElement(driver, btnAddMoreDevicesNo);
			btnAddMoreDevicesNo.click();
			Log.message("Clicked on Add More Device as No", driver, extentedReport);
			WaitUtils.waitForSpinner(driver);

		} catch (Exception e) {
			throw new Exception("Error while adding Security Device  : " + e);
		}
	}

	/**
	 * To click SaveDevice Button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickSaveDeviceBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			btnSaveDvice.click();
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForElement(driver, lblAnotherSecurityDeviceToAdd);
			Log.message("Clicked on button Save Device button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking SaveDevice Button : " + e);
		}
	}

	/**
	 * To get Added Security Device row
	 * 
	 * @param deviceName
	 * @param Modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public String getSecurityDeviceRow(String deviceName, String modal, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String DeviceRow = null;
			WaitUtils.waitForSpinner(driver);
			if (lstAddedSecurityDevice.size() == 0) {
				Log.message("No Security devices are available", driver, extentedReport, false);
			}

			for (int loopCount = 0; loopCount < lstAddedSecurityDevice.size(); loopCount++) {
				Integer row = loopCount + 1;
				String displayedDeviceName = lstAddedSecurityDevice.get(loopCount).getText();
				if (displayedDeviceName.contains(deviceName) || displayedDeviceName.contains(modal)) {
					Log.message(deviceName + " - Secutiry deivce with Modal ( " + modal + " ) is found", driver,
							extentedReport, screenshot);
					return row.toString();
				}
			}
			return DeviceRow;

		} catch (Exception e) {
			throw new Exception("Error while getting Added Security Device row : " + e);
		}
	}

	/**
	 * To click Delete button of security device
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickSecurityDeviceDeleteBtn(String deviceName, String modal, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String deviceRow = getSecurityDeviceRow(deviceName, modal, extentedReport, false);
			if (deviceRow == null) {
				Log.message(deviceName + " - Security device is not found", extentedReport);
			}
			String elementString = cssDeletDeviceBtn + deviceRow;
			WebElement deleteBtn = driver.findElement(By.cssSelector(elementString));
			deleteBtn.click();
			WaitUtils.waitForinvisiblityofElement(driver, 60, elementString,
					"Delete button did not disappear after waiting for 60 sec");
			Log.message("Clicked on " + deviceRow + " (th) delete button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking delete button of security device : " + e);
		}
	}

	/**
	 * To click Edit button of security device
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickSecurityDeviceEditBtn(String deviceName, String modal, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String deviceRow = getSecurityDeviceRow(deviceName, modal, extentedReport, false);
			if (deviceRow == null) {
				throw new Exception(deviceName + " - Security device is not found");
			}
			String elementString = cssEditDeviceBtn + deviceRow;
			WebElement editBtn = driver.findElement(By.cssSelector(elementString));
			editBtn.click();
			Log.message("Clicked on " + deviceRow + " (th) edit button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking edit button of security device : " + e);
		}
	}

	/**
	 * To verify Edit & Delete button of security device
	 * 
	 * @param deviceName
	 * @param modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifySecurityDeviceDeleteAndEditBtn(String deviceName, String modal, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {

			boolean btnFound = false;
			String deviceRow = getSecurityDeviceRow(deviceName, modal, extentedReport, false);

			if (deviceRow == null) {
				throw new Exception(deviceName + " - Security device is not found");
			}

			String editElementString = cssEditDeviceBtn + deviceRow;
			WebElement editBtn = driver.findElement(By.cssSelector(editElementString));

			String deleteElementString = cssDeletDeviceBtn + deviceRow;
			WebElement deleteBtn = driver.findElement(By.cssSelector(deleteElementString));

			if (WaitUtils.waitForElement(driver, editBtn) && WaitUtils.waitForElement(driver, deleteBtn)) {
				btnFound = true;
			}
			return btnFound;
		} catch (Exception e) {
			throw new Exception("Error while verifying edit and delete button of security device : " + e);
		}
	}

	/**
	 * To verify Make Of Installed Security Device
	 * 
	 * @param makeOfDevice
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyMakeOfInstalledSecurityDevice(String makeOfDevice, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			boolean isMatch = false;
			WaitUtils.waitForElement(driver, drpSelectMakeOfInstalledSecurityDevice);
			Select selectMakeOfInstalledSecurityDevice = new Select(drpSelectMakeOfInstalledSecurityDevice);
			String selectedOption = selectMakeOfInstalledSecurityDevice.getFirstSelectedOption().getText();
			if (selectedOption.contains(makeOfDevice)) {
				isMatch = true;
				Log.message(makeOfDevice + " - is displayed in make of Installed security device ", driver,
						extentedReport, screenshot);
			} else {
				Log.message(selectedOption + " - is displayed in make of Installed security device instead of value - "
						+ makeOfDevice, driver, extentedReport, screenshot);
			}
			return isMatch;
		} catch (Exception e) {
			throw new Exception("Error while verifying Make Of Installed Security Device : " + e);
		}
	}

	/**
	 * To verify Modal Of Installed Security Device
	 * 
	 * @param modal
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyModalOfInstalledSecurityDevice(String modal, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean isMatch = false;
			String selectedOption = fldModalOfInstalledDevice.getAttribute("value");
			if (selectedOption.contains(modal)) {
				isMatch = true;
				Log.message(modal + " - is displayed in Modal of Installed security device ", driver, extentedReport,
						screenshot);
			} else {
				Log.message(selectedOption + " - is displayed in make of Installed security device instead of value - "
						+ modal, driver, extentedReport, screenshot);
			}
			return isMatch;
		} catch (Exception e) {
			throw new Exception("Error while verifying Modal Of Installed Security Device : " + e);
		}
	}

	/**
	 * To verify date the device fitted
	 * 
	 * @param modal
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyDeviceDate(String date, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			boolean isMatch = false;
			String selectedOption = fldDeviceDateAfterClickedEditBtn.getAttribute("value");
			if (selectedOption.contains(date)) {
				isMatch = true;
				Log.message(date + " - is displayed in date the device fitted", driver, extentedReport, screenshot);
			} else {
				Log.message(selectedOption + " - is displayed in in date the device fitted instead of value - " + date,
						driver, extentedReport, screenshot);
			}
			return isMatch;
		} catch (Exception e) {
			throw new Exception("Error while verifying date the device fitted : " + e);
		}
	}

	/**
	 * To verify Who Installed Device
	 * 
	 * @param makeOfDevice
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyWhoInstalledDevice(String installer, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean isMatch = false;
			Select SelectwhoInstalledDevice = new Select(drpSelectwhoInstalledDevice);
			String selectedOption = SelectwhoInstalledDevice.getFirstSelectedOption().getText();
			if (selectedOption.contains(installer)) {
				isMatch = true;
				Log.message(installer + " - is displayed in Who Installed Device ", driver, extentedReport, screenshot);
			} else {
				Log.message(selectedOption + " - is displayed in Who Installed Device instead of value - " + installer,
						driver, extentedReport, screenshot);
			}
			return isMatch;
		} catch (Exception e) {
			throw new Exception("Error while verifying Who Installed Device : " + e);
		}
	}

	/**
	 * To verify security device details
	 * 
	 * @param deviceName
	 * @param modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifySecurityDeviceDetails(String makeOfDevice, String modal, String date, String installer,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			int matchcount = 0;
			if (verifyMakeOfInstalledSecurityDevice(makeOfDevice, extentedReport, false)) {
				matchcount++;
			}

			if (verifyModalOfInstalledSecurityDevice(modal, extentedReport, false)) {
				matchcount++;
			}
			if (verifyDeviceDate(date, extentedReport, false)) {
				matchcount++;
			}

			if (verifyWhoInstalledDevice(installer, extentedReport, true)) {
				matchcount++;
			}

			if (matchcount == 4) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifying security device details : " + e);
		}
	}

	/**
	 * To verify green background color of yes/no button of Add Security Device
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyGreenBackgroundColorOfYesOrNoBtnOfAddSecurityDevice(String buttonName,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			boolean isGreencolor = false;
			if (buttonName.equals("Yes")) {
				if (btnYesAddSecutiyDevicecolor.getCssValue("background-color").equals(greenBtnBackgroundcolor)) {
					isGreencolor = true;
				}
			} else {
				if (btnNoAddSecutiyDevicecolor.getCssValue("background-color").equals(greenBtnBackgroundcolor)) {
					isGreencolor = true;
				}
			}
			Log.message(
					"Color of the button " + buttonName + " in Location details is green : " + greenBtnBackgroundcolor,
					driver, extentedReport, screenshot);
			return isGreencolor;
		} catch (Exception e) {
			throw new Exception("Error while verifying : " + buttonName
					+ " button green background color of Add Security Device : " + e);
		}
	}

	/**
	 * To verify grey background color of yes/no button of Add Security Device
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyGreyBackgroundColorOfYesOrNoBtnOfAddSecurityDevice(String buttonName,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			boolean isGreycolor = false;
			if (buttonName.equals("Yes")) {
				if (greyBtnBackgroundcolor.contains(btnYesAddSecutiyDevicecolor.getCssValue("background-color"))) {
					isGreycolor = true;
				}
			} else {

				if (greyBtnBackgroundcolor.contains(btnNoAddSecutiyDevicecolor.getCssValue("color"))) {
					isGreycolor = true;
				}
			}
			Log.message(
					"Color of the button " + buttonName + " in Location details is grey : " + greyBtnBackgroundcolor,
					driver, extentedReport, screenshot);
			return isGreycolor;
		} catch (Exception e) {
			throw new Exception("Error while verifying : " + buttonName
					+ " button grey background color of Add Security Device : " + e);
		}
	}

	/**
	 * To verify Yes/No button got selected to Add Security Device
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifySelectedYesOrNobtnOfAddSecurityDevice(String buttonName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			boolean btnSelected = false;

			if (buttonName.equals("Yes")) {
				if (btnRadioYesAddSecutiyDevice.getAttribute("checked").equals("true")) {
					btnSelected = true;
				}
			} else {
				if (btnRadioNoAddSecutiyDevice.getAttribute("checked").equals("true")) {
					btnSelected = true;
				}
			}
			return btnSelected;

		} catch (Exception e) {
			throw new Exception(
					"Error while verifying : " + buttonName + " button is selected to Add Security Device : " + e);
		}
	}

	// *************************Modification Details******************//

	/**
	 * To select Modification Type
	 * 
	 * @param device
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectModificationType(String modificationType, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpSelectModificationType);
			Select selectmodificationType = new Select(drpSelectModificationType);
			selectmodificationType.selectByVisibleText(modificationType);
			Log.message("Selected Modifiation Type : " + modificationType + "", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while selecting Modification Type : " + modificationType + " : " + e);
		}
	}

	/**
	 * To enter Additional Information
	 * 
	 * @param Modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterAdditionalInformation(String information, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			txtAreaAdditonalInfo.clear();
			txtAreaAdditonalInfo.sendKeys(information);
			Log.message("Entered Additional Information : " + information, driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Additional Information : " + information + " : " + e);
		}
	}

	/**
	 * To click Save Modification Button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickSaveModificationBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnSaveModification);
			btnSaveModification.click();
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForElement(driver, btnNoAnothermodificationToAdd);
			Log.message("Clicked on button Save Modification button", driver, extentedReport, screenshot);
			WaitUtils.waitForSpinner(driver);
		} catch (Exception e) {
			throw new Exception("Error while clicking Save Modification Button  : " + e);
		}
	}

	/**
	 * To click Yes/No button for modification made to vehicle
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoBtnToModificationOfVehicle(String buttonName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnYesAddModificationDetails);
			WaitUtils.waitForSpinner(driver);
			if (buttonName.trim().equals("Yes")) {
				btnYesAddModificationDetails.click();
			} else {
				btnNoAddModificationDetails.click();
			}
			Log.message("Clicked on button ' " + buttonName + " ' to add Modification", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking : " + buttonName + " to add modification device : " + e);
		}
	}

	/**
	 * To enter Modification Detail
	 * 
	 * @param Modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void addModificationDetail(String modificationType, String information, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			selectModificationType(modificationType, extentedReport, false);
			//enterAdditionalInformation(information, extentedReport, screenshot);
			clickSaveModificationBtn(extentedReport, false);
		} catch (Exception e) {
			throw new Exception("Error while entering Modification Detail : " + e);
		}
	}

	/**
	 * To get Added Security Device row
	 * 
	 * @param deviceName
	 * @param Modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public String getAddedModificationRow(String modificationType, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String modificationRow = null;

			if (lstModification.size() == 0) {
				Log.message("No added modification are available", driver, extentedReport, false);
			}

			for (int loopCount = 0; loopCount < lstModification.size(); loopCount++) {
				Integer row = loopCount + 1;
				String displayedDeviceName = lstModification.get(loopCount).getText();
				if (displayedDeviceName.contains(modificationType)) {
					Log.message(modificationType + " - Modification is found", driver, extentedReport, screenshot);
					return row.toString();
				}
			}
			return modificationRow;

		} catch (Exception e) {
			throw new Exception("Error while getting Added modificationRow row : " + e);
		}
	}

	/**
	 * To verify Edit & Delete button of modification type
	 * 
	 * @param deviceName
	 * @param modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyModificationDeleteAndEditBtn(String modificationType, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {

			boolean btnFound = false;
			String row = getAddedModificationRow(modificationType, extentedReport, false);

			if (row == null) {
				throw new Exception(modificationType + " - Modification is not found");
			}

			String editElementString = cssEditModificationBtn + row;
			WebElement editBtn = driver.findElement(By.cssSelector(editElementString));

			String deleteElementString = cssDeleteModificationBtn + row;
			WebElement deleteBtn = driver.findElement(By.cssSelector(deleteElementString));

			if (WaitUtils.waitForElement(driver, editBtn) && WaitUtils.waitForElement(driver, deleteBtn)) {
				btnFound = true;
			}
			return btnFound;
		} catch (Exception e) {
			throw new Exception("Error while verifying edit and delete button of modification : " + e);
		}
	}

	/**
	 * To click Delete button of modification type
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickModificationDeleteBtn(String modificationType, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String row = getAddedModificationRow(modificationType, extentedReport, false);
			if (row == null) {
				Log.message(modificationType + " - Modification Type is not found", extentedReport);
			}
			String elementString = cssDeleteModificationBtn + row;
			WebElement deleteBtn = driver.findElement(By.cssSelector(elementString));
			deleteBtn.click();
			WaitUtils.waitForinvisiblityofElement(driver, 60, elementString,
					"Delete button did not disappear after waiting for 60 sec");
			Log.message("Clicked on " + row + " (th) delete button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking delete button of modification type : " + e);
		}
	}

	/**
	 * /* To click Edit button of Modification
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickModificationEditBtn(String modificationType, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String deviceRow = getAddedModificationRow(modificationType, extentedReport, false);
			if (deviceRow == null) {
				Log.message(modificationType + " - Modification Type is not found", extentedReport);
			}
			String elementString = cssEditModificationBtn + deviceRow;
			WebElement editBtn = driver.findElement(By.cssSelector(elementString));
			editBtn.click();
			Log.message("Clicked on " + deviceRow + " (th) edit button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking edit button of security device : " + e);
		}
	}

	/**
	 * To verify modification Type
	 * 
	 * @param modificationType
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyModificationType(String modificationType, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean isMatch = false;
			WaitUtils.waitForElement(driver, drpSelectModificationType);
			Select selectMakeOfInstalledSecurityDevice = new Select(drpSelectModificationType);
			String selectedOption = selectMakeOfInstalledSecurityDevice.getFirstSelectedOption().getText();
			if (selectedOption.contains(modificationType)) {
				isMatch = true;
				Log.message(modificationType + " - is displayed in Modification Type ", driver, extentedReport,
						screenshot);
			} else {
				Log.message(
						selectedOption + " - is displayed in Modification Type instead of value - " + modificationType,
						driver, extentedReport, screenshot);
			}
			return isMatch;
		} catch (Exception e) {
			throw new Exception("Error while verifying modification Type : " + e);
		}
	}

	/**
	 * To verify Modal Of Installed Security Device
	 * 
	 * @param modal
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyAdditionalInformation(String information, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean isMatch = false;
			String selectedOption = txtAreaAdditonalInfo.getAttribute("value");
			if (selectedOption.contains(information)) {
				isMatch = true;
				Log.message(information + " - is displayed in Additional information ", driver, extentedReport,
						screenshot);
			} else {
				Log.message(selectedOption + " - is displayed in Additional information - " + information, driver,
						extentedReport, screenshot);
			}
			return isMatch;
		} catch (Exception e) {
			throw new Exception("Error while verifying Modal Of Installed Security Device : " + e);
		}
	}

	/**
	 * To verify Modification *Details
	 * 
	 * @param modificationType
	 * @param information
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyModificationDetails(String modificationType, String information, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			int matchcount = 0;
			if (verifyModificationType(modificationType, extentedReport, false)) {
				matchcount++;
			}

			/*if (verifyAdditionalInformation(information, extentedReport, false)) {
				matchcount++;
			}*/
			if (matchcount == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifying modification details : " + e);
		}
	}

	/**
	 * To select cancel button on security Details
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickCancelButtonOnSecurity(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForelementToBeClickable(driver, btnCancelSecurityDetails,
					"Cancel button is not found on security details ");
			btnCancelSecurityDetails.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Cancel button on seurity details in 'change my car' page", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking cancel button on security details page : " + e);
		}
	}

	/**
	 * To verify text inside the fields
	 * 
	 * @param element
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyTextInsidebox(WebElement element, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean status = true;
			String textInsideInputBox = element.getAttribute("value");

			// Check whether input field is blank
			if (textInsideInputBox.isEmpty())
				return status;
			else {
				status = false;
				return status;
			}

		} catch (Exception e) {
			throw new Exception("Error while clicking verifying text field" + element + e);

		}
	}

	/**
	 * To verify security details inside the fields
	 * 
	 * @param element
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */

	public boolean verifySecurityDeaitls(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			boolean status = true;
			WaitUtils.waitForElement(driver, fldModalOfInstalledDevice);
			if (verifyTextInsidebox(fldModalOfInstalledDevice, extentedReport, true)) {
				Log.message("text is not dispalying inside box" + fldModalOfInstalledDevice);
				return status;
			} else {
				status = false;
				Log.message("text is not dispalying inside box" + fldModalOfInstalledDevice);
				return status;
			}
		} catch (Exception e) {
			throw new Exception("Error while clicking verifying text field" + e);

		}
	}

	/**
	 * To verify security details inside the fields
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */

	public void clickCancelModificationBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnCancelModificationDetails);
			btnCancelModificationDetails.click();
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForinvisiblityofElement(driver, 30, cssCancelModificationBtn,
					"Cancel button for modification did not close after 30s");
			Log.message("Clicked on button Cancel Modification button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking cancel Modification Button  : " + e);
		}
	}

	/**
	 * To verify Modification details inside the fields
	 * 
	 * @param modificationType
	 * @param information
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickCancelModificationDetails(String modificationType, String information, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			selectModificationType(modificationType, extentedReport, false);
			clickCancelModificationBtn(extentedReport, true);
		} catch (Exception e) {
			throw new Exception("Error while entering Modification Detail : " + e);
		}
	}

	/**
	 * To verify Modification details inside the fields
	 * 
	 * @param modificationType
	 * @param information
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterModificationDetails(String modificationType, String information, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			selectModificationType(modificationType, extentedReport, false);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForElement(driver, btnModificationDuetoMedicalCondition);
			btnModificationDuetoMedicalCondition.click();
			Log.message("Clicked on Modification due to Medical Condition", driver, extentedReport);

		} catch (Exception e) {
			throw new Exception("Error while entering Modification Detail : " + e);
		}
	}

	public void selectAddtionalMedicalConditions(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnNoAdditionalModification);
			btnNoAdditionalModification.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Additional medical conditions are selected", driver, extentedReport);
		} catch (Exception e) {
			Log.message("While selecting additional medical conditions" + e);
		}
	}

	/**
	 * To verify Modification details inside the fields with Edit button
	 * 
	 * @param modificationType
	 * @param information
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyModificationDeatils(String modificationType, String information, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			boolean status = true;
			WaitUtils.waitForElement(driver, btnEditModificationDetails);
			btnEditModificationDetails.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Edit button selected on modification details section", driver);
			Select selectmodificationType = new Select(drpSelectModificationType);
			if (selectmodificationType.getFirstSelectedOption().getText().equals(modificationType)) {
				Log.message("Modification details are restored on change of my car page", driver, extentedReport, true);
				return status;
			} else {
				status = false;
				Log.message("Modification details are not restored on change my car page", driver, extentedReport,
						true);
				return status;
			}

		} catch (Exception e) {
			throw new Exception("Error while entering Modification Detail : " + e);
		}
	}

	/**
	 * To verify Modification details inside the fields
	 * 
	 * @param modificationType
	 * @param information
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */

	public boolean verifyModificationDeatilsCancel(String modificationType, String information,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			boolean status = true;
			WaitUtils.waitForElement(driver, drpSelectModificationType);
			Select selectmodificationType = new Select(drpSelectModificationType);
			if (selectmodificationType.getFirstSelectedOption().getText().equals(modificationType)) {
				Log.message("Modification details are restored on change of my car page", driver, extentedReport, true);
				return status;
			} else {
				status = false;
				Log.message("Modification details are not restored on change my car page", driver, extentedReport,
						true);
				return status;
			}
		} catch (Exception e) {
			throw new Exception("Error while entering Modification Detail : " + e);
		}
	}
		
}
