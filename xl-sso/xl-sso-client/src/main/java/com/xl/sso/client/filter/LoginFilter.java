package com.xl.sso.client.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xl.sso.client.constant.AuthConst;
import com.xl.sso.client.storage.SessionStorage;

/**
 * 客户端登录filter
 * 
 * @author wangchengbin
 * @date 2017年11月15日-下午6:09:33
 */
public class LoginFilter implements Filter {
	private FilterConfig config;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}
	

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String clientUrl = request.getRequestURL().toString();
		if (SessionStorage.INSTANCE.getSessionToken(session.getId()) != null) {
			chain.doFilter(request, response);
			return;
		}
		response.sendRedirect(config.getInitParameter(AuthConst.LOGIN_URL) + "?" + AuthConst.CLIENT_URL + "=" + clientUrl);
	}

	
	
	@Override
	public void destroy() {
	}
}