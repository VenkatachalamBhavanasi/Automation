package com.ssp.support;

import java.io.File;
import java.io.IOException;

import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;

public class BaseTest {
    protected static ExtentReports extent;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
	extent = new ExtentReports("target/surefire-reports/TestReport.html", true, DisplayOrder.OLDEST_FIRST,
		NetworkMode.ONLINE);
	Reporter.getCurrentTestResult().getTestContext().getSuite().setAttribute("policy_number", "");
    }

    /*
     * After suite will be responsible to close the report properly at the end
     * You an have another afterSuite as well in the derived class and this one
     * will be called in the end making it the last method to be called in test
     * exe
     */
    @AfterSuite
    public void afterSuite() {
	extent.flush();
	String env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env");
	File reportFolder = new File("test-reports/TestReports");
	File reportSourceFile = new File("target/surefire-reports/TestReport.html");
	File reportScreenshotFile = new File("test-output/ScreenShot");
	// File testNGreportSourceFile = new
	// File("test-output/TestAutomationResults.html");

	try {
	    reportFolder = FileUtils.createReportFolder(reportFolder, env);
	} catch (IOException e2) {
	    e2.printStackTrace();
	}

	String reportDestFolder = reportFolder + File.separator + "TestReport.html";
	File reportDestFile = new File(reportDestFolder);
	// String testNGreportDestFolder = reportFolder + File.separator
	// +"TestAutomationResults.html";
	// File testNGreportDestFile = new File(testNGreportDestFolder);
	String screenshotFolder = reportFolder + File.separator + "ScreenShot";
	File screenshotDestFolder = new File(screenshotFolder);

	try {
	    Thread.sleep(2000);
	} catch (InterruptedException e1) {
	    e1.printStackTrace();
	}

	try {
	    FileUtils.copyFile(reportSourceFile, reportDestFile);
	} catch (IOException e) {
	    e.printStackTrace();
	}

	/*
	 * try { FileUtils.copyFile(testNGreportSourceFile,
	 * testNGreportDestFile); } catch (IOException e) { e.printStackTrace();
	 * }
	 */

	try {
	    FileUtils.copyFolder(reportScreenshotFile, screenshotDestFolder);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
