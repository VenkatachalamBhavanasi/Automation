package com.ssp.pages.webjourney;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

import com.relevantcodes.extentreports.ExtentTest;
import com.ssp.support.Log;
import com.ssp.support.Utils;
import com.ssp.support.WaitUtils;

/**
 * Get Quote Page
 */
public class GetQuotePage extends LoadableComponent<GetQuotePage> {

    private WebDriver driver;
    private boolean isPageLoaded;
    private int idx = 0;
    
    private String addressLoad = "div[class='loading-image']";
	public String radCoverType = "div#p4_QUE_9547744E5D4FFB161866>div>div>label";
    private String cmbNumberofBedroom = "//ul[@id='QUE_9547744E5D4FFB161993-menu']/li[text()=";
    private String cmbPropertyType = "//ul[@id='QUE_72C3D2CAFB89E17B2628-menu']/li[text()=";
    private String cmbTitle = "//ul[@id='QUE_72C3D2CAFB89E17B2812-menu']/li[text()=";
    private String cmbDay = "//ul[@id='QUE_D37785741BC2F0CB1464333.DAY-menu']/li[text()=";// QUE_D37785741BC2F0CB1464333.DAY-menu
    private String cmbMonth = "//ul[@id='QUE_D37785741BC2F0CB1464333.MONTH-menu']/li[text()=";
    private String cmbYear = "//ul[@id='QUE_D37785741BC2F0CB1464333.YEAR-menu']/li[text()=";
    private String cmbBuildInsHeld = "//ul[@id='QUE_72C3D2CAFB89E17B2822-menu']/li[text()=";
    private String cmbContentInsHeld = "//ul[@id='QUE_72C3D2CAFB89E17B2824-menu']/li[text()=";
    private String radClaimsMade = "div#p4_QUE_72C3D2CAFB89E17B2826>div>div>label";
    private String cmbNoOfClaim = "//ul[@id='QUE_B01DF5D2992DC57D7599-menu']/li[text()=";
    private String radJointPolicy = "div#p4_QUE_72C3D2CAFB89E17B2820>div>div>label";
    private String cmbJointTitle = "//ul[@id='QUE_72C3D2CAFB89E17B2876-menu']/li[text()=";
    private String cmbJointDay = "//ul[@id='QUE_72C3D2CAFB89E17B2882.DAY-menu']/li[text()=";// QUE_D37785741BC2F0CB1464333.DAY-menu
    private String cmbJointMonth = "//ul[@id='QUE_72C3D2CAFB89E17B2882.MONTH-menu']/li[text()=";
    private String cmbJointYear = "//ul[@id='QUE_72C3D2CAFB89E17B2882.YEAR-menu']/li[text()=";
    private String radPersonalItems = "div#p4_QUE_72C3D2CAFB89E17B2828>div>div>label";
    private String cmbNoOfPI = "//ul[@id='QUE_B01DF5D2992DC57D7499-menu']/li[text()=";
    private String cmbNoOfBicycle = "//ul[@id='QUE_7A508E6F65FCBA3D4648-menu']/li[text()=";
    private String cmbNoOfHR = "//ul[@id='QUE_B01DF5D2992DC57D7147-menu']/li[text()=";
    private String radBicycle = "div#p4_QUE_72C3D2CAFB89E17B2830>div>div>label";
    private String radHighRisk = "label[for*='QUE_72C3D2CAFB89E17B2832']>span";
    private String radPromoCodee = "div#p4_QUE_FE03B8B4E69C4F465444>div>div>label";
    private String menuDate = "span.ui-selectmenu-button.ui-widget[role='combobox']";
    private String radio_button_value = null;
    private String menu_value = null;
    private String locator = null;
    
    /**********************************************************************************************
     ********************************* WebElements of Home Page ***********************************
     **********************************************************************************************/
    @FindBy(css = "input[id='QUE_9547744E5D4FFB161738']")
    WebElement txtHouseNumberOrName;
    
    @FindBy(css = "button[id='BUT_CCA61F95A8528B8626095']")
    WebElement btnManuallAddress;

    @FindBy(css = "input.input-md[type='text'][name='HOMEINSURANCE[1].YOURDETAILS[1].HOME[1].ADDRESS[1].POSTCODE']")
    WebElement txtPostCode;
    
    @FindBy(css = "input[type='text'][name='HOMEINSURANCE[1].YOURDETAILS[1].ABOUTHOME[1].ADDRESS[1].POSTCODE']")
    WebElement txtPostCodeWB;

    @FindBy(css = "button[title='Find Address']")
    WebElement btnFindAddress;

    @FindBy(css = "input[name='HOMEINSURANCE[1].YOURDETAILS[1].HOME[1].YEAROFBUILD']")
    WebElement txtYearOfBuild;

    @FindBy(css = "input.input-lg[type='text'][name='HOMEINSURANCE[1].YOURDETAILS[1].ABOUTYOU[1].FIRSTNAME']")
    WebElement txtFirstName;

    @FindBy(css = "input.input-lg[type='text'][name='HOMEINSURANCE[1].YOURDETAILS[1].ABOUTYOU[1].SURNAME']")
    WebElement txtLastName;
    
    @FindBy(css = "input[type='text'][name='HOMEINSURANCE[1].YOURDETAILS[1].ABOUTYOU[1].FIRSTNAME_READONLY']")
    WebElement txtFirstNameWB;

    @FindBy(css = "input[type='text'][name='HOMEINSURANCE[1].YOURDETAILS[1].ABOUTYOU[1].SURNAME_READONLY']")
    WebElement txtLastNameWB;

