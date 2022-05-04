package com.xl.es.data.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 文档字段注解，需要添加到文档中的字段，进行标注
 * 
 * @author liufeng
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsDoc {
	String indexName();
	String type();
}