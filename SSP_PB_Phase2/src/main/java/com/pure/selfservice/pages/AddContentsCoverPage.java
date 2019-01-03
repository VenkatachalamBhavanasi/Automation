package com.pure.selfservice.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
public class AddContentsCoverPage extends LoadableComponent<AddContentsCoverPage> {
	public final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	public final String POPUP_MSG_HELPICON_Value_Of_Home_Contents = "The total value of your home contents. The amount required to fully re-instate all the items in your home in the event of an insured loss.";
	public final String POPUP_MSG_HELPICON_Value_Of_Precious_Or_Costly_Items = "How much of the contents sum insured is represented by high risk items to be insured within the home? Include items such as jewellery, watches, furs, photo equipment, items of gold, silver or other precious metals etc.";
	public final String POPUP_MSG_HELPICON_value_Of_Most_Expensive_Item = "What is the value of the most expensive single item to be covered by the policy?";
	public final String POPUP_MSG_HELPICON_Type_Of_Home_Contents_Cover_Required = "What type of contents cover is required including whether accidental damage is to be included.";
	public final String POPUP_MSG_HELPICON_Total_Excess_Required = "Total excess required";
	public final String POPUP_MSG_HELPICON_Contents_Sum_Represent_Replacement_Amount = "Does the sum insured for the contents represent the full replacement value of the items within the home?";
	public final String POPUP_MSG_HELPICON_Previously_Contents_Insurance = "Have you previously held contents insurance?";
	public final String POPUP_MSG_HELPICON_Name_Of_Previous_Insurer = "Previous insurer for contents insurance?";
	public final String POPUP_MSG_HELPICON_Policy_Number_Of_Previous_Insurance = "Previous policy number for contents insurance?";
	public final String POPUP_MSG_HELPICON_Expiry_Date_Of_Previous_Insurance = "Previous policy expiry date for contents insurance";
	public final String POPUP_MSG_HELPICON_Pay_For_Your_Previous_Insurance = "Previous payment frequency for contents insurance?";
	public final String POPUP_MSG_HELPICON_No_Of_Years_Without_Claiming = "The number of consecutive claim free years you have accrued for contents insurance.";
	public final String POPUP_MSG_HELPICON_Add_Contents_Cover = "I want to add contents cover to this policy";
	public final String POPUP_MSG_HELPICON_Cash_In_Your_Home_To_Be_Insured = "Sum insured required for loss of money away from the home. (Note that many policies include an amount for this cover by default)?";
	public final String POPUP_MSG_HELPICON_Credit_Cards_To_Be_Insured = "Sum insured required for loss of credit cards away from the home. (Note that many policies include an amount for this cover by default)?";
	public final String POPUP_MSG_HELPICON_Frozen_Food_To_Be_Insured = "Sum insured required for frozen food cover. (Note that many policies include an amount for this cover by default)?";
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
	public final String cssEditAddedItemInWithinHome = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365230_R";
	public final String cssDeleteAddedItemInWithinHome = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365231_R";
	public final String cssEditAddedItemInOutsideHome = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365268_R";
	public final String cssDeleteAddedItemInOutsideHome = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365269_R";

	@FindBy(css = "#C2__C1__HEAD_885CBC1D2759A867391551")
	WebElement txtTitleAddContentsCover;

	@FindBy(css = "button[title='Quote']")
	WebElement btnQuote;

	@FindBy(css = "#C2__C1__BUT_885CBC1D2759A867391611")
	WebElement btnCancel;

	@FindBy(css = "button[title='Add another policy change']")
	WebElement btnAddAnotherPolicyChange;

	@FindBy(css = "#C2__C1__QUE_885CBC1D2759A867391554")
	WebElement fldTotalValueOfYourHomeContents;

	@FindBy(css = "#C2__C1__QUE_885CBC1D2759A867391556")
	WebElement fldTotalValueOfPreciousOrCostlyItems;

	@FindBy(css = "#C2__C1__QUE_885CBC1D2759A867391557")
	WebElement fldValueOfMostExpensiveItem;

	@FindBy(css = "#C2__C1__QUE_885CBC1D2759A867391558")
	WebElement drpdwnTypeOfHomeContentsCoverRequired;

	@FindBy(css = "#C2__C1__QUE_885CBC1D2759A867391559")
	WebElement drpdwnTotalExcessRequired;

	@FindBy(css = "[id = C2__C1__QUE_885CBC1D2759A867391560_0]+span")
	WebElement btncontentsSumRepresentReplacementAmountYes;

	@FindBy(css = "[id = C2__C1__QUE_885CBC1D2759A867391560_1]+span")
	WebElement btncontentsSumRepresentReplacementAmountNo;

	@FindBy(css = "[id = C2__C1__QUE_885CBC1D2759A867391561_0]+span")
	WebElement btnPreviouslyContentsInsuranceYes;

	@FindBy(css = "[id = C2__C1__QUE_885CBC1D2759A867391561_1]+span")
	WebElement btnPreviouslyContentsInsuranceNo;

	@FindBy(css = "#C2__C1__ID-content-information")
	WebElement btnNextContentsInformation;

	@FindBy(css = "#C2__C1__QUE_885CBC1D2759A867391567")
	WebElement fldCashKeptInYourHomeToBeInsured;

	@FindBy(css = "#C2__C1__QUE_885CBC1D2759A867391568")
	WebElement fldCreditCardsToBeInsured;

	@FindBy(css = "#C2__C1__QUE_885CBC1D2759A867391569")
	WebElement fldFrozenFoodToBeInsured;

	@FindBy(css = "#C2__C1__ID-Additional-cover")
	WebElement btnNextAdditionalCovers;

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
	WebElement btnNoDeleteItemWithinHome;

	@FindBy(css = "#C2__C1__C1__BUT_D47350D8096870BC702644")
	WebElement btnYesDeleteItemOutsideHome;

