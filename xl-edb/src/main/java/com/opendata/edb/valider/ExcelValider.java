package com.opendata.edb.valider;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opendata.edb.template.ExcelTemplate;
import com.opendata.edb.template.SheetTemplate;

/**
 * 
 * 根据模板信息、excel实际数据，进行格式校验
 * 
 * @author 刘丰
 * 
 */
public class ExcelValider
{

	/**
	 * 根据数据得到的真实模板信息
	 */
	private ExcelTemplate realTemplate;

	/**
	 * key:sheet名称 boolean 验证值 对于所有子线程共享此属性，以便于在主线程能够看到此属性的变化
	 */
	private volatile Map<String, Boolean> valider = new HashMap<String, Boolean>();

	/**
	 * 真实数据的文件输入流
	 */
	private InputStream dataInputStream;

	/**
	 * 验证信息明细 key:sheet名称 List<String> 当前Sheet中的所有验证信息
	 */
	private final Map<String, List<String>> validerInfo = new HashMap<String, List<String>>();
	
	private boolean alikeTemplet=false;//是否使用相同的模板进行验证数据格式

	public ExcelValider(ExcelTemplate template, InputStream dataInputStream)
	{
		this.realTemplate = template.clone();
		this.dataInputStream = dataInputStream;
	}
	
	public Map<String, List<String>> getValiderInfo()
	{
		return validerInfo;
	}
	/**
	 * 是否使用相同的模板进行验证数据格式
	 * 对于一个excel文件中的所有sheet数据，使用同一个sheet模板进行验证。使用此方式时，仅当excel模板中只有一个sheet时成立
	 * @param isAlike 是否相同
	 * @throws Exception 
	 */
	public void setAlikeTemplet() throws Exception
	{
		if(this.realTemplate.getSheetTemplates().size()==1)
		{
			this.alikeTemplet=true;
		}else
		{
			throw new Exception("excel模板文件存在多个sheet模板，不能使用此功能...");
		}
	}

	/**
	 * 验证所有sheet
	 * 
	 * @return
	 * @throws IOException
	 * @throws BiffException
	 */
	public boolean valid() throws Exception
	{
		Workbook dataWorkBook=null;
		if(this.realTemplate.getExtendName().equals("xls")){
			dataWorkBook = new HSSFWorkbook(dataInputStream);
		}else{
			dataWorkBook = new XSSFWorkbook(dataInputStream);
		}
		this.resetRealTemplate(dataWorkBook);
		// 验证总sheet数量
		if (!this.validSumSheetNumber(dataWorkBook))
		{
			return false;
		}
		// 循环验证单个sheet格式
		int sheetNumber=dataWorkBook.getNumberOfSheets();
		for (int i = 0; i < sheetNumber; i++)
		{
			SheetTemplate sheetTemplate = realTemplate.getSheetTemplate(dataWorkBook.getSheetName(i));
			final Sheet sheet = dataWorkBook.getSheet(dataWorkBook.getSheetName(i));
			new SheetValider(sheetTemplate, sheet, this.valider, this.validerInfo).valid();
		}

		if (this.valider.containsValue(false))
		{
			return false;
		} else
		{
			return true;
		}
	}
	
	/**
	 * 当使用相同模板验证所有数据时，则需要复制与实际数据sheet个数相同的realTemplate
	 * @param dataWorkBook
	 */
	private void resetRealTemplate(Workbook dataWorkBook)
	{
		if(this.alikeTemplet)
		{
			//取得第一个模板
			SheetTemplate sheetTemplate=this.realTemplate.getSheetTemplates().get(0);
			//清除原有模板
			this.realTemplate.getSheetTemplates().clear();
			int sheetNumber=dataWorkBook.getNumberOfSheets();
			for (int i = 0; i < sheetNumber; i++)
			{
				//克隆并加入实际的新模板
				SheetTemplate newTemplate=sheetTemplate.clone();
				newTemplate.setName(dataWorkBook.getSheetName(i));
				this.realTemplate.putSheetTemplate(newTemplate);
			}
		}
	}

	/**
	 * 验证模板总sheet数是否与实际excel数据sheet总数是否一致,包括sheet名称
	 * 
	 * @return
	 */
	private boolean validSumSheetNumber(Workbook dataWorkBook)
	{
		//当使用相同模板验证所有数据时，不验证此方法
		List<SheetTemplate> sheetTemplateList = this.realTemplate.getSheetTemplates();
		int sheetNumber=dataWorkBook.getNumberOfSheets();
		// 校验数量是否相同
		if (sheetTemplateList.size() != sheetNumber)
		{
			new Exception("实际sheet数量与模板sheet数量不一致!");
			return false;
		}
		// 校验证名称是否一致
		for (int i = 0; i < sheetTemplateList.size(); i++)
		{
			if (!sheetTemplateList.get(i).getName().equals(dataWorkBook.getSheetName(i)))
			{
				new Exception(sheetTemplateList.get(i).getName() + ":实际sheet名称与模板sheet名称不一致!");
				return false;
			}
		}
		return true;
	}

	/**
	 * 获得验证信息
	 * 
	 * @return
	 */
	public Map<String, Boolean> getValider()
	{
		return this.valider;
	}

	/**
	 * 获得真实的数据模板信息
	 * 
	 * @return
	 */
	public ExcelTemplate getRealExcelTemplate()
	{
		return this.realTemplate;
	}

}
