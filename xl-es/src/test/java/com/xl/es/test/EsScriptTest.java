package com.xl.es.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.xl.es.script.builder.Script;
import com.xl.es.script.builder.ScriptFactory;
/**
 * 模板解析器测试用例
 * @author liufeng
 *
 */
public class EsScriptTest extends AbstractEsTest {
	@Resource
	private ScriptFactory scriptFactory;
	@Test
	public void testScript() {
		String name="merchant.rangeQueryByParam";
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("startIndex", 0);
		param.put("pageSize", 2);
		Script script = scriptFactory.findScript(name, param);
		String content=script.getContent();
		String index=script.getIndex();
		String type=script.getType();
		Class resultType=script.getResultType();
		script.getParameterValue();
		System.out.println(index);
		System.out.println(type);
		System.out.println(content);
	}
}	
