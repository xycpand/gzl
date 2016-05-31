package com.fh.controller.web.dcbgl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.DateTimeUtil;
import com.fh.util.GuidUtil;
import com.fh.util.PageData;
import com.fh.util.database.DatabaseUtil;


@Service("dcbglService")
public class DcbglService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//======================================================================================
	
	/**
	 * 查询信息列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findList(Page page) throws Exception{
		return (List<PageData>) dao.findForList("DcbglMapper.findlistPage", page);
	}
	
	
	/**
	 * 
	  * @Title: findInfo
	  * @Description: 查询信息详情
	  * @param @param pd
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findInfo(PageData pd) throws Exception{
		return (PageData)dao.findForObject("DcbglMapper.findInfo", pd);
	}
	
	/**
	 * 
	  * @Title: findZdydcbList
	  * @Description: TODO
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findZdydcbList(Page page) throws Exception{
		return (List<PageData>) dao.findForList("DcbglMapper.findZdydcblistPage", page);
	}
	
	/**
	 * 
	  * @Title: findMyDcbxxlistPage
	  * @Description: 查询我的调查表
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findMyDcbxxlistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("DcbglMapper.findMyDcbxxlistPage", page);
	}


	public PageData findMyDcbInfo(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DcbglMapper.findMyDcbInfo", pd);
	}


	public void saveMyDcbxx(PageData pd) throws Exception {
		PageData p = new PageData();
		p.put("id", pd.get("dcbid"));
		PageData pp = (PageData)dao.findForObject("DcbglMapper.findInfo", p);
		
		StringBuffer sb = new StringBuffer();
		sb.append(" update "+pp.getString("zdybm")+" set ");
		String[] fields = pp.getString("field").split(",");
		for (String field : fields) {
			sb.append(field+"='"+pd.getString(field)+"',");
		}
		sb.append(" status='Y',");
		sb.append(" xgsj='"+DateTimeUtil.getDateTimeStr()+"'");
		sb.append(" where id="+pd.get("id")); 
		
		System.out.println(sb.toString());
		DatabaseUtil.executeSql(sb.toString());
	}


	@SuppressWarnings("unchecked")
	public List<PageData> findBdxx(Page page) throws Exception {
		return (List<PageData>) dao.findForList("DcbglMapper.findBdxxlistPage", page);
	}


	public void saveBdxx(PageData pd) throws Exception {
		PageData pp = (PageData)dao.findForObject("DcbglMapper.findInfo", pd);
		
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into "+pp.getString("zdybm")+" (id,yhid,dcbid,");
		String[] fields = pp.getString("field").split(",");
		for (String field : fields) {
			sb.append(field+",");
		}
		sb.append("status,tjsj,xgsj)");
		sb.append(" values (");
		sb.append("'"+GuidUtil.getGuid()+"',"); 
		sb.append("'"+pd.get("yhid")+"',"); 
		sb.append("'"+pd.get("id")+"',"); 
		for (String field : fields) {
			sb.append("'"+pd.get(field)+"',");
		}
		sb.append("'N',"); 
		sb.append("'"+DateTimeUtil.getDateTimeStr()+"',"); 
		sb.append("'"+DateTimeUtil.getDateTimeStr()+"')"); 
		System.out.println(sb.toString());
		DatabaseUtil.executeSql(sb.toString());
	}


	public void removeBdxx(PageData pd) throws Exception {
		PageData pp = (PageData)dao.findForObject("DcbglMapper.findInfo", pd);
		
		StringBuffer sb = new StringBuffer();
		sb.append(" delete from "+pp.getString("zdybm")+" where id="+pd.get("bdid"));
		System.out.println(sb.toString());
		DatabaseUtil.executeSql(sb.toString());
	}


	public PageData findBdxxInfo(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DcbglMapper.findBdxxInfo", pd);
	}


	public void updateBdxx(PageData pd) throws Exception {
		PageData pp = (PageData)dao.findForObject("DcbglMapper.findInfo", pd);
		
		StringBuffer sb = new StringBuffer();
		sb.append(" update "+pp.getString("zdybm")+" set ");
		String[] fields = pp.getString("field").split(",");
		for (String field : fields) {
			sb.append(field+"='"+pd.get(field)+"',");
		}
		sb.append("xgsj='"+DateTimeUtil.getDateTimeStr()+"'");
		sb.append(" where id='"+pd.getString("bdid")+"'"); 
		System.out.println(sb.toString());
		DatabaseUtil.executeSql(sb.toString());
	}


	public void remove(PageData pd) throws Exception {
		//先查询表单
		PageData p = (PageData)dao.findForObject("DcbglMapper.findInfo", pd);
		
		//drop表
		String sql = "DROP TABLE IF EXISTS "+p.getString("zdybm")+";";
		DatabaseUtil.executeSql(sql);
		
		//删除该数据
		this.dao.delete("DcbglMapper.remove", pd);
	}
	
	
}
