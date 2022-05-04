package com.opendata.edb.template;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * 定义excel模板,包含模板名称，以及模板所包含的sheet; 一个excel文件包含多个sheet,模板名称，默认以excel文件名称作为模板名称
 * 
 * @author 刘丰
 * 
 */
public class ExcelTemplate implements Serializable
{
	private static final long serialVersionUID = 2450698339007558678L;
	private String name;// 模板名称
	private String path;//模板路径
	private List<SheetTemplate> sheetTemplates = new ArrayList<SheetTemplate>();// sheet模板
	private transient Workbook templatWorkBook;
	private String extendName="xls";//excel文件的扩展名，后续会根据此扩展名，进行不同的读写
	
	public String getExtendName() {
		return extendName;
	}

	public void setExtendName(String extendName) {
		this.extendName = extendName;
	}

	public Workbook getTemplatWorkBook() {
		return templatWorkBook;
	}

	public void setTemplatWorkBook(Workbook templatWorkBook) {
		this.templatWorkBook = templatWorkBook;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<SheetTemplate> getSheetTemplates()
	{
		return sheetTemplates;
	}

	public void setSheetTemplates(List<SheetTemplate> sheetTemplates)
	{
		this.sheetTemplates = sheetTemplates;
	}

	public boolean isEmpty()
	{
		if (this.sheetTemplates == null || this.sheetTemplates.isEmpty())
		{
			return true;
		}
		return false;
	}

	/**
	 * 根据sheet名称获得指定的模板
	 * @param sheetName
	 * @return
	 */
	public SheetTemplate getSheetTemplate(String sheetName)
	{
		for (SheetTemplate temp : sheetTemplates)
		{
			if (temp.getName().equals(sheetName))
			{
				return temp;
			}
		}
		return null;
	}

	public void putSheetTemplate(SheetTemplate sheetTemplate)
	{
		boolean isHave = false;
		for (SheetTemplate temp : sheetTemplates)
		{
			if (temp.getName().equals(sheetTemplate.getName()))
			{
				sheetTemplates.remove(temp);
				sheetTemplates.add(sheetTemplate);
				isHave = true;
				break;
			}
		}
		if (!isHave)
		{
			//不存在，更新
			this.sheetTemplates.add(sheetTemplate);
		}

	}

	public  ExcelTemplate clone()
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(this);
			out.close();
			ByteArrayInputStream bin = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bin);
			Object clone = in.readObject();
			in.close();
			ExcelTemplate cloneExcelTemplate=(ExcelTemplate)clone;
			cloneExcelTemplate.setTemplatWorkBook(this.getTemplatWorkBook());//此属性为不能进行克隆，因此需要手动进行设置
			return cloneExcelTemplate;
		} catch (ClassNotFoundException e)
		{
			throw new InternalError(e.toString());
		} catch (StreamCorruptedException e)
		{
			throw new InternalError(e.toString());
		} catch (IOException e)
		{
			throw new InternalError(e.toString());
		}
	}
	
}
