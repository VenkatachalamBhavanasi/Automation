package com.pure.selfservice.pages;

import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import java.util.ListIterator;

import com.generic.support.Log;
import com.generic.support.StopWatch;
import com.generic.utils.ElementLayer;
import com.generic.utils.GenericUtils;
import com.generic.utils.WaitUtils;
import com.relevantcodes.extentreports.ExtentTest;

//import net.sf.saxon.value.IntegerValue;

/**
 * CustomerDashboardPage contains Customer contact details
 * 
 */
public class CustomerDashboardPage extends LoadableComponent<CustomerDashboardPage> {
	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	public final String ERR_MSG_EMAIL_ADDRESS_UPDATE = "This e-mail address is already in use by another client.";
	public final String ERR_MSG_POSTCODE_VALIDATION = "Please enter a valid postcode";
	public final String ERR_MSG_NO_ADDRESS = "No Result Found";
	public final String MSG_ADDRESS_UPDATE_CONTACT_DETAILS = "Thank you for advising us of the change to your correspondence details. If any of your policies need to be updated because of this change in details, you will ALSO need to use the \"Change my policy\" option for each of those policies to update your details.";
	public final String MSG_EMAIL_UPDATE_CONTACT_DETAILS = "This email address will not be used for logging into the self-service site.";
	public final String ERR_MSG_RENEWAL_POPUP = "Your policy cannot be changed online because it is within 30 days of the renewal date. Please contact us to make changes.";
	public final String ERR_MSG_FREEZE_POPUP = "Your policy cannot be changed online please contact us for assistance.";
	public final String ERR_MSG_CANCEL_POLICY = "You have decided not to proceed with your change. No changes will be made to your policy.";
	public final String POPUP_MSG_HELPICON_MANAGE_CONTACT = "For changes to your email, phone and mailing address.To change the address on your policy select ‘Change My Policy’ below.";
	public final String MDL_CONTENT_EMAIL_CHANGE = "This email address will now be used for logging into your online account.";
	public final String Err_MSG_EMAIL_INVALID = "Please enter a valid email";

	public String collapse = "a[class*='viewaccordian']";
	public String btnChangeMyPolicy = "#C2__BUT_7D4422F57C5E7EC5157917_R";
	public String cssMarketingPrefOptionsYes = "div[id='FMT_90BD42C1720991FC288650']";
	public String lblCnctDetails = "div.form-group.clearfix>label";
	public String cnctDetailsValues = "#FMT_1E5656EA221EC753631642 .text-bold";
	public String policyHeaderValues = "div#FMT_7F8F110B7A232E78358483_R1 div[class*='noPadLft']";
	public String txtPolicyNumber = "#C2__QUE_B3A80BADFCD28CFD90822_R";
	public String txtLineOfBussiness = "#C2__QUE_48488D15143D8441289614_R";
	public String txtInsurer = "#C2__QUE_48488D15143D8441289753_R";
	public String txtStatus = "#C2__p1_HEAD_4495938AC379D776108484_R";
	public String txtInsuredItem = "#C2__p4_QUE_4495938AC379D776108307_R";
	public String txtPolicyTerm = "#C2__QUE_81FF9B6129B8230B505675_R";
	public String txtExpiryDate = "#C2__QUE_48488D15143D8441358045_R";
	public String txtRenewalDate = "#C2__QUE_48488D15143D8441358173_R";
	public String lblPolicyNumber = "#C2__p1_HEAD_4495938AC379D776108108_R";
	public String lblLineOfBussiness = "#C2__p1_QUE_48488D15143D8441289614_R";
	public String lblInsurer = "#C2__p1_QUE_48488D15143D8441289753_R";
	public String lblStatus = "#C2__p1_HEAD_4495938AC379D776108177_R";
	public String lblInsuredItem = "#C2__p1_HEAD_4495938AC379D776108221_R";
	public String lblPolicyTerm = "#C2__p1_QUE_81FF9B6129B8230B505675_R";
	public String lblExpiryDate = "#C2__p1_QUE_48488D15143D8441358045_R";
	public String lblRenewalDate = "#C2__p1_QUE_48488D15143D8441358173_R";
	public String cssExpandButton = "#tbl-collapse-";
	public String csscloseBtn = "button[id='BUT_A611B75C2D7F1B84109475']";
	public String lstdocuments = "[id*='C2__view_document_R";
	public String btnDocumentView = "div[id*='p4_view_document_R";
	public String lstDocumentsName = "div[id*='p4_QUE_93A73F833F857A9D184223_R";
	public String policyRibban1 = "[id='C2__FMT_7F8F110B7A232E78358483_R";
	public String btnRenewalMyPolicy = "#C2__BUT_20C9A55710F99F9293695_R";
	public String RenewalPolicyPremuim = "#C2__p1_HEAD_20C9A55710F99F9293693_R";
	public String noOfDocuments ="[id*='C2__view_document_R";
	public String docRow = "[id='C2__p1_TBL_B3A80BADFCD28CFD90846_R";
	
	@FindBy(css = "h2[class*='page-title']")
	WebElement txtCustomerName;

	@FindBy(css = "li[id*='ITM_4B30B4C0CA2951D419310'] > a")
	WebElement lnkWelcome;

	@FindBy(css = "li[id='li_C1__ITM_4B30B4C0CA2951D419316'] > a")
	WebElement lnkChangePassword;

	@FindBy(css = "#C1__ID_SignOut")
	WebElement lnkSignout;

	@FindBy(css = "button[id*=Continue]")
	WebElement btnContinue;

	@FindBy(css = "button[id*=ID_Manage_Contact]")
	WebElement btnManageContactDetails;

	@FindBy(css = "div#C2__COL_7943B46F88E2D96D117366")
	WebElement labelAddress;

	@FindBy(css = "span#C2__QUE_7943B46F88E2D96D117636")
	WebElement labelEmail;

	@FindBy(css = "span#C2__QUE_7943B46F88E2D96D117627")
	WebElement labelHome;

	@FindBy(css = "span#C2__QUE_7943B46F88E2D96D117630")
	WebElement labelMobile;

	@FindBy(css = "div#C2__row_QUE_7943B46F88E2D96D117633")
	WebElement labelWork;

	@FindBy(css = "#C2__Documents_R1 #C2__FMT_7F8F110B7A232E78359989_R1")
	WebElement tab_document;

	@FindBy(css = "button[id*=ID_MANAGE_PREFERENCES]")
	WebElement btnManagePreferences;

	@FindBy(xpath = "//h2[starts-with(text(), 'Manage Contact Details')]")
	WebElement hdrManageContactDetails;

	@FindBy(xpath = "//h2[starts-with(text(), 'Correspondence Preference')]")
	WebElement hdrCorrespondencePreference;

	@FindBy(css = "button[id*=BUT_90BD42C1720991FC85937]")
	WebElement btnCancelManageContactDetails;

	@FindBy(css = "button[id*='BUT_90BD42C1720991FC221235']")
	WebElement btnCancelManagePreference;

	@FindBy(css = "#accordian_Add_ons_R1")
	WebElement tabAddons;

	@FindBy(css = "#C2__QUE_B3A80BADFCD28CFD90822_R1")
	WebElement txtPolicyNoValue;

	@FindBy(css = "#C2__QUE_48488D15143D8441289614_R1")
	WebElement txtBusinessValue;

	@FindBy(css = "#C2__row_HEAD_4495938AC379D776108484_R1")
	WebElement txtPolicystatus;

	@FindBy(css = "#C2__QUE_48488D15143D8441358173_R1")
	WebElement txtStartDate;

	@FindBy(css = "#C2__FMT_7F8F110B7A232E78359725_R1")
	WebElement tabDocument;

	@FindBy(css = "#C2__view_document_R1_1")
	WebElement btnView;

	@FindBy(css = "#TBL_D7F3DE109D0EB543290167_R1_1 .bx-viewport")
	List<WebElement> addon;

	@FindBy(css = "#accordian_Add_ons_R1")
	WebElement containerAddOns;

	@FindBy(css = "div[id*='FMT_7F8F110B7A232E78358483'] .fa.fa-caret-down.pull-right")
	WebElement btnDowntoExpand;

	@FindBy(css = "a[class*='viewaccordian'] > i[class='fa fa-caret-down pull-right']")
	WebElement btnPolicyExpand;

	@FindBy(css = "a[class*='viewaccordian'] > i[class='fa fa-caret-up pull-right']")
	WebElement btnPolicyCollapse;

	@FindBy(css = "#pageNumber")
	WebElement docOpen;

	@FindBy(css = "paper-icon-button[id*='download]']")
	WebElement iconDownload;

	@FindBy(css = "paper-icon-button[id*='print]']")
	WebElement iconPrint;

	@FindBy(css = "button[id*=C1__BUT_CCE7E08A37BF764D13135]")
	WebElement btnEnterAddressManually;

	@FindBy(css = "input[id*=C1__QUE_CCE7E08A37BF764D13129]")
	WebElement txtAddressPostcodeMCD;

	@FindBy(css = "button[id*=C1__BUT_CCE7E08A37BF764D13133]")
	WebElement btnFindAddressMCD;

	@FindBy(css = "input[id*=C1__QUE_CCE7E08A37BF764D13137]")
	WebElement txtAddressLine1MCD;

	@FindBy(css = "input[id*=C1__QUE_CCE7E08A37BF764D13139]")
	WebElement txtAddressLine2MCD;

	@FindBy(css = "input[id*=C1__QUE_CCE7E08A37BF764D13141]")
	WebElement txtAddressLine3MCD;

	@FindBy(css = "input[id*=C1__QUE_CCE7E08A37BF764D13143]")
	WebElement txtAddressLine4MCD;

	@FindBy(css = "button[id*=C1__ID_SAVE_ADDRESS]")
	WebElement btnSaveAddressMCD;

	@FindBy(css = "button[id=C2__SaveContactDetails_btn]")
	WebElement btnSavePhoneEmailMCD;

	@FindBy(css = "#EmailChange .modal-body")
	WebElement mdlEmailChangeContent;

	@FindBy(css = "#EmailChange .modal-footer button")
	WebElement btnMdlEmailChangeOk;

	@FindBy(css = "button[id*='ID_Manage_Preference'][title='Save']")
	WebElement btnSaveManagePreference;

	@FindBy(xpath = "//a[text()='Phone/Email']")
	WebElement tabPhoneEmail;

	@FindBy(css = "input[name*='HOME']")
	WebElement fldHomePhone;

	@FindBy(css = "input[name*='MOBILE']")
	WebElement fldMobilePhone;

	@FindBy(css = "input[name*='WORK']")
	WebElement fldWorkPhone;

