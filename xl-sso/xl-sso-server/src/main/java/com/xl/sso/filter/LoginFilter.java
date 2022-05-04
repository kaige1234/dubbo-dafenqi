package com.xl.sso.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.xl.sso.beans.AuthConst;
import com.xl.sso.beans.Token;
import com.xl.sso.service.TikietService;
import com.xl.sso.util.CookieUtil;
import com.xl.sso.util.Platform;

/**
 * sso认证中心会话过滤
 * 
 * @author wangchengbin
 * @date 2017年11月15日-下午6:09:48
 */

public class LoginFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String uri = request.getRequestURI();
		// 注销请求，放行
		if ("/logout".equals(uri)) {
			chain.doFilter(req, res);
			return;
		}
		// 错误请求，放行
		if ("/erro".equals(uri)) {
			chain.doFilter(req, res);
			return;
		}
		// 校验令牌
		if (StringUtils.equals(uri, "/verifyToken")) {
			chain.doFilter(req, res);
			return;
		}
		TikietService tikietService = Platform.getBean("tikietService");
		String logintoken = CookieUtil.getCookie(request, AuthConst.TOKEN);
		Token tokenObj = tikietService.getToken(logintoken);
		if (tokenObj != null) {
			String clientUrl = request.getParameter(AuthConst.CLIENT_URL);
			String token = tokenObj.getTokenId();
			if (clientUrl != null && !"".equals(clientUrl)) {
				response.sendRedirect(clientUrl + "?" + AuthConst.TOKEN + "=" + token);
				return;
			}
			if (!"/success".equals(uri)) {
				response.sendRedirect("/success");
				return;
			}
			chain.doFilter(req, res);
			return;
		}
		
//		if(uri.startsWith("/css")){
//			chain.doFilter(req, res);
//			return;
//		}
		// 登录请求，放行
		if ("/".equals(uri) || "/login".equals(uri)) {
			chain.doFilter(req, res);
			return;
		}
		// 其他请求，拦截
		response.sendRedirect("/");
	}

	public void destroy() {
	}
}