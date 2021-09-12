/*
 * package cn.ccsit.eeoi.common.process;
 * 
 * import java.util.List; import java.util.Map;
 * 
 * import org.activiti.api.runtime.shared.query.Page; import
 * org.activiti.api.runtime.shared.query.Pageable; import
 * org.activiti.api.task.model.Task; import
 * org.activiti.api.task.runtime.TaskRuntime; import
 * org.activiti.engine.HistoryService; import org.activiti.engine.ProcessEngine;
 * import org.activiti.engine.ProcessEngines; import
 * org.activiti.engine.RepositoryService; import
 * org.activiti.engine.RuntimeService; import org.activiti.engine.TaskService;
 * import org.activiti.engine.history.HistoricProcessInstance; import
 * org.activiti.engine.history.HistoricTaskInstance; import
 * org.activiti.engine.repository.Deployment; import
 * org.activiti.engine.runtime.ProcessInstance; import
 * org.activiti.engine.runtime.ProcessInstanceQuery; import
 * org.activiti.engine.task.Comment; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Component;
 * 
 * @Component("activitiProcess") public class ActivitiProcess {
 * 
 * @Autowired private RuntimeService runtimeService;
 * 
 * @Autowired private TaskService taskService;
 * 
 * @Autowired private TaskRuntime taskRuntime;
 * 
 * @Autowired HistoryService historyService;
 * 
 * public ActivitiProcess() {
 * 
 * }
 * 
 *//**
	 * 部署流程
	 * 
	 * @param deployKey           部署流程key
	 * @param deployName          部署流程名称
	 * @param processPathResource 流程资源路径
	 */
/*
 * 
 * public void deployProcess(String deployKey, String deployName, String
 * processPathResource) {
 * 
 * ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine(); if
 * (processEngine == null) { throw new
 * RuntimeException("初始化流程引擎错误：processEngine is null"); } RepositoryService
 * repositoryService = processEngine.getRepositoryService(); if
 * (repositoryService == null) { throw new
 * RuntimeException("repositoryService is null"); } Deployment deployment =
 * repositoryService.createDeployment().key(deployKey)
 * .addClasspathResource(processPathResource)// 添加bpmn文件 .name(deployName)
 * .deploy(); if (deployment == null) { throw new
 * RuntimeException("部署流程错误：deployment is null"); } }
 * 
 *//**
	 * 启动流程 *
	 * 
	 * @param processDefinitionKey 流程定义key
	 * @param businessKey          业务id
	 * @param variables
	 * @return
	 */
/*
 * 
 * public String startProcess(String processDefinitionKey, String businessKey,
 * Map<String, Object> variables) { if (processDefinitionKey == null) { throw
 * new RuntimeException("流程定义错误：processDefinitionKey is null"); }
 * ProcessInstance instance = null; if (businessKey == null) { if (variables ==
 * null) { instance =
 * runtimeService.startProcessInstanceByKey(processDefinitionKey); } else {
 * instance = runtimeService.startProcessInstanceByKey(processDefinitionKey,
 * variables); } } else { if (variables == null) { instance =
 * runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey);
 * } else { instance =
 * runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey,
 * variables); } }
 * 
 * if (instance == null) { throw new
 * RuntimeException("启动流程错误：instance is null"); }
 * 
 * return instance.getId(); }
 * 
 *//**
	 * 获取第一步任务id
	 * 
	 * @param processInstanceId 流程实例id
	 * @param userId            用户名
	 * @return 任务id
	 */
/*
 * 
 * public String getFirstTask(String processInstanceId, String assignee) {
 * org.activiti.engine.task.Task task =
 * taskService.createTaskQuery().processInstanceId(processInstanceId)
 * .taskAssignee(assignee).singleResult(); if (task == null) { throw new
 * RuntimeException("提取第一步任务错误：task is null"); } return task.getId(); }
 * 
 *//**
	 * 查询分页后任务列表
	 * 
	 * @param taskRuntime
	 * @param pageable    分页
	 * @return
	 */
/*
 * 
 * public List<Task> getTasks(Pageable pageable) {
 * 
 * Page<Task> pageTasks = taskRuntime.tasks(pageable); return
 * pageTasks.getContent();
 * 
 * }
 * 
 *//**
	 * 查询任务数量
	 * 
	 * @param taskRuntime
	 * @param taskName    任务名称
	 * @return
	 */
/*
 * 
 * public List<org.activiti.engine.task.Task> getTasks(String taskName, String
 * assignee) {
 * 
 * return
 * taskService.createTaskQuery().taskName(taskName).taskAssignee(assignee).list(
 * );
 * 
 * }
 * 
 *//**
	 * 查询任务数量
	 * 
	 * @param taskRuntime
	 * @param taskName    任务名称
	 * @return
	 */
