package base;

import org.openqa.selenium.WebElement;

public interface WdBase {
	
		/**
		 * This method will launch the application in the specified browser
		 * and load the given URL
		 * @author Babu
		 * @category Selenium WebDriver
		 * @param browserName - The browser shall be specified as chrome or firefox or ie
		 * @param URL - It shall be specified as http: or https or file
		 */
		public void invokeApp(String browserName, String URL);
		
		
		public WebElement locateElement(String how, String using);
		
		public void clear(WebElement ele);
		
		public void type(WebElement ele, String data);		

		public void click(WebElement ele);

		public void selectUsingText(WebElement ele, String text);

		public void selectUsingValue(WebElement ele, String value);

		public void selectUsingIndex(WebElement ele, int index);

/*		void select(WebElement ele, String type, String textOrValue);
*/		
		public String getText(WebElement ele);

		public String getAttributeText(WebElement ele, String value);
		
		public boolean verifyText(WebElement ele, String text);		

		public boolean verifyPartialText(WebElement ele, String text);
		
		public boolean verifyTitle(String title) ;

		public void switchToFrame(WebElement ele);

		public void switchToFrame(int index);
		
		public void acceptAlert();

		public void dismissAlert();

		public void sendTextAlert(String txt);

		public String getTextAlert();	
		
		public void switchWindow(int index);		
		
		public void mouseOverOnElement(WebElement ele);
		
		
		public void dragAndDrop(WebElement ele1, WebElement ele2);
		


}
