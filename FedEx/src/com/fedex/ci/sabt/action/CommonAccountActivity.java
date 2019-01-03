package com.fedex.ci.sabt.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.fedex.ci.sabt.model.RowData;
import com.fedex.ci.sabt.util.TestConstants;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

public abstract class CommonAccountActivity extends MainAction {
	private static final FedExLoggerInterface logger = FedExLogger.getLogger(CommonAccountActivity.class);
	protected RowData data = null;
	
	public CommonAccountActivity(RowData data) {
		super("Test Case # " + data.getRowData().get("Test Case #") + " " + data.getRowData().get("Test Case Desc"));
		this.data = data;
	}

	protected boolean userAcceptanceDialog() throws Exception {
		// user acceptance dialog
		if ( "N".equals(data.getRowData().get("confirm")) ) {
			
			System.out.println("no confirmation");
			
			logger.debug("no confirmation");
			clickById("createAccountSearchForm:userDidNotAcceptCreateCommand");
			
			String expMsg = data.getRowData().get("homePageMsg");
			
			String actMsg = driver.findElement(By.cssSelector("div.modulePanelHeaderTitleCenter")).getText();
			logger.debug("homeText = " + actMsg);
			
			//Thread.sleep(TestConstants.SLEEP_SHORT_PAUSE);
			shortPause();
			
			checkText(expMsg, actMsg);
			return false;
		} else {
			clickById("createAccountSearchForm:userAcceptedCreateCommand");
			System.out.println("confirmed----1111");
			
			logger.debug("confirmed");
		}
		return true;
	}
	
	protected void setCustomerType() throws Exception {
		shortPause();
		setText("accountForm:accountTabSet:0:numberOfAccts_text", data.getRowData().get("numberOfAccts"));
		selectByVisibleText("accountForm:accountTabSet:0:customerType_text", data.getRowData().get("customerType"));
		selectByVisibleText("accountForm:accountTabSet:0:entAccountType_text", data.getRowData().get("entAccountType"));
		selectByVisibleText("accountForm:accountTabSet:0:opco_text", data.getRowData().get("opco"));
		logger.debug("setting opcoAccountType");
		selectByVisibleText("accountForm:accountTabSet:0:opcoAccountType_text", data.getRowData().get("opcoAccountType"));
		logger.debug("setting opcoAccountSubType from data=" + data.getRowData().get("opcoAccountType") + 
				",gui=" + getVisibleText("accountForm:accountTabSet:0:opcoAccountType_text"));
		selectByVisibleText("accountForm:accountTabSet:0:opcoAccountSubType_text", data.getRowData().get("opcoAccountSubType"));
	}
	
	protected void setCustomer(String custType, String key) throws Exception {
		shortPause();
		String mainType = "";
		String contactType = "";
		
		if ("enterprise".equals(key)) {
			mainType = "epa";
			contactType = "ec";
		} else if ("primary".equals(key)) {
			mainType = "pa";
			contactType = "ps";
		} else if ("billing".equals(key)) {
			mainType = "pba";
			contactType = "pb";
		}
		
		logger.debug("setting customer, mainType = " + mainType);
		
		
		
		setText("accountForm:accountTabSet:0:" + custType + "_companyName_text", data.getRowData().get(mainType + "_companyName"));
		setPerson(custType, mainType);
		setAddress(custType + "_main", mainType);
		setTelecom(custType + "_main", mainType);
		
		logger.debug("setting customer, contactType = " + contactType);
		
		if ("billing".equals(key))
			setText("accountForm:accountTabSet:0:" + custType + "_contact_companyName_text", data.getRowData().get(contactType + "_companyName"));

		setPerson(custType + "_contact", contactType);
		setAddress(custType + "_contact", contactType);
		setTelecom(custType + "_contact", contactType);
	}

