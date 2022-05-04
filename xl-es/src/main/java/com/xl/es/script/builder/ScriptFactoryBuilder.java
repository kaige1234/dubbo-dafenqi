package com.xl.es.script.builder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xl.es.script.builder.factory.Configuration;
import com.xl.es.script.builder.xml.XMLConfigBuilder;
import com.xl.es.script.io.Resources;

public class ScriptFactoryBuilder {
	protected static Log logger = LogFactory.getLog(ScriptFactoryBuilder.class);
	private static ScriptFactoryBuilder factoryBuilder;
	private Configuration configuration;
	private static Boolean DevelopModel = null;
	private static String FreesqlConfigFile = "script-config.xml";

	private ScriptFactoryBuilder() {
		logger.info("system init script............");
	}

	/**
	 * 通过自己的freesql文件，动态增加配置文件
	 * 
	 * @param otherFreesqlMapper
	 * @return
	 */
	public static ScriptFactoryBuilder builder(List<String> otherFreesqlMapper) {
		ScriptFactoryBuilder.configDevelopModel();
		if (DevelopModel) {
			try {
				factoryBuilder = new ScriptFactoryBuilder();
				Reader reader = Resources.getResourceAsReader(FreesqlConfigFile);
				XMLConfigBuilder parser = new XMLConfigBuilder(reader);
				parser.setOtherScriptMapper(otherFreesqlMapper);
				factoryBuilder.configuration = parser.parse();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (factoryBuilder == null) {
				try {
					factoryBuilder = new ScriptFactoryBuilder();
					Reader reader = Resources.getResourceAsReader(FreesqlConfigFile);
					XMLConfigBuilder parser = new XMLConfigBuilder(reader);
					parser.setOtherScriptMapper(otherFreesqlMapper);
					factoryBuilder.configuration = parser.parse();
				} catch (Exception e) {
					logger.error(e);
					e.printStackTrace();
				}
			}
		}
		return factoryBuilder;
	}

	/**
	 * 根据系统中的主配置文件加载freesql
	 * 
	 * @return
	 */
	public static ScriptFactoryBuilder builder() {
		ScriptFactoryBuilder.configDevelopModel();
		if (DevelopModel) {
			try {
				factoryBuilder = new ScriptFactoryBuilder();
				Reader reader = Resources.getResourceAsReader(FreesqlConfigFile);
				XMLConfigBuilder parser = new XMLConfigBuilder(reader);
				factoryBuilder.configuration = parser.parse();
				return factoryBuilder;
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		} else {
			if (factoryBuilder == null) {
				try {
					factoryBuilder = new ScriptFactoryBuilder();
					Reader reader = Resources.getResourceAsReader(FreesqlConfigFile);
					XMLConfigBuilder parser = new XMLConfigBuilder(reader);
					factoryBuilder.configuration = parser.parse();
				} catch (Exception e) {
					logger.error(e);
					e.printStackTrace();
				}
			}
		}
		return factoryBuilder;
	}

	/**
	 * 根据Readr加载freesql
	 * 
	 * @param reader
	 * @return
	 */
	public static ScriptFactoryBuilder builder(Reader reader) {
		ScriptFactoryBuilder.configDevelopModel();
		if (DevelopModel) {
			factoryBuilder = new ScriptFactoryBuilder();
			XMLConfigBuilder parser = new XMLConfigBuilder(reader);
			factoryBuilder.configuration = parser.parse();
		} else {
			if (factoryBuilder == null) {
				factoryBuilder = new ScriptFactoryBuilder();
				XMLConfigBuilder parser = new XMLConfigBuilder(reader);
				factoryBuilder.configuration = parser.parse();
			}
		}
		return factoryBuilder;
	}

	/**
	 * 根据配置文件路径加载script
	 * 
	 * @param configFilePath
	 * @return
	 * @throws Exception
	 */
	public static ScriptFactoryBuilder builder(String configFilePath) throws Exception {
		ScriptFactoryBuilder.configDevelopModel();
		if (DevelopModel) {
			factoryBuilder = new ScriptFactoryBuilder();
			Reader reader = Resources.getResourceAsReader(configFilePath);
			XMLConfigBuilder parser = new XMLConfigBuilder(reader);
			factoryBuilder.configuration = parser.parse();
		} else {
			if (factoryBuilder == null) {
				factoryBuilder = new ScriptFactoryBuilder();
				Reader reader = Resources.getResourceAsReader(configFilePath);
				XMLConfigBuilder parser = new XMLConfigBuilder(reader);
				factoryBuilder.configuration = parser.parse();
			}
		}

		return factoryBuilder;
	}

	/**
	 * 根据业务名称，参数，返回绑定的SQL
	 * 
	 * @param name
	 * @param parameterObject
	 * @return
	 */
	public Script findScript(String name, Object parameterObject) {
		Script.Builder builder = new Script.Builder(configuration, name, parameterObject);
		return builder.build();
	}

	/**
	 * 获得开发模式
	 * 
	 * @return
	 */
	private static void configDevelopModel() {
		if (DevelopModel == null) {
			try {
				Reader reader = Resources.getResourceAsReader(FreesqlConfigFile);
				DevelopModel = new XMLConfigBuilder(reader).getDevelopModel();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
