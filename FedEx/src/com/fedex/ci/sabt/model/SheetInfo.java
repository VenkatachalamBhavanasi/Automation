package com.fedex.ci.sabt.model;

import java.util.HashMap;

public class SheetInfo {

	private HashMap<String, String> sheetData = null;
	private String sheetName = null;
	
	public SheetInfo() {
		sheetData = new HashMap<String, String>();
	}
	
	public void setSheetData(HashMap<String, String> sheetData) {
		this.sheetData = sheetData;
	}
	public HashMap<String, String> getSheetData() {
		return sheetData;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getSheetName() {
		return sheetName;
	}
	
	private String[][] data;
	public String[][] getData() {
		return data;
	}
	public void setData(String[][] data) {
		this.data = data;
	}
	public int getxRows() {
		return xRows;
	}
	public void setxRows(int xRows) {
		this.xRows = xRows;
	}
	public int getxCols() {
		return xCols;
	}
	public void setxCols(int xCols) {
		this.xCols = xCols;
	}
	private int xRows;
	private int xCols;
}