    @FindBy(css = "a.action-button.button-lg[title='Forward']#BUT_72C3D2CAFB89E17B3290")
    WebElement btnGetQuote;

    @FindBy(css = "div.address-box[style='clear: both; ']")
    WebElement txtAddress;

    @FindBy(css = "button.address-button[title='Edit Address'][style='cursor: pointer;']")
    WebElement btnEditAddress;

    @FindBy(css = "span.ui-selectmenu-button.ui-widget[role='combobox']#QUE_9547744E5D4FFB161993-button")
    WebElement menuNumberofBedrooms;

    @FindBy(css = "span.ui-selectmenu-button.ui-widget[role='combobox']#QUE_72C3D2CAFB89E17B2628-button")
    WebElement menuPropType;

    @FindBy(css = "span.ui-selectmenu-button.ui-widget[role='combobox']#QUE_72C3D2CAFB89E17B2812-button")
    WebElement menuTitle;

    @FindBy(css = "span.ui-selectmenu-button.ui-widget[role='combobox']#QUE_72C3D2CAFB89E17B2822-button")
    WebElement menuBuildInsurance;

    @FindBy(css = "span.ui-selectmenu-button.ui-widget[role='combobox']#QUE_72C3D2CAFB89E17B2824-button")
    WebElement menuContentsInsurance;

    @FindBy(css = "span.error-text")
    WebElement txtErrorMsg;

    @FindBy(linkText = "eligibility conditions")
    WebElement lnkEligibilityConditions;

    @FindBy(linkText = "privacy policy")
    WebElement lnkPrivacyPolicies;

	@FindBy(linkText = "terms & conditions")
    WebElement lnkTermsConditions;

    @FindBy(css = "span[id='QUE_B01DF5D2992DC57D7599-button']")
    WebElement menuClaimCount;

    @FindBy(css = "label[for='QUE_72C3D2CAFB89E17B2828_0']")
    WebElement PIField;

    @FindBy(css = "span[id='QUE_B01DF5D2992DC57D7499-button']")
    WebElement menuPICount;

    @FindBy(css = "span[id='QUE_7A508E6F65FCBA3D4648-button']")
    WebElement menuBicycleCount;

    @FindBy(css = "span[id='QUE_B01DF5D2992DC57D7147-button']")
    WebElement menuHRCount;

    @FindBy(css = "label[for='QUE_72C3D2CAFB89E17B2820_0']")
    WebElement jointPolicyField;

    @FindBy(css = "span[id='QUE_72C3D2CAFB89E17B2876-button']")
    WebElement menuJointTitle;

    @FindBy(css = "input[id='QUE_72C3D2CAFB89E17B2878']")
    WebElement txtJointFirstName;

    @FindBy(css = "input[id='QUE_72C3D2CAFB89E17B2880']")
    WebElement txtJointLastName;

    @FindBy(css = "label[for='QUE_72C3D2CAFB89E17B2826_0']")
    WebElement claimMadeField;

    @FindBy(css = "label[for='QUE_72C3D2CAFB89E17B2830_0']")
    WebElement BicyclesField;

    @FindBy(css = "label[for='QUE_72C3D2CAFB89E17B2832_0']")
    WebElement HRField;

    @FindBy(css = "label[for='QUE_FE03B8B4E69C4F465444_0']")
    WebElement PromoCodeField;

    @FindBy(css = "input[id='QUE_FE03B8B4E69C4F465633']")
    WebElement txtPromoCode;

    @FindBy(css = "input[id='QUE_B75DF70C91A5CF1A1265865']")
    WebElement txtStaffCode;

