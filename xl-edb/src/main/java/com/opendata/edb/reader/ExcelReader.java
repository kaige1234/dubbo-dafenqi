package com.opendata.edb.reader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opendata.edb.base.DataCellGrid;
import com.opendata.edb.base.DataType;
import com.opendata.edb.data.ExcelData;
import com.opendata.edb.data.SheetData;
import com.opendata.edb.template.ExcelTemplate;
import com.opendata.edb.template.GridBlevel;
import com.opendata.edb.template.RowGrid;
import com.opendata.edb.template.SheetTemplate;
import com.opendata.edb.util.BeanUtils;
import com.opendata.edb.util.DateFormater;

/**
 * excel文件读取
 * 
 * @author 刘丰
 * 
 */
public class ExcelReader
{
	/**
	 * 根据数据得到的真实模板信息
	 */
	private ExcelTemplate realTemplate = new ExcelTemplate();

	/**
	 * 针对于列表数据时 当一个模板有多个list数据时，依顺序使用此集合中的转换类型 将读取的对象转换成对象类型,默认为HashMap
	 */
	private List<Class> listTransObject = new ArrayList<Class>();

	private Class formTransObject = LinkedHashMap.class;
	/**
	 * 默认针对于表单数据时，数据对象类型
	 */
	private Class defaultTransObject = LinkedHashMap.class;

	/**
	 * 当前的数据工作薄
	 */
	private Workbook dataWorkBook;// 当前的工作薄

	private SheetTemplate realSheetTemplate;
	
	private ReaderRow readerRow=new DefaultReaderRow();//行读取

	public ExcelReader(InputStream in, ExcelTemplate realTemplate) throws Exception
	{
		this.realTemplate = realTemplate;
		if (in != null)
		{
			if(realTemplate.getExtendName().equals("xls"))
			{
				dataWorkBook = new HSSFWorkbook(in);
			}else{
				dataWorkBook = new XSSFWorkbook(in);
			}
		}
	}
	
	public ReaderRow getReaderRow() {
		return readerRow;
	}

	public void setReaderRow(ReaderRow readerRow) {
		this.readerRow = readerRow;
	}

	public ExcelTemplate getRealTemplate()
	{
		return realTemplate;
	}

	public void setRealTemplate(ExcelTemplate realTemplate)
	{
		this.realTemplate = realTemplate;
	}

	public List<Class> getListTransObject()
	{
		return listTransObject;
	}

	public void setListTransObject(List<Class> listTransObject)
	{
		this.listTransObject = listTransObject;
	}


	public Class getFormTransObject()
	{
		return formTransObject;
	}

	public void setFormTransObject(Class formTransObject)
	{
		this.formTransObject = formTransObject;
	}

	public Class getDefaultTransObject()
	{
		return defaultTransObject;
	}

	public void setDefaultTransObject(Class defaultTransObject)
	{
		this.defaultTransObject = defaultTransObject;
	}

	/**
	 * 获得一个excel所有数据
	 * 
	 * @return
	 */
	public ExcelData getData() throws Exception
	{
		ExcelData excelData = new ExcelData();
		int sheetNumber=dataWorkBook.getNumberOfSheets();
		for (int i = 0; i < sheetNumber; i++)
		{
			SheetData sheetData = this.getSheetData(dataWorkBook.getSheetName(i));
			excelData.putSheetData(sheetData);
		}
		return excelData;
	}

	/**
	 * 获得指定sheet名称的数据
	 * 
	 * @param sheetName
	 * @return
	 */
	public SheetData getSheetData(String sheetName) throws Exception
	{
		SheetData sheetData = new SheetData(sheetName);
		Sheet sheet = this.dataWorkBook.getSheet(sheetName);
		this.realSheetTemplate = this.realTemplate.getSheetTemplate(sheetName);
		if (sheet != null && realSheetTemplate != null)
		{
			if (this.realSheetTemplate.getDataCellGridModel().equals(DataType.FormDataGridModel))
			{
				sheetData.setFormData(this.readFormData(sheet));
			} else
			{
				sheetData.setFormData(this.readFormData(sheet));
				sheetData.setListData(this.readListData(sheet));
			}
		}
		sheetData.setSheetTemplate(realSheetTemplate);
		return sheetData;
	}

