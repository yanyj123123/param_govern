package com.ghrk.consumer.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ghrk.common.exception.GlobalException;
import com.ghrk.common.utils.R;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * 异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return R.error("已存在该记录");
	}

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(GlobalException.class)
	public R handleGlobalException(GlobalException e){
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());
		logger.error(e.getMessage(), e);
		return r;
	}

	// 数据参数读取异常，比如Integer收到String或者float
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public R handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
		logger.error(e.getMessage(), e);
		return R.error("输入数据不合法");
	}
	// 参数校验异常
	@ExceptionHandler(ConstraintViolationException.class)
	public R handleConstraintViolationException(ConstraintViolationException e){
		logger.error(e.getMessage(), e);
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		if (CollectionUtils.isNotEmpty(violations)) {
			ConstraintViolation<?> violation = violations.iterator().next();// 取第一个进行提示就行了
			return R.error(violation.getMessage());
		}
		return R.error("输入数据不合法!");
	}

	@ExceptionHandler(ValidationException.class)
	public R handlerValidationException(ValidationException e) {
		logger.error(e.getMessage(), e);
		return R.error(500, "数据输入不合法!");
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public R handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		logger.error(e.getMessage(), e);
		return R.error(500, "请求方法错误!");
	}



	@ExceptionHandler(NoHandlerFoundException.class)
	public R handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		return R.error(404, "路径不存在，请检查路径是否正确");
	}

	@ExceptionHandler(NumberFormatException.class)
	public R handleNumberFormatException(NumberFormatException e){
		logger.error(e.getMessage(), e);
		return R.error("输入数据不合法");
	}

	@ExceptionHandler(InvalidFormatException.class)
	public R handleNumberFormatException(InvalidFormatException e){
		logger.error(e.getMessage(), e);
		return R.error("输入数据不合法");
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public R handleIllegalArgumentException(IllegalArgumentException e){
		logger.error(e.getMessage(), e);
		return R.error("输入数据不合法");
	}

	@ExceptionHandler(RuntimeException.class)
	public R handleException(RuntimeException e){
		logger.error(e.getMessage(), e);
		return R.error("运行异常，请重试!");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public R handleException(MethodArgumentNotValidException e){
		logger.error(e.getMessage(), e);
		return R.error("输入数据不合法");
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		logger.error(e.getMessage(), e);
		return R.error();
	}
}
