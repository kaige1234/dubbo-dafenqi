package com.xl.es.data.service.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xl.es.data.doc.Merchant;
import com.xl.es.data.service.AbstractQuery;
import com.xl.es.data.service.ElasticSearchPage;

/**
 * 查询的工具方法
 * 
 * @author liufeng
 *
 */
public class MerchantQueryRepository extends AbstractQuery{
	private static final Logger log = LoggerFactory.getLogger(MerchantQueryRepository.class);

	/**
	 * 全文本搜索加高亮显示，没法用泛型，只能设置死返回类型
	 * 
	 * @param param
	 * @param page
	 * @param highlight
	 * @param string
	 * @return
	 */
	public ElasticSearchPage<Merchant> searchFullText(Merchant param, ElasticSearchPage<Merchant> page,HighlightBuilder highlight, String indexName) {
		QueryBuilder builder = null;
		Map<String, Object> map = getObjectMap(param);
		if (map == null)
			return null;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				builder = QueryBuilders.matchQuery(entry.getKey(), entry.getValue());
			}
		}
		SearchResponse scrollResp = client.prepareSearch(indexName).setFrom(page.getPageNum() * page.getPageSize())
				.highlighter(highlight).setSize(page.getPageSize()).setQuery(builder).get();
		List<Merchant> result = new ArrayList<>();
		for (SearchHit hit : scrollResp.getHits().getHits()) {
			try {
				Merchant articleSearch = parseObject(param.getClass(), hit.getSourceAsString());
				Map<String, HighlightField> highlightResult = hit.getHighlightFields();
				String titleAdd = "";
				for (Text textTemp : highlightResult.get("strName").fragments()) {
					titleAdd += textTemp;
				}
				articleSearch.setStrName(titleAdd);
				result.add(articleSearch);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
		page.setTotal(scrollResp.getHits().totalHits);
		page.setParam(param);
		page.setRetList(result);
		return page;
	}
}
