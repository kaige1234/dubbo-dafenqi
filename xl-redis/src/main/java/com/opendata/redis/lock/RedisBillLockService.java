package com.opendata.redis.lock;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁接口
 * @author liufeng
 *
 */
public interface RedisBillLockService
{
	/**
	 * 获取锁
	 * @param billIdentify
	 * @return
	 */
	public boolean tryLock(BillIdentify billIdentify);
	
	/**
	 * 获取锁
	 * @param billIdentify
	 * @param timeout
	 * @param unit
	 * @return
	 */
	 public boolean tryLock(BillIdentify billIdentify, long timeout, TimeUnit unit);
	 
	 /**
	  * 获取锁，此方法无超时情况，直到拿到锁为止，否则一直等待，慎用
	  * @param billIdentify
	  */
	 public void lock(BillIdentify billIdentify);
	
	 /**
	  * 释放锁
	  * @param billIdentify
	  */
	 public void unLock(BillIdentify billIdentify);
	 
	 /**
	  * 批量获取负
	  * @param billIdentifyList
	  * @return
	  */
	 public boolean tryLock(List<BillIdentify> billIdentifyList);
	 
	 /**
	  * 批量获取锁
	  * @param billIdentifyList
	  * @param timeout
	  * @param unit
	  * @return
	  */
	 public boolean tryLock(List<BillIdentify> billIdentifyList, long timeout, TimeUnit unit);
	 
	 /**
	  * 批量释放锁
	  * @param billIdentifyList
	  */
	 public void unLock(List<BillIdentify> billIdentifyList);
	 
	 
	 
}
