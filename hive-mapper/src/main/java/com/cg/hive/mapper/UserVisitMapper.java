package com.cg.hive.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.cg.po.bigdata.UserVisit;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * 用户访问Mapper
 * @author Rex.Tan
 * 2019年3月25日 下午4:18:20
 */
@Mapper
public interface UserVisitMapper extends BaseMapper<UserVisit>{

}
