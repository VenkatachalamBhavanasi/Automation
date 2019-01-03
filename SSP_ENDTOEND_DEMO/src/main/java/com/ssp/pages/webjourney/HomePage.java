package com.ssp.pages.webjourney;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;
import com.ssp.support.*;

/**
 * WJ Home Page
 */
public class HomePage extends LoadableComponent<HomePage> {

    private WebDriver driver;
    private String sspURL;
    private boolean isPageLoaded;

    /**********************************************************************************************
     ********************************* WebElements of WJ Home Page ***********************************
     **********************************************************************************************/

    @FindBy(css = "a.btn.greybar-greenbtn[href='https://hood-test.ssp-hosting.com/HomeInsurance']")
    WebElement lnkGetQuote;
    
    @FindBy(css = "a.btn.greybar-bluebtn[href='https://hood-test.ssp-hosting.com/SIaaS-SSC']")
    WebElement lnkRetriveQuote;
    
    @FindBy(css = "a.btn.greybar-bluebtn[href='combined.html']")
    WebElement lnkFindMoreBandC;
    
    @FindBy(css = "a.btn.greybar-bluebtn[href='buildings.html']")
    WebElement lnkFindMoreOnlyB;
    
    @FindBy(css = "a.btn.greybar-bluebtn[href='contents.html']")
    WebElement lnkFindMoreOnlyC;
    
    @FindBy(css = "a[href='#combined']")
    WebElement lnkCombined;
    
    @FindBy(css = "a[href='#buildings']")
    WebElement lnkBuildings;
    
    @FindBy(css = "a[href='#contents']")
    WebElement lnkContents;
    
    @FindBy(css = "a[href='#customers']")
    WebElement lnkCustomers;
    
    @FindBy(css = "a[href='#faqs']")
    WebElement lnkFAQs;
    
    @FindBy(linkText = "3* Policy Summary")
    WebElement lnk3StarSummary;

    @FindBy(linkText = "3* Policy Wording ")
    WebElement lnk3StarWording;

    @FindBy(linkText = "5* Policy Summary")
    WebElement lnk5StarSummary;

    @FindBy(linkText = "5* Policy Wording ")
    WebElement lnk5StarWording;
    
    @FindBy(linkText = "Conditions of use ")
    WebElement lnkConditionsOfUse;
    
    @FindBy(linkText = "Privacy policy & cookie ")
    WebElement lnkPrivacyPolicyAndCookie;
    

    /**********************************************************************************************
     ********************************* WebElements of WJ Home Page - Ends ****************************
     **********************************************************************************************/
    /**
     * constructor of the class
     * 
     * @param driver
     *            : Webdriver
     */
    public HomePage(WebDriver driver, String url) {
        this.driver = driver;
        sspURL = url;
        ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, WaitUtils.maxElementWait);
        PageFactory.initElements(finder, this);
    }// HomePage
    public HomePage(WebDriver driver) {
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
        driver.get(sspURL);
        WaitUtils.waitForElement(driver, lnkGetQuote);

    }// load

    public void gotoGetquote(boolean screenShot) throws Exception { // change
                                                                    // the
                                                                    // return
                                                                    // type to
                                                                    // Get Quote
                                                                    // Page
        Log.event("Navigate to Get Quote Page");
        try {
            clickGetQuote();
        } catch (UnhandledAlertException f) {
            try {
                Alert alert = driver.switchTo().alert();
                alert.getText();
                alert.accept();
                driver.get(driver.getCurrentUrl());
                clickGetQuote();
                // }

            } catch (NoAlertPresentException e) {
                Log.fail("Problem in Navigating to Get Quote Page..!");
            }
        }
        // gotoGetquote

    }

    public void clickGetQuote() throws Exception {
        lnkGetQuote.click();
        WaitUtils.waitForPageLoad(driver);
        Log.event("Clicked on Get Quote	");
    }// clickGetQuote

}