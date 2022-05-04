package com.xl.cms.advertisement.service;

import com.github.pagehelper.PageInfo;
import com.xl.cms.advertisement.model.MerchantCity;
import com.xl.cms.advertisement.query.MerchantCityQuery;
/**
 * 城市编码服务
 * @author jianqiang.yuan
 *
 */
public interface MerchantCityService {

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public MerchantCity findById(Long id);
	
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	public int delete(Long id);
	
	/**
	 * 新增
	 * @param merchantCity
	 * @return
	 */
	public MerchantCity add(MerchantCity merchantCity);
	
	/**
	 * 根据查询条件分页查询
	 * @param query
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	public PageInfo<MerchantCity> findPage(MerchantCityQuery query,Integer pageSize,Integer pageNum);
	
	/**
	 * 查询 区 市 省 名称 编码
	 * @param query.区名称
	 * @return
	 */
	public MerchantCityQuery findCodeByArea(MerchantCityQuery query);
	
	/**
	 * 查询 市 省 名称编码
	 * @param query.市名称
	 * @return
	 */
	public MerchantCityQuery findCodeByCity(MerchantCityQuery query);
	
	/**
	 * 查询 省 名称编码
	 * @param query.省名称
	 * @return
	 */
	public MerchantCityQuery findCodeByProvince(MerchantCityQuery query);
}
