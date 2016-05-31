package com.fh.controller.workflow;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;


@Service("workflowService")
public class WorkflowService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	
	
}
