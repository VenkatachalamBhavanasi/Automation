package com.fedex.ci.sabt.action;

import com.fedex.ci.sabt.model.RowData;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

public class AccountInfoAction extends MainAction {
	private static final FedExLoggerInterface logger = FedExLogger.getLogger(AccountInfoAction.class);
	private RowData data = null;
	
	public AccountInfoAction(RowData data) {
		super("Test Case # " + data.getRowData().get("Test Case #") + " " + data.getRowData().get("Test Case Desc"));
		this.data = data;
	}
	
	public void execute() throws Exception {
		logger.debug("execute Begin");

		logger.info("Executing Test Case " + testCase);

		login(data.getRowData().get("login"), data.getRowData().get("password"));

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

		String expAcct = data.getRowData().get("expAcct");

		if ( isNotNull(expAcct) ) {
			String actAcct = findTextByXpathFromList("//*[starts-with(@id, 'caTables:accountTable:')]", expAcct);
			logger.debug("actAcct = " + actAcct);

			checkText(expAcct, actAcct);
		}

		String expDuns = data.getRowData().get("expDuns");

		if ( isNotNull(expDuns) ) {
			String actDuns = findTextByXpathFromList("//*[starts-with(@id, 'caTables:Tbl:')]", expDuns);
			logger.debug("dunsText = " + actDuns);

			checkText(expDuns, actDuns);
		}

		String expMsg = data.getRowData().get("expMsg");

		if ( isNotNull(expMsg) ) {
			String actMsg = findTextById("searchForm:searchTabSet:0:accountInfoMsg");

			logger.debug("actMsg = " + actMsg);

			checkText(expMsg, actMsg);
		}
		
		checkLinks(data.getRowData().get("dbLinks"), "", "//*[starts-with(@id, 'caTables:Tbl:0:')]");
		
		checkLinks(data.getRowData().get("acctLinks"), "", "//*[starts-with(@id, 'caTables:accountTable:0:')]");
		
		logger.debug("execute End");
	}
}
