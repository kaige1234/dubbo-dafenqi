package com.opendata.edb.data;

import java.util.ArrayList;
import java.util.List;

public class ExcelData
{
	private String name;//excel文件名称
	private List<SheetData>  listSheetData=new ArrayList<SheetData>();//各个sheet数据
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public List<SheetData> getListSheetData()
	{
		return listSheetData;
	}
	public void setListSheetData(List<SheetData> listSheetData)
	{
		this.listSheetData = listSheetData;
	}
	public void putSheetData(SheetData data)
	{
		if(data!=null)
		{
			this.listSheetData.add(data);
		}
		
	}

}
