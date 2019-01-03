package com.fedex.ci.sabt.action;


import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fedex.ci.sabt.model.RowData;
import com.fedex.ci.sabt.scheduler.FileCleanUpProcessorThread;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

public class FileCleanUpAction extends MainAction {
	private static final FedExLoggerInterface logger = FedExLogger.getLogger(FileCleanUpAction.class);
	private RowData data = null;
	
	public FileCleanUpAction(RowData data) {
		super("Test Case # " + data.getRowData().get("Test Case #") + " " + data.getRowData().get("Test Case Desc"));
		this.data = data;
	}
	
	public void execute() throws Exception {
		logger.debug("execute Begin");

		logger.info("Executing Test Case " + testCase);
		
		login(data.getRowData().get("login"), data.getRowData().get("password"));
		
		runBatchJob();
		
		logger.debug("execute End");
	}
	
	private void runBatchJob() throws Exception {
		logger.info("running the batch job");
		FileCleanUpProcessorThread fileProcessor = new FileCleanUpProcessorThread();
		
		logger.info("registering the appContext");
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-dev-batch.xml");
		context.registerShutdownHook();
		
		logger.info("processing bulk accounts");
		fileProcessor.deleteOldUserRequests();
		fileProcessor.cleanUpOldFiles();
	}
}