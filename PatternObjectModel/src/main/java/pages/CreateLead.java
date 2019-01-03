package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

import wdMethods.ProjectMethods;

public class CreateLead extends ProjectMethods {

	public CreateLead(RemoteWebDriver driver, ExtentTest test) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		this.test = test;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.ID, using = "createLeadForm_companyName")
	private WebElement eleCompanyName;

	public CreateLead EnterCompanyName(String CName) {
		type(eleCompanyName, CName);
		return this;
	}

	@FindBy(how = How.ID, using = "createLeadForm_firstName")
	private WebElement eleFirstName;

	public CreateLead EnterFirstName(String FName) {
		type(eleFirstName, FName);
		return this;
	}

	@FindBy(how = How.ID, using = "createLeadForm_lastName")
	private WebElement eleLastName;

	public CreateLead EnterLastName(String LName) {
		type(eleLastName, LName);
		return this;
	}

	@FindBy(how = How.ID, using = "createLeadForm_dataSourceId")
	private WebElement eleDataSourceId;

	public CreateLead SelecteleDataSourceId(String sourceId) {
		selectDropDownUsingText(eleDataSourceId, sourceId);

		return this;
	}
	// selectDropDownUsingText(locateElement("id",
	// "createLeadForm_dataSourceId"), "Conference");

	@FindBy(how = How.CLASS_NAME, using = "smallSubmit")
	private WebElement eleSubmit;

	public ViewLeads clickSubmit() {
		click(eleSubmit);
		return new ViewLeads(driver, test);
	}

}
