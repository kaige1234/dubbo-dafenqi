package com.opendata.edb.writer;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.opendata.edb.base.DataCellGrid;
import com.opendata.edb.base.DataType;
import com.opendata.edb.data.ExcelData;
import com.opendata.edb.data.SheetData;
import com.opendata.edb.template.ExcelTemplate;
import com.opendata.edb.template.GridBlevel;
import com.opendata.edb.template.RowGrid;
import com.opendata.edb.template.SheetTemplate;

/**
 * 
 * excel 文件写入
 * 
 * @author 刘丰
 * 
 */
public class ExcelWriter
{

	private ExcelTemplate excelTemplate;// 模板信息

	private ExcelData excelData;// 输出数据

	private OutputStream out;// 输出流

	private Workbook writeWorkBook = null;// 新的workBook
	
	public String dateFormatPatten="yyyy-MM-dd";;//日志格式默认的格式化字符串
	
	private boolean alikeTemplet=false;//是否使用相同的模板进行数据写入操作
	
	/**
	 * 是否使用相同的模板进行数据写入操作
	 * 对于一个excel文件中的所有sheet数据，使用同一个sheet模板进行写入数据。使用此方式时，仅当excel模板中只有一个sheet时成立
	 * @param isAlike 是否相同
	 * @throws Exception 
	 */
	public void setAlikeTemplet() throws Exception
	{
		if(this.excelTemplate.getSheetTemplates().size()==1)
		{
			this.alikeTemplet=true;
		}else
		{
			throw new Exception("excel模板文件存在多个sheet模板，不能使用此功能...");
		}
	}

	public String getDateFormatPatten() {
		return dateFormatPatten;
	}

	public void setDateFormatPatten(String dateFormatPatten) {
		this.dateFormatPatten = dateFormatPatten;
	}

	public ExcelWriter(ExcelTemplate excelTemplate, OutputStream out) throws Exception
	{
		this.excelTemplate = excelTemplate;
		this.out = out;
		this.writeWorkBook = excelTemplate.getTemplatWorkBook();
	}

	public ExcelWriter(ExcelTemplate excelTemplate, String outFilePath) throws Exception
	{
		FileOutputStream fos = new FileOutputStream(outFilePath, false);
		this.out = new BufferedOutputStream(fos);
		this.writeWorkBook =excelTemplate.getTemplatWorkBook();
		this.excelTemplate = excelTemplate;
	}

	public ExcelTemplate getExcelTemplate()
	{
		return excelTemplate;
	}

	public void setExcelTemplate(ExcelTemplate excelTemplate)
	{
		this.excelTemplate = excelTemplate;
	}

	/**
	 * 写excel文件方法
	 */
	public void writer() throws Exception
	{
		if(this.alikeTemplet)
		{
			this.resetWriteWorkBook();
		}
		if (this.excelData != null && this.out != null && this.excelTemplate != null)
		{
			for (SheetData sheetData : excelData.getListSheetData())
			{
				Sheet sheet = this.writeWorkBook.getSheet(sheetData.getName());
				SheetTemplate sheetTemplate =null;
				if(this.alikeTemplet)
				{
					 sheetTemplate = this.excelTemplate.getSheetTemplates().get(0);
				}else
				{
					sheetTemplate = this.excelTemplate.getSheetTemplate(sheetData.getName());
				}
				if (sheet != null && sheetTemplate != null)
				{
					this.writeFormData(sheet, sheetData, sheetTemplate);
					this.writeListData(sheet, sheetData, sheetTemplate);
				}
			}
		}
		this.close();
	}

	private void resetWriteWorkBook()
	{
		Sheet parentSheet=this.writeWorkBook.getSheetAt(0);
		writeWorkBook.setSheetName(0, "tmp_999999999");//重命名称第一个sheet名称，以避免后续复制的sheet名称与它相同出错
		for (SheetData sheetData : excelData.getListSheetData())
		{
			Sheet sonSheet=this.writeWorkBook.createSheet(sheetData.getName());
			CopySheet.copySheet(this.writeWorkBook, parentSheet, sonSheet, true);
		}
		this.writeWorkBook.removeSheetAt(0);//删除第一个sheet.
	}
	
	/**
	 * 刷新，关闭相关流
	 * @throws Exception
	 */
	private void close() throws Exception
	{
		this.writeWorkBook.write(this.out);
		this.out.flush();
		this.out.close();
	}

	/**
	 * 写表单数据
	 * 
	 * @param sheet
	 * @param sheetData
	 */
	private void writeFormData(Sheet sheet, SheetData sheetData, SheetTemplate sheetTemplate) throws Exception
	{
		if (sheetData.getFormData() != null)
		{
			for (GridBlevel gridBlevel : sheetTemplate.getGridBlevelList())
			{
				if (gridBlevel.getDataGridModel().equals(DataType.FormDataGridModel))
				{
					for (RowGrid rowGrid : gridBlevel.getRowGridList())
					{
						for (DataCellGrid dataCellGrid : rowGrid.getDataCellGridList())
						{
							this.writePointCellData(sheet, dataCellGrid, sheetData.getFormData());
						}
					}

				}
			}
		}
	}

