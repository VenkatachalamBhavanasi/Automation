package com.fedex.ci.sabt.testcase;

import org.junit.After;
import org.junit.Before;

import com.fedex.ci.sabt.action.MainAction;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

import junit.framework.TestCase;

public abstract class PageTestCase extends TestCase {
	private static final FedExLoggerInterface logger = FedExLogger.getLogger(PageTestCase.class);
	MainAction action = null;
	
	public PageTestCase(String testCase) {
		super(testCase);
	}

	@Before
	public void setUp() throws Exception {
		action.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		action.tearDown();
	}
	
	protected void runTest() throws Throwable {
		logger.debug("runTest Begin");
		action.execute();
		logger.debug("runTest End");
    }

}
