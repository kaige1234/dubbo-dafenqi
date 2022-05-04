package com.opendata.edb.demo;

import java.io.FileInputStream;
import java.io.InputStream;

import com.opendata.edb.data.ExcelData;
import com.opendata.edb.reader.ExcelReader;
import com.opendata.edb.template.ExcelTemplate;
import com.opendata.edb.template.ExcelTemplateParse;
import com.opendata.edb.valider.ExcelValider;
import com.opendata.edb.writer.ExcelWriter;
/**
 * 根据excel模板,写入向excel文件写入数据，支持列表模式、表单模式、列表与表单混合模式、固定列表模式、多列表模式等
 * @author liufeng
 *
 */
public class WriterTest extends BaseTest{

	public static void main(String[] args)throws Exception {
		new WriterTest().test();

	}
	public void test()throws Exception
	{
		ExcelTemplate template=ExcelTemplateParse.parse(this.templetFile);
		String outFile="c:/edb_writer_test.xlsx";
		ExcelWriter excelWriter=new ExcelWriter(template,outFile);
		ExcelData data=this.getData();
		excelWriter.setExcelData(data);
		excelWriter.writer();
	}
	
	/**
	 * 获得数据
	 * @return
	 * @throws Exception
	 */
	public ExcelData getData() throws Exception
	{
		ExcelTemplate template=ExcelTemplateParse.parse(this.templetFile);
		InputStream dataInputStream = new FileInputStream(this.dataFile);
		ExcelValider valider=new ExcelValider(template,dataInputStream);
		ExcelTemplate realExcelTemplate=null;
		if(valider.valid())
		{
			realExcelTemplate=valider.getRealExcelTemplate();
		}
		dataInputStream.close();
		dataInputStream=null;
		dataInputStream = new FileInputStream(dataFile);
		ExcelReader excelReader=new ExcelReader(dataInputStream,realExcelTemplate);
		ExcelData data=excelReader.getData();
		dataInputStream.close();
		return data;
	}
}
