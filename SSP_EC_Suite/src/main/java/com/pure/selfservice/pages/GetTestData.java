package com.pure.selfservice.pages;

import java.util.HashMap;

import org.testng.Reporter;
import org.testng.annotations.Listeners;

import com.generic.support.BaseTest;
import com.generic.support.EmailReport;
import com.generic.support.Log;
import com.generic.utils.DataUtils;

/**
 * Get TestData class is used for test data variable declaration globally and
 * use it in synchronized for parallel execution
 */

@Listeners(EmailReport.class)
public class GetTestData extends BaseTest {
	public HashMap<String, String> getTestData(String sheetName, String testcaseId) {
		String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
		String className = sheetName + env;
		return DataUtils.testDatabyID(testcaseId, className);
	}

	public GetTestData(String sheetName, String tcId) throws Exception {
		try {
			HashMap<String, String> testData = getTestData(sheetName, tcId);
			getTestData(testData);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public String emailAddress;
	public String password;
	public String policyName;
	public String typeOfChange;
	public String[] typeOfChangeValue;
	public String vehicleRegNo;
	public String invalidErrorMsg;
	public String description;
	public String SuccessMsg;
	public String Mileage_Pleasure;
	public String Mileage_Business;

	// Initiate
	public String coverType;
	public String[] coverTypeOption;
	public String mtaEffectiveDate;
	public String classOfUse;
	public String postcode;

	// Buy
	public String volantaryexcess;
	public String nameofClass;
	public String[] nameofClassList;
	public String paymentMethod;
	public String addonCover;
	public String[] addonList;
	public String drivingType;
	public String sectionType;
	public String[] sectionTypeList;

	// Ownership Information
	public String keeperOfVehilce;
	public String purchaseMonth;
	public String purchaseYear;

	// Vehicle Details
	public String vehicleValue;
	public String paintworkColor;
	public String paintworkFinish;
	public String odometerReading;

	// location details
	public String overnightVehicleLocation;

	// Card Details
	public String year;
	public String cardHolderName;
	public String cardNumber;

	// Quote Details
	public String voluntaryExcess;
	public String coverName;
	public String[] coverNameList;
	public String title;
	public String foreName;
	public String surname;
	public String dateOfBirth;
	public String dateOfLivingInUk;
	public String gender;
	public String maritalStatus;
	public String relationship;
	public String LicenceDate;
	public String LicenceType;
	public String qualification;
	public String LicenceNumber;
	public String reason;
	public String carCount;
	public String addionalCarCount;
	public String yearCount;
	public String householdCarCount;
	public String frequencyOfUse;
	public String isCommutingPurpose;
	public String isIncreasedPremiumApplied;
	public String isInsuranceRefused;
	public String isSpecialTermsApplied;
	public String hadCriminalConviction;
	public String driverType;
	public String[] driverTypeList;
	public String main_occupation;
	public String main_business;
	public String main_empStatus;
	public String sectionTypeCar;
	public String[] sectionTypeListCar;
	public String makeOfDevice;
	public String deviceModal;
	public String deviceDate;
	public String deviceInstaller;
	public String overnightPostcode;
	public String modificationType;
	public String[] arrModificationType;
	public String overnightLocation;
	public String address;
	public String[] arrAddress;
	public String postCode;
	public String other_empStatus;
	public String medicalCondition;
	public String medicalCondition2;
	public String medicalConditionFirstDiagnosed;
	public String medicalConditionLastOccured;
	public String haveDVLABeenInformed;
	public String claimType;
	public String claimDate;
	public String claimCostDriver;
	public String claimCostThirdParty;
	public String injuryCost;
	public String driverLostDiscount;
	public String driverAtFault;
	public String claimSettled;
	public String claimMadeOnThisPolicy;
	public String[] relationshipList;
	public String claimType2;
	public String isMotorProsecutionPending;
	public String convictionDate;
	public String convictionCode;
	public String penaltyPoints;
	public String wasDriverSuspended;
	public String fineAmount;
	public String isConvictionLinkedToClaim;
	public String chooseClaim;
	public String convictionCode2;
	
	//PB Phase2 Data Capture
	public String rebuildCost;
	public String accidentalDamage;
	public String previousBuildingInsurance;
	public String excessvalue;
	public String noOfYearsWithoutClaimingValue;
	public String nameOfPreviousInsurer;
	public String numberOfPreviousInsurance;
	public String expiryDateOfPreviousInsurance;
	public String payForYourPreviousInsurance;
	public String[] arrPreviousBuildingInsurance;
	public String valueOfHomeContents;
	public String valueOfPreciousOrCostlyItems;
	public String valueOfMostExpensiveItem;
	public String typeOfHomeContentsCoverRequired;
	public String contentsSumRepresentReplacementAmount;
	public String previouslyContentsInsurance;
	public String[] arrpreviouslyContentsInsurance;
	public String cashInYourHomeToBeInsured;
	public String creditCardsToBeInsured;
	public String frozenFoodToBeInsured;
	public String addPersonalPossessionsWithinHome;
	public String addPersonalPossessionsOutsideHome;
	public String itemTypeWH;
	public String[] arritemTypeWH;
	public String itemTypeOH;
	public String[] arritemTypeOH;
	public String itemValueWH;
	public String[] arritemValueWH;
	public String itemValueOH;
	public String[] arritemValueOH;
	public String itemDecsriptionWH;
	public String[] arritemDecsriptionWH;
	public String itemDecsriptionOH;
	public String[] arritemDecsriptionOH;
	public String saveAndCancelWH;
	public String[] arrsaveAndCancelWH;
	public String saveAndCancelOH;
	public String[] arrsaveAndCancelOH;
	public String itemNameWH;
	public String[] arritemNameWH;
	public String itemNameOH;
	public String[] arritemNameOH;
	public String addAnotherItemInWithinHome;
	public String[] arraddAnotherItemInWithinHome;
	public String addAnotherItemInOutsideHome;
	public String[] arraddAnotherItemInOutsideHome;
	public String deleteItemWH;
	public String deleteItemOH;
	public String typeOfCover;
	
	public String addAnotherOccupation;
	public String occupation;
	public String business;
	public String employmentStatus;
	public String mainOccupation;
	public String saveAndCancelO;
	public String occupationType;
	public String[] arroccupationType;
	public String addAnotherPolicyHolderOccupation;
	public String[] arrAddAnotherPolicyHolderOccupation;
	public String policyHolderOccupation;
	public String policyHolderBusiness;
	public String policyHolderEmploymentStatus;
	public String policyHolderMainOccupation;
	public String policyHoldersaveAndCancelO;
	public String policyHolderOccupationType;
	public String postCodeH;
	public String[] arrPostCodeH;
	public String addressH;
	public String[] arrAddressH;
	public String dateOfResidence;
	public String yearOfBuilt;
	public String perOfPropRoof;
	
	//renewals
	public String originalPolicyDate;
	public String renewalPolicyDate;
	public String vehicleRegistrationNumber;
	public String vehicleMatches;
	public String vehicleCCNumber;
	public String vehicleNumberOfDoors;
	public String vehicleBodyType;
	public String vehicleTransmission;
	public String vehicleFuel;
	public String activePremium;
	public String lastAnnualPremium;
	public String thePropertyToBeInsured;
	public String recommendedPremium;
	public String averageNCD;
	public String numberOfYears;
	public String[] arrAverageNCD;
	public String[] arrNumberOfYears;
	public String averageNCDTable;
	public String numberOfYearsTable;
	public String[] arrAverageNCDTable;
	public String[] arrNumberOfYearsTable;
	public String numberOfYearsNoClaimsAccrued;
	public String additionalCostOfNoClaims;
	public String numberOfClaims;
	public String withNCDProtection;
	public String[] arrNumberOfClaims;
	public String[] arrWithNCDProtection;
	public String numberOfClaimsNCD;
	public String withOutNCDProtection;
	public String[] arrNumberOfClaimsNCD;
	public String[] arrWithOutNCDProtection;

	//renewal Payment page
	public String premiumAmount;
	public String premiumFeesAmount;
	public String totalpremiumAmount;
	public String arrTotalpremiumAmount[];
	public String amountOfCreditCost;
	public String costOfCreditAmount;
	public String paidInInstalments;
	public String[] arrpaidInInstalments;
	
	//renewal Payment by Card
	public String cardName;
	public String existingCardNumber;
	public String cardExpiryDate;
	public String addedExtraFeaturesValue;
	public String[] arrAddedExtraFeaturesValue;
	public String addedExtraFeaturesName;
	public String[] arrAddedExtraFeaturesName;
	public String popupMessage;
	public String errorMessage;
	
	//renewal Payment by Premium Finance
	public String accountHolderName;
	public String accountNumber;
	public String sortCode;
	public String bankName;
	public String depositOf;
	public String firstPayment;
	public String followedMonthlyPayment;
	public String transactionFee;
	public String apr;
	public String totalpremiumAmountRenewal;
	
	
	public synchronized void getTestData(HashMap<String, String> testData) {
		emailAddress = testData.get("EmailAddress");
		password = testData.get("Password");
		policyName = testData.get("Policy_Name");
		typeOfChange = testData.get("Type_Of_Change");
		//typeOfChangeValue = typeOfChange.split("\\|");
		vehicleRegNo = testData.get("Vehicle_Registration_Number");
		invalidErrorMsg = testData.get("ExpectedMessage");
		description = testData.get("TC_Description");
		SuccessMsg = testData.get("ExpectedMessage");

		// Initiate
		mtaEffectiveDate = testData.get("MTA_Effective_Date");
		classOfUse = testData.get("Class_Of_Use");
		postcode = testData.get("PostCode");

		// Buy
		volantaryexcess = testData.get("voluntaryExcess");
		paymentMethod = testData.get("Payment_Method");

		// Ownership Information
		drivingType = testData.get("Driving_Type");

		keeperOfVehilce = testData.get("Keeper_Of_Vehicle");
		purchaseMonth = testData.get("Purchase_Month");
		purchaseYear = testData.get("Purchase_Year");

		// Vehicle Details
		vehicleValue = testData.get("Value_Of_Vehicle");
		paintworkColor = testData.get("Paintwork_Color");
		paintworkFinish = testData.get("Paintwork_Finish");
		odometerReading = testData.get("Odometer_Reading");

		// location details
		overnightVehicleLocation = testData.get("Overnight_VehicleLocation");

		// Card Details
		year = testData.get("Year");
		cardHolderName = testData.get("cardHolderName");
		cardNumber = testData.get("cardNumber");

		// Quote Details

		voluntaryExcess = testData.get("voluntaryExcess");
		title = testData.get("Title");
		foreName = testData.get("Forename");
		surname = testData.get("Surname");
		dateOfBirth = testData.get("DOB");
		dateOfLivingInUk = testData.get("Date_Of_Living_InUK");
		gender = testData.get("Gender");
		maritalStatus = testData.get("Marital_Status");
		LicenceDate = testData.get("Licence_Date");
		LicenceType = testData.get("Licence_Type");
		qualification = testData.get("Qualification");
		LicenceNumber = testData.get("Licence_Number");
		reason = testData.get("Reason");
		carCount = testData.get("Car_Count");
		addionalCarCount = testData.get("Additional_Car_Count");
		yearCount = testData.get("Year_Count");
		householdCarCount = testData.get("HouseHold_Car_Count");
		frequencyOfUse = testData.get("Frequency_of_Use");
		isCommutingPurpose = testData.get("IsCommutingPurpose");
		isIncreasedPremiumApplied = testData.get("IsIncreasedPremiunApplied");
		isInsuranceRefused = testData.get("IsInsuranceRefused");
		isSpecialTermsApplied = testData.get("IsSpecialTermsApplied");
		hadCriminalConviction = testData.get("HadCriminalConviction");
		main_occupation = testData.get("Main_Occupation");
		main_business = testData.get("Main_Business");
		main_empStatus = testData.get("Main_Employment_Status");

		// Data capture
		makeOfDevice = testData.get("Make_Of_Device");
		deviceModal = testData.get("Device_Modal");
		deviceDate = testData.get("Device_Date");
		deviceInstaller = testData.get("Device_Installer");
		overnightPostcode = testData.get("Overnight_Postcode");
		overnightLocation = testData.get("Overnight_Location");
		postCode = testData.get("PostCode");
		other_empStatus = testData.get("Other_Employment_Status");

		medicalCondition = testData.get("medicalCondition");
		medicalCondition2 = testData.get("medicalCondition2");
		medicalConditionFirstDiagnosed = testData.get("medicalConditionFirstDiagnosed");
		medicalConditionLastOccured = testData.get("medicalConditionLastOccured");
		haveDVLABeenInformed = testData.get("DVLA");
		claimType2 = testData.get("claimType2");
		claimType = testData.get("claimType");
		claimDate = testData.get("claimDate");
		claimCostDriver = testData.get("claimCostDriver");
		claimCostThirdParty = testData.get("claimCostThirdParty");
		injuryCost = testData.get("injuryCost");
		driverLostDiscount = testData.get("driverLostDiscount");
		driverAtFault = testData.get("driverAtFault");
		claimSettled = testData.get("claimSettled");
		claimMadeOnThisPolicy = testData.get("claimMadeOnThisPolicy");

		isMotorProsecutionPending = testData.get("isMotorProsecutionPending");
		convictionDate = testData.get("convictionDate");
		convictionCode = testData.get("convictionCode");
		penaltyPoints = testData.get("penaltyPoints");
		wasDriverSuspended = testData.get("wasDriverSuspended");
		fineAmount = testData.get("fineAmount");
		isConvictionLinkedToClaim = testData.get("isConvictionLinkedToClaim");
		convictionDate = testData.get("convictionDate");
		chooseClaim = testData.get("chooseClaim");
		convictionCode2 = testData.get("convictionCode2");
		Mileage_Pleasure = testData.get("Mileage_Pleasure");
		Mileage_Business = testData.get("Mileage_Business");
		
		rebuildCost = testData.get("rebuild_Cost");
		accidentalDamage = testData.get("accidental_Damage");
		excessvalue = testData.get("excess_Value");
		noOfYearsWithoutClaimingValue = testData.get("no_Of_Years_Without_Claiming");
		nameOfPreviousInsurer = testData.get("NameOf_Previous_Insurer");
		numberOfPreviousInsurance = testData.get("NumberOf_Previous_Insurance");
		expiryDateOfPreviousInsurance = testData.get("ExpiryDateOf_Previous_Insurance");
		payForYourPreviousInsurance = testData.get("Pay_Your_Previous_Insurance");
		valueOfHomeContents = testData.get("value_Of_Home_Contents");
		valueOfPreciousOrCostlyItems = testData.get("Precious_Or_CostlyItems");
		valueOfMostExpensiveItem = testData.get("Most_Expensive_Item");
		typeOfHomeContentsCoverRequired = testData.get("Home_Contents_Cover_Required");
		contentsSumRepresentReplacementAmount = testData.get("contents_Sum_Replacement_Amount");
		//previouslyHeldHomeContentsInsurance = testData.get("");
		cashInYourHomeToBeInsured = testData.get("cashIn_Your_Home_To_Insured");
		creditCardsToBeInsured = testData.get("creditCards_To_Insured");
		frozenFoodToBeInsured = testData.get("frozenFood_To_Insured");
		addPersonalPossessionsWithinHome = testData.get("add_PersonalPossessions_Within_Home");
		addPersonalPossessionsOutsideHome = testData.get("add_PersonalPossessions_Outside_Home");
		deleteItemWH = testData.get("delete_Item_WH");
		typeOfCover = testData.get("Type_Of_Cover");
		deleteItemOH = testData.get("delete_Item_OH");
		
		addAnotherOccupation = testData.get("add_Another_Occupation");
		occupation = testData.get("occupation");
		business = testData.get("business");
		employmentStatus = testData.get("employment_Status");
		mainOccupation = testData.get("main_Occupation");
		saveAndCancelO = testData.get("save_And_CancelO");
		//occupationType = testData.get("occupation_Type");
		policyHolderOccupation = testData.get("policyHolder_Occupation");
		policyHolderBusiness = testData.get("policyHolder_Business");
		policyHolderEmploymentStatus = testData.get("policyHolder_Employment_Status");
		policyHolderMainOccupation = testData.get("policyHolder_Main_Occupation");
		policyHoldersaveAndCancelO = testData.get("policyHolder_save_And_CancelO");
		policyHolderOccupationType = testData.get("policyHolder_Occupation_Type");
		//postCodeH = testData.get("postCodeH");
		dateOfResidence = testData.get("date_Of_Residence");
		yearOfBuilt = testData.get("year_Of_Built");
		perOfPropRoof = testData.get("per_Of_Prop_Roof");
		
		//renewal
		originalPolicyDate = testData.get("original_Policy_Date");
		renewalPolicyDate = testData.get("renewal_Policy_Date");
		vehicleRegistrationNumber = testData.get("vehicle_Registration_Number");
		vehicleMatches = testData.get("vehicle_Matches");
		vehicleCCNumber = testData.get("vehicle_CC");
		vehicleNumberOfDoors = testData.get("vehicle_Number_Of_Doors");
		vehicleBodyType = testData.get("vehicle_Body_Type");
		vehicleTransmission = testData.get("vehicle_Transmission");
		vehicleFuel = testData.get("vehicle_Fuel");
		activePremium = testData.get("active_Premium");
		lastAnnualPremium = testData.get("last_Annual_Premium");
		thePropertyToBeInsured = testData.get("the_Property_To_Be_Insured");
		recommendedPremium = testData.get("recommended_Premium");
		numberOfYearsNoClaimsAccrued = testData.get("Number_of_years_No_Claims_Accrued");
		additionalCostOfNoClaims = testData.get("Additional_Cost_Of_No_Claims_Protection_On_Your_Policy");
		
		//renewal Payment page
		premiumAmount = testData.get("premium_Amount");
		premiumFeesAmount = testData.get("premium_Fees_Amount");
		//totalpremiumAmount = testData.get("total_premium_Amount");
		amountOfCreditCost = testData.get("amount_Of_Credit_Cost");
		costOfCreditAmount = testData.get("cost_Of_Credit_Amount");
		//paidInInstalments = testData.get("paid_In_Instalments");
		
		//renewal payment by Card
		cardName = testData.get("card_Name");
		existingCardNumber = testData.get("existing_Card_Number");
		cardExpiryDate = testData.get("card_Expiry_Date");
		popupMessage = testData.get("popup_Message");
		errorMessage = testData.get("error_Message");
		
		//Renewal Bank Details
		accountHolderName =testData.get("Account_Holder_Name");
		sortCode = testData.get("Sort_Code");
		accountNumber = testData.get("Account_Number");
		bankName = testData.get("Bank_Name");

		//deposit details
		depositOf = testData.get("Deposit_Of");
		firstPayment =testData.get("First_Payment");
		followedMonthlyPayment = testData.get("Followed_Monthly_Payment");
		transactionFee = testData.get("Transaction_Fee");
		apr = testData.get("APR");
		totalpremiumAmountRenewal = testData.get("total_premium_Amount_Renewal");
		
		try {
			addonCover = testData.get("Add_on_Name");
			addonList = addonCover.split("\\|");
		} catch (Exception e) {
			Log.event("addonCover-->" + e);
		}
		try {
			nameofClass = testData.get("Class_Of_Use");
			nameofClassList = nameofClass.split("\\|");
		} catch (Exception e) {
			Log.event("Name of Class-->" + e);
		}
		try {
			driverType = testData.get("Driver_Type");
			driverTypeList = driverType.split("\\|");
		} catch (Exception e) {
			Log.event("driverType-->" + e);
		}
		try {
			relationship = testData.get("Relationship");
			relationshipList = relationship.split("\\|");
		} catch (Exception e) {
			Log.event("relationship-->" + e);
		}
		try {
			coverType = testData.get("CoverType");
			coverTypeOption = coverType.split("\\|");
		} catch (Exception e) {
			Log.event("coverType-->" + e);
		}
		try {
			sectionType = testData.get("Section_Type").toString();
			sectionTypeList = sectionType.split("\\|");
		} catch (Exception e) {
			Log.event("sectionType-->" + e);
		}
		try {
			sectionTypeCar = testData.get("Section_Type_Car").toString();
			sectionTypeListCar = sectionTypeCar.split("\\|");
		} catch (Exception e) {
			Log.event("sectionType-->" + e);
		}
		try {
			address = testData.get("Addresses");
			arrAddress = address.split("\\|");
		} catch (Exception e) {
			Log.event("address-->" + e);
		}
		try {
			modificationType = testData.get("Modification_Type");
			arrModificationType = modificationType.split(",");
		} catch (Exception e) {
			Log.event("Modification_Type-->" + e);
		}
		try {
			coverName = testData.get("Type_Of_Cover");
			coverNameList = coverName.split("\\|");
		} catch (Exception e) {
			Log.event("Cover_Name -->" + e);
		}
		try {
			previousBuildingInsurance = testData.get("previous_Building_Insurance");
			arrPreviousBuildingInsurance = previousBuildingInsurance.split("\\|");
		} catch (Exception e) {
			Log.event("previousBuildingInsurance -->" + e);
		}
		try {
			previouslyContentsInsurance = testData.get("previously_Contents_Insurance");
			arrpreviouslyContentsInsurance = previouslyContentsInsurance.split("\\|");
		} catch (Exception e) {
			Log.event("previousBuildingInsurance -->" + e);
		}
		try {
			itemTypeWH = testData.get("item_Type_WH");
			arritemTypeWH = itemTypeWH.split("\\|");
		} catch (Exception e) {
			Log.event("itemType -->" + e);
		}
		try {
			itemTypeOH = testData.get("item_Type_OH");
			arritemTypeOH = itemTypeOH.split("\\|");
		} catch (Exception e) {
			Log.event("itemType -->" + e);
		}		
		try {
			itemValueWH = testData.get("item_Value_WH");
			arritemValueWH = itemValueWH.split("\\|");
		} catch (Exception e) {
			Log.event("itemValue -->" + e);
		}
		try {
			itemValueOH = testData.get("item_Value_OH");
			arritemValueOH = itemValueOH.split("\\|");
		} catch (Exception e) {
			Log.event("itemValue -->" + e);
		}
		try {
			itemDecsriptionWH = testData.get("item_Decsription_WH");
			arritemDecsriptionWH = itemDecsriptionWH.split("\\|");
		} catch (Exception e) {
			Log.event("itemDecsription -->" + e);
		}
		try {
			itemDecsriptionOH = testData.get("item_Decsription_OH");
			arritemDecsriptionOH = itemDecsriptionOH.split("\\|");
		} catch (Exception e) {
			Log.event("itemDecsription -->" + e);
		}
		try {
			saveAndCancelWH = testData.get("save_And_Cancel_WH");
			arrsaveAndCancelWH = saveAndCancelWH.split("\\|");
		} catch (Exception e) {
			Log.event("saveAndCancel -->" + e);
		}
		try {
			saveAndCancelOH = testData.get("save_And_Cancel_OH");
			arrsaveAndCancelOH = saveAndCancelOH.split("\\|");
		} catch (Exception e) {
			Log.event("saveAndCancel -->" + e);
		}
		try {
			itemNameWH = testData.get("item_Name_WH");
			arritemNameWH = itemNameWH.split("\\|");
		} catch (Exception e) {
			Log.event("ItemName -->" + e);
		}
		try {
			itemNameOH = testData.get("item_Name_OH");
			arritemNameOH = itemNameOH.split("\\|");
		} catch (Exception e) {
			Log.event("ItemName -->" + e);
		}
		try {
			addAnotherItemInWithinHome = testData.get("add_Another_Item_Within_Home");
			arraddAnotherItemInWithinHome = addAnotherItemInWithinHome.split("\\|");
		} catch (Exception e) {
			Log.event("addAnotherItemInWithinHome -->" + e);
		}
		try {
			addAnotherItemInOutsideHome = testData.get("add_Another_Item_Outside_Home");
			arraddAnotherItemInOutsideHome = addAnotherItemInOutsideHome.split("\\|");
		} catch (Exception e) {
			Log.event("addAnotherItemInOutsideHome -->" + e);
		}
		try {
			addAnotherPolicyHolderOccupation = testData.get("add_Another_PolicyHolder_Occupation");
			arrAddAnotherPolicyHolderOccupation = addAnotherPolicyHolderOccupation.split("\\|");
		} catch (Exception e) {
			Log.event("arrAddAnotherOccupation -->" + e);
		}
		try {
			addedExtraFeaturesValue = testData.get("added_Extra_Features_Value");
			arrAddedExtraFeaturesValue = addedExtraFeaturesValue.split("\\|");
		} catch (Exception e) {
			Log.event("arrAddAnotherOccupation -->" + e);
		}
		try {
			addedExtraFeaturesName = testData.get("added_Extra_Features_Name");
			arrAddedExtraFeaturesName= addedExtraFeaturesName.split("\\|");
		} catch (Exception e) {
			Log.event("arrAddAnotherOccupation -->" + e);
		}
		try {
			totalpremiumAmount = testData.get("total_premium_Amount");
			arrTotalpremiumAmount= totalpremiumAmount.split("\\|");
		} catch (Exception e) {
			Log.event("arrAddAnotherOccupation -->" + e);
		}
		try {
			typeOfChange = testData.get("Type_Of_Change");
			typeOfChangeValue = typeOfChange.split("\\|");
		} catch (Exception e) {
			Log.event("typeOfChangeValue -->" + e);
		}
		try {
			addressH = testData.get("AddressesH");
			arrAddressH = addressH.split("\\|");
		} catch (Exception e) {
			Log.event("address-->" + e);
		}
		try {
			postCodeH = testData.get("postCodeH");
			arrPostCodeH = postCodeH.split("\\|");
		} catch (Exception e) {
			Log.event("address-->" + e);
		}
		try {
			averageNCD = testData.get("average_NCD_value");
			arrAverageNCD = averageNCD.split("\\|");
		} catch (Exception e) {
			Log.event("arrAverageNCD -->" + e);
		}
		try {
			numberOfYears = testData.get("number_Of_Years");
			arrNumberOfYears = numberOfYears.split("\\|");
		} catch (Exception e) {
			Log.event("arrNumberOfYears -->" + e);
		}
		try {
			numberOfYearsTable = testData.get("number_Of_Years_Table");
			arrNumberOfYearsTable = numberOfYearsTable.split("\\|");
		} catch (Exception e) {
			Log.event("arrNumberOfYearsTable -->" + e);
		}
		try {
			averageNCDTable = testData.get("average_NCD_value_Table");
			arrAverageNCDTable = averageNCDTable.split("\\|");
		} catch (Exception e) {
			Log.event("arrAverageNCDTable -->" + e);
		}
		try {
			numberOfClaims = testData.get("number_Of_Claims");
			arrNumberOfClaims = numberOfClaims.split("\\|");
		} catch (Exception e) {
			Log.event("arrNumberOfClaims -->" + e);
		}
		try {
			withNCDProtection = testData.get("with_NCD_Protection");
			arrWithNCDProtection = withNCDProtection.split("\\|");
		} catch (Exception e) {
			Log.event("arrWithNCDProtection -->" + e);
		}
		try {
			numberOfClaimsNCD = testData.get("number_Of_Claims_Table");
			arrNumberOfClaimsNCD = numberOfClaimsNCD.split("\\|");
		} catch (Exception e) {
			Log.event("arrNumberOfClaimsNCD -->" + e);
		}
		try {
			withOutNCDProtection = testData.get("without_NCD_Protection");
			arrWithOutNCDProtection = withOutNCDProtection.split("\\|");
		} catch (Exception e) {
			Log.event("arrWithOutNCDProtection -->" + e);
		}
		try {
			occupationType = testData.get("occupation_Type");
			arroccupationType = occupationType.split("\\|");
		} catch (Exception e) {
			Log.event("arrAddAnotherOccupation -->" + e);
		}
		try {
			paidInInstalments = testData.get("paid_In_Instalments");
			arrpaidInInstalments = paidInInstalments.split("\\|");
		} catch (Exception e) {
			Log.event("address-->" + e);
		}
	}
}
