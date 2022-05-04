package com.opendata.edb.template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.opendata.edb.base.DataCellGrid;
import com.opendata.edb.base.DataType;

/**
 * 数据结点
 * 
 * @author 刘丰
 * 
 */
public class GridBlevel implements Serializable
{

	/**
	 * 层次号，从0开始 excel文件中的报表格式，以表单形与列表形并存，但是分隔开的。
	 * 一个sheet中的模板信息，可以一段（某几行的信息）为表单形式，一段（某几行的信息为列表形式) 整个excel将分为
	 * 表单、列表、表单、列表、列表等规格存储于excel文件中，从0行0列开始读取，当读取到下一种类型时，则上一段则为某一类
	 * 
	 */
	private int level = 1;

	/**
	 * 数据的模板 当为表单时，则为form 当为列表时，则为list
	 */
	private String dataGridModel = DataType.FormDataGridModel;

	/**
	 * 行集单元格
	 */
	private List<RowGrid> rowGridList = new ArrayList<RowGrid>();

	/**
	 * 当前节点开始行号
	 */
	private int beginRow;

	/**
	 * 当前节点结束行号
	 */
	private int endRow;

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public String getDataGridModel()
	{
		return dataGridModel;
	}

	public void setDataGridModel(String dataGridModel)
	{
		this.dataGridModel = dataGridModel;
	}

	public int getBeginRow()
	{
		return beginRow;
	}

	public void setBeginRow(int beginRow)
	{
		this.beginRow = beginRow;
	}

	public int getEndRow()
	{
		return endRow;
	}

	public void setEndRow(int endRow)
	{
		this.endRow = endRow;
	}

	public List<RowGrid> getRowGridList()
	{
		return rowGridList;
	}

	public void setRowGridList(List<RowGrid> rowGridList)
	{
		this.rowGridList = rowGridList;
	}

	/**
	 * 新增一行
	 * 
	 * @param rowGrid
	 */
	public void putRowGrid(RowGrid rowGrid)
	{
		if (!rowGrid.isEmptyDataCellGrid() || !rowGrid.isEmptyStyleCellGrid())
		{
			this.rowGridList.add(rowGrid);
		}
	}

	/**
	 * 当前层级如果是固定列表时，则通过此方法可获得固定列表的总行数
	 * 
	 * @return
	 */
	public int getFixListSumRow()
	{
		if (this.getDataGridModel().equals(DataType.FixListDataGridModel))
		{
			for (RowGrid rowGrid : rowGridList)
			{
				for (DataCellGrid cellGrid : rowGrid.getDataCellGridList())
				{
					if (cellGrid.getDataGridModel().equals(DataType.FixListDataGridModel))
					{
						return cellGrid.getSumRow();
					}
				}
			}
		}
		return -1;
	}

	/**
	 * 获得当前层级中固定列表的所有列
	 * 
	 * @return
	 */
	public List<DataCellGrid> getFixListCellGrid()
	{

		if (this.getDataGridModel().equals(DataType.FixListDataGridModel))
		{
			List<DataCellGrid> fixList = new ArrayList<DataCellGrid>();
			for (RowGrid rowGrid : rowGridList)
			{
				for (DataCellGrid cellGrid : rowGrid.getDataCellGridList())
				{
					if (cellGrid.getDataGridModel().equals(DataType.FixListDataGridModel))
					{
						fixList.add(cellGrid);
					}
				}
			}
			return fixList;
		}
		return null;
	}
	
	/**
	 * 获得总行数
	 * @return
	 */
	public int getSumRow()
	{
		return this.endRow-this.beginRow+1;
	}

}
