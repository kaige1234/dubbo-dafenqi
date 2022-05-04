package com.opendata.edb.base;

import java.io.Serializable;

import org.apache.poi.ss.usermodel.CellStyle;


/**
 * 单元格
 * @author 刘丰
 *
 */
public interface Grid extends Serializable
{
	/**
	 * 获得列编号
	 * @return
	 */
	public int getColumnNumber();
	
	
	/**
	 * 设置列编号
	 * @param ColumnNumber
	 */
	public void setColumnNumber(int ColumnNumber);
	
	/**
	 * 获得行编号 
	 * @return
	 */
	public int getRowNumber();
	
	/**
	 * 设置行编号 
	 * @param rowNumber
	 */
	public void setRowNumber(int rowNumber);
	
	/**
	 * 设置单元格格式
	 */
	public void setCellStyle(CellStyle cellStyle);
	
	/**
	 * 获得单元格格式
	 */
	public CellStyle getCellStyle();
	
}
