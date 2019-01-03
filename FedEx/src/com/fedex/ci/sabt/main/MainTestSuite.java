package com.fedex.ci.sabt.main;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.fedex.ci.sabt.testcase.GenericTestCase;
import com.fedex.ci.sabt.excel.ExcelProcessor;
import com.fedex.ci.sabt.model.RowData;
import com.fedex.ci.sabt.util.TestConstants;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

public class MainTestSuite extends TestSuite {
	private static final FedExLoggerInterface logger = FedExLogger.getLogger(MainTestSuite.class);

	public static Test suite() throws Exception {
		logger.debug("suite Begin");

		TestSuite suite = new TestSuite("MainTestSuite");
		
		List<RowData> acctData = ExcelProcessor.read(TestConstants.SHEET_PAGES);
		
		for (RowData data : acctData) {
			if ( !"Y".equals(data.getRowData().get("Run")) ) {
				logger.debug("skipping the test suite " + data.getRowData().get("Test Page"));
				continue;
			}
			logger.info("adding suite " + data.getRowData().get("Test Page"));
			
			suite.addTest(pageTestSuite(data.getRowData().get("Test Page"), data.getRowData().get("Class Name")));
		}
		
		logger.debug("suite End");
		
    	return suite;
    }
	
	private static Test pageTestSuite(String suiteName, String className) throws Exception {
		logger.debug("pageTestSuite Begin");

		logger.debug("adding test class " + className);
		
		TestSuite suite = new TestSuite(suiteName);
		
		List<RowData> acctData = ExcelProcessor.read(suiteName);
		
		for ( RowData data : acctData ) {
			if ( data.getRowData().get("Test Case Desc") == null || data.getRowData().get("Test Case Desc").length() <=0 )
				break;

			if ( !"Y".equals(data.getRowData().get("Run")) ) {
				logger.debug("skipping the test " + data.getRowData().get("Test Case Desc"));
				continue;
			}
			
			logger.info("adding test case " + data.getRowData().get("Test Case Desc"));

			GenericTestCase testCase = new GenericTestCase(data, className);
			
			suite.addTest(testCase);
		}
		
		/*for ( RowData data : acctData ) {
			if ( data.getRowData().get("Test Case Desc") == null || data.getRowData().get("Test Case Desc").length() <=0 )
				break;

			if ( !"Y".equals(data.getRowData().get("Run")) ) {
				//logger.debug("skipping the test " + data.getRowData().get("Test Case Desc"));
				continue;
			}
			
			logger.info("adding test case " + data.getRowData().get("Test Case Desc"));
			
			Class testCaseClass = Class.forName(className);
			
			Class[] types = {RowData.class};
			Constructor constructor = testCaseClass.getConstructor(types);

			Object[] parameters = {data};
			PageTestCase testCase = (PageTestCase) constructor.newInstance(parameters);
			
			suite.addTest(testCase);
		}
*/		
		logger.debug("pageTestSuite End");
		
    	return suite;
    }
}
