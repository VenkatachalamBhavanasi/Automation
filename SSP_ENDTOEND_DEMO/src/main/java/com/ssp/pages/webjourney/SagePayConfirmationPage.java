package com.ssp.pages.webjourney;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
 * Sage Pay Confirmation Page
 */
public class SagePayConfirmationPage extends LoadableComponent<SagePayConfirmationPage> {

    private WebDriver driver;
    private boolean isPageLoaded;
    
    @FindBy(css = "button.btn.btn--hollow.btn--small--if-palm[value = 'back']")
    WebElement btnBack;

    @FindBy(css = ".declarative-list__description:nth-of-type(1)")
    WebElement lblDescription;

    @FindBy(css = ".declarative-list__description:nth-of-type(2)")
    WebElement lblTransactionReference;

    @FindBy(css = ".declarative-list__description:nth-of-type(3)")
    WebElement lblAmount;

    @FindBy(css = ".declarative-list__description:nth-of-type(4)")
    WebElement lblPaymentType;

    @FindBy(css = ".declarative-list__description:nth-of-type(5)")
    WebElement lblCardNumber;

    @FindBy(css = ".declarative-list__description:nth-of-type(6)")
    WebElement lblCardHolderName;

    @FindBy(css = ".declarative-list__description:nth-of-type(7)")
    WebElement lblEmail;

    @FindBy(css = "#section-billing_address")
    WebElement lblBillingAddress;

    @FindBy(css = "#section-shipping_address")
    WebElement lblShippingAddress;

    /**
     * constructor of the class
     * 
     * @param driver
     *            : Webdriver
     */
    public SagePayConfirmationPage(WebDriver driver) {
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
        // Utils.waitForPageLoad(driver);

    }// load

    public void verifySagePayConfirmPage() throws Exception {
        Log.event("Navigated Successfully to AnnualPayment Page");
    }// verifySagePayConfirmPage

    public ThankYouPage clickonPaymentConfirmation(boolean screenShot) throws Exception {
        Log.event("Navigating to Payment confirmation Page");

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        WaitUtils.waitForSpinner(driver);
        jse.executeScript("arguments[0].click()", driver.findElement(By.cssSelector("button[name='action'][value='proceed']")));               
        WaitUtils.waitForSpinner(driver);
        return new ThankYouPage(driver).get();
    }// clickonPaymentConfirmation
    
    public SagePayDetailsPage clickBack() throws Exception {

        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(btnBack));
        btnBack.click();
        Log.event("Clicked on Back Button");
        return new SagePayDetailsPage(driver).get();
    }//Click back

}