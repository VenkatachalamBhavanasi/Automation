package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LoginPage;
import wdMethods.ProjectMethods;

public class TC002_LoginNegative extends ProjectMethods{
	@BeforeClass
	public void setData() {
		dataSheetName = "TC002";
		testCaseName = "TC002_Login (Negative)";
		testDescription = "Login failures by negative test data";
		category= "Sanity";
		authors	="Gopi";
		browserName ="chrome";
	}
	
	@Test(dataProvider="fetchData")
	public void loginFail(String userName, String passWord, String errorMsg) {
		
		new LoginPage(driver,test)
		.enterUserName(userName)
		.enterPassword(passWord)
		.clickLoginForFailure()
		.verifyErrorMsg(errorMsg);	
		
		
		
	}

}
