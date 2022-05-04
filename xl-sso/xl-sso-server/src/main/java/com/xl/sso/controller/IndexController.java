package com.xl.sso.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xl.sso.beans.AuthConst;

/**
 * 认证中心页面显示控制器
 * @author wangchengbin
 * @date  2017年11月15日-下午6:10:08
 */
@Controller
public class IndexController {
	/**
	 * 登录页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String index(HttpServletRequest request, Model model) {
		model.addAttribute(AuthConst.CLIENT_URL, request.getParameter(AuthConst.CLIENT_URL));
		return "login";
	}

	/**
	 * 登录成功页面
	 * @return
	 */
	@RequestMapping("/success")
	public String success() {
		return "success";
	}
	/**
	 * 错误页面
	 * @return
	 */
	@RequestMapping("/error")
	public String error() {
		return "error";
	}
}