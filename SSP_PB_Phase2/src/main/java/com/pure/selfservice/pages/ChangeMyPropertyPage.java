package com.pure.selfservice.pages;

import java.util.List;
//import java.util.Random;

//import javax.swing.plaf.synth.SynthSeparatorUI;

//import org.apache.xml.utils.StringComparable;
//import org.hamcrest.core.IsNot;
//import org.hamcrest.core.IsNull;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.generic.support.Log;
import com.generic.utils.ElementLayer;
import com.generic.utils.GenericUtils;
import com.generic.utils.WaitUtils;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * ChangeMyCoverTypePage contains Customer contact details
 * 
 */
public class ChangeMyPropertyPage extends LoadableComponent<ChangeMyPropertyPage> {
	private final WebDriver driver;
	private ExtentTest extentedReport;
	private boolean isPageLoaded;
	public ElementLayer uielement;

	public final String POPUP_MSG_HELPICON_Add_Specified_Items= "I want add a specified item to this policy";
	public final String POPUP_MSG_HELPICON_No_Specified_Items_Added_To_Your_Policy = "You have not defined any specified items in your contents cover";
	public final String cssEditAddedItemInWithinHome = "#C2__C1__C1__C1__BUT_BA2AFC1B42B0F82C365230_R";
	public final String cssDeleteAddedItemInWithinHome = "#C2__C1__C1__C1__BUT_BA2AFC1B42B0F82C365231_R";
	public final String cssEditAddedItemInOutsideHome = "#C2__C1__C1__C1__BUT_BA2AFC1B42B0F82C365268_R";
	public final String cssDeleteAddedItemInOutsideHome = "#C2__C1__C1__C1__BUT_BA2AFC1B42B0F82C365269_R";
	public final String cssEditOccupations = "#C2__C1__BUT_43935BE4AB40B20C844376_R";
	public final String cssDeleteOccupations = "#C2__C1__BUT_43935BE4AB40B20C844380_R";
	public final String cssEditPolicyHolderOccupations = "#C2__C1__BUT_43935BE4AB40B20C1034490_R";
	public final String cssDeletePolicyHolderOccupations = "#C2__C1__BUT_43935BE4AB40B20C1034491_R";
	public final String POPUP_MSG_HELPICON_Title_Your_Property= "This section describes the property you wish to insure";
	public final String POPUP_MSG_HELPICON_Buildings_Contents= "Select to indicate whether buildings and contents cover is required for this property.";
	public final String POPUP_MSG_HELPICON_Buildings= "Select to indicate whether buildings only cover (no contents cover) is required for this property.";
	public final String POPUP_MSG_HELPICON_Contents="Select to indicate whether contents only cover (no buildings cover) is required for this property.";
	public final String POPUP_MSG_HELPICON_Address_of_Property_To_Be_Insured = "Details of the property to be insured.";
	public final String POPUP_MSG_HELPICON_Postcode_Of_Property = "Enter the postcode of the property to be insured.";
	public final String POPUP_MSG_HELPICON_Your_Correspondence_Address = "Details of your current correspondence address.";
	public final String POPUP_MSG_HELPICON_Correspondence_Address_To_Update = "Select to indicate if you want your correspondence address to be updated with the address of the property to be insured.";
	public final String POPUP_MSG_HELPICON_Type_Of_Property = "Select the type of property to be insured.";
	public final String POPUP_MSG_HELPICON_Date_Of_Residence = "Select the date you started residence in the property (For renters please enter the date your rental contract commenced.).";
	public final String POPUP_MSG_HELPICON_Year_Your_Property_Was_Built = "If you are not sure, please enter an approximate year.";
	public final String POPUP_MSG_HELPICON_Number_Of_Bath_Rooms = "Please select the number of rooms in the property with a bath or shower in them, including those that are en suite.";
	public final String POPUP_MSG_HELPICON_Number_Of_Bedrooms = "Please select the number of bedrooms that were intended as bedrooms when the property was built or when/if the property was extended and not only rooms currently used as bedrooms.";
	public final String POPUP_MSG_HELPICON_Type_Of_Wall_Construction = "Select the materials that the walls of the property are constructed with";
	public final String POPUP_MSG_HELPICON_Type_Of_Roof_Construction = "Select the materials that the roof of the property is constructed with.";
	public final String POPUP_MSG_HELPICON_Roof_That_Is_Flat = "Enter the percentage of the property’s roof that is flat.";
	public final String POPUP_MSG_HELPICON_Type_Of_Listed_Building_If_Appropriate = "Select the type of ‘listed building’, if appropriate.";
	public final String POPUP_MSG_HELPICON_Property_Been_Extended = "Has your home been extended, other than for a conservatory, which has resulted in more than a 10% increase in the original floor area?";
	public final String POPUP_MSG_HELPICON_Property_Used_For_Business_Purposes = "Is any form of business operation being carried out from the property?";
	public final String POPUP_MSG_HELPICON_Property_Free_From_Subsidence = "Is the property together with the surrounding area free from signs of damage (e.g. cracking or bulging walls) and/or underpinning or any remedial action in connection with land-slip, heave, subsidence, coastal or river erosion?";
	public final String POPUP_MSG_HELPICON_Property_Free_From_Flooding = "Is the area within 400m of the property free from flooding?";
	public final String POPUP_MSG_HELPICON_Property_Ever_Been_Underpinned= "Select to indicate if the property has been underpinned.";
	public final String POPUP_MSG_HELPICON_Property_Within_400_Metres_Of_Water = "Select to indicate if the property is within 400m of water (river, lake, sea, reservoir, quarry or other excavation)";
	public final String POPUP_MSG_HELPICON_Trees_Taller_Than_5_Metres = "Select to indicate if there any trees or shrubs taller than 5 metres (16ft) within 7 metres (22ft) of your property.";
	public final String POPUP_MSG_HELPICON_Property_In_A_Good_State_Of_Repair = "Is the property in a good state of repair, and to your knowledge free from damage or defect in any way and will it be so maintained?";
	public final String POPUP_MSG_HELPICON_Property_Self_Contained_And_Lockable = "Is the property self-contained with a separate lockable entrance under your sole control?";
	public final String POPUP_MSG_HELPICON_Title_Of_People = "This section describes the people who are the policyholder’s for this policy.";
	public final String POPUP_MSG_HELPICON_Occupation = "Select your occupation from the list of available options.";
	public final String POPUP_MSG_HELPICON_Business = "Select the type of business or trade that you are employed in.";
	public final String POPUP_MSG_HELPICON_Employment_Status ="Please select your employment type from the list of available options. Please remember if you are not employed (e.g. retired) choose the appropriate option that reflects your circumstances."; 
	public final String POPUP_MSG_HELPICON_Main_Occupation = "Select if this is your main occupation.";
	public final String POPUP_MSG_HELPICON_Type_Of_Ownership ="Please select the type of ownership for the property."; 
	public final String POPUP_MSG_HELPICON_Type_Of_Occupancy ="Please select the type of occupancy you have in the property.";
	public final String POPUP_MSG_HELPICON_People_Normally_Live_In_The_Property ="Please select the total number of people living in the property.";
	public final String POPUP_MSG_HELPICON_Children_Under_18_Live_In_The_Property ="Please select the total number of people living in the property who are aged under 18.";
	public final String POPUP_MSG_HELPICON_Property_Occupied_During_The_Day = "Select if the property is unoccupied during the day."; 
	public final String POPUP_MSG_HELPICON_Security_Max_Hours_Home_Unoccupied = "Enter the longest period that the property is likely to remain unoccupied in any one year.";
	public final String POPUP_MSG_HELPICON_Property_Occupied_At_Night = "Select if the property is unoccupied during at night.";     
	public final String POPUP_MSG_HELPICON_Property_Have_An_Alarm_Installed = "Select if the property has an alarm installed.";
	public final String POPUP_MSG_HELPICON_Security_NACOSS_Approved = "The burglar alarms has been fitted by a NSI (formally NACOSS) approved company and/or conforms to British Standard BS4737 or BS6799. Important - the alarm must have a professional maintenance agreement in force to be classified as an approved device.";
	public final String POPUP_MSG_HELPICON_Security_Alarm_Type = "Select the type of security system that has been installed, and is in use at the property.";
	public final String POPUP_MSG_HELPICON_British_Standard_Kitemark = "Main external doors are fitted with 5 Lever Mortice Deadlocks conforming to British Standard BS3621. All accessible windows, patio doors and French/double doors are fitted with key operated locks.";
	public final String POPUP_MSG_HELPICON_Property_Have_External_Security_Lighting = "Select to indicate if external security lights are installed and are in use.";
	public final String POPUP_MSG_HELPICON_Locks_Activated_At_Night = "Select to indicate if all locks are activated at night and when the property is unoccupied.";
	public final String POPUP_MSG_HELPICON_Neighbourhood_Watch_Scheme = "Select to indicate if the property is in an area where a neighbourhood watch or similar police approved scheme operates.";
	public final String POPUP_MSG_HELPICON_Property_Fitted_With_A_Smoke_Detector = "Select to indicate if smoke detector(s) are installed at the property.";
	public final String POPUP_MSG_HELPICON_Safe_Installed_Within_The_Property = "Select to indicate if a safe is installed at the property.";
	public final String POPUP_MSG_HELPICON_Policy_Holder_Occupation = "Select the joint policyholder’s occupation from the list of available options.";
	public final String POPUP_MSG_HELPICON_Policy_Holder_Business = "Select the type of business or trade that the joint policyholder is employed in.";
	public final String POPUP_MSG_HELPICON_Policy_Holder_Employment_Status = "Please select the joint policyholder’s employment type from the list of available options. Please remember if they are not employed (e.g. retired) choose the appropriate option that reflects their circumstances.";
	public final String POPUP_MSG_HELPICON_Policy_Holder_Main_Occupation = "Select if this is the joint policyholder’s main occupation.";

	//Content
	public final String POPUP_MSG_HELPICON_Title_Your_Contents = "This section describes the contents insured in your property.";
	public final String POPUP_MSG_HELPICON_Value_Of_Home_Contents = "The total value of your home contents. The amount required to fully re-instate all the items in your home in the event of an insured loss.";
	public final String POPUP_MSG_HELPICON_Value_Of_Precious_Or_Costly_Items = "How much of the contents sum insured is represented by high risk items to be insured within the home? Include items such as jewellery, watches, furs, photo equipment, items of gold, silver or other precious metals etc.";
	public final String POPUP_MSG_HELPICON_value_Of_Most_Expensive_Item = "What is the value of the most expensive single item to be covered by the policy?";
	public final String POPUP_MSG_HELPICON_Type_Of_Home_Contents_Cover_Required = "What type of contents cover is required including whether accidental damage is to be included.";
	public final String POPUP_MSG_HELPICON_Total_Excess_Required = "Total excess required";
	public final String POPUP_MSG_HELPICON_Contents_Sum_Represent_Replacement_Amount = "Does the sum insured for the contents represent the full replacement value of the items within the home?";
	public final String POPUP_MSG_HELPICON_Previously_Contents_Insurance = "Have you previously held contents insurance?";
	public final String POPUP_MSG_HELPICON_Name_Of_Previous_Insurer = "Previous insurer for contents insurance?";
	public final String POPUP_MSG_HELPICON_Policy_Number_Of_Previous_Insurance = "Previous policy number for contents insurance?";
	public final String POPUP_MSG_HELPICON_Expiry_Date_Of_Previous_Insurance = "Previous policy expiry date for contents insurance";
	public final String POPUP_MSG_HELPICON_Pay_For_Your_Previous_Insurance = "Previous payment frequency for contents insurance?";
	public final String POPUP_MSG_HELPICON_No_Of_Years_Without_Claiming = "The number of consecutive claim free years you have accrued for contents insurance.";
	public final String POPUP_MSG_HELPICON_Add_Contents_Cover = "I want to add contents cover to this policy";
	public final String POPUP_MSG_HELPICON_Cash_In_Your_Home_To_Be_Insured = "Sum insured required for loss of money away from the home. (Note that many policies include an amount for this cover by default)?";
	public final String POPUP_MSG_HELPICON_Credit_Cards_To_Be_Insured = "Sum insured required for loss of credit cards away from the home. (Note that many policies include an amount for this cover by default)?";
	public final String POPUP_MSG_HELPICON_Frozen_Food_To_Be_Insured = "Sum insured required for frozen food cover. (Note that many policies include an amount for this cover by default)?";
	public final String POPUP_MSG_HELPICON_Add_Personal_Possessions_Within_Home = "Indicate if cover is required for specified items while in the home?";
	public final String POPUP_MSG_HELPICON_Add_Another_Item_In_Within_Home = "Select if you want to add another specified item.";
	public final String POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Within_Home = "The type of high risk item to be insured while in the home.";
	public final String POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Within_Home = "Approximate value of the high risk item to be insured while in the home.";
	public final String POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Within_Home = "Description of the high risk item to be insured while in the home.";
	public final String POPUP_MSG_HELPICON_Add_Personal_Possessions_Outside_Home = "Indicate if cover is required for specified items which are taken outside the home?";
	public final String POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Outside_Home = "The type of high risk item to be insured while taken outside the home.";
	public final String POPUP_MSG_HELPICON_Add_Another_Item_In_Outside_Home = "Select if you want to add another specified item.";
	public final String POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Outside_Home = "Approximate value of the high risk item to be insured while taken outside the home.";
	public final String POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Outside_Home = "Description of item";

	//Building
	public final String POPUP_MSG_HELPICON_Rebuild_Cost = "The cost to re-build your property in the event of an insured loss as indicated on a Home Buyer’s Survey or Mortgage Valuation."; 
	public final String POPUP_MSG_HELPICON_Accidental_Damage = "This is an extension on your contents cover to insure you against additional unforeseen accidents.";
	public final String POPUP_MSG_HELPICON_Total_Excess_Required_B = "Enter the total excesses that you are prepared to pay. Note that the figure here is inclusive of any compulsory excesses applied to the policy.";
	public final String POPUP_MSG_HELPICON_Previous_Building_Insurance = "Have you previously held buildings insurance?";
	public final String POPUP_MSG_HELPICON_Name_Of_Previous_Insurer_B = "Previous insurer for buildings insurance?";
	public final String POPUP_MSG_HELPICON_Policy_Number_Of_Previous_Insurance_B = "Previous policy number for buildings insurance?";
	public final String POPUP_MSG_HELPICON_Expiry_Date_Of_Previous_Insurance_B = "Previous policy expiry date for buildings insurance?";
	public final String POPUP_MSG_HELPICON_Pay_For_Your_Previous_Insurance_B = "Previous payment frequency for buildings insurance?";
	public final String POPUP_MSG_HELPICON_No_Of_Years_Without_Claiming_B = "The number of consecutive claim free years you have accrued for buildings insurance.";

	@FindBy(css = "#C2__C1__HEAD_146EFBFB9856737A201280")
	WebElement txtTitleYourProperty;

	@FindBy(css = "#C2__C1__BUT_146EFBFB9856737A276187")
	WebElement buildingsContents;

	@FindBy(css = "#C2__C1__BUT_146EFBFB9856737A276197")
	WebElement buildings;

	@FindBy(css = "#C2__C1__BUT_146EFBFB9856737A276207")
	WebElement contents;

	@FindBy(css = "button[title='Quote']")
	WebElement btnQuote;

	@FindBy(css = "button[class*='btn-choice']")
	List<WebElement> lstChangeType;

	WebElement btnEditAddedItemInWithinHome;

