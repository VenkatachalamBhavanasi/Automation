package com.pure.selfservice.pages;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.generic.support.Log;
import com.generic.utils.ElementLayer;
import com.generic.utils.GenericUtils;
import com.generic.utils.WaitUtils;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * MTAInitiatePage contains Customer contact details
 * 
 */
public class MTAInitiatePage extends LoadableComponent<MTAInitiatePage> {
	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	public final String MTA_START_DATE_LESSER_ERR_MSG = "The date and time of your policy change cannot be in the past.";
	public final String MTA_END_DATE_GREATER_ERR_MSG = "The policy will expire before the date of this change.";
	public final String AMENDMENT_CANCEL_MSG = "You have decided not to proceed with your change. No changes will be made to your policy.";
	public final String POPUP_MSG_HELPICON_Add_Buildings_Cover = "I want to add Buildings Cover my policy";
	public final String POPUP_MSG_HELPICON_Add_Specified_Items = "I want to add a specified item my policy";
	public final String POPUP_MSG_HELPICON_Change_My_Property = "I want to change the property insured under my policy";
	public final String POPUP_MSG_HELPICON_Add_Contents_Cover = "I want to add Contents Cover my policy";
	
	@FindBy(css = "button[title='Continue']:not(.edgeConnectDisabled)")
	WebElement btnContinue;

	@FindBy(css = "button[title='Continue'][disabled='disabled']")
	WebElement btnContinueDisabled;

	@FindBy(css = "button[class*='btn-choice']")
	List<WebElement> lstChangeType;

	@FindBy(css = "#C2__HEAD_82C3F84B8B5F77B74542")
	WebElement txtTitleChooseTypeOfChange;

	@FindBy(css = "#C2__BUT_0481547D2A1288BF128477")
	WebElement btnEditChangesOfChangeClassOfUse;

	@FindBy(css = "#C2__BUT_0481547D2A1288BF126717")
	WebElement btnChangePermittedDrivers;

	@FindBy(css = "#C2__BUT_0481547D2A1288BF128490[title='Edit changes']")
	WebElement btnEditChangeMyCoverType;
	
	@FindBy(css = "#C2__BUT_822671E298C9A4B53715307")
	WebElement btnEditAddBuildingsCover;
	
	@FindBy(css = "#C2__BUT_822671E298C9A4B53715317")
	WebElement btnEditAddContentsCover;
	
	@FindBy(css = "#C2__BUT_822671E298C9A4B53715327")
	WebElement btnEditAddSepecifiedItems;

	@FindBy(css = ".back-button[value='Cancel']")
	WebElement btnCancel;

	@FindBy(css = "#C2__ID_MTA_COVER_DATE")
	WebElement fldChangeDate;

	@FindBy(css = "#C2__FMT_79DFC45EF0C42491176365 .flex-box-active .icon-selected")
	WebElement iconChangeMyCoverSelected;

	@FindBy(css = "button[title*='Add another policy change']#C2__BUT_82C3F84B8B5F77B74670")
	WebElement btnAddAnotherPolicyChange;

	@FindBy(css = "button[title='Quote']")
	WebElement btnQuote;

	@FindBy(css = "input[name*='MINUTES']")
	WebElement lstMinute;

	@FindBy(css = "input[name*='HOUR']")
	WebElement lstHour;

	@FindBy(css = "#C2__BUT_0481547D2A1288BF128472[title='Edit changes']")
	WebElement btnEditChangesOfChangeMyCar;

	@FindBy(css = "[id='C2__FMT_79DFC45EF0C42491163527'] [class='icon-selected']")
	WebElement iconTickSymbol;

	@FindBy(css = "[name='C2__MTA[1].MTAINITIATE[1].HOUR']+a>span[class='ui-icon ui-icon-triangle-1-n']")
	WebElement lstHourTime;

	@FindBy(css = "[id*='MTA_COVER_DATE_ERRORMESSAGE']")
	WebElement txtMTADateErrorMsg;

	@FindBy(css = "button[id='C2__BUT_82C3F84B8B5F77B74578']")
	WebElement btnEditChangeMyPolicyAddress;

