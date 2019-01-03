package com.fedex.ci.sabt.action;

import com.fedex.ci.sabt.model.RowData;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

public class HomeAction extends MainAction {
	private static final FedExLoggerInterface logger = FedExLogger.getLogger(HomeAction.class);
	private RowData data = null;
	
	public HomeAction(RowData data) {
		super("Test Case # " + data.getRowData().get("Test Case #") + " " + data.getRowData().get("Test Case Desc"));
		this.data = data;
	}
	
	public void execute() throws Exception {
		logger.debug("execute Begin");

		
		logger.info("Executing Test Case " + testCase);
		
		String user = data.getRowData().get("login");
		String pwd = data.getRowData().get("password");
		
		login(user, pwd);
		
		logger.debug(driver.getPageSource());
		
		String expMsg = data.getRowData().get("expMsg");
		
		//String actMsg = driver.findElement(By.cssSelector(data.getRowData().get("msgAnchor"))).getText();
		String actMsg = findTextByCssFromList(data.getRowData().get("msgAnchor"), expMsg);
		
		logger.debug("homeText = " + actMsg);
		
		checkText(expMsg, actMsg);
		
		logger.debug("execute End");
	}
}
