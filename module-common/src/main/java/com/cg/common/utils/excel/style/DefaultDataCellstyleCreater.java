package com.cg.common.utils.excel.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import com.cg.common.utils.excel.config.Format;

/**
 * 默认的数据单元格样式
 */
public class DefaultDataCellstyleCreater extends DataCellstyleCreater {
	
	@Override
	public CellStyle createTextStyle(Workbook workbook) {
		CellStyle style = createBaseStyle(workbook);
		style.setDataFormat(workbook.createDataFormat().getFormat("@"));
		return style;
	}

	@Override
	public CellStyle createGeneralStyle(Workbook workbook) {
		CellStyle style = createBaseStyle(workbook);
		style.setDataFormat(workbook.createDataFormat().getFormat(Format.GENERAL.name()));
		return style;
	}


	@Override
	public CellStyle createDateStyle(Workbook workbook) {
		CellStyle style = createBaseStyle(workbook);
		style.setDataFormat(workbook.createDataFormat().getFormat("yyyy-MM-dd"));
		return style;
	}


	@Override
	public CellStyle createAccountantStyle(Workbook workbook) {
		CellStyle style = createBaseStyle(workbook);
		style.setDataFormat(workbook.createDataFormat().getFormat("_ * #,##0.00_ ;_ * \\-#,##0.00_ ;_ * \"-\"??_ ;_ @_ "));
		return style;
	}


	@Override
	public CellStyle createPercentStyle(Workbook workbook) {
		CellStyle style = createBaseStyle(workbook);
		style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
		return style;
	}

	protected CellStyle createBaseStyle(Workbook workbook){
		CellStyle style = workbook.createCellStyle();
		
		Font dataFont = workbook.createFont();
		dataFont.setFontHeightInPoints((short) 10);
		dataFont.setColor(IndexedColors.BLACK.getIndex());
		dataFont.setFontName("宋体");

		style.setFont(dataFont);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		
		return style;
	}

}
