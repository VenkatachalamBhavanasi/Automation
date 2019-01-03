
package com.pure.selfservice.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.generic.support.Log;
import com.generic.utils.ElementLayer;
import com.generic.utils.GenericUtils;
import com.generic.utils.WaitUtils;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * ChangeMyAddressPage contains Customer contact details
 * 
 */
public class ChangeMyAddressPage extends LoadableComponent<ChangeMyAddressPage> {
	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	public String CHANGE_POLICY_ADDRESS_PROMPT_MSG = "Your Correspondence address not match the postcode where your car is parked overnight. Would you like to amend your overnight address?";
	public String MANAGE_CONTACT_DETAILS_MSG = "If you need to change your correspondence address and other contact details, you can do this on the 'Manage Contact Details' screen of this website, click here to visit this page";
	public String CHANGE_CORRESPONDENCE_ADDRESS_PROMPT_MSG = "Are you sure you want to change your Correspondence Address to match the Overnight Location? This means the address for postal correspondence will change?";

	public String cssNoButton = "button[title='No']";
	public String cssSaveAddress = "button[title='Save Address']";

	@FindBy(css = "div[class*='change-risk-address'] h2")
	WebElement txtTitleChangeMyOvernightLocation;

	@FindBy(css = ".col-md-10")
	WebElement changePolicyMsgField;

	@FindBy(css = "#C1__btn-change-overnight")
	WebElement btnYesChangePolicyAddr;

	@FindBy(css = "#C1__BUT_1A9461D6220E2CA9270314")
	WebElement btnNoChangePolicyAddr;

	@FindBy(css = "#C2__C1__QUE_3199650579E1D4E8873022")
	WebElement clientPostCode;

	@FindBy(css = "[id*='C1__ID_CHANGEADDRESS_CANCEL_BTN']")
	WebElement btnCancel;

	@FindBy(css = "p.text-header")
	WebElement txtManageContactDetailMsg;

	@FindBy(css = "button[title='Add another policy change']")
	WebElement btnAddAnotherPolicyChange;

	@FindBy(css = "#C1__BUT_82C3F84B8B5F77B74578")
	WebElement btnEditChanges;

	@FindBy(css = "select[name*='OVERNIGHTLOCATION']")
	WebElement drpSelectOvernightLocation;

	@FindBy(css = "#C2__C1__p1_QUE_3199650579E1D4E8855178")
	WebElement txtVehicleQue;

	@FindBy(css = "#C2__C1__QUE_3199650579E1D4E8855178_0")
	WebElement btnYesAddressConfirm;

	@FindBy(css = "#C2__C1__QUE_3199650579E1D4E8855178_1")
	WebElement btnNoAddressConfirm;

	@FindBy(css = "button[title='Change Address' i]")
	WebElement btnChangeAddress;

	@FindBy(css = "input[name*='NEWADDRESS[1].POSTCODE']")
	WebElement fldPostCode;

	@FindBy(css = "select[name*='ADDRESSLIST']")
	WebElement drpSelectAddressList;

	@FindBy(css = "[id*='C1__BUT_B010FEC6822AF308619365']")
	WebElement btnCancelAddress;

	@FindBy(css = "#C2__C1__COL_3199650579E1D4E8899198")
	WebElement txtOvernightLocationAddress;

	@FindBy(css = "#C1__row_HEAD_97614C78E3A0F03F2263264")
	WebElement premium;

	@FindBy(css = "button[title='Quote']")
	WebElement btnQuote;

	@FindBy(css = "[id*='p1_QUE_2D324756C180CBB11999980']")
	WebElement txtChangeCorrespondenceAddressToOvernightLocation;

	@FindBy(css = "#C2__C1__p4_QUE_2D324756C180CBB11999980 label:nth-child(1)")
	WebElement btnYesChangeCorrespondenceAddress;

	@FindBy(css = "#C2__C1__p4_QUE_2D324756C180CBB11999980 label:nth-child(2)")
	WebElement btnNoChangeCorrespondenceAddress;