	protected void setOpcoReferences() throws Exception{
		shortPause();
		logger.debug("setting Opco References");
		
		if(isNotNull(data.getRowData().get("deLinkOpco1"))){
			clickById("accountForm:accountTabSet:0:opcoReferenceTable:0:opcoReferenceTabDeLink");
		}
		
		if(isNotNull(data.getRowData().get("linkOpco1"))
				&& isNotNull(data.getRowData().get("linkOpcoRef1"))){
			selectByVisibleText("accountForm:accountTabSet:0:OpcoReferenceType",data.getRowData().get("linkOpco1"));
			
			setText("accountForm:accountTabSet:0:opcoReferenceNumber",data.getRowData().get("linkOpcoRef1"));
			clickById("accountForm:accountTabSet:0:opcoReferenceTabLink");
		
		
		if(isNotNull(data.getRowData().get("deLinkOpco2"))){
			clickById("accountForm:accountTabSet:0:opcoReferenceTable:0:opcoReferenceTabDeLink");			
		}		
		/*if(isNotNull(data.getRowData().get("linkOpco1"))
				&& isNotNull(data.getRowData().get("linkOpcoRef1"))){
			selectByVisibleText("accountForm:accountTabSet:0:OpcoReferenceType",data.getRowData().get("linkOpco1"));
			
			setText("accountForm:accountTabSet:0:opcoReferenceNumber",data.getRowData().get("linkOpcoRef1"));
			clickById("accountForm:accountTabSet:0:opcoReferenceTabLink");*/
		}
		if(isNotNull(data.getRowData().get("linkOpco2")) && 
				isNotNull(data.getRowData().get("linkOpcoRef2"))){
			selectByVisibleText("accountForm:accountTabSet:0:OpcoReferenceType",data.getRowData().get("linkOpco2"));
			setText("accountForm:accountTabSet:0:opcoReferenceNumber",data.getRowData().get("linkOpcoRef2"));
			clickById("accountForm:accountTabSet:0:opcoReferenceTabLink");
		}
	}
	
	protected void setPerson(String custType, String key) throws Exception {
		logger.debug("setting person, custType = " + custType + ", key = " + key);
		setText("accountForm:accountTabSet:0:" + custType + "_prefix_text", data.getRowData().get(key + "_prefix"));
		setText("accountForm:accountTabSet:0:" + custType + "_firstName_text", data.getRowData().get(key + "_firstName"));
		setText("accountForm:accountTabSet:0:" + custType + "_middleName_text", data.getRowData().get(key + "_middleName"));
		setText("accountForm:accountTabSet:0:" + custType + "_lastName_text", data.getRowData().get(key + "_lastName"));
		setText("accountForm:accountTabSet:0:" + custType + "_suffix_text", data.getRowData().get(key + "_suffix"));
		setText("accountForm:accountTabSet:0:" + custType + "_title_text", data.getRowData().get(key + "_title"));
	}
	
	protected void setSecondary(String custType, String key) throws Exception {
		logger.debug("setting secondary, custType = " + custType + ", key = " + key);
		if ( !"createAcctFromScratch".equals(data.getRowData().get("action")) &&
				"Y".equals(data.getRowData().get(key + "_unit_remove_flg")) ) {
			logger.debug("removing the previous secondary items");
			try {
				//clickMultipleElements("accountForm:accountTabSet:0:" + custType + "_units_table:", ":" + custType + "_rem_unit");
				clickMultipleElements("accountForm:accountTabSet:0:" + custType + "_units_table:", "Remove");
			} catch (Exception e) {
				logger.error("ignoring the exception " + e.getMessage());
			}
			logger.debug("removed the previous secondary items");
		}

		if ( isNotNull(data.getRowData().get(key + "_unit")) ) {
			String[] units = null;
			if ( isNotNull(data.getRowData().get(key + "_unit")) )
				units = data.getRowData().get(key + "_unit").split(":");
			
			String[] values = null;
			if ( isNotNull(data.getRowData().get(key + "_unitValue")) )
				values = data.getRowData().get(key + "_unitValue").split(":");
			
			logger.debug("removing the previous secondary items for units.length");
			if ( !"createAcctFromScratch".equals(data.getRowData().get("action")) &&
					!"Y".equals(data.getRowData().get(key + "_unit_remove_flg")) && (units != null && units.length > 1) ) {
				try {
					//clickMultipleElements("//*[starts-with(@id, 'accountForm:accountTabSet:0:"+ custType + "_units_table:')]", "Remove");
					clickMultipleElements("accountForm:accountTabSet:0:" + custType + "_units_table:", "Remove");
				} catch (Exception e) {
					logger.error("ignoring the exception " + e.getMessage());
				}
			}
			logger.debug("removed the previous secondary items for units.length");
			
			for (byte b=0;b<units.length;b++ ) {
				if ( units != null && units.length > b )
					setText("accountForm:accountTabSet:0:" + custType + "_unit_text", units[b]);
				if ( values != null && values.length > b )
					setText("accountForm:accountTabSet:0:" + custType + "_unitValue_text", values[b]);
				
				if ( units.length == 1 )
					break;
				
				clickById("accountForm:accountTabSet:0:" + custType + "_add_unit");
				logger.debug("one sec unit added");
			}
		}
		logger.debug("setSecondary complete");
	}
	
