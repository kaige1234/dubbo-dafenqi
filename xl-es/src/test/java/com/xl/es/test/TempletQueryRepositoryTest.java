package com.xl.es.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.xl.es.data.doc.Merchant;
import com.xl.es.data.service.ElasticSearchPage;
import com.xl.es.data.service.TempletQueryRepository;
/**
 * 根据模板查询
 * @author liufeng
 *
 */
public class TempletQueryRepositoryTest extends AbstractEsTest {
	@Resource
	private TempletQueryRepository templetQuery;

	@Test
	public void findByTmplet() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lon", 118.695844);
		params.put("lat", 40.005467);
		ElasticSearchPage<Merchant> page = new ElasticSearchPage<Merchant>(2);
		this.templetQuery.findByScript("merchant.rangeQueryByParam", params, page);
		System.out.println(page.getTotal());
		System.out.println(page.getTotalPage());
	}
}	
