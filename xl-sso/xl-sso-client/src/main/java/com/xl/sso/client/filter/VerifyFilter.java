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
import com.xl.sso.client.verify.VerifyService;

/**
 * 票据认证filter
 * 
 * @author wangchengbin
 * @date 2017年11月15日-下午6:09:33
 */
public class VerifyFilter implements Filter {
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
		String token = request.getParameter(AuthConst.TOKEN);
		String serviceUrl = config.getInitParameter(AuthConst.VERIFY_TOKEN_URL);
		if (token != null) {
			HttpSession tokensession = SessionStorage.INSTANCE.getTokenSession(token); 
			if (tokensession == null) {
				if (VerifyService.verify(serviceUrl,token, clientUrl)) {
					SessionStorage.INSTANCE.setTokenSession(token, session);
					SessionStorage.INSTANCE.setSessionToken(session.getId(), token);
					chain.doFilter(req, res);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}