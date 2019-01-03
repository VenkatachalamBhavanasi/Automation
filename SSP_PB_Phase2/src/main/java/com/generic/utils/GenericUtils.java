package com.generic.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.generic.support.Log;
import com.generic.support.StopWatch;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * Util class consists wait for page load,page load with user defined max time
 * and is used globally in all classes and methods
 * 
 */
public class GenericUtils {
	public static String MOUSE_HOVER_JS = "var evObj = document.createEvent('MouseEvents');"
			+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
			+ "arguments[0].dispatchEvent(evObj);";
	public static String CLICK_ELEMENT_JS = "arguments[0].click();";
	// private static EnvironmentPropertiesReader configProperty =
	// EnvironmentPropertiesReader.getInstance();

	/**
	 * To get the test orientation
	 * 
	 * <p>
	 * if test run on sauce lab device return landscape or portrait or valid
	 * message, otherwise check local device execution and return landscape or
	 * portrait or valid message
	 * 
	 * @return dataToBeReturned - portrait or landscape or valid message
	 */
	/*
	 * public static String getTestOrientation() { String dataToBeReturned = null;
	 * boolean checkExecutionOnSauce = false; boolean checkDeviceExecution = false;
	 * checkExecutionOnSauce = (System.getProperty("SELENIUM_DRIVER") != null ||
	 * System.getenv("SELENIUM_DRIVER") != null) ? true : false;
	 * 
	 * if (checkExecutionOnSauce) { checkDeviceExecution =
	 * ((System.getProperty("runUserAgentDeviceTest") != null) &&
	 * (System.getProperty("runUserAgentDeviceTest").equalsIgnoreCase("true"))) ?
	 * true : false; if (checkDeviceExecution) { dataToBeReturned =
	 * (System.getProperty("deviceOrientation") != null) ?
	 * System.getProperty("deviceOrientation") :
	 * "no sauce run system variable: deviceOrientation "; } else { dataToBeReturned
	 * = "sauce browser test: no orientation"; } } else { checkDeviceExecution =
	 * (configProperty.hasProperty("runUserAgentDeviceTest") &&
	 * (configProperty.getProperty("runUserAgentDeviceTest").equalsIgnoreCase("true"
	 * ))) ? true : false; if (checkDeviceExecution) { dataToBeReturned =
	 * configProperty.hasProperty("deviceOrientation") ?
	 * configProperty.getProperty("deviceOrientation") :
	 * "no local run config variable: deviceOrientation "; } else { dataToBeReturned
	 * = "local browser test: no orientation"; } } return dataToBeReturned; }
	 */

	public static WebDriver switchWindows(WebDriver driver, String windowToSwitch, String opt,
			String closeCurrentDriver, String match) throws Exception {

		WebDriver currentWebDriver = driver;
		WebDriver assingedWebDriver = driver;
		boolean windowFound = false;
		ArrayList<String> multipleWindows = new ArrayList<String>(assingedWebDriver.getWindowHandles());
		System.out.println(multipleWindows.size());
		System.out.println(multipleWindows);
		for (int i = 1; i < multipleWindows.size(); i++) {
			System.out.println(assingedWebDriver.getTitle());
			assingedWebDriver.switchTo().window(multipleWindows.get(i));

			/*
			 * if (opt.equals("title")) { if(match.equals("contain")){ if
			 * (assingedWebDriver.getTitle().contains(windowToSwitch)) { windowFound = true;
			 * break; } } if(match.equals("equal")){ if
			 * (assingedWebDriver.getTitle().equals(windowToSwitch)) { windowFound = true;
			 * break; } }
			 * 
			 * } else if (opt.equals("url")) { if
			 * (assingedWebDriver.getCurrentUrl().contains(windowToSwitch)) { windowFound =
			 * true; break; } } else if (opt.equals("class")){ if
			 * (assingedWebDriver.getClass().equals(windowToSwitch)){ windowFound = true;
			 * break; } }
			 */
			// if

			if (opt.equals("url")) {
				if (match.equals("contain")) {
					if (assingedWebDriver.getTitle().contains(windowToSwitch)) {
						windowFound = true;
						break;
					}
				}
				if (match.equals("equal")) {
					if (assingedWebDriver.getTitle().contains(windowToSwitch)) {
						windowFound = true;
						break;
					}
				}
			}
			if (opt.equals("title")) {
				if (match.equals("contain")) {
					if (assingedWebDriver.getTitle().contains(windowToSwitch)) {
						windowFound = true;
						break;
					}
				}
				if (match.equals("equal")) {
					if (assingedWebDriver.getTitle().contains(windowToSwitch)) {
						windowFound = true;
						break;
					}
				}

			} // for

			if (!windowFound)
				throw new Exception("Window: " + windowToSwitch + ", not found!!");
			else {
				if (closeCurrentDriver.equals("true"))
					currentWebDriver.close();
			}
		}
		return assingedWebDriver;

	}// switchWindows

