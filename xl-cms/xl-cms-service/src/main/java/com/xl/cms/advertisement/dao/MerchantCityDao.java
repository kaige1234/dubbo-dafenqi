package com.xl.cms.advertisement.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xl.cms.advertisement.model.MerchantCity;
import com.xl.cms.advertisement.query.MerchantCityQuery;
import com.xl.commons.annotation.MyBatisDao;
import com.xl.commons.service.BaseDao;

@MyBatisDao
public interface MerchantCityDao  extends BaseDao<MerchantCity> {
	
	public List<MerchantCity> findPageMerchantCity(@Param("query")MerchantCityQuery query);
	
	public MerchantCity findById(@Param("lId")Long lId);
	
	public Integer add(@Param("obj")MerchantCity advertisementActivity);
	
	public Integer deleteById(@Param("lId")Long lId);

	/**
	 * 查询 区 市 省 名称 编码
	 * @param query.区名称
	 * @return
	 */
	public MerchantCityQuery findCodeByArea(@Param("query") MerchantCityQuery query);
	
	/**
	 * 查询 市 省 名称编码
	 * @param query.市名称
	 * @return
	 */
	public MerchantCityQuery findCodeByCity(@Param("query") MerchantCityQuery query);
	
	/**
	 * 查询 省 名称编码
	 * @param query.省名称
	 * @return
	 */
	public MerchantCityQuery findCodeByProvince(@Param("query") MerchantCityQuery query);
}
