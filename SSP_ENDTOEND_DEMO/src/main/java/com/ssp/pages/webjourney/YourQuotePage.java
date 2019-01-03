package com.ssp.pages.webjourney;

import java.util.List;
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
import com.ssp.support.*;

/**
 * Your Quote Page
 */
public class YourQuotePage extends LoadableComponent<YourQuotePage> {

    private WebDriver driver;
    private boolean isPageLoaded;
    public String quoteNumber;
    private String dynamicLoad = "div[class='loading-image']";
    private String cmbBuildingExcess = "//ul[@id='QUE_D3AA9217F0DF1FFA4379724testg-menu']/li[text()=";
    private String cmbContentExcess = "//ul[@id='QUE_D3AA9217F0DF1FFA4379729teste-menu']/li[text()=";
    private String btnGardenCover = "//ul[@id='QUE_2EA329519BBC4DCC315262-menu']/li[text()=";
    private String btnBicycle = "//ul[@id='QUE_2EA329519BBC4DCC315266-menu']/li[text()=";
    private String btnTE = "//ul[@id='QUE_F15FCE5A4E4BF8F4251043-menu']/li[text()=";
    private String btnPI = "//ul[@id='QUE_5058C59F4FB97188244329-menu']/li[text()=";
    
    private String locator = null;
    
    /**********************************************************************************************
     ********************************* WebElements of Home Page ***********************************
     **********************************************************************************************/

    @FindBy(css = "span.quote-number>span")
    WebElement txtQuoteNumber;
    
    @FindBy(css = ".successText")
    WebElement txtThankyoumsg;

    @FindBy(css = "div[id='TXT_934F7A96B619906A1410118']>div>p>span[class='price-point']")
    WebElement txtOnePayment;

    @FindBy(css = "div[id='TXT_934F7A96B619906A1410137']>div>p>span[class='price-point']")
    WebElement txtMnthlyPayment;

    @FindBy(css = "a.action-button.button-lg[title='Forward']")
    WebElement btnSummary;

    @FindBy(id = "row_QUE_6FBFF1443DF61B493169860")
    WebElement txtPolicyType;

    @FindBy(id = "row_QUE_6FBFF1443DF61B492140112")
    WebElement txtCoverType;

    @FindBy(id = "row_QUE_6FBFF1443DF61B492139688")
    WebElement txtBuildingCover;

    @FindBy(id = "row_QUE_6FBFF1443DF61B492139889")
    WebElement txtContentCover;

    @FindBy(id = "TXT_FBDE8030F8CBB1552878994")
    WebElement txtAddress;

    @FindBy(id = "BUT_1089FCAE5AFABD803936")
    WebElement btnUpgradeNow;

    @FindBy(css = "span[id='QUE_D3AA9217F0DF1FFA4379724testg-button']")
    WebElement menuBuildingsExcess;

    @FindBy(css = "span[id='QUE_D3AA9217F0DF1FFA4379729teste-button']")
    WebElement menuContentsExcess;

    @FindBy(css = "label[for='BuildingsAD_0']")
    WebElement lblBuildingsAD;

    @FindBy(css = "label[for='ContentsAD_0']")
    WebElement lblContentsAD;

    @FindBy(css = "label[for='HomeEmergency_0']")
    WebElement lblHomeEmergency;

    @FindBy(css = "label[for='LegalExpenses_0']")
    WebElement lblLegalExpenses;

    @FindBy(css = "span[id='QUE_2EA329519BBC4DCC315262-button']")
    WebElement menuGardenCover;

    @FindBy(css = "span[id='QUE_2EA329519BBC4DCC315266-button']")
    WebElement menuBicycles;

    @FindBy(css = "span[id='QUE_F15FCE5A4E4BF8F4251043-button']")
    WebElement menuTE;

    @FindBy(css = "span[id='QUE_6FBFF1443DF61B493168017']")
    WebElement textPI;

    @FindBy(css = "span[id='QUE_5058C59F4FB97188244329-button']")
    WebElement menuPI;

