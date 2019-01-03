package com.pure.selfservice.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
 * MTAPaymentPage contains Customer contact details
 * 
 */

public class MTAPaymentPage extends LoadableComponent<MTAPaymentPage> {
	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;
	public String POPUP_MSG_HELPICON_Continuous_Payments = "Continuous Payment Authority is a term used to describe where you have given permission for regular collections to be made using your payment card. This means that when your policy is due for renewal, we will automatically collect payment for the renewal premium";
	public String POPUP_MSG_HELPICON_Previously_Used_Cards = "The card you select as a default will be made your default payment method for continuous payments and can be different to the card you choose to use for this transaction";
	
	public String CHOSEN_BY_INSTALMENT = "You have chosen to pay by instalments";
	public String cssSelectExistingCard = "[for='C2__QUE_554E9AF6046CC3A4379681_0_R";
	public String cssDefaultCard = "[for='C2__QUE_821080FB70B7488F659177_0_R";
	
	@FindBy(css = "button[title*='Buy with Another Card']")
	WebElement btnBuyWithAnotherCard;

	@FindBy(css = "#C2__HEAD_82C3F84B8B5F77B74814")
	WebElement txtTitlePleaseChoosePaymentCard;
	
	@FindBy(css = "#C2__HEAD_82C3F84B8B5F77B74743")
	WebElement txtPleaseChoosePaymentmethod;

	@FindBy(css = "button[title='Buy']")
	WebElement btnBuy;

	@FindBy(css = "button[title='Cancel']")
	WebElement btnCancel;

	@FindBy(css = ".col-md-12 text-normal payment-box-card box-hgt-290 text-center")
	WebElement btnCreditCard;

	@FindBy(css = "table[summary='PreviouslyUsedCards']")
	WebElement tblPreviouslyUsedCard;

	@FindBy(css = "table[summary='PreviouslyUsedCards'] tbody tr")
	List<WebElement> lstPreviouslyUsedCardRow;

	@FindBy(css = "#C2__p1_QUE_82C3F84B8B5F77B74831")
	WebElement hdrCardType;

	@FindBy(css = "#C2__p1_QUE_82C3F84B8B5F77B74833")
	WebElement hdrCardNumber;

	@FindBy(css = "#C2__p1_QUE_82C3F84B8B5F77B74835")
	WebElement hdrExpiryDate;

	@FindBy(css = "#C2__p1_QUE_554E9AF6046CC3A4379681")
	WebElement hdrCheckbox;

	@FindBy(css = "button[id*='PAYMENT_SELECT_CARD']")
	WebElement btnSelectCard;

	@FindBy(css = "button[id*='select-finance']")
	WebElement btnSelectFinance;

	@FindBy(css = "#C2__ID_BUY_POLICY_BTN")
	WebElement btnBuyPaymentMethod;

	@FindBy(css = "#C2__HEAD_D398C4F0EF189310502594")
	WebElement txtChosenToPayByInstalment;

	@FindBy(css = "#C2__HEAD_82C3F84B8B5F77B74737")
	WebElement txtChangeMyPolicy;

	@FindBy(css = "#C2__HEAD_D398C4F0EF189310546613")
	WebElement txtRefundDue;

	@FindBy(css = "button[title='Agree Change']")
	WebElement btnAgreeChange;

	@FindBy(css = "#C2__HEAD_D398C4F0EF189310540738")
	WebElement lblRefundAmount;

	@FindBy(css = "#C2__HEAD_D398C4F0EF189310541182")
	WebElement txtRefundAmount;

	@FindBy(css = "#C2__HEAD_554E9AF6046CC3A4147367")
	WebElement txtUnsuccessful;

	@FindBy(css = "#C2__p1_HEAD_D398C4F0EF189310528298")
	WebElement txtRefundAmtPremiumFinance;

	@FindBy(css = "#C2__p1_HEAD_D398C4F0EF189310528788")
	WebElement txtHowPlanOriginallySetUp;

	@FindBy(css = "#C2__row_HEAD_D398C4F0EF189310531950")
	WebElement txtHowPlanwillChange;

	@FindBy(css = "#C2__p0_TBL_82C3F84B8B5F77B74829_R1")
	WebElement rowPreviouslyUsedCard1;

	@FindBy(css = "#C2__HEAD_D398C4F0EF189310527800")
	WebElement txtRefundDueOnfinancePlan;

