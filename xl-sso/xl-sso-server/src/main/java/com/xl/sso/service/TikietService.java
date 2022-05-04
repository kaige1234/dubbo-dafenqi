package com.xl.sso.service;

import com.xl.sso.beans.Token;
/**
 * 票据存储服务
 * @author liufeng
 *
 */
public interface TikietService {
	
	public void addToken(Token token);
	
	public Token getToken(String tokenid);
	
	public void removeToken(String tokenid);
}