	@FindBy(css = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365231_R")
	WebElement btnDeleteAddedItemInWithinHome;

	@FindBy(css = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365268_R")
	WebElement btnEditAddedItemInOutsideHome;

	@FindBy(css = "#C2__C1__C1__BUT_BA2AFC1B42B0F82C365269_R")
	WebElement btnDeleteAddedItemInOutsideHome;

	@FindBy(css = "button[id='C2__C1__ID-insurence-policy']")
	WebElement btnNextTypeofInsurancePolicy;

	@FindBy(css = "input[id='C2__C1__C1__QUE_CCE7E08A37BF764D13129']")
	WebElement txtPostcodeOfProperty;

	@FindBy(css = "span[id='C2__C1__QUE_A335DC66FD885731254936']")
	WebElement lblAddressAdded;

	@FindBy(css = "button[id='C2__C1__BUT_A335DC66FD885731256428'][title='Change Address']")
	WebElement btnChangeAddress;

	@FindBy(css = "button[title='Find Address']")
	WebElement btnFindAddress;

	@FindBy(css = "select[id='C2__C1__C1__QUE_CCE7E08A37BF764D13131']")
	WebElement selectChooseAddress;

	@FindBy(css = "button[title='Save Address']")
	WebElement btnSaveAddress;

	@FindBy(css = "#C2__C1__C1__BUT_B010FEC6822AF308619365")
	WebElement btnCancelAddress;

	@FindBy(css = "input[id='C2__C1__QUE_A335DC66FD885731267001_0']+span")
	WebElement btnYesforConfirmation;

	@FindBy(css = "input[id='C2__C1__QUE_A335DC66FD885731267001_1']+span")
	WebElement btnNoforConfirmation;

	@FindBy(css = "button[name*='C2__C1____A335DC66FD885731 FormButton 25']")
	WebElement btnYesForOverlay;

	@FindBy(css = "button[name*='C2__C1____A335DC66FD885731 FormButton 26']")
	WebElement btnNoForOverlay;

	@FindBy(css = "select[id='C2__C1__QUE_F6EBE6B19D11AB75789289']")
	WebElement selectTypeOfProperty;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB75789293']")
	WebElement txtDateOfResidence;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB75829350']")
	WebElement txtYearOfBuilt;

	@FindBy(css = "select[id='C2__C1__QUE_F6EBE6B19D11AB75829355']")
	WebElement selectNoOfShower;

	@FindBy(css = "select[id='C2__C1__QUE_F6EBE6B19D11AB75829359']")
	WebElement selectNoOfBed;

	@FindBy(css = "#C2__C1__ID-Property-insured")
	WebElement btnNextYourCorrAddress;

	@FindBy(css = "select[id='C2__C1__QUE_F6EBE6B19D11AB75932886']")	               
	WebElement typeOfWall;

	@FindBy(css = "label[for='C2__C1__QUE_F6EBE6B19D11AB75789289']")	               
	WebElement lblTypeOfWall;

	@FindBy(css = "select[id='C2__C1__QUE_F6EBE6B19D11AB75932890']")
	WebElement typeOfRoof;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751025068']")
	WebElement txtPerOfPropRoof;

	@FindBy(css = "select[id='C2__C1__QUE_F6EBE6B19D11AB751026408']")
	WebElement typeOfListedBuilding;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751026412_0']+span")
	WebElement hasPropExtendedYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751026412_0']")
	WebElement hasPropExtendedYes1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751026412_1']+span")
	WebElement hasPropExtendedNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751026412_1']")
	WebElement hasPropExtendedNo1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751027751_0']+span")	
	WebElement isBusinessPropUsedForBusienssYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751027751_1']+span")
	WebElement isBusinessPropUsedForBusienssNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751027751_1']")
	WebElement isBusinessPropUsedForBusienssNo1;

	@FindBy(css = "input[id='C2__C1__QUE_A335DC66FD885731942432_0']+span")
	WebElement isBusinessPropUsedForClericalOnlyYes;

	@FindBy(css = "input[id='C2__C1__QUE_A335DC66FD885731942432_1']+span")
	WebElement isBusinessPropUsedForClericalOnlyNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751027755_0']+span")	
	WebElement isFreeFromSubsidenceYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751027755_0']")	
	WebElement isFreeFromSubsidenceYes1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751027755_1']+span")
	WebElement isFreeFromSubsidenceNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751027759_0']+span")	
	WebElement isFreeFromFloodingYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751027759_0']")	
	WebElement isFreeFromFloodingYes1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751027759_1']+span")	
	WebElement isFreeFromFloodingNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751027763_0']+span")	
	WebElement hasPropPinnedYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751027763_0']")	
	WebElement hasPropPinnedYes1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751027763_1']+span")	
	WebElement hasPropPinnedNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751029118_0']+span")	
	WebElement isPropWithin400mYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751029118_1']+span")	
	WebElement isPropWithin400mNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751029118_1']")	
	WebElement isPropWithin400mNo1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751030472_0']+span")	
	WebElement isAnyTreesYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751030472_1']+span")	
	WebElement isAnyTreesNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751030472_1']")	
	WebElement isAnyTreesNo1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751030492_0']+span")	
	WebElement isPropGoodStateYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751030492_0']")	
	WebElement isPropGoodStateYes1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751030492_1']+span")	
	WebElement isPropGoodStateNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751031847_0']+span")	
	WebElement isPropSelfContainedYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751031847_0']")	
	WebElement isPropSelfContainedYes1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751031847_1']+span")	
	WebElement isPropSelfContainedNo;

	@FindBy(css = "#C2__C1__ID-building-construct")	
	WebElement btnNextBuildingConst;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751041626_0']+span")	
	WebElement isPropOccupiedInDayYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751041626_1']+span")	
	WebElement isPropOccupiedInDayNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751041626_1']")	
	WebElement isPropOccupiedInDayNo1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751059717']")	
	WebElement txtMaxNoOfDays;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044320_0']+span")	
	WebElement isPropOccupiedInNightYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044320_0']")	
	WebElement isPropOccupiedInNightYes1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044320_1']+span")	
	WebElement isPropOccupiedInNightNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044323_0']+span")	
	WebElement isAlarmInstalledYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044323_1']+span")	
	WebElement isAlarmInstalledNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044323_1']")	
	WebElement isAlarmInstalledNo1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751061072_0']+span")	
	WebElement isNACOSSApprovedYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751061072_0']+span")	
	WebElement isNACOSSApprovedNo;

	@FindBy(css = "select[id='C2__C1__QUE_F6EBE6B19D11AB751061091']")	
	WebElement selectAlarmType;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044326_0']+span")	
	WebElement isBritishKitemarkYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044326_1']+span")	
	WebElement isBritishKitemarkNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044326_1']")	
	WebElement isBritishKitemarkNo1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044329_0']+span")	
	WebElement doesPropertyHaveExternalLightYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044329_1']+span")	
	WebElement doesPropertyHaveExternalLightNo;	

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044329_1']")	
	WebElement doesPropertyHaveExternalLightNo1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044332_0']+span")	
	WebElement areLocksActivatedAtNightYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044332_1']+span")	
	WebElement areLocksActivatedAtNightNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044332_1']")	
	WebElement areLocksActivatedAtNightNo1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044335_0']+span")	
	WebElement areYouANeighbourYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044335_1']+span")	
	WebElement areYouANeighbourNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751044335_1']")	
	WebElement areYouANeighbourNo1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751048441_0']+span")	
	WebElement isPropFittedSmokeYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751048441_1']+span")	
	WebElement isPropFittedSmokeNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751048441_1']")	
	WebElement isPropFittedSmokeNo1;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751048443_0']+span")	
	WebElement isSafeInstalledYes;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751048443_1']+span")	
	WebElement isSafeInstalledNo;

	@FindBy(css = "input[id='C2__C1__QUE_F6EBE6B19D11AB751048443_1']")	
	WebElement isSafeInstalledNo1;

	@FindBy(css = "#C2__C1__ID-security-details")	
	WebElement btnNextSecurityAccrn;

	@FindBy(css = "button[id='C2__C1__BUT_146EFBFB9856737A209704'][title='Continue']")	
	WebElement btnContinue;

	@FindBy(css = "#C2__C1__C2__QUE_17AC82CAB86EFF1C489445") 
	WebElement fldRebuildCost;

	@FindBy(css = "#C2__C1__C2__QUE_17AC82CAB86EFF1C502572")
	WebElement drpdwnTotalExcessRequiredB;

	@FindBy(css = "#C2__C1__C2__QUE_17AC82CAB86EFF1C505489")
	WebElement drpdwnNoOfYearsWithoutClaimingB;

	@FindBy(css = "#C2__C1__C2__QUE_17AC82CAB86EFF1C508403")
	WebElement drpdwnNameOfPreviousInsurerB;

	@FindBy(css = "#C2__C1__C2__QUE_17AC82CAB86EFF1C509860")
	WebElement fldPolicyNumberOfPreviousInsuranceB;

	@FindBy(css = "#C2__C1__C2__QUE_17AC82CAB86EFF1C5098634534")
	WebElement fldExpiryDateOfPreviousInsuranceB;

	@FindBy(css = "#C2__C1__C2__QUE_17AC82CAB86EFF1C509866")
	WebElement drpdwnPayForYourPreviousInsuranceB;

	@FindBy(css = "[id = C2__C1__C2__QUE_17AC82CAB86EFF1C490905_0]+span")
	WebElement btnrequireAccidentalDamageYes;

	@FindBy(css = "[id = C2__C1__C2__QUE_17AC82CAB86EFF1C490905_1]+span")
	WebElement btnrequireAccidentalDamageNo;

	@FindBy(css = "[id = C2__C1__C2__QUE_17AC82CAB86EFF1C506946_0]+span")
	WebElement btnPreviousBuildingInsuranceYes;

	@FindBy(css = "[id = C2__C1__C2__QUE_17AC82CAB86EFF1C506946_1]+span")
	WebElement btnPreviousBuildingInsuranceNo;

	@FindBy(css = "#C2__C1__C1__QUE_885CBC1D2759A867391554")
	WebElement fldTotalValueOfYourHomeContents;

	@FindBy(css = "#C2__C1__C1__QUE_885CBC1D2759A867391556")
	WebElement fldTotalValueOfPreciousOrCostlyItems;

	@FindBy(css = "#C2__C1__C1__QUE_885CBC1D2759A867391557")
	WebElement fldValueOfMostExpensiveItem;

	@FindBy(css = "#C2__C1__C1__QUE_885CBC1D2759A867391558")
	WebElement drpdwnTypeOfHomeContentsCoverRequired;

	@FindBy(css = "#C2__C1__C1__QUE_885CBC1D2759A867391559")
	WebElement drpdwnTotalExcessRequiredC;

	@FindBy(css = "[id = C2__C1__C1__QUE_885CBC1D2759A867391560_0]+span")
	WebElement btncontentsSumRepresentReplacementAmountYes;

	@FindBy(css = "[id = C2__C1__C1__QUE_885CBC1D2759A867391560_1]+span")
	WebElement btncontentsSumRepresentReplacementAmountNo;

	@FindBy(css = "[id = C2__C1__C1__QUE_885CBC1D2759A867391561_0]+span")
	WebElement btnPreviouslyContentsInsuranceYes;

	@FindBy(css = "[id = C2__C1__C1__QUE_885CBC1D2759A867391561_1]+span")
	WebElement btnPreviouslyContentsInsuranceNo;

	@FindBy(css = "#C2__C1__C1__ID-content-information")
	WebElement btnNextContentsInformation;

	@FindBy(css = "#C2__C1__C1__QUE_885CBC1D2759A867391567")
	WebElement fldCashKeptInYourHomeToBeInsured;

	@FindBy(css = "#C2__C1__C1__QUE_885CBC1D2759A867391568")
	WebElement fldCreditCardsToBeInsured;

	@FindBy(css = "#C2__C1__C1__QUE_885CBC1D2759A867391569")
	WebElement fldFrozenFoodToBeInsured;

	@FindBy(css = "#C2__C1__C1__ID-Additional-cover")
	WebElement btnNextAdditionalCovers;

	@FindBy(css = "[id = C2__C1__C1__C1__QUE_BA2AFC1B42B0F82C365160_0]+span")
	WebElement btnAddPersonalPossessionsWithinHomeYes;

	@FindBy(css = "[id = C2__C1__C1__C1__QUE_BA2AFC1B42B0F82C365160_1]+span")
	WebElement btnAddPersonalPossessionsWithinHomeNo;

	@FindBy(css = "[id = C2__C1__C1__C1__QUE_BA2AFC1B42B0F82C365163_0]+span")
	WebElement btnAddPersonalPossessionsOutsideHomeYes;

	@FindBy(css = "[id = C2__C1__C1__C1__QUE_BA2AFC1B42B0F82C365163_1]+span")
	WebElement btnAddPersonalPossessionsOutsideHomeNo;

	@FindBy(css = "[id = C2__C1__C1__C1__QUE_BA2AFC1B42B0F82C365233_0]+span")
	WebElement btnAddAnotherItemWithinHomeYes;

	@FindBy(css = "[id = C2__C1__C1__C1__QUE_BA2AFC1B42B0F82C365233_1]+span")
	WebElement btnAddAnotherItemWithinHomeNo;

	@FindBy(css = "#C2__C1__C1__C1__QUE_BA2AFC1B42B0F82C365236")
	WebElement drpdwnItemtypeNewSpecifiedItemWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__QUE_BA2AFC1B42B0F82C365237")
	WebElement fldItemvalueNewSpecifiedItemWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__QUE_BA2AFC1B42B0F82C365238")
	WebElement fldDescriptionOfItemNewSpecifiedItemWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__BUT_BA2AFC1B42B0F82C365239")
	WebElement btnSaveItemNewSpecifiedItemWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__BUT_BA2AFC1B42B0F82C365240")
	WebElement btnCancelItemNewSpecifiedItemWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__BUT_8FC62E0491705A7E403793")
	WebElement btnNextNewSpecifiedItemWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__QUE_BA2AFC1B42B0F82C365272")
	WebElement drpdwnItemtypeNewSpecifiedItemOutsideHome;

	@FindBy(css = "#C2__C1__C1__C1__QUE_BA2AFC1B42B0F82C365273")
	WebElement fldItemvalueNewSpecifiedItemOutsideHome;

	@FindBy(css = "#C2__C1__C1__C1__QUE_BA2AFC1B42B0F82C365274")
	WebElement fldDescriptionOfItemNewSpecifiedItemOutsideHome;

	@FindBy(css = "#C2__C1__C1__C1__BUT_BA2AFC1B42B0F82C365275")
	WebElement btnSaveItemNewSpecifiedItemOutsideHome;

	@FindBy(css = "#C2__C1__C1__C1__BUT_BA2AFC1B42B0F82C365276")
	WebElement btnCancelItemNewSpecifiedItemOutsideHome;

	@FindBy(css = "[id = C2__C1__C1__C1__QUE_BA2AFC1B42B0F82C365270_0]+span")
	WebElement btnAddAnotherItemOutsideHomeYes;

	@FindBy(css = "[id = C2__C1__C1__C1__QUE_BA2AFC1B42B0F82C365270_1]+span")
	WebElement btnAddAnotherItemOutsideHomeNo;

	@FindBy(css = "[id *= C2__C1__C1__C1__QUE_ABAEB7C18EBA7159127652_R]")
	List<WebElement> typeOfItemInWithinHome;

	@FindBy(css = "[id *= C2__C1__C1__C1__QUE_ABAEB7C18EBA7159670661_R]")
	List<WebElement> typeOfItemInOutsideHome;

	@FindBy(css = "#C2__C1__C1__C1__BUT_D47350D8096870BC699785")
	WebElement btnYesDeleteItemWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__BUT_D47350D8096870BC699790")
	WebElement btnNoDeleteItemWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__BUT_D47350D8096870BC702644")
	WebElement btnYesDeleteItemOutsideHome;

	@FindBy(css = "#C2__C1__C1__C1__BUT_D47350D8096870BC702646")
	WebElement btnNoDeleteItemOutsideHome;

	@FindBy(css = "#C2__C1__C1__QUE_885CBC1D2759A867391562")
	WebElement drpdwnNameOfPreviousInsurerC;

	@FindBy(css = "#C2__C1__C1__QUE_885CBC1D2759A867391563")
	WebElement fldPolicyNumberOfPreviousInsuranceC;

	@FindBy(css = "#C2__C1__C1__QUE_885CBC1D2759A867391564")
	WebElement fldExpiryDateOfPreviousInsuranceC;

	@FindBy(css = "#C2__C1__C1__QUE_885CBC1D2759A867391565")
	WebElement drpdwnPayForYourPreviousInsuranceC;

	@FindBy(css = "#C2__C1__C1__QUE_3AACC90BFC13E0AE309146")
	WebElement drpdwnNoOfYearsWithoutClaimingC;

	@FindBy(css = "#C2__C1__C1__C1__p1_HEAD_BA2AFC1B42B0F82C365223>div")
	WebElement headerTypeInWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__p1_HEAD_BA2AFC1B42B0F82C365224>div")
	WebElement headerValueInWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__p1_HEAD_BA2AFC1B42B0F82C365225>div")
	WebElement headerDescriptionInWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__p1_HEAD_BA2AFC1B42B0F82C365261>div")
	WebElement headerTypeInOutsideHome;

	@FindBy(css = "#C2__C1__C1__C1__p1_HEAD_BA2AFC1B42B0F82C365262>div")
	WebElement headerValueInOutsideHome;

	@FindBy(css = "#C2__C1__C1__C1__p1_HEAD_BA2AFC1B42B0F82C365263>div")
	WebElement headerDescriptionInOutsideHome;

	@FindBy(css = "#C2__C1__C2__ID-BuildingCover")
	WebElement btnNextBuildingsCoverDetails;

	@FindBy(css = "[id = C2__C1__QUE_43935BE4AB40B20C835315_0]+Span")
	WebElement btnAddAnotherOccupationYes;

	@FindBy(css = "[id = C2__C1__QUE_43935BE4AB40B20C835315_1]+Span")
	WebElement btnAddAnotherOccupationNo;

	@FindBy(css = "#C2__C1__QUE_43935BE4AB40B20C836855")
	WebElement drpdwnOccupation;

	@FindBy(css = "#C2__C1__QUE_43935BE4AB40B20C836857")
	WebElement drpdwnBusiness;

	@FindBy(css = "#C2__C1__QUE_43935BE4AB40B20C836859")
	WebElement drpdwnEmploymentStatus;

	@FindBy(css = "[id = C2__C1__QUE_43935BE4AB40B20C836861_0]+Span")
	WebElement btnMainOccupationYes;

	@FindBy(css = "[id = C2__C1__QUE_43935BE4AB40B20C836861_1]+Span")
	WebElement btnMainOccupationNo;

	@FindBy(css = "#C2__C1__BUT_43935BE4AB40B20C838352")
	WebElement btnSaveOccupation;

	@FindBy(css = "#C2__C1__BUT_FC5236CC46AC45B5364034")
	WebElement btnCancelOccupation;

	@FindBy(css = "#C2__C1__ID-occupation")
	WebElement btnNextOccupation;

	@FindBy(css = "[id = C2__C1__QUE_43935BE4AB40B20C1034493_0]+Span")
	WebElement btnAddAnotherPolicyHolderOccupationYes;

	@FindBy(css = "[id = C2__C1__QUE_43935BE4AB40B20C1034493_1]+Span")
	WebElement btnAddAnotherPolicyHolderOccupationNo;

	@FindBy(css = "#C2__C1__QUE_43935BE4AB40B20C1034499")
	WebElement drpdwnPolicyHolderOccupation;

	@FindBy(css = "#C2__C1__QUE_43935BE4AB40B20C1034501")
	WebElement drpdwnPolicyHolderBusiness;

	@FindBy(css = "#C2__C1__QUE_43935BE4AB40B20C1034503")
	WebElement drpdwnPolicyHolderEmploymentStatus;

	@FindBy(css = "[id = C2__C1__QUE_43935BE4AB40B20C1034505_0]+Span")
	WebElement btnPolicyHolderMainOccupationYes;

	@FindBy(css = "[id = C2__C1__QUE_43935BE4AB40B20C1034505_1]+Span")
	WebElement btnPolicyHolderMainOccupationNo;

	@FindBy(css = "#C2__C1__BUT_43935BE4AB40B20C1034510")
	WebElement btnPolicyHolderSaveOccupation;

	@FindBy(css = "#C2__C1__BUT_43935BE4AB40B20C1034511")
	WebElement btnPolicyHolderCancelOccupation;

	@FindBy(css = "#C2__C1__BUT_43935BE4AB40B20C1034512")
	WebElement btnNextJointPolicyholdersOccupations;

	@FindBy(css = "#C2__C1__QUE_95156CFBCB3D5F4E282813")
	WebElement drpdwnSelectTypeOfOwnership;

	@FindBy(css = "#C2__C1__QUE_95156CFBCB3D5F4E284324")
	WebElement drpdwnSelectTypeOfOccupancy;

	@FindBy(css = "#C2__C1__QUE_95156CFBCB3D5F4E284343")
	WebElement drpdwnPeopleInTotalLiveInProperty;

	@FindBy(css = "#C2__C1__QUE_95156CFBCB3D5F4E285848")
	WebElement drpdwnChildrenUnder18LiveInProperty;

	@FindBy(css = "#C2__C1__ID-property-resident")
	WebElement btnNextResidents;

	@FindBy(css = "[id *= C2__C1__p4_QUE_43935BE4AB40B20C839909_R] span")
	List<WebElement> typeOfOccupation;

	@FindBy(css = "[id *= C2__C1__p1_QUE_43935BE4AB40B20C1034486_R]")
	List<WebElement> typeOfPolicyHolderOccupation;

	@FindBy(css = "#C2__C1__BUT_146EFBFB9856737A209695")
	WebElement btnCancel;

	@FindBy(css = "#C2__C1__HELP_HEAD_146EFBFB9856737A201280")
	WebElement iconHelpTitleYourProperty;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpTitleYourProperty;

	@FindBy(css = "#C2__C1__HELP_BUT_146EFBFB9856737A276187")
	WebElement iconHelpBuildingsContents;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpBuildingsContents;

	@FindBy(css = "#C2__C1__HELP_BUT_146EFBFB9856737A276197")
	WebElement iconHelpBuildings;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpBuildings;

	@FindBy(css = "#C2__C1__HELP_BUT_146EFBFB9856737A276207")
	WebElement iconHelpContents;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpContents;

	@FindBy(css = "button[title='Enter Address Manually']")
	WebElement btnEnterManualAdd;

	@FindBy(css = "input[id='C2__C1__C1__QUE_CCE7E08A37BF764D13137']")
	WebElement txtAddressLine1;

	@FindBy(css = "input[id='C2__C1__C1__QUE_CCE7E08A37BF764D13139']")
	WebElement txtAddressLine2;

	@FindBy(css = "input[id='C2__C1__C1__QUE_CCE7E08A37BF764D13141']")
	WebElement txtAddressLine3;

	@FindBy(css = "input[id='C2__C1__C1__QUE_CCE7E08A37BF764D13143']")
	WebElement txtAddressLine4;

//	@FindBy(css = "#C2__C1__HELP_HEAD_A335DC66FD885731498370")	    
	@FindBy(css = "a[id='C2__C1__HELP_HEAD_A335DC66FD885731498370']")
	WebElement iconHelpAddressofPropertyToBeInsured;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpAddressofPropertyToBeInsured;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_CCE7E08A37BF764D13129")
	WebElement iconHelpPostcodeOfProperty;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPostcodeOfProperty;

	@FindBy(css = "#C2__C1__HELP_HEAD_A335DC66FD885731483330")
	WebElement iconHelpYourCorrespondenceAddress;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpYourCorrespondenceAddress;

	@FindBy(css = "#C2__C1__HELP_QUE_A335DC66FD885731267001")
	WebElement iconHelpCorrespondenceAddressToUpdate;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpCorrespondenceAddressToUpdate;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB75789289")
	WebElement iconHelpTypeOfProperty;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpTypeOfProperty;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB75789293")
	WebElement iconHelpDateOfResidence;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpDateOfResidence;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB75829350")
	WebElement iconHelpYearYourPropertyWasBuilt;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpYearYourPropertyWasBuilt;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB75829355")
	WebElement iconHelpNumberOfBathRooms;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpNumberOfBathRooms;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB75829359")
	WebElement iconHelpNumberOfBedrooms;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpNumberOfBedrooms;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB75932886")
	WebElement iconHelpTypeOfWallConstruction;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpTypeOfWallConstruction;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB75932890")
	WebElement iconHelpTypeOfRoofConstruction;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpTypeOfRoofConstruction;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB751025068")
	WebElement iconHelpRoofThatIsFlat;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpRoofThatIsFlat;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB751026408")
	WebElement iconHelpTypeOfListedBuildingIfAppropriate;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpTypeOfListedBuildingIfAppropriate;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB751026412")
	WebElement iconHelpPropertyBeenExtended;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPropertyBeenExtended;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB751027751")
	WebElement iconHelpPropertyUsedForBusinessPurposes;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPropertyUsedForBusinessPurposes;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB751027755")
	WebElement iconHelpPropertyFreeFromSubsidence;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPropertyFreeFromSubsidence;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB751027759")
	WebElement iconHelpPropertyFreeFromFlooding;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPropertyFreeFromFlooding;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB751027763")
	WebElement iconHelpPropertyEverBeenUnderpinned;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPropertyEverBeenUnderpinned;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB751029118")
	WebElement iconHelpPropertyWithin400MetresOfWater;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPropertyWithin400MetresOfWater;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB751030472")
	WebElement iconHelpTreesTallerThan5Metres;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpTreesTallerThan5Metres;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB751030492")
	WebElement iconHelpPropertyInAGoodStateOfRepair;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPropertyInAGoodStateOfRepair;

	@FindBy(css = "#C2__C1__HELP_QUE_F6EBE6B19D11AB751031847")
	WebElement iconHelpPropertySelfContainedAndLockable;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPropertySelfContainedAndLockable;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_F6EBE6B19D11AB751041626']")
	WebElement iconHelpOccupiedDuringTheDay;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPropertyOccupiedDuringTheDay;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_F6EBE6B19D11AB751044320']")
	WebElement iconHelpOccupiedAtNight;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPropertyOccupiedAtNight;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_F6EBE6B19D11AB751044323']")
	WebElement iconHelpPropertyHaveAnAlarmInstalled;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPropertyHaveAnAlarmInstalled;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_F6EBE6B19D11AB751044326']")
	WebElement iconHelpBritishStandardKitemark;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpBritishStandardKitemark;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_F6EBE6B19D11AB751044329']")
	WebElement iconHelpPropertyHaveExternalSecurityLighting;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPropertyHaveExternalSecurityLighting;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_F6EBE6B19D11AB751044332']")
	WebElement iconHelpLocksActivatedAtNight;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpLocksActivatedAtNight;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_F6EBE6B19D11AB751044335']")
	WebElement iconHelpNeighbourhoodWatchScheme;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpNeighbourhoodWatchScheme;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_F6EBE6B19D11AB751048441']")
	WebElement iconHelpPropertyFittedWithASmokeDetector;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPropertyFittedWithASmokeDetector;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_F6EBE6B19D11AB751048443']")
	WebElement iconHelpSafeInstalledWithinTheProperty;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpSafeInstalledWithinTheProperty;

	//Building
	@FindBy(css = "#C2__C1__C2__HELP_QUE_17AC82CAB86EFF1C489445")
	WebElement iconHelpRebuildCost;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpRebuildCost;

	@FindBy(css = "#C2__C1__C2__HELP_QUE_17AC82CAB86EFF1C490905")
	WebElement iconHelpAccidentalDamage;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpAccidentalDamage;

	@FindBy(css = "#C2__C1__C2__HELP_QUE_17AC82CAB86EFF1C502572")
	WebElement iconHelpTotalExcessRequired;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpTotalExcessRequired;

	@FindBy(css = "#C2__C1__C2__HELP_QUE_17AC82CAB86EFF1C506946")
	WebElement iconHelpPreviousBuildingInsurance;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPreviousBuildingInsurance;

	@FindBy(css = "#C2__C1__C2__HELP_QUE_17AC82CAB86EFF1C508403")
	WebElement iconHelpNameOfPreviousInsurer;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpNameOfPreviousInsurer;

	@FindBy(css = "#C2__C1__C2__HELP_QUE_17AC82CAB86EFF1C509860")
	WebElement iconHelpPolicyNumberOfPreviousInsuranceB;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPolicyNumberOfPreviousInsuranceB;

	@FindBy(css = "#C2__C1__C2__HELP_QUE_17AC82CAB86EFF1C5098634534")
	WebElement iconHelpExpiryDateOfPreviousInsuranceB;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpExpiryDateOfPreviousInsuranceB;

	@FindBy(css = "#C2__C1__C2__HELP_QUE_17AC82CAB86EFF1C509866")
	WebElement iconHelpPayForYourPreviousInsuranceB;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPayForYourPreviousInsuranceB;

	@FindBy(css = "#C2__C1__C2__HELP_QUE_17AC82CAB86EFF1C505489")
	WebElement iconHelpNoOfYearsWithoutClaimingB;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpNoOfYearsWithoutClaimingB;

	//Content
	@FindBy(css = "#C2__C1__HELP_HEAD_146EFBFB9856737A201283")
	WebElement iconHelpvalueOfTitleYourContents;
	
	@FindBy(css = ".tetris_helpText")
	WebElement popHelpvalueOfTitleYourContents;
	
	@FindBy(css = "#C2__C1__C1__HELP_QUE_885CBC1D2759A867391554")
	WebElement iconHelpvalueOfHomeContents;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpvalueOfHomeContents;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_885CBC1D2759A867391556")
	WebElement iconHelpvalueOfPreciousOrCostlyItems;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpvalueOfPreciousOrCostlyItems;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_885CBC1D2759A867391557")
	WebElement iconHelpvalueOfMostExpensiveItem;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpvalueOfMostExpensiveItem;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_885CBC1D2759A867391558")
	WebElement iconHelptypeOfHomeContentsCoverRequired;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelptypeOfHomeContentsCoverRequired;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_885CBC1D2759A867391559")
	WebElement iconHelptotalExcessRequired;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelptotalExcessRequired;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_885CBC1D2759A867391560")
	WebElement iconHelpcontentsSumRepresentReplacementAmount;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpcontentsSumRepresentReplacementAmount;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_885CBC1D2759A867391561")
	WebElement iconHelppreviouslyContentsInsurance;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelppreviouslyContentsInsurance;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_885CBC1D2759A867391562")
	WebElement iconHelpnameOfPreviousInsurer;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpnameOfPreviousInsurer;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_885CBC1D2759A867391563")
	WebElement iconHelpPolicyNumberOfPreviousInsuranceC;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPolicyNumberOfPreviousInsuranceC;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_885CBC1D2759A867391564")
	WebElement iconHelpExpiryDateOfPreviousInsuranceC;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpExpiryDateOfPreviousInsuranceC;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_885CBC1D2759A867391565")
	WebElement iconHelpPayForYourPreviousInsuranceC;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPayForYourPreviousInsuranceC;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_3AACC90BFC13E0AE309146")
	WebElement iconHelpNoOfYearsWithoutClaimingC;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpNoOfYearsWithoutClaimingC;

	@FindBy(css = "#C2__C1__C1__HELP_HEAD_885CBC1D2759A867391551")
	WebElement iconHelpAddContentsCover;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpAddContentsCover;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_885CBC1D2759A867391567")
	WebElement iconHelpcashInYourHomeToBeInsured;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpcashInYourHomeToBeInsured;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_885CBC1D2759A867391568")
	WebElement iconHelpcreditCardsToBeInsured;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpcreditCardsToBeInsured;

	@FindBy(css = "#C2__C1__C1__HELP_QUE_885CBC1D2759A867391569")
	WebElement iconHelpfrozenFoodToBeInsured;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpfrozenFoodToBeInsured;

	@FindBy(css = "#C2__C1__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365160")
	WebElement iconHelpaddPersonalPossessionsWithinHome;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpaddPersonalPossessionsWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365233")
	WebElement iconHelpaddAnotherItemInWithinHome;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpaddAnotherItemInWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365236")
	WebElement iconHelpItemtypeNewSpecifiedItemWithinHome;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpItemtypeNewSpecifiedItemWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365237")
	WebElement iconHelpItemvalueNewSpecifiedItemWithinHome;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpItemvalueNewSpecifiedItemWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365238")
	WebElement iconHelpDescriptionOfItemNewSpecifiedItemWithinHome;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpDescriptionOfItemNewSpecifiedItemWithinHome;

	@FindBy(css = "#C2__C1__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365163")
	WebElement iconHelpaddPersonalPossessionsOutsideHome;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpaddPersonalPossessionsOutsideHome;

	@FindBy(css = "#C2__C1__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365270")
	WebElement iconHelpaddAnotherItemInOutsideHome;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpaddAnotherItemInOutsideHome;

	@FindBy(css = "#C2__C1__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365272")
	WebElement iconHelpItemtypeNewSpecifiedItemOutsideHome;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpItemtypeNewSpecifiedItemOutsideHome;

	@FindBy(css = "#C2__C1__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365273")
	WebElement iconHelpItemvalueNewSpecifiedItemOutsideHome;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpItemvalueNewSpecifiedItemOutsideHome;

	@FindBy(css = "#C2__C1__C1__C1__HELP_QUE_BA2AFC1B42B0F82C365274")
	WebElement iconHelpDescriptionOfItemNewSpecifiedItemOutsideHome;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpDescriptionOfItemNewSpecifiedItemOutsideHome;

	//people
	@FindBy(css = "a[id='C2__C1__HELP_HEAD_146EFBFB9856737A201283']")
	WebElement iconHelpTitleOfPeople;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpTitleOfPeople;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_43935BE4AB40B20C836855']")
	WebElement iconHelpOccupation;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpOccupation;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_43935BE4AB40B20C836857']")
	WebElement iconHelpBusiness;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpBusiness;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_43935BE4AB40B20C836859']")
	WebElement iconHelpEmploymentStatus;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpEmploymentStatus;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_43935BE4AB40B20C836861']")
	WebElement iconHelpMainOccupation;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpMainOccupation;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_43935BE4AB40B20C1034499']")
	WebElement iconHelpPolicyHolderOccupation;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPolicyHolderOccupation;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_43935BE4AB40B20C1034501']")
	WebElement iconHelpPolicyHolderBusiness;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPolicyHolderBusiness;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_43935BE4AB40B20C1034503']")
	WebElement iconHelpPolicyHolderEmploymentStatus;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPolicyHolderEmploymentStatus;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_43935BE4AB40B20C1034505']")
	WebElement iconHelpPolicyHolderMainOccupation;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPolicyHolderMainOccupation;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_95156CFBCB3D5F4E282813']")
	WebElement iconHelpTypeOfOwnership;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpTypeOfOwnership;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_95156CFBCB3D5F4E284324']")
	WebElement iconHelpTypeOfOccupancy;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpTypeOfOccupancy;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_95156CFBCB3D5F4E284343']")
	WebElement iconHelpPeopleNormallyLiveInTheProperty;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpPeopleNormallyLiveInTheProperty;

	@FindBy(css = "a[id='C2__C1__HELP_QUE_95156CFBCB3D5F4E285848']")
	WebElement iconHelpChildrenUnder18LiveInTheProperty;

	@FindBy(css = ".tetris_helpText")
	WebElement popHelpChildrenUnder18LiveInTheProperty;

	/**
	 * 
	 * Constructor class for Add Specified Item page Here we initializing the driver
	 * for page factory objects. For ajax element waiting time has added while
	 * initialization
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public ChangeMyPropertyPage(WebDriver driver, ExtentTest report) {
		this.driver = driver;
		this.extentedReport = report;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() {

		if (!isPageLoaded) {
			Assert.fail();
		}

		if (isPageLoaded && (!(txtTitleYourProperty.isDisplayed()))) {
			Log.fail("User is not navigated to Your Property Page ", driver, extentedReport);
		}
		uielement = new ElementLayer(driver);
	}

	@Override
	protected void load() {
		isPageLoaded = true;
		WaitUtils.waitForPageLoad(driver);
	}

	/**
	 * To click Quote button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickQuoteButton(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnQuote);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnQuote);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17863_17774_Clicked on 'Quote' button in 'Occupation' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17863_17774_Error while clicking 'Quote' button in 'Occupation' page : " + e);
		}
	}

	/**
	 * To select any type of change
	 * 
	 * @param String
	 *            TypeOfChange - Change my car|Change my Cover Type|Change the Class
	 *            of Use|Change Permitted Drivers|Change my Overnight Location
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectChangeType(String TypeOfChange, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForListElement(driver, lstChangeType, 30);
			boolean status = false;
			for (int i = 0; i < lstChangeType.size(); i++) {
				if (lstChangeType.get(i).getAttribute("value").equalsIgnoreCase(TypeOfChange)) {
					WaitUtils.waitForElement(driver, lstChangeType.get(i));

					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", lstChangeType.get(i));

					Thread.sleep(3000); // required for the continue button to be visible
					status = true;
					break;
				}
			}
			if (status) {
				Log.message("Clicked on '" + TypeOfChange + "'", driver, extentedReport, screenshot);
			} else {
				Log.message("Failed to click on '" + TypeOfChange + "'", driver, extentedReport, screenshot);
			}
		} catch (Exception e) {
			throw new Exception("Error while selecting type of change : " + TypeOfChange + "" + e);
		}
	}

	/**
	 * To click Next button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextButton(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			//			clickHourtime(extentedReport, true);
			GenericUtils.scrollIntoView(driver, btnNextTypeofInsurancePolicy);
			WaitUtils.waitForelementToBeClickable(driver, btnNextTypeofInsurancePolicy, "Next button is not found");
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", btnNextTypeofInsurancePolicy);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17694_Clicked on Next button", driver, extentedReport, screenshot);
			WaitUtils.waitForPageLoad(driver, 30);
		} catch (Exception e) {
			throw new Exception("TCNO_17694_Error while clicking Next button : " + e);
		}
	}

	/**
	 * To click Cancel button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickCancelButton(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForelementToBeClickable(driver, btnCancel,
					"Cancel button is not found in 'Add Specified Items' page");
			btnCancel.click();
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Cancel button in 'Chamge my property' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking cancel button in 'Chamge my property' page : " + e);
		}
	}

	/**
	 * To click yes or No  to add personal possessions which stay within your home
	 * 
	 * @param Add Personal Possessions Within Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoAddPersonalPossessionsWithinHome(String addPersonalPossessionsWithinHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (addPersonalPossessionsWithinHome.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnAddPersonalPossessionsWithinHomeYes);
				btnAddPersonalPossessionsWithinHomeYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAddPersonalPossessionsWithinHomeNo);
				btnAddPersonalPossessionsWithinHomeNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("TCNO_17770_17772_Clicked option for want to add personal possessions which stay within your home : " + addPersonalPossessionsWithinHome + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("TCNO_17770_17772_Error while clicking Yes or No button : " + e);
		}
	}

	/**
	 * To click yes or No  to add personal possessions which are taken outside the home
	 * 
	 * @param Add Personal Possessions Outside Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoAddPersonalPossessionsOutsideHome(String addPersonalPossessionsOutsideHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (addPersonalPossessionsOutsideHome.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnAddPersonalPossessionsOutsideHomeYes);
				btnAddPersonalPossessionsOutsideHomeYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAddPersonalPossessionsOutsideHomeNo);
				btnAddPersonalPossessionsOutsideHomeNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("TCNO_17773_Clicked option for want to add personal possessions which are taken outside the home : " + addPersonalPossessionsOutsideHome + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("TCNO_17773_Error while clicking radio button : " + e);
		}
	}

	/**
	 * To select Item type of Add a New Specified Item under Specified Items within the Home
	 * 
	 * @param Item Type Of Specified Items Within Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectItemTypeOfSpecifiedItemsWithinHome(String itemTypeOfSpecifiedItemsWithinHome, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnItemtypeNewSpecifiedItemWithinHome);
			Select coverSelect = new Select(drpdwnItemtypeNewSpecifiedItemWithinHome);
			coverSelect.selectByVisibleText(itemTypeOfSpecifiedItemsWithinHome);
			Log.message("TCNO_17775_Selected Cover Type : " + itemTypeOfSpecifiedItemsWithinHome, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("TCNO_17775_Error while selecting Total Excess Required on Add Building cover page : " + e);
		}
	}

	/**
	 * To select Item type of Add a New Specified Item under Specified Items Outside the Home
	 * 
	 * @param Item Type Of Specified Items Outside Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectItemTypeOfSpecifiedItemsOutsideHome(String itemTypeOfSpecifiedItemsOutsideHome, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnItemtypeNewSpecifiedItemOutsideHome);
			Select coverSelect = new Select(drpdwnItemtypeNewSpecifiedItemOutsideHome);
			coverSelect.selectByVisibleText(itemTypeOfSpecifiedItemsOutsideHome);
			Log.message("TCNO_17775_Selected Cover Type : " + itemTypeOfSpecifiedItemsOutsideHome, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("TCNO_17775_Error while selecting Total Excess Required on Add Building cover page : " + e);
		}
	}

	/**
	 * To enter Item value of  Add a New Specified Item Under Specified Items within the Home
	 * 
	 * @param Item Value Of Specified Items Within Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterItemValueOfSpecifiedItemsWithinHome(String itemValueOfSpecifiedItemsWithinHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldItemvalueNewSpecifiedItemWithinHome);
			fldItemvalueNewSpecifiedItemWithinHome.click();
			fldItemvalueNewSpecifiedItemWithinHome.clear();
			fldItemvalueNewSpecifiedItemWithinHome.sendKeys(itemValueOfSpecifiedItemsWithinHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForElement(driver, fldDescriptionOfItemNewSpecifiedItemWithinHome);
			Log.message(itemValueOfSpecifiedItemsWithinHome + " - rebuild cost of your home is enterd in the Add Buildings Cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Rebuild Cost on Add Building cover: " + e);
		}

	}

	/**
	 * To enter Item value of  Add a New Specified Item Under Specified Items Outside the Home
	 * 
	 * @param Item Value Of Specified Items Outside Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterItemValueOfSpecifiedItemsOutsideHome(String itemValueOfSpecifiedItemsOutsideHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldItemvalueNewSpecifiedItemOutsideHome);
			fldItemvalueNewSpecifiedItemOutsideHome.clear();
			fldItemvalueNewSpecifiedItemOutsideHome.sendKeys(itemValueOfSpecifiedItemsOutsideHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForElement(driver, fldDescriptionOfItemNewSpecifiedItemOutsideHome);
			Log.message(itemValueOfSpecifiedItemsOutsideHome + " - rebuild cost of your home is enterd in the Add Buildings Cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Rebuild Cost on Add Building cover: " + e);
		}

	}

	/**
	 * To enter Description of item of  Add a New Specified Item Under Specified Items within the Home
	 * 
	 * @param Description of item
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterDescriptionOfItemOfSpecifiedItemsWithinHome(String descriptionOfItemOfSpecifiedItemsWithinHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldDescriptionOfItemNewSpecifiedItemWithinHome);
			fldDescriptionOfItemNewSpecifiedItemWithinHome.clear();
			WaitUtils.waitForSpinner(driver);
			fldDescriptionOfItemNewSpecifiedItemWithinHome.sendKeys(descriptionOfItemOfSpecifiedItemsWithinHome+Keys.TAB);
			Log.message(descriptionOfItemOfSpecifiedItemsWithinHome + " - rebuild cost of your home is enterd in the Add Buildings Cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Rebuild Cost on Add Building cover: " + e);
		}

	}

	/**
	 * To enter Description of item of  Add a New Specified Item Under Specified Items Outside the Home
	 * 
	 * @param Description of item
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterDescriptionOfItemOfSpecifiedItemsOutsideHome(String descriptionOfItemOfSpecifiedItemsOutsideHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldDescriptionOfItemNewSpecifiedItemOutsideHome);
			fldDescriptionOfItemNewSpecifiedItemOutsideHome.clear();
			WaitUtils.waitForSpinner(driver);
			fldDescriptionOfItemNewSpecifiedItemOutsideHome.sendKeys(descriptionOfItemOfSpecifiedItemsOutsideHome+Keys.TAB);
			WaitUtils.waitForSpinner(driver);
			Log.message(descriptionOfItemOfSpecifiedItemsOutsideHome + " - rebuild cost of your home is enterd in the Add Buildings Cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Rebuild Cost on Add Building cover: " + e);
		}

	}

	/**
	 * To click SaveItem button NewSpecifiedItemWithinHome
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickSaveItemNewSpecifiedItemWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnSaveItemNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnSaveItemNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17787_Clicked on 'Save Item' button of New SpecifiedItem Within Home in 'Add Specified Items' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17787_Error while clicking 'Save Item' button of New SpecifiedItem Within Home in 'Add Specified Items' page: " + e);
		}
	}

	/**
	 * To click SaveItem button NewSpecifiedItemOutsideHome
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickSaveItemNewSpecifiedItemOutsideHome(ExtentTest extentedReport, boolean screenshot) throws Exception {

		try {
			WaitUtils.waitForElement(driver, btnSaveItemNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnSaveItemNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17788_Clicked on 'Save Item' button of New SpecifiedItem Outside Home in 'Add Specified Items' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17788_Error while clicking 'Save Item' button of New SpecifiedItem Outside Home in 'Add Specified Items' page: " + e);
		}
	}

	/**
	 * To click Cancel button NewSpecifiedItemWithinHome
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickCancelNewSpecifiedItemWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnCancelItemNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnCancelItemNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17779_Clicked on 'Cancel' button of New SpecifiedItem Within Home in 'Add Specified Items' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17779_Error while clicking 'Cancel' button of New SpecifiedItem Within Home in 'Add Specified Items' page: " + e);
		}
	}

	/**
	 * To click Cancel button NewSpecifiedItemOutsideHome
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickCancelNewSpecifiedItemOutsideHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnCancelItemNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnCancelItemNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17789_Clicked on 'Cancel' button of New SpecifiedItem Outside Home in 'Add Specified Items' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17789_Error while clicking 'Cancel' button of New SpecifiedItem Outside Home in 'Add Specified Items' page: " + e);
		}
	}

	/**
	 * To click Next button NewSpecifiedItemWithinHome
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextNewSpecifiedItemWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnNextNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Next' button of New SpecifiedItem Within Home in 'Add Specified Items' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Next' button of New SpecifiedItem Within Home in 'Add Specified Items' page: " + e);
		}
	}

	/**
	 * To click yes or No for Add Another Item In Outside Home
	 * 
	 * @param Add Another Item In Outside Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoAddAnotherItemInOutsideHome(String addAnotherItemInOutsideHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (addAnotherItemInOutsideHome.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnAddAnotherItemOutsideHomeYes);
				btnAddAnotherItemOutsideHomeYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAddAnotherItemOutsideHomeNo);
				btnAddAnotherItemOutsideHomeNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("TCNO_17794_Clicked option for Do you want to add another item in outside the home : " + addAnotherItemInOutsideHome + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("TCNo_17794_Error while clicking radio button : " + e);
		}
	}

	/**
	 * To click yes or No for Add Another Item In Within Home
	 * 
	 * @param Add Another Item In Within Home
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoAddAnotherItemInWithinHome(String addAnotherItemInWithinHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (addAnotherItemInWithinHome.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnAddAnotherItemWithinHomeYes);
				btnAddAnotherItemWithinHomeYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAddAnotherItemWithinHomeNo);
				btnAddAnotherItemWithinHomeNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("TCNO_17783_17784_Clicked option for Do you want to add another item in within the home : " + addAnotherItemInWithinHome + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("TCNO_17783_17784_Error while clicking radio button : " + e);
		}
	}

	/**
	 * To get Added Item row in Within home
	 * 
	 * @param itemName
	 * @param Modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public String getAddedItemRowInWithinHome(String itemName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String DeviceRow = null;
			WaitUtils.waitForSpinner(driver);
			if (typeOfItemInWithinHome.size() == 0) {
				Log.message("No Item is available", driver, extentedReport, false);
			}

			for (int loopCount = 0; loopCount < typeOfItemInWithinHome.size(); loopCount++) {
				Integer row = loopCount + 1;
				String displayedItemName = typeOfItemInWithinHome.get(loopCount).getText();
				if (displayedItemName.contains(itemName)) {
					Log.message(itemName + "is found", driver,extentedReport, screenshot);
					return row.toString();
				}
			}
			return DeviceRow;
		} catch (Exception e) {
			throw new Exception("Error while getting Added Item row in Within Home : " + e);
		}
	}

	/**
	 * To get Added Item row in Outside home
	 * 
	 * @param itemName
	 * @param Modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */	
	public String getAddedItemRowInOutsideHome(String itemName, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String DeviceRow = null;
			WaitUtils.waitForSpinner(driver);
			if (typeOfItemInOutsideHome.size() == 0) {
				Log.message("No Item is available", driver, extentedReport, false);
			}

			for (int loopCount = 0; loopCount < typeOfItemInOutsideHome.size(); loopCount++) {
				Integer row = loopCount + 1;
				String displayedItemName = typeOfItemInOutsideHome.get(loopCount).getText();
				if (displayedItemName.contains(itemName)) {
					Log.message(itemName + "is found", driver,extentedReport, screenshot);
					return row.toString();
				}
			}
			return DeviceRow;
		} catch (Exception e) {
			throw new Exception("Error while getting Added Item row in Outsied Home: " + e);
		}
	}

	/**
	 * To click Edit button of Specified Item Within Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickEditBtnInWithinHome(String itemName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String ItemRow = getAddedItemRowInWithinHome(itemName, extentedReport, false);
			if (ItemRow == null) {
				throw new Exception(itemName + " - Added Item is not found");
			}
			String elementString = cssEditAddedItemInWithinHome + ItemRow;
			WebElement editBtn = driver.findElement(By.cssSelector(elementString));
			editBtn.click();
			Log.message("Clicked on " + ItemRow + " (th) edit button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking edit button of Added Item in Within Home: " + e);
		}
	}

	/**
	 * To click Delete button of Specified Item Within Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickDeleteBtnInWithinHome(String itemName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String ItemRow = getAddedItemRowInWithinHome(itemName, extentedReport, false);
			if (ItemRow == null) {
				throw new Exception(itemName + " - Added Item is not found");
			}
			String elementString = btnDeleteAddedItemInWithinHome + ItemRow;
			WebElement editBtn = driver.findElement(By.cssSelector(elementString));
			editBtn.click();
			Log.message("Clicked on " + ItemRow + " (th) Delete button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking Delete button of Added Item in Within Home: " + e);
		}
	}

	/**
	 * To click Edit button of Specified Item Outside Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickEditBtnInOutsideHome(String itemName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String ItemRow = getAddedItemRowInOutsideHome(itemName, extentedReport, false);
			if (ItemRow == null) {
				throw new Exception(itemName + " - Added Item is not found");
			}
			String elementString = btnEditAddedItemInOutsideHome + ItemRow;
			WebElement editBtn = driver.findElement(By.cssSelector(elementString));
			editBtn.click();
			Log.message("Clicked on " + ItemRow + " (th) edit button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking edit button of Added Item in Outside Home : " + e);
		}
	}

	/**
	 * To click Delete button of Specified Item Outside Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickDeleteBtnInOutsideHome(String itemName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String ItemRow = getAddedItemRowInOutsideHome(itemName, extentedReport, false);
			if (ItemRow == null) {
				throw new Exception(itemName + " - Added Item is not found");
			}
			String elementString = cssDeleteAddedItemInOutsideHome + ItemRow;
			WebElement editBtn = driver.findElement(By.cssSelector(elementString));
			editBtn.click();
			Log.message("Clicked on " + ItemRow + " (th) Delete button", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking Delete button of Added Item in Outside Home: " + e);
		}
	}

	/**
	 * To verify Edit & Delete button of Specified Item within Home
	 * 
	 * @param deviceName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyDeleteAndEditBtnInWithinHome(String deviceName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {

			boolean btnFound = false;
			String deviceRow = getAddedItemRowInWithinHome(deviceName, extentedReport, false);

			if (deviceRow == null) {
				//throw new Exception(deviceName + " - Added Item is not found");
				Log.message("The Specified item " +deviceName +" is not found in the list", extentedReport);
				return false;
			}

			String editElementString = cssEditAddedItemInWithinHome + deviceRow;
			WebElement editBtn = driver.findElement(By.cssSelector(editElementString));

			String deleteElementString = cssDeleteAddedItemInWithinHome + deviceRow;
			WebElement deleteBtn = driver.findElement(By.cssSelector(deleteElementString));

			if (WaitUtils.waitForElement(driver, editBtn) && WaitUtils.waitForElement(driver, deleteBtn)) {
				btnFound = true;
			}
			return btnFound;
		} catch (Exception e) {
			throw new Exception("Error while verifying edit and delete button of Added Item in Within Home : " + e);
		}
	}

	/**
	 * To verify Edit & Delete button of Specified Item Outside Home
	 * 
	 * @param deviceName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyDeleteAndEditBtnInOutsideHome(String itemName, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {

			boolean btnFound = false;
			String deviceRow = getAddedItemRowInOutsideHome(itemName, extentedReport, false);

			if (deviceRow == null) {
				//throw new Exception(itemName + " - Added Item is not found");
				return false;
			}

			String editElementString = cssEditAddedItemInOutsideHome + deviceRow;
			WebElement editBtn = driver.findElement(By.cssSelector(editElementString));

			String deleteElementString = cssDeleteAddedItemInOutsideHome + deviceRow;
			WebElement deleteBtn = driver.findElement(By.cssSelector(deleteElementString));

			if (WaitUtils.waitForElement(driver, editBtn) && WaitUtils.waitForElement(driver, deleteBtn)) {
				btnFound = true;
			}
			return btnFound;
		} catch (Exception e) {
			throw new Exception("Error while verifying edit and delete button of Added Item in Outside Home : " + e);
		}
	}

	/**
	 * To click yes/No button in popup Within Home
	 * 
	 * @param deleteItem
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoBtnToDeleteItemInWithinHome(String deleteItem, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (deleteItem) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnYesDeleteItemWithinHome);
				GenericUtils.clickElementJS(driver, btnYesDeleteItemWithinHome);
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnNoDeleteItemWithinHome);
				GenericUtils.clickElementJS(driver, btnNoDeleteItemWithinHome);
				WaitUtils.waitForSpinner(driver);
				break;
			}
			WaitUtils.waitForPageLoad(driver);
			Log.message("Clicked on " + deleteItem + " button in Item deleting Popup", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking button in Item deleting popup : " + e);
		}
	}

	/**
	 * To click yes/No button in popup in Outside Home
	 * 
	 * @param deleteItem
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoBtnToDeleteItemInOutsideHome(String deleteItem, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (deleteItem) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnYesDeleteItemOutsideHome);
				GenericUtils.clickElementJS(driver, btnYesDeleteItemOutsideHome);
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnNoDeleteItemOutsideHome);
				GenericUtils.clickElementJS(driver, btnNoDeleteItemOutsideHome);
				WaitUtils.waitForSpinner(driver);
				break;
			}
			WaitUtils.waitForPageLoad(driver);
			Log.message("Clicked on " + deleteItem + " button in Item deleting Popup", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking button in Item deleting popup : " + e);
		}
	}

	/**
	 * To click yes or No  to add personal possessions which are taken outside the home
	 * 
	 * @param answer
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoWantToAddAnotherItemWithinHome(String addAnotherItemWithinHome, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (addAnotherItemWithinHome.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnAddAnotherItemWithinHomeYes);
				btnAddAnotherItemWithinHomeYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAddAnotherItemWithinHomeNo);
				btnAddAnotherItemWithinHomeNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked option for want to Add Another Item Within " + addAnotherItemWithinHome + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * Editing Add Item Within Home
	 * 
	 * @param itemName
	 * @param itemType
	 * @param itemValue
	 * @param itemDecsription
	 * @param saveAndCancel
	 * @param extentReport
	 * @param screenshot
	 *
	 */
	public void editAddItemWithinHome(String itemName,String itemType, String itemValue, String itemDecsription, String saveAndCancel, ExtentTest extentReport,
			boolean screenshot) throws Exception {

		try {
			String rowNum = getAddedItemRowInWithinHome(itemName, extentedReport, false);
			WebElement edit = driver.findElement(By.cssSelector(cssEditAddedItemInWithinHome + String.valueOf(rowNum)));
			WaitUtils.waitForElement(driver,edit);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", edit);
			WaitUtils.waitForElement(driver, drpdwnItemtypeNewSpecifiedItemWithinHome);
			selectItemTypeOfSpecifiedItemsWithinHome(itemType,extentedReport,true);
			//enterItemValueOfSpecifiedItemsWithinHome(itemValue,extentedReport,true);
			enterDescriptionOfItemOfSpecifiedItemsWithinHome(itemDecsription, extentedReport,true);
			switch (saveAndCancel) {
			case "Save":
				WaitUtils.waitForElement(driver, btnSaveItemNewSpecifiedItemWithinHome);
				clickSaveItemNewSpecifiedItemWithinHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on Save Occupation", driver, extentedReport);
				break;

			case "Cancel":
				WaitUtils.waitForElement(driver,btnCancelItemNewSpecifiedItemWithinHome);
				clickCancelNewSpecifiedItemWithinHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				break;
			}			
			Log.message("TCNO_17781_Specified item details are edited successfuly", driver, extentedReport, true);
		} catch (Exception e) {
			Log.message("TCNO_17781_Getting error while editing Specified item details in Within Home : " + e);
		}
	}

	/**
	 * Editing Add Item Outside Home
	 * 
	 * @param itemName
	 * @param itemType
	 * @param itemValue
	 * @param itemDecsription
	 * @param saveAndCancel
	 * @param extentReport
	 * @param screenshot
	 *
	 */
	public void editAddItemOutsideHome(String itemName,String itemType, String itemValue, String itemDecsription, String saveAndCancel, ExtentTest extentReport,
			boolean screenshot) throws Exception {

		try {
			String rowNum = getAddedItemRowInOutsideHome(itemName, extentedReport, false);
			WebElement edit = driver.findElement(By.cssSelector(cssEditAddedItemInOutsideHome + String.valueOf(rowNum)));
			WaitUtils.waitForElement(driver,edit);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", edit);
			WaitUtils.waitForElement(driver, drpdwnItemtypeNewSpecifiedItemOutsideHome);
			selectItemTypeOfSpecifiedItemsOutsideHome(itemType,extentedReport,true);
			//enterItemValueOfSpecifiedItemsOutsideHome(itemValue,extentedReport,true);
			enterDescriptionOfItemOfSpecifiedItemsOutsideHome(itemDecsription, extentedReport,true);
			switch (saveAndCancel) {
			case "Save":
				WaitUtils.waitForElement(driver, btnSaveItemNewSpecifiedItemOutsideHome);
				clickSaveItemNewSpecifiedItemOutsideHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on Save Occupation", driver, extentedReport);
				break;

			case "Cancel":
				WaitUtils.waitForElement(driver,btnCancelItemNewSpecifiedItemOutsideHome);
				clickCancelNewSpecifiedItemOutsideHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				break;
			}			
			Log.message("TCNO_17791_Specified item details are edited successfuly", driver, extentedReport, true);
		} catch (Exception e) {
			Log.message("TCNO_17791_Getting error while editing Specified item details in Outside Home : " + e);
		}
	}

	/**
	 * Verifies deleting Add Item details which are added
	 * 
	 * @param itemName
	 * @param empStatus
	 * @param extentReport
	 * @param screenshot
	 *
	 */
	public void deleteAddItemInWithinHome(String itemName, String deleteItem, ExtentTest extenteReport, boolean screenshot) throws Exception {
		try {
			String rowNum = getAddedItemRowInWithinHome(itemName, extentedReport, false);
			WaitUtils.waitForElement(driver,
					driver.findElement(By.cssSelector(cssDeleteAddedItemInWithinHome + String.valueOf(rowNum))));

			WebElement delete = driver.findElement(By.cssSelector(cssDeleteAddedItemInWithinHome + String.valueOf(rowNum)));
			WaitUtils.waitForElement(driver,delete);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", delete);
			WaitUtils.waitForSpinner(driver);
			clickYesOrNoBtnToDeleteItemInWithinHome(deleteItem, extentedReport, true);
			Log.message("TCNO_17782_Deleted the existed Add Item details", driver, extentedReport);
			WaitUtils.waitForSpinner(driver);
			
		} catch (Exception e) {
			Log.message("TCNO_17782_Error while deleting the existed Add Item details : " + e);
		}
	}

	/**
	 * Verifies deleting Add Item details which are added
	 * 
	 * @param itemName
	 * @param empStatus
	 * @param extentReport
	 * @param screenshot
	 *
	 */
	public void deleteAddItemInOutsideHome(String itemName, String deleteItem, ExtentTest extenteReport, boolean screenshot) throws Exception {
		try {
			String rowNum = getAddedItemRowInOutsideHome(itemName, extentedReport, false);
			WaitUtils.waitForElement(driver,
					driver.findElement(By.cssSelector(cssDeleteAddedItemInOutsideHome + String.valueOf(rowNum))));
			WebElement delete = driver.findElement(By.cssSelector(cssDeleteAddedItemInOutsideHome + String.valueOf(rowNum)));
			WaitUtils.waitForElement(driver,delete);
			delete.click();
			WaitUtils.waitForSpinner(driver);
			clickYesOrNoBtnToDeleteItemInOutsideHome(deleteItem, extentedReport, true);
			Log.message("TCNO_17792_Deleted the existed Add Item details", driver, extentedReport);
			WaitUtils.waitForSpinner(driver);

		} catch (Exception e) {
			Log.message("TCNO_17792_Error while deleting the existed Add Item details : " + e);
		}
	}

	/**
	 * To Add Specified Item within Home
	 * 
	 * @param itemType, itemValue, itemDecsription, saveAndCancel
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void addSpecifiedItemwithinHome(String itemType, String itemValue, String itemDecsription, String saveAndCancel, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			selectItemTypeOfSpecifiedItemsWithinHome(itemType,extentedReport,true);
			enterItemValueOfSpecifiedItemsWithinHome(itemValue,extentedReport,true);
			enterDescriptionOfItemOfSpecifiedItemsWithinHome(itemDecsription, extentedReport,true);
			switch (saveAndCancel) {
			case "Save":
				clickSaveItemNewSpecifiedItemWithinHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				break;

			case "Cancel":
				clickCancelNewSpecifiedItemWithinHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("TCNO_17777_Added Specified item within home in Add Contents cover page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17777_Error while adding the Specified item within home in Add Contents cover page: " + e);
		}
	}

	/**
	 * To Add Specified Item outside Home
	 * 
	 * @param itemType, itemValue, itemDecsription, saveAndCancel
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void addSpecifiedItemOutsideHome(String itemType, String itemValue, String itemDecsription, String saveAndCancel, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			selectItemTypeOfSpecifiedItemsOutsideHome(itemType,extentedReport,true);
			enterItemValueOfSpecifiedItemsOutsideHome(itemValue,extentedReport,true);
			enterDescriptionOfItemOfSpecifiedItemsOutsideHome(itemDecsription, extentedReport,true);
			switch (saveAndCancel) {
			case "Save":
				clickSaveItemNewSpecifiedItemOutsideHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				break;

			case "Cancel":
				clickCancelNewSpecifiedItemOutsideHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				break;
			}

			Log.message("TCNO_17793_Added Specified item outside home in Add Contents cover page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17793_Error while adding the Specified item outside home in Add Contents cover page: " + e);
		}
	}

	/**
	 * To add the address
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterAddressOfPropToBeInsured(String postCode, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			enterPostCode(postCode, extentedReport, screenshot);
			clickFindAddressBtn(extentedReport, screenshot);
			selectAddress(extentedReport, screenshot);
			clickSaveAddressBtn(extentedReport, screenshot);
			Log.message("Clicked on Save Address button", driver, extentedReport, screenshot);
			WaitUtils.waitForPageLoad(driver, 30);
		} catch (Exception e) {
			throw new Exception("Error while Adding address in the Property to be insured accordion : " + e);
		}
	}
	
	public void enterPostCode(String postCode,ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForelementToBeClickable(driver, txtPostcodeOfProperty, "Field 'Postcode of property to be insured' is not found");
			WaitUtils.waitForElement(driver, txtPostcodeOfProperty);
			txtPostcodeOfProperty.clear();
			txtPostcodeOfProperty.sendKeys(postCode);
			Log.message("Entered the Postcode : " + postCode, driver, extentedReport, false);

		} catch (Exception e) {
			throw new Exception(
					"Error while entering Change Overnight Location Detail : " + postCode + "manually : " + e);
		}
	}
	
	/**
	 * Click Find Address Button
	 *
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickFindAddressBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			btnFindAddress.click();
			WaitUtils.waitForElement(driver, selectChooseAddress);
			Log.message("Clicked on Find Address button in change my policy address screen", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Unable to click Find Address button in change my policy address screen : " + e);
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
			if (WaitUtils.waitForElement(driver, selectChooseAddress)) {
				Select drpAddress = new Select(selectChooseAddress);
				drpAddress.selectByIndex(1);
				WaitUtils.waitForElement(driver, txtAddressLine1);
				Log.message("Address selected from address list", driver, extentedReport, screenshot);
			} else {
				throw new Exception("Drop down to select overnight Address is not found");
			}
		} catch (Exception e) {
			throw new Exception("Unable to select address : " + e);
		}
	}
	
	/**
	 * Click Save Address Button
	 *
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickSaveAddressBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			//btnSaveAddress.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnSaveAddress);
			WaitUtils.waitForSpinner(driver);
			if (WaitUtils.waitForElement(driver, btnSaveAddress)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnSaveAddress);
				//btnSaveAddress.click();
			}
			Log.message("Clicked on Save Address button in change my policy address screen", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Unable to click Save Address button in change my policy address screen : " + e);
		}
	}
	
	/**
	 * Click Cancel Address Button
	 *
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickCancelAddressBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			btnCancelAddress.click();
			WaitUtils.waitForSpinner(driver);
			if (WaitUtils.waitForElement(driver, btnCancelAddress)) {
				btnCancelAddress.click();
			}
			Log.message("Clicked on Cacenl Address button in change my policy address screen", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Unable to click Cancel Address button in change my policy address screen : " + e);
		}
	}

	/**
	 * To add the address
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void editAddedAddressOfProp(String postCode,ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			clickChangeAddressBtn(extentedReport, screenshot);
			
			enterPostCode(postCode, extentedReport, screenshot);
			clickFindAddressBtn(extentedReport, screenshot);
			selectAddress(extentedReport, screenshot);
			clickCancelAddressBtn(extentedReport, screenshot);
			Log.message("Clicked on Save Address button", driver, extentedReport, screenshot);
			WaitUtils.waitForPageLoad(driver, 30);

			Log.message("TCNO_17704_Clicked on Cancel Address button while editing the address", driver, extentedReport, screenshot);
			WaitUtils.waitForPageLoad(driver, 30);
		} catch (Exception e) {
			throw new Exception("TCNO_17704_Error while Cancel the adding address in the Property to be insured accordion while editing the address : " + e);
		}
	}

	/**
	 * To verify the saved address
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void verifySavedAddress(String postCode, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElementPresent(driver, btnChangeAddress, "The Change Address button is not available");

			String savedAddress = lblAddressAdded.getText();
			if(savedAddress.contains(postCode)){
				Log.message("Post code provided was saved", extentedReport);
			}
			else{
				Log.message("Post code provided was not saved", extentedReport);
			}

			WaitUtils.waitForPageLoad(driver, 30);
		} catch (Exception e) {
			throw new Exception("Error while Adding affress in the Property to be insured accordion : " + e);
		}
	}

	/**
	 * To click YES button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesButtonUnderYourCorrespondenceAddress(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnYesforConfirmation);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnYesforConfirmation);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17705_17706_17701_Clicked on 'YES' button under 'Your Correspondence Address'", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17705_17706_17701_Error while clicking 'YES' button under 'Your Correspondence Address' : " + e);
		}
	}
	
	/**
	 * To click NO button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNoButtonUnderYourCorrespondenceAddress(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnNoforConfirmation);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNoforConfirmation);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17705_17706_17701_Clicked on 'NO' button under 'Your Correspondence Address'", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17705_17706_17701_Error while clicking 'NO' button under 'Your Correspondence Address' : " + e);
		}
	}

	/**
	 * To click YES button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesButtonInOverlay(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnYesForOverlay);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnYesForOverlay);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17707_17708_Clicked on 'YES' button in the overlay", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'YES' in the overlay : " + e);
		}
	}

	/**
	 * To click YES button
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNoButtonInOverlay(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnNoForOverlay);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNoForOverlay);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17709_Clicked on 'NO' button in the overlay", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'NO' in the overlay : " + e);
		}
	}

	/**
	 * To add the other fields under The property to be insured accordion
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean enterOtherdetailsPropToBeInsured(String date, String yearOfBuilt, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			boolean status = false;
			WaitUtils.waitForelementToBeClickable(driver, selectTypeOfProperty, "Field 'Type of property' is not found");

			selectTypeOfProperty(extentedReport, screenshot);
			enterDateOfResidence(date, extentedReport, screenshot);
			
			enterYearOfBuilt(yearOfBuilt, extentedReport, screenshot);
			selectNoOfShower(extentedReport, screenshot);
			selectNoOfBed(extentedReport, screenshot);
			clickNextYourCorrAddressBtn(extentedReport, screenshot);
			
			status = true;
			Log.message("Clicked on NEXT button after providing other details in the Property to be insured", driver, extentedReport, screenshot);
			WaitUtils.waitForPageLoad(driver, 30);

			return status;

		} catch (Exception e) {
			throw new Exception("Error while Clicking on NEXT button after providing other details in the Property to be insured: " + e);
		}
	}
	
	/**
	 * To enter date Of Residence
	 * 
	 * @param date
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterDateOfResidence(String date, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, txtDateOfResidence);
			txtDateOfResidence.clear();
			Thread.sleep(1000);
			txtDateOfResidence.click();
			txtDateOfResidence.sendKeys(date);
			txtDateOfResidence.click();
			WaitUtils.waitForSpinner(driver);
			txtDateOfResidence.sendKeys(Keys.TAB);
			WaitUtils.waitForSpinner(driver);
			txtYearOfBuilt.click();
			Log.message("Entered the Date date Of Residence : " + date, driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering date Of Residence : " + e);
		}
	}
	
	/**
	 * To select No Of Shower
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectNoOfShower(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			if (WaitUtils.waitForElement(driver, selectNoOfShower)) {
				Select drpAddress = new Select(selectNoOfShower);
				drpAddress.selectByIndex(1);
				WaitUtils.waitForElement(driver, selectNoOfBed);
				Log.message("select No Of Shower selected from address list", driver, extentedReport, screenshot);
			} else {
				throw new Exception("Drop down to select No Of Shower is not found");
			}
		} catch (Exception e) {
			throw new Exception("Unable to select No Of Shower : " + e);
		}
	}
	
	/**
	 * To select No Of Bed
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectNoOfBed(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			if (WaitUtils.waitForElement(driver, selectNoOfBed)) {
				Select drpAddress = new Select(selectNoOfBed);
				drpAddress.selectByIndex(1);
				WaitUtils.waitForElement(driver, txtDateOfResidence);
				Log.message("select No Of Shower selected from address list", driver, extentedReport, screenshot);
			} else {
				throw new Exception("Drop down to select No Of Shower is not found");
			}
		} catch (Exception e) {
			throw new Exception("Unable to select No Of Shower : " + e);
		}
	}
	
	/**
	 * To select Type Of Property
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectTypeOfProperty(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			if (WaitUtils.waitForElement(driver, selectTypeOfProperty)) {
				Select drpAddress = new Select(selectTypeOfProperty);
				drpAddress.selectByIndex(1);
				WaitUtils.waitForElement(driver, txtDateOfResidence);
				Log.message("Type of property selected from address list", driver, extentedReport, screenshot);
			} else {
				throw new Exception("Drop down to select Type of property is not found");
			}
		} catch (Exception e) {
			throw new Exception("Unable to select Type of property : " + e);
		}
	}
	
	public void enterYearOfBuilt(String yearOfBuilt,ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, txtYearOfBuilt);
			txtYearOfBuilt.clear();
			txtYearOfBuilt.sendKeys(yearOfBuilt);
			Log.message("Entered the year of built : " + yearOfBuilt, driver, extentedReport, false);

		} catch (Exception e) {
			throw new Exception(
					"Error while entering year of built Detail : " + yearOfBuilt + e);
		}
	}
	
	/**
	 * Click Next button in Your Correspondence Address
	 *
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextYourCorrAddressBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			GenericUtils.scrollIntoView(driver, btnNextYourCorrAddress);
			WaitUtils.waitForelementToBeClickable(driver, btnNextYourCorrAddress, "Next button is not found");
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", btnNextYourCorrAddress);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Next button in Your Correspondence Address", driver, extentedReport, screenshot);
			WaitUtils.waitForPageLoad(driver, 30);
		} catch (Exception e) {
			throw new Exception("Unable to click Next button in Your Correspondence Address : " + e);
		}
	}

	public boolean VerfiyOtherdetailsPropToBeInsured(ExtentTest extentedReport) throws Exception
	{
		try
		{             
			if(selectTypeOfProperty.isDisplayed()){
				return false;
			}
			else{
				return true;
			}

		}
		catch(Exception e)
		{      
			throw new Exception("Error while Verfiying Other details Property To Be Insured Qeustions : " + e.getMessage());
		}
	}

	/**
	 * To add the details under The building construction
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterDetailsforBuildingConstruction(String perOfPropRoof, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElementPresent(driver, typeOfWall, "Field 'Type of wall' is not found");

			enterPerOfPropRoof(perOfPropRoof, extentedReport, screenshot);
			clickNoInUnderPinnedBtn(extentedReport, screenshot);
			clickNextInBuildingConstBtn(extentedReport, screenshot);

			Log.message("Clicked on NEXT button after building Construction", driver, extentedReport, screenshot);
			WaitUtils.waitForPageLoad(driver, 30);
		} catch (Exception e) {
			throw new Exception("Error while Clicking on NEXT button after building Construction: " + e);
		}
	}
	
	public void enterPerOfPropRoof(String PerOfPropRoof,ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, txtPerOfPropRoof);
			txtPerOfPropRoof.clear();
			txtPerOfPropRoof.sendKeys(PerOfPropRoof);
			Log.message("Entered the Percentage Of Property  Roof : " + PerOfPropRoof, driver, extentedReport, false);

		} catch (Exception e) {
			throw new Exception(
					"Error while entering year of built Detail : " + PerOfPropRoof + e);
		}
	}
	
	/**
	 * Click Next button in Building construction
	 *
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextInBuildingConstBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			GenericUtils.scrollIntoView(driver, btnNextBuildingConst);
			WaitUtils.waitForelementToBeClickable(driver, btnNextBuildingConst, "Next button is not found");
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", btnNextBuildingConst);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on Next button in Building construction", driver, extentedReport, screenshot);
			WaitUtils.waitForPageLoad(driver, 30);
		} catch (Exception e) {
			throw new Exception("Unable to click Next button in Building construction : " + e);
		}
	}
	
	/**
	 * Click No Button in Under pinned
	 *
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNoInUnderPinnedBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			GenericUtils.scrollIntoView(driver, hasPropPinnedNo);
			WaitUtils.waitForelementToBeClickable(driver, hasPropPinnedNo, "No button is not found");
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", hasPropPinnedNo);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on No button in Under pinned", driver, extentedReport, screenshot);
			WaitUtils.waitForPageLoad(driver, 30);
		} catch (Exception e) {
			throw new Exception("Unable to click Np button in Under pinned : " + e);
		}
	}

	/**
	 * To verify the restore of data for Property Extended question
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInBuildingConstructionPropertyExtended(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElementPresent(driver, typeOfWall, "Field 'Type of wall' is not found");

			if(hasPropExtendedNo1.getAttribute("checked") != null){
				Log.message("TCNO_17720_The value for Has the property been extended? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17720_The value for Has the property been extended? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Property Extended qestion: " + e);
		}

	}

	/**
	 * To verify the restore of data for Property Used for Business question
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInBuildingConstructionPropertyUsedforBusiness(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElementPresent(driver, hasPropExtendedNo, "Field 'Property Extended' question is not found");

			if(isBusinessPropUsedForBusienssNo1.getAttribute("checked") != null){
				Log.message("TCNO_17721_The value for Is the property used for business purposes? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17721_The value for Is the property used for business purposes? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Property Used for Business question: " + e);
		}

	}

	/**
	 * To verify the restore of data for Property Free From Substance question
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInBuildingConstructionPropertyFreeFromSubsidence(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			WaitUtils.waitForElementPresent(driver, isBusinessPropUsedForBusienssNo, "Field 'Property Used for Business' questionis not found");

			if(isFreeFromSubsidenceYes1.getAttribute("checked") != null){
				Log.message("TCNO_17723_The value for Is the property free from subsidence? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17723_The value for Is the property free from subsidence? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Property Free From Subsidence: " + e);
		}

	}

	/**
	 * To verify the restore of data under for Property Free From Flooding question
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInBuildingConstructionPropertyFreeFromFlooding(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElementPresent(driver, isFreeFromSubsidenceYes, "Field 'Property Free From Subsidence' question is not found");

			if(isFreeFromFloodingYes1.getAttribute("checked") != null){
				Log.message("TCNO_17724_The value for Is the property free from flooding? is restored", extentedReport);
				return true;

			}
			else {
				Log.message("TCNO_17724_The value for Is the property free from flooding? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Property Free From Flooding: " + e);
		}

	}

	/**
	 * To verify the restore of data for Property Ever Been Underpinned question
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInBuildingConstructionPropertyEverBeenUnderpinned(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElementPresent(driver, isFreeFromFloodingYes, "Field 'Property Free From Flooding' question is not found");

			if(hasPropPinnedYes1.getAttribute("checked") != null){
				Log.message("TCNO_17725_The value for Has the property ever been underpinned? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17725_The value for Has the property ever been underpinned? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Property Ever Been Underpinned: " + e);
		}

	}

	/**
	 * To verify the restore of data for Property Within 400 Metres Of Water question
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInBuildingConstructionPropertyWithin400MetresOfWater(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElementPresent(driver, hasPropPinnedYes, "Field 'Property Ever Been Underpinned' question is not found");


			if(isPropWithin400mNo1.getAttribute("checked") != null){
				Log.message("TCNO_17726_The value for Is the property within 400 metres (¼ mile)of water? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17726_The value for Is the property within 400 metres (¼ mile)of water? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Property Within 400 Metres Of Water: " + e);
		}

	}

	/**
	 * To verify the restore of data for Any Trees question
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInBuildingConstructionAnyTrees(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElementPresent(driver, isPropWithin400mNo, "Field 'Property Within 400 Metres Of Water' qestion is not found");

			if(isAnyTreesNo1.getAttribute("checked") != null){
				Log.message("TCNO_17727_The value for Are there any trees or shrubs taller than 5 metres(16ft) within 7 metres (22ft) of your property? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17727_The value for Are there any trees or shrubs taller than 5 metres(16ft) within 7 metres (22ft) of your property? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Any Trees: " + e);
		}

	}

	/**
	 * To verify the restore of data for Property Good State question
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInBuildingConstructionIsPropertyGoodState(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElementPresent(driver, isAnyTreesNo, "Field 'Any Trees' question is not found");

			if(isPropGoodStateYes1.getAttribute("checked") != null){
				Log.message("TCNO_17728_The value for Is the property in a good state of repair? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17728_The value for Is the property in a good state of repair? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Property Good State: " + e);
		}

	}

	/**
	 * To verify the restore of data for Property Self Contained
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInBuildingConstructionIsPropertySelfContained(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElementPresent(driver, isPropGoodStateYes, "Field 'Property Good State' question is not found");

			if(isPropSelfContainedYes1.getAttribute("checked") != null){
				Log.message("TCNO_17729_The value for Is the property self-contained and lockable? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17729_The value for Is the property self-contained and lockable? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Property Self Contained: " + e);
		}

	}

	/**
	 * To verify the restore of data for Is Property Occupied In Day
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInSecuritySectionIsPropertyOccupiedInDay(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			if(isPropOccupiedInDayNo1.getAttribute("checked") != null){
				Log.message("TCNO_17731_The value for Is the property occupied during the day? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17731_The value for Is the property occupied during the day? is NOT restored", extentedReport);
				return false;
			}	

		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Is Property Occupied In Day: " + e);
		}
	}

	/**
	 * To verify the restore of data for Is Property Occupied In Night
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInSecuritySectionIsPropertyOccupiedInNight(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			if(isPropOccupiedInNightYes1.getAttribute("checked") != null){
				Log.message("TCNO_17732_The value for Is the property occupied at night? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17732_The value for Is the property occupied at night? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Is Property Occupied In Night: " + e);
		}
	}

	/**
	 * To verify the restore of data for Is Alarm Installed
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInSecuritySectionIsAlarmInstalled(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			if(isAlarmInstalledNo1.getAttribute("checked") != null){
				Log.message("TCNO_17733_The value for Does the property have an alarm installed? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17733_The value for Does the property have an alarm installed? is NOT restored", extentedReport);
				return false;
			}

		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Is Alarm Installed: " + e);
		}
	}

	/**
	 * To verify the restore of data for Is British Kitemark
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInSecuritySectionIsBritishKitemark(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			if(isBritishKitemarkNo1.getAttribute("checked") != null){
				Log.message("TCNO_17735_The value for Are British Standard Kitemark (BS3621) approved locks fitted? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17735_The value for Are British Standard Kitemark (BS3621) approved locks fitted? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Is British Kitemark: " + e);
		}
	}

	/**
	 * To verify the restore of data for Does Property Have External Light
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInSecuritySectionDoesPropertyHaveExternalLight(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			if(doesPropertyHaveExternalLightNo1.getAttribute("checked") != null){
				Log.message("TCNO_17736_The value for Does the property have external security lighting? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17736_The value for Does the property have external security lighting? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Does Property Have External Light: " + e);
		}
	}

	/**
	 * To verify the restore of data for Are Locks Activated At Night
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInSecuritySectionAreLocksActivatedAtNight(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			if(areLocksActivatedAtNightNo1.getAttribute("checked") != null){
				Log.message("TCNO_17737_The value for Are all locks activated at night and when the property is unoccupied? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17737_The value for Are all locks activated at night and when the property is unoccupied? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Are Locks Activated At Night: " + e);
		}
	}

	/**
	 * To verify the restore of data for Are You A Neighbour
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInSecuritySectionAreYouANeighbour(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			if(areYouANeighbourNo1.getAttribute("checked") != null){
				Log.message("TCNO_17738_The value for Are you a member of an active Neighbourhood Watch Scheme? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17738_The value for Are you a member of an active Neighbourhood Watch Scheme? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Are You A Neighbour: " + e);
		}
	}

	/**
	 * To verify the restore of data for Is Property Fitted Smoke
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInSecuritySectionIsPropertyFittedSmoke(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			if(isPropFittedSmokeNo1.getAttribute("checked") != null){
				Log.message("TCNO_17739_The value for Is the property fitted with a smoke detector? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17739_The value for Is the property fitted with a smoke detector? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Is Property Fitted Smoke: " + e);
		}
	}

	/**
	 * To verify the restore of data for Is Safe Installed
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyRestoreInSecuritySectionIsSafeInstalled(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {

			if(isSafeInstalledNo1.getAttribute("checked") != null){
				Log.message("TCNO_17740_The value for Is there a safe installed within the property? is restored", extentedReport);
				return true;
			}
			else {
				Log.message("TCNO_17740_The value for Is there a safe installed within the property? is NOT restored", extentedReport);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Error while verifing restored values in Is Safe Installed: " + e);
		}
	}

	/**
	 * Click Next in security details
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextSecurityDetails(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnNextSecurityAccrn);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextSecurityAccrn);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Next' button of Security Details in Your Property page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking on 'Next' button of Security Details in Your Property page: " + e);
		}
	}

	/**
	 * To enter RebuildCost Of Home
	 * 
	 * @param RebuildCost Of Home Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterRebuildCostOfHome(String rebuildCost, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldRebuildCost);
			fldRebuildCost.clear();
			fldRebuildCost.sendKeys(rebuildCost);
			Log.message(rebuildCost + " - Rebuild Cost is enterd in the Add Buildings cover", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Rebuild Cost on Add Buildings cover: " + e);
		}

	}

	/**
	 * To select Total Excess Required
	 * 
	 * @param Excess Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectExcessValueB(String excessvalueB, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnTotalExcessRequiredB);
			Select coverSelect = new Select(drpdwnTotalExcessRequiredB);
			coverSelect.selectByVisibleText(excessvalueB);
			WaitUtils.waitForElement(driver, btnPreviousBuildingInsuranceYes);
			Log.message("TCNO_17743_Selected Excess Value : " + excessvalueB, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("TCNO_17743_Error while selecting Total Excess Required on Add Building cover page : " + e);
		}
	}

	/**
	 * To select No Of Years Without Claiming 
	 * 
	 * @param No Of Years Without Claiming Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectBuildingsInsuranceWithoutClaiming(String noOfYearsWithoutClaimingValue, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnNoOfYearsWithoutClaimingB);
			Select coverSelect = new Select(drpdwnNoOfYearsWithoutClaimingB);
			coverSelect.selectByVisibleText(noOfYearsWithoutClaimingValue);
			WaitUtils.waitForSpinner(driver);                                    
			Log.message("TCNO_17744_Selected Cover Type : " + noOfYearsWithoutClaimingValue, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("TCNO_17744_Error while selecting Buildings Insurance With out Claiming on Add Building cover page : " + e);
		}
	}

	/**
	 * To select Name Of Previous Insurer
	 * 
	 * @param Name Of Previous Insurer Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectNameOfPreviousInsurerB(String nameOfPreviousInsurerValueB, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnNameOfPreviousInsurerB);
			Select coverSelect = new Select(drpdwnNameOfPreviousInsurerB);
			coverSelect.selectByVisibleText(nameOfPreviousInsurerValueB);
			WaitUtils.waitForElement(driver, fldPolicyNumberOfPreviousInsuranceB);
			Log.message("TCNO_17768_Selected Cover Type : " + nameOfPreviousInsurerValueB, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("TCNO_17768_Error while selecting Name of Previous Insurer Value on Add Building cover page : " + e);
		}
	}

	/**
	 * To enter Previous Insurance Policy Number
	 * 
	 * @param Previous Insurance Policy Number
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterPolicyNumberOfPreviousInsuranceB(String policyNumberOfPreviousInsuranceB, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldPolicyNumberOfPreviousInsuranceB);
			fldPolicyNumberOfPreviousInsuranceB.clear();
			fldPolicyNumberOfPreviousInsuranceB.sendKeys(policyNumberOfPreviousInsuranceB);
			Log.message(policyNumberOfPreviousInsuranceB + " - Vehicle Registration Number is enterd in the ChangeMyCar Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering policy Number Of Previous Insurance on Add Building cover: " + e);
		}

	}

	/**
	 * To enter Expire Date Of Previous Insurance
	 * 
	 * @param Expire Date Of Previous Insurance Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterExpiryDateOfPreviousInsuranceB(String expiryDateOfPreviousInsuranceB, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldExpiryDateOfPreviousInsuranceB);
			fldExpiryDateOfPreviousInsuranceB.clear();
			Thread.sleep(1000);
			fldExpiryDateOfPreviousInsuranceB.click();
			fldExpiryDateOfPreviousInsuranceB.sendKeys(expiryDateOfPreviousInsuranceB);
			fldExpiryDateOfPreviousInsuranceB.click();
			WaitUtils.waitForSpinner(driver);
			fldExpiryDateOfPreviousInsuranceB.sendKeys(Keys.TAB);
			Log.message("Entered the Date date for Expiry Date Of Previous Insurance : " + expiryDateOfPreviousInsuranceB, driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering date for Expiry Date Of Previous Insurance : " + e);
		}
	}

	/**
	 * To select Pay For Your Previous Insurance
	 * 
	 * @param Pay For Your Previous Insurance Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectPayForYourPreviousInsuranceB(String payForYourPreviousInsuranceValueB, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnPayForYourPreviousInsuranceB);
			Select coverSelect = new Select(drpdwnPayForYourPreviousInsuranceB);
			coverSelect.selectByVisibleText(payForYourPreviousInsuranceValueB);
			WaitUtils.waitForElement(driver, drpdwnNoOfYearsWithoutClaimingB);
			Log.message("TCNO_17767_Selected Cover Type : " + payForYourPreviousInsuranceValueB, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("TCNO_17767_Error while selecting Name of Previous Insurer Value on Add Building cover page : " + e);
		}
	}

	/**
	 * To click yes or No Require Accidental Damage
	 * 
	 * @param Accidental Damage
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoRequireAccidentalDamage(String accidentalDamage, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (accidentalDamage.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnrequireAccidentalDamageYes);
				btnrequireAccidentalDamageYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnrequireAccidentalDamageNo);
				btnrequireAccidentalDamageNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked option for want to add Require Accidental Damage Cover : " + accidentalDamage + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * To click yes or No Previous Building Insurance
	 * 
	 * @param previousBuildingInsurance
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoPreviousBuildingInsurance(String previousBuildingInsurance, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (previousBuildingInsurance.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnPreviousBuildingInsuranceYes);
				btnPreviousBuildingInsuranceYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnPreviousBuildingInsuranceNo);
				btnPreviousBuildingInsuranceNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked option for want to add Previous Building Insurance : " + previousBuildingInsurance + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking radio button : " + e);
		}
	}

	/**
	 * To enter Home Contents Value
	 * 
	 * @param valueOfHomeContents
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterTotalValueOfYourHomeContents(String valueOfHomeContents, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldTotalValueOfYourHomeContents);
			fldTotalValueOfYourHomeContents.clear();
			fldTotalValueOfYourHomeContents.sendKeys(valueOfHomeContents);
			Log.message(valueOfHomeContents + " - Home Contents Value is enterd on Add Contents cover", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Home Contents Value on Add Buildings cover: " + e);
		}

	}

	/**
	 * To enter Precious Or Costly Items value
	 * 
	 * @param PreciousOrCostlyItems
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterTotalValueOfPreciousOrCostlyItems(String valueOfPreciousOrCostlyItems, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldTotalValueOfPreciousOrCostlyItems);
			fldTotalValueOfPreciousOrCostlyItems.clear();
			fldTotalValueOfPreciousOrCostlyItems.sendKeys(valueOfPreciousOrCostlyItems);
			Log.message(valueOfPreciousOrCostlyItems + " - Precious Or Costly Items Value is enterd on Add Contents cover", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Precious Or Costly Items Value on Add Buildings cover: " + e);
		}

	}

	/**
	 * To enter Most Expensive Item value
	 * 
	 * @param ValueOfMostExpensiveItem
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterValueOfMostExpensiveItem(String valueOfMostExpensiveItem, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldValueOfMostExpensiveItem);
			fldValueOfMostExpensiveItem.clear();
			fldValueOfMostExpensiveItem.sendKeys(valueOfMostExpensiveItem);
			Log.message(valueOfMostExpensiveItem + " - Most Expensive Item Value is enterd on Add Contents cover", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Most Expensive Item Value on Add Buildings cover: " + e);
		}

	}

	/**
	 * To select Type Of Home Contents Cover Required
	 * 
	 * @param TypeOfHomeContentsCoverRequired
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectTypeOfHomeContentsCoverRequired(String typeOfHomeContentsCoverRequired, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnTypeOfHomeContentsCoverRequired);
			Select coverSelect = new Select(drpdwnTypeOfHomeContentsCoverRequired);
			coverSelect.selectByVisibleText(typeOfHomeContentsCoverRequired);
			WaitUtils.waitForElement(driver, drpdwnTotalExcessRequiredC);
			Log.message("TCNO_17763_Selected Cover Type : " + typeOfHomeContentsCoverRequired, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("TCNO_17763_Error while selecting Type Of Home Contents Cover Required on Add Building cover page : " + e);
		}
	}

	/**
	 * To select Type Of Home Contents Cover Required
	 * 
	 * @param TypeOfHomeContentsCoverRequired
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectTotalExcessRequiredC(String totalExcessRequiredC, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnTotalExcessRequiredC);
			Select coverSelect = new Select(drpdwnTotalExcessRequiredC);
			coverSelect.selectByVisibleText(totalExcessRequiredC);
			WaitUtils.waitForElement(driver, btncontentsSumRepresentReplacementAmountYes);
			Log.message("TCNO_17764_Selected Cover Type : " + totalExcessRequiredC, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("TCNO_17764_Error while selecting Type Of Home Contents Cover Required on Add Building cover page : " + e);
		}
	}

	/**
	 * To select Name Of Previous Insurer
	 * 
	 * @param Name Of Previous Insurer Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectNameOfPreviousInsurerC(String nameOfPreviousInsurerC, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnNameOfPreviousInsurerC);
			Select coverSelect = new Select(drpdwnNameOfPreviousInsurerC);
			coverSelect.selectByVisibleText(nameOfPreviousInsurerC);
			WaitUtils.waitForElement(driver, fldPolicyNumberOfPreviousInsuranceC);
			Log.message("Selected Cover Type : " + nameOfPreviousInsurerC, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting Name of Previous Insurer Value on Add Building cover page : " + e);
		}
	}

	public boolean VerfiyPreviouslyContentsInsurancesQeustions(ExtentTest extentedReport) throws Exception
	{
		try
		{             
			if(drpdwnNameOfPreviousInsurerC.isDisplayed()){
				return false;
			}
			else{
				return true;
			}

		}
		catch(Exception e)
		{      
			throw new Exception("Error while Verfiying the Previously Contents Insurances Qeustions : " + e.getMessage());
		}
	}

	/**
	 * To enter Previous Insurance Policy Number
	 * 
	 * @param Previous Insurance Policy Number
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterPolicyNumberOfPreviousInsuranceC(String numberOfPreviousInsuranceC, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldPolicyNumberOfPreviousInsuranceC);
			fldPolicyNumberOfPreviousInsuranceC.clear();
			fldPolicyNumberOfPreviousInsuranceC.sendKeys(numberOfPreviousInsuranceC);
			Log.message(numberOfPreviousInsuranceC + " - Vehicle Registration Number is enterd in the ChangeMyCar Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering policy Number Of Previous Insurance on Add Building cover: " + e);
		}

	}

	/**
	 * To enter Expire Date Of Previous Insurance
	 * 
	 * @param Expire Date Of Previous Insurance Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterExpiryDateOfPreviousInsuranceC(String expiryDateOfPreviousInsuranceC, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldExpiryDateOfPreviousInsuranceC);
			fldExpiryDateOfPreviousInsuranceC.clear();
			Thread.sleep(1000);
			fldExpiryDateOfPreviousInsuranceC.click();
			fldExpiryDateOfPreviousInsuranceC.sendKeys(expiryDateOfPreviousInsuranceC);
			fldExpiryDateOfPreviousInsuranceC.click();
			WaitUtils.waitForSpinner(driver);
			fldExpiryDateOfPreviousInsuranceC.sendKeys(Keys.TAB);
			Log.message("Entered the Date date for Expiry Date Of Previous Insurance : " + expiryDateOfPreviousInsuranceC, driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering date for Expiry Date Of Previous Insurance : " + e);
		}
	}

	/**
	 * To select Pay For Your Previous Insurance
	 * 
	 * @param Pay For Your Previous Insurance Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectPayForYourPreviousInsuranceC(String payForYourPreviousInsuranceC, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnPayForYourPreviousInsuranceC);
			Select coverSelect = new Select(drpdwnPayForYourPreviousInsuranceC);
			coverSelect.selectByVisibleText(payForYourPreviousInsuranceC);
			WaitUtils.waitForElement(driver, drpdwnNoOfYearsWithoutClaimingC);
			Log.message("TCNO_17745_Selected Cover Type : " + payForYourPreviousInsuranceC, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("TCNO_17745_Error while selecting Name of Previous Insurer Value on Add Building cover page : " + e);
		}
	}

	/**
	 * To select No Of Years Without Claiming 
	 * 
	 * @param No Of Years Without Claiming Value
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectContentsInsuranceWithoutClaiming(String noOfYearsWithoutClaimingValue, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnNoOfYearsWithoutClaimingC);
			Select coverSelect = new Select(drpdwnNoOfYearsWithoutClaimingC);
			coverSelect.selectByVisibleText(noOfYearsWithoutClaimingValue);
			WaitUtils.waitForElement(driver, btnNextContentsInformation);
			Log.message("TCNO_17766_Selected Cover Type : " + noOfYearsWithoutClaimingValue, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("TCNO_17766_Error while selecting Buildings Insurance With out Claiming on Add Contents cover page : " + e);
		}
	}

	/**
	 * To enter Previous Insurance Policy Number
	 * 
	 * @param Previous Insurance Policy Number
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterCashKeptInYourHomeToBeInsured(String cashInYourHomeToBeInsured, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldCashKeptInYourHomeToBeInsured);
			fldCashKeptInYourHomeToBeInsured.clear();
			fldCashKeptInYourHomeToBeInsured.sendKeys(cashInYourHomeToBeInsured);
			Log.message(cashInYourHomeToBeInsured + " - Cash Kept In Your Home To Be Insured is enterd in the Add Contents cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Cash Kept In Your Home To Be Insured in the Add Contents cover Page: " + e);
		}

	}

	/**
	 * To enter Credit Cards To Be Insured
	 * 
	 * @param creditCardsToBeInsured
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void entercreditCardsToBeInsured(String creditCardsToBeInsured, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldCreditCardsToBeInsured);
			fldCreditCardsToBeInsured.clear();
			fldCreditCardsToBeInsured.sendKeys(creditCardsToBeInsured);
			Log.message(creditCardsToBeInsured + " - Credit Cards To Be Insured is enterd in the Add Contents cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Credit Cards To Be Insured in the Add Contents cover Page: " + e);
		}

	}

	/**
	 * To enter Frozen Food To Be Insured
	 * 
	 * @param frozenFoodToBeInsured
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterfrozenFoodToBeInsured(String frozenFoodToBeInsured, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			WaitUtils.waitForElement(driver, fldFrozenFoodToBeInsured);
			fldFrozenFoodToBeInsured.clear();
			fldFrozenFoodToBeInsured.sendKeys(frozenFoodToBeInsured);
			Log.message(frozenFoodToBeInsured + " - Frozen Food To Be Insured is enterd in the Add Contents cover Page", driver,
					extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while entering Frozen Food To Be Insured in the Add Contents cover Page: " + e);
		}

	}

	/**
	 * To click Next button Additional cover
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextAdditionalCovers(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnNextAdditionalCovers);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextAdditionalCovers);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Next' button of Additional cover in Add Contents cover page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking on 'Next' button of Additional cover in Add Contents cover page: " + e);
		}
	}

	/**
	 * To click Next button Additional cover
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextBuildingsCoverDetails(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnNextBuildingsCoverDetails);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextBuildingsCoverDetails);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Next' button of Buildings cover details in Your Property page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking on 'Next' button of Buildings cover details in Your Property page: " + e);
		}
	}

	/**
	 * To click Next button Additional cover
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickContinue(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnContinue);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnContinue);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Continue' button of Your Prpty page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking on 'Continue' button of Your Prpty page: " + e);
		}
	}

	/**
	 * To click yes/No button for full Contents Sum Represent Full Replacement Amount
	 * 
	 * @param fullContentsSumRepresentFullReplacementAmount
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoBtnContentsSumRepresentReplacementAmount(String contentsSumRepresentReplacementAmount, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (contentsSumRepresentReplacementAmount) {
			case "Yes":
				WaitUtils.waitForElement(driver, btncontentsSumRepresentReplacementAmountYes);
				GenericUtils.clickElementJS(driver, btncontentsSumRepresentReplacementAmountYes);
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btncontentsSumRepresentReplacementAmountNo);
				GenericUtils.clickElementJS(driver, btncontentsSumRepresentReplacementAmountNo);
				WaitUtils.waitForSpinner(driver);
				break;
			}
			WaitUtils.waitForPageLoad(driver);
			Log.message("Clicked on " + contentsSumRepresentReplacementAmount + " button in Add Contents cover", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking button in Add Contents cover : " + e);
		}
	}

	/**
	 * To click yes/No button for Previously Held Home Contents Insurance
	 * 
	 * @param PreviouslyHeldHomeContentsInsurance
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoBtnPreviouslyContentsInsurances(String PreviouslyHeldHomeContentsInsurance, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (PreviouslyHeldHomeContentsInsurance) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnPreviouslyContentsInsuranceYes);
				GenericUtils.clickElementJS(driver, btnPreviouslyContentsInsuranceYes);
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnPreviouslyContentsInsuranceNo);
				GenericUtils.clickElementJS(driver, btnPreviouslyContentsInsuranceNo);
				WaitUtils.waitForSpinner(driver);
				break;
			}
			WaitUtils.waitForPageLoad(driver);
			Log.message("TCNO_17765_Clicked on " + PreviouslyHeldHomeContentsInsurance + " button in Add Contents cover", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17765_Error while clicking button in Add Contents cover : " + e);
		}
	}

	/**
	 * To click Next button Additional cover
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextContentsInformation(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnNextContentsInformation);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextContentsInformation);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Next' button of Contents information in Add Contents cover page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking on 'Next' button of Contents information in Add Contents cover page: " + e);
		}
	}

	/**
	 * To click yes or No for Add Another Occupation
	 * 
	 * @param Add Another Occupation
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoAddAnotherOccupation(String addAnotherOccupation, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (addAnotherOccupation.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnAddAnotherOccupationYes);
				btnAddAnotherOccupationYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAddAnotherOccupationNo);
				btnAddAnotherOccupationNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("TCNO_17847_Clicked option for Do you want to add another Occupation : " + addAnotherOccupation + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("TCNO_17847_Error while clicking Occupation  button : " + e);
		}
	}

	/**
	 * To select occupation
	 * 
	 * @param occupation
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectOccupation(String occupation, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnOccupation);
			Select coverSelect = new Select(drpdwnOccupation);
			coverSelect.selectByVisibleText(occupation);
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected occupation Type : " + occupation, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting occupation : " + e);
		}
	}

	/**
	 * To select business
	 * 
	 * @param business
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectBusiness(String business, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnBusiness);
			Select coverSelect = new Select(drpdwnBusiness);
			coverSelect.selectByVisibleText(business);
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected Business Type : " + business, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting Business : " + e);
		}
	}

	/**
	 * To select Employment Status
	 * 
	 * @param Employment Status
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectEmploymentStatus(String employmentStatus, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnEmploymentStatus);
			Select coverSelect = new Select(drpdwnEmploymentStatus);
			coverSelect.selectByVisibleText(employmentStatus);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_177855_177856_Selected Employment Status : " + employmentStatus, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("TCNO_177855_177856_Error while selecting Employment Status : " + e);
		}
	}

	/**
	 * To click yes or No for Add Another Occupation
	 * 
	 * @param Add Another Occupation
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoMainOccupation(String mainOccupation, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (mainOccupation.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnMainOccupationYes);
				btnMainOccupationYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnMainOccupationNo);
				btnMainOccupationNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked option for Main Occupation : " + mainOccupation + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking Main Occupation button : " + e);
		}
	}

	/**
	 * To click Save button in Add Occupation
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickSaveOccupation(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnSaveOccupation);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnSaveOccupation);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17849_Clicked on 'Save Occupation' button 'Add Occupation' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17849_Error while clicking 'Save Occupation' button 'Add Occupation' page : " + e);
		}
	}

	/**
	 * To click Cancel button in Add Occupation
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickCancelOccupation(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnCancelOccupation);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnCancelOccupation);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17850_Clicked on 'Cancel' button 'Add Occupation' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17850_Error while clicking 'Cancel' button 'Add Occupation' page : " + e);
		}      
	}

	/**
	 * To click Next button Occupation
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextOccupation(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnNextOccupation);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextOccupation);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17846_Clicked on 'Next' button of  Occupation in 'People' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17846_Error while clicking 'Next' button of  Occupation in 'People' page : " + e);
		}
	}

	/**
	 * To click yes or No for Add Another Occupation
	 * 
	 * @param Add Another Occupation
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoAddAnotherPolicyHolderOccupation(String addAnotherPolicyHolderOccupation, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (addAnotherPolicyHolderOccupation.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnAddAnotherPolicyHolderOccupationYes);
				btnAddAnotherPolicyHolderOccupationYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnAddAnotherPolicyHolderOccupationNo);
				btnAddAnotherPolicyHolderOccupationNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked option for Do you want to add another policyHolder Occupation : " + addAnotherPolicyHolderOccupation + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking policyHolder Occupation  button : " + e);
		}
	}

	/**
	 * To select occupation
	 * 
	 * @param occupation
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectPolicyHolderOccupation(String policyHolderOccupation, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnPolicyHolderOccupation);
			Select coverSelect = new Select(drpdwnPolicyHolderOccupation);
			coverSelect.selectByVisibleText(policyHolderOccupation);
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected policyHolder occupation Type : " + policyHolderOccupation, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting policyHolder occupation : " + e);
		}
	}

	/**
	 * To select business
	 * 
	 * @param business
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectPolicyHolderBusiness(String policyHolderBusiness, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnPolicyHolderBusiness);
			Select coverSelect = new Select(drpdwnPolicyHolderBusiness);
			coverSelect.selectByVisibleText(policyHolderBusiness);
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected policyHolder Business Type : " + policyHolderBusiness, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting policyHolder Business : " + e);
		}
	}

	/**
	 * To select Employment Status
	 * 
	 * @param Employment Status
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectPolicyHolderEmploymentStatus(String policyHolderEmploymentStatus, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnPolicyHolderEmploymentStatus);
			Select coverSelect = new Select(drpdwnPolicyHolderEmploymentStatus);
			coverSelect.selectByVisibleText(policyHolderEmploymentStatus);
			WaitUtils.waitForSpinner(driver);
			Log.message("Selected policyHolder Employment Status : " + policyHolderEmploymentStatus, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("Error while selecting policyHolder Employment Status : " + e);
		}
	}

	/**
	 * To click yes or No for Add Another Occupation
	 * 
	 * @param Add Another Occupation
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickYesOrNoPolicyHolderMainOccupation(String policyHolderMainOccupation, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			switch (policyHolderMainOccupation.trim()) {
			case "Yes":
				WaitUtils.waitForElement(driver, btnPolicyHolderMainOccupationYes);
				btnPolicyHolderMainOccupationYes.click();
				WaitUtils.waitForSpinner(driver);
				break;

			case "No":
				WaitUtils.waitForElement(driver, btnPolicyHolderMainOccupationNo);
				btnPolicyHolderMainOccupationNo.click();
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("Clicked option for policyHolder Main Occupation : " + policyHolderMainOccupation + "", driver,
					extentedReport);
		} catch (Exception e) {
			throw new Exception("Error while clicking policyHolder Main Occupation button : " + e);
		}
	}

	/**
	 * To click Save button in Add Occupation
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickPolicyHolderSaveOccupation(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnPolicyHolderSaveOccupation);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnPolicyHolderSaveOccupation);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Save Occupation' button 'Add PolicyHolder Occupation' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Save Occupation' button 'Add PolicyHolder Occupation' page : " + e);
		}
	}

	/**
	 * To click Cancel button in Add PolicyHolder Occupation
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickPolicyHolderCancelOccupation(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnPolicyHolderCancelOccupation);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnPolicyHolderCancelOccupation);
			WaitUtils.waitForSpinner(driver);
			Log.message("Clicked on 'Cancel' button 'Add PolicyHolder Occupation' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("Error while clicking 'Cancel' button 'Add PolicyHolder Occupation' page : " + e);
		}      
	}


	/**
	 * To click Next Joint Policy holders Occupations
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextPolicyHolderOccupation(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnNextJointPolicyholdersOccupations);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextJointPolicyholdersOccupations);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17857_Clicked on 'Next' button of  Joint Policyholders Occupations in 'People' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17857_Error while clicking 'Next' button of  Joint Policyholders Occupations in 'People' page : " + e);
		}
	}

	/**
	 * To select type Of Ownership
	 * 
	 * @param type Of Ownership
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectTypeOfOwnership(String typeOfOwnership, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnSelectTypeOfOwnership);
			Select coverSelect = new Select(drpdwnSelectTypeOfOwnership);
			coverSelect.selectByVisibleText(typeOfOwnership);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17858_Selected type Of Ownership : " + typeOfOwnership, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("TCNO_17858_Error while selecting type Of Ownership : " + e);
		}
	}

	/**
	 * To select type Of Ownership
	 * 
	 * @param type Of Ownership
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectTypeOfOccupancy(String typeOfOccupancy, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnSelectTypeOfOccupancy);
			Select coverSelect = new Select(drpdwnSelectTypeOfOccupancy);
			coverSelect.selectByVisibleText(typeOfOccupancy);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17859_Selected type Of Occupancy : " + typeOfOccupancy, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("TCNO_17859_Error while selecting type Of Occupancy : " + e);
		}
	}

	/**
	 * To select people In Total Live In Property
	 * 
	 * @param people In Total Live In Property
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectPeopleInTotalLiveInProperty(String peopleInTotalLiveInProperty, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnPeopleInTotalLiveInProperty);
			Select coverSelect = new Select(drpdwnPeopleInTotalLiveInProperty);
			coverSelect.selectByVisibleText(peopleInTotalLiveInProperty);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17860_Selected people In Total Live In Property : " + peopleInTotalLiveInProperty, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("TCNO_17860_Error while selecting people In Total Live In Property : " + e);
		}
	}

	/**
	 * To select children Under 18 Live In Property
	 * 
	 * @param children Under 18 Live In Property
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void selectChildrenUnder18LiveInProperty(String childrenUnder18LiveInProperty, ExtentTest extentTest, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, drpdwnChildrenUnder18LiveInProperty);
			Select coverSelect = new Select(drpdwnChildrenUnder18LiveInProperty);
			coverSelect.selectByVisibleText(childrenUnder18LiveInProperty);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17861_Selected children Under 18 Live In Property : " + childrenUnder18LiveInProperty, driver, extentedReport, true);

		} catch (Exception e) {
			Log.message("TCNO_17861_Error while selecting children Under 18 Live In Property : " + e);
		}
	}

	/**
	 * To click Next Joint Policy holders Occupations
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickNextResidents(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnNextResidents);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnNextResidents);
			WaitUtils.waitForSpinner(driver);
			Log.message("TCNO_17862_Clicked on 'Next' button of  Residents in 'People' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17862_Error while clicking 'Next' button of  Residents in 'People' page : " + e);
		}
	}

	/**
	 * To verify Edit & Delete button of Specified Item within Home
	 * 
	 * @param deviceName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyDeleteAndEditBtnInOccupation(String occupationType, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {

			boolean btnFound = false;
			String occupationRow = getAddedOccupationRowInAddOccupation(occupationType, extentedReport, false);

			if (occupationRow == null) {
				return false;
			}

			String editElementString = cssEditOccupations + occupationRow;
			WebElement editBtn = driver.findElement(By.cssSelector(editElementString));

			String deleteElementString = cssDeleteOccupations + occupationRow;
			WebElement deleteBtn = driver.findElement(By.cssSelector(deleteElementString));

			if (WaitUtils.waitForElement(driver, editBtn) && WaitUtils.waitForElement(driver, deleteBtn)) {
				btnFound = true;
			}
			return btnFound;
		} catch (Exception e) {
			throw new Exception("Error while verifying edit and delete button of Added Occupation : " + e);
		}
	}

	/**
	 * To get Added occupation row in Within home
	 * 
	 * @param type of occupation
	 * @param Modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public String getAddedOccupationRowInAddOccupation(String occupationType, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String occupationRow = null;
			WaitUtils.waitForSpinner(driver);
			if (typeOfOccupation.size() == 0) {
				Log.message("No occupation is available", driver, extentedReport, false);
			}

			for (int loopCount = 0; loopCount < typeOfOccupation.size(); loopCount++) {
				Integer row = loopCount + 1;
				
				String displayedItemName = typeOfOccupation.get(loopCount).getText();
				if (displayedItemName.contains(occupationType)) {
					Log.message(occupationType + "is found", driver,extentedReport, screenshot);
					return row.toString();
				}
			}
			return occupationRow;
		} catch (Exception e) {
			throw new Exception("Error while getting Added Occupation row : " + e);
		}
	}

	/**
	 * Editing Add Occupation
	 * 
	 * @param occupationType
	 * @param occupation
	 * @param business
	 * @param employmentStatus
	 * @param saveAndCancel
	 * @param extentReport
	 * @param screenshot
	 *
	 */
	public void editAddOccupation(String occupationType,String occupation, String business, String employmentStatus, String saveAndCancel, ExtentTest extentReport,
			boolean screenshot) throws Exception {

		try {
			String rowNum = getAddedOccupationRowInAddOccupation(occupationType, extentedReport, false);
			WebElement edit = driver.findElement(By.cssSelector(cssEditOccupations + String.valueOf(rowNum))); 
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", edit);
			WaitUtils.waitForElement(driver, drpdwnOccupation);
			selectOccupation(occupation, extentedReport, true);
			selectBusiness(business, extentedReport, true);
			selectEmploymentStatus(employmentStatus, extentedReport, true);
			switch (saveAndCancel) {
			case "Save":
				WaitUtils.waitForElement(driver, btnSaveOccupation);
				clickSaveItemNewSpecifiedItemWithinHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				Log.message("Clicked on Save Occupation", driver, extentedReport);
				break;

			case "Cancel":
				WaitUtils.waitForElement(driver,btnCancelOccupation);
				clickCancelNewSpecifiedItemWithinHome(extentedReport,true);
				WaitUtils.waitForSpinner(driver);
				break;
			}                    
			Log.message("Occupation details are edited successfuly", driver, extentedReport, true);
		} catch (Exception e) {
			Log.message("Getting error while editing Occupation details in Within Home : " + e);
		}
	}

	/**
	 * To Add Specified Item within Home
	 * 
	 * @param itemType, itemValue, itemDecsription, saveAndCancel
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void addOccupation(String occupation, String business, String employmentStatus, String mainOccupation, String saveAndCancel, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			selectOccupation(occupation, extentedReport, true);
			selectBusiness(business, extentedReport, true);
			selectEmploymentStatus(employmentStatus, extentedReport, true);
			clickYesOrNoMainOccupation(mainOccupation, extentedReport, true);
			switch (saveAndCancel) {
			case "Save":
				clickSaveOccupation(extentedReport, true);;
				WaitUtils.waitForSpinner(driver);
				break;

			case "Cancel":
				clickCancelOccupation(extentedReport, true);
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("TCNO_17848_Added Occupation in 'People' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17848_Error while adding the Occupation in 'People' page : " + e);
		}
	}

	/**
	 * To Add Specified Item within Home
	 * 
	 * @param itemType, itemValue, itemDecsription, saveAndCancel
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void addPolicyHolderOccupation(String policyHolderOccupation, String policyHolderBusiness, String policyHolderEmploymentStatus, String policyHolderMainOccupation, String policyHolderSaveAndCancel, ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			selectPolicyHolderOccupation(policyHolderOccupation, extentedReport, true);
			selectPolicyHolderBusiness(policyHolderBusiness, extentedReport, true);
			selectPolicyHolderEmploymentStatus(policyHolderEmploymentStatus, extentedReport, true);
			clickYesOrNoPolicyHolderMainOccupation(policyHolderMainOccupation, extentedReport, true);
			switch (policyHolderSaveAndCancel) {
			case "Save":
				clickPolicyHolderSaveOccupation(extentedReport, true);;
				WaitUtils.waitForSpinner(driver);
				break;

			case "Cancel":
				clickPolicyHolderCancelOccupation(extentedReport, true);
				WaitUtils.waitForSpinner(driver);
				break;
			}
			Log.message("TCNO_17848_Added Occupation in 'People' page", driver, extentedReport, screenshot);
		} catch (Exception e) {
			throw new Exception("TCNO_17848_Error while adding the PolicyHolder Occupation in 'People' page : " + e);
		}
	}

	/**
	 * To verify Edit & Delete button of Specified Item within Home
	 * 
	 * @param deviceName
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyDeleteAndEditBtnInPolicyHolderOccupation(String PolicyHolderOccupationType, ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {

			boolean btnFound = false;
			String occupationRow = getAddedPolicyHolderOccupationRowInAddOccupation(PolicyHolderOccupationType, extentedReport, false);

			if (occupationRow == null) {
				return false;
			}

			String editElementString = cssEditPolicyHolderOccupations + occupationRow;
			WebElement editBtn = driver.findElement(By.cssSelector(editElementString));

			String deleteElementString = cssDeletePolicyHolderOccupations + occupationRow;
			WebElement deleteBtn = driver.findElement(By.cssSelector(deleteElementString));

			if (WaitUtils.waitForElement(driver, editBtn) && WaitUtils.waitForElement(driver, deleteBtn)) {
				btnFound = true;
			}
			return btnFound;
		} catch (Exception e) {
			throw new Exception("Error while verifying edit and delete button of Added Occupation : " + e);
		}
	}

	/**
	 * To get Added occupation row in Within home
	 * 
	 * @param type of occupation
	 * @param Modal
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public String getAddedPolicyHolderOccupationRowInAddOccupation(String policyHolderOccupationType, ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			String occupationRow = null;
			WaitUtils.waitForSpinner(driver);
			if (typeOfPolicyHolderOccupation.size() == 0) {
				Log.message("No occupation is available", driver, extentedReport, false);
			}

			for (int loopCount = 0; loopCount < typeOfPolicyHolderOccupation.size(); loopCount++) {
				Integer row = loopCount + 1;
				String displayedItemName = typeOfPolicyHolderOccupation.get(loopCount).getText();
				if (displayedItemName.contains(policyHolderOccupationType)) {
					Log.message(policyHolderOccupationType + "is found", driver,extentedReport, screenshot);
					return row.toString();
				}
			}
			return occupationRow;
		} catch (Exception e) {
			throw new Exception("Error while getting Added policyHolder Occupation row : " + e);
		}
	}

	/**
	 * To verify Column headers within home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void headersVerificationForTableInWithinHome(ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String type = headerTypeInWithinHome.getText();
			String value = headerValueInWithinHome.getText();
			String Description = headerDescriptionInWithinHome.getText();

			if(type == "Type" && value == "Value" && Description == "Description"){
				Log.message("TCNO_17776_Verification For Table headers In Within Home is done");
			}

		} catch (Exception e) {
			throw new Exception("TCNO_17776_Error while Verifing For Table headers In Within Home : " + e);
		}
	}

	/**
	 * To verify Column headers Outside home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void headersVerificationForTableInOutsideHome(ExtentTest extentedReport,
			boolean screenshot) throws Exception {
		try {
			String type = headerTypeInOutsideHome.getText();
			String value = headerValueInOutsideHome.getText();
			String Description = headerDescriptionInOutsideHome.getText();

			if(type == "Type" && value == "Value" && Description == "Description"){
				Log.message("TCNO_17786_Verification For Table headers In Outside Home is done");
			}

		} catch (Exception e) {
			throw new Exception("TCNO_17786_Error while Verifing For Table headers In Outside Home : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Title Your Property
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconTitleYourProperty(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpTitleYourProperty);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpTitleYourProperty);
			//iconHelpTitleYourProperty.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTitleYourProperty).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpTitleYourProperty);
			Log.message("Hovered on the HelpIcon Of Title Your Property", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Title Your Property : " + e);
		}
	}

	/**
	 * verify HelpIcon Title Your Property Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconTitleYourProperty() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpTitleYourProperty);
			if (GenericUtils.verifyWebElementTextContains(popHelpTitleYourProperty,
					POPUP_MSG_HELPICON_Title_Your_Property)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Title Your Property : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Buildings and Contents
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconBuildingsContents(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpBuildingsContents);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpBuildingsContents);
			//iconHelpBuildingsContents.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpBuildingsContents).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpBuildingsContents);
			Log.message("Hovered on the HelpIcon Of Buildings & Contents", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Buildings & Contents : " + e);
		}
	}

	/**
	 * verify HelpIcon Buildings & Contents Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconBuildingsContents() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpBuildingsContents);
			if (GenericUtils.verifyWebElementTextContains(popHelpBuildingsContents,
					POPUP_MSG_HELPICON_Buildings_Contents)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Buildings & Contents : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Buildings
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconBuildings(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpBuildings);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpBuildings);
			//iconHelpBuildings.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpBuildings).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpBuildings);
			Log.message("Hovered on the HelpIcon Of Buildings", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Buildings : " + e);
		}
	}

	/**
	 * verify HelpIcon Buildings Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconBuildings() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpBuildings);
			if (GenericUtils.verifyWebElementTextContains(popHelpBuildings,
					POPUP_MSG_HELPICON_Buildings)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Buildings : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Contents
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconContents(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpContents);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpContents);
			//iconHelpContents.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpContents).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpContents);
			Log.message("Hovered on the HelpIcon Of Contents", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Contents : " + e);
		}
	}

	/**
	 * verify HelpIcon Contents Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconContents() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpContents);
			if (GenericUtils.verifyWebElementTextContains(popHelpContents,
					POPUP_MSG_HELPICON_Contents)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Contents : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Address of Property To Be Insured
//	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconAddressofPropertyToBeInsured(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpAddressofPropertyToBeInsured);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpAddressofPropertyToBeInsured);
			//iconHelpAddressofPropertyToBeInsured.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpAddressofPropertyToBeInsured).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpAddressofPropertyToBeInsured);
			Log.message("Hovered on the HelpIcon Of Address of Property To Be Insured", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Address of Property To Be Insured : " + e);
		}
	}

	/**
	 * verify HelpIcon Address of Property To Be Insured Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconAddressofPropertyToBeInsured() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpAddressofPropertyToBeInsured);
			if (GenericUtils.verifyWebElementTextContains(popHelpAddressofPropertyToBeInsured,
					POPUP_MSG_HELPICON_Postcode_Of_Property)){//POPUP_MSG_HELPICON_Address_of_Property_To_Be_Insured)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Address of Property To Be Insured : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Postcode Of Property
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPostcodeOfProperty(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPostcodeOfProperty);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPostcodeOfProperty);
			//iconHelpPostcodeOfProperty.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPostcodeOfProperty).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPostcodeOfProperty);
			Log.message("Hovered on the HelpIcon Of Postcode Of Property", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Postcode Of Property : " + e);
		}
	}

	/**
	 * verify HelpIcon Postcode Of Property Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPostcodeOfProperty() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPostcodeOfProperty);
			if (GenericUtils.verifyWebElementTextContains(popHelpPostcodeOfProperty,
					POPUP_MSG_HELPICON_Postcode_Of_Property)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Postcode Of Property : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Your Correspondence Address
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconYourCorrespondenceAddress(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpYourCorrespondenceAddress);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpYourCorrespondenceAddress);
			//iconHelpYourCorrespondenceAddress.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpYourCorrespondenceAddress).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpYourCorrespondenceAddress);
			Log.message("Hovered on the HelpIcon Of Your Correspondence Address", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Your Correspondence Address : " + e);
		}
	}

	/**
	 * verify HelpIcon Your Correspondence Address Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconYourCorrespondenceAddress() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpYourCorrespondenceAddress);
			if (GenericUtils.verifyWebElementTextContains(popHelpYourCorrespondenceAddress,
					POPUP_MSG_HELPICON_Your_Correspondence_Address)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Your Correspondence Address : " + e);
		}
	}

	/**
	 * hover On Help Icon Of CorrespondenceAddressToUpdate
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconCorrespondenceAddressToUpdate(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpCorrespondenceAddressToUpdate);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpCorrespondenceAddressToUpdate);
			//iconHelpCorrespondenceAddressToUpdate.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpCorrespondenceAddressToUpdate).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpCorrespondenceAddressToUpdate);
			Log.message("Hovered on the HelpIcon Of Correspondence Address To Update", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Correspondence Address To Update : " + e);
		}
	}

	/**
	 * verify HelpIcon Your Correspondence Address Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconCorrespondenceAddressToUpdate() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpCorrespondenceAddressToUpdate);
			if (GenericUtils.verifyWebElementTextContains(popHelpCorrespondenceAddressToUpdate,
					POPUP_MSG_HELPICON_Correspondence_Address_To_Update)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Correspondence Address To Update : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Type Of Property
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconTypeOfProperty(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpTypeOfProperty);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpTypeOfProperty);
			//iconHelpTypeOfProperty.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTypeOfProperty).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpTypeOfProperty);
			Log.message("Hovered on the HelpIcon Of Type Of Property", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Type Of Property : " + e);
		}
	}

	/**
	 * verify HelpIcon Your Type Of Property Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconTypeOfProperty() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpTypeOfProperty);
			if (GenericUtils.verifyWebElementTextContains(popHelpTypeOfProperty,
					POPUP_MSG_HELPICON_Type_Of_Property)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Type Of Property : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Date Of Residence
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconDateOfResidence(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpDateOfResidence);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpDateOfResidence);
			//iconHelpDateOfResidence.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpDateOfResidence).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpDateOfResidence);
			Log.message("Hovered on the HelpIcon Of Date Of Residence", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Date Of Residence : " + e);
		}
	}

	/**
	 * verify HelpIcon Date Of Residence Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconDateOfResidence() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpDateOfResidence);
			if (GenericUtils.verifyWebElementTextContains(popHelpDateOfResidence,
					POPUP_MSG_HELPICON_Date_Of_Residence)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Date Of Residence : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Year Your Property Was Built
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconYearYourPropertyWasBuilt(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpYearYourPropertyWasBuilt);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpYearYourPropertyWasBuilt);
			//iconHelpYearYourPropertyWasBuilt.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpYearYourPropertyWasBuilt).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpYearYourPropertyWasBuilt);
			Log.message("Hovered on the HelpIcon Of Year Your Property Was Built", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Year Your Property Was Built : " + e);
		}
	}

	/**
	 * verify HelpIcon Year Your Property Was Built Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconYearYourPropertyWasBuilt() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpYearYourPropertyWasBuilt);
			if (GenericUtils.verifyWebElementTextContains(popHelpYearYourPropertyWasBuilt,
					POPUP_MSG_HELPICON_Year_Your_Property_Was_Built)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Year Your Property Was Built : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Number Of Bath Rooms
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconNumberOfBathRooms(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpNumberOfBathRooms);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpNumberOfBathRooms);
			//iconHelpNumberOfBathRooms.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNumberOfBathRooms).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpNumberOfBathRooms);
			Log.message("Hovered on the HelpIcon Of Number Of Bath Rooms", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Number Of Bath Rooms : " + e);
		}
	}

	/**
	 * verify HelpIcon Number Of Bath Rooms Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconNumberOfBathRooms() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpNumberOfBathRooms);
			if (GenericUtils.verifyWebElementTextContains(popHelpNumberOfBathRooms,
					POPUP_MSG_HELPICON_Number_Of_Bath_Rooms)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Number Of Bath Rooms : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Number Of Bedrooms
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconNumberOfBedrooms(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpNumberOfBedrooms);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpNumberOfBedrooms);
			//iconHelpNumberOfBedrooms.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNumberOfBedrooms).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpNumberOfBedrooms);
			Log.message("Hovered on the HelpIcon Of Number of bedrooms", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Number of bedrooms : " + e);
		}
	}

	/**
	 * verify HelpIcon Number Of Bedrooms Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconNumberOfBedrooms() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpNumberOfBedrooms);
			if (GenericUtils.verifyWebElementTextContains(popHelpNumberOfBedrooms,
					POPUP_MSG_HELPICON_Number_Of_Bedrooms)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Number of bedrooms : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Type Of Wall Construction
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconTypeOfWallConstruction(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpTypeOfWallConstruction);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpTypeOfWallConstruction);
			//iconHelpTypeOfWallConstruction.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTypeOfWallConstruction).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpTypeOfWallConstruction);
			Log.message("Hovered on the HelpIcon Of Type of wall construction", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Type of wall construction : " + e);
		}
	}

	/**
	 * verify HelpIcon Type Of Wall Construction Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconTypeOfWallConstruction() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpTypeOfWallConstruction);
			if (GenericUtils.verifyWebElementTextContains(popHelpTypeOfWallConstruction,
					POPUP_MSG_HELPICON_Type_Of_Wall_Construction)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Type of wall construction : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Type Of Roof Construction
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconTypeOfRoofConstruction(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpTypeOfRoofConstruction);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpTypeOfRoofConstruction);
			//iconHelpTypeOfRoofConstruction.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTypeOfRoofConstruction).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpTypeOfRoofConstruction);
			Log.message("Hovered on the HelpIcon Of Type of Roof Construction", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Type Of Roof Construction : " + e);
		}
	}

	/**
	 * verify HelpIcon Type Of Roof Construction Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconTypeOfRoofConstruction() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpTypeOfRoofConstruction);
			if (GenericUtils.verifyWebElementTextContains(popHelpTypeOfRoofConstruction,
					POPUP_MSG_HELPICON_Type_Of_Roof_Construction)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Type of roof construction : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Roof That Is Flat
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconRoofThatIsFlat(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpRoofThatIsFlat);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpRoofThatIsFlat);
			//iconHelpRoofThatIsFlat.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpRoofThatIsFlat).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpRoofThatIsFlat);
			Log.message("Hovered on the HelpIcon Of Roof That Is Flat", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Roof That Is Flat : " + e);
		}
	}

	/**
	 * verify HelpIcon Roof That Is Flat Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconRoofThatIsFlat() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpRoofThatIsFlat);
			if (GenericUtils.verifyWebElementTextContains(popHelpRoofThatIsFlat,
					POPUP_MSG_HELPICON_Roof_That_Is_Flat)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Roof That Is Flat : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Type of listed building if appropriate
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconTypeOfListedBuildingIfAppropriate(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpTypeOfListedBuildingIfAppropriate);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpTypeOfListedBuildingIfAppropriate);
			//iconHelpTypeOfListedBuildingIfAppropriate.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTypeOfListedBuildingIfAppropriate).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpTypeOfListedBuildingIfAppropriate);
			Log.message("Hovered on the HelpIcon Of Type of listed building if appropriate", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Type of listed building if appropriate : " + e);
		}
	}

	/**
	 * verify HelpIcon Type of listed building if appropriate Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconTypeOfListedBuildingIfAppropriate() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpTypeOfListedBuildingIfAppropriate);
			if (GenericUtils.verifyWebElementTextContains(popHelpTypeOfListedBuildingIfAppropriate,
					POPUP_MSG_HELPICON_Type_Of_Listed_Building_If_Appropriate)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Type of listed building if appropriate : " + e);
		}
	}

	/**
	 * hover On Help Icon Of property been extended
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPropertyBeenExtended(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPropertyBeenExtended);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPropertyBeenExtended);
			//iconHelpPropertyBeenExtended.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPropertyBeenExtended).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPropertyBeenExtended);
			Log.message("Hovered on the HelpIcon Of property been extended", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property been extended : " + e);
		}
	}

	/**
	 * verify HelpIcon property been extended Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPropertyBeenExtended() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPropertyBeenExtended);
			if (GenericUtils.verifyWebElementTextContains(popHelpPropertyBeenExtended,
					POPUP_MSG_HELPICON_Property_Been_Extended)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property been extended : " + e);
		}
	}

	/**
	 * hover On Help Icon Of property used for business purposes
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPropertyUsedForBusinessPurposes(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPropertyUsedForBusinessPurposes);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPropertyUsedForBusinessPurposes);
			//iconHelpPropertyUsedForBusinessPurposes.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPropertyUsedForBusinessPurposes).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPropertyUsedForBusinessPurposes);
			Log.message("Hovered on the HelpIcon Of property used for business purposes", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property used for business purposes : " + e);
		}
	}

	/**
	 * verify HelpIcon property used for business purposes Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPropertyUsedForBusinessPurposes() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPropertyUsedForBusinessPurposes);
			if (GenericUtils.verifyWebElementTextContains(popHelpPropertyUsedForBusinessPurposes,
					POPUP_MSG_HELPICON_Property_Used_For_Business_Purposes)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property used for business purposes : " + e);
		}
	}

	/**
	 * hover On Help Icon Of property free from subsidence
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPropertyFreeFromSubsidence(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPropertyFreeFromSubsidence);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPropertyFreeFromSubsidence);
			//iconHelpPropertyFreeFromSubsidence.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPropertyFreeFromSubsidence).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPropertyFreeFromSubsidence);
			Log.message("Hovered on the HelpIcon Of property free from subsidence", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property free from subsidence : " + e);
		}
	}

	/**
	 * verify HelpIcon property free from subsidence Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPropertyFreeFromSubsidence() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPropertyFreeFromSubsidence);
			if (GenericUtils.verifyWebElementTextContains(popHelpPropertyFreeFromSubsidence,
					POPUP_MSG_HELPICON_Property_Free_From_Subsidence)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property free from subsidence : " + e);
		}
	}

	/**
	 * hover On Help Icon Of property free from Flooding
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPropertyFreeFromFlooding(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPropertyFreeFromFlooding);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPropertyFreeFromFlooding);
			//iconHelpPropertyFreeFromFlooding.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPropertyFreeFromFlooding).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPropertyFreeFromFlooding);
			Log.message("Hovered on the HelpIcon Of property free from Flooding", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property free from Flooding : " + e);
		}
	}

	/**
	 * verify HelpIcon property free from Flooding Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPropertyFreeFromFlooding() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPropertyFreeFromFlooding);
			if (GenericUtils.verifyWebElementTextContains(popHelpPropertyFreeFromFlooding,
					POPUP_MSG_HELPICON_Property_Free_From_Flooding)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property free from Flooding : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Property Ever Been Underpinned
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPropertyEverBeenUnderpinned(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPropertyEverBeenUnderpinned);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPropertyEverBeenUnderpinned);
			//iconHelpPropertyEverBeenUnderpinned.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPropertyEverBeenUnderpinned).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPropertyEverBeenUnderpinned);
			Log.message("Hovered on the HelpIcon Of property Ever Been Underpinned", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property Ever Been Underpinned : " + e);
		}
	}

	/**
	 * verify HelpIcon property Ever Been Underpinned Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPropertyEverBeenUnderpinned() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPropertyEverBeenUnderpinned);
			if (GenericUtils.verifyWebElementTextContains(popHelpPropertyEverBeenUnderpinned,
					POPUP_MSG_HELPICON_Property_Ever_Been_Underpinned)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property Ever Been Underpinned : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Property within 400 metres (¼ mile) of water
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPropertyWithin400MetresPfWater(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPropertyWithin400MetresOfWater);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPropertyWithin400MetresOfWater);
			//iconHelpPropertyWithin400MetresOfWater.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPropertyWithin400MetresOfWater).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPropertyWithin400MetresOfWater);
			Log.message("Hovered on the HelpIcon Of property within 400 metres (¼ mile) of water", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property within 400 metres (¼ mile) of water : " + e);
		}
	}

	/**
	 * verify HelpIcon property within 400 metres (¼ mile) of water Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPropertyWithin400MetresOfWater() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPropertyWithin400MetresOfWater);
			if (GenericUtils.verifyWebElementTextContains(popHelpPropertyWithin400MetresOfWater,
					POPUP_MSG_HELPICON_Property_Within_400_Metres_Of_Water)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property within 400 metres (¼ mile) of water : " + e);
		}
	}

	/**
	 * hover On Help Icon Of trees or shrubs taller than 5 metres(16ft) within 7 metres (22ft) of your property
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconTreesTallerThan5Metres(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpTreesTallerThan5Metres);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpTreesTallerThan5Metres);
			//iconHelpTreesTallerThan5Metres.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTreesTallerThan5Metres).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpTreesTallerThan5Metres);
			Log.message("Hovered on the HelpIcon Of trees or shrubs taller than 5 metres(16ft) within 7 metres (22ft) of your property", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of trees or shrubs taller than 5 metres(16ft) within 7 metres (22ft) of your property : " + e);
		}
	}

	/**
	 * verify HelpIcon trees or shrubs taller than 5 metres(16ft) within 7 metres (22ft) of your property Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconTreesTallerThan5Metres() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpTreesTallerThan5Metres);
			if (GenericUtils.verifyWebElementTextContains(popHelpTreesTallerThan5Metres,
					POPUP_MSG_HELPICON_Trees_Taller_Than_5_Metres)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of trees or shrubs taller than 5 metres(16ft) within 7 metres (22ft) of your property : " + e);
		}
	}

	/**
	 * hover On Help Icon Of property in a good state of repair
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPropertyInAGoodStateOfRepair(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPropertyInAGoodStateOfRepair);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPropertyInAGoodStateOfRepair);
			//iconHelpPropertyInAGoodStateOfRepair.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPropertyInAGoodStateOfRepair).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPropertyInAGoodStateOfRepair);
			Log.message("Hovered on the HelpIcon Of property in a good state of repair", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property in a good state of repair : " + e);
		}
	}

	/**
	 * verify HelpIcon property in a good state of repair
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPropertyInAGoodStateOfRepair() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPropertyInAGoodStateOfRepair);
			if (GenericUtils.verifyWebElementTextContains(popHelpPropertyInAGoodStateOfRepair,
					POPUP_MSG_HELPICON_Property_In_A_Good_State_Of_Repair)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property in a good state of repair : " + e);
		}
	}

	/**
	 * hover On Help Icon Of property self-contained and lockable
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPropertySelfContainedAndLockable(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPropertySelfContainedAndLockable);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPropertySelfContainedAndLockable);
			//iconHelpPropertySelfContainedAndLockable.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPropertySelfContainedAndLockable).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPropertySelfContainedAndLockable);
			Log.message("Hovered on the HelpIcon Of property self-contained and lockable", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property self-contained and lockable : " + e);
		}
	}

	/**
	 * verify HelpIcon property self-contained and lockable
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPropertySelfContainedAndLockable() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPropertySelfContainedAndLockable);
			if (GenericUtils.verifyWebElementTextContains(popHelpPropertySelfContainedAndLockable,
					POPUP_MSG_HELPICON_Property_Self_Contained_And_Lockable)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property self-contained and lockable : " + e);
		}
	}

	/**
	 * hover On Help Icon Of property occupied during the day
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPropertyOccupiedDuringTheDay(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpOccupiedDuringTheDay);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpOccupiedDuringTheDay);
			//iconHelpOccupiedDuringTheDay.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpOccupiedDuringTheDay).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPropertyOccupiedDuringTheDay);
			Log.message("Hovered on the HelpIcon Of property occupied during the day", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property occupied during the day : " + e);
		}
	}

	/**
	 * verify HelpIcon property occupied during the day
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPropertyOccupiedDuringTheDay() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPropertyOccupiedDuringTheDay);
			if (GenericUtils.verifyWebElementTextContains(popHelpPropertyOccupiedDuringTheDay,
					POPUP_MSG_HELPICON_Property_Occupied_During_The_Day)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property occupied during the day : " + e);
		}
	}

	/**
	 * hover On Help Icon Of property occupied At Night
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPropertyOccupiedAtNight(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpOccupiedAtNight);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpOccupiedAtNight);
			//iconHelpOccupiedAtNight.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpOccupiedAtNight).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPropertyOccupiedAtNight);
			Log.message("Hovered on the HelpIcon Of property occupied at night", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property occupied at night : " + e);
		}
	}

	/**
	 * verify HelpIcon property occupied At Night
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPropertyOccupiedAtNight() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPropertyOccupiedAtNight);
			if (GenericUtils.verifyWebElementTextContains(popHelpPropertyOccupiedAtNight,
					POPUP_MSG_HELPICON_Property_Occupied_At_Night)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property occupied at night : " + e);
		}
	}

	/**
	 * hover On Help Icon Of property have an alarm installed
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPropertyHaveAnAlarmInstalled(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPropertyHaveAnAlarmInstalled);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPropertyHaveAnAlarmInstalled);
			//iconHelpPropertyHaveAnAlarmInstalled.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPropertyHaveAnAlarmInstalled).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPropertyHaveAnAlarmInstalled);
			Log.message("Hovered on the HelpIcon Of property have an alarm installed", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property have an alarm installed : " + e);
		}
	}

	/**
	 * verify HelpIcon property have an alarm installed
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPropertyHaveAnAlarmInstalled() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPropertyHaveAnAlarmInstalled);
			if (GenericUtils.verifyWebElementTextContains(popHelpPropertyHaveAnAlarmInstalled,
					POPUP_MSG_HELPICON_Property_Have_An_Alarm_Installed)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property have an alarm installed : " + e);
		}
	}

	/**
	 * hover On Help Icon Of property Are British Standard Kitemark (BS3621) approved locks fittedd
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconBritishStandardKitemark(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpBritishStandardKitemark);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpBritishStandardKitemark);
			//iconHelpBritishStandardKitemark.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpBritishStandardKitemark).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpBritishStandardKitemark);
			Log.message("Hovered on the HelpIcon Of Are British Standard Kitemark (BS3621) approved locks fitted", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Are British Standard Kitemark (BS3621) approved locks fitted : " + e);
		}
	}

	/**
	 * verify HelpIcon property Are British Standard Kitemark (BS3621) approved locks fitted
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconBritishStandardKitemark() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpBritishStandardKitemark);
			if (GenericUtils.verifyWebElementTextContains(popHelpBritishStandardKitemark,
					POPUP_MSG_HELPICON_British_Standard_Kitemark)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Are British Standard Kitemark (BS3621) approved locks fitted : " + e);
		}
	}

	/**
	 * hover On Help Icon Of property property have external security lighting
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPropertyHaveExternalSecurityLighting(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPropertyHaveExternalSecurityLighting);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPropertyHaveExternalSecurityLighting);
			//iconHelpPropertyHaveExternalSecurityLighting.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPropertyHaveExternalSecurityLighting).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPropertyHaveExternalSecurityLighting);
			Log.message("Hovered on the HelpIcon Of property have external security lighting", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property have external security lighting : " + e);
		}
	}

	/**
	 * verify HelpIcon property property have external security lighting
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPropertyHaveExternalSecurityLighting() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPropertyHaveExternalSecurityLighting);
			if (GenericUtils.verifyWebElementTextContains(popHelpPropertyHaveExternalSecurityLighting,
					POPUP_MSG_HELPICON_Property_Have_External_Security_Lighting)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property have external security lighting : " + e);
		}
	}

	/**
	 * hover On Help Icon Of property property have external security lighting
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconLocksActivatedAtNight(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpLocksActivatedAtNight);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpLocksActivatedAtNight);
			//iconHelpLocksActivatedAtNight.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpLocksActivatedAtNight).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpLocksActivatedAtNight);
			Log.message("Hovered on the HelpIcon Of Are all locks activated at night and when the property is unoccupied", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Are all locks activated at night and when the property is unoccupied : " + e);
		}
	}

	/**
	 * verify HelpIcon Are all locks activated at night and when the property is unoccupied
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconLocksActivatedAtNight() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpLocksActivatedAtNight);
			if (GenericUtils.verifyWebElementTextContains(popHelpLocksActivatedAtNight,
					POPUP_MSG_HELPICON_Locks_Activated_At_Night)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Are all locks activated at night and when the property is unoccupied : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Are you a member of an active Neighbourhood Watch Scheme
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconNeighbourhoodWatchScheme(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpNeighbourhoodWatchScheme);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpNeighbourhoodWatchScheme);
			//iconHelpNeighbourhoodWatchScheme.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNeighbourhoodWatchScheme).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpNeighbourhoodWatchScheme);
			Log.message("Hovered on the HelpIcon Of Are you a member of an active Neighbourhood Watch Scheme", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Are you a member of an active Neighbourhood Watch Scheme : " + e);
		}
	}

	/**
	 * verify HelpIcon Are you a member of an active Neighbourhood Watch Scheme
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconNeighbourhoodWatchScheme() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpNeighbourhoodWatchScheme);
			if (GenericUtils.verifyWebElementTextContains(popHelpNeighbourhoodWatchScheme,
					POPUP_MSG_HELPICON_Neighbourhood_Watch_Scheme)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Are you a member of an active Neighbourhood Watch Scheme : " + e);
		}
	}

	/**
	 * hover On Help Icon Of property fitted with a smoke detector
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPropertyFittedWithASmokeDetector(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPropertyFittedWithASmokeDetector);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPropertyFittedWithASmokeDetector);
			//iconHelpPropertyFittedWithASmokeDetector.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPropertyFittedWithASmokeDetector).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPropertyFittedWithASmokeDetector);
			Log.message("Hovered on the HelpIcon Of property fitted with a smoke detector", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property fitted with a smoke detector : " + e);
		}
	}

	/**
	 * verify HelpIcon property fitted with a smoke detector
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPropertyFittedWithASmokeDetector() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPropertyFittedWithASmokeDetector);
			if (GenericUtils.verifyWebElementTextContains(popHelpPropertyFittedWithASmokeDetector,
					POPUP_MSG_HELPICON_Property_Fitted_With_A_Smoke_Detector)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of property fitted with a smoke detector : " + e);
		}
	}

	/**
	 * hover On Help Icon Of safe installed within the property
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconSafeInstalledWithinTheProperty(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpSafeInstalledWithinTheProperty);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpSafeInstalledWithinTheProperty);
			//iconHelpSafeInstalledWithinTheProperty.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpSafeInstalledWithinTheProperty).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpSafeInstalledWithinTheProperty);
			Log.message("Hovered on the HelpIcon Of safe installed within the property", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of safe installed within the property : " + e);
		}
	}

	/**
	 * verify HelpIcon safe installed within the property
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconSafeInstalledWithinTheProperty() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpSafeInstalledWithinTheProperty);
			if (GenericUtils.verifyWebElementTextContains(popHelpSafeInstalledWithinTheProperty,
					POPUP_MSG_HELPICON_Safe_Installed_Within_The_Property)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of safe installed within the property : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Rebuild Cost
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfRebuildCost(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpRebuildCost);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpRebuildCost);
			//iconHelpRebuildCost.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpRebuildCost).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpRebuildCost);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Rebuild Cost Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconRebuildCostPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpRebuildCost);
			if (GenericUtils.verifyWebElementTextContains(popHelpRebuildCost,
					POPUP_MSG_HELPICON_Rebuild_Cost)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Accidental Damage
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfAccidentalDamage(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpAccidentalDamage);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpAccidentalDamage);
			//iconHelpAccidentalDamage.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpAccidentalDamage).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpAccidentalDamage);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Accidental Damage Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconAccidentalDamagePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpAccidentalDamage);
			if (GenericUtils.verifyWebElementTextContains(popHelpAccidentalDamage,
					POPUP_MSG_HELPICON_Accidental_Damage)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Total Excess Required
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfTotalExcessRequired(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpTotalExcessRequired);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpTotalExcessRequired);
			//iconHelpTotalExcessRequired.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTotalExcessRequired).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpTotalExcessRequired);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Total Excess Required Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconTotalExcessRequiredePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpTotalExcessRequired);
			if (GenericUtils.verifyWebElementTextContains(popHelpTotalExcessRequired,
					POPUP_MSG_HELPICON_Total_Excess_Required_B)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Previous Building Insurance
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfPreviousBuildingInsurance(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPreviousBuildingInsurance);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPreviousBuildingInsurance);
			//iconHelpPreviousBuildingInsurance.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPreviousBuildingInsurance).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPreviousBuildingInsurance);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Previous Building Insurance Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPreviousBuildingInsurancePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPreviousBuildingInsurance);
			if (GenericUtils.verifyWebElementTextContains(popHelpPreviousBuildingInsurance,
					POPUP_MSG_HELPICON_Previous_Building_Insurance)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Name Of Previous Insurer
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfNameOfPreviousInsurer(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpNameOfPreviousInsurer);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpNameOfPreviousInsurer);
			//iconHelpNameOfPreviousInsurer.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNameOfPreviousInsurer).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpNameOfPreviousInsurer);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Name Of Previous Insurer Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconNameOfPreviousInsurerPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpNameOfPreviousInsurer);
			if (GenericUtils.verifyWebElementTextContains(popHelpNameOfPreviousInsurer,
					POPUP_MSG_HELPICON_Name_Of_Previous_Insurer_B)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Policy Number Of Previous InsuranceB
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfPolicyNumberOfPreviousInsuranceB(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPolicyNumberOfPreviousInsuranceB);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPolicyNumberOfPreviousInsuranceB);
			//iconHelpPolicyNumberOfPreviousInsuranceB.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPolicyNumberOfPreviousInsuranceB).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPolicyNumberOfPreviousInsuranceB);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Policy Number Of Previous InsuranceB Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPolicyNumberOfPreviousInsuranceBPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPolicyNumberOfPreviousInsuranceB);
			if (GenericUtils.verifyWebElementTextContains(popHelpPolicyNumberOfPreviousInsuranceB,
					POPUP_MSG_HELPICON_Policy_Number_Of_Previous_Insurance_B)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Expiry Date Of Previous InsuranceB
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfExpiryDateOfPreviousInsuranceB(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpExpiryDateOfPreviousInsuranceB);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpExpiryDateOfPreviousInsuranceB);
			//iconHelpExpiryDateOfPreviousInsuranceB.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpExpiryDateOfPreviousInsuranceB).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpExpiryDateOfPreviousInsuranceB);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Expiry Date Of Previous InsuranceB Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconExpiryDateOfPreviousInsuranceBPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpExpiryDateOfPreviousInsuranceB);
			if (GenericUtils.verifyWebElementTextContains(popHelpExpiryDateOfPreviousInsuranceB,
					POPUP_MSG_HELPICON_Expiry_Date_Of_Previous_Insurance_B)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Pay For Your Previous InsuranceB
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfPayForYourPreviousInsuranceB(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPayForYourPreviousInsuranceB);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPayForYourPreviousInsuranceB);
			//iconHelpPayForYourPreviousInsuranceB.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPayForYourPreviousInsuranceB).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPayForYourPreviousInsuranceB);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Pay For Your Previous InsuranceB Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPayForYourPreviousInsuranceBPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPayForYourPreviousInsuranceB);
			if (GenericUtils.verifyWebElementTextContains(popHelpPayForYourPreviousInsuranceB,
					POPUP_MSG_HELPICON_Pay_For_Your_Previous_Insurance_B)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of No Of Years Without ClaimingB
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfNoOfYearsWithoutClaimingB(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpNoOfYearsWithoutClaimingB);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpNoOfYearsWithoutClaimingB);
			//iconHelpNoOfYearsWithoutClaimingB.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaimingB).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpNoOfYearsWithoutClaimingB);
			Log.message("Hovered on the HelpIcon Of Add Buildings Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon No Of Years Without ClaimingB Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconNoOfYearsWithoutClaimingBPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpNoOfYearsWithoutClaimingB);
			if (GenericUtils.verifyWebElementTextContains(popHelpNoOfYearsWithoutClaimingB,
					POPUP_MSG_HELPICON_No_Of_Years_Without_Claiming_B)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Buildings Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of value Of Home Contents
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfvalueOfHomeContents(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpvalueOfHomeContents);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpvalueOfHomeContents);
			//iconHelpvalueOfHomeContents.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpvalueOfHomeContents).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpvalueOfHomeContents);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon value Of Home Contents Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconvalueOfHomeContentsPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpvalueOfHomeContents);
			if (GenericUtils.verifyWebElementTextContains(popHelpvalueOfHomeContents,
					POPUP_MSG_HELPICON_Value_Of_Home_Contents)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of value Of Precious Or Costly Items
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfvalueOfPreciousOrCostlyItems(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpvalueOfPreciousOrCostlyItems);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpvalueOfPreciousOrCostlyItems);
			//iconHelpvalueOfPreciousOrCostlyItems.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpvalueOfPreciousOrCostlyItems).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpvalueOfPreciousOrCostlyItems);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Value Of Precious Or Costly Items Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconvalueOfPreciousOrCostlyItemsPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpvalueOfPreciousOrCostlyItems);
			if (GenericUtils.verifyWebElementTextContains(popHelpvalueOfPreciousOrCostlyItems,
					POPUP_MSG_HELPICON_Value_Of_Precious_Or_Costly_Items)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of value Of Most Expensive Item
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfvalueOfMostExpensiveItem(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpvalueOfMostExpensiveItem);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpvalueOfMostExpensiveItem);
			//iconHelpvalueOfMostExpensiveItem.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpvalueOfMostExpensiveItem).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpvalueOfMostExpensiveItem);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon value Of Most Expensive Item Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconvalueOfMostExpensiveItemPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpvalueOfMostExpensiveItem);
			if (GenericUtils.verifyWebElementTextContains(popHelpvalueOfMostExpensiveItem,
					POPUP_MSG_HELPICON_value_Of_Most_Expensive_Item)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of type Of Home Contents Cover Required
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOftypeOfHomeContentsCoverRequired(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelptypeOfHomeContentsCoverRequired);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelptypeOfHomeContentsCoverRequired);
			//iconHelptypeOfHomeContentsCoverRequired.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelptypeOfHomeContentsCoverRequired).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelptypeOfHomeContentsCoverRequired);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon type Of Home Contents Cover Required Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIcontypeOfHomeContentsCoverRequiredPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelptypeOfHomeContentsCoverRequired);
			if (GenericUtils.verifyWebElementTextContains(popHelptypeOfHomeContentsCoverRequired,
					POPUP_MSG_HELPICON_Type_Of_Home_Contents_Cover_Required)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of total Excess Required
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOftotalExcessRequired(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelptotalExcessRequired);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelptotalExcessRequired);
			//iconHelptotalExcessRequired.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelptotalExcessRequired).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelptotalExcessRequired);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon total Excess Required Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIcontotalExcessRequiredPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelptotalExcessRequired);
			if (GenericUtils.verifyWebElementTextContains(popHelptotalExcessRequired,
					POPUP_MSG_HELPICON_Total_Excess_Required)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of total Excess Required
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfcontentsSumRepresentReplacementAmount(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpcontentsSumRepresentReplacementAmount);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpcontentsSumRepresentReplacementAmount);
			//iconHelpcontentsSumRepresentReplacementAmount.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpcontentsSumRepresentReplacementAmount).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpcontentsSumRepresentReplacementAmount);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon total Excess Required Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconcontentsSumRepresentReplacementAmountPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpcontentsSumRepresentReplacementAmount);
			if (GenericUtils.verifyWebElementTextContains(popHelpcontentsSumRepresentReplacementAmount,
					POPUP_MSG_HELPICON_Contents_Sum_Represent_Replacement_Amount)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of previously Contents Insurance
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfpreviouslyContentsInsurance(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelppreviouslyContentsInsurance);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelppreviouslyContentsInsurance);
			//iconHelppreviouslyContentsInsurance.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelppreviouslyContentsInsurance).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelppreviouslyContentsInsurance);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon previously Contents Insurance Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconpreviouslyContentsInsurancePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelppreviouslyContentsInsurance);
			if (GenericUtils.verifyWebElementTextContains(popHelppreviouslyContentsInsurance,
					POPUP_MSG_HELPICON_Previously_Contents_Insurance)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of name Of Previous Insurer
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfnameOfPreviousInsurer(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpnameOfPreviousInsurer);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpnameOfPreviousInsurer);
			//iconHelpnameOfPreviousInsurer.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpnameOfPreviousInsurer).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpnameOfPreviousInsurer);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon name Of Previous Insurer Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconnameOfPreviousInsurerPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpnameOfPreviousInsurer);
			if (GenericUtils.verifyWebElementTextContains(popHelpnameOfPreviousInsurer,
					POPUP_MSG_HELPICON_Name_Of_Previous_Insurer)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of AddContentsCover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Policy Number Of Previous Insurance
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfPolicyNumberOfPreviousInsurance(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPolicyNumberOfPreviousInsuranceC);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPolicyNumberOfPreviousInsuranceC);
			//iconHelpPolicyNumberOfPreviousInsuranceC.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPolicyNumberOfPreviousInsuranceC).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPolicyNumberOfPreviousInsuranceC);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Policy Number Of Previous Insurance Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPolicyNumberOfPreviousInsurancePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPolicyNumberOfPreviousInsuranceC);
			if (GenericUtils.verifyWebElementTextContains(popHelpPolicyNumberOfPreviousInsuranceC,
					POPUP_MSG_HELPICON_Policy_Number_Of_Previous_Insurance)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Expiry Date Of Previous Insurance
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfExpiryDateOfPreviousInsurance(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpExpiryDateOfPreviousInsuranceC);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpExpiryDateOfPreviousInsuranceC);
			//iconHelpExpiryDateOfPreviousInsuranceC.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpExpiryDateOfPreviousInsuranceC).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpExpiryDateOfPreviousInsuranceC);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Expiry Date Of Previous Insurance Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconExpiryDateOfPreviousInsurancePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpExpiryDateOfPreviousInsuranceC);
			if (GenericUtils.verifyWebElementTextContains(popHelpExpiryDateOfPreviousInsuranceC,
					POPUP_MSG_HELPICON_Expiry_Date_Of_Previous_Insurance)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Pay For Your Previous Insurance
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfPayForYourPreviousInsurance(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPayForYourPreviousInsuranceC);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPayForYourPreviousInsuranceC);
			//iconHelpPayForYourPreviousInsuranceC.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPayForYourPreviousInsuranceC).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPayForYourPreviousInsuranceC);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Pay For Your Previous Insurance Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPayForYourPreviousInsurancePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPayForYourPreviousInsuranceC);
			if (GenericUtils.verifyWebElementTextContains(popHelpPayForYourPreviousInsuranceC,
					POPUP_MSG_HELPICON_Pay_For_Your_Previous_Insurance)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of No Of Years Without Claiming
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfNoOfYearsWithoutClaiming(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpNoOfYearsWithoutClaimingC);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpNoOfYearsWithoutClaimingC);
			//iconHelpNoOfYearsWithoutClaimingC.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpNoOfYearsWithoutClaimingC).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpNoOfYearsWithoutClaimingC);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon No Of Years Without Claiming Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconNoOfYearsWithoutClaimingPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpNoOfYearsWithoutClaimingC);
			if (GenericUtils.verifyWebElementTextContains(popHelpNoOfYearsWithoutClaimingC,
					POPUP_MSG_HELPICON_No_Of_Years_Without_Claiming)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Add Contents Cover
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfAddContentsCover(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpAddContentsCover);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpAddContentsCover);
			//iconHelpAddContentsCover.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpAddContentsCover).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpAddContentsCover);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Add Contents Cover Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconAddContentsCoverPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpAddContentsCover);
			if (GenericUtils.verifyWebElementTextContains(popHelpAddContentsCover,
					POPUP_MSG_HELPICON_Add_Contents_Cover)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of cash In Your Home To Be Insured
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfcashInYourHomeToBeInsured(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpcashInYourHomeToBeInsured);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpcashInYourHomeToBeInsured);
			//iconHelpcashInYourHomeToBeInsured.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpcashInYourHomeToBeInsured).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpcashInYourHomeToBeInsured);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon cash In Your Home To Be Insured Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconcashInYourHomeToBeInsuredPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpcashInYourHomeToBeInsured);
			if (GenericUtils.verifyWebElementTextContains(popHelpcashInYourHomeToBeInsured,
					POPUP_MSG_HELPICON_Cash_In_Your_Home_To_Be_Insured)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of credit Cards To Be Insured
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOfcreditCardsToBeInsured(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpcreditCardsToBeInsured);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpcreditCardsToBeInsured);
			//iconHelpcreditCardsToBeInsured.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpcreditCardsToBeInsured).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpcreditCardsToBeInsured);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon credit Cards To Be Insured Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconcreditCardsToBeInsuredPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpcreditCardsToBeInsured);
			if (GenericUtils.verifyWebElementTextContains(popHelpcreditCardsToBeInsured,
					POPUP_MSG_HELPICON_Credit_Cards_To_Be_Insured)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of frozen Food To Be Insured
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOffrozenFoodToBeInsured(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpfrozenFoodToBeInsured);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpfrozenFoodToBeInsured);
			//iconHelpfrozenFoodToBeInsured.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpfrozenFoodToBeInsured).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpfrozenFoodToBeInsured);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon credit Cards To Be Insured Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconfrozenFoodToBeInsuredPopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpfrozenFoodToBeInsured);
			if (GenericUtils.verifyWebElementTextContains(popHelpfrozenFoodToBeInsured,
					POPUP_MSG_HELPICON_Frozen_Food_To_Be_Insured)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of add Personal Possessions Within Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIaddPersonalPossessionsWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpaddPersonalPossessionsWithinHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpaddPersonalPossessionsWithinHome);
			//iconHelpaddPersonalPossessionsWithinHome.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpaddPersonalPossessionsWithinHome).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpaddPersonalPossessionsWithinHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon add Personal Possessions Within Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconaddPersonalPossessionsWithinHomePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpaddPersonalPossessionsWithinHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpaddPersonalPossessionsWithinHome,
					POPUP_MSG_HELPICON_Add_Personal_Possessions_Within_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Do you want to Add Another Item In Within Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIaddAnotherItemInWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpaddAnotherItemInWithinHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpaddAnotherItemInWithinHome);
			//iconHelpaddAnotherItemInWithinHome.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpaddAnotherItemInWithinHome).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpaddAnotherItemInWithinHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Do you want to Add Another Item In Within Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconaddAnotherItemInWithinHomePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpaddAnotherItemInWithinHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpaddAnotherItemInWithinHome,
					POPUP_MSG_HELPICON_Add_Another_Item_In_Within_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Item type New Specified Item Within Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIItemtypeNewSpecifiedItemWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpItemtypeNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpItemtypeNewSpecifiedItemWithinHome);
			//iconHelpItemtypeNewSpecifiedItemWithinHome.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpItemtypeNewSpecifiedItemWithinHome).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpItemtypeNewSpecifiedItemWithinHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Item type New Specified Item Within Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconItemtypeNewSpecifiedItemWithinHome() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpItemtypeNewSpecifiedItemWithinHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpItemtypeNewSpecifiedItemWithinHome,
					POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Within_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Item value New Specified Item Within Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIItemvalueNewSpecifiedItemWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpItemvalueNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpItemvalueNewSpecifiedItemWithinHome);
			//iconHelpItemvalueNewSpecifiedItemWithinHome.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpItemvalueNewSpecifiedItemWithinHome).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpItemvalueNewSpecifiedItemWithinHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Item type New Specified Item Within Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconItemvalueNewSpecifiedItemWithinHome() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpItemvalueNewSpecifiedItemWithinHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpItemvalueNewSpecifiedItemWithinHome,
					POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Within_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Description Of Item New Specified Item Within Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIDescriptionOfItemNewSpecifiedItemWithinHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpDescriptionOfItemNewSpecifiedItemWithinHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpDescriptionOfItemNewSpecifiedItemWithinHome);
			//iconHelpDescriptionOfItemNewSpecifiedItemWithinHome.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpDescriptionOfItemNewSpecifiedItemWithinHome).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpDescriptionOfItemNewSpecifiedItemWithinHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Description Of Item New Specified Item Within Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconDescriptionOfItemNewSpecifiedItemWithinHome() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpDescriptionOfItemNewSpecifiedItemWithinHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpDescriptionOfItemNewSpecifiedItemWithinHome,
					POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Within_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of add Personal Possessions Outside Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIaddPersonalPossessionsOutsideHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpaddPersonalPossessionsOutsideHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpaddPersonalPossessionsOutsideHome);
			//iconHelpaddPersonalPossessionsOutsideHome.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpaddPersonalPossessionsOutsideHome).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpaddPersonalPossessionsOutsideHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Description Of Item New Specified Item Within Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconaddPersonalPossessionsOutsideHome() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpaddPersonalPossessionsOutsideHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpaddPersonalPossessionsOutsideHome,
					POPUP_MSG_HELPICON_Add_Personal_Possessions_Outside_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Do you want to Add Another Item In Outside Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIaddAnotherItemInOutsideHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpaddAnotherItemInOutsideHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpaddAnotherItemInOutsideHome);
			//iconHelpaddAnotherItemInOutsideHome.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpaddAnotherItemInOutsideHome).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpaddAnotherItemInOutsideHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Do you want to Add Another Item In Outside Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconaddAnotherItemInOutsideHomePopupMessage() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpaddAnotherItemInOutsideHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpaddAnotherItemInOutsideHome,
					POPUP_MSG_HELPICON_Add_Another_Item_In_Outside_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Item type New Specified Item Outside Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIItemtypeNewSpecifiedItemOutsideHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpItemtypeNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpItemtypeNewSpecifiedItemOutsideHome);
			//iconHelpItemtypeNewSpecifiedItemOutsideHome.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpItemtypeNewSpecifiedItemOutsideHome).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpItemtypeNewSpecifiedItemOutsideHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Description Of Item New Specified Item Within Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconItemtypeNewSpecifiedItemOutsideHome() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpItemtypeNewSpecifiedItemOutsideHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpItemtypeNewSpecifiedItemOutsideHome,
					POPUP_MSG_HELPICON_Itemtype_New_Specified_Item_Outside_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Item value New Specified Item Outside Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIItemvalueNewSpecifiedItemOutsideHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpItemvalueNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpItemvalueNewSpecifiedItemOutsideHome);
			//iconHelpItemvalueNewSpecifiedItemOutsideHome.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpItemvalueNewSpecifiedItemOutsideHome).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpItemvalueNewSpecifiedItemOutsideHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Item type New Specified Item Outside Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconItemvalueNewSpecifiedItemOutsideHome() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForelementToBeClickable(driver, iconHelpItemvalueNewSpecifiedItemOutsideHome, "");
			WaitUtils.waitForElement(driver, popHelpItemvalueNewSpecifiedItemOutsideHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpItemvalueNewSpecifiedItemOutsideHome,
					POPUP_MSG_HELPICON_Itemvalue_New_Specified_Item_Outside_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Description Of Item New Specified Item Outside Home
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIDescriptionOfItemNewSpecifiedItemOutsideHome(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpDescriptionOfItemNewSpecifiedItemOutsideHome);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpDescriptionOfItemNewSpecifiedItemOutsideHome);
			//iconHelpDescriptionOfItemNewSpecifiedItemOutsideHome.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpDescriptionOfItemNewSpecifiedItemOutsideHome).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpDescriptionOfItemNewSpecifiedItemOutsideHome);
			Log.message("Hovered on the HelpIcon Of Add Contents Cover", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * verify HelpIcon Description Of Item New Specified Item Outside Home Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconDescriptionOfItemNewSpecifiedItemOutisdeHome() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpDescriptionOfItemNewSpecifiedItemOutsideHome);
			if (GenericUtils.verifyWebElementTextContains(popHelpDescriptionOfItemNewSpecifiedItemOutsideHome,
					POPUP_MSG_HELPICON_Description_Of_Item_New_Specified_Item_Outside_Home)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Add Contents Cover : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Title of People
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconTitleOfPeople(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpTitleOfPeople);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpTitleOfPeople);
			//iconHelpTitleOfPeople.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTitleOfPeople).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpTitleOfPeople);
			Log.message("Hovered on the HelpIcon Of Title Of People", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Title Of People : " + e);
		}
	}

	/**
	 * verify HelpIcon Title Of People Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconTitleOfPeople() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpTitleOfPeople);
			if (GenericUtils.verifyWebElementTextContains(popHelpTitleOfPeople,
					POPUP_MSG_HELPICON_Title_Of_People)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Title Of People : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Occupation
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconOccupation(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpOccupation);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpOccupation);
			//iconHelpOccupation.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpOccupation).click();
			action.perform();*/
			/*((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpOccupation);*/
			WaitUtils.waitForElement(driver, popHelpOccupation);
			Log.message("Hovered on the HelpIcon Of Occupation", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Occupation : " + e);
		}
	}

