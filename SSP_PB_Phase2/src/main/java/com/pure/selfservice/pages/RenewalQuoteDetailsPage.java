package com.pure.selfservice.pages;

//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;

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

public class RenewalQuoteDetailsPage extends LoadableComponent<RenewalQuoteDetailsPage> {

	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;
	
	public final String TERMS_AND_CONDITION_Note = "Please refer to your renewal documents and Policy Booklet for full details of what’s included with this policy. This is a direct renewal of your current insurance policy, you cannot make any changes to your policy details. If any of the details are incorrect or you need to make any changes to your quote information, please call your broker on XXXXXXX XXXX.";
	public final String Msg_In_Cancel_Popup = "";
	public final String Payment_Required = "Payment Required";
	public final String Your_Preferred_Payment_Choice = "Your preferred payment choice";
	public final String Total_Amount_To_Pay = "Total amount to pay";
	public final String txt_For_Annual_Type_Payment = "(to be paid in a single annual payment)";
	public final String txt_For_Monthly_Type_Payment = "(spread throughout the next year in monthly payments)";
	public final String Tax_Mag_Annual = "Inclusive of Insurance Premium Tax at its current rate.";
	public final String TERMS_AND_CONDITION_MSG = "I can confirm that I have read, understood and accepted the  Policy Wording(s) and Terms of Business.";
	public final String To_Be_Paid_In_Instalments_Of = "To be paid in instalments of:";
	public final String Cost_Of_Renewal_Premium = "Cost of renewal premium:";
	public final String Amount_Of_Credit = "Amount of credit:";
	public final String Cost_Of_Credit = "Cost of credit:";
	public final String Tax_Msg_For_Monthly = "The cost of credit includes a transaction fee of 19.40% variable. The APR is 39.90% variable. Inclusive of Insurance Premium Tax at its current rate.";
	public final String Note_For_Monthly_Payment = "Your payment plan is administered by [payment plan provider]. The figures shown here relating to payment amounts are for illustrative purposes only, these figures will be confirmed to you by the payment provider following completion of this policy renewal process";
	
	
	@FindBy(css = "#C2__C1__HEAD_82C3F84B8B5F77B74679")
	WebElement txtTitleRenewQuotePage;
	
	@FindBy(css = "#C2__C1__BUT_8BD58C2D2C2369111042290")
	WebElement btnBuy;
	
	@FindBy(css = "#C2__C1__BUT_8BD58C2D2C2369111042287")
	WebElement btnCancel;
	
	@FindBy(css = "#C2__C1__p1_HEAD_1F145A4C2A19EE9F1539973")
	WebElement msgInCancelPopup;
	
	@FindBy(css = "#C2__C1__BUT_A7C5FED01AF778F0196305")
	WebElement btnClose;
	
	@FindBy(css = "#C2__C1__BUT_14343F615DE6DB18944711")
	WebElement btnAnnual;
	
	@FindBy(css = "#C2__C1__BUT_14343F615DE6DB18944713")
	WebElement btnMonthly;
	
	@FindBy(css = "#C2__C1__HEAD_14343F615DE6DB18852380")
	WebElement typeOfInsurare;
	
	@FindBy(css = "#C2__C1__p1_HEAD_14343F615DE6DB18866567")
	WebElement typeOfScheme;

	@FindBy(css = "#C2__C1__HEAD_14343F615DE6DB18887853")
	WebElement premiumAmount;

	@FindBy(css = "#C2__C1__p1_HEAD_14343F615DE6DB18887855")
	WebElement typeOfPayment;
	
	@FindBy(css = "#C2__C1__HEAD_8BD58C2D2C2369111057475")
	WebElement txtPaymentRequired;
	
	@FindBy(css = "#C2__C1__HEAD_4B3EFBDE39E166431168607")
	WebElement txtYourPreferredPaymentChoice;
	
	@FindBy(css = "#C2__C1__HEAD_3BCCB471307153DC505851")
	WebElement yourPreferredPaymentChoice;
	
	@FindBy(css = "#C2__C1__p1_HEAD_14343F615DE6DB181238779")
	WebElement txtTotalAmountToPay;
	
	@FindBy(css = "#C2__C1__HEAD_19DC4308D1EC83DF237866")
	WebElement txtForAnnualTypePayment;
	
	@FindBy(css = "#C2__C1__HEAD_14343F615DE6DB181245873")
	WebElement txtForMonthlyTypePayment;
	
	@FindBy(css = "#C2__C1__p1_HEAD_14343F615DE6DB181281366")
	WebElement premiumAmountInPaymentInformation;
	
	@FindBy(css = "#C2__C1__HEAD_80D0DA3D4AAF8D8C355760")
	WebElement txtTaxMagAnnual;
	
	@FindBy(css = "[id = C2__C1__p1_HEAD_8BD58C2D2C2369111234971]>div")
	WebElement txtchkTermsandConditionsNote;
	
	@FindBy(css = "#C2__C1__FS_QUE_8BD58C2D2C2369111042252 > div > label")
	WebElement txtchkTermsandConditionsMsg;
	
