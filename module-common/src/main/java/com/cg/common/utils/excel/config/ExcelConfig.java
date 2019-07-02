package com.cg.common.utils.excel.config;

import com.cg.common.utils.excel.style.DefaultColumnNameCellstyleCreater;
import com.cg.common.utils.excel.style.DefaultDataCellstyleCreater;
import com.cg.common.utils.excel.style.DefaultTitleCellstyleCreater;
import com.cg.common.utils.excel.style.ICellstyleCreater;
import com.cg.common.utils.excel.style.IDataCellstyleCreater;

/**
 * sheet配置
 */
public class ExcelConfig {

	/**
	 * Sheet名
	 */
	private String sheetName;
	/**
	 * 大标题
	 */
	private String title;

	/**
	 * 列配置信息
	 */
	private ColumnConfig[] columnConfigs;

	/**
	 * 大标题高度
	 */
	private int titleHeight = 600;

	/**
	 * 列标题行高度
	 */
	private int columnNameHeight = 300;

	/**
	 * 数据行高度
	 */
	private int dataHeight = 300;

	/**
	 * 大标题行的样式创建器
	 */
	private ICellstyleCreater titleCellstyleCreater;

	/**
	 * 列标题行的样式创建器
	 */
	private ICellstyleCreater columnNameCellstyleCreater;

	/**
	 * 数据行的样式创建器
	 */
	private IDataCellstyleCreater dataCellstyleCreater;

	public ExcelConfig(String sheetName, ColumnConfig[] columnConfigs) {
		super();
		this.sheetName = sheetName;
		this.columnConfigs = columnConfigs;
	}

	/************************* get and shet *******************************/
	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ColumnConfig[] getColumnConfigs() {
		return columnConfigs;
	}

	public void setColumnConfigs(ColumnConfig[] columnConfigs) {
		this.columnConfigs = columnConfigs;
	}

	public int getTitleHeight() {
		return titleHeight;
	}

	public void setTitleHeight(int titleHeight) {
		this.titleHeight = titleHeight;
	}

	public int getColumnNameHeight() {
		return columnNameHeight;
	}

	public void setColumnNameHeight(int columnNameHeight) {
		this.columnNameHeight = columnNameHeight;
	}

	public int getDataHeight() {
		return dataHeight;
	}

	public void setDataHeight(int dataHeight) {
		this.dataHeight = dataHeight;
	}

	public ICellstyleCreater getTitleCellstyleCreater() {
		if (titleCellstyleCreater == null) {
			titleCellstyleCreater = new DefaultTitleCellstyleCreater();
		}
		return titleCellstyleCreater;
	}

	public void setTitleCellstyleCreater(ICellstyleCreater titleCellstyleCreater) {
		this.titleCellstyleCreater = titleCellstyleCreater;
	}

	public ICellstyleCreater getColumnNameCellstyleCreater() {
		if (columnNameCellstyleCreater == null) {
			columnNameCellstyleCreater = new DefaultColumnNameCellstyleCreater();
		}
		return columnNameCellstyleCreater;
	}

	public void setColumnNameCellstyleCreater(ICellstyleCreater columnNameCellstyleCreater) {
		this.columnNameCellstyleCreater = columnNameCellstyleCreater;
	}

	public IDataCellstyleCreater getDataCellstyleCreater() {
		if (dataCellstyleCreater == null) {
			dataCellstyleCreater = new DefaultDataCellstyleCreater();
		}
		return dataCellstyleCreater;
	}

	public void setDataCellstyleCreater(IDataCellstyleCreater dataCellstyleCreater) {
		this.dataCellstyleCreater = dataCellstyleCreater;
	}

	/************************ ext ****************************/
	/**
	 * 设置大标题行的高度,默认600
	 * 
	 * @param titleHeight
	 * @return
	 */
	public ExcelConfig titleHeight(int titleHeight) {
		this.titleHeight = titleHeight;
		return this;
	}

	/**
	 * 设置列标题行的高度,默认300
	 * 
	 * @param columnNameHeight
	 * @return
	 */
	public ExcelConfig columnNameHeight(int columnNameHeight) {
		this.columnNameHeight = columnNameHeight;
		return this;
	}

	/**
	 * 设置数据行的高度,默认300
	 * 
	 * @param dataHeight
	 * @return
	 */
	public ExcelConfig dataHeight(int dataHeight) {
		this.dataHeight = dataHeight;
		return this;
	}

	/**
	 * 设置大标题
	 * 
	 * @param title
	 * @return
	 */
	public ExcelConfig title(String title) {
		this.title = title;
		return this;
	}

	/**
	 * 设置大标题行的样式创建器,默认DefaulTitleCellstyleCreater
	 * 
	 * @param titleCellstyleCreater
	 * @return
	 */
	public ExcelConfig titleCellstyleCreater(ICellstyleCreater titleCellstyleCreater) {
		this.titleCellstyleCreater = titleCellstyleCreater;
		return this;
	}

	/**
	 * 设置列标题行的样式创建器，默认DefaultColumnNameCellstyleCreater
	 * 
	 * @param columnNameCellstyleCreater
	 * @return
	 */
	public ExcelConfig columnNameCellstyleCreater(ICellstyleCreater columnNameCellstyleCreater) {
		this.columnNameCellstyleCreater = columnNameCellstyleCreater;
		return this;
	}

	/**
	 * 设置数据行的样式创建器,默认DefaultDataCellstyleCreater
	 * 
	 * @param dataCellstyleCreater
	 * @return
	 */
	public ExcelConfig dataCellstyleCreater(IDataCellstyleCreater dataCellstyleCreater) {
		this.dataCellstyleCreater = dataCellstyleCreater;
		return this;
	}

}
