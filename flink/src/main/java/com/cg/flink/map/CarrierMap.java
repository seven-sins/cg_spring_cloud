package com.cg.flink.map;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import com.alibaba.fastjson.JSONObject;
import com.cg.common.utils.CarrierUtil;
import com.cg.hbase.config.HbaseUtil;
import com.cg.po.flink.Carrier;
import com.cg.po.sys.User;

/**
 * @author seven sins
 * 2019年6月29日 下午2:54:46
 */
public class CarrierMap implements MapFunction<String, Carrier> {
	private static final long serialVersionUID = 1L;

	@Override
	public Carrier map(String arg) throws Exception {
		if(StringUtils.isBlank(arg)) {
			return null;
		}
		User user = JSONObject.parseObject(arg, User.class);
		int carrierType = CarrierUtil.getCarrierByMobile(user.getMobile());
		String carrierTypeString = carrierType == 1 ? "移动用户" : carrierType == 2 ? "联通用户" : carrierType == 3 ? "电信用户" : "未知运营商";
		HbaseUtil.put(User.class.getName(), String.valueOf(user.getId()), "base", Carrier.class.getName(), carrierTypeString);
		
		Carrier carrier = new Carrier();
		carrier.setCarrier(carrierTypeString);
		String groupField = "carrier==" + carrierType;
		carrier.setCount(1L);
		carrier.setGroupField(groupField);
		
		return carrier;
	}
	
}
