package com.opendata.redis.template;

import java.util.List;
import java.util.Map;

/**
 * Created by chenbei on 16/3/24.
 */
public interface IBaseCache
{

    Boolean set(String key, Object value);

    List<String> lrange(String key, int from, int end);

    Boolean setOnSeconds(String key, String value, int seconds);

    Boolean delete(String key);

    Object get(String key);

    Boolean expire(String key, int seconds);

    long ttl(String key);

    Boolean setnx(String key, String value);

    Boolean exists(String key);

    Boolean hasKeyLike(String patt);

    Long rpush(String key, String... value);

    Long llen(String key);

    List<String> lrange(String key, Long from, Long to);

    Object lpop(String key);

    Object rpop(String key);

    Long increment(String key, int delta);

    Long lPushAll(String key, String[] luckNumArr);

    Long hset(String key, String field, Object value);

    Boolean hmset(String key, Map<String, Object> map);

    Long hdel(String key, String field);

    Object hget(String key, String field);

    Map<String, Object> hgetAll(String key);

    Long hincrby(String key, String field, long num);

    Boolean hexists(String key, String field);

    Long lpush(String key, Object value);

    Map<String, Object> hgetAllAndDel(String key);
}
