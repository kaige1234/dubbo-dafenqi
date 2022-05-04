package com.xl.es.data.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xl.es.data.annotation.EsDoc;
import com.xl.es.data.annotation.EsId;

/**
 * 索引工具方法
 * 
 * @author liufeng
 *
 */
@Service
public class IndexRepository {
	private static final Logger log = LoggerFactory.getLogger(IndexRepository.class);
	@Resource
	private TransportClient client;

	public IndexRepository() {

	}

	/**
	 * 创建搜索引擎文档
	 * 
	 * @param index
	 *            索引名称
	 * @param type
	 *            索引类型
	 * @param id
	 *            索引id
	 * @param doc
	 * @return
	 */
	public String saveDoc(String index, String type, String id, Object doc) {
		IndexResponse response = client.prepareIndex(index, type, id).setSource(this.docToJson(doc), XContentType.JSON)
				.get();
		return response.getId();
	}

	public String saveDoc(Object doc) {
		String index = this.getIndexByTypeClass(doc.getClass());
		String type = this.getTypeByTypeClass(doc.getClass());
		String id = getIdByDoc(doc);
		IndexResponse response = client.prepareIndex(index, type, id).setSource(this.docToJson(doc), XContentType.JSON)
				.get();
		return response.getId();
	}

	/**
	 * 更新文档
	 *
	 * @param index
	 * @param type
	 * @param id
	 * @param doc
	 * @return
	 */
	public String updateDoc(String index, String type, String id, Object doc) {
		UpdateResponse response = client.prepareUpdate(index, type, id).setDoc(this.docToJson(doc), XContentType.JSON)
				.get();
		return response.getId();
	}

	public String updateDoc(Object doc) {
		String index = this.getIndexByTypeClass(doc.getClass());
		String type = this.getTypeByTypeClass(doc.getClass());
		String id = getIdByDoc(doc);
		UpdateResponse response = client.prepareUpdate(index, type, id).setDoc(this.docToJson(doc), XContentType.JSON)
				.get();
		return response.getId();
	}

	/**
	 * 删除索引
	 *
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	public boolean deleteById(String index, String type, String id) {
		DeleteResponse response = client.prepareDelete(index, type, id).get();
		return response.status().getStatus() == 2 ? true : false;
	}

	public boolean deleteDoc(Object doc) {
		String index = this.getIndexByTypeClass(doc.getClass());
		String type = this.getTypeByTypeClass(doc.getClass());
		String id = getIdByDoc(doc);
		DeleteResponse response = client.prepareDelete(index, type, id).get();
		return response.status().getStatus() == 2 ? true : false;
	}

	/**
	 * 获取索引对应的存储内容
	 * 
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	public String getById(String index, String type, String id) {
		GetResponse response = client.prepareGet(index, type, id).get();
		if (response.isExists()) {
			return response.getSourceAsString();
		}
		return null;
	}

	/**
	 * 获取索引对应的存储内容自动转换成对象的方式
	 * 
	 * @param index
	 * @param type
	 * @param id
	 * @param t
	 * @return
	 */
	public <T> T getById(String index, String type, String id, Class t) {
		return parseObject(t, getById(index, type, id));
	}

	/**
	 * 获取数据
	 * 
	 * @param id
	 * @param t
	 * @return
	 */
	public <T> T getById(String id, Class typeClass) {
		String index = this.getIndexByTypeClass(typeClass);
		String type = this.getTypeByTypeClass(typeClass);
		return parseObject(typeClass, getById(index, type, id));
	}

	/**
	 * 对象转换
	 *
	 * @param t
	 * @param src
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T parseObject(Class typeClass, String src) {
		try {
			return (T) JSON.parseObject(src, typeClass);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("解析失败，{}", e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private String docToJson(Object doc) {
		try {
			return JSON.toJSONString(doc);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("解析失败，{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 判断某个索引下type是否存在
	 *
	 * @param index
	 * @param type
	 * @return
	 */
	public boolean isExistType(String index, String type) {
		return client.admin().indices().prepareTypesExists(index).setTypes(type).execute().actionGet().isExists();
	}

	/**
	 * 判断索引是否存在
	 *
	 * @param index
	 * @return
	 */
	public boolean isExistIndex(String index) {
		return client.admin().indices().prepareExists(index).execute().actionGet().isExists();
	}

	/**
	 * 创建type（存在则进行更新）
	 *
	 * @param index
	 *            索引名称
	 * @param type
	 *            type名称
	 * @param o
	 *            要设置type的object
	 * @return
	 */
	public boolean createType(String index, String type, Object o) {
		if (!isExistIndex(index)) {
			log.error("{}索引不存在", index);
			return false;
		}
		try {
			return client.admin().indices().preparePutMapping(index).setType(type).setSource(o).get().isAcknowledged();
		} catch (Exception e) {
			log.error("创建type失败，{}", e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取文档的主键值
	 * 
	 * @param o
	 * @return
	 */
	private static String getIdByDoc(Object o) {
		List<Field> fieldList = new ArrayList<Field>();
		@SuppressWarnings("rawtypes")
		Class tempClass = o.getClass();
		while (tempClass != null) {
			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			tempClass = tempClass.getSuperclass();
		}
		try {
			for (Field field : fieldList) {
				if (field.isAnnotationPresent(EsId.class)) {
					field.setAccessible(true);
					return field.get(o).toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 根据类型删除索引
	 * 
	 * @param typeClass
	 */
	public void deleteIndex(Class typeClass) {
		String indexName = this.getIndexByTypeClass(typeClass);
		if (!isExistIndex(indexName)) {
			log.info(indexName + " not exists");
		} else {
			DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(indexName).execute().actionGet();
			if (dResponse.isAcknowledged()) {
				log.info("delete index " + indexName + "  successfully!");
			} else {
				log.info("Fail to delete index " + indexName);
			}
		}
	}

	/**
	 * 根据类型创建索引
	 * 
	 * @param typeClass
	 */
	public void createIndex(Class typeClass) {
		String indexName = getIndexByTypeClass(typeClass);
		if (isExistIndex(indexName)) {
			log.info("Index  " + indexName + " already exits!");
		} else {
			CreateIndexRequest cIndexRequest = new CreateIndexRequest(indexName);
			CreateIndexResponse cIndexResponse = client.admin().indices().create(cIndexRequest).actionGet();
			if (cIndexResponse.isAcknowledged()) {
				log.info("create index successfully！");
			} else {
				log.error("Fail to create index!");
			}
		}
	}

	/**
	 * 根据类型，获取索引名称
	 * 
	 * @param typeClass
	 * @return
	 */
	private String getIndexByTypeClass(Class typeClass) {
		if (typeClass.isAnnotationPresent(EsDoc.class)) {
			EsDoc doc = (EsDoc) typeClass.getAnnotation(EsDoc.class);
			String index = doc.indexName();
			return index;
		}
		return null;
	}

	/**
	 * 根据类型，获取索引类型
	 * 
	 * @param typeClass
	 * @return
	 */
	private String getTypeByTypeClass(Class typeClass) {
		if (typeClass.isAnnotationPresent(EsDoc.class)) {
			EsDoc doc = (EsDoc) typeClass.getAnnotation(EsDoc.class);
			String type = doc.type();
			return type;
		}
		return null;
	}
}
