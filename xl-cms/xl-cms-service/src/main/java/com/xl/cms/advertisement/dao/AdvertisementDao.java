package com.xl.cms.advertisement.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xl.cms.advertisement.model.Advertisement;
import com.xl.cms.advertisement.query.AdvertisementQuery;
import com.xl.commons.annotation.MyBatisDao;
import com.xl.commons.service.BaseDao;

@MyBatisDao
public interface AdvertisementDao extends BaseDao<Advertisement> {

	public List<Advertisement> findPageAdvertisement(@Param("query") AdvertisementQuery query);

	public Advertisement findPageByAppGprBottomCity(@Param("query") AdvertisementQuery query);

	public Advertisement findPageByAppGprBottom(@Param("query") AdvertisementQuery query);

	public List<Advertisement> findPageByAppGprsArea(@Param("query") AdvertisementQuery query);

	public List<Advertisement> findPageByAppGprsCity(@Param("query") AdvertisementQuery query);

	public List<Advertisement> findPageByAppGprsProvince(@Param("query") AdvertisementQuery query);

	public List<Advertisement> findPageByAppGprsCountry(@Param("query") AdvertisementQuery query);

	public Advertisement findById(@Param("lId") Long lId);

	public Integer add(@Param("obj") Advertisement advertisementActivity);

	public Integer deleteById(@Param("lId") Long lId);
}