	protected void setAddress(String custType, String key) throws Exception {
		logger.debug("setting address, custType = " + custType + ", key = " + key);
		selectByVisibleText("accountForm:accountTabSet:0:" + custType + "_country_text", data.getRowData().get(key + "_cntry"));
		setText("accountForm:accountTabSet:0:" + custType + "_address1_text", data.getRowData().get(key + "_address1"));
		setText("accountForm:accountTabSet:0:" + custType + "_address2_text", data.getRowData().get(key + "_address2"));
		setText("accountForm:accountTabSet:0:" + custType + "_address3_text", data.getRowData().get(key + "_address3"));
		
		setSecondary(custType, key);
		
		logger.debug("after setting units");
		setText("accountForm:accountTabSet:0:" + custType + "_store_id_text", data.getRowData().get(key + "_storeId"));
		setText("accountForm:accountTabSet:0:" + custType + "_city_text", data.getRowData().get(key + "_city"));
		logger.debug("after city");
		setText("accountForm:accountTabSet:0:" + custType + "_state_text", data.getRowData().get(key + "_state"));
		setText("accountForm:accountTabSet:0:" + custType + "_postalCode_text", data.getRowData().get(key + "_postalCd"));
		// accountForm:accountTabSet:0:paCustForm_main_ovrd
		// accountForm:accountTabSet:0:paCustForm_main_reasonCode_text
		if ( "Y".equals(data.getRowData().get(key + "_ovrd_flg")) )
			clickById("accountForm:accountTabSet:0:" + custType + "_ovrd");
		
		if ( "Y".equals(data.getRowData().get(key + "_addr_lookup")) ) {
			clickById("accountForm:accountTabSet:0:" + custType + "_addressLookup");
			
			// select the first address
			// accountForm:addressLookupTable:0:j_idt1588
			WebElement ele = findLinkByName("Select", "//*[starts-with(@id, 'accountForm:addressLookupTable:0:')]");
			checkNotNull(ele, "Select");
			ele.click();
		}
		
		selectByVisibleText("accountForm:accountTabSet:0:" + custType + "_reasonCode_text", data.getRowData().get(key + "_ovrd_reason"));
	}
	
	protected void setTelecom(String custType, String key) throws Exception {
		logger.debug("setting teleCom, custType = " + custType + ", key = " + key);
		
		if ( !"createAcctFromScratch".equals(data.getRowData().get("action")) &&
				"Y".equals(data.getRowData().get(key + "_telecom_remove_flg")) ) {
			try {
				clickMultipleElements("accountForm:accountTabSet:0:" + custType + "_telecom_table:", "Remove");
			} catch (Exception e) {
				logger.error("ignoring the exception " + e.getMessage());
			}
		}
		
		if ( isNotNull(data.getRowData().get(key + "_phoneCntryCd")) ) {

			String[] telecoms = null;
			if ( isNotNull(data.getRowData().get(key + "_telecom")) )
				telecoms = data.getRowData().get(key + "_telecom").split(":");
			
			String[] countries = null;
			if ( isNotNull(data.getRowData().get(key + "_phoneCntryCd")) )
				countries = data.getRowData().get(key + "_phoneCntryCd").split(":");
			
			String[] areaCds = null;
			if ( isNotNull(data.getRowData().get(key + "_phoneAreaCd")) )
				areaCds = data.getRowData().get(key + "_phoneAreaCd").split(":");
			
			String[] nbrs = null;
			if ( isNotNull(data.getRowData().get(key + "_phoneNumber")) )
				nbrs = data.getRowData().get(key + "_phoneNumber").split(":");
			
			String[] exts = null;
			if ( isNotNull(data.getRowData().get(key + "_extension")) )
				exts = data.getRowData().get(key + "_extension").split(":");
			
			String[] pins = null;
			if ( isNotNull(data.getRowData().get(key + "_pin")) )
				pins = data.getRowData().get(key + "_pin").split(":");

			// first remove all the telecoms
			// accountForm:accountTabSet:0:paCustForm_contact_telecom_table:0:paCustForm_contact_rem_tel
			if ( !"createAcctFromScratch".equals(data.getRowData().get("action")) &&
					!"Y".equals(data.getRowData().get(key + "_telecom_remove_flg")) 
					&& (countries != null && countries.length > 1) ) {
				try {
					//clickMultipleElements("//*[starts-with(@id, 'accountForm:accountTabSet:0:"+ custType + "_telecom_table:')]", "Remove");
					//clickMultipleElements("accountForm:accountTabSet:0:" + custType + "_telecom_table:", ":" + custType + "_rem_tel");
					clickMultipleElements("accountForm:accountTabSet:0:" + custType + "_telecom_table:", "Remove");
				} catch (Exception e) {
					logger.error("ignoring the exception " + e.getMessage());
				}
			}

			for (byte b=0;b<countries.length;b++ ) {
				if ( telecoms != null && telecoms.length > b )
					selectByVisibleText("accountForm:accountTabSet:0:" + custType + "_telecom_text", telecoms[b]);
				
				if ( countries != null && countries.length > b )
					setText("accountForm:accountTabSet:0:" + custType + "_phoneCntryCd", countries[b]);
				
				if ( areaCds != null && areaCds.length > b )
					setText("accountForm:accountTabSet:0:" + custType + "_phoneAreaCd", areaCds[b]);
				
				if ( nbrs != null && nbrs.length > b )
					setText("accountForm:accountTabSet:0:" + custType + "_phoneNumber", nbrs[b]);
				
				if ( exts != null && exts.length > b )
					setText("accountForm:accountTabSet:0:" + custType + "_extension", exts[b]);
				
				if ( pins != null && pins.length > b )
					setText("accountForm:accountTabSet:0:" + custType + "_pin", pins[b]);

				if ( countries.length == 1 )
					break;
				
				clickById("accountForm:accountTabSet:0:" + custType + "_tel");
			}
		}
	}

