package com.fedex.ci.sabt.action;

import com.fedex.ci.sabt.model.RowData;
import com.fedex.ci.sabt.util.TestConstants;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

public class SearchAccountActivityAction extends CommonAccountActivity {
	private static final FedExLoggerInterface logger = FedExLogger.getLogger(SearchAccountActivityAction.class);

	public SearchAccountActivityAction(RowData data) {
		super(data);
	}
	
	public void enterpriseAccountSearch(String acct) throws Exception {
		logger.debug("enterpriseAccountSearch Begin");

		//printPageSource();
		
		logger.info("enterpriseAccountSearch with acct " + acct);
		
		if ( isNotNull(acct) ) {
			logger.info("searching with acct from file " + acct);
			
			clickById("menu:accountSearchLink");
			clickByXpath("//a[@id='searchForm:searchTabSet:0.1']/div/table/tbody/tr/td");
			
			selectByVisibleText("searchForm:searchTabSet:0:searchCriteria", "Enterprise Acct #");
			setText("searchForm:searchTabSet:0:searchNumber", acct);
			clickById("searchForm:searchTabSet:0:cmdSearchNumber");
			//Thread.sleep(TestConstants.SLEEP_SHORT_PAUSE);
		} else {
			if ( isNotNull(data.getRowData().get("accountType")) ) {
				logger.debug("clicking on the menu:accountSearchLink");
				clickById("menu:accountSearchLink");
				//driver.findElement(By.id("menu:accountSearchLink")).click();
				logger.debug("after clicking on the menu:accountSearchLink");
				clickByXpath("//a[@id='searchForm:searchTabSet:0.1']/div/table/tbody/tr/td");
			
				selectByVisibleText("searchForm:searchTabSet:0:searchCriteria", data.getRowData().get("accountType"));
				setText("searchForm:searchTabSet:0:searchNumber", data.getRowData().get("searchNumber"));
				logger.info("searching with acct from excel " + data.getRowData().get("searchNumber"));
				
				clickById("searchForm:searchTabSet:0:cmdSearchNumber");
				
				//Thread.sleep(TestConstants.SLEEP_SHORT_PAUSE);
			} else {
				logger.info("advanced search");
				
				clickById("menu:accountSearchLink");
				setText("searchForm:searchTabSet:0:accName", data.getRowData().get("accName"));
				setText("searchForm:searchTabSet:0:addLn1", data.getRowData().get("addLn1"));
				setText("searchForm:searchTabSet:0:addLn2", data.getRowData().get("addLn2"));
				setText("searchForm:searchTabSet:0:city", data.getRowData().get("city"));
				logger.debug("setting the state");
				setText("searchForm:searchTabSet:0:state", data.getRowData().get("state"));

				String cntry = data.getRowData().get("cntry");
			
				logger.debug("setting the cntry");
				if ( cntry != null && cntry.length() > 1 )
					selectByVisibleText("searchForm:searchTabSet:0:cntry", cntry);

				logger.debug("setting the postal");
				setText("searchForm:searchTabSet:0:postalCode", data.getRowData().get("postalCode"));
				setText("searchForm:searchTabSet:0:phNm", data.getRowData().get("phNm"));

				clickById("searchForm:searchTabSet:0:cmdAdvancedSearch");
				//Thread.sleep(TestConstants.SLEEP_LONG_PAUSE);
			}
		}
		
		//Thread.sleep(TestConstants.SLEEP_LONG_PAUSE);
		longPause();
		
		logger.debug("enterpriseAccountSearch End");
	}
	
	public void execute() throws Exception {
		logger.debug("execute Begin");
		
		String testCase = data.getRowData().get("Test Case Desc");
		logger.info("Executing Test Case " + testCase);

		String prevResultsFlag = data.getRowData().get("previousSearchResults");
		
		if ( isNotNull(prevResultsFlag) ) {
			logger.debug("going back to previous results");
			
			clickById("accountForm:searchResultsLink");
			
			String expMsg = "FedEx Enterprise / OpCo Accounts";
			
			String actMsg = findTextByCssFromList("div", expMsg);
			
			checkText(expMsg, actMsg);
			
			return;
		}
		
		checkCustomerType();
		
		logger.debug("going to ent tab");
		
		clickById("accountForm:accountTabSet:0:accountTypeTabNextLink");
		
		checkCustomer("entCustForm", "enterprise");

		logger.debug("going to primary tab");
		
		clickById("accountForm:accountTabSet:0:entTabNextLink");
		
		checkCustomer("paCustForm", "primary");
		
		logger.debug("going to billing tab");
		
		clickById("accountForm:accountTabSet:0:primaryTabNextLink");
		
		checkCustomer("pbaCustForm", "billing");
		
		checkCreditCard();
		
		logger.debug("going to reg info tab");
		
		clickById("accountForm:accountTabSet:0:billingTabNextLink");

		checkRegulatoryInfo();

		logger.debug("going to ref tab");
		
		clickById("accountForm:accountTabSet:0:regulatoryTabNextLink");

		checkOpcoRefInfo();
		
		logger.debug("going to comments tab");
		
		clickById("accountForm:accountTabSet:0:OpcoRefTabNextLink");
		
		if("Y".equals(data.getRowData().get("BalanceForwardindicator")))
		{
			checkByXpath("//*[@id='accountForm:accountTabSet:0:balaneForwardCode']/option[1]", data.getRowData().get("StatusforBalanceforward"));
		}
		
		if("Y".equals(data.getRowData().get("Fifoindicator")))
			
		{
			
	       checkByXpath("//*[@id='accountForm:accountTabSet:0:fifoIndicator']/option[1]",data.getRowData().get("StatusforFifoindicator") );
		}
		
       if("Y".equals(data.getRowData().get("AR Pref code")))
			
		{
    	   checkByXpath("//*[@id='accountForm:accountTabSet:0:arPrefCode']/option[1]",data.getRowData().get("StatusforFifoindicator") );
		}
		
		
		if ("Y".equals(data.getRowData().get("Confirmforbffifonextlink")))
		{
			clickById("accountForm:accountTabSet:0:bfFiFoNextLink");
		}
		
		
		checkComments();

		logger.debug("execute End");
	}
}
