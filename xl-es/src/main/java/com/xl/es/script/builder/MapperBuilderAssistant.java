package com.xl.es.script.builder;

import com.xl.es.script.builder.Exception.BuilderException;
import com.xl.es.script.builder.factory.BaseBuilder;
import com.xl.es.script.builder.factory.Configuration;
import com.xl.es.script.mapping.MappedStatement;
import com.xl.es.script.mapping.ScriptCommandType;
import com.xl.es.script.mapping.SqlSource;

public class MapperBuilderAssistant extends BaseBuilder {

	private String currentNamespace;
	private String resource;

	public MapperBuilderAssistant(Configuration configuration, String resource) {
		super(configuration);
		this.resource = resource;
	}

	public String getCurrentNamespace() {
		return currentNamespace;
	}

	public void setCurrentNamespace(String currentNamespace) {
		if (currentNamespace != null) {
			this.currentNamespace = currentNamespace;
		}
		if (this.currentNamespace == null) {
			throw new BuilderException("The mapper element requires a namespace attribute to be specified.");
		}
	}

	public String applyCurrentNamespace(String base) {
		if (base == null)
			return null;
		if (base.contains("."))
			return base;
		return currentNamespace + "." + base;
	}

	/**
	 * 添加执行操作映射
	 * 
	 * @param id
	 * @param sqlSource
	 * @param parameterMap
	 * @param parameterType
	 * @param resultMap
	 * @param resultType
	 * @param resultSetType
	 * @return
	 */
	public MappedStatement addMappedStatement(String id, String index, String type, SqlSource sqlSource,
			Class<?> parameterType, Class<?> resultType, ScriptCommandType sqlCommandType) {
		id = applyCurrentNamespace(id);
		MappedStatement statement = new MappedStatement();
		statement.setId(id);
		statement.setIndex(index);
		statement.setType(type);
		statement.setConfiguration(configuration);
		statement.setSqlSource(sqlSource);
		statement.setParameterType(parameterType);
		statement.setResultType(resultType);
		statement.setResource(resource);
		statement.setSqlCommandType(sqlCommandType);
		configuration.addMappedStatements(id, statement);
		return statement;
	}

}
