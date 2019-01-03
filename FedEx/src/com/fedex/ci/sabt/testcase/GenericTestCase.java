package com.fedex.ci.sabt.testcase;

import java.lang.reflect.Constructor;

import com.fedex.ci.sabt.action.MainAction;
import com.fedex.ci.sabt.model.RowData;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

public class GenericTestCase extends PageTestCase {
	private static final FedExLoggerInterface logger = FedExLogger.getLogger(GenericTestCase.class);
	
	public GenericTestCase(RowData data, String className) throws Exception {
		super("Test Case # " + data.getRowData().get("Test Case #") + " " + data.getRowData().get("Test Case Desc"));

		logger.info("adding test case " + data.getRowData().get("Test Case Desc"));
			
		Class testCaseClass = Class.forName(className);
			
		Class[] types = {RowData.class};
		Constructor constructor = testCaseClass.getConstructor(types);

		Object[] parameters = {data};
			
		action = (MainAction) constructor.newInstance(parameters);
	}
}

