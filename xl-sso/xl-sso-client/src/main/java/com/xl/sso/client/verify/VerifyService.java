package com.xl.sso.client.verify;

import java.util.HashMap;
import java.util.Map;

import com.xl.sso.client.constant.AuthConst;
import com.xl.sso.client.util.HttpUtil;

public class VerifyService {
	/**
	 * 到认证中心校验票据
	 * 
	 * @param token
	 * @return
	 */
	public static boolean verify(String serverUrl,String token, String clientUrl) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(AuthConst.TOKEN, token);
		params.put(AuthConst.CLIENT_URL, clientUrl);
		String post = HttpUtil.doPost(serverUrl, null, params);
		return Boolean.parseBoolean(post);
	}
}
