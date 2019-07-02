package com.cg.common.utils.excel.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 默认的大标题单元格样式
 */
public class DefaultTitleCellstyleCreater implements ICellstyleCreater {

	@Override
	public CellStyle createStyle(Workbook workbook) {

		// 设置字体
		Font font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 14);
		// 字体加粗
		font.setBold(true);
		// 设置字体名字
		font.setFontName("宋体");
		// 设置样式
		CellStyle style = workbook.createCellStyle();
		// 设置底边框
		style.setBorderBottom(BorderStyle.THIN);
		// 设置底边框颜色
		style.setBottomBorderColor(IndexedColors.BLACK.index);
		// 设置左边框
		style.setBorderLeft(BorderStyle.THIN);
		// 设置左边框颜色
		style.setLeftBorderColor(IndexedColors.BLACK.index);
		// 设置右边框
		style.setBorderRight(BorderStyle.THIN);
		// 设置右边框颜色
		style.setRightBorderColor(IndexedColors.BLACK.index);
		// 设置顶边框
		style.setBorderTop(BorderStyle.THIN);
		// 设置顶边框颜色
		style.setTopBorderColor(IndexedColors.BLACK.index);
		// 在样式用应用设置的字体
		style.setFont(font);
		// 设置自动换行
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐
		style.setAlignment(HorizontalAlignment.CENTER);
		// 设置垂直对齐的样式为居中对齐
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		return style;
	}

}