	@FindBy(css = "div[class*='checkbox checkbox-info checkbox-inline text-left'] > label")
	WebElement chkTermsandConditions;
	
	@FindBy(css = "#polWordURL")
	WebElement linkPolicyWordDoc;
	
	@FindBy(css = "#C2__C1__p1_HEAD_5680A3374288E81D434223")
	WebElement txtToBePaidInInstalmentsOf;
	
	@FindBy(css = "#C2__C1__p1_HEAD_3BCCB471307153DC418766")
	WebElement premiumAmountToBePaidInInstalmentsOf;
	
	@FindBy(css = "[id=C2__C1__p1_QUE_5680A3374288E81D455495]>div>label")
	WebElement txtCostOfRenewalPremium;
	
	@FindBy(css = "#C2__C1__p4_QUE_5680A3374288E81D455495")
	WebElement premiumForCostOfRenewal;
	
	@FindBy(css = "[id=C2__C1__p1_QUE_5680A3374288E81D455500]>div>label")
	WebElement txtAmountOfCredit;
	
	@FindBy(css = "#C2__C1__p4_QUE_5680A3374288E81D455500")
	WebElement premiumAmountOfCredit;
	
	@FindBy(css = "[id=C2__C1__p1_QUE_5680A3374288E81D455505]>div>label")
	WebElement txtCostOfCredit;
	
	@FindBy(css = "#C2__C1__p4_QUE_5680A3374288E81D455505")
	WebElement premuimCostOfCredit;
	
	@FindBy(css = "#C2__C1__HEAD_5680A3374288E81D577463")
	WebElement txtTaxMsgForMonthly;
	
	@FindBy(css = "#C2__C1__HEAD_5680A3374288E81D584557")
	WebElement txtNoteForMonthlyPayment;
	
	/**
	 * 
	 * Constructor class for Renew My Policy Page Here we initializing the driver
	 * for page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */

	public RenewalQuoteDetailsPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void load() {
		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
		WaitUtils.waitForElement(driver, btnBuy);

	}

