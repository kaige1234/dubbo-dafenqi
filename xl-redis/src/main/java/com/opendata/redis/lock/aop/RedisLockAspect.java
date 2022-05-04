package com.opendata.redis.lock.aop;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.opendata.redis.lock.BillIdentify;
import com.opendata.redis.lock.RedisBillLockService;
import com.opendata.redis.lock.exception.NoGetRedisLockException;
/**
 * redis锁切面
 * author liufeng
 */
@Aspect
@Component
public class RedisLockAspect implements Ordered{
	protected static final Logger logger = LoggerFactory.getLogger(RedisLockAspect.class);
	static final String RedisKeyTop="_redis_lock_";
    @Resource
    private RedisBillLockService redisBillLockService;

    private ExpressionParser parser = new SpelExpressionParser();
    
    /**
     * 指定切面
     */
    @Pointcut("@annotation(com.opendata.redis.lock.aop.RedisLock)")
    public void redisLock(){
        
    }
    
    /**
     * 环绕通知，在方法执行前加锁，执行后释放锁
     * @param pjp
     * @param redisLock
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.opendata.redis.lock.aop.RedisLock) && @annotation(redisLock)")
    public Object aroundExec(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable{
    	String key=RedisKeyTop+this.getVerbKey(joinPoint, redisLock.key());
    	long timeOut=redisLock.timeOut();
    	TimeUnit timeUnit=redisLock.timeUnit();
    	BillIdentify identify=new BillIdentify(key);
    	boolean hasLock=redisBillLockService.tryLock(identify, timeOut, timeUnit);
    	if(!hasLock){
    		throw new NoGetRedisLockException();
    	}
        Object proceed = joinPoint.proceed();
        redisBillLockService.unLock(identify);
        return proceed;
    }
    
    private String getVerbKey(JoinPoint pjp,String key)
    {
    	CodeSignature codeSignature=(CodeSignature)pjp.getSignature();
    	String[] parameterName=codeSignature.getParameterNames();
    	Object[] args=pjp.getArgs();
    	EvaluationContext context = new StandardEvaluationContext();
    	for(int i=0;i<parameterName.length;i++)
    	{
    		context.setVariable(parameterName[i], args[i]);
    	}
    	String realKey=parser.parseExpression(key).getValue(context, String.class);
    	return realKey;
    }
    
    /**
     * 出异常了，进行释放锁
     * @param redisLock
     */
    @AfterThrowing(throwing="throwable",value="@annotation(com.opendata.redis.lock.aop.RedisLock) && @annotation(redisLock)")  
    public void doAfterThrow(JoinPoint joinPoint,Exception  throwable,RedisLock redisLock){
    	if(throwable instanceof NoGetRedisLockException){
    		logger.error(throwable.getMessage(),throwable);
    		return;
    	}
    	String key=RedisKeyTop+this.getVerbKey(joinPoint, redisLock.key());
    	BillIdentify identify=new BillIdentify(key);
        redisBillLockService.unLock(identify);
    }  
    

    @Override
    public int getOrder() {
        return 4000;
    }
}
