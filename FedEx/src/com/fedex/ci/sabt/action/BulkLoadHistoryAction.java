package com.fedex.ci.sabt.action;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebElement;

import com.fedex.ci.sabt.model.RowData;
import com.fedex.ci.sabt.util.TestConstants;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

public class BulkLoadHistoryAction extends MainAction {
	private static final FedExLoggerInterface logger = FedExLogger.getLogger(BulkLoadHistoryAction.class);
	private RowData data = null;
	
	public BulkLoadHistoryAction(RowData data) {
		super("Test Case # " + data.getRowData().get("Test Case #") + " " + data.getRowData().get("Test Case Desc"));
		this.data = data;
	}
	
	public void execute() throws Exception {
		logger.debug("execute Begin");

		logger.info("Executing Test Case " + testCase);

		login(data.getRowData().get("login"), data.getRowData().get("password"));

		clickById("menu:bulkRequestHistoryLink");
		
		//Thread.sleep(TestConstants.SLEEP_LONG_PAUSE);
		mediumPause();
		
		String expMsg = data.getRowData().get("expMsg");

		logger.debug("expMsg = " + expMsg);
		
		if ( isNotNull(expMsg) ) {
			String actMsg = findTextByCssFromList("div", expMsg);
			logger.debug("actMsg = " + actMsg);

			assertEquals(expMsg, actMsg);
		}
		
		logger.debug("clicking the headers ... ");
		// click on the headers for sorting
		clickById("sTables:requestListTable:descHeader");
		clickById("sTables:requestListTable:enteredDtHeader");
		clickById("sTables:requestListTable:enterdByEmpNbrHeader");
		clickById("sTables:requestListTable:statusDescHeader");
		logger.debug("clicked all headers ... ");
		
		logger.debug("clicking the complete link ... ");
		// click on the complete
		WebElement ele = findLinkByName("Complete", "//*[starts-with(@id, 'sTables:requestListTable:')]");
		checkNotNull(ele, "Complete");
		ele.click();
		
		//Thread.sleep(TestConstants.SLEEP_LONG_PAUSE);
		mediumPause();
		
		logger.debug("clicking the download ... ");
		//click the download link
		clickById("newRequestForm:downloadResultsFileId");
		
		logger.debug("execute End");
	}
}
