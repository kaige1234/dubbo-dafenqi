package com.opendata.redis.template;

public interface BeanSerializer
{
    String deserialKey(byte[] bytes);
    byte[] serialKey(String string);

    byte[] serialValue(Object t);
    Object deserialValue(byte[] bytes);
}
