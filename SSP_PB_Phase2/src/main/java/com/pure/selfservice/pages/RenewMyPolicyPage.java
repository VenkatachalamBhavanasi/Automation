package com.pure.selfservice.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.generic.support.Log;
import com.generic.utils.ElementLayer;
import com.generic.utils.GenericUtils;
import com.generic.utils.WaitUtils;
import com.relevantcodes.extentreports.ExtentTest;

public class RenewMyPolicyPage extends LoadableComponent<RenewMyPolicyPage> {

	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	public final String CAR_INSURENCE_POLICY_MSG = "This is a direct renewal of your current insurance policy, you cannot make any changes to your details. Basic information about your policy renewal is shown below. Full details are shown in the documents section, please read through your documents carefully to ensure accuracy. If any of your policy details are incorrect or you need to make any changes to your quote information, please call your broker on XXXX XXX XXXX. Quote details and costs are shown on the next page.";
	public final String CAR_INSURENCE_Details_MSG = "Car Insurance Renewal Details";
	public final String Home_INSURENCE_Details_MSG = "Home Insurance Renewal Details";
	public final String TERMS_AND_CONDITION_MSG = "I can confirm that I have read all the information on this page in its entirety, everything stated here is correct and current, no amendments or updates are required prior to the renewal of my policy.";
	public String lstdocuments = "#C2__C1__C1__view_document_R";
	public final String TERMS_AND_CONDITION_Note = "If any of the details here are incorrect or you need to make any changes to your quote information, please call your broker on XXXX XXX XXXX.";
	public final String POPUP_MSG_HELPICON_Renew_My_Policy = "This screen is for reviewing your policy details and ensuring that the details presented here are correct to proceed with the renewal of your policy. Please contact your Broker on XXXXX if you wish to amend any details";
	public final String Msg_In_Cancel_Popup = "Your renewal has not been processed";
	public final String txtForRenewalTypeHeader = "Please read the information on this page and check that everything is correct.";
	public final String txtForManualRenewalHappyToRenew = "Happy to renew? Click 'buy' at the bottom of the page.";
	public final String txtForManualRenewalNeedToMakeAChange = "Need to make a change? Please call us to make policy changes.";
	public final String txtForManualRenewalDontWantToRenew = "Don't want to renew? No need to do anything, your policy expires on.";
	public final String txtForAutomaticRenewalHappyToRenew = "Happy to renew? No need to do anything, your policy will auto-renew.";
	public final String txtForAutomaticRenewalNeedToMakeAChange = "Need to make a change? Please call us to make policy changes.";
	public final String txtForAutomaticRenewalDontWantToRenew = "Don't want to renew?  Please call us if you want to stop your renewal.";
	public final String txtForAutomaticChangingPaymentMethod = "Changing payment method? You can switch the payment method below.";
	public final String txtForNoClaimsDiscountProtection = "No Claims Discount Protection";

	@FindBy(css = "#C2__C1__HEAD_218F18D73E2813DC1030297")
	WebElement txtTitleRenewMyPolicy;

	@FindBy(css = "#C2__C1__HEAD_218F18D73E2813DC1030298")
	WebElement txtTitleCarInsuranceRenewalDetails;

	@FindBy(css = "#C2__C1__HEAD_4E5990D5ACF49696650733")
	WebElement txtTitleHomeInsuranceRenewalDetails;

	@FindBy(css = "#C2__C1__QUE_218F18D73E2813DC1030325")
	WebElement txtPeriodCoverForOriginalPolicyMsg;

	@FindBy(css = "#C2__C1__QUE_218F18D73E2813DC1030326")
	WebElement txtPeriodCoverForPolicyRenewalMsg;

	@FindBy(css = "#C2__C1__FS_QUE_218F18D73E2813DC1030482 > div > label")
	WebElement txtchkTermsandConditionsMsg;

	@FindBy(css = "div[id='C2__C1__p1_HEAD_218F18D73E2813DC1030483']")
	WebElement txtchkTermsandConditionsNote;

	@FindBy(css = "div[class*='checkbox checkbox-info text-left renew_checkbox'] > label") // div[class*='checkbox
	// checkbox-info text-left
	// renew_checkbox']
	WebElement chkTermsandConditions;

