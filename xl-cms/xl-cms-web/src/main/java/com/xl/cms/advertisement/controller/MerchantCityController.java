package com.xl.cms.advertisement.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.xl.cms.advertisement.model.MerchantCity;
import com.xl.cms.advertisement.query.MerchantCityQuery;
import com.xl.cms.advertisement.service.MerchantCityService;
import com.xl.commons.data.Result;
import com.xl.commons.web.BaseController;

import net.sf.ehcache.Element;


/**
 *	收息活动控制层
 * @author liufeng
 *
 */
@Controller
@RequestMapping("/advertisement/merchantCity")
public class MerchantCityController extends BaseController
{
		@Resource
	    private MerchantCityService merchantCityService;
	    
		/**
		 * 分页查询
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
	    @RequestMapping(value = "/list")
	    @ResponseBody
	    public Result  list(HttpServletRequest request, HttpServletResponse response) throws Exception{
	    	PageInfo<MerchantCity> pageInfo=this.merchantCityService.findPage(this.getQuery(request, MerchantCityQuery.class), this.getPageSize(request), this.getPageNumber(request));
	    	return Result.getOk("pageInfo", pageInfo);
	    }
	    
	    /**
		 * 新增
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
	    @RequestMapping(value = "/add")
	    @ResponseBody
	    public Result add(HttpServletRequest request, HttpServletResponse response,MerchantCity merchantCity)throws Exception{
	    	MerchantCity obj = this.merchantCityService.add(merchantCity);
	    	return Result.getOK("添加成功").putData("advertisement", obj);
	    }
	    
	    
	    /**
		 * 查看
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
	    @RequestMapping(value = "/get")
	    @ResponseBody
	    public Result get(HttpServletRequest request, HttpServletResponse response)throws Exception{
	    	Element cache=cacheUtil.get("MerchantCityController.get."+this.getLongParam(request, "lId"));
	    	if(cache!=null){
	    		return Result.getOK("获取成功").putData("advertisement", cache.getObjectValue()); 
	    	}else{
	    		MerchantCity obj= this.merchantCityService.findById(this.getLongParam(request, "lId"));
	    		cacheUtil.put("MerchantCityController.get."+this.getLongParam(request, "lId"), obj);
	    		return Result.getOK("获取成功").putData("advertisement", obj);
	    	}
	    }
	    
	    /**
	     * 删除
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    @RequestMapping(value = "/del")
	    @ResponseBody
	    public Result del(HttpServletRequest request, HttpServletResponse response)throws Exception{
	    	int rs= this.merchantCityService.delete(this.getLongParam(request, "lId"));
	    	if(rs>0){
	    		return Result.getOK("删除成功");
	    	}
	    	return Result.getError("删除失败");
	    }
	    
}
