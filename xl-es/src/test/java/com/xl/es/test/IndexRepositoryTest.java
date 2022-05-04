package com.xl.es.test;

import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;

import com.xl.es.data.annotation.GeoField;
import com.xl.es.data.doc.Merchant;
import com.xl.es.data.service.IndexRepository;

public class IndexRepositoryTest extends AbstractEsTest {
	@Resource
	IndexRepository index;	


	//创建索引
	//@Test
	public void createIndex()
	{
		index.deleteIndex(Merchant.class);
		index.createIndex(Merchant.class);
	}
	
	public static double getRandom(int min,int max){
		min=min*100000;
		max=max*100000;
		Random random = new Random();
        int number = random.nextInt(max)%(max-min+1) + min;
		return number/100000;
	}
	//添加数据
	@Test
	public void saveDoc() throws Exception {
		this.deleteDoc();
		for(Long i=0l;i<50;i++){
			Merchant merchant=new Merchant();
			merchant.setlId(i);
			merchant.setStrName(i+"测试商户");
			merchant.setStrLongitudeAndLatitude(getRandom(10,180)+","+getRandom(10,40));
			merchant.setDtAuditTime(new Date());
			merchant.setDtCreateTime(new Date());
			this.index.saveDoc(merchant);
		}
	}
	
	//更新数据
	//@Test
	public void updateDoc() throws Exception {
		for(Long i=0l;i<5;i++){
			Merchant merchant=new Merchant();
			merchant.setlId(i);
			merchant.setStrName(i+"测试商户");
			merchant.setDtAuditTime(new Date());
			merchant.setDtCreateTime(new Date());
			merchant.setStrLongitudeAndLatitude("118.695844,40.005467");
			this.index.updateDoc(merchant);
		}
	}
	
	//删除数据
	//@Test
	public void deleteDoc(){
		for(Long i=0l;i<100;i++){
			Merchant merchant=new Merchant();
			merchant.setlId(i);
			this.index.deleteDoc(merchant);
		}
	}
	
	
	//获取数据
	//@Test
	public void getDoc(){
		Merchant m= this.index.getById("1", Merchant.class);
		System.out.println();
	}
}
