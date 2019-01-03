package com.ssp.pages.webjourney;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.ssp.support.*;

/**
 * Payment Page
 */
public class PaymentPage extends LoadableComponent<PaymentPage> {

    private WebDriver driver;
    private boolean isPageLoaded;
    public String radio_button_value;
    public String radPaymentMethod = "div#p4_QUE_FE03B8B4E69C4F4615536>div>div>label";
    public String radInstallment = "div#p4_QUE_FE03B8B4E69C4F4616230>div>div>label"; // Bank Details radio button
    public String radBillingAddress = "div#p4_QUE_16066C729BDC294C8881>div>div>label";
    public String radUKResident = "div#p4_QUE_7B73D9DC642171EC40339>div>div>label";
    public String radBankrupcy = "div#p4_QUE_7B73D9DC642171EC40258>div>div>label";
    public String agreeCheckbox = "div#QUE_7B73D9DC642171EC40046>div";
    public String checkbox_value;

    /**********************************************************************************************
     ********************************* WebElements of Payment Page ***********************************
     **********************************************************************************************/

    @FindBy(css = "input.input-lg[name='HOMEINSURANCE[1].YOURDETAILS[1].ABOUTYOU[1].EMAIL']")
    WebElement txtEmail;

    @FindBy(css = "input.input-lg[name='HOMEINSURANCE[1].YOURDETAILS[1].ABOUTYOU[1].CONFIRMEMAIL']")
    WebElement txtConfirmEmail;

    @FindBy(css = "input.input-lg[name='HOMEINSURANCE[1].YOURDETAILS[1].ABOUTYOU[1].TELEPHONE']")
    WebElement txtPhone;

    @FindBy(css = "label[for='QUE_FE03B8B4E69C4F4616230_0']")
    WebElement txtInstallments;
    
    @FindBy(css = "input.input-lg[name='HOMEINSURANCE[1].PAYMENT[1].OTHERBANKACCOUNT[1].BANKACCOUNTHOLDERNAME']")
    WebElement txtActHolder;

    @FindBy(css = "input.input-lg[name='HOMEINSURANCE[1].PAYMENT[1].OTHERBANKACCOUNT[1].SORTCODE']")
    WebElement txtSortCode;

    @FindBy(css = "input.input-lg[name='HOMEINSURANCE[1].PAYMENT[1].OTHERBANKACCOUNT[1].BANKACCOUNT']")
    WebElement txtBankAccount;

    @FindBy(css = "a.action-button[title='Forward']")
    WebElement btnConfirmation;
    
	@FindBy(css = "span[id='QUE_FE03B8B4E69C4F4615828']")
    WebElement lblFirstPayment;
    
    @FindBy(css = "a.back-button[title = 'Back']")
    WebElement btnBack;
	
	@FindBy(css = "input[id='QUE_93F4D8F2D63B4990152604']")
    WebElement txtHouseNumberOrName;
    
    @FindBy(css = "button[id='BUT_CCA61F95A8528B8626095']")
    WebElement btnManuallAddress;

    @FindBy(css = "input[id='QUE_93F4D8F2D63B4990152616']")
    WebElement txtPostCode;

    @FindBy(css = "button[id='BUT_9547744E5D4FFB161793']")
    WebElement btnFindAddress;

    @FindBy(css = "button.address-button[title='Edit Address'][style='cursor: pointer;']")
    WebElement btnEditAddress;

	@FindBy(css = "input[id='QUE_9547744E5D4FFB161738']")
    WebElement txtHouseNumberOrName_Annual;
    
    @FindBy(css = "input[id='QUE_93F4D8F2D63B4990152616']")
    WebElement txtPostCode_Annual;

    @FindBy(css = "button[id='BUT_93F4D8F2D63B4990152854']")
    WebElement btnFindAddress_Annual;

    @FindBy(css = "label[for='QUE_16066C729BDC294C8881_0']")
    WebElement BillingAddressYes; 

    @FindBy(css = "label[for='QUE_FE03B8B4E69C4F4615536_0']")
    WebElement AnnualPayment; 

