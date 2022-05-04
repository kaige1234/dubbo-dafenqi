package com.opendata.redis.template;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

/**
 * Created by mazw on 2016/3/17.
 */
public class JedisTemplate implements IBaseCache
{
    private static Logger logger = LoggerFactory.getLogger(JedisTemplate.class);
    private BeanSerializer serializer;
    private GenericObjectPoolConfig jedisPoolConfig;
    private String hostName;
    private int port;
    private JedisPool jedisPool;

    public JedisTemplate(GenericObjectPoolConfig jedisPoolConfig, String hostName, int port, BeanSerializer serializer)
    {
        this.jedisPoolConfig = jedisPoolConfig;
        this.hostName = hostName;
        this.port = port;
        this.jedisPool = new JedisPool(jedisPoolConfig, hostName, port);
        this.serializer = serializer;
    }

    public BeanSerializer getSerializer()
    {
        return serializer;
    }

    public void setSerializer(BeanSerializer serializer)
    {
        this.serializer = serializer;
    }

    public GenericObjectPoolConfig getJedisPoolConfig()
    {
        return jedisPoolConfig;
    }

    public void setJedisPoolConfig(GenericObjectPoolConfig jedisPoolConfig)
    {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public String getHostName()
    {
        return hostName;
    }

    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public JedisPool getJedisPool()
    {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool)
    {
        this.jedisPool = jedisPool;
    }

    public Jedis getJedis()
    {
        return jedisPool.getResource();
    }

    private byte[] sk(String key)
    {
        return serializer.serialKey(key);
    }
    private byte[] sv(Object value)
    {
        return serializer.serialValue(value);
    }
    private String dk(byte[] keyb)
    {
        return serializer.deserialKey(keyb);
    }
    private Object dv(byte[] value)
    {
        return serializer.deserialValue(value);
    }

    /* ======================= */
    @Override
    public Boolean set(String key, Object value)
    {
        if (StringUtils.isEmpty(key) || ObjectUtils.isEmpty(value))
        {
            return false;
        }
        Jedis jedis = getJedis();
        String result = "";
        try{
            result = jedis.set(sk(key), sv(value));
        }catch (Exception e){
            logger.error("JedisTemplate.set()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return "ok".equalsIgnoreCase(result);
    }

    /**
     * 查询缓存信息
     */
    @Override
    public Object get(String key)
    {
        if (StringUtils.isEmpty(key))
        {
            return null;
        }
        Jedis jedis = getJedis();
        byte[] value = null;
        try{
            value = jedis.get(sk(key));
        }catch (Exception e){
            logger.error("JedisTemplate.get()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }

        if (ArrayUtils.isEmpty(value))
        {
            return null;
        }
        return dv(value);
    }

    @Override
    public List<String> lrange(String key, int from, int end)
    {
        if (StringUtils.isEmpty(key))
        {
            return null;
        }
        Jedis jedis = getJedis();
        List<String> lrange = null;
        try{
            lrange = jedis.lrange(key, from, end);
        }catch (Exception e){
            logger.error("JedisTemplate.lrange()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return lrange;
    }

    @Override
    public Boolean setOnSeconds(String key, String value, int seconds)
    {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value))
        {
            return false;
        }
        Jedis jedis = getJedis();


        try{
            String result = jedis.set(sk(key), sv(value));

            if ("ok".equalsIgnoreCase(result))
            {
                if (seconds > 0)
                    jedis.expire(sk(key), seconds);
            } else
                return false;
        }catch (Exception e){
            logger.error("JedisTemplate.setOnSeconds()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return true;
    }

    /**
     * 删除缓存
     */
    @Override
    public Boolean delete(String key)
    {
        if (StringUtils.isEmpty(key))
        {
            return false;
        }
        Jedis jedis = getJedis();
        Long result = null;
        try{
            result = jedis.del(sk(key));
        }catch (Exception e){
            logger.error("JedisTemplate.delete()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return result > 0;
    }

    /**
     * 设置过期时间
     */
    @Override
    public Boolean expire(String key, int seconds)
    {
        if (seconds <= 0)
        {
            return false;
        }
        Jedis jedis = getJedis();
        Long result = null;
        try{
            result = jedis.expire(sk(key), seconds);
        }catch (Exception e){
            logger.error("JedisTemplate.expire()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return result > 0;
    }

    /**
     * 查询过期时间
     */
    @Override
    public long ttl(String key)
    {
        if (StringUtils.isEmpty(key))
        {
            return -1;
        }
        Jedis jedis = getJedis();
        Long result = null;
        try{
            result = jedis.ttl(sk(key));
        }catch (Exception e){
            logger.error("JedisTemplate.ttl()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return result;
    }

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     * 设置成功，返回 true 。
     * 设置失败，返回 false 。
     */
    @Override
    public Boolean setnx(String key, String value)
    {
        if (StringUtils.isEmpty(key))
        {
            return false;
        }
        String v = StringUtils.isBlank(value) ? String.valueOf(System.currentTimeMillis()) : value;

        Jedis jedis = getJedis();
        boolean isSuccess = true;
        try{
            Long result = jedis.setnx(sk(key), sv(value));
            isSuccess = result == 1;
            jedis.expire(key, 3);//3秒钟后过期
        }catch (Exception e){
            logger.error("JedisTemplate.setnx()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return isSuccess;
    }

    public void main(String[] args)
    {
        Jedis jedis = getJedis();
        String key = "865931024090714";
        String value = jedis.get(key);
        this.jedisPool.returnResource(jedis);
        ;
    }

    @Override
    public Boolean exists(String key)
    {
        if (StringUtils.isEmpty(key))
        {
            return false;
        }
        Jedis jedis = getJedis();
        Boolean result = true;
        try{
            result = jedis.exists(sk(key));
        }catch (Exception e){
            logger.error("JedisTemplate.exists()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return result;
    }

    @Override
    public Boolean hasKeyLike(String patt)
    {
        if (StringUtils.isEmpty(patt))
        {
            return null;
        }
        Jedis jedis = getJedis();
        Set<String> keys = null;
        try{
            keys = jedis.keys("*" + patt + "*");
        }catch (Exception e){
            logger.error("JedisTemplate.hasKeyLike()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }

        return keys != null && keys.size() > 0;
    }

    @Override
    public Long rpush(String key, String... value)
    {
        if (StringUtils.isBlank(key)) return -1l;
        if (ArrayUtils.isEmpty(value)) return -1l;

        Jedis jedis = getJedis();
        long count = 0l;
        try{
            count = jedis.rpush(sk(key), sv(value));
        }catch (Exception e){
            logger.error("JedisTemplate.rpush()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return count;
    }

    @Override
    public Long llen(String key)
    {
        if (StringUtils.isBlank(key)) return -1l;

        Jedis jedis = getJedis();
        long count = 0l;
        try{
            count = jedis.llen(sk(key));
        }catch (Exception e){
            logger.error("JedisTemplate.llen()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return count;
    }

    @Override
    public List<String> lrange(String key, Long from, Long to)
    {
        if (StringUtils.isBlank(key)) return null;
        if (from==null) return null;
        if (to==null) return null;

        Jedis jedis = getJedis();
        jedis.multi();
        List<String> list = null;
        try{
            list = jedis.lrange(key, from, to);
        }catch (Exception e){
            logger.error("JedisTemplate.lrange()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return list;
    }

    @Override
    public Object lpop(String key)
    {
        if (StringUtils.isBlank(key)) return null;

        Jedis jedis = getJedis();
        byte[] value = null;
        try{
            value = jedis.lpop(sk(key));
        }catch (Exception e){
            logger.error("JedisTemplate.lpop()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        if (ObjectUtils.isEmpty(value) || "nil".equalsIgnoreCase(Arrays.toString(value)))
        {
            return null;
        }
        return dv(value);
    }

    @Override
    public Object rpop(String key)
    {
        if (StringUtils.isBlank(key)) return null;

        Jedis jedis = getJedis();
        byte[] value = null;
        try{
            value = jedis.rpop(sk(key));
        }catch (Exception e){
            logger.error("JedisTemplate.lpop()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        if (ObjectUtils.isEmpty(value) || "nil".equalsIgnoreCase(Arrays.toString(value)))
        {
            return null;
        }
        return dv(value);
    }

    @Override
    public Long increment(String key, int delta)
    {
        if (StringUtils.isBlank(key)) return null;

        Jedis jedis = getJedis();
        Long number = null;
        try{
            number = jedis.incrBy(sk(key), delta);
        }catch (Exception e){
            logger.error("JedisTemplate.increment()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }

        return number;
    }

    @Override
    public Long lPushAll(String key, String[] luckNumArr)
    {
        if (StringUtils.isBlank(key)) return null;

        Jedis jedis = getJedis();
        Long size = null;
        try{
            size = jedis.lpush(key, luckNumArr);
        }catch (Exception e){
            logger.error("JedisTemplate.lPushAll()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }

        return size;
    }


    @Override
    public Long hset(String key,String field, Object value)
    {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field) || ObjectUtils.isEmpty(value))
        {
            return -1l;
        }
        Jedis jedis = getJedis();
        Long result = null;
        try{
            result = jedis.hset(sk(key), sk(field), sv(value));
        }catch (Exception e){
            logger.error("JedisTemplate.hset()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return result;
    }
    @Override
    public Boolean hmset(String key, Map<String, Object> map)
    {
        if (StringUtils.isEmpty(key) || ObjectUtils.isEmpty(map))
        {
            return false;
        }
        Jedis jedis = getJedis();
        String result = null;
        Map<byte[], byte[]> value = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            value.put(sk(entry.getKey()), sv(entry.getValue()));
        }

        try{
            result = jedis.hmset(sk(key), value);
        }catch (Exception e){
            logger.error("JedisTemplate.hmset()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return "ok".equalsIgnoreCase(result);
    }
    @Override
    public Long hdel(String key,String field)
    {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field))
        {
            return -1l;
        }
        Jedis jedis = getJedis();
        Long result = null;
        try{
            result = jedis.hdel(sk(key), sk(field));
        }catch (Exception e){
            logger.error("JedisTemplate.hdel()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return result;
    }
    @Override
    public Object hget(String key, String field)
    {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field))
        {
            return -1l;
        }
        Jedis jedis = getJedis();
        byte[] result = null;
        try{
            result = jedis.hget(sk(key), sk(field));
        }catch (Exception e){
            logger.error("JedisTemplate.hget()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        if (ObjectUtils.isEmpty(result) || "nil".equalsIgnoreCase(Arrays.toString(result)))
        {
            return null;
        }
        return dv(result);
    }
    @Override
    public Map<String, Object> hgetAll(String key)
    {
        if (StringUtils.isEmpty(key))
        {
            return null;
        }
        Jedis jedis = getJedis();
        Map<byte[], byte[]> result = null;
        try{
            result = jedis.hgetAll(sk(key));
        }catch (Exception e){
            logger.error("JedisTemplate.hgetAll()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        if (ObjectUtils.isEmpty(result))
        {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<byte[], byte[]> entry : result.entrySet())
        {
            map.put(dk(entry.getKey()), dv(entry.getValue()));
        }
        return map;
    }
    @Override
    public Long hincrby(String key, String field, long num)
    {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field))
        {
            return -1l;
        }
        Jedis jedis = getJedis();
        Long re = null;
        try{
            re = jedis.hincrBy(sk(key), sk(field), num);
        }catch (Exception e){
            logger.error("JedisTemplate.hincrby()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return re;
    }

    @Override
    public Boolean hexists(String key,String field)
    {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field))
        {
            return false;
        }
        Jedis jedis = getJedis();
        Boolean result = true;
        try{
            result = jedis.hexists(sk(key),sk(field));
        }catch (Exception e){
            logger.error("JedisTemplate.hexists()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return result;
    }

    @Override
    public Long lpush(String key, Object value) {
        if (StringUtils.isBlank(key) || ObjectUtils.isEmpty(value)) return null;

        Jedis jedis = getJedis();

        Long result = null;
        try{
            result = jedis.lpush(sk(key),sv(value));
        }catch (Exception e){
            logger.error("JedisTemplate.lpush()异常",e);
        }finally {
            this.jedisPool.returnResourceObject(jedis);
        }
        return result;
    }

    @Override
    public Map<String, Object> hgetAllAndDel(String key)
    {
        if (StringUtils.isEmpty(key))
        {
            return null;
        }
        Jedis jedis = getJedis();
        Transaction multi = jedis.multi();
        Response<Map<byte[], byte[]>> result = null;
        try{
            result = multi.hgetAll(sk(key));
            multi.del(sk(key));
        }catch (Exception e){
            multi.discard();
            logger.error("JedisTemplate.hgetAllAndDel()异常",e);
        }finally {
            multi.exec();
            this.jedisPool.returnResourceObject(jedis);
        }
        if (ObjectUtils.isEmpty(result))
        {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<byte[], byte[]> entry : result.get().entrySet())
        {
            map.put(dk(entry.getKey()), dv(entry.getValue()));
        }
        return map;
    }
}
