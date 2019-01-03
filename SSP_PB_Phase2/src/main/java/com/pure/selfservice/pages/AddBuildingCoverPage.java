package com.pure.selfservice.pages;

import java.util.HashMap;
import java.util.Set;

//import org.openqa.selenium.By;
//import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
 * ChangeMyCoverTypePage contains Customer contact details
 * 
 */
public class AddBuildingCoverPage extends LoadableComponent<AddBuildingCoverPage> {
	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	public final String POPUP_MSG_HELPICON_Title_Add_Building_Cover = "I want to add buildings cover to this policy";
	public final String POPUP_MSG_HELPICON_Rebuild_Cost = "The cost to re-build your property in the event of an insured loss as indicated on a Home Buyer’s Survey or Mortgage Valuation.";
	public final String POPUP_MSG_HELPICON_Accidental_Damage = "This is an extension on your contents cover to insure you against additional unforeseen accidents.";
	public final String POPUP_MSG_HELPICON_Total_Excess_Required = "Enter the total excesses that you are prepared to pay. Note that the figure here is inclusive of any compulsory excesses applied to the policy.";
	public final String POPUP_MSG_HELPICON_Previous_Building_Insurance = "Have you previously held buildings insurance?";
	public final String POPUP_MSG_HELPICON_Name_Of_Previous_Insurer = "Previous insurer for buildings insurance?";
	public final String POPUP_MSG_HELPICON_Policy_Number_Of_Previous_Insurance = "Previous policy number for buildings insurance?";
	public final String POPUP_MSG_HELPICON_Expiry_Date_Of_Previous_Insurance = "Previous policy expiry date for buildings insurance?";
	public final String POPUP_MSG_HELPICON_Pay_For_Your_Previous_Insurance = "Previous payment frequency for buildings insurance?";
	public final String POPUP_MSG_HELPICON_No_Of_Years_Without_Claiming = "The number of consecutive claim free years you have accrued for buildings insurance.";

	@FindBy(css = "#C2__C1__HEAD_FF10E2CFA969B23C5206988")
	WebElement txtTitleAddBuildingCover;

	@FindBy(css = "button[title='Quote']")
	WebElement btnQuote;

	@FindBy(css = "button[title='Cancel']")
	WebElement btnCancel;

	@FindBy(css = "button[title='Add another policy change']")
	WebElement btnAddAnotherPolicyChange;

	@FindBy(css = "#C2__C1__QUE_17AC82CAB86EFF1C489445")
	WebElement fldRebuildCost;

	@FindBy(css = "#C2__C1__QUE_17AC82CAB86EFF1C502572")
	WebElement drpdwnTotalExcessRequired;

	@FindBy(css = "#C2__C1__QUE_17AC82CAB86EFF1C505489")
	WebElement drpdwnNoOfYearsWithoutClaiming;

	@FindBy(css = "#C2__C1__QUE_17AC82CAB86EFF1C508403")
	WebElement drpdwnNameOfPreviousInsurer;

	@FindBy(css = "#C2__C1__QUE_17AC82CAB86EFF1C509860")
	WebElement fldPolicyNumberOfPreviousInsurance;

	@FindBy(css = "#C2__C1__QUE_17AC82CAB86EFF1C5098634534")
	WebElement fldExpiryDateOfPreviousInsurance;

	@FindBy(css = "#C2__C1__QUE_17AC82CAB86EFF1C509866")
	WebElement drpdwnPayForYourPreviousInsurance;

	@FindBy(css = "[id = C2__C1__QUE_17AC82CAB86EFF1C490905_0]+span")
	WebElement btnrequireAccidentalDamageYes;

	@FindBy(css = "[id = C2__C1__QUE_17AC82CAB86EFF1C490905_1]+span")
	WebElement btnrequireAccidentalDamageNo;

	@FindBy(css = "[id = C2__C1__QUE_17AC82CAB86EFF1C506946_0]+span")
	WebElement btnPreviousBuildingInsuranceYes;

	@FindBy(css = "[id = C2__C1__QUE_17AC82CAB86EFF1C506946_1]+span")
	WebElement btnPreviousBuildingInsuranceNo;

	@FindBy(css = "#C2__C1__HELP_HEAD_FF10E2CFA969B23C5206988")
	WebElement iconHelpTitleAddBuildingCover;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpTitleAddBuildingCover;

