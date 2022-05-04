package com.opendata.edb.template;

import java.io.InputStream;

/**
 * 根据模板数据信息，重写模板文件
 * @author 刘丰
 *
 */
public class ExcelTemplateWriter
{

	private ExcelTemplate template;
	public ExcelTemplateWriter(ExcelTemplate template)  
	{
		this.template=template;
	}
	
	public void write(InputStream in)
	{
		
	}
}
