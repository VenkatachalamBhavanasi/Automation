package com.pure.selfservice.pages;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.Keys;
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
 * ChangeTheClassOfUsePage contains Customer contact details
 * 
 */
public class ChangeTheClassOfUsePage extends LoadableComponent<ChangeTheClassOfUsePage> {
	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	@FindBy(css = "#C2__C1__HEAD_E50DCEAE27413E4D2883717")
	WebElement txtTitleClassOfUse;

	@FindBy(css = "select[name*='CLASSOFUSE' i]")
	WebElement drpdwnChangeClassOfUse;

	@FindBy(css = "button[title='Cancel']")
	WebElement btnCancel;

	@FindBy(css = "button[title='Add another policy change']")
	WebElement btnAddAnotherPolicyChange;

	@FindBy(css = "button[title='Quote']")
	WebElement btnQuote;
	
	@FindBy(css = "#C2__C1__QUE_D6578A13CC75B398222129")
	WebElement drpdwnChangeAnnualPleasure;
	       
	@FindBy(css = "#C2__C1__QUE_D6578A13CC75B398223465")
	WebElement drpdwnChangeBusinessPleasure;

	/**
	 * 
	 * Constructor class for ChangePermittedDriversPage Here we initializing the
	 * driver for page factory objects. For ajax element waiting time has added
	 * while initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public ChangeTheClassOfUsePage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(WaitUtils.waitForElement(driver, txtTitleClassOfUse)))) {
			Log.fail("User is not navigated to ChangeTheClassOfUsePage ", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {
		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
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
					"Cancel button is not found in 'change class of use' page");
			btnCancel.click();
			Log.message("Clicked on Cancel button in 'change class of use' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking cancel button in 'change class of use' page : " + e);
		}
	}

	/**
	 * To select any type of Class of use
	 * 
	 * @param String
	 *            typeClassOfUse
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectClassOfUse(String typeClassOfUse, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnChangeClassOfUse);
			Select dropdown = new Select(drpdwnChangeClassOfUse);
			List<WebElement> lists = dropdown.getOptions();

			for (int i = 0; i < lists.size(); i++) {
				if (lists.get(i).getText().contains(typeClassOfUse)) {
					lists.get(i).click();
					break;
				}
			}
			Thread.sleep(2000); //required for the button to get enabled
			WaitUtils.waitForelementToBeClickable(driver, btnQuote, "Quote button is in disabled state and not clikable");
			Log.message(typeClassOfUse + " : Option is selected in ClassOfUse dropdown", driver, extentedReport,
					screenshot);

		} catch (Exception e) {
			throw new Exception("Error while selecting class of use option - " + typeClassOfUse + " : " + e);
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
			Thread.sleep(1000);
			WaitUtils.waitForElement(driver, btnAddAnotherPolicyChange);
			WaitUtils.waitForelementToBeClickable(driver, btnAddAnotherPolicyChange,
					"Add another Policy change button is not enabled");
			btnAddAnotherPolicyChange.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Add Another Policy Change' button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception(
					"Error while clicking 'Add Another Policy Change' button in 'change class of use' page : " + e);
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
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnQuote);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Quote' button in 'change class of use' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Quote' button in 'change class of use' page : " + e);
		}
	}
	
	public void selectAnnualPleasure(String mileagepleasure, String businesspleasure, ExtentTest extentedReport, boolean screenshot) throws Exception
    {
           try
           {             
                  WaitUtils.waitForElement(driver, drpdwnChangeAnnualPleasure);
                  drpdwnChangeAnnualPleasure.clear();
                  GenericUtils.sendkeys(driver, drpdwnChangeAnnualPleasure, mileagepleasure);
                  if(drpdwnChangeBusinessPleasure.isEnabled())
                  {
                        WaitUtils.waitForElement(driver, drpdwnChangeBusinessPleasure);
                        //WaitUtils.waitForSpinner(driver);
                        drpdwnChangeBusinessPleasure.clear();
                        WaitUtils.waitForSpinner(driver);
                        GenericUtils.sendkeys(driver, drpdwnChangeBusinessPleasure, businesspleasure);
                        Log.message(businesspleasure + " - Business pleasure is entered in Change annual mileage page", driver,
                                      extentedReport, screenshot);
                  }
                  //drpdwnChangeAnnualPleasure.sendKeys(Keys.TAB);
                  Log.message(mileagepleasure + " - Mileage pleasure is entered in Change annual mileage page", driver,
                               extentedReport, screenshot);
                  
           }
           catch(Exception e)
           {      
                  throw new Exception("Error while entering Mileage and business pleasure : " + e);
           }
    }
}
