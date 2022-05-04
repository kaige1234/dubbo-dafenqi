package com.xl.es.script.mapping;

import com.xl.es.script.builder.factory.Configuration;


/**
 * 操作映射段
 * 
 * @author liufeng
 * 
 */
public class MappedStatement {

	private String resource;
	private Configuration configuration;
	private String id;
	private String index;
	private String type;
	private SqlSource sqlSource;
	private Class parameterType;
	private Class resultType;
	private ScriptCommandType sqlCommandType;

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MappedStatement() {
	}


	public BoundSql getBoundSql(Object parameterObject) {
		BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
		return boundSql;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SqlSource getSqlSource() {
		return sqlSource;
	}

	public void setSqlSource(SqlSource sqlSource) {
		this.sqlSource = sqlSource;
	}

	public Class getParameterType() {
		return parameterType;
	}

	public void setParameterType(Class parameterType) {
		this.parameterType = parameterType;
	}

	public Class getResultType() {
		return resultType;
	}

	public void setResultType(Class resultType) {
		this.resultType = resultType;
	}

	public ScriptCommandType getSqlCommandType() {
		return sqlCommandType;
	}

	public void setSqlCommandType(ScriptCommandType sqlCommandType) {
		this.sqlCommandType = sqlCommandType;
	}

}
