package com.xl.es.test;

import javax.annotation.Resource;

import org.junit.Test;

import com.xl.es.data.doc.Merchant;
import com.xl.es.data.service.IndexRepository;
import com.xl.es.data.service.MappingRepository;

public class MappingRepositoryTest extends AbstractEsTest {
	@Resource
	private MappingRepository mapping;
	@Resource
	private IndexRepository index;

	@Test
	public void createMapping() {
		index.deleteIndex(Merchant.class);
		index.createIndex(Merchant.class);
		this.mapping.createMapping(Merchant.class);
	}

}
/*

*/