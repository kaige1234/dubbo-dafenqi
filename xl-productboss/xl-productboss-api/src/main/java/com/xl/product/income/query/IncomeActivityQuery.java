package com.xl.product.income.query;

import com.xl.product.income.model.IncomeActivity;

/**
 * 收息活动查询
 * @author liufeng
 *
 */
public class IncomeActivityQuery extends IncomeActivity{

	private Integer pageSize;
	private Integer pageNumber;
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
}
