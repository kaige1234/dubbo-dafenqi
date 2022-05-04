package com.xl.es.script.builder;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * script工厂
 * 
 * @author liufeng
 *
 */
public class ScriptFactory implements InitializingBean, DisposableBean {

	private ScriptFactoryBuilder factoryBuilder;

	public Script findScript(String name, Object parameterObject) {
		return factoryBuilder.findScript(name, parameterObject);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		factoryBuilder = ScriptFactoryBuilder.builder();
	}

	@Override
	public void destroy() throws Exception {
	}
}