	/**
	 * To compare two array list values,then print unique list value and print
	 * missed list value
	 * 
	 * @param expectedElements
	 *            - expected element list
	 * @param actualElements
	 *            - actual element list
	 * @return statusToBeReturned - returns true if both the lists are equal, else
	 *         returns false
	 */
	public static boolean compareTwoList1(WebDriver driver, List<String> expectedElements, List<String> actualElements,
			ExtentTest extentedReport) {
		boolean statusToBeReturned = false;
		List<String> uniqueList = new ArrayList<String>();
		List<String> missedList = new ArrayList<String>();
		for (String item : expectedElements) {
			if (actualElements.contains(item)) {
				uniqueList.add(item);
				Log.message("Element Displayed in policy ribbon: " + item, driver, extentedReport);
			} else {
				missedList.add(item);
			}
		}
		Collections.sort(expectedElements);
		Collections.sort(actualElements);
		if (expectedElements.equals(actualElements)) {
			Log.event("All elements checked on this page:: " + uniqueList);

			statusToBeReturned = true;
		} else {
			Log.event("Missing element on this page:: " + missedList);
			statusToBeReturned = false;
		}
		return statusToBeReturned;
	}

	/**
	 * To compare two array list values,then print unique list value and print
	 * missed list value
	 * 
	 * @param expectedElements
	 *            - expected element list
	 * @param actualElements
	 *            - actual element list
	 * @return statusToBeReturned - returns true if both the lists are equal, else
	 *         returns false
	 */
	public static boolean compareTwoList(List<String> expectedElements, List<String> actualElements) {
		boolean statusToBeReturned = false;
		List<String> uniqueList = new ArrayList<String>();
		List<String> missedList = new ArrayList<String>();
		for (String item : expectedElements) {
			if (actualElements.contains(item)) {
				uniqueList.add(item);
				Log.message("Displyed: " + item);
			} else {
				missedList.add(item);
			}
		}
		Collections.sort(expectedElements);
		Collections.sort(actualElements);
		if (expectedElements.equals(actualElements)) {
			Log.event("All elements checked on this page:: " + uniqueList);

			statusToBeReturned = true;
		} else {
			Log.event("Missing element on this page:: " + missedList);
			statusToBeReturned = false;
		}
		return statusToBeReturned;
	}

	/**
	 * To wait for the specific element which is in disabled state on the page
	 * 
	 * @param driver
	 *            - current driver object
	 * @param element
	 *            - disabled webelement
	 * @param maxWait
	 *            - duration of wait in seconds
	 * @return boolean - return true if disabled element is present else return
	 *         false
	 */
	public static boolean waitForDisabledElement(WebDriver driver, WebElement element, int maxWait) {
		boolean statusOfElementToBeReturned = false;
		long startTime = StopWatch.startTime();
		WebDriverWait wait = new WebDriverWait(driver, maxWait);
		try {
			WebElement waitElement = wait.until(ExpectedConditions.visibilityOf(element));
			if (!waitElement.isEnabled()) {
				statusOfElementToBeReturned = true;
				Log.event("Element is displayed and disabled:: " + element.toString());
			}
		} catch (Exception ex) {
			statusOfElementToBeReturned = false;
			Log.event("Unable to find disabled element after " + StopWatch.elapsedTime(startTime) + " sec ==> "
					+ element.toString());
		}
		return statusOfElementToBeReturned;
	}