    @FindBy(css = "span[id='QUE_018FE5740E9ECBE5251910']")
    WebElement textHR;

    @FindBy(css = "button[id='BUT_72C3D2CAFB89E17B3509']")
    WebElement btnSaveQuote;

    @FindBy(css = "a[id='BUT_72C3D2CAFB89E17B3473']")
    WebElement btnBack;
  

    /**********************************************************************************************
     ********************************* WebElements of Your Quote Page - Ends ****************************
     **********************************************************************************************/
    /**
     * constructor of the class
     * 
     * @param driver
     *            : Webdriver
     * @throws Exception 
     */
    public YourQuotePage(WebDriver driver) throws Exception {
        try{
	    	this.driver = driver;
	        ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, WaitUtils.maxElementWait);
	        PageFactory.initElements(finder, this);
        }
        catch(Exception e) {
        	throw new Exception(e.getMessage());			
        }
    }// HomePage

    @Override
    protected void isLoaded() {
    	try {
	        if (!isPageLoaded) {
	            Assert.fail();
	        }// To handle page loading
	        
	        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS)).until(ExpectedConditions.titleIs("Your Quote"));
	        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS)).until(ExpectedConditions.visibilityOf(txtQuoteNumber));
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
        WaitUtils.waitForElement(driver, txtQuoteNumber, WaitUtils.maxElementWait);

    }// load

    public boolean verifyQuotePage() throws Exception {

        boolean verifyquote = true;
        try {
            String oneoffPayment = txtOnePayment.getText();
            String mntlyPayment = txtMnthlyPayment.getText();

            // Verify Policy number is not null
            try {
            	WaitUtils.waitForElement(driver, txtQuoteNumber, WaitUtils.maxElementWait);
                quoteNumber = txtQuoteNumber.getText().split("-")[2];
                if (quoteNumber.length() == 7 && !quoteNumber.matches("[0-9]{7}")) {
                    verifyquote = false;
					Log.message("Premium digits are unexpected :" + oneoffPayment + "," + mntlyPayment);
                }
				else if (quoteNumber.length() == 8 && !quoteNumber.matches("[0-9]{8}")) {
                    verifyquote = false;
					Log.message("Premium digits are unexpected :" + oneoffPayment + "," + mntlyPayment);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new Exception("Quote is not created (Invalid Quote Number):" + txtQuoteNumber.getText());
            }
            // Verify Premium amount is not null and non zero
            try {
                if ((!oneoffPayment.matches("(£)([0-9]*)+(.[0-9]*)"))) {// ||
                                                                        // (!mntlyPayment.matches("(£)([0-9]*)+(.[0-9]*)")
                    verifyquote = false;
                    Log.message("Premium values are null :" + oneoffPayment + "," + mntlyPayment);
                }
                if ((oneoffPayment.equalsIgnoreCase("£0.00"))) {// ||
                                                                // (mntlyPayment.matches("£0.00")))
                    verifyquote = false;
                    Log.message("Premium values are zero : One off Payment - " + oneoffPayment + " ; Montly Payment -" + mntlyPayment);
                }

            }

            catch (Exception e) {
                throw new Exception("Premium Values are not generated :  One Off Payment -  " + txtOnePayment.getText() + " Monthly Payment -" + txtMnthlyPayment.getText());
            }
        } catch (Exception e) {
            throw new Exception("Your Quote Page is not loaded");
        }
        Log.event("Quote Number - " + quoteNumber + ", One Off Payment : " + txtOnePayment.getText() + ", Monthly Payment : " + txtMnthlyPayment.getText());
        return verifyquote;
    }// verifyYourQoutePage

	public void upgradePolicy() {
		(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(btnUpgradeNow));
		Actions action = new Actions(driver);
    	action.moveToElement(btnUpgradeNow).click().build().perform();
        Log.event("Clicked on Upgrade Now Button");   
        VerifyElementsLoaded();
	}// upgradePolicy

	public void VerifyElementsLoaded() {
		(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to loading image")).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(dynamicLoad)));		
	}// upgradePolicy

    private void selectOption(String locator) {
    	boolean isSelected = false;
    	List<WebElement> options = driver.findElements(By.xpath(locator));
        
        for (WebElement option : options) {
        	if (option.isDisplayed() && option.isEnabled()) {

            	//if (Utils.getLocationOnScreenView(option) > 300)
            		//((JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)", "");
            	
        		Actions action = new Actions(driver);
	        	action.moveToElement(option).click().build().perform();
	        	isSelected = true;
                break;
        	}
        }
        if (!isSelected) 
        	Log.fail("No options has been selected from dropdown");
     
    } // selectOption

	public void SelectBuidingExcess(String buildingExcess) {
		buildingExcess =  "\'" + buildingExcess + "\'";
		locator = cmbBuildingExcess + buildingExcess + "]";
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Building Excess menu")).until(ExpectedConditions.visibilityOf(menuBuildingsExcess));

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click();", menuBuildingsExcess); 
        jse.executeScript("arguments[0].scrollIntoView()", menuBuildingsExcess); 
        
    	selectOption(locator);
    	Log.event("Selected Building excess :" + buildingExcess);        
        VerifyElementsLoaded();
	}// SelectBuidingExcess

	public void SelectContentExcess(String contentExcess) {
		contentExcess =  "\'" + contentExcess + "\'";
		locator = cmbContentExcess + contentExcess + "]";
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Content Excess menu")).until(ExpectedConditions.visibilityOf(menuContentsExcess));

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click();", menuContentsExcess); 
        jse.executeScript("arguments[0].scrollIntoView()", menuContentsExcess); 
        
    	selectOption(locator);
        Log.event("Selected Content excess :" + contentExcess);        
        VerifyElementsLoaded();
	}// SelectContentExcess

	public void SelectGarden(String gardenCover) {
		gardenCover =  "\'" + gardenCover + "\'";
		locator = btnGardenCover + gardenCover + "]";
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Content Excess menu")).until(ExpectedConditions.visibilityOf(menuGardenCover));

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click();", menuGardenCover); 
        jse.executeScript("arguments[0].scrollIntoView()", menuGardenCover); 
        
        selectOption(locator);
        Log.event("Selected Garden cover :" + gardenCover);        
        VerifyElementsLoaded();
	}// SelectGarden

	public void SelectTech(String tech) {
		tech =  "\'" + tech + "\'";
		locator = btnTE + tech + "]";
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Content Excess menu")).until(ExpectedConditions.visibilityOf(menuTE));

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click();", menuTE); 
        jse.executeScript("arguments[0].scrollIntoView()", menuTE); 
        
    	selectOption(locator);
        Log.event("Selected Tech and Entertainment cover :" + tech);        
        VerifyElementsLoaded();
	}// SelectTech

	public void SelectBicycle(String bicycle) {
		bicycle =  "\'" + bicycle + "\'";
		locator = btnBicycle + bicycle + "]";
    	(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Content Excess menu")).until(ExpectedConditions.visibilityOf(menuBicycles));

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click();", menuBicycles); 
        jse.executeScript("arguments[0].scrollIntoView()", menuBicycles); 
        
    	selectOption(locator);
        Log.event("Selected Bicycle cover :" + bicycle);        
        VerifyElementsLoaded();
	}// SelectBicycle

	public void SelectPI(String unspecPI) {
		unspecPI =  "\'" + unspecPI + "\'";
		locator = btnPI + unspecPI + "]";
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Content Excess menu")).until(ExpectedConditions.visibilityOf(menuPI));

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click();", menuPI); 
        jse.executeScript("arguments[0].scrollIntoView()", menuPI); 

        selectOption(locator);
        Log.event("Selected unspecified Personal Item cover :" + unspecPI);        
        VerifyElementsLoaded();
	}// SelectPI

    public UserRegPage saveQuote(WebDriver driver, boolean screenShot) throws Exception {
        try {
        	(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Get Quote button")).until(ExpectedConditions.visibilityOf(btnSaveQuote));
            Log.message("Data applied in the Your Quote Page", driver, screenShot);
            if (btnSaveQuote.isDisplayed()) {
            	btnSaveQuote.click();
            } else {
                throw new Exception("Save button is not displayed");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());            
        }

        Log.event("Clicked on Save Quote:");
        return new UserRegPage(driver).get();
    }// getQuote

    public void verifyYourQuotePage() throws Exception {
    	WaitUtils.waitForElement(driver, txtQuoteNumber, 60);
        try {
            quoteNumber = txtQuoteNumber.getText().split("-")[2];
            if (quoteNumber.matches("[0-9]{7}")) {
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new Exception("Policy is not created successfully :" + txtQuoteNumber.getText());

        }
        Log.event("Policy Created Successfully with: " + quoteNumber + "," + txtOnePayment.getText() + "," + txtMnthlyPayment.getText());

    }// verifyYourQoutePage

	public void SelectBuildingsAD(String buildingad) {
		if (buildingad.equalsIgnoreCase("yes")) {
			(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Buildings AD label")).until(ExpectedConditions.visibilityOf(lblBuildingsAD));
			lblBuildingsAD.click();
			Log.event("Selected Buildings AD cover :" + buildingad);        
	        VerifyElementsLoaded();
		}
	}// SelectBuildingsAD

	public void SelectContentsAD(String conetntad) {
		if (conetntad.equalsIgnoreCase("yes")) {
			(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Contents AD label")).until(ExpectedConditions.visibilityOf(lblContentsAD));
			lblContentsAD.click();
			Log.event("Selected Contents AD cover :" + conetntad);        
	        VerifyElementsLoaded();
		}
	}// SelectBuildingsAD

	public void SelectHEcover(String hecover) {
		if (hecover.equalsIgnoreCase("yes")) {
			(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Home Emergency Label cover")).until(ExpectedConditions.visibilityOf(lblHomeEmergency));

	        JavascriptExecutor jse = (JavascriptExecutor)driver;
	        jse.executeScript("arguments[0].click();", lblHomeEmergency); 
			
			Log.event("Selected Home Emergency cover :" + hecover);        
	        VerifyElementsLoaded();
		}
	}// SelectHEcover

	public void SelectLEcover(String lecover) {
		if (lecover.equalsIgnoreCase("yes")) {
			(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Legal Expenses Cover label")).until(ExpectedConditions.visibilityOf(lblLegalExpenses));

	        JavascriptExecutor jse = (JavascriptExecutor)driver;
	        jse.executeScript("arguments[0].click();", lblLegalExpenses); 

			Log.event("Selected Legal Expenses cover :" + lecover);        
	        VerifyElementsLoaded();
		}
	}// SelectLEcover

    public String VerifyQuoteSaved() throws Exception {
    	try {
	    	(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class)).until(ExpectedConditions.visibilityOf(txtThankyoumsg));
	    	(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class)).until(ExpectedConditions.visibilityOf(txtQuoteNumber));
	    	Log.event("Quote Saved successfully");
	    	return txtQuoteNumber.getText().split("-")[2];
    	}
    	catch (Exception e) {
            throw new Exception(e.getMessage());            
        }
    }// VerifyQuoteSaved

    public boolean verifyQuoteNumber(String exp_quotenumber) throws Exception {
    	WaitUtils.waitForElement(driver, txtQuoteNumber, 60);
        String actual_quoteNumber = txtQuoteNumber.getText();
        if ((actual_quoteNumber.indexOf(exp_quotenumber)) >= 0) 
        	return true;
        else
        	return false;
        
    }// verifyYourQoutePage

    public SummaryPage gotoSummaryPage(ExtentTest extentedReport, boolean screenShot) throws Exception {
        Log.message("Clicked on Summary Button",driver, extentedReport, screenShot);
		(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(btnSummary));

		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click();", btnSummary);
		
    	Log.event("Clicked on Summary Button");
        return new SummaryPage(driver).get();
    }//

    public void getPremiumAmount() {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtOnePayment));
        Log.event("Premium Amount: " + txtOnePayment.getText() + "," + txtMnthlyPayment.getText());
    }//

}