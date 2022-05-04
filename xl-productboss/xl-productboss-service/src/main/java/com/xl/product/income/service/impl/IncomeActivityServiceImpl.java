package com.xl.product.income.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xl.commons.service.BaseService;
import com.xl.product.income.dao.IncomeActivityDao;
import com.xl.product.income.model.IncomeActivity;
import com.xl.product.income.query.IncomeActivityQuery;
import com.xl.product.income.service.IncomeActivityService;
/**
 * 收息活动服务
 * @author liufeng
 *
 */
/**
 * 收息活动服务
 * @author liufeng
 *
 */
@Service
public class IncomeActivityServiceImpl extends BaseService<IncomeActivityDao,IncomeActivity> implements IncomeActivityService {

	private static final Logger logger = LoggerFactory.getLogger(IncomeActivityServiceImpl.class);
	
	@Resource
	@Qualifier("incomeActivityDao")
	@Override
	protected void setDao(IncomeActivityDao dao) {
		this.dao=dao;
	}
	
	/**
	 * 根据查询条件分页查询
	 * @param query
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	@Transactional(readOnly=true)
	public PageInfo<IncomeActivity> findPage(IncomeActivityQuery query,Integer pageSize,Integer pageNum){
		PageHelper.startPage(pageNum, pageSize);//设置分页条件
		return new PageInfo<IncomeActivity>( this.dao.findPageIncomeActivity(query));
	}

	


}
