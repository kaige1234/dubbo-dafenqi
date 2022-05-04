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
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsField {
	/**
	 * 字段类型
		文本: string
		数字: byte, short, integer, long
		浮点数: float, double
		布尔值: boolean
		Date: date
	  	多重: multi
		经纬度: geo_point
		网络地址: ip
		堆叠对象: nested object
		二进制: binary
		附件: attachment
	 * @return
	 */
	FieldType type() default FieldType.text;

	/**
	 * 是否分词
	 *
	 * @return
	 */
	boolean analyze() default false;
	
	/**
	 * 当类型为时间时，此字段有意义
	 * @return
	 */
	String dateFormat() default "yyyy-MM-dd HH:mm:ss";

}