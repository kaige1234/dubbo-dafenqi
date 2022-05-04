package com.xl.es.data.service;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.xl.es.data.annotation.EsField;
import com.xl.es.data.annotation.FieldType;
import com.xl.es.script.builder.ScriptFactory;

public abstract class AbstractQuery {
	private static final Logger log = LoggerFactory.getLogger(AbstractQuery.class);
	@Resource
	protected TransportClient client;

	@Resource
	protected ScriptFactory scriptFactory;

	@SuppressWarnings("unchecked")
	public <T> T parseObject(Class t, String src) {
		try {
			return (T) JSON.parseObject(src, t);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("解析失败，{}", e.getMessage());
		}
		return null;
	}

	public static Map<String, Object> getObjectMap(Object doc) {
		List<Field> fieldList = new ArrayList<Field>();
		@SuppressWarnings("rawtypes")
		Class tempClass = doc.getClass();
		while (tempClass != null) {
			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			tempClass = tempClass.getSuperclass();
		}
		try {
			Map<String, Object> result = new HashMap<>();
			for (Field field : fieldList) {
				if (field.isAnnotationPresent(EsField.class)) {
					Object value = getFieldValue(field, doc);
					result.put(field.getName(), value);
				}
			}
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			return null;
		}
	}

	public static Object getFieldValue(Field field, Object doc)
			throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		Object value = field.get(doc);
		EsField column = field.getAnnotation(EsField.class);
		FieldType type = column.type();// 类型
		if (value != null && type == FieldType.Date) {
			String dateFormat = column.dateFormat();
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			return sdf.format((Date) value);
		}
		if (value != null) {
			return value;
		}
		return null;
	}

	public TransportClient getClient() {
		return client;
	}

	public void setClient(TransportClient client) {
		this.client = client;
	}

	public ScriptFactory getScriptFactory() {
		return scriptFactory;
	}

	public void setScriptFactory(ScriptFactory scriptFactory) {
		this.scriptFactory = scriptFactory;
	}
}
