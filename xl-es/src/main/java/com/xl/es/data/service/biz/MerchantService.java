package com.xl.es.data.service.biz;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xl.es.data.doc.Merchant;
import com.xl.es.data.service.ElasticSearchPage;
import com.xl.es.data.service.IndexRepository;
import com.xl.es.data.service.MappingRepository;
import com.xl.es.data.service.TempletQueryRepository;

/**
 * 商户服务
 * 
 * @author liufeng
 *
 */
@Service
public class MerchantService {
	@Resource
	private MappingRepository mapping;
	@Resource
	private IndexRepository index;
	@Resource
	private TempletQueryRepository templetQuery;
	private static Map<String, String> QueryScript = new HashMap<String, String>();

	static {
		QueryScript.put("default", "merchant.merchantQuery");// 默认查询
	}

	/**
	 * 创建商户结构
	 */
	public void createMapping() {
		mapping.createMapping(Merchant.class);
	}

	/**
	 * 保存商户数据
	 * 
	 * @param merchant
	 * @return
	 */
	public boolean saveMerchant(Merchant merchant) {
		if (merchant == null || merchant.getlId() == null) {
			return false;
		}
		Merchant sysMerchant = index.getById(merchant.getlId().toString(), Merchant.class);
		if (sysMerchant == null) {
			// 新增
			index.saveDoc(merchant);
		} else {
			// 更新
			index.updateDoc(merchant);
		}
		return true;
	}

	/**
	 * 查询商户
	 * 
	 * @param queryType
	 * @param pageNum
	 * @param params
	 * @return
	 */
	public ElasticSearchPage<Merchant> findMerchant(String queryType, Integer pageNum, Map<String, Object> params) {
		ElasticSearchPage<Merchant> page = new ElasticSearchPage<Merchant>(10, pageNum);
		if (QueryScript.containsKey(queryType)) {
			this.templetQuery.findByScript(QueryScript.get("default"), params, page);
		}
		return page;
	}
}
