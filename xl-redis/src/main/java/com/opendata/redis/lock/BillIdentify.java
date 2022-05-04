package com.opendata.redis.lock;

/**
 * 锁身份信息
 * @author liufeng
 *
 */
public class BillIdentify
{

	public BillIdentify(Object identify){
		this.key=identify;	
	}
	
	private Object key;
	
	public Object uniqueIdentify(){
		return key;
	}
}
