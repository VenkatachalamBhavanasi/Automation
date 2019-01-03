package com.ssp.support;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class DataProviderUtils {
	@DataProvider(parallel = true)
	public static Iterator <Object[]> ssBVTDataProvider(ITestContext context) throws IOException {

		List <Object[]> dataToBeReturned = new ArrayList <Object[]>();
		List <String> browsers = Arrays.asList(context.getCurrentXmlTest().getParameter("browserName").split(","));

		for (String browser : browsers) {
			dataToBeReturned.add(new Object[] { browser });
		}

		return dataToBeReturned.iterator();
	}
}