package com.ssp.support;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

//import javax.swing.text.html.parser.Element;
import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.xmlbeans.XmlException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStepResult;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.support.SoapUIException;
import com.eviware.soapui.support.types.StringToObjectMap;
import com.relevantcodes.extentreports.ExtentTest;

/* import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStepResult;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.support.SoapUIException;
import com.eviware.soapui.support.types.StringToObjectMap;
import com.relevantcodes.extentreports.ExtentTest;
import com.ssp.support.Log; */

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* *
 * Soap UI */

public class SOAP_UI_Utils {


    /** Create Person through SOAP call
     * 
     * @param projectFilePath
     *            : Path where you placed the SOAP Project
     * @param testSuiteName
     *            : Suite Name
     * @param testCaseName
     *            : Test Case Name which need to be execute
     * @param testStepName
     *            : Test Step Name
     * @param foreName
     *            : First name of the person
     * @param surName
     *            : Sur name of the person
     * @throws XmlException
     * @throws SAXException
     * @throws Exception
     *             : Throws SOAP exception
    */
    public void createPersonThroughSOAPCall(String projectFilePath, String testSuiteName, String testCaseName, String testStepName, String foreName, String surName) throws Exception, IOException, SoapUIException, XmlException, SAXException {
        WsdlProject wsdlProject = new WsdlProject(projectFilePath);
        TestSuite testSuite = wsdlProject.getTestSuiteByName(testSuiteName);
        TestCase testCase = testSuite.getTestCaseByName(testCaseName);
        testCase.setPropertyValue("forename", foreName);
        testCase.setPropertyValue("surname", surName);
        TestStep testStep = testCase.getTestStepByName(testStepName);

        WsdlTestCaseRunner testRunner = new com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner((WsdlTestCase) testCase, null);
        String response = runStepAndGetResponseContent(testRunner, testStep);
        DocumentBuilder builder;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(response));
            builder.parse(src);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String createPersonThruSOAPCall(String projectFilePath, String testSuiteName, String testCaseName, String testStepName, String foreName, String surName, String soapenv, ExtentTest extentedReport) throws Exception {
        String urn = null;
        WsdlProject wsdlProject = new WsdlProject(projectFilePath);
        wsdlProject.setPropertyValue("env", soapenv);
        TestSuite testSuite = wsdlProject.getTestSuiteByName(testSuiteName);
        TestCase testCase = testSuite.getTestCaseByName(testCaseName);
        testCase.setPropertyValue("forename", foreName);
        testCase.setPropertyValue("surname", surName);
        TestStep testStep = testCase.getTestStepByName(testStepName);
        try {
            WsdlTestCaseRunner testRunner = new com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner((WsdlTestCase) testCase, new StringToObjectMap());
            String response = runStepAndGetResponseContent(testRunner, testStep);
            DocumentBuilder builder;

            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(response));
            Document doc = builder.parse(src);
            urn = doc.getElementsByTagName("v1_1:personIdentity").item(0).getTextContent();

        } catch (ParserConfigurationException e) {
            Log.fail("Soap UI Call failed", extentedReport);
        }
        wsdlProject.release();
        return urn;

    }// createPersonThroughSOAPCall

    public String retrievePersonThroughSOAPCall(String projectFilePath, String testSuiteName, String testCaseName, String testStepName, String personkey, ExtentTest extentedReport) throws Exception, IOException, SoapUIException, XmlException, SAXException {
        String lastname = null;
        WsdlProject wsdlProject = new WsdlProject(projectFilePath);
        TestSuite testSuite = wsdlProject.getTestSuiteByName(testSuiteName);
        TestCase testCase = testSuite.getTestCaseByName(testCaseName);
        testCase.setPropertyValue("personKey", personkey);
        TestStep testStep = testCase.getTestStepByName(testStepName);
        try {
            WsdlTestCaseRunner testRunner = new com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner((WsdlTestCase) testCase, new StringToObjectMap());
            String response = runStepAndGetResponseContent(testRunner, testStep);
            DocumentBuilder builder;
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(response));
            Document doc = builder.parse(src);
            lastname = doc.getElementsByTagName("v1_1:surname").item(0).getTextContent();
        } catch (ParserConfigurationException e) {
            Log.fail("Retrieving person details failed", extentedReport);
        }
        wsdlProject.release();
        return lastname;

    }// createPersonThroughSOAPCall

    public String runStepAndGetResponseContent(WsdlTestCaseRunner runner, TestStep testStep) throws Exception, IOException, SoapUIException, XmlException {
        try {
            String val = "Failed";
            TestStepResult result = runner.runTestStep(testStep);
            if (result instanceof WsdlTestRequestStepResult) {
                val = ((WsdlTestRequestStepResult) result).getResponse().getContentAsString();
            }
            return val;
        }

        catch (Exception e) {
            throw new Exception("Failed to run the request, exception has occured: " + e.getMessage());
        }
    }

    @SuppressWarnings("finally")
    public boolean createNBthroughIQH(String projectFilePath, String testSuiteName, String testCaseName, String testStepName, String calculatedDate, String soapenv, ExtentTest extentedReport) throws Exception {

        boolean result = false;
        WsdlProject wsdlProject = new WsdlProject(projectFilePath);
        TestSuite testSuite = wsdlProject.getTestSuiteByName(testSuiteName);
        TestCase testCase = testSuite.getTestCaseByName(testCaseName);
        testSuite.setPropertyValue("env", soapenv);
        testCase.setPropertyValue("PolicyDate", calculatedDate);
        TestStep testStep = testCase.getTestStepByName(testStepName);
        try {
            WsdlTestCaseRunner testRunner = new com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner((WsdlTestCase) testCase, new StringToObjectMap());

            String response = runStepAndGetResponseContent(testRunner, testStep);
            System.out.println(response);
            // testRunner.
            // getTestStepByName(testStep).getTestRequest();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(response));
            Document doc = builder.parse(src);

            if (ValidateFaultString(doc)) {

                return false;

            }
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "//GetQuoteResponse";
            NodeList nodeList = null;
            try {
                nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
            } catch (XPathExpressionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            List<String> PremiumChk = Arrays.asList("TotalPremium", "BasePremium", "Premium", "UnspecifiedPremiumExIPT");

            result = verifyAttributeValues(nodeList, PremiumChk, 0);
            wsdlProject.release();
        } catch (ParserConfigurationException e) {
            Log.fail("Soap UI Call failed " + e, extentedReport);
        }
        return result;
    }// createPersonThroughSOAPCall

    private static boolean verifyAttributeValues(NodeList nodeList, List<String> attributes, double valueToBeVerified) {
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            System.out.println(tempNode.getNodeName());
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                if (tempNode.hasAttributes()) {
                    NamedNodeMap map = tempNode.getAttributes();
                    for (int i = 0; i < map.getLength(); i++) {
                        for (String attName : attributes) {
                            System.out.println(map.getNamedItem(attName));
                            // System.out.println(Double.parseDouble(map.getNamedItem(attName).getNodeValue())<valueToBeVerified);
                            if (null != map.getNamedItem(attName) && Double.parseDouble(map.getNamedItem(attName).getNodeValue()) < valueToBeVerified) {
                                return false;
                            }
                        }
                    }
                }
                if (tempNode.hasChildNodes()) {
                    verifyAttributeValues(tempNode.getChildNodes(), attributes, valueToBeVerified);
                }
            }
        }
        return true;
    }

    public boolean ValidateFaultString(Document doc) {
        // return false;

        int FaultStr = doc.getElementsByTagName("faultstring").getLength();
        if (FaultStr > 0) {
            return true;
        } else {
            return false;
        }

    } 
}// SOAP_UI