	@FindBy(css = "#C2__C1__HELP_QUE_17AC82CAB86EFF1C489445")
	WebElement iconHelpRebuildCost;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpRebuildCost;

	@FindBy(css = "#C2__C1__HELP_QUE_17AC82CAB86EFF1C490905")
	WebElement iconHelpAccidentalDamage;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpAccidentalDamage;

	@FindBy(css = "#C2__C1__HELP_QUE_17AC82CAB86EFF1C502572")
	WebElement iconHelpTotalExcessRequired;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpTotalExcessRequired;

	@FindBy(css = "#C2__C1__HELP_QUE_17AC82CAB86EFF1C506946")
	WebElement iconHelpPreviousBuildingInsurance;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPreviousBuildingInsurance;

	@FindBy(css = "#C2__C1__HELP_QUE_17AC82CAB86EFF1C508403")
	WebElement iconHelpNameOfPreviousInsurer;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpNameOfPreviousInsurer;

	@FindBy(css = "#C2__C1__HELP_QUE_17AC82CAB86EFF1C509860")
	WebElement iconHelpPolicyNumberOfPreviousInsurance;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPolicyNumberOfPreviousInsurance;

	@FindBy(css = "#C2__C1__HELP_QUE_17AC82CAB86EFF1C5098634534")
	WebElement iconHelpExpiryDateOfPreviousInsurance;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpExpiryDateOfPreviousInsurance;

	@FindBy(css = "#C2__C1__HELP_QUE_17AC82CAB86EFF1C509866")
	WebElement iconHelpPayForYourPreviousInsurance;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPayForYourPreviousInsurance;

	@FindBy(css = "#C2__C1__HELP_QUE_17AC82CAB86EFF1C505489")
	WebElement iconHelpNoOfYearsWithoutClaiming;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpNoOfYearsWithoutClaiming;

