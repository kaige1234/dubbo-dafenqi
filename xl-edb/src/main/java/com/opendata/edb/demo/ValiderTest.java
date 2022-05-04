package com.opendata.edb.demo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.opendata.edb.template.ExcelTemplate;
import com.opendata.edb.template.ExcelTemplateParse;
import com.opendata.edb.valider.ExcelValider;

/**
 * 根据excel模板文件与excel数据文件验证数据文件的格式是否与模板文件格式相同，
 * 此验证不进行业务数据的验证， 仅对模板格式进行校验
 * @author liufeng
 *
 */
public class ValiderTest extends  BaseTest{

	public static void main(String[] args) {
		new ValiderTest().test();
	}

	public void test()
	{
		try
		{
			ExcelTemplate template=ExcelTemplateParse.parse(this.templetFile);
			InputStream dataInputStream = new FileInputStream(this.dataFile);
			ExcelValider valider=new ExcelValider(template,dataInputStream);
			boolean templateValidResult=valider.valid();//获得整个模板文件的验证
			System.out.println(templateValidResult);
			for(List<String> validerInfoList:valider.getValiderInfo().values())
			{
				for(String info:validerInfoList)
				{
					System.out.println(info);
				}
			}
			ExcelTemplate realExcelTemplate=valider.getRealExcelTemplate();//根据实际数据，获得真实的数据模板信息
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
