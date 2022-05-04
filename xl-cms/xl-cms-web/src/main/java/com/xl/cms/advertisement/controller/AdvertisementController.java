package com.xl.cms.advertisement.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.xl.cms.advertisement.model.Advertisement;
import com.xl.cms.advertisement.query.AdvertisementQuery;
import com.xl.cms.advertisement.query.MerchantCityQuery;
import com.xl.cms.advertisement.service.AdvertisementService;
import com.xl.cms.advertisement.service.MerchantCityService;
import com.xl.commons.data.Result;
import com.xl.commons.web.BaseController;

import net.sf.ehcache.Element;

/**
 * 广告控制层
 * 
 * @author jianqiang.yuan
 *
 */
@Controller
@RequestMapping("/advertisement/advertisement")
public class AdvertisementController extends BaseController {
	@Resource
	private AdvertisementService advertisementService;
	@Resource
    private MerchantCityService merchantCityService;
	
	private static final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);
	/**
	 * 前端接口--查询首页广告
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findAdByGprs")
	@ResponseBody
	public Result findAdByGprs(HttpServletRequest request, HttpServletResponse response){
		if (null == this.getIntParam(request, "nPositionCode")) {
			return Result.getError("系统参数错误-缺少广告类型");
		}
		AdvertisementQuery queryAdvertisement =  new AdvertisementQuery();
		MerchantCityQuery queryMerchant = new MerchantCityQuery();
		try {
//			String strProvinceCode = this.getParam(request, "strProvinceCode", "");
			String strProvinceCode = request.getParameter("strProvinceCode")==null ? "" :request.getParameter("strProvinceCode");
//			String strProvinceName = this.getParam(request, "strProvinceName", "");
			String strProvinceName = request.getParameter("strProvinceName")==null ? "" :request.getParameter("strProvinceName");
//			String strCityCode = this.getParam(request, "strCityCode", "");
			String strCityCode = request.getParameter("strCityCode")==null ? "" :request.getParameter("strCityCode");
//			String strCityName = this.getParam(request, "strCityName", "");
			String strCityName =  request.getParameter("strCityName")==null ? "" :request.getParameter("strCityName");
//			String strAreaCode = this.getParam(request, "strAreaCode", "");
			String strAreaCode = request.getParameter("strAreaCode")==null ? "" :request.getParameter("strAreaCode");
//			String strAreaName = this.getParam(request, "strAreaName", "");
			String strAreaName = request.getParameter("strAreaName")==null ? "" :request.getParameter("strAreaName");
			// 7 首页轮播图   10 首页 icon  8 新增广告位
			Integer nPositionCode=this.getIntParam(request, "nPositionCode");
			Integer pageNum=this.getIntParam(request, "pageNum");
			Integer pageSize=this.getIntParam(request, "pageSize");
			//查询首页尾部广告
			if(nPositionCode.equals("1")){
				queryAdvertisement.setStrCityCode(strCityCode);
				queryAdvertisement.setStrCityName(strCityName);
				Advertisement advertisement = advertisementService.findPageByAppGprBottomCity(queryAdvertisement);
				if(advertisement==null){
					//城市尾部广告没有找到--继续查询国家级
					advertisement = advertisementService.findPageByAppGprBottom(queryAdvertisement);
					if(advertisement==null){
						return Result.getError("查询首页尾部广告错误");
					}
				}
				return Result.getOk("pageInfo", advertisement);
			}
			//利用地址 查询  对应编码code
			if(null!=strAreaName && strAreaName.length()>0){
				// 查询地区code
				queryMerchant.setStrAreaName(strAreaName);
				queryMerchant = merchantCityService.findCodeByArea(queryMerchant);
				strAreaCode = queryMerchant.getStrAreaCode();
				strAreaName = queryMerchant.getStrAreaName();
				strCityCode = queryMerchant.getStrCityCode();
				strCityName = queryMerchant.getStrCityName();
				strProvinceCode = queryMerchant.getStrProvinceCode();
				strProvinceName = queryMerchant.getStrProvinceName();
			}else if(null!=strCityName && strCityName.length()>0){
				// 查询城市code
				queryMerchant.setStrCityName(strCityName);
				queryMerchant = merchantCityService.findCodeByCity(queryMerchant);
				strCityCode = queryMerchant.getStrCityCode();
				strCityName = queryMerchant.getStrCityName();
				strProvinceCode = queryMerchant.getStrProvinceCode();
				strProvinceName = queryMerchant.getStrProvinceName();
			}else if(null!=strProvinceName && strProvinceName.length()>0){
				// 查询省code
				queryMerchant.setStrProvinceName(strProvinceName);
				queryMerchant = merchantCityService.findCodeByProvince(queryMerchant);
				strProvinceCode = queryMerchant.getStrProvinceCode();
				strProvinceName = queryMerchant.getStrProvinceName();
			}else{
				//定位失败，全部为空的时候
				return Result.getError("系统参数错误-定位失败");
			}
			Element cache = cacheUtil.get("AdvertisementController.findAdByGprs." + this.getLongParam(request, "lId"));
			if (cache != null) {
				return Result.getOK("获取成功").putData("advertisement", cache.getObjectValue());
			} else {
				
			}
			queryAdvertisement.setStrCityCode(strCityCode);
			queryAdvertisement.setStrCityName(strCityName);
			queryAdvertisement.setStrProvinceCode(strProvinceCode);
			queryAdvertisement.setStrProvinceName(strProvinceName);
			queryAdvertisement.setStrAreaCode(strAreaCode);
			queryAdvertisement.setStrAreaName(strAreaName);
			queryAdvertisement.setnPositionCode(nPositionCode);
			//第一查询 地区 广告
			PageInfo<Advertisement> data = advertisementService.findPageByAppGprsArea(queryAdvertisement, pageSize, pageNum);
			if(data.getTotal()<=0){
				//第二  地区广告为空，查询市级广告
				 data = advertisementService.findPageByAppGprsCity(queryAdvertisement, pageSize, pageNum);
				 if(data.getTotal()<=0){
					 //第三 市级广告为空 ，查询省级广告
					 data = advertisementService.findPageByAppGprsProvince(queryAdvertisement, pageSize, pageNum);
					 if(data.getTotal()<=0){
						 //第四 省级广告为空 ， 查询国家级广告
						 data = advertisementService.findPageByAppGprsCountry(queryAdvertisement, pageSize, pageNum);
						 if(data.getTotal()<=0){
							 logger.info("===========获取首页广告失败=================");
							 return Result.getError("获取首页广告失败");
						 }
					}
				}
			}
			return Result.getOk("pageInfo",data);
		}catch(Exception e){
			logger.info("获取首页地区广告出错"+e);
		}
		return Result.getError("获取首页地区广告出错");
	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Result list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageInfo<Advertisement> pageInfo = this.advertisementService.findPage(
				this.getQuery(request, AdvertisementQuery.class), this.getPageSize(request),
				this.getPageNumber(request));
		return Result.getOk("pageInfo", pageInfo);
	}

	/**
	 * 新增
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Result add(HttpServletRequest request, HttpServletResponse response, Advertisement advertisement)
			throws Exception {
		Advertisement obj = this.advertisementService.add(advertisement);
		return Result.getOK("添加成功").putData("advertisement", obj);
	}

	/**
	 * 查看
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Result get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Element cache = cacheUtil.get("AdvertisementController.get." + this.getLongParam(request, "lId"));
		if (cache != null) {
			return Result.getOK("获取成功").putData("advertisement", cache.getObjectValue());
		} else {
			Advertisement obj = this.advertisementService.findById(this.getLongParam(request, "lId"));
			cacheUtil.put("AdvertisementController.get." + this.getLongParam(request, "lId"), obj);
			return Result.getOK("获取成功").putData("advertisement", obj);
		}
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Result del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int rs = this.advertisementService.deleteById(this.getLongParam(request, "lId"));
		if (rs > 0) {
			return Result.getOK("删除成功");
		}
		return Result.getError("删除失败");
	}

}
