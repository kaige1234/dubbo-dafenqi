package com.xl.commons.data;

import java.util.List;

public class DataGridPage
{
	private List rows;
	private Integer  pageSize;
	private Integer pageNumber;
	private Long total;
	public DataGridPage(){
		
	}
	public DataGridPage(List rows,Integer pageNumber,Integer pageSize,Long total){
		this.rows=rows;
		this.pageNumber=pageNumber;
		this.pageSize=pageSize;
		this.total=total;
	}
	public List getRows()
	{
		return rows;
	}
	public void setRows(List rows)
	{
		this.rows = rows;
	}
	public Integer getPageSize()
	{
		return pageSize;
	}
	public void setPageSize(Integer pageSize)
	{
		this.pageSize = pageSize;
	}
	public Long getTotal()
	{
		return total;
	}
	public void setTotal(Long total)
	{
		this.total = total;
	}
	public Integer getPageNumber()
	{
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber)
	{
		this.pageNumber = pageNumber;
	}
	
}
