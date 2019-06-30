package com.cg.po.business;

import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * 商品类型
 * 
 * @author seven sins 
 * 2019年6月30日 下午5:09:42
 */
public class ProductType implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	@NotBlank(message = "商品类型不能为空")
	private String productTypeName;

	private String productTypeDesc;

	private Integer isDelete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getProductTypeDesc() {
		return productTypeDesc;
	}

	public void setProductTypeDesc(String productTypeDesc) {
		this.productTypeDesc = productTypeDesc;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

}
