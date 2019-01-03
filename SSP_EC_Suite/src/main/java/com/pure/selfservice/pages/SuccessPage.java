package com.pure.selfservice.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.generic.support.Log;
import com.generic.utils.ElementLayer;
import com.generic.utils.WaitUtils;

public class SuccessPage extends LoadableComponent<SuccessPage> {

	private final WebDriver driver;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	@FindBy(css = "h1[class*='successful-message']")
	WebElement txtLblSuccessMsg;

	@FindBy(css = "button[title='Finish']")
	WebElement btnFinish;

	@FindBy(css = "#C2__HEAD_D398C4F0EF189310569874")
	WebElement txtSuccessMsg;

	@FindBy(css = "div>h1[id='C2__HEAD_D398C4F0EF189310569874']")
	WebElement lblSuccess;
	
	/**
	 * 
	 * Constructor class for Card Details Page Here we initializing the driver
	 * for page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */

	public SuccessPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && !driver.getTitle().contains("")) {
			Log.fail("User is not navigated to Card Details section in newquote page", driver);
		}
	}

	@Override
	protected void load() {

		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
		WaitUtils.waitForElement(driver, btnFinish);
	}

	public boolean getSuccessMsg(ExtentTest extentTest, boolean screenshot) throws Exception {
		boolean status = true;
		try {
			WaitUtils.waitForElement(driver, txtSuccessMsg);
			String successMsg = txtSuccessMsg.getText();
			String Success = lblSuccess.getText();
			if (Success.contains("unsuccessful")) {
				status = false;
			} else {
				Log.message("Successfully accepted MTA policy with Live status:" + successMsg, driver, extentTest, true);
			}
			
		} catch (Exception e) {
			Log.message("Error while verifying the success message on success page : " + e);
		}
		return status;	
	}

	public CustomerDashboardPage clickFinish(ExtentTest extentReport, boolean screenshot) throws Exception {
		WaitUtils.waitForElement(driver, btnFinish);
		btnFinish.click();
		WaitUtils.waitForSpinner(driver);
		return new CustomerDashboardPage(driver, extentReport).get();
	}

}
