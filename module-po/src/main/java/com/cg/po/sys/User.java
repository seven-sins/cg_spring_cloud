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
 * @author Rex.Tan 
 * 2019年3月25日 下午3:28:09
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	@Id
	private String id;
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
	/**
	 * 性别:1男,0女
	 */
	private Integer sex;

	@Length(max = 11, message = "手机号超出最大长度(11)")
	private String mobile;

	@Length(max = 30, message = "邮箱超出最大长度(30)")
	private String email;

	private Integer age;
	/**
	 * 证件号
	 */
	@Length(max = 18, message = "证件号超出最大长度(18)")
	private String idCard;

	private Date createTime;

	private String createBy;

	private Date updateTime;

	private String updateBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

}
