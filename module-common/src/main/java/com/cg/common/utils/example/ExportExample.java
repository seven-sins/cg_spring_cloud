//package com.cg.common.utils.example;
//
//public class ExportExample {
//
//	public static void main(String[] args) {
//		List<PedestrianVo> record = pedestrianInfoService.record(user, enteringData.getActivityName(), enteringData.getLineName(), enteringData.getStartTime(), enteringData.getEndTime());
//		
//		ColumnConfig[] columnConfigs = new ColumnConfig[] {
//                new ColumnConfig("领队名称", "leaderNickName", Format.TEXT),
//                new ColumnConfig("活动名称", "activityName", Format.TEXT),
//                new ColumnConfig("出行日期", "outsetTime", Format.DATE),
//                new ColumnConfig("下单人名称", "nickName", Format.TEXT),
//                new ColumnConfig("下单时间", "placeOrderTime", Format.DATE, "yyyy-MM-dd HH:mm:ss"),
//                new ColumnConfig("返利", "distributionAmount", Format.ACCOUNTANT),
//                new ColumnConfig("状态", "distributionStatusText", Format.TEXT)
//        };
//        Workbook wb = new HSSFWorkbook();
//        ExcelConfig excelConfig = new ExcelConfig("分销记录", columnConfigs);
//        excelConfig.setTitle("分销记录");
//        ExcelUtil<PedestrianVo> export = new ExcelUtil<>(wb, excelConfig);
//        export.createDataRows(record);
//        //
//        try {
//            String fileName = "分销记录-" + DateUtils.format(new Date(), "yyyy-MM-dd") + ".xls";
//            response.setContentType("APPLICATION/OCTET-STREAM");
//            response.setCharacterEncoding("utf-8"); 
//			// 设置浏览器响应头对应的Content-disposition
//			response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1") + ".xls");
//            OutputStream out = response.getOutputStream();
//            wb.write(out);
//        } catch (IOException e) {
//        	LOG.error("=============导出分销记录出错: ", e);
//        	throw new CustomException("导出分销记录出错, " + e.getMessage());
//        }
//	}
//}
