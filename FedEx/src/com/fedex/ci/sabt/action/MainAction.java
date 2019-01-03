package com.fedex.ci.sabt.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fedex.ci.sabt.excel.ExcelProcessor;
import com.fedex.ci.sabt.model.RowData;
import com.fedex.ci.sabt.util.TestConstants;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

public abstract class MainAction {
	private static final FedExLoggerInterface logger = FedExLogger.getLogger(MainAction.class);
	protected WebDriver driver;
	protected String testCase = "";
	WebDriverWait wait;
	
	public MainAction(String testCase) {
		this.testCase = testCase;
	}

	public void setUp() throws Exception {
		logger.debug("opening the firefox");
		System.setProperty("webdriver.ie.driver", "D:\\selenium\\IEDriverServer.exe");
		driver = new InternetExplorerDriver();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 60);
	}
	
	public void tearDown() throws Exception {
		//logout
		//clickById("logoutLink");
		clickById("logoutForm:entTabTopNextLink");
		logger.debug("quitting the window");
		// close window
		driver.quit();
	}
	
	protected void clickById(String id) throws Exception {
		//Thread.sleep(TestConstants.SLEEP_SHORT_PAUSE);
		//shortPause();
		  mediumPause();
		try {
			//checkByClickableButtonById(id);
			driver.findElement(By.id(id)).click();
		} catch (NoSuchElementException e) {
			logger.error("not able find the element clickById " + testCase + id);
			screenShot(testCase);
			throw e;
		}
		mediumPause();
		//Thread.sleep(TestConstants.SLEEP_MEDIUM_PAUSE);
	}
	
	protected void clickByName(String name) throws Exception {
		//Thread.sleep(TestConstants.SLEEP_SHORT_PAUSE);
		shortPause();
		try {
			//checkByClickableButtonByName(name);
			driver.findElement(By.name(name)).click();
		} catch (NoSuchElementException e) {
			logger.error("not able find the element clickByName " + testCase + name);
			screenShot(testCase);
			throw e;
		}
		mediumPause();
		//Thread.sleep(TestConstants.SLEEP_MEDIUM_PAUSE);
	}
	
	protected void clickByXpath(String xpath) throws Exception {
		//Thread.sleep(TestConstants.SLEEP_SHORT_PAUSE);
		shortPause();
		try {
			//checkByClickableButtonByXpath(xpath);
			driver.findElement(By.xpath(xpath)).click();
		} catch (NoSuchElementException e) {
			logger.error("not able find the element clickByXpath " + testCase + xpath);
			screenShot(testCase);
			throw e;
		}
		mediumPause();
		//Thread.sleep(TestConstants.SLEEP_MEDIUM_PAUSE);
	}
	
	protected void selectByVisibleText(String id, String val) throws Exception {
		try {
			if ( isNotNull(val) )
				new Select(driver.findElement(By.id(id))).selectByVisibleText(val);
		} catch (NoSuchElementException e) {
			logger.error("not able find the element selectByVisibleText " + testCase + id);
			screenShot(testCase);
			throw e;
		}
		shortPause();
		//Thread.sleep(TestConstants.SLEEP_SHORT_PAUSE);
	}
	
	protected String findTextById(String id)  throws Exception {
		String val = "";
		
		try {
			checkPresenceOfElementById(id);
			
	    	val = driver.findElement(By.id(id)).getText();
		} catch (NoSuchElementException e) {
			logger.error("not able find the element findTextById " + testCase + id);
			screenShot(testCase);
			throw e;
		}
		return val;
	}
	
	protected String findTextByXpath(String xpath)  throws Exception {
		String val = "";
		
		try {
	    	val = driver.findElement(By.xpath(xpath)).getText();
		} catch (NoSuchElementException e) {
			logger.error("not able find the element with xpath findTextByXpath " + testCase);
			screenShot(testCase);
			throw e;
		}
		return val;
	}
	
	protected String findTextByIdFromList(String id, String key)  throws Exception {
		logger.debug("id = " + id + ", key = " + key);
		try {
			List<WebElement> webElements = driver.findElements(By.id(id));
		    
		    for (WebElement ele : webElements) {
		    	//logger.debug("ele = " + ele.getText());
		    	//logger.debug("element = " + ele.toString());
		    	if ( key.equalsIgnoreCase(ele.getText()) ) {
		    		return key;
		    	}
		    }
		} catch (NoSuchElementException e) {
			logger.error("not able find the elements findTextByIdFromList " + testCase + id);
			screenShot(testCase);
			throw e;
		}	    return "";
	}
	
	protected String findTextByXpathFromList(String xpath, String key)  throws Exception {
		logger.debug("xpath = " + xpath + ", key = " + key);
		try {
			List<WebElement> webElements = driver.findElements(By.xpath(xpath));
		    
		    for (WebElement ele : webElements) {
		    	logger.debug("ele = " + ele.getText());
		    	//logger.debug("element = " + ele.toString());
		    	if ( key.equalsIgnoreCase(ele.getText()) ) {
		    		return key;
		    	}
		    }
		} catch (NoSuchElementException e) {
			logger.error("not able find the elements in xpath findTextByXpathFromList " + testCase);
			screenShot(testCase);
			throw e;
		}
	    return "";
	}
	
	protected String findTextByCssFromList(String css, String key)  throws Exception {
		logger.debug("css = " + css + ", key = " + key);
		try {
			List<WebElement> webElements = driver.findElements(By.cssSelector(css));
		    
		    for (WebElement ele : webElements) {
		    	logger.debug("element = " + ele.getTagName() + "," + ele.getText() + "," + ele.getAttribute("id"));
		    	if ( key.equalsIgnoreCase(ele.getText()) ) {
		    		return key;
		    	}
		    }
		} catch (NoSuchElementException e) {
			logger.error("not able find the elements findTextByCssFromList " + testCase);
			screenShot(testCase);
			throw e;
		}
	    return "";
	}

	protected WebElement findLinkByName(String name1, String xpath)  throws Exception {
		logger.debug("name1, xpath = " + name1 + "," + xpath);
		try {
			List<WebElement> webElements = driver.findElements(By.xpath(xpath));

			for (WebElement ele : webElements) {
				logger.debug("text, tag = " + ele.getText() + "," + ele.getTagName());
				if ( ele.getText().equalsIgnoreCase(name1) && ele.getTagName().equals("a") ) {
					return ele;
				}
			}
		} catch (NoSuchElementException e) {
			logger.error("not able find the element findLinkByName " + testCase + name1);
			screenShot(testCase);
			throw e;
		}		
		
		return null;
	}	

	protected WebElement findLinkByName(String name1, String name2, String xpath)  throws Exception {
		logger.debug("name1, name2, xpath = " + name1 + "," + name2 + "," + xpath);
		try {
			
			if ( "Add Opco".equals(name1) )
				return findLinkByName(name1, xpath);
			
			List<WebElement> webElements = driver.findElements(By.xpath(xpath));
			boolean found = false;
			String foundId = "";
			
			for (WebElement ele : webElements) {
		    	logger.debug("text, tag = " + ele.getText() + "," + ele.getTagName());
	    		if ( ele.getText().equalsIgnoreCase(name2) ) {
		    		found = true;
		    		foundId = ele.getAttribute("id");
		    		//logger.debug("found the name2 " + foundId);
		    		break;
		    	}
		    }
			
			String tmpId = "";
			
			if ( found ) {
				foundId = foundId.substring(0, foundId.lastIndexOf(":"));
				
				//logger.debug("foundId after substring " + foundId);
				
				for (WebElement ele : webElements) {
			    	//logger.debug("text, tag = " + ele.getText() + "," + ele.getTagName());
		    		if ( ele.getText().equalsIgnoreCase(name1) && ele.getTagName().equals("a") ) {
		    			tmpId = ele.getAttribute("id");
		    			//logger.debug("tmpId " + tmpId);
		    			tmpId = tmpId.substring(0, tmpId.lastIndexOf(":"));
		    			//logger.debug("tmpId after substring " + tmpId);
		    			
		    			if ( foundId.equalsIgnoreCase(tmpId) ) {
		    				return ele;
		    			}
			    	}
			    }
			}
		} catch (NoSuchElementException e) {
			logger.error("not able find the element findLinkByName " + testCase + name1);
			screenShot(testCase);
			throw e;
		}
	    return null;
	}
	
	protected String findAccountTextByIdFromList(String id)  throws Exception {
		logger.debug("id = " + id);
		try {
			List<WebElement> webElements = driver.findElements(By.id(id));
		    
		    for (WebElement ele : webElements) {
		    	if ( ele.getText().matches("[0-9]{9}") ) {
		    		return ele.getText();
		    	}
		    }
		} catch (NoSuchElementException e) {
			logger.error("not able find the element findAccountTextByIdFromList " + testCase + id);
			screenShot(testCase);
			throw e;
		}
	    return "";
	}
	
	protected String findAccountTextByXpathFromList(String xpath)  throws Exception {
		logger.debug("xpath = " + xpath);
		try {
			List<WebElement> webElements = driver.findElements(By.xpath(xpath));
		    
		    for (WebElement ele : webElements) {
		    	logger.debug("ele = " + ele.getText());
		    	//logger.debug("element = " + ele.toString());
		    	if ( ele.getText().matches("[0-9]{9}") ) {
		    		return ele.getText();
		    	}
		    }
		} catch (NoSuchElementException e) {
			logger.error("not able find the element findAccountTextByXpathFromList " + testCase);
			screenShot(testCase);
			throw e;
		}
	    return "";
	}
	
	protected void setText(String comp, String text) throws Exception {
		if ( isNotNull(text) ) {
			try {
				driver.findElement(By.id(comp)).clear();
				driver.findElement(By.id(comp)).sendKeys(text);
			} catch (NoSuchElementException e) {
				logger.error("not able find the element setText " + testCase + comp);
				screenShot(testCase);
				throw e;
			}
		}
	}
	
	protected void setTextByXpath(String xpath, String text) throws Exception {
		if ( isNotNull(text) ) {
			try {
				driver.findElement(By.xpath(xpath)).clear();
				driver.findElement(By.xpath(xpath)).sendKeys(text);
			} catch (NoSuchElementException e) {
				logger.error("not able find the element setTextByXpath " + testCase);
				screenShot(testCase);
				throw e;
			}
		}
	}
	
	protected void setTextByXpathWithoutClear(String xpath, String text) throws Exception {
		if ( isNotNull(text) ) {
			try {
				driver.findElement(By.xpath(xpath)).sendKeys(text);
			} catch (NoSuchElementException e) {
				logger.error("not able find the element setTextByXpathWithoutClear " + testCase);
				screenShot(testCase);
				throw e;
			}
		}
	}
	
	protected void setTextByCss(String css, String text) throws Exception {
		if ( isNotNull(text) ) {
			try {
				driver.findElement(By.cssSelector(css)).clear();
				driver.findElement(By.cssSelector(css)).sendKeys(text);
			} catch (NoSuchElementException e) {
				logger.error("not able find the element setTextByCss " + testCase);
				screenShot(testCase);
				throw e;
			}
		}
	}
	
	/*protected void login(String role) throws Exception {
		List<RowData> loginData = ExcelProcessor.read(TestConstants.SHEET_LOGIN);
		
		String baseUrl = loginData.get(0).getRowData().get("baseURL") + loginData.get(0).getRowData().get("uri");
		logger.debug("baseUrl = " + baseUrl);
		
		driver.get(baseUrl);
		
		String local = loginData.get(0).getRowData().get("local");
		String user = "";
		
		if ( "N".equalsIgnoreCase(local) ) {
			user = loginData.get(0).getRowData().get("login");
			String pwd = loginData.get(0).getRowData().get("password");
			setText("login", user);
			setText("password", pwd);
			clickById("submit");
		}
		
		logger.info("user, url, local = <" + user + ">,<" + baseUrl + ">,<" + local + ">");
		
		logger.debug("wsso login complete");
	}*/
	
	private void copy(String user){
		logger.debug("copying " + user);
		
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			in = new BufferedReader(new FileReader(TestConstants.SABT_USERS_SRC_FILE));
			out = new PrintWriter(new BufferedWriter(new FileWriter(TestConstants.SABT_USERS_FILE)));
			
			String tmp = "";
		
			while ( (tmp = in.readLine()) != null ) {
				if ( tmp.startsWith(user + "~") ) {
					out.println(tmp);
					break;
				}
			}
		} catch (Exception e) {
			logger.error("Exception in copy() " + e.toString());
		} finally {
			try {
				in.close();
				out.close();
			} catch (Exception e) {
				logger.error("unable to close the streams in copy " + e.toString());
			}
		}
	}
	
	protected void login(String user, String pwd) throws Exception {
		List<RowData> loginData = ExcelProcessor.read(TestConstants.SHEET_LOGIN);
		String local = loginData.get(0).getRowData().get("local");
		
		if ( "Y".equalsIgnoreCase(local) ) {
			//copy {user}.txt to c:\\pTemp\\sabt\\users.txt
			copy(user.trim());
		}
		
		String url = loginData.get(0).getRowData().get("baseURL") + loginData.get(0).getRowData().get("uri");
		logger.debug("+++ url = " + url);
		driver.get(url);
		
		if ( "N".equalsIgnoreCase(local) ) {
			setText("login", user);
			setText("password", pwd);
			clickById("submit");
		}
		
		logger.debug("user, url, local = <" + user + ">,<" + url + ">,<" + local + ">");
		
		logger.debug("wsso login complete");
	}
	
	public boolean isNotNull(String s) {
		return (s != null && s.length() > 0);
	}

	public boolean isNull(String s) {
		return (s == null || s.length() <= 0);
	}

	protected void writeAccountNumber(String opco, String acct) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(TestConstants.SABT_TEMP_DATA_DIR + opco + ".txt")));
			out.println(acct);
			out.close();
		} catch (Exception e) {
			logger.error("unable to write the account number <opco, acct> = <" + opco + "," + acct + ">");
		}
		
	}

	protected String readAccountNumber(String opco) {
		String acct = "";
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(TestConstants.SABT_TEMP_DATA_DIR + opco + ".txt"));
			
			acct = in.readLine();
		} catch (Exception e) {
			logger.error("unable to read the account number <opco> = <" + opco + ">");
		}
		
		return acct;
	}
	
	protected boolean checkByIdByVisibleText(String id, String expected) {
		if ( isNotNull(expected) ) {
			WebElement ele = driver.findElement(By.id(id));
			Select dropDown = new Select(ele); 
			String actual = dropDown.getFirstSelectedOption().getText();
			return checkText(expected, actual);
		}
		
		return false;
	}

	protected String getVisibleText(String id) {
		if ( isNotNull(id) ) {
			WebElement ele = driver.findElement(By.id(id));
			Select dropDown = new Select(ele); 
			return dropDown.getFirstSelectedOption().getText();
		}
		return "";
	}
	
	protected boolean checkById(String id, String expected) {
		if ( isNotNull(expected) ) {
			logger.debug("id = " + id);
			logger.debug("driver.findElement(By.id(id)).getText() = " + driver.findElement(By.id(id)).getText());
			logger.debug("driver.findElement(By.id(id)).getAttribute(value) " + driver.findElement(By.id(id)).getAttribute("value"));
			
			String actual = driver.findElement(By.id(id)).getText();
			if ( isNull(actual) )
				actual = driver.findElement(By.id(id)).getAttribute("value");
			
			return checkText(expected, actual);
		}
		
		return false;
	}

	protected boolean checkText(String expected, String actual) {
		if ( isNotNull(expected) ) {
			logger.debug("exp, act = " + expected + "," + actual);
			
			expected = expected.toUpperCase();
			expected = expected.replaceAll(" ", "");
			
			if ( isNotNull(actual) ) {
				actual = actual.toUpperCase();
				actual = actual.replaceAll(" ", "");
			}
			
			try {
				assertEquals(expected, actual);
			} catch (AssertionError e) {
				logger.error("assertionError checkText " + testCase);
				screenShot(testCase);
				throw e;
			}
			
			return true;
		}
		
		return false;
	}
	
	protected boolean checkByXpath(String xpath, String expected) {
		if ( isNotNull(expected) ) {
			String actual = driver.findElement(By.xpath(xpath)).getText();
			logger.debug("xpath, expected, actual = " + xpath + "," + expected + "," + actual);
		
			return checkText(expected, actual);
		}
		
		return false;
	}
	
	protected boolean checkByCSS(String css, String expected) {
		if ( isNotNull(expected) ) {
			String actual = driver.findElement(By.cssSelector(css)).getAttribute("value");
			logger.debug("expected, actual = " + expected + "," + actual);
		
			return checkText(expected, actual);
		}
		
		return false;
	}

	protected void checkLinks(String link, String parentOpco, String xpath) throws Exception {

		if ( isNull(link) )
			return;
		
		String[] links = link.split(":");
		
		boolean noOper = false;
		WebElement ele = null;
		
		if ( links != null ) {
			for ( String s : links ) {
				String expAction = "";
				String actAction = "";
				
				logger.debug("checking for link " + s);
				if ( s.startsWith("!") ) {
					noOper = true;
					s = s.substring(1);
				} else
					noOper = false;
				
				if ( isNotNull(parentOpco) )
					ele = findLinkByName(s, parentOpco, xpath);
				else
					ele = findLinkByName(s, xpath);
		
				if ( ele != null )
					actAction = ele.getText();
				
				expAction = s;
				
				if ( noOper )
					expAction = "";

				logger.debug("exp, act = " + expAction + "," + actAction);
				
				try {
					assertEquals(expAction, actAction);
				} catch (AssertionError e) {
					logger.error("assertionError checkLinks " + testCase);
					screenShot(testCase);
					throw e;
				}
			}
		}
	}
	
	
	protected void clickMultipleElements(String xpath, String button) {
		logger.debug("xpath = " + xpath + ", button = " + button);
		try {
			List<WebElement> webElements = driver.findElements(By.xpath("//*[starts-with(@id, '" + xpath + "')]"));
			logger.debug("got the webElements = " + (webElements != null));
			
			if ( webElements == null || (webElements != null && webElements.isEmpty()) )
				return;
			
		    for (WebElement ele : webElements) {
		    	logger.debug("looping thru elements " + ele.getText());
		    	if ( ele.getText().equalsIgnoreCase(button) ) {
		    		logger.debug("found ele = " + ele.getText() + "," + ele.getTagName());
		    		try {
		    			ele.click();
		    			logger.debug("removed one element");
		    			//Thread.sleep(TestConstants.SLEEP_SHORT_PAUSE);
		    			shortPause();
		    			clickMultipleElements(xpath, button);
		    		} catch (NoSuchElementException e) {
		    			logger.error("ignoring clickMultipleElements " + testCase);
		    		} catch (Exception e) {
		    			logger.error("ignoring InterruptedException " + testCase);
					}
		    	}
		    }
			logger.debug("completed clickMulti call");
			
		} catch (NoSuchElementException e) {
			logger.error("not able find the element clickMultipleElements " + testCase);
			screenShot(testCase);
			throw e;
		}
	}

	/*protected void clickMultipleElements(String xpath, String button) {
		logger.debug("xpath = " + xpath + ", button = " + button);
		try {
			List<WebElement> webElements = driver.findElements(By.xpath("//*[starts-with(@id, '" + xpath + "')]"));
			logger.debug("got the webElements = " + (webElements != null));
			
			if ( webElements == null || (webElements != null && webElements.isEmpty()) )
				return;
			
			List<WebElement> clickElements = new ArrayList<WebElement>();
			
		    for (WebElement ele : webElements) {
		    	logger.debug("looping thru elements " + ele.getText());
		    	if ( ele.getText().equalsIgnoreCase(button) ) {
		    		logger.debug("found ele = " + ele.getText() + "," + ele.getTagName());
		    		try {
		    			clickElements.add(ele);
		    			logger.debug("added one element");
		    		} catch (NoSuchElementException e) {
		    			logger.error("ignoring in add - clickMultipleElements " + testCase);
		    		} catch (Exception e) {
		    			logger.error("ignoring in add - InterruptedException " + testCase);
					}
		    	}
		    }
		    
		    for (WebElement ele : clickElements) {
	    		try {
	    			ele.click();
	    			logger.debug("removed one element");
	    			shortPause();
	    		} catch (NoSuchElementException e) {
	    			logger.error("ignoring in click - clickMultipleElements " + testCase);
	    		} catch (Exception e) {
	    			logger.error("ignoring in click - InterruptedException " + testCase);
				}
		    }
			logger.debug("completed clickMulti call");
			
		} catch (NoSuchElementException e) {
			logger.error("not able find the element clickMultipleElements " + testCase);
			screenShot(testCase);
			throw e;
		}
	}
	*/
	/*protected void clickMultipleElements(String mainPath, String button) {
		logger.debug("mainPath = " + mainPath + ", button = " + button);
		try {
			for ( int i=0; i<5; i++ ) {
				try {
					logger.debug("finding the element with id " + mainPath + i + button);
					WebElement ele = driver.findElement(By.id(mainPath + i + button));
					logger.debug("found the ele " + (ele != null));
					
					if ( ele == null )
						continue;
					
	    			ele.click();
	    			logger.debug("removed one element");
	    			//Thread.sleep(TestConstants.SLEEP_SHORT_PAUSE);
	    			shortPause();
	    		} catch (NoSuchElementException e) {
	    			logger.error("ignoring clickMultipleElements for id " + mainPath + i + button);
	    		} catch (Exception e) {
	    			logger.error("ignoring InterruptedException for id " + mainPath + i + button);
				}
			}
			logger.debug("completed clickMulti call");
			
		} catch (NoSuchElementException e) {
			logger.error("not able find the element clickMultipleElements " + testCase);
			screenShot(testCase);
			throw e;
		}
	}*/

	protected boolean checkNotNull(Object obj, String msg) {
		try {
			assertNotNull(obj);
		} catch (AssertionError e) {
			logger.error("assertionError checkText " + testCase + ", msg = " + msg);
			screenShot(testCase);
			throw e;
		}
			
		return true;
	}
	
	protected void screenShot(String testCase) {
		testCase = testCase.replaceAll(" ", "_");
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File(TestConstants.SABT_SCREEN_SHOTS_DIR + testCase + ".jpg"));
		} catch (Exception e) {
			logger.error("unable to copy the file " + scrFile.getName() + ", " + TestConstants.SABT_SCREEN_SHOTS_DIR + testCase + ".jpg");
		}
	}
	
	protected void checkByClickableButtonById(String id) throws Exception {
		try {
			if ( isNotNull(id) ) 
				wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
		} catch (Exception e) {
			logger.error("not able find the element, exception " + testCase + " " + id);
			screenShot(testCase);
			throw e;
		}
	}

	protected void checkByClickableButtonByXpath(String xpath) throws Exception {
		try {
			if ( isNotNull(xpath) ) 
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		} catch (Exception e) {
			logger.error("not able find the element, exception " + testCase + " " + xpath);
			screenShot(testCase);
			throw e;
		}

	}

	protected void checkByClickableButtonByName(String name) throws Exception {
		try {
			if ( isNotNull(name) ) 
				wait.until(ExpectedConditions.elementToBeClickable(By.name(name)));
		} catch (Exception e) {
			logger.error("not able find the element, exception " + testCase + " " + name);
			screenShot(testCase);
			throw e;
		}

	}

	protected boolean isElementPresent(String id) throws Exception {
		if ( isNotNull(id) ) {
			logger.debug("checking for the element presence " + id);
			try {
				WebElement ele = driver.findElement(By.id(id));
				if ( ele != null )
					return true;
			} catch (NoSuchElementException e) {
				logger.debug("isElementPresent - not able find the element " + id);
				return false;
			}
		}
		return false;
	}

	protected void checkPresenceOfElementByTextById(String id, String text) throws Exception {
		try {
			if ( isNotNull(id) && isNotNull(text)) 
				wait.until(ExpectedConditions.textToBePresentInElement(By.id(id), text));
		} catch (Exception e) {
			logger.error("not able find the element, exception " + testCase + " " + id + " " + text);
			screenShot(testCase);
			throw e;
		}
	}
	
	protected void checkPresenceOfElementByValueById(String id, String text) throws Exception {
		try {
			if ( isNotNull(id) && isNotNull(text)) 
				wait.until(ExpectedConditions.textToBePresentInElementValue(By.id(id), text));
		} catch (Exception e) {
			logger.error("not able find the element, exception " + testCase + " " + id + " " + text);
			screenShot(testCase);
			throw e;
		}
	}
	
	protected void checkPresenceOfElementById(String id) throws Exception {
		try {
			if ( isNotNull(id) ) 
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
		} catch (Exception e) {
			logger.error("not able find the element, exception " + testCase + " " + id);
			screenShot(testCase);
			throw e;
		}
	}
	
	protected void checkPresenceOfElementByXpath(String xpath) throws Exception {
		try {
			if ( isNotNull(xpath) ) 
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		} catch (Exception e) {
			logger.error("not able find the element, exception " + testCase + " " + xpath);
			screenShot(testCase);
			throw e;
		}
	}
	protected void longPause() throws Exception {
		Thread.sleep(TestConstants.SLEEP_LONG_PAUSE);
	}
	
	protected void mediumPause() throws Exception {
		Thread.sleep(TestConstants.SLEEP_MEDIUM_PAUSE);
	}
	
	protected void shortPause() throws Exception {
		Thread.sleep(TestConstants.SLEEP_SHORT_PAUSE);
	}
	
	public void printPageSource() {
		if ( logger.debugEnabled() ) {
			logger.debug("+++ page source begin +++");
			logger.debug(driver.getPageSource());
			logger.debug("+++ page source end +++");
		}
	}

	
	public abstract void execute() throws Exception;
}
