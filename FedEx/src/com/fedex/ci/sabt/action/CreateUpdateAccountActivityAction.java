package com.fedex.ci.sabt.action;

import com.fedex.ci.sabt.model.RowData;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

public class CreateUpdateAccountActivityAction extends CommonAccountActivity {
	private static final FedExLoggerInterface logger = FedExLogger.getLogger(CreateUpdateAccountActivityAction.class);
	
	public CreateUpdateAccountActivityAction(RowData data) {
		super(data);
	}
	
	public void execute() throws Exception {
		logger.debug("execute Begin");

		logger.info("Executing Test Case " + testCase);
		
		String action = data.getRowData().get("action");
		String acct = readAccountNumber(data.getRowData().get("parentAcctOpco"));
		
		if ( "createAcctFromScratch".equals(action) || "createAcctFromCMDM".equals(action) )
			if ( !userAcceptanceDialog() )
				return;
		
		setCustomerType();
		
		logger.debug("going to ent tab");
		
		clickById("accountForm:accountTabSet:0:accountTypeTabNextLink");
		
		setCustomer("entCustForm", "enterprise");

		logger.debug("going to primary tab");
		
		clickById("accountForm:accountTabSet:0:entTabNextLink");
		
		if ("Y".equals(data.getRowData().get("copyEntToPrimary")))
			clickById("accountForm:accountTabSet:0:copyEntToPrimaryLink");
		
		setCustomer("paCustForm", "primary");
		
		logger.debug("going to billing tab");
		
		clickById("accountForm:accountTabSet:0:primaryTabNextLink");
		
		if ("Y".equals(data.getRowData().get("copyEntToBilling")))
			clickById("accountForm:accountTabSet:0:copyEntToBillingLink");
		
		setCustomer("pbaCustForm", "billing");
		
		setCreditCard();
		
		logger.debug("going to reg info tab");
		
		clickById("accountForm:accountTabSet:0:billingTabNextLink");

		setRegulatoryInfo();
		
		logger.debug("going to Opco Ref tab");
		
		clickById("accountForm:accountTabSet:0:regulatoryTabNextLink");
		
		setOpcoReferences();
		mediumPause();
		
		logger.debug("going to comments tab");
		
		clickById("accountForm:accountTabSet:0:OpcoRefTabNextLink");
		shortPause();
		
		
		
		if("Y".equals(data.getRowData().get("BalanceForwardindicator")))
		{
			selectByVisibleText("accountForm:accountTabSet:0:balaneForwardCode", data.getRowData().get("StatusforBalanceforward"));
		}
		
		if("Y".equals(data.getRowData().get("Fifoindicator")))
			
				{
			selectByVisibleText("accountForm:accountTabSet:0:fifoIndicator",data.getRowData().get("StatusforFifoindicator") );
				}
						
		if ("Y".equals(data.getRowData().get("Confirmforbffifonextlink")))
		{
			clickById("accountForm:accountTabSet:0:bfFiFoNextLink");
		}
				
			setComments();	
			
		
		//clickById("accountForm:accountTabSet:0:bfFiFoNextLink");
		
		
		
		
		String prevResultsFlag = data.getRowData().get("previousSearchResults");
		
		if ( isNotNull(prevResultsFlag) ) {
			logger.debug("going back to previous results");
			
			clickById("accountForm:searchResultsLink");
			
			String expMsg = "FedEx Enterprise / OpCo Accounts";
			
			String actMsg = findTextByCssFromList("div", expMsg);
			
			checkText(expMsg, actMsg);
			
			return;
		}
		if ( "createAcctFromScratch".equals(action) || "createAcctFromCMDM".equals(action) ) {
			clickById("accountForm:accountTabSet:0:createEnterpriseAndOpco");
		} else if ( "addOpco".equals(action) ) {
			clickById("accountForm:accountTabSet:0:addNewOpco");
		} else if ( "updateOpco".equals(action) ) {
			clickById("accountForm:accountTabSet:0:updateOpco");
			
			
		} else if ( "duplicateAcct".equals(action) ) {
			clickById("accountForm:accountTabSet:0:duplicateEnterprise");
		}
		logger.debug("acct " + action);
		
		String expFinalMsg = data.getRowData().get("expMsg");

		String errButton = data.getRowData().get("errorDetailsButton");
		
		//Thread.sleep(TestConstants.SLEEP_LONG_PAUSE);
		
		if ( isNotNull(errButton) ) {
			clickById(errButton);
			
			String expMsg = data.getRowData().get("previousResultsMsg");
			String actMsg = findTextByCssFromList("div", expMsg);
			
			checkText(expMsg, actMsg);
		}
		
		if ( isNotNull(expFinalMsg) ) { 
		
			longPause();
			longPause();
			String actHeader = findTextByXpathFromList("//*[starts-with(@id, 'accountResultsForm:account_results_table:')]", expFinalMsg);

			checkText(expFinalMsg, actHeader);
			longPause();
			longPause();
			String actAcct = findAccountTextByXpathFromList("//*[starts-with(@id, 'accountResultsForm:account_results_table:')]");
			longPause();
			logger.debug("newAcct = " + actAcct);

			if ( "createAcctFromScratch".equals(action) || "createAcctFromCMDM".equals(action) )
				writeAccountNumber(data.getRowData().get("opco"), actAcct);
			else if ( "addOpco".equals(action) ) {
				writeAccountNumber(data.getRowData().get("opco"), actAcct);
				checkText(acct, actAcct);
			} else if ( "updateOpco".equals(action) ) {
				if ( !"Y".equals(data.getRowData().get("ignoreNoAcctError")) )
					checkText(acct, actAcct);
			} else if ( "duplicateAcct".equals(action) ) {
				writeAccountNumber(data.getRowData().get("parentAcctOpco"), actAcct);
			}
		}
		
		longPause();
		
		logger.debug("execute End");
	}

	
}
