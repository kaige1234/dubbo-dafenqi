package com.xl.product.income.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xl.commons.annotation.MyBatisDao;
import com.xl.commons.service.BaseDao;
import com.xl.product.income.model.IncomeActivity;
import com.xl.product.income.query.IncomeActivityQuery;

@MyBatisDao
public interface IncomeActivityDao  extends BaseDao<IncomeActivity> {
	
	public List<IncomeActivity> findPageIncomeActivity(@Param("query")IncomeActivityQuery query);
}
