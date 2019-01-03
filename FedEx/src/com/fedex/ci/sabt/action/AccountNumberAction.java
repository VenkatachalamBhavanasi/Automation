package com.fedex.ci.sabt.action;

import org.openqa.selenium.By;

import com.fedex.ci.sabt.model.RowData;
import com.fedex.ci.sabt.util.TestConstants;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

public class AccountNumberAction extends MainAction {
	private static final FedExLoggerInterface logger = FedExLogger.getLogger(AccountNumberAction.class);
	private RowData data = null;
	
	public AccountNumberAction(RowData data) {
		super("Test Case # " + data.getRowData().get("Test Case #") + " " + data.getRowData().get("Test Case Desc"));
		this.data = data;
	}
	
	public void execute() throws Exception {
		logger.debug("execute Begin");

		logger.info("Executing Test Case " + testCase);
		
		login(data.getRowData().get("login"), data.getRowData().get("password"));
		
		clickById("menu:accountSearchLink");
		clickByXpath("//a[@id='searchForm:searchTabSet:0.1']/div/table/tbody/tr/td");
		
		selectByVisibleText("searchForm:searchTabSet:0:searchCriteria", data.getRowData().get("accountType"));
		setText("searchForm:searchTabSet:0:searchNumber", data.getRowData().get("searchNumber"));
		clickById("searchForm:searchTabSet:0:cmdSearchNumber");

		//Thread.sleep(TestConstants.SLEEP_LONG_PAUSE);
		mediumPause();
		
		String expAcct = data.getRowData().get("expAcct");
		
		if ( isNotNull(expAcct) ) {
			String actAcct = findTextByXpathFromList("//*[starts-with(@id, 'caTables:accountTable:')]", expAcct);
			logger.debug("actAcct = " + actAcct);

			checkText(expAcct,actAcct);
		}
		
		String expMsg = data.getRowData().get("expMsg");
		String actMsg = "";
		
		if ( isNotNull(expMsg) ) {
			actMsg = driver.findElement(By.id("searchForm:searchTabSet:0:searchNumberMsg")).getText();
			logger.debug("errMsg = " + actMsg);
		
			checkText(expMsg, actMsg);
		}

		checkLinks(data.getRowData().get("links"), data.getRowData().get("parentOpco"), "//*[starts-with(@id, 'caTables:accountTable:0:')]");
		
		expMsg = data.getRowData().get("statusMsg");
		
		if ( isNotNull(expMsg) ) {
			actMsg = findTextByXpathFromList("//*[contains(@style,'white-space: nowrap;color:red;')]", expMsg);
			logger.debug("expMsg, actMsg = " + expMsg + "," + actMsg);
		
			checkText(expMsg, actMsg);
		}
		
		
		
		logger.debug("execute End");
	}
}