	@FindBy(css = "[id='C2__p1_HEAD_76F225C131F909E4808261']")
    WebElement mdlCancelPopup;

    @FindBy(css = "button[title='Ok']")
    WebElement btnOkMdlCancelPopup;
    
    @FindBy(css = "#C2__BUT_822671E298C9A4B53715292")
    WebElement changeMyProperty;
    
    @FindBy(css = "#C2__BUT_822671E298C9A4B53715323")
    WebElement addSpecifiedItems;

    @FindBy(css = "#C2__BUT_822671E298C9A4B53715303")
    WebElement addBuildingsCover;
    
    @FindBy(css = "#C2__BUT_822671E298C9A4B53715313")
    WebElement addContentsCover;

    @FindBy(css = "#C2__HELP_BUT_822671E298C9A4B53715303")
	WebElement iconHelpAddBuildingsCover;

    @FindBy(css = ".tetris_helpText")
	WebElement popHelpAddBuildingsCover;
    
    @FindBy(css = "#C2__HELP_BUT_822671E298C9A4B53715323")
	WebElement iconHelpAddSpecifiedItems;

    @FindBy(css = ".tetris_helpText")
	WebElement popHelpAddSpecifiedItems;
    
    @FindBy(css = "#C2__HELP_BUT_822671E298C9A4B53715292")
	WebElement iconHelpChangeMyProperty;

    @FindBy(css = ".tetris_helpText")
	WebElement popHelpChangeMyProperty;
    
    @FindBy(css = "#C2__HELP_BUT_822671E298C9A4B53715313")
	WebElement iconHelpAddContentsCover;

    @FindBy(css = ".tetris_helpText")
	WebElement popHelpAddContentsCover;
    
	/**
	 * 
	 * Constructor class for MTAInitiatePage Here we initializing the driver for
	 * page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public MTAInitiatePage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(WaitUtils.waitForElement(driver, txtTitleChooseTypeOfChange)))) {
			Log.fail("User is not navigated to MTAInitiatePage ", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {

		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
		WaitUtils.waitForElement(driver, btnContinue);
	}

	/**
	 * To click Continue button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickContinueButton(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			if(lstHourTime.isDisplayed()){
				clickHourtime(extentedReport, true);
			}
			//clickHourtime(extentedReport, true);
			GenericUtils.scrollIntoView(driver, btnContinue);
			WaitUtils.waitForelementToBeClickable(driver, btnContinue, "Continue button is not found");
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", btnContinue);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Continue button", driver, extentedReport, screenshot);
			WaitUtils.waitForPageLoad(driver, 30);
		} catch (Exception e) {
			throw new Exception("Error while clicking Continue button : " + e);
		}
	}

	/**
	 * To verify Continue button is enabled
	 * 
	 * @throws Exception
	 * 
	 */
	public boolean verifyContinueBtnEnabled() throws Exception {
		try {
			if (WaitUtils.waitForElement(driver, btnContinue))
				return true;
			else
				return false;
		} catch (Exception e) {
			throw new Exception("Continue button is not enabled : " + e);
		}
	}

