package com.cg.po.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.cg.utils.validator.Amount;

/**
 * 用户
 * 
 * @author Rex.Tan 
 * 2019年3月25日 下午3:28:09
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@Id
	private Long id;
	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空")
	@Length(max = 30, message = "用户名超出最大长度(30)")
	private String userName;
	/**
	 * 昵称
	 */
	@NotBlank(message = "昵称不能为空")
	@Length(max = 30, message = "昵称超出最大长度(30)")
	private String nickName;
	/**
	 * 密码
	 */
	@NotBlank(message = "密码不能为空")
	@Length(max = 15, message = "密码超出最大长度(15)")
	private String password;
	/**
	 * 金额
	 */
	@Amount(message = "金额超出范围{11,2}")
	private BigDecimal amount;
	/**
	 * 1:已删除
	 */
	private Integer isDelete;
	/**
	 * 1:正常, 0:禁用
	 */
	private Integer status;

	private Date createTime;

	private Long createBy;

	private Date updateTime;

	private Long updateBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
