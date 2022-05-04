package com.xl.commons.util;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;

public class EhCacheUtil {
	private static final CacheManager cacheManager = new CacheManager();
	private static final EhCacheUtil generalizeEhCahe = new EhCacheUtil();
	private static final String DEFAULT_EHNAME = "appCache";

	/**
	 * 单例类私有构造方法
	 */
	private EhCacheUtil() {
	}

	public static EhCacheUtil getInstacne() {
		return generalizeEhCahe;
	}

	/**
	 * 
	 * @param key
	 *            缓存key
	 * @return
	 */
	public Element get(String key) {
		Element elem = getCache().get(key);
		return elem;
	}

	/**
	 * 
	 * @param key
	 *            缓存key
	 * @param ehName
	 *            指定ehcahce名称
	 * @return
	 */
	public Element get(String key, String ehName) {
		Element elem = getCache(ehName).get(key);
		return elem;
	}

	/**
	 * 移除单个key
	 * 
	 * @param key
	 */
	public void remove(String key) {
		if (!StringUtils.isEmpty(key)) {
			getCache().remove(key);
		}
	}

	/**
	 * 移除单个key从指定的缓存配置中
	 * 
	 * @param key
	 */
	public void remove(String key, String ehName) {
		if (!StringUtils.isEmpty(key)) {
			getCache(ehName).remove(key);
		}
	}

	/**
	 * 移除所有key
	 */
	public void removeAll() {
		getCache().removeAll();
	}

	/**
	 * 移除所有key从指定的ehcache中
	 */
	public void removeAll(String ehName) {
		getCache(ehName).removeAll();
	}

	/**
	 * 将对象放入缓存
	 * 
	 * @param key
	 * @param obj
	 */
	public void put(String key, Object obj) {
		getCache().put(new Element(key, obj));
	}

	/**
	 * 将对象放入指定的eh缓存
	 * 
	 * @param key
	 * @param obj
	 */
	public void put(String key, Object obj, String ecName) {
		getCache(ecName).put(new Element(key, obj));
	}

	public long getTTL() {
		return getCache().getCacheConfiguration().getTimeToLiveSeconds();
	}

	public long getTTL(String ehName) {
		return getCache(ehName).getCacheConfiguration().getTimeToLiveSeconds();
	}

	public long getTTI() {
		return getCache().getCacheConfiguration().getTimeToIdleSeconds();
	}

	public long getTTI(String ehName) {
		return getCache(ehName).getCacheConfiguration().getTimeToIdleSeconds();
	}

	public int getSize() {
		return getCache().getSize();
	}

	public int getSize(String ehName) {
		return getCache(ehName).getSize();
	}

	private Ehcache getCache() {
		return cacheManager.getEhcache(DEFAULT_EHNAME);
	}

	private Ehcache getCache(String ehName) {
		return cacheManager.getEhcache(ehName);
	}
}