	@FindBy(css = "#C2__C1__C1__BUT_D47350D8096870BC702646")
	WebElement btnNoDeleteItemOutsideHome;

	@FindBy(css = "#C2__C1__QUE_885CBC1D2759A867391562")
	WebElement drpdwnNameOfPreviousInsurer;

	@FindBy(css = "#C2__C1__QUE_885CBC1D2759A867391563")
	WebElement fldPolicyNumberOfPreviousInsurance;

	@FindBy(css = "#C2__C1__QUE_885CBC1D2759A867391564")
	WebElement fldExpiryDateOfPreviousInsurance;

	@FindBy(css = "#C2__C1__QUE_885CBC1D2759A867391565")
	WebElement drpdwnPayForYourPreviousInsurance;

	@FindBy(css = "#C2__C1__QUE_3AACC90BFC13E0AE309146")
	WebElement drpdwnNoOfYearsWithoutClaiming;

	@FindBy(css = "a[href='#C2__C1__Contents-Information']")
	WebElement contentsInformation;

	@FindBy(css = "a[href='#C2__C1__Additional-covers']")
	WebElement additionaCovers;

	@FindBy(css = "a[href='#C2__C1__C1__Specified-Items']")
	WebElement specifieItems;

	@FindBy(css = "#C2__C1__C1__p1_HEAD_BA2AFC1B42B0F82C365223>div")
	WebElement headerTypeInWithinHome;

	@FindBy(css = "#C2__C1__C1__p1_HEAD_BA2AFC1B42B0F82C365224>div")
	WebElement headerValueInWithinHome;

	@FindBy(css = "#C2__C1__C1__p1_HEAD_BA2AFC1B42B0F82C365225>div")
	WebElement headerDescriptionInWithinHome;

	@FindBy(css = "#C2__C1__C1__p1_HEAD_BA2AFC1B42B0F82C365261>div")
	WebElement headerTypeInOutsideHome;

	@FindBy(css = "#C2__C1__C1__p1_HEAD_BA2AFC1B42B0F82C365262>div")
	WebElement headerValueInOutsideHome;

	@FindBy(css = "#C2__C1__C1__p1_HEAD_BA2AFC1B42B0F82C365263>div")
	WebElement headerDescriptionInOutsideHome;

	@FindBy(css = "#C2__C1__HELP_QUE_885CBC1D2759A867391554")
	WebElement iconHelpvalueOfHomeContents;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpvalueOfHomeContents;

	@FindBy(css = "#C2__C1__HELP_QUE_885CBC1D2759A867391556")
	WebElement iconHelpvalueOfPreciousOrCostlyItems;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpvalueOfPreciousOrCostlyItems;

	@FindBy(css = "#C2__C1__HELP_QUE_885CBC1D2759A867391557")
	WebElement iconHelpvalueOfMostExpensiveItem;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpvalueOfMostExpensiveItem;

	@FindBy(css = "#C2__C1__HELP_QUE_885CBC1D2759A867391558")
	WebElement iconHelptypeOfHomeContentsCoverRequired;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelptypeOfHomeContentsCoverRequired;

	@FindBy(css = "#C2__C1__HELP_QUE_885CBC1D2759A867391559")
	WebElement iconHelptotalExcessRequired;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelptotalExcessRequired;

	@FindBy(css = "#C2__C1__HELP_QUE_885CBC1D2759A867391560")
	WebElement iconHelpcontentsSumRepresentReplacementAmount;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpcontentsSumRepresentReplacementAmount;

	@FindBy(css = "#C2__C1__HELP_QUE_885CBC1D2759A867391561")
	WebElement iconHelppreviouslyContentsInsurance;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelppreviouslyContentsInsurance;

	@FindBy(css = "#C2__C1__HELP_QUE_885CBC1D2759A867391562")
	WebElement iconHelpnameOfPreviousInsurer;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpnameOfPreviousInsurer;

	@FindBy(css = "#C2__C1__HELP_QUE_885CBC1D2759A867391563")
	WebElement iconHelpPolicyNumberOfPreviousInsurance;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPolicyNumberOfPreviousInsurance;

	@FindBy(css = "#C2__C1__HELP_QUE_885CBC1D2759A867391564")
	WebElement iconHelpExpiryDateOfPreviousInsurance;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpExpiryDateOfPreviousInsurance;

	@FindBy(css = "#C2__C1__HELP_QUE_885CBC1D2759A867391565")
	WebElement iconHelpPayForYourPreviousInsurance;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPayForYourPreviousInsurance;

	@FindBy(css = "#C2__C1__HELP_QUE_3AACC90BFC13E0AE309146")
	WebElement iconHelpNoOfYearsWithoutClaiming;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpNoOfYearsWithoutClaiming;

	@FindBy(css = "#C2__C1__HELP_HEAD_885CBC1D2759A867391551")
	WebElement iconHelpAddContentsCover;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpAddContentsCover;

	@FindBy(css = "#C2__C1__HELP_QUE_885CBC1D2759A867391567")
	WebElement iconHelpcashInYourHomeToBeInsured;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpcashInYourHomeToBeInsured;

	@FindBy(css = "#C2__C1__HELP_QUE_885CBC1D2759A867391568")
	WebElement iconHelpcreditCardsToBeInsured;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpcreditCardsToBeInsured;

	@FindBy(css = "#C2__C1__HELP_QUE_885CBC1D2759A867391569")
	WebElement iconHelpfrozenFoodToBeInsured;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpfrozenFoodToBeInsured;

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

