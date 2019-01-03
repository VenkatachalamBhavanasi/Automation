package com.pure.selfservice.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.generic.support.Log;
import com.generic.utils.ElementLayer;
import com.generic.utils.WaitUtils;
import com.relevantcodes.extentreports.ExtentTest;

public class CustomerForgottenPassword extends LoadableComponent<CustomerForgottenPassword> {
	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	@FindBy(css = "#recaptcha-anchor-label")
	WebElement txtImNotRobot;

	@FindBy(css = "#C2__p1_GRP_3D8461446F669061149905")
	WebElement mdlForgottenYourLoginDetails;

	@FindBy(css = "#C2__email-address-fgt")
	WebElement fldEmailAddress;

	@FindBy(css = "#C2__BUT_3D8461446F669061151189")
	WebElement btnResetMyPassword;

	@FindBy(css = "#C2__isGoogleRecaptchaVerified_ERRORMESSAGE")
	WebElement txtPleaseCheckTheCheckboxError;

	@FindBy(css = "#C2__email-address-fgt_ERRORMESSAGE")
	WebElement txtPleaseEnterEmailAddressErrorMsg;

	/**
	 * 
	 * Constructor class for CustomerForgottenPassword Here we initializing the
	 * driver for page factory objects. For ajax element waiting time has added
	 * while initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public CustomerForgottenPassword(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && !driver.getTitle().contains("Customer Forgotten Password")) {
			Log.fail("User is not navigated to Customer ForgottenPassword Page ", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {

		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
		WaitUtils.waitForElement(driver, btnResetMyPassword);
	}

	/**
	 * Enter EmailAddress
	 * 
	 * @param emailaddress
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterEmailAddress(String emailaddress, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldEmailAddress);
			fldEmailAddress.clear();
			fldEmailAddress.sendKeys(emailaddress);
			Log.message(emailaddress + " - email address is enterd in the field", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while Entering email address in Customer Forgotten Password page : " + e);
		}
	}

	/**
	 * click ResetMyPassword button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickResetMyPasswordBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnResetMyPassword);
			btnResetMyPassword.click();
			Log.message("Clicked Reset My Password Button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception(
					"Error while Clicking Reset My Password Button in Customer Forgotten Password page : " + e);
		}
	}

	/**
	 * To verify entered Email Address
	 * 
	 * @param emailaddress
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyEmailAddress(String emailaddress, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean stringMatched = false;

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			String displayedEmailAddress = executor.executeScript("return arguments[0].value", fldEmailAddress)
					.toString();

			if (displayedEmailAddress.contains(emailaddress)) {
				stringMatched = true;
				Log.message(emailaddress + " - email address is displayed in the email address field ", driver,
						extentedReport, screenshot);
			}
			return stringMatched;

		} catch (Exception e) {
			throw new Exception("Error while verifying email address in Customer Forgotten Password page : " + e);
		}
	}

	/**
	 * To verify email address error message
	 * 
	 * @param expectedMsg
	 *            : (Please enter your email address)
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * @return boolean
	 * 
	 */

	public boolean verifyEmailAddressErrorMsg(String expectedMsg, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		boolean status = false;
		try {
			if (txtPleaseEnterEmailAddressErrorMsg.getText().trim().equals(expectedMsg)) {
				status = true;
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while verifying Error message for email address : " + e);
		}
	}

}
