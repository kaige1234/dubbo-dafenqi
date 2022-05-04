package com.opendata.redis.lock;

import java.util.List;

import redis.clients.jedis.Jedis;

public class Test1 extends  Thread{
	String key;
	public Test1(String key){
		this.key=key;
	}
	public void run(){
		Jedis jedis = new Jedis("127.0.0.1", 6379); 
		List<String> content=null;
		do
		{
			content=jedis.brpop(0,"informList");
			if(content!=null){
				System.out.println(key+"========"+content);
			}
		}while(content!=null);
	}
	public static void main(String[] args) {
		
		Test1 one=new Test1("1");
		Test1 two=new Test1("2");
		one.start();
		two.start();
	}

}
