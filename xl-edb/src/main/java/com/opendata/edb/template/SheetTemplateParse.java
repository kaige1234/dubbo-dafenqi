package com.opendata.edb.template;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.opendata.edb.base.DataCellGrid;
import com.opendata.edb.base.DataGrid;
import com.opendata.edb.base.DataType;
import com.opendata.edb.base.StyleCellGrid;
import com.opendata.edb.util.DateFormater;


/**
 * 单个sheet解析程序
 * 
 * @author 刘丰
 * 
 */
public class SheetTemplateParse
{

	private ExcelTemplate template;// 模板对象
	private Sheet sheet;// 当前sheet
	private SheetTemplate sheetTemplate = new SheetTemplate();// 当前sheet模板

	public SheetTemplateParse(ExcelTemplate template, Sheet sheet)
	{
		this.template = template;
		this.sheet = sheet;
	}

	public void parse()
	{
		try
		{
			this.parseSheet();
			this.template.putSheetTemplate(sheetTemplate);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 解析指定sheet名称的模板
	 * 
	 * @param sheetName
	 * @throws Exception
	 */
	private void parseSheet() throws Exception
	{
		this.sheetTemplate.setName(this.sheet.getSheetName());
		this.sheetTemplate.setSumColumnNumber(this.getSheetSumColumnNumber(sheet));
		this.sheetTemplate.setSumRowNumber(this.getSheetSumRowNumber(sheet));
		if (sheet != null)
		{
			GridBlevel gridBlevel = new GridBlevel();// 数据叶节点
			int beginRow = 0;// 当前节点开始行号
			
			for (int row = 0; row < sheetTemplate.getSumRowNumber(); row++)
			{
				// 定义行的数据结构，以用于后面进行测试是否为混合数据模型
				List<DataCellGrid> curRowDataGrid = new ArrayList<DataCellGrid>();
				List<StyleCellGrid> curRowStyleGrid = new ArrayList<StyleCellGrid>();
				RowGrid curRowGrid = new RowGrid(row);
				Row hssfRow=sheet.getRow(row);
				for (int col = 0; col < sheetTemplate.getSumColumnNumber(); col++)
				{
					String content="";
					Cell cell =null;
					if(hssfRow!=null)
					{
						 cell = hssfRow.getCell(col);
						 if(cell!=null)
						 {
							 content = this.getCellContents(cell);
						 }
					}
					if (content != null && !content.trim().equals(""))
					{
						if (content.startsWith("#") && content.endsWith("#") && content.indexOf("#") != content.lastIndexOf("#"))
						{
							// 以#开始，以#结束的认为是数据，反之则为格式
							DataCellGrid dataGridCell = this.getDataGridCell(row, col, content);
							dataGridCell.setCellStyle(cell.getCellStyle());
							curRowDataGrid.add(dataGridCell);
						} else if((content.startsWith("#")&&content.lastIndexOf("#")!=content.indexOf("#")&&content.toLowerCase().indexOf("fixlist")!=-1)||(content.endsWith("#")&&content.indexOf("#")!=content.lastIndexOf("#")&&content.toLowerCase().indexOf("fixlist")!=-1))
						{
							//当为固定列表时，可在定义的标识前或标识后加固定值，此时需要将固定值加入样式中，数据加入数据单元格中
							DataCellGrid dataGridCell = this.getDataGridCell(row, col, content.substring(content.indexOf("#"),content.lastIndexOf("#")+1));
							dataGridCell.setCellStyle(cell.getCellStyle());
							curRowDataGrid.add(dataGridCell);
							
							if(content.startsWith("#"))
							{
								curRowStyleGrid.add(new StyleCellGrid(row, col,this.getStyleInfo( content.substring(content.lastIndexOf("#")+1)),cell.getCellStyle()));
							}else
							{
								curRowStyleGrid.add(new StyleCellGrid(row, col,this.getStyleInfo(content.substring(0,content.indexOf("#"))),cell.getCellStyle()));
							}
						}else
						{
							curRowStyleGrid.add(new StyleCellGrid(row, col, this.getStyleInfo(content),cell.getCellStyle()));
						}
						
						this.sheetTemplate.putDimension(new DimensionParseHelper(cell).parse());//解析并添加维度信息
						
					} else
					{
						curRowStyleGrid.add(new StyleCellGrid(row, col, "",cell!=null?cell.getCellStyle():null));// 格式中也添加空格样式
					}
				}
				curRowGrid.setDataCellGridList(curRowDataGrid);
				curRowGrid.setStyleCellGridList(curRowStyleGrid);

				/**
				 * 同一行的数据模型若为混合型，则认为结构错误，不能使用
				 */
				if (this.isMixGridModel(curRowDataGrid))
				{
					throw new Exception(row + "行出现混合的数据模型....");
				}

				/**
				 * 当前行的数据模型为list
				 */
				String curRowGridModel = this.getGridModel(curRowDataGrid);// 获得当前行的数据格模型
				if (curRowGridModel.equals(DataType.ListDataGridModel) || curRowGridModel.equals(DataType.FixListDataGridModel))
				{
					if (!curRowDataGrid.isEmpty() || !curRowStyleGrid.isEmpty())
					{
						// 保存上一层次
						gridBlevel.setBeginRow(beginRow);// 设置当前结点的开始行号
						gridBlevel.setEndRow(row - 1);// 设置当前结点的结束行号
						if (!this.validFixListHeight(this.sheetTemplate.getLastGridBlevel(), gridBlevel))
						{
							throw new Exception(row + "行固定列表模型出错....");
						}
						this.putTemplate(gridBlevel);
					}

					{
						// 保存当前list

						gridBlevel = new GridBlevel();
						beginRow = row;// 将开始行号设置为当前行号
						this.putRowGrid(curRowGrid, gridBlevel);
						gridBlevel.setDataGridModel(curRowGridModel);
						gridBlevel.setBeginRow(beginRow);// 设置当前结点的开始行号
						if (curRowGridModel.equals(DataType.FixListDataGridModel))
						{
							// 如果为固定列表时，则根据固定高度来设置结束行号 1 共4行，则最后一行为
							gridBlevel.setEndRow(this.getFixListSumRow(curRowGrid) - 1 + beginRow);
						} else
						{
							gridBlevel.setEndRow(row);// 当为非固定列表时，开始行号与结束行号相同
						}

						if (!this.validFixListHeight(this.sheetTemplate.getLastGridBlevel(), gridBlevel))
						{
							throw new Exception(row + "行固定列表模型出错....");
						}
						this.putTemplate(gridBlevel);
					}

					{
						// 构建新的gridBlevel
						gridBlevel = new GridBlevel();
						beginRow = row + 1;// 将开始行号设置为当前行号
					}

				} else
				{
					/**
					 * 当前行与上一行的模型一致，且不是列表模型，且不是最后一行时
					 * 将当前行的数据单元格、样式单元格、空白单元格添加到当前节点中去
					 */
					this.putRowGrid(curRowGrid, gridBlevel);
				}

				if (row == sheetTemplate.getSumRowNumber() - 1)
				{
					if (!curRowDataGrid.isEmpty() || !curRowStyleGrid.isEmpty())
					{
						gridBlevel.setBeginRow(beginRow);// 设置当前结点的开始行号
						gridBlevel.setEndRow(row);// 设置结束行号
						if (!this.validFixListHeight(this.sheetTemplate.getLastGridBlevel(), gridBlevel))
						{
							throw new Exception(row + "行固定列表模型出错....");
						}
						this.putTemplate(gridBlevel);
					}
				}
			}

			this.sheetTemplate.setDataCellGridModel(this.getSheetDataGridModel());
		}
		
		this.validDimensionsGrid();
		
	}

	/**
	 * 根据单元格内容，获得样式信息，排除维度定义信息、数据单元格定义信息
	 * 
	 * @param content
	 * @return
	 */
	private String getStyleInfo(String content)
	{
		String data=content;
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
		return data;
	}
	
	
	/**
	 * 审核表单模型中维度信息是否正确
	 
	private void validDimensionsGrid() throws Exception
	{
		List<DataCellGrid> formCellGridList=this.sheetTemplate.getFormDataCellGrid();
		for(DataCellGrid cellGrid:formCellGridList)
		{
			if(cellGrid.getDimensions()!=null)
			{
				for(String dimension:cellGrid.getDimensions())
				{
				
						if(dimension.equals(cellGrid.getName()))
						{
							throw new Exception("单元格"+cellGrid.getRowNumber()+"行"+cellGrid.getColumnNumber()+"列的维度dimensions="+dimension+"所指向单元格不能是单元格本身!");
						}
						
						boolean exist=false;
						for(DataCellGrid queryCellGrid:formCellGridList)
						{
							if(queryCellGrid.getName().equals(dimension))
							{
								exist=true;
								break;
							}
						}
						if(!exist)
						{
							throw new Exception("单元格"+cellGrid.getRowNumber()+"行"+cellGrid.getColumnNumber()+"列的横向维度dimensions="+dimension+"所指向单元格不存在!");
						}
						
				}
			}
		}
	}
	*/
	
	/**
	 * 从当前sheet中所有的维度数据中，校验其维度信息是否存在
	 */
	private void validDimensionsGrid() throws Exception
	{
		List<DataCellGrid> formCellGridList=this.sheetTemplate.getFormDataCellGrid();
		for(DataCellGrid cellGrid:formCellGridList)
		{
			if(cellGrid.getDimensions()!=null)
			{
				for(String dimension:cellGrid.getDimensions())
				{
					if(!this.sheetTemplate.getDimensions().containsKey(dimension))
					{
						throw new Exception("单元格"+cellGrid.getRowNumber()+"行"+cellGrid.getColumnNumber()+"列的维度dimensions="+dimension+"不存在!");
					}
						
				}
			}
		}
	}
	
	/**
	 * 验证上一固定列表与当前层级的格式是否能够匹配 校验其高度
	 * 
	 * @param fixGridBlevel
	 * @param curGridBlevel
	 * @return
	 */
	private boolean validFixListHeight(GridBlevel fixGridBlevel, GridBlevel curGridBlevel)
	{

		if (fixGridBlevel != null && fixGridBlevel.getDataGridModel().equals(DataType.FixListDataGridModel))
		{
			// 判断当前层级的总行数是否小于上一层级（fixList)的总行数，如果小于则说明格式出错
			int fixListSumRow = fixGridBlevel.getFixListSumRow() - 1;
			if (curGridBlevel.getRowGridList().size() < fixListSumRow)
			{
				return false;
			}

			// 判断当前层级中与固定列表总行数相匹配的行模型是否为空，若不为空，则报错
			List<DataCellGrid> fixCellGrid = fixGridBlevel.getFixListCellGrid();
			for (int i = 0; i < fixListSumRow; i++)
			{
				RowGrid rowGrid = curGridBlevel.getRowGridList().get(i);
				List<StyleCellGrid> styleCellGrid = rowGrid.getStyleCellGridList();
				List<StyleCellGrid> tmpStyleCellGrid = new ArrayList<StyleCellGrid>();// 新的样式，此样式去除了不需要验证的单元格

				// 循环固定列表的所有列
				for (DataCellGrid fixCell : fixCellGrid)
				{
					// 循环当前样式中的所有列
					for (StyleCellGrid styleCell : styleCellGrid)
					{
						if (fixCell.getColumnNumber() == styleCell.getColumnNumber())
						{
							// 当不为空时，则直接返回
							if (!styleCell.getTitle().equals(""))
							{
								tmpStyleCellGrid.add(styleCell);
							}
						}

					}
				}
				rowGrid.setStyleCellGridList(tmpStyleCellGrid);
			}
		}
		return true;
	}

	/**
	 * 根据当前行信息，获得固定列表的高度，即总行数，仅只取第一个列的总行数
	 * 
	 * @param curRowGrid
	 * @return
	 * @throws Exception
	 */
	private int getFixListSumRow(RowGrid curRowGrid) throws Exception
	{
		int sumRow = -1;
		if (!curRowGrid.getDataCellGridList().isEmpty())
		{
			sumRow = curRowGrid.getDataCellGridList().get(0).getSumRow();
		}
		if (sumRow == -1)
		{
			throw new Exception("固定列表没有指定sumRow总行数属性值.");
		}
		return sumRow;
	}

	/**
	 * 获得实际总行数，从0开始计算
	 * 
	 * @return
	 */
	private int getSheetSumRowNumber(Sheet sheet)
	{
		int sumRowNumber = sheet.getLastRowNum()+1;
		return sumRowNumber;
	}

	/**
	 * 获得实际总列数
	 * 
	 * @return
	 */
	private int getSheetSumColumnNumber(Sheet sheet)
	{
		//int sumColumnNumber = sheet.getRow(0).getLastCellNum();
		int sumColumnNumber=0;
		for(int i=0;i<sheet.getLastRowNum()+1;i++){
			if(sheet.getRow(i)!=null){
				int tmpSumColumnNumber = sheet.getRow(i).getLastCellNum();
				if(tmpSumColumnNumber>sumColumnNumber){
					sumColumnNumber=tmpSumColumnNumber;
				}
			}
		}
		return sumColumnNumber;
	}

	/**
	 * 获得当前sheet的数据模型
	 * 
	 * @return
	 */
	private String getSheetDataGridModel()
	{
		String gridModel = "";
		List<GridBlevel> gridBlevelList = this.sheetTemplate.getGridBlevelList();
		for (GridBlevel gb : gridBlevelList)
		{
			if (gb.getDataGridModel().trim().equals(""))
			{
				continue;
			}
			if (gridModel.equals(""))
			{
				gridModel = gb.getDataGridModel();
				continue;
			}
			if (!gb.getDataGridModel().equals(gridModel))
			{
				return DataType.ListAndFormDataGridModel;
			}
		}
		return gridModel;
	}

	/**
	 * 向节点中添加一行
	 * 
	 * @param rowGrid
	 * @param gridBlevel
	 */
	private void putRowGrid(RowGrid rowGrid, GridBlevel gridBlevel)
	{
		gridBlevel.putRowGrid(rowGrid);
	}

	/**
	 * 向模板中添加一叶结点
	 * 
	 * @param gridBlevel
	 */
	private void putTemplate(GridBlevel gridBlevel)
	{
		if (!gridBlevel.getRowGridList().isEmpty())
		{
			this.sheetTemplate.putGridBlevel(gridBlevel);// 向sheet模板中添加一结点
		}
	}

	/**
	 * 判定一行的数据模型是否为一至,但可以是固定的fixlist与form模型在同一行中
	 * 
	 * @param dataGridList
	 * @return
	 */
	private boolean isMixGridModel(List<DataCellGrid> rowDataGridList)
	{
		String gridModel = "";
		if (rowDataGridList != null && !rowDataGridList.isEmpty())
		{
			for (DataGrid dgc : rowDataGridList)
			{
				if (gridModel.equals(""))
				{
					gridModel = dgc.getDataGridModel();
				} else
				{
					if (dgc.getDataGridModel().equals(DataType.ListDataGridModel) && (!gridModel.equals(DataType.ListDataGridModel)))
					{
						return true;
					}

				}
			}

		}
		return false;
	}

	/**
	 * 获得当前行的数据模型 当返回空时，则此行无数据
	 * 
	 * @param dataGridList
	 * @return
	 */
	private String getGridModel(List<DataCellGrid> rowDataGridList)
	{
		if (rowDataGridList != null && !rowDataGridList.isEmpty())
		{
			boolean isFixList = false;
			for (DataCellGrid cellGrid : rowDataGridList)
			{
				if (cellGrid.getDataGridModel().equals(DataType.FixListDataGridModel))
				{
					isFixList = true;
					break;
				}
			}
			if (isFixList)
			{
				return DataType.FixListDataGridModel;
			}

			String gridModel = rowDataGridList.get(0).getDataGridModel();
			return gridModel;
		}
		return DataType.FormDataGridModel;
	}

	/**
	 * 根据内容，获得单元格对象 内容格式以 #key=value&key=value#模式进行编写
	 * 
	 * @param row
	 * @param col
	 * @param content
	 * @return
	 */
	private DataCellGrid getDataGridCell(int row, int col, String content)
	{
		DataCellGrid dataGridCell = new DataCellGrid();
		dataGridCell.setRowNumber(row);
		dataGridCell.setColumnNumber(col);
		String tmpContent = content.substring(1, content.length() - 1);
		String[] params = tmpContent.split("&");
		for (int i = 0; i < params.length; i++)
		{
			String param = params[i];
			String key = param.substring(0, param.indexOf("=")).toLowerCase();
			String realValue=param.substring(param.indexOf("=") + 1);
			String value = param.substring(param.indexOf("=") + 1).toLowerCase();
			if (key.equals("length"))
			{
				dataGridCell.setDataLength(Integer.parseInt(value));
			}
			if (key.equals("name"))
			{
				dataGridCell.setName(realValue);
			}
			if (key.equals("title"))
			{
				dataGridCell.setTitle(value);
			}
			if (key.equals("datatype"))
			{
				dataGridCell.setCellDataType(value);
			}
			if (key.equals("format"))
			{
				dataGridCell.setFormat(realValue);
			}
			if (key.equals("isnull"))
			{
				dataGridCell.setDataIsNull(Boolean.parseBoolean(value));
			}
			if (key.equals("gridmodel"))
			{
				dataGridCell.setDataGridModel(value);
			}
			if (key.equals("sumrow"))
			{
				dataGridCell.setSumRow(Integer.parseInt(value));
			}
			//设置维度所属单元格
			if(key.equals("dimensions"))
			{
				List <String>dimensions=new ArrayList<String>();
				String []dimensionArray=value.split(",");
				for(int d=0;d<dimensionArray.length;d++)
				{
					if(dimensionArray[d]!=null&&!dimensionArray[d].equals(""))
					{
						dimensions.add(dimensionArray[d]);
					}
				}
				dataGridCell.setDimensions(dimensions);
			}
		}
		if (dataGridCell.getName() == null || dataGridCell.getName().equals(""))
		{
			dataGridCell.setName(row + "_" + col);
		}
		if (dataGridCell.getTitle() == null || dataGridCell.getTitle().equals(""))
		{
			dataGridCell.setTitle(row + "_" + col);
		}
		return dataGridCell;
	}

	/**
	 * 获得单元格内容字符串
	 * 
	 * @param cell
	 * @return
	 */
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
