package com.ssp.pages.webjourney;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import com.ssp.support.Log;
import com.ssp.support.Utils;
import com.ssp.support.WaitUtils;

/**
 * Sage Pay Details Page
 */
public class SagePayDetailsPage extends LoadableComponent<SagePayDetailsPage> {

    private WebDriver driver;
    private boolean isPageLoaded;

    /**********************************************************************************************
     ********************************* WebElements of Sage Pay Details Page ***********************************
     **********************************************************************************************/
    @FindBy(css = "div#FMT_5D815C83B85AFF774483")
    WebElement dummy;

    @FindBy(css = "button.btn--hollow[name ='action'][value='cancel']")
    WebElement btn_Cancel;

    @FindBy(css = "iframe.embed-responsive-item")
    WebElement Frame_Ele;

    @FindBy(css = "button.payment-method[value='VISA']>span[class='payment-method__name']")
    WebElement selectVISA;
    
    /**********************************************************************************************
     ********************************* WebElements of Sage Pay Details Page ****************************
     **********************************************************************************************/
    /**
     * constructor of the class
     * 
     * @param driver
     *            : Webdriver
     * @throws Exception 
     */
    public SagePayDetailsPage(WebDriver driver) throws Exception {
        try {
	    	this.driver = driver;
	        ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, WaitUtils.maxElementWait);
	        PageFactory.initElements(finder, this);
        }
        catch(Exception e) {
        	throw new Exception(e.getMessage());			
        }
    }// SagePayDetailsPage

    @Override
    protected void isLoaded() {

    	try{
	        if (!isPageLoaded) {
	            Assert.fail();
	        }
	        
	        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class).withMessage("Sage Detail Page did not open up.")).until(ExpectedConditions.titleIs("Payment Confirmation"));
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
        WaitUtils.waitForPageLoad(driver);

    }// load

   /* public void verifySagePayPage() throws Exception {

        // }
        driver.switchTo().frame(0);
        Log.event("Navigated Successfully to Sage Pay Details Page");

    }// verifySagePayPage
*/
    public SagePayDetailsPage clickOnSelectedCard() throws Exception {
        driver.switchTo().frame(Frame_Ele);
        Log.event("Navigating to Sage Pay  Page");
        (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Personal Items Menu")).until(ExpectedConditions.visibilityOf(selectVISA));
        
        Actions action = new Actions(driver);
        action.moveToElement(selectVISA).build().perform();
        selectVISA.click();
        
        Log.event("Clicked on Visa");
        WaitUtils.waitForSpinner(driver);
        return new SagePayDetailsPage(driver).get();
        
    }// clickOnSelectedCard
    
    public void enterCardNumber(String cardnumber) throws Exception {
        try {
            try {
                driver.findElement(By.cssSelector("input[name=cardnumber]")).sendKeys(cardnumber);
                Log.event("Entered Card Details");
            } catch (Exception e) {
                throw new Exception("Entering card details failed");
            }

        } catch (Exception e) {
            throw new Exception("Entering card details failed");
        }

    }// enterCardNumber

    public void selectExpiryMonth(String expirymonth) throws Exception {
        try {
            driver.findElement(By.cssSelector("input[name=expirymonth]")).sendKeys(expirymonth);
            Log.event("Entering Expiry Month");
        } catch (Exception e) {
            throw new Exception("Entering card details failed");
        }
    }// selectExpiryMonth

    public void selectExpiryYear(String expiryyear) throws Exception {
        try {
            driver.findElement(By.cssSelector("input[name=expiryyear]")).sendKeys(expiryyear);
            Log.event("Entering Expiry Year");
        } catch (Exception e) {
            throw new Exception("Entering card details failed");
        }
    }// selectExpiryYear

    public void enterSecurityCode(String securitycode) throws Exception {
        driver.findElement(By.cssSelector("input[name='securitycode']")).clear();
        driver.findElement(By.cssSelector("input[name='securitycode']")).sendKeys(securitycode);
        Log.event("Entering Security Code");
    }// enterSecurityCode

    public SagePayConfirmationPage gotoSagePayConfirmPage(ExtentTest extentedReport, boolean screenShot) throws Exception {
        Log.message("Data Entered in Sagepay page", driver, extentedReport, screenShot);

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.cssSelector("button[name='action'][value='proceed']")));
        jse.executeScript("arguments[0].click()", driver.findElement(By.cssSelector("button[name='action'][value='proceed']")));               
        
        Log.event("Clicked on Proceed button");
        return new SagePayConfirmationPage(driver).get();
    }// gotoSagePayConfirmPage
    
    public PaymentPage clickCancel() throws Exception {

        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(btn_Cancel));
        btn_Cancel.click();
        Log.event("Clicked on Cancel Payment");
        driver.findElement(By.cssSelector("#BUT_D38969DB873E60DC593528>span")).click();
        return new PaymentPage(driver).get();
    }//Click Cancel Payment

}