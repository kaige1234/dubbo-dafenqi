package com.xl.es.script.builder.xml.dynamic;

import com.xl.es.script.builder.Exception.BuilderException;
import com.xl.es.script.ognl.Ognl;
import com.xl.es.script.ognl.OgnlException;
import com.xl.es.script.parsing.GenericTokenParser;
import com.xl.es.script.parsing.TokenHandler;
import com.xl.es.script.type.SimpleTypeRegistry;

public class TextSqlNode implements SqlNode {
	private String text;

	public TextSqlNode(String text) {
		this.text = text;
	}

	public boolean apply(DynamicContext context) {
		GenericTokenParser parser = new GenericTokenParser("${", "}", new BindingTokenParser(context));
		context.appendSql(parser.parse(text));
		return true;
	}

	private static class BindingTokenParser implements TokenHandler {

		private DynamicContext context;

		public BindingTokenParser(DynamicContext context) {
			this.context = context;
		}

		public String handleToken(String content) {
			try {
				Object parameter = context.getBindings().get("_parameter");
				if (parameter == null) {
					context.getBindings().put("value", null);
				} else if (SimpleTypeRegistry.isSimpleType(parameter.getClass())) {
					context.getBindings().put("value", parameter);
				}
				Object value = Ognl.getValue(content, context.getBindings());
				return String.valueOf(value);
			} catch (OgnlException e) {
				throw new BuilderException("Error evaluating expression '" + content + "'. Cause: " + e, e);
			}
		}
	}

}
