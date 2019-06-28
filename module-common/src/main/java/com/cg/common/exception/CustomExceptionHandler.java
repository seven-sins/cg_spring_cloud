package com.cg.common.exception;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.common.config.date.converter.CustomDateFormat;
import com.cg.utils.response.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 全局异常捕获
 * @author Rex.Tan
 * 2019年3月25日 下午5:46:44
 */
@RestControllerAdvice
public class CustomExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);
	/**
	 * 参数验证异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object methodArgumentNotValidException(MethodArgumentNotValidException e){
		e.printStackTrace();
		BindingResult bindingResult = e.getBindingResult();
		if(bindingResult != null) {
			List<ObjectError> errors = bindingResult.getAllErrors();
			if(errors != null && !errors.isEmpty()) {
				String errorMsg = errors.get(0).getDefaultMessage();
				LOG.error("=============请求触发异常: ", errorMsg);
				
				return Result.FAILURE.msg(errorMsg);
			}
		}
		return Result.FAILURE;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CustomException.class)
	public Object customException(CustomException e){
		LOG.error("=============请求触发异常: ", e);
		
		return Result.FAILURE.code(e.getCode()).msg(e.getMsg());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	public Object commonException(Exception e){
		LOG.error("=============请求触发异常: ", e);
		
		return Result.FAILURE.msg(e.getMessage());
	}
	
	/********************************************
	 * 初始化绑定操作(全局形式)(GET请求日期参数格式转换)
	 * @param binder
	 */
    @InitBinder
    public void dataBind(WebDataBinder binder){
        // 給指定类型注册类型转换器操作
        DateFormat dateFmt = new ObjectMapper().getDateFormat();
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new CustomDateFormat(dateFmt), true));
    }
}
