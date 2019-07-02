//package com.cg.common.utils.qiniuyun;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.google.gson.Gson;
//import com.qiniu.common.QiniuException;
//import com.qiniu.common.Zone;
//import com.qiniu.http.Response;
//import com.qiniu.storage.Configuration;
//import com.qiniu.storage.UploadManager;
//import com.qiniu.storage.model.DefaultPutRet;
//import com.qiniu.util.Auth;
//
///**
// * 七牛云上传工具
// * @author Rex.Tan
// * 2019年7月2日 上午9:02:38
// */
//public class QiniuYunUtil {
//	static final Logger LOG = LoggerFactory.getLogger(QiniuYunUtil.class);
//	static final String QINIU_APPKEY = "ak";
//	static final String QINIU_SECRET = "sk";
//	
//	public static void uploadToQiniu(String fileFullPath, String fileName) {
//		// 构造一个带指定Zone对象的配置类
//		Configuration cfg = new Configuration(Zone.zone0());
//		// ...其他参数参考类注释
//		UploadManager uploadManager = new UploadManager(cfg);
//		// ...生成上传凭证，然后准备上传
//		// String accessKey = "lia5cRsWjXQbXzZ62jLqHx8xuWvuKVogZqaqldz8";
//		// String secretKey = "gjfFRvmNo6MWeOJg0UXdlJ2b4QoWN_qR4aM5fX7t";
//		String bucket = "common";
//		// 如果是Windows情况下，格式是 D:\\qiniu\\test.png
//		String localFilePath = fileFullPath;
//		
//		LOG.info("=============filePath: " + fileFullPath);
//		// 默认不指定key的情况下，以文件内容的hash值作为文件名
//		String key = bucket + "/" + fileName;
//		Auth auth = Auth.create(QINIU_APPKEY, QINIU_SECRET);
//		String upToken = auth.uploadToken(bucket);
//		try {
//			Response response = uploadManager.put(localFilePath, key, upToken);
//			// 解析上传成功的结果
//			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//			System.out.println(putRet.key);
//			System.out.println(putRet.hash);
//			LOG.info("=============response key: " + putRet.key);
//		} catch (QiniuException ex) {
//			Response r = ex.response;
//			System.err.println(r.toString());
//			try {
//				System.err.println(r.bodyString());
//			} catch (QiniuException ex2) {
//				// ignore
//			}
//		}
//	}
//}
