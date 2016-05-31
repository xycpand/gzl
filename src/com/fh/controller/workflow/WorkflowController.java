package com.fh.controller.workflow;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.menu.MenuService;
import com.fh.controller.web.dcbgl.DcbglService;
import com.fh.controller.workflow.util.ProcessDiagramGenerator;
import com.fh.controller.workflow.util.SystemContext;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.DateTimeUtil;
import com.fh.util.Json;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;
import com.fh.util.Verify;
import com.fh.util.database.DatabaseUtil;

/**
 * 调查表草稿箱信息
 * @author anxingtao
 *
 */
@Controller
@RequestMapping(value="/workflow")
public class WorkflowController extends BaseController {
	
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name="dcbglService")
	private DcbglService dcbglService;
	
	@Resource
	private RepositoryService repositoryService;
	
	@Resource
	private RuntimeService runtimeService;
	
	@Resource
	private TaskService taskService;
	
	@Resource
	private FormService formService;
	
	@Resource
	private HistoryService historyService;
	
	//===================================================================================================
	

	/**
	 * 查询流程部署
	 * @throws Exception 
	 */
	@RequestMapping(value="/findDeploy")
	public ModelAndView findDeploy(HttpSession session,Page page) throws Exception{
		mv.clear();
		getHome(session, page);
		pd = this.getPageData();
		this.setPdmenu(pd);
		
		//1:查询部署对象信息，对应表（act_re_deployment）
		List<Deployment> depList = repositoryService.createDeploymentQuery().orderByDeploymenTime().asc().list();
		
//		//2:查询流程定义的信息，对应表（act_re_procdef）
//		List<ProcessDefinition> pdList = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionKey().asc().list();
	
		mv.addObject("pd", pd);
		mv.addObject("depList", depList);
		mv.setViewName("workflow/deploy_list");
		return mv;
	}
	
	/**
	 * @throws IOException 
	 * 
	  * @Title: delDeployment
	  * @Description: 删除流程定义
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@RequestMapping(value="/delDeployment")
	public void delDeployment(HttpServletRequest request,HttpServletResponse response) throws IOException{
		pd = new PageData(request);
		//1：获取部署对象ID
		String deploymentId = pd.getString("deploymentId");
		//2：使用部署对象ID，删除流程定义
		repositoryService.deleteDeployment(deploymentId, true);
		
		Json json = new Json();
		json.setSuccess(true);
		json.setMsg("删除成功。");
		this.writeJson(response, json);
	}
	
	
	/**
	 * 
	  * @Title: saveNewDeploye
	  * @Description: 部署流程定义
	  * @param @param file
	  * @param @param filename    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/saveNewDeploye")
	public ModelAndView saveNewDeploye(HttpServletRequest request) {
		pd = new PageData(request);
		try {
			CommonsMultipartResolver  multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if(multipartResolver.isMultipart(request)){
	             MultipartHttpServletRequest  multipartRequest = (MultipartHttpServletRequest)request;
	             Iterator<String> iterator = multipartRequest.getFileNames();
	             while(iterator.hasNext()){
	                MultipartFile  file  = multipartRequest.getFile(iterator.next());
	                if(!file.isEmpty()){
		            	//上传到阿里云OSS服务器
//		            	String fileName = file.getOriginalFilename();
		            	//2：将File类型的文件转化成ZipInputStream流
		    			ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
		    			repositoryService.createDeployment()//创建部署对象
		    							.name(pd.getString("filename"))//添加部署名称
		    							.addZipInputStream(zipInputStream)//
		    							.deploy();//完成部署
		            }
	             }
	          }
		} catch (Exception e) {
			e.printStackTrace();
		}
		pd.put("label", "b");
		mv.addObject("pd", pd);
		mv.setViewName("workflow/deploy_add");
		return mv;
	}
	
	
	
	/**
	 * 
	  * @Title: findProcessDefinition
	  * @Description: 查询流程定义的信息，对应表（act_re_procdef）
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findProcessDefinition")
	public ModelAndView findProcessDefinition(HttpSession session,Page page) throws Exception{
		mv.clear();
		getHome(session, page);
		pd = this.getPageData();
		this.setPdmenu(pd);
		
		//2:查询流程定义的信息，对应表（act_re_procdef）
		List<ProcessDefinition> pdList = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionKey().asc().list();
	
		mv.addObject("pd", pd);
		mv.addObject("pdList", pdList);
		mv.setViewName("workflow/processDefinition_list");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: findProcessDefinitionForJson
	  * @Description: 查询流程定义 用于分配表单
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findProcessDefinitionForJson")
	public void findProcessDefinitionForJson(HttpServletResponse response) throws Exception{
		pd = this.getPageData();
		//2:查询流程定义的信息，对应表（act_re_procdef）
		List<ProcessDefinition> pdList = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionKey().asc().list();
		
		List<PageData> ls = new ArrayList<PageData>();
		for (ProcessDefinition pd : pdList) {
			PageData p = new PageData();
			p.put("key", pd.getKey());
			p.put("name", pd.getName());
			ls.add(p);
		}
		
        writeJson(response, ls);
	}
	
	
	
	/**
	 * 
	  * @Title: findViewImage
	  * @Description: 查询流程图
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findViewImage")
	public ModelAndView findViewImage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		pd = new PageData(request);
		mv.addObject("pd", pd);
		mv.setViewName("workflow/ActivitiProccessImagePage");
		return mv;
	}
	
	
	
	/** 
	 * 获取流程图像，已执行节点和流程线高亮显示
	 */
	@RequestMapping(value="/getActivitiProccessImage")
	public void getActivitiProccessImage(String pProcessInstanceId, HttpServletResponse response) throws Exception {
		logger.info("[开始]-获取流程图图像");
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			//  获取历史流程实例
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(pProcessInstanceId).singleResult();

			if (historicProcessInstance == null) {
				throw new Exception("获取流程实例ID[" + pProcessInstanceId + "]对应的历史流程实例失败！");
			} else {
				// 获取流程定义
				ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
						.getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());

				// 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
				List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
						.processInstanceId(pProcessInstanceId).orderByHistoricActivityInstanceId().asc().list();

				// 已执行的节点ID集合
				List<String> executedActivityIdList = new ArrayList<String>();
				int index = 1;
				logger.info("获取已经执行的节点ID");
				for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
					executedActivityIdList.add(activityInstance.getActivityId());
					logger.info("第[" + index + "]个已执行节点=" + activityInstance.getActivityId() + " : " +activityInstance.getActivityName());
					index++;
				}

				// 获取流程图图像字符流
				InputStream imageStream = ProcessDiagramGenerator.generateDiagram(processDefinition, "png", executedActivityIdList);

				response.setContentType("image/png");
				OutputStream os = response.getOutputStream();
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				os.close();
				imageStream.close();
			}
			logger.info("[完成]-获取流程图图像");
		} catch (Exception e) {
//			logger.error("【异常】-获取流程图失败！" + e.getMessage());
//			throw new Exception("获取流程图失败！" + e.getMessage());
			
			// 获取流程定义
			ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService.createProcessDefinitionQuery().processDefinitionId(pProcessInstanceId).singleResult();
			//2：获取资源文件表（act_ge_bytearray）中资源图片输入流InputStream
			InputStream in = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),processDefinition.getDiagramResourceName());
			//3：从response对象获取输出流
			OutputStream os = response.getOutputStream();
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			in.close();
		}
	}
	
	
	
	/**
	 * @throws Exception 
	 * 
	  * @Title: startProcess
	  * @Description: 启动流程
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@RequestMapping(value="/startProcess")
	public void startProcess(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
		pd = new PageData(request);
		String id = pd.getString("id");
		String bdid = pd.getString("bdid");
		String key = pd.getString("key");
		/**
		 * 4：从Session中获取当前任务的办理人，使用流程变量设置下一个任务的办理人
			    * inputUser是流程变量的名称，
			    * 获取的办理人是流程变量的值
		 */
		Map<String, Object> variables = new HashMap<String,Object>();
		variables.put("inputUser", ((User) session.getAttribute(Const.SESSION_USER)).getUSER_ID());//表示惟一用户
		/**
		 * 5：	(1)使用流程变量设置字符串（格式：LeaveBill.id的形式），通过设置，让启动的流程（流程实例）关联业务
   				(2)使用正在执行对象表中的一个字段BUSINESS_KEY（Activiti提供的一个字段），让启动的流程（流程实例）关联业务
		 */
		//格式：LeaveBill.id的形式（使用流程变量）
		String objId = key+"."+id+"."+bdid;
		variables.put("objId", objId);
		//6：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		runtimeService.startProcessInstanceByKey(key,objId,variables);
		
		//更新数据状态 N-Y
		PageData p = dcbglService.findInfo(pd);
		StringBuffer sb = new StringBuffer();
		sb.append("update "+p.get("zdybm")+" set ");
		sb.append(" status='1',xgsj='"+DateTimeUtil.getDateTimeStr()+"'");
		sb.append(" where id='"+bdid+"'");
		DatabaseUtil.executeSql(sb.toString());
		
		this.writeJson(response, true);
	}
	
	
	
	/**
	 * 
	  * @Title: findTaskList
	  * @Description: 获取任务管理信息
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findTaskList")
	public ModelAndView findTaskList(HttpSession session,Page page) throws Exception{
		mv.clear();
		getHome(session, page);
		pd = this.getPageData();
		this.setPdmenu(pd);
		//1：从Session中获取当前用户ID 
		String id = ((User) session.getAttribute(Const.SESSION_USER)).getUSER_ID();
		//获取任务
		List<Task> list = taskService.createTaskQuery()//
		.taskAssignee(id)//指定个人任务查询
		.orderByTaskCreateTime().asc()//
		.list();
		
	
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("workflow/task_list");
		return mv;
	}
	
	
	
	@RequestMapping(value="/findTaskInfo")
	public ModelAndView findTaskInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		pd = new PageData(request);
		
		//获取任务id
		String taskId = pd.getString("id");
		/**一：使用任务ID，查找请假单ID，从而获取请假单信息*/
		mv = findBdxxInfo(taskId);
		/**二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
		List<String> outcomeList = findOutComeListByTaskId(taskId);
		/**三：查询所有历史审核人的审核信息，帮助当前人完成审核，返回List<Comment>*/
		List<Comment> commentList = findCommentByTaskId(taskId);
		
		pd.put("taskId", taskId);
		mv.addObject("pd", pd);
		mv.addObject("outcomeList", outcomeList);
		mv.addObject("commentList", commentList);
		mv.setViewName("workflow/task_info");
		return mv;
	}
	
	
	
	/**
	 * 
	  * @Title: findBdxxInfo
	  * @Description: 查询表单信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public ModelAndView findBdxxInfo(String taskId) throws Exception{
		PageData pd = new PageData();
		//获取任务id
		/**一：使用任务ID，查找请假单ID，从而获取请假单信息*/
		//1：使用任务ID，查询任务对象Task
		Task task = taskService.createTaskQuery()//
						.taskId(taskId)//使用任务ID查询
						.singleResult();
		//2：使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
						.processInstanceId(processInstanceId)//使用流程实例ID查询
						.singleResult();
		//4：使用流程实例对象获取BUSINESS_KEY
		String buniness_key = pi.getBusinessKey();
		//5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
		String id = "";
		String bdid = "";
		if(Verify.verifyIsNotNull(buniness_key)){
			//截取字符串，取buniness_key小数点的第2个值
			id = buniness_key.split("\\.")[1];
			bdid=buniness_key.split("\\.")[2];
		}
		//查询请假单对象
		pd.put("id", id);
		PageData p = dcbglService.findInfo(pd);
		pd.put("table", p.getString("zdybm"));
		pd.put("bdid", bdid);
		PageData pdInfo = dcbglService.findBdxxInfo(pd);
		mv.addObject("p", p);
		mv.addObject("bdPd", pdInfo);
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: findOutComeListByTaskId
	  * @Description: 查询连线
	  * @param @param taskId
	  * @param @return    设定文件
	  * @return List<String>    返回类型
	  * @throws
	 */
	public List<String> findOutComeListByTaskId(String taskId) {
		//返回存放连线的名称集合
		List<String> list = new ArrayList<String>();
		//1:使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//2：获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		//3：查询ProcessDefinitionEntiy对象
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		//使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(processInstanceId)//使用流程实例ID查询
					.singleResult();
		//获取当前活动的id
		String activityId = pi.getActivityId();
		//4：获取当前的活动
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		//5：获取当前活动完成之后连线的名称
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if(pvmList!=null && pvmList.size()>0){
			for(PvmTransition pvm:pvmList){
				String name = (String) pvm.getProperty("name");
				if(Verify.verifyIsNotNull(name)){
					list.add(name);
				}
				else{
					list.add("默认提交");
				}
			}
		}
		return list;
	}
	
	
	
	
	/**
	 * 
	  * @Title: findCommentByTaskId
	  * @Description: 获取批注信息，传递的是当前任务ID，获取历史任务ID对应的批注
	  * @param @param taskId
	  * @param @return    设定文件
	  * @return List<Comment>    返回类型
	  * @throws
	 */
	public List<Comment> findCommentByTaskId(String taskId) {
		List<Comment> list = new ArrayList<Comment>();
		//使用当前的任务ID，查询当前流程对应的历史任务ID
		//使用当前任务ID，获取当前任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//使用任务ID查询
				.singleResult();
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
//		//使用流程实例ID，查询历史任务，获取历史任务对应的每个任务ID
//		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()//历史任务表查询
//						.processInstanceId(processInstanceId)//使用流程实例ID查询
//						.list();
//		//遍历集合，获取每个任务ID
//		if(htiList!=null && htiList.size()>0){
//			for(HistoricTaskInstance hti:htiList){
//				//任务ID
//				String htaskId = hti.getId();
//				//获取批注信息
//				List<Comment> taskList = taskService.getTaskComments(htaskId);//对用历史完成后的任务ID
//				list.addAll(taskList);
//			}
//		}
		list = taskService.getProcessInstanceComments(processInstanceId);
		return list;
	}
	
	
	
	
	
	
	/**
	 * 
	  * @Title: submitTask
	  * @Description: 提交任务
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/submitTask")
	public ModelAndView submitTask(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		pd = new PageData(request);
		SystemContext.setCurrentUser(((User) session.getAttribute(Const.SESSION_USER)).getUSER_ID());
		//获取任务ID
		String taskId = pd.getString("taskId");
		//获取连线的名称
		String outcome = pd.getString("outcome");
		//批注信息
		String message = pd.getString("comment");
		//获取请假单ID
		String id = pd.getString("id");
		String bdid = pd.getString("bdid");
		/**
		 * 1：在完成之前，添加一个批注信息，向act_hi_comment表中添加数据，用于记录对当前申请人的一些审核信息
		 */
		//使用任务ID，查询任务对象，获取流程流程实例ID
		Task task = taskService.createTaskQuery()//
						.taskId(taskId)//使用任务ID查询
						.singleResult();
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		/**
		 * 注意：添加批注的时候，由于Activiti底层代码是使用：
		 * 		String userId = Authentication.getAuthenticatedUserId();
			    CommentEntity comment = new CommentEntity();
			    comment.setUserId(userId);
			  所有需要从Session中获取当前登录人，作为该任务的办理人（审核人），对应act_hi_comment表中的User_ID的字段，不过不添加审核人，该字段为null
			 所以要求，添加配置执行使用Authentication.setAuthenticatedUserId();添加当前任务的审核人
		 * */
		Authentication.setAuthenticatedUserId(((User) session.getAttribute(Const.SESSION_USER)).getUSER_ID());
		taskService.addComment(taskId, processInstanceId, message);
		/**
		 * 2：如果连线的名称是“默认提交”，那么就不需要设置，如果不是，就需要设置流程变量
		 * 在完成任务之前，设置流程变量，按照连线的名称，去完成任务
				 流程变量的名称：outcome
				 流程变量的值：连线的名称
		 */
		Map<String, Object> variables = new HashMap<String,Object>();
		if(outcome!=null && !outcome.equals("默认提交")){
			variables.put("outcome", outcome);
		}

		//3：使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(taskId, variables);
		//4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		
		/**
		 * 5：在完成任务之后，判断流程是否结束
   			如果流程结束了，更新请假单表的状态从1变成2（审核中-->审核完成）
		 */
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
						.processInstanceId(processInstanceId)//使用流程实例ID查询
						.singleResult();
		//流程结束了
		if(pi==null){
			//更新请假单表的状态从1变成2（审核中-->审核完成）
			PageData p = dcbglService.findInfo(pd);
			StringBuffer sb = new StringBuffer();
			sb.append("update "+p.get("zdybm")+" set ");
			sb.append(" status='2',xgsj='"+DateTimeUtil.getDateTimeStr()+"'");
			sb.append(" where id='"+bdid+"'");
			DatabaseUtil.executeSql(sb.toString());
		}
		
		pd.put("label", "b");
		mv.addObject("pd", pd);
		mv.setViewName("workflow/task_info");
		return mv;
	}
	
	
	
	
	/**
	 * 
	  * @Title: getWFImage
	  * @Description: 当前任务查看流程图
	  * @param @param pProcessInstanceId
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/getWFImage")
	public void getWFImage(String id,String bdid,String key, HttpServletResponse response) throws Exception {
		logger.info("[开始]-获取流程图图像");
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			String str = key+"."+id+"."+bdid;
			//  获取历史流程实例
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
					.processInstanceBusinessKey(str).singleResult();
			
			if (historicProcessInstance == null) {
				throw new Exception("获取流程实例ID[" + historicProcessInstance.getId() + "]对应的历史流程实例失败！");
			} else {
				// 获取流程定义
				ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
						.getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());

				// 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
				List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
						.processInstanceId(historicProcessInstance.getId()).orderByHistoricActivityInstanceId().asc().list();

				// 已执行的节点ID集合
				List<String> executedActivityIdList = new ArrayList<String>();
				int index = 1;
				logger.info("获取已经执行的节点ID");
				for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
					executedActivityIdList.add(activityInstance.getActivityId());
					logger.info("第[" + index + "]个已执行节点=" + activityInstance.getActivityId() + " : " +activityInstance.getActivityName());
					index++;
				}

				// 获取流程图图像字符流
				InputStream imageStream = ProcessDiagramGenerator.generateDiagram(processDefinition, "png", executedActivityIdList);

				response.setContentType("image/png");
				OutputStream os = response.getOutputStream();
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				os.close();
				imageStream.close();
			}
			logger.info("[完成]-获取流程图图像");
		} catch (Exception e) {
			logger.error("【异常】-获取流程图失败！" + e.getMessage());
			throw new Exception("获取流程图失败！" + e.getMessage());
		}
	}
	
	
	
	@RequestMapping(value="/findBdxxTask")
	public ModelAndView findBdxxTask(HttpServletRequest request,HttpServletResponse response) throws Exception{
		pd = new PageData(request);
		String id = pd.getString("id");
		String bdid = pd.getString("bdid");
		String key = pd.getString("key");
		
		//查询请假单对象
		pd.put("id", id);
		PageData p = dcbglService.findInfo(pd);
		pd.put("table", p.getString("zdybm"));
		pd.put("bdid", bdid);
		PageData pdInfo = dcbglService.findBdxxInfo(pd);
		
		String str = key+"."+id+"."+bdid;
		//  获取历史流程实例
//		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
//				.processInstanceBusinessKey(str).singleResult();
		
		/**2:使用历史的流程变量查询，返回历史的流程变量的对象，获取流程实例ID*/
		HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery()//对应历史的流程变量表
						.variableValueEquals("objId", str)//使用流程变量的名称和流程变量的值查询
						.singleResult();
		//流程实例ID
		String processInstanceId = hvi.getProcessInstanceId();
		List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
		
		mv.addObject("p", p);
		mv.addObject("bdPd", pdInfo);
		mv.addObject("list", list);
		mv.setViewName("workflow/task_bdxx_info");
		return mv;
	}
	
	
	
	@RequestMapping(value="/findHiTask")
	public ModelAndView findHiTask(HttpServletRequest request,HttpServletResponse response) throws Exception{
		pd = new PageData(request);
		String id = "";
		String bdid = "";
		String bk = pd.getString("businessKey");
		if(Verify.verifyIsNotNull(bk)){
			//截取字符串，取buniness_key小数点的第2个值
			id = bk.split("\\.")[1];
			bdid=bk.split("\\.")[2];
		}
		
		//查询请假单对象
		pd.put("id", id);
		PageData p = dcbglService.findInfo(pd);
		pd.put("table", p.getString("zdybm"));
		pd.put("bdid", bdid);
		PageData pdInfo = dcbglService.findBdxxInfo(pd);
		
		/**2:使用历史的流程变量查询，返回历史的流程变量的对象，获取流程实例ID*/
		HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery()//对应历史的流程变量表
						.variableValueEquals("objId", bk)//使用流程变量的名称和流程变量的值查询
						.singleResult();
		//流程实例ID
		String processInstanceId = hvi.getProcessInstanceId();
		List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
		
		mv.addObject("p", p);
		mv.addObject("bdPd", pdInfo);
		mv.addObject("list", list);
		mv.setViewName("workflow/task_bdxx_info");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: findHiTaskList
	  * @Description: 查询历史处理任务
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findHiTaskList")
	public ModelAndView findHiTaskList(HttpSession session,Page page) throws Exception{
		mv.clear();
		getHome(session, page);
		pd = this.getPageData();
		this.setPdmenu(pd);
		//1：从Session中获取当前用户ID 
		String user = ((User) session.getAttribute(Const.SESSION_USER)).getUSER_ID();
		//获取任务
		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()//
									.taskAssignee(user)
									.orderByHistoricTaskInstanceEndTime().desc()
									.list();
		TreeSet<String> set = new TreeSet<String>();
		for (HistoricTaskInstance historicTaskInstance : list) {
			set.add(historicTaskInstance.getProcessInstanceId());
		}
		List<PageData> myLs = new ArrayList<PageData>();
		
		if(set.size()>0){
			List<HistoricProcessInstance> ls = historyService.createHistoricProcessInstanceQuery().processInstanceIds(set).orderByProcessInstanceEndTime().desc().list();
			
			
			for (HistoricProcessInstance historicProcessInstance : ls) {
				PageData piPd = new PageData();
				piPd.put("businessKey", historicProcessInstance.getBusinessKey());
				piPd.put("endTime", historicProcessInstance.getEndTime());
				piPd.put("id", historicProcessInstance.getId());
				piPd.put("processDefinitionId", historicProcessInstance.getProcessDefinitionId());
				piPd.put("startUserId", historicProcessInstance.getStartUserId());
				
				String bk = historicProcessInstance.getBusinessKey();
				String id = "";
				String bdid = "";
				if(Verify.verifyIsNotNull(bk)){
					//截取字符串，取buniness_key小数点的第2个值
					id = bk.split("\\.")[1];
					bdid=bk.split("\\.")[2];
				}
				//查询请假单对象
				pd.put("id", id);
				PageData p = dcbglService.findInfo(pd);
				pd.put("table", p.getString("zdybm"));
				pd.put("bdid", bdid);
				PageData pdInfo = dcbglService.findBdxxInfo(pd);
				piPd.put("p", p);
				piPd.put("bdPd", pdInfo);
				myLs.add(piPd);
			}
		}
		mv.addObject("pd", pd);
		mv.addObject("myLs", myLs);
		mv.setViewName("workflow/hitask_list");
		return mv;
	}
	

	
	
	
	
	
	
	
	
	/**
	 * 
	  * @Title: findDeploy
	  * @Description: 查询model数据列表
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findModelList")
	public ModelAndView findModelList(HttpSession session,Page page) throws Exception{
		mv.clear();
		getHome(session, page);
		pd = this.getPageData();
		this.setPdmenu(pd);
		
		List<Model> list = repositoryService.createModelQuery()
			.list();
		
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("workflow/model_list");
		return mv;
	}
	
	
	
	/**
	 * 
	  * @Title: addModel
	  * @Description: 创建模型
	  * @param @param session
	  * @param @param request
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/addModel")
	public void addModel(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception{
		pd = new PageData(request);
		try {
			String name = pd.getString("name");
			String key = pd.getString("key");
			String description = pd.getString("description");
			
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace",
					"http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.put("stencilset", stencilSetNode);
			Model modelData = repositoryService.newModel();

			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			description = StringUtils.defaultString(description);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
					description);
			modelData.setMetaInfo(modelObjectNode.toString());
			modelData.setName(name);
			modelData.setKey(StringUtils.defaultString(key));
			
			//保存模型
			repositoryService.saveModel(modelData);
			repositoryService.addModelEditorSource(modelData.getId(),
					editorNode.toString().getBytes("UTF-8"));
			
			//跳转到activiti modeler渲染模型到页面
			response.sendRedirect(request.getContextPath()
					+ "/service/editor?id=" + modelData.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @throws IOException 
	  * @Title: delete
	  * @Description: 删除模型
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@RequestMapping(value="/deleteModel")
	public void deleteModel(HttpServletRequest request,HttpServletResponse response) throws IOException {
		pd = new PageData(request);
		repositoryService.deleteModel(pd.getString("id"));
		this.writeJson(response, "true");
	}
	
	
	/**
	 * 
	  * @Title: deploy
	  * @Description: 根据Model部署流程
	  * @param @param request
	  * @param @param response    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/deploy")
	public void deploy(HttpServletRequest request,HttpServletResponse response) {
		pd = new PageData(request);
		String modelId = pd.getString("modelId");
		
		try {
			Model modelData = repositoryService.getModel(modelId);
			ObjectNode modelNode = (ObjectNode) new ObjectMapper()
			.readTree(repositoryService.getModelEditorSource(modelData
					.getId()));
			byte[] bpmnBytes = null;

			BpmnModel model = new BpmnJsonConverter()
					.convertToBpmnModel(modelNode);
			bpmnBytes = new BpmnXMLConverter().convertToXML(model, "UTF-8");
			System.out.println(new String(bpmnBytes));
			String processName = modelData.getName() + ".bpmn20.xml";
			repositoryService.createDeployment()
			.name(modelData.getName())
			.addInputStream(processName, new ByteArrayInputStream(bpmnBytes)).deploy();
//			.addString(processName, new String(bpmnBytes)).deploy();
			
			Json json = new Json();
			json.setMsg("流程【"+modelData.getName()+"】部署成功。");
			json.setSuccess(true);
			this.writeJson(response, json);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	
	
	/**
	 * 
	  * @Title: getExportBpmnFile
	  * @Description: 导出model的xml文件
	  * @param @param request
	  * @param @param response
	  * @param @throws JsonProcessingException
	  * @param @throws IOException    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/getExportBpmnFile")
	public void getExportBpmnFile(HttpServletRequest request,HttpServletResponse response) throws JsonProcessingException, IOException {
		pd = new PageData(request);
		String modelId = pd.getString("modelId");
		
		ByteArrayInputStream in = null;
		Model modelData = repositoryService.getModel(modelId);
		BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
		JsonNode editorNode = new ObjectMapper().readTree(repositoryService
				.getModelEditorSource(modelData.getId()));
		BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
		BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
		byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
		
//		pd.put("exportBpmnFileName", bpmnModel.getMainProcess().getId() + ".bpmn20.xml");
		in = new ByteArrayInputStream(bpmnBytes);
		
		String filename = new String((modelData.getName() +"("+modelData.getKey()+").bpmn20.xml").getBytes(), "GBK");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ filename);
		OutputStream os = response.getOutputStream();
		byte[] bb = new byte[1024*8];
		int i = 0;
		while ((i = in.read(bb)) > 0) {
			os.write(bb, 0, i);
		}
		os.close();
		os.flush();
		in.close();
	}
	
	
	
	
	/**
	 * export :  下载流程图
	 * @param  name    
	 * @param  @return    
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 * @Exception 异常对象  
	 * @date 2014-3-4
	 * @@version
	 */
	@RequestMapping(value="/exportImg")
	public void exportImg(HttpServletRequest request,HttpServletResponse response) throws JsonProcessingException, IOException {
		pd = new PageData(request);
		String modelId = pd.getString("modelId");
		
		Model modelData = repositoryService.getModel(modelId);
		byte[] bytes = repositoryService.getModelEditorSourceExtra(modelData.getId());
		
		InputStream in = new ByteArrayInputStream(bytes);
//		String filename = new String((modelData.getName()+modelData.getVersion() + ".bpmn20.png").getBytes(), "UTF-8");
//		response.addHeader("Content-Disposition", "attachment;filename="
//				+ filename);
		OutputStream os = response.getOutputStream();
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		os.flush();
		in.close();
	}
	

	
	
	
	/**
	 * 
	  * @Title: convert2Model
	  * @Description: 流程定义转换模型
	  * @param @param request
	  * @param @param response    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/convert2Model")
	public void convert2Model(HttpServletRequest request,HttpServletResponse response) {
		pd = new PageData(request);
		String definitionId = pd.getString("definitionId");
		
		try {
			//我的
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			//！我的
			
			ProcessDefinition processDefinition = repositoryService
					.createProcessDefinitionQuery()
					.processDefinitionId(definitionId).singleResult();

			InputStream bpmnStream = repositoryService.getResourceAsStream(
					processDefinition.getDeploymentId(),
					processDefinition.getResourceName());
			XMLInputFactory xif = XMLInputFactory.newInstance();
			InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
			XMLStreamReader xtr = xif.createXMLStreamReader(in);
			BpmnModel bpmnModel = new BpmnXMLConverter()
					.convertToBpmnModel(xtr);

			if (bpmnModel.getMainProcess() == null
					|| bpmnModel.getMainProcess().getId() == null) {
				out.print("转换失败...");
			} else {

				if (bpmnModel.getLocationMap().size() == 0) {
					out.print("转换失败...");
				} else {

					BpmnJsonConverter converter = new BpmnJsonConverter();
					ObjectNode modelNode = converter.convertToJson(bpmnModel);
					Model modelData = repositoryService.newModel();

					ObjectNode modelObjectNode = new ObjectMapper()
							.createObjectNode();
					modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME,
							processDefinition.getName());
					modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION,
							1);
					modelObjectNode.put(
							ModelDataJsonConstants.MODEL_DESCRIPTION,
							processDefinition.getDescription());
					modelData.setMetaInfo(modelObjectNode.toString());
					modelData.setName(processDefinition.getName());

					repositoryService.saveModel(modelData);

					repositoryService.addModelEditorSource(modelData.getId(),
							modelNode.toString().getBytes("utf-8"));

					response.sendRedirect(this.getRequest()
							.getContextPath()
							+ "/service/editor?id="
							+ modelData.getId());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	
	/*
	 * 菜单 =====================================================================================================
	 */
	public void getHome(HttpSession session, Page page) throws Exception{
			pd = this.getPageData();
			String roleRights = session.getAttribute("sessionRoleRights").toString();
			//String userRights = session.getAttribute("sessionUserRights").toString();
			String userRights = "";
			//避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限限都存入session
			List<Menu> menuList = menuService.listAllMenu();
			if(Tools.notEmpty(userRights) || Tools.notEmpty(roleRights)){
				for(Menu menu : menuList){
					menu.setHasMenu(RightsHelper.testRights(userRights, menu.getMENU_ID()) || RightsHelper.testRights(roleRights, menu.getMENU_ID()));
					if(menu.isHasMenu()){
						List<Menu> subMenuList = menu.getSubMenu();
						for(Menu sub : subMenuList){
							sub.setHasMenu(RightsHelper.testRights(userRights, sub.getMENU_ID()) || RightsHelper.testRights(roleRights, sub.getMENU_ID()));
						}
					}
				}
			}
				mv.addObject("menuList", menuList);
	}
	
	//===========================================================================================================
}
