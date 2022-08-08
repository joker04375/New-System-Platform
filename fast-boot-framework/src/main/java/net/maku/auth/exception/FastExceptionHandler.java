package net.maku.auth.exception;

import lombok.extern.slf4j.Slf4j;
import net.maku.auth.bo.ApiResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 异常处理器   自定义异常
 * RestControllerAdvice都是对Controller进行增强的，可以全局捕获spring mvc抛的异常。
 *
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.example.leo"})
public class FastExceptionHandler {
	/**
	 * 处理自定义异常
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(FastException.class)
	public ApiResult handleRenException(FastException ex){

		return ApiResult.error(ex.getMsg(),ex.getCode());
	}

	/**
	 * SpringMVC参数绑定，Validator校验不正确
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(BindException.class)
	public ApiResult bindException(BindException ex) {
		FieldError fieldError = ex.getFieldError();
		assert fieldError != null;
		return ApiResult.error(fieldError.getDefaultMessage());
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
	public ApiResult handleAccessDeniedException(Exception ex){

		return ApiResult.error(ErrorCode.FORBIDDEN);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ApiResult handleException(Exception ex){
		log.error(ex.getMessage(), ex);
		return ApiResult.error(ErrorCode.INTERNAL_SERVER_ERROR);
	}

}