package com.ssp.pages.selfservice;

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
import com.ssp.support.Log;
import com.ssp.support.Utils;
import com.ssp.support.WaitUtils;

/**
 * Login Page
 */
public class LoginPage extends LoadableComponent<LoginPage> {

    private WebDriver driver;
    private boolean isPageLoaded;
    ExtentTest extentedReport;

    /**********************************************************************************************
     ********************************* WebElements of SS Login Page ***********************************
     **********************************************************************************************/
    @FindBy(css = "input#email_address")
    WebElement txtEmaiAdd;

    @FindBy(css = "input#password")
    WebElement txtPswd;

    @FindBy(css = "button#sign_in")
    WebElement btnSignIn;
    
    @FindBy(css = "button.btn.btn-black.cancel-SIGN_IN")
    WebElement btnCancel;
    
    @FindBy(css = "button#closeModal")
    WebElement btnCloseModal;//cross (x)icon
    
    @FindBy(css = "div.checkbox.checkbox-info.checkbox-inline>label")
    WebElement checkBoxremMyEmail;
    
    @FindBy(css = "i.fa.fa-2x.fa-info-circle")
    WebElement info;

    @FindBy(css = "p.errorClass")
    WebElement txtErorMessage;

    /**********************************************************************************************
     ********************************* WebElements of Login Page - Ends ****************************
     **********************************************************************************************/

    public LoginPage(WebDriver driver) {
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

    }// load

    /**
     * Verify HomePage
     * 
     * @throws Exception
     *             : Custom Exception Message
     */
    public boolean verifyLoginPage() throws Exception {
        boolean loginpage = true;
        WaitUtils.waitForElement(driver, txtEmaiAdd, 20);
        WaitUtils.waitForElement(driver, btnSignIn, 20);
        if (!btnSignIn.isDisplayed())
            loginpage = false;
        return loginpage;
    }// verifyLoginPage

    public void enterEmailID(String emailid) {
        txtEmaiAdd.clear();
        txtEmaiAdd.sendKeys(emailid);
        Log.event("Entered the Email ID: " + emailid);
    }// enterEmailID

    public void enterPassword(String password) {
        txtPswd.clear();
        txtPswd.sendKeys(password);
        Log.event("Entered the Email ID: " + password);
    }// enterPassword

    public void verifyLoaded() {
    	String dynamicLoad = ".xs-msg-block-active-bg spinning-on-load-bg-table-active";
		(new WebDriverWait(driver, 30).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to loading image")).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(dynamicLoad )));
    }// enterPassword

    public CustomerDashBoardPage clickSignIn() throws Exception {
        try {
            if (btnSignIn.isDisplayed()) {
                btnSignIn.click();
                verifyLoaded();
            } else {
                throw new Exception("Sign In Button is not clickable");
            }
            
        } catch (Exception e) {
            try {
                if (txtErorMessage.isDisplayed() && (txtErorMessage.getText().contains("We do not recognise your details"))) {

                    throw new Exception("Invalid Login Details");
                }
            } catch (Exception f) {
                throw new Exception("Error in Login!!");
            }
        }
        Log.event("Clicked on Sign In Button");
        return new CustomerDashBoardPage(driver).get();
    }// enterPassword
    
}// LoginPage