	@FindBy(css = "#C2__C1__p4_QUE_2D324756C180CBB11999980 label:nth-child(1) input")
	WebElement btnRadioYesChangeCorrespondenceAddress;

	@FindBy(css = "#C2__C1__p4_QUE_2D324756C180CBB11999980 label:nth-child(2) input")
	WebElement btnRadioNoChangeCorrespondenceAddress;

	@FindBy(css = "#C2__C1__FMT_BBBA702406E00AFA223387")
	WebElement mdlChangeCorrespondenceAddress;

	@FindBy(css = "#C2__C1__p4_HEAD_D7DD5ACB9608E590369082")
	WebElement txtChangeCorrespondenceAddressModal;

	@FindBy(css = "button[title='Yes']")
	WebElement btnYesInChangeAddressModel;

	@FindBy(css = "button[title='No']")
	WebElement btnNoInChangeAddressModel;

	@FindBy(css = "#C2__C1__p4_QUE_2D324756C180CBB11999980 label:nth-child(1) input")
	WebElement btnRadioYesVehicleKeptAtCurrentAddress;

	@FindBy(css = "#C2__C1__p4_QUE_2D324756C180CBB11999980 label:nth-child(2) input")
	WebElement btnRadioNoVehicleKeptAtCurrentAddress;

	@FindBy(css = "#C2__C1__QUE_3199650579E1D4E8873019")
	WebElement txtCorrespondenceAddress;

	//
	@FindBy(css = "#C1__BUT_82C3F84B8B5F77B74578")
	WebElement lnkClickHere;

	@FindBy(css = "#FMT_8E7EB693E1D30B9E85983")
	WebElement mdlManageContactDetail;

	// ***Change Overnight location****//

	@FindBy(css = "button[title='Enter Address Manually' i]")
	WebElement btnEnterAddressManually;

	@FindBy(css = "button[title='Find Address' i]")
	WebElement btnFindAddress;

	@FindBy(css = "input[name*='ADDRESSLINE1']")
	WebElement fldAddressLine1;

	@FindBy(css = "input[name*='ADDRESSLINE2']")
	WebElement fldAddressLine2;

	@FindBy(css = "input[name*='ADDRESSLINE3']")
	WebElement fldAddressLine3;

	@FindBy(css = "input[name*='ADDRESSLINE4']")
	WebElement fldAddressLine4;

	@FindBy(css = "select[name*='COUNTRY']")
	WebElement drpSelectCountry;

	@FindBy(css = "button[title='Save Address']")
	WebElement btnSaveAddress;

	/**********************************************************************************************
	 ********************************* WebElements of ChangeMyAddress- Ends *************************
	 **********************************************************************************************/

