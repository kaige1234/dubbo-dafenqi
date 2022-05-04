package com.opendata.edb.base;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 校验excel表格样式单元格
 * 
 * @author 刘丰
 * 
 */
public class StyleCellGrid implements Grid
{
	private int rowNumber;// 行号
	private int columnNumber;// 列号
	private String title;// 标题内容
	private boolean isValid=true;//是否审核，默认为真

	private transient CellStyle cellStyle;

	public StyleCellGrid(int rowNumber, int columnNumber, String title,CellStyle cellStyle)
	{
		super();
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
		this.title = title;
		this.cellStyle=cellStyle;
	}
	public StyleCellGrid()
	{
		
	}
	public int getRowNumber()
	{
		return rowNumber;
	}

	public void setRowNumber(int rowNumber)
	{
		this.rowNumber = rowNumber;
	}

	public int getColumnNumber()
	{
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber)
	{
		this.columnNumber = columnNumber;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
	public boolean isValid()
	{
		return isValid;
	}
	public void setValid(boolean isValid)
	{
		this.isValid = isValid;
	}
	
	public CellStyle getCellStyle() {
		return cellStyle;
	}
	
	public void setCellStyle(CellStyle cellFormat) {
		this.cellStyle = cellFormat;
	}
}
