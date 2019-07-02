package com.cg.common.utils.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import com.cg.common.utils.excel.config.Format;

/**
 * 数据单元格样式创建器
 */
public interface IDataCellstyleCreater {
	
	/**
	 * 创建单元格样式
	 * @param workbook excel文档
	 * @param format 单元格格式
	 * @return
	 */
	public CellStyle createStyle(Workbook workbook, Format format);
}
