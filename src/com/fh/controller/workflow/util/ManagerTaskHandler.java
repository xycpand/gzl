package com.fh.controller.workflow.util;

import java.sql.SQLException;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.fh.util.database.DatabaseUtil;

/**
 * 员工经理任务分配
 *
 */
@SuppressWarnings("serial")
public class ManagerTaskHandler implements TaskListener {

	public void notify(DelegateTask delegateTask) {
//		根据id获得上级领导id
		String sql = "select pid from sys_workflow_user where yhid='"+SystemContext.getCurrentUser()+"'";
		
		try {
			String pid = (String) DatabaseUtil.findInfoSql(sql,"pid");
			
			String sql2 = "select yhid from sys_workflow_user where id='"+pid+"'";
			//设置个人任务的办理人
			delegateTask.setAssignee(DatabaseUtil.findInfoSql(sql2,"yhid"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	

}
