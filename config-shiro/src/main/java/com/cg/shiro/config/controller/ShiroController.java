package com.cg.shiro.config.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.utils.response.Result;

/**
 * @author Rex.Tan
 * 2019年3月26日 上午11:16:52
 */
@RestController
public class ShiroController {

	@GetMapping("/401")
	public Result<?> unauthorized(String errorMsg){
		if(StringUtils.isBlank(errorMsg)) {
			return Result.FAILURE.code(401).msg("无权限访问");
		} else {
			return Result.FAILURE.code(401).msg(errorMsg);
		}
	}
}
