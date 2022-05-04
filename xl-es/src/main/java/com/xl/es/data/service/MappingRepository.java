package com.xl.es.data.service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xl.es.data.annotation.EsDoc;
import com.xl.es.data.annotation.EsField;
import com.xl.es.data.annotation.FieldType;

/**
 * mapping工具方法
 * 
 * @author liufeng
 *
 */
@Service
public class MappingRepository {
	private static final Logger log = LoggerFactory.getLogger(MappingRepository.class);

	@Resource
	private TransportClient client;
	@Resource
	private IndexRepository indexRepository;

	public MappingRepository() {

	}

	public void createMapping(Class typeClass) {
		if (typeClass.isAnnotationPresent(EsDoc.class)) {
			EsDoc doc = (EsDoc) typeClass.getAnnotation(EsDoc.class);
			String index = doc.indexName();
			String type = doc.type();
			if (!this.indexRepository.isExistIndex(index)) {
				this.indexRepository.createIndex(typeClass);
			}
			PutMappingRequest mapping = Requests.putMappingRequest(index).type(type).source(this.getMapping(typeClass,new ArrayList<String>()));
			PutMappingResponse responst = client.admin().indices().putMapping(mapping).actionGet();
		}
	}

	/**
	 * 根据对象类型，获取对应的mapping定义
	 * 
	 * @param obj
	 * @return
	 */
	public <T> XContentBuilder getMapping(Class<T> objClass,List<String> typeList) {
		XContentBuilder mapping = null;
		try {
			mapping = XContentFactory.jsonBuilder();
			mapping.startObject().startObject("properties");
			if(!typeList.contains(objClass.getName())){
				typeList.add(objClass.getName());
				this.fillFields(this.getFieldByClass(objClass), mapping,typeList);
			}
			mapping.endObject().endObject();
			log.debug("mapping=>" + mapping.string());
		} catch (Exception e) {
			log.error("创建Mapping失败，{}", e.getMessage());
			e.printStackTrace();
		}
		return mapping;
	}

	private void fillFields(List<Field> fieldList, XContentBuilder mapping,List<String> typeList) throws Exception {
		for (Field field : fieldList) {
			if (field.isAnnotationPresent(EsField.class)) {
				EsField column = field.getAnnotation(EsField.class);
				String name = field.getName();// 名称
				FieldType type = column.type();// 类型
				if (type.equals(FieldType.Object)) {
					Class classType=getClassByField(field);
					if(!typeList.contains(classType.getName())){
						typeList.add(classType.getName());
						mapping.startObject(name).startObject("properties");
						List<Field> childFieldList = getFieldByClass(classType);
						this.fillFields(childFieldList, mapping,typeList);
						mapping.endObject().endObject();
					}
				} else {
					this.fillField(field, mapping);
				}
			}
		}
	}

	private XContentBuilder fillField(Field field, XContentBuilder mapping) throws Exception {
		if (!field.isAnnotationPresent(EsField.class)) {
			return mapping;
		}
		EsField column = field.getAnnotation(EsField.class);
		String name = field.getName();// 名称
		FieldType type = column.type();// 类型
		String dateFormat = column.dateFormat();// 日期格式
		String isAnalyzed = column.analyze() ? "true" : "false";// 是否分词
		mapping.startObject(name);
		if (type.equals(FieldType.GeoPoint)) {
			mapping.field("type", "geo_point");
		} else {
			mapping.field("type", type.toString().toLowerCase());
		}
		mapping.field("index", isAnalyzed);
		if (type.equals(FieldType.Date) && !dateFormat.equals("")) {
			mapping.field("format", dateFormat);
		}
		mapping.endObject();
		return mapping;
	}

	private Class getClassByField(Field field) throws Exception{
		if (field.getType().equals(List.class)) {
			ParameterizedType pt = (ParameterizedType) field.getGenericType();
			String typeName = pt.getActualTypeArguments()[0].getTypeName();
			return Class.forName(typeName);
		} else {
			return field.getType();
		}
	}

	private List<Field> getFieldByClass(Class classType) {
		Class tempClass = classType;
		List<Field> fieldList = new ArrayList<Field>();
		while (tempClass != null) {
			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			tempClass = tempClass.getSuperclass();
		}
		return fieldList;
	}

}
