package com.opendata.edb.base;

import java.util.List;

/**
 * 定义数据单元接口
 * @author 刘丰
 *
 */
public interface  DataGrid extends Grid
{

	/**
	 * 获得数据项的名称
	 * @return
	 */
	public String getName();
	
	/**
	 * 设置数据项的名称
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * 获得标题
	 * @return
	 */
	public String getTitle();
	
	/**
	 * 设置标题
	 * @param title
	 */
	public void setTitle(String title);
	
	
	/**
	 * 获得数据类型
	 * @return
	 */
	public String getCellDataType();
	
	/**
	 * 设置数据类型
	 * @param dataType
	 */
	public void setCellDataType(String dataType);
	
	/**
	 * 获得数据长度
	 * @return
	 */
	public int getDataLength();
	
	/**
	 * 设置数据长度
	 * @param dataLength
	 */
	public void setDataLength(int dataLength);
	
	/**
	 * 获得是否为空
	 * @return
	 */
	public boolean getDataIsNull();
	
	/**
	 * 设置是否为空
	 * @param dataIsNull
	 */
	public void setDataIsNull(boolean dataIsNull);
	
	/**
	 * 获得单元格数据模形
	 * @return
	 */
	public String getDataGridModel();
	
	/**
	 * 设置单元格数据模形 form 或list
	 * @param dataGridModel
	 */
	public void setDataGridModel(String dataGridModel);
	
	
	public int getSumRow();

	public void setSumRow(int sumRow);
	
	/**
	 * 获得数据维度
	 * @return
	 */
	public List<String> getDimensions();

	/**
	 * 设置数据维度
	 * @param transverseName
	 */
	public void setDimensions(List<String> dimensions);

	public String getFormat();

	public void setFormat(String format);


}