	/**
	 * 
	 * Constructor class for Add Building Cover page Here we initializing the driver
	 * for page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public AddBuildingCoverPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(txtTitleAddBuildingCover.isDisplayed()))) {
			Log.fail("User is not navigated to ChangeMyCoverTypePage ", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {
		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
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
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnQuote);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Quote' button in 'Add Building Cover' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Quote' button in 'Add Building Cover' page : " + e);
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
					"Cancel button is not found in 'Add Building Cover' page");
			btnCancel.click();
			Log.message("Clicked on Cancel button in 'Add Building Cover' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking cancel button in 'Add Building Cover' page : " + e);
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
			WaitUtils.waitForelementToBeClickable(driver, btnAddAnotherPolicyChange,
					"Add another Policy change button is not enabled");
			btnAddAnotherPolicyChange.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Add Another Policy Change' button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception(
					"Error while clicking 'Add Another Policy Change' button in 'Add Building Cover' page : " + e);
		}
	}

	/**
	 * To select Total Excess Required
	 * 
	 * @param Excess
	 *            Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectExcessValue(String excessvalue, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnTotalExcessRequired);
			Select coverSelect = new Select(drpdwnTotalExcessRequired);
			coverSelect.selectByVisibleText(excessvalue);
			WaitUtils.waitForElement(driver, btnPreviousBuildingInsuranceYes);
			Log.message("Selected Excess Value : " + excessvalue, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting Total Excess Required on Add Building cover page : " + e);
		}
	}

	/**
	 * To select No Of Years Without Claiming
	 * 
	 * @param No
	 *            Of Years Without Claiming Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectBuildingsInsuranceWithoutClaiming(String noOfYearsWithoutClaimingValue, ExtentTest extentTest,
			boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnNoOfYearsWithoutClaiming);
			Select coverSelect = new Select(drpdwnNoOfYearsWithoutClaiming);
			coverSelect.selectByVisibleText(noOfYearsWithoutClaimingValue);
			WaitUtils.waitForElement(driver, btnQuote);
			Log.message("Selected Cover Type : " + noOfYearsWithoutClaimingValue, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message(
					"Error while selecting Buildings Insurance With out Claiming on Add Building cover page : " + e);
		}
	}

	/**
	 * To select Name Of Previous Insurer
	 * 
	 * @param Name
	 *            Of Previous Insurer Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectNameOfPreviousInsurer(String nameOfPreviousInsurerValue, ExtentTest extentTest,
			boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnNameOfPreviousInsurer);
			Select coverSelect = new Select(drpdwnNameOfPreviousInsurer);
			coverSelect.selectByVisibleText(nameOfPreviousInsurerValue);
			WaitUtils.waitForElement(driver, fldPolicyNumberOfPreviousInsurance);
			Log.message("Selected Cover Type : " + nameOfPreviousInsurerValue, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting Name of Previous Insurer Value on Add Building cover page : " + e);
		}
	}

	/**
	 * To select Pay For Your Previous Insurance
	 * 
	 * @param Pay
	 *            For Your Previous Insurance Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectPayForYourPreviousInsurance(String payForYourPreviousInsuranceValue, ExtentTest extentTest,
			boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnPayForYourPreviousInsurance);
			Select coverSelect = new Select(drpdwnPayForYourPreviousInsurance);
			coverSelect.selectByVisibleText(payForYourPreviousInsuranceValue);
			WaitUtils.waitForElement(driver, drpdwnNoOfYearsWithoutClaiming);
			Log.message("Selected Cover Type : " + payForYourPreviousInsuranceValue, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting Name of Previous Insurer Value on Add Building cover page : " + e);
		}
	}

	/**
	 * To enter RebuildCost Of Home
	 * 
	 * @param RebuildCost
	 *            Of Home Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterRebuildCostOfHome(String rebuildCost, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldRebuildCost);
			fldRebuildCost.clear();
			fldRebuildCost.sendKeys(rebuildCost);
			Log.message(rebuildCost + " - Rebuild Cost is enterd in the Add Buildings cover", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Rebuild Cost on Add Buildings cover: " + e);
		}

	}

	/**
	 * To enter Previous Insurance Policy Number
	 * 
	 * @param Previous
	 *            Insurance Policy Number
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterPolicyNumberOfPreviousInsurance(String policyNumberOfPreviousInsurance, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldPolicyNumberOfPreviousInsurance);
			fldPolicyNumberOfPreviousInsurance.clear();
			fldPolicyNumberOfPreviousInsurance.sendKeys(policyNumberOfPreviousInsurance);
			Log.message(
					policyNumberOfPreviousInsurance
							+ " - Policy Number of previous insurance is enterd in the Add bulidings cover Page",
					driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering policy Number Of Previous Insurance on Add Buildings cover: " + e);
		}

	}

	/**
	 * To enter Expire Date Of Previous Insurance
	 * 
	 * @param Expire
	 *            Date Of Previous Insurance Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterExpiryDateOfPreviousInsurance(String expiryDateOfPreviousInsurance, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldExpiryDateOfPreviousInsurance);
			fldExpiryDateOfPreviousInsurance.clear();
			Thread.sleep(1000);
			fldExpiryDateOfPreviousInsurance.click();
			fldExpiryDateOfPreviousInsurance.sendKeys(expiryDateOfPreviousInsurance);
			fldExpiryDateOfPreviousInsurance.click();
			WaitUtils.waitForSpinner(driver);
			fldExpiryDateOfPreviousInsurance.sendKeys(Keys.TAB);
			Log.message(
					"Entered the Date date for Expiry Date Of Previous Insurance : " + expiryDateOfPreviousInsurance,
					driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering date for Expiry Date Of Previous Insurance : " + e);
		}
	}

	/**
	 * To click yes or No Previous Building Insurance
	 * 
	 * @param previousBuildingInsurance
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoPreviousBuildingInsurance(String previousBuildingInsurance, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			switch (previousBuildingInsurance.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnPreviousBuildingInsuranceYes);
				btnPreviousBuildingInsuranceYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnPreviousBuildingInsuranceNo);
				btnPreviousBuildingInsuranceNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message(
					"Clicked option for want to add Previous Building Insurance : " + previousBuildingInsurance + "",
					driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * To click yes or No Require Accidental Damage
	 * 
	 * @param Accidental
	 *            Damage
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoRequireAccidentalDamage(String accidentalDamage, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			switch (accidentalDamage.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnrequireAccidentalDamageYes);
				btnrequireAccidentalDamageYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnrequireAccidentalDamageNo);
				btnrequireAccidentalDamageNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked option for want to add Require Accidental Damage Cover : " + accidentalDamage + "",
					driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Title Add Building Cover
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfTitleAddBuildingCover(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpTitleAddBuildingCover);
			WaitUtils.waitForSpinner(driver);
			iconHelpTitleAddBuildingCover.click();
			/*
			 * Actions action = new Actions(driver);
			 * action.moveToElement(iconHelpTitleAddBuildingCover).click();
			 * action.perform();
			 */
			WaitUtils.waitForElement(driver, popHelpTitleAddBuildingCover);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Title Add Building Cover Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconTitleAddBuildingCoverPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpTitleAddBuildingCover);
			if (GenericUtils.verifyWebElementTextContains(popHelpTitleAddBuildingCover,
					POPUP_MSG_HELPICON_Title_Add_Building_Cover)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Rebuild Cost
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfRebuildCost(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpRebuildCost);
			WaitUtils.waitForSpinner(driver);
			iconHelpRebuildCost.click();
			/*
			 * Actions action = new Actions(driver);
			 * action.moveToElement(iconHelpRebuildCost).click(); action.perform();
			 */
			WaitUtils.waitForElement(driver, popHelpRebuildCost);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Rebuild Cost Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconRebuildCostPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpRebuildCost);
			if (GenericUtils.verifyWebElementTextContains(popHelpRebuildCost, POPUP_MSG_HELPICON_Rebuild_Cost)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Accidental Damage
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfAccidentalDamage(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpAccidentalDamage);
			WaitUtils.waitForSpinner(driver);
			iconHelpAccidentalDamage.click();
			/*
			 * Actions action = new Actions(driver);
			 * action.moveToElement(iconHelpAccidentalDamage).click(); action.perform();
			 */
			WaitUtils.waitForElement(driver, popHelpAccidentalDamage);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Accidental Damage Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconAccidentalDamagePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpAccidentalDamage);
			if (GenericUtils.verifyWebElementTextContains(popHelpAccidentalDamage,
					POPUP_MSG_HELPICON_Accidental_Damage)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Total Excess Required
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfTotalExcessRequired(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpTotalExcessRequired);
			WaitUtils.waitForSpinner(driver);
			iconHelpTotalExcessRequired.click();
			/*
			 * Actions action = new Actions(driver);
			 * action.moveToElement(iconHelpTotalExcessRequired).click(); action.perform();
			 */
			WaitUtils.waitForElement(driver, popHelpTotalExcessRequired);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Total Excess Required Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconTotalExcessRequiredePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpTotalExcessRequired);
			if (GenericUtils.verifyWebElementTextContains(popHelpTotalExcessRequired,
					POPUP_MSG_HELPICON_Total_Excess_Required)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Previous Building Insurance
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfPreviousBuildingInsurance(ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPreviousBuildingInsurance);
			WaitUtils.waitForSpinner(driver);
			iconHelpPreviousBuildingInsurance.click();
			/*
			 * Actions action = new Actions(driver);
			 * action.moveToElement(iconHelpPreviousBuildingInsurance).click();
			 * action.perform();
			 */
			WaitUtils.waitForElement(driver, popHelpPreviousBuildingInsurance);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Previous Building Insurance Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPreviousBuildingInsurancePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPreviousBuildingInsurance);
			if (GenericUtils.verifyWebElementTextContains(popHelpPreviousBuildingInsurance,
					POPUP_MSG_HELPICON_Previous_Building_Insurance)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Name Of Previous Insurer
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfNameOfPreviousInsurer(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpNameOfPreviousInsurer);
			WaitUtils.waitForSpinner(driver);
			iconHelpNameOfPreviousInsurer.click();
			/*
			 * Actions action = new Actions(driver);
			 * action.moveToElement(iconHelpNameOfPreviousInsurer).click();
			 * action.perform();
			 */
			WaitUtils.waitForElement(driver, popHelpNameOfPreviousInsurer);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Name Of Previous Insurer Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconNameOfPreviousInsurerPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpNameOfPreviousInsurer);
			if (GenericUtils.verifyWebElementTextContains(popHelpNameOfPreviousInsurer,
					POPUP_MSG_HELPICON_Name_Of_Previous_Insurer)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Policy Number Of Previous Insurance
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfPolicyNumberOfPreviousInsurance(ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPolicyNumberOfPreviousInsurance);
			WaitUtils.waitForSpinner(driver);
			iconHelpPolicyNumberOfPreviousInsurance.click();
			/*
			 * Actions action = new Actions(driver);
			 * action.moveToElement(iconHelpPolicyNumberOfPreviousInsurance).click();
			 * action.perform();
			 */
			WaitUtils.waitForElement(driver, popHelpPolicyNumberOfPreviousInsurance);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Policy Number Of Previous Insurance Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPolicyNumberOfPreviousInsurancePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPolicyNumberOfPreviousInsurance);
			if (GenericUtils.verifyWebElementTextContains(popHelpPolicyNumberOfPreviousInsurance,
					POPUP_MSG_HELPICON_Policy_Number_Of_Previous_Insurance)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Expiry Date Of Previous Insurance
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfExpiryDateOfPreviousInsurance(ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpExpiryDateOfPreviousInsurance);
			WaitUtils.waitForSpinner(driver);
			iconHelpExpiryDateOfPreviousInsurance.click();
			/*
			 * Actions action = new Actions(driver);
			 * action.moveToElement(iconHelpExpiryDateOfPreviousInsurance).click();
			 * action.perform();
			 */
			WaitUtils.waitForElement(driver, popHelpExpiryDateOfPreviousInsurance);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Expiry Date Of Previous Insurance Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconExpiryDateOfPreviousInsurancePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpExpiryDateOfPreviousInsurance);
			if (GenericUtils.verifyWebElementTextContains(popHelpExpiryDateOfPreviousInsurance,
					POPUP_MSG_HELPICON_Expiry_Date_Of_Previous_Insurance)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Pay For Your Previous Insurance
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfPayForYourPreviousInsurance(ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPayForYourPreviousInsurance);
			WaitUtils.waitForSpinner(driver);
			iconHelpPayForYourPreviousInsurance.click();
			/*
			 * Actions action = new Actions(driver);
			 * action.moveToElement(iconHelpPayForYourPreviousInsurance).click();
			 * action.perform();
			 */
			WaitUtils.waitForElement(driver, popHelpPayForYourPreviousInsurance);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Pay For Your Previous Insurance Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPayForYourPreviousInsurancePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPayForYourPreviousInsurance);
			if (GenericUtils.verifyWebElementTextContains(popHelpPayForYourPreviousInsurance,
					POPUP_MSG_HELPICON_Pay_For_Your_Previous_Insurance)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of No Of Years Without Claiming
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfNoOfYearsWithoutClaiming(ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpNoOfYearsWithoutClaiming);
			WaitUtils.waitForSpinner(driver);
			iconHelpNoOfYearsWithoutClaiming.click();
			/*
			 * Actions action = new Actions(driver);
			 * action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			 * action.perform();
			 */
			WaitUtils.waitForElement(driver, popHelpNoOfYearsWithoutClaiming);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon No Of Years Without Claiming Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconNoOfYearsWithoutClaimingPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpNoOfYearsWithoutClaiming);
			if (GenericUtils.verifyWebElementTextContains(popHelpNoOfYearsWithoutClaiming,
					POPUP_MSG_HELPICON_No_Of_Years_Without_Claiming)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify Policy Detail In Add Buildings cover Section
	 * 
	 * @param expectedPolicyDetail
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyDetailInAddBuildingsDetailSection(HashMap<String, String> expectedAddBuildingsDetails,
			ExtentTest extentedReport) throws Exception {
		try {
			HashMap<String, String> AddBuildingsDetail = getDetailInAddBuildingDetailSection(extentedReport);
			return GenericUtils.compareTwoHashMap(AddBuildingsDetail, expectedAddBuildingsDetails, driver,
					extentedReport, true);
		} catch (Exception e) {
			throw new Exception("Error while verifying Policy Detail in Add Building cover section : " + e);
		}
	}

	/**
	 * To get Details from Policy detail Section
	 * 
	 * @param policyNumber
	 * @param extentedReport
	 * @return HashMap
	 * 
	 */
	public HashMap<String, String> getDetailInAddBuildingDetailSection(ExtentTest extentedReport) throws Exception {
		try {
			HashMap<String, String> AddBuildingsDetails = new HashMap<String, String>();

			// Rebuild Cost
			String displayedRebuildCost = fldRebuildCost.getAttribute("value");
			AddBuildingsDetails.put("RebuildCost", displayedRebuildCost);

			// Total Excess Value
			Select selectExcessValue = new Select(drpdwnTotalExcessRequired);
			WebElement optionExcessValue = selectExcessValue.getFirstSelectedOption();
			String displayedExcessValue = optionExcessValue.getText();
			AddBuildingsDetails.put("ExcessValue", displayedExcessValue);

			// Total Name Of Previous Insurance
			Select selectNameOfPreviousInsurance = new Select(drpdwnNameOfPreviousInsurer);
			WebElement optionNameOfPreviousInsurance = selectNameOfPreviousInsurance.getFirstSelectedOption();
			String displayedNameOfPreviousInsurance = optionNameOfPreviousInsurance.getText();
			AddBuildingsDetails.put("NameOfPreviousInsurance", displayedNameOfPreviousInsurance);

			// Total Number Of Previous Insurance
			String displayedNumberOfPreviousInsurance = fldPolicyNumberOfPreviousInsurance.getAttribute("value");
			AddBuildingsDetails.put("NumberOfPreviousInsurance", displayedNumberOfPreviousInsurance);

			// Total Expiry Date Of Previous Insurance
			String displayedExpiryDateOfPreviousInsurance = fldExpiryDateOfPreviousInsurance.getAttribute("value");
			AddBuildingsDetails.put("ExpiryDateOfPreviousInsurance", displayedExpiryDateOfPreviousInsurance);

			// Total Pay For Your Previous Insurance
			Select selectPayForYourPreviousInsurance = new Select(drpdwnPayForYourPreviousInsurance);
			WebElement optionPayForYourPreviousInsurance = selectPayForYourPreviousInsurance.getFirstSelectedOption();
			String displayedPayForYourPreviousInsurance = optionPayForYourPreviousInsurance.getText();
			AddBuildingsDetails.put("PayForYourPreviousInsurance", displayedPayForYourPreviousInsurance);

			// Total No Of Years Without Claiming Value
			Select selectnoOfYearsWithoutClaimingValue = new Select(drpdwnNoOfYearsWithoutClaiming);
			WebElement optionnoOfYearsWithoutClaimingValue = selectnoOfYearsWithoutClaimingValue
					.getFirstSelectedOption();
			String displayednoOfYearsWithoutClaimingValue = optionnoOfYearsWithoutClaimingValue.getText();
			AddBuildingsDetails.put("NoOfYearsWithoutClaimingValue", displayednoOfYearsWithoutClaimingValue);

			Set<String> keys = AddBuildingsDetails.keySet();
			for (String key : keys) {
				Log.message("Displayed Value of " + key + " is : " + AddBuildingsDetails.get(key), extentedReport);
			}

			return AddBuildingsDetails;

		} catch (Exception e) {
			throw new Exception("Error while getting Detail from Policy detail Section : " + e);
		}
	}

	public boolean VerfiyPreviouslyBuildingInsurancesQeustions(ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			if (drpdwnNameOfPreviousInsurer.isDisplayed()) {

				Log.message("after clicking No Btton Previously Building Insurances qeustions are displayed", driver,
						extentedReport, screenshot);
				return screenshot;
			} else {
				screenshot = false;
				Log.message("after clicking No Btton Previously Building Insurances qeustions are not displayed",
						driver, extentedReport, screenshot);
				return screenshot;
			}

		} catch (Exception e) {
			throw new Exception("Error while Verfiying the Previously Building Insurances Qeustions : " + e);
		}
	}

	public boolean verifyMaximumFieldLength(ExtentTest extentedReport, boolean screenshot) throws Exception {
		boolean isAvailable = false;
		try {

			String maxFieldValue = "7";
			if (fldRebuildCost.getAttribute("maxlength").equalsIgnoreCase(maxFieldValue))

				return isAvailable = true;

			else {
				Log.message("Error while verifying field strength of Rebuild Cost", driver, extentedReport, screenshot);
				return isAvailable;
			}

		} catch (Exception e) {
			throw new Exception("Error while verifying field strength of Rebuild Cost on Add Buildings cover : " + e);
		}
	}

}