	protected void setCreditCard() throws Exception {
		logger.debug("setting credit card info");
		selectByVisibleText("accountForm:accountTabSet:0:ccType_text", data.getRowData().get("ccType"));
		setText("accountForm:accountTabSet:0:ccNumber_text", data.getRowData().get("ccNumber"));
		setText("accountForm:accountTabSet:0:ccExpDate_text", data.getRowData().get("ccExpDate"));
		setText("accountForm:accountTabSet:0:ccCCVNumber_text", data.getRowData().get("ccCCVNumber"));
	}

	protected void setRegulatoryInfo() throws Exception {
		shortPause();
		logger.debug("setting regulatory info");
		setText("accountForm:accountTabSet:0:taxId1_text", data.getRowData().get("taxId1"));
		setText("accountForm:accountTabSet:0:taxId2_text", data.getRowData().get("taxId2"));
		setText("accountForm:accountTabSet:0:taxId3_text", data.getRowData().get("taxId3"));
		setText("accountForm:accountTabSet:0:taxId4_text", data.getRowData().get("taxId4"));
		
		setText("accountForm:accountTabSet:0:taxId1Desc_text", data.getRowData().get("taxId1Desc"));
		setText("accountForm:accountTabSet:0:taxId2Desc_text", data.getRowData().get("taxId2Desc"));
		setText("accountForm:accountTabSet:0:taxId3Desc_text", data.getRowData().get("taxId3Desc"));
		setText("accountForm:accountTabSet:0:taxId4Desc_text", data.getRowData().get("taxId4Desc"));
		
		setText("accountForm:accountTabSet:0:customerVAT_text", data.getRowData().get("customerVAT"));
	}


	/*protected void setOpcoReferences() throws Exception{
		logger.debug("setting Opco References");
	}*/
	
	protected void setComments() throws Exception {
		shortPause();
		logger.debug("setting comments");
		if ( isNotNull(data.getRowData().get("commentType")) && isNotNull(data.getRowData().get("comment"))) {
			String[] commentTypes = data.getRowData().get("commentType").split(":");
			String[] comments = data.getRowData().get("comment").split(":");
			
			if ( commentTypes == null )
				return;
			
			for (byte b=0;b<commentTypes.length;b++ ) {
				if ( commentTypes != null && commentTypes.length > b )
					selectByVisibleText("accountForm:accountTabSet:0:commentType_text", commentTypes[b]);
				
				if ( comments != null && comments.length > b )
					setText("accountForm:accountTabSet:0:comment_text", comments[b]);
				
				if ( commentTypes.length == 1 )
					break;
				
				clickById("accountForm:accountTabSet:0:_add_cmnt");	
			}
		}
	}
	
	protected void checkCustomerType() throws Exception {
		checkById("accountForm:accountTabSet:0:numberOfAccts_text", data.getRowData().get("numberOfAccts"));
		checkByIdByVisibleText("accountForm:accountTabSet:0:customerType_text", data.getRowData().get("customerType"));
		checkByIdByVisibleText("accountForm:accountTabSet:0:entAccountType_text", data.getRowData().get("entAccountType"));
		checkByIdByVisibleText("accountForm:accountTabSet:0:opco_text", data.getRowData().get("opco"));
		logger.debug("setting opcoAccountType");
		checkByIdByVisibleText("accountForm:accountTabSet:0:opcoAccountType_text", data.getRowData().get("opcoAccountType"));
		logger.debug("setting opcoAccountSubType");
		checkByIdByVisibleText("accountForm:accountTabSet:0:opcoAccountSubType_text", data.getRowData().get("opcoAccountSubType"));
	}
	
