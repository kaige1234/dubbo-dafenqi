package com.xl.es.script.builder.xml.dynamic;

import com.xl.es.script.builder.factory.Configuration;

public class SetSqlNode extends TrimSqlNode {

	public SetSqlNode(Configuration configuration, SqlNode contents) {
		super(configuration, contents, "SET", null, null, ",");
	}

}
