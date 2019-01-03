package com.ssp.pages.selfservice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
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

import com.ssp.pages.webjourney.GetQuotePage;
import com.ssp.pages.webjourney.YourQuotePage;
import com.ssp.support.*;

/**
 * Customer Dash board Page
 */
public class CustomerDashBoardPage extends LoadableComponent<CustomerDashBoardPage> {

    private WebDriver driver;
    private boolean isPageLoaded;
    private String policy_details = null;
    public String panelTitle = "div.panel-heading>h4.panel-title>a";
    public String viewBtn = "div.panel-collapse>div.panel-body>div.table-responsive>table>tbody>tr>td>a.btn";
    private String ViewWindow = "ServerFileRetrievalServlet";
    private String OptView = "url";
    /**********************************************************************************************
     ********************************* WebElements of SS Customer Dash board Page
     * ***********************************
     **********************************************************************************************/

    @FindBy(css = "a[data-toggle='dropdown']")
    WebElement cmbUserDrpDwnForLogout;
    
    @FindBy(css = "li#li_ITM_B4B8A0C26FAF54DE86052")
    WebElement changePassword;
    
    @FindBy(css = "li#li_ITM_B4B8A0C26FAF54DE86054")
    WebElement logout;

    @FindBy(css = "h2.page-title")
    WebElement titleCustomerName;

    @FindBy(css = "button[title='New Quote']")
    WebElement btnNewQuote;

    @FindBy(css = "a.btn.btn-sm.btn-default.hidden-xs.page-spinner")
    WebElement btnView;

    @FindBy(css = "i+div.col-md-2")
    List<WebElement> txtTitle;
    
    @FindBy(css = "div#p4_QUE_BE5B2742EC5056B5400280>div>div>div>div>label")
    WebElement agreecheckboxWB;
    
    @FindBy(css = "button#BUT_DEFE9E13A1EF781D957")
    WebElement buttonStartYourQuoteWB;

