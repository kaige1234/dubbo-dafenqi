package com.xl.sso.client.storage;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * session存储用于注销
 * 
 * @author wangchengbin
 * @date 2017年11月17日-下午3:18:34
 */
public enum SessionStorage {
	INSTANCE;
	private Map<String, HttpSession> tokenSession = new HashMap<String, HttpSession>();// token=session
	private Map<String, String> seeionToken = new HashMap<String, String>();// session=token

	public void setSessionToken(String session, String token) {
		seeionToken.put(session, token);
	}

	public String getSessionToken(String session) {
		if (seeionToken.containsKey(session)) {
			return seeionToken.get(session);
		}
		return null;
	}

	public void removeSessionToken(String session) {
		seeionToken.remove(session);
	}

	public void setTokenSession(String token, HttpSession session) {
		tokenSession.put(token, session);
	}

	public HttpSession getTokenSession(String token) {
		if (tokenSession.containsKey(token)) {
			return tokenSession.get(token);
		}
		return null;
	}

	public void removeTokenSession(String token) {
		tokenSession.remove(token);
	}
}