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
 * 客户端注销filter
 * 
 * @author wangchengbin
 * @date 2017年11月15日-下午6:10:31
 */
public class LogoutFilter implements Filter {
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
		String logoutUrl = config.getInitParameter(AuthConst.LOGOUT_URL);
		String token = (String) session.getAttribute(AuthConst.TOKEN);
		if ("/logout".equals(request.getRequestURI())) {
			response.sendRedirect(logoutUrl);
			return;
		}
		token = request.getParameter(AuthConst.LOGOUT_REQUEST);
		if (token != null && !"".equals(token)) {
			session = SessionStorage.INSTANCE.getTokenSession(token);
			SessionStorage.INSTANCE.removeTokenSession(token);
			SessionStorage.INSTANCE.removeSessionToken(session.getId());
		}
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}

}