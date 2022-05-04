package com.xl.es.script.builder.xml.dynamic;

import java.util.Map;

import com.xl.es.script.builder.ScriptSourceBuilder;
import com.xl.es.script.builder.factory.Configuration;
import com.xl.es.script.mapping.BoundSql;
import com.xl.es.script.mapping.SqlSource;


public class DynamicSqlSource implements SqlSource {

	private Configuration configuration;
	private SqlNode rootSqlNode;

	public DynamicSqlSource(Configuration configuration, SqlNode rootSqlNode) {
		this.configuration = configuration;
		this.rootSqlNode = rootSqlNode;
	}

	public BoundSql getBoundSql(Object parameterObject) {
		DynamicContext context = new DynamicContext(configuration, parameterObject);
		rootSqlNode.apply(context);
		ScriptSourceBuilder sqlSourceParser = new ScriptSourceBuilder(configuration);
		Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
		SqlSource sqlSource = sqlSourceParser.parse(context.getSql(), parameterType);
		BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
		for (Map.Entry<String, Object> entry : context.getBindings().entrySet()) {
			boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
		}
		return boundSql;
	}

}
