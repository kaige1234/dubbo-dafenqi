package com.xl.commons.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
/**
 * request获取参数过滤器，去除带有javascript的代码，避免出现跨域攻击
 * @author liufeng
 *
 */
public class ScriptFilter implements Filter
{



	public void init(FilterConfig filterConfig) throws ServletException
	{
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException
	{
		request = new ScriptRequestWrapper((HttpServletRequest)request);
		filterChain.doFilter(request, response);
	}
	public void destroy()
	{
	}
}
