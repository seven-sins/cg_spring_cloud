package com.cg.flink.map;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import com.alibaba.fastjson.JSONObject;
import com.cg.hbase.config.HbaseUtil;
import com.cg.po.flink.YearBase;
import com.cg.po.sys.User;

/**
 * @author seven sins
 * 2019年6月29日 下午2:54:46
 */
public class YearBaseMap implements MapFunction<String, YearBase> {
	private static final long serialVersionUID = 1L;

	@Override
	public YearBase map(String arg) throws Exception {
		if(StringUtils.isBlank(arg)) {
			return null;
		}
		User user = JSONObject.parseObject(arg, User.class);
		String yearType = this.getYearType(this.getYear(user.getAge()));
		HbaseUtil.put(User.class.getName(), String.valueOf(user.getId()), "base", YearBase.class.getName(), yearType);
		
		YearBase yearBase = new YearBase();
		yearBase.setYearType(yearType);
		String groupField = "yearBase==" + yearType;
		yearBase.setCount(1L);
		yearBase.setGroupField(groupField);
		
		return yearBase;
	}
	
	private Integer getYear(Integer age) {
		if(age == null) {
			return age;
		}
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.YEAR, -Integer.valueOf(age));
		return ca.get(Calendar.YEAR);
	}

	private String getYearType(Integer year) {
		String yearType = "未知";
		if(year == null) {
			return yearType;
		}
		if (year >= 1940 && year < 1950) {
			yearType = "40后";
		} else if (year >= 1950 && year < 1960) {
			yearType = "50后";
		} else if (year >= 1960 && year < 1970) {
			yearType = "60后";
		} else if (year >= 1970 && year < 1980) {
			yearType = "70后";
		} else if (year >= 1980 && year < 1990) {
			yearType = "80后";
		} else if (year >= 1990 && year < 2000) {
			yearType = "90后";
		} else if (year >= 2000 && year < 2010) {
			yearType = "00后";
		} else if (year >= 2010) {
			yearType = "10后";
		}
		return yearType;
	}

}
