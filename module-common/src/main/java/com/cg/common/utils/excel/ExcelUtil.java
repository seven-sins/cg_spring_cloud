package com.cg.common.utils.excel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.fastjson.JSON;
import com.cg.common.utils.excel.config.ColumnConfig;
import com.cg.common.utils.excel.config.ExcelConfig;
import com.cg.common.utils.excel.config.Format;
import com.cg.common.utils.excel.style.IDataCellstyleCreater;

/**
 * Excel操作类
 */
public class ExcelUtil<T> {
	/**
	 * 当前操作行
	 */
	private int rowIndex = 0;
	/**
	 * 当前sheet
	 */
	private Sheet sheet;
	/**
	 * Excel配置信息
	 */
	private ExcelConfig config;
	/**
	 * Excel文档对象
	 */
	private Workbook workbook;

	public ExcelUtil(Workbook workbook, ExcelConfig config) {
		this.config = config;
		this.workbook = workbook;

		this.createSheet();

		if (StringUtils.isNotBlank(config.getTitle())) {
			this.createTitleRow();
		}
		this.createColumnNameRow(this.workbook);
	}

	// 创建大标题行
	private ExcelUtil<? extends T> createTitleRow() {
		Row row = sheet.createRow(rowIndex++);
		row.setHeight((short) config.getTitleHeight());
		Cell cellTiltle = row.createCell(0);
		CellStyle columnTopStyle = config.getTitleCellstyleCreater().createStyle(workbook);
		if (config.getColumnConfigs().length > 1) {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, config.getColumnConfigs().length - 1));
		}
		cellTiltle.setCellStyle(columnTopStyle);
		cellTiltle.setCellValue(config.getTitle());
		return this;
	}

	// 创建列标题行
	private ExcelUtil<? extends T> createColumnNameRow(Workbook wb) {
		Row row = sheet.createRow(rowIndex++);
		row.setHeight((short) config.getColumnNameHeight());
		ColumnConfig[] columnInfos = config.getColumnConfigs();
		CellStyle style = config.getColumnNameCellstyleCreater().createStyle(wb);
		for (int j = 0; j < columnInfos.length; j++) {
			Cell cell = row.createCell(j);
			cell.setCellValue(columnInfos[j].getTitle());
			cell.setCellStyle(style);
		}

		return this;
	}

	/**
	 * 设置标题
	 * 
	 * @param wb          文档对象
	 * @param sheetname   sheet名
	 * @param columnNames 标题
	 * @return
	 */
	private void createSheet() {
		sheet = workbook.createSheet(config.getSheetName());

		for (int j = 0; j < config.getColumnConfigs().length; j++) {
			sheet.setColumnWidth(j, config.getColumnConfigs()[j].getWidth());
		}
	}

	/**
	 * 接收单个数据对象创建行
	 * 
	 * @param obj 行数据对象
	 */
	@SuppressWarnings("unchecked")
	public void createDataRow(T t) {
		Map<String, Object> json = (Map<String, Object>) JSON.toJSON(t);

		Row row = sheet.createRow(rowIndex++);
		row.setHeight((short) config.getDataHeight());

		ColumnConfig[] columnInfos = config.getColumnConfigs();

		IDataCellstyleCreater styleCreater = config.getDataCellstyleCreater();
		for (int i = 0; i < columnInfos.length; i++) {
			Cell cell = row.createCell(i);

			Format format = columnInfos[i].getFormat();
			String fieldName = columnInfos[i].getFieldName();
			Map<String, String> enums = columnInfos[i].getEnums();

			CellStyle style = styleCreater.createStyle(workbook, format);
			if (format == Format.GENERAL) {
				cell.setCellValue(json.get(fieldName) == null ? null : json.get(fieldName).toString());
			} else if (format == Format.DATE) {
				Object value = json.get(fieldName);
				if (value != null) {
					if (value instanceof Date) {
						cell.setCellValue(new SimpleDateFormat(columnInfos[i].getDateFormat()).format((Date) value));
					} else if (value instanceof String) {
						cell.setCellValue(value.toString());
					} else {
						style = styleCreater.createStyle(workbook, Format.TEXT);
						cell.setCellValue(value.toString());
					}
				}
			}else if (format == Format.DATETIME) {
				Object value = json.get(fieldName);
				if (value != null) {
					if (value instanceof Date) {
						cell.setCellValue(new SimpleDateFormat(columnInfos[i].getDateFormat()).format((Date) value));
					} else if (value instanceof String) {
						cell.setCellValue(value.toString());
					} else {
						style = styleCreater.createStyle(workbook, Format.TEXT);
						cell.setCellValue(value.toString());
					}
				}
			} else if (format == Format.ACCOUNTANT || format == Format.PERCENT) {
				Object obj = json.get(fieldName);
				cell.setCellValue(obj == null ? null : new BigDecimal(obj.toString(), java.math.MathContext.UNLIMITED).doubleValue());
			} else {
				Object value = json.get(fieldName);
				cell.setCellValue(value == null ? "" : getEnumText(enums, value.toString()));
			}

			style.setWrapText(columnInfos[i].isWrapText());

			cell.setCellStyle(style);
		}
	}

	private String getEnumText(Map<String, String> enums, String enumValue) {
		if (enums != null && !enums.isEmpty() && enums.containsKey(enumValue)) {
			return enums.get(enumValue);
		} else {
			return enumValue;
		}
	}

	/**
	 * 接收List数据源创建行
	 * 
	 * @param list
	 */
	public void createDataRows(List<T> list) {
		for (T t : list) {
			createDataRow(t);
		}
	}

	public static Workbook getWorkbook() {
		return new HSSFWorkbook();
	}
}