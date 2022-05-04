package com.opendata.edb.template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.opendata.edb.base.DataCellGrid;
import com.opendata.edb.base.StyleCellGrid;

/**
 * 存储一行单元格信息
 * @author 刘丰
 * 
 */
public class RowGrid implements Serializable
{

	private int rowNumber;// 行号
	private List<DataCellGrid> dataCellGridList = new ArrayList<DataCellGrid>();// 当前行数据单元格
	private List<StyleCellGrid> styleCellGridList = new ArrayList<StyleCellGrid>();// 当前行样式单元格

	public RowGrid(int rowNumber)
	{
		this.rowNumber=rowNumber;
	}
	public int getRowNumber()
	{
		return rowNumber;
	}

	public void setRowNumber(int rowNumber)
	{
		this.rowNumber = rowNumber;
	}

	public List<DataCellGrid> getDataCellGridList()
	{
		return dataCellGridList;
	}

	public void setDataCellGridList(List<DataCellGrid> dataCellGridList)
	{
		this.dataCellGridList = dataCellGridList;
	}

	public List<StyleCellGrid> getStyleCellGridList()
	{
		return styleCellGridList;
	}

	public void setStyleCellGridList(List<StyleCellGrid> styleCellGridList)
	{
		this.styleCellGridList = styleCellGridList;
	}

	public void putDataCellGrid(DataCellGrid dataCellGrid)
	{
		if (dataCellGrid != null)
		{
			this.dataCellGridList.add(dataCellGrid);
		}
	}

	public void putStyleCellGrid(StyleCellGrid styleCellGrid)
	{
		if (styleCellGrid != null)
		{
			this.styleCellGridList.add(styleCellGrid);
		}
	}

	/**
	 * 当前行数据单元格是否为空
	 * 
	 * @return
	 */
	public boolean isEmptyDataCellGrid()
	{
		if (this.dataCellGridList == null || this.dataCellGridList.isEmpty())
		{
			return true;
		}
		return false;
	}

	/**
	 * 当前行样式单元格是否为空
	 * 
	 * @return
	 */
	public boolean isEmptyStyleCellGrid()
	{
		if (this.styleCellGridList == null || this.styleCellGridList.isEmpty())
		{
			return true;
		}
		return false;
	}
}
