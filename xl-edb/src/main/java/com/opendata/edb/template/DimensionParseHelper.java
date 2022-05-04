package com.opendata.edb.template;


import org.apache.poi.ss.usermodel.Cell;

/**
 * 以@开始 以@结束为规则，定义维度信息
 * 
 * 格式如:@title=中学&code=middleSchool&data=中学@
 * @author 刘丰
 *
 */
public class DimensionParseHelper {

	private String content;
	private Dimension dimension;
	private Cell cell;

	public DimensionParseHelper(Cell cell) {
		this.content =this.getCellContents(cell);
		this.cell=cell;
	}
	/**
	 * 获得单元格内容字符串
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellContents(Cell cell)
	{
		if(cell==null)
		{
			return "";
		}
		String content = "";
		if (cell.getCellType()==Cell.CELL_TYPE_NUMERIC)//数字类型单元格
		{
			content = Double.toString(cell.getNumericCellValue());
		} else if (cell.getCellType()==Cell.CELL_TYPE_BOOLEAN)//boolean类型单元格
		{
			content = Boolean.toString(cell.getBooleanCellValue());
		} else if (cell.getCellType()==Cell.CELL_TYPE_BLANK)//空白单元格
		{
			content = "";
		} else
		{
			content = cell.getStringCellValue();//字符串单元格
		}
		return content.trim();
	}
	/**
	 * 获得
	 * @return
	 */
	public Dimension parse() {
		
		if(content.trim().equals("")){
			return null;
		}
		String data="";
		String style="";
		int startIndex=content.indexOf("~");
		int endIndex=content.lastIndexOf("~");
		if(startIndex!=-1&&endIndex!=-1&&(startIndex!=endIndex))
		{
			
			if(content.startsWith("~"))
			{
				data=this.content.substring(endIndex+1);
				style=this.content.substring(0+1,endIndex);
			}else
			{
				data=this.content.substring(0,startIndex);
				style=this.content.substring(startIndex+1,content.length()-1);
			}
			
		}
		return this.parseStyle(style, data);
	}
	
	private Dimension parseStyle(String style,String data)
	{
		
		String tmpData="";
		String tmpTitle="";
		String tmpName="";
		String [] styleArray=style.split("&");
		for(int i=0;i<styleArray.length;i++)
		{
			String[] keyValue=styleArray[i].split("=");
			if(keyValue.length==2)
			{
				String key=keyValue[0];
				String value=keyValue[1];
				if(key.toLowerCase().equals("title"))
				{
					tmpTitle=value;
				}else if(key.toLowerCase().equals("name"))
				{
					tmpName=value;
				}else if(key.toLowerCase().equals("data"))
				{
					tmpData=value;
				}
			}
		}
		
		if(tmpData.equals(""))
		{
			tmpData=data;
		}
		if(tmpTitle.equals(""))
		{
			tmpTitle=data;
		}
		
		if(!tmpName.equals("")&&!tmpData.equals("")&&!tmpTitle.equals(""))
		{
			dimension=new Dimension(tmpTitle,tmpName,tmpData,cell.getRowIndex(),cell.getColumnIndex());
			dimension.setCellStyle(cell.getCellStyle());
		}
		return dimension;
	}
	
}
