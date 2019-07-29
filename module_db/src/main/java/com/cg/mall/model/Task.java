package com.cg.mall.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class Task implements Serializable {
    @NotBlank(message = "taskId不能为空")
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date finishTime;

    @ApiModelProperty(value = "任务类型")
    private Date type;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "任务参数")
    private String params;

    private static final long serialVersionUID = 1L;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getType() {
        return type;
    }

    public void setType(Date type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(", taskName=").append(taskName);
        sb.append(", createTime=").append(createTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", finishTime=").append(finishTime);
        sb.append(", type=").append(type);
        sb.append(", status=").append(status);
        sb.append(", params=").append(params);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}