	protected void checkCustomer(String custType, String key) throws Exception {
		String mainType = "";
		String contactType = "";
		
		if ("enterprise".equals(key)) {
			mainType = "epa";
			contactType = "ec";
		} else if ("primary".equals(key)) {
			mainType = "pa";
			contactType = "ps";
		} else if ("billing".equals(key)) {
			mainType = "pba";
			contactType = "pb";
		}
		
		logger.debug("checking customer, mainType = " + mainType + ", comp = " + data.getRowData().get(mainType + "_companyName"));
		
		checkById("accountForm:accountTabSet:0:" + custType + "_companyName_text", data.getRowData().get(mainType + "_companyName"));
		checkPerson(custType, mainType);
		checkAddress(custType + "_main", mainType);
		checkTelecom(custType + "_main", mainType);
		
		if ("billing".equals(key))
			checkById("accountForm:accountTabSet:0:" + custType + "_contact_companyName_text", data.getRowData().get(contactType + "_companyName"));
		
		logger.debug("checking customer, contactType = " + contactType);
		checkPerson(custType + "_contact", contactType);
		checkAddress(custType + "_contact", contactType);
		checkTelecom(custType + "_contact", contactType);
	}

	protected void checkPerson(String custType, String key) throws Exception {
		logger.debug("check person, custType = " + custType + ", key = " + key);
		checkById("accountForm:accountTabSet:0:" + custType + "_prefix_text", data.getRowData().get(key + "_prefix"));
		checkById("accountForm:accountTabSet:0:" + custType + "_firstName_text", data.getRowData().get(key + "_firstName"));
		checkById("accountForm:accountTabSet:0:" + custType + "_middleName_text", data.getRowData().get(key + "_middleName"));
		checkById("accountForm:accountTabSet:0:" + custType + "_lastName_text", data.getRowData().get(key + "_lastName"));
		checkById("accountForm:accountTabSet:0:" + custType + "_suffix_text", data.getRowData().get(key + "_suffix"));
		checkById("accountForm:accountTabSet:0:" + custType + "_title_text", data.getRowData().get(key + "_title"));
	}

	protected void checkSecondary(String custType, String key) throws Exception {
		logger.debug("check secondary, custType = " + custType + ", key = " + key);
		// accountForm:accountTabSet:0:paCustForm_main_units_table:0
		if ( isNotNull(data.getRowData().get(key + "_unit")) ) {

			String[] units = data.getRowData().get(key + "_unit").split(":");
			String[] values = data.getRowData().get(key + "_unitValue").split(":");
			String tmp = "";
			
			if ( units != null && units.length > 1 ) {
				for (byte b=0;b<units.length;b++ ) {
					if ( units != null && units.length > b ) {
						tmp = findTextByXpathFromList("//*[starts-with(@id, 'accountForm:accountTabSet:0:" + custType + "_units_table:')]", units[b]);
						checkText(units[b], tmp);
					}
					
					if ( values != null && values.length > b ) {
						tmp = findTextByXpathFromList("//*[starts-with(@id, 'accountForm:accountTabSet:0:" + custType + "_units_table:')]", values[b]);
						checkText(values[b], tmp);
					}
				}
			} else {
				if ( units != null && units.length > 0 )
					checkById("accountForm:accountTabSet:0:" + custType + "_unit_text", units[0]);
			
				if ( values != null && values.length > 0 )
					checkById("accountForm:accountTabSet:0:" + custType + "_unitValue_text", values[0]);
			}
		}
	}

	protected void checkAddress(String custType, String key) throws Exception {
		logger.debug("check address, custType = " + custType + ", key = " + key);
		checkByIdByVisibleText("accountForm:accountTabSet:0:" + custType + "_country_text", data.getRowData().get(key + "_cntry"));
		checkById("accountForm:accountTabSet:0:" + custType + "_address1_text", data.getRowData().get(key + "_address1"));
		checkById("accountForm:accountTabSet:0:" + custType + "_address2_text", data.getRowData().get(key + "_address2"));
		checkById("accountForm:accountTabSet:0:" + custType + "_address3_text", data.getRowData().get(key + "_address3"));
		
		checkSecondary(custType, key);
		
		logger.debug("after setting units");
		checkById("accountForm:accountTabSet:0:" + custType + "_store_id_text", data.getRowData().get(key + "_storeId"));
		checkById("accountForm:accountTabSet:0:" + custType + "_city_text", data.getRowData().get(key + "_city"));
		logger.debug("after city");
		checkById("accountForm:accountTabSet:0:" + custType + "_state_text", data.getRowData().get(key + "_state"));
		checkById("accountForm:accountTabSet:0:" + custType + "_postalCode_text", data.getRowData().get(key + "_postalCd"));
	}
	
