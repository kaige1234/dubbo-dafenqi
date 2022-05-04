package com.xl.cms.advertisement.test;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;
import com.xl.cms.advertisement.model.MerchantCity;
import com.xl.cms.advertisement.query.MerchantCityQuery;
import com.xl.cms.advertisement.service.MerchantCityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:dubbo\\*.xml"})
public class MerchantCityServiceTest {
	@Autowired
	private MerchantCityService merchantCityService;
	@Test
	public void findById(){
		MerchantCity merchantCity =  merchantCityService.findById(190l);
		System.out.println(merchantCity);
	}
	/**
	 * 分页测试
	 */
	@Test
	public void findPageByMerchantCityTest(){
		PageInfo<MerchantCity> merchantCitys  =  merchantCityService.findPage(new MerchantCityQuery(), 2, 2);
		for(MerchantCity merchantCity:merchantCitys.getList()){
			System.out.println("----------------------------------------"+merchantCity.getlId());
		}
	}
	
	/**
	 * 地区编码code查询测试
	 */
	@Test
	public void findPageByAppGprsArea(){
		MerchantCityQuery query = new MerchantCityQuery();
		query.setStrAreaName("东城区");
		MerchantCityQuery merchantCity =  merchantCityService.findCodeByArea(query);
		System.out.println("----------------------------------------"+merchantCity);
		System.out.println("----------------------------------------"+merchantCity.getStrAreaCode());
		System.out.println("----------------------------------------"+merchantCity.getStrAreaName());
		System.out.println("----------------------------------------"+merchantCity.getStrCityCode());
		System.out.println("----------------------------------------"+merchantCity.getStrCityName());
		System.out.println("----------------------------------------"+merchantCity.getStrProvinceCode());
		System.out.println("----------------------------------------"+merchantCity.getStrProvinceName());
	}

	/**
	 * 市级编码code查询测试
	 */
	@Test
	public void findPageByAppGprsCity(){
		MerchantCityQuery query = new MerchantCityQuery();
		query.setStrCityName("北京市");
		MerchantCityQuery merchantCity =  merchantCityService.findCodeByCity(query);
		System.out.println("----------------------------------------"+merchantCity);
		System.out.println("----------------------------------------"+merchantCity.getStrCityCode());
		System.out.println("----------------------------------------"+merchantCity.getStrCityName());
		System.out.println("----------------------------------------"+merchantCity.getStrProvinceCode());
		System.out.println("----------------------------------------"+merchantCity.getStrProvinceName());
	}

	/**
	 * 省级编码code查询测试
	 */
	@Test
	public void findPageByAppGprsProvince(){
		MerchantCityQuery query = new MerchantCityQuery();
		query.setStrProvinceName("北京市");
		MerchantCityQuery merchantCity =  merchantCityService.findCodeByProvince(query);
		System.out.println("----------------------------------------"+merchantCity);
		System.out.println("----------------------------------------"+merchantCity.getStrProvinceCode());
		System.out.println("----------------------------------------"+merchantCity.getStrProvinceName());
	}
}
