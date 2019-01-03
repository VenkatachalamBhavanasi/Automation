package com.pure.selfservice.pages;

import java.util.List;

import org.openqa.selenium.By;
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
public class AddSpecifiedItemPage extends LoadableComponent<AddSpecifiedItemPage> {
	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	public final String Title_No_Existing_Items = "You already have specified items added to your policy Please note: you cannot edit or delete existing specified items already added to your policy. Please call us on 0121 234567 to make updates to your existing items.";
	public final String Title_Existing_Items = "You currently have no specified items added to your policy";
	public final String cssEditAddedItemInWithinHome = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365230_R";
	public final String cssDeleteAddedItemInWithinHome = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365231_R";
	public final String cssEditAddedItemInOutsideHome = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365268_R";
	public final String cssDeleteAddedItemInOutsideHome = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365269_R";
	public final String POPUP_MSG_HELPICON_Add_Specified_Items= "I want add a specified item to this policy";
	public final String POPUP_MSG_HELPICON_No_Specified_Items_Added_To_Your_Policy = "You have not defined any specified items in your contents cover";
	public final String POPUP_MSG_HELPICON_Add_Personal_Possessions_Within_Home = "Indicate if cover is required for specified items while in the home?";
	public final String POPUP_MSG_HELPICON_Add_Another_Item_In_Within_Home = "Select if you want to add another specified item.";
	public final String POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Within_Home = "The type of high risk item to be insured while in the home.";
	public final String POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Within_Home = "Approximate value of the high risk item to be insured while in the home.";
	public final String POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Within_Home = "Description of the high risk item to be insured while in the home.";
	public final String POPUP_MSG_HELPICON_Add_Personal_Possessions_Outside_Home = "Indicate if cover is required for specified items which are taken outside the home?";
	public final String POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Outside_Home = "The type of high risk item to be insured while taken outside the home.";
	public final String POPUP_MSG_HELPICON_Add_Another_Item_In_Outside_Home = "Select if you want to add another specified item.";
	public final String POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Outside_Home = "Approximate value of the high risk item to be insured while taken outside the home.";
	public final String POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Outside_Home = "Description of item";

	@FindBy(css = "#C2__C1__HEAD_867AD172BB8C9D5A674600")
	WebElement txtTitleAddSpecifiedItems;

	@FindBy(css = "#C2__C1__row_HEAD_867AD172BB8C9D5A1235877")
	WebElement txtTitleNoExistingItems;

	@FindBy(css = "#C2__C1__row_HEAD_D36A884E672933B6356195")
	WebElement txtTitleExistingItems;

	@FindBy(css = "button[title='Quote']")
	WebElement btnQuote;

	@FindBy(css = "#C2__C1__BUT_867AD172BB8C9D5A737852")
	WebElement btnCancel;

	@FindBy(css = "button[title='Add another policy Change']")
	WebElement btnAddAnotherPolicyChange;

	@FindBy(css = "[id = C2__C1__C1__QUE_BA2AFC1B42B0F82C365160_0]+span")
	WebElement btnAddPersonalPossessionsWithinHomeYes;

	@FindBy(css = "[id = C2__C1__C1__QUE_BA2AFC1B42B0F82C365160_1]+span")
	WebElement btnAddPersonalPossessionsWithinHomeNo;

	@FindBy(css = "[id = C2__C1__C1__QUE_BA2AFC1B42B0F82C365163_0]+span")
	WebElement btnAddPersonalPossessionsOutsideHomeYes;

	@FindBy(css = "[id = C2__C1__C1__QUE_BA2AFC1B42B0F82C365163_1]+span")
	WebElement btnAddPersonalPossessionsOutsideHomeNo;

	@FindBy(css = "[id = C2__C1__C1__QUE_BA2AFC1B42B0F82C365233_0]+span")
	WebElement btnAddAnotherItemWithinHomeYes;

	@FindBy(css = "[id = C2__C1__C1__QUE_BA2AFC1B42B0F82C365233_1]+span")
	WebElement btnAddAnotherItemWithinHomeNo;

	@FindBy(css = "#C2__C1__C1__QUE_BA2AFC1B42B0F82C365236")
	WebElement drpdwnItemtypeNewSpecifiedItemWithinHome;

	@FindBy(css = "#C2__C1__C1__QUE_BA2AFC1B42B0F82C365237")
	WebElement fldItemvalueNewSpecifiedItemWithinHome;

	@FindBy(css = "#C2__C1__C1__QUE_BA2AFC1B42B0F82C365238")
	WebElement fldDescriptionOfItemNewSpecifiedItemWithinHome;

