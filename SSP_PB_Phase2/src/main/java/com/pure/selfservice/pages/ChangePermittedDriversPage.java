package com.pure.selfservice.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.generic.support.DateTimeUtility;
import com.generic.support.Log;
import com.generic.utils.ElementLayer;
import com.generic.utils.GenericUtils;
import com.generic.utils.WaitUtils;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * ChangePermittedDriversPage contains Customer contact details
 * 
 */
public class ChangePermittedDriversPage extends LoadableComponent<ChangePermittedDriversPage> {
	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	public String TXT_ON_CNCL_CNTCT = "Clicking cancel will remove all the edits you have just made to your named drivers details. Are You sure you want to delete this information?";
	public String ADDITIONAL_DRIVER_MSG = "You cannot add any further additional drivers. If you choose the option ‘Insured and Named Drivers’ against the question ‘Who should be allowed to drive on your policy’ then only 5 named drivers are permitted. To allow more than 5 drivers to use your vehicle, choose the option ‘Any Driver’.";
	public String INSURED_AND_CIVILPARTNER_DRIVER_MSG = "You cannot add any further additional drivers. If you choose the option ‘Insured and Civil Partner’ against the question ‘Who should be allowed to drive on your policy’ then only 1 named driver is permitted. To allow more than 1 drivers to use your vehicle, choose the option ‘Insured and Named Drivers’.";
	public String INSURED_AND_SPOUSE_DRIVER_MSG = "You cannot add any further additional drivers. If you choose the option ‘Insured and Spouse’ against the question ‘Who should be allowed to drive on your policy’ then only 1 named driver is permitted. To allow more than 1 drivers to use your vehicle, choose the option ‘Insured and Named Drivers’.";
	public String DELETE_POPUP = "The change you just made will remove the additional drivers you already have from your policy. Are you sure you want to remove all of your additional drivers?";
	public String ERR_MSG_MAIN_OCCUPATION_VALIDATION = "Atleast one main occupation";

	public String btnEditDrivers = "#C2__C1__BUT_68E86E63D657A14780118_R";
	public String btnDeleteDrivers = "#C2__C1__BUT_68E86E63D657A14780120_R";
	public String rowDriverName = "span[id*='C2__C1__QUE_68E86E63D657A14780106_R']";
	public String txtDriverName = "#C2__C1__QUE_68E86E63D657A14780106_R";
	public String occupationEdit = "#C2__C1__BUT_B591B7D46A522F33133923_R";
	public String occupationDelete = "#C2__C1__BUT_B591B7D46A522F33134949_R";
	public String cssAddedNamedDriverTitle = "span[id*=QUE_68E86E63D657A14780106]";
	public String xpathAddingNamedDriverTitle = "//h2[text()='Adding Named Driver ${NUMBER}']";
	public String xpathAddNewOccupationTitle = "//h2[text()='Add a New Occupation']";
	public String xpathAddNewClaimTitle = "//h2[text()='Add a New Claim']";
	public String xpathAddNewConvictionTitle = "//h2[text()='Add a New Conviction']";
	public String xpathAddNewMedicalConditionTitle = "//h2[text()='Add a Medical Condition']";
	public String cssAddedOccupations = "div[id*='QUE_C1474FD33356212D501584_R";
	public String cssAddedClaimTypes = "[id*=C2__C1__QUE_18102645D390E3D065355_R]";
	public String cssAddedConvictionCodes = "[id*=C2__C1__QUE_822697A8D6EB51C7542376_R]";
	public String cssAddedCondition = "[id*=C2__C1__QUE_D6B2B13555266C7537174_R]";

	@FindBy(css = ".text-header")
	WebElement txtHeader;

	@FindBy(css = "input[id*='C23FCA4A6A07F05357849_0']")
	WebElement radAddDriverYes;

	@FindBy(css = "input[id*='C23FCA4A6A07F05357849_1']")
	WebElement radAddDriverNo;

	@FindBy(id = "C2__C1__HEAD_8023A2C8DE670FCA111782")
	WebElement txtTitleChangePermittedDrivers;

	@FindBy(css = "select[name*='ALLOWEDDRIVERSONPOLICY' i]")
	WebElement drpDriverType;

	// *** Permitted driver section ***************//

	@FindBy(css = "#C2__C1__p4_QUE_C23FCA4A6A07F05357849 label:nth-child(1)")
	WebElement btnAdditionalDriversYes;

	@FindBy(css = "#C2__C1__p4_QUE_C23FCA4A6A07F05357849 label:nth-child(2)")
	WebElement btnAdditionalDriversNo;

	@FindBy(id = "C2__C1__BUT_68E86E63D657A14780118_R1")
	WebElement btnEdit;

	@FindBy(id = "C2__C1__BUT_68E86E63D657A14780120_R1")
	WebElement btnDelete;

	@FindBy(css = "#C2__C1__BUT_C23FCA4A6A07F05368510")
	WebElement btnCancelAddingDriver;

	@FindBy(css = "#C2__C1__p4_HEAD_1F145A4C2A19EE9F1624247")
	WebElement txtWarningMsg;

	@FindBy(css = "#C2__C1__FMT_622D0025F03AF04A80949")
	WebElement popupDisplay;

	// **** Personal detail section ******//

	@FindBy(id = "C2__C1__QUE_C23FCA4A6A07F05382858")
	WebElement drpSelectTitle;

	@FindBy(id = "C2__C1__QUE_C23FCA4A6A07F05383744")
	WebElement fldForename;

	@FindBy(id = "C2__C1__QUE_C23FCA4A6A07F05384186")
	WebElement fldSurname;

	@FindBy(id = "C2__C1__QUE_C23FCA4A6A07F05384192")
	WebElement fldDOB;

	@FindBy(css = "label[for='date-picker-C2__C1__QUE_C23FCA4A6A07F05384192']")
	WebElement fldDOBDatePicker;

	@FindBy(css = "button[id='C2__C1__BUT_C1474FD33356212D477646']")
	WebElement btnSinceBirth;

	@FindBy(css = "input[name*='PERSONALDETAILS[1].SINCEBIRTHDOB']")
	WebElement fldDateOfLivingInUK;

	@FindBy(id = "C2__C1__QUE_C1474FD33356212D474862")
	WebElement drpSelectGender;

	@FindBy(id = "C2__C1__QUE_C1474FD33356212D474867")
	WebElement drpSelectMaritalStatus;

	@FindBy(id = "C2__C1__QUE_C1474FD33356212D474877")
	WebElement drpSelectRelationship;

	@FindBy(id = "C2__C1__ID_DRIVER_PERSENOAL_INFO_BTN")
	WebElement btnNextInPersonalDetails;

	// ***Occupation section****//

	@FindBy(css = "button[title ='Add Occupation' i]")
	WebElement btnAddOccupation;

	@FindBy(css = "select[name*='NEWOCCUPATION[1].OCCUPATION' i]")
	WebElement drpOccupation;

	@FindBy(css = "select[name*='NEWOCCUPATION[1].BUSINESS' i]")
	WebElement drpBusiness;

	@FindBy(css = "select[name*='NEWOCCUPATION[1].EMPLOYMENTSTATUS' i]")
	WebElement drpEmpStatus;

	@FindBy(css = "div[id*='C2__C1__p4_QUE_C1474FD33356212D491333'] label:nth-child(1)")
	WebElement radMainOccupationYes;

	@FindBy(css = "div[id*='C2__C1__p4_QUE_C1474FD33356212D491333'] label:nth-child(2)")
	WebElement radMainOccupationNo;

	@FindBy(css = "button[title='Save Occupation' i]")
	WebElement btnSaveOccupation;

	@FindBy(css = "button[title='Cancel' i][id*='BUT_6E7C9B296648545E211511']")
	WebElement btnCancelOccupation;

	@FindBy(id = "C2__C1__ID_DRIVER_OCCUPATION_BTN")
	WebElement btnNextInOccupation;

	@FindBy(css = "div[id*='QUE_97F08D629794BE6430934'] label:nth-child(1)")
	WebElement btnAddOccupationYes;

	@FindBy(css = "div[id*='QUE_97F08D629794BE6430934'] label:nth-child(2)")
	WebElement btnAddOccupationNo;

	@FindBy(id = "C2__C1__QUE_C1474FD33356212D491333_ERRORMESSAGE")
	WebElement msgMainOccupationValidationError;

	@FindBy(id = "C2__C1__QUE_C1474FD33356212D501584_R1")
	WebElement firstOccupationName;

	@FindBy(id = "C2__C1__QUE_C1474FD33356212D501587_R1")
	WebElement firstOccupationType;

	@FindBy(id = "C2__C1__QUE_C1474FD33356212D501584_R2")
	WebElement secondOccupationName;

	@FindBy(id = "C2__C1__QUE_C1474FD33356212D501587_R2")
	WebElement secondOccupationType;

	@FindBy(id = "C2__C1__BUT_B591B7D46A522F33133923_R1")
	WebElement btnEditOccupation;

	@FindBy(id = "C2__C1__BUT_B591B7D46A522F33134949_R1")
	WebElement btnDeleteOccupation;

	@FindBy(css = "span[id*='C2__C1__QUE_C1474FD33356212D501584_R']")
	List<WebElement> occupationName;

	@FindBy(css = "div[id*='p4_QUE_97F08D629794BE6430934' i] label:nth-child(1)")
	WebElement btnYesMoreOccupations;

	@FindBy(css = "div[id*='p4_QUE_97F08D629794BE6430934' i] label:nth-child(2)")
	WebElement btnNoMoreOccupations;

	@FindBy(css = "#C2__C1__p4_BUT_251D7B0F6525714D60563")
	WebElement btnYesOccupationDelete;

	// ***License details section****//

	@FindBy(css = "input[name*='C2__C1__DRIVER[1].LICENCEDETAILS[1].LICENCEDATE']")
	WebElement FldLicenseDate;

	@FindBy(css = "select[name*='LICENCEDETAILS[1].LICENCETYPE']")
	WebElement drpSelectLicenseType;

	@FindBy(css = "select[name*='LICENCEDETAILS[1].ADDITIONALQUALIFICATIONS']")
	WebElement drpAddtionalDrivingQualification;

	@FindBy(id = "C2__C1__QUE_3B49D19050E3960781973")
	WebElement fldLicenseNumber;

	@FindBy(id = "C2__C1__QUE_813976210592DD4F74959_0")
	WebElement btnAddLicenseDetailsYes;

	@FindBy(id = "C2__C1__QUE_813976210592DD4F74959_1")
	WebElement btnAddLicenseDetailsNo;

	@FindBy(id = "C2__C1__QUE_813976210592DD4F80778")
	WebElement drpSelectDVLAReason;

	@FindBy(id = "C2__C1__ID_DRIVER_LICENCE_BTN")
	WebElement btnNextInLicenseDetails;

	@FindBy(css = "input[name*='DATEATTAINED']")
	WebElement fldDateAttained;

	// ***Use of other vehicles section****//

	@FindBy(css = "select[name*='VEHICLES[1].HOWMANYOTHERCARS']")
	WebElement drpSelectHowManyOwnCars;

	@FindBy(css = "select[name*='VEHICLES[1].HOWMANYADDITIONALCARS']")
	WebElement drpHowManyAddtionalCars;

	@FindBy(css = "select[name*='VEHICLES[1].NOCLIAMSYEARS']")
	WebElement drpHowManyYears;

	@FindBy(css = "select[name*='HOWMANYHOUSEHOLDCARS']")
	WebElement drpHowManyCarsInHousehold;

	@FindBy(css = "button[id*='DRIVER_OTHER_VEHICLE_INFO_BTN']")
	WebElement btnNextInUseOfVehicle;

	// ***Use of insured vehicles section****//

	@FindBy(css = "select[name*='VEHICLES[1].FREQUENCYOFUSE' i]")
	WebElement drpSelectFrequencyOfUse;

	@FindBy(id = "C2__C1__QUE_C4D8F1D89DFE94EF71525_0")
	WebElement btnAddVehiclePurposeYes;

	@FindBy(css = "div[id='C2__C1__p4_QUE_C4D8F1D89DFE94EF71525'] label:nth-child(2)")
	WebElement btnAddVehiclePurposeNo;

	@FindBy(css = "button[id*='USE_OF_INSURED_VEH_BTN']")
	WebElement btnNextInInsuredVehicle;

	// ***Claims section****//

	@FindBy(css = "div[id*='p4_QUE_813976210592DD4F98807'] label:nth-child(1)")
	WebElement radAddMoreClaimYes;

	@FindBy(css = "div[id*='p4_QUE_813976210592DD4F98807'] label:nth-child(2)")
	WebElement radAddMoreClaimNo;

	@FindBy(css = "button[id*='DRIVER_CLAIMS_BTN']")
	WebElement btnNextInClaims;

	@FindBy(css = "select[name*='ADDNEWCLAIM[1].CLAIMTYPE' i]")
	WebElement drpClaimType;

	@FindBy(css = "input[name*='ADDNEWCLAIM[1].CLAIMDATE' i]")
	WebElement txtClaimDate;

	@FindBy(css = "input[name*='ADDNEWCLAIM[1].CLAIMTODRIVER' i]")
	WebElement txtClaimCostDriver;

	@FindBy(css = "input[name*='ADDNEWCLAIM[1].CLAIMTOTHIRDPARTY' i]")
	WebElement txtClaimCostThirdParty;

	@FindBy(css = "input[name*='ADDNEWCLAIM[1].INJURYCOSTS' i]")
	WebElement txtInjuryCost;

	@FindBy(css = "div[id*='p4_QUE_813976210592DD4F111481'] label:nth-child(1)")
	WebElement radDriverLostDiscountYes;

	@FindBy(css = "div[id*='p4_QUE_813976210592DD4F111481'] label:nth-child(2)")
	WebElement radDriverLostDiscountNo;

	@FindBy(css = "div[id*='p4_QUE_813976210592DD4F112507'] label:nth-child(1)")
	WebElement radDriverAtFaultYes;

	@FindBy(css = "div[id*='p4_QUE_813976210592DD4F112507'] label:nth-child(2)")
	WebElement radDriverAtFaultNo;

	@FindBy(css = "div[id*='p4_QUE_813976210592DD4F114157'] label:nth-child(1)")
	WebElement radClaimSettledYes;

	@FindBy(css = "div[id*='p4_QUE_813976210592DD4F114157'] label:nth-child(2)")
	WebElement radClaimSettledNo;

	@FindBy(css = "div[id*='p4_QUE_813976210592DD4F114629'] label:nth-child(1)")
	WebElement radClaimMadeOnThisPolicyYes;

	@FindBy(css = "div[id*='p4_QUE_813976210592DD4F114629'] label:nth-child(2)")
	WebElement radClaimMadeOnThisPolicyNo;

	@FindBy(css = "button[title ='Save Claim' i]")
	WebElement btnSaveClaim;

	@FindBy(css = "button[title ='Cancel' i][name*='FormButton 10']")
	WebElement btnCancelClaim;

	@FindBy(id = "C2__C1__BUT_18102645D390E3D065363_R1")
	WebElement btnEditClaim;

	@FindBy(id = "C2__C1__BUT_18102645D390E3D065365_R1")
	WebElement btnDeleteClaim;

	@FindBy(css = "button[id*='deleteclaims_btn' i]")
	WebElement btnYesConfirmDelete;

	// ***Convictions section****//

	@FindBy(css = "div[id*='p4_QUE_C4D8F1D89DFE94EF134594' i] label:nth-child(1)")
	WebElement radAddNewConvictionYes;

	@FindBy(css = "div[id*='p4_QUE_C4D8F1D89DFE94EF134594' i] label:nth-child(2)")
	WebElement radAddNewConvictionNo;

	@FindBy(css = "button[id*='DRIVER_CONVICTIONS_BTN']")
	WebElement btnNextInConvictions;

	// ***** Conviction details ***********//

	@FindBy(css = "div[id*='p4_QUE_C4D8F1D89DFE94EF136463' i] label:nth-child(1)")
	WebElement chkYesIsMotorProsecutionPending;

	@FindBy(css = "div[id*='QUE_C4D8F1D89DFE94EF136463' i] label:nth-child(2)")
	WebElement chkNoIsMotorProsecutionPending;

	@FindBy(css = "input[name*='CONVICTIONS[1].CONVICTIONDATE' i]")
	WebElement txtConvictionDate;

	@FindBy(css = "select[name*='CONVICTIONS[1].CONVICTIONCODE' i]")
	WebElement drpConvictionCode;

	@FindBy(css = "input[name*='CONVICTIONS[1].FINEAMOUNT' i]")
	WebElement txtFineAmount;

	@FindBy(css = "select[name*='CONVICTIONS[1].PENALTYPOINTS' i]")
	WebElement drpPenaltyPoints;

	@FindBy(css = "div[id*='p4_QUE_C4D8F1D89DFE94EF138810'] label:nth-child(1)")
	WebElement radDriverSuspendedYes;

	@FindBy(css = "div[id*='p4_QUE_C4D8F1D89DFE94EF138810'] label:nth-child(2)")
	WebElement radDriverSuspendedNo;

	@FindBy(css = "button[title='Save Conviction' i]")
	WebElement btnSaveConviction;

	@FindBy(id = "C2__C1__QUE_C4D8F1D89DFE94EF141145")
	WebElement txtDisqualificationPeriod;

	@FindBy(id = "C2__C1__QUE_C4D8F1D89DFE94EF142537")
	WebElement txtAlchoholReading;

	@FindBy(id = "C2__C1__QUE_C4D8F1D89DFE94EF142547")
	WebElement txtAlchoholReadingType;

	@FindBy(id = "C2__C1__QUE_C4D8F1D89DFE94EF144402")
	WebElement drpIsConvictionLinkedToClaim;