	/**
	 * 
	 * Constructor class for Add Specified Item page Here we initializing the driver
	 * for page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public AddContentsCoverPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(txtTitleAddContentsCover.isDisplayed()))) {
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
			Log.message("Clicked on 'Quote' button in 'Add Contents Cover' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Quote' button in 'Add Contents Cover' page : " + e);
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
					"Cancel button is not found in 'Add Contents cover' page");
			btnCancel.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Cancel button in 'Add Contents Cover' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking cancel button in 'Add Contents Cover' page : " + e);
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
					"Error while clicking 'Add Another Policy Change' button in 'Add Contents Cover' page : " + e);
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
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected Cover Type : " + itemTypeOfSpecifiedItemsWithinHome, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting Total Excess Required on Add Contents cover page : " + e);
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
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected Cover Type : " + itemTypeOfSpecifiedItemsOutsideHome, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting Total Excess Required on Add Contents cover page : " + e);
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
			WaitUtils.waitForSpinner(driver);
			fldItemvalueNewSpecifiedItemWithinHome.sendKeys(itemValueOfSpecifiedItemsWithinHome);
			fldDescriptionOfItemNewSpecifiedItemWithinHome.click();
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForElement(driver, fldDescriptionOfItemNewSpecifiedItemWithinHome);
			Log.message(itemValueOfSpecifiedItemsWithinHome + " - rebuild cost of your home is enterd in the Add Buildings Cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Rebuild Cost on Add Contents cover: " + e);
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
			WaitUtils.waitForSpinner(driver);
			fldItemvalueNewSpecifiedItemOutsideHome.sendKeys(itemValueOfSpecifiedItemsOutsideHome);
			fldDescriptionOfItemNewSpecifiedItemOutsideHome.click();
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForElement(driver, fldDescriptionOfItemNewSpecifiedItemOutsideHome);
			Log.message(itemValueOfSpecifiedItemsOutsideHome + " - rebuild cost of your home is enterd in the Add Buildings Cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Rebuild Cost on Add Contents cover: " + e);
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
			WaitUtils.waitForSpinner(driver);
			fldDescriptionOfItemNewSpecifiedItemWithinHome.clear();
			fldDescriptionOfItemNewSpecifiedItemWithinHome.sendKeys(descriptionOfItemOfSpecifiedItemsWithinHome);
			WaitUtils.waitForSpinner(driver);
			Log.message(descriptionOfItemOfSpecifiedItemsWithinHome + " - rebuild cost of your home is enterd in the Add Buildings Cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Rebuild Cost on Add Contents cover: " + e);
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
			WaitUtils.waitForSpinner(driver);
			fldDescriptionOfItemNewSpecifiedItemOutsideHome.clear();
			fldDescriptionOfItemNewSpecifiedItemOutsideHome.sendKeys(descriptionOfItemOfSpecifiedItemsOutsideHome);
			WaitUtils.waitForSpinner(driver);
			Log.message(descriptionOfItemOfSpecifiedItemsOutsideHome + " - rebuild cost of your home is enterd in the Add Buildings Cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Rebuild Cost on Add Contents cover: " + e);
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
			throw new Exception("Error while clicking 'Save Item' button of New SpecifiedItem Within Home in 'Add Contents Cover' page: " + e);
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
			throw new Exception("Error while clicking 'Save Item' button of New SpecifiedItem Outside Home in 'Add Contents Cover' page: " + e);
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
			throw new Exception("Error while clicking 'Cancel' button of New SpecifiedItem Within Home in 'Add Contents Cover' page: " + e);
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
			throw new Exception("Error while clicking 'Cancel' button of New SpecifiedItem Outside Home in 'Add Contents Cover' page: " + e);
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
			throw new Exception("Error while clicking 'Next' button of New SpecifiedItem Within Home in 'Add Contents Cover' page: " + e);
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
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAddAnotherItemOutsideHomeYes);
				//btnAddAnotherItemOutsideHomeYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAddAnotherItemOutsideHomeNo);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAddAnotherItemOutsideHomeNo);
				//btnAddAnotherItemOutsideHomeNo.click();
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
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAddAnotherItemWithinHomeYes);
				//btnAddAnotherItemWithinHomeYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAddAnotherItemWithinHomeNo);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAddAnotherItemWithinHomeNo);
				//btnAddAnotherItemWithinHomeNo.click();
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
			String elementString = cssDeleteAddedItemInWithinHome + ItemRow;
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
			String elementString = cssEditAddedItemInOutsideHome + ItemRow;
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
	public boolean verifyDeleteAndEditBtnInWithinHome(String itemName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {

			boolean btnFound = false;
			String deviceRow = getAddedItemRowInWithinHome(itemName, extentedReport, false);

			if (deviceRow == null) {
				//				throw new Exception(itemName + " - Added Item is not found");
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
				//				throw new Exception(itemName + " - Added Item is not found");
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
				WaitUtils.waitForElement(driver, btnNoDeleteItemWithinHome);
				GenericUtils.clickElementJS(driver, btnNoDeleteItemWithinHome);
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
	 * To enter Home Contents Value
	 * 
	 * @param valueOfHomeContents
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterTotalValueOfYourHomeContents(String valueOfHomeContents, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldTotalValueOfYourHomeContents);
			fldTotalValueOfYourHomeContents.clear();
			fldTotalValueOfYourHomeContents.sendKeys(valueOfHomeContents);
			Log.message(valueOfHomeContents + " - Home Contents Value is enterd on Add Contents cover", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Home Contents Value on Add Buildings cover: " + e);
		}

	}

	/**
	 * To enter Precious Or Costly Items value
	 * 
	 * @param PreciousOrCostlyItems
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterTotalValueOfPreciousOrCostlyItems(String valueOfPreciousOrCostlyItems, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldTotalValueOfPreciousOrCostlyItems);
			fldTotalValueOfPreciousOrCostlyItems.clear();
			fldTotalValueOfPreciousOrCostlyItems.sendKeys(valueOfPreciousOrCostlyItems);
			Log.message(valueOfPreciousOrCostlyItems + " - Precious Or Costly Items Value is enterd on Add Contents cover", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Precious Or Costly Items Value on Add Contents Cover: " + e);
		}

	}

	/**
	 * To enter Most Expensive Item value
	 * 
	 * @param ValueOfMostExpensiveItem
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterValueOfMostExpensiveItem(String valueOfMostExpensiveItem, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldValueOfMostExpensiveItem);
			fldValueOfMostExpensiveItem.clear();
			fldValueOfMostExpensiveItem.sendKeys(valueOfMostExpensiveItem);
			Log.message(valueOfMostExpensiveItem + " - Most Expensive Item Value is enterd on Add Contents cover", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Most Expensive Item Value on Add Buildings cover: " + e);
		}

	}

	/**
	 * To select Type Of Home Contents Cover Required
	 * 
	 * @param TypeOfHomeContentsCoverRequired
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectTypeOfHomeContentsCoverRequired(String typeOfHomeContentsCoverRequired, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnTypeOfHomeContentsCoverRequired);
			Select coverSelect = new Select(drpdwnTypeOfHomeContentsCoverRequired);
			coverSelect.selectByVisibleText(typeOfHomeContentsCoverRequired);
			WaitUtils.waitForElement(driver, drpdwnTotalExcessRequired);
			Log.message("Selected Cover Type : " + typeOfHomeContentsCoverRequired, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting Type Of Home Contents Cover Required on Add Building cover page : " + e);
		}
	}

	/**
	 * To select Type Of Home Contents Cover Required
	 * 
	 * @param TypeOfHomeContentsCoverRequired
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectTotalExcessRequired(String totalExcessRequired, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnTotalExcessRequired);
			Select coverSelect = new Select(drpdwnTotalExcessRequired);
			coverSelect.selectByVisibleText(totalExcessRequired);
			WaitUtils.waitForElement(driver, btncontentsSumRepresentReplacementAmountYes);
			Log.message("Selected Cover Type : " + totalExcessRequired, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting Type Of Home Contents Cover Required on Add Building cover page : " + e);
		}
	}

	/**
	 * To click yes/No button for full Contents Sum Represent Full Replacement Amount
	 * 
	 * @param fullContentsSumRepresentFullReplacementAmount
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoBtnContentsSumRepresentReplacementAmount(String contentsSumRepresentReplacementAmount, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (contentsSumRepresentReplacementAmount) {
			case "Yes":
				WaitUtils.waitForElement(driver, btncontentsSumRepresentReplacementAmountYes);
				GenericUtils.clickElementJS(driver, btncontentsSumRepresentReplacementAmountYes);
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btncontentsSumRepresentReplacementAmountNo);
				GenericUtils.clickElementJS(driver, btncontentsSumRepresentReplacementAmountNo);
				WaitUtils.waitForSpinner(driver);
				break;
			}
			WaitUtils.waitForPageLoad(driver);
			Log.message("Clicked on " + contentsSumRepresentReplacementAmount + " button in Add Contents cover", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking button in Add Contents cover : " + e);
		}
	}

	/**
	 * To click yes/No button for Previously Held Home Contents Insurance
	 * 
	 * @param PreviouslyHeldHomeContentsInsurance
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoBtnPreviouslyContentsInsurances(String PreviouslyHeldHomeContentsInsurance, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (PreviouslyHeldHomeContentsInsurance) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnPreviouslyContentsInsuranceYes);
				GenericUtils.clickElementJS(driver, btnPreviouslyContentsInsuranceYes);
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnPreviouslyContentsInsuranceNo);
				GenericUtils.clickElementJS(driver, btnPreviouslyContentsInsuranceNo);
				WaitUtils.waitForSpinner(driver);
				break;
			}
			WaitUtils.waitForPageLoad(driver);
			Log.message("Clicked on " + PreviouslyHeldHomeContentsInsurance + " button in Add Contents cover", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking button in Add Contents cover : " + e);
		}
	}

	/**
	 * To select Name Of Previous Insurer
	 * 
	 * @param Name Of Previous Insurer Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectNameOfPreviousInsurer(String nameOfPreviousInsurer, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnNameOfPreviousInsurer);
			Select coverSelect = new Select(drpdwnNameOfPreviousInsurer);
			coverSelect.selectByVisibleText(nameOfPreviousInsurer);
			WaitUtils.waitForElement(driver, fldPolicyNumberOfPreviousInsurance);
			Log.message("Selected Cover Type : " + nameOfPreviousInsurer, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting Name of Previous Insurer Value on Add Building cover page : " + e);
		}
	}

	/**
	 * To enter Previous Insurance Policy Number
	 * 
	 * @param Previous Insurance Policy Number
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterPolicyNumberOfPreviousInsurance(String numberOfPreviousInsurance, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldPolicyNumberOfPreviousInsurance);
			fldPolicyNumberOfPreviousInsurance.clear();
			fldPolicyNumberOfPreviousInsurance.sendKeys(numberOfPreviousInsurance);
			Log.message(numberOfPreviousInsurance + " - Vehicle Registration Number is enterd in the ChangeMyCar Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering policy Number Of Previous Insurance on Add Building cover: " + e);
		}

	}

	/**
	 * To enter Expire Date Of Previous Insurance
	 * 
	 * @param Expire Date Of Previous Insurance Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterExpiryDateOfPreviousInsurance(String expiryDateOfPreviousInsurance, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldExpiryDateOfPreviousInsurance);
			fldExpiryDateOfPreviousInsurance.clear();
			Thread.sleep(1000);
			fldExpiryDateOfPreviousInsurance.click();
			fldExpiryDateOfPreviousInsurance.sendKeys(expiryDateOfPreviousInsurance);
			fldExpiryDateOfPreviousInsurance.click();
			drpdwnNoOfYearsWithoutClaiming.click();
			WaitUtils.waitForSpinner(driver);
			fldExpiryDateOfPreviousInsurance.sendKeys(Keys.TAB);
			Log.message("Entered the Date date for Expiry Date Of Previous Insurance : " + expiryDateOfPreviousInsurance, driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering date for Expiry Date Of Previous Insurance : " + e);
		}
	}

	/**
	 * To select Pay For Your Previous Insurance
	 * 
	 * @param Pay For Your Previous Insurance Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectPayForYourPreviousInsurance(String payForYourPreviousInsurance, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnPayForYourPreviousInsurance);
			Select coverSelect = new Select(drpdwnPayForYourPreviousInsurance);
			coverSelect.selectByVisibleText(payForYourPreviousInsurance);
			WaitUtils.waitForElement(driver, drpdwnNoOfYearsWithoutClaiming);
			Log.message("Selected Cover Type : " + payForYourPreviousInsurance, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting Name of Previous Insurer Value on Add Building cover page : " + e);
		}
	}

	/**
	 * To select No Of Years Without Claiming 
	 * 
	 * @param No Of Years Without Claiming Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectContentsInsuranceWithoutClaiming(String noOfYearsWithoutClaimingValue, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnNoOfYearsWithoutClaiming);
			Select coverSelect = new Select(drpdwnNoOfYearsWithoutClaiming);
			coverSelect.selectByVisibleText(noOfYearsWithoutClaimingValue);
			WaitUtils.waitForElement(driver, btnNextContentsInformation);
			Log.message("Selected Cover Type : " + noOfYearsWithoutClaimingValue, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting Buildings Insurance With out Claiming on Add Contents cover page : " + e);
		}
	}

	/**
	 * To enter Previous Insurance Policy Number
	 * 
	 * @param Previous Insurance Policy Number
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterCashKeptInYourHomeToBeInsured(String cashInYourHomeToBeInsured, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldCashKeptInYourHomeToBeInsured);
			fldCashKeptInYourHomeToBeInsured.clear();
			fldCashKeptInYourHomeToBeInsured.sendKeys(cashInYourHomeToBeInsured);
			Log.message(cashInYourHomeToBeInsured + " - Cash Kept In Your Home To Be Insured is enterd in the Add Contents cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Cash Kept In Your Home To Be Insured in the Add Contents cover Page: " + e);
		}

	}

	/**
	 * To enter Credit Cards To Be Insured
	 * 
	 * @param creditCardsToBeInsured
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void entercreditCardsToBeInsured(String creditCardsToBeInsured, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldCreditCardsToBeInsured);
			fldCreditCardsToBeInsured.clear();
			fldCreditCardsToBeInsured.sendKeys(creditCardsToBeInsured);
			Log.message(creditCardsToBeInsured + " - Credit Cards To Be Insured is enterd in the Add Contents cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Credit Cards To Be Insured in the Add Contents cover Page: " + e);
		}

	}

	/**
	 * To enter Frozen Food To Be Insured
	 * 
	 * @param frozenFoodToBeInsured
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterfrozenFoodToBeInsured(String frozenFoodToBeInsured, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldFrozenFoodToBeInsured);
			fldFrozenFoodToBeInsured.clear();
			fldFrozenFoodToBeInsured.sendKeys(frozenFoodToBeInsured);
			Log.message(frozenFoodToBeInsured + " - Frozen Food To Be Insured is enterd in the Add Contents cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Frozen Food To Be Insured in the Add Contents cover Page: " + e);
		}

	}

	/**
	 * To click Next button Additional cover
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextAdditionalCovers(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnNextAdditionalCovers);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextAdditionalCovers);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Next' button of Additional cover in Add Contents cover page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking on 'Next' button of Additional cover in Add Contents cover page: " + e);
		}
	}

	/**
	 * To click Next button Additional cover
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextContentsInformation(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnNextContentsInformation);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextContentsInformation);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Next' button of Contents information in Add Contents cover page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking on 'Next' button of Contents information in Add Contents cover page: " + e);
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
			WaitUtils.waitForSpinner(driver);
			enterItemValueOfSpecifiedItemsWithinHome(itemValue,extentedReport,true);
			WaitUtils.waitForSpinner(driver);
			enterDescriptionOfItemOfSpecifiedItemsWithinHome(itemDecsription, extentedReport,true);
			WaitUtils.waitForSpinner(driver);
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
			WaitUtils.waitForSpinner(driver);
			enterItemValueOfSpecifiedItemsOutsideHome(itemValue,extentedReport,true);
			WaitUtils.waitForSpinner(driver);
			enterDescriptionOfItemOfSpecifiedItemsOutsideHome(itemDecsription, extentedReport,true);
			WaitUtils.waitForSpinner(driver);
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

	public boolean VerfiyPreviouslyContentsInsurancesQeustions(ExtentTest extentedReport) throws Exception
	{
		try
		{             
			if(drpdwnNameOfPreviousInsurer.isDisplayed()){
				return false;
			}
			else{
				return true;
			}

		}
		catch(Exception e)
		{      
			throw new Exception("Error while Verfiying the Previously Contents Insurances Qeustions : " + e.getMessage());
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
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", edit);
			//			.click();
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
	 * Editing Add Item Outsied Home
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
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", edit);
			//.click();
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
			//			((JavascriptExecutor)driver).executeScript("window.scrollTo(0,0);");
			WaitUtils.waitForElement(driver,delete);
			//			delete.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", delete);
			WaitUtils.waitForSpinner(driver);
			clickYesOrNoBtnToDeleteItemInWithinHome(deleteItem, extenteReport, true);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Yes Add Item", driver, extentedReport);

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
	 * To verify Expand And Collapse The Acadians
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void verifyExpandAndCollapseTheAcadians(String addAnotherItemInOutsideHome, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			editPreviousQuestions(addAnotherItemInOutsideHome, extentedReport, true);
			contentsInformation.click();
			WaitUtils.waitForElement(driver, fldTotalValueOfYourHomeContents);
			Log.message("Contents information section is expanded");
			clickNextContentsInformation(extentedReport, true);
			WaitUtils.waitForElement(driver, fldCashKeptInYourHomeToBeInsured);
			Log.message("Additional covers section is expanded");
			clickNextAdditionalCovers(extentedReport, true);
			WaitUtils.waitForElement(driver, btnAddAnotherItemWithinHomeYes);
			Log.message("Additional covers section is expanded");
		} catch (Exception e) {
			throw new Exception("Error while Expand And Collapse The Acadians : " + e);
		}
	}

	/**
	 * To verify Expand And Collapse The Acadians
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void editPreviousQuestions(String addAnotherItemInOutsideHome, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			specifieItems.click();
			WaitUtils.waitForElement(driver, btnAddAnotherItemWithinHomeYes);
			clickYesOrNoAddAnotherItemInWithinHome(addAnotherItemInOutsideHome, extentedReport, true);
			clickSaveItemNewSpecifiedItemWithinHome(extentedReport,true);
			clickNextNewSpecifiedItemWithinHome(extentedReport, true);

		} catch (Exception e) {
			throw new Exception("Error while Expand And Collapse The Acadians : " + e);
		}
	}

	/**
	 * To verify Column headers within home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void headersVerificationForTableInWithinHome(ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String type = headerTypeInWithinHome.getText();
			String value = headerValueInWithinHome.getText();
			String Description = headerDescriptionInWithinHome.getText();

			if(type == "Type" && value == "Value" && Description == "Description"){
				Log.message("Verification For Table headers In Within Home is done");
			}

		} catch (Exception e) {
			throw new Exception("Error while Verifing For Table headers In Within Home : " + e);
		}
	}

	/**
	 * To verify Column headers Outside home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void headersVerificationForTableInOutsideHome(ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String type = headerTypeInOutsideHome.getText();
			String value = headerValueInOutsideHome.getText();
			String Description = headerDescriptionInOutsideHome.getText();

			if(type == "Type" && value == "Value" && Description == "Description"){
				Log.message("Verification For Table headers In Outside Home is done");
			}

		} catch (Exception e) {
			throw new Exception("Error while Verifing For Table headers In Outside Home : " + e);
		}
	}

	/**
	 * verify Policy Detail In Add Contents cover Section
	 * 
	 * @param expectedPolicyDetail
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyDetailInAddContentsDetailSection(HashMap<String, String> expectedAddBuildingsDetails,
			ExtentTest extentedReport) throws Exception {
		try {
			HashMap<String, String> AddContentsDetails = getDetailInAddContentsDetailSection(extentedReport);
			return GenericUtils.compareTwoHashMap(AddContentsDetails, expectedAddBuildingsDetails,driver, extentedReport, true);
		} catch (Exception e) {
			throw new Exception("Error while verifying Policy Detail in Add Contests cover section : " + e);
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
	public HashMap<String, String> getDetailInAddContentsDetailSection(ExtentTest extentedReport)
			throws Exception {
		try {
			HashMap<String, String> AddContentsDetails = new HashMap<String, String>();

			// Value Of Your Home Contents
			String displayedValueOfYourHomeContents = fldTotalValueOfYourHomeContents.getAttribute("value");
			AddContentsDetails.put("ValueOfHomeContents", displayedValueOfYourHomeContents);

			// Value Of Precious Or Costly Items
			String displayedValueOfPreciousOrCostlyItems = fldTotalValueOfPreciousOrCostlyItems.getAttribute("value");
			AddContentsDetails.put("ValueOfPreciousOrCostlyItems", displayedValueOfPreciousOrCostlyItems);

			// Value Of Precious Or Costly Items
			String displayedValueOfMostExpensiveItem = fldValueOfMostExpensiveItem.getAttribute("value");
			AddContentsDetails.put("ValueOfMostExpensiveItem", displayedValueOfMostExpensiveItem);

			// Total Name Of Previous Insurance
			Select selectTypeOfHomeContentsCoverRequired = new Select(drpdwnTypeOfHomeContentsCoverRequired);
			WebElement optionTypeOfHomeContentsCoverRequired = selectTypeOfHomeContentsCoverRequired.getFirstSelectedOption();
			String displayedTypeOfHomeContentsCoverRequired = optionTypeOfHomeContentsCoverRequired.getText();
			AddContentsDetails.put("TypeOfHomeContentsCoverRequired", displayedTypeOfHomeContentsCoverRequired);		

			// Total Excess Value
			Select selectExcessValue = new Select(drpdwnTotalExcessRequired);
			WebElement optionExcessValue = selectExcessValue.getFirstSelectedOption();
			String displayedExcessValue = optionExcessValue.getText();
			AddContentsDetails.put("ExcessValue", displayedExcessValue);

			// Total Name Of Previous Insurance
			Select selectNameOfPreviousInsurance = new Select(drpdwnNameOfPreviousInsurer);
			WebElement optionNameOfPreviousInsurance = selectNameOfPreviousInsurance.getFirstSelectedOption();
			String displayedNameOfPreviousInsurance = optionNameOfPreviousInsurance.getText();
			AddContentsDetails.put("NameOfPreviousInsurer", displayedNameOfPreviousInsurance);

			// Total Number Of Previous Insurance
			String displayedNumberOfPreviousInsurance = fldPolicyNumberOfPreviousInsurance.getAttribute("value");
			AddContentsDetails.put("NumberOfPreviousInsurance", displayedNumberOfPreviousInsurance);

			// Total Expiry Date Of Previous Insurance
			String displayedExpiryDateOfPreviousInsurance = fldExpiryDateOfPreviousInsurance.getAttribute("value");
			AddContentsDetails.put("ExpiryDateOfPreviousInsurance", displayedExpiryDateOfPreviousInsurance);

			// Total Pay For Your Previous Insurance
			Select selectPayForYourPreviousInsurance = new Select(drpdwnPayForYourPreviousInsurance);
			WebElement optionPayForYourPreviousInsurance = selectPayForYourPreviousInsurance.getFirstSelectedOption();
			String displayedPayForYourPreviousInsurance = optionPayForYourPreviousInsurance.getText();
			AddContentsDetails.put("PayForYourPreviousInsurance", displayedPayForYourPreviousInsurance);

			// Total No Of Years Without Claiming Value
			Select selectnoOfYearsWithoutClaimingValue = new Select(drpdwnNoOfYearsWithoutClaiming);
			WebElement optionnoOfYearsWithoutClaimingValue = selectnoOfYearsWithoutClaimingValue.getFirstSelectedOption();
			String displayednoOfYearsWithoutClaimingValue = optionnoOfYearsWithoutClaimingValue.getText();
			AddContentsDetails.put("NoOfYearsWithoutClaimingValue", displayednoOfYearsWithoutClaimingValue);

			clickNextContentsInformation(extentedReport, true);

			// Value Of Your Home Contents
			String displayedCashKeptInYourHomeToBeInsured = fldCashKeptInYourHomeToBeInsured.getAttribute("value");
			AddContentsDetails.put("CashInYourHomeToBeInsured", displayedCashKeptInYourHomeToBeInsured);

			// Value Of Your Home Contents
			String displayedCreditCardsToBeInsured = fldCreditCardsToBeInsured.getAttribute("value");
			AddContentsDetails.put("CreditCardsToBeInsured", displayedCreditCardsToBeInsured);

			// Value Of Your Home Contents
			String displayedFrozenFoodToBeInsured = fldFrozenFoodToBeInsured.getAttribute("value");
			AddContentsDetails.put("FrozenFoodToBeInsured", displayedFrozenFoodToBeInsured);

			clickNextAdditionalCovers(extentedReport, true);

			Set<String> keys = AddContentsDetails.keySet();
			for (String key : keys) {
				Log.message("Displayed Value of " + key + " is : " + AddContentsDetails.get(key), extentedReport);
			}

			return AddContentsDetails;

		} catch (Exception e) {
			throw new Exception("Error while getting Detail from Policy detail Section : " + e);
		}
	}

	/**
	 * hover On Help Icon Of value Of Home Contents
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfvalueOfHomeContents(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpvalueOfHomeContents);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpvalueOfHomeContents.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpvalueOfHomeContents);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTotalExcessRequired).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpvalueOfHomeContents);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon value Of Home Contents Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconvalueOfHomeContentsPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpvalueOfHomeContents);
			if (GenericUtils.verifyWebElementTextContains(popHelpvalueOfHomeContents,
					POPUP_MSG_HELPICON_Value_Of_Home_Contents)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of value Of Precious Or Costly Items
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfvalueOfPreciousOrCostlyItems(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpvalueOfPreciousOrCostlyItems);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpvalueOfPreciousOrCostlyItems.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpvalueOfPreciousOrCostlyItems);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTotalExcessRequired).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpvalueOfPreciousOrCostlyItems);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Value Of Precious Or Costly Items Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconvalueOfPreciousOrCostlyItemsPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpvalueOfPreciousOrCostlyItems);
			if (GenericUtils.verifyWebElementTextContains(popHelpvalueOfPreciousOrCostlyItems,
					POPUP_MSG_HELPICON_Value_Of_Precious_Or_Costly_Items)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of value Of Most Expensive Item
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfvalueOfMostExpensiveItem(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpvalueOfMostExpensiveItem);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpvalueOfMostExpensiveItem.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpvalueOfMostExpensiveItem);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTotalExcessRequired).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpvalueOfMostExpensiveItem);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon value Of Most Expensive Item Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconvalueOfMostExpensiveItemPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpvalueOfMostExpensiveItem);
			if (GenericUtils.verifyWebElementTextContains(popHelpvalueOfMostExpensiveItem,
					POPUP_MSG_HELPICON_value_Of_Most_Expensive_Item)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of type Of Home Contents Cover Required
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOftypeOfHomeContentsCoverRequired(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelptypeOfHomeContentsCoverRequired);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelptypeOfHomeContentsCoverRequired.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelptypeOfHomeContentsCoverRequired);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTotalExcessRequired).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelptypeOfHomeContentsCoverRequired);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon type Of Home Contents Cover Required Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIcontypeOfHomeContentsCoverRequiredPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelptypeOfHomeContentsCoverRequired);
			if (GenericUtils.verifyWebElementTextContains(popHelptypeOfHomeContentsCoverRequired,
					POPUP_MSG_HELPICON_Type_Of_Home_Contents_Cover_Required)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of total Excess Required
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOftotalExcessRequired(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelptotalExcessRequired);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelptotalExcessRequired.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelptotalExcessRequired);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTotalExcessRequired).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelptotalExcessRequired);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon total Excess Required Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIcontotalExcessRequiredPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelptotalExcessRequired);
			if (GenericUtils.verifyWebElementTextContains(popHelptotalExcessRequired,
					POPUP_MSG_HELPICON_Total_Excess_Required)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of total Excess Required
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfcontentsSumRepresentReplacementAmount(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpcontentsSumRepresentReplacementAmount);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpcontentsSumRepresentReplacementAmount.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpcontentsSumRepresentReplacementAmount);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTotalExcessRequired).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpcontentsSumRepresentReplacementAmount);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon total Excess Required Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconcontentsSumRepresentReplacementAmountPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpcontentsSumRepresentReplacementAmount);
			if (GenericUtils.verifyWebElementTextContains(popHelpcontentsSumRepresentReplacementAmount,
					POPUP_MSG_HELPICON_Contents_Sum_Represent_Replacement_Amount)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of previously Contents Insurance
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfpreviouslyContentsInsurance(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelppreviouslyContentsInsurance);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelppreviouslyContentsInsurance.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelppreviouslyContentsInsurance);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTotalExcessRequired).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelppreviouslyContentsInsurance);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon previously Contents Insurance Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconpreviouslyContentsInsurancePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelppreviouslyContentsInsurance);
			if (GenericUtils.verifyWebElementTextContains(popHelppreviouslyContentsInsurance,
					POPUP_MSG_HELPICON_Previously_Contents_Insurance)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of name Of Previous Insurer
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfnameOfPreviousInsurer(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpnameOfPreviousInsurer);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpnameOfPreviousInsurer.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpnameOfPreviousInsurer);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTotalExcessRequired).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpnameOfPreviousInsurer);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon name Of Previous Insurer Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconnameOfPreviousInsurerPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpnameOfPreviousInsurer);
			if (GenericUtils.verifyWebElementTextContains(popHelpnameOfPreviousInsurer,
					POPUP_MSG_HELPICON_Name_Of_Previous_Insurer)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
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
	public void hoverOnHelpIconOfPolicyNumberOfPreviousInsurance(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPolicyNumberOfPreviousInsurance);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpPolicyNumberOfPreviousInsurance.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPolicyNumberOfPreviousInsurance);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPolicyNumberOfPreviousInsurance).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPolicyNumberOfPreviousInsurance);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
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
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
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
	public void hoverOnHelpIconOfExpiryDateOfPreviousInsurance(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpExpiryDateOfPreviousInsurance);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpExpiryDateOfPreviousInsurance.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpExpiryDateOfPreviousInsurance);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpExpiryDateOfPreviousInsurance).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpExpiryDateOfPreviousInsurance);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
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
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
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
	public void hoverOnHelpIconOfPayForYourPreviousInsurance(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPayForYourPreviousInsurance);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpPayForYourPreviousInsurance.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPayForYourPreviousInsurance);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPayForYourPreviousInsurance).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPayForYourPreviousInsurance);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
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
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
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
	public void hoverOnHelpIconOfNoOfYearsWithoutClaiming(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpNoOfYearsWithoutClaiming);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpNoOfYearsWithoutClaiming.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpNoOfYearsWithoutClaiming);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpNoOfYearsWithoutClaiming);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
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
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
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
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpAddContentsCover.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpAddContentsCover);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpAddContentsCover);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
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
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of cash In Your Home To Be Insured
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfcashInYourHomeToBeInsured(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpcashInYourHomeToBeInsured);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpcashInYourHomeToBeInsured.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpcashInYourHomeToBeInsured);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpcashInYourHomeToBeInsured);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon cash In Your Home To Be Insured Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconcashInYourHomeToBeInsuredPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpcashInYourHomeToBeInsured);
			if (GenericUtils.verifyWebElementTextContains(popHelpcashInYourHomeToBeInsured,
					POPUP_MSG_HELPICON_Cash_In_Your_Home_To_Be_Insured)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of credit Cards To Be Insured
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfcreditCardsToBeInsured(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpcreditCardsToBeInsured);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpcreditCardsToBeInsured.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpcreditCardsToBeInsured);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpcreditCardsToBeInsured);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon credit Cards To Be Insured Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconcreditCardsToBeInsuredPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpcreditCardsToBeInsured);
			if (GenericUtils.verifyWebElementTextContains(popHelpcreditCardsToBeInsured,
					POPUP_MSG_HELPICON_Credit_Cards_To_Be_Insured)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of frozen Food To Be Insured
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOffrozenFoodToBeInsured(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpfrozenFoodToBeInsured);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			//iconHelpfrozenFoodToBeInsured.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpfrozenFoodToBeInsured);
			WaitUtils.waitForSpinner(driver);
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpfrozenFoodToBeInsured);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon credit Cards To Be Insured Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconfrozenFoodToBeInsuredPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpfrozenFoodToBeInsured);
			if (GenericUtils.verifyWebElementTextContains(popHelpfrozenFoodToBeInsured,
					POPUP_MSG_HELPICON_Frozen_Food_To_Be_Insured)) {
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
			WaitUtils.waitForelementToBeClickable(driver, iconHelpItemvalueNewSpecifiedItemOutsideHome, "");
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

	/**
	 * verify Maximum field length
	 * 
	 * @param Approximate value of cash kept in your home to be insured
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyMaximumFieldLength(String maxFieldValue, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		boolean isAvailable = false;
		try {
			if (fldCashKeptInYourHomeToBeInsured.getAttribute("maxlength").equalsIgnoreCase(maxFieldValue)) {
				return isAvailable = true;
			} else if (fldCreditCardsToBeInsured.getAttribute("maxlength").equalsIgnoreCase(maxFieldValue)) {
				return isAvailable = true;
			} else if (fldFrozenFoodToBeInsured.getAttribute("maxlength").equalsIgnoreCase(maxFieldValue)) {
				return isAvailable = true;
			} else if (fldTotalValueOfYourHomeContents.getAttribute("maxlength").equalsIgnoreCase(maxFieldValue)) {
				return isAvailable = true;
			} else if (fldTotalValueOfPreciousOrCostlyItems.getAttribute("maxlength").equalsIgnoreCase(maxFieldValue)) {
				return isAvailable = true;
			} else if (fldValueOfMostExpensiveItem.getAttribute("maxlength").equalsIgnoreCase(maxFieldValue)) {
				return isAvailable = true;
			} else if (fldItemvalueNewSpecifiedItemWithinHome.getAttribute("maxlength").equalsIgnoreCase(maxFieldValue)) {
				return isAvailable = true;
			} else if (fldItemvalueNewSpecifiedItemOutsideHome.getAttribute("maxlength")
					.equalsIgnoreCase(maxFieldValue)) {
				return isAvailable = true;
			}

			else {
				Log.message("Error while verifying field strength ", driver, extentedReport, screenshot);
				return isAvailable;
			}

		} catch (Exception e) {
			throw new Exception("Error while verifying field strength : " + e);
		}
	}

}