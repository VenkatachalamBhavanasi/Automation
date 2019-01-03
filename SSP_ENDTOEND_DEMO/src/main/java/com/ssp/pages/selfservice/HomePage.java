package com.ssp.pages.selfservice;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
 * Home Page
 */
public class HomePage extends LoadableComponent<HomePage> {

    private WebDriver driver;
    private boolean isPageLoaded;
    private String sspURL;

    /**********************************************************************************************
     ********************************* WebElements of SS Home Page ***********************************
     **********************************************************************************************/

    @FindBy(css = "button#BUT_E14D31D21EBB8E7F24944")
    WebElement btnSignIn;
    
    @FindBy(css = "a#BUT_E14D31D21EBB8E7F26237>span")
    WebElement lnkForgotLoginDetails;
    
    @FindBy(css = "a.main-brand.GothamBook")
    WebElement txtSelfService;
    
    @FindBy(css = "h2#HEAD_3D8461446F669061149488")
    WebElement txtExistingCustomers;
    
    @FindBy(css = "h2#HEAD_3D8461446F669061149602")
    WebElement txtViewPolicyDocuments;
    
    @FindBy(css = "h2#HEAD_3D8461446F669061149731")
    WebElement txtViewAcceptYourQuote;
    
    @FindBy(css = "h1#HEAD_3D8461446F669061150043")
    WebElement txtWantToSaveTime;
    
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
     ********************************* WebElements of Home Page - Ends ****************************
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
        WaitUtils.waitForPageLoad(driver);

    }// load

    /**
     * Verify HomePage
     * 
     * @throws Exception
     *             : Custom Exception Message
     */
    public boolean verifyHomePage() throws Exception {
        boolean homepage = true;
        WaitUtils.waitForElement(driver, btnSignIn, WaitUtils.maxElementWait);
        if (!driver.getTitle().equals("Customer Self Service")) {
            homepage = false;
        }
        return homepage;
    }// verifyHomePage

    public LoginPage clickSignIn() throws Exception {
        if (btnSignIn.isDisplayed()) {

            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("arguments[0].click()", btnSignIn);               
            
        } else {
            throw new Exception("Sign In button is not visible!");
        }
        return new LoginPage(driver).get();
    }// clickSignIn

}// HomePage
