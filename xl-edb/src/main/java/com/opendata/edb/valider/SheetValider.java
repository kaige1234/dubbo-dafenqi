package com.opendata.edb.valider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.opendata.edb.base.DataCellGrid;
import com.opendata.edb.base.DataType;
import com.opendata.edb.base.StyleCellGrid;
import com.opendata.edb.template.GridBlevel;
import com.opendata.edb.template.RowGrid;
import com.opendata.edb.template.SheetTemplate;
import com.opendata.edb.util.DateFormater;

/**
 * 为每一个sheet产生一个线程进行验证
 * @author 刘丰
 */
public class SheetValider
{
	private SheetTemplate realSheetTemplate;//当前sheet模板
	private Sheet sheet;//当前的sheet数据
	private Map<String,Boolean> valider;//验证结果
	private Map<String,List<String>> validerInfo;//验证信息明细
	private List<String> validInfoList=new ArrayList<String>();//验证信息
	private int sumRow;//当前sheet总行数
	private int sumColumn;//当前sheet总列数
	
	
	public SheetValider(SheetTemplate realSheetTemplate, Sheet sheet, Map<String, Boolean> valider,Map<String,List<String>> validerInfo)
	{
		super();
		this.realSheetTemplate = realSheetTemplate;
		this.sheet = sheet;
		this.valider = valider;
		this.validerInfo=validerInfo;
	}
	
	
	/**
	 * 验证指定的sheet，验证流程有
	 * 1:若sheetGridModel为form时，则先验证总行数与总列数，再验证各个单元格的内容是否与模板一致
	 * 2.若sheetGridModel为list时，则先验证总列数，再验证模板
	 * 3.若sheetGridModel为listAndForm时，则要先验证总列数，再验证其模板
	 * @param sheetName
	 * @return
	 */
	public void valid() 
	{
		this.putValidInfo("开始验证sheet:"+this.sheet.getSheetName());
		this.sumColumn=this.getSheetSumColumn();
		this.sumRow=this.getSheetSumRow();
		//验证数据行数与列数是否一致
		if(!this.validRowColumnNumber())
		{
			this.valider.put(this.sheet.getSheetName(), false);
			return;
		}
		//验证模板格式与数据格式是否一致
		if(!this.validGridStyle())
		{
			this.valider.put(this.sheet.getSheetName(), false);
			return;
		}
		realSheetTemplate.setSumColumnNumber(this.sumColumn);
		realSheetTemplate.setSumRowNumber(this.sumRow);
		this.valider.put(this.sheet.getSheetName(), true);
		this.validerInfo.put(this.sheet.getSheetName(), this.validInfoList);
		this.putValidInfo("结束验证sheet:"+this.sheet.getSheetName());
	}
	
	
	/**
	 * 验证格式
	 * @param sheet
	 * @return
	 */
	private boolean validGridStyle()
	{
		/**
		 * 当为表单模型时，则直接验证指定的单元格
		 */
		if(this.realSheetTemplate.getDataCellGridModel().equals(DataType.FormDataGridModel))
		{
			GridBlevel curGridBlevel=this.realSheetTemplate.getGridBlevelList().get(0);
			int endRowNumber=this.validBlevel(0,this.realSheetTemplate.getGridBlevelList().get(0),null);
			if(endRowNumber==-1)
			{
				return false;
			}
			//将实际的开始行，结束行添加到实际的模板信息中去
			curGridBlevel.setBeginRow(0);
			curGridBlevel.setEndRow(endRowNumber);
			
		}else
		{
			 List<GridBlevel> gridBlevelList=this.realSheetTemplate.getGridBlevelList();
			 int beginRowNumber=0;//当前节点开始行号
			 int endRowNumber=0;//当前节点结束行号
			 for(int i=0;i<gridBlevelList.size();i++)
			 {
				 GridBlevel curGridBlevel=gridBlevelList.get(i);//获得当前节点
				 GridBlevel nextGridBlevel=null;//获得下一节点
				 if(i!=gridBlevelList.size()-1)
				 {
					 nextGridBlevel=gridBlevelList.get(i+1);
				 }
				 
				 if(curGridBlevel.getDataGridModel().equals(DataType.FixListDataGridModel))
				 {
					 //当为固定列表结点时,需要验证其节点的格式信息以及固定列表总高度的各行列表信息
					 int fixListEndRowNumber=this.validBlevel(beginRowNumber,curGridBlevel, nextGridBlevel);//验证当前节点，并返回结束行号
					 if(fixListEndRowNumber==-1)
					 {
						 return false;
					 }
					 curGridBlevel.setBeginRow(beginRowNumber);
					 curGridBlevel.setEndRow(fixListEndRowNumber);
					 beginRowNumber=beginRowNumber+1;//交换开始行号与结束行号
				 }else
				 {
					 endRowNumber=this.validBlevel(beginRowNumber,curGridBlevel, nextGridBlevel);//验证当前节点，并返回结束行号
					 if(endRowNumber==-1)
					 {
						 return false;
					 }
					 //将实际的开始行，结束行添加到实际的模板信息中去
					 curGridBlevel.setBeginRow(beginRowNumber);
					 curGridBlevel.setEndRow(endRowNumber);
					 beginRowNumber=endRowNumber+1;//交换开始行号与结束行号
				 }
				 
				 
			 }
		}
		return true;
	}
	
	
	/**
	 * 验证列表模型
	 * @param  beginRowNumber 数据读取的开始行号
	 * @param listGrid 当前list节点
	 * @param nextGrid 下一节点
	 * @return 如返回-1则验证不通过，否则返回当前节点的最后一行行号
	 */
	private int validBlevel(int beginRowNumber,GridBlevel curGridBlevel,GridBlevel nextGridBlevel)
	{
		//逐行验证当前节点内容与模板中的内容是否一致
		if(curGridBlevel.getDataGridModel().equals(DataType.FormDataGridModel)||curGridBlevel.getDataGridModel().equals(DataType.FixListDataGridModel))
		{
			int tmpEndRowNumber=beginRowNumber+(curGridBlevel.getEndRow()-curGridBlevel.getBeginRow());//实际结束行号
			int realRow=0;//真实行
			int rowIndex=-1;//循环行下标
			for(RowGrid rowGrid:curGridBlevel.getRowGridList())
			{
				rowIndex++;
				realRow=beginRowNumber+rowIndex;
				for(StyleCellGrid styleGrid:rowGrid.getStyleCellGridList())
				{
					styleGrid.setRowNumber(realRow);//设置真实的行号
					Row hssfRow=this.sheet.getRow(styleGrid.getRowNumber());
					Cell cell=null;
					if(hssfRow!=null)
					{
						cell=hssfRow.getCell(styleGrid.getColumnNumber());
					}
					String content="";
					if(cell!=null)
					{
						content=cell.getStringCellValue();
					}
					if(content==null){content="";}
					if(!content.equals(styleGrid.getTitle()))
					{
						if(styleGrid.isValid())
						{
							this.putValidInfo(this.sheet.getSheetName()+":数据"+styleGrid.getRowNumber()+"行"+styleGrid.getColumnNumber()+"列与模板不匹配!");
							return -1;
						}
					}
				}
				for(DataCellGrid dataGrid:rowGrid.getDataCellGridList())
				{
					dataGrid.setRowNumber(realRow);//设置真实的行号
				}
				rowGrid.setRowNumber(realRow);
			}
			return tmpEndRowNumber;//验证通过，返回下一节点开始行号
		}else if(curGridBlevel.getDataGridModel().equals(DataType.ListDataGridModel))
		{
			
			/**
			 * 验证列表格式
			 */
			if(nextGridBlevel==null)
			{
				//当为最后一节点时，直接返回最后一行行号
				return this.sumRow-1;
			}else
			{
				boolean nextBeleveRowContent=false;
				String nextFirstRowContent=this.getStyleGridContent(nextGridBlevel.getRowGridList().get(0).getStyleCellGridList());//以(列号_title_&)格式存放
				List<DataCellGrid> nextBlevelFistDataCellList=nextGridBlevel.getRowGridList().get(0).getDataCellGridList();
				for(int row=beginRowNumber;row<this.sumRow;row++)
				{
					String rowContent="";//取得数据行的样式值，排除数据格
					for(int col=0;col<this.sumColumn;col++)
					{
						if(!this.isHaveColumn(nextBlevelFistDataCellList,col))
						{
							
							Cell cell=this.sheet.getRow(row).getCell(col);
							String content="";
							if(cell!=null)
							{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								content=cell.getStringCellValue();
							}
							if(content==null){content="";}
							rowContent+=col+"_"+content+"_&";
						}
					}
					if(rowContent.trim().equals(nextFirstRowContent))
					{
						nextBeleveRowContent=true;
						return row-1;
					}
					
				}
				if(!nextBeleveRowContent)
				{
					this.putValidInfo(this.sheet.getSheetName()+":"+beginRowNumber+"行未找到与下一节点相匹配行!");
					return -1;
				}
			}
			
		}
		return -1;
	}
	
	
	
	
	/**
	 * 判断某列是否在某集合内
	 * @param rowDataList
	 * @param col
	 * @return
	 */
	private boolean isHaveColumn(List<DataCellGrid> rowDataList,int col )
	{
		for(DataCellGrid grid:rowDataList)
		{
			if(grid.getColumnNumber()==col)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 取得指定第几行的样式信息
	 * @param styleGrid
	 * @return
	 */
	private String getStyleGridContent(List<StyleCellGrid> styleGrids)
	{
		String content="";
		for(StyleCellGrid grid:styleGrids)
		{
			content+=grid.getColumnNumber()+"_"+grid.getTitle()+"_&";
		}
		return content;
	}
	
	/**
	 * 验证总的行列数
	 * @param sheet
	 * @return
	 */
	private boolean validRowColumnNumber()
	{
		if(realSheetTemplate.getDataCellGridModel().equals(DataType.FormDataGridModel))
		{
			if(realSheetTemplate.getSumRowNumber()!=this.sumRow)
			{
				this.putValidInfo(this.sheet.getSheetName()+":实际数据的总行数与模板数据总行数不一致!");
				return false;
			}
			if(realSheetTemplate.getSumColumnNumber()!=this.sumColumn)
			{
				this.putValidInfo(this.sheet.getSheetName()+":实际数据的总列数与模板数据的总列数不一致!");
				return false;
			}
		}else
		{
			if(realSheetTemplate.getSumRowNumber()>this.sumRow)
			{
				this.putValidInfo(this.sheet.getSheetName()+":实际数据的总行数与模板数据总行数不一致!");
				return false;
			}
			if(realSheetTemplate.getSumColumnNumber()>this.sumColumn)
			{
				this.putValidInfo(this.sheet.getSheetName()+":实际数据的总列数与模板数据的总列数不一致!");
				return false;
			}
		}
		return true;
	}

	
	/**
	 * 获得实际总行数
	 * @return
	 */
	private int getSheetSumRow()
	{
		int sumRowNumber = sheet.getLastRowNum()+1;
		return sumRowNumber;
		
	}
	
	/**
	 * 获得实际总列数
	 * @return
	 */
	private int getSheetSumColumn()
	{
		int sumColumnNumber = sheet.getRow(0).getLastCellNum();
		return sumColumnNumber;
	}
	
	/**
	 * 获得单元格内容字符串
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getCellContents(Cell cell)
	{
		if(cell==null)
		{
			return "";
		}
		String content =  "";
		if (cell.getCellType()==Cell.CELL_TYPE_NUMERIC)//数字类型单元格
		{
			if(DateUtil.isCellDateFormatted(cell))
			{
				Date date = new Date(0) ;
				date = DateUtil.getJavaDate(new Double(cell.getNumericCellValue()));
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
	private void putValidInfo(String info)
	{
		this.validInfoList.add(info);
	}
}
