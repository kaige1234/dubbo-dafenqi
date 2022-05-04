package com.xl.sso.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * cookie工具
 * 
 * @author liufeng
 * @date 2017年11月17日-下午3:19:02
 */
public class CookieUtil {

	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie ck : cookies) {
				if (ck.getName().equals(name)) {
					return ck.getValue();
				}
			}
		}
		return null;
	}
}