	@FindBy(css = "#C2__C1__BUT_218F18D73E2813DC1030485")
	WebElement btnCancel;

	@FindBy(css = "button[title='Close']")
	WebElement btnClose;

	@FindBy(css = "button[id='C2__C1__BUT_FA30D94E9C7869E01723364']")
	WebElement btnChange;

	@FindBy(css = "#C2__C1__p1_HEAD_218F18D73E2813DC1030299 > div")
	WebElement txtInsuranceRenewalDetails;

	@FindBy(css = "#C2__C1__HEAD_4DAF1474CE5A264B983702")
	WebElement txtVehicleRegistrationNumber;

	@FindBy(css = "#C2__C1__p1_HEAD_218F18D73E2813DC1030301")
	WebElement txtvehicleDetails;

	@FindBy(css = "#C2__C1__HELP_HEAD_218F18D73E2813DC1030298")
	WebElement iconHelpCarRenewMyPolicy;

	@FindBy(css = "#C2__C1__HELP_HEAD_4E5990D5ACF49696650733")
	WebElement iconHelpHomeRenewMyPolicy;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpRenewMyPolicy;

	@FindBy(css = "[id *= C2__C1__C1__view_document_R]")
	List<WebElement> noOfDocuments;

	@FindBy(css = "#C2__C1__QUE_218F18D73E2813DC1030353")
	WebElement txtThePropertyToBeInsured;

	@FindBy(css = "#C2__C1__p1_HEAD_1F145A4C2A19EE9F1539973")
	WebElement msgInCancelPopup;

	@FindBy(css = "#C2__C1__p1_HEAD_5708D6023CBAFC1B1793559")
	WebElement txtRenewalTypeHeader;

	@FindBy(css = "#C2__C1__p1_HEAD_5708D6023CBAFC1B1626866")
	WebElement txtHappyToRenewMsg;

	@FindBy(css = "#C2__C1__p1_HEAD_5708D6023CBAFC1B1631482")
	WebElement txtNeedToMakeChangeMsg;

	@FindBy(css = "        #C2__C1__p1_HEAD_5708D6023CBAFC1B1631488")
	WebElement txtMsgDontWantToRenew;

	@FindBy(css = "#C2__C1__p1_HEAD_5708D6023CBAFC1B1793579")
	WebElement txtChangingPaymentMethod;

	@FindBy(css = "button[title='Back']")
	WebElement btnBack;

	@FindBy(css = "#C2__C1__BUT_218F18D73E2813DC1030486")
	WebElement btnBuy;

	@FindBy(css = "#C2__C1__BUT_218F18D73E2813DC1030486")
	WebElement btnRenew;

	@FindBy(css = "#C2__C1__BUT_218F18D73E2813DC1030486")
	WebElement btnChangePaymentMethod;

	@FindBy(css = "#C2__C1__HEAD_FA30D94E9C7869E01709454")
	WebElement txtTitlePayment;

	@FindBy(css = "#C2__C1__HEAD_19DC4308D1EC83DF378600")
	WebElement txtTitleNoClaimsDiscountProtection;

	@FindBy(css = "[id *= C2__C1__QUE_B206C76683F45E66880103_R]")
	List<WebElement> averageNCDFirstTable;

	@FindBy(css = "[id *= C2__C1__QUE_B206C76683F45E661016430_R]")
	List<WebElement> averageNCDSecondTable;

	@FindBy(css = "[id *=C2__C1__QUE_B206C76683F45E66880098_R]")
	List<WebElement> numberOfYearsFirstTable;

	@FindBy(css = "[id *=C2__C1__QUE_B206C76683F45E661016429_R]")
	List<WebElement> numberOfYearsSecondTable;

	@FindBy(css = "#C2__C1__FMT_821080FB70B7488F572301 > div.ncd-panel-heading.clearfix > h4 > a > i.fa.fa-caret-down.pull-right")
	WebElement btnExpand;

	@FindBy(css = "#C2__C1__QUE_3260BCDA086476EF445227")
	WebElement txtNumberOfYearsNoClaimsAccrued;

	@FindBy(css = "#C2__C1__QUE_3260BCDA086476EF445229")
	WebElement txtAdditionalCostOfNoClaims;

	@FindBy(css = "##C2__C1__p1_HEAD_218F18D73E2813DC1030299")
	WebElement txtforNCDClaims;

