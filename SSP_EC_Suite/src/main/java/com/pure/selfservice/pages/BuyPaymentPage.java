package com.pure.selfservice.pages;

//import java.util.List;
//import org.openqa.selenium.JavascriptExecutor;
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

public class BuyPaymentPage extends LoadableComponent<BuyPaymentPage> {
	
	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;
	
	public final String txtForChosenPayInInstalment = "You have chosen to pay in instalments";
	public final String txtForAmountToPayForRenewal = "Amouont to pay for your policy renewal";
	public final String txtForTotalPayment = "Total Payment";
	public final String txtForPaymentPlanProvider = "Payment plan provider";
	public final String	txtForDeposit = "Deposit of";
	public final String txtForFirstPayment = "First payment of ";
	public final String txtForFollowedByMonthly = "Followed by 11 monthly payments of ";
	public final String txtForTaxMsgAnnual = "Inclusive of Insurance Premium Tax at its current rate.";
	public final String msgForClosePopup = "Your renewal has not been processed";
	public final String txtForMssgOfPaymentPlan = "Your Payment plan is administered by Premium Credit Limited. The figures shown here relating to payment amounts are for illustrative purposes only, these figures will be confirmed to you by the payment provider following completion of this policy renewal process." ;
	
	@FindBy(css = "#C2__C1__HEAD_2788975567264A11908307")
	WebElement txtTitleChosenToPayInInstalment;
	
	@FindBy(css = "#C2__C1__p1_HEAD_8085E878F6244B7D871253 > div")
	WebElement amountToPayPolicyRenewal;
	
	@FindBy(css = "#C2__C1__QUE_2788975567264A11957431")
	WebElement totalAmountToPay;
	
	@FindBy(css = "#C2__C1__QUE_2788975567264A11951298")
	WebElement paymentPlanProvider;
	
	@FindBy(css = "#C2__C1__QUE_2788975567264A11963564")
	WebElement depositOfAmount;
	
	@FindBy(css = "#C2__C1__QUE_2788975567264A11963569")
	WebElement firstPaymentAmount;
	
	@FindBy(css = "#C2__C1__QUE_2788975567264A11969702")
	WebElement followedByMonthlyPaymentAmount;
		
	@FindBy(css = "input[name*='RENEWALPAYMENT[1].BANKDETAILS[1].BANKACCOUNTHOLDERSNAME']")
	WebElement bankAccountHolderName;
	
	@FindBy(css = "#C2__C1__QUE_2788975567264A11926729")
	WebElement sortCode;
	
	@FindBy(css = "input[name*='RENEWALPAYMENT[1].BANKDETAILS[1].ACCOUNTNUMBER']")
	WebElement accountNumber;
	
	@FindBy(css = "input[name*='RENEWALPAYMENT[1].BANKDETAILS[1].BANKNAME']")
	WebElement bankName;
	
	@FindBy(css = "#C2__C1__BUT_D398C4F0EF189310524105")
	WebElement buttonBack;
	
	@FindBy(css = "#C2__C1__ID_PAYMENT_PAYINSTALMENT_AGREECHANGE_BTN")
	WebElement buttonBuy;
	
	@FindBy(css = "#C2__C1__BUT_9B4F5996E9ED73EC2711919")
	WebElement btnClosePopup;
	
	@FindBy(css = "div[id='C2__C1__p1_HEAD_2788975567264A11908307'] div>h2")
	WebElement txtChosenPayInInstalment;
	
	@FindBy(css = "div[id='C2__C1__p1_HEAD_2788975567264A11908309'] div")
	WebElement txtAmountToPayForRenwal;
	
	@FindBy(css = "div[id='C2__C1__p1_QUE_2788975567264A11957431'] label")
	WebElement txtTotalPayment;
	
	@FindBy(css = "div[id='C2__C1__p1_QUE_2788975567264A11951298'] label")
	WebElement txtPaymentPlanProvider;
	
	@FindBy(css = "div[id='C2__C1__p1_QUE_2788975567264A11963564'] label")
	WebElement txtDeposit;
	
	@FindBy(css = "div[id='C2__C1__p1_QUE_2788975567264A11963569'] label")
	WebElement txtFirstPayment;
	
	@FindBy(css = "div[id='C2__C1__p1_QUE_2788975567264A11969702'] label")
	WebElement txtFollowedByMonthly;
	
	@FindBy(css = "#C2__C1__p1_HEAD_9B4F5996E9ED73EC2711915")
	WebElement txtTaxMsgAnnual;
	
	@FindBy(css = "div[id='C2__C1__p1_HEAD_9B4F5996E9ED73EC2711915'] > div")
	WebElement mssgClosePopup;
	
	@FindBy(css = "#C2__C1__QUE_373076A58080C75B2211042")
	WebElement preferredPaymentDate;
	
