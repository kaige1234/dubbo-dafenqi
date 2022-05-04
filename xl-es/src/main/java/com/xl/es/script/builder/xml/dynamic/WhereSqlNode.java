package com.xl.es.script.builder.xml.dynamic;

import com.xl.es.script.builder.factory.Configuration;

public class WhereSqlNode extends TrimSqlNode
{

	public WhereSqlNode(Configuration configuration, SqlNode contents)
	{
		super(configuration, contents, "WHERE", "AND |OR ", null, null);
	}

}
