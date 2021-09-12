package cn.ccsit.eeoi.ship.vo;

import java.util.Date;

public class TaskShowVo {
	// 业务主键
	public String id;
	public String taskId;
	public String processInsId;
	// 工作编码
	public String jobNum;
	// 工作标题
	public String name;
	// 日期
	public Date date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessInsId() {
		return processInsId;
	}

	public void setProcessInsId(String processInsId) {
		this.processInsId = processInsId;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TaskShowVo() {
	}
}
