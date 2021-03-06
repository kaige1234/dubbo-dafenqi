package com.xl.sso.util;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 平台类<br>
 * 记录平台的配置信息供各个应用使用，并加载并初始化各个应用。
 * 
 * @author liufeng
 * @version 2.0
 * @since 1.0
 */
public class Platform implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	private static ServletContext servletContext;
	
	public static void init(ServletContext _servletContext) {
		servletContext = _servletContext;
	}
	
	public static ServletContext getServletContext() throws Exception {
		return servletContext;
	}

	/**
	 * ApplicationContextAware接口的context注入函数.
	 */
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		if (applicationContext == null)
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}
}
