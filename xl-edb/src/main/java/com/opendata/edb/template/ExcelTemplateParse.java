package com.opendata.edb.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * 根据excel模板文件，构建模板信息
 * @author 刘丰
 *
 */
public class ExcelTemplateParse
{
	private static Map<String,ExcelTemplate> templateMap=new ConcurrentHashMap<String,ExcelTemplate>();
	/**
	 * 根据输入流，构建模板
	 * @param excelPath
	 * @return
	 */
	public static ExcelTemplate parse(InputStream inputStream,String extendsName)throws Exception
	{
		Workbook templatWorkBook =null;
		if(extendsName.equals("xls")){
			 templatWorkBook =new HSSFWorkbook(inputStream);
		}else{
			templatWorkBook =new XSSFWorkbook(inputStream);
		}
		ExcelTemplate template=new ExcelTemplate();//模板信息
		template.setTemplatWorkBook(templatWorkBook);
		int sheetNumber=templatWorkBook.getNumberOfSheets();
		for( int i=0;i<sheetNumber;i++)
		{
			final Sheet sheet=templatWorkBook.getSheetAt(i);
			new SheetTemplateParse(template,sheet).parse();
		}
		return template;
	}
	
	public static ExcelTemplate parse(String path)throws Exception{
		if(templateMap.containsKey(path)){
			//return templateMap.get(path);
		}
		String extendName=path.substring(path.lastIndexOf(".")+1,path.length());
		File file = new File(path);
		InputStream templatInputStream = new FileInputStream(file);
		ExcelTemplate template = ExcelTemplateParse.parse(templatInputStream,extendName);
		template.setExtendName(extendName);
		templatInputStream.close();
		//templateMap.put(path, template);
		return template;
		
	}
	
}