/*
 * 
 * public List<org.activiti.engine.task.Task> getTasks(String assignee) { return
 * taskService.createTaskQuery().taskAssignee(assignee).list(); }
 * 
 *//**
	 * 查询任务数量
	 * 
	 * @param taskRuntime
	 * @param taskName    任务名称
	 * @return
	 */
/*
 * 
 * public long getTasksCount(String taskName, String assignee) { return
 * taskService.createTaskQuery().taskName(taskName).taskAssignee(assignee).count
 * (); // todo 需测试SQL语句执行效率
 * 
 * }
 * 
 * public String getBusinessIdByTaskId(String taskId) { if (taskId == null) {
 * throw new RuntimeException("流程为空错误：taskId is null"); } //
 * taskRuntime.task(taskId).getBusinessKey(); String processInstanceId =
 * taskRuntime.task(taskId).getProcessInstanceId(); ProcessInstanceQuery
 * processInstanceQuery = runtimeService.createProcessInstanceQuery();
 * ProcessInstance processInstance =
 * processInstanceQuery.processInstanceId(processInstanceId).singleResult();
 * 
 * if (processInstance == null) { throw new
 * RuntimeException("查询流程为空：processInstance is null"); } return
 * processInstance.getBusinessKey(); }
 * 
 *//**
	 * 根据流程id获取业务id
	 * 
	 * @param processInstanceId 流程id
	 * @return 返回业务id
	 */
/*
 * 
 * public String getBusinessIdByProcessInstanceId(String processInstanceId) { if
 * (processInstanceId == null) { throw new
 * RuntimeException("流程为空错误：processInstanceId is null"); } ProcessInstanceQuery
 * processInstanceQuery = runtimeService.createProcessInstanceQuery();
 * ProcessInstance processInstance =
 * processInstanceQuery.processInstanceId(processInstanceId).singleResult();
 * 
 * if (processInstance == null) { throw new
 * RuntimeException("查询流程为空：processInstance is null"); } return
 * processInstance.getBusinessKey(); }
 * 
 *//**
	 * 完成任务
	 * 
	 * @param processInstanceId 流程实例id
	 * @param taskId            任务id
	 * @param variales          参数
	 * @param comment           描述
	 */
/*
 * 
 * public void completeTask(String processInstanceId, String taskId, Map<String,
 * Object> variables, String comment) { taskService.addComment(taskId,
 * processInstanceId, comment); taskService.complete(taskId, variables); }
 * 
 *//**
	 * 完成任务
	 * 
	 * @param processInstanceId 流程实例id
	 * @param taskId            任务id
	 * @param comment           描述
	 */
/*
 * public void completeTask(String processInstanceId, String taskId, String
 * comment) {
 * 
 * taskService.addComment(taskId, processInstanceId, comment);
 * taskService.complete(taskId);
 * 
 * }
 * 
 *//**
	 * 获取备注
	 * 
	 * @param taskId
	 * @return
	 *//*
		 * public String getTaskComment(String taskId) { List<Comment> comments =
		 * taskService.getTaskComments(taskId); if (comments != null && comments.size()
		 * > 0) { return taskService.getTaskComments(taskId).get(0).getFullMessage(); }
		 * return ""; }
		 * 
		 * public List<HistoricTaskInstance> getTaskHistoriesByProcessInstanceId(String
		 * processInstanceId) {
		 * 
		 * List<HistoricTaskInstance> taskHistories =
		 * historyService.createHistoricTaskInstanceQuery()
		 * .processInstanceId(processInstanceId).orderByTaskCreateTime().asc().list();
		 * return taskHistories; }
		 * 
		 * public List<HistoricTaskInstance> getTaskHistoriesByBusinessId(String
		 * businessId) { if (businessId == null) { throw new
		 * RuntimeException("业务key为空错误：businessId is null"); } HistoricProcessInstance
		 * historicProcessInstance; try { historicProcessInstance =
		 * historyService.createHistoricProcessInstanceQuery()
		 * .processInstanceBusinessKey(businessId).singleResult();
		 * 
		 * } catch (Exception ex) { throw new
		 * RuntimeException("同一业务key存在多条流程实例：businessId=" + businessId); } if
		 * (historicProcessInstance == null) { throw new
		 * RuntimeException("业务key对应的流程实例不存在：businessId=" + businessId); } return
		 * getTaskHistoriesByProcessInstanceId(historicProcessInstance.getId());
		 * 
		 * } }
		 */