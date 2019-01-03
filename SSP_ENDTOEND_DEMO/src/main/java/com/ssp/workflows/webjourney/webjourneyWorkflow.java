package com.ssp.workflows.webjourney;

import java.util.Calendar;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;

import com.relevantcodes.extentreports.ExtentTest;
import com.ssp.data.WebjourneyDataDriver;
import com.ssp.pages.webjourney.ConfirmationPage;
import com.ssp.pages.webjourney.GetQuotePage;
import com.ssp.pages.webjourney.PaymentPage;
import com.ssp.pages.webjourney.SagePayConfirmationPage;
import com.ssp.pages.webjourney.SagePayDetailsPage;
import com.ssp.pages.webjourney.SignInPage;
import com.ssp.pages.webjourney.SummaryPage;
import com.ssp.pages.webjourney.ThankYouPage;
import com.ssp.pages.webjourney.UserRegPage;
import com.ssp.pages.webjourney.YourQuotePage;
import com.ssp.support.BaseTest;
import com.ssp.support.EmailReport;
import com.ssp.support.Log;

@Listeners(EmailReport.class)
public class webjourneyWorkflow extends BaseTest {
    private String webSite;
    public WebjourneyDataDriver _dataObject;
    private ExtentTest _extentedReport;
    private WebDriver _driver;
    private GetQuotePage getQuotePage;
    private YourQuotePage yourQuotePage;
    private SummaryPage summaryPage;
    private PaymentPage paymentPage;
    private ConfirmationPage confirmationpage;
    private ThankYouPage thankyoupage;
    private SagePayDetailsPage sagepadetailspage;
    private SagePayConfirmationPage sagepayconfirmpage;
    private UserRegPage userRegPage;
    private SignInPage signInPage;
    public static String quoteNumber;
    
    public webjourneyWorkflow(WebDriver driver, ExtentTest extentedReport) throws Exception {
    	try {
    		_driver = driver;
    		_extentedReport = extentedReport;    		
    	}
    	catch (Exception e) {
             Log.exception(e, driver, extentedReport);
        }// catch
        finally {
             Log.endTestCase(extentedReport);             
        }// finally
    }//annualWorkflow
    
