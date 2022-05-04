package com.xl.sso.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Token implements Serializable{
	private Date createDate;
	private String userId;
	private String tokenId;
	private List<String> clientUrl=new ArrayList<String>();
	public Token(String tokenId){
		this.tokenId=tokenId;
	}
	public String getUserId() { 
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public List<String> getClientUrl() {
		return clientUrl;
	}
	public void setClientUrl(List<String> clientUrl) {
		this.clientUrl = clientUrl;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
