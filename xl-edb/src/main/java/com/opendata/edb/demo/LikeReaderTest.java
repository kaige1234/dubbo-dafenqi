package com.opendata.edb.demo;

import java.io.FileInputStream;
import java.io.InputStream;

import com.opendata.edb.data.ExcelData;
import com.opendata.edb.reader.ExcelReader;
import com.opendata.edb.template.ExcelTemplate;
import com.opendata.edb.template.ExcelTemplateParse;
import com.opendata.edb.valider.ExcelValider;

/**
 * 通过单个sheet模板，读取excel表中多个相同格式的sheet数据
 * @author liufeng
 *
 */
public class LikeReaderTest extends BaseTest{

	public static void main(String [] args)throws Exception
	{
		new LikeReaderTest().test();
	}
	
	public void test() throws Exception
	{
		ExcelTemplate template=ExcelTemplateParse.parse(this.likeTempletFile);
		InputStream dataInputStream = new FileInputStream(this.likeDataFile);
		ExcelValider valider=new ExcelValider(template,dataInputStream);
		valider.setAlikeTemplet();
		if(!valider.valid())
		{
			System.out.println(valider.getValiderInfo());
		}
		dataInputStream.close();
		dataInputStream = new FileInputStream(this.likeDataFile);
		ExcelReader excelReader=new ExcelReader(dataInputStream,valider.getRealExcelTemplate());
		ExcelData data=excelReader.getData();
		System.out.println(data);
	}
}
