package com.opendata.edb.base;

import org.apache.poi.ss.usermodel.CellStyle;

import java.util.List;
/**
 * 表单属性数据项
 * @author 刘丰 
 *
 */
public class DataCellGrid implements DataGrid
{

	private String name;//名称
	private String title;//标题
	private int columnNumber;//列号
	private int rowNumber;//行号
	private String cellDataType=DataType.Number;//数据类型
	private int dataLength=100;//长度
	private boolean dataIsNull=true;//是否为空
	private String dataGridModel=DataType.FormDataGridModel;//单元格数据模式
	private int sumRow=1;//总行数,仅当模型为fixlist 固定列表时，则需要固定的行数
	private List<String> dimensions;//维度信息
	private String format;
	
	private transient CellStyle cellStyle;

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public CellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public List<String> getDimensions() {
		return dimensions;
	}

	public void setDimensions(List<String> dimensions) {
		this.dimensions = dimensions;
	}

	public String getDataGridModel()
	{
		return dataGridModel;
	}

	public void setDataGridModel(String dataGridModel)
	{
		this.dataGridModel = dataGridModel;
	}

	public DataCellGrid(String name, int columnNumber, int rowNumber,String dataType)
	{
		super();
		this.name = name;
		this.columnNumber = columnNumber;
		this.rowNumber = rowNumber;
		this.cellDataType=dataType;
	}

	public DataCellGrid()
	{
		super();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getColumnNumber()
	{
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber)
	{
		this.columnNumber = columnNumber;
	}

	public int getRowNumber()
	{
		return rowNumber;
	}

	public void setRowNumber(int rowNumber)
	{
		this.rowNumber = rowNumber;
	}

	public String getCellDataType()
	{
		return cellDataType;
	}

	public void setCellDataType(String dataType)
	{
		this.cellDataType = dataType;
	}

	public int getDataLength()
	{
		return dataLength;
	}

	public void setDataLength(int dataLength)
	{
		this.dataLength = dataLength;
	}

	public void setDataIsNull(boolean dataIsNull)
	{
		this.dataIsNull = dataIsNull;
	}

	public boolean getDataIsNull()
	{
		return this.dataIsNull;
	}

	public int getSumRow()
	{
		return sumRow;
	}

	public void setSumRow(int sumRow)
	{
		this.sumRow = sumRow;
	}

}
