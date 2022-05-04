package com.xl.sso.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xl.sso.beans.Token;
import com.xl.sso.service.TikietService;

public class MemoryTikietServiceImpl implements TikietService {

	private static Map<String,Token> tokenMap=new ConcurrentHashMap<String,Token>();
	@Override
	public void addToken(Token token) {
		tokenMap.put(token.getTokenId(), token);
	}

	@Override
	public Token getToken(String tokenId) {
		return tokenMap.get(tokenId);
	}

	@Override
	public void removeToken(String tokenId) {
		tokenMap.remove(tokenId);
	}

}
