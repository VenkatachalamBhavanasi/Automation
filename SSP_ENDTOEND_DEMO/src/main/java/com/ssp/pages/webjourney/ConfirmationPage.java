package com.ssp.pages.webjourney;

import java.util.List;
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
 * Confirmation Page
 */
public class ConfirmationPage extends LoadableComponent<ConfirmationPage> {

    private WebDriver driver;
    private boolean isPageLoaded;
    public String radio_button_value;
    public String confirmCheckbox = "div#QUE_BAE601C919E3AD945563>div";
    public String checkbox_value;

    /**********************************************************************************************
     ********************************* WebElements of Confirmation Page - Monthly ***********************************
     **********************************************************************************************/

    @FindBy(css = "h3#HEAD_1089FCAE5AFABD804088.quote-summary")
    WebElement txtImpDocuments;

    @FindBy(css = "a.action-button[title='Forward']")
    WebElement btnGetCovered;
    
    /*@FindBy(css = "a.action-button[title='Forward']")
    WebElement btnConfirmation;*/ //Not required

    /**********************************************************************************************
     ********************************* WebElements of Confirmation Page - Monthly - Ends ****************************
     **********************************************************************************************/
    /**
     * constructor of the class
     * 
     * @param driver
     *            : Webdriver
     */
    public ConfirmationPage(WebDriver driver) {
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
        WaitUtils.waitForElement(driver, txtImpDocuments);

    }// load

    public void verifyConfirmationPage() throws Exception {
    	WaitUtils.waitForElement(driver, txtImpDocuments, WaitUtils.maxElementWait);
    	WaitUtils.waitForElement(driver, btnGetCovered, WaitUtils.maxElementWait);
        Log.event("Navigated Successfully to Confirmation Page");
    }// verifyConfirmationPage

    public void confirmStatement(String option) {
        List<WebElement> options = driver.findElements(By.cssSelector(confirmCheckbox));

        for (int i = 0; i < options.size(); i++) {
            checkbox_value = options.get(i).getText();
            if (checkbox_value.contains(option) && options.get(i).isEnabled()) {
                options.get(i).findElement(By.cssSelector("span.checkbox")).click();
                break;
            }
        }
    }// confirmStatement

    public ThankYouPage confirmPayment(ExtentTest extentedReport,boolean screenShot) throws Exception {
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to Get Covered Button")).until(ExpectedConditions.visibilityOf(btnGetCovered));
        Log.message("Navigating to Your Quote Page", driver, extentedReport);
        try {

            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("arguments[0].click()", btnGetCovered);               
            
        } catch (Exception e) {
            try {

                JavascriptExecutor jse = (JavascriptExecutor)driver;
                jse.executeScript("arguments[0].click()", btnGetCovered);               
                
            } catch (Exception f) {
                Log.fail("Problem in Navigating to Thank you Page..!");
            }
        }
        Log.event("Clicked on Get Quote:");
        return new ThankYouPage(driver).get();
    }// confirmPayment   

}