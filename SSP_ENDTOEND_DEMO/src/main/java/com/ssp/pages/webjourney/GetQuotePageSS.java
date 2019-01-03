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

import com.ssp.support.Log;
import com.ssp.support.Utils;
import com.ssp.support.WaitUtils;

/**
 * Get Quote Page
 */
public class GetQuotePageSS extends LoadableComponent<GetQuotePageSS> {

    private WebDriver driver;
    private boolean isPageLoaded;
    public String radCoverType = "div#p4_QUE_9547744E5D4FFB161866>div>div>label";
    private String cmbNumberofBedroom = "//ul[@id='QUE_9547744E5D4FFB161993-menu']/li[text()=";
    private String cmbPropertyType = "//ul[@id='QUE_72C3D2CAFB89E17B2628-menu']/li[text()=";
    private String cmbTitle = "//ul[@id='QUE_72C3D2CAFB89E17B2812-menu']/li[text()=";
    private String cmbDay = "//ul[@id='QUE_D37785741BC2F0CB1464333.DAY-menu']/li[text()=";// QUE_D37785741BC2F0CB1464333.DAY-menu
    private String cmbMonth = "//ul[@id='QUE_D37785741BC2F0CB1464333.MONTH-menu']/li[text()=";
    private String cmbYear = "//ul[@id='QUE_D37785741BC2F0CB1464333.YEAR-menu']/li[text()=";
    private String cmbBuildInsHeld = "//ul[@id='QUE_72C3D2CAFB89E17B2822-menu']/li[text()=";
    private String cmbContentInsHeld = "//ul[@id='QUE_72C3D2CAFB89E17B2824-menu']/li[text()=";
    public String radJointPolicy = "div#p4_QUE_72C3D2CAFB89E17B2820>div>div>label";
    public String radClaimsMade = "div#p4_QUE_72C3D2CAFB89E17B2826>div>div>label";
    public String radPersonalItems = "div#p4_QUE_72C3D2CAFB89E17B2828>div>div>label";
    public String radBicycle = "div#p4_QUE_72C3D2CAFB89E17B2830>div>div>label";
    public String radHighRisk = "div#p4_QUE_72C3D2CAFB89E17B2832>div>div>label";
    public String radPromoCodee = "div#p4_QUE_FE03B8B4E69C4F465444>div>div>label";
    public String menuDate = "span.ui-selectmenu-button.ui-widget[role='combobox']";
    private String radio_button_value = null;
    private String menu_value = null;
    private String locator = null;

    /**********************************************************************************************
     ********************************* WebElements of Home Page ***********************************
     **********************************************************************************************/
    @FindBy(css = "input.input-md[type='text'][name='HOMEINSURANCE[1].YOURDETAILS[1].HOME[1].ADDRESS[1].POSTCODE']")
    WebElement txtPostCode;

    @FindBy(css = "button[title='Find Address']")
    WebElement btnFindAddress;

    @FindBy(css = "input.input-md[type='text'][name='HOMEINSURANCE[1].YOURDETAILS[1].HOME[1].YEAROFBUILD']")
    WebElement txtYearOfBuild;

    @FindBy(css = "input.input-lg[type='text'][name='HOMEINSURANCE[1].YOURDETAILS[1].ABOUTYOU[1].FIRSTNAME']")
    WebElement txtFirstName;

