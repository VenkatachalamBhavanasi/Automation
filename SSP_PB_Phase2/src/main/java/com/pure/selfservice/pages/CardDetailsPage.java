package com.pure.selfservice.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.generic.support.Log;
import com.generic.utils.ElementLayer;
import com.generic.utils.GenericUtils;
import com.generic.utils.WaitUtils;

/**
 * Card Details Page allows user to enter the Card Details for Making Annual
 * Payment
 */
public class CardDetailsPage extends LoadableComponent<CardDetailsPage> {

	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;
	
	@FindBy(css = "input[name='cardNumber']")
	WebElement txtCardNumber;

	@FindBy(css = "input#name")
	WebElement txtCardHolderAnnual;

	@FindBy(css = "select[name='expiryDate.expiryMonth']")
	WebElement selectExpiryMonth;

	@FindBy(css = "select[name='expiryDate.expiryYear']")
	WebElement selectExpiryYear;

	@FindBy(css = "input[name='securityCode']")
	WebElement txtVerification;

	@FindBy(css = "input[alt='Continue']")
	WebElement btnContinue;

	@FindBy(css = "#cardholderName")
	WebElement txtCardHoldName;

	@FindBy(css = "input[value='Pay Now']")
	WebElement btnBuy;

	@FindBy(css = "#address1")
	WebElement txtAddress1;

	@FindBy(css = "#address2")
	WebElement txtAddress2;

	@FindBy(css = "#town")
	WebElement txttown;

	@FindBy(css = "#postcode")
	WebElement txtPostCode;

	@FindBy(xpath = "tr[id='amount_payable'] td:nth-child(2)")
	WebElement txtAmountToPay;

	@FindBy(css = "p[class='field-wrapper first error']>.error-hint")
	WebElement txtCardInvalidErrorMsg;
	
	@FindBy(css = "div[id='paymentDetails'] h2[class*='desktop']>span:nth-child(1)")
	WebElement lnkVisa;
	