	@FindBy(id = "C2__C1__QUE_C4D8F1D89DFE94EF145793")
	WebElement drpChooseClaim;

	@FindBy(css = "button[title = 'cancel' i][id*='BUT_10A8908140B3A47932904']")
	WebElement btnCancelConviction;

	@FindBy(id = "C2__C1__BUT_822697A8D6EB51C7542384_R1")
	WebElement btnEditConviction;

	@FindBy(id = "C2__C1__BUT_822697A8D6EB51C7542386_R1")
	WebElement btnDeleteConviction;

	@FindBy(css = "button[id*='BUT_251D7B0F6525714D62341' i]")
	WebElement btnYesconfirmDelete;

	// ***Medical and disability information section****//

	@FindBy(css = "button[id*='DRIVER_MEDICAL_DISABILITY_BTN']")
	WebElement btnNextInMedicalInfo;

	@FindBy(id = "C2__C1__QUE_622D0025F03AF04A32311_0")
	WebElement radAddNewMedicalConditionYes;

	@FindBy(id = "C2__C1__QUE_622D0025F03AF04A32311_1")
	WebElement radAddNewMedicalConditionNo;

	@FindBy(id = "C2__C1__QUE_622D0025F03AF04A34366_0")
	WebElement radIsDisabledBadgeHolderYes;

	@FindBy(id = "C2__C1__QUE_622D0025F03AF04A34366_1")
	WebElement radIsDisabledBadgeHolderNo;

	@FindBy(id = "C2__C1__QUE_622D0025F03AF04A34372_0")
	WebElement radIsRegisteredDisabledYes;

	@FindBy(id = "C2__C1__QUE_622D0025F03AF04A34372_1")
	WebElement radIsRegisteredDisabledNo;

	@FindBy(css = "select[name*='ADDMEDICALINFO[1].MEDICALCONDITION']")
	WebElement drpMedicalCondition;

	@FindBy(css = "input[name*='ADDMEDICALINFO[1].DIAGNOSEDDATE' i]")
	WebElement txtDateConditionFirstDiagnosed;

	@FindBy(css = "#C2__C1__QUE_CA5A44469A27ECE21065863")
	WebElement txtDateConditionLastOccured;

	@FindBy(css = "input[name*='ADDMEDICALINFO[1].DATELASTOCCURRED' i]")
	WebElement txtDateconditionlastoccur;

	@FindBy(css = "div[id*='p4_QUE_622D0025F03AF04A123264' i] label:nth-child(1)")
	WebElement radHaveDVLABeenInformedYes;

	@FindBy(css = "div[id*='p4_QUE_622D0025F03AF04A123264' i] label:nth-child(2)")
	WebElement radHaveDVLABeenInformedNo;

	@FindBy(id = "C2__C1__QUE_622D0025F03AF04A123255")
	WebElement txtAdditionalInformation;

	@FindBy(id = "C2__C1__BUT_622D0025F03AF04A130016")
	WebElement btnSaveCondition;

	@FindBy(id = "C2__C1__BUT_622D0025F03AF04A130019")
	WebElement btnCancelCondition;

	@FindBy(id = "C2__C1__BUT_D6B2B13555266C7538106_R1")
	WebElement btnEditCondition;

	@FindBy(id = "C2__C1__BUT_D6B2B13555266C7538112_R1")
	WebElement btnDeleteCondition;

	@FindBy(css = "button[id='C2__C1__BUT_251D7B0F6525714D62380']")
	WebElement btnConfirmDelete;

	// **Medical and disability information **//

	@FindBy(css = "#C2__C1__p4_QUE_622D0025F03AF04A32311 label:nth-child(2)")
	WebElement btnAddMoreMedicalConditions;

	@FindBy(css = "#C2__C1__p4_QUE_622D0025F03AF04A34366 label:nth-child(2)")
	WebElement btnDisabledBadgedHolder;

	@FindBy(css = "#C2__C1__p4_QUE_622D0025F03AF04A34372 label:nth-child(2)")
	WebElement btnRegisterDriverDisabled;

	// ***Adverse terms section****//

	@FindBy(css = "#C2__C1__p4_QUE_622D0025F03AF04A37627 label:nth-child(1)")
	WebElement btnYesIncreasedPremiumApplied;

	@FindBy(css = "#C2__C1__p4_QUE_622D0025F03AF04A37627 label:nth-child(2)")
	WebElement btnNoIncreasedPremiumApplied;

	@FindBy(css = "#C2__C1__p4_QUE_622D0025F03AF04A40504 label:nth-child(1)")
	WebElement btnYesInsuranceRefused;

	@FindBy(css = "#C2__C1__p4_QUE_622D0025F03AF04A40504 label:nth-child(2)")
	WebElement btnNoInsuranceRefused;

	@FindBy(css = "#C2__C1__p4_QUE_622D0025F03AF04A40510 label:nth-child(1)")
	WebElement btnYesSpecialTermsApplied;

	@FindBy(css = "#C2__C1__p4_QUE_622D0025F03AF04A40510 label:nth-child(2)")
	WebElement btnNoSpecialTermsApplied;

	@FindBy(css = "#C2__C1__p4_QUE_622D0025F03AF04A40516 label:nth-child(1)")
	WebElement btnYesCriminalConvictions;

	@FindBy(css = "#C2__C1__p4_QUE_622D0025F03AF04A40516 label:nth-child(2)")
	WebElement btnNoCriminalConvictions;

	@FindBy(css = "button[id*='SAVE_NAMED_DRIVER']")
	WebElement btnSaveNamedDriver;

	// ******** Bottom section ***********//

	@FindBy(id = "button[title='Add another policy Change']")
	WebElement btnAddAnotherPolicyChange;

	@FindBy(id = "C2__C1__ID_DRIVERS_CANCEL_BTN")
	WebElement btnCancel;

	// ******** Alert section ***********//

	@FindBy(id = "C2__C1__BUT_B8407EE99A8089A7432861")
	WebElement btnYesRemoveAddtionalDriverPopup;

	@FindBy(id = "C2__C1__BUT_B8407EE99A8089A7432863")
	WebElement btnNoRemoveAddtionalDriverPopup;

	@FindBy(css = "#C2__C1__p4_HEAD_1F145A4C2A19EE9F1624260")
	WebElement txtMsgRemoveAddtionalDriver;

	@FindBy(css = "#C2__C1__TXT_58297DC35B5FB1FF1697250>div")
	WebElement txtInfoInAdditionalDriverPopup;

	// ********* Delete driver *****************//

	@FindBy(css = "#C2__C1__FMT_04968A81233A721470540")
	WebElement mdlDeleteDriver;

	@FindBy(css = "#C2__C1__BUT_04968A81233A721470548")
	WebElement btnYesDeleteDriver;

	@FindBy(css = "#C2__C1__BUT_04968A81233A721470550")
	WebElement btnNoDeleteDriver;

	// ********** Added driver section ***********//

	@FindBy(css = "span[id*='C2__C1__QUE_68E86E63D657A14780106_R']")
	List<WebElement> lstAdditionalDriver;

	@FindBy(css = "#C2__C1__p1_QUE_C23FCA4A6A07F05357849>div>label")
	WebElement txtAddDriverQuestion;

	@FindBy(css = "#C2__C1__HEAD_1F145A4C2A19EE9F1385239")
	WebElement txtBlockMessageInsuredAndNamedDrivers;

	@FindBy(css = "#C2__C1__HEAD_1F145A4C2A19EE9F1385250")
	WebElement txtBlockMessageInsuredAndCivilPartner;

	@FindBy(css = "#C2__C1__HEAD_1F145A4C2A19EE9F1385255")
	WebElement txtBlockMessageInsuredAndSpouse;

	@FindBy(css = "button[id*='QUOTE' i][id*='BTN' i]")
	WebElement btnQuote;

	@FindBy(css = "li[class='active' i] span:nth-child(2)")
	WebElement titleBanner;

	@FindBy(css = "#C2__C1__p4_QUE_C23FCA4A6A07F05357849 label:nth-child(1) input")
	WebElement btnRadioYesAddDriver;

	@FindBy(css = "#C2__C1__p4_QUE_C23FCA4A6A07F05357849 label:nth-child(2) input")
	WebElement btnRadioNoAddDriver;

	/**
	 * 
	 * Constructor class for ChangePermittedDriversPage Here we initializing the
	 * driver for page factory objects. For ajax element waiting time has added
	 * while initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public ChangePermittedDriversPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(txtTitleChangePermittedDrivers.isDisplayed()))) {
			Log.fail("User is not navigated to ChangePermittedDriversPage ", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {
		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
	}

	/**
	 * Verify header name as Change Permitted Drivers
	 * 
	 * @param textToVerify
	 * 
	 * @throws Exception
	 * 
	 */
	public boolean verifyHeader(String textToVerify) throws Exception {
		return GenericUtils.verifyWebElementTextEquals(txtHeader, textToVerify);
	}

	/**
	 * Select driver from Who should be allowed to drive on your policy?
	 * 
	 * @param type
	 *            - Insured and Civil Partner|Insured and Named Drivers|Insured and
	 *            Spouse|Insured Only
	 * @param extentReport
	 * @throws Exception
	 * 
	 */
	public boolean selectDriverTypeDropdown(String type, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		boolean status = true;
		try {
			Select selectmonth = new Select(drpDriverType);
			selectmonth.selectByVisibleText(type);
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected driver type : " + type + "", driver, extentedReport, screenshot);
			return status;
		} catch (Exception e) {
			throw new Exception("Error while selecting driver type: " + type + ": " + e);
		}
	}

	/**
	 * To click on Next button
	 * 
	 * @param sectionType
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextButtonInSection(String sectionType, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (sectionType) {
			case "Personal Details":
				WaitUtils.waitForelementToBeClickable(driver, btnNextInPersonalDetails, "Next button is not found");
				//btnNextInPersonalDetails.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextInPersonalDetails);
				WaitUtils.waitForSpinner(driver);
				break;

			case "Occupation":
				WaitUtils.waitForelementToBeClickable(driver, btnNextInOccupation, "Next button is not found");
				//btnNextInOccupation.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextInOccupation);
				WaitUtils.waitForSpinner(driver);
				break;

			case "Licence Details":
				WaitUtils.waitForelementToBeClickable(driver, btnNextInLicenseDetails, "Next button is not found");
				//btnNextInLicenseDetails.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextInLicenseDetails);
				WaitUtils.waitForSpinner(driver);
				if (WaitUtils.waitForElement(driver, btnNextInLicenseDetails)) {
					btnNextInLicenseDetails.click();
					WaitUtils.waitForSpinner(driver);
				}
				break;

			case "Use Of Vehicle":
				WaitUtils.waitForelementToBeClickable(driver, btnNextInUseOfVehicle, "Next button is not found");
				//btnNextInUseOfVehicle.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextInUseOfVehicle);
				WaitUtils.waitForSpinner(driver);
				break;

			case "Insured Vehicle":
				WaitUtils.waitForelementToBeClickable(driver, btnNextInInsuredVehicle, "Next button is not found");
				//btnNextInInsuredVehicle.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextInInsuredVehicle);
				WaitUtils.waitForSpinner(driver);
				break;

			case "Claims":
				WaitUtils.waitForelementToBeClickable(driver, btnNextInClaims, "Next button is not found");
				JavascriptExecutor executor = (JavascriptExecutor) driver;executor.executeScript("arguments[0].click();", btnNextInClaims);
				WaitUtils.waitForSpinner(driver);
				break;

			case "Convictions":
				WaitUtils.waitForelementToBeClickable(driver, btnNextInConvictions, "Next button is not found");
				JavascriptExecutor executor1 = (JavascriptExecutor) driver;executor1.executeScript("arguments[0].click();", btnNextInConvictions);
				WaitUtils.waitForSpinner(driver);
				break;

			case "Medical and disablity Information":
				WaitUtils.waitForelementToBeClickable(driver, btnNextInMedicalInfo, "Next button is not found");
				JavascriptExecutor executor2 = (JavascriptExecutor) driver;executor2.executeScript("arguments[0].click();", btnNextInMedicalInfo);
				WaitUtils.waitForSpinner(driver);
				break;
			}
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Next button in " + sectionType + " section", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking "+sectionType+" next button : " + e);
		}
	}

	/**
	 * To click yes or No to click add additional drivers
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoForAdditionalDriver(String answer, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (answer) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnAdditionalDriversYes);
				WaitUtils.waitForelementToBeClickable(driver, btnAdditionalDriversYes, "Yes button is not clickable");
				btnAdditionalDriversYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAdditionalDriversNo);
				WaitUtils.waitForelementToBeClickable(driver, btnAdditionalDriversNo, "No button is not clickable");
				btnAdditionalDriversNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			WaitUtils.waitForElement(driver, drpSelectTitle);
			Log.message("Clicked option for Do you want to add additional drivers? is : " + answer + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	// **************** Personal details section ********************///

	/**
	 * To Select title
	 * 
	 * @param title
	 * 
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectTitle(String title, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			Select selectTitle = new Select(drpSelectTitle);
			selectTitle.selectByVisibleText(title);
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected Title: " + title + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while selecting Title: " + title + ": " + e);
		}
	}

	/**
	 * To enter fore name
	 * 
	 * @param forename
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterForename(String foreName, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldForename);
			fldForename.clear();
			fldForename.sendKeys(foreName);
			Log.message("Entered the Forename : " + foreName, driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Forename : " + e);
		}
	}

	/**
	 * To enter surname
	 * 
	 * @param surname
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterSurename(String surname, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldSurname);
			fldSurname.clear();
			fldSurname.sendKeys(surname);
			Log.message("Entered the Surname : " + surname, driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Surname : " + e);
		}
	}

	/**
	 * To enter dateOfBirth
	 * 
	 * @param dateOfBirth
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterDOB(String dateOfBirth, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldDOB);
			fldDOB.clear();
			fldDOB.sendKeys(dateOfBirth);
			fldDOB.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Entered the Date Of Birth : " + dateOfBirth, driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering date : " + e);
		}
	}

	/**
	 * Verifies that the user is able to enter DOB on input field.
	 * 
	 * @return true if the user is able to enter DOB on input field; false otherwise
	 */
	public boolean verifyDOBFieldIsEditable(String dateOfBirth, ExtentTest extentedReport) throws Exception {
		enterDOB(dateOfBirth, extentedReport, false);
		String actualDate = fldDOB.getAttribute("value");
		Log.message("Actual Date : [" + actualDate + "]; Expected Date : [" + dateOfBirth + "]", extentedReport);
		return actualDate.equals(dateOfBirth);
	}

	/**
	 * Verifies that the DOB date picker works properly.
	 * 
	 * @return true if date picker works as expected; false otherwise
	 */
	public boolean verifyDOBInDatePicker(ExtentTest extentedReport) {
		WaitUtils.waitForElement(driver, fldDOBDatePicker);
		fldDOB.clear();
		fldDOBDatePicker.click();
		String actualDate = fldDOB.getAttribute("value");
		fldDOBDatePicker.click();
		String expectedDate = DateTimeUtility.getCurrentDate();
		Log.message("Actual Date : [" + actualDate + "]; Expected Date : [" + expectedDate + "]", extentedReport);
		return actualDate.equals(expectedDate);
	}

