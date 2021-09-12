package cn.ccsit.eeoi.ship.vo;

public class WfOldShowVo {
	private String Id;
	private String businessKey;
	private String taskId;
	// 序号
	private String num;
	// 步骤名称
	private String name;
	// 负责岗位/操作人
	private String operateJob;
	// 描述
	private String nodeRemark;
	// 操作类型
	private String operateType;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperateJob() {
		return operateJob;
	}

	public void setOperateJob(String operateJob) {
		this.operateJob = operateJob;
	}

	public String getNodeRemark() {
		return nodeRemark;
	}

	public void setNodeRemark(String nodeRemark) {
		this.nodeRemark = nodeRemark;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public WfOldShowVo() {
	}
}
