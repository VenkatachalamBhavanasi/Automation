package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

import wdMethods.ProjectMethods;

public class ViewLeads extends ProjectMethods{

	public ViewLeads(RemoteWebDriver driver, ExtentTest test) {
		
		this.driver = driver;
		this.test = test;
		PageFactory.initElements(driver, this);

	}
	
	@FindBy(how=How.ID,using="viewLead_firstName_sp")
	private WebElement eleVerifyFname;
	public ViewLeads verifyFName(String expectedText1) {
		verifyPartialText(eleVerifyFname, expectedText1);
		return this;
	

}
}