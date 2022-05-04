package com.xl.cms.advertisement.service;

import com.github.pagehelper.PageInfo;
import com.xl.cms.advertisement.model.Advertisement;
import com.xl.cms.advertisement.query.AdvertisementQuery;

/**
 * 收息活动服务
 * 
 * @author liufeng
 *
 */
public interface AdvertisementService {

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	public Advertisement findById(Long id);

	/**
	 * 根据主键删除
	 * 
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);

	/**
	 * 新增
	 * 
	 * @param advertisementActivity
	 * @return
	 */
	public Advertisement add(Advertisement advertisementActivity);

	/**
	 * 根据查询条件分页查询
	 * 
	 * @param query
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	public PageInfo<Advertisement> findPage(AdvertisementQuery query, Integer pageSize, Integer pageNum);

	/**
	 * 查询底部广告（）
	 * @param query
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	public Advertisement findPageByAppGprBottomCity(AdvertisementQuery query);

	/**
	 *  查询底部广告（）
	 * @param query
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	public Advertisement findPageByAppGprBottom(AdvertisementQuery query);

	/**
	 * 查询 地区广告
	 * @param query
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	public PageInfo<Advertisement> findPageByAppGprsArea(AdvertisementQuery query, Integer pageSize,
			Integer pageNum);

	/**
	 * 查询 市级广告
	 * @param query
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	public PageInfo<Advertisement> findPageByAppGprsCity(AdvertisementQuery query, Integer pageSize, Integer pageNum);

	/**
	 * 查询省级广告
	 * @param query
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	public PageInfo<Advertisement> findPageByAppGprsProvince(AdvertisementQuery query, Integer pageSize,
			Integer pageNum);

	/**
	 * 查询国家级广告
	 * @param query
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	public PageInfo<Advertisement> findPageByAppGprsCountry(AdvertisementQuery query, Integer pageSize,
			Integer pageNum);
}
