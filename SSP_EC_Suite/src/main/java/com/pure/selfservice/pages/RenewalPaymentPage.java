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

public class RenewalPaymentPage extends LoadableComponent<RenewalPaymentPage> {

	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;
	public String cssSelectExistingCard = "[for='C2__C1__QUE_554E9AF6046CC3A4379681_0_R";
	public final String msgForCancelPopup = "Your renewal has not been processed";
	public String checkbox = "checkbox checkbox-info checkbox-inline remove-label checkbox-used-cards";
	public String POPUP_MSG_HELPICON_Continuous_Payments = "Continuous Payment Authority is a term used to describe where you have given permission for regular collections to be made using your payment card. This means that when your policy is due for renewal, we will automatically collect payment for the renewal premium";
	public String POPUP_MSG_HELPICON_Previously_Used_Cards = "The card you select as a default will be made your default payment method for continuous payments and can be different to the card you choose to use for this transaction";
	public String cssDefaultCard = "[for='C2__C1__QUE_FA30D94E9C7869E0619459_0_R";
	
	@FindBy(css = "#C2__C1__HEAD_82C3F84B8B5F77B74737")
	WebElement txtTitleRenewMyPolicy;

	@FindBy(css = "#C2__C1__HEAD_82C3F84B8B5F77B74814")
	WebElement txtTitlePleaseChoosePaymentCard;

	@FindBy(css = "#C2__C1__HEAD_82C3F84B8B5F77B74822")
	WebElement valuePaymentAmount;

	@FindBy(css = "#C2__C1__HEAD_82C3F84B8B5F77B74819")
	WebElement txtPaymentAmount;

	@FindBy(css = "#C2__C1__HEAD_82C3F84B8B5F77B74819")
	WebElement txtPreviouslyUsedCards;

	@FindBy(css = "button[title*='Buy with Another Card']")
	WebElement btnBuyWithAnotherCard;

	@FindBy(css = "#C2__C1__BUT_82C3F84B8B5F77B74846")
	WebElement btnCancel;
	
	@FindBy(css = "#C2__C1__BUT_9B4F5996E9ED73EC2711919")
	WebElement btnClose;
	
	@FindBy(css = "#C2__C1__p1_HEAD_9B4F5996E9ED73EC2711915")
	WebElement msgInCancelPopup;

	@FindBy(css = "#C2__C1__ID_PAYBYCARD_BUY_BTN")
	WebElement btnBuy;

	@FindBy(css = "#C2__C1__FS_QUE_554E9AF6046CC3A4379681_R > div > label")
	WebElement btnSelectCard;
	
	@FindBy(css = "[id *= C2__C1__QUE_82C3F84B8B5F77B74831_R]")
	List<WebElement> selectExistingCardName;
	
	@FindBy(css = "[id *= C2__C1__QUE_82C3F84B8B5F77B74833_R]")
	List<WebElement> selectExistingCardNumber;
	
	@FindBy(css = "[id *= C2__C1__QUE_82C3F84B8B5F77B74835_R]")
	List<WebElement> selectExistingCardExpiryDate;
	
	@FindBy(css = "[id*='C2__C1__QUE_FA30D94E9C7869E0360808_0'] + span")
	WebElement continuousPaymentsYes;
	
	@FindBy(css = "[id*='C2__C1__QUE_FA30D94E9C7869E0360808_1'] + span")
	WebElement continuousPaymentsNo;
	
	@FindBy(css = "#C2__C1__HELP_HEAD_FA30D94E9C7869E0570240")
	WebElement iconHelpContinuousPayments;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpContinuousPayments;
	
	@FindBy(css = "#C2__C1__p1_QUE_554E9AF6046CC3A4379681")
	WebElement txtSelectCard;
	
	@FindBy(css = "#C2__C1__p1_HEAD_6FDDCE1046562C1B1613453")
	WebElement txtPopupMessage;
	
	@FindBy(css = "[id*='C2__C1__BUT_6FDDCE1046562C1B1613458']")
	WebElement PopupMessageYes;
	
	@FindBy(css = "[id*='C2__C1__BUT_6FDDCE1046562C1B1613464']")
	WebElement PopupMessageNo;
	
	@FindBy(css = "#C2__C1__HELP_HEAD_3C6FC801BAB6424B840287")
	WebElement iconHelpPreviouslyUsedCards;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPreviouslyUsedCards;
	
	@FindBy(css = "#C2__C1__p1_HEAD_3C6FC801BAB6424B1180874")
	WebElement errorMag;
	
	@FindBy(css = "#C2__C1__p1_QUE_FA30D94E9C7869E0619459")
	WebElement txtDefault;
	

	/*********************************
	 * WebElements of RenewalPaymentPage - Ends *************************
	 **********************************************************************************************/

	/**
	 * 
	 * Constructor class for Renew My Policy Page Here we initializing the driver
	 * for page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */

	public RenewalPaymentPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void load() {
		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
		WaitUtils.waitForElement(driver, txtTitlePleaseChoosePaymentCard);

	}

