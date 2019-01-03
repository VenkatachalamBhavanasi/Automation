package com.pure.selfservice.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

/**
 * CustomerChangePasswordPage contains Customer change password details
 * 
 */
public class CustomerChangePasswordPage extends LoadableComponent<CustomerChangePasswordPage> {
	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;
	
	public final String OLD_PWD_INCORRECT_ERR_MSG = "Old Password Is Incorrect.";
	public final String NEW_PWD_MATCH_INCORRECT_ERR_MSG = "New Passwords must match";

	@FindBy(css = "button[id='C2__change-pwd']")
	WebElement btnChangePassword;

	@FindBy(css = "input[name*='CHANGEPASSWORD[1].PASSWORD']")
	WebElement fldOldPassword;

	@FindBy(css = "#C2__QUE_38B8C532070269F718363")
	WebElement fldNewPassword;

	@FindBy(css = "#C2__QUE_38B8C532070269F718366")
	WebElement fldReenterNewPassword;

	@FindBy(css = ".errorClass.PasswordChange")
	WebElement txtInvalidPasswordMsg;

	@FindBy(css = "#C2__BUT_3D8461446F669061150942")
	WebElement btnCancel;

	@FindBy(css = "#C2__QUE_38B8C532070269F718363_ERRORMESSAGE")
	WebElement txtInvalidNewPasswordErrorMsg;

	@FindBy(css = "#C2__QUE_38B8C532070269F718366_ERRORMESSAGE")
	WebElement txtInvalidReEnterNewPasswordErrorMsg;

	@FindBy(css = "#HEAD_4C373C4E5B6278C3106478")
	WebElement txtPasswordChangedSuccessfully;

	@FindBy(css = "#C2__BUT_4C373C4E5B6278C3106480")
	WebElement btnOk;

	/**
	 * 
	 * Constructor class for CustomerChangePasswordPage Here we initializing the
	 * driver for page factory objects. For ajax element waiting time has added
	 * while initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public CustomerChangePasswordPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && !driver.getTitle().contains("Customer Change Password")) {
			Log.fail("User is not navigated to Customer ChangePassword Page ", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {

		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
		WaitUtils.waitForElement(driver, btnChangePassword);
	}

	/**
	 * Enter OldPassword
	 * 
	 * @param oldPassword
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterOldPassword(String oldPassword, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldOldPassword);
			fldOldPassword.clear();
			fldOldPassword.sendKeys(oldPassword);
			Log.message(oldPassword + " - Old Password is enterd in the Old Password field", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while Entering Old Password : " + e);
		}

	}

	/**
	 * Enter NewPassword
	 * 
	 * @param newPassword
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterNewPassword(String newPassword, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForelementToBeClickable(driver, fldNewPassword, "Field to enter new password not found");
			fldNewPassword.clear();
			fldNewPassword.sendKeys(newPassword);
			fldNewPassword.sendKeys(Keys.TAB);
			WaitUtils.waitForSpinner(driver);
			Log.message(newPassword + " - New Password is enterd in the NewPassword field", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while Entering New Password : " + e);
		}

	}

	/**
	 * Enter re-enter NewPassword
	 * 
	 * @param newPassword
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void reenterNewPassword(String newPassword, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			fldReenterNewPassword.click();
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForelementToBeClickable(driver, fldReenterNewPassword,
					"Field to re-enter new password not found");
			fldReenterNewPassword.clear();
			fldReenterNewPassword.sendKeys(newPassword);
			fldNewPassword.sendKeys(Keys.TAB);
			WaitUtils.waitForSpinner(driver);
			Log.message(newPassword + " - New Password is enterd in the Re-enter NewPassword field", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while re-entering New Password : " + e);
		}

	}

	/**
	 * click Change My Password button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickChangeMyPasswordBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnChangePassword);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", btnChangePassword);

			WaitUtils.waitForSpinner(driver);
			Log.message("ChangeMyPassword button is clicked", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking ChangeMyPassword Button : " + e);
		}

	}

	/**
	 * verify Invalid Password Error Message
	 * 
	 * @param errorMsg
	 *            - error message to verify
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyInvalidPasswordErrorMsg(String errorMsg, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean status = false;
			WaitUtils.waitForElement(driver, txtInvalidPasswordMsg);
			GenericUtils.scrollIntoView(driver, txtInvalidPasswordMsg);
			if (GenericUtils.verifyWebElementTextContains(txtInvalidPasswordMsg, errorMsg)) {
				status = true;
			}
			return status;

		} catch (Exception e) {
			throw new Exception("Error while verifying Invalid Password Error Message : " + e);
		}

	}

	/**
	 * click cancel button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickCancelBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForelementToBeClickable(driver, btnCancel, "Cancel button is not found");
			btnCancel.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Cancel button is clicked", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking Cancel Button : " + e);
		}

	}

	/**
	 * verify New Password Complexity Error Message
	 * 
	 * @param errorMsg
	 *            - error message to verify
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyNewPasswordComplexityErrorMsg(String errorMsg, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, txtInvalidNewPasswordErrorMsg);
			GenericUtils.scrollIntoView(driver, txtInvalidNewPasswordErrorMsg);
			return GenericUtils.verifyWebElementTextContains(txtInvalidNewPasswordErrorMsg, errorMsg);

		} catch (Exception e) {
			throw new Exception("Error while verifying NewPassword Complexity Error Message : " + e);
		}

	}

	/**
	 * verify Re-enter Password Complexity Error Message
	 * 
	 * @param errorMsg
	 *            - error message to verify
	 * @param extentedReport
	 * @param screenshot
	 * @return true if 'errorMsg' matches; false otherwise
	 * @throws Exception
	 * 
	 */
	public boolean verifyReenterPasswordComplexityErrorMsg(String errorMsg, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, txtInvalidReEnterNewPasswordErrorMsg);
			return GenericUtils.verifyWebElementTextContains(txtInvalidReEnterNewPasswordErrorMsg, errorMsg);

		} catch (Exception e) {
			throw new Exception("Error while verifying Re-enter Password Complexity Error Message : " + e);
		}

	}

	/**
	 * click OK button On PasswordChanged success message model
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickOkBtnOnPasswordChangedModel(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForelementToBeClickable(driver, btnOk, "Cancel button is not found");
			btnOk.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("OK button is clicked", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking OK Button : " + e);
		}

	}

}
