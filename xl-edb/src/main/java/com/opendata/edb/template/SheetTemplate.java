package com.opendata.edb.template;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opendata.edb.base.DataCellGrid;
import com.opendata.edb.base.DataType;

/**
 * excel sheet模板格式
 * 
 * @author 刘丰
 * 
 */
public class SheetTemplate implements Serializable
{

	private String name;// sheet名称
	private List<GridBlevel> gridBlevelList = new ArrayList<GridBlevel>();// 以叶结点形式存储一个excel的格式
	private int sumRowNumber;// 总行数,从0开始计算
	private int sumColumnNumber;// 总列数,从0开始计算
	private String dataCellGridModel;// 数据格模式 列表模式(list)、表单模式(form)、列表与表单混合模式(listAndForm)
	private Map<String,Dimension> dimensions=new HashMap<String,Dimension>();//维度定义信息
	
	public Map<String, Dimension> getDimensions() {
		return dimensions;
	}

	public void setDimensions(Map<String, Dimension> dimensions) {
		this.dimensions = dimensions;
	}

	/**
	 * 新增维度定义
	 * @param dimension
	 */
	public void putDimension(Dimension dimension)
	{
		if(dimension!=null)
		{
			if(!dimensions.containsKey(dimension.getName()))
			{
				dimensions.put(dimension.getName(), dimension);
			}
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<GridBlevel> getGridBlevelList()
	{
		return gridBlevelList;
	}

	public void setGridBlevelList(List<GridBlevel> gridBlevelList)
	{
		this.gridBlevelList = gridBlevelList;
	}

	/**
	 * 添加一层级节点
	 * @param gridBlevel
	 */
	public void putGridBlevel(GridBlevel gridBlevel)
	{
		if (gridBlevel != null)
		{
			gridBlevel.setLevel(gridBlevelList.size() + 1);// 设置其叶节点的深度
			this.gridBlevelList.add(gridBlevel);
		}
	}

	public int getSumRowNumber()
	{
		return sumRowNumber;
	}

	public void setSumRowNumber(int sumRowNumber)
	{
		this.sumRowNumber = sumRowNumber;
	}

	public int getSumColumnNumber()
	{
		return sumColumnNumber;
	}

	public void setSumColumnNumber(int sumColumnNumber)
	{
		this.sumColumnNumber = sumColumnNumber;
	}

	public String getDataCellGridModel()
	{
		return dataCellGridModel;
	}

	public void setDataCellGridModel(String dataCellGridModel)
	{
		this.dataCellGridModel = dataCellGridModel;
	}

	/**
	 * 获得最后一节点
	 * @return
	 */
	public GridBlevel getLastGridBlevel()
	{
		if (!gridBlevelList.isEmpty())
		{
			return this.gridBlevelList.get(gridBlevelList.size() - 1);
		} else
		{
			return null;
		}
	}

	
	/**
	 * 获得所有的表单型单元格
	 * @return
	 */
	private List<DataCellGrid> formGrid=null;
	public List<DataCellGrid> getFormDataCellGrid()
	{
		if(formGrid==null)
		{
			formGrid=new ArrayList<DataCellGrid>();
			for(GridBlevel blevel:this.gridBlevelList)
			{
				if(blevel.getDataGridModel().equals(DataType.FormDataGridModel))
				{
					for(RowGrid row:blevel.getRowGridList())
					{
						for(DataCellGrid cellGrid:row.getDataCellGridList())
						{
							formGrid.add(cellGrid);
						}
					}
				}
			}
		}
		return formGrid;
	}
	
	
	public  SheetTemplate clone()
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(this);
			out.close();
			ByteArrayInputStream bin = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bin);
			Object clone = in.readObject();
			in.close();
			return (SheetTemplate) (clone);
		} catch (ClassNotFoundException e)
		{
			throw new InternalError(e.toString());
		} catch (StreamCorruptedException e)
		{
			throw new InternalError(e.toString());
		} catch (IOException e)
		{
			throw new InternalError(e.toString());
		}
	}
	
}
