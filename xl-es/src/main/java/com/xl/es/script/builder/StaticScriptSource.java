package com.xl.es.script.builder;

import java.util.List;

import com.xl.es.script.builder.factory.Configuration;
import com.xl.es.script.mapping.BoundSql;
import com.xl.es.script.mapping.ParameterMapping;
import com.xl.es.script.mapping.SqlSource;

public class StaticScriptSource implements SqlSource {

	private String sql;
	private List<ParameterMapping> parameterMappings;
	private Configuration configuration;

	public StaticScriptSource(Configuration configuration, String sql) {
		this(configuration, sql, null);
	}

	public StaticScriptSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
		this.sql = sql;
		this.parameterMappings = parameterMappings;
		this.configuration = configuration;
	}

	public BoundSql getBoundSql(Object parameterObject) {
		return new BoundSql(configuration, sql, parameterMappings, parameterObject);
	}

}