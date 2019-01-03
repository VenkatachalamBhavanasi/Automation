package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

import wdMethods.ProjectMethods;

public class MyLeads extends ProjectMethods {

	public MyLeads(RemoteWebDriver driver, ExtentTest test) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		this.test = test;
		PageFactory.initElements(driver, this);

	}

	@FindBy(how = How.LINK_TEXT, using = "Create Lead")
	private WebElement eleCreateLead;

	public CreateLead clickCreateLead() {
		click(eleCreateLead);
		return new CreateLead(driver, test);
	}
	
	
}