	@FindBy(css = "[id *=C2__C1__QUE_B206C76683F45E66889440_R]")
	List<WebElement> numberOfClaimsFirstTable;

	@FindBy(css = "[id *=C2__C1__QUE_B206C76683F45E66889441_R]")
	List<WebElement> withNCDProtectionFirstTable;

	@FindBy(css = "[id *=C2__C1__QUE_B206C76683F45E66898763_R]")
	List<WebElement> numberOfClaimsSecondTable;

	@FindBy(css = "[id *=C2__C1__QUE_B206C76683F45E66898764_R]")
	List<WebElement> withOutNCDProtectionTable;

	/**
	 * 
	 * Constructor class for Renew My Policy Page Here we initializing the driver
	 * for page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */

	public RenewMyPolicyPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void load() {
		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
		WaitUtils.waitForElement(driver, btnChange);

	}

	@Override
	protected void isLoaded() throws Error {
		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(txtTitleRenewMyPolicy.isDisplayed()))) {
			Log.fail("Renew my policy page did not open up. Error Page is displayed.", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);

	}

	/**
	 * Get terms and conditions
	 * 
	 * @return terms and conditions
	 * 
	 *//*
		 * public String verifyTermsandConditions() { return
		 * GenericUtils.getTextOfWebElement(txtchkTermsandConditionsMsg, driver); }
		 */

	/**
	 * Get Original Policy date
	 * 
	 * @return Original Policy date
	 * 
	 */
	public boolean getPeriodCoverForOriginalPolicy(String originalPolicyDate) {
		return GenericUtils.verifyWebElementTextContains(txtPeriodCoverForOriginalPolicyMsg, originalPolicyDate);
	}

	/**
	 * Get Renewal Policy date
	 * 
	 * @return Renewal Policy date
	 * 
	 */
	public boolean getPeriodCoverForRenwalPolicy(String renewalPolicyDate) {
		return GenericUtils.verifyWebElementTextContains(txtPeriodCoverForPolicyRenewalMsg, renewalPolicyDate);
	}

	/**
	 * select cancel button
	 * 
	 * @param extentReport
	 * @throws Excption
	 */
	public void clickCancelButton(ExtentTest extentReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnCancel);
			btnCancel.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected on cancel button on Renew my policy page ");
		} catch (Exception e) {
			Log.message("Error while selecting cancel button on Renew my policy page" + e);
		}
	}

	/**
	 * select close button
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public void selectClose(ExtentTest extentReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnClose);
			btnClose.click();
			Log.message("Close button selected on cancel window", driver, extentedReport);
			WaitUtils.waitForSpinner(driver);
		} catch (Exception e) {
			Log.message("error while selecting close button on cancel pop up window" + e);
		}
	}

	/**
	 * select Change button
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public void selectChange(ExtentTest extentReport, boolean Screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnChange);
			btnChange.click();
			Log.message("Change button selected on Renew my policy page", driver, extentedReport);
			WaitUtils.waitForSpinner(driver);
		} catch (Exception e) {
			Log.message("error while selecting Change button selected on Renew my policy page" + e);
		}
	}

	/**
	 * select Terms and conditions
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public void clickTermsAndCond(ExtentTest extetReprot, boolean Screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, chkTermsandConditions);
			chkTermsandConditions.click();
			Log.message("Terms and Conditions check box selected on Renew my policy page", driver, extentedReport,
					true);
		} catch (Exception e) {
			Log.message("Terms and Conditions check box not selected on Renew my policy page" + e);
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
			WaitUtils.waitForelementToBeClickable(driver, btnChange, "Quote button is not found");
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", btnChange);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Quote' button in Renew my policy page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Quote' button in Renew my policy page : " + e);
		}
	}
	
	/**
	 * select Change button
	 * 
	 * @param extentReport
	 * @throws Excption
	 */
	public void clickChangeButton(ExtentTest extentReport,  boolean Screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnChange);
			btnChange.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected on Change button on Renew my policy page ");
		} catch (Exception e) {
			Log.message("Error while selecting Change button on Renew my policy page" + e);
		}
	}

	/**
	 * 
	 * Description : Verify car insurance renewal details.
	 * 
	 * Return : boolean
	 * 
	 * @param extentedReport
	 * @param screenShot
	 * 
	 * @throws Exception
	 *             as custom Exception Message
	 * 
	 */
	public boolean verifyInsuranceRenewalDetails(ExtentTest extentedReport, boolean Screenshot) throws Exception {

		WaitUtils.waitForElementPresent(driver, 10, txtInsuranceRenewalDetails,
				"Unable to find message 'This is a direct renewal of your current insurance policy, you cannot make any changes to your details.");
		try {
			return GenericUtils.verifyWebElementTextContains(txtInsuranceRenewalDetails, CAR_INSURENCE_POLICY_MSG);
		} catch (Exception e) {
			throw new Exception(
					"Unable to find message 'This is a direct renewal of your current insurance policy, you cannot make any changes to your details."
							+ e);
		}
	}

	/**
	 * verify Insurance Renewal Header
	 * 
	 * @param msg
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyCarInsuranceRenewalHeader(String msg) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextEqualsIgnoreCase(txtTitleCarInsuranceRenewalDetails, msg);
		} catch (Exception e) {
			throw new Exception("Error while verifying Insurance Renewal Header : " + e);
		}
	}

	/**
	 * verify Insurance Renewal Header
	 * 
	 * @param msg
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyHomeInsuranceRenewalHeader(String msg) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextEqualsIgnoreCase(txtTitleHomeInsuranceRenewalDetails, msg);
		} catch (Exception e) {
			throw new Exception("Error while verifying Insurance Renewal Header : " + e);
		}
	}

	/**
	 * To select view my document button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */

	public void clickDocumentView(WebDriver driver, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			String documentCount = getDocumentsCount(extentedReport, true);
			String winHandle = driver.getWindowHandle();
			for (int i = 1; i <= Integer.valueOf(documentCount); i++) {
				driver.findElement(By.cssSelector(lstdocuments + String.valueOf(i))).click();
				WaitUtils.waitForSpinner(driver);
				ArrayList<String> documentTab = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(documentTab.get(1));
				Thread.sleep(10000);
				Log.message("View button selected on documents section", driver, extentedReport, true);
				driver.switchTo().window(winHandle);
				Log.message("Switched to new window", driver, extentedReport, screenshot);
			}

		} catch (Exception e) {
			throw new Exception("Error while clicking view document button: " + e);
		}
	}

	/**
	 * verify Terms and conditions
	 * 
	 * @param msg
	 * @param extentedReport
	 * @return boolean
	 * 
	 */

	public boolean verifyTermsAndConditions() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtchkTermsandConditionsMsg, TERMS_AND_CONDITION_MSG);

		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions : " + e);
		}
	}

	public boolean verifyTermsAndConditionsNote() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtchkTermsandConditionsNote, TERMS_AND_CONDITION_Note);

		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions Note : " + e);
		}
	}

	/**
	 * Get Vehicle Registration Number
	 * 
	 * @return Vehicle Registration Number
	 * 
	 */
	public boolean getVehicleRegistrationNumber(String vehicleRegistrationNumber) {
		return GenericUtils.verifyWebElementTextContains(txtVehicleRegistrationNumber, vehicleRegistrationNumber);

	}

	/**
	 * Get the Property To Be Insured
	 * 
	 * @return the Property To Be Insured
	 * 
	 */
	public boolean getThePropertyToBeInsured(String thePropertyToBeInsured) {
		return GenericUtils.verifyWebElementTextContains(txtThePropertyToBeInsured, thePropertyToBeInsured);

	}

	/**
	 * Get Vehicle Matches
	 * 
	 * @return Vehicle Matches
	 * 
	 */
	public boolean getVehicleMatches(String vehicleMatches) {
		return GenericUtils.verifyWebElementTextContains(txtvehicleDetails, vehicleMatches);

	}

	/**
	 * Get Vehicle Matches
	 * 
	 * @return Vehicle Matches
	 * 
	 */
	public boolean getVehicleCCNumber(String vehicleCCNumber) {
		return GenericUtils.verifyWebElementTextContains(txtvehicleDetails, vehicleCCNumber);

	}

	/**
	 * Get Vehicle Number Of Doors
	 * 
	 * @return Vehicle Number Of Doors
	 * 
	 */
	public boolean getVehicleNumberOfDoors(String vehicleNumberOfDoors) {
		return GenericUtils.verifyWebElementTextContains(txtvehicleDetails, vehicleNumberOfDoors);

	}

	/**
	 * Get Vehicle Body Type
	 * 
	 * @return Vehicle Body Type
	 * 
	 */
	public boolean getVehicleBodyType(String vehicleBodyType) {
		return GenericUtils.verifyWebElementTextContains(txtvehicleDetails, vehicleBodyType);

	}

	/**
	 * Get Vehicle Transmission
	 * 
	 * @return Vehicle Transmission
	 * 
	 */
	public boolean getVehicleTransmission(String vehicleTransmission) {
		return GenericUtils.verifyWebElementTextContains(txtvehicleDetails, vehicleTransmission);

	}

	/**
	 * Get Vehicle Fuel
	 * 
	 * @return Vehicle Fuel
	 * 
	 */
	public boolean getVehicleFuel(String vehicleFuel) {
		return GenericUtils.verifyWebElementTextContains(txtvehicleDetails, vehicleFuel);

	}

	/**
	 * Verify Save Quote button clickable
	 * 
	 * @param extentReport
	 * @return boolean
	 * @throws Exception
	 */
	public boolean verifySaveQuoteButtonClickable(ExtentTest extentReport) throws Exception {
		boolean status = true;

		try {
			if (btnChange.isEnabled()) {
				WaitUtils.waitForelementToBeClickable(driver, btnChange, "Save Quote button able to click");
				return status;
			} else {
				Log.message("Save Quote button displaing as disabled on quote page" + btnChange);
				status = false;
				return status;
			}
		} catch (Exception e) {
			status = false;
			Log.message("Error while clicking Save Quote button : " + e);
			return false;
		}

	}

	/**
	 * hover On Help Icon Of Display note indicating the process for changing risk
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfRenewMyPolicyMotor(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpCarRenewMyPolicy);
			iconHelpCarRenewMyPolicy.click();
			/*
			 * Actions action = new Actions(driver);
			 * action.moveToElement(iconHelpRenewMyPolicy).click(); action.perform();
			 */
			WaitUtils.waitForElement(driver, popHelpRenewMyPolicy);
			Log.message("Hovered on the HelpIcon Of Renew My Policy Page", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Renew My Policy Page : " + e);
		}
	}

	/**
	 * verify HelpIcon Display note indicating the process for changing risk Popup
	 * Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconRenewMyPolicyMotorPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpRenewMyPolicy);
			if (GenericUtils.verifyWebElementTextContains(popHelpRenewMyPolicy, POPUP_MSG_HELPICON_Renew_My_Policy)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Renew My Policy Page : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Display note indicating the process for changing risk
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfRenewMyPolicyHousehold(ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpHomeRenewMyPolicy);
			iconHelpHomeRenewMyPolicy.click();
			/*
			 * Actions action = new Actions(driver);
			 * action.moveToElement(iconHelpRenewMyPolicy).click(); action.perform();
			 */
			WaitUtils.waitForElement(driver, popHelpRenewMyPolicy);
			Log.message("Hovered on the HelpIcon Of Renew My Policy Page", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Renew My Policy Page : " + e);
		}
	}

	/**
	 * verify HelpIcon Display note indicating the process for changing risk Popup
	 * Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconRenewMyPolicyHouseholdPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpRenewMyPolicy);
			if (GenericUtils.verifyWebElementTextContains(popHelpRenewMyPolicy, POPUP_MSG_HELPICON_Renew_My_Policy)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Renew My Policy Page : " + e);
		}
	}

	/**
	 * To get Added Item row in Within home
	 * 
	 * @param Documents
	 * @param Modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public String getDocumentsCount(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			String DocumentsRow = null;
			WaitUtils.waitForSpinner(driver);
			if (noOfDocuments.size() == 0) {
				Log.message("No Documents is available", driver, extentedReport, false);
				return DocumentsRow;
			}

			for (int loopCount = 1; loopCount <= noOfDocuments.size(); loopCount++) {
				if (loopCount == noOfDocuments.size()) {
					Integer row = loopCount;
					return row.toString();
				}
			}
			return DocumentsRow;
		} catch (Exception e) {
			throw new Exception("Error while getting Added Item row in Within Home : " + e);
		}
	}

	public boolean verifyMsgInCancelPopup() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(msgInCancelPopup, Msg_In_Cancel_Popup);

		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions Note : " + e);
		}
	}

	/**
	 * verify table values in No Claims Discount award table
	 * 
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public String verifyNoClaimsDiscountawardedTable(String numberOfYears, String averageNCD, String numberOfYearTable,
			String averageNCDTable, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			String DeviceRow = null;
			WaitUtils.waitForSpinner(driver);
			if (averageNCDFirstTable.size() == 0 && averageNCDSecondTable.size() == 0) {
				Log.message("No NCD Feature is available", driver, extentedReport, false);
			}

			String arrAverageNCD[] = averageNCD.split("\\|");
			String arrNumberOfYears[] = numberOfYears.split("\\|");
			String arrNumberOfYearTable[] = numberOfYearTable.split("\\|");
			String arrAverageNCDTable[] = averageNCDTable.split("\\|");

			if (averageNCDFirstTable.size() > 0) {
				for (int loopCount = 0; loopCount < averageNCDFirstTable.size(); loopCount++) {
					// Integer row = loopCount + 1;
					String displayedValueOfNCDFirstTable = averageNCDFirstTable.get(loopCount).getText();
					String displayedValueofNumberOfYearsFirstTable = numberOfYearsFirstTable.get(loopCount).getText();
					if ((displayedValueOfNCDFirstTable.contains(arrAverageNCD[loopCount]))
							&& (displayedValueofNumberOfYearsFirstTable.contains(arrNumberOfYears[loopCount]))) {
						GenericUtils.scrollIntoViewFromList(driver, averageNCDFirstTable);
						Log.message(arrNumberOfYears[loopCount] + ":" + arrAverageNCD[loopCount] + "is Verifyed",
								driver, extentedReport, screenshot);
						// return row.toString();
					}

				}
			}
			if (averageNCDSecondTable.size() > 0) {
				for (int loopCount = 0; loopCount < averageNCDSecondTable.size(); loopCount++) {
					// Integer row = loopCount + 1;
					String displayedValueOfNCDSecondTable = averageNCDSecondTable.get(loopCount).getText();
					String displayedValueofNumberOfYearsSecondTable = numberOfYearsSecondTable.get(loopCount).getText();
					if ((displayedValueofNumberOfYearsSecondTable.contains(arrNumberOfYearTable[loopCount]))
							&& (displayedValueOfNCDSecondTable.contains(arrAverageNCDTable[loopCount]))) {
						GenericUtils.scrollIntoViewFromList(driver, averageNCDSecondTable);
						Log.message(
								arrNumberOfYearTable[loopCount] + ":" + arrAverageNCDTable[loopCount] + "is Verifyed",
								driver, extentedReport, screenshot);
						// return row.toString();
					}

				}
			}

			return DeviceRow;
		} catch (Exception e) {
			throw new Exception("Error while No Claims Discount awarded details : " + e);
		}
	}

	/**
	 * To Expand NCD Table
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void expandNCDTable(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForelementToBeClickable(driver, btnExpand, "NCD Table is not found");
			GenericUtils.scrollIntoView(driver, btnExpand);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", btnExpand);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Expand' button for NCD Table in Renew my policy page", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Expand' button for NCD Table in Renew my policy page : " + e);
		}
	}

	/**
	 * Get Number of years No Claims accrued
	 * 
	 * @return Number of years No Claims accrued
	 * 
	 */
	public boolean getNumberOfYearsNoClaims(String numberOfYears) {
		return GenericUtils.verifyWebElementTextContains(txtNumberOfYearsNoClaimsAccrued, numberOfYears);
	}

	/**
	 * Get Additional Cost Of No Claims
	 * 
	 * @return Additional Cost Of No Claims
	 * 
	 */
	public boolean getAdditionalCostOfNoClaims(String costOfNoClaims) {
		return GenericUtils.verifyWebElementTextContains(txtAdditionalCostOfNoClaims, costOfNoClaims);
	}

	/**
	 * verify table values in with NCD Protection
	 * 
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public String verifyEffectOfClaimsOnYourNoClaimsDiscountTable(String numberofclaims, String withNCDProtection,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			String DeviceRow = null;
			WaitUtils.waitForSpinner(driver);
			if (numberOfClaimsFirstTable.size() == 0) {
				Log.message("No NCD Feature is available", driver, extentedReport, false);
			}

			String arrNumberOfClaims[] = numberofclaims.split("\\|");
			String arrWithNCDProtection[] = withNCDProtection.split("\\|");

			for (int loopCount = 0; loopCount < numberOfClaimsFirstTable.size(); loopCount++) {
				// Integer row = loopCount + 1;
				String displayedValueOfNumberOfClaims = numberOfClaimsFirstTable.get(loopCount).getText();
				String displayedValueofWithNCDProtection = withNCDProtectionFirstTable.get(loopCount).getText();
				if ((displayedValueOfNumberOfClaims.contains(arrNumberOfClaims[loopCount]))
						&& (displayedValueofWithNCDProtection.contains(arrWithNCDProtection[loopCount]))) {
					GenericUtils.scrollIntoViewFromList(driver, numberOfClaimsFirstTable);
					Log.message(arrNumberOfClaims[loopCount] + ":" + arrWithNCDProtection[loopCount] + "is Verifyed",
							driver, extentedReport, screenshot);
					// return row.toString();
				}

			}
			return DeviceRow;
		} catch (Exception e) {
			throw new Exception("Error Effect Of Claims On Your No Claims Discount details : " + e);
		}

	}

	/**
	 * verify table values in With out NCD Protection
	 * 
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public String verifyEffectOfClaimsWithoutNCDProtectionTable(String numberofclaimsNCD, String withOutNCDProtection,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			String DeviceRow = null;
			WaitUtils.waitForSpinner(driver);
			if (numberOfClaimsSecondTable.size() == 0) {
				Log.message("No NCD Feature is available", driver, extentedReport, false);
			}

			String arrNumberOfClaimsNCD[] = numberofclaimsNCD.split("\\|");
			String arrWithOutNCDProtection[] = withOutNCDProtection.split("\\|");

			for (int loopCount = 0; loopCount < numberOfClaimsSecondTable.size(); loopCount++) {
				// Integer row = loopCount + 1;
				String displayedValueOfNumberOfClaims = numberOfClaimsSecondTable.get(loopCount).getText();
				String displayedValueofWithOutNCDProtection = withOutNCDProtectionTable.get(loopCount).getText();
				if ((displayedValueOfNumberOfClaims.contains(arrNumberOfClaimsNCD[loopCount]))
						&& (displayedValueofWithOutNCDProtection.contains(arrWithOutNCDProtection[loopCount]))) {
					GenericUtils.scrollIntoViewFromList(driver, numberOfClaimsSecondTable);
					Log.message(
							arrNumberOfClaimsNCD[loopCount] + ":" + arrWithOutNCDProtection[loopCount] + "is Verifyed",
							driver, extentedReport, screenshot);
					// return row.toString();
				}

			}
			return DeviceRow;
		} catch (Exception e) {
			throw new Exception("Error Effect Of Claims Without NCD Protection details : " + e);
		}

	}

	/**
	 * verify NCD Header
	 * 
	 * @param msg
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyNoClaimsDiscountProtectionHeader(String expMsg) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextEqualsIgnoreCase(txtTitleNoClaimsDiscountProtection, expMsg);
		} catch (Exception e) {
			throw new Exception("Error while verifying NCD Header : " + e);
		}
	}

	/**
	 * verify NCD Header
	 * 
	 *
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyClaimsDiscountProtectionHeader(ExtentTest extentedReport, boolean Screenshot) {
		boolean status = false;
		try {
			if (txtTitleNoClaimsDiscountProtection.isDisplayed()) {
				status = true;
				Log.message("NCD Header is showed");

			}

		} catch (NoSuchElementException e) {
			Log.message("NCD  Header is Not showed");
		}
		return status;

	}

	/**
	 * select Renew button
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public void selectRenewButton(ExtentTest extentReport, boolean Screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnRenew);
			btnRenew.click();
			Log.message("Renew button selected on Renew my policy page", driver, extentedReport);
			WaitUtils.waitForSpinner(driver);
		} catch (Exception e) {
			Log.message("error while selecting Renew button selected on Renew my policy page" + e);
		}
	}
}