	/**
	 * 
	 * Constructor class for Card Details Page Here we initializing the driver for
	 * page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public CardDetailsPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && !driver.getTitle().contains("")) {
			Log.fail("User is not navigated to Card Details page", driver, extentedReport);
			
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {

		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
		WaitUtils.waitForElement(driver, txtCardNumber);
	}

	/**
	 * Enter Card number
	 * 
	 * @param testdata
	 * @param extentReport
	 * @param Screenshot
	 * @throws Exception
	 * 
	 */
	public void enterCardNumber(String cardNumber, ExtentTest extentReport, boolean Screenshot) throws Exception {
		WaitUtils.waitForElement(driver, txtCardNumber);
		try {
			txtCardNumber.clear();
			txtCardNumber.sendKeys(cardNumber);
			Log.message("Entered Card Number : " + cardNumber, driver, extentReport, Screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Card number : " + e);
		}
		WaitUtils.waitForSpinner(driver);
	}

	/**
	 * Select Expiry date
	 * 
	 * @param testdata
	 * @param extentReport
	 * @param Screenshot
	 * @throws Exception
	 * 
	 */
	public void selectExpiry(String year, ExtentTest extentReport, boolean Screenshot) throws Exception {
		try {
			selectMonth(extentReport);
			selectYear(year, extentReport);
		} catch (Exception e) {
			throw new Exception("Error while entering expiry details : " + e);
		}
		WaitUtils.waitForSpinner(driver);
	}

	/**
	 * Select Month
	 * 
	 * @param extentReport
	 * @throws Exception
	 * 
	 */
	public void selectMonth(ExtentTest extentReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, selectExpiryMonth);
			Select selectmonth = new Select(selectExpiryMonth);
			selectmonth.selectByValue("02");
			Log.message("Selected Month : 2", driver, extentReport);
		} catch (Exception e) {
			throw new Exception("Error while entering expiry month :" + e);
		}
	}

	/**
	 * Select Year
	 * 
	 * @param extentReport
	 * @param testdata
	 * @throws Exception
	 * 
	 */
	public void selectYear(String year, ExtentTest extentReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, selectExpiryYear);
			Select selectyear = new Select(selectExpiryYear);
			selectyear.selectByVisibleText(year);
			Log.message("Selected Year : " + year, driver, extentReport);
		} catch (Exception e) {
			throw new Exception("Error while entering expiry year :" + e);
		}
	}

	/**
	 * Enter CVV verification
	 * 
	 * @param extentReport
	 * @param Screenshot
	 * @throws Exception
	 * 
	 */
	public void enterVerification(ExtentTest extentReport, boolean Screenshot) throws Exception {
		try {
			txtVerification.clear();
			txtVerification.sendKeys("123");
			Log.message("Entered CVV : 123", driver, extentReport, Screenshot);

		} catch (Exception e) {
			throw new Exception("Error while entering CVV number :" + e);
		}
	}

	/**
	 * Enter Card Holder Name
	 * 
	 * @param extentReport
	 * @param Screenshot
	 * @throws Exception
	 * 
	 */
	public void enterName(String cardHolderName, ExtentTest extentReport, boolean Screenshot) throws Exception {
		try {
			txtCardHoldName.sendKeys(cardHolderName);
			Log.message("Entered Card Holder Name : " + cardHolderName, driver, extentReport, Screenshot);
		} catch (Exception e) {
			throw new Exception("Error occured while entering card holders name : " + e);
		}

	}

	/**
	 * Click Buy
	 * 
	 * @param extentReport
	 * @param Screenshot
	 * @throws Exception
	 * 
	 */
	public void clickbuy(ExtentTest extentReport, boolean Screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnBuy);
			btnBuy.click();
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForPageLoad(driver);
			Log.message("Clicked on Buy button", driver, extentReport, true);
		} catch (Exception e) {
			throw new Exception("Failed to click buy button on card details page : " + e);
		}
	}

	/**
	 * Click on Continue button
	 * 
	 * @param extentReport
	 * @return BuyQuotePage
	 * @throws Exception
	 * 
	 */
	public SuccessPage clickContinueButton(ExtentTest extentReport) throws Exception {
		try {
			btnContinue.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Continue Button", driver, extentReport);
			driver.switchTo().defaultContent();
			return new SuccessPage(driver).get();
		} catch (Exception e) {
			throw new Exception("Continue button not clicked : " + e);
		}
	}

	/**
	 * Change Address1
	 * 
	 * @param extentReport
	 * @param Screenshot
	 * @throws Exception
	 * 
	 */
	public void changeAddressOne(String Address1, ExtentTest extentReport, boolean Screenshot) throws Exception {
		try {
			GenericUtils.scrollIntoView(driver, txtAddress1);
			txtAddress1.clear();
			txtAddress1.sendKeys(Address1);
			Log.message("Address1 detail is changed in payment panel as - " + Address1, driver, extentReport,
					Screenshot);
		} catch (Exception e) {
			throw new Exception("Error while changing the Address1 : " + e);
		}

	}

	/**
	 * verify customer address
	 * 
	 * @param address
	 * @param extentReport
	 * @param Screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyCustomerAddress(String[] address, ExtentTest extentReport, boolean Screenshot)
			throws Exception {
		try {

			boolean isfilled = false;
			Integer totalElement = address.length;
			Integer elementMatched = 0;

			String addressOne = txtAddress1.getAttribute("value");
			String address2 = txtAddress2.getAttribute("value");
			String town = txttown.getAttribute("value");
			String postCode = txtPostCode.getAttribute("value").replaceAll("\\s+", "");

			if (GenericUtils.verifyStringPresentInArray(address, addressOne)) {
				elementMatched++;
				Log.message(addressOne + " - is present in the textfield of Address1", extentReport);
			}

			if (GenericUtils.verifyStringPresentInArray(address, address2)) {
				elementMatched++;
				Log.message(address2 + " - is present in the textfield of Address2", extentReport);
			}

			if (GenericUtils.verifyStringPresentInArray(address, town)) {
				elementMatched++;
				Log.message(town + " - is present in the textfield of town", extentReport);
			}

			if (GenericUtils.verifyStringPresentInArray(address, postCode)) {
				elementMatched++;
				Log.message(town + " - is present in the textfield of postCode", extentReport);
			}

			if (elementMatched == totalElement) {
				isfilled = true;
			}

			return isfilled;

		} catch (Exception e) {
			throw new Exception("Error while verifying address detail in card detail page : " + e);
		}

	}

	/**
	 * verify customer address
	 * 
	 * @param amount
	 * @param extentReport
	 * @param Screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyAmountToPay(String amount) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtAmountToPay, amount);

		} catch (Exception e) {
			throw new Exception("Error while verifying amount to pay in card detail page : " + e);
		}

	}

	/**
	 * verify customer address
	 * 
	 * @param amount
	 * @param extentReport
	 * @param Screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyCardHolderName(String cardholderName) throws Exception {
		try {

			return GenericUtils.verifyWebElementTextContains(txtCardHoldName, cardholderName);

		} catch (Exception e) {
			throw new Exception("Error while verifying CardHolde rName in card detail page : " + e);
		}

	}

	public boolean enterCardDetails(String year, String cardHolderName, String cardNumber, ExtentTest extentReport,
			boolean screenshot) throws Exception {
		boolean status = true;
		try {
			WaitUtils.waitForElement(driver, btnBuy);
			enterCardNumber(cardNumber, extentReport, true);
			enterName(cardHolderName, extentReport, true);
			selectExpiry(year, extentReport, true);
			enterVerification(extentReport, true);
			Log.message("Card details entered successfully on card details page", driver, extentReport, true);
			return status;
		} catch (Exception e) {
			Log.message("Error while entering card details on card details page" + e);
			status = false;
			return status;
		}

	}
	
}