    /**********************************************************************************************
     ********************************* WebElements of Home Page - Ends ****************************
     **********************************************************************************************/
    /**
     * constructor of the class
     * 
     * @param driver
     *            : Webdriver
     * @throws Exception
     */
    public GetQuotePage(WebDriver driver) throws Exception {
        this.driver = driver;
        ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, WaitUtils.maxElementWait);
        PageFactory.initElements(finder, this);
    }// GetQuotePage
    @Override
    protected void isLoaded() {

        if (!isPageLoaded) {
            Assert.fail();
        }
        // To handle page loading
        (new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class).withMessage("Get Quote page did not open up.")).until(ExpectedConditions.titleIs("Your Details"));        
    }// isLoaded

    @Override
    protected void load() {

        isPageLoaded = true;

    }// load

    public void verifyGetQuotePage() throws Exception {
        try {
        	WaitUtils.waitForElement(driver, txtPostCode, 20);

        } catch (Exception e) {
            throw new Exception("Get Quote page is not opened");
        }
    }// verifyGetQuotePage
    public void verifyGetQuotePageWB() throws Exception {
        try {
        	WaitUtils.waitForElement(driver, txtPostCodeWB, 20);

        } catch (Exception e) {
            throw new Exception("Get Quote page is not opened");
        }
    }// verifyGetQuotePage

    public void enterPostCode(String postCode) throws Exception {
        try {
            (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find post code text box")).until(ExpectedConditions.visibilityOf(txtPostCode));
            txtPostCode.clear();
            txtPostCode.sendKeys(postCode);
            txtPostCode.sendKeys(Keys.TAB);
            Log.event("Entered the PostCode: " + postCode);
        } catch (Exception e) {
            throw new Exception("Error while entering Post Code");
        }

    }// enterPostCode

    public void selectCoverType(String covertype) throws Exception {

        try {
            selectRadioButton(radCoverType, covertype);
        } catch (InterruptedException e) {
            throw new Exception("Unable to Select Cover Type");
        }
        Log.event("Selected Property Type :" + covertype);
    }// selectJointPolicy

    public void enterYearOfBuild(String yearOfBuild) throws Exception {
        try {
            (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Year Of Build text box")).until(ExpectedConditions.visibilityOf(txtYearOfBuild));
            Actions action = new Actions(driver);
            action.doubleClick(txtYearOfBuild).build().perform();
            txtYearOfBuild.sendKeys(yearOfBuild);
            txtYearOfBuild.sendKeys(Keys.TAB);
            Log.event("Entered the yearOfBuild: " + yearOfBuild);
        } catch (Exception e) {
            throw new Exception("Error while entering Year of Build");
        }

    }// enterYearOfBuild

    public void enterJointPolicyDetails(HashMap <String, String> jointaccount) throws Exception {
        try {
        	String titleMenu;
        	
        	//Select Joint Holder's Title
        	titleMenu = cmbJointTitle + "\'" + jointaccount.get("Title") + "\']";
        	Thread.sleep(1000);
        	(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Joint Holder's Title menu")).until(ExpectedConditions.elementToBeClickable(menuJointTitle));
        	menuJointTitle.click();
        	selectOption(titleMenu);
        	
        	 //Select First name
    		(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Joint Holder's First name text box")).until(ExpectedConditions.elementToBeClickable(txtJointFirstName));
        	Actions action = new Actions(driver);
        	action.moveToElement(txtJointFirstName).build().perform(); 
        	action = new Actions(driver);
            action.doubleClick(txtJointFirstName).build().perform();
            txtJointFirstName.sendKeys(jointaccount.get("FirstName"));
            txtJointFirstName.sendKeys(Keys.TAB);
            
        	//Select Surname
        	(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Joint Holder's Surname text box")).until(ExpectedConditions.elementToBeClickable(txtJointLastName));
        	action = new Actions(driver);
        	action.moveToElement(txtJointLastName).build().perform(); 
        	action.doubleClick(txtJointLastName).build().perform();
            txtJointLastName.sendKeys(jointaccount.get("Surname"));
            txtJointLastName.sendKeys(Keys.TAB);
            
            //Select Surname
        	selectDateofBirth(jointaccount.get("DOB"), "jointHolder");
        	Log.event("Entered data for Joint Policy Account Holder - " + jointaccount.toString());    
        	
        } catch (Exception e) {
            throw new Exception(e.getMessage() + " Error while entering Joint Policy details");
        }

    }// enterJointPolicyDetails

    public void enterClaimMadeDetails(ArrayList<HashMap <String, String>> claimDetails) throws Exception {
        try {
        	int index = 0;
        	WebElement val;
        	WebElement menPolicySection;
        	WebElement menTypeOfClaim;
        	String locator1;
        	String locator2;

        	locator = cmbNoOfClaim + claimDetails.size() + "]";
            (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Address Label")).until(ExpectedConditions.elementToBeClickable(menuClaimCount));
            Actions action = new Actions(driver);

            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("arguments[0].click();", menuClaimCount); 
            jse.executeScript("arguments[0].scrollIntoView()", menuClaimCount); 
            
            Thread.sleep(200);

            selectOption(locator);
            Thread.sleep(1000);
        	Log.event("Selected the total no. of Claims made: " + claimDetails.size()); 
            
            while (index < claimDetails.size()) {
            	idx = index+1;
            	HashMap <String, String> claimDetail = claimDetails.get(index);

	        	//Set value of claim
	        	val = driver.findElement(By.cssSelector(setClaimValue(idx)));
            	(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Value text box")).until(ExpectedConditions.elementToBeClickable(val));
            	action = new Actions(driver);
            	action.moveToElement(val).build().perform(); 
            	Thread.sleep(200);
            	val.clear();
            	val.sendKeys(claimDetail.get("Value of claim"));
            	val.sendKeys(Keys.TAB);
                
	        	//Select Date of Claim
            	//if (Utils.getLocationOnScreenView(val) > 300)
            		//((JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)", "");
            	
            	selectDateofBirth(claimDetail.get("Date of claim"), "claim" + idx);

            	//Select Claim Policy Section
            	locator1 = setClaimPolicySection(idx);
            	locator2 = setcmbPolicySection(idx) + "\'" + claimDetail.get("Policy Section") + "\']";
            	menPolicySection = driver.findElement(By.cssSelector(locator1));
            	(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Category menu")).until(ExpectedConditions.elementToBeClickable(menPolicySection));
            	
            	menPolicySection.click();
            	Thread.sleep(200);
            	selectOption(locator2);

            	//Select Type Of Claim
            	locator1 = setTypeOfClaim(idx);
            	locator2 = setcmbTypeOfClaim(idx) + "\'" + claimDetail.get("Type of claim") + "\']";
            	menTypeOfClaim = driver.findElement(By.cssSelector(locator1));
            	(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Category menu")).until(ExpectedConditions.elementToBeClickable(menTypeOfClaim));
            	
            	menTypeOfClaim.click();
            	Thread.sleep(200);
            	selectOption(locator2);
            	
            	index++;
            }
            Log.event("Entered data for Claim Made details - " + claimDetails.toString());    
        } catch (Exception e) {
            throw new Exception(e.getMessage() + " Error while entering Claim Made details");
        }

    }// enterJointPolicyDetails

    public void enterPI(ArrayList<HashMap <String, String>> piList) throws Exception {
        try {
        	int index = 0;
        	WebElement desc;
        	WebElement val;
        	WebElement menCat;
        	String locator1;
        	String locator2;
        	
        	locator = cmbNoOfPI + piList.size() + "]";
            (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Address Label")).until(ExpectedConditions.elementToBeClickable(menuPICount));
            Actions action = new Actions(driver);

            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("arguments[0].click();", menuPICount); 
            jse.executeScript("arguments[0].scrollIntoView()", menuPICount); 
            
            Thread.sleep(200);
            //menuPICount.click();
            selectOption(locator);
            Thread.sleep(2000);
        	Log.event("Selected the total no. of PI: " + piList.size()); 
            
            while (index < piList.size()) {
            	try {
	            	idx = index+1;
	            	HashMap <String, String> pi = piList.get(index);
	            	
	            	//Select Category
	            	locator1 = setPICategory(idx);
	            	locator2 = setcmbCategory(idx) + "\'" + pi.get("Category") + "\']";
	            	menCat = driver.findElement(By.cssSelector(locator1));
	            	(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Category menu")).until(ExpectedConditions.elementToBeClickable(menCat));
	            	
	            	//if (Utils.getLocationOnScreenView(menCat) > 300)
	            		//((JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)", "");
	            	
	            	menCat.click();
	            	Thread.sleep(200);
	            	selectOption(locator2);
	            	
	            	 //Select Description
	            	desc = driver.findElement(By.cssSelector(setPIDescription(idx)));
	            	(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Description text box")).until(ExpectedConditions.elementToBeClickable(desc));
	            	action = new Actions(driver);
	            	Thread.sleep(200);
	            	action.doubleClick(desc).build().perform(); 
	            	desc.clear();
	            	desc.sendKeys(pi.get("Description"));
	            	//desc.sendKeys(Keys.TAB);
	                //Utils.sendkeys(driver, desc, pi.get("Description"));
	            	
	                //Select Value
	            	val = driver.findElement(By.cssSelector(setPIValue(idx)));
	            	(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Value text box")).until(ExpectedConditions.elementToBeClickable(val));
	            	action = new Actions(driver);
	            	action.doubleClick(val).build().perform(); 
	            	Thread.sleep(200);
	            	val.clear();
	            	val.sendKeys(pi.get("Value"));
	            	//val.sendKeys(Keys.TAB);
	            	//Utils.sendkeys(driver, val, pi.get("Value"));
	            	
	            	index++;
            	}
        	catch (StaleElementReferenceException e) {        	
            }
            }
            Log.event("Entered data for personal Item - " + piList.toString());    
        	
        } catch (Exception e) {
            throw new Exception(e.getMessage() + " Error while entering specific PI cover");
        }

    }// enterPI

    public void enterBicycle(ArrayList<HashMap <String, String>> bicycleList) throws Exception {
        try {
        	int index = 0;
        	locator = cmbNoOfBicycle + bicycleList.size() + "]";
        	Thread.sleep(3000);
        	(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Address Label")).until(ExpectedConditions.elementToBeClickable(menuBicycleCount));

        	JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("arguments[0].click();", menuBicycleCount); 
            jse.executeScript("arguments[0].scrollIntoView()", menuBicycleCount); 
            
            Thread.sleep(1000);
        	selectOption(locator);
        	Thread.sleep(5000);
        	Log.event("Selected the total no. of Bicycles: " + bicycleList.size()); 
        	
            while (index < bicycleList.size()) {
            	try {
	            	WebElement desc;
	            	WebElement desc1;
	            	idx = index+1;
	            	HashMap <String, String> bicycle = bicycleList.get(index);
	            	
	                //Select Description
	            	desc = driver.findElement(By.cssSelector(setBicycleDescription(idx)));
	            	desc.clear();
	            	Thread.sleep(1000);
	            	desc.sendKeys(bicycle.get("Description"));
	            	//desc.sendKeys(Keys.TAB);
	            	//Utils.sendkeys(driver, desc, bicycle.get("Description"));
	            	Thread.sleep(200);

	            	//if (Utils.getLocationOnScreenView(desc) > 300)
	            		//((JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)", "");
	            	Thread.sleep(3000);
	            	//Select Value
	            	desc1 = driver.findElement(By.cssSelector(setBicycleValue(idx)));
	            
	            	
	            	Thread.sleep(2000);
	            	//Utils.sendkeys(driver, val, bicycle.get("Value"));
	            
	            	desc1.sendKeys(bicycle.get("Value"));
	            	            	
	                index++;
            	}
            	catch (StaleElementReferenceException e) {        	
                }
            }
            Log.event("Entered data for Bicycles - " + bicycleList.toString());    
        	
        } 
        catch (Exception e) {
            throw new Exception(e.getMessage() + " - Error while entering specific Bicycle cover");
        }

    }// enterBicycle

    public void enterHRI(ArrayList<HashMap <String, String>> hriList) throws Exception {
        try {
        	int index = 0;
        	WebElement desc;
        	WebElement menHR;
        	String locator1;
        	String locator2;
        	
        	locator = cmbNoOfHR + hriList.size() + "]";
            (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Address Label")).until(ExpectedConditions.elementToBeClickable(menuHRCount));
            Actions action = new Actions(driver);

            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("arguments[0].click();", menuHRCount); 
            jse.executeScript("arguments[0].scrollIntoView()", menuHRCount); 
            
            Thread.sleep(2000);
        	selectOption(locator);
            Thread.sleep(1000);
        	Log.event("Selected the total no. of PI: " + hriList.size()); 
            
            while (index < hriList.size()) {
	            try {
            		idx = index+1;
	            	HashMap <String, String> pi = hriList.get(index);
	            	
	            	//Select Category
	            	locator1 = setHRCategory(idx);
	            	locator2 = setcmbHRCategory(idx) + "\'" + pi.get("Category") + "\']";
	            	menHR = driver.findElement(By.cssSelector(locator1));
	            	(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Category menu")).until(ExpectedConditions.elementToBeClickable(menHR));
	            	
	            	//if (Utils.getLocationOnScreenView(menHR) > 300)
	            		//((JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)", "");
	            	
	            	menHR.click();
	            	Thread.sleep(200);
	            	selectOption(locator2);
	            	
	            	 //Select Description
	            	desc = driver.findElement(By.cssSelector(setHRDescription(idx)));
	            	(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Description text box")).until(ExpectedConditions.elementToBeClickable(desc));
	            	action = new Actions(driver);
	            	action.moveToElement(desc).moveToElement(desc).build().perform(); 
	            	Thread.sleep(200);
	            	desc.clear();
	            	desc.sendKeys(pi.get("Description"));
	            	//desc.sendKeys(Keys.TAB);
	            	//Utils.sendkeys(driver, desc, pi.get("Description"));
	            	
	            	//Select Value
	            	By by = By.cssSelector(setHRValue(idx));
	            	Utils.sendkeys(driver, by, pi.get("Value"));
	            	
	            	index++;
	            }
            	catch (StaleElementReferenceException e) {        	
                }
            }
            Log.event("Entered data for personal Item - " + hriList.toString());    
        	
        } catch (Exception e) {
            throw new Exception(e.getMessage() + " Error while entering specific High risk cover");
        }

    }// enterPI

    public void enterFirstName(String firstName) throws Exception {
        try {
            (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find First Name text box")).until(ExpectedConditions.visibilityOf(txtFirstName));
            Actions action = new Actions(driver);
            action.doubleClick(txtFirstName).build().perform();
            txtFirstName.sendKeys(firstName);
            txtFirstName.sendKeys(Keys.TAB);
        	
            Log.event("Entered the firstName: " + firstName);
        } catch (Exception e) {
            throw new Exception("Error while entering First Name");
        }

    }// enterFirstName

    public boolean verifyFirstName(String first_name) throws Exception {
        try {
            boolean firstname = true;
            if (!txtFirstName.getAttribute("value").equals(first_name)) {
                firstname = false;
            }
            Log.event("First Name verified successfully");
            return firstname;
        } catch (Exception e) {
            throw new Exception("Error while verifying First Name");
        }

    }// enterFirstName
    
    public boolean verifyFirstNameWB(String first_name) throws Exception {
        try {
            boolean firstname = true;
            if (!txtFirstNameWB.getAttribute("value").equals(first_name)) {
                firstname = false;
            }
            Log.event("First Name verified successfully");
            return firstname;
        } catch (Exception e) {
            throw new Exception("Error while verifying First Name");
        }

    }// enterFirstName

    public void enterJointFirstName(String firstName) throws Exception {
        try {
            (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find First Name text box")).until(ExpectedConditions.visibilityOf(txtJointFirstName));
            Actions action = new Actions(driver);
            action.doubleClick(txtJointFirstName).build().perform();
            txtJointFirstName.sendKeys(firstName);
            txtJointFirstName.sendKeys(Keys.TAB);
        	Log.event("Entered the Joint Jolder's firstName: " + firstName);
        } catch (Exception e) {
            throw new Exception("Error while entering Joint Holder's First Name");
        }

    }// enterJointFirstName
    
    public void enterJointLastName(String last_name) throws Exception {
    	try {
            (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find First Name text box")).until(ExpectedConditions.visibilityOf(txtJointLastName));
            Actions action = new Actions(driver);
            action.doubleClick(txtJointLastName).build().perform();
            txtJointLastName.sendKeys(last_name);
            txtJointLastName.sendKeys(Keys.TAB);
        	Log.event("Entered the Joint Holder's LastName: " + last_name);
        } catch (Exception e) {
            throw new Exception("Error while entering Joint Holder's First Name");
        }

    }// enterJointLastName

    public boolean verifyLastName(String last_name) throws Exception {
        try {
            boolean lastname = true;
            if (!txtLastName.getAttribute("value").equals(last_name)) {
                lastname = false;
            }
            Log.event("Last Name verified successfully");
            return lastname;
        } catch (Exception e) {
            throw new Exception("Error while verifying Last Name");
        }

    }// verifyLastName
    
    public boolean verifyLastNameWB(String last_name) throws Exception {
        try {
            boolean lastname = true;
            if (!txtLastNameWB.getAttribute("value").equals(last_name)) {
                lastname = false;
            }
            Log.event("Last Name verified successfully");
            return lastname;
        } catch (Exception e) {
            throw new Exception("Error while verifying Last Name");
        }

    }// verifyLastName

    public boolean verifyDetails(String customer_name) throws Exception {
        try {
            boolean custName = true;
            String customername[] = customer_name.split(" ");
            String fstName = customername[1];
            String lstName = customername[2];
            if (!verifyFirstName(fstName)) {
                custName = false;
                Log.event("First Name Validation Failed");
            }
            if (!verifyLastName(lstName)) {
                custName = false;
                Log.event("Last Name Validation Failed");
            }
            Log.event("Customer Details Verified Successfully");
            return custName;
        }

        catch (Exception e) {
            throw new Exception("Error while verifying Customer Details");
        }

    }
    public boolean verifyDetailsWB(String customer_name) throws Exception {
        try {
            boolean custName = true;
            String customername[] = customer_name.split(" ");
            String fstName = customername[1];
            String lstName = customername[2];
            if (!verifyFirstNameWB(fstName)) {
                custName = false;
                Log.event("First Name Validation Failed");
            }
            if (!verifyLastNameWB(lstName)) {
                custName = false;
                Log.event("Last Name Validation Failed");
            }
            Log.event("Customer Details Verified Successfully");
            return custName;
        }

        catch (Exception e) {
            throw new Exception("Error while verifying Customer Details");
        }

    }

    public void enterLastName(String lastName) throws Exception {
        try {
            (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Last Name text box")).until(ExpectedConditions.visibilityOf(txtLastName));
            Thread.sleep(1000);
            Actions action = new Actions(driver);
            action.doubleClick(txtLastName).build().perform();
            txtLastName.sendKeys(lastName);
            txtLastName.sendKeys(Keys.TAB);
        	Log.event("Entered the lastName: " + lastName);
        } catch (Exception e) {
            throw new Exception("Error while entering Last Name");
        }

    }// enterLastName

    public void clickFindAddress() throws Exception {
        try {
            (new WebDriverWait(driver, 10).pollingEvery(50, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Find Address Button")).until(ExpectedConditions.visibilityOf(btnFindAddress));
            Actions action = new Actions(driver);
            action.moveToElement(btnFindAddress).build().perform();
            btnFindAddress.click();
            VerifyAddressLoaded();
            Log.event("Clicked on Find Address");
        } catch (Exception e) {
            throw new Exception("Error while clicking on Find Address");
        }

    }// clickFindAddress

	public void VerifyAddressLoaded() {
		(new WebDriverWait(driver, WaitUtils.maxElementWait).pollingEvery(50, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to loading image")).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(addressLoad)));		
	}// upgradePolicy

    public void selectNumberofBedroom(String noOfBedrooms) throws Exception {
        try {
            locator = cmbNumberofBedroom + noOfBedrooms + "]";
            (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Year Of Build text box")).until(ExpectedConditions.visibilityOf(txtYearOfBuild));
            menuNumberofBedrooms.click();
            selectOption(locator);
            Log.event("Selected Number of Bedrooms :" + noOfBedrooms);
        } catch (Exception e) {
            throw new Exception("Error while Selecting Number of Bedrooms");
        }
    }// selectNumberofBedroom

    public void selectDateofBirth(String dateofBirth, String type) throws Exception {
        try {
            String dateformat[] = dateofBirth.split("-");
            int day = Integer.parseInt(dateformat[0]);
            String month = dateformat[1];
            int year = Integer.parseInt(dateformat[2]);
            Thread.sleep(1000);
            selectDate(day, type);
            selectMonth(month, type);
            selectYear(year, type);
            Log.event("Selected Day :" + day);
            Log.event("Selected Month :" + month);
            Log.event("Selected Year :" + year);
        } 
        catch (InterruptedException e) {
            throw new InterruptedException("Date of Birth is not Selected");
        } catch (Exception f) {
            throw new Exception("Date of Birth is not Selected");
        }

    }// selectDateOfBirth

    public void selectTypeOfProperty(String propertyType) throws Exception {
        try {
            locator = cmbPropertyType + "'" + propertyType + "']";
            (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Year Of Build text box")).until(ExpectedConditions.visibilityOf(txtYearOfBuild));
            menuPropType.click();
            selectOption(locator);
            Log.event("Selected Property Type :" + propertyType);

        } catch (Exception e) {
            throw new Exception("Error in selecting property type");
        }

    }// selectTypeOfProperty

    public void selectTitle(String Title) throws Exception {
        try {
            locator = cmbTitle + "'" + Title + "']";
            (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Year Of Build text box")).until(ExpectedConditions.visibilityOf(menuTitle));
            
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("arguments[0].click();", menuTitle); 
            jse.executeScript("arguments[0].scrollIntoView()", menuTitle); 
            
			selectOption(locator);
            Log.event("Selected Property Type :" + Title);
        } catch (Exception e) {
            throw new Exception("Error in selecting Title");
        }

    }// selectTitle

    public void selectBuildingIns(String buildinsurance) throws Exception {
        try {
        	if (Integer.parseInt(buildinsurance) >= 4)
        		buildinsurance = "\'" + "4" + '+' + "\'";
        	locator = cmbBuildInsHeld + buildinsurance + "]";
            (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Build Insurance Menu")).until(ExpectedConditions.visibilityOf(menuBuildInsurance));
            menuBuildInsurance.click();
            selectOption(locator);
            Log.event("Selected Property Type :" + buildinsurance);
        } catch (Exception e) {
            throw new Exception("Error in selecting Building Isurance");
        }
    }// selectBuildingIns

    public void selectContentIns(String contentinsurance) throws Exception {
        try {
            if (Integer.parseInt(contentinsurance) >= 4)
            	contentinsurance = "\'" + "4" + '+' + "\'";
        	locator = cmbContentInsHeld + contentinsurance + "]";
            (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Content Insurance Menu")).until(ExpectedConditions.visibilityOf(menuContentsInsurance));
            menuContentsInsurance.click();
            selectOption(locator);
            Log.event("Selected Property Type :" + contentinsurance);
        } catch (Exception e) {
            throw new Exception("Error in selecting Contents Isurance");
        }
    }// selectContentIns

    public void selectJointPolicy(String jointpolicy) throws Exception {
        (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Joint Policy Menu")).until(ExpectedConditions.visibilityOf(jointPolicyField));
        try {
            selectRadioButton(radJointPolicy, jointpolicy);
        } catch (InterruptedException e) {
            throw new Exception("Unable to Select Joint Policy Holder");
        }
        Log.event("Selected Property Type :" + jointpolicy);
    }// selectJointPolicy

    public void selectClaimsMade(String claimsmade) throws Exception {
        (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Claims Made Menu")).until(ExpectedConditions.visibilityOf(claimMadeField));
        try {
            selectRadioButton(radClaimsMade, claimsmade);
        } catch (InterruptedException e) {
            throw new Exception("Unable to Select Claims Made");
        }
        Log.event("Selected Property Type :" + claimsmade);
    }// selectClaimsMade

    public void selectPersonalItems(String personalitems) throws Exception {
        (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Personal Items Menu")).until(ExpectedConditions.visibilityOf(PIField));
        try {
            selectRadioButton(radPersonalItems, personalitems);
        } catch (InterruptedException e) {
            throw new Exception("Unable to Select Personal Items Option");
        }
        Log.event("Selected Property Type :" + personalitems);
    }// selectClaimsMade

    public void selectBicycle(String bicycle) throws Exception {
        (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Bicycles Menu")).until(ExpectedConditions.visibilityOf(BicyclesField));
        try {
            selectRadioButton(radBicycle, bicycle);
        } catch (InterruptedException e) {
            throw new Exception("Unable to Select Bicycle Option");
        }
        Log.event("Selected Property Type :" + bicycle);
    }// selectClaimsMade

    public void selectHighRisk(String highrisk) throws Exception {
        try {
            (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find High risk Items Menu")).until(ExpectedConditions.visibilityOf(HRField));
            // Point hoverItem
            // =driver.findElement(By.cssSelector(radBicycle)).getLocation();
            //((JavascriptExecutor) driver).executeScript("return window.title;");
            //((JavascriptExecutor) driver).executeScript("window.scroll(0,1200)");
            List<WebElement> buttonOptions = driver.findElements(By.cssSelector(radHighRisk));

            for (int i = 0; i < buttonOptions.size(); i++) {
                radio_button_value = buttonOptions.get(i).getText();
                if (radio_button_value.equals(highrisk) && buttonOptions.get(i).isDisplayed()) {
                	JavascriptExecutor executor = (JavascriptExecutor)driver;
                	executor.executeScript("arguments[0].click();", buttonOptions.get(i));
                	break;
                }
            }
            
            Log.event("Selected High Risk Type :" + highrisk);
        } catch (Exception e) {
            throw new Exception("Unable to Select High Risk Option");
        }

    }// selectHighRisk

    public void selectPromocode(String promoCode) throws Exception {
        (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Promo Code Menu")).until(ExpectedConditions.visibilityOf(PromoCodeField));
        try {
            selectRadioButton(radPromoCodee, promoCode);
        } catch (InterruptedException e) {
            throw new Exception("Unable to Select Promo Code");
        }
        Log.event("Selected Property Type :" + promoCode);
    }// selectPromocode

    public void setPromocode(String promoCode, String staffCode) throws Exception {
        try {
        	(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Promo Code text box")).until(ExpectedConditions.visibilityOf(txtPromoCode));
            txtPromoCode.sendKeys(promoCode);
            Thread.sleep(500);
        	txtPromoCode.sendKeys(Keys.TAB);
        	Thread.sleep(500);
        	if (!staffCode.equals("")) {
        		(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Staff Code text box")).until(ExpectedConditions.visibilityOf(txtStaffCode));
        		txtStaffCode.sendKeys(staffCode);        		
        	}        		
        } 
        catch (Exception e) {
            throw new Exception("Unable to Set Promo Code");
        }
        Log.event("Selected Property Type :" + promoCode);
    }// setPromocode

    public YourQuotePage getQuote(WebDriver driver,ExtentTest extentedReport, boolean screenShot) throws Exception {
        (new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find Get Quote button")).until(ExpectedConditions.visibilityOf(btnGetQuote));
        Log.message("Data entered in the Quote Page", driver,extentedReport, screenShot);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
		
		try {
            if (!txtErrorMsg.isDisplayed()) {
                jse.executeScript("arguments[0].click();", btnGetQuote);
				
            } else {
                throw new Exception("Details are not enetered properly");
            }
        } catch (Exception e) {
            try {
                driver.get(driver.getCurrentUrl());
                jse.executeScript("arguments[0].click();", btnGetQuote);
            } catch (Exception f) {
                throw new Exception("Problem in Navigating to Your Quote Page..!");
            }
        }

        Log.event("Clicked on Get Quote:");
        return new YourQuotePage(driver).get();
    }// getQuote

    private void selectDate(int day, String type) {
        if (type.equalsIgnoreCase("jointHolder"))
        	locator = cmbJointDay + "'" + day + "']";
        else if (type.startsWith("claim"))
        	locator = setClaimDay(Character.getNumericValue(type.charAt(type.length()-1))) + "'" + day + "']";
        else
        	locator = cmbDay + "'" + day + "']";
        
        try {
            selectDateMenu("Day");
            Log.event("Selected Day: " + day);
            selectOption(locator);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    private void selectMonth(String month, String type) {
    	if (type.equalsIgnoreCase("jointHolder"))
        	locator = cmbJointMonth + "'" + month + "']";
        else if (type.startsWith("claim"))
        	locator = setClaimMonth(Character.getNumericValue(type.charAt(type.length()-1))) + "'" + month + "']";
        else
    		locator = cmbMonth + "'" + month + "']";
        try {
            selectDateMenu("Month");
            Log.event("Selected Day: " + month);
            selectOption(locator);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }

    private void selectYear(int year, String type) {
    
    	if (type.equalsIgnoreCase("jointHolder"))
        	locator = cmbJointYear + "'" + year + "']";
        else if (type.startsWith("claim"))
        	locator = setClaimYear(Character.getNumericValue(type.charAt(type.length()-1))) + "'" + year + "']";
        else
            locator = cmbYear + "'" + year + "']";
        try {
        	Thread.sleep(100);
            selectDateMenu("Year");
            Log.event("Selected Day: " + year);
            selectOption(locator);
        } catch (Exception e) {

            e.printStackTrace();
        }      
    }

    private void selectDateMenu(String text) throws Exception {
        try {
            List<WebElement> menuOption = driver.findElements(By.cssSelector(menuDate));
            for (int i = 0; i < menuOption.size(); i++) {
                menu_value = menuOption.get(i).getText();
                if (menu_value.equals(text)) {
                    while (menuOption.get(i).isEnabled()) {

                        JavascriptExecutor jse = (JavascriptExecutor)driver;
                        jse.executeScript("arguments[0].scrollIntoView()", menuOption.get(i)); 
                    	jse.executeScript("arguments[0].click();", menuOption.get(i)); 
                        
                    	break;
                    }
                    menu_value = null;
                    break;
                }
            }
        } catch (Exception e) {
            throw new Exception("Unable to Select DOB");
        }
    }

    private void selectOption(String locator) throws Exception  {
		boolean isSelected = false;
    	List<WebElement> options = driver.findElements(By.xpath(locator));
        for (WebElement option : options) {
        	if (option.isEnabled() && option.isDisplayed()) {
        		//Utils.SetToOnScreen(option);

                option.click();
                
        		isSelected = true;
                break;
        	}        	    		
        }
        if (!isSelected) 
        	throw new Exception("No options has been selected from dropdown");
     
    } // selectOption

    private void selectRadioButton(String locator, String option) throws InterruptedException {
        List<WebElement> buttonOptions = driver.findElements(By.cssSelector(locator));

        for (int i = 0; i < buttonOptions.size(); i++) {
            radio_button_value = buttonOptions.get(i).getText();
            if (radio_button_value.equals(option) && buttonOptions.get(i).isDisplayed()) {
                WebElement button_select = buttonOptions.get(i).findElement(By.cssSelector("span.radio"));
                
                JavascriptExecutor jse = (JavascriptExecutor)driver;
                jse.executeScript("arguments[0].click()", button_select);               
                
                //button_select.click();
                
                radio_button_value = null;
                break;
            }
        }
    }// selectRadioButton

    private String setClaimDay(int id) {
    	return "//ul[@id='QUE_F5EF53E173A9139A20543.DAY_R" + id + "-menu']/li[text()=";
    }

    private String setClaimMonth(int id) {
    	return "//ul[@id='QUE_F5EF53E173A9139A20543.MONTH_R" + id + "-menu']/li[text()=";
    }

    private String setClaimYear(int id) {
    	return "//ul[@id='QUE_F5EF53E173A9139A20543.YEAR_R" + id + "-menu']/li[text()=";
    }

    private String setClaimValue(int id) {
    	return "input[id='QUE_F5EF53E173A9139A20545_R" + id + "']";
    }

    private String setClaimPolicySection(int id) {
    	return "span[id='QUE_F5EF53E173A9139A20547_R" + id + "-button']";
    }

    private String setTypeOfClaim(int id) {
    	return "span[id='QUE_F5EF53E173A9139A20549_R" + id + "-button']";
    }

    private String setcmbPolicySection(int id) {
    	return "//ul[@id='QUE_F5EF53E173A9139A20547_R" + id + "-menu']/li[text()=";
    }

    private String setcmbTypeOfClaim(int id) {
    	return "//ul[@id='QUE_F5EF53E173A9139A20549_R" + id + "-menu']/li[text()=";
    }
    
    private String setcmbCategory(int id) {
    	return "//ul[@id='QUE_F0C2BAC516DAABB925835_R" + id + "-menu']/li[text()=";
    }
    
    private String setPICategory(int id) {
    	return "span[id='QUE_F0C2BAC516DAABB925835_R" + id + "-button']";
    }

    private String setPIDescription(int id) {
    	return "input[id='QUE_F0C2BAC516DAABB925836_R" + id + "']";
    }

    private String setPIValue(int id) {
    	return "input[id='QUE_F0C2BAC516DAABB925837_R" + id + "']";
    }

    private String setcmbHRCategory(int id) {
    	return "//ul[@id='QUE_F0C2BAC516DAABB926236_R" + id + "-menu']/li[text()=";
    }
    
    private String setHRCategory(int id) {
    	return "span[id='QUE_F0C2BAC516DAABB926236_R" + id + "-button']";
    }
    
    private String setHRDescription(int id) {
    	return "input[id='QUE_F0C2BAC516DAABB926237_R" + id + "']";
    }

    private String setHRValue(int id) {
    	return "input[id='QUE_F0C2BAC516DAABB926238_R" + id + "']";
    }

    private String setBicycleDescription(int id) {
    	return "input[id='QUE_ACD332F0F8EFB64F6651_R" + id + "']";
    }

    private String setBicycleValue(int id) {
    	return "input[id='QUE_ACD332F0F8EFB64F6652_R" + id + "']";
    }
}