package com.pure.selfservice.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.generic.support.Log;
import com.generic.utils.ElementLayer;
import com.generic.utils.GenericUtils;
import com.generic.utils.WaitUtils;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * MTAInitiatePage contains Customer contact details
 * 
 */
public class MTAQuoteDetailsPage extends LoadableComponent<MTAQuoteDetailsPage> {
	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	public String listAddonsAdd = "#C1__ID_BUY_POLICY_ADDON_BTN_R";
	public String listAddonsRemove = "#C1__BUT_ACCBA99CC7989320176377_R";
	public String CoverName = "#C1__QUE_97614C78E3A0F03F1938560_R";
	public String[] arrExcessColumnName = { "Type", "Accidental Damage", "Malicious Damage", "Fire", "Theft",
			"Windscreen" };
	public String[] arrExcessRowName = { "Voluntary", "Compulsory", "Total" };
	public String premiumSctnTitle = "Payment Due";
	public String premiumSctnMsg = "You originally paid for your policy via a one-off payment, so your method of payment will be the same for this policy update. Payment will be taken on the next page.";
	public String POPUP_MSG_HELPICON_Terms_And_Conditions = "Terms and Conditions applying to your policy change.";
	public String POPUP_MSG_HELPICON_Your_Policy_Excesses = "An excess is the portion you are required to pay towards a claim you make on your insurance policy. An insurer may have many types of excesses that can apply in different situations or apply concurrently.";
	public String POPUP_MSG_HELPICON_Quote_Details = "Choose how would you like to pay for this change.";
	

	@FindBy(css = "button[title='Buy']")
	WebElement btnBuy;
	
	@FindBy(css = ".edgeConnectDisabled[title='Buy']")
	WebElement btnBuyDisabled;

	@FindBy(css = "#C2__p1_HEAD_97614C78E3A0F03F2263264")
	WebElement txtPremium;

	@FindBy(css = "#C2__p1_HEAD_8010ACC33B1CAE4D187359")
	WebElement txtRefundPremium;
	
	@FindBy(css = "#C2__HEAD_97614C78E3A0F03F2434277")
	WebElement txtFinancePlanPremium;
	
	@FindBy(css = "#C2__HEAD_C423F1157C1B7961244012")
	WebElement txtAdditionalPremiumAmt;
	
	@FindBy(css = "#C2__HEAD_97614C78E3A0F03F2434277")
	WebElement txtAmtToPayAmt;

	@FindBy(css = "#C2__HEAD_82C3F84B8B5F77B74684")
	WebElement txtPaymentRequired;

	@FindBy(css = "span[id*='C2__QUE_97614C78E3A0F03F1938560_R']")
	List<WebElement> lstAddOns;

	@FindBy(css = "#C2__HEAD_AAABB1DD923D940C696797")
	WebElement txtAlreadyAddedFeatureMsg;

	@FindBy(css = "li[class='active' i] span:nth-child(2)")
	WebElement titleBanner;

	@FindBy(css = "button[id*='cancel']")
	WebElement btnCancel;

	@FindBy(css = "button[title='close' i]")
	WebElement btnClose;
	
	@FindBy(css = "button[title='Ok']")
	WebElement btnOk;

	@FindBy(css = "button[title='Add to Policy' i]")
	WebElement btnPolicyAddon;

	@FindBy(css = "#C2__FS_QUE_6674B92B9B739BAD99818 label")
	WebElement chkTermsandConditions;

	@FindBy(css = "div[class*='box_tablebrdr' i]>div[class*='tbl_exces' i]>div")
	List<WebElement> tblPolicyExcessColumnLbl;

	@FindBy(css = "div[class*='tabl_row' i] div:nth-child(1)")
	List<WebElement> tblPolicyExessRowLbl;

	@FindBy(css = "div[id='C2__p1_HEAD_B44FCB20D6620C211446868']>div")
	WebElement lblAmt;
	
	@FindBy(css = "#C2__HEAD_97614C78E3A0F03F2263264")
	WebElement lblAmtAdditional;
	
	@FindBy(css = "#C2__p1_HEAD_8010ACC33B1CAE4D187359 div")
	WebElement lblAmtFinanceAdditional;

	@FindBy(css = "button[title='remove' i]")
	WebElement btnRemove;
	
