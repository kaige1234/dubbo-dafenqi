package com.xl.sso.client.constant;

/**
 * 授权过程中涉及到的常量字符串
 * @author wangchengbin
 * @date  2017年11月17日-下午3:18:10
 */
public interface AuthConst {
	// 登录中心url
	public static String LOGIN_URL = "loginUrl";
	// 子系统登录成功的URL
	public static String SUCCESS_URL = "successUrl";
	// 注销url
	public static String LOGOUT_URL = "logoutUrl";
	// 客户端url
	public static String CLIENT_URL = "clientUrl";
	//校验票据url
	public static String VERIFY_TOKEN_URL = "verifyUrl";
	// 授权令牌
	public static String TOKEN = "token";
	// 注销请求
	public static String LOGOUT_REQUEST = "logoutRequest";
}