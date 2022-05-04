package com.xl.sso.service;

import java.util.Map;

public interface LoginService {

	public Map<String,String> login(String userName,String password);
}
