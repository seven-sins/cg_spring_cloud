package com.cg.common.utils.excel.style;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import com.cg.common.utils.excel.config.Format;

/**
 * 数据样式创建器
 */
public abstract class DataCellstyleCreater implements IDataCellstyleCreater {
	
	private Map<Format,CellStyle> cellStylesCache = new HashMap<>();
	
	public CellStyle createStyle(Workbook workbook,Format format) {
		CellStyle style = cellStylesCache.get(format);
		if(style != null){
			return style;
		}
		if(format == Format.DATE){
			style = createDateStyle(workbook);
		}else if(format == Format.ACCOUNTANT){
			style = createAccountantStyle(workbook);
		}else if(format == Format.GENERAL){
			style = createGeneralStyle(workbook);
		}else if(format == Format.PERCENT){
			style = createPercentStyle(workbook);
		}else{
			style = createTextStyle(workbook);
		}
		
		cellStylesCache.put(format, style);
		
		return style;
	}
	
	public abstract CellStyle createTextStyle(Workbook workbook);
	public abstract CellStyle createGeneralStyle(Workbook workbook);
	public abstract CellStyle createDateStyle(Workbook workbook);
	public abstract CellStyle createAccountantStyle(Workbook workbook);
	public abstract CellStyle createPercentStyle(Workbook workbook);
}