	/**
	 * To click Hour Time - increase to future time
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickHourtime(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			GenericUtils.scrollIntoView(driver, lstHourTime);
			lstHourTime.click();
			Log.message("Incremented to 1 hour", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking hour time : " + e);
		}
	}

	/**
	 * To select any type of change
	 * 
	 * @param String
	 *            TypeOfChange - Change my car|Change my Cover Type|Change the Class
	 *            of Use|Change Permitted Drivers|Change my Overnight Location
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectChangeType(String TypeOfChange, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForListElement(driver, lstChangeType, 30);
			boolean status = false;
			for (int i = 0; i < lstChangeType.size(); i++) {
				if (lstChangeType.get(i).getAttribute("value").equalsIgnoreCase(TypeOfChange)) {
					WaitUtils.waitForElement(driver, lstChangeType.get(i));

					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", lstChangeType.get(i));

					Thread.sleep(3000); // required for the continue button to be visible
					status = true;
					break;
				}
			}
			if (status) {
				Log.message("Clicked on '" + TypeOfChange + "'", driver, extentedReport, screenshot);
			} else {
				Log.message("Failed to click on '" + TypeOfChange + "'", driver, extentedReport, screenshot);
			}
		} catch (Exception e) {
			throw new Exception("Error while selecting type of change : " + TypeOfChange + "" + e);
		}
	}

	/**
	 * To click Edit changes button of Change Class of use
	 * 
	 * @param ChangeTypeName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickEditChangesBtn(String ChangeTypeName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {

			switch (ChangeTypeName) {

			case "Change my cover":
				WaitUtils.waitForElement(driver, btnEditChangeMyCoverType);
				//btnEditChangeMyCoverType.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnEditChangeMyCoverType);
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on Edit changes button of Change my cover type", driver, extentedReport,
						screenshot);
				break;

			case "Change the annual mileage":
				WaitUtils.waitForElement(driver, btnEditChangesOfChangeClassOfUse);
				//btnEditChangesOfChangeClassOfUse.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnEditChangesOfChangeClassOfUse);
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on Edit changes button of Change Class of use", driver, extentedReport,
						screenshot);
				break;

			case "Change my car":
				WaitUtils.waitForElement(driver, btnEditChangesOfChangeMyCar);
				//btnEditChangesOfChangeMyCar.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnEditChangesOfChangeMyCar);
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on Edit changes button of Change my car", driver, extentedReport, screenshot);
				break;

			case "Change my overnight location":
				WaitUtils.waitForElement(driver, btnEditChangeMyPolicyAddress);
				//btnEditChangeMyPolicyAddress.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnEditChangeMyPolicyAddress);
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on Edit changes button of Change my policy address", driver, extentedReport,
						screenshot);
				break;
				
			case "Add buildings cover":
				WaitUtils.waitForElement(driver, btnEditAddBuildingsCover);
				//btnEditChangeMyPolicyAddress.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnEditAddBuildingsCover);
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on Edit changes button of Add Buildings cover", driver, extentedReport,
						screenshot);
				break;
				
			case "Add contents cover":
				WaitUtils.waitForElement(driver, btnEditAddContentsCover);
				//btnEditChangeMyPolicyAddress.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnEditAddContentsCover);
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on Edit changes button of Add Contents cover", driver, extentedReport,
						screenshot);
				break;
				
			case "Add specified itams":
				WaitUtils.waitForElement(driver, btnEditAddSepecifiedItems);
				//btnEditChangeMyPolicyAddress.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnEditAddSepecifiedItems);
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on Edit changes button of Add Specified Itams cover", driver, extentedReport,
						screenshot);
				break;
				
				
			}
		} catch (Exception e) {
			throw new Exception("Error while clicking Edit changes button : " + e);
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
	public void clickCancelButton(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnCancel);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", btnCancel);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on cancel button", driver, extentedReport, screenshot);
			if (WaitUtils.waitForElement(driver, mdlCancelPopup)) {
				Log.softAssertThat(verifyCancelPopupMsg(),
						"Cancel pop up message is displayed as expected : " + AMENDMENT_CANCEL_MSG,
						"Cancel pop up message is not displayed as expected : " + AMENDMENT_CANCEL_MSG, driver,
						extentedReport, true);
				btnOkMdlCancelPopup.click();
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on Ok button icon in Cancel modal popup", driver, extentedReport, screenshot);
			}
		} catch (Exception e) {
			throw new Exception("Error while clicking cancel button : " + e);
		}
	}

	/**
	 * To verify cancel amendment message
	 * 
	 * @throws Exception
	 * 
	 */
	public boolean verifyCancelPopupMsg() throws Exception {
		try {
			WaitUtils.waitForElement(driver, mdlCancelPopup);
			return GenericUtils.getTextOfWebElement(mdlCancelPopup, driver).contains(AMENDMENT_CANCEL_MSG);
		} catch (Exception e) {
			throw new Exception("Error while verifying cancel popup message : " + e);
		}
	}

