package com.ssp.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import org.testng.annotations.Listeners;

import com.ssp.support.BaseTest;
import com.ssp.support.DataUtils;
import com.ssp.support.EmailReport;
import com.ssp.support.Utils;


@Listeners(EmailReport.class)
public class WebjourneyDataDriver extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(DataUtils.class);
	
    public WebjourneyDataDriver() throws Exception {
    	try {    		            
    	}
    	catch (Exception e) {
             throw new Exception(e.getMessage());
        }// catch        
    }
    
    public String webSite;
	public String description;
	public String postcode;
	public String noofbedroom;
	public String yearofbuild;
	public String propertytype;
	public String title;
    public String firstname;
    public String covertype;
    public String lastname;
    public String jointpolicy;
    public ArrayList<HashMap <String, String>> jointpolicydetails = new ArrayList<HashMap <String, String>>();
    public String claimsmade;
    public ArrayList<HashMap <String, String>> claimsMadeDetails = new ArrayList<HashMap <String, String>>();
    public String personalitems;
    public String bicycle;
    public String highrisk;
    public String havepromocode;
    public String promocode;
    public String staffcode;
    public String dob;
    public String buildinginsurance;
    public String contentsinsurance;
    public int noofPI;
    public ArrayList<HashMap <String, String>> personalitemslist = new ArrayList<HashMap <String, String>>(); 
    public int noofHR;
    public ArrayList<HashMap <String, String>> highriskitemslist = new ArrayList<HashMap <String, String>>(); 
    public int noofbicycle;
    public ArrayList<HashMap <String, String>> bicycleslist = new ArrayList<HashMap <String, String>>(); 
    public String policytype;
    public String buildingExcess;
    public String contentExcess;
    public String gardencover;
    public String bicyclecover;
    public String techandentertainment;
    public String unspecPI;
    public String buildingsad;
    public String contentsad;
    public String hecover;
    public String lecover;
    public String paymentmethod;
    public String installment;
    public String email;
    public String password;
    public String confirmemail;
    public String phone;
    public String sortcode;
    public String bankaccount;
    public String ukresident;
    public String bankrupcy;
    public String test;
    public String test_type;
    public String browserwithos = null;
    public String browsername;
    public String os;
    public String device;
    public String osversion;
    public String browserversion;
    public String billingaddress;
    public String cardNumber;
    public String securityCode;
    
    public void getTestData(String methodName, String className) throws Exception {
    	
    	try {
	    	HashMap<String, String> testData = DataUtils.testDatabyID(methodName, className);
	    	description = testData.get("Description").toString();
	        postcode = testData.get("PostCode").toString();
	        noofbedroom = testData.get("No of Bedroom").toString();
	        yearofbuild = testData.get("Year of Build").toString();
	        propertytype = testData.get("Type of Property").toString();
	        title = testData.get("Title").toString();
	        dob = testData.get("DOB(dd/mm/yyyy)").toString();
	        contentsinsurance = testData.get("Contents Insurance Held").toString();
	        buildinginsurance = testData.get("Building Insurance Held").toString();	       
	        covertype = testData.get("Cover Type").toString();
	        //Set Joint Policy Data
	        jointpolicy = testData.get("Joint Policy").toString();
	        if (jointpolicy.equalsIgnoreCase("yes")) 
	        	jointpolicydetails = getList(testData.get("Joint Policy holder details"));
	        //Set Data for Claims Made if any
	        claimsmade = testData.get("Claims Made").toString();
	        if (claimsmade.equalsIgnoreCase("yes")) 
	        	claimsMadeDetails = getList(testData.get("Claim Details"));
	        //Set PI data
	        personalitems = testData.get("Personal Items").toString();
	        noofPI = getSize(testData.get("Specified Personal Items"));
	        if (noofPI > 0) 
	        	personalitemslist = getList(testData.get("Specified Personal Items"));	        	
	        //Set Bicycle data
	        bicycle = testData.get("Bicycle").toString();
	        noofbicycle = getSize(testData.get("Specified Bicycle"));
	        if (noofbicycle > 0) 
	        	bicycleslist = getList(testData.get("Specified Bicycle"));
	        //Set High risk data
	        highrisk = testData.get("High Risk").toString();
	        noofHR = getSize(testData.get("High Risk Items"));
	        if (noofHR > 0) 
	        	highriskitemslist = getList(testData.get("High Risk Items"));
	        //Set Promo code
	        havepromocode = testData.get("Have Promo Code").toString();
	        promocode = testData.get("Promo Code").toString();
	        if (promocode.split("\n").length > 1) {
	        	staffcode = promocode.split("\n")[1];
	        	promocode = promocode.split("\n")[0];
	        }
	        //Set Cover Data Info
	        buildingExcess = '£' + testData.get("BuildingsExcess").toString();
	        contentExcess = '£' + testData.get("ContentsExcess").toString();
	        gardencover = '£' + testData.get("Garden").toString();
	        bicyclecover = testData.get("Bicycles").toString();
	        techandentertainment = '£' + testData.get("Tech").toString();
	        unspecPI = '£' + testData.get("Unspecified PersonalItems").toString();
	        buildingsad = testData.get("BuildingsAD").toString();
	        contentsad = testData.get("ContentsAD").toString();
	        hecover = testData.get("Hecover").toString();
	        lecover = testData.get("Lecover").toString();
	        paymentmethod = testData.get("Payment Method").toString();
	        policytype = testData.get("PolicyType").toString();
	        installment = testData.get("Installment").toString();
	        phone = testData.get("Phone Number").toString();
	        sortcode = testData.get("Sort Code").toString();
	        bankaccount = testData.get("Bank Account").toString();
	        ukresident = testData.get("UK Resident").toString();
	        bankrupcy = testData.get("Bankrupcy").toString();
	        billingaddress = testData.get("Billing Address").toString();
	        cardNumber = testData.get("Card Number").toString();
	        securityCode = testData.get("Security Code").toString();
	        password = testData.get("Password").toString();
	        
	        if (testData.get("FirstName").toString().equals(""))
	        	firstname = Utils.getRandomCharacters("alpha", 8);
	        else
	        	firstname = testData.get("FirstName").toString();

	        if (testData.get("LastName").toString().equals(""))
	        	lastname = "TestAutomation";
	        else
	        	lastname = testData.get("LastName").toString();

	        if (testData.get("Email").toString().equals(""))
	        	email = Utils.getRandomCharacters("alphanumeric", 10) + "@mail.com";
	        else
	        	email = testData.get("Email").toString();

	        webSite = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("webSite");
	        test = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getName();
	
	        browserwithos = null;
	        test_type = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("type");
	        if ("device".equalsIgnoreCase(test_type)) {
	            browsername = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
	            os = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("os").substring(0, 1);
	            device = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("device");
	            browserwithos = os + "_" + browsername + "_" + device;
	        } else {
	            browsername = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
	            browserversion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser_version");
	            os = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("os").substring(0, 1);
	            osversion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("os_version");
	            browserwithos = os + osversion + "_" + browsername + browserversion;		     
	        }
        
        }// try
        catch(Exception e) {
        	logger.error(e.getMessage(), e);
        }// catch
        
    }// getTestData
    
    private ArrayList<HashMap <String, String>> getList(String data) throws Exception {
    	ArrayList<HashMap <String, String>> list = new ArrayList<HashMap <String, String>>();
    	try {
    		int itemidx = 0;
	    	String[] rows = data.split("\n");
	    	
	    	while (itemidx < rows.length) {
	    		HashMap <String, String> row = new HashMap <String, String>();
	    		String[] fields = rows[itemidx].split(Pattern.quote("|"));
	    		int fieldidx=0;
	    		
	    		while (fieldidx < fields.length) {
	    			String[] field = fields[fieldidx].split(":");
	    			row.put(field[0], field[1]);	
	    			fieldidx++;
	    		}
	    		list.add(row);
	    		itemidx++;	    	
	    	}
	    		
    	}
    	catch(Exception e) {
        	logger.error(e.getMessage(), e);
        }// catch	    
	    return list;	    
    }
    

    private int getSize(String data) {
    	return data == ""? 0 : data.split("\n").length;
    }   
 
}