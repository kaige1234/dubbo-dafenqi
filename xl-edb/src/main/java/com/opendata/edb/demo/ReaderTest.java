package com.opendata.edb.demo;

import java.io.FileInputStream;
import java.io.InputStream;

import com.opendata.edb.data.ExcelData;
import com.opendata.edb.reader.ExcelReader;
import com.opendata.edb.template.ExcelTemplate;
import com.opendata.edb.template.ExcelTemplateParse;
import com.opendata.edb.valider.ExcelValider;

/**
 * 根据excel模板,读取excel表中的数据，支持列表模式、表单模式、列表与表单混合模式、固定列表模式、多列表模式等
 * @author liufeng
 *
 */
public class ReaderTest extends BaseTest{

	public static void main(String [] args)throws Exception
	{
		new ReaderTest().test();
	}
	
	public void test() throws Exception
	{
		ExcelTemplate template=ExcelTemplateParse.parse(this.templetFile);
		InputStream dataInputStream = new FileInputStream(this.dataFile);
		ExcelValider valider=new ExcelValider(template,dataInputStream);
		ExcelTemplate realExcelTemplate=null;
		if(valider.valid())
		{
			realExcelTemplate=valider.getRealExcelTemplate();
		}else
		{
			System.out.println(valider.getValiderInfo());
		}
		dataInputStream.close();
		dataInputStream = new FileInputStream(this.dataFile);
		ExcelReader excelReader=new ExcelReader(dataInputStream,realExcelTemplate);
		ExcelData data=excelReader.getData();
		System.out.println(data);
	}
}
