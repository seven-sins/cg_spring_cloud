package com.cg.common.utils.excel.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 默认的列标题单元格样式
 */
public class DefaultColumnNameCellstyleCreater implements ICellstyleCreater {

	@Override
	public CellStyle createStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setBold(true);
		font.setFontName("宋体");
		
		style.setFont(font);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		
		return style;
	}

}