	@Override
	protected void isLoaded() throws Error {
		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(txtTitleRenewQuotePage.isDisplayed()))) {
			Log.fail("Renew my policy page did not open up. Error Page is displayed.", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);

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
			btnCancel.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected on cancel button on Quote page ");
		} catch (Exception e) {
			Log.message("Error while selecting cancel button on Quote page" + e);
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
			btnClose.click();
			Log.message("Close button selected on cancel window", driver, extentedReport);
			WaitUtils.waitForSpinner(driver);
		} catch (Exception e) {
			Log.message("error while selecting close button on cancel pop up window" + e);
		}
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
			chkTermsandConditions.click();
			Log.message("Terms and Conditions check box selected on Renew my policy page", driver, extentedReport,
					true);
		} catch (Exception e) {
			Log.message("Terms and Conditions check box not selected on Renew my policy page" + e);
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
			WaitUtils.waitForelementToBeClickable(driver, btnBuy, "Buy button is not found");
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", btnBuy);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Buy' button in Quote page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Buy' button in Quote page : " + e);
		}
	}
	
	public boolean verifyTermsAndConditionsNote() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtchkTermsandConditionsNote, TERMS_AND_CONDITION_Note);
			
		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions Note : " + e);
		}
	}
	
	public boolean verifyTermsAndConditions() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtchkTermsandConditionsMsg, TERMS_AND_CONDITION_MSG);
			
		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions : " + e);
		}
	}
	
	public boolean verifyMsgInCancelPopup() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(msgInCancelPopup, Msg_In_Cancel_Popup);
			
		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions Note : " + e);
		}
	}
	
	public boolean verifyPaymentRequired() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtPaymentRequired, Payment_Required);
			
		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions Note : " + e);
		}
	}
	
	public boolean verifyYourPreferredPaymentChoice() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtYourPreferredPaymentChoice, Your_Preferred_Payment_Choice);
			
		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions Note : " + e);
		}
	}
	
	public boolean verifyTotalAmountToPay() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtTotalAmountToPay, Total_Amount_To_Pay);
			
		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions Note : " + e);
		}
	}
	
	public boolean verifytxtForAnnualTypePayment() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtForAnnualTypePayment, txt_For_Annual_Type_Payment);
			
		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions Note : " + e);
		}
	}
	
	public boolean verifytxtForMonthlyTypePayment() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtForMonthlyTypePayment, txt_For_Monthly_Type_Payment);
			
		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions Note : " + e);
		}
	}
	
	public boolean verifyTaxMagAnnual() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtTaxMagAnnual, Tax_Mag_Annual);
			
		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions Note : " + e);
		}
	}
	
	public boolean verifyToBePaidInInstalmentsOf() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtToBePaidInInstalmentsOf, To_Be_Paid_In_Instalments_Of);
			
		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions : " + e);
		}
	}
	
	public boolean verifyCostOfRenewalPremium() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtCostOfRenewalPremium, Cost_Of_Renewal_Premium);
			
		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions : " + e);
		}
	}
	
	public boolean verifyAmountOfCredit() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtAmountOfCredit, Amount_Of_Credit);
			
		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions : " + e);
		}
	}
	
	public boolean verifyCostOfCredit() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtCostOfCredit, Cost_Of_Credit);
			
		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions : " + e);
		}
	}
	
	public boolean verifyTaxMsgForMonthly() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtTaxMsgForMonthly, Tax_Msg_For_Monthly);
			
		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions : " + e);
		}
	}
	
	public boolean verifyNoteForMonthlyPayment() throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtNoteForMonthlyPayment, Note_For_Monthly_Payment);
			
		} catch (Exception e) {
			throw new Exception("Error while verifying Terms and Conditions : " + e);
		}
	}
	
	
	
	/**
	 * select Annual button
	 * 
	 * @param extentReport
	 * @throws Excption
	 */
	public void clickAnnualButton(ExtentTest extentReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnAnnual);
			btnAnnual.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected on Annual button on Quote page ");
		} catch (Exception e) {
			Log.message("Error while selecting Annual button on Quote page" + e);
		}
	}
	
	/**
	 * select Monthly button
	 * 
	 * @param extentReport
	 * @throws Excption
	 */
	public void clickMonthlyButton(ExtentTest extentReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnMonthly);
			btnMonthly.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected on Monthly button on Quote page ");
		} catch (Exception e) {
			Log.message("Error while selecting Monthly button on Quote page" + e);
		}
	}
	
	/**
	 * Get type Of Insurance
	 * 
	 * @return type Of Insurance
	 * 
	 */
	public boolean getTypeOfInsurance(String typeOfInsurance) {
		return GenericUtils.verifyWebElementTextContains(typeOfInsurare, typeOfInsurance);
	}
	
	/**
	 * Get type Of Cover
	 * 
	 * @return type Of Cover
	 * 
	 */
	public boolean getTypeOfScheme(String typeOfSubScheme) {
		return GenericUtils.verifyWebElementTextContains(typeOfScheme, typeOfSubScheme);
	}
	
	/**
	 * Get type Of Cover
	 * 
	 * @return type Of Cover
	 * 
	 */
	public boolean getPremiumAmount(String totalPremium) {
		return GenericUtils.verifyWebElementTextContains(premiumAmount, totalPremium);
	}
	
	/**
	 * Get type Of Cover
	 * 
	 * @return type Of Cover
	 * 
	 */
	public boolean getpremiumAmountInPaymentInformation(String totalPremium) {
		return GenericUtils.verifyWebElementTextContains(premiumAmountInPaymentInformation, totalPremium);
	}
	
	/**
	 * Get type Of Cover
	 * 
	 * @return type Of Cover
	 * 
	 */
	public boolean getpremiumAmountToBePaidInInstalmentsOf(String paidInInstalments) {
		return GenericUtils.verifyWebElementTextContains(premiumAmountToBePaidInInstalmentsOf, paidInInstalments);
	}
	
	/**
	 * Get type Of Cover
	 * 
	 * @return type Of Cover
	 * 
	 */
	public boolean getpremiumForCostOfRenewal(String CostOfRenewal) {
		return GenericUtils.verifyWebElementTextContains(premiumForCostOfRenewal, CostOfRenewal);
	}
	
	/**
	 * Get type Of Cover
	 * 
	 * @return type Of Cover
	 * 
	 */
	public boolean getpremiumAmountOfCredit(String AmountOfCredit) {
		return GenericUtils.verifyWebElementTextContains(premiumAmountOfCredit, AmountOfCredit);
	}
	
	/**
	 * Get type Of Cover
	 * 
	 * @return type Of Cover
	 * 
	 */
	public boolean getpremuimCostOfCredit(String CostOfCredit) {
		return GenericUtils.verifyWebElementTextContains(premuimCostOfCredit, CostOfCredit);
	}
	
	public boolean verifyAnnualButton()
	{
		boolean status = false;
		if(btnAnnual.isEnabled())
		{
			status = true;
			Log.message("Premium type is selected as Annual", extentedReport);
		}
		else
		{
			Log.message("Premium type is not Verified");
		}
		
		return status;
	}
	
	public boolean verifyMonthlyButton()
	{
		boolean status = false;
		if(btnMonthly.isEnabled())
		{
			status = true;
			Log.message("Premium type is selected as Monthly", extentedReport);
		}
		else
		{
			Log.message("Premium type is not Verified");
		}
		
		return status;
	}
	
	/**
	 * Verify Buy clickable
	 * 
	 * @param extentReport
	 * @return boolean
	 * @throws Exception
	 */
	public boolean verifyBuyButtonClickable(ExtentTest extentReport) throws Exception {
		boolean status = true;

		try {
			if (btnBuy.isEnabled()) {
				WaitUtils.waitForelementToBeClickable(driver, btnBuy, "Buy button is able to click");
				return status;
			} else {
				Log.message("Buy button displaing as disabled on quote page" + btnBuy);
				status = false;
				return status;
			}
		} catch (Exception e) {
			status = false;
			Log.message("Error while Verifying Buy button : " + e);
			return false;
		}

	}
}
