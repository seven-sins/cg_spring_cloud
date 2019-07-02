package com.cg.common.utils.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 单元格样式创建器
 */
public interface ICellstyleCreater {
	
	/**
	 * 创建单元格样式
	 * @param workbook excel文档
	 * @return
	 */
	public CellStyle createStyle(Workbook workbook);
}