	@FindBy(css = "input[id*=QUE_90BD42C1720991FC153771]")
	WebElement txtEmail;

	@FindBy(css = "span[id*='QUE_90BD42C1720991FC153771_ERRORMESSAGE']")
	WebElement txtErrorMsg;

	@FindBy(css = "#C2__QUE_2B25C62F756308AA32731")
	WebElement fldFormErrorMsg;

	@FindBy(css = "span[id*=C1__QUE_CCE7E08A37BF764D13129_ERRORMESSAGE]")
	WebElement fldValidationErrorMsgPostcode;

	@FindBy(css = "button[title*='Change My Policy']")
	WebElement btnChangeMyPolicy1;

	@FindBy(css = "div[class*='modal-body'] p[class*='alert-custom']")
	WebElement txtRenewalAlertMessage;

	@FindBy(css = "div[id*='create-mta'] > div")
	WebElement modalRenewalAlert;

	@FindBy(css = "div[id*='create-mta'] > div >div> div>a")
	WebElement btnOkInRenewalAlert;

	@FindBy(css = "div[class*='modal-body modal_botzer0'] >p")
	WebElement txtContactMessage;

	@FindBy(css = "div[id*='block-mta'] > div")
	WebElement modalContactMessageAlert;

	@FindBy(css = "div[id*='block-mta'] > div > div > div[class*='modal-footer warning_modalfooter'] >a")
	WebElement btnOkInContactMessageAlert;

	@FindBy(css = "[id*='FMT_7F8F110B7A232E78358446_R']")
	List<WebElement> lstPolicy;
	
	@FindBy(css = "[id*='FMT_7F8F110B7A232E78358446_R'] i+div[class*='noPadLft']")
	List<WebElement> lstPolicyName;

	@FindBy(css = "#C2__FMT_7943B46F88E2D96D117042")
	WebElement txtCustAddr;

	@FindBy(css = "#C2__QUE_E7B607645443AA0817006")
	WebElement txtDateOfBirth;

	@FindBy(css = ".correspondencePreference [id*='QUE_90BD42C1720991FC221324_1']")
	WebElement radCorrespondancePreferenceEmail;

	@FindBy(css = "input[id*='QUE_90BD42C1720991FC221473_0']")
	WebElement radMarketingPreferenceYes;

	@FindBy(css = "input[id*='QUE_90BD42C1720991FC221473_1']")
	WebElement radMarketingPreferenceNo;

	@FindBy(css = "div[id*='p4_QUE_90BD42C1720991FC221324'] label[for*='QUE_90BD42C1720991FC221324_0']")
	WebElement radSendtoPrintHouse;

	@FindBy(css = "input[id*='QUE_48488D15143D8441154733_0']")
	WebElement chkMarketingPreferenceEmail;

	@FindBy(css = "input[id*='QUE_48488D15143D8441154736_0']")
	WebElement chkMarketingPreferencePost;

	@FindBy(css = "input[id*='QUE_48488D15143D8441154739_0']")
	WebElement chkMarketingPreferencePhone;

	@FindBy(css = "input[id*='QUE_48488D15143D8441154742_0']")
	WebElement chkMarketingPreferenceSMS;

	@FindBy(css = "div[id*='p1_HEAD_A611B75C2D7F1B84109469']")
	WebElement popContactDetailsAddressUpdateMessage;

	@FindBy(css = "div[id*='TXT_D77BCCB2B7DEA7C7153844']")
	WebElement popContactDetailsEmailUpdateMessage;

	@FindBy(css = "button[id*='BUT_A611B75C2D7F1B84109475']")
	WebElement btnContactDetailsAddressUpdateMessageClose;

	@FindBy(css = "button[id*='BUT_D77BCCB2B7DEA7C7153906']")
	WebElement btnContactDetailsEmailUpdateMessageClose;

	@FindBy(css = "button[id*='BUT_81FF9B6129B8230B154084']")
	WebElement btnMarketingPreferenceMessageClose;

	@FindBy(css = "#tbl-collapse-1")
	WebElement btnExpandOrCollapsed;

	@FindBy(css = "#FS_QUE_D057AA4FA101B58786133 label")
	WebElement lblTelephone;

	@FindBy(css = "button[title='Cancel'][name='C2____27D0AB85A568A358 FormButton 37']")
	WebElement btnXCancel;

	@FindBy(css = "[class*='tblhdr-column tblhdr1']")
	WebElement hdrDocumentName;

	@FindBy(css = "[class*='tblhdr-column tblhdr2']")
	WebElement hdrDateCreated;

	@FindBy(css = "[class*='tblhdr-column tblhdr3']")
	WebElement hdrViewBtn;

	@FindBy(css = "[class*='tblhdr-column tblhdr4']")
	WebElement hdrUpdateNewDocument;

	@FindBy(css = "[class*='tblhdr-column tblhdr4']")
	WebElement mdlNoChange;

	@FindBy(css = "#p1_HEAD_0B852743F98CD3DB703256")
	WebElement errorPopup;

	@FindBy(css = ".modal-popup-content button[title='Ok']")
	WebElement btnOkErrorPopup;

	@FindBy(css = "select[name*='C1__CONTACTCENTRE[1].CUSTOMERDASHBOARD[1].PERSON[1].CONTACT[1].CONTACTDETAILS[1].ADDRESS[1].ADDRESSLIST']")
	WebElement drpSelectAddressList;

	@FindBy(css = "a[id*='HELP_ID_Manage_Contact']")
	WebElement iconHelpManageContactDetail;

	@FindBy(css = "[id*='helpContent']")
	WebElement popHelpManageContactDetail;
	
	// Mobile browsers
    @FindBy(css = "[id*='p1_QUE_93A73F833F857A9D184223_R2_1']")
    WebElement hdrDocumentName_andrd;

    @FindBy(css = "[id*='p1_QUE_4A846E19AF2A798B27581_R2_1']")
    WebElement hdrDateCreated_andrd;

    @FindBy(css = "[id*='COL_48B712DCCE60949584899_R2_1']")
    WebElement hdrViewBtn_andrd;

    @FindBy(css = "button[class*=navbar-toggle]")
    WebElement btnNavBar;
    
    @FindBy(css="#C2__p1_HEAD_AF166CB2C0355BDC110015")
	WebElement Renewalpopup;

    @FindBy(css="#C2__QUE_2B25C62F756308AA32736")
	WebElement Password;

    @FindBy(css="#C2__Save_contact_email")
	WebElement btnCustomerOk;
    
    @FindBy(css="[id*='C2__FMT_7F8F110B7A232E78358483_R']")
	List<WebElement> policyRibban;
    
    @FindBy(css = "button[id*='C2__BUT_20C9A55710F99F9293695_R2']")
    WebElement btnViewMyRenewal;
    
    @FindBy(css = "[id*='C2__p0_TBL_20C9A55710F99F9293688_R']")
	List<WebElement> lstRenewalPolicy;
    
    /*@FindBy(css = "div[id*='C2__view_document_R")
	List<WebElement> noOfDocuments;*/
    
	/**
	 * 
	 * Constructor class for CustomerDashboardPage Here we initializing the driver
	 * for page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public CustomerDashboardPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && !driver.getTitle().contains("Customer Dashboard")) {
			Log.fail("User is not navigated to CustomerDashboardPage ", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {

		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
		WaitUtils.waitForElement(driver, txtCustomerName);
	}

	/**
	 * To click welcome link
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickWelcomeDropdown(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, -document.body.scrollHeight);");
			if (!WaitUtils.waitForElement(driver, lnkWelcome)) {
                btnNavBar.click(); //For Mobile browser
          }
			lnkWelcome.click();
			WaitUtils.waitForElement(driver, lnkChangePassword);
			Log.message("Clicked on Welcome dropdown", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Application Error : Error while clicking Welcome button : " + e);
		}

	}

	/**
	 * Verify customer name
	 * 
	 * @throws Exception
	 * 
	 */
	public String getCustomerName() throws Exception {

		try {
			WaitUtils.waitForElement(driver, txtCustomerName);
			return GenericUtils.getTextOfWebElement(txtCustomerName, driver);

		} catch (Exception e) {
			throw new Exception("Application Error : Error while clicking verifying title : " + e);
		}
	}

	/**
	 * Click link from welcome drop down
	 * 
	 * @param linkName
	 *            - Change Password/Signout
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickLinkFromWelcomeDropdown(String linkName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, -document.body.scrollHeight);");
			if (linkName.equals("Change Password")) {
				lnkChangePassword.click();
			} else if (linkName.equals("Signout")) {
				lnkSignout.click();
			}
			WaitUtils.waitForPageLoad(driver);
			Log.message("Clicked on " + linkName + " link from welcome dropdown", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking option from dropdown : " + e);
		}

	}

	/**
	 * To click 'Manage Contact Details' button
	 * 
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void clickManageContactDetailsBtn(ExtentTest extentedReport) throws Exception {
		try {
			btnManageContactDetails.click();
			Log.message("Clicked on 'Manage Contact Details' button", extentedReport);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForElement(driver, hdrManageContactDetails);
		} catch (Exception e) {
			throw new Exception("Error while clicking ManageContactDetails button : " + e);
		}
	}

	/**
	 * To verify 'Manage Contact Details' modal is Opened by clicking on 'Manage
	 * Contact Details' button.
	 * 
	 * @param extentedReport
	 * @return true if 'Manage Contact Details' modal is opened; false otherwise
	 * 
	 */
	public boolean verifyManageContactDetailsModalOpened(ExtentTest extentedReport) throws Exception {
		try {
			clickManageContactDetailsBtn(extentedReport);
			return WaitUtils.waitForElement(driver, hdrManageContactDetails);
		} catch (Exception e) {
			throw new Exception("Error while verifying ManageContactDetails Modal is Opened : " + e);
		}
	}

	/**
	 * To verify Policy Status
	 * 
	 * @param label
	 */
	public String getPolicyDetails(String label) throws Exception {
		String labelValue = null;
		try {
			if (label.equals("Policy Status")) {
				labelValue = GenericUtils.getTextOfWebElement(txtPolicystatus, driver);
			} else if (label.equals("Policy No")) {
				labelValue = GenericUtils.getTextOfWebElement(txtPolicyNoValue, driver);
			} else if (label.equals("Business")) {
				labelValue = GenericUtils.getTextOfWebElement(txtBusinessValue, driver);
			} else if (label.equals("Start Date")) {
				labelValue = GenericUtils.getTextOfWebElement(txtStartDate, driver);
			}
		} catch (Exception e) {
			throw new Exception("Error while verifying Policy Status : " + e);
		}
		return labelValue;
	}

