package com.xl.es.script.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.xl.es.script.builder.factory.BaseBuilder;
import com.xl.es.script.builder.factory.Configuration;
import com.xl.es.script.mapping.ParameterMapping;
import com.xl.es.script.mapping.SqlSource;
import com.xl.es.script.parsing.GenericTokenParser;
import com.xl.es.script.parsing.TokenHandler;
import com.xl.es.script.reflection.MetaClass;

public class ScriptSourceBuilder extends BaseBuilder {

	public ScriptSourceBuilder(Configuration configuration) {
		super(configuration);
	}

	public SqlSource parse(String originalSql, Class<?> parameterType) {
		ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler(configuration, parameterType);
		GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
		String sql = parser.parse(originalSql);
		return new StaticScriptSource(configuration, sql, handler.getParameterMappings());
	}

	private static class ParameterMappingTokenHandler extends BaseBuilder implements TokenHandler {

		private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
		private Class<?> parameterType;

		public ParameterMappingTokenHandler(Configuration configuration, Class<?> parameterType) {
			super(configuration);
			this.parameterType = parameterType;
		}

		public List<ParameterMapping> getParameterMappings() {
			return parameterMappings;
		}

		public String handleToken(String content) {
			parameterMappings.add(buildParameterMapping(content));
			return "{{" + content + "}}";
		}

		private ParameterMapping buildParameterMapping(String content) {
			StringTokenizer parameterMappingParts = new StringTokenizer(content, ", \n\r\t");
			String propertyWithJdbcType = parameterMappingParts.nextToken();
			String property = extractPropertyName(propertyWithJdbcType);
			Class<?> propertyType;
			MetaClass metaClass = MetaClass.forClass(parameterType);
			if (metaClass.hasGetter(property)) {
				propertyType = metaClass.getGetterType(property);
			} else {
				propertyType = Object.class;
			}
			return new ParameterMapping(property, propertyType);
		}

		private String extractPropertyName(String property) {
			if (property.contains(":")) {
				StringTokenizer simpleJdbcTypeParser = new StringTokenizer(property, ": ");
				if (simpleJdbcTypeParser.countTokens() == 2) {
					return simpleJdbcTypeParser.nextToken();
				}
			}
			return property;
		}

	}
}
