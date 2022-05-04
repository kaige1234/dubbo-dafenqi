package com.xl.cms.advertisement.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xl.cms.advertisement.dao.MerchantCityDao;
import com.xl.cms.advertisement.model.MerchantCity;
import com.xl.cms.advertisement.query.MerchantCityQuery;
import com.xl.cms.advertisement.service.MerchantCityService;
import com.xl.commons.service.BaseService;


@Service
public class MerchantCityServiceImpl extends BaseService<MerchantCityDao,MerchantCity> implements MerchantCityService {

	
	private static final Logger logger = LoggerFactory.getLogger(MerchantCityServiceImpl.class);
	
	@Resource
	@Qualifier("merchantCityDao")
	@Override
	protected void setDao(MerchantCityDao dao) {
		this.dao=dao;
	}
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	@Cacheable(value="appCache",keyGenerator="keyGenerator")
	public MerchantCity findById(Long id){
		return this.dao.findById(id);
	}
	
	/**
	 * 根据查询条件分页查询
	 * @param query
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	@Transactional(readOnly=true)
	@Cacheable(value="appCache",keyGenerator="keyGenerator")
	public PageInfo<MerchantCity> findPage(MerchantCityQuery query,Integer pageSize,Integer pageNum){
		PageHelper.startPage(pageNum, pageSize);//设置分页条件
		return new PageInfo<MerchantCity>( this.dao.findPageMerchantCity(query));
	}

	
	@Override
	@Transactional
	public MerchantCity add(MerchantCity merchantCity) {
		this.dao.insert(merchantCity);
		 return merchantCity;
	}
	
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	@Transactional
	public int delete(Long id){
		return this.dao.deleteById(id);
	}
	
	/**
	 * 查询区 市 省 名称及编码
	 */
	@Transactional(readOnly=true)
	@Cacheable(value="appCache",keyGenerator="keyGenerator")
	@Override
	public MerchantCityQuery findCodeByArea(MerchantCityQuery query) {
		return this.dao.findCodeByArea(query);
	}
	
	/**
	 * 查询市 省  名称及编码
	 */
	@Transactional(readOnly=true)
	@Cacheable(value="appCache",keyGenerator="keyGenerator")
	@Override
	public MerchantCityQuery findCodeByCity(MerchantCityQuery query) {
		return this.dao.findCodeByCity(query);
	}
	
	/**
	 *查询 省名称及编码 
	 */
	@Transactional(readOnly=true)
	@Cacheable(value="appCache",keyGenerator="keyGenerator")
	@Override
	public MerchantCityQuery findCodeByProvince(MerchantCityQuery query) {
		return this.dao.findCodeByProvince(query);
	}

}