    /**********************************************************************************************
     ********************************* WebElements of Payment Page - Ends ****************************
     **********************************************************************************************/
    /**
     * constructor of the class
     * 
     * @param driver
     *            : Webdriver
     * @throws Exception 
     */
    public PaymentPage(WebDriver driver) throws Exception {
    	try {
        	this.driver = driver;
	        ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, WaitUtils.maxElementWait);
	        PageFactory.initElements(finder, this);
    	}
    	catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }// HomePage

    @Override
    protected void isLoaded() {

        try {
	    	if (!isPageLoaded) {
	            Assert.fail();
	        }
	
	        // To handle home page loading properly in IE browsers
	        WaitUtils.waitForPageLoad(driver);
	        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class).withMessage("Payment Page did not open up.")).until(ExpectedConditions.visibilityOf(txtEmail));
	        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class).withMessage("Payment Page did not open up.")).until(ExpectedConditions.visibilityOf(txtConfirmEmail));
	        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class).withMessage("Payment Page did not open up.")).until(ExpectedConditions.visibilityOf(txtPhone));
        }
        catch(Exception e) {
        	try {
				throw new Exception(e.getMessage());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        
    }// isLoaded

    @Override
    protected void load() {

        isPageLoaded = true;
        WaitUtils.waitForElement(driver, txtEmail);

    }// load

    public void verifyPaymentPage() throws Exception {
    	WaitUtils.waitForElement(driver, txtEmail, WaitUtils.maxElementWait);
    	WaitUtils.waitForElement(driver, txtEmail, WaitUtils.maxElementWait);
        Log.event("Navigated Successfully to Payment Page");
    }// verifyPaymentPage

    public void enterEmail(String email) throws Exception{
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(1000, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtEmail));
        //txtEmail.clear();
        //txtEmail.sendKeys(email);
        //JavascriptExecutor executor = (JavascriptExecutor) driver;
        //executor.executeScript("txtEmail.setAttribute('value', email)");
        Actions action = new Actions(driver);
        action.doubleClick(txtEmail).build().perform();
        action.sendKeys(txtEmail, email).build().perform();
        txtEmail.sendKeys(Keys.chord(Keys.SHIFT, Keys.TAB));
    	Thread.sleep(1000);
        Log.event("Entered the Email: " + email);
    }// enterEmail

    public void enterConfirmEmail(String confirmemail) throws Exception {
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(1000, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Confirm Email text box")).until(ExpectedConditions.visibilityOf(txtConfirmEmail));
        //txtConfirmEmail.clear();
        //txtConfirmEmail.sendKeys(confirmemail);
        //JavascriptExecutor executor = (JavascriptExecutor) driver;
        //executor.executeScript("txtConfirmEmail.setAttribute('value', confirmemail)");
        Actions action = new Actions(driver);
        action.doubleClick(txtConfirmEmail).build().perform();
        action.sendKeys(txtConfirmEmail, confirmemail).perform();
        txtConfirmEmail.sendKeys(Keys.chord(Keys.SHIFT, Keys.TAB));
    	Thread.sleep(1000);
        Log.event("Entered the ConfirmEmail: " + confirmemail);
    }// enterConfirmEmail

    public void enterPhone(String Phone) throws Exception {
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Telephone Number text box")).until(ExpectedConditions.visibilityOf(txtPhone));
        //txtPhone.clear();
        //txtPhone.sendKeys(Phone);
        //JavascriptExecutor executor = (JavascriptExecutor) driver;
        //executor.executeScript("txtPhone.setAttribute('value', Phone)");
        Actions action = new Actions(driver);
        action.doubleClick(txtPhone).build().perform();
        action.sendKeys(txtPhone, Phone).perform();
        txtPhone.sendKeys(Keys.chord(Keys.SHIFT, Keys.TAB));
    	Log.event("Entered the Phone: " + Phone);
    }// enterPhone

    public void selectPaymentMethod(String paymentMethod) throws Exception {
    	(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Payment Type label")).until(ExpectedConditions.visibilityOf(AnnualPayment));
        selectRadioButton(radPaymentMethod, paymentMethod);
        Log.event("Selected Payment Method :" + paymentMethod);
    }// selectPaymentMethod

    public void selectBillingAddress(String billingAddress) throws Exception {
    	(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Billing Address label")).until(ExpectedConditions.visibilityOf(BillingAddressYes));
        selectRadioButton(radBillingAddress, billingAddress);
        Log.event("Selected Billing Address :" + billingAddress);
    }// selectBillingAddress

    public void selectInstallment(String installment) throws Exception {
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Installments label")).until(ExpectedConditions.visibilityOf(txtInstallments));
        selectRadioButton(radInstallment, installment);
        Log.event("Selected Installment - Bank Details for monthly :" + installment);
    }// selectInstallment - Bank Details for monthly

    public void enterActHolderName(String actHolderName) {
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Account Holder text box")).until(ExpectedConditions.visibilityOf(txtActHolder));
        //txtActHolder.clear();
        //txtActHolder.sendKeys(actHolderName);
        //JavascriptExecutor executor = (JavascriptExecutor) driver;
        //executor.executeScript("txtActHolder.setAttribute('value', actHolderName)");
        //Actions action = new Actions(driver);
        //Utils.SetToOnScreen(txtActHolder);
        
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].value='"+actHolderName+"';", txtActHolder);
        
        //action.sendKeys(txtActHolder, actHolderName).build().perform();
        //txtActHolder.sendKeys(Keys.chord(Keys.SHIFT, Keys.TAB));
    	Log.event("Entered the Policy Holder name: " + actHolderName);
    }// enterActHolderName

    public void enterSortCode(String SortCode) {
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Sort Code text box")).until(ExpectedConditions.visibilityOf(txtSortCode));
        //txtSortCode.clear();
        //txtSortCode.sendKeys(SortCode);
        //JavascriptExecutor executor = (JavascriptExecutor) driver;
        //executor.executeScript("txtSortCode.setAttribute('value', SortCode)");
        //Actions action = new Actions(driver);
        //Utils.SetToOnScreen(txtSortCode);

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].value='"+SortCode+"';", txtSortCode);
        
        //action.sendKeys(txtSortCode, SortCode).build().perform();
        //txtSortCode.sendKeys(Keys.chord(Keys.SHIFT, Keys.TAB));
    	Log.event("Entered the sortcode: " + SortCode);
    }// enterSortCode

    public void enterBankAccount(String BankAccount) {
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Bank Account text box")).until(ExpectedConditions.visibilityOf(txtBankAccount));
        //Actions action = new Actions(driver);
        //Utils.SetToOnScreen(txtBankAccount);

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].value='"+BankAccount+"';", txtBankAccount);
        
        //action.sendKeys(txtBankAccount, BankAccount).build().perform();
        //txtBankAccount.sendKeys(Keys.chord(Keys.SHIFT, Keys.TAB));
    	Log.event("Entered the Bank Account: " + BankAccount);
    }// enterBankAccount

    public void selectUKResident(String ukresident) throws Exception {
        selectRadioButton(radUKResident, ukresident);
        Log.event("Selected UK Resident :" + ukresident);
    }// selectUKResident

    public void selectBankrupcy(String bankrupcy) throws Exception {
        selectRadioButton(radBankrupcy, bankrupcy);
        Log.event("Selected Bankrupcy :" + bankrupcy);
    }// selectBankrupcy

    public void agreeStatement(String option) {
        List<WebElement> options = driver.findElements(By.cssSelector(agreeCheckbox));

        for (int i = 0; i < options.size(); i++) {
            checkbox_value = options.get(i).getText();
            if (checkbox_value.contains(option) && options.get(i).isEnabled()) {

                JavascriptExecutor jse = (JavascriptExecutor)driver;
                jse.executeScript("arguments[0].click()", options.get(i).findElement(By.cssSelector("span.checkbox")));               
                
                break;
            }
        } // agreeStatement
    }// agreeStatement

    public void gotoPaymentPage() {
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtEmail));
        txtEmail.click();
        Log.event("Clicked on Summary Button");
    }// gotoPaymentPage

    private void selectRadioButton(String locator, String option) {
        List<WebElement> buttonOptions = driver.findElements(By.cssSelector(locator));
        for (int i = 0; i < buttonOptions.size(); i++) {
            radio_button_value = buttonOptions.get(i).getText();
            if (radio_button_value.equals(option)) {
            	(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(buttonOptions.get(i)));
                
                JavascriptExecutor jse = (JavascriptExecutor)driver;
                jse.executeScript("arguments[0].click()", buttonOptions.get(i).findElement(By.cssSelector("span.radio")));               
                
                radio_button_value = null;
                break;
            }
        }
    }// selectRadioButton

    public ConfirmationPage gotoUserRegPage(ExtentTest extentedReport, boolean screenShot) throws Exception {
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(btnConfirmation));
        Log.message("Entered data in Monthly Payment page", driver, extentedReport, screenShot);
        Actions action = new Actions(driver);
        action.moveToElement(btnConfirmation).build().perform();
        action.click(btnConfirmation).build().perform();
        Log.event("Clicked on Confirmation Button");
        return new ConfirmationPage(driver).get();

    }// gotoUserRegPage

    public SignInPage gotoSignInPage(ExtentTest extentedReport, boolean screenShot) throws Exception {
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(btnConfirmation));
        Log.message("Clicking on Confirmation Button", driver, extentedReport, screenShot);
        Actions action = new Actions(driver);
        action.moveToElement(btnConfirmation).build().perform();
        action.click(btnConfirmation).build().perform();
        Log.event("Clicked on Confirmation Button");
        return new SignInPage(driver).get();
    }// gotoSignInPage

    public SagePayDetailsPage clickconfirm(ExtentTest extentedReport, boolean screenShot) throws Exception {

        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(btnConfirmation));
        Log.message("Clicking on Confirmation Button", driver, extentedReport, true);
        Actions action = new Actions(driver);
        action.moveToElement(btnConfirmation).build().perform();
        action.click(btnConfirmation).build().perform();
        Log.event("Clicked on Confirmation Button");
        return new SagePayDetailsPage(driver).get();
    }//Click confirm and handing over driver to Confirmation page

    public SummaryPage clickBack() throws Exception {

        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(btnBack));
        Actions action = new Actions(driver);
        action.moveToElement(btnBack).click().build().perform();
        Log.event("Clicked on Back Button");
        return new SummaryPage(driver).get();
    }//Click back
    
}