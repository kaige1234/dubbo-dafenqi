package com.xl.es.test;

import javax.annotation.Resource;

import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;

import com.xl.es.data.doc.Merchant;
import com.xl.es.data.service.ElasticSearchPage;
import com.xl.es.data.service.biz.MerchantQueryRepository;

public class MerchantQueryRepositoryTest extends AbstractEsTest {
	@Resource
	private MerchantQueryRepository query;

	/**
	 * 根据名称查询
	 */
	@Test
	public void findByName() {
		ElasticSearchPage<Merchant> page = new ElasticSearchPage<Merchant>(10);
		HighlightBuilder highlight = new HighlightBuilder();
		highlight.field("strName").preTags("<span style=\"color:red\">").postTags("</span>");
		Merchant param = new Merchant();
		param.setStrName("测试");
		query.searchFullText(param, page, highlight, "xl");
		for (Merchant aa : page.getRetList()) {
			System.out.println(aa.getlId() + "====" + aa.getStrName() );
		}
		System.out.println(page.getTotal());
	}
}
