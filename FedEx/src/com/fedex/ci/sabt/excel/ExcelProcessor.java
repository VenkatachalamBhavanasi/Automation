package com.fedex.ci.sabt.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.fedex.ci.sabt.model.RowData;
import com.fedex.ci.sabt.util.TestConstants;
import com.fedex.framework.logging.FedExLogger;
import com.fedex.framework.logging.FedExLoggerInterface;

public class ExcelProcessor {

	private static final FedExLoggerInterface logger = FedExLogger.getLogger(ExcelProcessor.class);
	
	public static List<RowData> read(String sheetName) {
		logger.debug("opening the sheet, testCase " + sheetName);
		
		List<RowData> rowList = new ArrayList<RowData>();
		
		try {
			Workbook wb = openWorkbook(TestConstants.SABT_TEST_DATA_FILE_NAME);

			logger.debug("wb == null " + wb == null);

			Sheet sheet = wb.getSheet(sheetName);

			int lastRow = sheet.getLastRowNum() + 1;
			Row row = null;
			String tmp = null;

			// first row is keys
			row = sheet.getRow(0);
			Vector<String> headers = new Vector<String>();

			for (int j=0; j<row.getLastCellNum(); j++ ) {
				headers.add(getCellValue(row.getCell(j)));
				//logger.debug("putting the header values " + j + "," + getCellValue(row.getCell(j)));
			}

			RowData rowData = null;
			String rowVal = "";
			// next rows are data
			for (int i=1; i<lastRow; i++ ) {
				row = sheet.getRow(i);

				if ( row == null ) {
					logger.error("row is null at " + i + ", continueing ...");
					continue;
				}
				
				rowData = new RowData();
				for (int j=0; j<row.getLastCellNum(); j++ ) {
					rowVal = getCellValue(row.getCell(j));
					
					if ( rowVal == null || rowVal.length() <= 0 )
						continue;
					
					tmp = headers.get(j);
					rowData.getRowData().put(tmp, getCellValue(row.getCell(j)));
					//logger.debug("putting the row data " + tmp + "," + getCellValue(row.getCell(j)));
				}
				rowList.add(rowData);
			}

		} catch (Exception e) {
			logger.error("unable to read the excel file ", e);
		}

		return rowList;
	}
	
	private static String getCellValue(Cell cell) {
		if ( cell == null ) {
			return "";
		}
		
		String cellVal = null;
		
		switch (cell.getCellType()) {
    	case Cell.CELL_TYPE_STRING:
    		cellVal = cell.getRichStringCellValue().getString();
    		break;
    	case Cell.CELL_TYPE_NUMERIC:
    		cellVal = String.format("%.0f", cell.getNumericCellValue());
    		break;
    	case Cell.CELL_TYPE_BOOLEAN:
    		cellVal = cell.getBooleanCellValue() + "";
    		break;
    	case Cell.CELL_TYPE_FORMULA:
    		cellVal = cell.getCellFormula();
    		break;
    	case Cell.CELL_TYPE_BLANK:
    		cellVal = "";
    		break;
    	case Cell.CELL_TYPE_ERROR:
    		logger.error("cell has an error cellType " + cell.getStringCellValue());
    		cellVal = cell.getStringCellValue();
    		break;
    	default:
    		logger.error("cell has an invalid cellType " + cell.getCellType());
		}
		
		return cellVal;
	}
	
	private static Workbook openWorkbook(String file) throws FileNotFoundException, IOException, InvalidFormatException {
		FileInputStream fis = null;

		try {
			logger.debug("Opening workbook [" + file + "]");

			fis = new FileInputStream(file);

			return WorkbookFactory.create(fis);

		} finally {
			if(fis != null) {
				fis.close();
			}
		}
	}
}
