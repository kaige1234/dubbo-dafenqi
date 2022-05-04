package com.xl.es.data.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xl.es.data.doc.Merchant;
import com.xl.es.data.doc.Result;
import com.xl.es.data.service.ElasticSearchPage;
import com.xl.es.data.service.biz.MerchantService;

/**
 * 商户检索服务
 * 
 * @author liufeng
 *
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController {

	@Resource
	private MerchantService merchantService;

	/**
	 * 创建商户角色
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createMapping")
	@ResponseBody
	public Result createMapping() throws Exception {
		merchantService.createMapping();
		return Result.getOK();
	}

	/**
	 * 保存商户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/saveMerchant")
	@ResponseBody
	public Result saveMerchant(@RequestBody Merchant merchant) throws Exception {
		boolean result = this.merchantService.saveMerchant(merchant);
		if (result) {
			return Result.getOK();
		} else {
			return Result.getError();
		}
	}

	/**
	 * 查询商户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findMerchant")
	@ResponseBody
	public Result findMerchant(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String queryType = request.getParameter("queryType");
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null){
			pageNum="0";
		}
		Map<String,Object> param = this.getParamMap(request);
		ElasticSearchPage<Merchant> page = this.merchantService.findMerchant(queryType, Integer.parseInt(pageNum),param);
		return Result.getOk("page", page);
	}
	
	private Map<String,Object> getParamMap(HttpServletRequest request)throws Exception{
		Enumeration paramets = request.getParameterNames();
		Map<String,Object> param=new HashMap<String,Object>();
		while (paramets.hasMoreElements()) {
			String key = (String) paramets.nextElement();
			String value= request.getParameter(key);
			if(request.getMethod().equals("GET")){
				if(value!=null){
					value = new String(value.getBytes("iso-8859-1"),"UTF-8");
				}
			}
			param.put(key, value);
		}
		return param;
	}
}
