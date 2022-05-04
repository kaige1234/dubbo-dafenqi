package com.xl.cms.advertisement.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;
import com.xl.cms.advertisement.model.Advertisement;
import com.xl.cms.advertisement.query.AdvertisementQuery;
import com.xl.cms.advertisement.service.AdvertisementService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:dubbo\\*.xml"})
public class AdvertisementServiceTest {
	@Autowired
	private AdvertisementService advertisementService;
	@Test
	public void findById(){
		Advertisement advertisement =  advertisementService.findById(190l);
		System.out.println(advertisement);
	}
	/**
	 * 分页测试
	 */
	@Test
	public void findPageByAdvertisementTest(){
		PageInfo<Advertisement> advertisements  =  advertisementService.findPage(new AdvertisementQuery(), 2, 2);
		for(Advertisement advertisement:advertisements.getList()){
			System.out.println("----------------------------------------"+advertisement.getlId());
		}
	}
	
	/**
	 * 底部广告查询测试
	 */
	@Test
	public void findPageByAppGprBottomCity(){
		AdvertisementQuery query = new AdvertisementQuery();
		query.setStrCityCode("130300");
		query.setStrCityName("秦皇岛市");
		query.setStrAreaCode("");
		query.setStrAreaName("");
		Advertisement advertisement  =  advertisementService.findPageByAppGprBottomCity(query);
		System.out.println("----------------------------------------"+advertisement);
	}

	/**
	 * 底部广告查询测试2
	 */
	@Test
	public void findPageByAppGprBottom(){
		Advertisement advertisement  =  advertisementService.findPageByAppGprBottom(new AdvertisementQuery());
		System.out.println("----------------------------------------"+advertisement);
	}

	/**
	 * 地区广告查询测试
	 */
	@Test
	public void findPageByAppGprsArea(){
		AdvertisementQuery query = new AdvertisementQuery();
		query.setnPositionCode(7);
		query.setStrAreaCode("110101");
		query.setStrAreaName("东城区");
		query.setStrProvinceCode("110000");
		query.setStrProvinceName("北京市");
		query.setStrCityCode("110000");
		query.setStrCityName("北京市");
		PageInfo<Advertisement> advertisements  =  advertisementService.findPageByAppGprsArea(query, 1, 1);
		for(Advertisement advertisement:advertisements.getList()){
			System.out.println("----------------------------------------"+advertisement.getlId());
		}
	}

	/**
	 * 市级广告查询测试
	 */
	@Test
	public void findPageByAppGprsCity(){
		AdvertisementQuery query = new AdvertisementQuery();
		query.setnPositionCode(7);
		query.setStrProvinceCode("110000");
		query.setStrProvinceName("北京市");
		query.setStrCityCode("110000");
		query.setStrCityName("北京市");
		PageInfo<Advertisement> advertisements  =  advertisementService.findPageByAppGprsCity(query, 1, 2);
		for(Advertisement advertisement:advertisements.getList()){
			System.out.println("----------------------------------------"+advertisement.getlId());
		}
	}

	/**
	 * 省级广告查询测试
	 */
	@Test
	public void findPageByAppGprsProvince(){
		AdvertisementQuery query = new AdvertisementQuery();
		query.setnPositionCode(7);
		query.setStrProvinceCode("110000");
		query.setStrProvinceName("北京市");
		PageInfo<Advertisement> advertisements  =  advertisementService.findPageByAppGprsProvince(query, 2, 2);
		for(Advertisement advertisement:advertisements.getList()){
			System.out.println("----------------------------------------"+advertisement.getlId());
		}
	}

	/**
	 * 国家级广告查询测试
	 */
	@Test
	public void findPageByAppGprsCountry(){
		AdvertisementQuery query = new AdvertisementQuery();
		query.setnPositionCode(7);
		PageInfo<Advertisement> advertisements  =  advertisementService.findPageByAppGprsCountry(query, 2, 2);
		for(Advertisement advertisement:advertisements.getList()){
			System.out.println("----------------------------------------"+advertisement.getlId());
		}
	}
}