	/**
	 * To get the text of a WebElement
	 * 
	 * @param element
	 *            - the input field you need the value/text of
	 * @param driver
	 *            -
	 * @return text of the input's value
	 */
	public static String getTextOfWebElement(WebElement element, WebDriver driver) {
		String sDataToBeReturned = null;
		if (WaitUtils.waitForElement(driver, element)) {
			sDataToBeReturned = element.getText().trim().replaceAll("\\s+", " ");

		}
		return sDataToBeReturned;
	}

	/**
	 * Verify contents of a WebElement equals a passed in string variable
	 * 
	 * @param textToVerify
	 *            - expected text
	 * @param elementToVerify
	 *            - element to verify the text of
	 * @return true if text on screen matches passed variable contents
	 */
	public static boolean verifyWebElementTextEquals(WebElement elementToVerify, String textToVerify) {
		boolean status = false;
		if (elementToVerify.getText().trim().replaceAll("\\s+", " ").equals(textToVerify)) {
			status = true;
		}
		return status;
	}

	/**
	 * Verify contents of a WebElement equals ignoring case a passed in string
	 * variable
	 * 
	 * @param textToVerify
	 *            - expected text
	 * @param elementToVerify
	 *            - element to verify the text of
	 * @return true if text on screen equals ignoring case passed variable contents
	 */
	public static boolean verifyWebElementTextEqualsIgnoreCase(WebElement elementToVerify, String textToVerify) {
		boolean status = false;
		if (elementToVerify.getText().trim().replaceAll("\\s+", " ").equalsIgnoreCase(textToVerify.trim())) {
			status = true;
		}
		return status;
	}

	/**
	 * Verify any text is equals a passed in string variable
	 * 
	 * @param textToVerify
	 *            - expected text
	 * @param elementToVerify
	 *            - text that got from an element
	 * @return true if text on screen matches passed variable contents
	 */
	public static boolean verifyTextEquals(String elementTextToVerify, String textToVerify) {
		boolean status = false;
		if (elementTextToVerify.equals(textToVerify)) {
			status = true;
		}
		return status;
	}

	/**
	 * Verify contents of a WebElement contains a passed in string variable
	 * 
	 * @param textToVerify
	 *            - expected text
	 * @param elementToVerify
	 *            - element to verify the text of
	 * @return true if text on screen matches passed variable contents
	 */
	public static boolean verifyWebElementTextContains(WebElement elementToVerify, String textToVerify) {
		boolean status = false;
		if ((elementToVerify.getText().trim().replaceAll("\\s+", " ")).contains(textToVerify.replace(".To", ". To"))
				|| (elementToVerify.getText().trim().replaceAll("\\s+", " ")).contains(textToVerify)) {
			status = true;
		}
		return status;
	}

	/**
	 * To get matching text element from List of web elements
	 * 
	 * @param elements
	 *            -
	 * @param contenttext
	 *            - text to match
	 * @return elementToBeReturned as WebElement
	 * @throws Exception
	 *             -
	 */
	public static WebElement getMatchingTextElementFromList(List<WebElement> elements, String contenttext)
			throws Exception {
		WebElement elementToBeReturned = null;
		boolean found = false;

		if (elements.size() > 0) {
			for (WebElement element : elements) {
				if (element.getText().trim().replaceAll("\\s+", " ").equalsIgnoreCase(contenttext)) {
					elementToBeReturned = element;
					found = true;
					break;
				}
			}
			if (!found) {
				throw new Exception("Didn't find the correct text(" + contenttext + ")..! on the page");
			}
		} else {
			throw new Exception("Unable to find list element...!");
		}
		return elementToBeReturned;
	}

