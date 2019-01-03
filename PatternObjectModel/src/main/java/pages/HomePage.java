package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

import wdMethods.ProjectMethods;

public class HomePage extends ProjectMethods {

	public HomePage(RemoteWebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;

		PageFactory.initElements(driver, this);

	}

	@FindBy(how = How.CLASS_NAME, using = "decorativeSubmit")
	private WebElement eleLogOut;

	public LoginPage clickLogout() {
		click(eleLogOut);
		return new LoginPage(driver, test);
	}

	@FindBy(how = How.LINK_TEXT, using = "CRM/SFA")
	private WebElement eleCRMSFA;

	public MyHomes clickCRMSFA() {
		click(eleCRMSFA);
		return new MyHomes(driver, test);

	}

}
