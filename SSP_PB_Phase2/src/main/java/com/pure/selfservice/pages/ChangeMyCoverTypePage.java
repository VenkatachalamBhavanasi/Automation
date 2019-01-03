package com.pure.selfservice.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.generic.support.Log;
import com.generic.utils.ElementLayer;
import com.generic.utils.WaitUtils;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * ChangeMyCoverTypePage contains Customer contact details
 * 
 */
public class ChangeMyCoverTypePage extends LoadableComponent<ChangeMyCoverTypePage> {
	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	@FindBy(css = "h2[id*='C2__C1__HEAD' i]")
	WebElement txtTitleChangeMyCoverType;

	@FindBy(css = "select[name*='TYPEOFCOVER']")
	WebElement drpdwnTypeOfCover;

	@FindBy(css = "button[title='Quote']")
	WebElement btnQuote;

	@FindBy(css = "button[title='Cancel']")
	WebElement btnCancel;

	@FindBy(css = "button[title='Add another policy change']")
	WebElement btnAddAnotherPolicyChange;

	@FindBy(css = "select[name*='VOLUNTARYEXCESS']")
	WebElement drpdwnVoluntaryExcess;

	/**
	 * 
	 * Constructor class for ChangeMyCoverTypePage Here we initializing the driver
	 * for page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public ChangeMyCoverTypePage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(txtTitleChangeMyCoverType.isDisplayed()))) {
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
			WaitUtils.waitForelementToBeClickable(driver, btnQuote, "Quote button is not found to click");
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", btnQuote);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Quote' button in 'change my cover type' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Quote' button in 'change my cover type' page : " + e);
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
					"Cancel button is not found in 'change ny cover type' page");
			btnCancel.click();
			Log.message("Clicked on Cancel button in 'change my cover type' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking cancel button in 'change my cover type' page : " + e);
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
					"Add Another policy button is not enabled");
			btnAddAnotherPolicyChange.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Add Another Policy Change' button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception(
					"Error while clicking 'Add Another Policy Change' button in 'change of Cover type' page : " + e);
		}
	}

	/**
	 * To select type of policy cover
	 * 
	 * @param covername
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectCoverType(String covername, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnTypeOfCover);
			Select coverSelect = new Select(drpdwnTypeOfCover);
			coverSelect.selectByVisibleText(covername);
			WaitUtils.waitForElement(driver, btnAddAnotherPolicyChange);
			WaitUtils.waitForElement(driver, btnQuote);
			Log.message("Selected Cover Type : " + covername, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting cover type on change my cover type page : " + e);
		}
	}

	/**
	 * To select Voluntary excess
	 * 
	 * @param volantaryexcess
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectVoluntaryExcess(String volantaryexcess, ExtentTest extentTest, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnVoluntaryExcess);
			Select voluntaryExcess = new Select(drpdwnVoluntaryExcess);
			voluntaryExcess.selectByVisibleText(volantaryexcess);
			WaitUtils.waitForElement(driver, btnAddAnotherPolicyChange);
			WaitUtils.waitForElement(driver, btnQuote);
			Log.message("Selected volantary Excess : " + volantaryexcess, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting voluntary Excess on change my cover type page : " + e);
		}
	}

}