	/**
	 * To verify 'Manage Contact Details' modal is closed by clicking on 'Cancel'
	 * button.
	 * 
	 * @param extentedReport
	 * @return true if 'Manage Contact Details' modal is closed; false otherwise
	 * 
	 */
	public boolean verifyManageContactDetailsModalclosed(ExtentTest extentedReport) throws Exception {
		try {
			btnCancelManageContactDetails.click();
			Log.message("Clicked on 'Cancel' button", extentedReport);
			WaitUtils.waitForSpinner(driver);
			return !hdrManageContactDetails.isDisplayed();
		} catch (Exception e) {
			throw new Exception("Error while verifying ManageContactDetails Modal is Closed : " + e);
		}
	}

	/**
	 * To verify 'Correspondence Preference' modal is opened by clicking on 'Manage
	 * Preferences' button.
	 * 
	 * @param extentedReport
	 * @return true if 'Correspondence Preference' modal is opened; false otherwise
	 */
	public boolean verifyCorrespondencePreferenceModalOpened(ExtentTest extentedReport) throws Exception {
		try {
			boolean status = false;
			WaitUtils.waitForElement(driver, btnManagePreferences);
			btnManagePreferences.click();
			Log.message("Clicked on 'Manage Preferences' button", extentedReport);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForElement(driver, hdrCorrespondencePreference);
			if (hdrCorrespondencePreference.getText().contains("Correspondence Preference")) {
				status = true;
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while verifying Correspondence Preference Modal is Opened : " + e);
		}
	}

	/**
	 * To verify 'Correspondence Preference' modal is opened
	 * 
	 * @param extentedReport
	 * @return true if 'Correspondence Preference' modal is opened; false otherwise
	 */

	public boolean verifyCorrespondencePreference(ExtentTest extentedReport) throws Exception {
		boolean status = false;
		if (hdrCorrespondencePreference.getText().contains("Correspondence Preference")) {
			status = true;
		}
		return status;
	}

	/**
	 * To verify 'Correspondence Preference' modal is closed by clicking on 'Cancel'
	 * button.
	 * 
	 * @param extentedReport
	 * @return true if 'Correspondence Preference' modal is closed; false otherwise
	 * 
	 */
	public boolean verifyCorrespondencePreferenceModalClosed(ExtentTest extentedReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnCancelManagePreference);
			btnCancelManagePreference.click();
			Log.message("Clicked on 'Cancel' button", extentedReport);
			WaitUtils.waitForSpinner(driver);
			return !WaitUtils.waitForElement(driver, hdrCorrespondencePreference);
		} catch (Exception e) {
			throw new Exception("Error while verifying Correspondence Preference Modal is Closed : " + e);
		}
	}

	/**
	 * Click an Expand Field to view the policy details.
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickExpand(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			String isexpanded = btnExpandOrCollapsed.getAttribute("aria-expanded");
			if (isexpanded.contains("false")) {
				btnDowntoExpand.click();
				Log.message("Field is expanded successfully", extentedReport);
			} else {
				Log.message("The policy is in expanded state defaultly", extentedReport);
			}

		} catch (Exception e) {
			throw new Exception("Error while selecting expanded symbol : " + e);
		}
	}

	/**
	 * Verify all the policy in collapsed state.
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyCollapses(ExtentTest extentedReport, boolean screenshot) {
		boolean status = true;
		List<WebElement> collapseList = driver.findElements(By.cssSelector(collapse));
		for (int i = 0; i < collapseList.size(); i++) {
			WebElement lstEle = collapseList.get(i);
			String isexpanded = lstEle.getAttribute("aria-expanded");
			if (isexpanded.equalsIgnoreCase("true")) {
				status = false;
				break;
			}
		}
		return status;
	}

	/**
	 * Click an view button in document tab to view the document.
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickViewButton(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			btnView.click();
			Log.message("View button clicked", extentedReport);
			WaitUtils.waitForSpinner(driver);
			String handle = driver.getWindowHandle();
			driver.switchTo().window(handle);
			Log.message("Document viewed successfully", extentedReport);
		} catch (Exception e) {
			throw new Exception("Failed to open document : " + e);
		}
	}

	/**
	 * To click first view button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean clickFirstViewButtonAndVerifyContent(ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean status = false;
			Set<String> allwindows = driver.getWindowHandles();
			String parentwindow = driver.getWindowHandle();
			WaitUtils.waitForElement(driver, btnView);
			btnView.click();
			Thread.sleep(1000);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on first view button", driver, extentedReport, screenshot);
			for (String window : driver.getWindowHandles()) {
				if (!allwindows.contains(window)) {
					driver.switchTo().window(window);
					break;
				}
			}
			String childWindow = driver.getTitle();

			if (!childWindow.contains("Apache")) {
				Log.message("Document is opened", driver, extentedReport, screenshot);
				status = true;
			} else {
				Log.message("Document is not opened", driver, extentedReport, screenshot);
			}

			// driver.close();
			driver.switchTo().window(parentwindow);
			return status;
		} catch (Exception e) {
			throw new Exception("Error while clicking view button : " + e);
		}
	}

	/**
	 * To click Policy DownArrow
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickPolicyDownArrow(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnPolicyExpand);
			btnPolicyExpand.click();
			WaitUtils.waitForElement(driver, btnPolicyCollapse);
			Log.message("Clicked on policy down arrow to expand the policy", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking policy down arrow button : " + e);
		}

	}

	/**
	 * To click Policy UpArrow
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickPolicyUpArrow(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnPolicyCollapse);
			btnPolicyCollapse.click();
			WaitUtils.waitForElement(driver, btnPolicyExpand);
			Log.message("Clicked on policy up arrow to collapse the policy", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking policy up arrow button : " + e);
		}

	}

	/**
	 * To verify viewed doc
	 * 
	 * @throws Exception
	 * 
	 */
	public boolean verifyViewedDocument() throws Exception {
		try {
			boolean status = false;
			if (WaitUtils.waitForElement(driver, docOpen)) {
				status = true;
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while clicking policy up arrow button : " + e);
		}

	}

	/**
	 * Tells whether all address input fields of 'Manage Contact Details' are reset.
	 * 
	 * @return true if all address input fields are reset; false otherwise
	 */
	public boolean verifyAllAddressFieldsReset() throws Exception {
		try {
			String postcode = txtAddressPostcodeMCD.getAttribute("value");
			String addLine1 = txtAddressLine1MCD.getAttribute("value");
			String addLine2 = txtAddressLine2MCD.getAttribute("value");
			String addLine3 = txtAddressLine3MCD.getAttribute("value");
			String addLine4 = txtAddressLine4MCD.getAttribute("value");
			Log.message(
					"Postcode : [" + postcode + "]; Address Line 1 : [" + addLine1 + "]; Address Line 2 : [" + addLine2
							+ "]; Address Line 3 : [" + addLine3 + "]; Address Line 4 : [" + addLine4 + "]",
					extentedReport);
			return postcode.equals("") && addLine1.equals("") && addLine2.equals("") && addLine3.equals("")
					&& addLine4.equals("");
		} catch (Exception e) {
			throw new Exception("Error while verifying All Address Fields got Reset : " + e);
		}
	}

	/**
	 * To click save button in manage contact detail popup
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickSaveBtnInManageContactDetailPopup(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnSaveAddressMCD);
			btnSaveAddressMCD.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Save' button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking print icon : " + e);
		}
	}

	/**
	 * Updates the mailing address in 'Manage Contact Details' and verifies the
	 * updated data.
	 * 
	 * @param extentedReport
	 * @return true if mailing address is updated successfully; false otherwise
	 */
	public boolean verifyMailingAddressUpdated(ExtentTest extentedReport) throws Exception {
		try {
			boolean isOkUpdateAddress = false;
			WaitUtils.waitForElement(driver, btnEnterAddressManually);
			btnEnterAddressManually.click();
			Log.message("Clicked on 'Enter Address Manually' button", extentedReport);
			WaitUtils.waitForSpinner(driver);
			if (verifyAllAddressFieldsReset()) {
				String[] postcodes = { "LA11LA", "GU111PZ", "GU12JW", "AB101AH", "NR11XR" };

				String postcodeMCD = postcodes[new Random().nextInt(postcodes.length)];
				enterAddressManually("Postcode", postcodeMCD, extentedReport, false);

				String addLine1MCD = GenericUtils.getRandomCharacters("ALPHANUMERIC", 15);
				enterAddressManually("Address1", addLine1MCD, extentedReport, false);
				WaitUtils.waitForSpinner(driver);

				String addLine2MCD = GenericUtils.getRandomCharacters("ALPHANUMERIC", 10);
				enterAddressManually("Address2", addLine2MCD, extentedReport, false);
				WaitUtils.waitForSpinner(driver);

				String addLine3MCD = GenericUtils.getRandomCharacters("ALPHANUMERIC", 7);
				enterAddressManually("Address3", addLine3MCD, extentedReport, false);
				WaitUtils.waitForSpinner(driver);

				String addLine4MCD = GenericUtils.getRandomCharacters("ALPHANUMERIC", 5);
				enterAddressManually("Address4", addLine4MCD, extentedReport, false);
				WaitUtils.waitForSpinner(driver);
				GenericUtils.clickElementJS(driver, btnSaveAddressMCD);
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on 'Save' button", driver, extentedReport, true);

				if (verifyContactDetailsAddressUpdateMessage(extentedReport)) {
					Log.pass("Verified the address update message displayed", extentedReport);
				} else {
					Log.fail("Address Change pop up message not displayed",driver,extentedReport,true);
				}
				if (!WaitUtils.waitForElement(driver, hdrManageContactDetails)) {

					Log.pass("Verified that the 'Manage Contact Details' modal is closed", extentedReport);
					if (verifyManageContactDetailsModalOpened(extentedReport)) {
						Log.pass("Verified that the 'Manage Contact Details' modal is opened", extentedReport);

						String actualPostcodeMCD = txtAddressPostcodeMCD.getAttribute("value");
						Log.message("Actual Postcode : [" + actualPostcodeMCD + "]; Expected Postcode : [" + postcodeMCD
								+ "]", extentedReport);
						String actualAddLine1MCD = txtAddressLine1MCD.getAttribute("value");
						Log.message("Actual Add. Line 1 : [" + actualAddLine1MCD + "]; Expected Add. Line 1 : ["
								+ addLine1MCD + "]", extentedReport);
						String actualAddLine2MCD = txtAddressLine2MCD.getAttribute("value");
						Log.message("Actual Add. Line 2 : [" + actualAddLine2MCD + "]; Expected Add. Line 2 : ["
								+ addLine2MCD + "]", extentedReport);
						String actualAddLine3MCD = txtAddressLine3MCD.getAttribute("value");
						Log.message("Actual Add. Line 3 : [" + actualAddLine3MCD + "]; Expected Add. Line 3 : ["
								+ addLine3MCD + "]", extentedReport);
						String actualAddLine4MCD = txtAddressLine4MCD.getAttribute("value");
						Log.message("Actual Add. Line 4 : [" + actualAddLine4MCD + "]; Expected Add. Line 4 : ["
								+ addLine4MCD + "]", extentedReport);
						isOkUpdateAddress = actualPostcodeMCD.contains(postcodeMCD)
								&& actualAddLine1MCD.contains(addLine1MCD) && actualAddLine2MCD.contains(addLine2MCD)
								&& actualAddLine3MCD.contains(addLine3MCD) && actualAddLine4MCD.contains(addLine4MCD);
					} else {
						Log.fail("The 'Manage Contact Details' modal is not opened", extentedReport);
					}
				} else {
					Log.fail("The 'Manage Contact Details' modal is not closed", extentedReport);
				}
			}

			return isOkUpdateAddress;
		} catch (Exception e) {
			throw new Exception("Application Error : Error while updating Mailing address : " + e);
		}
	}

	/**
	 * Changes the existing email address.
	 * 
	 * @param newEmailAddress
	 * @param extentedReport
	 * @return true if email address change is successful and 'Manage Contact
	 *         Details' modal is closed; false otherwise
	 */
	public boolean verifyEmailAddressChanged(String newEmailAddress, ExtentTest extentedReport) throws Exception {
		try {
			tabPhoneEmail.click();
			Log.message("Clicked on 'Phone/Email' tab", extentedReport);
			WaitUtils.waitForElement(driver, txtEmail);
			txtEmail.clear();
			txtEmail.sendKeys(newEmailAddress);
			Log.message("Entered the email address '" + newEmailAddress + "' on 'Email' field", extentedReport);
			clickSaveInPhoneEmail(extentedReport);
			/*if (mdlEmailChangeContent.getText().contains(MDL_CONTENT_EMAIL_CHANGE)) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;executor.executeScript("arguments[0].click();", btnMdlEmailChangeOk);
				Log.message("The email update message is displayed : " + MDL_CONTENT_EMAIL_CHANGE, extentedReport);
			} else {
				JavascriptExecutor executor = (JavascriptExecutor) driver;executor.executeScript("arguments[0].click();", btnMdlEmailChangeOk);
				Log.fail("The email update message is not displayed / Validation error message : "
						+ MDL_CONTENT_EMAIL_CHANGE, extentedReport);
			}*/
			return !hdrManageContactDetails.isDisplayed();
		} catch (Exception e) {
			throw new Exception("Error while verifying EmailAddress got changed : " + e);
		}
	}
	
	/**
	 * Click OK in change address popup
	 * 
	 * @param extentedReport
	 * @return true if email address change is successful and 'Manage Contact
	 *         Details' modal is closed; false otherwise
	 */
	public boolean clickOkInChangeAdressPopUp(ExtentTest extentedReport) throws Exception {
		try {			
			if (mdlEmailChangeContent.getText().contains(MDL_CONTENT_EMAIL_CHANGE)) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;executor.executeScript("arguments[0].click();", btnMdlEmailChangeOk);
				Log.message("The email update message is displayed : " + MDL_CONTENT_EMAIL_CHANGE, extentedReport);
			} else {
				JavascriptExecutor executor = (JavascriptExecutor) driver;executor.executeScript("arguments[0].click();", btnMdlEmailChangeOk);
				Log.fail("The email update message is not displayed / Validation error message : "
						+ MDL_CONTENT_EMAIL_CHANGE, extentedReport);
			}
			return !hdrManageContactDetails.isDisplayed();
		} catch (Exception e) {
			throw new Exception("Error while clicking Ok in Address change pop up : " + e);
		}
	}

	/**
	 * Enter phone number
	 * 
	 * @param phoneNumber
	 * @param number
	 * @param extentedReport
	 * 
	 */
	public void enterPhoneNumber(String phoneNumber, String number, ExtentTest extentedReport) throws Exception {
		try {
			tabPhoneEmail.click();
			Log.message("Clicked on 'Phone/Email' tab", extentedReport);
			WaitUtils.waitForElement(driver, fldHomePhone);
			if (phoneNumber.equalsIgnoreCase("Home")) {
				fldHomePhone.clear();
				fldHomePhone.sendKeys(number);
			} else if (phoneNumber.equalsIgnoreCase("Mobile")) {
				fldMobilePhone.clear();
				fldMobilePhone.sendKeys(number);
			} else if (phoneNumber.equalsIgnoreCase("Work")) {
				fldWorkPhone.clear();
				fldWorkPhone.sendKeys(number);
			}
			Log.message("Entered " + phoneNumber + " number : " + number, extentedReport);			
		} catch (Exception e) {
			throw new Exception("Error while entering Phone numbers : " + e);
		}
	}

	/**
	 * Enter Save in Correspondence modal
	 * 
	 * @param extentedReport
	 * 
	 */
	public void clickSaveInPhoneEmail(ExtentTest extentedReport) throws Exception {
		try {
			btnSavePhoneEmailMCD.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Save' button", driver, extentedReport, true);
		} catch (Exception e) {
			throw new Exception("Error while clicking Save button : " + e);
		}
	}

	/**
	 * Verifies that the actual error message matches with the expected.
	 * 
	 * @param extentedReport
	 * @return true if the actual error message matches with the expected; false
	 *         otherwise
	 */
	public boolean verifyChangeEmailAddressErrorMsg(ExtentTest extentedReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldFormErrorMsg);
			String expectedErrMsg = ERR_MSG_EMAIL_ADDRESS_UPDATE;
			String actualErrMsg = fldFormErrorMsg.getText().trim();
			Log.message("Expected Msg : [" + expectedErrMsg + "]; Actual Msg : [" + actualErrMsg + "]", extentedReport);
			return actualErrMsg.contains(expectedErrMsg);
		} catch (Exception e) {
			throw new Exception("Error while verifying Change EmailAddress Error message : " + e);
		}
	}

	/**
	 * To Enter Address Manually
	 * 
	 * @param option
	 * @param address
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterAddressManually(String option, String address, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			if (option.equalsIgnoreCase("Postcode")) {
				txtAddressPostcodeMCD.clear();
				txtAddressPostcodeMCD.sendKeys(address);
				Log.message("Entered the value '" + address + "' on 'Postcode'", driver, extentedReport, screenshot);

			} else if (option.equalsIgnoreCase("Address1")) {
				txtAddressLine1MCD.clear();
				WaitUtils.waitForSpinner(driver);
				txtAddressLine1MCD.sendKeys(address);
				Log.message("Entered the value Address Line 1 value : " + address, driver, extentedReport, screenshot);
			} else if (option.equalsIgnoreCase("Address2")) {
				txtAddressLine2MCD.clear();
				WaitUtils.waitForSpinner(driver);
				txtAddressLine2MCD.sendKeys(address);
				Log.message("Entered the value Address Line 2 value : " + address, driver, extentedReport, screenshot);
			} else if (option.equalsIgnoreCase("Address3")) {
				txtAddressLine3MCD.clear();
				WaitUtils.waitForSpinner(driver);
				txtAddressLine3MCD.sendKeys(address);
				Log.message("Entered the value Address Line 3 value : " + address, driver, extentedReport, screenshot);
			} else if (option.equalsIgnoreCase("Address4")) {
				txtAddressLine4MCD.clear();
				WaitUtils.waitForSpinner(driver);
				txtAddressLine4MCD.sendKeys(address);
				Log.message("Entered the value Address Line 4 value : " + address, driver, extentedReport, screenshot);
			}
		} catch (Exception e) {
			throw new Exception("Error while entering address option : " + option + " manually : " + e);
		}
	}

	/**
	 * verify AddressLine and Postcode value
	 * 
	 * @param address2
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyAddressLine(String option, String address) throws Exception {
		try {
			boolean isEntered = false;
			String displayedValue = null;
			if (option.equalsIgnoreCase("Address1")) {
				displayedValue = txtAddressLine4MCD.getAttribute("value");
				if (displayedValue.contains(address)) {
					isEntered = true;
				}
			} else if (option.equalsIgnoreCase("Address2")) {
				displayedValue = txtAddressLine2MCD.getAttribute("value");
				if (displayedValue.contains(address)) {
					isEntered = true;
				}
			} else if (option.equalsIgnoreCase("Address3")) {
				displayedValue = txtAddressLine3MCD.getAttribute("value");
				if (displayedValue.contains(address)) {
					isEntered = true;
				}
			} else if (option.equalsIgnoreCase("Address4")) {
				displayedValue = txtAddressLine4MCD.getAttribute("value");
				if (displayedValue.contains(address)) {
					isEntered = true;
				}
			}
			return isEntered;
		} catch (Exception e) {
			throw new Exception("Error while verifying " + option + " value : " + e);
		}
	}

	/**
	 * verify postcode value
	 * 
	 * @param address2
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyPostcode(String postcode) throws Exception {
		try {
			boolean isEntered = false;
			String displayedValue = txtAddressPostcodeMCD.getAttribute("value");
			if (displayedValue.contains(postcode)) {
				isEntered = true;
			}
			return isEntered;
		} catch (Exception e) {
			throw new Exception("Error while verifyin postcode value : " + e);
		}
	}

	/**
	 * Click Find Address button
	 * 
	 * @param postcode
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void clickFindAddressBtn(ExtentTest extentedReport) throws Exception {
		try {
			btnFindAddressMCD.click();
			Log.message("Clicked on 'Find Address' button'", extentedReport);
			WaitUtils.waitForSpinner(driver);
		} catch (Exception e) {
			throw new Exception("Error while clicking Find Address Button : " + e);
		}
	}

	/**
	 * To select Address
	 * 
	 * @param String
	 *            postCode
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectAddress(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			if (WaitUtils.waitForElement(driver, drpSelectAddressList)) {
				Select drpAddress = new Select(drpSelectAddressList);
				drpAddress.selectByIndex(0);
				WaitUtils.waitForElement(driver, txtAddressLine1MCD);
				Log.message("Address selected from address list", driver, extentedReport, screenshot);
			} else {
				throw new Exception("Drop down to select overnight Address is not found");
			}

		} catch (Exception e) {
			throw new Exception("Unable to select address : " + e);
		}
	}

	/**
	 * To verify Finds the address with the given postcode.
	 * 
	 * @param postcode
	 * @param extentedReport
	 * @return true if the address is found successfully; false otherwise
	 * 
	 */
	public boolean verifyFindAddressValue(String postcode, ExtentTest extentedReport) throws Exception {
		try {
			boolean isOkFindAddress = false;

			btnEnterAddressManually.click();
			Log.message("Clicked on 'Enter Address Manually' button", extentedReport);
			WaitUtils.waitForSpinner(driver);
			if (verifyAllAddressFieldsReset()) {
				enterAddressManually("Postcode", postcode, extentedReport, true);
				clickFindAddressBtn(extentedReport);

				String actualPostcodeMCD = txtAddressPostcodeMCD.getAttribute("value");
				String actualAddLine1MCD = txtAddressLine1MCD.getAttribute("value");
				String actualAddLine2MCD = txtAddressLine2MCD.getAttribute("value");
				String actualAddLine3MCD = txtAddressLine3MCD.getAttribute("value");
				String actualAddLine4MCD = txtAddressLine4MCD.getAttribute("value");
				Log.message("Postcode: [" + actualPostcodeMCD + "]; Add. Line 1 : [" + actualAddLine1MCD
						+ "]; Add. Line 2 : [" + actualAddLine2MCD + "]; Add. Line 3 : [" + actualAddLine3MCD
						+ "]; Add. Line 4 : [" + actualAddLine4MCD + "]", extentedReport);
				isOkFindAddress = txtAddressLine1MCD.isDisplayed() && !txtAddressLine1MCD.getText().equals("");
			}

			return isOkFindAddress;
		} catch (Exception e) {
			throw new Exception("Error while verifying FindAddress Value : " + e);
		}
	}

	/**
	 * Verifies that the actual post code validation error message matches with the
	 * expected.
	 * 
	 * @param extentedReport
	 * @return true if the actual post code validation error message matches with
	 *         the expected; false otherwise
	 */
	public boolean verifyPostcodeValidationErrorMsg(ExtentTest extentedReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldValidationErrorMsgPostcode);
			String expectedErrMsg = ERR_MSG_POSTCODE_VALIDATION;
			String actualErrMsg = fldValidationErrorMsgPostcode.getText().trim();
			Log.message("Expected Msg : [" + expectedErrMsg + "]; Actual Msg : [" + actualErrMsg + "]", extentedReport);
			return actualErrMsg.equals(expectedErrMsg);
		} catch (Exception e) {
			throw new Exception("Error while Postcode Validation Error Ok Message : " + e);
		}
	}

	/**
	 * To click ok button on renewal alert modal
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickOkButtonOnRenewalModal(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnOkInRenewalAlert);
			btnOkInRenewalAlert.click();
			Log.message("Clicked Ok button on Renewal Modal", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking Ok button on Renewal Modal : " + e);
		}
	}

	/**
	 * To click Ok Button On contact Message modal
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickOkButtonOnContactmodal(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnOkInContactMessageAlert);
			btnOkInContactMessageAlert.click();
			Log.message("Clicked Ok button On Contact modal", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking Ok button On Contact modal : " + e);
		}
	}

	/**
	 * To select policy from list
	 * 
	 * @param policyName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickPolicyFromList(String policyName, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			boolean status = false;
			for (int i = 0; i < lstPolicy.size(); i++) {
				if (lstPolicy.get(i).getText().contains(policyName)) {
					Integer policyrow = i + 1;
					WebElement btnExpand = lstPolicy.get(i).findElement(By.cssSelector(cssExpandButton + policyrow));
					if (btnExpand.getAttribute("aria-expanded").contains("false")) {
						lstPolicy.get(i).click();
						status = true;
						break;
					}
				}
			}
			if (status) {
				Log.message("Clicked policy from list : " + policyName + "", driver, extentedReport, screenshot);
			}
		} catch (Exception e) {
			throw new Exception("Error while clicking policy ( " + policyName + " ) : " + e);
		}
	}

	/**
	 * To click change My policy button based on order
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickChangePolicyButton(String policyNumber, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String number = getPolicyRowFromList(policyNumber, extentedReport);
			String elementString = btnChangeMyPolicy + number;
			WaitUtils.waitForSpinner(driver);
			WebElement btn = driver.findElement(By.cssSelector(elementString));
			WaitUtils.waitForElement(driver, btn);
			GenericUtils.scrollIntoView(driver, btn);
			JavascriptExecutor executor = (JavascriptExecutor) driver;executor.executeScript("arguments[0].click();", btn);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Change My policy button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking change My policy button : " + e);
		}
	}

	/**
	 * Selects the marketing preference.
	 * 
	 * @param preferenceType
	 *            - Email | Post | Phone | SMS
	 * @param extentedReport
	 */
	public void selectMarketingPreference(String preferenceType, ExtentTest extentedReport) throws Exception {
		try {
			if (preferenceType.equals("Email")) {
				if (!chkMarketingPreferenceEmail.isSelected()) {
					GenericUtils.clickElementJS(driver, chkMarketingPreferenceEmail);
					Log.message("Selected the marketing preference 'Email'", extentedReport);
				} else {
					Log.message("The marketing preference 'Email' is already selected", extentedReport);
				}
			} else if (preferenceType.equals("Post")) {
				if (!chkMarketingPreferencePost.isSelected()) {
					GenericUtils.clickElementJS(driver, chkMarketingPreferencePost);
					Log.message("Selected the marketing preference 'Post'", extentedReport);
				} else {
					Log.message("The marketing preference 'Post' is already selected", extentedReport);
				}
			} else if (preferenceType.equals("Phone")) {
				if (!chkMarketingPreferencePhone.isSelected()) {
					GenericUtils.clickElementJS(driver, chkMarketingPreferencePhone);
					Log.message("Selected the marketing preference 'Phone'", extentedReport);
				} else {
					Log.message("The marketing preference 'Phone' is already selected", extentedReport);
				}
			} else if (preferenceType.equals("SMS")) {
				if (!chkMarketingPreferenceSMS.isSelected()) {
					GenericUtils.clickElementJS(driver, chkMarketingPreferenceSMS);
					Log.message("Selected the marketing preference 'SMS'", extentedReport);
				} else {
					Log.message("The marketing preference 'SMS' is already selected", extentedReport);
				}
			}
		} catch (Exception e) {
			throw new Exception("Error while selecting Marketing Preference : " + e);
		}
	}

	/**
	 * Selects all the marketing preferences.
	 * 
	 * @param extentedReport
	 */
	public void selectAllMarketingPreference(ExtentTest extentedReport) throws Exception {
		try {
			selectMarketingPreference("Email", extentedReport);
			selectMarketingPreference("Post", extentedReport);
			selectMarketingPreference("Phone", extentedReport);
			selectMarketingPreference("SMS", extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while selecting All Marketing Preference : " + e);
		}
	}

	/**
	 * To click close button in ContactDetails Address Update Message popup
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickCloseBtnInContactDetailsAddressUpdateMsg(ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnContactDetailsAddressUpdateMessageClose);
			btnContactDetailsAddressUpdateMessageClose.click();
			Log.message("Clicked on 'Close' button of address update message pop-up", driver, extentedReport,
					screenshot);
			WaitUtils.waitForinvisiblityofElement(driver, 30, csscloseBtn,
					"Contact Details Address Update Message did not close after waiting for 30s");
		} catch (Exception e) {
			throw new Exception(
					"Error while clicking Close Button In Manage Contact Details Address Update Message : " + e);
		}
	}

	/**
	 * Verifies the contact details address update message shown in pop-up. Also
	 * closes the pop-up once verification is done.
	 * 
	 * @param extentedReport
	 * @return true if message is verified successfully; false otherwise
	 */
	public boolean verifyContactDetailsAddressUpdateMessage(ExtentTest extentedReport) throws Exception {
		try {
			String actualMessage = popContactDetailsAddressUpdateMessage.getText().trim();
			String expectedMessage = MSG_ADDRESS_UPDATE_CONTACT_DETAILS;
			boolean isOkMessage = actualMessage.equals(expectedMessage);
			Log.message("Actual Message : [" + actualMessage + "]; Expected Message: [" + expectedMessage
					+ "]; isOk : [" + isOkMessage + "]", extentedReport);
			if (isOkMessage) {
				btnContactDetailsAddressUpdateMessageClose.click();
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on 'Close' button of address update message pop-up", extentedReport);
			} else {
				throw new Exception(
						"Application Failure : Error while verifying Contact Details Address Update Message" );
			}
			return isOkMessage;
		} catch (Exception e) {
			throw new Exception(
					"Application Failure : Error while verifying Contact Details Address Update Message : " + e);
		}
	}

	/**
	 * Verifies the contact details email update message shown in pop-up. Also
	 * closes the pop-up once verification is done.
	 * 
	 * @param extentedReport
	 * @return true if message is verified successfully; false otherwise
	 */
	public boolean verifyContactDetailsEmailUpdateMessage(ExtentTest extentedReport) throws Exception {
		try {
			String actualMessage = popContactDetailsEmailUpdateMessage.getText().trim();
			String expectedMessage = MSG_EMAIL_UPDATE_CONTACT_DETAILS;
			boolean isOkMessage = actualMessage.equals(expectedMessage);
			Log.message("Actual Message : [" + actualMessage + "]; Expected Message : [" + expectedMessage
					+ "]; is Ok : [" + isOkMessage + "]", extentedReport);
			WaitUtils.waitForelementToBeClickable(driver, btnContactDetailsEmailUpdateMessageClose,
					"Ok button displaying on pop up");
			btnContactDetailsEmailUpdateMessageClose.click();
			Log.message("Clicked on 'Ok' button of email update message pop-up", extentedReport);
			WaitUtils.waitForSpinner(driver);
			return isOkMessage;
		} catch (Exception e) {
			throw new Exception("Error while verifying Contact Details Email Update Message : " + e);
		}
	}

	/**
	 * Updates the marketing preference by selecting the alternative option and
	 * verifies the update.
	 * 
	 * @param extentedReport
	 * @return true if update is successful; false otherwise
	 */
	public boolean verifyMarketingPreferenceUpdated(ExtentTest extentedReport) throws Exception {
		try {
			boolean isUpdateSuccessful = false;
			boolean isOkOption = false;

			GenericUtils.clickElementJS(driver, radCorrespondancePreferenceEmail);
			WaitUtils.waitForElement(driver, radMarketingPreferenceYes);
			if (radMarketingPreferenceYes.isSelected()) {
				GenericUtils.clickElementJS(driver, radSendtoPrintHouse);
				GenericUtils.clickElementJS(driver, radMarketingPreferenceNo);
				Log.message("Selected the marketing preference option 'No'", extentedReport);

				WaitUtils.waitForSpinner(driver);
				if (verifyCorrespondencePreference(extentedReport)) {
					isOkOption = radMarketingPreferenceNo.isSelected();
					isUpdateSuccessful = isOkOption;
					Log.message("isUpdateSuccessful: [" + isUpdateSuccessful + "]", extentedReport);
				}

				btnSaveManagePreference.click();
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on 'Save' button in 'Manage Preference' modal", extentedReport);
			} else if (radMarketingPreferenceNo.isSelected()) {
				GenericUtils.clickElementJS(driver, radMarketingPreferenceYes);
				Log.message("Selected the marketing preference option 'Yes'", extentedReport);
				WaitUtils.waitForSpinner(driver);
				selectAllMarketingPreference(extentedReport);

				if (verifyCorrespondencePreference(extentedReport)) {
					WaitUtils.waitForElement(driver, radMarketingPreferenceYes);
					isOkOption = radMarketingPreferenceYes.isSelected();
					boolean isOkEmailPreference = chkMarketingPreferenceEmail.isSelected();
					boolean isOkPostPreference = chkMarketingPreferencePost.isSelected();
					boolean isOkPhonePreference = chkMarketingPreferencePhone.isSelected();
					boolean isOkSMSPreference = chkMarketingPreferenceSMS.isSelected();
					boolean isOkSelectedPreferences = isOkEmailPreference && isOkPostPreference && isOkPhonePreference
							&& isOkSMSPreference;
					isUpdateSuccessful = isOkOption && isOkSelectedPreferences;

					if (isOkSelectedPreferences && isUpdateSuccessful && isOkOption)
						Log.message("Marketing preference details are updated successfully on Manage preference window",
								driver, extentedReport, true);
					else
						Log.message(
								"Marketing preference details are not udpated successfully on Manage preference window",
								driver, extentedReport, true);
				}

				btnSaveManagePreference.click();
				Log.message("Clicked on 'Save' button in 'Manage Preference' modal", extentedReport);
				WaitUtils.waitForSpinner(driver);
			}
			return isUpdateSuccessful;
		} catch (Exception e) {
			throw new Exception("Error while verifying Marketing Preference is Updated : " + e);
		}
	}

	/**
	 * To get the length of View Port - Multiple Add ons
	 * 
	 * @return int size
	 */
	public int getMultipleAddOnsLength() {
		GenericUtils.scrollIntoView(driver, containerAddOns);
		return addon.size();
	}

	/**
	 * verify correspondence preference telephone label
	 * 
	 * @param extentedReport
	 * @return true if button is displayed; false otherwise
	 * 
	 */
	public boolean verifyTelphoneLabel(ExtentTest extentedReport) throws Exception {
		try {
			boolean status = true;
			if (WaitUtils.waitForElement(driver, lblTelephone))
				if ((lblTelephone.getText()).equals("Telephone"))
					return true;
				else {
					status = true;
					Log.message("Telephone feild is not selected in Pure Back office", extentedReport);
				}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while verifying telephone label in correspondence preference : " + e);
		}

	}

	/**
	 * verify cancel icon on Manage preference pop up
	 * 
	 * @param extentedReport
	 * @return true if button is displayed; false otherwise
	 * 
	 */
	public boolean verifyCancelIcon(ExtentTest extentedReport) throws Exception {
		try {
			boolean status = true;
			if (WaitUtils.waitForElement(driver, btnXCancel)) {
				Log.message("Cancel icon displayed on correspondance popup", extentedReport);
				return true;
			} else {
				status = false;
				Log.message("Cancel icon displayed on correspondance popup", extentedReport);
				return status;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifying cancel pop up in correspondence preference pop up : " + e);
		}

	}

	/**
	 * Click cancel icon on Manage preference pop up
	 * 
	 * @param extentedReport
	 * 
	 */
	public void clickCancelIconInManagePreference(ExtentTest extentedReport) throws Exception {
		try {
			btnXCancel.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Cancel icon clicked on correspondance popup", extentedReport);

		} catch (Exception e) {
			throw new Exception("Error while clicking cancel link in Manage preference pop up : " + e);
		}

	}

	/**
	 * verify contact labels and values on customer dashboard page
	 * 
	 * @param extentedReport
	 * @return true if values are displayed; false otherwise
	 * 
	 */
	public boolean verifyContactDetails(HashMap<String, String> testData, ExtentTest extentedReport) throws Exception {
		boolean flag = true;
		
		try {
			/*if (GenericUtils.getTextOfWebElement(labelAddress, driver).equals(testData.get("Address")))
				Log.message("Verified Address details of the customer", extentedReport);
			else
				flag = false;*/
			
			if (GenericUtils.getTextOfWebElement(labelEmail, driver).equals(testData.get("EmailAddress")))
				Log.message("Verified Email Address detail of the customer", extentedReport);
			else
				flag = false;
			
			if (GenericUtils.getTextOfWebElement(labelHome, driver).equals(testData.get("Home_Phone")))
				Log.message("Verified Home Phone detail of the customer", extentedReport);
			else
				flag = false;
			
			if (GenericUtils.getTextOfWebElement(labelMobile, driver).equals(testData.get("Mobile_Phone")))
				Log.message("Verified Mobile Phone detail of the customer", extentedReport);
			else
				flag = false;
				
			
		} catch (Exception e) {
			throw new Exception("Error while verifying contact details : " + e);
		}
		return flag;		
	}

	/**
	 * verify policy ribbon labels and values on customer dashboard page
	 * 
	 * @param extentedReport
	 * @return true if values are displayed; false otherwise
	 * 
	 */
	public boolean verifyPolicyHeaderDetails(String policyName, ExtentTest extentedReport) throws Exception {
		try {
			HashMap<String, String> hmpolDetails = new HashMap<String, String>();
			boolean status = true;
			String policyBanner;
			List<WebElement> val_Cntctdetails_Premium = new ArrayList<WebElement>();
			List<WebElement> val_Cntctdetails = new ArrayList<WebElement>();

			for (int i = 0; i < lstPolicyName.size(); i++) {
				if (lstPolicyName.get(i).getText().contains(policyName)) {
					policyBanner = "div#C2__FMT_7F8F110B7A232E78358483_R"
							.concat(Integer.toString(i + 1) + " div[class*='col-xs-11']");
					val_Cntctdetails = driver.findElements(By.cssSelector(policyBanner));
					policyBanner = "div#C2__FMT_7F8F110B7A232E78358483_R"
							.concat(Integer.toString(i + 1) + " div[class*='noPadRht']");
					val_Cntctdetails_Premium = driver.findElements(By.cssSelector(policyBanner));
					Log.message("policy got identified on customer dashboard page", extentedReport);
					status = true;
					break;
				}
			}

			if (val_Cntctdetails != null) {

				hmpolDetails.put("Policy reference:",
						GenericUtils.getTextOfWebElement(val_Cntctdetails.get(0), driver));
				hmpolDetails.put("Lines of business:",
						GenericUtils.getTextOfWebElement(val_Cntctdetails.get(1), driver));
				hmpolDetails.put("Status : ", 
						GenericUtils.getTextOfWebElement(val_Cntctdetails.get(2), driver));
				hmpolDetails.put("Effective date : ",
						GenericUtils.getTextOfWebElement(val_Cntctdetails.get(3), driver));
				hmpolDetails.put("Annual Premium : ",
						GenericUtils.getTextOfWebElement(val_Cntctdetails_Premium.get(0), driver));
				Set<String> keys = hmpolDetails.keySet();
				for (String key : keys) {
					Log.message("Value of " + key + " is : " + hmpolDetails.get(key));
				}
				return status;

			} else {
				Log.message("Policy details are not displaying on header section", extentedReport);
				status = false;
				return status;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifying policy details on customer dashboard page : " + e);
		}

	}

	/**
	 * verify only motor policies are displaying not quotes on customer dashboard
	 * page
	 * 
	 * @param extentedReport
	 * @return true if values are displayed; false otherwise
	 * 
	 */
	public boolean verifyAllPolicyTypeStatus(ExtentTest extentedReport) throws Exception {
		try {
			HashMap<String, String> hmpolDetails = new HashMap<String, String>();
			boolean status = true;
			String policyBanner;
			List<WebElement> val_Cntctdetails = new ArrayList<WebElement>();

			for (int i = 0; i < lstPolicyName.size(); i++) {
				policyBanner = "div#C2__FMT_7F8F110B7A232E78358483_R"
						.concat(Integer.toString(i + 1) + " div[class*='noPadLft']");
				val_Cntctdetails = driver.findElements(By.cssSelector(policyBanner));

				if (val_Cntctdetails != null) {

					hmpolDetails.put("Policy reference",
							GenericUtils.getTextOfWebElement(val_Cntctdetails.get(0), driver));
					hmpolDetails.put("Lines of business",
							GenericUtils.getTextOfWebElement(val_Cntctdetails.get(1), driver));
					hmpolDetails.put("Status", GenericUtils.getTextOfWebElement(val_Cntctdetails.get(2), driver));
					hmpolDetails.put("Effective date",
							GenericUtils.getTextOfWebElement(val_Cntctdetails.get(3), driver));
					hmpolDetails.put("Annual Premium",
							GenericUtils.getTextOfWebElement(val_Cntctdetails.get(4), driver));
					Set<String> keys = hmpolDetails.keySet();
					for (String key : keys) {
						Log.message("Value of" + key + " is:" + hmpolDetails.get(key));
					}
					if (hmpolDetails.get("Lines of business").contains("Motor")
							&& hmpolDetails.get("Status").contains("Accepted")) {
						Log.message(hmpolDetails.get("Policy reference:") + "is motor accepted policy");
					} else {
						status = false;
						Log.message(hmpolDetails.get("Policy reference:") + "is not motor accepted policy);");
						return status;
					}

				} else {
					Log.message("Policy details are not displaying on header section", extentedReport);
					status = false;
					return status;
				}
			}

			return status;
		} catch (Exception e) {
			throw new Exception("Error while verifying policy details on customer dashboard page : " + e);
		}
	}

	// *********** Policy Detail Section**************//

	/**
	 * To get Policy Row From List
	 * 
	 * @param policyName
	 * @param extentedReport
	 * @param screenshot
	 * @return string
	 * @throws Exception
	 * 
	 */
	public String getPolicyRowFromList(String policyName, ExtentTest extentedReport) throws Exception {
		try {
			String row = null;
			for (int i = 0; i < lstPolicy.size(); i++) {
				if (lstPolicy.get(i).getText().contains(policyName)) {
					Integer policyRow = i + 1;
					Log.message(policyName + " - Policy is available at row - " + policyRow, extentedReport);
					return policyRow.toString();
				}
			}
			return row;
		} catch (Exception e) {
			throw new Exception("Error while getting ( " + policyName + " ) row number : " + e);
		}
	}

	/**
	 * To get Details from Policy detail Section
	 * 
	 * @param policyNumber
	 * @param extentedReport
	 * @return HashMap
	 * 
	 */
	public HashMap<String, String> getDetailInPolicyDetailSection(String policyNumber, ExtentTest extentedReport)
			throws Exception {
		try {
			HashMap<String, String> policyDetails = new HashMap<String, String>();
			String policyrow = getPolicyRowFromList(policyNumber, extentedReport);

			if (policyrow == null) {
				throw new Exception("No policy is available");
			}

			// Policy number
			WebElement policyNo = driver.findElement(By.cssSelector(txtPolicyNumber + policyrow));
			GenericUtils.scrollIntoView(driver, policyNo);
			String displayedPolicyNumber = GenericUtils.getTextOfWebElement(policyNo, driver).trim();
			policyDetails.put("PolicyNumber", displayedPolicyNumber);

			// Line Of business
			WebElement lineOfBusiness = driver.findElement(By.cssSelector(txtLineOfBussiness + policyrow));
			String displayedLineOfBusiness = GenericUtils.getTextOfWebElement(lineOfBusiness, driver);
			policyDetails.put("LineOfBussiness", displayedLineOfBusiness);

			// Insurer
			WebElement insurer = driver.findElement(By.cssSelector(txtInsurer + policyrow));
			String displayedInsurer = GenericUtils.getTextOfWebElement(insurer, driver);
			policyDetails.put("Insurer", displayedInsurer);

			// Status
			WebElement policyStatus = driver.findElement(By.cssSelector(txtStatus + policyrow));
			String displayedStatus = GenericUtils.getTextOfWebElement(policyStatus, driver).trim();
			policyDetails.put("Status", displayedStatus.trim());

			// Insured Item
			
			WebElement insuredItem = driver.findElement(By.cssSelector(txtInsuredItem +	policyrow)); 
			String displayedInsuredItem = GenericUtils.getTextOfWebElement(insuredItem, driver);
			policyDetails.put("InsuredItem", displayedInsuredItem);
			 
			// Policy term
			WebElement policyTerm = driver.findElement(By.cssSelector(txtPolicyTerm + policyrow));
			String displayedPolicyerm = GenericUtils.getTextOfWebElement(policyTerm, driver);
			policyDetails.put("PolicyTerm", displayedPolicyerm);

			// Expiry Date
			WebElement expiryDate = driver.findElement(By.cssSelector(txtExpiryDate + policyrow));
			String displayedExpiryDate = GenericUtils.getTextOfWebElement(expiryDate, driver);
			policyDetails.put("ExpiryDate", displayedExpiryDate);

			// Renewal Date
			WebElement renewalDate = driver.findElement(By.cssSelector(txtRenewalDate + policyrow));
			String displayedRenewalDate = GenericUtils.getTextOfWebElement(renewalDate, driver);
			policyDetails.put("RenewalDate", displayedRenewalDate);

			Set<String> keys = policyDetails.keySet();
			for (String key : keys) {
				Log.message("Displayed Value of " + key + " is : " + policyDetails.get(key), extentedReport);
			}

			return policyDetails;

		} catch (Exception e) {
			throw new Exception("Error while getting Detail from Policy detail Section : " + e);
		}
	}

	/**
	 * verify Policy Detail In PolicyDetail Section
	 * 
	 * @param policyNumber
	 * @param expectedPolicyDetail
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyDetailInPolicyDetailSection(String policyNumber, HashMap<String, String> expectedPolicyDetail,
			ExtentTest extentedReport) throws Exception {
		try {
			HashMap<String, String> policyDetail = getDetailInPolicyDetailSection(policyNumber, extentedReport);
			return GenericUtils.compareTwoHashMap(policyDetail, expectedPolicyDetail);
		} catch (Exception e) {
			throw new Exception("Error while verifying Policy Detail in policy detail section : " + e);
		}
	}
	
	/**
	 * verify Policy Detail In PolicyDetail Section
	 * 
	 * @param policyNumber
	 * @param expectedPolicyDetail
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyDetailInPolicyribban(String policyName, List<String> policyDetails,
			ExtentTest extentedReport) throws Exception {
		try {
			List<String> policyDetail = getDetailInPolicyRibban(policyName, extentedReport);	
			return GenericUtils.compareTwoList(policyDetails, policyDetail);
		} catch (Exception e) {
			throw new Exception("Error while verifying Policy Detail in policy detail section : " + e);
		}
	}
	
	/**
	 * verify Policy Detail In PolicyDetail Section
	 * 
	 * @param policyNumber
	 * @param expectedPolicyDetail
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyDetailInPolicyribban1(String policyName, List<String> policyDetails,
			ExtentTest extentedReport) throws Exception {
		try {
			List<String> policyDetail = getDetailInPolicyRibban(policyName, extentedReport);	
			return GenericUtils.compareTwoList1(driver,policyDetails, policyDetail,extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while verifying Policy Detail in policy detail section : " + e);
		}
	}
	
	private List<String> getDetailInPolicyRibban(String policyName, ExtentTest extentedReport) {
		List<String> policyRibbanDetails = new ArrayList<String>();	
		try {
			String number = getPolicyRowFromList(policyName, extentedReport);
			String elementString = policyRibban1 + number+"'] div";
			WaitUtils.waitForSpinner(driver);
			List<WebElement> ribban = driver.findElements(By.cssSelector(elementString));
			
			int length = ribban.size();
			for(int i=0;i<length-1;i++)
			{
				policyRibbanDetails.add(ribban.get(i).getText());
				
			}
		} catch (Exception e) {
			Log.message("Error while getting Detail from Policy detail Section : " + e);
		}
		return policyRibbanDetails;
	}

	/**
	 * To get label from Policy detail Section
	 * 
	 * @param policyNumber
	 * @param extentedReport
	 * @return list
	 * 
	 */
	public List<String> getLabelNameFromPolicyDetailSection(String policyNumber, ExtentTest extentedReport)
			throws Exception {
		try {
			List<String> policyLabel = new ArrayList<String>();
			String policyrow = getPolicyRowFromList(policyNumber, extentedReport);

			if (policyrow == null) {
				throw new Exception("No policy is available");
			}

			// Policy number
			WebElement policyNo = driver.findElement(By.cssSelector(lblPolicyNumber + policyrow));
			String PolicyNumberLabel = GenericUtils.getTextOfWebElement(policyNo, driver);
			GenericUtils.scrollIntoView(driver, policyNo);
			policyLabel.add(PolicyNumberLabel);

			// Line Of business
			WebElement lineOfBusiness = driver.findElement(By.cssSelector(lblLineOfBussiness + policyrow));
			String LineOfBusinessLabel = GenericUtils.getTextOfWebElement(lineOfBusiness, driver);
			policyLabel.add(LineOfBusinessLabel);

			// Insurer
			WebElement insurer = driver.findElement(By.cssSelector(lblInsurer + policyrow));
			String InsurerLabel = GenericUtils.getTextOfWebElement(insurer, driver);
			policyLabel.add(InsurerLabel);

			// Status
			WebElement policyStatus = driver.findElement(By.cssSelector(lblStatus + policyrow));
			String statusLabel = GenericUtils.getTextOfWebElement(policyStatus, driver);
			policyLabel.add(statusLabel);

			// Insured Item
			
			WebElement insuredItem = driver.findElement(By.cssSelector(lblInsuredItem +	policyrow)); 
			String InsuredItemLabel = GenericUtils.getTextOfWebElement(insuredItem, driver);
			policyLabel.add(InsuredItemLabel);
			 

			// Policy term
			WebElement policyTerm = driver.findElement(By.cssSelector(lblPolicyTerm + policyrow));
			String PolicyermLabel = GenericUtils.getTextOfWebElement(policyTerm, driver);
			policyLabel.add(PolicyermLabel);

			// Expiry Date
			WebElement expiryDate = driver.findElement(By.cssSelector(lblExpiryDate + policyrow));
			String ExpiryDateLabel = GenericUtils.getTextOfWebElement(expiryDate, driver);
			policyLabel.add(ExpiryDateLabel);

			// Renewal Date
			WebElement renewalDate = driver.findElement(By.cssSelector(lblRenewalDate + policyrow));
			String RenewalDateLabel = GenericUtils.getTextOfWebElement(renewalDate, driver);
			policyLabel.add(RenewalDateLabel);

			for (String string : policyLabel) {
				Log.message("Name of Label : " + string, extentedReport);
			}
			return policyLabel;

		} catch (Exception e) {
			throw new Exception("Error while getting Label Name from Policy detail Section : " + e);
		}
	}

	/**
	 * verify Label Name In PolicyDetail Section
	 * 
	 * @param policyNumber
	 * @param expectedPolicyDetail
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyLabelInPolicyDetailSection(String policyNumber, List<String> expectedLabel,
			ExtentTest extentedReport) throws Exception {
		try {
			List<String> labelNames = getLabelNameFromPolicyDetailSection(policyNumber, extentedReport);
			return GenericUtils.compareTwoList(labelNames, expectedLabel);
		} catch (Exception e) {
			throw new Exception("Error while verifying Label Name in policy detail section : " + e);
		}
	}

	/**
	 * verify error pop up message
	 * 
	 * @param msg
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyErrorMsgInCustDashboard(String msg) throws Exception {
		try {
			return GenericUtils.verifyWebElementTextEqualsIgnoreCase(errorPopup, msg);
		} catch (Exception e) {
			throw new Exception("Error while verifying error message : " + e);
		}
	}
	
	public boolean verifyRenewalErrorMsgInCustDashboard(String msg) throws Exception {
        try {
               return GenericUtils.verifyWebElementTextEqualsIgnoreCase(Renewalpopup, msg);
        } catch (Exception e) {
               throw new Exception("Error while verifying error message : " + e);
        }
 }

	/**
	 * verify error pop up message
	 * 
	 * @param msg
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public void clickOkInErrorPopup(ExtentTest extentedReport) throws Exception {
		try {
			btnOkErrorPopup.click();
			WaitUtils.waitUntilElementDisappear(driver, btnOkErrorPopup);
			Log.message("Clickon Ok in Error pop up", extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while verifying error message : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Manage ContactDetail
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfManageContactDetail(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpManageContactDetail);
			iconHelpManageContactDetail.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpManageContactDetail).click();
			//action.perform();*/
			WaitUtils.waitForElement(driver, popHelpManageContactDetail);
			Log.message("Hovered on the HelpIcon Of ManageContactDetail", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of ManageContactDetail : " + e);
		}
	}

	/**
	 * verify HelpIcon Manage Contact Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconManageContactPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpManageContactDetail);
			if (GenericUtils.verifyWebElementTextContains(popHelpManageContactDetail,
					POPUP_MSG_HELPICON_MANAGE_CONTACT)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of ManageContactDetail : " + e);
		}
	}

	/**
	 * Validate email address
	 * 
	 * @param newEmailAddress
	 * @param extentedReport
	 * @return true if the actual error message matches with the expected; false
	 *         otherwise
	 */
	public boolean validateEmailAddress(String newEmailAddress, ExtentTest extentedReport) throws Exception {
		try {
			tabPhoneEmail.click();
			Log.message("Clicked on 'Phone/Email' tab", extentedReport);
			WaitUtils.waitForElement(driver, txtEmail);
			txtEmail.clear();
			txtEmail.sendKeys(newEmailAddress);
			Log.message("Entered the email address '" + newEmailAddress + "' on 'Email' field", driver, extentedReport,
					true);
			clickSaveInPhoneEmail(extentedReport);
			return WaitUtils.waitForElement(driver, txtErrorMsg);
		} catch (Exception e) {
			throw new Exception("Error while verifying EmailAddress got changed : " + e);
		}
	}

	/**
	 * Verifies invalid email address actual error message matches with the
	 * expected.
	 * 
	 * @param extentedReport
	 * @return true if the actual error message matches with the expected; false
	 *         otherwise
	 */
	public boolean verifyInvalidEmailAddressErrorMsg(ExtentTest extentedReport) throws Exception {
		try {
			WaitUtils.waitForElement(driver, txtErrorMsg);
			String expectedErrMsg = Err_MSG_EMAIL_INVALID;
			String actualErrMsg = txtErrorMsg.getText().trim();
			Log.message("Expected Msg : [" + expectedErrMsg + "]; Actual Msg : [" + actualErrMsg + "]", extentedReport);
			return actualErrMsg.equals(expectedErrMsg);
		} catch (Exception e) {
			throw new Exception("Error while verifying Change EmailAddress Error message : " + e);
		}
	}

	/**
	 * To select MTA document from list
	 * 
	 * @param policyName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean clickDocumentView(String policyName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean status = false;
			List<WebElement> documentlst;
			String documentName;
			for (int i = 0; i < lstPolicy.size(); i++) {
				if (lstPolicy.get(i).getText().contains(policyName)) {
					Integer policyrow = i + 1;
					WebElement btnExpand = lstPolicy.get(i).findElement(By.cssSelector(cssExpandButton + policyrow));
					if (btnExpand.getAttribute("aria-expanded").contains("false")) {
						lstPolicy.get(i).click();
					}
					documentlst = driver.findElements(By.cssSelector(lstdocuments + String.valueOf(i + 1) + "_"));
					documentlst.size();
					documentName = driver.findElement(By.cssSelector(
							lstDocumentsName + String.valueOf(i + 1) + "_" + String.valueOf(documentlst.size())))
							.getText();
					if (documentName.contains("MTA")) {
						driver.findElement(By.cssSelector(
								btnDocumentView + String.valueOf(i + 1) + "_" + String.valueOf(documentlst.size())))
								.click();
						WaitUtils.waitForSpinner(driver);
						Log.message("View button selected on documents section" + policyName, driver, extentedReport,
								true);
						status = true;
						break;
					} else {

						status = false;
						break;
					}

				}
			}

			if (status) {
				Log.message("MTA documents are generated successfully for " + policyName, driver, extentedReport, true);
				return status;
			} else {
				Log.message("MTA documents are not generated successfully" + policyName, driver, extentedReport, true);
				return status;
			}

		} catch (Exception e) {
			throw new Exception("Error while clicking policy ( " + policyName + " ) : " + e);
		}
	}

	public void enterPassword(String password, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, Password);
			Password.clear();
			Password.sendKeys(password);
			Password.click();
			Log.message("Entered the Password : " + password, driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering password : " + e);
		}
	}
	
	public void clickOkButton(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			final long startTime = StopWatch.startTime();
			WaitUtils.waitForElement(driver, btnCustomerOk);
			btnCustomerOk.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked OK button on Save contact email", driver, extentedReport, screenshot);
			Log.event("Clicked OK button on Save contact email", StopWatch.elapsedTime(startTime));
		} catch (Exception e) {
			throw new Exception("Error while clicking Sign in button : " + e);
		}
	}
	
	/**
	 * To select policy from list
	 * 
	 * @param policyName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyPolicyFromList(String policyName, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			boolean status = false;
			for (int i = 0; i < lstPolicy.size(); i++) {
				if (lstPolicy.get(i).getText().contains(policyName)) {
					Integer policyrow = i + 1;
					WebElement btnExpand = lstPolicy.get(i).findElement(By.cssSelector(cssExpandButton + policyrow));
					WaitUtils.waitForElement(driver, btnExpand);
					status = true;
				}
			}
			return status;
			
		} catch (Exception e) {
			throw new Exception("Error while clicking policy ( " + policyName + " ) : " + e);
		}
	}
	
	/**
	 * verify Expire date for transferred Policy Detail Section
	 * 
	 * @param policyNumber
	 * @param expectedPolicyDetail
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyExpireDateInPolicyDetailSection(String policyNumber, HashMap<String, String> expectedPolicyDetail,
			ExtentTest extentedReport) throws Exception {
		try {
			HashMap<String, String> policyDetail = getDetailInPolicyDetailSection(policyNumber, extentedReport);
			String expiryDate= policyDetail.get("ExpiryDate");
			if(policyDetail.containsValue(expiryDate)) {
				return true;
			}
			else{
				return false;
			}	
		} catch (Exception e) {
			throw new Exception("Error while verifying Policy Detail in policy detail section : " + e);
		}
	}

	
	/**
	 * To click View My policy button based on order
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickViewMYPolicyButton(String policyNumber, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String number = getRenewalPolicyRowFromList(policyNumber, extentedReport);
			String elementString = btnRenewalMyPolicy + number;
			WaitUtils.waitForSpinner(driver);
			WebElement btn = driver.findElement(By.cssSelector(elementString));
			WaitUtils.waitForElement(driver, btn);
			GenericUtils.scrollIntoView(driver, btn);
			JavascriptExecutor executor = (JavascriptExecutor) driver;executor.executeScript("arguments[0].click();", btn);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on View My policy button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking View My policy button : " + e);
		}
	}
	
	/**
	 * To get Policy Row From List
	 * 
	 * @param policyName
	 * @param extentedReport
	 * @param screenshot
	 * @return string
	 * @throws Exception
	 * 
	 */
	public String getRenewalPolicyRowFromList(String policyName, ExtentTest extentedReport) throws Exception {
		try {
			String row = null;
			for (int i = 0; i < lstRenewalPolicy.size(); i++) {
				if (lstRenewalPolicy.get(i).getText().contains(policyName)) {
					Integer policyRow = i + 1;
					Log.message(policyName + " - Policy is available at row - " + policyRow, extentedReport);
					return policyRow.toString();
				}
			}
			return row;
		} catch (Exception e) {
			throw new Exception("Error while getting ( " + policyName + " ) row number : " + e);
		}
	}
	
	/**
	 * To select policy from list
	 * 
	 * @param policyName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRenewalPolicyFromList(String policyName, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			boolean status = false;
			for (int i = 0; i < lstRenewalPolicy.size(); i++) {
				if (lstRenewalPolicy.get(i).getText().contains(policyName)) {
						status = true;
						break;
				}
			}
			if (status == true) {
				Log.message("Displayed policy from list : " + policyName + "", driver, extentedReport, screenshot);
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while clicking policy ( " + policyName + " ) : " + e);
		}
	}
	
	/**
	 * To click View My policy button based on order
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyActivePremiumOfRenewalPolicy(String policyNumber, String activePremium, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String number = getRenewalPolicyRowFromList(policyNumber, extentedReport);
			String elementString = RenewalPolicyPremuim + number;
			WebElement premiumAmount = driver.findElement(By.cssSelector(elementString));
			WaitUtils.waitForSpinner(driver);
			return GenericUtils.verifyWebElementTextContains(premiumAmount, activePremium);
		} catch (Exception e) {
			throw new Exception("Error while clicking View My policy button : " + e);
		}
	}
	
	/**
	 * To click View My policy button based on order
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRecommendedPremiumOfRenewalPolicy(String policyNumber, String recommendedPremium, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String number = getRenewalPolicyRowFromList(policyNumber, extentedReport);
			String elementString = RenewalPolicyPremuim + number;
			WebElement premiumAmount = driver.findElement(By.cssSelector(elementString));
			WaitUtils.waitForSpinner(driver);
			return GenericUtils.verifyWebElementTextContains(premiumAmount, recommendedPremium);
		} catch (Exception e) {
			throw new Exception("Error while clicking View My policy button : " + e);
		}
	}
	
	/**
	 * To select view my document button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickDocumentView(String policyNumber, WebDriver driver, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			String documentCount = getDocumentsCount(policyNumber, extentedReport, true);
			String policyrow = getPolicyRowFromList(policyNumber, extentedReport);
			String winHandle = driver.getWindowHandle();
			for (int i = 1; i <= Integer.valueOf(documentCount); i++) {
				
				driver.findElement(By.cssSelector(lstdocuments + policyrow + "_" + String.valueOf(i))).click();
				WaitUtils.waitForSpinner(driver);
				ArrayList<String> documentTab = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(documentTab.get(i));
				Thread.sleep(10000);
				Log.message("View button selected on documents section", driver, extentedReport, true);
				driver.switchTo().window(winHandle);
				Log.message("Switched to new window", driver, extentedReport, screenshot);
			}
		} catch (Exception e) {
			Log.message("No documents are not available for this policy",extentedReport);
			//throw new Exception("Error while clicking view document button: " + e);
		}
	}
	
	/**
	 * To get Added Item row in Within home
	 * 
	 * @param Documents
	 * @param Modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public String getDocumentsCount(String policyNumber, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String DocumentsRow = null;
			WaitUtils.waitForSpinner(driver);
			String policyrow = getPolicyRowFromList(policyNumber, extentedReport);
			String elementString = docRow + policyrow + "_1'] " + noOfDocuments + policyrow;
			List<WebElement> Document = (List<WebElement>) driver.findElements(By.cssSelector(elementString));
			if (Document.size() == 0) {
				Log.message("No Documents is available", driver, extentedReport, false);
				return DocumentsRow;
			}
			
			for (int loopCount = 1; loopCount <= Document.size();loopCount++) {
				if(loopCount == Document.size()){
					Integer row = loopCount;
				return row.toString();
				}
			}
			return DocumentsRow;
		} catch (Exception e) {
			throw new Exception("Error while getting Added Item row in Within Home : " + e);
		}
	}
}