	/**
	 * 写列表数据
	 * 
	 * @param sheet
	 * @param sheetData
	 */
	private void writeListData(Sheet sheet, SheetData sheetData, SheetTemplate sheetTemplate) throws Exception
	{
		int insertSumRow = 0;//插入总数据行
		int js = 0;//待减数量
		int listDataModelNumber = 0;//标识是第几行list模型
		if (sheetData.getListData()== null||sheetData.getListData().isEmpty())
		{
			return ;
		}
		for (GridBlevel gridBlevel : sheetTemplate.getGridBlevelList())
		{
			
			if (gridBlevel.getDataGridModel().equals(DataType.ListDataGridModel)||gridBlevel.getDataGridModel().equals(DataType.FixListDataGridModel))
			{
					RowGrid rowGrid =gridBlevel.getRowGridList().get(0);
					List<? extends Object> listData = sheetData.getListData().get(listDataModelNumber);//取得当前模型对应的结果集数据
					listDataModelNumber++;
					//自动循环时
					if (gridBlevel.getDataGridModel().equals(DataType.ListDataGridModel))
					{
						//当有多行数据处理方式
						if (listData.size() > 1)
						{
							js++;
							//循环数据
							boolean isFirstRow=true;//是否为第一行数据
							for (Object obj : listData)
							{
								insertSumRow++;
								int realRowNumber = rowGrid.getRowNumber() + insertSumRow - js;//计算实际的行号
								if(!isFirstRow)
								{
									//由于2007版本及以后，对于移动，会存在bug，因此只针对2003进行移动
									if(this.excelTemplate.getExtendName().equals("xls")){
										//不是第一行数据时，需要在excel最后面插件一个空行，再将其移动后当前位置
										sheet.createRow(sheet.getLastRowNum()+1);
										sheet.shiftRows(rowGrid.getRowNumber() + insertSumRow-js, sheet.getLastRowNum(), 1,true,false);
									}else{
										int startRow=rowGrid.getRowNumber() + insertSumRow-js;
										insertRow(sheet,startRow,1);
									}
								}
								if(isFirstRow)
								{
									isFirstRow=false;
								}
								//循环列
								for (DataCellGrid dataCellGrid : rowGrid.getDataCellGridList())
								{
									DataCellGrid formGrid = new DataCellGrid(dataCellGrid.getName(), dataCellGrid.getColumnNumber(), realRowNumber,dataCellGrid.getCellDataType());
									formGrid.setFormat(dataCellGrid.getFormat());
									formGrid.setCellStyle(dataCellGrid.getCellStyle());
									this.writePointCellData(sheet, formGrid, obj);
								}
							}
						}else
						{
							int realRowNumber = rowGrid.getRowNumber() + insertSumRow-js;
							if(listData.size()==1)
							{
								for (DataCellGrid dataCellGrid : rowGrid.getDataCellGridList())
								{
									DataCellGrid formGrid = new DataCellGrid(dataCellGrid.getName(), dataCellGrid.getColumnNumber(), realRowNumber,dataCellGrid.getCellDataType());
									formGrid.setFormat(dataCellGrid.getFormat());
									formGrid.setCellStyle(dataCellGrid.getCellStyle());
									this.writePointCellData(sheet, formGrid, listData.get(0));
								}
							}else
							{
								//如果没有数据时，使用空数据填充
								for (DataCellGrid dataCellGrid : rowGrid.getDataCellGridList())
								{
									DataCellGrid formGrid = new DataCellGrid(dataCellGrid.getName(), dataCellGrid.getColumnNumber(), realRowNumber,dataCellGrid.getCellDataType());
									formGrid.setFormat(dataCellGrid.getFormat());
									formGrid.setCellStyle(dataCellGrid.getCellStyle());
									Map obj=new HashMap();
									obj.put(dataCellGrid.getName(), "");
									this.writePointCellData(sheet, formGrid, obj);
								}
							}
							
						}
						
					} else if(gridBlevel.getDataGridModel().equals(DataType.FixListDataGridModel))
					{
						//固定列表,则以表单模式，逐一进行填充数据,充种循环，需要知道循环总共有多少行，才能够进行循环填充数据值
						int sumRow=gridBlevel.getEndRow()-gridBlevel.getBeginRow()+1;
						int insertIndex=0;
						for (Object obj : listData)
						{
							int realRowNumber = rowGrid.getRowNumber() + insertIndex+insertSumRow;
							for (DataCellGrid dataCellGrid : rowGrid.getDataCellGridList())
							{
								DataCellGrid formGrid = new DataCellGrid(dataCellGrid.getName(), dataCellGrid.getColumnNumber(), realRowNumber,dataCellGrid.getCellDataType());
								formGrid.setFormat(dataCellGrid.getFormat());
								formGrid.setCellStyle(dataCellGrid.getCellStyle());
								this.writePointCellData(sheet, formGrid, obj);
							}
							insertIndex++;
							if(insertIndex==sumRow)
							{
								break;
							}
						}
					}

				}
		}

	}

