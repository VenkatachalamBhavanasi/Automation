package com.fedex.ci.sabt.model;

import java.util.HashMap;

public class RowData {
	private HashMap<String, String> rowData = null;
	private String sheetName = null;
	
	public RowData() {
		rowData = new HashMap<String, String>();
	}
	
	public void setRowData(HashMap<String, String> rowData) {
		this.rowData = rowData;
	}
	public HashMap<String, String> getRowData() {
		return rowData;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getSheetName() {
		return sheetName;
	}
}