	@FindBy(css = "#C2__HEAD_82C3F84B8B5F77B74753")
	WebElement txtAdditionalPremium;

	@FindBy(css = "#C2__HEAD_82C3F84B8B5F77B74755")
	WebElement txtAdditionalPremiumAmt;

	@FindBy(css = "#C2__HEAD_82C3F84B8B5F77B74763")
	WebElement txtPremiumFinanceTitle;

	@FindBy(css = "#C2__HEAD_82C3F84B8B5F77B74753")
	WebElement txtAdditionalPremiumCard;

	@FindBy(css = "#C2__HEAD_82C3F84B8B5F77B74755")
	WebElement txtAdditionalPremiumCardAmt;

	@FindBy(css = "C2__HEAD_82C3F84B8B5F77B74769")
	WebElement txtAmtToPay;

	@FindBy(css = "#C2__HEAD_82C3F84B8B5F77B74772")
	WebElement txtAmtToPayAmt;
	
	@FindBy(css = "[id*='C2__QUE_FA30D94E9C7869E01070002_0'] + span")
	WebElement continuousPaymentsYes;
	
	@FindBy(css = "[id*='C2__QUE_FA30D94E9C7869E01070002_1'] + span")
	WebElement continuousPaymentsNo;
	
	@FindBy(css = "#C2__HELP_HEAD_FA30D94E9C7869E01052755")
	WebElement iconHelpContinuousPayments;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpContinuousPayments;
	
	@FindBy(css = "#C2__p1_QUE_554E9AF6046CC3A4379681")
	WebElement txtSelectCard;
	
	@FindBy(css = "#C2__p1_HEAD_821080FB70B7488F899252")
	WebElement txtPopupMessage;
	
	@FindBy(css = "[id*='C2__BUT_821080FB70B7488F899253']")
	WebElement PopupMessageYes;
	
	@FindBy(css = "[id*='C2__BUT_821080FB70B7488F899254']")
	WebElement PopupMessageNo;
	
	@FindBy(css = "#C2__HELP_HEAD_821080FB70B7488F693755")
	WebElement iconHelpPreviouslyUsedCards;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPreviouslyUsedCards;
	
	@FindBy(css = "#C2__p1_HEAD_821080FB70B7488F1075664")
	WebElement errorMag;
	
	@FindBy(css = "#C2__p1_QUE_821080FB70B7488F659177")
	WebElement txtDefault;
	
	@FindBy(css = "[id *= C2__QUE_82C3F84B8B5F77B74831_R]")
	List<WebElement> selectExistingCardName;
	


	/*********************************
	 * WebElements of MTAPaymentPage - Ends *************************
	 **********************************************************************************************/

	/**
	 * 
	 * Constructor class for MTAPaymentPage Here we initializing the driver for page
	 * factory objects. For ajax element waiting time has added while initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public MTAPaymentPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(WaitUtils.waitForElement(driver, txtChangeMyPolicy)))) {
			Log.fail("User is not navigated to MTA Payment Page ", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {

		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
		WaitUtils.waitForElement(driver, txtChangeMyPolicy);
	}

	/**
	 * click Buy button
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public void clickBuyButton(ExtentTest extetReprot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnBuy);
			btnBuy.click();

			WaitUtils.waitForSpinner(driver);
			Log.message("Buy button clicked on Quote page", driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Buy button is not clicked on quote page : " + e);
		}
	}

	/**
	 * click VISA button
	 * 
	 * @param extentReport
	 * @throws Exception
	 */

	public CardDetailsPage selectVisacard(ExtentTest extentedReport) throws Exception {
		try {
			WaitUtils.waitForPageLoad(driver);
			driver.switchTo().frame("sagepay");
			//WaitUtils.waitForElement(driver, lnkVisa);
			//lnkVisa.click();
			Log.message("Selected the Visa card", driver, extentedReport, true);
			WaitUtils.waitForSpinner(driver);
			return new CardDetailsPage(driver, extentedReport).get();

		} catch (Exception e) {
			throw new Exception("Error while selecting VISA Card : " + e);
		}

	}

