package com.xl.sso.service.impl;

import javax.annotation.Resource;

import com.opendata.redis.template.JedisTemplate;
import com.xl.sso.beans.Token;
import com.xl.sso.service.TikietService;

public class RedisTikietServiceImpl implements TikietService {

	@Resource
	JedisTemplate jedisTemplate;
	
	@Override
	public void addToken(Token token) {
		jedisTemplate.set(token.getTokenId(), token);
	}

	@Override
	public Token getToken(String tokenId) {
		return (Token) jedisTemplate.get(tokenId);
	}

	@Override
	public void removeToken(String tokenId) {
		jedisTemplate.delete(tokenId);
		
	}

}
