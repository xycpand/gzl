package com.fh.controller.system.dqgl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("dqglService")
public class DqglService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//======================================================================================
	
	@SuppressWarnings("unchecked")
	public List<PageData> findList(Page page) throws Exception{
		return (List<PageData>) dao.findForList("DqglMapper.findlistPage", page);
	}
	
	
	public PageData findInfo(PageData pd) throws Exception{
		return (PageData) dao.findForObject("DqglMapper.findInfo", pd);
	}
	
	
	public void save(PageData pd) throws Exception{
		dao.save("DqglMapper.save", pd);
	}
	
	public void update(PageData pd) throws Exception{
		dao.save("DqglMapper.update", pd);
	}
	
	public void remove(PageData pd) throws Exception{
		dao.delete("DqglMapper.remove", pd);
	}


	@SuppressWarnings("unchecked")
	public List<PageData> dqglfindlist(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DqglMapper.findList", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> findDqglForZtree(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DqglMapper.findList", pd);
	}
	
}
