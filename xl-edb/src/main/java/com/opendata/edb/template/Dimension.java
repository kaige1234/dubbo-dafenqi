package com.opendata.edb.template;

import org.apache.poi.ss.usermodel.CellStyle;

import com.opendata.edb.base.Grid;

/**
 * 维度定义
 * @author 刘丰
 *
 */
public class Dimension implements Grid{

	private String title;// 标题
	private String name;// 代码
	private String data;// 数据
	private int rowNumber;//行号
	private int columnNumber;//列号
	private boolean isDataCellGrid=false;//是否为数据单元格
	private transient CellStyle cellStyle;//单元格格式
	

	public Dimension() {

	}

	public Dimension(String title, String name, String data,int rowNumber,int columnNumber) {
		super();
		this.title = title;
		this.name = name;
		this.data = data;
		this.rowNumber=rowNumber;
		this.columnNumber=columnNumber;
	}
	
	public Dimension(String title, String name, String data,int rowNumber,int columnNumber,boolean isDataCellGrid) {
		super();
		this.title = title;
		this.name = name;
		this.data = data;
		this.rowNumber=rowNumber;
		this.columnNumber=columnNumber;
		this.isDataCellGrid=isDataCellGrid;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}

	public boolean isDataCellGrid() {
		return isDataCellGrid;
	}

	public void setDataCellGrid(boolean isDataCellGrid) {
		this.isDataCellGrid = isDataCellGrid;
	}
	
	public CellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(CellStyle cellFormat) {
		this.cellStyle = cellFormat;
	}
	
}
