package com.fedex.ci.sabt.action;

import org.openqa.selenium.WebElement;

import com.fedex.ci.sabt.model.RowData;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

public class AccountActivitiesAction extends MainAction {
	private static final FedExLoggerInterface logger = FedExLogger.getLogger(AccountActivitiesAction.class);
	private RowData data = null;
	
	public AccountActivitiesAction(RowData data) {
		super("Test Case # " + data.getRowData().get("Test Case #") + " " + data.getRowData().get("Test Case Desc"));
		this.data = data;
	}
	
	public void execute() throws Exception {
		logger.debug("execute Begin");

		logger.info("Executing Test Case " + testCase);
		
		login(data.getRowData().get("login"), data.getRowData().get("password"));

		String acct = "";
		
		if ( "Enterprise Acct #".equals(data.getRowData().get("accountType")) )
			acct = readAccountNumber(data.getRowData().get("parentAcctOpco"));
				
		logger.debug("searching for acct " + acct);
		
		CommonAccountActivity action = null;
		
		SearchAccountActivityAction searchAction = new SearchAccountActivityAction(data);
		searchAction.setDriver(driver);
		searchAction.enterpriseAccountSearch(acct);
		
		System.out.println();
		WebElement ele = null;
		mediumPause();
		
		if ( "createAcctFromScratch".equals(data.getRowData().get("action")) ) {
	
			clickById("createAccountSearchForm:createNewEntAccount2");
			mediumPause();
			

			//clickByXpath("input[@id='createAccountSearchForm:createNewEntAccount2']");
			
			
			logger.debug("should get user accept dialog");
			
			action = new CreateUpdateAccountActivityAction(data);
			
		} else if ( "addOpco".equals(data.getRowData().get("action")) ) {
			ele = findLinkByName("Add Opco", data.getRowData().get("parentAcctOpco"), "//*[starts-with(@id, 'caTables:accountTable:0:')]");

			if ( ele == null && "Y".equals(data.getRowData().get("ignoreNoAcctError")) ) {
				return;
			}

			checkNotNull(ele, "Add Opco");
		    logger.debug("add opco text " + ele.getText() + "," + ele.getTagName() + "," + ele.getAttribute("id"));
	    	//ele.click();
		    clickById(ele.getAttribute("id"));
	    	action = new CreateUpdateAccountActivityAction(data);
		} else if ( "updateOpco".equals(data.getRowData().get("action")) ) {
			ele = findLinkByName("Update", data.getRowData().get("parentAcctOpco"), "//*[starts-with(@id, 'caTables:accountTable:0:')]");
			//ele = findLinkByName("Update", data.getRowData().get("parentAcctOpco"), "//*[starts-with(@id, 'caTables:accountTable:0:acctSnbrsTbl:0:j_idt210')]");
			
			//caTables:accountTable:0:acctSnbrsTbl:0:j_idt210
			if ( ele == null && "Y".equals(data.getRowData().get("ignoreNoAcctError")) ) {
				return;
			}
		    
			checkNotNull(ele, "updateOpco");
		    logger.debug("update opco text " + ele.getText() + "," + ele.getTagName() + "," + ele.getAttribute("id"));
	    	//ele.click();
		    clickById(ele.getAttribute("id"));
	    	action = new CreateUpdateAccountActivityAction(data);
		} else if ( "duplicateAcct".equals(data.getRowData().get("action")) ) {
			ele = findLinkByName("Duplicate", data.getRowData().get("parentAcctOpco"), "//*[starts-with(@id, 'caTables:accountTable:0:')]");
			//ele = findLinkByName("Duplicate", data.getRowData().get("parentAcctOpco"), "//*[starts-with(@id, 'caTables:accountTable:0:acctSnbrsTbl:0:j_idt214')]");
			
			if ( ele == null && "Y".equals(data.getRowData().get("ignoreNoAcctError")) ) {
				return;
			}
		    
			checkNotNull(ele, "duplicateAcct");
		    logger.debug("duplicate text " + ele.getText() + "," + ele.getTagName() + "," + ele.getAttribute("id"));
	    	//ele.click();
		    clickById(ele.getAttribute("id"));
	    	action = new CreateUpdateAccountActivityAction(data);
		} else if ( "searchResults".equals(data.getRowData().get("action")) ) {
			ele = findLinkByName(data.getRowData().get("parentAcctOpco"), "//*[starts-with(@id, 'caTables:accountTable:0:')]");	
			if ( ele == null && "Y".equals(data.getRowData().get("ignoreNoAcctError")) ) {
				return;
			}
		    
			checkNotNull(ele, "searchResults");
		    logger.debug("searchResults text " + ele.getText() + "," + ele.getTagName() + "," + ele.getAttribute("id"));
	    	//ele.click();
		    clickById(ele.getAttribute("id"));
	    	action = new SearchAccountActivityAction(data);
		} else if ( "createAcctFromScratchError".equals(data.getRowData().get("action")) ) {
			clickById("createAccountSearchForm:createNewEntAccount2");
			logger.debug("should get user accept dialog");
			action = new CreateUpdateErrorAccountActivityAction(data);
		} else if ( "createAcctFromCMDM".equals(data.getRowData().get("action")) ) {
		    ele = findLinkByName("Create Account", "//*[starts-with(@id, 'caTables:Tbl:0:')]");
		    checkNotNull(ele, "Create Account");
		    logger.debug("Create Account text " + ele.getText() + "," + ele.getTagName() + "," + ele.getAttribute("id"));
	    	//ele.click();
		    clickById(ele.getAttribute("id"));
	    	action = new CreateUpdateAccountActivityAction(data);
		}
		
		action.setDriver(driver);
		action.execute();
		
		logger.debug("execute End");
	}
}