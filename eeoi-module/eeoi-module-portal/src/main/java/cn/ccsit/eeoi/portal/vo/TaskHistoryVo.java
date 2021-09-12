package cn.ccsit.eeoi.portal.vo;

import java.util.Date;

/**
 * 任务列表
 * @author lfj
 *
 */
public class TaskHistoryVo {
	
	
	public TaskHistoryVo() {
	}
	
	
	/**  todo 属性禁止public 
	 * 任务ID
	 */
	private String id;
	
	private int num;
	
	


	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}


	/**
	 * 经办人
	 */
	private String operator;
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}


	/**
	 * 业务ID
	 */
	private String businessId;
	
	/**
	 * 流程实例ID
	 */
	private String processInstanceId;
	
	/**
	 * 任务名称
	 */
	private String taskName;
	
	/**
	 * 开始日期
	 */
	private Date createDate;

	/**
	 * 结束日期
	 */
	private Date endDate;
	
	/**
	 * 审批描述
	 */
	private String comment;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