	protected void checkTelecom(String custType, String key) throws Exception {
		logger.debug("checking teleCom, custType = " + custType + ", key = " + key);
		
		//accountForm:accountTabSet:0:paCustForm_main_telecom_table:0
		
		if ( isNotNull(data.getRowData().get(key + "_phoneCntryCd")) ) {

			String[] telecoms = data.getRowData().get(key + "_telecom").split(":");
			String[] countries = data.getRowData().get(key + "_phoneCntryCd").split(":");
			String[] areaCds = data.getRowData().get(key + "_phoneAreaCd").split(":");
			String[] nbrs = data.getRowData().get(key + "_phoneNumber").split(":");
			String[] exts = data.getRowData().get(key + "_extension").split(":");
			String[] pins = data.getRowData().get(key + "_pin").split(":");
			String tmp = "";
			
			if ( countries != null && countries.length > 1 ) {
				for (byte b=0;b<countries.length;b++ ) {
					if ( telecoms != null && telecoms.length > b ) {
						tmp = findTextByXpathFromList("//*[starts-with(@id, 'accountForm:accountTabSet:0:"+ custType + "_telecom_table:')]", telecoms[b]);
						checkText(telecoms[b], tmp);
					}
					
					if ( countries != null && countries.length > b ) {
						tmp = findTextByXpathFromList("//*[starts-with(@id, 'accountForm:accountTabSet:0:"+ custType + "_telecom_table:')]", countries[b]);
						checkText(countries[b], tmp);
					}
				
					if ( areaCds != null && areaCds.length > b ) {
						tmp = findTextByXpathFromList("//*[starts-with(@id, 'accountForm:accountTabSet:0:"+ custType + "_telecom_table:')]", areaCds[b]);
						checkText(areaCds[b], tmp);
					}
				
					if ( nbrs != null && nbrs.length > b ) {
						tmp = findTextByXpathFromList("//*[starts-with(@id, 'accountForm:accountTabSet:0:"+ custType + "_telecom_table:')]", nbrs[b]);
						checkText(nbrs[b], tmp);
					}
				
					if ( exts != null && exts.length > b ) {
						tmp = findTextByXpathFromList("//*[starts-with(@id, 'accountForm:accountTabSet:0:"+ custType + "_telecom_table:')]", exts[b]);
						checkText(exts[b], tmp);
					}
				
					if ( pins != null && pins.length > b ) {
						tmp = findTextByXpathFromList("//*[starts-with(@id, 'accountForm:accountTabSet:0:"+ custType + "_telecom_table:')]", pins[b]);
						checkText(pins[b], tmp);
					}
				}
			} else {
				if ( telecoms != null && telecoms.length > 0 )
					checkByIdByVisibleText("accountForm:accountTabSet:0:" + custType + "_telecom_text", telecoms[0]);
			
				if ( countries != null && countries.length > 0 )
					checkById("accountForm:accountTabSet:0:" + custType + "_phoneCntryCd", countries[0]);
			
				if ( areaCds != null && areaCds.length > 0 )
					checkById("accountForm:accountTabSet:0:" + custType + "_phoneAreaCd", areaCds[0]);
			
				if ( nbrs != null && nbrs.length > 0 )
					checkById("accountForm:accountTabSet:0:" + custType + "_phoneNumber", nbrs[0]);
			
				if ( exts != null && exts.length > 0 )
					checkById("accountForm:accountTabSet:0:" + custType + "_extension", exts[0]);
			
				if ( pins != null && pins.length > 0 )
					checkById("accountForm:accountTabSet:0:" + custType + "_pin", pins[0]);
				
			}
		}
	}
	
	protected void checkCreditCard() throws Exception {
		logger.debug("checking credit card info");
		checkByIdByVisibleText("accountForm:accountTabSet:0:ccType_text", data.getRowData().get("ccType"));
		checkById("accountForm:accountTabSet:0:ccNumber_text", data.getRowData().get("ccNumber"));
		checkById("accountForm:accountTabSet:0:ccExpDate_text", data.getRowData().get("ccExpDate"));
		checkById("accountForm:accountTabSet:0:ccCCVNumber_text", data.getRowData().get("ccCCVNumber"));
	}

	protected void checkRegulatoryInfo() throws Exception {
		logger.debug("checking regulatory info");
		checkById("accountForm:accountTabSet:0:taxId1_text", data.getRowData().get("taxId1"));
		checkById("accountForm:accountTabSet:0:taxId2_text", data.getRowData().get("taxId2"));
		checkById("accountForm:accountTabSet:0:taxId3_text", data.getRowData().get("taxId3"));
		checkById("accountForm:accountTabSet:0:taxId4_text", data.getRowData().get("taxId4"));
		
		checkById("accountForm:accountTabSet:0:taxId1Desc_text", data.getRowData().get("taxId1Desc"));
		checkById("accountForm:accountTabSet:0:taxId2Desc_text", data.getRowData().get("taxId2Desc"));
		checkById("accountForm:accountTabSet:0:taxId3Desc_text", data.getRowData().get("taxId3Desc"));
		checkById("accountForm:accountTabSet:0:taxId4Desc_text", data.getRowData().get("taxId4Desc"));
		
		checkById("accountForm:accountTabSet:0:customerVAT_text", data.getRowData().get("customerVAT"));
	}

