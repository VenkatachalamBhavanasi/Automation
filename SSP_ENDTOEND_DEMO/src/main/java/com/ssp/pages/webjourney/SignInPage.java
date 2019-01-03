package com.ssp.pages.webjourney;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
 * Sign In Page
 */
public class SignInPage extends LoadableComponent<SignInPage> {

    private WebDriver driver;
    private boolean isPageLoaded;
    public String agreeCheckbox = "div#p4_SingleSelectCheckbox_imp>div>div>div";// not required
    public String checkbox_value;//not required

    /**********************************************************************************************
     ********************************* WebElements of Sign In Page ***********************************
     **********************************************************************************************/
    @FindBy(css = "input[readonly='readonly'][name='HOMEINSURANCE[1].SAVEQUOTE[1].EMAIL_READONLY']")
    WebElement txtEmail;

    @FindBy(css = "input[type='password'][name='HOMEINSURANCE[1].SAVEQUOTE[1].PASSWORD']")
    WebElement txtPassword;

    @FindBy(css = "button[title='Sign In']")
    WebElement btnSignIn;
    
    @FindBy(css = "a.pwd_reset[value='Forgot my password']")
    WebElement linkForgetPassword;  // Method to forgot password is now not included- need to include while writing script for forgot password in SS.
    
    @FindBy(css = "a.back-button[title='Back']")
    WebElement btnBack;

    /**********************************************************************************************
     ********************************* WebElements of Sign In Page ****************************
     **********************************************************************************************/
    /**
     * constructor of the class
     * 
     * @param driver
     *            : Webdriver
     */
    public SignInPage(WebDriver driver) {
        this.driver = driver;
        ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, WaitUtils.maxElementWait);
        PageFactory.initElements(finder, this);
    }// HomePage

    @Override
    protected void isLoaded() {

        if (!isPageLoaded) {
            Assert.fail();
        }

    }// isLoaded

    @Override
    protected void load() {

        isPageLoaded = true;
        WaitUtils.waitForElement(driver, txtPassword);

    }// load

    public void verifySignInPage() throws Exception {
    	WaitUtils.waitForElement(driver, txtPassword, 20);
        Log.event("Navigated Successfully to SignIn Page");

    }// verifySignInPage

    public ConfirmationPage gotoConfirmationPage(ExtentTest extentedReport, boolean screenShot) throws Exception {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(btnSignIn));
        Log.message("Clicking on Signin Button", driver, extentedReport, screenShot);
        btnSignIn.click();
        return new ConfirmationPage(driver).get();
    }// gotoConfirmationPage

    public SagePayDetailsPage makeAnnualPayment(ExtentTest extentedReport, boolean screenShot) throws Exception {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(btnSignIn));
        Log.message("Clicking on Signin Button", driver, screenShot);
        btnSignIn.click();
        Log.event("Clicked on Summary Button");
        return new SagePayDetailsPage(driver).get();
    }// makeAnnualPayment

    public YourQuotePage saveQuote(ExtentTest extentedReport, boolean screenShot) throws Exception {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(btnSignIn));
        Log.message("Clicking on Signin Button", driver, extentedReport, screenShot);
        btnSignIn.click();
        Log.event("Clicked on Signin Button");
        return new YourQuotePage(driver).get();
    }// makeAnnualPayment

   /* public void enterEmail(String Email) {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtEmail));
        txtEmail.clear();
        txtEmail.sendKeys(Email);
        Log.event("Entered Email: " + Email);
    }// enterEmail */ //Not required - Read only mode

    public void enterPassword(String Password) {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtPassword));
        txtPassword.clear();
        txtPassword.sendKeys(Password);
        Log.event("Entered the Password: " + Password);
    }// enterPassword

   public void agreeStatement(String option) {
        List<WebElement> options = driver.findElements(By.cssSelector(agreeCheckbox));

        for (int i = 0; i < options.size(); i++) {
            checkbox_value = options.get(i).getText();
            if (checkbox_value.contains(option)) {
                options.get(i).findElement(By.cssSelector("span.checkbox")).click();
                break;
            }
        }
    }// agreeStatement - Not required
   
   public PaymentPage clickBack() throws Exception {

       (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(btnBack));
       btnBack.click();
       Log.event("Clicked on Back Button");
       return new PaymentPage(driver).get();
   }//Click back
   
// Method to forgot password is now not included- need to include while writing script for forgot password.
}