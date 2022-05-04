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
import com.xl.cms.advertisement.dao.AdvertisementDao;
import com.xl.cms.advertisement.model.Advertisement;
import com.xl.cms.advertisement.query.AdvertisementQuery;
import com.xl.cms.advertisement.service.AdvertisementService;
import com.xl.commons.service.BaseService;
/**
 * 广告服务
 * @author jianqiang.yuan
 *
 */
@Service
public class AdvertisementServiceImpl extends BaseService<AdvertisementDao,Advertisement> implements AdvertisementService {

	
	@Resource
	@Qualifier("advertisementDao")
	@Override
	protected void setDao(AdvertisementDao dao) {
		this.dao=dao;
	}
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	@Cacheable(value="appCache",keyGenerator="keyGenerator")
	public Advertisement findById(Long id){
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
	public PageInfo<Advertisement> findPage(AdvertisementQuery query,Integer pageSize,Integer pageNum){
		PageHelper.startPage(pageNum, pageSize);//设置分页条件
		return new PageInfo<Advertisement>( this.dao.findPageAdvertisement(query));
	}

	
	@Override
	@Transactional
	public Advertisement add(Advertisement advertisement) {
		this.dao.insert(advertisement);
		 return advertisement;
	}
	
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	@Transactional
	public int deleteById(Long id){
		return this.dao.deleteById(id);
	}
	
	/**
	 * app接口 --- 查询区级底部广告
	 */
	@Transactional(readOnly=true)
	@Cacheable(value="appCache",keyGenerator="keyGenerator")
	@Override
	public Advertisement findPageByAppGprBottomCity(AdvertisementQuery query) {
		return this.dao.findPageByAppGprBottomCity(query);
	}
	
	/**
	 * app接口 --- 查询国家级底部广告
	 */
	@Transactional(readOnly=true)
	@Cacheable(value="appCache",keyGenerator="keyGenerator")
	@Override
	public Advertisement findPageByAppGprBottom(AdvertisementQuery query) {
		return this.dao.findPageByAppGprBottom(query);
	}
	
	/**
	 * app接口 --- 查询地区广告
	 */
	@Transactional(readOnly=true)
	@Cacheable(value="appCache",keyGenerator="keyGenerator")
	@Override
	public PageInfo<Advertisement> findPageByAppGprsArea(AdvertisementQuery query, Integer pageSize,
			Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);//设置分页条件
		return new PageInfo<Advertisement>( this.dao.findPageByAppGprsArea(query));
	}
	
	/**
	 * app接口 --- 查询市级广告
	 */
	@Transactional(readOnly=true)
	@Cacheable(value="appCache",keyGenerator="keyGenerator")
	@Override
	public PageInfo<Advertisement> findPageByAppGprsCity(AdvertisementQuery query, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);//设置分页条件
		return new PageInfo<Advertisement>( this.dao.findPageByAppGprsCity(query));
	}
	
	/**
	 * app接口 --- 查询省级广告
	 */
	@Transactional(readOnly=true)
	@Cacheable(value="appCache",keyGenerator="keyGenerator")
	@Override
	public PageInfo<Advertisement> findPageByAppGprsProvince(AdvertisementQuery query, Integer pageSize,
			Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);//设置分页条件
		return new PageInfo<Advertisement>( this.dao.findPageByAppGprsProvince(query));
	}
	
	/**
	 * app接口 --- 查询国家级广告
	 */
	@Transactional(readOnly=true)
	@Cacheable(value="appCache",keyGenerator="keyGenerator")
	@Override
	public PageInfo<Advertisement> findPageByAppGprsCountry(AdvertisementQuery query, Integer pageSize,
			Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);//设置分页条件
		return new PageInfo<Advertisement>( this.dao.findPageByAppGprsCountry(query));
	}

}