	public ExcelData getExcelData()
	{
		return excelData;
	}

	public void setExcelData(ExcelData excelData)
	{
		this.excelData = excelData;
	}

	/**
	 * 填写指定单元格数据
	 * 
	 * @param
	 * @param property
	 * @param value
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private void writePointCellData(Sheet sheet, DataCellGrid formGrid, Object objData) throws Exception
	{
		Row hssfRow=sheet.getRow(formGrid.getRowNumber());
		Object value = this.findFieldValue(objData, formGrid.getName());
		String content = "";
		
		if (value != null)
		{
			if(value instanceof java.util.Date||value instanceof java.sql.Timestamp)
			{
				content=this.dateFormat(formGrid,value);
			}else
			{
				content = value.toString();
			}
		}
		CellStyle cellStyle = formGrid.getCellStyle();
		if (formGrid.getCellDataType().equals(DataType.Date))
		{
			Cell cell=hssfRow.createCell(formGrid.getColumnNumber());
			cell.setCellValue(content);
			cell.setCellStyle(cellStyle);
			return;

		}
		if (formGrid.getCellDataType().equals(DataType.String))
		{
			Cell cell=hssfRow.createCell(formGrid.getColumnNumber());
			cell.setCellValue(content);
			cell.setCellStyle(cellStyle);
			return;

		}
		if (formGrid.getCellDataType().equals(DataType.Number))
		{
			Cell cell=hssfRow.createCell(formGrid.getColumnNumber());
			if(content!=null&&!content.equals("")){
				cell.setCellValue(Double.parseDouble(content));
			}
			String format=formGrid.getFormat();
			if(format!=null&&!format.equals("")){
				cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(format));
			}
			cell.setCellStyle(cellStyle);
			return;
		}
		
	}

	/**
	 * 通过对象，属性名称，调用方法，获得值
	 * 
	 * @param dataBean
	 * @param fieldName
	 * @return
	 */
	public Object findFieldValue(Object dataBean, String fieldName) throws Exception
	{
		/**
		 * 如果是Map，则直接返回value
		 */
		if (dataBean instanceof HashMap)
		{
			return this.getMapValue((HashMap) dataBean,fieldName);
		} else
		{
			Method[] methods = findMethods(dataBean.getClass());
			for (Method method : methods)
			{
				if (method.getName().equalsIgnoreCase("get" + fieldName))
				{
					return method.invoke(dataBean, null);
				}
			}
		}
		return null;
	}
	
	private Object getMapValue(Map map,String fieldName){
		if(map.containsKey(fieldName)){
			return map.get(fieldName);
		}
		for(Object key:map.keySet()){
			if(key.toString().equalsIgnoreCase(fieldName)){
				return map.get(key);
			}
		}
		return null;
	}
	
	private String dateFormat(DataCellGrid formGrid,Object obj)
	{
		String format=formGrid.getFormat();
		if(format==null||format.equals("")){
			format=this.getDateFormatPatten();
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(obj);
	}
	
	public Map<String,Method[]> classMethodMap=new HashMap<String,Method[]>();
	
	private Method[] findMethods(Class objClass)
	{
		String className=objClass.getName();
		if(classMethodMap.containsKey(className)){
			return classMethodMap.get(className);
		}
		classMethodMap.put(className,  objClass.getMethods());
		return classMethodMap.get(className);
	}
	
	private void insertRow(Sheet sheet, int startRow, int rows) {  
        sheet.shiftRows(startRow, sheet.getLastRowNum(), rows, true, false);  
        for (int i = 0; i < rows; i++) {  
            Row sourceRow = null;//原始位置  
            Row targetRow = null;//移动后位置  
            Cell sourceCell = null;  
            Cell targetCell = null;  
            sourceRow = sheet.createRow(startRow);  
            targetRow = sheet.getRow(startRow + rows);  
            sourceRow.setHeight(targetRow.getHeight());  
            for (int m = targetRow.getFirstCellNum(); m < targetRow.getPhysicalNumberOfCells(); m++) {  
                sourceCell = sourceRow.createCell(m);  
                targetCell = targetRow.getCell(m);
                sourceCell.setCellStyle(targetCell.getCellStyle());  
                sourceCell.setCellType(targetCell.getCellType());  
            }  
            startRow++;  
        }
    }  
}
