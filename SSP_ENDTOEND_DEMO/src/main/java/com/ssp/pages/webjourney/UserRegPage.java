package com.ssp.pages.webjourney;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
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
import com.ssp.support.*;


/**
 * User Registration Page
 */
public class UserRegPage extends LoadableComponent<UserRegPage> {

    private WebDriver driver;
    private boolean isPageLoaded;
    public String agreeCheckbox = "div#p4_SingleSelectCheckbox_imp>div>div>div";
    public String checkbox_value;

    /**********************************************************************************************
     ********************************* WebElements of User Registration Page ***********************************
     **********************************************************************************************/
    @FindBy(css = "input[type='text'][name='HOMEINSURANCE[1].SAVEQUOTE[1].USERNAME']")
    WebElement txtUserName;

    @FindBy(css = "input[id='QUE_4513E281EC0720232247']")
    WebElement txtEmail;

    @FindBy(css = "input[id='QUE_2EC1722A1EF6DC071238892']")
    WebElement txtConfirmEmail;

    @FindBy(css = "button[title='Sign In']")
    WebElement btnSignIn;

    @FindBy(css = "a[id='BUT_72C3D2CAFB89E17B3478']")
    WebElement btnSaveQuote;

    /**********************************************************************************************
     ********************************* WebElements of User Registration Page - Ends ****************************
     **********************************************************************************************/
    /**
     * constructor of the class
     * 
     * @param driver
     *            : Webdriver
     */
    public UserRegPage(WebDriver driver) {
        this.driver = driver;
        ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, WaitUtils.maxElementWait);
        PageFactory.initElements(finder, this);
    }// UserRegPage

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

    public void verifyUserRegPage() throws Exception {
    	WaitUtils.waitForElement(driver, txtEmail, 20);
    	WaitUtils.waitForElement(driver, txtConfirmEmail, 20);
        Log.event("Navigated Successfully to Summary Page");

    }// verifyUserRegPage

    public YourQuotePage saveQuote() throws Exception {
        try {
	    	(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Save Quote button")).until(ExpectedConditions.visibilityOf(btnSaveQuote));
	        btnSaveQuote.click();
	        Log.event("Clicked on Save Quote Button");
	        return new YourQuotePage(driver).get();
        }
        catch(Exception e) {
        	throw new Exception(e.getMessage());
        }
    }// saveQuote

    public SignInPage gotoSignInPage(boolean screenShot) throws Exception {
        try {
	    	(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Save Quote button")).until(ExpectedConditions.visibilityOf(btnSaveQuote));
	    	btnSaveQuote.click();
	        Log.event("Clicked on Save Quote Button");
	        return new SignInPage(driver).get();
        }
        catch(Exception e) {
        	throw new Exception(e.getMessage());
        }
    }// gotoSignInPage

    public void enterEmail(String Email) throws Exception {
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Email address text box")).until(ExpectedConditions.visibilityOf(txtEmail));
        Actions action = new Actions(driver);
        action.doubleClick(txtEmail).build().perform();
        action.sendKeys(txtEmail, Email).build().perform();
        txtEmail.sendKeys(Keys.chord(Keys.SHIFT, Keys.TAB));
    	Thread.sleep(100);
        Log.event("Entered the Mail: " + Email);
    }// enterEmail

    public void enterConfirmEmail(String confirmemail) throws Exception {
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Confirm Email address text box")).until(ExpectedConditions.visibilityOf(txtConfirmEmail));
        Actions action = new Actions(driver);
        action.doubleClick(txtConfirmEmail).build().perform();
        action.sendKeys(txtConfirmEmail, confirmemail).build().perform();
        txtEmail.sendKeys(Keys.chord(Keys.SHIFT, Keys.TAB));
    	Thread.sleep(100);
        Log.event("Entered the Confirm Mail: " + confirmemail);
    }// enterConfirmEmail

    public void enterUserName(String username) {
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtUserName));
        txtUserName.clear();
        txtUserName.sendKeys(username);
        Log.event("Entered the lastName: " + username);
    }// enterUserName

    public void agreeStatement(String option) {
        List<WebElement> options = driver.findElements(By.cssSelector(agreeCheckbox));

        for (int i = 0; i < options.size(); i++) {
            checkbox_value = options.get(i).getText();
            if (checkbox_value.contains(option)) {
                options.get(i).findElement(By.cssSelector("span.checkbox")).click();
                break;
            }
        }
    }// agreeStatement
}