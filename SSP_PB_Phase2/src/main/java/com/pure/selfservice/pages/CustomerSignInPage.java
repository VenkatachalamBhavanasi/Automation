package com.pure.selfservice.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.generic.support.Log;
import com.generic.support.StopWatch;
import com.generic.utils.ElementLayer;
import com.generic.utils.GenericUtils;
import com.generic.utils.WaitUtils;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * CustomerSignInPage consists login as a User with the their credentials (Email
 * Address, password) *
 */
public class CustomerSignInPage extends LoadableComponent<CustomerSignInPage> {

	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	public final String ALERT_ERR_FRAUD_MSG = "Your online account is locked please contact us for assistance.";
	public final String LOGIN_ERROR_MSG = "Username / Password Incorrect.";

	@FindBy(css = "#C2__email_address")
	WebElement fldEmailAddress;

	@FindBy(css = "#C2__password")
	WebElement fldPassword;

	@FindBy(css = "#C2__sign_in")
	WebElement btnCustomerSignIn;

	@FindBy(css = "button[title='Cancel']")
	WebElement btnCancel;

	@FindBy(css = "#C2__email_address_ERRORMESSAGE")
	WebElement txtMsgInvalidEmailAddress;

	@FindBy(css = "#C2__password_ERRORMESSAGE")
	WebElement txtMsgInvalidPassword;

	@FindBy(css = ".custom-alert.alert-danger .errorClass")
	WebElement txtMsgInvalidCredential;

	@FindBy(css = "[class='checkbox checkbox-info checkbox-inline']")
	WebElement chkRememberMyEmailAddress;

	@FindBy(css = "a[title='Reset my password'] span")
	WebElement lnkReserMyPassword;

	@FindBy(css = "div[id='FMT_44D826E3438ABF7A108528'].alert-danger")
	WebElement txtErrorAlert;

	/**
	 * 
	 * Constructor class for CustomerSignInPage Here we initializing the driver for
	 * page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public CustomerSignInPage(WebDriver driver, ExtentTest report) {

		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		WaitUtils.waitForPageLoad(driver);

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && !driver.getTitle().contains("Customer Sign in")) {
			Log.fail("Self service sign in page not open up. Site might be down.", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {

		isPageLoaded = true;
		WaitUtils.waitForElement(driver, btnCustomerSignIn);
	}

	/**
	 * To enter Email Address
	 * 
	 * @param emailAddress
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterEmailAddress(String emailAddress, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldEmailAddress);
			fldEmailAddress.clear();
			fldEmailAddress.sendKeys(emailAddress);
			Log.message("Entered the Email Address : " + emailAddress, driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Email Address : " + e);
		}
	}

	/**
	 * To Enter password
	 * 
	 * @param password
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterPassword(String password, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldPassword);
			fldPassword.clear();
			fldPassword.sendKeys(password);
			fldPassword.click();
			Log.message("Entered the Password : " + password, driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering password : " + e);
		}
	}

	/**
	 * Click SignIn button on login page
	 * 
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void clickSignInButton(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			final long startTime = StopWatch.startTime();
			WaitUtils.waitForElement(driver, btnCustomerSignIn);
			btnCustomerSignIn.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked Sign in button on login page ", driver, extentedReport, screenshot);
			Log.event("Clicked Sign in button on login page", StopWatch.elapsedTime(startTime));
		} catch (Exception e) {
			throw new Exception("Error while clicking Sign in button : " + e);
		}
	}

	/**
	 * Login to the SS portal with the given credential.
	 * 
	 * @param emailAddress
	 * @param password
	 * @param extentedReport
	 * @param screenshot
	 * @return the <code>CustDashboardPage</code> object, or null if no
	 *         <code>CustDashboardPage</code> representation is applicable
	 * @throws Exception
	 */
	public CustomerDashboardPage loginToSSCustomerDashboard(String emailAddress, String password,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		CustomerDashboardPage objCustomerDashboardPage = null;
		try {
			enterEmailAddress(emailAddress, extentedReport, screenshot);
			enterPassword(password, extentedReport, screenshot);
			clickSignInButton(extentedReport, screenshot);
			objCustomerDashboardPage = new CustomerDashboardPage(driver, extentedReport).get();
		} catch (Exception e) {
			throw new Exception("Exception while performing Signin : " + e);
		}
		return objCustomerDashboardPage;
	}

	/**
	 * To verify email address error message
	 * 
	 * @param expectedMsg
	 *            : (Please enter valid email address)
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
			if (txtMsgInvalidEmailAddress.getText().trim().equals(expectedMsg)) {
				status = true;
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Unable to locate element : " + e);
		}
	}

	/**
	 * To verify password error message
	 * 
	 * @param expectedMsg
	 *            : (Please enter your password)
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * @return boolean
	 * 
	 */

	public boolean verifyPasswordErrorMsg(String expectedMsg, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		boolean status = false;
		try {
			if (txtMsgInvalidPassword.getText().trim().equals(expectedMsg)) {
				status = true;
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Unable to locate element : " + e);
		}
	}

	/**
	 * To verify invalid credential error message
	 * 
	 * @param expectedMsg
	 *            : (Please enter your password)
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * @return boolean
	 * 
	 */

	public boolean verifyInvalidCredentialErrorMsg(String expectedMsg, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		boolean status = false;
		try {
			GenericUtils.scrollIntoView(driver, txtMsgInvalidCredential);
			if (txtMsgInvalidCredential.getText().trim().equals(expectedMsg)) {
				status = true;
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Unable to locate element : " + e);
		}
	}

	/**
	 * verify error alert message
	 * 
	 * @param msg
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyErrorAlertMsg(String msg) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextEquals(txtErrorAlert, msg);
		} catch (Exception e) {
			throw new Exception("Error while verifying alert message : " + e);
		}
	}
}
