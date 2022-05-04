package com.opendata.redis.template;

import java.nio.charset.Charset;

import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FastJsonSerializer implements BeanSerializer
{
    private final Charset charset;

    @Override
    public byte[] serialValue(Object source)
    {
        if (source == null)
        {
            return new byte[0];
        }
        return JSON.toJSONBytes(source, SerializerFeature.WriteClassName);
    }

    @Override
    public Object deserialValue(byte[] bytes)
    {
        if (isEmpty(bytes))
        {
            return null;
        }
        return JSON.parse(new String(bytes, charset));
    }

    public FastJsonSerializer()
    {
        this(Charset.forName("UTF8"));
    }

    public FastJsonSerializer(Charset charset)
    {
        Assert.notNull(charset);
        this.charset = charset;
    }

    public String deserialKey(byte[] bytes)
    {
        return (bytes == null ? null : new String(bytes, charset));
    }

    public byte[] serialKey(String string)
    {
        return (string == null ? null : string.getBytes(charset));
    }

    static boolean isEmpty(byte[] data)
    {
        return (data == null || data.length == 0);
    }
}
