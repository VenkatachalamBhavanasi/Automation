package com.ssp.pages.uxp;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.ssp.support.*;

/**
 * Login page consists login as a User with the their credentials (username,
 * password) *
 */
public class LoginPage extends LoadableComponent<LoginPage> {

	private final WebDriver driver;
	private ExtentTest extentedReport;
	private String sspURL;
	private boolean isPageLoaded;
	public String btnSignOut="a#signOut";
	public String spinner = "div.spinning-on-load-bg-table-active";
	public final String ERROR_MSG_LOGIN = "We do not recognise your details. Please re-enter your credentials";
	public final String ERROR_MSG_NotDisplayed = "No error message displayed";
	/**********************************************************************************************
	 ********************************* WebElements of Login Page **********************************
	 **********************************************************************************************/

	@FindBy(css = "#C2__QUE_ED508ADA5C44080859323")
	WebElement txtUserName;
	

	@FindBy(css = "input[name='C2__CONTACTCENTRE[1].LOGIN[1].PASSWORD']")
	WebElement txtPassWord;

	@FindBy(css = "button[title='LOGIN']")
	WebElement btnSignIn;

	@FindBy(css = "#C1__ITM_5866E32A0412B1F918363")
	WebElement cmbUserDrpDwnForLogout;

	@FindBy(css = "#C2__TXT_A9DC523EA1854121283121")
	WebElement errMsgForInvalidCredential;

	@FindBy(css = "label[for='C2__QUE_ED508ADA5C44080859343_0']")
	WebElement rmbrUsername;

	/**********************************************************************************************
	 ********************************* WebElements of Login Page - Ends ***************************
	 **********************************************************************************************/

	/**
	 * 
	 * Constructor class for Login page Here we initializing the driver for page
	 * factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 * @param url
	 *            : Login Page URL
	 * @param extentedReport
	 */
	public LoginPage(WebDriver driver, String url, ExtentTest report) {

		this.driver = driver;
		this.extentedReport = report;
		sspURL = url;
		PageFactory.initElements(driver, this);
	}

	/**
	 * 
	 * Constructor class for Login page Here we initializing the driver for page
	 * factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public LoginPage(WebDriver driver,ExtentTest report) {

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

		if (isPageLoaded && !driver.getTitle().contains("Login")) {
			Log.fail("SSP Login Page did not open up. Site might be down.", driver,extentedReport);
		}
	}

	@Override
	protected void load() {

		isPageLoaded = true;
		driver.get(sspURL);
		WaitUtils.waitForPageLoad(driver);
		WaitUtils.waitForElement(driver, txtUserName);

	}

	/**
	 * Login to SSP
	 * 
	 * @param username
	 * @param password
	 * @param screenShot
	 * @param extentedReport
	 * @return Home Page
	 * @throws Exception
	 * 
	 */

	public HomePage loginToSSP(String username, String password, boolean screenShot, ExtentTest extentedReport)
			throws Exception {
		try {

			if (sspURL != null)
				Log.event("Launched SSP site:: " + sspURL);

			Log.event("Login to the SSP");
			enterUserName(username, extentedReport);
			enterPassword(password, extentedReport);
			//rmbrUsername.click();
			clickBtnSignIn(extentedReport);
			WaitUtils.waitForSpinner(driver);
			//WaitUtils.waitForPageLoad(driver);
			if (isPageLoaded && driver.getTitle().toLowerCase().contains("login")) {
				throw new Exception("Invalid Login Details");
			}

			return new HomePage(driver).get();
		} catch (Exception e) {
			throw new Exception("Error while login to application : " + e);
		}
	}

	/**
	 * Verify User Name is displayed
	 * 
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyUserNameField() throws Exception {
		try {
			return WaitUtils.waitForElement(driver, txtUserName);
		} catch (Exception e) {
			throw new Exception("Error while verifying username field : " + e);
		}
	}

	/**
	 * Verify Password is displayed
	 * 
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyPasswordField() throws Exception {
		try {
			return WaitUtils.waitForElement(driver, txtPassWord);
		} catch (Exception e) {
			throw new Exception("Error while verifying Password field : " + e);
		}
	}

	/**
	 * To enter user name
	 * 
	 * @param userName
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void enterUserName(String userName, ExtentTest extentedReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, txtUserName);
			txtUserName.clear();
			txtUserName.sendKeys(userName);
			Log.message("Entered the UserName : " + userName, driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while entering Username : " + e);
		}
	}

	/**
	 * To Enter password
	 * 
	 * @param pwd
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void enterPassword(String pwd, ExtentTest extentedReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, txtPassWord);
			txtPassWord.clear();
			txtPassWord.sendKeys(pwd);
			Log.message("Entered the Password: " + pwd, driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while entering password : " + e);
		}
	}

	/**
	 * Click signIn button on login page
	 * 
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void clickBtnSignIn(ExtentTest extentedReport) throws Exception {
		try {
			final long startTime = StopWatch.startTime();
			WaitUtils.waitForElement(driver, btnSignIn);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", btnSignIn);
			// btnSignIn.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked signIn button on login page ", driver, extentedReport);
			Log.event("Clicked signIn button on login page", StopWatch.elapsedTime(startTime));
		} catch (Exception e) {
			throw new Exception("Error while clicking signin button : " + e);
		}

	}

	/**
	 * To verify error message in login page
	 * 
	 * @param msgToVerify
	 * @throws Exception
	 * @return boolean
	 * 
	 * 
	 */
	public boolean verifyErroMessage() throws Exception {
		try {
			String message = Utils.getTextOfWebElement(errMsgForInvalidCredential, driver);
			return message != null && message.contains(ERROR_MSG_LOGIN);
		} catch (Exception e) {
			throw new Exception("Error while verifying error message in login page : " + e);
		}

	}
	/**
	 * To verify error message in login page
	 * 
	 * @param msgToVerify
	 * @throws Exception
	 * @return boolean
	 * 
	 * 
	 */
	public void Logout() throws Exception {
		try {
			
		Actions signout = new Actions(driver);
		signout.moveToElement(cmbUserDrpDwnForLogout).click().build().perform();
		cmbUserDrpDwnForLogout.findElement(By.cssSelector(btnSignOut)).click();		
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked signOut button on login page ", driver, extentedReport);
			
		} catch (Exception e) {
			throw new Exception("Error while clicking signOut button : " + e);
		}

	}
	
}