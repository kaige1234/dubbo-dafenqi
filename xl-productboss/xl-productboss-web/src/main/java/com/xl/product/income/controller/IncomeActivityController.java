package com.xl.product.income.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageInfo;
import com.xl.commons.data.Result;
import com.xl.commons.web.BaseController;
import com.xl.product.income.model.IncomeActivity;
import com.xl.product.income.query.IncomeActivityQuery;
import com.xl.product.income.service.IncomeActivityService;

/**
 *	收息活动控制层
 * @author liufeng
 *
 */
@Controller
@RequestMapping("/user/income/incomeActivity")
public class IncomeActivityController extends BaseController  {
		@Autowired
	    private IncomeActivityService incomeActivityService;
		
		private static final String redirectUrl="redirect:/user/income/incomeActivity/list.do";
		
		/**
		 * 分页查询
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
	    @RequestMapping(value = "/list")
	    public String list(RedirectAttributes redirectattributes) throws Exception{
	    	return "/income/incomeActivity/list";
	    }
	    
	    /**
		 * 活动管理异步请求分页查询
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
	    @RequestMapping("/listData")
	    @ResponseBody
	    public Result listData(HttpServletRequest request,IncomeActivityQuery query){
	    	PageInfo<IncomeActivity> pageInfo=this.incomeActivityService.findPage(query, query.getPageSize(), query.getPageNumber());
	    	return Result.getOk("pageInfo", pageInfo);
	    }
	    
	    /**
		 * 查看
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
	    @RequestMapping(value = "/view")
	    public String view(HttpServletRequest request, HttpServletResponse response,Model model)throws Exception{
	    	IncomeActivity incomeactivity = this.incomeActivityService.getById(this.getLongParam(request, "lId"));
	    	model.addAttribute("incomeactivity", incomeactivity);
	    	return "income/incomeActivity/view";
	    }
	    
	    /**
	     * 删除
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    @RequestMapping(value = "/drop")
	    @ResponseBody
	    public Result drop(HttpServletRequest request, HttpServletResponse response)throws Exception{
	    	int rs= this.incomeActivityService.delete(this.getLongParam(request, "lId"));
	    	if(rs>0){
	    		return Result.getOK();
	    	}
	    	return Result.getError();
	    }
	    
	    /**
		 * 修改回显页面
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
	    @RequestMapping(value = "/getEdit")
	    public String getEdit(HttpServletRequest request, HttpServletResponse response,Model model)throws Exception{
	    	IncomeActivity incomeactivity = this.incomeActivityService.getById(this.getLongParam(request, "lId"));
	    	model.addAttribute("incomeactivity", incomeactivity);
	    	return "income/incomeActivity/edit";
	    }
	    
	    /**
		 * 修改
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
	    @RequestMapping(value = "/edit")
	    public String edit(HttpServletRequest request, HttpServletResponse response,IncomeActivity incomeActivity,RedirectAttributes redirectattributes)throws Exception{
	    	int rs= this.incomeActivityService.update(incomeActivity);
	    	if(rs>0){
	    		redirectattributes.addFlashAttribute("result","修改成功");
	    	}else{
	    		redirectattributes.addFlashAttribute("result","修改失败");
	    	}
	    	return redirectUrl;
	    }
	    
	    /**
		 * 同步新增
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
	    @RequestMapping(value = "/add")
	    public String add(HttpServletRequest request, HttpServletResponse response,IncomeActivity incomeActivity,RedirectAttributes redirectattributes)throws Exception{
	    	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss"); 
	    	Date date = new Date();
	    	String nowTime1 = sdf1.format(date);
	    	incomeActivity.setlId(Long.parseLong(nowTime1));
	    	int  result = this.incomeActivityService.save(incomeActivity);
	    	if(result>0){
	    		redirectattributes.addFlashAttribute("result","添加成功");
	    	}else{
	    		redirectattributes.addFlashAttribute("result","添加失败");
	    	}
	    	return redirectUrl;
	    }
}