    @FindBy(css = "input.input-lg[type='text'][name='HOMEINSURANCE[1].YOURDETAILS[1].ABOUTYOU[1].SURNAME']")
    WebElement txtLastName;

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
    public GetQuotePageSS(WebDriver driver) throws Exception {
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

        try {
        	WaitUtils.waitForPageLoad(driver);
            (new WebDriverWait(driver, 60).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtPostCode));
        } catch (TimeoutException f) {
            try {
                throw new Exception("Your Details Page is not Loaded");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }// isLoaded

    @Override
    protected void load() {

        isPageLoaded = true;
    }// load

    public void verifyGetQuotePage() throws Exception {
    	WaitUtils.waitForElement(driver, txtLastName, 20);
    }// verifyGetQuotePage

    public void enterPostCode(String postCode) {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtPostCode));
        txtPostCode.clear();
        txtPostCode.sendKeys(postCode);
        Log.event("Entered the PostCode: " + postCode);
    }// enterPostCode

    public void selectCoverType(String covertype) throws Exception {

        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtAddress));
        try {
            selectRadioButton(radCoverType, covertype);
        } catch (InterruptedException e) {
            throw new Exception("Unable to Select Cover Type");
        }
        Log.event("Selected Property Type :" + covertype);
    }// selectJointPolicy

    public void enterYearOfBuild(String yearOfBuild, boolean screenShot) {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtAddress));
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtYearOfBuild));
        txtYearOfBuild.clear();
        txtYearOfBuild.sendKeys(yearOfBuild);
        Log.event("Entered the yearOfBuild: " + yearOfBuild);
    }// enterYearOfBuild

    public void enterFirstName(String firstName) {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtFirstName));
        txtFirstName.clear();
        txtFirstName.sendKeys(firstName);
        Log.event("Entered the firstName: " + firstName);
    }// enterFirstName

    public boolean verifyFirstName(String first_name) {
        boolean firstname = true;
        if (!txtFirstName.getAttribute("value").equals(first_name)) {
            firstname = false;
        }
        Log.event("First Name verified successfully");
        return firstname;
    }// enterFirstName

    public boolean verifyLastName(String last_name) {
        boolean lastname = true;
        if (!txtLastName.getAttribute("value").equals(last_name)) {
            lastname = false;
        }
        Log.event("Last Name verified successfully");
        return lastname;
    }// verifyLastName

    public boolean verifyDetails(String customer_name) {
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

    public void enterLastName(String lastName) {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtLastName));
        txtLastName.clear();
        txtLastName.sendKeys(lastName);
        Log.event("Entered the lastName: " + lastName);
    }// enterLastName

    public void clickFindAddress(boolean screenShot) {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(btnFindAddress));
        btnFindAddress.click();
        Log.event("Clicked on Find Address");
    }// clickFindAddress

    public void selectNumberofBedroom(String noOfBedrooms, boolean screenShot) {
        locator = cmbNumberofBedroom + noOfBedrooms + "]";
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtYearOfBuild));
        menuNumberofBedrooms.click();
        selectOption(locator);
        Log.event("Selected Number of Bedrooms :" + noOfBedrooms);
    }// selectNumberofBedroom

    public void selectDateofBirth(String dateofBirth) throws InterruptedException {
        String dateformat[] = dateofBirth.split("-");
        int day = Integer.parseInt(dateformat[0]);
        String month = dateformat[1];
        int year = Integer.parseInt(dateformat[2]);
        Thread.sleep(2000);
        selectDate(day);
        selectMonth(month);
        selectYear(year);
        Log.event("Selected Day :" + day);
        Log.event("Selected Month :" + month);
        Log.event("Selected Year :" + year);
    }// selectDateOfBirth

    public void selectTypeOfProperty(String propertyType) {
        locator = cmbPropertyType + "'" + propertyType + "']";
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtYearOfBuild));
        menuPropType.click();
        selectOption(locator);
        Log.event("Selected Property Type :" + propertyType);
    }// selectTypeOfProperty

    public void selectTitle(String Title) {
        locator = cmbTitle + "'" + Title + "']";
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(txtYearOfBuild));
        menuTitle.click();
        selectOption(locator);
        Log.event("Selected Property Type :" + Title);
    }// selectTitle

    public void selectBuildingIns(String buildinsurance) {
        locator = cmbBuildInsHeld + buildinsurance + "]";
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(menuBuildInsurance));
        menuBuildInsurance.click();
        selectOption(locator);
        Log.event("Selected Property Type :" + buildinsurance);
    }// selectBuildingIns

    public void selectContentIns(String contentinsurance) {
        locator = cmbContentInsHeld + contentinsurance + "]";
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(menuContentsInsurance));
        menuContentsInsurance.click();
        selectOption(locator);
        Log.event("Selected Property Type :" + contentinsurance);
    }// selectContentIns

    public void selectJointPolicy(String jointpolicy) throws Exception {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(menuContentsInsurance));
        try {
            selectRadioButton(radJointPolicy, jointpolicy);
        } catch (InterruptedException e) {
            throw new Exception("Unable to Select Joint Policy Holder");
        }
        Log.event("Selected Property Type :" + jointpolicy);
    }// selectJointPolicy

    public void selectClaimsMade(String claimsmade) throws Exception {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(menuContentsInsurance));
        try {
            selectRadioButton(radClaimsMade, claimsmade);
        } catch (InterruptedException e) {
            throw new Exception("Unable to Select Claims Made");
        }
        Log.event("Selected Property Type :" + claimsmade);
    }// selectClaimsMade

    public void selectPersonalItems(String personalitems) throws Exception {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(menuContentsInsurance));
        try {
            selectRadioButton(radPersonalItems, personalitems);
        } catch (InterruptedException e) {
            throw new Exception("Unable to Select Personal Items Option");
        }
        Log.event("Selected Property Type :" + personalitems);
    }// selectClaimsMade

    public void selectBicycle(String bicycle) throws Exception {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(menuContentsInsurance));
        try {
            selectRadioButton(radBicycle, bicycle);
        } catch (InterruptedException e) {
            throw new Exception("Unable to Select Bicycle Option");
        }
        Log.event("Selected Property Type :" + bicycle);
    }// selectClaimsMade

    public void selectHighRisk(String highrisk) throws Exception {
        try {
            (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(menuContentsInsurance));
            // Point hoverItem
            // =driver.findElement(By.cssSelector(radBicycle)).getLocation();
            ((JavascriptExecutor) driver).executeScript("return window.title;");
            Thread.sleep(6000);
            ((JavascriptExecutor) driver).executeScript("window.scroll(0,1200)");
            selectRadioButton(radHighRisk, highrisk);
            Log.event("Selected High Risk Type :" + highrisk);
        } catch (Exception e) {
            throw new Exception("Unable to Select High Risk Option");
        }

    }// selectHighRisk

    public void selectPromocode(String promoCode) throws Exception {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(menuContentsInsurance));
        try {
            selectRadioButton(radPromoCodee, promoCode);
        } catch (InterruptedException e) {
            throw new Exception("Unable to Select Promo Code");
        }
        Log.event("Selected Property Type :" + promoCode);
    }// selectPromocode

    public YourQuotePage getQuote(WebDriver driver, boolean screenShot) throws Exception {
        (new WebDriverWait(driver, 180).pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage("Unable to find username text box")).until(ExpectedConditions.visibilityOf(btnGetQuote));
        Log.event("Navigating to Your Quote Page");
        try {
            if (!txtErrorMsg.isDisplayed()) {
                btnGetQuote.click();
            } else {
                throw new Exception("Details are not enetered properly");
            }
        } catch (Exception e) {
            try {
                driver.get(driver.getCurrentUrl());
                btnGetQuote.click();
            } catch (Exception f) {
                throw new Exception("Problem in Navigating to Your Quote Page..!");
            }
        }

        Log.event("Clicked on Get Quote:");
        return new YourQuotePage(driver).get();
    }// selectPromocode

    private void selectDate(int day) {
        locator = cmbDay + "'" + day + "']";
        try {
            selectDateMenu("Day");
            Log.event("Selected Day: " + day);
        } catch (Exception e) {
            e.printStackTrace();
        }
        selectOption(locator);
    }

    private void selectMonth(String month) {
        locator = cmbMonth + "'" + month + "']";
        try {
            selectDateMenu("Month");
            Log.event("Selected Day: " + month);
        } catch (Exception e) {
            e.printStackTrace();
        }
        selectOption(locator);

    }

    private void selectYear(int year) {
        locator = cmbYear + "'" + year + "']";
        try {
            selectDateMenu("Year");
            Log.event("Selected Day: " + year);
        } catch (Exception e) {
            e.printStackTrace();
        }
        selectOption(locator);

    }

    private void selectDateMenu(String text) throws Exception {
        try {
            List<WebElement> menuOption = driver.findElements(By.cssSelector(menuDate));
            for (int i = 0; i < menuOption.size(); i++) {
                menu_value = menuOption.get(i).getText();
                if (menu_value.equals(text)) {
                    while (menuOption.get(i).isEnabled()) {
                        menuOption.get(i).click();
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

    private void selectOption(String locator) {
        List<WebElement> options = driver.findElements(By.xpath(locator));
        for (WebElement option : options) {
            if (!option.isSelected()) {
                option.click();
            }
        }
    } // selectOption

    private void selectRadioButton(String locator, String option) throws InterruptedException {
        List<WebElement> buttonOptions = driver.findElements(By.cssSelector(locator));

        for (int i = 0; i < buttonOptions.size(); i++) {
            radio_button_value = buttonOptions.get(i).getText();
            if (radio_button_value.equals(option)) {
                WebElement button_select = buttonOptions.get(i).findElement(By.cssSelector("span.radio"));
                Actions action = new Actions(driver);
                action.moveToElement(button_select).click().build().perform();
                radio_button_value = null;
                break;
            }
        }
    }// selectRadioButton
}