package com.cg.po.sys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * 角色
 * @author Rex.Tan 
 * 2019年6月21日 上午10:39:05
 */
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	/**
	 * 角色名称
	 */
	@NotBlank(message = "roleName不能为空")
	private String roleName;
	/**
	 * 描述
	 */
	private String roleDesc;

	private Integer isDelete;

	private Integer status;

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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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

}
