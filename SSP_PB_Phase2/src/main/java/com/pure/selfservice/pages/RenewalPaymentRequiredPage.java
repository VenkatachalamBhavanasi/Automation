package com.pure.selfservice.pages;

//import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;

//import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

//import com.gargoylesoftware.htmlunit.html.DisabledElement;
import com.generic.support.Log;
import com.generic.utils.ElementLayer;
import com.generic.utils.GenericUtils;
import com.generic.utils.WaitUtils;
import com.relevantcodes.extentreports.ExtentTest;

public class RenewalPaymentRequiredPage extends LoadableComponent<RenewalPaymentRequiredPage> {

	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	public final String txtForAnnualTypePayment = "(to be paid in a single annual payment)";
	public final String txtForMonthlyTypePayment = "(spread throughout the next year in monthly payments)";
	public final String txtForPaymentRequired = "Payment Required";
	public final String txtForYourPreferredPaymentChoice = "Your preferred payment choice";
	public final String txtForTotalAmountPay = "Total amount to pay";
	public final String txtForTaxMsgAnnual = "Inclusive of Insurance Premium Tax at its current rate.";
	public final String txtForToBePaidInInstalmentsOf = "To be paid in instalments of:";
	public final String txtForAmountOfCredit = "Amount of credit:";
	public final String txtForCostOfCredit = "Cost of credit:";
	public final String txtForNoteForMonthlyPayment = "Your payment plan is administered by [payment plan provider]. The figures shown here relating to payment amounts are for illustrative purposes only, these figures will be confirmed to you by the payment provider following completion of this policy renewal process";
	public final String txtForPreferredPaymentDate = "Date of each month when you want the payment to happen";
	public final String msgForCancelPopup = "Your renewal has not been processed";

	@FindBy(css = "#C2__C1__HEAD_82C3F84B8B5F77B74737")
	WebElement txtTitleRenewMyPolicy;

	@FindBy(css = "#C2__C1__HEAD_8BD58C2D2C2369111057475")
	WebElement txtPaymentRequired;

	@FindBy(css = "#C2__C1__BUT_14343F615DE6DB18944711")
	WebElement btnAnnual;

	@FindBy(css = "#C2__C1__BUT_14343F615DE6DB18944713")
	WebElement btnMonthly;

	@FindBy(css = "#C2__C1__HEAD_4B3EFBDE39E166431168607")
	WebElement txtYourPreferredPaymentChoice;

	@FindBy(css = "#C2__C1__p1_HEAD_14343F615DE6DB181281366 > div")
	WebElement totalAmountToPay;

	@FindBy(css = "#C2__C1__p1_HEAD_14343F615DE6DB181238779 > div")
	WebElement txtTotalAmountToPay;

	@FindBy(css = "#C2__C1__HEAD_19DC4308D1EC83DF237866")
	WebElement txtAnnualTypePayment;

	@FindBy(css = "#C2__C1__HEAD_14343F615DE6DB181245873")
	WebElement txtMonthlyTypePayment;

	@FindBy(css = "#C2__C1__p1_HEAD_5680A3374288E81D434223 > div")
	WebElement txtToBePaidInInstalmentsOf;

	@FindBy(css = "#C2__C1__p1_HEAD_3BCCB471307153DC418766")
	WebElement premiumAmountToBePaidInInstalmentsOf;

	@FindBy(css = "#C2__C1__HEAD_373076A58080C75B2180534")
	WebElement txtBreakdownOfCosts;

	@FindBy(css = "#C2__C1__HEAD_373076A58080C75B2180540")
	WebElement txtYourAddedExtraFeatures;

	@FindBy(css = "#C2__C1__HEAD_373076A58080C75B2186642")
	WebElement txtCreditCosts;

	@FindBy(css = "#C2__C1__QUE_D3F131B5E617F3F41088901")
	WebElement insurerPremium;

	@FindBy(css = "#C2__C1__QUE_D3F131B5E617F3F41088903")
	WebElement feesAmount;

	@FindBy(css = "#C2__C1__QUE_D3F131B5E617F3F41095022")
	WebElement amountOfCreditPremium;

	@FindBy(css = "#C2__C1__QUE_D3F131B5E617F3F41095024")
	WebElement costOfCreditpremium;

	@FindBy(css = "#C2__C1__HEAD_373076A58080C75B2204941")
	WebElement txtPreferredPaymentDate;

	@FindBy(css = "#C2__C1__QUE_373076A58080C75B2211042")
	WebElement preferredPaymentDate;

	@FindBy(css = "#C2__C1__p1_QUE_D3F131B5E617F3F41095022 > div > label")
	WebElement txtAmountOfCredit;

