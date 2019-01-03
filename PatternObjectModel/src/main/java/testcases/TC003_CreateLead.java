package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LoginPage;
import wdMethods.ProjectMethods;

public class TC003_CreateLead extends ProjectMethods {
	@BeforeClass
	public void setData() {
		dataSheetName = "TC003";
		testCaseName = "TC003_CreateLead";
		testDescription = "To Create Lead";
		category= "Smoke";
		authors	="Venkat";
		browserName ="chrome";
	}
	@Test(dataProvider="fetchData")
	public void logInLogOut(String userName, String passWord, String C1Name, String F1Name, String L1Name, String expected1, String sourceId ) {
		
		new LoginPage(driver, test)
		.enterUserName(userName)
		.enterPassword(passWord)
		.clickLogin()
		.clickCRMSFA()
		.clickLead()
		.clickCreateLead()
		.EnterCompanyName(C1Name)
		.EnterFirstName(F1Name)
		.EnterLastName(L1Name)
		.SelecteleDataSourceId(sourceId)
		.clickSubmit()
		.verifyFName(expected1);
		
	}
	
	
	
	

}
