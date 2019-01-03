package com.ssp.pages.selfservice;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
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

import com.ssp.pages.webjourney.GetQuotePage;
import com.ssp.pages.webjourney.YourQuotePage;
import com.ssp.support.*;

/**
 * Customer Dash board Page
 */
    

public class ChangeMyPassword extends LoadableComponent<ChangeMyPassword>{
	
	private WebDriver driver;
    private boolean isPageLoaded;
    
    /**********************************************************************************************
     ********************************* WebElements of SS Change Password Page
     * ***********************************
     **********************************************************************************************/

    @FindBy(css = "a#change-pwd")
    WebElement btnChangePass;

    @FindBy(css = "h2.panel-title.pull-left")
    WebElement titleChangePass;

    @FindBy(css = "button#BUT_3D8461446F669061150942[title='Cancel']")
    WebElement btnCancel;

    @FindBy(css = "button#closeModal")
    WebElement btnView;

    @FindBy(css = "input#QUE_B2C721F075FCE394207217")
    WebElement txtOldPass;
    
    @FindBy(css = "input#QUE_B2C721F075FCE394207220")
    WebElement txtNewPass;
    
    @FindBy(css = "input#QUE_B2C721F075FCE394207223")
    WebElement txtReNewPass;

    /**********************************************************************************************
     ********************************* WebElements of Change password Page - Ends
     * ****************************
     **********************************************************************************************/
    public ChangeMyPassword(WebDriver driver) {
        this.driver = driver;
        ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, 60);
        PageFactory.initElements(finder, this);
    }// HomePage

    @Override
    protected void isLoaded() {

        if (!isPageLoaded) {
            Assert.fail();
        }
        try {
            (new WebDriverWait(driver, 60).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class).withMessage("Get Quote page did not open up.")).until(ExpectedConditions.titleIs("Customer Sign in"));
        } catch (TimeoutException e) {
            // Utils.waitForPageLoad(driver);
            (new WebDriverWait(driver, 60).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class).withMessage("Get Quote page did not open up.")).until(ExpectedConditions.titleIs("Customer Sign in"));
        }
    }// isLoaded

    @Override
    protected void load() {

        isPageLoaded = true;
        WaitUtils.waitForElement(driver, titleChangePass);

    }// load
    
    public boolean verifyChangePassPage() throws Exception {
        boolean changePassPage = true;
        WaitUtils.waitForPageLoad(driver);
        WaitUtils.waitForElement(driver, btnChangePass, 20);
        if (!btnChangePass.isDisplayed())
            throw new Exception("Self Service Customer Dashboard Page is not loaded properly");
        if (!(titleChangePass.getText().contains("Change My Password"))) {
        	changePassPage = false;
        }
        return changePassPage;
    }// verify change password page
    
    public void enterOldPassword(String password) {
    	txtOldPass.clear();
    	txtOldPass.sendKeys(password);
        Log.event("Entered the Email ID: " + password);
    }// enterEmailID

    public void enterNewPassword(String Newpassword) {
    	txtNewPass.clear();
    	txtNewPass.sendKeys(Newpassword);
        Log.event("Entered the Email ID: " + Newpassword);
    }// enterPassword
    
    public void reEnterNewPassword(String Newpassword) {
    	txtReNewPass.clear();
    	txtReNewPass.sendKeys(Newpassword);
        Log.event("Entered the Email ID: " + Newpassword);
    }// enterPassword
    
    public HomePage clickChangePass(boolean screenShot) throws Exception {
    	btnChangePass.click();
        Log.event("Clicked on Change Password");
        return new HomePage(driver).get();
    }// clickNewQuote
    
    public CustomerDashBoardPage clickCancel(boolean screenShot) throws Exception {
    	btnCancel.click();
        Log.event("Clicked on New Quote");
        return new CustomerDashBoardPage(driver).get();
    }// clickNewQuote

}


