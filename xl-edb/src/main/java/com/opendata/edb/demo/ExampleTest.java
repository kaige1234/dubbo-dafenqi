package com.opendata.edb.demo;

import com.opendata.edb.example.ExcelTemplateExample;
import com.opendata.edb.template.ExcelTemplate;
import com.opendata.edb.template.ExcelTemplateParse;
/**
 * 通过excel模板，生成一个空excel示例文件
 * @author liufeng
 *
 */
public class ExampleTest extends BaseTest {

	public static void main(String[] args)throws Exception {
		new ExampleTest().test();
	}
	
	public void test() throws Exception
	{
		ExcelTemplate template=ExcelTemplateParse.parse(this.templetFile);
		String outFile="c:/edb_example_test.xls";
		new ExcelTemplateExample(template,outFile).writer();
	}

}