	/**
	 * 
	 * Constructor class for ChangeMyAddressPage Here we initializing the driver for
	 * page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public ChangeMyAddressPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(txtTitleChangeMyOvernightLocation.isDisplayed()))) {
			Log.fail("User is not navigated to ChangeMyAddressPage ", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {
		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
	}

	/**
	 * To change address message
	 * 
	 * @param String
	 *            type
	 * @param String
	 *            Error message
	 * @param extentedReport
	 * @throws Exception
	 * @return boolean
	 * 
	 */
	public boolean verifyChangeAddressMsg(String type, String ErrMsg, ExtentTest extentedReport) throws Exception {
		try {
			String isYes = btnYesChangePolicyAddr.getAttribute("title");
			String isNo = btnNoChangePolicyAddr.getAttribute("title");

			boolean status = false;
			if (type.equalsIgnoreCase("Change Address")) {
				if (GenericUtils.verifyWebElementTextEquals(changePolicyMsgField, CHANGE_POLICY_ADDRESS_PROMPT_MSG)) {
					status = true;
				}
			}

			if (isYes.equalsIgnoreCase("Yes") && isNo.equalsIgnoreCase("No")) {
				status = true;
				Log.message("Prompt Message is displayed with 'YES' and 'NO' button");
			} else {
				Log.fail("Prompt message is not having YES and NO button");
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while verifying Change Address message : " + e);
		}
	}

	/**
	 * To click cancel button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickCancelbtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForelementToBeClickable(driver, btnCancel,
					"Cancel button is not found in 'change my policy address' page");
			btnCancel.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Cancel button in 'change my policy address' page", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking Cancel button in 'Change My Policy Address' page : " + e);
		}
	}

	/**
	 * Click Add Another Policy changes
	 *
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickAddAnotherPolicyChange(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnAddAnotherPolicyChange);
			btnAddAnotherPolicyChange.click();
			Log.message("Clicked on Add Another Policy Change button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking Add Another Policy Change button: " + e);
		}
	}

	/**
	 * To select overnight location
	 * 
	 * @param String
	 *            reason
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectOverNightLocation(String reason, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			if (WaitUtils.waitForElement(driver, drpSelectOvernightLocation)) {
				Select drpReason = new Select(drpSelectOvernightLocation);
				drpReason.selectByVisibleText(reason);
				Log.message("Overnight location is selected - " + reason, driver, extentedReport, screenshot);
			} else {
				throw new Exception("Drop down to select Overnight location is not found");
			}
		} catch (Exception e) {
			throw new Exception("Unable to select Overnight location : " + e);
		}
	}

	/**
	 * To verify manage contact details message
	 * 
	 * @param String
	 *            ErrMsg
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public boolean verifyManageContactDetailsMsg(String ErrMsg, ExtentTest extentedReport) throws Exception {
		boolean status = false;
		GenericUtils.scrollIntoView(driver, btnQuote);
		if (WaitUtils.waitForElement(driver, txtManageContactDetailMsg)) {
			if (GenericUtils.verifyWebElementTextEquals(txtManageContactDetailMsg, MANAGE_CONTACT_DETAILS_MSG))
				status = true;
		}
		return status;
	}

	/**
	 * To Verify Yes and No Button in address confirm
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * 
	 */
	public void clickYesNoAddressConfirm(ExtentTest extentedReport, boolean screenshot) {
		String isChecked = btnNoAddressConfirm.getAttribute("checked");
		if (isChecked == "checked") {
			btnYesAddressConfirm.click();
			if (WaitUtils.waitForElement(driver, btnChangeAddress)) {
				Log.message("Change Address is displayed while clicking on Yes button");
			} else {
				Log.message("Change Address is not displayed while clicking on Yes button");
			}
		} else {
			btnNoAddressConfirm.click();
			if (WaitUtils.waitForElement(driver, btnChangeAddress)) {
				Log.message("Change Address is displayed while clicking on No button");
			} else {
				Log.message("Change Address is not displayed while clicking on No button");
			}
		}

	}

	/**
	 * To get Default Address
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * 
	 */
	public String getDefaultAddress(ExtentTest extentedReport, boolean screenshot) {
		String defaultAddress = txtOvernightLocationAddress.getText();
		Log.message(defaultAddress + " - Address is displayed as overnight location", driver, extentedReport,
				screenshot);
		return defaultAddress;

	}

	/**
	 * Click change Address button
	 *
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickChangeAddressBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			WaitUtils.waitForElement(driver, btnChangeAddress);
			btnChangeAddress.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Change Address button in change my policy address screen", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Unable to click Change Address button in change my policy address screen : " + e);
		}
	}

	/**
	 * Click Find Address Button
	 *
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickFindAddressBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			btnFindAddress.click();
			WaitUtils.waitForElement(driver, drpSelectAddressList);
			Log.message("Clicked on Find Address button in change my policy address screen", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Unable to click Find Address button in change my policy address screen : " + e);
		}
	}

	/**
	 * To enter Change Overnight Location Detail For Enter Address Manually
	 * 
	 * @param country
	 * @param postcode
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterAddressManuallyChangeOvernightLocationDetail(String option, String value,ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			if (option.equalsIgnoreCase("Postcode")) {
				WaitUtils.waitForElement(driver, fldPostCode);
				fldPostCode.clear();
				fldPostCode.sendKeys(value);
				Log.message("Entered the Postcode : " + value, driver, extentedReport, false);
			} else if (option.equalsIgnoreCase("Address1")) {
				WaitUtils.waitForElement(driver, fldAddressLine1);
				fldAddressLine1.click();
				WaitUtils.waitForSpinner(driver);
				fldAddressLine1.clear();
				fldAddressLine1.sendKeys(value);
				Log.message("Entered the Address1 : " + value, driver, extentedReport, screenshot);
			} else if (option.equalsIgnoreCase("Address2")) {
				fldAddressLine2.click();
				WaitUtils.waitForSpinner(driver);
				fldAddressLine2.clear();
				fldAddressLine2.sendKeys(value);
				Log.message("Entered the Address2 : " + value, driver, extentedReport, screenshot);
			} else if (option.equalsIgnoreCase("Address3")) {
				fldAddressLine3.click();
				WaitUtils.waitForSpinner(driver);
				fldAddressLine3.clear();
				fldAddressLine3.sendKeys(value);
				Log.message("Entered the Address3 : " + value, driver, extentedReport, screenshot);
			} else if (option.equalsIgnoreCase("Address4")) {
				fldAddressLine4.click();
				WaitUtils.waitForSpinner(driver);
				fldAddressLine4.clear();
				fldAddressLine4.sendKeys(value);
				fldAddressLine4.click();
				Log.message("Entered the Address4 : " + value, driver, extentedReport, screenshot);
			}
		} catch (Exception e) {
			throw new Exception(
					"Error while entering Change Overnight Location Detail : " + option + "manually : " + e);
		}
	}

	/**
	 * To select Address
	 * 
	 * @param String
	 *            postCode
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectAddress(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			if (WaitUtils.waitForElement(driver, drpSelectAddressList)) {
				Select drpAddress = new Select(drpSelectAddressList);
				drpAddress.selectByIndex(0);
				WaitUtils.waitForElement(driver, fldAddressLine1);
				Log.message("Address selected from address list", driver, extentedReport, screenshot);
			} else {
				throw new Exception("Drop down to select overnight Address is not found");
			}
		} catch (Exception e) {
			throw new Exception("Unable to select address : " + e);
		}
	}

	/**
	 * To Click Quote button in change address field
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
			Log.message("Clicked on 'Quote' button in 'change my policy address' page", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Quote' button in 'change my policy address' page : " + e);
		}
	}

	/**
	 * To Click 'Enter Address Manually' button in change address field
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickEnterAddressManuallyBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnEnterAddressManually);
			btnEnterAddressManually.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Enter Address Manually' button in 'change my policy address' page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception(
					"Error while clicking 'Enter Address Manually' button in 'change my policy address' page : " + e);
		}
	}

	/**
	 * To verify Overnight location of vehicle
	 * 
	 * @param overnightLocation
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyOvernightLocationOfVehicle(String overnightLocation, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			boolean isMatch = false;
			WaitUtils.waitForElement(driver, drpSelectOvernightLocation);
			Select drpOvernightLocation = new Select(drpSelectOvernightLocation);
			String selectedOption = drpOvernightLocation.getFirstSelectedOption().getText();
			if (selectedOption.contains(overnightLocation)) {
				isMatch = true;
				Log.message(overnightLocation + " - is displayed in Overnight location of vehicle ", driver,
						extentedReport, screenshot);
			} else {
				Log.message(selectedOption + " - is displayed in Overnight location of vehicle instead of value - "
						+ overnightLocation, driver, extentedReport, screenshot);
			}
			return isMatch;
		} catch (Exception e) {
			throw new Exception("Error while verifying Overnight location of vehicle : " + e);
		}
	}

	/**
	 * To click Yes/No button To change Correspondence address to match overnight
	 * location
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoBtnToChangeAddressToMatchOvernightLocation(String buttonName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {

			if (buttonName.equals("Yes")) {
				WaitUtils.waitForElement(driver, btnYesChangeCorrespondenceAddress);
				btnYesChangeCorrespondenceAddress.click();
			} else {
				WaitUtils.waitForElement(driver, btnNoChangeCorrespondenceAddress);
				btnNoChangeCorrespondenceAddress.click();
			}
			Log.message(
					"Clicked on button ' " + buttonName
							+ " ' to change the corresponding address to match overnight location",
					driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking : " + buttonName
					+ " to change the corresponding address to match overnight location : " + e);
		}
	}

	/**
	 * To verify Change Correspondence Address Modal Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyChangeCorrespondenceAddressModalMsg(ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, mdlChangeCorrespondenceAddress);

			if (GenericUtils.verifyWebElementTextEquals(txtChangeCorrespondenceAddressModal,
					CHANGE_CORRESPONDENCE_ADDRESS_PROMPT_MSG)) {
				isDisplayed = true;
				Log.message("Modal with message (" + CHANGE_CORRESPONDENCE_ADDRESS_PROMPT_MSG + " ) is displayed ",
						driver, extentedReport, screenshot);
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while verifying Overnight location of vehicle : " + e);
		}
	}

	/**
	 * To click link 'Click Here'
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickLinkClickHere(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			lnkClickHere.click();
			Log.message("Clicked on link 'Click Here'", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while clicking link 'Click Here' : " + e);
		}
	}

	/**
	 * To verify Address Fields Auto-Populated
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyAddressFieldsAutoPopulated(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			boolean isPopulated = false;
			Integer count = 0;

			if (!fldAddressLine1.getAttribute("value").equals("")) {
				Log.message("Address line 1 Field is auto-populated wiht value", extentedReport);
				count++;
			} else {
				Log.message("Address line 1 Field is not auto-populated wiht value", extentedReport);
			}

			if (!fldAddressLine2.getAttribute("value").equals("")) {
				Log.message("Address line 2 Field is auto-populated wiht value", extentedReport);
				count++;
			} else {
				Log.message("Address line 2 Field is not auto-populated wiht value", extentedReport);
			}

			if (!fldAddressLine3.getAttribute("value").equals("")) {
				Log.message("Address line 3 Field is auto-populated wiht value", extentedReport);
				count++;
			} else {
				Log.message("Address line 3 Field is not auto-populated wiht value", extentedReport);
			}

			if (!fldAddressLine4.getAttribute("value").equals("")) {
				Log.message("Address line 4 Field is auto-populated wiht value", extentedReport);
				count++;
			} else {
				Log.message("Address line 4 Field is not auto-populated wiht value", extentedReport);
			}

			if (count == 4) {
				isPopulated = true;
			}

			return isPopulated;
		} catch (Exception e) {
			throw new Exception("Error while verifying Address Fields AutoPopulated : " + e);
		}
	}

	/**
	 * To click Yes/No button in change Correspondence address to match overnight
	 * location popup
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoBtnInChangeAddressPopup(String buttonName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {

			if (buttonName.equals("Yes")) {
				WaitUtils.waitForElement(driver, btnYesInChangeAddressModel);
				WaitUtils.waitForSpinner(driver);
				btnYesInChangeAddressModel.click();
			} else {
				WaitUtils.waitForElement(driver, btnNoInChangeAddressModel);
				btnNoInChangeAddressModel.click();
			}
			WaitUtils.waitForinvisiblityofElement(driver, 10, cssNoButton,
					"Change Correspondence address to match overnight location popup is not closed after waiting for 10s");
			Log.message(
					"Clicked on button ' " + buttonName
							+ " ' in the modal popup 'change the corresponding address to match overnight location'",
					driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking ' " + buttonName
					+ " ' in the modal popup 'change the corresponding address to match overnight location' " + e);
		}
	}

	/**
	 * To verify button got selected for change address to match overnight location
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyYesOrNoBtnSelectedForChangeAddress(String buttonName, ExtentTest extentedReport,
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
					+ " button is selected for for change address to match overnight location : " + e);
		}
	}

	/**
	 * To select country
	 * 
	 * @param country
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectcountry(String country, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			if (WaitUtils.waitForElement(driver, drpSelectCountry)) {
				Select drpCountry = new Select(drpSelectCountry);
				drpCountry.selectByVisibleText(country);
				Log.message("Selcted country - " + country, driver, extentedReport, screenshot);
			} else {
				throw new Exception("Drop down to select country is not found");
			}
		} catch (Exception e) {
			throw new Exception("Error while selecting country : " + e);
		}
	}

	/**
	 * To enter Change Overnight Location Detail For FindAddress
	 * 
	 * @param country
	 * @param postcode
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterFindAddressChangeOvernightLocationDetail(String postcode, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			enterAddressManuallyChangeOvernightLocationDetail("Postcode", postcode, extentedReport,
					screenshot);
			clickFindAddressBtn(extentedReport, true);
			selectAddress(extentedReport, true);
		} catch (Exception e) {
			throw new Exception("Error while entering Change Overnight Location Detail : " + e);
		}
	}

	/**
	 * To save Change Overnight Location Detail
	 * 
	 * @param country
	 * @param postcode
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickSaveAddressBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			btnSaveAddress.click();
			WaitUtils.waitForSpinner(driver);
			if (WaitUtils.waitForElement(driver, btnSaveAddress)) {
				btnSaveAddress.click();
			}
			WaitUtils.waitForinvisiblityofElement(driver, 10, cssSaveAddress,
					"Change Overnight location is not saved after waiting for 10s");
			Log.message("Clicked on Save address button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while saving Change Overnight Location Detail : " + e);
		}
	}

	/**
	 * To Click cancel button in change overnight location detail
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickCancelBtnForOvernightLocation(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			btnCancelAddress.click();
			WaitUtils.waitForSpinner(driver);
			if (WaitUtils.waitForElement(driver, btnCancelAddress)) {
				btnCancelAddress.click();
			}
			WaitUtils.waitForinvisiblityofElement(driver, 10, cssSaveAddress,
					"Change Overnight location is not canceled after waiting for 10s");
			Log.message("Clicked on Cancel button in change overnight location detail", extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking cancel button in change overnight location detail : " + e);
		}
	}

	/**
	 * To verify Over night Location Address
	 * 
	 * @param address
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyOvernightLocationAddress(String address, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean addressAdded = false;

			String displayedAddress = getDefaultAddress(extentedReport, false);
			if (displayedAddress.trim().replaceAll("\\s+", "").contains(address)) {
				addressAdded = true;
			}
			return addressAdded;

		} catch (Exception e) {
			throw new Exception("Error while verifying Overnight Location Address : " + e);
		}
	}

	/**
	 * To verify Correspondence Address
	 * 
	 * @param address
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyCorrespondenceAddress(String address, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean addressAdded = false;

			String defaultAddress = txtCorrespondenceAddress.getText();
			Log.message(defaultAddress + " - Address is displayed as Correspondence Address", driver, extentedReport,
					screenshot);
			if (defaultAddress.trim().replaceAll("\\s+", "").contains(address)) {
				addressAdded = true;
			}
			return addressAdded;

		} catch (Exception e) {
			throw new Exception("Error while verifying Correspondence Address : " + e);
		}
	}

	/**
	 * To verify button got selected for change address to match overnight location
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyBtnDisabledForChangeCorrespondenceAddress() throws Exception {
		try {
			boolean isDisabled = false;

			if (btnRadioYesChangeCorrespondenceAddress.getAttribute("disabled").equals("true")
					&& btnRadioNoChangeCorrespondenceAddress.getAttribute("disabled").equals("true")) {
				isDisabled = true;
			}
			return isDisabled;

		} catch (Exception e) {
			throw new Exception(
					"Error while verifying Yes and No buttons are disabled for change Correspondence address to match overnight location : "
							+ e);
		}
	}

}
