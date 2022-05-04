package com.opendata.edb.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opendata.edb.data.ExcelData;
import com.opendata.edb.data.SheetData;
import com.opendata.edb.template.ExcelTemplate;
import com.opendata.edb.template.ExcelTemplateParse;
import com.opendata.edb.writer.ExcelWriter;
/**
 * 通过单个sheet模板，写入excel表中多个相同格式的sheet数据
 * @author liufeng
 *
 */
public class LikeWriterTest extends BaseTest{

	public static void main(String [] args)throws Exception
	{
		new LikeWriterTest().test();
	}
	
	public void test() throws Exception
	{
		ExcelTemplate template=ExcelTemplateParse.parse(this.likeTempletFile);
		String outFile="c:/edb_likewriter_test.xls";
		ExcelWriter excelWriter=new ExcelWriter(template,outFile);
		ExcelData data=new ExcelData();
		{
			//准备数据
			SheetData sheetData=new SheetData("test1");
			Map<String,String> formData=new HashMap<String,String>();
			formData.put("writeMan", "test");
			formData.put("writeDate", "2016-11-21");
			sheetData.setFormData(formData);
			
			Map<String,String> listRowData=new HashMap<String,String>();
			listRowData.put("userName", "test");
			listRowData.put("sex", "男");
			listRowData.put("age","13");
			List<Map> listData=new ArrayList<Map>();
			listData.add(listRowData);
			listData.add(listRowData);
			List allListData=new ArrayList();
			allListData.add(listData);
			sheetData.setListData(allListData);
			
			data.putSheetData(sheetData);
			sheetData=new SheetData("test2");
			sheetData.setListData(allListData);
			sheetData.setFormData(formData);
			data.putSheetData(sheetData);
		}
		excelWriter.setExcelData(data);
		excelWriter.setAlikeTemplet();
		excelWriter.writer();
	}
}
