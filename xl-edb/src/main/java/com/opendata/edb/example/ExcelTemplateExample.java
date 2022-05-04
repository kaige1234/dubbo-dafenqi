package com.opendata.edb.example;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.opendata.edb.base.DataCellGrid;
import com.opendata.edb.base.DataType;
import com.opendata.edb.base.StyleCellGrid;
import com.opendata.edb.template.Dimension;
import com.opendata.edb.template.ExcelTemplate;
import com.opendata.edb.template.GridBlevel;
import com.opendata.edb.template.RowGrid;
import com.opendata.edb.template.SheetTemplate;

/**
 * 根据excel模板文件，生成一个空excel样例文件以便于用户根据此空文件进行数据填写。
 * @author 刘丰
 *
 */
public class ExcelTemplateExample
{

	private OutputStream out;// 输出流

	private Workbook writeWorkBook = null;// 新的workBook

	private ExcelTemplate template;//模板数据
	
	private static final String content = "";
	
	public ExcelTemplateExample() throws Exception
	{
	}
	
	public ExcelTemplateExample(ExcelTemplate template, OutputStream out) throws Exception
	{
		this.out = out;
		this.writeWorkBook=template.getTemplatWorkBook();
		this.template = template;
	}
	
	public ExcelTemplateExample(ExcelTemplate template,  String outFilePath) throws Exception
	{
		FileOutputStream fos = new FileOutputStream(outFilePath, false);
		this.out = new BufferedOutputStream(fos);
		this.writeWorkBook=template.getTemplatWorkBook();
		this.template = template;
	}
	
	public void writer() throws Exception
	{
		if (this.out != null && this.template != null)
		{
			for (SheetTemplate sheetTemplate :template.getSheetTemplates())
			{
				Sheet sheet = this.writeWorkBook.getSheet(sheetTemplate.getName());
				if (sheet != null && sheetTemplate != null)
				{
					this.writeFormData(sheet, sheetTemplate);
					this.writeListData(sheet, sheetTemplate);
					this.writeDimension(sheet, sheetTemplate);
				}
			}
		}
		this.close();
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
	private void writeFormData(Sheet sheet,  SheetTemplate sheetTemplate) throws Exception
	{
			for (GridBlevel gridBlevel : sheetTemplate.getGridBlevelList())
			{
				if (gridBlevel.getDataGridModel().equals(DataType.FormDataGridModel))
				{
					for (RowGrid rowGrid : gridBlevel.getRowGridList())
					{
						for (DataCellGrid dataCellGrid : rowGrid.getDataCellGridList())
						{
							this.writePointCellData(sheet, dataCellGrid,content);
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
	private void writeListData(Sheet sheet, SheetTemplate sheetTemplate) throws Exception
	{
			for (GridBlevel gridBlevel : sheetTemplate.getGridBlevelList())
			{
				if (gridBlevel.getDataGridModel().equals(DataType.ListDataGridModel)||gridBlevel.getDataGridModel().equals(DataType.FixListDataGridModel))
				{
					for (RowGrid rowGrid : gridBlevel.getRowGridList())
					{
						List<DataCellGrid> data = rowGrid.getDataCellGridList();
						for(DataCellGrid dataCellGrid : data){
							
							if(dataCellGrid.getDataGridModel().equals(DataType.FixListDataGridModel))
							{
								String fixContent=content;
								for(StyleCellGrid style:rowGrid.getStyleCellGridList())
								{
									if(style.getRowNumber()==dataCellGrid.getRowNumber()&&style.getColumnNumber()==dataCellGrid.getColumnNumber())
									{
										fixContent=style.getTitle();
										break;
									}
								}
								this.writePointCellData(sheet, dataCellGrid,fixContent);
							}else
							{
								this.writePointCellData(sheet, dataCellGrid,content);
							}
							
						}
						
					}

				}
			}
	}

	private void writeDimension(Sheet sheet,  SheetTemplate sheetTemplate) throws Exception
	{
			for (Dimension dimension : sheetTemplate.getDimensions().values())
			{
				
				Cell cell = sheet.getRow(dimension.getRowNumber()).getCell(dimension.getColumnNumber());
				String content =cell.getStringCellValue();
				String data="";
				int startIndex=content.indexOf("~");
				int endIndex=content.lastIndexOf("~");
				if(startIndex!=-1&&endIndex!=-1&&(startIndex!=endIndex))
				{
					
					if(content.startsWith("~"))
					{
						data=content.substring(endIndex+1);
					}else
					{
						data=content.substring(0,startIndex);
					}
				}
				
				Row hssfRow=sheet.getRow(dimension.getRowNumber());
				if(hssfRow==null)
				{
					sheet.createRow(dimension.getRowNumber());
				}
				Cell hssfCell=hssfRow.createCell(dimension.getColumnNumber());
				hssfCell.setCellValue(data);
				hssfCell.setCellStyle(dimension.getCellStyle());
			}
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
	private void writePointCellData(Sheet sheet, DataCellGrid formGrid,String content) throws Exception
	{
			CellStyle cellStyle =formGrid.getCellStyle();
			Row row=sheet.getRow(formGrid.getRowNumber());
			if(row==null){
				sheet.createRow(formGrid.getRowNumber());
			}
			if (formGrid.getCellDataType().equals(DataType.Date))
			{
				Cell cell=row.createCell(formGrid.getColumnNumber());
				cell.setCellValue(content);
				cell.setCellStyle(cellStyle);
				return;

			}
			if (formGrid.getCellDataType().equals(DataType.String))
			{
				Cell cell=row.createCell(formGrid.getColumnNumber());
				cell.setCellValue(content);
				cell.setCellStyle(cellStyle);
				return;

			}
			if (formGrid.getCellDataType().equals(DataType.Number))
			{
				Double d = null;
				if(content!=null&&!content.equals("")){
					d = Double.parseDouble(content);
				}
				Cell cell=row.createCell(formGrid.getColumnNumber());
				if(d==null){
					cell.setCellValue("");
				}
				cell.setCellStyle(cellStyle);
				return;
			}
		
	}

	
}