	@FindBy(css = "[id=C2__C1__p1_QUE_5680A3374288E81D455505]>div>label")
	WebElement txtCostOfCredit;

	@FindBy(css = "#C2__C1__HEAD_80D0DA3D4AAF8D8C355760")
	WebElement txtTaxMagAnnual;

	@FindBy(css = "#C2__C1__HEAD_5680A3374288E81D584557")
	WebElement txtTaxMagMonthly;

	@FindBy(css = "#C2__C1__PaymentMethodCancel")
	WebElement btnCancel;

	@FindBy(css = "#C2__C1__ID_BUY_POLICY_BTN")
	WebElement btnBuy;

	@FindBy(css = "#C2__C1__ID-RENEWAL-BUY")
	WebElement btnRenew;

	@FindBy(css = "#C2__C1__BUT_9B4F5996E9ED73EC2711919")
	WebElement btnClose;

	@FindBy(css = "#C2__C1__HEAD_5680A3374288E81D584557")
	WebElement txtNoteForMonthlyPayment;

	@FindBy(css = "[id*='C2__C1__QUE_D3F131B5E617F3F41095012_R']")
	List<WebElement> lstaddedExtraFeatures;

	@FindBy(css = "#C2__C1__p1_HEAD_9B4F5996E9ED73EC2711915")
	WebElement msgInCancelPopup;

	@FindBy(css = "[id *= C2__C1__QUE_D3F131B5E617F3F41095012_R]")
	List<WebElement> valueOfExtraFeatures;

	@FindBy(css = "[id *= C2__C1__QUE_D3F131B5E617F3F41095011_R]")
	List<WebElement> nameOfExtraFeatures;

	@FindBy(css = "button[title='Back']")
	WebElement btnBack;

	@FindBy (css = "[class*='checkbox checkbox-info checkbox-inline text-left'] > label")//div[class*='checkbox checkbox-info text-left renew_checkbox']
	WebElement chkTermsandConditions;

	/*********************************
	 * WebElements of RenewalRequiredPage - Ends *************************
	 **********************************************************************************************/
	/**
	 * 
	 * Constructor class for Payment Required Page Here we initializing the driver
	 * for page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */

	public RenewalPaymentRequiredPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void load() {
		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
		WaitUtils.waitForElement(driver, btnRenew);

	}