	public static void sendkeys(WebDriver driver, WebElement element, String inputText) throws Exception {
		try {

			int iter = 0;

			while (iter < inputText.toCharArray().length) {
				// element.click();
				// (new WebDriverWait(driver, 10).pollingEvery(200,
				// TimeUnit.MILLISECONDS).withMessage("Unable to find the text
				// box")).until(ExpectedConditions.elementToBeClickable(element));
				// element.click();
				// Thread.sleep(100);
				// element.sendKeys(Keys.END);
				// Thread.sleep(100);
				// element.sendKeys(Keys.RIGHT);
				// Thread.sleep(100);

				(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS)
						.withMessage("Unable to find the text box"))
								.until(ExpectedConditions.elementToBeClickable(element));
				element.sendKeys(getKey(inputText.charAt(iter)));
				Thread.sleep(100);
				iter++;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} // catch
	}

	public static void sendkeys(WebDriver driver, By by, String inputText) throws Exception {
		try {

			int iter = 0;
			WebElement element = driver.findElement(by);

			while (iter < inputText.toCharArray().length) {
				// element.click();
				element = driver.findElement(by);
				(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS)
						.withMessage("Unable to find the text box"))
								.until(ExpectedConditions.elementToBeClickable(element));
				element.click();
				Thread.sleep(100);
				element.sendKeys(Keys.END);
				Thread.sleep(100);
				element.sendKeys(Keys.RIGHT);
				Thread.sleep(100);

				(new WebDriverWait(driver, 10).pollingEvery(200, TimeUnit.MILLISECONDS)
						.withMessage("Unable to find the text box"))
								.until(ExpectedConditions.elementToBeClickable(element));
				element.sendKeys(getKey(inputText.charAt(iter)));
				Thread.sleep(100);
				iter++;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} // catch
	}

	public static Keys getKey(char ch) throws Exception {
		Keys key = null;

		switch (ch) {
		case '0':
			key = Keys.NUMPAD0;
			break;
		case '1':
			key = Keys.NUMPAD1;
			break;
		case '2':
			key = Keys.NUMPAD2;
			break;
		case '3':
			key = Keys.NUMPAD3;
			break;
		case '4':
			key = Keys.NUMPAD4;
			break;
		case '5':
			key = Keys.NUMPAD5;
			break;
		case '6':
			key = Keys.NUMPAD6;
			break;
		case '7':
			key = Keys.NUMPAD7;
			break;
		case '8':
			key = Keys.NUMPAD8;
			break;
		case '9':
			key = Keys.NUMPAD9;
			break;
		}
		return key;
	}

	/**
	 * To verify matching text element from List of web elements
	 * 
	 * @param elements
	 *            -
	 * @param contenttext
	 *            - text to match
	 * @return elementToBeReturned as WebElement
	 * @throws Exception
	 *             -
	 */
	public static boolean verifyMatchingTextContainsElementFromList(List<WebElement> elements, String contenttext)
			throws Exception {

		boolean found = false;
		if (elements.size() > 0) {
			for (WebElement element : elements) {
				if (element.getText().trim().replaceAll("\\s+", " ").contains(contenttext)) {
					found = true;
					break;
				}
			}
			if (!found) {
				Log.failsoft("Didn't find the correct text(" + contenttext + ")..! on the page");
			}
		} else {
			throw new Exception("Unable to find list element...!");
		}

		return found;
	}

