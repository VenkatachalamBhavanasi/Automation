package com.fedex.ci.sabt.action;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fedex.ci.sabt.model.RowData;
import com.fedex.ci.sabt.scheduler.BulkAccountProcessorThread;
import com.fedex.ci.sabt.services.EmailSenderService;
import com.fedex.ci.sabt.services.MediatorService;
import com.fedex.common.icefaces.util.AppBeanUtils;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

public class NewBulkLoadAction extends MainAction {
	private static final FedExLoggerInterface logger = FedExLogger.getLogger(NewBulkLoadAction.class);
	private RowData data = null;
	
	public NewBulkLoadAction(RowData data) {
		super("Test Case # " + data.getRowData().get("Test Case #") + " " + data.getRowData().get("Test Case Desc"));
		this.data = data;
	}
	
	public void execute() throws Exception {
		logger.debug("execute Begin");

		logger.info("Executing Test Case " + testCase);
		
		login(data.getRowData().get("login"), data.getRowData().get("password"));

		clickById("menu:newBulkRequestLink");

		setTextByXpathWithoutClear("//input[@type='file']", data.getRowData().get("fileName"));
	    
		logger.debug("clicking saveReq");
		
		clickById("newRequestForm:submit");
		
		logger.debug("clicked saveReq");
		
	    longPause();
	    
		String expMsg = data.getRowData().get("expMsg");
		
		if ( isNotNull(expMsg) ) {
			String actMsg = findTextByCssFromList("div", expMsg);
			logger.debug("newBulkText = " + actMsg);
		
			checkText(expMsg, actMsg);
			
			if ( "Y".equals(data.getRowData().get("runBatchJob")) ) {
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.MINUTES);
				runBatchJob();
			}
			return;
		}

		expMsg = data.getRowData().get("errMsg");
		
		if ( isNotNull(expMsg) ) {
			checkById("sabtErrorForm:errorDetails", expMsg);
			return;
		}
		
		logger.debug("going to validation");
		
		// validation results
		Set<String> keys = data.getRowData().keySet();
		for (String key : keys) {
			//logger.debug("key = " + key);
			if ( key.endsWith("Msg") && !"expMsg".equals(key) && !"resubmitMsg".equals(key) && !"runBatchJob".equals(key)) {
				expMsg = data.getRowData().get(key);
				//logger.debug(key + " = " + expMsg);
				
				if ( isNotNull(expMsg) ) {
					//String actMsg = findTextByXpathFromList("//ul[@id='sTables:bulkRequestMessages']", expMsg);
					if ( expMsg.contains("~") ) {
						for ( String expM : expMsg.split("~") ) {
							String actMsg = findTextByCssFromList("span", expM);
							logger.debug(key + " = " + actMsg);
							
							checkText(expM, actMsg);
						}
					} else {
						String actMsg = findTextByCssFromList("span", expMsg);
						logger.debug(key + " = " + actMsg);
					
						checkText(expMsg, actMsg);
					}
				}
				
			}
		}
		
		logger.debug("going to resubmit");
		
		// sTables:requestDetailsTable:0:
		// update the values and resubmit
		keys = data.getRowData().keySet();
		String newData = "";
		boolean resubmit = false;
		
		for (String key : keys) {
			//logger.debug("resubmit key = " + key);
			if ( !key.endsWith("Msg") &&
					!"runBatchJob".equals(key) &&
					!"Test Case #.Test Case Desc.Run.login.password.fileName".contains(key) &&
					!"expResult".equals(key) ) {
				newData = data.getRowData().get(key);
				
				if ( isNotNull(newData) ) {
					setText("sTables:requestDetailsTable:0:" + key, newData);
					logger.debug(key + " = " + newData);
					resubmit = true;
				}
			}
		}
		
		/*if ( resubmit ) {
			clickById("sTables:saveRequestAgain");

			//Thread.sleep(TestConstants.SLEEP_MEDIUM_PAUSE);
			longPause();

			String resubmitMsg = data.getRowData().get("resubmitMsg");
		
			if ( isNotNull(resubmitMsg) ) {
				String actMsg = findTextByCssFromList("div", resubmitMsg);
				logger.debug("newBulkText = " + actMsg);
		
				checkText(resubmitMsg, actMsg);
			}

			if ( "Y".equals(data.getRowData().get("runBatchJob")) ) {
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.MINUTES);
				runBatchJob();
			}
		}*/
		logger.debug("execute End");
	}
	
	private void runBatchJob() throws Exception {
		logger.info("running the batch job");
		BulkAccountProcessorThread batchProcessor = new BulkAccountProcessorThread();
		
		logger.info("registering the appContext");
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-dev-batch.xml");
		context.registerShutdownHook();
		
		//logger.debug("getting mediatorService");
		//MediatorService mediatorService = (MediatorService) AppBeanUtils.getManagedBean("mediatorService");
		
		//logger.debug("getting emailService");
		//EmailSenderService emailSenderService = (EmailSenderService) AppBeanUtils.getManagedBean("emailSenderService");
		
		//batchProcessor.setEmailSenderService(emailSenderService);
		//batchProcessor.setMediatorService(mediatorService);
		
		logger.info("processing bulk accounts");
		batchProcessor.processBulkAccountRequests();
	}
}