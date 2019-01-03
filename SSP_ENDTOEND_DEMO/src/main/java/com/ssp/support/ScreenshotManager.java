package com.ssp.support;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.log4testng.Logger;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.screentaker.ViewportPastingStrategy;

/**
 * ScreenshotManager to take screenshots using logger class
 * 
 */
public class ScreenshotManager {
	private static final Logger logger = Logger.getLogger(ScreenshotManager.class);

	public static void takeScreenshot(WebDriver driver, String filepath) {
		File screenshot = null;
		
		try {
			if (Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser").equalsIgnoreCase("firefox")) {
				screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				File destFile = new File(filepath);
				destFile.getParentFile().mkdirs();
				FileUtils.copyFile(screenshot, destFile);
				screenshot.delete(); // it will delete the previous screenshots
			}
			else {
				Screenshot screenshot1 = new AShot().shootingStrategy(new ViewportPastingStrategy(50)).takeScreenshot(driver);
				ImageIO.write(screenshot1.getImage(), "PNG", new File(filepath));				
			}
			logger.debug("screenshot taken and stored at " + filepath);
		}
		catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * takeScreenshot to take screenshots by passing driver as parameter with date and time
	 * 
	 * @param driver
	 *            - webdriver
	 */
	public static void takeScreenshot(WebDriver driver) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-hhmmss-SSS");
		String path = "screenshots/Test-" + sdf.format(cal.getTime()) + ".jpg";
		takeScreenshot(driver, path);
	}
}