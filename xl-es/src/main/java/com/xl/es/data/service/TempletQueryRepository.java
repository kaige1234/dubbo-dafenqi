package com.xl.es.data.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xl.es.data.doc.Document;
import com.xl.es.script.builder.Script;

/**
 * 根据脚本查询
 * 
 * @author liufeng
 *
 */
@Service
public class TempletQueryRepository extends AbstractQuery {
	private static final Logger log = LoggerFactory.getLogger(TempletQueryRepository.class);

	/**
	 * 按范围查询
	 * 
	 * @param indexName
	 * @param point
	 * @param page
	 * @return
	 */
	public <T> ElasticSearchPage<T> findByScript(String scriptId, Map<String, Object> params,ElasticSearchPage<T> page) {
		params.put("pageSize", page.getPageSize());
		params.put("startIndex", page.getPageNum() * page.getPageSize());
		Script script = scriptFactory.findScript(scriptId, params);
		SearchResponse scrollResp = new SearchTemplateRequestBuilder(client).setScript(script.getContent().trim())
				.setScriptType(ScriptType.INLINE).setScriptParams(script.getParameterValue())
				.setRequest(new SearchRequest().indices(script.getIndex()).types(script.getType())).get().getResponse();
		List result = new ArrayList();
		for (SearchHit hit : scrollResp.getHits().getHits()) {
			try {
				String source = hit.getSourceAsString();
				Document doc = parseObject(script.getResultType(), source);
				doc.setSort(hit.getSortValues());
				result.add(doc);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
		page.setRetList(result);
		page.setTotal(scrollResp.getHits().totalHits);
		return page;
	}
}