	/**
	 * To scroll into particular element
	 * 
	 * @param driver
	 *            -
	 * @param element
	 *            - the element to scroll to
	 */
	public static void scrollIntoView(final WebDriver driver, WebElement element) {
		try {
			String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
					+ "var elementTop = arguments[0].getBoundingClientRect().top;"
					+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";
			((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
			(new WebDriverWait(driver, 20).pollingEvery(500, TimeUnit.MILLISECONDS)
					.ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
					.withMessage("Page spinners/page not loading")).until(WaitUtils.documentLoad);
		} catch (Exception ex) {
			Log.event("Moved to element");
		}
	}

	/**
	 * To scroll into particular element
	 * 
	 * @param driver
	 *            -
	 * @param element
	 *            - the element to scroll to
	 */
	public static void scrollIntoViewFromList(final WebDriver driver, List<WebElement> elements) {
		try {
			for (WebElement element : elements) {
				String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
						+ "var elementTop = arguments[0].getBoundingClientRect().top;"
						+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";
				((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
				(new WebDriverWait(driver, 20).pollingEvery(500, TimeUnit.MILLISECONDS)
						.ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
						.withMessage("Page spinners/page not loading")).until(WaitUtils.documentLoad);
			}

		} catch (Exception ex) {
			Log.event("Moved to element");
		}
	}

	/**
	 * Switching between tabs or windows in a browser
	 * 
	 * @param driver
	 *            -
	 */
	public static void switchToNewWindow(WebDriver driver, ExtentTest extentedReport, boolean screenshot) {
		String winHandle = driver.getWindowHandle();
		for (String index : driver.getWindowHandles()) {
			if (!index.equals(winHandle)) {
				driver.switchTo().window(index);
				Log.message("Switched to new window", driver, extentedReport, screenshot);
				break;
			}
		}
		if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().matches(".*safari.*")) {
			((JavascriptExecutor) driver).executeScript(
					"if(window.screen){window.moveTo(0, 0); window.resizeTo(window.screen.availWidth, window.screen.availHeight);};");
		}
	}

	/**
	 * To perform mouse hover on an element using javascript
	 * 
	 * @param driver
	 * @param element
	 */
	public static void moveToElementJS(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript(MOUSE_HOVER_JS, element);
	}

	/**
	 * To compare two HashMap values,then print unique list value and print missed
	 * list value
	 * 
	 * @param expectedList
	 *            - expected element list
	 * @param actualList
	 *            - actual element list
	 * @return statusToBeReturned - returns true if both the lists are equal, else
	 *         returns false
	 */
	public static boolean compareTwoHashMap(HashMap<String, String> expectedList, HashMap<String, String> actualList) {
		List<String> missedkey = new ArrayList<String>();
		HashMap<String, String> missedvalue = new HashMap<String, String>();
		try {
			for (String k : expectedList.keySet()) {
				if (!(actualList.get(k).equals(expectedList.get(k)))) {
					missedvalue.put(k, actualList.get(k));
					Log.event("Missed Values:: " + missedvalue);
					return false;
				}
			}
			for (String y : actualList.keySet()) {
				if (!expectedList.containsKey(y)) {
					missedkey.add(y);
					Log.event("Missed keys:: " + missedkey);
					// Log.message("Missed Value is : " + missedkey, driver, extentedReport, true);
					return false;
				}
			}
		} catch (NullPointerException np) {
			return false;
		}
		return true;
	}

	public static boolean compareTwoHashMap(HashMap<String, String> expectedList, HashMap<String, String> actualList,
			WebDriver driver, ExtentTest extentedReport, Boolean... takeScreenShot) {
		List<String> missedkey = new ArrayList<String>();
		HashMap<String, String> missedvalue = new HashMap<String, String>();
		try {
			for (String k : expectedList.keySet()) {
				if (!(actualList.get(k).equals(expectedList.get(k)))) {
					missedvalue.put(k, actualList.get(k));
					Log.event("Missed Values:: " + missedvalue);
					Log.message("Missed Value is : " + missedvalue, driver, extentedReport, true);
					return false;
				}
			}
			for (String y : actualList.keySet()) {
				if (!expectedList.containsKey(y)) {
					missedkey.add(y);
					Log.event("Missed keys:: " + missedkey);
					Log.message("Missed Key is : " + missedkey, driver, extentedReport, true);
					return false;
				}
			}
		} catch (NullPointerException np) {
			return false;
		}
		return true;
	}

	/**
	 * To verify font of the webelement
	 * 
	 * 
	 * @param locator
	 *            - css selector of webelement
	 * @param font
	 *            - font type to verify
	 * @return boolean
	 * 
	 */
	public static boolean verifyFont(WebDriver driver, String locator, String font) throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(By.cssSelector(locator));
			String fontWeight = (String) js
					.executeScript("return getComputedStyle(arguments[0]).getPropertyValue('font-Weight');", element);
			if (fontWeight.trim().equals(font)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			throw new Exception("Error while veriying whether the string is in bold" + ex);

		}
	}

	/**
	 * To verify given string is displayed first
	 * 
	 * 
	 * @param locator
	 *            - css selector of webelement
	 * @param font
	 *            - font type to verify
	 * @return boolean
	 * 
	 */
	public static boolean verifyWebElementStartWith(WebDriver driver, WebElement element, String substringToVerify)
			throws Exception {
		try {
			boolean firstString = false;
			String displayedPolicyHolderName = getTextOfWebElement(element, driver);
			if (displayedPolicyHolderName.startsWith(substringToVerify)) {
				firstString = true;
			}
			return firstString;

		} catch (Exception ex) {
			throw new Exception("Error while veriying whether the string is in bold" + ex);

		}
	}

	/**
	 * To verify a list is in alphabetical order
	 * 
	 * @param listToCheck
	 *            - what to check alpha order of
	 * @return boolean
	 */
	public static boolean verifyListInAlphabeticalOrder(List<WebElement> listToCheck) {
		boolean status = false;
		List<String> ActualList = new ArrayList<String>();
		List<String> Sortedlist = new ArrayList<String>();
		for (WebElement element : listToCheck) {
			ActualList.add(element.getText());
			Sortedlist.add(element.getText());
		}
		Collections.sort(Sortedlist);
		if (ActualList.equals(Sortedlist)) {
			status = true;
			Log.message("List is in alphabetical order: " + Sortedlist);
		} else {
			status = false;
		}
		return status;
	}

	/**
	 * To generate Random characters
	 * 
	 * @param type
	 * @param length
	 * 
	 */
	public static String getRandomCharacters(String type, int length) {
		String SALTCHARS = null;
		if ("ALPHANUMERIC".equalsIgnoreCase(type))
			SALTCHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
		else if ("ALPHA".equalsIgnoreCase(type))
			SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
		else if ("NUMERIC".equalsIgnoreCase(type))
			SALTCHARS = "0123456789";

		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < length) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}

		String saltStr = salt.toString();
		return saltStr;
	}