	protected void checkOpcoRefInfo() throws Exception {
		logger.debug("checking opco ref info");
	}
	
	protected void checkComments() throws Exception {
		logger.debug("checking comments");
		// accountForm:accountTabSet:0:_comments_table:0
		if ( isNotNull(data.getRowData().get("commentType")) && isNotNull(data.getRowData().get("comment"))) {
			String[] commentTypes = data.getRowData().get("commentType").split(":");
			String[] comments = data.getRowData().get("comment").split(":");
			String tmp = "";
			
			for (byte b=0;b<commentTypes.length;b++ ) {
				if ( commentTypes != null ) {
					tmp = findTextByXpathFromList("//*[starts-with(@id, 'accountForm:accountTabSet:0:_comments_table:')]", commentTypes[b]);
					logger.debug("act, exp = " + tmp + "," + commentTypes[b]);

					checkText(commentTypes[b], tmp);
				}
				
				if ( comments != null ) {
					tmp = findTextByXpathFromList("//*[starts-with(@id, 'accountForm:accountTabSet:0:_comments_table:')]", comments[b]);
					logger.debug("act, exp = " + tmp + "," + comments[b]);

					checkText(comments[b], tmp);
				}
			}
		}
	}

	protected void checkCustomerTypeErrors() throws Exception {
		checkById("accountForm:accountTabSet:0:numberOfAcctsMsg", data.getRowData().get("numberOfAcctsMsg"));
		checkById("accountForm:accountTabSet:0:customerTypeMsg", data.getRowData().get("customerTypeMsg"));
		checkById("accountForm:accountTabSet:0:entAccountTypeMsg", data.getRowData().get("entAccountTypeMsg"));
		checkById("accountForm:accountTabSet:0:opcoMsg", data.getRowData().get("opcoMsg"));
		logger.debug("checking opcoAccountType");
		checkById("accountForm:accountTabSet:0:opcoAccountTypeMsg", data.getRowData().get("opcoAccountTypeMsg"));
		logger.debug("checking opcoAccountSubType");
		checkById("accountForm:accountTabSet:0:opcoAccountSubTypeMsg", data.getRowData().get("opcoAccountSubTypeMsg"));		
	}

	protected void checkCustomerErrors(String custType, String key) throws Exception {
		String mainType = "";
		String contactType = "";
		
		if ("enterprise".equals(key)) {
			mainType = "epa";
			contactType = "ec";
		} else if ("primary".equals(key)) {
			mainType = "pa";
			contactType = "ps";
		} else if ("billing".equals(key)) {
			mainType = "pba";
			contactType = "pb";
		}
		
		logger.debug("checking customer, mainType = " + mainType);
		
		//checkByXpath("//*[@id='accountForm:accountTabSet:0:" + custType + "_companyNameMsg']", data.getRowData().get(mainType + "_companyNameMsg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_companyNameMsg", data.getRowData().get(mainType + "_companyNameMsg"));
		checkPersonErrors(custType, mainType);
		checkAddressErrors(custType + "_main", mainType);
		checkTelecomErrors(custType + "_main", mainType);
		
		logger.debug("checking customer, contactType = " + contactType);
		
		if ("billing".equals(key))
			checkById("accountForm:accountTabSet:0:" + custType + "_contact_companyNameMsg", data.getRowData().get(contactType + "_companyNameMsg"));
		
		//checkByXpath("//*[@id='accountForm:accountTabSet:0:" + custType + "_contact_companyNameMsg']", data.getRowData().get(contactType + "_companyNameMsg"));
		
		checkPersonErrors(custType + "_contact", contactType);
		checkAddressErrors(custType + "_contact", contactType);
		checkTelecomErrors(custType + "_contact", contactType);
	}
	
