//package com.cg.hbase.config;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.Admin;
//import org.apache.hadoop.hbase.client.Connection;
//import org.apache.hadoop.hbase.client.ConnectionFactory;
//import org.apache.hadoop.hbase.client.Get;
//import org.apache.hadoop.hbase.client.Put;
//import org.apache.hadoop.hbase.client.Result;
//import org.apache.hadoop.hbase.client.Table;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.cg.common.exception.CustomException;
//
///**
// * hbase工具类
// * @author seven sins
// * 2019年6月29日 下午4:15:10
// */
//public class HbaseUtil {
//	private static final Logger LOG = LoggerFactory.getLogger(HbaseUtil.class);
//	/**
//	 * 管理程序
//	 */
//	static Admin admin = null;
//	/**
//	 * 连接
//	 */
//	static Connection conn = null;
//	
//	static {
//		// 创建hbase配置对象
//		Configuration conf = HBaseConfiguration.create();
//		conf.set("hbase.rootdir", "hdfs://192.168.0.205:9000/hbase");
//		// 
//		conf.set("hbase.zookeeper.quorum", "192.168.0.205");
//		conf.set("hbase.client.scanner.timeout.period", "600000");
//		conf.set("hbase.rpc.timeout", "600000");
//		
//		try {
//			conn = ConnectionFactory.createConnection(conf);
//			// 得到管理程序
//			admin = conn.getAdmin();
//		} catch (IOException e) {
//			LOG.error("=============hbase初始化失败", e);
//		}
//	}
//	
//	/**
//	 * 插入数据
//	 * @param tablename
//	 * @param rowkey
//	 * @param famliyname
//	 * @param datamap
//	 */
//    public static void put(String tablename, String rowkey, String famliyname, Map<String, String> datamap) {
//        try {
//        	Table table = conn.getTable(TableName.valueOf(tablename));
//            // 将字符串转换成byte[]
//    		byte[] rowkeybyte = Bytes.toBytes(rowkey);
//    		Put put = new Put(rowkeybyte);
//    		if (datamap != null) {
//    			Set<Map.Entry<String, String>> set = datamap.entrySet();
//    			for (Map.Entry<String, String> entry : set) {
//    				String key = entry.getKey();
//    				Object value = entry.getValue();
//    				put.addColumn(Bytes.toBytes(famliyname), Bytes.toBytes(key), Bytes.toBytes(value + ""));
//    			}
//    		}
//    		table.put(put);
//    		table.close();
//        } catch(Exception e) {
//        	LOG.error("=============插入数据到hbase中出错", e);
//			throw new CustomException("插入数据到hbase中出错, " + e.getMessage());
//		}
//    }
//
//    /**
//     * 获取数据
//     * @param tablename
//     * @param rowkey
//     * @param famliyname
//     * @param colum
//     * @return
//     */
//	public static String get(String tablename, String rowkey, String famliyname, String colum) {
//		try {
//			Table table = conn.getTable(TableName.valueOf(tablename));
//			// 将字符串转换成byte[]
//			byte[] rowkeybyte = Bytes.toBytes(rowkey);
//			Get get = new Get(rowkeybyte);
//			Result result = table.get(get);
//			byte[] resultbytes = result.getValue(famliyname.getBytes(), colum.getBytes());
//			if (resultbytes == null) {
//				return null;
//			}
//			return new String(resultbytes);
//		} catch(Exception e) {
//			LOG.error("=============从hbase中查询数据出错", e);
//			throw new CustomException("从hbase中查询数据出错, " + e.getMessage());
//		}
//	}
//
//	/**
//	 * 插入数据
//	 * @param tablename
//	 * @param rowkey
//	 * @param famliyname
//	 * @param colum
//	 * @param data
//	 */
//	public static void put(String tablename, String rowkey, String famliyname, String colum, String data) {
//		try {
//			Table table = conn.getTable(TableName.valueOf(tablename));
//			Put put = new Put(rowkey.getBytes());
//			put.addColumn(famliyname.getBytes(), colum.getBytes(), data.getBytes());
//			table.put(put);
//		} catch(Exception e) {
//			LOG.error("=============插入数据到hbase中出错", e);
//			throw new CustomException("插入数据到hbase中出错, " + e.getMessage());
//		}
//	}
//}
