package com.xl.es.data.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.xl.es.data.doc.Result;

/**
 * 通用异常处理器
 * 
 * @author liufeng
 */
@RestControllerAdvice
public class AppExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e){
		logger.error(e.getMessage(), e);
		return Result.getError(e.getMessage());
	}
}