	protected void checkPersonErrors(String custType, String key) throws Exception {
		logger.debug("check person, custType = " + custType + ", key = " + key);
		checkById("accountForm:accountTabSet:0:" + custType + "_prefixMsg", data.getRowData().get(key + "_prefixMsg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_firstNameMsg", data.getRowData().get(key + "_firstNameMsg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_middleNameMsg", data.getRowData().get(key + "_middleNameMsg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_lastNameMsg", data.getRowData().get(key + "_lastNameMsg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_suffixMsg", data.getRowData().get(key + "_suffixMsg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_titleMsg", data.getRowData().get(key + "_titleMsg"));
	}
	
	protected void checkAddressErrors(String custType, String key) throws Exception {
		logger.debug("check address, custType = " + custType + ", key = " + key);
		checkById("accountForm:accountTabSet:0:" + custType + "_countryMsg", data.getRowData().get(key + "_cntryMsg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_address1Msg", data.getRowData().get(key + "_address1Msg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_address2Msg", data.getRowData().get(key + "_address2Msg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_address3Msg", data.getRowData().get(key + "_address3Msg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_unitMsg", data.getRowData().get(key + "_unit1Msg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_unitValueMsg", data.getRowData().get(key + "_unitValue1Msg"));
		
		/*if ( isNotNull(data.getRowData().get(key + "_unit2"))) {
			for (byte b=1;b<6;b++ ) {
				if ( isNull(data.getRowData().get(key + "_unit" + b)))
					break;
			
				checkById("accountForm:accountTabSet:0:" + custType + "_unit_text", data.getRowData().get(key + "_unit" + b));
				checkById("accountForm:accountTabSet:0:" + custType + "_unitValue_text", data.getRowData().get(key + "_unitValue" + b));
			}
		}*/
		logger.debug("after setting units");
		checkById("accountForm:accountTabSet:0:" + custType + "_storeIdMsg", data.getRowData().get(key + "_storeIdMsg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_cityMsg", data.getRowData().get(key + "_cityMsg"));
		logger.debug("after city");
		checkById("accountForm:accountTabSet:0:" + custType + "_stateMsg", data.getRowData().get(key + "_stateMsg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_postalCodeMsg", data.getRowData().get(key + "_postalCdMsg"));
	}
	
	protected void checkTelecomErrors(String custType, String key) throws Exception {
		logger.debug("checking teleCom, custType = " + custType + ", key = " + key);
		checkById("accountForm:accountTabSet:0:" + custType + "_telecomMsg", data.getRowData().get(key + "_telecomMsg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_phoneCntryCdMsg", data.getRowData().get(key + "_phoneCntryCdMsg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_phoneAreaCdMsg", data.getRowData().get(key + "_phoneAreaCdMsg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_phoneNumberMsg", data.getRowData().get(key + "_phoneNumberMsg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_extensionMsg", data.getRowData().get(key + "_extensionMsg"));
		checkById("accountForm:accountTabSet:0:" + custType + "_pinMsg", data.getRowData().get(key + "_pinMsg"));
	}
	
	protected void checkCreditCardErrors() throws Exception {
		logger.debug("checking credit card info");
		checkById("accountForm:accountTabSet:0:ccTypeMsg", data.getRowData().get("ccTypeMsg"));
		checkById("accountForm:accountTabSet:0:ccNumberMsg", data.getRowData().get("ccNumberMsg"));
		checkById("accountForm:accountTabSet:0:ccExpDateMsg", data.getRowData().get("ccExpDateMsg"));
		checkById("accountForm:accountTabSet:0:ccCCVNumberMsg", data.getRowData().get("ccCCVNumberMsg"));
	}

	protected void checkRegulatoryInfoErrors() throws Exception {
		logger.debug("checking regulatory info");
		checkById("accountForm:accountTabSet:0:taxId1Msg", data.getRowData().get("taxId1Msg"));
		
		checkById("accountForm:accountTabSet:0:taxId2Msg", data.getRowData().get("taxId2Msg"));
		checkById("accountForm:accountTabSet:0:taxId3Msg", data.getRowData().get("taxId3Msg"));
		checkById("accountForm:accountTabSet:0:taxId4Msg", data.getRowData().get("taxId4Msg"));
		
		checkById("accountForm:accountTabSet:0:taxId1DescMsg", data.getRowData().get("taxId1DescMsg"));
		checkById("accountForm:accountTabSet:0:taxId2DescMsg", data.getRowData().get("taxId2DescMsg"));
		checkById("accountForm:accountTabSet:0:taxId3DescMsg", data.getRowData().get("taxId3DescMsg"));
		checkById("accountForm:accountTabSet:0:taxId4DescMsg", data.getRowData().get("taxId4DescMsg"));
		
		checkById("accountForm:accountTabSet:0:customerVATMsg", data.getRowData().get("customerVATMsg"));
	}

	protected void checkOpcoReferenceErrors() {
		checkById("accountForm:accountTabSet:0:opcoReferenceNumberMsg", data.getRowData().get("linkOpcoRef1Msg"));
	}

	protected void checkCommentErrors() throws Exception {
		logger.debug("checking comments");
		checkById("accountForm:accountTabSet:0:commentTypeMsg", data.getRowData().get("commentType1Msg"));
		checkById("accountForm:accountTabSet:0:commentMsg", data.getRowData().get("comment1Msg"));
	}
	
	protected void setDriver(WebDriver driver) {
		this.driver = driver;
	}
}
