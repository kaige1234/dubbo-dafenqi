package com.xl.es.script.builder.xml;

import java.io.Reader;
import java.util.List;
import java.util.Map;

import com.xl.es.script.builder.MapperBuilderAssistant;
import com.xl.es.script.builder.factory.BaseBuilder;
import com.xl.es.script.builder.factory.Configuration;
import com.xl.es.script.parsing.XNode;
import com.xl.es.script.parsing.XPathParser;


public class XMLMapperBuilder extends BaseBuilder {

	private XPathParser parser;
	private MapperBuilderAssistant builderAssistant;
	private Map<String, XNode> sqlFragments;
	private String resource;

	public XMLMapperBuilder(Reader reader, Configuration configuration, String resource, Map<String, XNode> sqlFragments) {
		super(configuration);
		this.builderAssistant = new MapperBuilderAssistant(configuration, resource);
		this.parser = new XPathParser(reader, true, configuration.getVariables(), new XMLMapperEntityResolver());
		this.sqlFragments = sqlFragments;
		this.resource = resource;
	}

	public void parse() {
		if (!configuration.isResourceLoaded(resource)) {
			configurationElement(parser.evalNode("/mapper"));
			configuration.addLoadedResource(resource);
		}
	}

	public XNode getSqlFragment(String refid) {
		return sqlFragments.get(refid);
	}

	private void configurationElement(XNode context) {
		try {
			String namespace = context.getStringAttribute("namespace");
			builderAssistant.setCurrentNamespace(namespace);
			sqlElement(context.evalNodes("/mapper/script"));
			bufferStatementNodes(context.evalNodes("select|insert|update|delete"));
			configuration.buildAllStatements();
		} catch (Exception e) {
			throw new RuntimeException("Error parsing Mapper XML. Cause: " + e, e);
		}

	}

	
	private void sqlElement(List<XNode> list) throws Exception {
		for (XNode context : list) {
			String id = context.getStringAttribute("id");
			id = builderAssistant.applyCurrentNamespace(id);
			sqlFragments.put(id, context);
		}
	}

	/**
	 * To achieve mapper-order-independent parsing, stores the statement nodes
	 * into the temporary map without actually parsing them.
	 * 
	 * @param list
	 * @see Configuration#getMappedStatement(String)
	 * @see Configuration#buildAllStatements()
	 */
	private void bufferStatementNodes(List<XNode> list) {
		String currentNamespace = builderAssistant.getCurrentNamespace();
		configuration.addStatementNodes(currentNamespace, list);
	}

}
