package com.ssp.pages.uxp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.ssp.support.*;

public class ComplaintPage extends LoadableComponent<ComplaintPage> {

	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	/**********************************************************************************************
	 ********************************* WebElements of Complaint Page - begins ***************************
	 **********************************************************************************************/

	@FindBy(css = "#C2__p1_QUE_7A9224CB87C05DCB250784 label")
	WebElement labl_Ref;

	@FindBy(css = "div.main-brand")
	WebElement ECname;

	/**********************************************************************************************
	 ********************************* WebElements of Complaint Page - Ends ***************************
	 **********************************************************************************************/

	/**
	 * 
	 * Constructor class for complaint Page Here we initializing the driver for page
	 * factory objects. For ajax element waiting time has added while initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 * 
	 */

	public ComplaintPage(WebDriver driver, ExtentTest report) {
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

		if (isPageLoaded && !driver.getTitle().contains("Complaint")) {
			Log.fail("SSP Complaint Page did not open up. Site might be down.", driver,extentedReport);
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {

		isPageLoaded = true;
	}

	/**
	 * Verify Complaint page
	 * 
	 * @throws Exception
	 *             : Custom Exception Message
	 * @param extentReport
	 * @param Screenshot
	 * @return boolean
	 * 
	 */
	public boolean verifyComplaintPage(boolean Screenshot, ExtentTest extentReport) throws Exception {
		boolean status = false;
		if (WaitUtils.waitForElement(driver, labl_Ref)) {

			status = true;
			Log.message("Navigated to Complaint page", driver, extentReport, Screenshot);
		}
		return status;
	}

	/**
	 * Verify banner title as Engagement centre
	 * 
	 * @param extentedReport
	 * @throws Exception
	 *             : Custom Exception Message
	 * @return boolean
	 * 
	 */

	public boolean ECbannertitleCheck(ExtentTest extentedReport) throws Exception {
		try {
			return Utils.verifyWebElementTextEquals(ECname, "Engagement Centre");
		}

		catch (Exception e) {
			throw new Exception("Exception while getting banner title" + e);
		}
	}

}