	/**
	 * To set past or future date
	 * 
	 * @param days
	 *            - future/past/current
	 * @param dateFieldLocator
	 *            - locator of the date field to set the date
	 * @param nofOfDays
	 *            - No of Days to add or Subtract
	 * @param noOfYear
	 *            - No of Years to add or subtract
	 * @throws Exception
	 * 
	 */
	public static String setDate(WebDriver driver, String days, WebElement dateFieldLocator, int nofOfDays,
			int noOfYear) throws Exception {

		String pastAndFutureDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String stringDate = sdf.format(date);
		Date d = sdf.parse(stringDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		if (days.equalsIgnoreCase("future")) {
			cal.add(Calendar.DATE, +nofOfDays); // number of days to add
			cal.add(Calendar.YEAR, +noOfYear);
		} else if (days.equalsIgnoreCase("past")) {
			cal.add(Calendar.DATE, -nofOfDays); // number of days to minus
			cal.add(Calendar.YEAR, -noOfYear);
		}

		pastAndFutureDate = sdf.format(cal.getTime()).toString();
		pastAndFutureDate = pastAndFutureDate.replaceAll("\\/", "");
		System.out.println(pastAndFutureDate);
		dateFieldLocator.click();
		dateFieldLocator.clear();
		sendkeys(driver, dateFieldLocator, pastAndFutureDate);

		return pastAndFutureDate;
	}

	/**
	 * Returns a random integer number in given range.
	 * 
	 * @param min
	 * @param max
	 * @return the random integer number in given range
	 */
	public static int getRandomNumberBetween(int min, int max) {
		return new Random().nextInt(max) + min;
	}

	/**
	 * To verify String Present in Array
	 * 
	 * @param arr
	 * @param targetValue
	 * 
	 */
	public static boolean verifyStringPresentInArray(String[] arr, String targetValue) {
		for (String s : arr) {
			if (s.equals(targetValue))
				return true;
		}
		return false;
	}

	/**
	 * To check background color of given element
	 * 
	 * @param elementToCheck
	 *            - WebElement that we are checking
	 * @param desiredColor
	 *            - hex value of a color
	 * @return true if the desired color matches actual color
	 * @throws Exception
	 *             -
	 */
	public static boolean checkBackgroundColor(WebElement elementToCheck, String desiredColor) throws Exception {
		boolean flag = false;
		try {
			String color = elementToCheck.getCssValue("background-color");
			String actualColor = convertColorFromRgbaToHex(color);

			flag = actualColor.equalsIgnoreCase(desiredColor);
		} catch (NoSuchElementException ex) {
			Log.exception(ex);
		}
		return flag;
	}

	/**
	 * To check color of given element
	 * 
	 * @param elementToCheck
	 *            - WebElement that we are checking
	 * @param desiredColor
	 *            - hex value of a color
	 * @return true if the desired color matches actual color
	 * @throws Exception
	 *             -
	 */
	public static boolean checkColor(WebElement elementToCheck, String desiredColor) throws Exception {
		boolean flag = false;
		try {
			String color = elementToCheck.getCssValue("color");
			String actualColor = convertColorFromRgbaToHex(color);
			flag = actualColor.equalsIgnoreCase(desiredColor);
		} catch (NoSuchElementException ex) {
			Log.exception(ex);
		}
		return flag;
	}

	/**
	 * To convert color of an element from rgba to hex
	 * 
	 * @param color
	 *            -
	 * @return String of hex value
	 */
	public static String convertColorFromRgbaToHex(String color) {
		String[] hexValue = color.replaceAll("[^,0-9]", "").split(",");

		int hexValue1 = Integer.parseInt(hexValue[0]);
		hexValue[1] = hexValue[1].trim();
		int hexValue2 = Integer.parseInt(hexValue[1]);
		hexValue[2] = hexValue[2].trim();
		int hexValue3 = Integer.parseInt(hexValue[2]);

		String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);

		return actualColor;
	}

	/**
	 * Performs click action on the given element using javascript.
	 * 
	 * @param driver
	 * @param element
	 */
	public static void clickElementJS(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript(CLICK_ELEMENT_JS, element);
	}

	/**
	 * To get the number of a WebElement
	 * 
	 * @param element
	 *            - the input field you need the value/text of
	 * @param driver
	 *            -
	 * @return text of the input's value
	 */
	public static String getNumericOfWebElement(WebElement element, WebDriver driver) {
		String sDataToBeReturned = null;
		if (WaitUtils.waitForElement(driver, element)) {
			sDataToBeReturned = element.getText().trim().replaceAll("\\d+", " ");

		}
		return sDataToBeReturned;
	}

	/**
	 * Verify contents of a WebElement equals a passed in string variable
	 * 
	 * @param textToVerify
	 *            - expected numeric value
	 * @param elementToVerify
	 *            - element to verify the text of
	 * @return true if text on screen matches passed variable contents
	 */
	public static boolean verifyWebElementNumericEquals(WebElement elementToVerify, String textToVerify) {
		boolean status = false;
		if (elementToVerify.getText().trim().replaceAll("\\d+", " ").equals(textToVerify)) {
			status = true;
		}
		return status;
	}
}
