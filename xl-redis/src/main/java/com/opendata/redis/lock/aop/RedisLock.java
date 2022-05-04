package com.opendata.redis.lock.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;


/**
 * 使用注解实现分布式锁
 * 在方法上添加注解后，切面会在方法的开始获取锁，方法结束释放锁
 * 默认无超时时间，如果一次没获取成功，直接返回
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
public @interface RedisLock {
	/**
	 * 定义锁的名称，可使用spring 表达式，获取参数值
	 * @return
	 */
    String key() default "";
    /**
     * 超时设置
     * @return
     */
    long timeOut() default 0;
    /**
     * 超时设置的单位，默认为秒
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}