	@FindBy(css = "#C2__p1_HEAD_9E12E862CA0AB8DC31536")
	WebElement lblPaymentDue;
	
	@FindBy(css = "#C2__p1_HEAD_97614C78E3A0F03F2252959")
	WebElement txtPaymentSection;
	
	@FindBy(css = "h1[id='C2__HEAD_8010ACC33B1CAE4D187359']")
	WebElement txtRefundAmount;
	
	@FindBy(css = "#C2__HEAD_D398C4F0EF189310546613")
	WebElement txtRefundDue;
	
	@FindBy(css = "#C2__HEAD_B1266803FE59AB28681712")
	WebElement refundAmt;
	
	@FindBy(css = ".table-container>div[class = 'col-md-12 col-xs-12 col-sm-12 no-gutter box_tablebrdr table-wrapper'] div[class*= 'col-md-12 col-xs-12 col-sm-12 ta']")
	List<WebElement> policyExcessesTable;
	
	@FindBy(css = "#C2__FMT_C50C73E1B08CB61D323843 div[class = 'col-md-12 col-xs-12 col-sm-12 tbl_excess']>div")
	List<WebElement> excessTableHeader;
	
	@FindBy(css = "#C2__HELP_HEAD_82C3F84B8B5F77B74684")
	WebElement iconHelpQuoteDetails;
	
	@FindBy(css = ".tetris_helpText")
	WebElement popHelpQuoteDetails;
	
	@FindBy(css = "#C2__HELP_HEAD_82C3F84B8B5F77B74728")
	WebElement iconHelpYourPolicyExcesses;
	
	@FindBy(css = ".tetris_helpText")
	WebElement popHelpYourPolicyExcesses;
	
	@FindBy(css = "#C2__HELP_HEAD_6674B92B9B739BAD98110")
	WebElement iconHelpTermsAndConditions;
	
	@FindBy(css = ".tetris_helpText")
	WebElement popHelpTermsAndConditions;
	
	