	@Override
	protected void isLoaded() throws Error {
		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(txtTitleRenewMyPolicy.isDisplayed()))) {
			Log.fail("Renewal payment page did not open up. Error Page is displayed.", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);

	}

	/**
	 * click Buy button
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public void clickBuyButton(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnBuy);
			btnBuy.click();

			WaitUtils.waitForSpinner(driver);
			Log.message("Buy button clicked on Payment page", driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Buy button is not clicked on Payment page : " + e);
		}
	}

	/**
	 * click BuyWithOtherCard button
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public CardDetailsPage clickBuyWithOtherCard(ExtentTest extentedReport, boolean screenshot) throws Exception {
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
	 *//*
	public void clickSelectInPayByCard(ExtentTest extentReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnSelectCard);
			btnSelectCard.click();
			Log.message("Clicked on elect in Pay By Card section", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking the previously used checkbox : " + e);
		}
	}*/

	/**
	 * click cancel button
	 * 
	 * @param extentReport
	 * @throws Exception
	 * 
	 */
	public void clickCancelButton(ExtentTest extentedReport) throws Exception {
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
	 * Get Total premium 
	 * 
	 * @return extra features
	 * @throws Exception 
	 * 
	 */
	public boolean getInsurerPremiumAmount(String premiumAmount) {
		return GenericUtils.verifyWebElementTextContains(valuePaymentAmount, premiumAmount);
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
	 * To get Existing Card Name
	 *
	 * @param cardName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 *
	 */
	public boolean verifyExistingCardName(String cardName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean status = false;
			WaitUtils.waitForSpinner(driver);
			if (selectExistingCardName.size() == 0) {
				Log.message("No Card is available", driver, extentedReport, false);
			}
			for (int loopCount = 0; loopCount < selectExistingCardName.size(); loopCount++) {
				//Integer row = loopCount + 1;
				String existingCardName = selectExistingCardName.get(loopCount).getText();
				if (existingCardName.contains(cardName)) {
					Log.message(cardName + "is found", driver,extentedReport, screenshot);
					//return row.toString();
					status = true;
				}
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while getting Card Name : " + e);
		}
	}
	
	/**
	 * To get Existing Card Number
	 *
	 * @param cardName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 *
	 */
	public boolean verifyExistingCardNumber(String cardNumber, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean status = false;
			WaitUtils.waitForSpinner(driver);
			if (selectExistingCardNumber.size() == 0) {
				Log.message("No Card is available", driver, extentedReport, false);
			}
			for (int loopCount = 0; loopCount < selectExistingCardNumber.size(); loopCount++) {
				//Integer row = loopCount + 1;
				String existingCardNumber = selectExistingCardNumber.get(loopCount).getText();
				if (existingCardNumber.contains(cardNumber)) {
					Log.message(cardNumber + "is found", driver,extentedReport, screenshot);
					//return row.toString();
					status = true;
				}
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while getting Card Number : " + e);
		}
	}
	
	/**
	 * To get Existing Card Expiry Date
	 *
	 * @param cardName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 *
	 */
	public boolean verifyExistingCardExpiryDate(String cardExpiryDate, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean status = false;
			WaitUtils.waitForSpinner(driver);
			if (selectExistingCardExpiryDate.size() == 0) {
				Log.message("No Card is available", driver, extentedReport, false);
			}
			for (int loopCount = 0; loopCount < selectExistingCardExpiryDate.size(); loopCount++) {
				//Integer row = loopCount + 1;
				String existingCardExpiryDate = selectExistingCardExpiryDate.get(loopCount).getText();
				if (existingCardExpiryDate.contains(cardExpiryDate)) {
					Log.message(cardExpiryDate + "is found", driver,extentedReport, screenshot);
					//return row.toString();
					status = true;
				}
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while getting Card Expiry Date : " + e);
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
	public boolean verifySelectedCheckBoxForCard(String cardName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			boolean status = false;
			String CardRow = getAddedCardRow(cardName, extentedReport, false);
			if (CardRow == null) {
				throw new Exception(cardName + " - existing Card is not found");
			}
			String elementString = cssSelectExistingCard + CardRow + "']";
			WebElement checkBox = driver.findElement(By.cssSelector(elementString));
			if(checkBox.getAttribute("for").contains(""))
			{
				status = true;
				Log.message("Credit Card is Selected default");
			}
			else
			{
				Log.message("Credit Card is not Selected default");
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while verifying the selected check box: " + e);
		}
	}
	
	/**
	 * Verify  Note For Cancel popup
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
	
	/**
	 * Verify Buy With Other Card button clickable
	 * 
	 * @param extentReport
	 * @return boolean
	 * @throws Exception
	 */
	public boolean verifyBuyWithOtherCardButtonClickable(ExtentTest extentReport) throws Exception {

		boolean status = false;
		if (btnBuyWithAnotherCard.getAttribute("class").contains("green")) {
			status = true;
			Log.message("Buy with Another Card button is Selected default");
		}
		else 
		{
			Log.message("Buy With Other Card button displaing as disabled on quote page" + btnBuyWithAnotherCard);
		}
		return status;
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
			PopupMessageYes.click();
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
			PopupMessageNo.click();
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

}