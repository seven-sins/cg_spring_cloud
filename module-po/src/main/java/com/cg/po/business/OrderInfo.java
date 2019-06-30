package com.cg.po.business;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.cg.utils.validator.Amount;

/**
 * 订单信息
 * 
 * @author seven sins 
 * 2019年6月30日 下午5:01:02
 */
public class OrderInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	/**
	 * 商品类别ID
	 */
	@NotNull(message = "商品类型不能为空")
	private Long productTypeId;
	/**
	 * 商品ID
	 */
	@NotNull(message = "商品ID不能为空")
	private Long productId;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 支付方式
	 */
	@NotNull(message = "支付方能不能为空")
	private Integer payType;
	/**
	 * 支付状态(1:未支付,2:已支付)
	 */
	private Integer payStatus;
	/**
	 * 支付金额
	 */
	@Amount
	private BigDecimal payAmount;
	/**
	 * 优惠金额
	 */
	@Amount
	private BigDecimal couponAmount;
	/**
	 * 订单总额
	 */
	@Amount
	private BigDecimal orderAmount;
	/**
	 * 退款金额
	 */
	@Amount
	private BigDecimal refundAmount;

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

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

}
