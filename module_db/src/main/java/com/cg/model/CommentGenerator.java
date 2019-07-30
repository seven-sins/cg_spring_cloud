package com.cg.model;

import java.util.Properties;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 自定义注释生成器
 * @author seven sins 
 * 2019年5月25日 下午3:03:32
 */
public class CommentGenerator extends DefaultCommentGenerator {
	private boolean addRemarkComments = false;
	private static final String EXAMPLE_SUFFIX = "Example";
	private static final String API_MODEL_PROPERTY_FULL_CLASS_NAME = "io.swagger.annotations.ApiModelProperty";
	private static final String FIELD_NOT_NULL_FULL_CLASS_NAME = "javax.validation.constraints.NotNull";
	private static final String FIELD_NOT_BLANK_FULL_CLASS_NAME = "javax.validation.constraints.NotBlank";
	private static final String PRIMARY_KEY_FULL_CLASS_NAME = "javax.persistence.Id";
	private static final String PRIMARY_KEY = "id";
	/**
	 * 字段类型
	 */
	private static final String VARCHAR = "VARCHAR";

	/**
	 * 设置用户配置的参数
	 */
	@Override
	public void addConfigurationProperties(Properties properties) {
		super.addConfigurationProperties(properties);
		this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
	}

	/**
	 * 给字段添加注释
	 */
	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
		String remarks = introspectedColumn.getRemarks();
		
		if(PRIMARY_KEY.equals(introspectedColumn.getActualColumnName())) {
			field.addJavaDocLine("@Id");
		}
		// 根据参数和备注信息判断是否添加备注信息
		if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
			// 数据库中特殊字符需要转义
			if (remarks.contains("\"")) {
				remarks = remarks.replace("\"", "'");
			}
			// 给model的字段添加swagger注解
			field.addJavaDocLine("@ApiModelProperty(value = \"" + remarks + "\")");
		}
		
		if(!introspectedColumn.isNullable() && !PRIMARY_KEY.equals(introspectedColumn.getActualColumnName())) {
			if(VARCHAR.equals(introspectedColumn.getJdbcTypeName().toUpperCase())) {
				field.addJavaDocLine("@NotBlank(message = \"" + field.getName() + "不能为空\")");
			} else {
				field.addJavaDocLine("@NotNull(message = \"" + field.getName() + "不能为空\")");
			}
		}
	}

	/**
	 * 给model的字段添加注释
	 */
	@SuppressWarnings("unused")
	private void addFieldJavaDoc(Field field, String remarks) {
		// 文档注释开始
		field.addJavaDocLine("/**");
		// 获取数据库字段的备注信息
		String[] remarkLines = remarks.split(System.getProperty("line.separator"));
		for (String remarkLine : remarkLines) {
			field.addJavaDocLine(" * " + remarkLine);
		}
		addJavadocTag(field, false);
		field.addJavaDocLine(" */");
	}

	@Override
	public void addJavaFileComment(CompilationUnit compilationUnit) {
		super.addJavaFileComment(compilationUnit);
		// 只在model中添加swagger注解类的导入
		if (!compilationUnit.isJavaInterface() && !compilationUnit.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)) {
			compilationUnit.addImportedType(new FullyQualifiedJavaType(PRIMARY_KEY_FULL_CLASS_NAME));
			compilationUnit.addImportedType(new FullyQualifiedJavaType(FIELD_NOT_BLANK_FULL_CLASS_NAME));
			compilationUnit.addImportedType(new FullyQualifiedJavaType(API_MODEL_PROPERTY_FULL_CLASS_NAME));
			compilationUnit.addImportedType(new FullyQualifiedJavaType(FIELD_NOT_NULL_FULL_CLASS_NAME));
		}
	}
}