	@FindBy(css = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365239")
	WebElement btnSaveItemNewSpecifiedItemWithinHome;

	@FindBy(css = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365240")
	WebElement btnCancelItemNewSpecifiedItemWithinHome;

	@FindBy(css = "#C2__C1__C1__BUT_8FC62E0491705A7E403793")
	WebElement btnNextNewSpecifiedItemWithinHome;

	@FindBy(css = "#C2__C1__C1__QUE_BA2AFC1B42B0F82C365272")
	WebElement drpdwnItemtypeNewSpecifiedItemOutsideHome;

	@FindBy(css = "#C2__C1__C1__QUE_BA2AFC1B42B0F82C365273")
	WebElement fldItemvalueNewSpecifiedItemOutsideHome;

	@FindBy(css = "#C2__C1__C1__QUE_BA2AFC1B42B0F82C365274")
	WebElement fldDescriptionOfItemNewSpecifiedItemOutsideHome;

	@FindBy(css = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365275")
	WebElement btnSaveItemNewSpecifiedItemOutsideHome;

	@FindBy(css = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365276")
	WebElement btnCancelItemNewSpecifiedItemOutsideHome;

	@FindBy(css = "[id = C2__C1__C1__QUE_BA2AFC1B42B0F82C365270_0]+span")
	WebElement btnAddAnotherItemOutsideHomeYes;

	@FindBy(css = "[id = C2__C1__C1__QUE_BA2AFC1B42B0F82C365270_1]+span")
	WebElement btnAddAnotherItemOutsideHomeNo;

	@FindBy(css = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365230_R")
	WebElement btnEditAddedItemInWithinHome;

	@FindBy(css = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365231_R")
	WebElement btnDeleteAddedItemInWithinHome;

	@FindBy(css = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365268_R")
	WebElement btnEditAddedItemInOutsideHome;

	@FindBy(css = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365269_R")
	WebElement btnDeleteAddedItemInOutsideHome;

	@FindBy(css = "[id *= C2__C1__C1__QUE_ABAEB7C18EBA7159127652_R]")
	List<WebElement> typeOfItemInWithinHome;

	@FindBy(css = "[id *= C2__C1__C1__QUE_ABAEB7C18EBA7159670661_R]")
	List<WebElement> typeOfItemInOutsideHome;

	@FindBy(css = "#C2__C1__C1__BUT_D47350D8096870BC699785")
	WebElement btnYesDeleteItemWithinHome;

	@FindBy(css = "#C2__C1__C1__BUT_D47350D8096870BC699790")
	WebElement btnNoDeleteItemWithinHoem;

	@FindBy(css = "#C2__C1__C1__BUT_D47350D8096870BC702644")
	WebElement btnYesDeleteItemOutsideHome;

	@FindBy(css = "#C2__C1__C1__BUT_D47350D8096870BC702646")
	WebElement btnNoDeleteItemOutsideHome;

	@FindBy(css = "#C2__C1__C1__FMT_ABAEB7C18EBA7159299251_R1 div[id*='_row_QUE'][id*='_R']")
	List<WebElement> typeOfItemInWithinHomeForAlreadyExistingItem;

	@FindBy(css = "#C2__C1__p1_HEAD_D36A884E672933B6358878")
	WebElement msgExistingSpecifiedItemsAlreadyAdded;

	@FindBy(css = "#C2__C1__C1__TBL_ABAEB7C18EBA7159121965_R2 div[id*='_row_QUE'][id*='_R']")
	List<WebElement> tableSelectedSpecifiedItemWithinHome;
	
	@FindBy(css = "#C2__C1__HELP_HEAD_867AD172BB8C9D5A674600")
	WebElement iconHelpAddSpecifiedItems ;

	@FindBy(css = "#C2__C1__HELP_HEAD_867AD172BB8C9D5A1235877")
	WebElement iconHelpNoSpecifiedItemsAddedToYourPolicy;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpAddSpecifiedItems;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpNoSpecifiedItemsAddedToYourPolicy;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365160")
	WebElement iconHelpaddPersonalPossessionsWithinHome;
	
	@FindBy(css = ".tetris_helpText")
	WebElement popHelpaddPersonalPossessionsWithinHome;
	
	@FindBy(css = "#C2__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365233")
	WebElement iconHelpaddAnotherItemInWithinHome;
	
	@FindBy(css = ".tetris_helpText")
	WebElement popHelpaddAnotherItemInWithinHome;
	
	@FindBy(css = "#C2__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365236")
	WebElement iconHelpItemtypeNewSpecifiedItemWithinHome;
	
	@FindBy(css = ".tetris_helpText")
	WebElement popHelpItemtypeNewSpecifiedItemWithinHome;
	
	@FindBy(css = "#C2__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365237")
	WebElement iconHelpItemvalueNewSpecifiedItemWithinHome;
	
	@FindBy(css = ".tetris_helpText")
	WebElement popHelpItemvalueNewSpecifiedItemWithinHome;
	
	@FindBy(css = "#C2__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365238")
	WebElement iconHelpDescriptionOfItemNewSpecifiedItemWithinHome;
	
	@FindBy(css = ".tetris_helpText")
	WebElement popHelpDescriptionOfItemNewSpecifiedItemWithinHome;
	
	@FindBy(css = "#C2__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365163")
	WebElement iconHelpaddPersonalPossessionsOutsideHome;
	
	@FindBy(css = ".tetris_helpText")
	WebElement popHelpaddPersonalPossessionsOutsideHome;
	
	@FindBy(css = "#C2__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365270")
	WebElement iconHelpaddAnotherItemInOutsideHome;
	
	@FindBy(css = ".tetris_helpText")
	WebElement popHelpaddAnotherItemInOutsideHome;
	
	@FindBy(css = "#C2__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365272")
	WebElement iconHelpItemtypeNewSpecifiedItemOutsideHome;
	
	@FindBy(css = ".tetris_helpText")
	WebElement popHelpItemtypeNewSpecifiedItemOutsideHome;
	
	@FindBy(css = "#C2__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365273")
	WebElement iconHelpItemvalueNewSpecifiedItemOutsideHome;
	
	@FindBy(css = ".tetris_helpText")
	WebElement popHelpItemvalueNewSpecifiedItemOutsideHome;
	
	@FindBy(css = "#C2__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365274")
	WebElement iconHelpDescriptionOfItemNewSpecifiedItemOutsideHome;
	
	@FindBy(css = ".tetris_helpText")
	WebElement popHelpDescriptionOfItemNewSpecifiedItemOutsideHome;
	
	@FindBy(css = "button[title='Call us to change']")
	WebElement btnCallUsToChange;


	/**
	 * 
	 * Constructor class for Add Specified Item page Here we initializing the driver
	 * for page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public AddSpecifiedItemPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(txtTitleAddSpecifiedItems.isDisplayed()))) {
			Log.fail("User is not navigated to Add Specified Items Page ", driver, extentedReport);
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
			Log.message("Clicked on 'Quote' button in 'Add Specified Items' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Quote' button in 'Add Specified Cover' page : " + e);
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
					"Cancel button is not found in 'Add Specified Items' page");
			btnCancel.click();
			Log.message("Clicked on Cancel button in 'Add Specified Items' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking cancel button in 'Add Specified Items' page : " + e);
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
	 * To click yes or No  to add personal possessions which stay within your home
	 * 
	 * @param Add Personal Possessions Within Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoAddPersonalPossessionsWithinHome(String addPersonalPossessionsWithinHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (addPersonalPossessionsWithinHome.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnAddPersonalPossessionsWithinHomeYes);
				btnAddPersonalPossessionsWithinHomeYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAddPersonalPossessionsWithinHomeNo);
				btnAddPersonalPossessionsWithinHomeNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked option for want to add personal possessions which stay within your home : " + addPersonalPossessionsWithinHome + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * To click yes or No  to add personal possessions which are taken outside the home
	 * 
	 * @param Add Personal Possessions Outside Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoAddPersonalPossessionsOutsideHome(String addPersonalPossessionsOutsideHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (addPersonalPossessionsOutsideHome.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnAddPersonalPossessionsOutsideHomeYes);
				btnAddPersonalPossessionsOutsideHomeYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAddPersonalPossessionsOutsideHomeNo);
				btnAddPersonalPossessionsOutsideHomeNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked option for want to add personal possessions which are taken outside the home : " + addPersonalPossessionsOutsideHome + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * To select Item type of Add a New Specified Item under Specified Items within the Home
	 * 
	 * @param Item Type Of Specified Items Within Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectItemTypeOfSpecifiedItemsWithinHome(String itemTypeOfSpecifiedItemsWithinHome, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnItemtypeNewSpecifiedItemWithinHome);
			Select coverSelect = new Select(drpdwnItemtypeNewSpecifiedItemWithinHome);
			coverSelect.selectByVisibleText(itemTypeOfSpecifiedItemsWithinHome);
			Log.message("Selected Cover Type : " + itemTypeOfSpecifiedItemsWithinHome, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting Total Excess Required on Add Building cover page : " + e);
		}
	}

	/**
	 * To select Item type of Add a New Specified Item under Specified Items Outside the Home
	 * 
	 * @param Item Type Of Specified Items Outside Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectItemTypeOfSpecifiedItemsOutsideHome(String itemTypeOfSpecifiedItemsOutsideHome, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnItemtypeNewSpecifiedItemOutsideHome);
			Select coverSelect = new Select(drpdwnItemtypeNewSpecifiedItemOutsideHome);
			coverSelect.selectByVisibleText(itemTypeOfSpecifiedItemsOutsideHome);
			Log.message("Selected Cover Type : " + itemTypeOfSpecifiedItemsOutsideHome, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting Total Excess Required on Add Building cover page : " + e);
		}
	}

	/**
	 * To enter Item value of  Add a New Specified Item Under Specified Items within the Home
	 * 
	 * @param Item Value Of Specified Items Within Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterItemValueOfSpecifiedItemsWithinHome(String itemValueOfSpecifiedItemsWithinHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldItemvalueNewSpecifiedItemWithinHome);
			fldItemvalueNewSpecifiedItemWithinHome.clear();
			fldItemvalueNewSpecifiedItemWithinHome.sendKeys(itemValueOfSpecifiedItemsWithinHome+Keys.TAB);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForElement(driver, fldDescriptionOfItemNewSpecifiedItemWithinHome);
			Log.message(itemValueOfSpecifiedItemsWithinHome + " - rebuild cost of your home is enterd in the Add Buildings Cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Rebuild Cost on Add Building cover: " + e);
		}

	}

	/**
	 * To enter Item value of  Add a New Specified Item Under Specified Items Outside the Home
	 * 
	 * @param Item Value Of Specified Items Outside Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterItemValueOfSpecifiedItemsOutsideHome(String itemValueOfSpecifiedItemsOutsideHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldItemvalueNewSpecifiedItemOutsideHome);
			fldItemvalueNewSpecifiedItemOutsideHome.clear();
			fldItemvalueNewSpecifiedItemOutsideHome.sendKeys(itemValueOfSpecifiedItemsOutsideHome+Keys.TAB);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForElement(driver, fldDescriptionOfItemNewSpecifiedItemOutsideHome);
			Log.message(itemValueOfSpecifiedItemsOutsideHome + " - rebuild cost of your home is enterd in the Add Buildings Cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Rebuild Cost on Add Building cover: " + e);
		}

	}

	/**
	 * To enter Description of item of  Add a New Specified Item Under Specified Items within the Home
	 * 
	 * @param Description of item
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterDescriptionOfItemOfSpecifiedItemsWithinHome(String descriptionOfItemOfSpecifiedItemsWithinHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldDescriptionOfItemNewSpecifiedItemWithinHome);
			fldDescriptionOfItemNewSpecifiedItemWithinHome.clear();
			fldDescriptionOfItemNewSpecifiedItemWithinHome.sendKeys(descriptionOfItemOfSpecifiedItemsWithinHome+Keys.TAB);
			Log.message(descriptionOfItemOfSpecifiedItemsWithinHome + " - rebuild cost of your home is enterd in the Add Buildings Cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Rebuild Cost on Add Building cover: " + e);
		}

	}

	/**
	 * To enter Description of item of  Add a New Specified Item Under Specified Items Outside the Home
	 * 
	 * @param Description of item
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterDescriptionOfItemOfSpecifiedItemsOutsideHome(String descriptionOfItemOfSpecifiedItemsOutsideHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldDescriptionOfItemNewSpecifiedItemOutsideHome);
			fldDescriptionOfItemNewSpecifiedItemOutsideHome.clear();
			fldDescriptionOfItemNewSpecifiedItemOutsideHome.sendKeys(descriptionOfItemOfSpecifiedItemsOutsideHome+Keys.TAB);
			Log.message(descriptionOfItemOfSpecifiedItemsOutsideHome + " - rebuild cost of your home is enterd in the Add Buildings Cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Rebuild Cost on Add Building cover: " + e);
		}

	}

	/**
	 * To click SaveItem button NewSpecifiedItemWithinHome
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickSaveItemNewSpecifiedItemWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnSaveItemNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnSaveItemNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Save Item' button of New SpecifiedItem Within Home in 'Add Specified Items' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Save Item' button of New SpecifiedItem Within Home in 'Add Specified Items' page: " + e);
		}
	}

	/**
	 * To click SaveItem button NewSpecifiedItemOutsideHome
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickSaveItemNewSpecifiedItemOutsideHome(ExtentTest extentedReport, boolean screenshot) throws Exception {

		try {
			WaitUtils.waitForElement(driver, btnSaveItemNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnSaveItemNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Save Item' button of New SpecifiedItem Outside Home in 'Add Specified Items' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Save Item' button of New SpecifiedItem Outside Home in 'Add Specified Items' page: " + e);
		}
	}

	/**
	 * To click Cancel button NewSpecifiedItemWithinHome
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickCancelNewSpecifiedItemWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnCancelItemNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnCancelItemNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Cancel' button of New SpecifiedItem Within Home in 'Add Specified Items' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Cancel' button of New SpecifiedItem Within Home in 'Add Specified Items' page: " + e);
		}
	}

	/**
	 * To click Cancel button NewSpecifiedItemOutsideHome
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickCancelNewSpecifiedItemOutsideHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnCancelItemNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnCancelItemNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Cancel' button of New SpecifiedItem Outside Home in 'Add Specified Items' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Cancel' button of New SpecifiedItem Outside Home in 'Add Specified Items' page: " + e);
		}
	}

	/**
	 * To click Next button NewSpecifiedItemWithinHome
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextNewSpecifiedItemWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnNextNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Next' button of New SpecifiedItem Within Home in 'Add Specified Items' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Next' button of New SpecifiedItem Within Home in 'Add Specified Items' page: " + e);
		}
	}

	/**
	 * To click yes or No for Add Another Item In Outside Home
	 * 
	 * @param Add Another Item In Outside Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoAddAnotherItemInOutsideHome(String addAnotherItemInOutsideHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (addAnotherItemInOutsideHome.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnAddAnotherItemOutsideHomeYes);
				btnAddAnotherItemOutsideHomeYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAddAnotherItemOutsideHomeNo);
				btnAddAnotherItemOutsideHomeNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked option for Do you want to add another item in outside the home : " + addAnotherItemInOutsideHome + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * To click yes or No for Add Another Item In Within Home
	 * 
	 * @param Add Another Item In Within Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoAddAnotherItemInWithinHome(String addAnotherItemInWithinHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (addAnotherItemInWithinHome.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnAddAnotherItemWithinHomeYes);
				btnAddAnotherItemWithinHomeYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAddAnotherItemWithinHomeNo);
				btnAddAnotherItemWithinHomeNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked option for Do you want to add another item in within the home : " + addAnotherItemInWithinHome + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * To get Added Item row in Within home
	 * 
	 * @param itemName
	 * @param Modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public String getAddedItemRowInWithinHome(String itemName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String DeviceRow = null;
			WaitUtils.waitForSpinner(driver);
			if (typeOfItemInWithinHome.size() == 0) {
				Log.message("No Item is available", driver, extentedReport, false);
			}

			for (int loopCount = 0; loopCount < typeOfItemInWithinHome.size(); loopCount++) {
				Integer row = loopCount + 1;
				String displayedItemName = typeOfItemInWithinHome.get(loopCount).getText();
				if (displayedItemName.contains(itemName)) {
					Log.message(itemName + "is found", driver,extentedReport, screenshot);
					return row.toString();
				}
			}
			return DeviceRow;
		} catch (Exception e) {
			throw new Exception("Error while getting Added Item row in Within Home : " + e);
		}
	}

	/**
	 * To get Added Item row in Outside home
	 * 
	 * @param itemName
	 * @param Modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */	
	public String getAddedItemRowInOutsideHome(String itemName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String DeviceRow = null;
			WaitUtils.waitForSpinner(driver);
			if (typeOfItemInOutsideHome.size() == 0) {
				Log.message("No Item is available", driver, extentedReport, false);
			}

			for (int loopCount = 0; loopCount < typeOfItemInOutsideHome.size(); loopCount++) {
				Integer row = loopCount + 1;
				String displayedItemName = typeOfItemInOutsideHome.get(loopCount).getText();
				if (displayedItemName.contains(itemName)) {
					Log.message(itemName + "is found", driver,extentedReport, screenshot);
					return row.toString();
				}
			}
			return DeviceRow;
		} catch (Exception e) {
			throw new Exception("Error while getting Added Item row in Outsied Home: " + e);
		}
	}

	/**
	 * To click Edit button of Specified Item Within Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickEditBtnInWithinHome(String itemName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String ItemRow = getAddedItemRowInWithinHome(itemName, extentedReport, false);
			if (ItemRow == null) {
				throw new Exception(itemName + " - Added Item is not found");
			}
			String elementString = cssEditAddedItemInWithinHome + ItemRow;
			WebElement editBtn = driver.findElement(By.cssSelector(elementString));
			editBtn.click();
			Log.message("Clicked on " + ItemRow + " (th) edit button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking edit button of Added Item in Within Home: " + e);
		}
	}

	/**
	 * To click Delete button of Specified Item Within Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickDeleteBtnInWithinHome(String itemName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String ItemRow = getAddedItemRowInWithinHome(itemName, extentedReport, false);
			if (ItemRow == null) {
				throw new Exception(itemName + " - Added Item is not found");
			}
			String elementString = btnDeleteAddedItemInWithinHome + ItemRow;
			WebElement editBtn = driver.findElement(By.cssSelector(elementString));
			editBtn.click();
			Log.message("Clicked on " + ItemRow + " (th) Delete button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking Delete button of Added Item in Within Home: " + e);
		}
	}

	/**
	 * To click Edit button of Specified Item Outside Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickEditBtnInOutsideHome(String itemName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String ItemRow = getAddedItemRowInOutsideHome(itemName, extentedReport, false);
			if (ItemRow == null) {
				throw new Exception(itemName + " - Added Item is not found");
			}
			String elementString = btnEditAddedItemInOutsideHome + ItemRow;
			WebElement editBtn = driver.findElement(By.cssSelector(elementString));
			editBtn.click();
			Log.message("Clicked on " + ItemRow + " (th) edit button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking edit button of Added Item in Outside Home : " + e);
		}
	}

	/**
	 * To click Delete button of Specified Item Outside Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickDeleteBtnInOutsideHome(String itemName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String ItemRow = getAddedItemRowInOutsideHome(itemName, extentedReport, false);
			if (ItemRow == null) {
				throw new Exception(itemName + " - Added Item is not found");
			}
			String elementString = cssDeleteAddedItemInOutsideHome + ItemRow;
			WebElement editBtn = driver.findElement(By.cssSelector(elementString));
			editBtn.click();
			Log.message("Clicked on " + ItemRow + " (th) Delete button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking Delete button of Added Item in Outside Home: " + e);
		}
	}

	/**
	 * To verify Edit & Delete button of Specified Item within Home
	 * 
	 * @param deviceName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyDeleteAndEditBtnInWithinHome(String deviceName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {

			boolean btnFound = false;
			String deviceRow = getAddedItemRowInWithinHome(deviceName, extentedReport, false);

			if (deviceRow == null) {
				//throw new Exception(deviceName + " - Added Item is not found");
				return false;
			}

			String editElementString = cssEditAddedItemInWithinHome + deviceRow;
			WebElement editBtn = driver.findElement(By.cssSelector(editElementString));

			String deleteElementString = cssDeleteAddedItemInWithinHome + deviceRow;
			WebElement deleteBtn = driver.findElement(By.cssSelector(deleteElementString));

			if (WaitUtils.waitForElement(driver, editBtn) && WaitUtils.waitForElement(driver, deleteBtn)) {
				btnFound = true;
			}
			return btnFound;
		} catch (Exception e) {
			throw new Exception("Error while verifying edit and delete button of Added Item in Within Home : " + e);
		}
	}

	/**
	 * To verify Edit & Delete button of Specified Item Outside Home
	 * 
	 * @param deviceName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyDeleteAndEditBtnInOutsideHome(String itemName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {

			boolean btnFound = false;
			String deviceRow = getAddedItemRowInOutsideHome(itemName, extentedReport, false);

			if (deviceRow == null) {
				//throw new Exception(itemName + " - Added Item is not found");
				return false;
			}

			String editElementString = cssEditAddedItemInOutsideHome + deviceRow;
			WebElement editBtn = driver.findElement(By.cssSelector(editElementString));

			String deleteElementString = cssDeleteAddedItemInOutsideHome + deviceRow;
			WebElement deleteBtn = driver.findElement(By.cssSelector(deleteElementString));

			if (WaitUtils.waitForElement(driver, editBtn) && WaitUtils.waitForElement(driver, deleteBtn)) {
				btnFound = true;
			}
			return btnFound;
		} catch (Exception e) {
			throw new Exception("Error while verifying edit and delete button of Added Item in Outside Home : " + e);
		}
	}

	/**
	 * To click yes/No button in popup Within Home
	 * 
	 * @param deleteItem
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoBtnToDeleteItemInWithinHome(String deleteItem, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (deleteItem) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnYesDeleteItemWithinHome);
				GenericUtils.clickElementJS(driver, btnYesDeleteItemWithinHome);
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnNoDeleteItemWithinHoem);
				GenericUtils.clickElementJS(driver, btnNoDeleteItemWithinHoem);
				WaitUtils.waitForSpinner(driver);
				break;
			}
			WaitUtils.waitForPageLoad(driver);
			Log.message("Clicked on " + deleteItem + " button in Item deleting Popup", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking button in Item deleting popup : " + e);
		}
	}

	/**
	 * To click yes/No button in popup in Outside Home
	 * 
	 * @param deleteItem
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoBtnToDeleteItemInOutsideHome(String deleteItem, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (deleteItem) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnYesDeleteItemOutsideHome);
				GenericUtils.clickElementJS(driver, btnYesDeleteItemOutsideHome);
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnNoDeleteItemOutsideHome);
				GenericUtils.clickElementJS(driver, btnNoDeleteItemOutsideHome);
				WaitUtils.waitForSpinner(driver);
				break;
			}
			WaitUtils.waitForPageLoad(driver);
			Log.message("Clicked on " + deleteItem + " button in Item deleting Popup", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking button in Item deleting popup : " + e);
		}
	}

	/**
	 * To get Added Item row in Within home For Already Existing Item
	 *
	 * @param itemName
	 * @param Modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 *
	 */
	public String getAddedItemRowInWithinHomeForAlreadyExistingItem(String itemName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String DeviceRow = null;
			WaitUtils.waitForSpinner(driver);
			if (typeOfItemInWithinHomeForAlreadyExistingItem.size() == 0) {
				Log.message("No Item is available", driver, extentedReport, false);
			}
			for (int loopCount = 0; loopCount < typeOfItemInWithinHomeForAlreadyExistingItem.size(); loopCount++) {
				Integer row = loopCount + 1;
				String displayedItemName = typeOfItemInWithinHomeForAlreadyExistingItem.get(loopCount).getText();
				if (displayedItemName.contains(itemName)) {
					Log.message(itemName + "is found", driver,extentedReport, screenshot);
					return row.toString();
				}
			}
			return DeviceRow;
		} catch (Exception e) {
			throw new Exception("Error while getting Added Item row in Within Home : " + e);
		}
	}
	
	/**
	 * To click yes or No  to add personal possessions which are taken outside the home
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoWantToAddAnotherItemWithinHome(String addAnotherItemWithinHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (addAnotherItemWithinHome.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnAddAnotherItemWithinHomeYes);
				btnAddAnotherItemWithinHomeYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAddAnotherItemWithinHomeNo);
				btnAddAnotherItemWithinHomeNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked option for want to Add Another Item Within " + addAnotherItemWithinHome + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * Editing Add Item Within Home
	 * 
	 * @param itemName
	 * @param itemType
	 * @param itemValue
	 * @param itemDecsription
	 * @param saveAndCancel
	 * @param extentReport
	 * @param screenshot
	 *
	 */
	public void editAddItemWithinHome(String itemName,String itemType, String itemValue, String itemDecsription, String saveAndCancel, ExtentTest extentReport,
			boolean screenshot) throws Exception {

		try {
			String rowNum = getAddedItemRowInWithinHome(itemName, extentedReport, false);
			WebElement edit = driver.findElement(By.cssSelector(cssEditAddedItemInWithinHome + String.valueOf(rowNum)));
			WaitUtils.waitForElement(driver,edit);
			WaitUtils.waitForelementToBeClickable(driver, edit, "");
			edit.click();
			/*((JavascriptExecutor) driver).executeScript("arguments[0].click();", edit);*/
			WaitUtils.waitForElement(driver, drpdwnItemtypeNewSpecifiedItemWithinHome);
			selectItemTypeOfSpecifiedItemsWithinHome(itemType,extentedReport,true);
			enterItemValueOfSpecifiedItemsWithinHome(itemValue,extentedReport,true);
			enterDescriptionOfItemOfSpecifiedItemsWithinHome(itemDecsription, extentedReport,true);
			switch (saveAndCancel) {
			case "Save":
				WaitUtils.waitForElement(driver, btnSaveItemNewSpecifiedItemWithinHome);
				clickSaveItemNewSpecifiedItemWithinHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on Save Occupation", driver, extentedReport);
				break;

			case "Cancel":
				WaitUtils.waitForElement(driver,btnCancelItemNewSpecifiedItemWithinHome);
				clickCancelNewSpecifiedItemWithinHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				break;
			}			
			Log.message("Specified item details are edited successfuly", driver, extentedReport, true);
		} catch (Exception e) {
			Log.message("Getting error while editing Specified item details in Within Home : " + e);
		}
	}

	/**
	 * Editing Add Item Outside Home
	 * 
	 * @param itemName
	 * @param itemType
	 * @param itemValue
	 * @param itemDecsription
	 * @param saveAndCancel
	 * @param extentReport
	 * @param screenshot
	 *
	 */
	public void editAddItemOutsideHome(String itemName,String itemType, String itemValue, String itemDecsription, String saveAndCancel, ExtentTest extentReport,
			boolean screenshot) throws Exception {

		try {
			String rowNum = getAddedItemRowInOutsideHome(itemName, extentedReport, false);
			WebElement edit = driver.findElement(By.cssSelector(cssEditAddedItemInOutsideHome + String.valueOf(rowNum)));
			WaitUtils.waitForElement(driver,edit);
			WaitUtils.waitForelementToBeClickable(driver, edit, "");
			edit.click();
			//((JavascriptExecutor) driver).executeScript("arguments[0].click();", edit);
			WaitUtils.waitForElement(driver, drpdwnItemtypeNewSpecifiedItemOutsideHome);
			selectItemTypeOfSpecifiedItemsOutsideHome(itemType,extentedReport,true);
			enterItemValueOfSpecifiedItemsOutsideHome(itemValue,extentedReport,true);
			enterDescriptionOfItemOfSpecifiedItemsOutsideHome(itemDecsription, extentedReport,true);
			switch (saveAndCancel) {
			case "Save":
				WaitUtils.waitForElement(driver, btnSaveItemNewSpecifiedItemOutsideHome);
				clickSaveItemNewSpecifiedItemOutsideHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on Save Occupation", driver, extentedReport);
				break;

			case "Cancel":
				WaitUtils.waitForElement(driver,btnCancelItemNewSpecifiedItemOutsideHome);
				clickCancelNewSpecifiedItemOutsideHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				break;
			}			
			Log.message("Specified item details are edited successfuly", driver, extentedReport, true);
		} catch (Exception e) {
			Log.message("Getting error while editing Specified item details in Outside Home : " + e);
		}
	}

	/**
	 * Verifies deleting Add Item details which are added
	 * 
	 * @param itemName
	 * @param empStatus
	 * @param extentReport
	 * @param screenshot
	 *
	 */

	public void deleteAddItemInWithinHome(String itemName, String deleteItem, ExtentTest extenteReport, boolean screenshot) throws Exception {
		try {
			String rowNum = getAddedItemRowInWithinHome(itemName, extentedReport, false);
			WaitUtils.waitForElement(driver,
					driver.findElement(By.cssSelector(cssDeleteAddedItemInWithinHome + String.valueOf(rowNum))));
			
			WebElement delete = driver.findElement(By.cssSelector(cssDeleteAddedItemInWithinHome + String.valueOf(rowNum)));
			WaitUtils.waitForElement(driver,delete);
			WaitUtils.waitForelementToBeClickable(driver, delete, "");
			delete.click();
			//((JavascriptExecutor) driver).executeScript("arguments[0].click();", delete);
			WaitUtils.waitForSpinner(driver);
			clickYesOrNoBtnToDeleteItemInWithinHome(deleteItem, extenteReport, true);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Yes Add Item", driver, extentedReport);
			WaitUtils.waitForSpinner(driver);


		} catch (Exception e) {
			Log.message("Error while deleting the existed Add Item details : " + e);
		}
	}

	/**
	 * Verifies deleting Add Item details which are added
	 * 
	 * @param itemName
	 * @param empStatus
	 * @param extentReport
	 * @param screenshot
	 *
	 */

	public void deleteAddItemInOutsideHome(String itemName, String deleteItem, ExtentTest extenteReport, boolean screenshot) throws Exception {
		try {
			String rowNum = getAddedItemRowInOutsideHome(itemName, extentedReport, false);
			WaitUtils.waitForElement(driver,
					driver.findElement(By.cssSelector(cssDeleteAddedItemInOutsideHome + String.valueOf(rowNum))));
			WebElement delete = driver.findElement(By.cssSelector(cssDeleteAddedItemInOutsideHome + String.valueOf(rowNum)));
			WaitUtils.waitForElement(driver,delete);
			WaitUtils.waitForelementToBeClickable(driver, delete, "");
			delete.click();
			WaitUtils.waitForSpinner(driver);
			clickYesOrNoBtnToDeleteItemInOutsideHome(deleteItem, extentedReport, true);
			Log.message("Clicked on Yes Add Item", driver, extentedReport);
			WaitUtils.waitForSpinner(driver);

		} catch (Exception e) {
			Log.message("Error while deleting the existed Add Item details : " + e);
		}
	}

	/**
	 * To verify 'AddAnotherPolicyChange' and "Quote" button disabled
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */


	public void verifyAddAnotherPolicyAndQuoteIsDiabled(ExtentTest extentedReport, boolean screenshot) throws Exception
	{
		//

		try {

			WaitUtils.waitForDisabledElement(driver, btnAddAnotherPolicyChange, 20);

			WaitUtils.waitForDisabledElement(driver,btnQuote, 20);

			Log.message("Button "+ btnAddAnotherPolicyChange.getText() +" and "+"Button "+btnQuote.getText()+" is displayed and disabled", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On Help Icon Of Description Of Item Outside Home  : " + e);
		}

	}

	//msgExistingSpecifiedItemsAlreadyAdded

	/**
	 * To verify existing specified items already added are shown 
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */


	public void verifyExistingSpecifiedItemsAlreadyAdded(GetTestData testData, ExtentTest extentedReport, boolean screenshot) throws Exception
	{


		try {


			if(msgExistingSpecifiedItemsAlreadyAdded.getText().contains("you cannot edit or delete existing specified items already added to your policy."))
			{

				Log.message("Note Message is shown saying unable to edit existing policy "+msgExistingSpecifiedItemsAlreadyAdded.getText() );	
			}


		} catch (Exception e) {
			Log.message("Note Message is Not shown saying unable to edit existing policy " + e);
		}
	}

	/**
	 * To Add Specified Item within Home
	 * 
	 * @param itemType, itemValue, itemDecsription, saveAndCancel
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void addSpecifiedItemwithinHome(String itemType, String itemValue, String itemDecsription, String saveAndCancel, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			selectItemTypeOfSpecifiedItemsWithinHome(itemType,extentedReport,true);
			enterItemValueOfSpecifiedItemsWithinHome(itemValue,extentedReport,true);
			enterDescriptionOfItemOfSpecifiedItemsWithinHome(itemDecsription, extentedReport,true);
			switch (saveAndCancel) {
			case "Save":
				clickSaveItemNewSpecifiedItemWithinHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				break;

			case "Cancel":
				clickCancelNewSpecifiedItemWithinHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Added Specified item within home in Add Contents cover page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while adding the Specified item within home in Add Contents cover page: " + e);
		}
	}

	/**
	 * To Add Specified Item outside Home
	 * 
	 * @param itemType, itemValue, itemDecsription, saveAndCancel
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void addSpecifiedItemOutsideHome(String itemType, String itemValue, String itemDecsription, String saveAndCancel, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			selectItemTypeOfSpecifiedItemsOutsideHome(itemType,extentedReport,true);
			enterItemValueOfSpecifiedItemsOutsideHome(itemValue,extentedReport,true);
			enterDescriptionOfItemOfSpecifiedItemsOutsideHome(itemDecsription, extentedReport,true);
			switch (saveAndCancel) {
			case "Save":
				clickSaveItemNewSpecifiedItemOutsideHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				break;

			case "Cancel":
				clickCancelNewSpecifiedItemOutsideHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				break;
			}

			Log.message("Added Specified item outside home in Add Contents cover page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while adding the Specified item outside home in Add Contents cover page: " + e);
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
			WaitUtils.waitForSpinner(driver);
			//iconHelpAddSpecifiedItems.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpAddSpecifiedItems);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpChangeMyProperty).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpAddSpecifiedItems);
			Log.message("Hovered on the HelpIcon Of Add Specified Items", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Specified Items : " + e);
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
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of No Specified ItemsAdded To YourPolicy
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfNoSpecifiedItemsAddedToYourPolicy(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpNoSpecifiedItemsAddedToYourPolicy);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpNoSpecifiedItemsAddedToYourPolicy.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpNoSpecifiedItemsAddedToYourPolicy);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpChangeMyProperty).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpNoSpecifiedItemsAddedToYourPolicy);
			Log.message("Hovered on the Help Icon Of No Specified ItemsAdded To YourPolicy", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On Help Icon Of No Specified ItemsAdded To YourPolicy: " + e);
		}
	}

	/**
	 * verify HelpIcon No Specified Items Added To Your Policy Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconNoSpecifiedItemsAddedToYourPolicyPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpNoSpecifiedItemsAddedToYourPolicy);
			if (GenericUtils.verifyWebElementTextContains(popHelpNoSpecifiedItemsAddedToYourPolicy,
					POPUP_MSG_HELPICON_No_Specified_Items_Added_To_Your_Policy)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of add Personal Possessions Within Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIaddPersonalPossessionsWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpaddPersonalPossessionsWithinHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpaddPersonalPossessionsWithinHome.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpaddPersonalPossessionsWithinHome);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpaddPersonalPossessionsWithinHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * verify HelpIcon add Personal Possessions Within Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconaddPersonalPossessionsWithinHomePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpaddPersonalPossessionsWithinHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpaddPersonalPossessionsWithinHome,
					POPUP_MSG_HELPICON_Add_Personal_Possessions_Within_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of Do you want to Add Another Item In Within Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIaddAnotherItemInWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpaddAnotherItemInWithinHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpaddAnotherItemInWithinHome.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpaddAnotherItemInWithinHome);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpaddAnotherItemInWithinHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Do you want to Add Another Item In Within Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconaddAnotherItemInWithinHomePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpaddAnotherItemInWithinHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpaddAnotherItemInWithinHome,
					POPUP_MSG_HELPICON_Add_Another_Item_In_Within_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of Item type New Specified Item Within Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIItemtypeNewSpecifiedItemWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpItemtypeNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpItemtypeNewSpecifiedItemWithinHome.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpItemtypeNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpItemtypeNewSpecifiedItemWithinHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Item type New Specified Item Within Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconItemtypeNewSpecifiedItemWithinHome() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpItemtypeNewSpecifiedItemWithinHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpItemtypeNewSpecifiedItemWithinHome,
					POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Within_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of Item value New Specified Item Within Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIItemvalueNewSpecifiedItemWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpItemvalueNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpItemvalueNewSpecifiedItemWithinHome.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpItemvalueNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpItemvalueNewSpecifiedItemWithinHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Item type New Specified Item Within Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconItemvalueNewSpecifiedItemWithinHome() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpItemvalueNewSpecifiedItemWithinHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpItemvalueNewSpecifiedItemWithinHome,
					POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Within_Home)) {
				isDisplayed = true;
			}
			btnAddAnotherItemWithinHomeYes.click();
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of Description Of Item New Specified Item Within Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIDescriptionOfItemNewSpecifiedItemWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpDescriptionOfItemNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpDescriptionOfItemNewSpecifiedItemWithinHome.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpDescriptionOfItemNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpDescriptionOfItemNewSpecifiedItemWithinHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Description Of Item New Specified Item Within Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconDescriptionOfItemNewSpecifiedItemWithinHome() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpDescriptionOfItemNewSpecifiedItemWithinHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpDescriptionOfItemNewSpecifiedItemWithinHome,
					POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Within_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of add Personal Possessions Outside Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIaddPersonalPossessionsOutsideHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpaddPersonalPossessionsOutsideHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpaddPersonalPossessionsOutsideHome.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpaddPersonalPossessionsOutsideHome);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpaddPersonalPossessionsOutsideHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Description Of Item New Specified Item Within Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconaddPersonalPossessionsOutsideHome() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpaddPersonalPossessionsOutsideHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpaddPersonalPossessionsOutsideHome,
					POPUP_MSG_HELPICON_Add_Personal_Possessions_Outside_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of Do you want to Add Another Item In Outside Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIaddAnotherItemInOutsideHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpaddAnotherItemInOutsideHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpaddAnotherItemInOutsideHome.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpaddAnotherItemInOutsideHome);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpaddAnotherItemInOutsideHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Do you want to Add Another Item In Outside Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconaddAnotherItemInOutsideHomePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpaddAnotherItemInOutsideHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpaddAnotherItemInOutsideHome,
					POPUP_MSG_HELPICON_Add_Another_Item_In_Outside_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of Item type New Specified Item Outside Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIItemtypeNewSpecifiedItemOutsideHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpItemtypeNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpItemtypeNewSpecifiedItemOutsideHome.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpItemtypeNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpItemtypeNewSpecifiedItemOutsideHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Description Of Item New Specified Item Within Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconItemtypeNewSpecifiedItemOutsideHome() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpItemtypeNewSpecifiedItemOutsideHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpItemtypeNewSpecifiedItemOutsideHome,
					POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Outside_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of Item value New Specified Item Outside Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIItemvalueNewSpecifiedItemOutsideHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpItemvalueNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpItemvalueNewSpecifiedItemOutsideHome.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpItemvalueNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpItemvalueNewSpecifiedItemOutsideHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Item type New Specified Item Outside Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconItemvalueNewSpecifiedItemOutsideHome() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpItemvalueNewSpecifiedItemOutsideHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpItemvalueNewSpecifiedItemOutsideHome,
					POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Outside_Home)) {
				isDisplayed = true;
			}
			btnAddAnotherItemOutsideHomeYes.click();
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of Description Of Item New Specified Item Outside Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIDescriptionOfItemNewSpecifiedItemOutsideHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpDescriptionOfItemNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpDescriptionOfItemNewSpecifiedItemOutsideHome.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpDescriptionOfItemNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpDescriptionOfItemNewSpecifiedItemOutsideHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Description Of Item New Specified Item Outside Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconDescriptionOfItemNewSpecifiedItemOutisdeHome() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpDescriptionOfItemNewSpecifiedItemOutsideHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpDescriptionOfItemNewSpecifiedItemOutsideHome,
					POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Outside_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

}
