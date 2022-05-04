package com.xl.es.script.builder.xml;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xl.es.script.builder.Exception.BuilderException;
import com.xl.es.script.builder.factory.BaseBuilder;
import com.xl.es.script.builder.factory.Configuration;
import com.xl.es.script.io.Resources;
import com.xl.es.script.parsing.XNode;
import com.xl.es.script.parsing.XPathParser;


public class XMLConfigBuilder extends BaseBuilder {
	protected Log logger = LogFactory.getLog(getClass());
	private boolean parsed;// 是否解析
	private XPathParser parser;// 解析器
	private List<String> otherScriptMapper = new ArrayList<String>();// 额外的sqlmapper

	public XMLConfigBuilder(Reader reader) {
		this(reader, null);
	}

	public void setOtherScriptMapper(List<String> otherScriptMapper) {
		this.otherScriptMapper = otherScriptMapper;
	}

	/**
	 * 加载系统资源配置文件
	 * 
	 * @param reader
	 * @param environment
	 * @param props
	 */
	public XMLConfigBuilder(Reader reader, Properties props) {
		super(new Configuration());
		this.parsed = false;
		this.parser = new XPathParser(reader, true, props, new XMLMapperEntityResolver());
	}

	/**
	 * 解析配置文件
	 * 
	 * @return
	 */
	public Configuration parse() {
		if (parsed) {
			throw new BuilderException("Each MapperConfigParser can only be used once.");
		}
		parsed = true;
		parseConfiguration(parser.evalNode("/configuration"));
		return configuration;
	}

	private void parseConfiguration(XNode root) {
		try {
			mapperElement(root.evalNode("mappers"));
		} catch (Exception e) {
			throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
		}
	}

	/**
	 * 获得开发模式，默认为部署模式，当develop=true时，则为开发模式
	 * @return
	 */
	public boolean getDevelopModel()
	{
		return parser.evalNode("/configuration").getBooleanAttribute("develop",false);
	}
	
	/**
	 * 根据映射文件的主节点，逐次解析各个映射文件
	 * 
	 * @param parent
	 * @throws Exception
	 */
	private void mapperElement(XNode parent) throws Exception {
		if (parent != null) {
			for (XNode child : parent.getChildren()) {
				String resource = child.getStringAttribute("resource");
				String url = child.getStringAttribute("url");
				Reader reader;
				if (resource != null && url == null) {
					 reader = Resources.getResourceAsReader(resource);
					logger.info("load script configfile " + resource);
					XMLMapperBuilder mapperParser = new XMLMapperBuilder(reader, configuration, resource, configuration.getSqlFragments());
					mapperParser.parse();
				} else if (url != null && resource == null) {
					reader = Resources.getResourceAsReader(url);
					logger.info("load script configfile " + url);
					XMLMapperBuilder mapperParser = new XMLMapperBuilder(reader, configuration, url, configuration.getSqlFragments());
					mapperParser.parse();
				} else {
					throw new BuilderException("A mapper element may only specify a url or resource, but not both.");
				}
			}
			// 添加额外增加的freesql配置文件
			for (int i = 0; i < otherScriptMapper.size(); i++) {
				String configFile = otherScriptMapper.get(i);
				Reader reader = Resources.getResourceAsReader(configFile);
				logger.info("load script configfile " + configFile);
				XMLMapperBuilder mapperParser = new XMLMapperBuilder(reader, configuration, configFile, configuration.getSqlFragments());
				mapperParser.parse();
			}
		}
	}

}
