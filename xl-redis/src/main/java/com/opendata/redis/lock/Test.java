package com.opendata.redis.lock;

import redis.clients.jedis.Jedis;

public class Test {

	public static void main(String [] args){
		Jedis jedis = new Jedis("127.0.0.1", 6379); 
        for(int i = 0;i<10;i++) {
            jedis.lpush("informList","value_" + i);  
        }
        jedis.close();
	}
}