	/**
	 * verify HelpIcon Occupation Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconOccupation() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpOccupation);
			if (GenericUtils.verifyWebElementTextContains(popHelpOccupation,
					POPUP_MSG_HELPICON_Occupation)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Occupation : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Business
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconBusiness(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpBusiness);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpBusiness);
			//iconHelpBusiness.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpBusiness).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpBusiness);
			Log.message("Hovered on the HelpIcon Of Business", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Business : " + e);
		}
	}

	/**
	 * verify HelpIcon Business Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconBusiness() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpBusiness);
			if (GenericUtils.verifyWebElementTextContains(popHelpBusiness,
					POPUP_MSG_HELPICON_Business)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Business : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Employment Status
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconEmploymentStatus(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpEmploymentStatus);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpEmploymentStatus);
			//iconHelpEmploymentStatus.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpEmploymentStatus).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpEmploymentStatus);
			Log.message("Hovered on the HelpIcon Of Employment Status", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Employment Status : " + e);
		}
	}

	/**
	 * verify HelpIcon Employment Status Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconEmploymentStatus() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpEmploymentStatus);
			if (GenericUtils.verifyWebElementTextContains(popHelpEmploymentStatus,
					POPUP_MSG_HELPICON_Employment_Status)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Employment Status : " + e);
		}
	}

	/**
	 * hover On Help Icon Of main occupation
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconMainOccupation(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpMainOccupation);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpMainOccupation);
			//iconHelpMainOccupation.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpMainOccupation).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpMainOccupation);
			Log.message("Hovered on the HelpIcon Of main occupation", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of main occupation : " + e);
		}
	}

	/**
	 * verify HelpIcon main occupation Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconMainOccupation() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpMainOccupation);
			if (GenericUtils.verifyWebElementTextContains(popHelpMainOccupation,
					POPUP_MSG_HELPICON_Main_Occupation)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of main occupation : " + e);
		}
	}

	/**
	 * hover On Help Icon Of type of ownership
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconTypeOfOwnership(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpTypeOfOwnership);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpTypeOfOwnership);
			//iconHelpTypeOfOwnership.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTypeOfOwnership).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpTypeOfOwnership);
			Log.message("Hovered on the HelpIcon Of type of ownership", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of type of ownership : " + e);
		}
	}

	/**
	 * verify HelpIcon type of ownership Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconTypeOfOwnership() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpTypeOfOwnership);
			if (GenericUtils.verifyWebElementTextContains(popHelpTypeOfOwnership,
					POPUP_MSG_HELPICON_Type_Of_Ownership)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of type of ownership : " + e);
		}
	}

	/**
	 * hover On Help Icon Of type of occupancy
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconTypeOfOccupancy(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpTypeOfOccupancy);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpTypeOfOccupancy);
			//iconHelpTypeOfOccupancy.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpTypeOfOccupancy).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpTypeOfOccupancy);
			Log.message("Hovered on the HelpIcon Of type of Occupancy", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of type of Occupancy : " + e);
		}
	}

	/**
	 * verify HelpIcon type of occupancy Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconTypeOfOccupancy() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpTypeOfOccupancy);
			if (GenericUtils.verifyWebElementTextContains(popHelpTypeOfOccupancy,
					POPUP_MSG_HELPICON_Type_Of_Occupancy)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of type of Occupancy : " + e);
		}
	}

	/**
	 * hover On Help Icon Of How many people in total normally live in the property
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPeopleNormallyLiveInTheProperty(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPeopleNormallyLiveInTheProperty);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPeopleNormallyLiveInTheProperty);
			//iconHelpPeopleNormallyLiveInTheProperty.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPeopleNormallyLiveInTheProperty).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPeopleNormallyLiveInTheProperty);
			Log.message("Hovered on the HelpIcon Of How many people in total normally live in the property", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of How many people in total normally live in the property : " + e);
		}
	}

	/**
	 * verify HelpIcon How many people in total normally live in the property Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPeopleNormallyLiveInTheProperty() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPeopleNormallyLiveInTheProperty);
			if (GenericUtils.verifyWebElementTextContains(popHelpPeopleNormallyLiveInTheProperty,
					POPUP_MSG_HELPICON_People_Normally_Live_In_The_Property)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of How many people in total normally live in the property : " + e);
		}
	}

	/**
	 * hover On Help Icon Of How many children under 18 live in the property
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconChildrenUnder18LiveInTheProperty(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpChildrenUnder18LiveInTheProperty);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpChildrenUnder18LiveInTheProperty);
			//iconHelpChildrenUnder18LiveInTheProperty.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpChildrenUnder18LiveInTheProperty).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpChildrenUnder18LiveInTheProperty);
			Log.message("Hovered on the HelpIcon Of How many children under 18 live in the property", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of How many children under 18 live in the property : " + e);
		}
	}

	/**
	 * verify HelpIcon How many children under 18 live in the property Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconChildrenUnder18LiveInTheProperty() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpChildrenUnder18LiveInTheProperty);
			if (GenericUtils.verifyWebElementTextContains(popHelpChildrenUnder18LiveInTheProperty,
					POPUP_MSG_HELPICON_Children_Under_18_Live_In_The_Property)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of How many children under 18 live in the property : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Occupation
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPolicyHolderOccupation(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPolicyHolderOccupation);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPolicyHolderOccupation);
			//iconHelpPolicyHolderOccupation.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPolicyHolderOccupation).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPolicyHolderOccupation);
			Log.message("Hovered on the HelpIcon Of PolicyHolder Occupation", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of PolicyHolder Occupation : " + e);
		}
	}

	/**
	 * verify HelpIcon Occupation Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPolicyHolderOccupation() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPolicyHolderOccupation);
			if (GenericUtils.verifyWebElementTextContains(popHelpPolicyHolderOccupation,
					POPUP_MSG_HELPICON_Policy_Holder_Occupation)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of PolicyHolder Occupation : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Business
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPolicyHolderBusiness(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPolicyHolderBusiness);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPolicyHolderBusiness);
			//iconHelpPolicyHolderBusiness.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPolicyHolderBusiness).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPolicyHolderBusiness);
			Log.message("Hovered on the HelpIcon Of PolicyHolder Business", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of PolicyHolder Business : " + e);
		}
	}

	/**
	 * verify HelpIcon Business Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPolicyHolderBusiness() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPolicyHolderBusiness);
			if (GenericUtils.verifyWebElementTextContains(popHelpPolicyHolderBusiness,
					POPUP_MSG_HELPICON_Policy_Holder_Business)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of PolicyHolder Business : " + e);
		}
	}

	/**
	 * hover On Help Icon Of Employment Status
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPolicyHolderEmploymentStatus(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPolicyHolderEmploymentStatus);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPolicyHolderEmploymentStatus);
			//iconHelpPolicyHolderEmploymentStatus.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPolicyHolderEmploymentStatus).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPolicyHolderEmploymentStatus);
			Log.message("Hovered on the HelpIcon Of PolicyHolder Employment Status", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of PolicyHolder Employment Status : " + e);
		}
	}

	/**
	 * verify HelpIcon Employment Status Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPolicyHolderEmploymentStatus() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPolicyHolderEmploymentStatus);
			if (GenericUtils.verifyWebElementTextContains(popHelpPolicyHolderEmploymentStatus,
					POPUP_MSG_HELPICON_Policy_Holder_Employment_Status)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of PolicyHolder Employment Status : " + e);
		}
	}

	/**
	 * hover On Help Icon Of main occupation
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconPolicyHolderMainOccupation(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpPolicyHolderMainOccupation);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpPolicyHolderMainOccupation);
			//iconHelpPolicyHolderMainOccupation.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpPolicyHolderMainOccupation).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpPolicyHolderMainOccupation);
			Log.message("Hovered on the HelpIcon Of PolicyHolder main occupation", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of PolicyHolder main occupation : " + e);
		}
	}

	/**
	 * verify HelpIcon main occupation Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconPolicyHolderMainOccupation() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpPolicyHolderMainOccupation);
			if (GenericUtils.verifyWebElementTextContains(popHelpPolicyHolderMainOccupation,
					POPUP_MSG_HELPICON_Policy_Holder_Main_Occupation)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of PolicyHolder main occupation : " + e);
		}
	}
	
	/**
	 * To enter Change Overnight Location Detail For Enter Address Manually
	 * 
	 * @param country
	 * @param postcode
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void enterAddressManually(String option, String value,ExtentTest extentedReport, boolean screenshot)
			throws Exception {
		try {
			if (option.equalsIgnoreCase("Postcode")) {
				WaitUtils.waitForElement(driver, txtPostcodeOfProperty);
				txtPostcodeOfProperty.clear();
				txtPostcodeOfProperty.sendKeys(value);
				Log.message("Entered the Postcode : " + value, driver, extentedReport, false);
			} else if (option.equalsIgnoreCase("Address1")) {
				WaitUtils.waitForElement(driver, txtAddressLine1);
				txtAddressLine1.click();
				WaitUtils.waitForSpinner(driver);
				txtAddressLine1.clear();
				txtAddressLine1.sendKeys(value);
				Log.message("Entered the Address1 : " + value, driver, extentedReport, screenshot);
			} else if (option.equalsIgnoreCase("Address2")) {
				txtAddressLine2.click();
				WaitUtils.waitForSpinner(driver);
				txtAddressLine2.clear();
				txtAddressLine2.sendKeys(value);
				Log.message("Entered the Address2 : " + value, driver, extentedReport, screenshot);
			} else if (option.equalsIgnoreCase("Address3")) {
				txtAddressLine3.click();
				WaitUtils.waitForSpinner(driver);
				txtAddressLine3.clear();
				txtAddressLine3.sendKeys(value);
				Log.message("Entered the Address3 : " + value, driver, extentedReport, screenshot);
			} else if (option.equalsIgnoreCase("Address4")) {
				txtAddressLine4.click();
				WaitUtils.waitForSpinner(driver);
				txtAddressLine4.clear();
				txtAddressLine4.sendKeys(value);
				txtAddressLine4.click();
				Log.message("Entered the Address4 : " + value, driver, extentedReport, screenshot);
			}
			WaitUtils.waitForSpinner(driver);
		} catch (Exception e) {
			throw new Exception("Error while entering Change Overnight Location Detail : " + option + "manually : " + e);
		}
	}
	
	/**
	 * Click Enter Manual Address Button
	 *
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickEnterManualAddressBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, btnEnterManualAdd);
			WaitUtils.waitForSpinner(driver);
			btnEnterManualAdd.click();
			WaitUtils.waitForElement(driver, btnEnterManualAdd);
			Log.message("Clicked on Enter Manual Address button in change my policy address screen", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Unable to click Enter Manual Address button in change my policy address screen : " + e);
		}
	}
	
	/**
	 * Click Change Address Button
	 *
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void clickChangeAddressBtn(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			btnChangeAddress.click();
			WaitUtils.waitForElement(driver, btnEnterManualAdd);
			Log.message("Clicked on Change Address button in change my policy address screen", driver, extentedReport,
					screenshot);
		} catch (Exception e) {
			throw new Exception("Unable to click Change Address button in change my policy address screen : " + e);
		}
	}
	
	/**
	 * hover On Help Icon Of Title of Your Contents
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public void hoverOnHelpIconTitleYourContents(ExtentTest extentedReport, boolean screenshot) throws Exception {
		try {
			WaitUtils.waitForElement(driver, iconHelpvalueOfTitleYourContents);
			WaitUtils.waitForSpinner(driver);
			WaitUtils.waitForSpinner(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", iconHelpvalueOfTitleYourContents);
			//iconHelpvalueOfTitleYourContents.click();
			/*Actions action = new Actions(driver);
			action.moveToElement(iconHelpvalueOfTitleYourContents).click();
			action.perform();*/
			WaitUtils.waitForElement(driver, popHelpvalueOfTitleYourContents);
			Log.message("Hovered on the HelpIcon Of Title of Your Contents", driver, extentedReport, screenshot);

		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Title of Your Contents : " + e);
		}
	}

	/**
	 * verify HelpIcon Title of Your Contents Popup Message
	 * 
	 * @param extentedReport
	 * @param screenshot
	 * @throws Exception
	 * 
	 */
	public boolean verifyHelpIconTitleYourContents() throws Exception {
		try {
			boolean isDisplayed = false;
			WaitUtils.waitForElement(driver, popHelpvalueOfTitleYourContents);
			if (GenericUtils.verifyWebElementTextContains(popHelpvalueOfTitleYourContents,
					POPUP_MSG_HELPICON_Title_Your_Contents)) {
				isDisplayed = true;
			}
			return isDisplayed;
		} catch (Exception e) {
			throw new Exception("Error while hovering On HelpIcon Of Title of Your Contents : " + e);
		}
	}
	
}