    /**********************************************************************************************
     ********************************* WebElements of Customer Dash board Page - Ends
     * ****************************
     **********************************************************************************************/
    public CustomerDashBoardPage(WebDriver driver) {
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
            (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class).withMessage("Customer Dashboard page did not open up.")).until(ExpectedConditions.titleIs("Customer Dashboard"));
        } catch (TimeoutException e) {
            // Utils.waitForPageLoad(driver);
            (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class).withMessage("Customer Dashboard page did not open up.")).until(ExpectedConditions.titleIs("Customer Dashboard"));
        	
        }
    }// isLoaded

    @Override
    protected void load() {

        isPageLoaded = true;
        WaitUtils.waitForElement(driver, titleCustomerName);

    }// load

    /**
     * Verify HomePage
     * 
     * @throws Exception
     *             : Custom Exception Message
     */
    public boolean verifyCustomerDashboardPage(String customername) throws Exception {
        boolean customerpage = true;
        
        WaitUtils.waitForElement(driver, cmbUserDrpDwnForLogout, 20);
        if (!cmbUserDrpDwnForLogout.isDisplayed())
            throw new Exception("Self Service Customer Dashboard Page is not loaded properly");
        if (!(titleCustomerName.getText().contains(customername))) {
            customerpage = false;
        }
        return customerpage;
    }// verifyCustomerDashboardPage

    
    
    
    public boolean VerifyQuoteExists(String quote_number) throws Exception {
        System.out.println("Verifying the created quote does exist or not");
        
        for (int i = 0; i < txtTitle.size(); i++) {

            policy_details = txtTitle.get(i).getText();
            // System.out.println(policy_details);
            try {
                if (policy_details.contains(quote_number)) {
                    return true;
                }
            } catch (ElementNotVisibleException e) {
                //Log.fail("Unable to find the quote details");
                throw new Exception("Unable to find the quote details");
            }
        }
        return false;
    }// VerifyQuoteExists

    public boolean VerifyDocuments(String quote_number) throws Exception {
    	System.out.println("Verifying the created quote does have documents and view button");
        
    	for (int i = 0; i < txtTitle.size(); i++) {
            String policy_details = txtTitle.get(i).getText();
            try {
                if (policy_details.contains(quote_number)) {
                	WebElement policyRow = txtTitle.get(i).findElement(By.xpath("..")).findElement(By.xpath("..")).findElement(By.xpath("..")).findElement(By.xpath(".."));
                	
                    List<WebElement> docs = policyRow.findElements(By.cssSelector("div[id*='p0_TBL_B3A80BADFCD28CFD90846_R']"));
                	if (docs.size() < 2)
                		return false;
                	else {
                		int docIdx = 0; 

						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						Log.message("The quote has " + docs.size() + " documents in the list");
						
						while (docIdx < docs.size()) {
							List<WebElement> cols = docs.get(docIdx).findElements(By.cssSelector("div[id*='FMT_']"));
							
							if (cols.get(0).getText().equals("")){
                				Log.message("Doc name validation failed", driver);
								return false;
							}
                			if (!cols.get(1).getText().equals(sdf.format(date))){
                				Log.message("Doc date validation failed", driver);
								return false;
                			}
                			if (!cols.get(2).getText().equals("View") || !cols.get(2).isEnabled()){
                				Log.message("Doc view button validation failed", driver);
								return false;     
                			}
                			docIdx++;
                		}                		
                	}
                	break;
                }             
            } 
                
            catch (ElementNotVisibleException e) {
                //Log.fail("Unable to find the quote details");
                throw new Exception("Unable to find the quote details");
            }
        }
        return true;
    }// VerifyDocuments

    public YourQuotePage continueQuote(String quote_number) throws Exception {
        System.out.println("Quote number is" + quote_number);
        System.out.println("text title is" + txtTitle);
        Log.message("Clicking on Continue button", driver, true);
        for (int i = 0; i < txtTitle.size(); i++) {

            policy_details = txtTitle.get(i).getText();
            // System.out.println(policy_details);
            try {
                if (policy_details.contains(quote_number)) {
                    WebElement ContinueBtn = driver.findElement(By.cssSelector("button#BUT_B3A80BADFCD28CFD90816_R" + (i + 1)));
                   
                    JavascriptExecutor jse = (JavascriptExecutor)driver;
                    jse.executeScript("arguments[0].click()", ContinueBtn);               
                    
                    Log.event("Clicked Continue button");
                    break;
                }
            } catch (ElementNotVisibleException e) {
                //Log.fail("Unable to find the quote details");
                throw new Exception("Unable to find the quote details");
            }
        }
        return new YourQuotePage(driver).get();

    }// continueQuote
    public YourQuotePage continueQuoteWB(String quote_number) throws Exception {
        System.out.println("Quote number is" + quote_number);
        System.out.println("text title is" + txtTitle);
        Log.message("Clicking on Continue button", driver, true);
        for (int i = 0; i < txtTitle.size(); i++) {

            policy_details = txtTitle.get(i).getText();
            // System.out.println(policy_details);
            try {
                if (policy_details.contains(quote_number)) {
                    WebElement ContinueBtn = driver.findElement(By.cssSelector("button#BUT_B3A80BADFCD28CFD90816_R" + (i + 1)));
                   
                    JavascriptExecutor jse = (JavascriptExecutor)driver;
                    jse.executeScript("arguments[0].click()", ContinueBtn);               
                    
                    Log.event("Clicked Continue button");
                    break;
                }
            } catch (ElementNotVisibleException e) {
                //Log.fail("Unable to find the quote details");
                throw new Exception("Unable to find the quote details");
            }
        }
        return new YourQuotePage(driver).get();

    }// continueQuote

    public void clickViewDocument(String policy_number) throws Exception {
        
        String parentHandle = driver.getWindowHandle();
        for (int i = 0; i < txtTitle.size(); i++) {
            String policy_details = txtTitle.get(i).getText();
            try {
                if (policy_details.contains(policy_number)) {
                    driver.findElement(By.cssSelector("a#view_document_R" + (i + 1) + "_1")).click();
                    Log.event("Clicked on View Document");
                    break;
                }
            } catch (ElementNotVisibleException e) {
                //Log.fail("Unable to find the quote details");
                throw new Exception("Unable to find the quote details");
            }
        }
       (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to Switch to Windows")).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.spinning-on-load-bg-table-active")));
       for (String winHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                }
       if("Microsoft Word - 1_Asda_WelcomeLetter".contains(driver.getTitle().toString())){
       /*Utils.switchWindows(driver, ViewWindow, OptView, "false", "contain");
        Utils.waitForPageLoad(driver);*/
        Log.event("Verified View Document Window : " + driver.getCurrentUrl());
       }
       driver.switchTo().window(parentHandle);
    }// click View Document

    public GetQuotePage clickNewQuote() throws Exception {
    	(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find New Quote button")).until(ExpectedConditions.visibilityOf(btnNewQuote));
        
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", btnNewQuote);               
        
        Log.event("Clicked on New Quote");
        return new GetQuotePage(driver).get();
    }// clickNewQuote
    
    public void clickNewQuoteWB() throws Exception {
    	(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find New Quote button")).until(ExpectedConditions.visibilityOf(btnNewQuote));
        
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", btnNewQuote);               
        
        Log.event("Clicked on New Quote");
       
    }// clickNewQuote
    
    public void clickagreecheckBox() throws Exception {
    	WaitUtils.waitForSpinner(driver);
    	try{
    	(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Agree checkbox ")).until(ExpectedConditions.visibilityOf(agreecheckboxWB));
        
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", agreecheckboxWB);               
        
        Log.event("Clicked on Agree Check Box");
    	}
    	catch(ElementNotVisibleException e) {
            //Log.fail("Unable to find the quote details");
            throw new Exception("Unable to find agreecheckbox details");
    		
    	}

    }//Clicking Agree checkbox

    
    public void startYourQuoteWB() throws Exception {
    	WaitUtils.waitForSpinner(driver);
    	try{
    	(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Agree checkbox ")).until(ExpectedConditions.visibilityOf(buttonStartYourQuoteWB));
        
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", buttonStartYourQuoteWB);               
        
        Log.event("Clicked on Start Quote button");
    	}
    	catch(ElementNotVisibleException e) {
            //Log.fail("Unable to find the quote details");
            throw new Exception("Unable to find Start Quote button");
    		
    	}

    }//
}