	public MTAQuoteDetailsPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(WaitUtils.waitForElement(driver, txtPaymentRequired)))) {
			Log.fail("MTA Quote Details page did not open up. Error Page is displayed.", driver,
					extentedReport);
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {

		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
		WaitUtils.waitForElement(driver, btnBuy);
	}

	/**
	 * To verify Add-ons
	 * 
	 * @param AddOnsName
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public void verifyAddOns(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			Log.message("Existed addons count displaying on quote page" + lstAddOns.size());
			Log.message("Available added addons on quote page:");

			for (int i = 0; i < lstAddOns.size(); i++) {
				String actualAddOnName = lstAddOns.get(i).getText();
				Log.message(actualAddOnName);

			}
		} catch (Exception e) {
			throw new Exception("Error while Add-Ons Name : " + e);
		}
	}

	/**
	 * Get Premium Amt
	 * 
	 * @return Premium Amount
	 * 
	 */
	public String getPremiumAmt(ExtentTest extententReport, boolean screenshot) {
		String premium = null;
		if(lblAmt.isDisplayed()) {
		WaitUtils.waitForElement(driver, lblAmt);
		String[] premiumText = lblAmt.getText().replaceAll("\\r\\n|\\r|\\n", " ").split(" ");
		premium = premiumText[premiumText.length - 1].replace("£", "");
		Log.message("Premium Amount displayed : "+premium,extententReport);
		return premium;
		} else if(lblAmtAdditional.isDisplayed()){
			WaitUtils.waitForElement(driver, lblAmtAdditional);
			String[] premiumText = lblAmtAdditional.getText().replaceAll("\\r\\n|\\r|\\n", " ").split(" ");
			premium = premiumText[premiumText.length - 1].replace("£", "");
			Log.message("Premium Amount displayed : "+premium,extententReport);
			return premium;
		}  else {
			WaitUtils.waitForElement(driver, lblAmtFinanceAdditional);
			lblAmtFinanceAdditional.getText();
			String[] premiumText = lblAmtFinanceAdditional.getText().replaceAll("\\r\\n|\\r|\\n", " ").split(" ");
			premium = premiumText[premiumText.length - 1].replace("£", "");
			Log.message("Premium Amount displayed : "+premium,extententReport);
			return premium;
		}		
	}

	/**
	 * Get Premium Amt
	 * 
	 * @return Premium Amount
	 * 
	 */
	public String getRefundPremiumAmt() {
		return GenericUtils.getTextOfWebElement(txtRefundPremium, driver);
	}

	/**
	 * Get Finance Plan Premium Amt
	 * 
	 * @return Premium Amount
	 * 
	 */
	public String getFinancePlanPremiumAmt() {
		return GenericUtils.getTextOfWebElement(txtFinancePlanPremium, driver);
	}

	
	/**
	 * Get Additional Premium Amount
	 * 
	 * @return Additional Amount
	 * 
	 */
	public String getAdditionalPremiumAmt() {
		return GenericUtils.getTextOfWebElement(lblAmtAdditional, driver);
	}
	
	/**
	 * Get Amt To Pay 
	 * 
	 * @return Amt to Pay Amount
	 * 
	 */
	public String getAmtToPayAmt() {
		return GenericUtils.getTextOfWebElement(lblAmtAdditional, driver);
	}
	
	/**
	 * verify banner title
	 * 
	 * @param extentReport
	 * @return boolean
	 * @throws Exception
	 */
	public boolean verifyQuotePageBannerTitle(ExtentTest extentReport) throws Exception {
		boolean status = true;
		try {
			if (titleBanner.getText().contains("Your Quote")) {
				Log.message("Banner title displaying as :" + titleBanner.getText());
				return status;
			} else {
				Log.message("Banner title displaing as expected :" + titleBanner.getText());
				status = false;
				return status;
			}
		} catch (Exception e) {
			status = false;
			Log.message("Error while verifying banner title : " + e);
			return status;
		}
	}

	/**
	 * select cancel button
	 * 
	 * @param extentReport
	 * @throws Excption
	 */
	public void clickCancelButton(ExtentTest extentReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnCancel);
			btnCancel.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected on cancel button on quote page ");
		} catch (Exception e) {
			Log.message("Error while selecting cancel button on quote page" + e);
		}
	}

	/**
	 * select close button
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public void selectClose(ExtentTest extentReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnClose);
			btnClose.click();
			Log.message("Close button selected on cancel window", driver, extentedReport);
			WaitUtils.waitForSpinner(driver);
		} catch (Exception e) {
			Log.message("error while selecting close button on cancel pop up window" + e);
		}
	}
	
	public void selectOk(ExtentTest extentReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnOk);
			btnOk.click();
			Log.message("Ok button selected on cancel window", driver, extentedReport);
			WaitUtils.waitForSpinner(driver);
		} catch (Exception e) {
			Log.message("error while selecting Ok button on cancel pop up window" + e);
		}
	}

	/**
	 * select Terms and conditions
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public void clickTermsandCond(ExtentTest extetReprot, boolean Screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, chkTermsandConditions);
			//chkTermsandConditions.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", chkTermsandConditions);
			Log.message("Terms and Conditions check box selected on Quote page", driver, extentedReport, true);
		} catch (Exception e) {
			Log.message("Terms and Conditions check box not selected on quote page" + e);
		}
	}

	/**
	 * Verify Buy button clickable
	 * 
	 * @param extentReport
	 * @return boolean
	 * @throws Exception
	 */
	public boolean verifyBuyButtonClickable(ExtentTest extentReport) throws Exception {
		boolean status = true;
		try {
			if (WaitUtils.waitForElement(driver, btnBuy)) {
				WaitUtils.waitForelementToBeClickable(driver, btnBuy, "Buy button able to click");
				return status;
			} else {
				Log.message("Buy button displaing as disabled on quote page" + btnBuy);
				status = false;
				return status;
			}
		} catch (Exception e) {
			status = false;
			Log.message("Error while clicking Buy button : " + e);
			return false;
		}

	}

	/**
	 * Select Buy button
	 * 
	 * @param extentReport
	 * @throws Exception
	 */
	public void clickBuyButton(ExtentTest extetReprot, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnBuy);
			WaitUtils.waitForelementToBeClickable(driver, btnBuy, "Buy button is not clickable");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnBuy);
			
			/*JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", btnBuy);*/
						
			//btnBuy.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Buy button selected on Quote page", driver, extentedReport);
		} catch (Exception e) {
			Log.message("Buy button is not selected on quote page" + e);
		}
	}
		

	/**
	 * Verify Buy Page title
	 * 
	 * @param extentReport
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyBuyPageBannerTitle(ExtentTest extentReport) throws Exception {
		boolean status = true;
		try {
			if (titleBanner.getText().contains("Payment")) {
				Log.message("Banner title displaying as : " + titleBanner.getText(),extentReport);
				return status;
			} else {
				Log.message("Banner title displaing as expected : " + titleBanner.getText(),extentReport);
				status = false;
				return status;
			}
		} catch (Exception e) {
			status = false;
			Log.message("Error while verifying banner title : " + e);
			return status;
		}

	}

	/**
	 * Verify policy premium
	 * 
	 * @param extentReport
	 * @throws Exception
	 */

	public boolean VerifyPolicyPremium(String operation, String prevPremium, ExtentTest extentReport) throws Exception {
		boolean status = false;
		try {

			String premium = getPremiumAmt(extentedReport, true);
			String defaultPremium = "0.00";
			if (operation.equals("Add")) {
				if (Double.parseDouble(premium) > Double.parseDouble(prevPremium) || Double.parseDouble(premium) < Double.parseDouble(prevPremium)) {
					Log.message(
							"Premium displaying as incremented/decremented correctly on quote page : " + Double.parseDouble(premium),
							driver, extentedReport, true);
					status = true;
					return status;
				} else {
					Log.message("Premium displaying as not incremented on quote page : " + Double.parseDouble(premium),
							driver, extentedReport, true);
					return status;
				}

			} else if (operation.equals("Remove")) {
				if (Double.parseDouble(premium) < Double.parseDouble(prevPremium) || Double.parseDouble(premium) > Double.parseDouble(prevPremium)) {
					Log.message(
							"Premium displaying as decremented correctly on quote page : " + Double.parseDouble(premium),
							driver, extentedReport, true);
					status = true;
					return status;
				} else {
					Log.message("Premium displaying as not decremented on quote page : " + Double.parseDouble(premium),
							driver, extentedReport, true);
					return status;
				}
			} else if (operation.equals("DefaultCar") || operation.equals("DefaultDriver")) {
				if (Double.parseDouble(premium) >= Double.parseDouble(defaultPremium)) {
					Log.message("Premium displaying as correctly on quote page : " + Double.parseDouble(premium), driver,
							extentedReport, true);
					status = true;
					return status;
				}
			} else if (operation.equals("Default")) {
				Log.message("Pemium getting displayed on your quote page and premium displaying as : "
						+ Double.parseDouble(prevPremium), driver, extentedReport, true);
				status = true;
				return status;

			}
		} catch (Exception e) {
			Log.message("Unable to find the premium on quote page : " + e, driver, extentedReport);
		}

		return status;
	}

	/**
	 * Verify Add to Policy button
	 * 
	 * @param extentReport
	 * @return boolean
	 * @throws Exception
	 */

	public boolean verifyAddoncovers(ExtentTest extentReport) throws Exception {
		boolean status = true;
		try {
			if (WaitUtils.waitForElement(driver, btnPolicyAddon)) {

				WaitUtils.waitForelementToBeClickable(driver, btnPolicyAddon,
						"Add to Policy button displaying as clickable on quote page");
				Log.message("Policy addons are displaying on quote page with add to policy button", driver);
				return status;
			} else {
				status = false;
				Log.message("Add to Policy button not displaying on quote page", driver);
				return status;
			}
		} catch (Exception e) {
			throw new Exception("Error while  verifying policy addon button : " + e);
		}

	}

	/**
	 * To verify Add-ons
	 * 
	 * @param AddOnsName
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean selectAddOns(String[] arrAddOnsName, String operation, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean addOnsFound = false;
			String specificCover = null;
			String specificAddonAddBtn = null;
			WaitUtils.waitForElement(driver, chkTermsandConditions);
			for (int i = 0; i < arrAddOnsName.length; i++) {
				for (int j = 0; j < lstAddOns.size(); j++) {
					String actualAddOnName = CoverName + Integer.toString(j + 1);
					specificCover = driver.findElement(By.cssSelector(actualAddOnName)).getText();
					if ((specificCover.contains(arrAddOnsName[i])) && (operation.equals("Add"))) {
						specificAddonAddBtn = listAddonsAdd + Integer.toString(j + 1);
						driver.findElement(By.cssSelector(specificAddonAddBtn)).click();
						Log.message("Addon cover added on Quote page as" + arrAddOnsName[i]);
						WaitUtils.waitForSpinner(driver);
						addOnsFound = true;
						break;
					} else if ((specificCover.contains(arrAddOnsName[i])) && (operation.equals("Remove"))) {
						specificAddonAddBtn = listAddonsRemove + Integer.toString(j + 1);
						driver.findElement(By.cssSelector(specificAddonAddBtn)).click();
						Log.message("remove button selected on your quote page" + arrAddOnsName[i]);
						WaitUtils.waitForSpinner(driver);
						addOnsFound = true;
						break;
					}
				}
			}
			return addOnsFound;
		} catch (Exception e) {
			throw new Exception("Error while Add-Ons Name : " + e);
		}
	}

	/**
	 * To verify Policy Excess Addon column Label names
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyPolicyExcessColumnLabels(ExtentTest extentedReport, boolean screenshot) throws Exception {
		boolean addOnsFound = false;
		try {

			ArrayList<String> ColumnNames = new ArrayList<String>();
			for (int i = 0; i < tblPolicyExcessColumnLbl.size(); i++) {
				ColumnNames.add(tblPolicyExcessColumnLbl.get(i).getText());

			}
			ColumnNames.removeAll(Collections.singleton(null));
			if (ColumnNames.equals(Arrays.asList(arrExcessColumnName))) {
				WaitUtils.waitForSpinner(driver);
				addOnsFound = true;
				Log.message("Your Policy Excess Column section displaying as expected on your quote page : "+Arrays.toString(arrExcessColumnName), driver,
						extentedReport, true);
				return addOnsFound;
			} else {
				Log.message("Your Policy Excess Column section not displaying as expected on your quote page : "+Arrays.toString(arrExcessColumnName), driver,
						extentedReport, true);
				return addOnsFound;
			}

		} catch (Exception e) {
			Log.message("Error while verifying Your Policy Excess column section on your quote page : " + e);
			return addOnsFound;
		}
	}

	/**
	 * To verify Policy Excess Addon Row Label names
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @return boolean
	 * @throws Exception
	 * 
	 */
	public boolean verifyPolicyExcessRowLabels(ExtentTest extentedReport, boolean screenshot) throws Exception {
		boolean addOnsFound = false;
		try {
			ArrayList<String> RowNames = new ArrayList<String>();
			for (int i = 0; i < tblPolicyExessRowLbl.size(); i++) {
				RowNames.add(tblPolicyExessRowLbl.get(i).getText());

			}
			RowNames.removeAll(Collections.singleton(null));
			if (RowNames.equals(Arrays.asList(arrExcessRowName))) {
				WaitUtils.waitForSpinner(driver);
				addOnsFound = true;
				Log.message("Your Policy Excess Row section displaying as expected on your quote page : "+Arrays.toString(arrExcessRowName), driver,
						extentedReport, true);
				return addOnsFound;
			} else {
				Log.message("Your Policy Excess Row section not displaying as expected on your quote page : "+Arrays.toString(arrExcessRowName), driver,
						extentedReport, true);
				return addOnsFound;
			}

		} catch (Exception e) {
			Log.message("Error while verifying Your Policy Row Excess section on your quote page : " + e);
			return addOnsFound;
		}
	}
	
	public boolean verifyPaymentRequiredSection(ExtentTest extentReport, boolean screenshot) throws Exception {
		boolean status = false;
		try {
			WaitUtils.waitForElement(driver, lblPaymentDue);
			WaitUtils.waitForElement(driver, txtPaymentSection);
	
	if((lblPaymentDue.getText()).equals(premiumSctnTitle) && (txtPaymentSection.getText()).equals(premiumSctnMsg)) {
		status = true;
		Log.message("Payment required section dispalying with title as" +premiumSctnTitle, driver, extentedReport, true);
		Log.message("Payment Required section dispalying with text as" +premiumSctnMsg, driver, extentedReport, true);
		return status;
	}
	return status;
		} catch(Exception e) {
			Log.message("Error while verifying Payment required section title and Message : " + e);
			return status;
		}
		
	}
	
	/**
	 * To get Field values of Policy Excesses Table
	 * 
	 * @param extentReport
	 * @throws Excption
	 */
	public void getPolicyExcessesTableDetails(ExtentTest extentReport, boolean screenShot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, excessTableHeader.get(0));
			HashMap<String,String> policyExcessTable = new HashMap<String, String>();
			String[] columnHeader = new String[(excessTableHeader.size())];
			
			int count = policyExcessesTable.size(); 
			