	@Override
	protected void isLoaded() throws Error {
		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(txtTitleRenewMyPolicy.isDisplayed()))) {
			Log.fail("Renew Requirement page did not open up. Error Page is displayed.", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);

	}

	/**
	 * select Buy button
	 * 
	 * @param extentReport
	 * @throws Excption
	 */
	public void clickBuyButton(ExtentTest extentReport, boolean Screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnRenew);
			btnRenew.click();
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForPageLoad(driver);
			Log.message("Selected on Buy button on payment required page ");
		} catch (Exception e) {
			Log.message("Error while selecting Buy button on payment required page" + e);
		}
	}

	/**
	 * select Buy button
	 * 
	 * @param extentReport
	 * @throws Excption
	 */
	public void clickRenewButton(ExtentTest extentReport, boolean Screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnRenew);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnRenew);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForPageLoad(driver);
			Log.message("Selected on Renew button on payment required page ");
		} catch (Exception e) {
			Log.message("Error while selecting Renew button on payment required page" + e);
		}
	}

	/**
	 * select Annual button
	 * 
	 * @param extentReport
	 * @throws Excption
	 */
	public void clickAnnualButton(ExtentTest extentReport, boolean Screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnAnnual);
			//btnAnnual.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAnnual);
			// WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForPageLoad(driver);
			Log.message("Selected on Annual button on payment required page ");
		} catch (Exception e) {
			Log.message("Error while selecting Annual button on payment required page" + e);
		}
	}

	/**
	 * select Monthly button
	 * 
	 * @param extentReport
	 * @throws Excption
	 */
	public void clickMonthlyButton(ExtentTest extentReport, boolean Screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnMonthly);
			//btnMonthly.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnMonthly);
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected on Monthly button on payment required page ");
		} catch (Exception e) {
			Log.message("Error while selecting Monthly button on payment required page" + e);
		}
	}

	/**
	 * select cancel button
	 * 
	 * @param extentReport
	 * @throws Excption
	 */
	public void clickCancelButton(ExtentTest extentReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnCancel);
			//btnCancel.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnCancel);
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected on cancel button on payment required page ");
		} catch (Exception e) {
			Log.message("Error while selecting cancel button on payment required page" + e);
		}
	}

	/**
	 * select close button
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public void selectClose(ExtentTest extentReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnClose);
			//btnClose.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnClose);
			Log.message("Close button selected on cancel window", driver, extentedReport);
			WaitUtils.waitForSpinner(driver);
		} catch (Exception e) {
			Log.message("error while selecting close button on cancel pop up window" + e);
		}
	}

	/**
	 * Verify Payment Required label
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public boolean verifyPaymentRequired(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtPaymentRequired, txtForPaymentRequired);

		} catch (Exception e) {
			throw new Exception("Error while verifying payment required method: " + e);
		}
	}

	/**
	 * Verify Preferred Payment Choice Label
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public boolean verifyYourPreferredPaymentChoice(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtYourPreferredPaymentChoice,
					txtForYourPreferredPaymentChoice);

		} catch (Exception e) {
			throw new Exception("Error while verifying preferred payment choice : " + e);
		}
	}

	/**
	 * Verify Total Amount To Pay Label
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public boolean verifyTotalAmountToPay(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtTotalAmountToPay, txtForTotalAmountPay);

		} catch (Exception e) {
			throw new Exception("Error while verifying total amount pay label  : " + e);
		}
	}

	/**
	 * Verify Annual Type Payment text
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public boolean verifytxtForAnnualTypePayment(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtAnnualTypePayment, txtForAnnualTypePayment);

		} catch (Exception e) {
			throw new Exception("Error while verifying Annual payment type label: " + e);
		}
	}

	/**
	 * Verify Monthly Type Payment text
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public boolean verifytxtForMonthlyTypePayment(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtMonthlyTypePayment, txtForMonthlyTypePayment);

		} catch (Exception e) {
			throw new Exception("Error while verifying monthly payment type label : " + e);
		}
	}

	/**
	 * Verify tax message for Annual text
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public boolean verifyTaxMagAnnual(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtTaxMagAnnual, txtForTaxMsgAnnual);

		} catch (Exception e) {
			throw new Exception("Error while verifying test msg for Annual option : " + e);
		}
	}

	/**
	 * Verify To Be Paid In instalments Of Text
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public boolean verifyToBePaidInInstalmentsOf(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtToBePaidInInstalmentsOf, txtForToBePaidInInstalmentsOf);

		} catch (Exception e) {
			throw new Exception("Error while verifying txt To Be Paid In Instalments Of : " + e);
		}
	}

	/**
	 * Verify Amount of Credit label
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public boolean verifyAmountOfCredit(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtAmountOfCredit, txtForAmountOfCredit);

		} catch (Exception e) {
			throw new Exception("Error while verifying Amount of Credit text : " + e);
		}
	}

	/**
	 * Verify Cost of credit label
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public boolean verifyCostOfCredit(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtCostOfCredit, txtForCostOfCredit);

		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions : " + e);
		}
	}

	/*
	 * public boolean verifyTaxMsgForMonthly() throws Exception { try { return
	 * GenericUtils.verifyWebElementTextContains(txtTaxMsgForMonthly,
	 * Tax_Msg_For_Monthly);
	 * 
	 * } catch (Exception e) { throw new
	 * Exception("Error while verifying Terms and Conditions : " + e); } }
	 */

	/**
	 * Verify Note For Monthly Payment
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public boolean verifyNoteForMonthlyPayment(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtNoteForMonthlyPayment, txtForNoteForMonthlyPayment);

		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions : " + e);
		}
	}

	/**
	 * Verify Note For Cancel popup
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public boolean verifyMsgInCancelPopup(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(msgInCancelPopup, msgForCancelPopup);

		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions Note : " + e);
		}
	}

	/**
	 * Get Insurer Premium amount
	 * 
	 * @return Insurer Premium amount
	 * 
	 */
	public boolean getInsurerPremiumAmount(String premiumAmount) {
		return GenericUtils.verifyWebElementTextContains(insurerPremium, premiumAmount);
	}

	/**
	 * Get Total Premium amount
	 * 
	 * @return Total Premium amount
	 * 
	 */
	public boolean getTotalPremiumAmount(String totalPremiumAmount) {
		return GenericUtils.verifyWebElementTextContains(totalAmountToPay, totalPremiumAmount);
	}

	/**
	 * Get premium Amount To Be Paid In Instalments Of
	 * 
	 * @return premium Amount To Be Paid In Instalments Of
	 * 
	 */
	public boolean getpremiumAmountToBePaidInInstalmentsOf(String paidInInstalments) {
		return GenericUtils.verifyWebElementTextContains(premiumAmountToBePaidInInstalmentsOf, paidInInstalments);
	}

	/**
	 * Get Fees amount
	 * 
	 * @return Fees amount
	 * 
	 */
	public boolean getPremiumFee(String premiumFeesAmount) {
		return GenericUtils.verifyWebElementTextContains(feesAmount, premiumFeesAmount);
	}

	/**
	 * Get Amount Of Credit
	 * 
	 * @return Amount Of Credit
	 * 
	 */
	public boolean getAmountOfCredit(String amountOfCreditCost) {
		return GenericUtils.verifyWebElementTextContains(amountOfCreditPremium, amountOfCreditCost);

	}

	/**
	 * Get Cost Of Credit
	 * 
	 * @return Cost Of Credit
	 * 
	 */
	public boolean getCostOfCredit(String costOfCreditAmount) {
		return GenericUtils.verifyWebElementTextContains(costOfCreditpremium, costOfCreditAmount);

	}
	/*
	 * public boolean isNotNull(String s) { return (s != null && s.length() > 0); }
	 * 
	 * public boolean isNull(String s) { return (s == null || s.length() <= 0); }
	 */

	/**
	 * Get added extra features
	 * 
	 * @return extra features
	 * @throws Exception
	 * 
	 */
	/*
	 * public boolean verifyAddedExtraFeatures(String addedExtraFeatures) throws
	 * Exception { return
	 * GenericUtils.verifyMatchingTextContainsElementFromList(lstaddedExtraFeatures,
	 * addedExtraFeatures);
	 * 
	 * }
	 */
	public String verifyAddedExtraFeatures(String addedExtraFeaturesValue, String addedExtraFeaturesName,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			String DeviceRow = null;
			WaitUtils.waitForSpinner(driver);
			if (valueOfExtraFeatures.size() == 0) {
				Log.message("No Added Extra Feature is available", driver, extentedReport, false);
			}

			String arrAddedExtraFeaturesValue[] = addedExtraFeaturesValue.split("\\|");
			String arrAddedExtraFeaturesName[] = addedExtraFeaturesName.split("\\|");

			for (int loopCount = 0; loopCount < valueOfExtraFeatures.size(); loopCount++) {
				// Integer row = loopCount + 1;
				String displayedValueOfExtraFeatures = valueOfExtraFeatures.get(loopCount).getText();
				String displayedNameOfExtraFeatures = nameOfExtraFeatures.get(loopCount).getText();
				if (displayedValueOfExtraFeatures.contains(arrAddedExtraFeaturesValue[loopCount])
						&& (displayedNameOfExtraFeatures.contains(arrAddedExtraFeaturesName[loopCount]))) {
					Log.message(arrAddedExtraFeaturesName[loopCount] + ":" + arrAddedExtraFeaturesValue[loopCount]
							+ "is Verifyed", driver, extentedReport, screenshot);
					// return row.toString();
				}
			}
			return DeviceRow;
		} catch (Exception e) {
			throw new Exception("Error while getting Extra Features details : " + e);
		}
	}

	public boolean verifyMonthlyButton(ExtentTest extentedReport, boolean Screenshot) {
		boolean status = false;
		if (btnMonthly.isEnabled()) {
			status = true;
			Log.message("Monthly button is showed");
		} else {
			Log.message("Monthly button is not showed");
		}

		return status;
	}

	public boolean verifyMonthlyButtonSelected(ExtentTest extentedReport, boolean Screenshot) {
		boolean status = false;
		if (btnMonthly.getAttribute("class").contains("green")) {
			status = true;
			Log.message("Monthly button is Selected default");
		} else {
			Log.message("Monthly button is not Selected default");
		}

		return status;
	}

	public boolean verifyAnnualButtonSelected(ExtentTest extentedReport, boolean Screenshot) {
		boolean status = false;
		if (btnAnnual.getAttribute("class").contains("green")) {
			status = true;
			Log.message("Annual button is Selected default");
		} else {
			Log.message("Annual button is not Selected default");
		}

		return status;
	}

	public boolean verifyPreferredPaymentDate(ExtentTest extentedReport, boolean Screenshot) {
		boolean status = false;
		if (preferredPaymentDate.isDisplayed()) {
			status = true;
			Log.message("Preferred payment date is showed");
		} else {
			Log.message("Preferred payment date is not showed");
		}

		return status;
	}

	/**
	 * select Terms and conditions
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public void clickTermsAndCond(ExtentTest extetReprot, boolean Screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, chkTermsandConditions);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", chkTermsandConditions);
			Log.message("Terms and Conditions check box selected on Payment Required", driver, extentedReport, true);
		} catch (Exception e) {
			Log.message("Terms and Conditions check box not selected on Payment Required" + e);
		}
	}

}
