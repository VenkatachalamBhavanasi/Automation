package com.pure.selfservice.pages;

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

/**
 * CustomerServicePage consists Sign in button to continue the customer login
 * and Forgotten your login details? link
 * 
 */
public class CustomerServicePage extends LoadableComponent<CustomerServicePage> {

	private final WebDriver driver;
	private ExtentTest extentedReport;
	private String ssURL;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	@FindBy(css = "#C2__BUT_E14D31D21EBB8E7F24944")
	WebElement btnSignIn;

	@FindBy(css = "a[title='Forgotten your login details?']")
	WebElement lnkForgottenLoginDetail;

	/**
	 * 
	 * Constructor class for CustomerServicePage Here we initializing the driver for
	 * page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 * @param url
	 *            : CustomerServicePage URL
	 */
	public CustomerServicePage(WebDriver driver, String url, ExtentTest report) {

		this.driver = driver;
		ssURL = url;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	/**
	 * 
	 * Constructor class for CustomerServicePage Here we initializing the driver for
	 * page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public CustomerServicePage(WebDriver driver, ExtentTest report) {

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

		if (isPageLoaded && !driver.getTitle().contains("Customer Self Service")) {
			Log.fail("Customer Self service page not open up. Site might be down.", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {

		isPageLoaded = true;
		driver.get(ssURL);
		WaitUtils.waitForPageLoad(driver);
		WaitUtils.waitForElement(driver, btnSignIn);

	}

	/**
	 * Click signIn button on CustomerServicePage
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public CustomerSignInPage clickSignInButton(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnSignIn);
			btnSignIn.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked Sign In button on Customer Self Service page ", driver, extentedReport, screenshot);
			return new CustomerSignInPage(driver, extentedReport).get();
		} catch (Exception e) {
			throw new Exception("Error while clicking signin button : " + e);
		}

	}

	/**
	 * Click Forgotten Your Login Details Link
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @return CustomerForgottenPassword
	 * @throws Exception
	 * 
	 */
	public CustomerForgottenPassword clickForgottenYourLoginDetailsLink(ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, lnkForgottenLoginDetail);
			lnkForgottenLoginDetail.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked Forgotten Your Login Details Link on Customer Self Service page", driver,
					extentedReport, screenshot);
			return new CustomerForgottenPassword(driver, extentedReport).get();
		} catch (Exception e) {
			throw new Exception("Error while clicking Forgotten Your Login Details Link : " + e);
		}

	}

}