	/**
	 * Verifies that the 'Since Birth' button works properly.
	 * 
	 * @return true if 'Since Birth' button works as expected; false otherwise
	 */
	public boolean verifySinceBirth(ExtentTest extentedReport) {
		WaitUtils.waitForElement(driver, btnSinceBirth);
		String expectedDate = fldDOB.getAttribute("value");
		fldDateOfLivingInUK.clear();
		btnSinceBirth.click();
		WaitUtils.waitForSpinner(driver);
		new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class)
				.withMessage("Timed out waiting for date to be populated in 'Living in UK' field")
				.until(ExpectedConditions.attributeToBeNotEmpty(fldDateOfLivingInUK, "value"));
		String actualDate = fldDateOfLivingInUK.getAttribute("value");
		Log.message("Actual Date : [" + actualDate + "]; Expected Date : [" + expectedDate + "]", extentedReport);
		return actualDate.equals(expectedDate);
	}

	/**
	 * To enter date of living
	 * 
	 * @param date
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterDateOfLivingInUK(String date, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForelementToBeClickable(driver, fldDateOfLivingInUK,
					"Field to enter value for DateOfLivingInUK is not found");
			fldDateOfLivingInUK.click();
			WaitUtils.waitForSpinner(driver);
			fldDateOfLivingInUK.clear();
			((JavascriptExecutor) driver).executeScript("arguments[0].value='" + date + "'", fldDateOfLivingInUK);
			Thread.sleep(1000); // Mandatory
			fldDateOfLivingInUK.click();
			Log.message("Entered the Date for When did the named driver begin living in the U.K.?\r\n" + " : " + date,
					driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering date : " + e);
		}
	}

	/**
	 * To Select Gender
	 * 
	 * @param gender
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectGender(String gender, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			Select selectGender = new Select(drpSelectGender);
			selectGender.selectByVisibleText(gender);
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected Gender : " + gender + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while selecting Gender : " + gender + " : " + e);
		}
	}

	/**
	 * To Select MaritalStatus
	 * 
	 * @param MaritalStatus
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectMaritalStatus(String maritalStatus, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			Select selectMaritalStatus = new Select(drpSelectMaritalStatus);
			selectMaritalStatus.selectByVisibleText(maritalStatus);
			Log.message("Selected Marital Status : " + maritalStatus + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while selecting Marital Status : " + maritalStatus + ": " + e);
		}
	}

	/**
	 * To Select RelationShip
	 * 
	 * @param RelationShip
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectRelationShip(String RelationShip, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			if(drpSelectRelationship.isEnabled()){
			Select selectRelationShip = new Select(drpSelectRelationship);
			selectRelationShip.selectByVisibleText(RelationShip);
			Log.message("Selected RelationShip : " + RelationShip + "", driver, extentedReport, screenshot);
			WaitUtils.waitForSpinner(driver);
			}
		} catch (Exception e) {
			throw new Exception("Error while selecting RelationShip : " + RelationShip + ": " + e);
		}
	}

	/**
	 * To get Details from personal detail Section
	 * 
	 * @param extentedReport
	 * @return HashMap
	 * 
	 */
	public HashMap<String, String> getPersonalDetailSection(ExtentTest extentedReport) throws Exception {
		try {
			HashMap<String, String> personalDetails = new HashMap<String, String>();

			// Title
			Select selectTitle = new Select(drpSelectTitle);
			String titleValue = selectTitle.getFirstSelectedOption().getText();
			personalDetails.put("Title", titleValue);

			// fore name
			String displayedForeName = fldForename.getAttribute("value");
			personalDetails.put("ForeName", displayedForeName);

			// surname
			String displayedSurName = fldSurname.getAttribute("value");
			personalDetails.put("SurName", displayedSurName);

			// DOB
			String displayedDOB = fldDOB.getAttribute("value");
			personalDetails.put("DOB", displayedDOB);

			// date of living
			String dateOfLiving = fldDateOfLivingInUK.getAttribute("value");
			personalDetails.put("DateOfLiving", dateOfLiving);

			// Gender
			Select selectgender = new Select(drpSelectGender);
			String genderValue = selectgender.getFirstSelectedOption().getText();
			personalDetails.put("Gender", genderValue);

			// Marital status
			Select selectMaritalStatus = new Select(drpSelectMaritalStatus);
			String maritalStatusValue = selectMaritalStatus.getFirstSelectedOption().getText();
			personalDetails.put("MaritalStatus", maritalStatusValue);

			// Relationship status
			Select selectRelationShip = new Select(drpSelectRelationship);
			String relationshipValue = selectRelationShip.getFirstSelectedOption().getText();
			personalDetails.put("Relationship", relationshipValue);

			Set<String> keys = personalDetails.keySet();
			for (String key : keys) {
				Log.message("Displayed Value of " + key + " is : " + personalDetails.get(key), extentedReport);
			}
			return personalDetails;

		} catch (Exception e) {
			throw new Exception("Error while getting Personal Details : " + e);
		}
	}

	/**
	 * verify Personal Detail
	 * 
	 * @param expectedPersonalDetail
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyPersonalDetail(HashMap<String, String> expectedPersonalDetail, ExtentTest extentedReport)
			throws Exception {
		try {
			HashMap<String, String> personalDetail = getPersonalDetailSection(extentedReport);
			return GenericUtils.compareTwoHashMap(personalDetail, expectedPersonalDetail);
		} catch (Exception e) {
			throw new Exception("Error while verifying Personal Detail section : " + e);
		}
	}

	// *************************Occupation details ******************//

	/**
	 * To click yes or No to add occupation
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoToAddOccupation(String answer, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (answer) {
			case "Yes":
				btnAddOccupationYes.click();
				break;

			case "No":
				btnAddOccupationNo.click();
				break;
			}
			Log.message("Clicked option for Do you want to add another occupation? is : " + answer + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * Verifies the main occupation validation error message.
	 * 
	 * @param occupation
	 * @param extentReport
	 * @param screenshot
	 * @return true if the error message is as expected; false otherwise
	 */
	public boolean verifyOccupationDetails(String occupation, ExtentTest extentReport, boolean screenshot)
			throws Exception {
		boolean status = false;
		try {

			if (occupationName.size() == 0) {
				Log.message("Occupation section not contain saved ocupation records", driver, extentReport);
				return status;

			} else {
				Log.message("Occupation section contains saved occupation records", driver, extentReport);

				for (int rowCount = 0; rowCount <= occupationName.size(); rowCount++) {
					if (occupation.equals(occupationName.get(rowCount).getText())) {
						status = true;
						break;
					} else {
						Log.message(occupation + " : Occupation name not displaying under occupation details section",
								driver, extentReport);
					}
				}
			}
			return status;

		} catch (Exception e) {
			Log.message("Error while verifying the occupation details seciton : " + e);
			status = false;
			return status;
		}

	}

	// ************* License details************//

	/**
	 * To enter License Date
	 * 
	 * @param date
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean enterLicenseStartDate(String date, ExtentTest extentedReport, boolean screenshot) throws Exception {
		boolean status = true;
		try {

			WaitUtils.waitForElement(driver, FldLicenseDate);
			FldLicenseDate.clear();
			WaitUtils.waitForSpinner(driver);
			FldLicenseDate.sendKeys(date);
			Log.message("Entered the Date that the named driver attained their current License : " + date, driver,
					extentedReport, screenshot);
			WaitUtils.waitForSpinner(driver);
			return status;
		} catch (Exception e) {
			status = false;
			Log.message("Error while entering date : " + e);
			return status;
		}
	}

	/**
	 * To Select LicenseType
	 * 
	 * @param LicenseType
	 * @param screenshot
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectLicenseType(String LicenseType, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			Select selectLicenseType = new Select(drpSelectLicenseType);
			selectLicenseType.selectByVisibleText(LicenseType);
			Log.message("Selected LicenseType : " + LicenseType + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while selecting LicenseType : " + LicenseType + " : " + e);
		}
	}

	/**
	 * To Select Additional Driving Qualification
	 * 
	 * @param qualification
	 * @param screenshot
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectAdditionalDrivingQualification(String qualification, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			Select selectList = new Select(drpAddtionalDrivingQualification);
			selectList.selectByVisibleText(qualification);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForElement(driver, fldDateAttained);
			Log.message("Selected Additional Driving Qualification : " + qualification + "", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception(
					"Error while selecting Additional Driving Qualification : " + qualification + " : " + e);
		}
	}

	/**
	 * Enter License number
	 * 
	 * @param Number
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterLicenseNumber(String Number, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldLicenseNumber);
			fldLicenseNumber.clear();
			fldLicenseNumber.sendKeys(Number);
			Log.message("Entered the License Number : " + Number, driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Number : " + e);
		}
	}

	/**
	 * To Select reason
	 * 
	 * @param reason
	 * @param screenshot
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectReason(String reason, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			Select selectList = new Select(drpSelectDVLAReason);
			selectList.selectByVisibleText(reason);
			Log.message("Selected Reason for not allowing DVLA lookup consent is : " + reason + "", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception(
					"Error while selecting Reason for not allowing DVLA lookup consent : " + reason + " : " + e);
		}
	}

	/**
	 * To Enter Date Attained
	 * 
	 * @param date
	 * @param screenshot
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void enterDateAttained(String date, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldDateAttained);
			fldDateAttained.clear();
			fldDateAttained.click();
			fldDateAttained.sendKeys(date);
			fldDateAttained.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Entered Date Attained Value : " + date, driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Date Attained - " + date + " : " + e);
		}
	}

	/**
	 * To get Details from personal detail Section
	 * 
	 * @param extentedReport
	 * @return HashMap
	 * 
	 */
	public HashMap<String, String> getLicenseDetail(ExtentTest extentedReport) throws Exception {
		try {
			HashMap<String, String> LicenseDetails = new HashMap<String, String>();

			// License Date
			String LicenseDate = FldLicenseDate.getAttribute("value");
			LicenseDetails.put("LicenceDate", LicenseDate);

			// LicenseType
			Select selectLicenseType = new Select(drpSelectLicenseType);
			String LicenseType = selectLicenseType.getFirstSelectedOption().getText();
			LicenseDetails.put("LicenceType", LicenseType);

			// Additional Driving Qualification
			Select selectaddtionalDrivingQualification = new Select(drpAddtionalDrivingQualification);
			String addtionalDrivingQualification = selectaddtionalDrivingQualification.getFirstSelectedOption()
					.getText();
			LicenseDetails.put("AdditionalDrivingQualification", addtionalDrivingQualification);

			String dateAttained = fldDateAttained.getAttribute("value");
			LicenseDetails.put("DateAttained", dateAttained);

			Set<String> keys = LicenseDetails.keySet();
			for (String key : keys) {
				Log.message("Displayed Value of " + key + " is: " + LicenseDetails.get(key), extentedReport);
			}
			return LicenseDetails;

		} catch (Exception e) {
			throw new Exception("Error while getting License Details : " + e);
		}
	}

	/**
	 * verify License Detail
	 * 
	 * @param expectedPersonalDetail
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyLicenseDetail(HashMap<String, String> expectedLicenseDetail, ExtentTest extentedReport)
			throws Exception {
		try {
			HashMap<String, String> LicenseDetail = getLicenseDetail(extentedReport);
			return GenericUtils.compareTwoHashMap(LicenseDetail, expectedLicenseDetail);
		} catch (Exception e) {
			throw new Exception("Error while verifying License Detail section : " + e);
		}
	}

	// *********** Use of other vehicles ************ //

	/**
	 * To Select Own Car Count
	 * 
	 * @param Count
	 * @param screenshot
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectOwnCarCount(String Count, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			Select selectList = new Select(drpSelectHowManyOwnCars);
			selectList.selectByVisibleText(Count);
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected How many other cars does the named driver own? is : " + Count + "", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception(
					"Error while selecting How many other cars does the named driver own? : " + Count + " : " + e);
		}
	}

	/**
	 * To Select additional Car Count
	 * 
	 * @param Count
	 * @param screenshot
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectAdditionalCarCount(String Count, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			Select selectList = new Select(drpHowManyAddtionalCars);
			selectList.selectByVisibleText(Count);
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected How many additional cars does the named driver have access to? is : " + Count + "",
					driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception(
					"Error while selecting How many additional cars does the named driver have access to? : " + Count
							+ ": " + e);
		}
	}

	/**
	 * To Select year Count
	 * 
	 * @param Count
	 * @param screenshot
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectYearCount(String Count, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			Select selectList = new Select(drpHowManyYears);
			selectList.selectByVisibleText(Count);
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected How many years no claims does the named driver have on their own policy? is : "
					+ Count + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception(
					"Error while selecting How many years no claims does the named driver have on their own policy? : "
							+ Count + " : " + e);
		}
	}

	/**
	 * To Select car Count in household
	 * 
	 * @param Count
	 * @param screenshot
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectHouseholdCarCount(String Count, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			Select selectList = new Select(drpHowManyCarsInHousehold);
			selectList.selectByVisibleText(Count);
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected How many cars does the named driver in their household? is : " + Count + "", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while selecting How many cars does the named driver in their household? : "
					+ Count + " : " + e);
		}
	}

	/**
	 * To get Details from Use Of Other Vehicles
	 * 
	 * @param extentedReport
	 * @return HashMap
	 * 
	 */
	public HashMap<String, String> getUseOfOtherVehiclesDetail(ExtentTest extentedReport) throws Exception {
		try {
			HashMap<String, String> UseOfOtherVehicles = new HashMap<String, String>();

			// How Many Own Cars
			Select selectCarCount = new Select(drpSelectHowManyOwnCars);
			String carCount = selectCarCount.getFirstSelectedOption().getText();
			UseOfOtherVehicles.put("OwnCars", carCount);

			// Additional Cars
			Select displayedAddtionalCars = new Select(drpHowManyAddtionalCars);
			String additionalCar = displayedAddtionalCars.getFirstSelectedOption().getText();
			UseOfOtherVehicles.put("AdditionalCars", additionalCar);

			// Additional Cars
			Select displayedYear = new Select(drpHowManyYears);
			String yearCount = displayedYear.getFirstSelectedOption().getText();
			UseOfOtherVehicles.put("YearCount", yearCount);

			// Household car
			/*Select selectHouseholdCar = new Select(drpHowManyCarsInHousehold);
			String householdCar = selectHouseholdCar.getFirstSelectedOption().getText();
			UseOfOtherVehicles.put("HouseholdCar", householdCar);*/

			Set<String> keys = UseOfOtherVehicles.keySet();
			for (String key : keys) {
				Log.message("Displayed Value of " + key + " is : " + UseOfOtherVehicles.get(key), extentedReport);
			}

			return UseOfOtherVehicles;

		} catch (Exception e) {
			throw new Exception("Error while getting Vechile Details : " + e);
		}
	}

	/**
	 * verify UseOfOtherVehicles
	 * 
	 * @param expectedUseOfOtherVehicles
	 * @param extentedReport
	 * @return boolean
	 * 
	 */
	public boolean verifyUseOfOtherVehiclesDetail(HashMap<String, String> expectedUseOfOtherVehicles,
			ExtentTest extentedReport) throws Exception {
		try {
			HashMap<String, String> useOfOtherVehiclesDetail = getUseOfOtherVehiclesDetail(extentedReport);
			return GenericUtils.compareTwoHashMap(useOfOtherVehiclesDetail, expectedUseOfOtherVehicles);
		} catch (Exception e) {
			throw new Exception("Error while verifying Use Of Other Vehicles Detail : " + e);
		}
	}

	// *******Use of insured vehicles ****** //

	/**
	 * To Select Frequency Of Use
	 * 
	 * @param type
	 * @param screenshot
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public void selectFrequencyOfUse(String type, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			Select selectList = new Select(drpSelectFrequencyOfUse);
			selectList.selectByVisibleText(type);
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected Frequency Of Use is : " + type + "", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while selecting Frequency Of Use : " + type + ": " + e);
		}
	}

	/**
	 * To click yes or No for vehicle purpose
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoForVehiclePurpose(String answer, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (answer) {
			case "Yes":
				btnAddVehiclePurposeYes.click();
				break;

			case "No":
				btnAddVehiclePurposeNo.click();
				break;
			}
			Log.message("Clicked option for Will the named driver be using this vehicle for commuting purposes? is : "
					+ answer + "", driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * To verify Frequency Of Use
	 * 
	 * @param use
	 * @param extentedReport
	 * @throws Exception
	 * 
	 */
	public boolean verifyFrequencyOfUse(String use, ExtentTest extentedReport) throws Exception {
		try {
			boolean status = false;
			Select selectList = new Select(drpSelectFrequencyOfUse);
			String frequencyOfUse = selectList.getFirstSelectedOption().getText();
			Log.message("Selected frequency of Use value as : " + frequencyOfUse, driver, extentedReport);
			if (frequencyOfUse.contains(use)) {
				status = true;
			}

			return status;
		} catch (Exception e) {
			throw new Exception("Error while verifying Frequency Of Use : " + e);
		}
	}

	// ********* Claims ****************//

	/**
	 * To click yes or No To Add Claims
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoToAddClaims(String answer, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			switch (answer) {
			case "Yes":
				radAddMoreClaimYes.click();
				break;

			case "No":
				radAddMoreClaimNo.click();
				break;
			}
			Log.message("Clicked option for Do you want to add any more claims? is : " + answer + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	// ************** Conviction ****** //

	/**
	 * To click yes or No To Add Convictions
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoToAddConvictions(String answer, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (answer) {
			case "Yes":
				btnYesCriminalConvictions.click();
				break;

			case "No":
				btnNoCriminalConvictions.click();
				break;
			}
			Log.message("Clicked option for Do you want to add any more convictions? is : " + answer + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	// ************** Medical conditions ***********//

	/**
	 * To click yes or No To Add medical conditions
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */

	public void clickYesOrNoMoreMedicalConditions(String answer, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (answer.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnAddMoreMedicalConditions);
				btnAddMoreMedicalConditions.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAddMoreMedicalConditions);
				btnAddMoreMedicalConditions.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked option for want to add any more medical conditions? : " + answer + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * To click yes or No To Add medical conditions
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */

	public void clickYesOrNoBadgedHolder(String answer1, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (answer1.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnDisabledBadgedHolder);
				btnDisabledBadgedHolder.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnDisabledBadgedHolder);
				btnDisabledBadgedHolder.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked option for Is the named driver a disabled badge holder? : " + answer1 + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * To click yes or No To Add medical conditions
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */

	public void clickYesOrNoRegisterDriverDisabled(String answer2, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (answer2.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnRegisterDriverDisabled);
				btnRegisterDriverDisabled.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnRegisterDriverDisabled);
				btnRegisterDriverDisabled.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked option for Is the named driver registered disabled? : " + answer2 + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	// ************* Adverse ****************//

	/**
	 * To click yes or No for Increased Premium Applied
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoIncreasedPremiumApplied(String answer, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (answer.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnYesIncreasedPremiumApplied);
				btnYesIncreasedPremiumApplied.click();
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnNoIncreasedPremiumApplied);
				btnNoIncreasedPremiumApplied.click();
				break;
			}
			Log.message("Clicked option for Has the named driver ever had an increased premium applied? is : " + answer
					+ "", driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * To click yes or No to check is Insurance Refused
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoIsInsuranceRefused(String answer, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnYesInsuranceRefused);
			switch (answer) {
			case "Yes":
				btnYesInsuranceRefused.click();
				break;

			case "No":
				btnNoInsuranceRefused.click();
				break;
			}
			Log.message(
					"Clicked option for Has the named driver ever had insurance refused, declined or cancelled? is : "
							+ answer + "",
					driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * To click yes or No to check is SpecialTerms Applied
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoIsSpecialTermsApplied(String answer, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnYesSpecialTermsApplied);
			switch (answer) {
			case "Yes":
				btnYesSpecialTermsApplied.click();
				break;

			case "No":
				btnNoSpecialTermsApplied.click();
				break;
			}
			Log.message("Clicked option for Has the named driver ever had special terms applied? is : " + answer + "",
					driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * To click yes or No to check For CriminalConviction
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoForCriminalConviction(String answer, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnYesCriminalConvictions);
			switch (answer) {
			case "Yes":
				btnYesCriminalConvictions.click();
				break;

			case "No":
				btnNoCriminalConvictions.click();
				break;
			}
			Log.message(
					"Clicked option for Has the named driver ever had any criminal convictions, Cautions or pending prosecutions? is : "
							+ answer + "",
					driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	// ************** Bottom **************//

	/**
	 * To click save named driver button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickSaveNamedDriverBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnSaveNamedDriver);
			btnSaveNamedDriver.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Save named driver Button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking save named driver button : " + e);
		}
	}

	/**
	 * To click AddAnotherPolicyChange
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickAddAnotherPolicyChangeBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnAddAnotherPolicyChange);
			btnAddAnotherPolicyChange.click();
			Log.message("Clicked on Add Another PolicyChange button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking Add Another PolicyChange button : " + e);
		}
	}

	/**
	 * To click quote
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickQuoteBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnQuote);
			btnQuote.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Quote button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking Quote button : " + e);
		}
	}

	/**
	 * To click cancel
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickCancelBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnCancel);
			btnCancel.click();
			Log.message("Clicked on Cancel button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking Cancel button : " + e);
		}
	}

	// ************ Edit and delete section ***********//

	/**
	 * To click edit button based on order
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickEditButton(String number, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			String elementString = btnEditDrivers + number;
			WebElement btn = driver.findElement(By.cssSelector(elementString));
			btn.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on " + number + " (th) Edit button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking Edit button : " + e);
		}
	}

	/**
	 * To click Delete button based on order
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickDeleteButton(String number, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			String elementString = btnDeleteDrivers + number;
			WebElement btn = driver.findElement(By.cssSelector(elementString));
			btn.click();
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on " + number + " (th) delete button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking delete button : " + e);
		}
	}

	// *********** Pop up section **************//

	/**
	 * To click yes button in pop up
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoInRemoveAdditionalDriverPopup(String answer, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (answer) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnYesRemoveAddtionalDriverPopup);
				WaitUtils.waitForelementToBeClickable(driver, btnYesRemoveAddtionalDriverPopup,
						"Yes button is not found in the popup");
				GenericUtils.clickElementJS(driver, btnYesRemoveAddtionalDriverPopup);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnNoRemoveAddtionalDriverPopup);
				WaitUtils.waitForelementToBeClickable(driver, btnNoRemoveAddtionalDriverPopup,
						"No button is not found in the popup");
				GenericUtils.clickElementJS(driver, btnNoRemoveAddtionalDriverPopup);
				break;
			}
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on " + answer + " button in Remove Additional Driver Popup", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking button in popup : " + e);
		}
	}

	/**
	 * To enter personal details
	 * 
	 * @param title
	 * @param foreName
	 * @param surname
	 * @param dateOfBirth
	 * @param dateOfLivingInUk
	 * @param gender
	 * @param maritalStatus
	 * @param RelationShip
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterPersonalDetails(String title, String foreName, String surname, String dateOfBirth,
			String dateOfLivingInUk, String gender, String maritalStatus, String RelationShip,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			selectTitle(title, extentedReport, screenshot);
			enterForename(foreName, extentedReport, screenshot);
			enterSurename(surname, extentedReport, screenshot);
			enterDOB(dateOfBirth, extentedReport, screenshot);
			enterDateOfLivingInUK(dateOfLivingInUk, extentedReport, screenshot);
			selectGender(gender, extentedReport, screenshot);
			selectMaritalStatus(maritalStatus, extentedReport, screenshot);
			selectRelationShip(RelationShip, extentedReport, screenshot);
			WaitUtils.waitForSpinner(driver);
			Log.message("Entered Personal details", driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while Entering personal details : " + e);
		}
	}

	/**
	 * To enter License details
	 * 
	 * @param date
	 * @param LicenseType
	 * @param qualification
	 * @param Number
	 * @param reason
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterLicenseDetails(String date, String LicenseType, String qualification, String Number, String reason,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			enterLicenseStartDate(date, extentedReport, screenshot);
			selectLicenseType(LicenseType, extentedReport, screenshot);
			selectAdditionalDrivingQualification(qualification, extentedReport, screenshot);
			enterDateAttained(date, extentedReport, screenshot);
			Log.message("Entered License details", driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while entering License details : " + e);
		}
	}

	/**
	 * To enter UseOfOtherVehicle Details
	 * 
	 * @param Count
	 * @param addionalCarCount
	 * @param yearCount
	 * @param householdCarCount
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterUseOfOtherVehicleDetails(String Count, String addionalCarCount, String yearCount,
			String householdCarCount, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			selectOwnCarCount(Count, extentedReport, screenshot);
			selectAdditionalCarCount(addionalCarCount, extentedReport, screenshot);
			selectYearCount(yearCount, extentedReport, screenshot);
			selectHouseholdCarCount(householdCarCount, extentedReport, screenshot);
			Log.message("Entered use of vehicle details", driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while entering use of vehicle details : " + e);
		}
	}

	/**
	 * To enter UseOfOtherVehicle Details
	 * 
	 * @param frequencyOfUse
	 * @param isCommutingPurpose
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterUseOfInsuredVehicleDetails(String frequencyOfUse, String isCommutingPurpose,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			selectFrequencyOfUse(frequencyOfUse, extentedReport, screenshot);
			clickYesOrNoForVehiclePurpose(isCommutingPurpose, extentedReport, screenshot);
			WaitUtils.waitForSpinner(driver);
			Log.message("Entered use of insured vehicle details : "+frequencyOfUse, driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while entering use of insured vehicle details : " + e);
		}
	}

	/**
	 * To enter AdverseAndTermsDetails
	 * 
	 * @param type
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterAdverseAndTermsDetails(String answer1, String answer2, String answer3, String answer4,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			clickYesOrNoIncreasedPremiumApplied(answer1, extentedReport, screenshot);
			clickYesOrNoIsInsuranceRefused(answer2, extentedReport, screenshot);
			clickYesOrNoIsSpecialTermsApplied(answer3, extentedReport, screenshot);
			clickYesOrNoForCriminalConviction(answer3, extentedReport, screenshot);
			Log.message("Entered AdverseAndTerms details", driver, extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while entering AdverseAndTerms : " + e);
		}
	}

	public void enterMedicalandDisabilityDetails(String answer, String answer1, String answer2,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		{
			try {
				clickYesOrNoMoreMedicalConditions(answer, extentedReport, screenshot);
				clickYesOrNoBadgedHolder(answer1, extentedReport, screenshot);
				clickYesOrNoRegisterDriverDisabled(answer2, extentedReport, screenshot);
			} catch (Exception e) {
				throw new Exception("Error while entering Medical and disablity information : " + e);
			}
		}
	}

	/**
	 * To enter AdverseAndTermsDetails
	 * 
	 * @param type
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void addAdditionalDrivers(String[] sectionTypeList, String title, String foreName, String surname,
			String dateOfBirth, String dateOfLivingInUk, String gender, String maritalStatus, String relationship,
			String main_occupation, String main_business, String main_empStatus, String LicenseDate, String LicenseType,
			String qualification, String LicenseNumber, String reason, String carCount, String addionalCarCount,
			String yearCount, String householdCarCount, String frequencyOfUse, String isCommutingPurpose,
			String isIncreasedPremiumApplied, String isInsuranceRefused, String isSpecialTermsApplied,
			String hadCriminalConviction, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			// Personal Detail
			enterPersonalDetails(title, foreName, surname, dateOfBirth, dateOfLivingInUk, gender, maritalStatus,
					relationship, extentedReport, screenshot);
			clickNextButtonInSection(sectionTypeList[0], extentedReport, screenshot);

			// Occupation
			addOccupation(main_occupation, main_business, main_empStatus, true, extentedReport, screenshot);
			clickNextButtonInSection(sectionTypeList[1], extentedReport, screenshot);

			// License Detail
			enterLicenseDetails(LicenseDate, LicenseType, qualification, LicenseNumber, reason, extentedReport,
					screenshot);
			clickNextButtonInSection(sectionTypeList[2], extentedReport, screenshot);

			// Use of other Vehicle
			enterUseOfOtherVehicleDetails(carCount, addionalCarCount, yearCount, householdCarCount, extentedReport,
					screenshot);
			clickNextButtonInSection(sectionTypeList[3], extentedReport, screenshot);

			// Use of Insured Vehicle
			enterUseOfInsuredVehicleDetails(frequencyOfUse, isCommutingPurpose, extentedReport, screenshot);
			clickNextButtonInSection(sectionTypeList[4], extentedReport, screenshot);

			// Claims
			clickNextButtonInSection(sectionTypeList[5], extentedReport, screenshot);

			// Conviction
			clickNextButtonInSection(sectionTypeList[6], extentedReport, screenshot);

			// Medical and Disability Details
			enterMedicalandDisabilityDetails(isIncreasedPremiumApplied, isInsuranceRefused, isSpecialTermsApplied,
					extentedReport, screenshot);
			clickNextButtonInSection(sectionTypeList[7], extentedReport, screenshot);

			// Adverse And Terms Details
			enterAdverseAndTermsDetails(isIncreasedPremiumApplied, isInsuranceRefused, isSpecialTermsApplied,
					hadCriminalConviction, extentedReport, screenshot);
			clickSaveNamedDriverBtn(extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while adding drivers : " + e);
		}
	}

	// ********* Alert section *************//

	/**
	 * To click yes button in popup
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyMessageInRemoveAddtionalDriverPopup(String expectedMessage, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			boolean status = false;
			WaitUtils.waitForElement(driver, txtMsgRemoveAddtionalDriver);
			if (txtMsgRemoveAddtionalDriver.getText().trim().equals(expectedMessage)) {
				status = true;
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while clicking yes button : " + e);
		}
	}

	/**
	 * To Get row consists of driver name
	 * 
	 * @param driverName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public String getDriverNameRow(String driverName, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			String DriverRow = null;

			List<WebElement> lstdriverNameRow = driver.findElements(By.cssSelector(rowDriverName));
			if (lstdriverNameRow.size() == 0) {
				Log.message("No Permitted driver are available", driver, extentedReport, false);
			}

			for (int loopCount = 0; loopCount < lstdriverNameRow.size(); loopCount++) {
				Integer row = loopCount + 1;
				String displayedDriverName = driver.findElement(By.cssSelector(txtDriverName + row.toString()))
						.getText();
				if (displayedDriverName.contains(driverName)) {
					return row.toString();
				}
			}
			return DriverRow;

		} catch (Exception e) {
			throw new Exception("Error while getting row consists of driver name : " + e);
		}
	}

	/**
	 * To click delete button
	 * 
	 * @param driverName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickDeleteBtnOfPermittedDriver(String driverName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {

			String driverNameRow = getDriverNameRow(driverName, extentedReport, true);
			if (driverNameRow == null) {
				throw new Exception(driverName + " - Driver is not found");
			}
			clickDeleteButton(driverNameRow, extentedReport, false);
		} catch (Exception e) {
			throw new Exception("Error while clicking delete button : " + e);
		}
	}

	/**
	 * To click delete button
	 * 
	 * @param driverName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickEditBtnOfPermittedDriver(String driverName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {

			String driverNameRow = getDriverNameRow(driverName, extentedReport, true);
			if (driverNameRow == null) {
				throw new Exception(driverName + " - Driver is not found");
			}
			clickEditButton(driverNameRow, extentedReport, false);
		} catch (Exception e) {
			throw new Exception("Error while clicking Edit button : " + e);
		}
	}

	/**
	 * To click yes/No button in popup
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoBtnToDeleteDriver(String answer, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (answer) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnYesDeleteDriver);
				GenericUtils.clickElementJS(driver, btnYesDeleteDriver);
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnNoDeleteDriver);
				GenericUtils.clickElementJS(driver, btnNoDeleteDriver);
				WaitUtils.waitForSpinner(driver);
				break;
			}
			WaitUtils.waitForPageLoad(driver);
			Log.message("Clicked on " + answer + " button in Driver deleting Popup", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking button in driver deleting popup : " + e);
		}
	}

	/**
	 * To verify the message
	 * 
	 * @param expectedMessage
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyBlockMessageForInsuredAndNamedDrivers(String expectedMessage, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			boolean status = false;
			if (txtBlockMessageInsuredAndNamedDrivers.getText().trim().equals(expectedMessage)) {
				status = true;
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while verifying message : " + e);
		}
	}

	/**
	 * To verify Driver Name contains relationship
	 * 
	 * @param driverName
	 * @param relationship
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyDriverNameContainsRelationship(String driverName, String relationship,
			ExtentTest extentedReport, boolean screenshot) throws Exception {
		boolean status = false;
		try {

			List<WebElement> lstdriverNameRow = driver.findElements(By.cssSelector(rowDriverName));
			if (lstdriverNameRow.size() == 0) {
				Log.message("No Permitted driver are available", driver, extentedReport, false);
			}

			for (int loopCount = 0; loopCount < lstdriverNameRow.size(); loopCount++) {
				Integer row = loopCount + 1;
				String displayedDriverName = driver.findElement(By.cssSelector(txtDriverName + row.toString()))
						.getText();
				if (displayedDriverName.contains(driverName) && displayedDriverName.contains(relationship)) {
					status = true;
					break;
				}
			}
			return status;

		} catch (Exception e) {
			throw new Exception("Error while verifying Driver Name : " + e);
		}

	}

	/**
	 * To get additional Driver count
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public int getAdditionalDriverCount(ExtentTest extentedReport, boolean screenshot) throws Exception {
		int driverCount = 0;
		try {
			driverCount = lstAdditionalDriver.size();
		} catch (Exception e) {
			throw new Exception("Error while verifying Driver count : " + e);
		}
		return driverCount;
	}

	/**
	 * Clicks on 'Yes' for 'Do you want to add additional drivers?' and verifies the
	 * functionality.
	 * 
	 * @param extentReport
	 * @return true if the section with title 'Adding Named Driver
	 *         &lt;DRIVER_INDEX&gt;' is shown; false otherwise
	 */
	public boolean clickYesAddAdditionalDriver(ExtentTest extentReport) {
		int newDriverIndex = driver.findElements(By.cssSelector(cssAddedNamedDriverTitle)).size();
		Log.message("Total no. of existing named drivers: " + (newDriverIndex), extentReport);
		GenericUtils.clickElementJS(driver, radAddDriverYes);
		Log.message("Clicked on 'Yes' radio button for 'Do you want to add additional drivers?'", extentReport);
		GenericUtils.scrollIntoView(driver, radAddDriverYes);
		boolean isOpenedUp = false;
		try {
			String xpathNewDriver = xpathAddingNamedDriverTitle.replaceAll("\\$\\{NUMBER\\}", ("" + (newDriverIndex+1)));
			WaitUtils.waitForElementPresent(driver, "xpath", xpathNewDriver, 30,
					"Timed out to find the section with title 'Adding Named Driver " + (newDriverIndex) + "'");
			Log.message("The section with title 'Adding Named Driver " + (newDriverIndex+1)+ "' is present", extentReport);
			isOpenedUp = true;
		} catch (TimeoutException toe) {
			Log.message("Timed out to find the section with title 'Adding Named Driver " +( newDriverIndex) + "'",
					extentReport);
		}

		return isOpenedUp;
	}

	/**
	 * Tells whether the Occupation section is in expanded or collapsed state.
	 * 
	 * @param extentReport
	 * @return true if 'Add Occupation' button is visible (i.e., Occupation section
	 *         is opened); false otherwise
	 */
	public boolean isOccupationSectionExpanded(ExtentTest extentReport) {
		boolean isInExpandedState;
		try {
			btnAddOccupation.click();
			Log.message("Clicked on 'Add Occupation' button'", extentReport);
			WaitUtils.waitForelementToBeClickable(driver, btnCancelOccupation,
					"Timed out waiting for 'Cancel' button in 'Add Occupation' section");
			btnCancelOccupation.click();
			Log.message("Clicked on 'Cancel' button in 'Add Occupation' section'", extentReport);
			isInExpandedState = true;
			Log.message("The 'Occupation' section is in expanded state", driver, extentedReport, true);
		} catch (Exception e) {
			isInExpandedState = false;
			Log.message("The 'Occupation' section is not in expanded state", driver, extentedReport, true);
		}
		return isInExpandedState;
	}

	/**
	 * Selects the given occupation in 'Add a New Occupation' section.
	 * 
	 * @param occupation
	 * @param extentedReport
	 * @param screenshot
	 */
	public void selectOccupation(String occupation, ExtentTest extentedReport, boolean screenshot) {
		WaitUtils.waitForElement(driver, drpOccupation);
		Select selOccupation = new Select(drpOccupation);
		selOccupation.selectByVisibleText(occupation);
		Log.message("Selected Occupation : " + occupation + "", driver, extentedReport, screenshot);
	}

	/**
	 * Select the given business in 'Add a New Occupation' section.
	 * 
	 * @param business
	 * @param extentedReport
	 * @param screenshot
	 */
	public void selectBusiness(String business, ExtentTest extentedReport, boolean screenshot) {
		Select selBusiness = new Select(drpBusiness);
		selBusiness.selectByVisibleText(business);
		Log.message("Selected Business : " + business + "", driver, extentedReport, screenshot);
	}

	/**
	 * Select the given employment status in 'Add a New Occupation' section.
	 * 
	 * @param empStatus
	 * @param extentedReport
	 * @param screenshot
	 */
	public void selectEmploymentStatus(String empStatus, ExtentTest extentedReport, boolean screenshot) {
		Select selEmpStatus = new Select(drpEmpStatus);
		selEmpStatus.selectByVisibleText(empStatus);
		Log.message("Selected Employment Status : " + empStatus + "", driver, extentedReport, screenshot);
	}

	/**
	 * Adds an occupation with the given details. Also verifies the added details.
	 * 
	 * @param occupation
	 * @param business
	 * @param empStatus
	 * @param isMainOccupation
	 * @param extentReport
	 * @param screenshot
	 * @return true if occupation is added successfully; false otherwise
	 * @throws InterruptedException
	 */
	public boolean addOccupation(String occupation, String business, String empStatus, boolean isMainOccupation,
			ExtentTest extentReport, boolean screenshot) throws InterruptedException {
		boolean status = true;

		if (isMainOccupation) {
			Thread.sleep(3000); // Mandatory for selecting btnAddOccupation
			if (btnAddOccupation.isDisplayed()) {
				WaitUtils.waitForElement(driver, btnAddOccupation);
				WaitUtils.waitForelementToBeClickable(driver, btnAddOccupation,
						"Add occupation is clickable under occupatin details section");
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", btnAddOccupation);
				WaitUtils.waitForSpinner(driver);
			}
			selectOccupation(occupation, extentReport, screenshot);
			selectBusiness(business, extentReport, screenshot);
			selectEmploymentStatus(empStatus, extentReport, screenshot);
			WaitUtils.waitForElement(driver, radMainOccupationYes);
			radMainOccupationYes.click();
			Log.message("Selected Yes in Main occupation", driver, extentReport);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForElement(driver, btnSaveOccupation);
			btnSaveOccupation.click();
			Log.message("Clicked on Save occupation", driver, extentReport);
			WaitUtils.waitForSpinner(driver);
			return status;

		} else {
			if (btnAddOccupation.isDisplayed()) {
				WaitUtils.waitForElement(driver, btnAddOccupation);
				WaitUtils.waitForelementToBeClickable(driver, btnAddOccupation,
						"Add occupation is clickable under occupatin details section");
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", btnAddOccupation);
				WaitUtils.waitForSpinner(driver);
			}
			selectOccupation(occupation, extentReport, screenshot);
			selectBusiness(business, extentReport, screenshot);
			selectEmploymentStatus(empStatus, extentReport, screenshot);
			WaitUtils.waitForElement(driver, radMainOccupationNo);
			radMainOccupationNo.click();
			Log.message("Selected No in Main occupation", driver, extentReport);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForElement(driver, btnSaveOccupation);
			btnSaveOccupation.click();
			Log.message("Clicked on Save occupation", driver, extentReport);
			WaitUtils.waitForSpinner(driver);
			return status;
		}
	}

	/**
	 * Verifies the adding multiple occupation details.
	 * 
	 * @param occupation
	 * @param business
	 * @param empStatus
	 * @param extentReport
	 * @param screenshot
	 * @return true if the error message is as expected; false otherwise
	 */

	public boolean addingMultipleOccupations(String occupation, String business, String empStatus,
			boolean isMainOccupation, ExtentTest extentReport, boolean screenshot) throws Exception {
		boolean status = true;
		try {
			if (addOccupation(occupation, business, empStatus, true, extentedReport, true))
				Log.message("Main occupation details are added successfully", driver, extentedReport, true);
			else
				Log.message("Not able to add Main occupation details", driver, extentedReport, true);
			WaitUtils.waitForElement(driver, btnYesMoreOccupations);
			btnYesMoreOccupations.click();
			WaitUtils.waitForElement(driver, radMainOccupationYes);
			if (addOccupation(occupation, business, empStatus, true, extentedReport, false))
				Log.message("Other occupation details are added successfully", driver, extentedReport, true);
			else
				Log.message("Unable to add other occupation details", driver, extentedReport, true);
			if (WaitUtils.waitForElement(driver, btnYesMoreOccupations))
				Log.message("Able to add only more than 2 additional occupation on change permitted driver page",
						driver, extentedReport);
			else
				Log.message("Not able to add more than 2 additional occupations on change pemittted driver page",
						driver, extentedReport);
			return status;
		} catch (Exception e) {
			Log.message("Error while adding occupation details : " + e);
			status = false;
			return status;
		}

	}

	/**
	 * Verifies the able to add occupation when No button selected
	 *
	 * @param extentReport
	 * @param screenshot
	 * @return true if the occupation details not displaying; false otherwise
	 */
	public boolean noAnotherOccupation(ExtentTest extentReport, boolean screenshot) throws Exception {
		boolean status = true;
		try {
			WaitUtils.waitForElement(driver, btnYesMoreOccupations);
			btnNoMoreOccupations.click();
			if (WaitUtils.waitForElement(driver, drpOccupation)) {
				Log.message("Able to add another occupation when No button selected", driver, extentedReport, true);
				status = false;
				return status;
			} else
				Log.message("Unable to proceed to add one more occupation when no button selected", driver,
						extentedReport, true);
			return status;
		} catch (Exception e) {
			Log.message("Error while selecting No button for addanother occupation:" + e);
			return status;
		}
	}

	/**
	 * Getting occupation row number in list
	 * 
	 * @param occupation
	 * @ @param
	 *       extentReport
	 * @param screenshot
	 * @return true if the occupation details found; false otherwise
	 */
	public int getOccupationRowno(String occupation, ExtentTest extentReport, boolean screenshot) throws Exception {

		int rowNum = 0;
		try {
			if (occupationName.size() == 0) {
				Log.message("Occupation section not contain saved ocupation records", driver, extentReport);
				return rowNum;
			} else {
				Log.message("Occupation section contains saved occupation records", driver, extentReport);

				for (int rowcount = 0; rowcount <= occupationName.size(); rowcount++) {
					if (occupation.equals(occupationName.get(rowcount).getText())) {
						rowNum = rowcount + 1;
						break;

					} else {
						Log.message(occupation + " : Occupation name not displaying under occupation details section",
								driver, extentReport);
					}
				}
			}
			return rowNum;

		} catch (Exception e) {
			Log.message("Error while verifying the occupation details seciton : " + e);
			return rowNum;
		}

	}

	/**
	 * Editing occupation details which are added
	 * 
	 * @param occupation
	 * @param business
	 * @param empStatus
	 * @param extentReport
	 * @param screenshot
	 *
	 */
	public void editOccupation(String occupation, String business, String empStatus, ExtentTest extentReport,
			boolean screenshot) throws Exception {

		try {
			int rowNum = getOccupationRowno(occupation, extentedReport, true);
			driver.findElement(By.cssSelector(occupationEdit + String.valueOf(rowNum))).click();
			WaitUtils.waitForElement(driver, drpOccupation);
			selectOccupation(occupation, extentedReport, screenshot);
			selectBusiness(business, extentedReport, screenshot);
			selectEmploymentStatus(empStatus, extentedReport, screenshot);
			WaitUtils.waitForElement(driver, btnSaveOccupation);
			btnSaveOccupation.click();
			Log.message("Clicked on Save Occupation", driver, extentedReport);
			WaitUtils.waitForSpinner(driver);
			Log.message("Occupation details are edited successfuly", driver, extentedReport, true);
		} catch (Exception e) {
			Log.message("Getting error while editing occupation details on change permitted driver page : " + e);
		}
	}

	/**
	 * Verifies deleting occupation details which are added
	 * 
	 * @param occupation
	 * @param business
	 * @param empStatus
	 * @param extentReport
	 * @param screenshot
	 *
	 */

	public void deleteOccupation(String occupation, ExtentTest extenteReport, boolean screenshot) throws Exception {
		try {
			int rowNum = getOccupationRowno(occupation, extentedReport, true);
			WaitUtils.waitForElement(driver,
					driver.findElement(By.cssSelector(occupationDelete + String.valueOf(rowNum))));
			driver.findElement(By.cssSelector(occupationDelete + String.valueOf(rowNum))).click();
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForElement(driver, btnYesOccupationDelete);
			btnYesOccupationDelete.click();
			Log.message("Clicked on Yes Occupation", driver, extentedReport);
			WaitUtils.waitForSpinner(driver);

		} catch (Exception e) {
			Log.message("Error while deleting the existed Occupation details : " + e);
		}
	}

	/**
	 * Verifies the main occupation validation error message.
	 * 
	 * @param occupation
	 * @param business
	 * @param empStatus
	 * @param extentReport
	 * @param screenshot
	 * @return true if the error message is as expected; false otherwise
	 */
	public boolean verifyMainOccupationValidationMessage(String occupation, String business, String empStatus,
			ExtentTest extentReport, boolean screenshot) {
		List<WebElement> lstOccupations = driver.findElements(By.cssSelector(cssAddedOccupations));
		int addedOccupations = lstOccupations.size();
		if (addedOccupations == 0) {
			Log.message("There are no occupations added already", extentReport);
			return false;
		} else if (addedOccupations == 1) {
			GenericUtils.clickElementJS(driver, btnAddOccupationYes);
			Log.message("Clicked on 'Yes' button to add another occupation", extentReport);
		}

		WaitUtils.waitForElementPresent(driver, "xpath", xpathAddNewOccupationTitle, 30,
				"Timed out to find the section with title 'Add a New Occupation'");
		Log.message("The section with title 'Add a New Occupation' is shown", extentReport);
		selectOccupation(occupation, extentReport, screenshot);
		selectBusiness(business, extentReport, screenshot);
		selectEmploymentStatus(empStatus, extentReport, screenshot);
		GenericUtils.clickElementJS(driver, radMainOccupationNo);
		Log.message("Clicked on 'No' button not to mark it as main occupation", driver, extentedReport, screenshot);
		btnSaveOccupation.click();
		Log.message("Clicked on 'Save Occupation' button", driver, extentedReport, screenshot);

		String actualValidationMessage = msgMainOccupationValidationError.getText().trim();
		String expectedValidationMessage = ERR_MSG_MAIN_OCCUPATION_VALIDATION;
		Log.message(
				"Expected Msg : [" + expectedValidationMessage + "]; Actual Msg : [" + actualValidationMessage + "]",
				extentedReport);
		return actualValidationMessage.equals(expectedValidationMessage);
	}

	/**
	 * Verifies the 'No' button functionality of 'Add Occupation' section.
	 * 
	 * @param extentReport
	 * @param screenshot
	 * @return true if 'Add Occupation' section is not shown if 'No' option is
	 *         selected; false otherwise
	 */
	public boolean verifyNoOptionForAddOccupation(ExtentTest extentReport, boolean screenshot) {
		boolean isVerified = false;
		List<WebElement> lstOccupations = driver.findElements(By.cssSelector(cssAddedOccupations));
		int addedOccupations = lstOccupations.size();
		if (addedOccupations == 0) {
			Log.message("There are no occupations added already", extentReport);
			return false;
		} else if (addedOccupations == 1) {
			GenericUtils.clickElementJS(driver, btnAddOccupationNo);
			Log.message("Clicked on 'No' button to add another occupation", extentReport);
		}

		try {
			WaitUtils.waitForelementToBeClickable(driver, btnSaveOccupation,
					"Timed out waiting for 'Save Occupation' button");
			btnSaveOccupation.click();
			isVerified = false;
		} catch (Exception e) {
			isVerified = true;
			Log.message("The section with title 'Add a New Occupation' is not shown", extentReport);
		}
		return isVerified;
	}

	/**
	 * Edits the occupation and verifies the edited data.
	 * 
	 * @param occupation
	 * @param business
	 * @param empStatus
	 * @param isMainOccupation
	 * @param extentReport
	 * @param screenshot
	 * @return true if occupation is edited successfully; false otherwise
	 */
	public boolean editOccupation(String occupation, String business, String empStatus, boolean isMainOccupation,
			ExtentTest extentReport, boolean screenshot) {
		btnEditOccupation.click();
		Log.message("Clicked on 'Edit Occupation' button", driver, extentedReport, screenshot);

		WaitUtils.waitForElementPresent(driver, "xpath", xpathAddNewOccupationTitle, 30,
				"Timed out to find the section with title 'Add a New Occupation'");
		Log.message("The section with title 'Add a New Occupation' is shown", extentReport);
		selectOccupation(occupation, extentReport, screenshot);
		selectBusiness(business, extentReport, screenshot);
		selectEmploymentStatus(empStatus, extentReport, screenshot);
		if (isMainOccupation) {
			GenericUtils.clickElementJS(driver, radMainOccupationYes);
			Log.message("Clicked on 'Yes' button to mark it as main occupation", driver, extentedReport, screenshot);
		} else {
			GenericUtils.clickElementJS(driver, radMainOccupationNo);
			Log.message("Clicked on 'No' button not to mark it as main occupation", driver, extentedReport, screenshot);
		}
		btnSaveOccupation.click();
		Log.message("Clicked on 'Save Occupation' button", driver, extentedReport, screenshot);
		String occupationName = firstOccupationName.getText();
		boolean occupationType = firstOccupationType.getText().equalsIgnoreCase("Yes");
		Log.message("Edited occupation name: [" + occupationName + "]", extentedReport);
		Log.message("Is occupation added as main occupation : [" + occupationType + "]", extentedReport);
		return occupationName.equals(occupation) && occupationType == isMainOccupation;
	}

	/**
	 * Deletes the occupation and verifies the operation.
	 * 
	 * @param extentReport
	 * @param screenshot
	 * @return true if occupation is deleted successfully; false otherwise
	 */
	public boolean deleteOccupation(ExtentTest extentReport, boolean screenshot) {
		List<WebElement> lstOccupations = driver.findElements(By.cssSelector("cssAddedOccupations"));
		int addedOccupationsBeforeDelete = lstOccupations.size();
		Log.message("Total occupations before delete: [" + addedOccupationsBeforeDelete + "]", driver, extentedReport,
				screenshot);
		btnDeleteOccupation.click();
		Log.message("Clicked on 'Delete Occupation' button", driver, extentedReport, screenshot);
		lstOccupations = driver.findElements(By.cssSelector("cssAddedOccupations"));
		int addedOccupationsAfterDelete = lstOccupations.size();
		Log.message("Total occupations after delete : [" + addedOccupationsAfterDelete + "]", driver, extentedReport,
				screenshot);
		return addedOccupationsAfterDelete == (addedOccupationsBeforeDelete - 1);
	}

	/**
	 * Tells whether the 'License Details' section is in expanded or collapsed
	 * state.
	 * 
	 * @param extentReport
	 * @return true if 'License attained date' input field is visible (i.e.,
	 *         'License Details' section is opened); false otherwise
	 */
	public boolean isLicenseDetailsSectionExpanded(ExtentTest extentReport) {
		boolean isInExpandedState;
		try {
			FldLicenseDate.click();
			Log.message("Clicked on 'License attained date' input field", extentReport);
			isInExpandedState = true;
			Log.message("The 'License Details' section is in expanded state", driver, extentedReport, true);
		} catch (Exception e) {
			isInExpandedState = false;
			Log.message("The 'License Details' section is not in expanded state", driver, extentedReport, true);
		}
		return isInExpandedState;
	}

	/**
	 * Tells whether the 'Use of Other Vehicles' section is in expanded or collapsed
	 * state.
	 * 
	 * @param extentReport
	 * @return true if 'How many other cars own' drop-down is visible (i.e., 'Use of
	 *         Other Vehicles' section is opened); false otherwise
	 */
	public boolean isUseOfOtherVehiclesSectionExpanded(ExtentTest extentReport) {
		boolean isInExpandedState;
		try {
			Select selectList = new Select(drpSelectHowManyOwnCars);
			selectList.selectByIndex(0);
			Log.message("Clicked on 'Howmany other cars own' drop-down", extentReport);
			isInExpandedState = true;
			Log.message("The 'Use of Other Vehicles' section is in expanded state", driver, extentedReport, true);
		} catch (Exception e) {
			isInExpandedState = false;
			Log.message("The 'Use of Other Vehicles' section is not in expanded state", driver, extentedReport, true);
		}
		return isInExpandedState;
	}

	/**
	 * Tells whether the 'Use of the Insured Vehicle' section is in expanded or
	 * collapsed state.
	 * 
	 * @param extentReport
	 * @return true if 'Frequency of use' drop-down is visible (i.e., 'Use of the
	 *         Insured Vehicle' section is opened); false otherwise
	 */
	public boolean isUseOfTheInsuredVehicleSectionExpanded(ExtentTest extentReport) {
		boolean isInExpandedState;
		try {
			Select selectList = new Select(drpSelectFrequencyOfUse);
			selectList.selectByIndex(0);
			Log.message("Clicked on 'Howmany other cars own' drop-down", extentReport);
			isInExpandedState = true;
			Log.message("The 'Use of the Insured Vehicle' section is in expanded state", driver, extentedReport, true);
		} catch (Exception e) {
			isInExpandedState = false;
			Log.message("The 'Use of the Insured Vehicle' section is not in expanded state", driver, extentedReport,
					true);
		}
		return isInExpandedState;
	}

	/**
	 * Tells whether the 'Claims' section is in expanded or collapsed state.
	 * 
	 * @param extentReport
	 * @return true if 'Want to add more claim' radio button is visible (i.e.,
	 *         'Claims' section is opened); false otherwise
	 */
	public boolean isClaimsSectionExpanded(ExtentTest extentReport) {
		boolean isInExpandedState;
		try {
			clickYesOrNoToAddClaims("Yes", extentReport, true);
			Log.message("Clicked on 'Yes' radio button in 'Want to add more claim' section", extentReport);
			isInExpandedState = true;
			Log.message("The 'Claims' section is in expanded state", driver, extentedReport, true);
		} catch (Exception e) {
			isInExpandedState = false;
			Log.message("The 'Claims' section is not in expanded state", driver, extentedReport, true);
		}
		return isInExpandedState;
	}

	/**
	 * Selects the given claim type in 'Add a New Claim' section.
	 * 
	 * @param claimType
	 * @param extentedReport
	 * @param screenshot
	 */
	public void selectClaimType(String claimType, ExtentTest extentedReport, boolean screenshot) {
		Select selClaimType = new Select(drpClaimType);
		selClaimType.selectByVisibleText(claimType);
		WaitUtils.waitForSpinner(driver);
		Log.message("Selected Claim Type : " + claimType + "", driver, extentedReport, screenshot);
	}

	/**
	 * Enters the given claim date in 'Add a New Claim' section.
	 * 
	 * @param claimDate
	 * @param extentedReport
	 * @param screenshot
	 */
	public void enterClaimDate(String claimDate, ExtentTest extentedReport, boolean screenshot) {
		txtClaimDate.clear();
		txtClaimDate.sendKeys(claimDate);
		txtClaimDate.click();
		WaitUtils.waitForSpinner(driver);
		Log.message("Entered Claim Date : " + claimDate + "", driver, extentedReport, screenshot);
	}

	/**
	 * Enters the given claim cost to driver in 'Add a New Claim' section.
	 * 
	 * @param claimCostDriver
	 * @param extentedReport
	 * @param screenshot
	 */
	public void enterClaimCostToDriver(String claimCostDriver, ExtentTest extentedReport, boolean screenshot) {
		txtClaimCostDriver.clear();
		WaitUtils.waitForSpinner(driver);
		txtClaimCostDriver.sendKeys(claimCostDriver);
		txtClaimCostDriver.click();
		WaitUtils.waitForSpinner(driver);
		Log.message("Entered Claim Cost to Driver : " + claimCostDriver + "", driver, extentedReport, screenshot);
	}

	/**
	 * Enters the given claim cost to third party in 'Add a New Claim' section.
	 * 
	 * @param claimCostThirdParty
	 * @param extentedReport
	 * @param screenshot
	 */
	public void enterClaimCostToThirdParty(String claimCostThirdParty, ExtentTest extentedReport, boolean screenshot) {
		txtClaimCostThirdParty.clear();
		txtClaimCostThirdParty.sendKeys(claimCostThirdParty);
		txtClaimCostThirdParty.click();
		WaitUtils.waitForSpinner(driver);
		Log.message("Entered Claim Cost to Thrid Party : " + claimCostThirdParty + "", driver, extentedReport,
				screenshot);
	}

	/**
	 * Enters the given injury cost in 'Add a New Claim' section.
	 * 
	 * @param injuryCost
	 * @param extentedReport
	 * @param screenshot
	 */
	public void enterInjuryCost(String injuryCost, ExtentTest extentedReport, boolean screenshot) {
		txtInjuryCost.clear();
		txtInjuryCost.sendKeys(injuryCost);
		txtInjuryCost.click();
		Log.message("Entered Personal Injury Cost : " + injuryCost + "", driver, extentedReport, screenshot);
	}

	/**
	 * Chooses the given option for 'Did the named driver lose their no claims
	 * discount?' in 'Add a New Claim' section.
	 * 
	 * @param driverLostDiscount
	 * @param extentedReport
	 * @param screenshot
	 */
	public void selectDriverLostDiscount(String driverLostDiscount, ExtentTest extentedReport, boolean screenshot) {
		if (driverLostDiscount.equalsIgnoreCase("Yes")) {
			GenericUtils.clickElementJS(driver, radDriverLostDiscountYes);
			WaitUtils.waitForSpinner(driver);
			Log.message(
					"Clicked on 'Yes' radio button for 'Did the named driver lose their no claims discount?' in add more claim",
					driver, extentedReport, screenshot);
		} else if (driverLostDiscount.equalsIgnoreCase("No")) {
			GenericUtils.clickElementJS(driver, radDriverLostDiscountNo);
			WaitUtils.waitForSpinner(driver);
			Log.message(
					"Clicked on 'No' radio button for 'Did the named driver lose their no claims discount?' in add more claim",
					driver, extentedReport, screenshot);
		}
	}

	/**
	 * Chooses the given option for 'Was the named driver at fault?' in 'Add a New
	 * Claim' section.
	 * 
	 * @param driverAtFault
	 * @param extentedReport
	 * @param screenshot
	 */
	public void selectDriverAtFault(String driverAtFault, ExtentTest extentedReport, boolean screenshot) {
		if (driverAtFault.equalsIgnoreCase("Yes")) {
			GenericUtils.clickElementJS(driver, radDriverAtFaultYes);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Yes' radio button for 'Was the named driver at fault?' in add more claim", driver,
					extentedReport, screenshot);
		} else if (driverAtFault.equalsIgnoreCase("No")) {
			GenericUtils.clickElementJS(driver, radDriverAtFaultNo);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'No' radio button for 'Was the named driver at fault?' in add more claim", driver,
					extentedReport, screenshot);
		}
	}

	/**
	 * Chooses the given option for 'Was the claim settled?' in 'Add a New Claim'
	 * section.
	 * 
	 * @param claimSettled
	 * @param extentedReport
	 * @param screenshot
	 */
	public void selectClaimSettled(String claimSettled, ExtentTest extentedReport, boolean screenshot) {
		if (claimSettled.equalsIgnoreCase("Yes")) {
			GenericUtils.clickElementJS(driver, radClaimSettledYes);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Yes' radio button for 'Was the claim settled?' in add more claim", driver,
					extentedReport, screenshot);
		} else if (claimSettled.equalsIgnoreCase("No")) {
			GenericUtils.clickElementJS(driver, radClaimSettledNo);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'No' radio button for 'Was the claim settled?' in add more claim", driver,
					extentedReport, screenshot);
		}
	}

	/**
	 * Chooses the given option for 'Was the claim made on this insurance policy?'
	 * in 'Add a New Claim' section.
	 * 
	 * @param claimMadeOnThisPolicy
	 * @param extentedReport
	 * @param screenshot
	 */
	public void selectClaimMadeOnThisPolicy(String claimMadeOnThisPolicy, ExtentTest extentedReport,
			boolean screenshot) {
		if (claimMadeOnThisPolicy.equalsIgnoreCase("Yes")) {
			GenericUtils.clickElementJS(driver, radClaimMadeOnThisPolicyYes);
			WaitUtils.waitForSpinner(driver);
			Log.message(
					"Clicked on 'Yes' radio button for 'Was the claim made on this insurance policy?' in add more claim",
					driver, extentedReport, screenshot);
		} else if (claimMadeOnThisPolicy.equalsIgnoreCase("No")) {
			GenericUtils.clickElementJS(driver, radClaimMadeOnThisPolicyNo);
			WaitUtils.waitForSpinner(driver);
			Log.message(
					"Clicked on 'No' radio button for 'Was the claim made on this insurance policy?' in add more claim",
					driver, extentedReport, screenshot);
		}
	}

	/**
	 * Adds a claim with given details and verifies the addition.
	 * 
	 * @param claimType
	 * @param claimDate
	 * @param claimCostDriver
	 * @param claimCostThirdParty
	 * @param injuryCost
	 * @param driverLostDiscount
	 * @param driverAtFault
	 * @param claimSettled
	 * @param claimMadeOnThisPolicy
	 * @param extentReport
	 * @param screenshot
	 * @return true if claim is added successfully; false otherwise
	 */
	public boolean addClaim(String claimType, String claimDate, String claimCostDriver, String claimCostThirdParty,
			String injuryCost, String driverLostDiscount, String driverAtFault, String claimSettled,
			String claimMadeOnThisPolicy, ExtentTest extentReport, boolean screenshot) {
		GenericUtils.clickElementJS(driver, radAddMoreClaimYes);
		Log.message("Clicked on 'Yes' button to add more claim", extentReport);
		WaitUtils.waitForSpinner(driver);
		selectClaimType(claimType, extentReport, screenshot);
		enterClaimDate(claimDate, extentReport, screenshot);
		enterClaimCostToDriver(claimCostDriver, extentReport, screenshot);
		enterClaimCostToThirdParty(claimCostThirdParty, extentReport, screenshot);
		selectDriverLostDiscount(driverLostDiscount, extentReport, screenshot);
		selectDriverAtFault(driverAtFault, extentReport, screenshot);
		selectClaimSettled(claimSettled, extentReport, screenshot);
		selectClaimMadeOnThisPolicy(claimMadeOnThisPolicy, extentReport, screenshot);
		WaitUtils.waitForElement(driver, btnSaveClaim);
		btnSaveClaim.click();
		Log.message("Clicked on 'Save Claim' button", driver, extentedReport, screenshot);
		WaitUtils.waitForSpinner(driver);
		List<WebElement> lstAddedClaimTypes = driver.findElements(By.cssSelector(cssAddedClaimTypes));
		String savedClaimType = lstAddedClaimTypes.get(lstAddedClaimTypes.size() - 1).getText().trim();
		Log.message("Added claim type : [" + savedClaimType + "]", extentedReport);
		return savedClaimType.equals(claimType);
	}

	/**
	 * Adds a claim with given details and verifies the addition.
	 * 
	 * @param claimType
	 * @param claimDate
	 * @param claimCostDriver
	 * @param claimCostThirdParty
	 * @param injuryCost
	 * @param driverLostDiscount
	 * @param driverAtFault
	 * @param claimSettled
	 * @param claimMadeOnThisPolicy
	 * @param extentReport
	 * @param screenshot
	 * @return true if claim is added successfully; false otherwise
	 */

	public boolean addMultipleClaims(String claimType, String claimType2, String claimDate, String claimCostDriver,
			String claimCostThirdParty, String injuryCost, String driverLostDiscount, String driverAtFault,
			String claimSettled, String claimMadeOnThisPolicy, ExtentTest extentReport, boolean screenshot) {
		boolean status = true;
		int multipleCount = 0;

		if (addClaim(claimType, claimDate, claimCostDriver, claimCostThirdParty, injuryCost, driverLostDiscount,
				driverAtFault, claimSettled, claimMadeOnThisPolicy, extentedReport, true))
			multipleCount++;
		WaitUtils.waitForSpinner(driver);
		if (addClaim(claimType2, claimDate, claimCostDriver, claimCostThirdParty, injuryCost, driverLostDiscount,
				driverAtFault, claimSettled, claimMadeOnThisPolicy, extentedReport, true))
			multipleCount++;
		if (multipleCount == 2) {
			Log.message("Able to add multiple claims", driver, extentedReport, true);
			return status;
		} else {
			Log.message("Not able to add multiple claims", driver, extentedReport, true);
			status = false;
			return status;
		}

	}

	/**
	 * Fills all the given claim details in 'Add a New Claim' section and clicks on
	 * Cancel button.
	 * 
	 * @param claimType
	 * @param claimDate
	 * @param claimCostDriver
	 * @param claimCostThirdParty
	 * @param injuryCost
	 * @param driverLostDiscount
	 * @param driverAtFault
	 * @param claimSettled
	 * @param claimMadeOnThisPolicy
	 * @param extentReport
	 * @param screenshot
	 * @return true if claim is not added; false otherwise
	 */
	public boolean fillClaimDetailsAndCancel(String claimType, String claimDate, String claimCostDriver,
			String claimCostThirdParty, String injuryCost, String driverLostDiscount, String driverAtFault,
			String claimSettled, String claimMadeOnThisPolicy, ExtentTest extentReport, boolean screenshot) {
		int addedClaimTypesBeforeCancel = driver.findElements(By.cssSelector(cssAddedClaimTypes)).size();
		Log.message("Added claim types before cancel: [" + addedClaimTypesBeforeCancel + "]", extentedReport);
		GenericUtils.clickElementJS(driver, radAddMoreClaimYes);
		WaitUtils.waitForSpinner(driver);
		Log.message("Clicked on 'Yes' button to add more claim", extentReport);
		selectClaimType(claimType, extentReport, screenshot);
		enterClaimDate(claimDate, extentReport, screenshot);
		enterClaimCostToDriver(claimCostDriver, extentReport, screenshot);
		enterClaimCostToThirdParty(claimCostThirdParty, extentReport, screenshot);
		// enterInjuryCost(injuryCost, extentReport, screenshot);
		selectDriverLostDiscount(driverLostDiscount, extentReport, screenshot);
		selectDriverAtFault(driverAtFault, extentReport, screenshot);
		selectClaimSettled(claimSettled, extentReport, screenshot);
		selectClaimMadeOnThisPolicy(claimMadeOnThisPolicy, extentReport, screenshot);
		btnCancelClaim.click();
		Log.message("Clicked on 'Cancel' button", driver, extentedReport, screenshot);
		WaitUtils.waitForSpinner(driver);

		int addedClaimTypesAfterCancel = driver.findElements(By.cssSelector(cssAddedClaimTypes)).size();
		Log.message("Added claim types after cancel : [" + addedClaimTypesAfterCancel + "]", extentedReport);
		return addedClaimTypesAfterCancel == addedClaimTypesBeforeCancel;
	}

	/**
	 * Edits the claim and verifies the edited data.
	 * 
	 * @param claimType
	 * @param claimDate
	 * @param claimCostDriver
	 * @param claimCostThirdParty
	 * @param injuryCost
	 * @param driverLostDiscount
	 * @param driverAtFault
	 * @param claimSettled
	 * @param claimMadeOnThisPolicy
	 * @param extentReport
	 * @param screenshot
	 * @return true if edit is successful; false otherwise
	 */
	public boolean editClaim(String claimType, String claimDate, String claimCostDriver, String claimCostThirdParty,
			String injuryCost, String driverLostDiscount, String driverAtFault, String claimSettled,
			String claimMadeOnThisPolicy, ExtentTest extentReport, boolean screenshot) {
		GenericUtils.clickElementJS(driver, btnEditClaim);
		WaitUtils.waitForSpinner(driver);
		selectClaimType(claimType, extentReport, screenshot);
		enterClaimDate(claimDate, extentReport, screenshot);
		enterClaimCostToDriver(claimCostDriver, extentReport, screenshot);
		enterClaimCostToThirdParty(claimCostThirdParty, extentReport, screenshot);
		// enterInjuryCost(injuryCost, extentReport, screenshot);
		selectDriverLostDiscount(driverLostDiscount, extentReport, screenshot);
		selectDriverAtFault(driverAtFault, extentReport, screenshot);
		selectClaimSettled(claimSettled, extentReport, screenshot);
		selectClaimMadeOnThisPolicy(claimMadeOnThisPolicy, extentReport, screenshot);
		btnSaveClaim.click();
		Log.message("Clicked on 'Save Claim' button", driver, extentedReport, screenshot);
		WaitUtils.waitForSpinner(driver);

		List<WebElement> lstAddedClaimTypes = driver.findElements(By.cssSelector(cssAddedClaimTypes));
		String editedClaimType = lstAddedClaimTypes.get(0).getText().trim();
		Log.message("Edited claim type : [" + editedClaimType + "]", extentedReport);
		return editedClaimType.equals(claimType);
	}

	/**
	 * Deletes the claim and verifies it.
	 * 
	 * @param extentReport
	 * @param screenshot
	 * @return true if claim is deleted successfully; false otherwise
	 */
	public boolean deleteClaim(ExtentTest extentReport, boolean screenshot) {
		List<WebElement> lstAddedClaimTypes = driver.findElements(By.cssSelector(cssAddedClaimTypes));
		int addedClaimsBeforeDelete = lstAddedClaimTypes.size();
		Log.message("Total claims before delete : [" + addedClaimsBeforeDelete + "]", driver, extentedReport,
				screenshot);
		btnDeleteClaim.click();
		WaitUtils.waitForSpinner(driver);
		WaitUtils.waitForElement(driver, btnYesConfirmDelete);
		btnYesConfirmDelete.click();
		WaitUtils.waitForSpinner(driver);
		Log.message("Clicked on 'Delete Claim' button", driver, extentedReport, screenshot);
		lstAddedClaimTypes = driver.findElements(By.cssSelector(cssAddedClaimTypes));
		int addedClaimsAfterDelete = lstAddedClaimTypes.size();
		Log.message("Total claims after delete : [" + addedClaimsAfterDelete + "]", driver, extentedReport, screenshot);
		return addedClaimsAfterDelete == (addedClaimsAfterDelete - 1);
	}

	/**
	 * Chooses the option for 'Do you want to add any more convictions?' in
	 * 'Convictions' section.
	 * 
	 * @param option
	 * @param extentReport
	 * @param screenshot
	 */
	public void chooseAddMoreConviction(String option, ExtentTest extentReport, boolean screenshot) {
		if (option.equalsIgnoreCase("Yes")) {
			GenericUtils.clickElementJS(driver, radAddNewConvictionYes);
			Log.message("Clicked on 'Yes' radio button to add more conviction", driver, extentedReport, screenshot);
		} else if (option.equalsIgnoreCase("No")) {
			GenericUtils.clickElementJS(driver, radAddNewConvictionNo);
			Log.message("Clicked on 'No' radio button not to add more conviction", driver, extentedReport, screenshot);
		}
	}

	/**
	 * Tells whether the 'Convictions' section is in expanded or collapsed state.
	 * 
	 * @param extentReport
	 * @return true if 'Yes' radio button to add more conviction is visible (i.e.,
	 *         'Convictions' section is opened); false otherwise
	 */
	public boolean isConvictionsSectionExpanded(ExtentTest extentReport) {
		boolean isInExpandedState;
		try {
			chooseAddMoreConviction("Yes", extentReport, true);
			isInExpandedState = true;
			Log.message("The 'Convictions' section is in expanded state", driver, extentedReport, true);
		} catch (Exception e) {
			isInExpandedState = false;
			Log.message("The 'Convictions' section is not in expanded state", driver, extentedReport, true);
		}
		return isInExpandedState;
	}

	/**
	 * Chooses 'Is Motor Prosecution Pending' in 'Add a New Conviction' section.
	 * 
	 * @param claimType
	 * @param extentedReport
	 * @param screenshot
	 */
	public void selectIsMotorProsecutionPending(String isMotorProsecutionPending, ExtentTest extentedReport,
			boolean screenshot) {
		WaitUtils.waitForSpinner(driver);
		chkNoIsMotorProsecutionPending.click();
		Log.message("Selected Is Motor Prosecution Pending : " + isMotorProsecutionPending + "", driver, extentedReport,
				screenshot);
	}

	/**
	 * Enters the given conviction date in 'Add a New Conviction' section.
	 * 
	 * @param convictionDate
	 * @param extentedReport
	 * @param screenshot
	 */
	public void enterConvictionDate(String convictionDate, ExtentTest extentedReport, boolean screenshot) {
		txtConvictionDate.clear();
		txtConvictionDate.sendKeys(convictionDate);
		txtConvictionDate.click();
		WaitUtils.waitForSpinner(driver);
		Log.message("Entered Conviction Date : " + convictionDate + "", driver, extentedReport, screenshot);
	}

	/**
	 * Enters the given fine amount in 'Add new conviction' section.
	 * 
	 * @param fineAmount
	 * @param extentedReport
	 * @param screenshot
	 */
	public void enterFineAmount(String fineAmount, ExtentTest extentedReport, boolean screenshot) {
		WaitUtils.waitForElement(driver, txtFineAmount);
		txtFineAmount.sendKeys(fineAmount);
		Log.message("Fine amount entered as :" + fineAmount, driver, extentedReport, screenshot);
	}

	/**
	 * Chooses 'Conviction Code' in 'Add a New Conviction' section.
	 * 
	 * @param convictionCode
	 * @param extentedReport
	 * @param screenshot
	 */
	public void selectConvictionCode(String convictionCode, ExtentTest extentedReport, boolean screenshot) {
		Select selConvictionCode = new Select(drpConvictionCode);
		selConvictionCode.selectByVisibleText(convictionCode);
		WaitUtils.waitForSpinner(driver);
		Log.message("Selected Conviction Code : " + convictionCode + "", driver, extentedReport, screenshot);
	}

	/**
	 * Chooses 'Penalty Points' in 'Add a New Conviction' section.
	 * 
	 * @param penaltyPoints
	 * @param extentedReport
	 * @param screenshot
	 */
	public void selectPenaltyPoints(String penaltyPoints, ExtentTest extentedReport, boolean screenshot) {
		Select selPenaltyPoints = new Select(drpPenaltyPoints);
		selPenaltyPoints.selectByVisibleText(penaltyPoints);
		Log.message("Selected Penalty Points : " + penaltyPoints + "", driver, extentedReport, screenshot);
	}

	/**
	 * Chooses the option for 'Was the named driver suspended from driving?' in 'Add
	 * a New Conviction' section.
	 * 
	 * @param wasDriverSuspended
	 * @param extentReport
	 * @param screenshot
	 */
	public void chooseDriverSuspendedOption(String wasDriverSuspended, ExtentTest extentReport, boolean screenshot) {
		if (wasDriverSuspended.equalsIgnoreCase("Yes")) {
			GenericUtils.clickElementJS(driver, radDriverSuspendedYes);
			Log.message("Clicked on 'Yes' radio button for 'Was the named driver suspended from driving?'", driver,
					extentedReport, screenshot);
			WaitUtils.waitForSpinner(driver);
		} else if (wasDriverSuspended.equalsIgnoreCase("No")) {
			GenericUtils.clickElementJS(driver, radDriverSuspendedNo);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'No' radio button for 'Was the named driver suspended from driving?'", driver,
					extentedReport, screenshot);
		}
	}

	/**
	 * Enters the given disqualification period in 'Add a New Conviction' section.
	 * 
	 * @param disqualificationPeriod
	 * @param extentedReport
	 * @param screenshot
	 */
	public void enterDisqulificationPeriod(String disqualificationPeriod, ExtentTest extentedReport,
			boolean screenshot) {
		txtDisqualificationPeriod.clear();
		txtDisqualificationPeriod.sendKeys(disqualificationPeriod);
		txtDisqualificationPeriod.click();
		Log.message("Entered Disqualification Period : " + disqualificationPeriod + "", driver, extentedReport,
				screenshot);
	}

	/**
	 * Enters the given alchohol reading in 'Add a New Conviction' section.
	 * 
	 * @param alchoholReading
	 * @param extentedReport
	 * @param screenshot
	 */
	public void enterAlchoholReading(String alchoholReading, ExtentTest extentedReport, boolean screenshot) {
		txtAlchoholReading.clear();
		txtAlchoholReading.sendKeys(alchoholReading);
		txtAlchoholReading.click();
		Log.message("Entered Alchohol Reading : " + alchoholReading + "", driver, extentedReport, screenshot);
	}

	/**
	 * Enters the given alcohol reading type in 'Add a New Conviction' section.
	 * 
	 * @param alchoholReadingType
	 * @param extentedReport
	 * @param screenshot
	 */
	public void enterAlchoholReadingType(String alchoholReadingType, ExtentTest extentedReport, boolean screenshot) {
		txtAlchoholReadingType.clear();
		txtAlchoholReadingType.sendKeys(alchoholReadingType);
		txtAlchoholReadingType.click();
		Log.message("Entered Alchohol Reading Type : " + alchoholReadingType + "", driver, extentedReport, screenshot);
	}

	/**
	 * Chooses 'Is Conviction Linked To Claim' in 'Add a New Conviction' section.
	 * 
	 * @param isConvictionLinkedToClaim
	 * @param extentedReport
	 * @param screenshot
	 */
	public void selectIsConvictionLinkedToClaim(String isConvictionLinkedToClaim, ExtentTest extentedReport,
			boolean screenshot) {
		Select selPenaltyPoints = new Select(drpIsConvictionLinkedToClaim);
		selPenaltyPoints.selectByVisibleText(isConvictionLinkedToClaim);
		Log.message("Selected Is Conviction Linked To Claim : " + isConvictionLinkedToClaim + "", driver,
				extentedReport, screenshot);
	}

	/**
	 * Chooses 'Choose Claim' in 'Add a New Conviction' section.
	 * 
	 * @param chooseClaim
	 * @param extentedReport
	 * @param screenshot
	 */
	public void selectChooseClaim(String chooseClaim, ExtentTest extentedReport, boolean screenshot) {
		Select selPenaltyPoints = new Select(drpChooseClaim);
		selPenaltyPoints.selectByVisibleText(chooseClaim);
		Log.message("Selected Choose Claim : " + chooseClaim + "", driver, extentedReport, screenshot);
	}

	/**
	 * Adds a new conviction and verifies the addition.
	 * 
	 * @param isMotorProsecutionPending
	 * @param convictionDate
	 * @param convictionCode
	 * @param penaltyPoints
	 * @param wasDriverSuspended
	 * @param disqualificationPeriod
	 * @param alchoholReading
	 * @param alchoholReadingType
	 * @param isConvictionLinkedToClaim
	 * @param chooseClaim
	 * @param extentReport
	 * @param screenshot
	 * @return true if conviction is added successfully; false otherwise
	 */
	public boolean addConviction(String isMotorProsecutionPending, String convictionDate, String convictionCode,
			String penaltyPoints, String wasDriverSuspended, String isConvictionLinkedToClaim, String chooseClaim,
			ExtentTest extentReport, boolean screenshot) {
		GenericUtils.clickElementJS(driver, radAddNewConvictionYes);
		Log.message("Clicked on 'Yes' button to add new conviction", extentReport);
		selectIsMotorProsecutionPending(isMotorProsecutionPending, extentReport, screenshot);
		enterConvictionDate(convictionDate, extentReport, screenshot);
		selectConvictionCode(convictionCode, extentReport, screenshot);
		selectPenaltyPoints(penaltyPoints, extentReport, screenshot);
		chooseDriverSuspendedOption(wasDriverSuspended, extentReport, screenshot);
		btnSaveConviction.click();
		Log.message("Clicked on 'Save Conviction' button", driver, extentedReport, screenshot);
		WaitUtils.waitForSpinner(driver);

		List<WebElement> lstAddedConvictionCodes = driver.findElements(By.cssSelector(cssAddedConvictionCodes));
		String savedConvictionCode = lstAddedConvictionCodes.get(lstAddedConvictionCodes.size() - 1).getText().trim();
		Log.message("Added conviction code : [" + savedConvictionCode + "]", extentedReport);
		return savedConvictionCode.equals(convictionCode);
	}

	/**
	 * Adds a new conviction and verifies the addition.
	 * 
	 * @param isMotorProsecutionPending
	 * @param convictionDate
	 * @param convictionCode
	 * @param penaltyPoints
	 * @param wasDriverSuspended
	 * @param disqualificationPeriod
	 * @param alchoholReading
	 * @param alchoholReadingType
	 * @param isConvictionLinkedToClaim
	 * @param chooseClaim
	 * @param extentReport
	 * @param screenshot
	 * @return true if conviction is added successfully; false otherwise
	 */

	public boolean addMultipleConvictions(String isMotorProsecutionPending, String convictionDate,
			String convictionCode, String convictionCode2, String penaltyPoints, String wasDriverSuspended,
			String isConvictionLinkedToClaim, String chooseClaim, ExtentTest extentReport, boolean screenshot) {
		boolean status = true;
		int multipleCount = 0;

		if (addConviction(isMotorProsecutionPending, convictionDate, convictionCode, penaltyPoints, wasDriverSuspended,
				isConvictionLinkedToClaim, chooseClaim, extentedReport, true))
			multipleCount++;
		WaitUtils.waitForSpinner(driver);
		if (addConviction(isMotorProsecutionPending, convictionDate, convictionCode2, penaltyPoints, wasDriverSuspended,
				isConvictionLinkedToClaim, chooseClaim, extentedReport, true))
			multipleCount++;
		if (multipleCount == 2) {
			Log.message("Able to add multiple convictions", driver, extentedReport, true);
			return status;
		} else {
			Log.message("Not able to add multiple convictions", driver, extentedReport, true);
			status = false;
			return status;
		}

	}

	/**
	 * Verifies that the section with title 'Add a New Conviction' is not shown when
	 * selecting 'No' option in 'Do you want to add any more convictions?'.
	 * 
	 * @param extentReport
	 * @param screenshot
	 * @return
	 */
	public boolean verifyNoOptionOfAddMoreConviction(ExtentTest extentReport, boolean screenshot) {
		GenericUtils.clickElementJS(driver, radAddNewConvictionNo);
		Log.message("Clicked on 'No' button not to add new conviction", extentReport);
		boolean isVerified = false;
		try {
			WaitUtils.waitForElementPresent(driver, "xpath", xpathAddNewConvictionTitle, 30,
					"Timed out to find the section with title 'Add a New Conviction'");
			isVerified = true;
			Log.message("The section with title 'Add a New Conviction' is shown unexpectedly", extentReport);
		} catch (Exception e) {
			isVerified = true;
			Log.message("The section with title 'Add a New Conviction' is not shown", extentReport);
		}
		return isVerified;
	}

	/**
	 * Fills the conviction details in 'Add a New Conviction' section and clicks the
	 * cancel button.
	 * 
	 * @param isMotorProsecutionPending
	 * @param convictionDate
	 * @param convictionCode
	 * @param penaltyPoints
	 * @param wasDriverSuspended
	 * @param disqualificationPeriod
	 * @param alchoholReading
	 * @param alchoholReadingType
	 * @param isConvictionLinkedToClaim
	 * @param chooseClaim
	 * @param extentReport
	 * @param screenshot
	 * @return true if conviction is not added; false otherwise
	 */
	public boolean fillConvictionDetailsAndCancel(String isMotorProsecutionPending, String convictionDate,
			String convictionCode, String penaltyPoints, String wasDriverSuspended, String isConvictionLinkedToClaim,
			String chooseClaim, String fineAmount, ExtentTest extentReport, boolean screenshot) {
		int addedConvictionCodesBeforeCancel = driver.findElements(By.cssSelector(cssAddedClaimTypes)).size();
		Log.message("Added conviction codes before cancel : [" + addedConvictionCodesBeforeCancel + "]",
				extentedReport);

		GenericUtils.clickElementJS(driver, radAddNewConvictionYes);
		Log.message("Clicked on 'Yes' button to add new conviction", extentReport);
		selectIsMotorProsecutionPending(isMotorProsecutionPending, extentReport, screenshot);
		enterConvictionDate(convictionDate, extentReport, screenshot);
		selectConvictionCode(convictionCode, extentReport, screenshot);
		selectPenaltyPoints(penaltyPoints, extentReport, screenshot);
		chooseDriverSuspendedOption(wasDriverSuspended, extentReport, screenshot);
		enterFineAmount(fineAmount, extentedReport, screenshot);
		btnCancelConviction.click();
		Log.message("Clicked on 'Cancel Conviction' button", driver, extentedReport, screenshot);
		WaitUtils.waitForSpinner(driver);

		int addedConvictionCodesAfterCancel = driver.findElements(By.cssSelector(cssAddedConvictionCodes)).size();
		Log.message("Added conviction codes after cancel : [" + addedConvictionCodesAfterCancel + "]", extentedReport);
		return addedConvictionCodesAfterCancel == addedConvictionCodesBeforeCancel;
	}

	/**
	 * Edits the conviction and verifies the edited data.
	 * 
	 * @param isMotorProsecutionPending
	 * @param convictionDate
	 * @param convictionCode
	 * @param penaltyPoints
	 * @param wasDriverSuspended
	 * @param disqualificationPeriod
	 * @param alchoholReading
	 * @param alchoholReadingType
	 * @param isConvictionLinkedToClaim
	 * @param chooseClaim
	 * @param extentReport
	 * @param screenshot
	 * @return true if conviction is added successfully; false otherwise
	 */
	public boolean editConviction(String isMotorProsecutionPending, String convictionDate, String convictionCode,
			String penaltyPoints, String wasDriverSuspended, String isConvictionLinkedToClaim, String chooseClaim,
			ExtentTest extentReport, boolean screenshot) {
		GenericUtils.clickElementJS(driver, btnEditConviction);
		Log.message("Clicked on 'Yes' button to edit conviction", extentReport);
		WaitUtils.waitForSpinner(driver);
		selectIsMotorProsecutionPending(isMotorProsecutionPending, extentReport, screenshot);
		enterConvictionDate(convictionDate, extentReport, screenshot);
		selectConvictionCode(convictionCode, extentReport, screenshot);
		selectPenaltyPoints(penaltyPoints, extentReport, screenshot);
		chooseDriverSuspendedOption(wasDriverSuspended, extentReport, screenshot);
		btnSaveConviction.click();
		Log.message("Clicked on 'Save Conviction' button", driver, extentedReport, screenshot);
		WaitUtils.waitForSpinner(driver);

		List<WebElement> lstAddedConvictionCodes = driver.findElements(By.cssSelector(cssAddedConvictionCodes));
		String editedConvictionCode = lstAddedConvictionCodes.get(lstAddedConvictionCodes.size() - 1).getText().trim();
		Log.message("Edited conviction code : [" + editedConvictionCode + "]", extentedReport);
		return editedConvictionCode.equals(convictionCode);
	}

	/**
	 * Deletes the conviction and verifies it.
	 * 
	 * @param extentReport
	 * @param screenshot
	 * @return true if conviction is deleted successfully; false otherwise
	 */
	public boolean deleteConviction(ExtentTest extentReport, boolean screenshot) {
		List<WebElement> lstAddedConvictionCodes = driver.findElements(By.cssSelector(cssAddedConvictionCodes));
		int addedConvictionsBeforeDelete = lstAddedConvictionCodes.size();
		Log.message("Total convictions before delete: [" + addedConvictionsBeforeDelete + "]", driver, extentedReport,
				screenshot);
		btnDeleteConviction.click();
		WaitUtils.waitForSpinner(driver);
		Log.message("Clicked on 'Delete Conviction' button", driver, extentedReport, screenshot);
		WaitUtils.waitForElement(driver, btnYesconfirmDelete);
		btnYesconfirmDelete.click();
		Log.message("Clicked on 'Yes' button to delete conviction", extentReport);
		WaitUtils.waitForSpinner(driver);
		lstAddedConvictionCodes = driver.findElements(By.cssSelector(cssAddedConvictionCodes));
		int addedConvictionsAfterDelete = lstAddedConvictionCodes.size();
		Log.message("Total convictions after delete : [" + addedConvictionsAfterDelete + "]", driver, extentedReport,
				screenshot);
		return addedConvictionsAfterDelete == (addedConvictionsAfterDelete - 1);
	}

	/**
	 * Chooses the option for 'Do you want to add any more medical conditions?' in
	 * 'Medical & Disability Information' section.
	 * 
	 * @param option
	 * @param extentReport
	 * @param screenshot
	 */
	public void chooseAddMoreMedicalCondition(String option, ExtentTest extentReport, boolean screenshot) {
		if (option.equalsIgnoreCase("Yes")) {
			GenericUtils.clickElementJS(driver, radAddNewMedicalConditionYes);
			Log.message("Clicked on 'Yes' radio button to add more medical condition", driver, extentedReport,
					screenshot);
		} else if (option.equalsIgnoreCase("No")) {
			GenericUtils.clickElementJS(driver, radAddNewMedicalConditionNo);
			Log.message("Clicked on 'No' radio button not to add more medical condition", driver, extentedReport,
					screenshot);
		}
	}

	/**
	 * Tells whether the 'Medical & Disability Information' section is in expanded
	 * or collapsed state.
	 * 
	 * @param extentReport
	 * @return true if 'Yes' radio button to add more medical condition is visible
	 *         (i.e., 'Medical & Disability Information' section is opened); false
	 *         otherwise
	 */
	public boolean isMedicalAndDisabilityInformationSectionExpanded(ExtentTest extentReport) {
		boolean isInExpandedState;
		try {
			chooseAddMoreMedicalCondition("Yes", extentReport, true);
			isInExpandedState = true;
			Log.message("The 'Medical & Disability Information' section is in expanded state", driver, extentedReport,
					true);
		} catch (Exception e) {
			isInExpandedState = false;
			Log.message("The 'Medical & Disability Information' section is not in expanded state", driver,
					extentedReport, true);
		}
		return isInExpandedState;
	}

	/**
	 * Chooses 'Medical Condition' in 'Add a Medical Condition' section.
	 * 
	 * @param medicalCondition
	 * @param extentedReport
	 * @param screenshot
	 */
	public void selectMedicalCondition(String medicalCondition, ExtentTest extentedReport, boolean screenshot) {
		Select selMedicalCondition = new Select(drpMedicalCondition);
		selMedicalCondition.selectByVisibleText(medicalCondition);
		WaitUtils.waitForElement(driver, drpMedicalCondition);
		Log.message("Selected Medical Condition : " + medicalCondition + "", driver, extentedReport, screenshot);
	}

	/**
	 * Enters the given medical condition first diagnosed date in 'Add a Medical
	 * Condition' section.
	 * 
	 * @param medicalConditionFirstDiagnosed
	 * @param extentedReport
	 * @param screenshot
	 */
	public void enterMedicalConditionFirstDiagnosedDate(String medicalConditionFirstDiagnosed,
			ExtentTest extentedReport, boolean screenshot) {
		txtDateConditionFirstDiagnosed.clear();
		WaitUtils.waitForSpinner(driver);
		txtDateConditionFirstDiagnosed.sendKeys(medicalConditionFirstDiagnosed);
		//txtDateConditionFirstDiagnosed.click();
		WaitUtils.waitForSpinner(driver);
		//txtDateconditionlastoccur.click();
		//WaitUtils.waitForSpinner(driver);
		Log.message("Entered Medical Condition First Diagnosed : " + medicalConditionFirstDiagnosed + "", driver,
				extentedReport, screenshot);
	}

	public void enterMedicalConditionLastOccured(String medicalConditionLastOccured,
            ExtentTest extentedReport, boolean screenshot) {
     txtDateConditionLastOccured.clear();
     WaitUtils.waitForSpinner(driver);
     txtDateConditionLastOccured.sendKeys(medicalConditionLastOccured);
     
     WaitUtils.waitForSpinner(driver);
     Log.message("Entered Medical Condition First Diagnosed : " + medicalConditionLastOccured + "", driver,
                  extentedReport, screenshot);
}

	/**
	 * Chooses the option for 'Have the DVLA been informed?' in 'Add a Medical
	 * Condition' section.
	 * 
	 * @param wasDriverSuspended
	 * @param extentReport
	 * @param screenshot
	 */
	public void chooseHaveDVLABeenInformedOption(String haveDVLABeenInformed, ExtentTest extentReport,
			boolean screenshot) {
		if (haveDVLABeenInformed.equalsIgnoreCase("Yes")) {
			GenericUtils.clickElementJS(driver, radHaveDVLABeenInformedYes);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Yes' radio button for 'Was the named driver suspended from driving?'", driver,
					extentedReport, screenshot);
		} else if (haveDVLABeenInformed.equalsIgnoreCase("No")) {
			GenericUtils.clickElementJS(driver, radHaveDVLABeenInformedNo);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'No' radio button for 'Have the DVLA been informed?'", driver, extentedReport,
					screenshot);
		}
	}

	/**
	 * Enters the given additional information in 'Add a Medical Condition' section.
	 * 
	 * @param additionalInformation
	 * @param extentedReport
	 * @param screenshot
	 */
	public void enterAdditionalInformation(String additionalInformation, ExtentTest extentedReport,
			boolean screenshot) {
		txtAdditionalInformation.clear();
		txtAdditionalInformation.sendKeys(additionalInformation);
		txtAdditionalInformation.click();
		Log.message("Entered Additional Information : " + additionalInformation + "", driver, extentedReport,
				screenshot);
	}

	/**
	 * Adds a medical condition and verifies the data.
	 * 
	 * @param medicalCondition
	 * @param medicalConditionFirstDiagnosed
	 * @param haveDVLABeenInformed
	 * @param additionalInformation
	 * @param extentReport
	 * @param screenshot
	 * @return true if medical condition is added successfully; false otherwise
	 */
	public boolean addMedicalCondition(String medicalCondition, String medicalConditionFirstDiagnosed, String medicalConditionLastOccured,
			String haveDVLABeenInformed, ExtentTest extentReport, boolean screenshot) {
		GenericUtils.clickElementJS(driver, radAddNewMedicalConditionYes);
		Log.message("Clicked on 'Yes' button to add a medical condition", extentReport);
		WaitUtils.waitForSpinner(driver);
		selectMedicalCondition(medicalCondition, extentReport, screenshot);
		enterMedicalConditionFirstDiagnosedDate(medicalConditionFirstDiagnosed, extentReport, screenshot);
		enterMedicalConditionLastOccured(medicalConditionLastOccured, extentReport, screenshot);
		chooseHaveDVLABeenInformedOption(haveDVLABeenInformed, extentReport, screenshot);
		GenericUtils.clickElementJS(driver, btnSaveCondition);
		Log.message("Clicked on 'Save Condition' button", driver, extentedReport, screenshot);
		WaitUtils.waitForSpinner(driver);
		List<WebElement> lstAddedMedicalCondition = driver.findElements(By.cssSelector(cssAddedCondition));
		String savedCondition = lstAddedMedicalCondition.get(lstAddedMedicalCondition.size() - 1).getText().trim();
		Log.message("Added condition : [" + savedCondition + "]", extentedReport);
		return savedCondition.equals(medicalCondition);
	}

	/**
	 * Adds a medical condition and verifies the data.
	 * 
	 * @param medicalCondition
	 * @param medicalConditionFirstDiagnosed
	 * @param haveDVLABeenInformed
	 * @param additionalInformation
	 * @param extentReport
	 * @param screenshot
	 * @return true if medical condition is added successfully; false otherwise
	 */

	public boolean addMultipleMedicalConditions(String medicalCondition, String medicalCondition2, String medicalConditionLastOccured,
			String medicalConditionFirstDiagnosed, String haveDVLABeenInformed, ExtentTest extentReport,
			boolean screenshot) {
		boolean status = true;
		int multipleCount = 0;

		if (addMedicalCondition(medicalCondition, medicalConditionFirstDiagnosed, medicalConditionLastOccured, haveDVLABeenInformed, extentedReport,
				true))
			multipleCount++;
		WaitUtils.waitForSpinner(driver);
		if (addMedicalCondition(medicalCondition2, medicalConditionFirstDiagnosed, medicalConditionLastOccured, haveDVLABeenInformed, extentedReport,
				true))
			multipleCount++;
		if (multipleCount == 2) {
			Log.message("Able to add multiple medical conditions ", driver, extentedReport, true);
			return status;
		} else {
			Log.message("Not able to add multiple medical conditions", driver, extentedReport, true);
			status = false;
			return status;
		}

	}

	/**
	 * Verifies that the section with title 'Add a Medical Condition' is not shown
	 * when selecting 'No' option in 'Do you want to add any more medical
	 * conditions?'.
	 * 
	 * @param extentReport
	 * @param screenshot
	 * @return true if 'Add a Medical Condition' is not shown; false otherwise
	 */
	public boolean verifyNoOptionOfAddMedicalCondition(ExtentTest extentReport, boolean screenshot) {
		GenericUtils.clickElementJS(driver, radAddNewMedicalConditionNo);
		Log.message("Clicked on 'No' button not to add a medical condition", extentReport);
		boolean isVerified = false;
		try {
			WaitUtils.waitForElementPresent(driver, "xpath", xpathAddNewMedicalConditionTitle, 30,
					"Timed out to find the section with title 'Add a Medical Condition'");
			isVerified = true;
			Log.message("The section with title 'Add a Medical Condition' is shown unexpectedly", extentReport);
		} catch (Exception e) {
			isVerified = true;
			Log.message("The section with title 'Add a Medical Condition' is shown", extentReport);
		}
		return isVerified;
	}

	/**
	 * Fills the medical condition details in 'Add a Medical Condition' section and
	 * clicks the cancel button.
	 * 
	 * @param medicalCondition
	 * @param medicalConditionFirstDiagnosed
	 * @param haveDVLABeenInformed
	 * @param additionalInformation
	 * @param extentReport
	 * @param screenshot
	 * @return true if medical condition is not added successfully; false otherwise
	 */
	public boolean fillConditionDetailsAndCancel(String medicalCondition, String medicalConditionFirstDiagnosed,
			String haveDVLABeenInformed, ExtentTest extentReport, boolean screenshot) {
		int addedConditionsBeforeCancel = driver.findElements(By.cssSelector(cssAddedCondition)).size();
		Log.message("Added conditions before cancel: [" + addedConditionsBeforeCancel + "]", extentedReport);

		GenericUtils.clickElementJS(driver, radAddNewMedicalConditionYes);
		Log.message("Clicked on 'Yes' button to add a medical condition", extentReport);
		WaitUtils.waitForSpinner(driver);
		selectMedicalCondition(medicalCondition, extentReport, screenshot);
		enterMedicalConditionFirstDiagnosedDate(medicalConditionFirstDiagnosed, extentReport, screenshot);
		chooseHaveDVLABeenInformedOption(haveDVLABeenInformed, extentReport, screenshot);
		GenericUtils.clickElementJS(driver, btnCancelCondition);
		WaitUtils.waitForSpinner(driver);
		Log.message("Clicked on 'Cancel Condition' button", driver, extentedReport, screenshot);
		WaitUtils.waitForElement(driver, radAddNewMedicalConditionYes);
		int addedConditionsAfterCancel = driver.findElements(By.cssSelector(cssAddedCondition)).size();
		Log.message("Added conditions after cancel : [" + addedConditionsAfterCancel + "]", extentedReport);
		return addedConditionsAfterCancel == addedConditionsBeforeCancel;
	}

	/**
	 * Edits a medical condition and verifies the data.
	 * 
	 * @param medicalCondition
	 * @param medicalConditionFirstDiagnosed
	 * @param haveDVLABeenInformed
	 * @param additionalInformation
	 * @param extentReport
	 * @param screenshot
	 * @return true if medical condition is edited successfully; false otherwise
	 */
	public boolean editMedicalCondition(String medicalCondition, String medicalConditionFirstDiagnosed, String medicalConditionLastOccured,
			String haveDVLABeenInformed, ExtentTest extentReport, boolean screenshot) {
		WaitUtils.waitForElement(driver, btnEditCondition);
		GenericUtils.clickElementJS(driver, btnEditCondition);
		WaitUtils.waitForSpinner(driver);
		Log.message("Clicked on 'Yes' button to add a medical condition", extentReport);
		Log.message("The section with title 'Add a Medical Condition' is shown", extentReport);
		selectMedicalCondition(medicalCondition, extentReport, screenshot);
		enterMedicalConditionFirstDiagnosedDate(medicalConditionFirstDiagnosed, extentReport, screenshot);
		enterMedicalConditionLastOccured(medicalConditionLastOccured, extentReport, screenshot);
		chooseHaveDVLABeenInformedOption(haveDVLABeenInformed, extentReport, screenshot);
		GenericUtils.clickElementJS(driver, btnSaveCondition);
		Log.message("Clicked on 'Save Condition' button", driver, extentedReport, screenshot);
		WaitUtils.waitForSpinner(driver);

		List<WebElement> lstAddedMedicalCondition = driver.findElements(By.cssSelector(cssAddedCondition));
		String editedCondition = lstAddedMedicalCondition.get(lstAddedMedicalCondition.size() - 1).getText().trim();
		Log.message("Edited condition : [" + editedCondition + "]", extentedReport);
		return editedCondition.equals(medicalCondition);
	}

	/**
	 * Deletes the medical condition and verifies it.
	 * 
	 * @param extentReport
	 * @param screenshot
	 * @return true if medical condition is deleted successfully; false otherwise
	 */
	public boolean deleteMedicalCondition(ExtentTest extentReport, boolean screenshot) {
		List<WebElement> lstAddedMedicalCondition = driver.findElements(By.cssSelector(cssAddedCondition));
		int addedConditionsBeforeDelete = lstAddedMedicalCondition.size();
		Log.message("Total conditions before delete: [" + addedConditionsBeforeDelete + "]", driver, extentedReport,
				screenshot);
		btnDeleteCondition.click();
		WaitUtils.waitForSpinner(driver);
		Log.message("Clicked on 'Delete Condition' button", driver, extentedReport, screenshot);
		btnConfirmDelete.click();
		WaitUtils.waitForSpinner(driver);
		lstAddedMedicalCondition = driver.findElements(By.cssSelector(cssAddedCondition));
		int addedConditionsAfterDelete = lstAddedMedicalCondition.size();
		Log.message("Total conditions after delete : [" + addedConditionsAfterDelete + "]", driver, extentedReport,
				screenshot);
		return addedConditionsAfterDelete == (addedConditionsBeforeDelete - 1);
	}

	/**
	 * Tells whether the 'Adverse Terms' section is in expanded or collapsed state.
	 * 
	 * @param extentReport
	 * @return true if 'Yes' radio button of 'Has the named driver ever had an
	 *         increased premium applied?' is visible (i.e., 'Adverse Terms' section
	 *         is opened); false otherwise
	 */
	public boolean isAdverseTermsSectionExpanded(ExtentTest extentReport) {
		boolean isInExpandedState;
		try {
			clickYesOrNoIncreasedPremiumApplied("Yes", extentReport, true);
			isInExpandedState = true;
			Log.message("The 'Adverse Terms' section is in expanded state", driver, extentedReport, true);
		} catch (Exception e) {
			isInExpandedState = false;
			Log.message("The 'Adverse Terms' section is not in expanded state", driver, extentedReport, true);
		}
		return isInExpandedState;
	}

	/**
	 * Verify cancel button displaying or not while adding new driver
	 * 
	 * @param extentReport
	 * 
	 */
	public void selectCancelOnContact(ExtentTest extentReport) {

		if (WaitUtils.waitForElement(driver, btnCancelAddingDriver)) {
			WaitUtils.waitForelementToBeClickable(driver, btnCancelAddingDriver, "");
			btnCancelAddingDriver.click();
			Log.message("Cancel button displaying while adding new driver", driver, extentedReport, true);

		} else
			Log.message("Cancel button not displaying while adding new driver", driver, extentedReport, true);
	}

	/**
	 * Verify warning message is displaying as expected when cancel the adding new
	 * driver
	 * 
	 * @param extentReport
	 * 
	 */
	public boolean isWarningMsgOnCntctDelete(ExtentTest extentReport) {
		boolean txtMessage = true;
		WaitUtils.waitForElementPresent(driver, popupDisplay, "Cancel driver pop dispalyed");
		try {
			if ((txtWarningMsg.getText()).equals(TXT_ON_CNCL_CNTCT)) {
				Log.message("Expected warning message displayed on driver cancel pop-up : " + txtWarningMsg.getText(),
						driver, extentedReport, true);
				return txtMessage;

			} else {
				txtMessage = false;
				Log.message("Expected warning message is not displayed on driver cancel pop-up : "
						+ txtWarningMsg.getText(), driver, extentedReport, true);
				return txtMessage;
			}
		} catch (Exception e) {

			txtMessage = false;
			Log.message("unable to get the warning message on driver cancel pop-up", driver, extentReport);
			return txtMessage;

		}
	}

	/**
	 * verify banner title
	 * 
	 * @param extentReport
	 * 
	 */
	public boolean verifyBannerTitle(ExtentTest extentReport) throws Exception {
		boolean status = true;
		try {

			if (titleBanner.getText().contains("YourQuote")) {
				Log.message("Banner title displaying as : " + titleBanner.getText(), extentReport);
				return status;
			} else {
				Log.message("Banner title displaing as expected : " + titleBanner.getText(), extentReport);
				status = false;
				return status;
			}
		} catch (Exception e) {
			status = false;
			Log.message("error while verifying banner title : " + e);
			return status;
		}

	}

	/**
	 * To verify the message
	 * 
	 * @param expectedMessage
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyBlockMessageForDrivers(String expectedMessage, String DriverType, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			boolean status = false;

			switch (DriverType) {

			case "Insured And Spouse":
				if (txtBlockMessageInsuredAndSpouse.getText().trim().contains(expectedMessage)) {
					status = true;
				}
				break;

			case "Insured And Civil Partner":
				if (txtBlockMessageInsuredAndCivilPartner.getText().trim().equals(expectedMessage)) {
					status = true;
				}
				break;

			case "Insured And Named Drivers":
				if (txtBlockMessageInsuredAndNamedDrivers.getText().trim().equals(expectedMessage)) {
					status = true;
				}
				break;
			}
			return status;
		} catch (Exception e) {
			throw new Exception("Error while verifying block message for driver type : " + DriverType + " : " + e);
		}
	}

	/**
	 * To verify yes/no button got disabled
	 * 
	 * @param buttonName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyAddDriverYesOrNoBtnDisabled(String buttonName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			boolean btnSelected = false;
			if (buttonName.equals("Yes")) {
				if (btnRadioYesAddDriver.getAttribute("disabled").equals("true")) {
					btnSelected = true;
				}
			} else {
				if (btnRadioNoAddDriver.getAttribute("disabled").equals("true")) {
					btnSelected = true;
				}
			}
			return btnSelected;
		} catch (Exception e) {
			throw new Exception("Error while verifying : " + buttonName + " button is selected in for Q-Plate : " + e);
		}
	}

}
