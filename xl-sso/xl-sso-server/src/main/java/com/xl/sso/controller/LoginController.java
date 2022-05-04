package com.xl.sso.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xl.sso.beans.AuthConst;
import com.xl.sso.beans.Token;
import com.xl.sso.service.LoginService;
import com.xl.sso.service.TikietService;
import com.xl.sso.util.HttpUtil;

/**
 * 登录和注销控制器
 * 
 * @author wangchengbin
 * @date 2017年11月15日
 */
@Controller
public class LoginController {
	
	@Resource
	private TikietService tikietService;
	@Resource
	private LoginService loginService;
	/**
	 * 登录
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model, HttpServletResponse response) {
		// 验证用户
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		Map<String, String> info = loginService.login(userName, password);
		if (info!=null) { 
			model.addAttribute("error", "user not exist.");
			return "redirect:/";
		}
		// 授权
		String token = UUID.randomUUID().toString();
		Cookie cookie = new Cookie(AuthConst.TOKEN, token);
		cookie.setDomain("sso.dafy.test.com");
		response.addCookie(cookie);

		Token tokenObj = new Token(token);
		tikietService.addToken(tokenObj);
		// 子系统跳转过来的登录请求，授权、存储后，跳转回去
		String clientUrl = request.getParameter(AuthConst.CLIENT_URL);
		if (clientUrl != null && !"".equals(clientUrl)) {
			// 返回票据到子系统
			return "redirect:" + clientUrl + "?" + AuthConst.TOKEN + "=" + token;
		}

		return "redirect:/success";
	}

	/**
	 * 注销
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {

		String logintoken = "";
		Cookie[] cookies = request.getCookies();
		for (Cookie ck : cookies) {
			if (ck.getName().equals("token")) {
				logintoken = ck.getValue();
			}
		}

		Token tokenObj = tikietService.getToken(logintoken);
		if (logintoken != null && !logintoken.equals("")) {
			tikietService.removeToken(logintoken);
		}

		List<String> list = tokenObj.getClientUrl();
		if (list != null && list.size() > 0) {
			Map<String, String> params = new HashMap<String, String>();
			params.put(AuthConst.LOGOUT_REQUEST, tokenObj.getTokenId());
			for (String url : list) {
				HttpUtil.post(url, params);
			}
		}

		return "redirect:/";
	}

	/**
	 * 校验票据,并返回用户登录信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/verifyToken")
	@ResponseBody
	public String verifyToken(HttpServletRequest request) {
		String verifyToken = request.getParameter(AuthConst.TOKEN);
		String clientUrl = request.getParameter(AuthConst.CLIENT_URL);

		Token token = tikietService.getToken(verifyToken);
		if (token != null) {
			if (StringUtils.isNotBlank(clientUrl)) {
				token.getClientUrl().add(clientUrl);
			}
			return "true";
		} else {
			return "false";
		}
	}
}