	/**
	 * To enter date for changes to take effect
	 * 
	 * @param date
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterDateForChangeTakeEffect(String date, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldChangeDate);
			fldChangeDate.clear();
			Thread.sleep(1000);
			fldChangeDate.click();
			fldChangeDate.sendKeys(date);
			fldChangeDate.click();
			WaitUtils.waitForSpinner(driver);
			fldChangeDate.sendKeys(Keys.TAB);
			WaitUtils.waitForSpinner(driver);
			changeMyProperty.click();
			Thread.sleep(2000); // Sleep required for the error message to occur after few seconds
			Log.message("Entered the Date date for changes to take effect : " + date, driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering date for changes to take effect : " + e);
		}
	}

	/**
	 * To verify entered date for changes to take effect
	 * 
	 * @param expectedMsg
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * @return boolean
	 * 
	 */
	public boolean verifyDateForChangeTakeEffect(String date, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		boolean status = false;
		try {
			if (fldChangeDate.getAttribute("value").trim().contains(date)) {
				status = true;
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while verify Date For Change to Take Effect : " + e);
		}
	}

	/**
	 * To click Add Another Policy Change button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickAddAnotherPolicyChangeButton(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnAddAnotherPolicyChange);
			JavascriptExecutor executor = (JavascriptExecutor) driver;executor.executeScript("arguments[0].click();", btnAddAnotherPolicyChange);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Add Another Policy Change' button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Add Another Policy Change' button : " + e);
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
			WaitUtils.waitForelementToBeClickable(driver, btnQuote, "Quote button is not found");
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", btnQuote);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Quote' button in 'MTA initiate' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Quote' button in MTA initiate page : " + e);
		}
	}

	/**
	 * To verify email address error message
	 * 
	 * @param expectedMsg
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * @return boolean
	 * 
	 */
	public boolean verifyMTADateErrorMsg(String expectedMsg, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		boolean status = false;
		try {
			GenericUtils.scrollIntoView(driver, txtMTADateErrorMsg);
			if (txtMTADateErrorMsg.getText().trim().contains(expectedMsg)) {
				status = true;
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while verifying MTA date error message : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of Add Buildings Cover
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfAddBuildingsCover(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpAddBuildingsCover);
			WaitUtils.waitForSpinner(driver);
			iconHelpAddBuildingsCover.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpAddBuildingsCover).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpAddBuildingsCover);
			Log.message("Hovered on the HelpIcon Of ManageContactDetail", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of ManageContactDetail : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Add Buildings Cover Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconAddBuildingsCoverPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpAddBuildingsCover);
			if (GenericUtils.verifyWebElementTextContains(popHelpAddBuildingsCover,
					POPUP_MSG_HELPICON_Add_Buildings_Cover)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of ManageContactDetail : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Add Specified Items
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfAddSpecifiedItems(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpAddSpecifiedItems);
			WaitUtils.waitForSpinner(driver);
			iconHelpAddSpecifiedItems.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpAddSpecifiedItems).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpAddSpecifiedItems);
			Log.message("Hovered on the HelpIcon Of AddSpecifiedItems", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddSpecifiedItems : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Add Specified Items Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconAddSpecifiedItemsPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpAddSpecifiedItems);
			if (GenericUtils.verifyWebElementTextContains(popHelpAddSpecifiedItems,
					POPUP_MSG_HELPICON_Add_Specified_Items)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddSpecifiedItems : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Change My Property
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfChangeMyProperty(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpChangeMyProperty);
			WaitUtils.waitForSpinner(driver);
			iconHelpChangeMyProperty.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpChangeMyProperty).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpChangeMyProperty);
			Log.message("Hovered on the HelpIcon Of ChangeMyProperty", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of ChangeMyProperty : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Change My Property Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconChangeMyPropertyPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpChangeMyProperty);
			if (GenericUtils.verifyWebElementTextContains(popHelpChangeMyProperty,
					POPUP_MSG_HELPICON_Change_My_Property)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of ChangeMyProperty : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of Add Contents Cover
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfAddContentsCover(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpAddContentsCover);
			iconHelpAddContentsCover.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpAddContentsCover).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpAddContentsCover);
			Log.message("Hovered on the HelpIcon Of AddContentsCover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Add Contents Cover Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconAddContentsCoverPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpAddContentsCover);
			if (GenericUtils.verifyWebElementTextContains(popHelpAddContentsCover,
					POPUP_MSG_HELPICON_Add_Contents_Cover)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}
	
}