	@FindBy(css = "#C2__C1__QUE_2788975567264A11926724_ERRORMESSAGE")
	WebElement errorMssgaccountHolderName;
	
	@FindBy(css = "#C2__C1__QUE_2788975567264A11926729_ERRORMESSAGE")
	WebElement errorMssgSortCode;
	
	@FindBy(css = "#C2__C1__QUE_2788975567264A11926724_ERRORMESSAGE")
	WebElement errorMssgAccountNumber;
	
	@FindBy(css = "#C2__C1__QUE_2788975567264A11926724_ERRORMESSAGE")
	WebElement errorMssgBnakName;
	
	@FindBy(css = "#C2__C1__HEAD_5680A3374288E81D584557")
	WebElement txtMssgOfPaymentPlan;
	
	@FindBy(css = "#C2__C1__ID_PAYMENT_BUYWITHANOTHERCARD_BTN")
	WebElement btnBuyWithAnotherCard;
	
	@FindBy(css = "#C2__C1__p1_HEAD_502FFAF9BCEC52612104671")
	WebElement errorMssgforBankDetails;
	
	@FindBy(css = "#C2__C1__p1_HEAD_2788975567264A11981968")
	WebElement transactionFeeAndAPR;
	
	@FindBy(css = "#C2__C1__QUE_2788975567264A11975835")
	WebElement paymentDate;
	
	public BuyPaymentPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	protected void load() {
		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
		WaitUtils.waitForElement(driver, buttonBuy);

	}

