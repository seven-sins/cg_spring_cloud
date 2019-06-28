package com.cg.common.exception;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.utils.response.Result;

/**
 * 异常处理
 * @author Rex.Tan
 * 2019年3月29日 上午10:49:44
 */
@Controller
public class CustomErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		return "error";
	}

	@RequestMapping("/error")
	@ResponseBody
	public Object handleError(HttpServletRequest request) {
		// Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		if(exception instanceof ServletException) {
			ServletException servletException = (javax.servlet.ServletException) exception;
			if(servletException.getCause() instanceof CustomException) {
				CustomException customException = (CustomException) servletException.getCause();
				return Result.FAILURE.code(customException.getCode()).msg(customException.getMsg());
			}
		}
		
		return Result.FAILURE.msg("未知错误");
	}

}
