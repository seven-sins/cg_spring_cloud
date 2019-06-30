package com.cg.po.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cg.utils.validator.Amount;

/**
 * 商品信息
 * @author seven sins
 * 2019年6月30日 下午5:08:33
 */
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	@NotNull(message = "商品类型不能为空")
	private Long productTypeId;
	@NotBlank(message = "商品名称不能为空")
	private String productName;

	private String productDesc;
	@Amount
	private BigDecimal price;

	private Integer num;
	@NotNull(message = "店铺ID不能为空")
	private Long shopId;
	@NotBlank(message = "商品图片不能为空")
	private String imageUrl;

	private Integer isDelete;

	private Date createTime;

	private Date updateTime;

	private Long createBy;

	private Long updateBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

}