	/**
	 * click BuyWithOtherCard button
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public CardDetailsPage clickBuyWithOtherCard(ExtentTest extentRepot, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnBuyWithAnotherCard);
			btnBuyWithAnotherCard.click();
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForPageLoad(driver);
			Log.message("Buy  with another card button clicked on Quote page", driver, extentedReport, screenshot);
			return new CardDetailsPage(driver, extentedReport).get();
		} catch (Exception e) {
			throw new Exception("Buy with another card button is not clicked on quote page : " + e);
		}
	}

	/**
	 * click Previously Used Card checkbox
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public void clickSelectInPayByCard(ExtentTest extentRepot, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnSelectCard);
			btnSelectCard.click();
			Log.message("Clicked on elect in Pay By Card section", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking the previously used checkbox : " + e);
		}
	}

	/**
	 * click cancel button
	 * 
	 * @param extentReport
	 * @throws Exception
	 * 
	 */
	public void clickCancelButton(ExtentTest extetReprot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnCancel);
			btnCancel.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("clicked Cancel button in Payment page", driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking Cancel button in Payment page : " + e);
		}
	}

	/**
	 * To select PaymentMethod
	 * 
	 * @param extentReport
	 * @throws Exception
	 * 
	 */
	public void selectPaymentMethod(String methodName, ExtentTest extetReprot, boolean screenshot) throws Exception {
		try {
			switch (methodName) {
			case "Pay by card":
				WaitUtils.waitForElement(driver, btnSelectCard);
				btnSelectCard.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "Pay by finance":
				WaitUtils.waitForElement(driver, btnSelectFinance);
				btnSelectFinance.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message(methodName + " : Pyament method is selected in payment page", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while selecting payment method in Payment page : " + e);
		}
	}

	/**
	 * click Buy button
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public void clickBuyBtnForPaymentMethod(ExtentTest extetReprot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnBuyPaymentMethod);
			btnBuyPaymentMethod.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Buy button for payment method clicked on Payment page", driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking buy button for Payment method" + e);
		}
	}

	/**
	 * click AgreeChange button
	 * 
	 * @param extentReport
	 * @throws Exception
	 */

	public SuccessPage clickAgreeChangeBtn(ExtentTest extentedReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnAgreeChange);
			btnAgreeChange.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Agree change button clicked on confirmation page", driver, extentedReport, true);
			return new SuccessPage(driver).get();
		} catch (Exception e) {
			throw new Exception("Error while clicking agree button on confirmation page : " + e);
		}
	}

	/**
	 * Get Refund Amount
	 * 
	 * @throws Exception
	 */
	public String getRefundAmount() throws Exception {
		return GenericUtils.getTextOfWebElement(txtRefundAmount, driver);

	}

	/**
	 * Get Additional Premium Amount
	 * 
	 * @throws Exception
	 */
	public String getAdditionalPremiumAmount() throws Exception {
		return GenericUtils.getTextOfWebElement(txtAdditionalPremiumAmt, driver);

	}

	/**
	 * Get Refund Due Premium Amount
	 * 
	 * @throws Exception
	 */
	public String getRefundDuePremiumAmount() throws Exception {
		return GenericUtils.getTextOfWebElement(txtRefundAmtPremiumFinance, driver);

	}

	/**
	 * Get Additional Premium Amount By Card
	 * 
	 * @throws Exception
	 */
	public String getAdditionalPremiumAmountByCard() throws Exception {
		return GenericUtils.getTextOfWebElement(txtAdditionalPremiumCardAmt, driver);

	}

	/**
	 * Verify Title for Premium finance display and Additional amt
	 * 
	 * @throws Exception
	 */
	public boolean verifyPremiumFinanceTitleAndAdditionalAmt() throws Exception {
		boolean status = false;
		if (GenericUtils.getTextOfWebElement(txtPremiumFinanceTitle, driver)
				.equals("Pay in Instalments with a Finance Plan")
				&& GenericUtils.getTextOfWebElement(txtAdditionalPremiumCard, driver).equals("Additional premium")) {
			status = true;
		}
		return status;
	}

	/**
	 * Get Additional Amount to be paid
	 * 
	 * @throws Exception
	 */
	public String getAdditionalAmtToBePaid() throws Exception {
		return GenericUtils.getTextOfWebElement(txtAmtToPayAmt, driver);

	}
	
	/**
	 * Verify Continuous Payments Question
	 * 
	 * @param extentReport
	 * @return boolean
	 * @throws Exception
	 */
	public boolean verifyContinuousPaymentsQuestion(ExtentTest extentReport) throws Exception {
		boolean status = true;

		try {
			if (continuousPaymentsYes.isDisplayed()) {
				WaitUtils.waitForelementToBeClickable(driver, continuousPaymentsYes, "Continuous Payments Question is displayed");
				return status;
			} else {
				Log.message("Continuous Payments Question is not displayed" + continuousPaymentsYes);
				status = false;
				return status;
			}
		} catch (Exception e) {
			status = false;
			Log.message("Error while Verifing Continuous Payments Question : " + e);
			return false;
		}

	}
	
	/**
	 * Verify Select Card Session
	 * 
	 * @param extentReport
	 * @return boolean
	 * @throws Exception
	 */
	public boolean verifySelectCardSession(ExtentTest extentReport) throws Exception {
		boolean status = true;

		try {
			if (txtSelectCard.isDisplayed()) {
				WaitUtils.waitForelementToBeClickable(driver, txtSelectCard, "Select Card Session is displayed");
				return status;
			} else {
				Log.message("Select Card Session is not displayed" + txtSelectCard);
				status = false;
				return status;
			}
		} catch (Exception e) {
			status = false;
			Log.message("Error while Verifing Select Card Session : " + e);
			return false;
		}
	}
	
	/**
	 * hover On Help Icon Of Continuous Payments Question
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfContinuousPayments(ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpContinuousPayments);
			//iconHelpContinuousPayments.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpContinuousPayments);
			/*
			 * Actions action = new Actions(driver);
			 * action.moveToElement(iconHelpContinuousPayments).click();
			 * action.perform();
			 */
			WaitUtils.waitForElement(driver, popHelpContinuousPayments);
			Log.message("Hovered on the HelpIcon Of Continuous Payments Question", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Continuous Payments Question : " + e);
		}
	}

	/**
	 * verify HelpIcon Continuous Payments Question Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconContinuousPaymentsPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpContinuousPayments);
			if (GenericUtils.verifyWebElementTextContains(popHelpContinuousPayments,
					POPUP_MSG_HELPICON_Continuous_Payments)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Continuous Payments Question : " + e);
		}
	}
	
	/**
	 * click Yes button
	 * 
	 * @param extentReport
	 * @throws Exception
	 * 
	 */
	public void clickYesButtonContinuousPayments(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, continuousPaymentsYes);
			continuousPaymentsYes.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("clicked continuous Payments Yes button in Card payment page", driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking continuous Payments Yes button in Card payment page : " + e);
		}
	}
	
	/**
	 * click No button
	 * 
	 * @param extentReport
	 * @throws Exception
	 * 
	 */
	public void clickNoButtonContinuousPayments(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, continuousPaymentsNo);
			continuousPaymentsNo.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("clicked continuous Payments No button in Card payment page", driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking continuous Payments No button in Card payment page : " + e);
		}
	}
	
	/**
	 * Get Popup Message
	 * 
	 * @return Popup Message
	 * 
	 */
	public boolean getPopupMessage(String popupMessage) {
		return GenericUtils.verifyWebElementTextContains(txtPopupMessage, popupMessage);
	}
	
	/**
	 * click Popup Message Yes button
	 * 
	 * @param extentReport
	 * @throws Exception
	 * 
	 */
	public void clickYesButtonPopupMessage(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, PopupMessageYes);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", PopupMessageYes);
			//PopupMessageYes.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("clicked Popup Message Yes button in Card payment page", driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking Popup Message Yes button in Card payment page : " + e);
		}
	}
	
	/**
	 * click Popup Message No button
	 * 
	 * @param extentReport
	 * @throws Exception
	 * 
	 */
	public void clickNoButtonPopupMessage(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, PopupMessageNo);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", PopupMessageNo);
			//PopupMessageNo.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("clicked Popup Message No button in Card payment page", driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking Popup Message No button in Card payment page : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of Previously Used Cards
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfPreviouslyUsedCards(ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPreviouslyUsedCards);
			//iconHelpContinuousPayments.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPreviouslyUsedCards);
			/*
			 * Actions action = new Actions(driver);
			 * action.moveToElement(iconHelpContinuousPayments).click();
			 * action.perform();
			 */
			WaitUtils.waitForElement(driver, popHelpPreviouslyUsedCards);
			Log.message("Hovered on the HelpIcon Of Previously Used Cards", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Previously Used Cards : " + e);
		}
	}

	/**
	 * verify HelpIcon Previously Used Cards Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPreviouslyUsedCardsPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPreviouslyUsedCards);
			if (GenericUtils.verifyWebElementTextContains(popHelpPreviouslyUsedCards,
					POPUP_MSG_HELPICON_Previously_Used_Cards)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Previously Used Cards : " + e);
		}
	}
	
	/**
	 * Get Error Message
	 * 
	 * @return Error Message
	 * 
	 */
	public boolean getErrorMessage(String errorMessage) {
		return GenericUtils.verifyWebElementTextContains(errorMag, errorMessage);
	}
	
	/**
	 * Verify Default Session
	 * 
	 * @param extentReport
	 * @return boolean
	 * @throws Exception
	 */
	public boolean verifyDefaultSession(ExtentTest extentReport) throws Exception {
		boolean status = true;

		try {
			if (txtDefault.isDisplayed()) {
				WaitUtils.waitForelementToBeClickable(driver, txtDefault, "Default Session is displayed");
				return status;
			} else {
				Log.message("Default Session is not displayed" + txtDefault);
				status = false;
				return status;
			}
		} catch (Exception e) {
			status = false;
			Log.message("Error while Verifing Default Session : " + e);
			return false;
		}

	}
	
	/**
	 * Verify Buy With Other Card button clickable
	 * 
	 * @param extentReport
	 * @return boolean
	 * @throws Exception
	 */
	public boolean verifyBuyWithOtherCardButtonClickable(ExtentTest extentReport) throws Exception {
		boolean status = true;

		try {
			if (btnBuyWithAnotherCard.isEnabled()) {
				WaitUtils.waitForelementToBeClickable(driver, btnBuyWithAnotherCard, "Buy With Other Card button able to click");
				return status;
			} else {
				Log.message("Buy With Other Card button displaing as disabled on quote page" + btnBuyWithAnotherCard);
				status = false;
				return status;
			}
		} catch (Exception e) {
			status = false;
			Log.message("Error while clicking Buy With Other Card button : " + e);
			return false;
		}

	}
	
	/**
	 * To click Check box to select existing card
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectCheckBoxForCard(String cardName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String CardRow = getAddedCardRow(cardName, extentedReport, false);
			if (CardRow == null) {
				throw new Exception(cardName + " - existing Card is not found");
			}
			String elementString = cssSelectExistingCard + CardRow + "']";
			WebElement checkBox = driver.findElement(By.cssSelector(elementString));
			checkBox.click();
			Log.message("Clicked on " + CardRow + " (th) Check box", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while selecting check box: " + e);
		}
	}
	
	/**
	 * To click Check box to Default existing card
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void defaultCheckBoxForCard(String cardName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String CardRow = getAddedCardRow(cardName, extentedReport, false);
			if (CardRow == null) {
				throw new Exception(cardName + " - existing Card is not found");
			}
			String elementString = cssDefaultCard + CardRow + "']";
			WebElement checkBox = driver.findElement(By.cssSelector(elementString));
			checkBox.click();
			Log.message("Clicked on " + CardRow + " (th) Check box", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while selecting check box: " + e);
		}
	}
	
	/**
	 * To get Added Card row
	 * 
	 * @param Card row
	 * @param Modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public String getAddedCardRow(String cardName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String CardRow = null;
			WaitUtils.waitForSpinner(driver);
			if (selectExistingCardName.size() == 0) {
				Log.message("No Card is available", driver, extentedReport, false);
			}

			for (int loopCount = 0; loopCount < selectExistingCardName.size(); loopCount++) {
				Integer row = loopCount + 1;
				String displayedCard = selectExistingCardName.get(loopCount).getText();
				if (displayedCard.contains(cardName)) {
					Log.message(cardName + "is found", driver,extentedReport, screenshot);
					return row.toString();
				}
			}
			return CardRow;
		} catch (Exception e) {
			throw new Exception("Error while getting Added Item row in Within Home : " + e);
		}
	}
	
	/**
	 * Verify Buy button clickable
	 * 
	 * @param extentReport
	 * @return boolean
	 * @throws Exception
	 */
	public boolean verifyBuyButtonClickable(ExtentTest extentReport) throws Exception {
		boolean status = true;

		try {
			if (btnBuy.isEnabled()) {
				WaitUtils.waitForelementToBeClickable(driver, btnBuy, "Buy button able to click");
				return status;
			} else {
				Log.message("Buy button displaing as disabled on quote page" + btnBuy);
				status = false;
				return status;
			}
		} catch (Exception e) {
			status = false;
			Log.message("Error while clicking Buy button : " + e);
			return false;
		}

	}
	
}
