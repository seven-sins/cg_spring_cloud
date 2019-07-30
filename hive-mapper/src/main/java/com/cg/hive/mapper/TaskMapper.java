package com.cg.hive.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.cg.po.bigdata.Task;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * 任务
 * @author seven sins
 * @date 2019年7月27日 下午2:39:56
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

}