    public void EnterQuoteDetails() throws Exception {
    	
    	try {
    		
            //Navigate and Verify Get Quote Page
    		_driver.get(_dataObject.webSite);
            getQuotePage = new GetQuotePage(_driver);
            getQuotePage.verifyGetQuotePage();
            Log.message("Navigated to Get Quote Page successfully!!");
            
            //Enter Details of Home
            getQuotePage.enterPostCode(_dataObject.postcode);
            getQuotePage.clickFindAddress();
            Log.message("Entered address of the customer with postcode : " + _dataObject.postcode, _driver, _extentedReport);

            getQuotePage.selectCoverType(_dataObject.covertype);
            Log.message("Selected Cover Type : " + _dataObject.covertype, _driver, _extentedReport);

            getQuotePage.enterYearOfBuild(_dataObject.yearofbuild);
            Log.message("Entered year of Build : " + _dataObject.yearofbuild, _driver, _extentedReport);

            getQuotePage.selectNumberofBedroom(_dataObject.noofbedroom);
            Log.message("Selected number of Bedrooms : " + _dataObject.noofbedroom, _driver, _extentedReport);

            getQuotePage.selectTypeOfProperty(_dataObject.propertytype);
            Log.message("Selected number of Bedrooms : " + _dataObject.propertytype, _driver, _extentedReport);

            //Enter customer Details
            getQuotePage.selectTitle(_dataObject.title);
            Log.message("Selected title : " + _dataObject.title, _driver, _extentedReport);

            getQuotePage.enterFirstName(_dataObject.firstname);
            Log.message("Entered first name : " + _dataObject.firstname, _driver, _extentedReport);

            getQuotePage.enterLastName(_dataObject.lastname);
            Log.message("Entered Last Name :" + _dataObject.lastname, _driver, _extentedReport);

            getQuotePage.selectDateofBirth(_dataObject.dob, "");
            Log.message("SelectedDate of Birth : " + _dataObject.dob, _driver, _extentedReport);

            if (!_dataObject.covertype.equalsIgnoreCase("Contents") && !_dataObject.buildinginsurance.equals("")) {
            	getQuotePage.selectBuildingIns(_dataObject.buildinginsurance);
            	Log.message("Number of years Building Insurance Held : " + _dataObject.buildinginsurance, _driver, _extentedReport);
            }

            if (!_dataObject.covertype.equalsIgnoreCase("Buildings") &&!_dataObject.contentsinsurance.equals("")) {
            	getQuotePage.selectContentIns(_dataObject.contentsinsurance);            
            	Log.message("Number of years Content Insurance Held : " + _dataObject.contentsinsurance, _driver, _extentedReport);
            }

            getQuotePage.selectJointPolicy(_dataObject.jointpolicy);
            Log.message("Select Join policy Holder ?" + _dataObject.jointpolicy, _driver, _extentedReport);

            if (_dataObject.jointpolicy.equalsIgnoreCase("yes") && _dataObject.jointpolicydetails.size() > 0)
            	getQuotePage.enterJointPolicyDetails(_dataObject.jointpolicydetails.get(0));
            	
            //Enter other details
            getQuotePage.selectClaimsMade(_dataObject.claimsmade);
            Log.message("Previous Claims Made ? " + _dataObject.claimsmade, _driver, _extentedReport);

            if (_dataObject.claimsMadeDetails.size() > 0)
            	getQuotePage.enterClaimMadeDetails(_dataObject.claimsMadeDetails);
            	
            getQuotePage.selectPersonalItems(_dataObject.personalitems);
            Log.message("Personal Items? " + _dataObject.personalitems, _driver, _extentedReport);

            if (_dataObject.personalitemslist.size() > 0) {
            	getQuotePage.enterPI(_dataObject.personalitemslist);
            	Log.message("Personal Items with specific covers? " + _dataObject.personalitemslist, _driver, _extentedReport);
            }
               
            getQuotePage.selectBicycle(_dataObject.bicycle);
            Log.message("Bicycle?" + _dataObject.bicycle, _driver, _extentedReport);

            if (_dataObject.bicycleslist.size() > 0) {
            	getQuotePage.enterBicycle(_dataObject.bicycleslist);
            	Log.message("Bicycles with specific covers? " + _dataObject.bicycleslist, _driver, _extentedReport);
            }
               
            getQuotePage.selectHighRisk(_dataObject.highrisk);
            Log.message("High Risk Items?" + _dataObject.highrisk, _driver, _extentedReport);

            if (_dataObject.highriskitemslist.size() > 0) {
            	getQuotePage.enterHRI(_dataObject.highriskitemslist);
            	Log.message("Highrisk Items with specific covers? " + _dataObject.personalitemslist, _driver, _extentedReport);
            }
               
            getQuotePage.selectPromocode(_dataObject.havepromocode);
            Log.message("Have any promo code?" + _dataObject.havepromocode, _driver, _extentedReport);

            if (_dataObject.havepromocode.equalsIgnoreCase("yes")) {
            	getQuotePage.setPromocode(_dataObject.promocode, _dataObject.staffcode);
            	Log.message("Promo Code?" + _dataObject.promocode + "-" + _dataObject.staffcode == ""?"Staff Code not Applicable": _dataObject.staffcode, _driver, _extentedReport);
            }              
            
        }// try
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }// catch
        finally {                        
        }// finally
    }//EnterQuoteDetails

    public void SubmitQuote() throws Exception {
    	
    	try {
            //Click on Get Quote
            yourQuotePage = getQuotePage.getQuote(_driver,_extentedReport, true);
            Log.message("Details entered and clicked on Get Quote", _driver, _extentedReport);            
            
        }// try
        catch (Exception e) {
        	throw new Exception(e.getMessage());
        }// catch
        finally {                        
        }// finally
    }//SubmitQuote

    public void SaveQuote() throws Exception {
    	
    	try {
            //Click on Get Quote
    		userRegPage = yourQuotePage.saveQuote(_driver, true);
            Log.message("Clicked on Save Quote", _driver, _extentedReport);            
            
        }// try
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }// catch
        finally {                        
        }// finally
    }//SaveQuote
       
    public void EnterCoverDetails() throws Exception {
    	
    	try {
    		if (!yourQuotePage.verifyQuotePage()) {
                Log.fail("Quote Number and Premium details are missing", _extentedReport);
				return;
            }
            
            if (_dataObject.policytype.equals("5star"))
            	yourQuotePage.upgradePolicy();

            if (!_dataObject.covertype.equalsIgnoreCase("Contents") && !_dataObject.buildingExcess.equals("£"))
            	yourQuotePage.SelectBuidingExcess(_dataObject.buildingExcess);

            if (!_dataObject.covertype.equalsIgnoreCase("Buildings") && !_dataObject.contentExcess.equals("£"))
            	yourQuotePage.SelectContentExcess(_dataObject.contentExcess);

            if (!_dataObject.buildingsad.equals(""))
            	yourQuotePage.SelectBuildingsAD(_dataObject.buildingsad);

            if (!_dataObject.contentsad.equals(""))
            	yourQuotePage.SelectContentsAD(_dataObject.contentsad);

            if (!_dataObject.gardencover.equals("£"))
            	yourQuotePage.SelectGarden(_dataObject.gardencover);

            if (!_dataObject.techandentertainment.equals("£"))
            	yourQuotePage.SelectTech(_dataObject.techandentertainment);

            if (!_dataObject.bicyclecover.equals(""))
            	yourQuotePage.SelectBicycle(_dataObject.bicyclecover);

            if (!_dataObject.unspecPI.equals("£"))
            	yourQuotePage.SelectPI(_dataObject.unspecPI);

            if (!_dataObject.hecover.equals(""))
            	yourQuotePage.SelectHEcover(_dataObject.hecover);

            if (!_dataObject.lecover.equals(""))
            	yourQuotePage.SelectLEcover(_dataObject.lecover); 
            
        }// try
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }// catch
        finally {                        
        }// finally
    }//EnterCoverDetails

    public void SubmitCover() throws Exception {
    	
    	try {
            summaryPage = yourQuotePage.gotoSummaryPage(_extentedReport, true);
            Log.message("Navigating to Summary Page", _driver, _extentedReport);            
            
        }// try
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }// catch
        finally {                        
        }// finally
    }//SubmitQuote

    public void EnterUserInfoSaveQuote() throws Exception {
    	
    	try {
            //Set User mail info
    		userRegPage.enterEmail(_dataObject.email);
    		userRegPage.enterConfirmEmail(_dataObject.email); 		
            
    		Log.message("Entered User info for Save Quote", _driver, _extentedReport);            
            
        }// try
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }// catch
        finally {                        
        }// finally
    }//EnterUserInfoSaveQuote

    public void SubmitUserInfoSaveQuote() throws Exception {
    	
    	try {
            // Navigate to Confirmation Page
            if (_dataObject.email.contains("existinguser")) {
            	signInPage = userRegPage.gotoSignInPage(true);
            	signInPage.enterPassword(_dataObject.password);
            	yourQuotePage = signInPage.saveQuote(_extentedReport, true);            	
            }
            else
            	yourQuotePage = userRegPage.saveQuote();

    		Log.message("Clicked on Save Quote from User Reg page", _driver, _extentedReport);            
            
        }// try
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }// catch
        finally {                        
        }// finally
    }//EnterUserInfoSaveQuote
       
    public void AddSummary() throws Exception {
    	try {
    		//Navigate to Summary Page
            if (summaryPage == null)
            	summaryPage = new SummaryPage(_driver);
            
            summaryPage.verifySummaryPage();
                 
            //Agree Statement and Navigate to Payment Page
            summaryPage.agreeStatement("I agree with the above statement");
            Log.message("Confirm Agreement Statement", _driver, _extentedReport);
            
            paymentPage = summaryPage.gotoPaymentPage(_extentedReport, Boolean.FALSE);
            Log.message("Navigating to Payment Page", _driver, _extentedReport);            
        }// try
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }// catch
        finally {                        
        }// finally
    }//AddSummary
    
    public void MakeAnnualPayment() throws Exception {
    	try {
    		
    		if (paymentPage == null)
                paymentPage = new PaymentPage(_driver);
            
            paymentPage.verifyPaymentPage();
            Log.message("Navigated to Payment Page");

            //Enter Customer and Bank Details
            paymentPage.enterEmail(_dataObject.email);
            Log.message("Entering Email Address:" + _dataObject.email, _driver, _extentedReport);

            paymentPage.enterConfirmEmail(_dataObject.email);
            Log.message("Confirming Email Address: " + _dataObject.email, _driver, _extentedReport);

            paymentPage.enterPhone(_dataObject.phone);
            Log.message("Entering Phone number :" + _dataObject.phone, _driver, _extentedReport);

            paymentPage.selectPaymentMethod(_dataObject.paymentmethod);
            Log.message("Selected Payment Method" + _dataObject.paymentmethod, _driver, _extentedReport);

            paymentPage.selectBillingAddress(_dataObject.billingaddress);
            Log.message("Change Billing Address : " + _dataObject.billingaddress, _driver, _extentedReport);

            // Navigate to Confirmation Page
            // hanldled for Email format .....@hguat.com => Ram.sharma@hguat.com
            if (_dataObject.email.contains("@hguat.com")) {
            	signInPage = paymentPage.gotoSignInPage(_extentedReport, Boolean.TRUE);
            	signInPage.enterPassword(_dataObject.password);
            	sagepadetailspage = signInPage.makeAnnualPayment(_extentedReport, true);            	
            }
            else
            	sagepadetailspage = paymentPage.clickconfirm(_extentedReport, true);
            
            //Navigate to SagePay Page
            sagepadetailspage.clickOnSelectedCard();
            Log.message("Navigating to Sage Pay Page", _driver, _extentedReport);

            //sagepadetailspage.verifySagePayPage();
            
            //Navigate to SagePay Confirmation Page
            sagepadetailspage.enterCardNumber(_dataObject.cardNumber);
            Log.message("Entering Card Number : " + _dataObject.cardNumber, _driver, _extentedReport);

            Calendar now = Calendar.getInstance();
            String year = Integer.toString(now.get(Calendar.YEAR)+1).substring(2);
            String month = Integer.toString(now.get(Calendar.MONTH)+1);
            
            sagepadetailspage.selectExpiryMonth(month);
            Log.message("Selecting Expiry Month : " + month, _driver, _extentedReport);

            sagepadetailspage.selectExpiryYear(year);
            Log.message("Selecting Expiry Year : " + year, _driver, _extentedReport);

            sagepadetailspage.enterSecurityCode(_dataObject.securityCode);
            Log.message("Selecting Expiry Year : 234", _driver, _extentedReport);

            sagepayconfirmpage = sagepadetailspage.gotoSagePayConfirmPage(_extentedReport, true);
            Log.message("Navigate to SagePay Confirmation Page", _driver, _extentedReport);

            sagepayconfirmpage.verifySagePayConfirmPage();
            
            //Navigate to Thank You Page
            thankyoupage = sagepayconfirmpage.clickonPaymentConfirmation(true);
            Log.message("Navigating to Thank You Page", _driver, _extentedReport);

        }// try
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }// catch
        finally {                      
        }// finally
        	
    }//MakeAnnualPayment 

    public void MakeMonthlyPayment() throws Exception {
    	try {
    		if (paymentPage == null)
                paymentPage = new PaymentPage(_driver);
            
            paymentPage.verifyPaymentPage();
            Log.message("Navigated to Payment Page");

            //Enter Customer and Bank Details
            paymentPage.enterEmail(_dataObject.email);
            Log.message("Entering Email Address:" + _dataObject.email, _driver, _extentedReport);

            paymentPage.enterConfirmEmail(_dataObject.email);
            Log.message("Confirming Email Address: " + _dataObject.email, _driver, _extentedReport);

            paymentPage.enterPhone(_dataObject.phone);
            Log.message("Entering Phone number :" + _dataObject.phone, _driver, _extentedReport);

            paymentPage.selectPaymentMethod(_dataObject.paymentmethod);
            Log.message("Selected Payment Method" + _dataObject.paymentmethod, _driver, _extentedReport);

            paymentPage.selectInstallment(_dataObject.installment);
            Log.message("Installment Option ?" + _dataObject.installment, _driver, _extentedReport);

            paymentPage.enterActHolderName(_dataObject.firstname);
            Log.message("Account Holder Name :" + _dataObject.firstname, _driver, _extentedReport);

            paymentPage.enterSortCode(_dataObject.sortcode);
            Log.message("SortCode :" + _dataObject.sortcode, _driver, _extentedReport);

            paymentPage.enterBankAccount(_dataObject.bankaccount);
            Log.message("Account Number :" + _dataObject.bankaccount, _driver, _extentedReport);

            paymentPage.selectUKResident(_dataObject.ukresident);
            Log.message("UK Resident ?" + _dataObject.ukresident, _driver, _extentedReport);

            paymentPage.selectBankrupcy(_dataObject.bankrupcy);
            Log.message("Any Bankrupcy?" + _dataObject.bankrupcy, _driver, _extentedReport);

            // Confirm Agreement Statement
            paymentPage.agreeStatement("I agree with the statement above");
            
            // Navigate to Confirmation Page
            if (_dataObject.email.contains("existinguser")) {
            	signInPage = paymentPage.gotoSignInPage(_extentedReport, Boolean.TRUE);
            	signInPage.enterPassword(_dataObject.password);
            	confirmationpage = signInPage.gotoConfirmationPage(_extentedReport, true);
            }
            else
            	confirmationpage = paymentPage.gotoUserRegPage(_extentedReport, Boolean.TRUE);
        }// try
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }// catch
        finally {                        
        }// finally

    }//MakeMonthlyPayment

    public void confirmMonthlyQuote() throws Exception {
    	try {

            //Initialize to Confirmation Page
    		if (confirmationpage == null)
                confirmationpage = new ConfirmationPage(_driver);

            confirmationpage.verifyConfirmationPage();
            
            // Agreement Statement and Navigate to Thank You Page
            confirmationpage.confirmStatement("I confirm");
            Log.message("Clicked on Confirm Statement", _driver, _extentedReport);

            thankyoupage = confirmationpage.confirmPayment(_extentedReport, true);
            Log.message("Clicked on Get Covered button", _driver, _extentedReport);

        }// try
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }// catch
        finally {                  
        }// finally
        	
    }// confirmMonthlyQuote 

    public String VerifyQuoteSaved() throws Exception {
    	try {
    		//Initialize Thank You Page
    		yourQuotePage.verifyQuotePage();
    		yourQuotePage.quoteNumber = yourQuotePage.VerifyQuoteSaved();
            Log.pass(" Saved Quote Successfully, " + yourQuotePage.quoteNumber, _driver, _extentedReport, true);
            return yourQuotePage.quoteNumber;
        }// try
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }// catch
        finally {                  
        }// finally
        	
    }// VerifyQuoteCreated 
    public String VerifyQuoteCreated() throws Exception {
    	try {
    		//Initialize Thank You Page
    		if (thankyoupage == null)
                thankyoupage = new ThankYouPage(_driver);
            
            thankyoupage.verifyThankYouPage();
            quoteNumber = thankyoupage.GetQuoteNum();
            Log.pass("Created Quote Successfully, " + quoteNumber, _driver, _extentedReport, true);
            return quoteNumber.split("-")[2];
        }// try
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }// catch
        finally {                  
        }// finally
        	
    }// VerifyQuoteCreated 
}