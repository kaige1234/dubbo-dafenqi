package com.opendata.edb.data;

import java.util.ArrayList;
import java.util.List;

import com.opendata.edb.template.Dimension;
import com.opendata.edb.template.SheetTemplate;

/**
 * 
 * 存储一个excel文件中的一个sheet的所有数据
 * 数据内容包含列表数据，固定列表数据，表单数据
 * 
 * @author 刘丰
 *
 */
public class SheetData
{

	private String name;// sheet名称
	private Object formData;// 表单数据
	private List<List<? extends Object>> listData;// 列表数据
	private SheetTemplate sheetTemplate;//当前sheet的实际模板
	public SheetTemplate getSheetTemplate()
	{
		return sheetTemplate;
	}

	public void setSheetTemplate(SheetTemplate sheetTemplate)
	{
		this.sheetTemplate = sheetTemplate;
	}

	public SheetData(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Object getFormData()
	{
		return formData;
	}

	public void setFormData(Object formData)
	{
		this.formData = formData;
	}

	public List<List<? extends Object>> getListData()
	{
		return listData;
	}

	public void setListData(List<List<? extends Object>> listData)
	{
		this.listData = listData;
	}

	public List<? extends Object> getFistListData()
	{
		if (this.listData != null && this.listData.isEmpty())
		{
			return this.listData.get(0);
		}
		return null;
	}

	public void putListData(List<? extends Object> data)
	{
		if (data != null)
		{
			if(this.listData==null){
				this.listData=new ArrayList<List<? extends Object>>();
			}
			this.listData.add(data);
		}
	}
	
	/**
	 * 根据数据单元格名称、维度名称，获得相应的值
	 * @param gridName 单元格名称
	 * @param dimensionName 维度名称
	 * @return
	 */
	public Object getDimensionValue(String gridName,String dimensionName) throws Exception
	{
		
		Dimension dimension=null;
		if(this.sheetTemplate.getDimensions().containsKey(dimensionName))
		{
			dimension=this.sheetTemplate.getDimensions().get(dimensionName);
			return dimension.getData();
		}
		
		return "";
		/*
		List<DataCellGrid> dimensionGrid=this.sheetTemplate.getDimensionGrid();
		for(DataCellGrid cellGrid:dimensionGrid)
		{
			if(cellGrid.getName().equals(gridName))
			{
				if(dimensionName!=null&&!dimensionName.equals(""))
				{
					 if(formData instanceof HashMap)
					 {
						 return ((Map)formData).get(dimensionName);
					 }else
					 {
						return BeanUtils.getPrivateProperty(formData, dimensionName);
					 }
				}
			}
		}
		return "";
		*/
	}
	


}
