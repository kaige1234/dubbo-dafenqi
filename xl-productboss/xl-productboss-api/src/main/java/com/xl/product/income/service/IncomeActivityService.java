package com.xl.product.income.service;

import com.github.pagehelper.PageInfo;
import com.xl.product.income.model.IncomeActivity;
import com.xl.product.income.query.IncomeActivityQuery;
/**
 * 收息活动服务
 * @author liufeng
 *
 */
public interface IncomeActivityService {

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public IncomeActivity getById(Long id);
	
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	public int delete(Long id);
	
	/**
	 * 新增
	 * @param incomeActivity
	 * @return
	 */
	public int save(IncomeActivity incomeActivity);
	
	/**
	 * 根据查询条件分页查询
	 * @param query
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	public PageInfo<IncomeActivity> findPage(IncomeActivityQuery query,Integer pageSize,Integer pageNum);

	/**
	 * 根据主键修改
	 * @param query
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	public int update(IncomeActivity incomeActivity);
}