	/**
	 * 读取指定sheet的form数据
	 * 
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	private Object readFormData(Sheet sheet) throws Exception
	{
		Object formData = this.formTransObject.newInstance();
		List<GridBlevel> gridBlevelList = this.realSheetTemplate.getGridBlevelList();
		for (GridBlevel blevel : gridBlevelList)
		{
			if (blevel.getDataGridModel().equals(DataType.FormDataGridModel))
			{
				List<RowGrid> rowGridList = blevel.getRowGridList();
				for (RowGrid rowGrid : rowGridList)
				{
					for (DataCellGrid dataCellGrid : rowGrid.getDataCellGridList())
					{
						String content = this.getContent(sheet, dataCellGrid.getColumnNumber(), dataCellGrid.getRowNumber());
						if (formData instanceof HashMap)
						{
							((HashMap) formData).put(dataCellGrid.getName(), content);
						} else
						{
							BeanUtils.setObjectValue(formData, dataCellGrid.getName(), content);
						}
					}
				}
			}
		}
		return this.readerRow.readFormRow(sheet.getSheetName(),formData);
	}

	/**
	 * 读取指定sheet的list数据
	 * 
	 * @param sheet
	 * @return
	 */
	private List<List<? extends Object>> readListData(Sheet sheet) throws Exception
	{
		List<List<? extends Object>> listData = new ArrayList<List<? extends Object>>();
		List<GridBlevel> gridBlevelList = this.realSheetTemplate.getGridBlevelList();

		for (GridBlevel blevel : gridBlevelList)
		{
			int index = -1;
			//循环节点
			if (blevel.getDataGridModel().equals(DataType.ListDataGridModel)||blevel.getDataGridModel().equals(DataType.FixListDataGridModel))
			{
				index++;
				List<DataCellGrid> dataCellGridList = blevel.getRowGridList().get(0).getDataCellGridList();
				List<Object> listObject = new ArrayList<Object>(blevel.getSumRow());

				//循环数据行
				for (int row = blevel.getBeginRow(); row <= blevel.getEndRow(); row++)
				{
					//循环数据单元格
					Object obj = null;
					if (this.listTransObject.size() >= index + 1)
					{
						obj = listTransObject.get(index).newInstance();
					}
					if (obj == null)
					{
						obj = new HashMap();
					}
					for (DataCellGrid dataCellGrid : dataCellGridList)
					{

						String content = this.getContent(sheet, dataCellGrid.getColumnNumber(), row);
						if (obj instanceof HashMap)
						{
							((HashMap) obj).put(dataCellGrid.getName(), content);
						} else
						{
							BeanUtils.setObjectValue(obj, dataCellGrid.getName(), content);
						}
					}
					Object tmp=this.readerRow.readListRow(sheet.getSheetName(), index, obj);
					if(tmp!=null)
					{
						listObject.add(tmp);
					}
				}
				
				listData.add(listObject);
			}

		}
		return listData;
	}

	/**
	 * 获得指定单元格数据
	 * 
	 * @param sheet
	 * @param row
	 * @param col
	 * @return
	 */
	private String getContent(Sheet sheet, int col, int row)
	{
		Row hssfRow= sheet.getRow(row);
		Cell cell =null;
		if(hssfRow==null)
		{
			return "";
		}else
		{
			cell = hssfRow.getCell(col);
			if(cell==null)
			{
				return "";
			}
		}
		String content =  "";
		if (cell.getCellType()==Cell.CELL_TYPE_NUMERIC)//数字类型单元格
		{
			if(DateUtil.isCellDateFormatted(cell))
			{
				Date date = new Date(0) ;
				date = HSSFDateUtil.getJavaDate(new Double(cell.getNumericCellValue()));
				content=DateFormater.DateToString(date);
			}else
			{
				content = Double.toString(cell.getNumericCellValue());
				if(content.endsWith(".0"))
				{
					content=content.substring(0,content.indexOf(".0"));
				}
			}
		} else if (cell.getCellType()==Cell.CELL_TYPE_BOOLEAN)//boolean类型单元格
		{
			content = Boolean.toString(cell.getBooleanCellValue());
		} else if (cell.getCellType()==Cell.CELL_TYPE_BLANK)//空白单元格
		{
			content = "";
		} else
		{
			cell.setCellType(Cell.CELL_TYPE_STRING);
			content = cell.getStringCellValue();//字符串单元格
		}
		return content.trim();
	}
	
	
}
