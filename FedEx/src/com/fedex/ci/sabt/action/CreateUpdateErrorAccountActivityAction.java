package com.fedex.ci.sabt.action;

import com.fedex.ci.sabt.model.RowData;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

public class CreateUpdateErrorAccountActivityAction extends CommonAccountActivity {
	
	private static final FedExLoggerInterface logger = FedExLogger.getLogger(CreateUpdateErrorAccountActivityAction.class);
	
	public CreateUpdateErrorAccountActivityAction(RowData data) {
		super(data);
	}
	
	public void execute() throws Exception {
		logger.debug("execute Begin");

		logger.info("Executing Test Case " + testCase);
		
		//String action = data.getRowData().get("action");
		//String acct = readAccountNumber(data.getRowData().get("parentAcctOpco"));
		
		userAcceptanceDialog();
		
							

		if ( "Y".equals(data.getRowData().get("customerTypeTab")) ) {
			setCustomerType();
			logger.debug("clicking the next");
			clickById("accountForm:accountTabSet:0:accountTypeTabNextLink");
			logger.debug("after clicking the next");
			checkCustomerTypeErrors();
		}
		
		if ( "Y".equals(data.getRowData().get("enterpriseTab")) ) {
			logger.debug("going to ent tab");
		
			setCustomer("entCustForm", "enterprise");

			clickById("accountForm:accountTabSet:0:entTabNextLink");

			checkCustomerErrors("entCustForm", "enterprise");
		}
		
		if ( "Y".equals(data.getRowData().get("primaryTab")) ) {
			logger.debug("going to primary tab");
		
			if ("Y".equals(data.getRowData().get("copyEntToPrimary")))
				clickById("accountForm:accountTabSet:0:copyEntToPrimaryLink");
			
			setCustomer("paCustForm", "primary");
			
			clickById("accountForm:accountTabSet:0:primaryTabNextLink");
		
			checkCustomerErrors("paCustForm", "primary");
		}
		
		if ( "Y".equals(data.getRowData().get("billingTab")) ) {
			logger.debug("going to billing tab");
		
			if ("Y".equals(data.getRowData().get("copyEntToBilling")))
				clickById("accountForm:accountTabSet:0:copyEntToBillingLink");
			
			setCustomer("pbaCustForm", "billing");

			setCreditCard();

			clickById("accountForm:accountTabSet:0:billingTabNextLink");
			
			checkCustomerErrors("pbaCustForm", "billing");
		
			checkCreditCardErrors();
		}
		
		if ( "Y".equals(data.getRowData().get("regTab")) ) {
			logger.debug("going to reg info tab");

			setRegulatoryInfo();
			
			clickById("accountForm:accountTabSet:0:regulatoryTabNextLink");
		
			checkRegulatoryInfoErrors();
		}
		
		if ( "Y".equals(data.getRowData().get("opcoRefTab")) ) {
			logger.debug("going to opco reference tab");

			setOpcoReferences();
			
			clickById("accountForm:accountTabSet:0:OpcoRefTabNextLink");
			
			checkOpcoReferenceErrors();
			
			//clickById("accountForm:accountTabSet:0:OpcoRefTabNextLink");
		}
		
		
		//id="accountForm:accountTabSet:0:balaneForwardCode"
		
		if ( "Y".equals(data.getRowData().get("commentTab")) ) {
			logger.debug("going to comments tab");
		
			setComments();
		
			clickById("accountForm:accountTabSet:0:createEnterpriseAndOpco");
			
			checkCommentErrors();
		}
		
		logger.debug("execute End");
	}
}
