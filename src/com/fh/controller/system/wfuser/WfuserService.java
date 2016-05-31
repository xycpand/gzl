package com.fh.controller.system.wfuser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("wfuserService")
public class WfuserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//======================================================================================
	
	@SuppressWarnings("unchecked")
	public List<PageData> findList(Page page) throws Exception{
		return (List<PageData>) dao.findForList("WfuserMapper.findlistPage", page);
	}
	
	
	public PageData findInfo(PageData pd) throws Exception{
		return (PageData) dao.findForObject("WfuserMapper.findInfo", pd);
	}
	
	
	public void save(PageData pd) throws Exception{
		dao.save("WfuserMapper.save", pd);
	}
	
	public void update(PageData pd) throws Exception{
		dao.save("WfuserMapper.update", pd);
	}
	
	public void remove(PageData pd) throws Exception{
		dao.delete("WfuserMapper.remove", pd);
	}


	@SuppressWarnings("unchecked")
	public List<PageData> Wfuserfindlist(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WfuserMapper.findList", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> findWfuserForZtree(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WfuserMapper.findList", pd);
	}


	@SuppressWarnings("unchecked")
	public List<PageData> findUser(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WfuserMapper.findUser", pd);
	}
	
}
