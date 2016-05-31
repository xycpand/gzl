package com.fh.controller.weixin.contorller.gzhf;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.DateTimeUtil;
import com.fh.util.PageData;


@Service("gzhfService")
public class GzhfService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//======================================================================================
	
	@SuppressWarnings("unchecked")
	public List<PageData> findList(Page page) throws Exception{
		return (List<PageData>) dao.findForList("GzhfMapper.findlistPage", page);
	}
	
	
	public PageData findInfo(PageData pd) throws Exception{
		return (PageData) dao.findForObject("GzhfMapper.findInfo", pd);
	}
	
	
	public void save(PageData pd) throws Exception{
		dao.save("GzhfMapper.save", pd);
	}
	
	public void update(PageData pd) throws Exception{
		dao.save("GzhfMapper.update", pd);
	}
	
	public void remove(PageData pd) throws Exception{
		dao.delete("GzhfMapper.remove", pd);
	}


	public void updateStatus(PageData pd) throws Exception {
		PageData p = new PageData();
		p.put("status", "N");
		p.put("xgsj", DateTimeUtil.getDateTimeStr());
		dao.save("GzhfMapper.update", p);
		p.put("status", "Y");
		p.put("id", pd.getString("id"));
		dao.save("GzhfMapper.update", p);
	}
	
}
