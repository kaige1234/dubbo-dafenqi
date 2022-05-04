package com.xl.es.script.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xl.es.script.builder.factory.Configuration;
import com.xl.es.script.builder.xml.dynamic.DynamicContext;
import com.xl.es.script.mapping.BoundSql;
import com.xl.es.script.mapping.MappedStatement;
import com.xl.es.script.mapping.ParameterMapping;
import com.xl.es.script.mapping.ScriptCommandType;
import com.xl.es.script.reflection.MetaObject;

/**
 * @功能描述: script 生成SQL
 * @author liufeng
 *
 */
public class Script {
	private Object parameterObject;// 传入参数对象
	private Map<String,Object> parameterValue;// 查询使用的参数值
	private ScriptCommandType scriptCommandType;//执行语句类型
	private String index;//索引名称
	private String type;//索引类型
	private Class resultType;//返回结果类型
	private String content;// 查询SQL语句
	
	public Class getResultType() {
		return resultType;
	}

	public void setResultType(Class resultType) {
		this.resultType = resultType;
	}

	private Script() {
	}

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

	public Object getParameterObject() {
		return parameterObject;
	}

	public void setParameterObject(Object parameterObject) {
		this.parameterObject = parameterObject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Map<String,Object> getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(Map<String,Object> parameterValue) {
		this.parameterValue = parameterValue;
	}

	public ScriptCommandType getScriptCommandType() {
		return scriptCommandType;
	}

	public void setScriptCommandType(ScriptCommandType scriptCommandType) {
		this.scriptCommandType = scriptCommandType;
	}

	public static class Builder {
		private Script script = new Script();
		private MetaObject metaParameter;
		private MappedStatement mappendStatement;
		private List<ParameterMapping> parameterMappings;// 应用查询条件的参数属性列表
		
		public Builder(Configuration configuration, String name, Object parameterObject) {
			mappendStatement = configuration.getMappedStatement(name);
			BoundSql boundSql = mappendStatement.getBoundSql(parameterObject);
			script.index=mappendStatement.getIndex();
			script.type=mappendStatement.getType();
			script.resultType=mappendStatement.getResultType();
			script.content = boundSql.getSql();
			script.parameterObject = parameterObject;
			parameterMappings = boundSql.getParameterMappings();
			script.scriptCommandType = mappendStatement.getSqlCommandType();
			metaParameter = boundSql.getMetaParameters();
		}

		public Script build() {
			Map<String,Object> parameterValue = new HashMap<String,Object>();
			// 处理条件参数
			if (parameterMappings != null && !parameterMappings.isEmpty()) {
				for (ParameterMapping parameterMapping : parameterMappings) {
					String name=parameterMapping.getProperty();
					Object value=metaParameter.getValue(DynamicContext.PARAMETER_OBJECT_KEY + "." + parameterMapping.getProperty());
					parameterValue.put(name, value);
				}
			}
			script.parameterValue = parameterValue;
			return script;
		}
	}
}
