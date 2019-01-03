package com.ssp.pages.webjourney;

import java.util.concurrent.TimeUnit;

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

import com.ssp.support.Log;
import com.ssp.support.Utils;
import com.ssp.support.WaitUtils;

/**
 * Thank You Page
 */
public class ThankYouPage extends LoadableComponent<ThankYouPage> {

    private WebDriver driver;
    private boolean isPageLoaded;

    /**********************************************************************************************
     ********************************* WebElements of Thank You Page ***********************************
     **********************************************************************************************/
    @FindBy(css = "h3.quote-summary#HEAD_398053B6E2189E741873")
    WebElement txtThankyoumsg;

    @FindBy(css = "div>span.quote-number")
    WebElement txtQuoteNumber;

    @FindBy(css = "span.quote-number span")
    WebElement ThankPage_QuoteNumber; 

    @FindBy(linkText = "click here")
    WebElement lnkClickHere;

    @FindBy(linkText = "3* Policy Summary")
    WebElement lnk3StarSummary;

    @FindBy(linkText = "3* Policy Wording ")
    WebElement lnk3StarWording;

    @FindBy(linkText = "5* Policy Summary")
    WebElement lnk5StarSummary;

    @FindBy(linkText = "5* Policy Wording ")
    WebElement lnk5StarWording;

    /**********************************************************************************************
     ********************************* WebElements of Thank You Page - Ends ****************************
     **********************************************************************************************/
    /**
     * constructor of the class
     * 
     * @param driver
     *            : Webdriver
     */
    public ThankYouPage(WebDriver driver) {
        this.driver = driver;
        ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, WaitUtils.maxElementWait);
        PageFactory.initElements(finder, this);
    }// HomePage

    @Override
    protected void isLoaded() {

        if (!isPageLoaded) {
            Assert.fail();
        }
        if (!txtThankyoumsg.getText().contains("Thanks for insuring with Asda Home Insurance")) {
            Assert.fail("Thank you page is not loaded");
        }

    }// isLoaded

    @Override
    protected void load() {

        isPageLoaded = true;
        WaitUtils.waitForElement(driver, txtThankyoumsg);

    }// load

    public void verifyThankYouPage() throws Exception {
    	WaitUtils.waitForElement(driver, txtThankyoumsg, 20);
    	WaitUtils.waitForElement(driver, txtQuoteNumber, 20);
        Log.event("Navigated Successfully to ThankYou Page");

    }// verifyYourQoutePage

    public String GetQuoteNum() throws Exception {

        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find quote number on thank you page")).until(ExpectedConditions.visibilityOf(ThankPage_QuoteNumber));

        return ThankPage_QuoteNumber.getText();
    }
    
    public HomePage clickClickHereLink() throws Exception {

        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(lnkClickHere));
        lnkClickHere.click();
        Log.event("Clicked on Click Here Link");
        return new HomePage(driver).get();
    }//Click back

}