	protected void isLoaded() throws Error {
		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(txtTitleChosenToPayInInstalment.isDisplayed()))) {
			Log.fail("Renew Requirement page did not open up. Error Page is displayed.", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);

	}
	
	public void clickBackButton(ExtentTest extentReport, boolean Screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, buttonBack);
			buttonBack.click();
			//WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForPageLoad(driver);
			Log.message("Selected on Back button on Buy payment page ");
		} catch (Exception e) {
			Log.message("Error while selecting Back button on Buy payment page" + e);
		}
	}
	
	public void clickBuyButton(ExtentTest extentReport, boolean Screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, buttonBuy);
			buttonBuy.click();
			//WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForPageLoad(driver);
			Log.message("Selected on Buy button on Buy payment page ");
		} catch (Exception e) {
			Log.message("Error while selecting Buy button on Buy payment page" + e);
		}
	}
	
	public void selectClose(ExtentTest extentReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnClosePopup);
			btnClosePopup.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Close button selected on close popup window", driver, extentedReport);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			
		} catch (Exception e) {
			Log.message("error while selecting close button on close popup window" + e);
		}
	}
	
	public boolean getAmountToPayForRenewal(String totalPremiumAmount) {
		return GenericUtils.verifyWebElementTextContains(amountToPayPolicyRenewal, totalPremiumAmount);
	}
	
	public boolean getTotalPremiumAmount(String totalPremiumAmount) {
		return GenericUtils.verifyWebElementTextContains(totalAmountToPay, totalPremiumAmount);
	}
	
	public boolean getPaymentPlanProvider(String paymentplanprovider) {
		return GenericUtils.verifyWebElementTextContains(paymentPlanProvider, paymentplanprovider);
		
	}
	
	public boolean getDepositOf(String depositAmount) {
		return GenericUtils.verifyWebElementTextContains(depositOfAmount, depositAmount);
	}
	
	public boolean getFirstPaymentOf(String firstAmountToPay) {
		return GenericUtils.verifyWebElementTextContains(firstPaymentAmount, firstAmountToPay);

	}
	
	public boolean getFollowedMonthlyPayments(String followedMonthlyPaymentAmount) {
		return GenericUtils.verifyWebElementTextContains(followedByMonthlyPaymentAmount, followedMonthlyPaymentAmount);

	}
	
	public boolean verifyChosenPayInInstalment(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtChosenPayInInstalment, txtForChosenPayInInstalment);

		} catch (Exception e) {
			throw new Exception("Error while verifying Chosen payment in instalment : " + e);
		}
	}
	
	public boolean verifyAmountToPayForRenewal(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtAmountToPayForRenwal, txtForAmountToPayForRenewal);

		} catch (Exception e) {
			throw new Exception("Error while verifying Amount to pay for renewal : " + e);
		}
	}
	
	public boolean verifyTotalPayment(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtTotalPayment, txtForTotalPayment);

		} catch (Exception e) {
			throw new Exception("Error while verifying Total amount : " + e);
		}
	}
	
	public boolean verifyPaymentPlanProvider(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtPaymentPlanProvider, txtForPaymentPlanProvider);

		} catch (Exception e) {
			throw new Exception("Error while verifying Payment plan provider : " + e);
		}
	}
	
	public boolean verifyDepositOf(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtDeposit, txtForDeposit);

		} catch (Exception e) {
			throw new Exception("Error while verifying Deposit Of : " + e);
		}
	}
	
	public boolean verifyFirstPayment(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtFirstPayment, txtForFirstPayment);

		} catch (Exception e) {
			throw new Exception("Error while verifying First paymenet : " + e);
		}
	}
	
	public boolean verifyFollowedByMonthlyPayments(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtFollowedByMonthly, txtForFollowedByMonthly);

		} catch (Exception e) {
			throw new Exception("Error while verifying Followed By Monthly Payments : " + e);
		}
	}
	
	public boolean verifyTaxMssgAnnual(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtTaxMsgAnnual, txtForTaxMsgAnnual);

		} catch (Exception e) {
			throw new Exception("Error while verifying Tax Message Annual : " + e);
		}
	}
	
	public boolean verifyMssgInClosePopup(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		WaitUtils.waitForElement(driver, mssgClosePopup);
		try {
			return GenericUtils.verifyWebElementTextContains(mssgClosePopup, msgForClosePopup);

		} catch (Exception e) {
			throw new Exception("Error while verifying Close Popup : " + e);
		}
	}
	
	public boolean verifyPreferredPaymentDate(ExtentTest extentedReport, boolean Screenshot)
	{
		boolean status = false;
		if(preferredPaymentDate.isDisplayed())
		{
			status = true;
			Log.message("Preferred payment date is showed");
		}
		else
		{
			Log.message("Preferred payment date is not showed");
		}
		
		return status;
	}
	
	public boolean verifyPaymentDate(ExtentTest extentedReport, boolean Screenshot)
	{
		boolean status = false;
		if(paymentDate.isDisplayed())
		{
			status = true;
			Log.message("Preferred payment date is showed");
		}
		else
		{
			Log.message("Preferred payment date is not showed");
		}
		
		return status;
	}
	
	public boolean verifyBankDetailsEnabled(ExtentTest extentedReport, boolean Screenshot )
	{
		boolean status = false;
		if(!sortCode.isEnabled())
		{
			status = true;
			Log.message("Bank details are Disabled mode");
		}
		else
		{
			Log.message("Bank details are Enabled mode");
		}
		
		return status;
	}
	
	public boolean verifySortCode(ExtentTest extentedReport, boolean Screenshot)
	{
		boolean status = false;
		
		if(sortCode.getAttribute("value").contains("-"))
		{
			status = true;
			Log.message("Sort code is displayed with hyphen");
		}
		else
		{
			Log.message("Sort code is not displayed with hyphen");
		}
		
		return status;
	}
	
	public boolean verifyAccountNumberIsMasked(ExtentTest extentedReport, boolean Screenshot)
	{
		boolean status = false;
		
		if(accountNumber.getAttribute("value").contains("****"))
		{
			status = true;
			Log.message("Bank Account number is displayed with masked format except last 4 digits");
		}
		else
		{
			Log.message("Bank Account number is not displayed with masked format except last 4 digits");
		}
		
		return status;
	}
	
	/**
	 * To enter Bank account holder name
	 * 
	 * @param Account name
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterAccountHolderName(String accountHolderName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, bankAccountHolderName);
			bankAccountHolderName.clear();
			bankAccountHolderName.sendKeys(accountHolderName);
			WaitUtils.waitForSpinner(driver);
			Log.message(accountHolderName + " - Account holder name is enterd in the Add your bank details section", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Account holder name in the Add your bank details section " + e);
		}

	}
	
	/**
	 * To enter Bank sort code
	 * 
	 * @param sort code
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterSortCode(String bankSortCode, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
				
			WaitUtils.waitForElement(driver, sortCode);
			sortCode.click();
			sortCode.clear();
			sortCode.sendKeys(bankSortCode);
			accountNumber.click();
			WaitUtils.waitForSpinner(driver);
			
			Log.message(bankSortCode + " - Sort code is enterd in the Add your bank details section", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Sort code in the Add your bank details section " + e);
		}

	}
	
	public void enterAccountNumber(String bankAccountNumber, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
						
			WaitUtils.waitForElement(driver, accountNumber);
			accountNumber.click();
			accountNumber.clear();
			accountNumber.sendKeys(bankAccountNumber);
			bankName.click();
			WaitUtils.waitForSpinner(driver);
			
			Log.message("Account number is enterd in the Add your bank details section", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Account number in the Add your bank details section " + e);
		}

	}
	
	public boolean verifyAccountNumber(ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		    boolean status= false;
			int sizeofAccountNumber = accountNumber.getText().length();
			if(sizeofAccountNumber>9)
			{
				
				Log.message("Invalid Account number is enterd in the Add your bank details section", driver, extentedReport,
						screenshot);			
			}
			else {
				status= true;
				Log.message("Valid Account number is enterd in the Add your bank details section", driver, extentedReport,
						screenshot);	
			}
			return status;
	}
	
	/**
	 * To enter Bank name
	 * 
	 * @param Bank name
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterBankName(String bankname, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, bankName);
			bankName.clear();
			bankName.sendKeys(bankname);
			Log.message(bankname + " - Acoonut number is enterd in the Add your bank details section", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Acoonut number in the Add your bank details section " + e);
		}

	}
	
	public boolean verifyErrorMssgBankAccountHolderName(ExtentTest extentedReport, boolean Screenshot)
	{
		boolean status = false;
		
		if(errorMssgaccountHolderName.isDisplayed())
		{
			status = true;
			Log.message("Bank Account holder name is displayed wrong in add your bank details section");
		}
		else
		{
			Log.message("Bank Account holder name is displayed correct in add your bank details section");
		}
		
		return status;
	}
	
	public boolean verifyErrorMssgSortCode(ExtentTest extentedReport, boolean Screenshot)
	{
		boolean status = false;
		
		if(errorMssgSortCode.isDisplayed())
		{
			status = true;
			Log.message("Sort Code is displayed wrong in add your bank details section");
		}
		else
		{
			Log.message("Sort Code is displayed correct in add your bank details section");
		}
		
		return status;
	}
	
	public boolean verifyErrorMssgAccountNumber(ExtentTest extentedReport, boolean Screenshot)
	{
		boolean status = false;
		
		if(errorMssgAccountNumber.isDisplayed())
		{
			status = true;
			Log.message("Account Number is displayed wrong in add your bank details section");
		}
		else
		{
			Log.message("Account Number is displayed correct in add your bank details section");
		}
		
		return status;
	}
	
	public boolean verifyErrorMssgBankName(ExtentTest extentedReport, boolean Screenshot)
	{
		boolean status = false;
		
		if(errorMssgBnakName.isDisplayed())
		{
			status = true;
			Log.message("Bank Name is displayed wrong in add your bank details section");
		}
		else
		{
			Log.message("Bank Name is displayed correct in add your bank details section");
		}
		
		return status;
	}
	
	public boolean verifyBankNameIsEmpty(ExtentTest extentedReport, boolean Screenshot)
	{
		boolean status = false;
		
		if(bankName.getAttribute("value").contains("Bootle Centre"))
		{
			status = true;
			Log.message("Bank Name is entered in add your bank details section");
		}
		else
		{
			Log.message("Bank Name is empty in add your bank details section");
		}
		
		return status;
	}
		
	public boolean verifyTextMessageOfPaymentPlan(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextContains(txtMssgOfPaymentPlan, txtForMssgOfPaymentPlan);

		} catch (Exception e) {
			throw new Exception("Error while verifying Message of payment plan : " + e);
		}
	}
	
	public boolean verifyOfPaymentPlanProvider(ExtentTest extentedReport, boolean Screenshot) throws Exception {
		boolean status = false;
		
		String paymentProvide = paymentPlanProvider.getText();
		if(txtMssgOfPaymentPlan.getText().contains(paymentProvide))
		{
			status = true;
			Log.message("Payment plan provider is displayed in the advisory message");
		}
		else
		{
			Log.message("Payment plan provider is not displayed in the advisory message");
		}
		
		return status;
		
	}
	
	public boolean verifyBuyButton(ExtentTest extentedReport, boolean Screenshot)
	{
		boolean status = false;
		
		if(buttonBuy.isEnabled())
		{
			status = true;
			Log.message("Buy button is Enabled ");
		}
		else
		{
			Log.message("Buy button is Disabled");
		}
		
		return status;
	}
	
	public boolean verifyDepositAmount(ExtentTest extentedReport, boolean Screenshot)
	{
		boolean status = false;
		
		//String txtString = depositOfAmount.getText();
		//int depositAmount = Integer.parseInt(depositOfAmount.getText());
		float depositAmountfloat =  Float.parseFloat(depositOfAmount.getText());
		
		if(depositAmountfloat > 0)
		{
			status = true;
			Log.message("Deposit amount is greaterthan ZERO");
		}
		else
		{
			Log.message("Deposit amount is lessthan ZERO");
		}
		
		return status;
	}
		
	public boolean verifyTranscationFeeAndAPR(String textValue, ExtentTest extentedReport, boolean Screenshot)
	{
		boolean status = false;
		
		if(transactionFeeAndAPR.getText().contains(textValue))
		{
			status = true;
			Log.message(textValue+ "Displayed in paymnet page");
		}
		else
		{
			Log.message(textValue+ "not Displayed in paymnet page");
		}
		
		return status;
	}
	
}