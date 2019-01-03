package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import wdMethods.ProjectMethods;

public class LoginPage extends ProjectMethods{
	
	public LoginPage(RemoteWebDriver driver,ExtentTest test) {
		this.driver = driver;
		this.test = test;
		PageFactory.initElements(driver, this);				
	}
	
	@FindBy(how=How.ID,using="username")
	private WebElement eleUserName;
	
	public LoginPage enterUserName(String uName) {
		type(eleUserName, uName);
		return this;
		
	}
	
	@FindBy(how=How.ID,using="password")
	private WebElement elePassWord;
	
	public LoginPage enterPassword(String pwd) {
		type(elePassWord, pwd);
		return this;		
	}
	
	
	@FindBy(how=How.CLASS_NAME,using="decorativeSubmit")
	private WebElement eleLogin;
	public HomePage clickLogin() {
		click(eleLogin);
		return new HomePage(driver, test);
	}
	
	public LoginPage clickLoginForFailure() {
		click(eleLogin);
		return this;
	}
	
	@FindBy(how=How.ID,using="errorDiv")
	private WebElement eleErrorMsg;
	public LoginPage verifyErrorMsg(String expectedText) {
		verifyPartialText(eleErrorMsg, expectedText);
		return this;
	}
	
	
	
	
	
	
	
	
	
	
	

}