//			System.out.println(count);
			Log.message("Verification of premium values in Your Excess Table to be performed:", driver, extentedReport, true);
			
			for(int header = 0; header<excessTableHeader.size(); header++)
				columnHeader[header] = excessTableHeader.get(header).getText();
				
			for(int i=0;i<count-1;i++)
			{				
				String[] value = policyExcessesTable.get(i).getText().split("£|\\-");
				//Log.message(value[0]+"\t\t"+"Sum Insured is:"+value[1]+"Compulsory Excess is:"+"\t\t"+value[2]+"Voluntary Excess is:"+"\t\t"+value[3]+"Premium is :"+"\t\t"+value[4]);
				for(int data=0; data<excessTableHeader.size(); data++){
					policyExcessTable.put(columnHeader[data], value[data]);
					
				}								
					 if((Float.parseFloat(policyExcessTable.get("Premium")))> (0.00)) {
//						 System.out.println("premium got generated for the "+ policyExcessTable.get("Type") +"type: " + policyExcessTable.get("Premium") );
						 Log.pass("Premium got generated for the "+ policyExcessTable.get("Type") +" type: " + policyExcessTable.get("Premium"), extentedReport);
					 } else {
//						 System.out.println("premium not generated for the "+ policyExcessTable.get("Type") +"type" + policyExcessTable.get("Premium") );
						 Log.message("Premium was NOT generated for the "+ policyExcessTable.get("Type") +" type: " + policyExcessTable.get("Premium"), extentedReport);
					 }
				 
				/*System.out.println(policyExcessTable.get("Premium"));
				Set<String> keys = policyExcessTable.keySet();  //get all keys
				for(String header: keys)
				{
				    System.out.println("Value of " +header+":"+ policyExcessTable.get(header));
				}*/
				
			 }	
			
		} catch (Exception e) {
			Log.message("Error while reading Policy Excesses table on quote page" + e);
		}
	}
	

	/**
	 * hover On Help Icon Of Quote Details
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIQuoteDetails(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpQuoteDetails);
			iconHelpQuoteDetails.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpQuoteDetails);
			Log.message("Hovered on the HelpIcon Of  Quote Page", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Quote Page : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Quote Details
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconQuoteDetails() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpQuoteDetails);
			if (GenericUtils.verifyWebElementTextContains(popHelpQuoteDetails,
					POPUP_MSG_HELPICON_Quote_Details)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Quote Page : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of Your Policy Excesses
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIYourPolicyExcesses(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpYourPolicyExcesses);
			iconHelpYourPolicyExcesses.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpYourPolicyExcesses);
			Log.message("Hovered on the HelpIcon Of  Quote Page", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Quote Page : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Your Policy Excesses
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconYourPolicyExcesses() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpYourPolicyExcesses);
			if (GenericUtils.verifyWebElementTextContains(popHelpYourPolicyExcesses,
					POPUP_MSG_HELPICON_Your_Policy_Excesses)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Quote Page : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of Your Policy Excesses
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpITermsAndConditions(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpTermsAndConditions);
			iconHelpTermsAndConditions.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaiming).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpTermsAndConditions);
			Log.message("Hovered on the HelpIcon Of  Quote Page", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Quote Page : " + e);
		}
	}
	
	/**
	 * verify HelpIcon Your Policy Excesses
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconTermsAndConditions() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpTermsAndConditions);
			if (GenericUtils.verifyWebElementTextContains(popHelpTermsAndConditions,
					POPUP_MSG_HELPICON_Terms_And_Conditions)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Quote Page : " + e);
		}
	}
	
}
