package com.fh.controller.web.cgx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.util.DateTimeUtil;
import com.fh.util.GuidUtil;
import com.fh.util.PageData;
import com.fh.util.database.DatabaseUtil;


@Service("cgxService")
public class CgxService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//======================================================================================
	
	/**
	 * 查询信息列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findList(Page page) throws Exception{
		return (List<PageData>) dao.findForList("CgxMapper.findlistPage", page);
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
		return (PageData)dao.findForObject("CgxMapper.findInfo", pd);
	}
	
	
	/**
	 * @throws Exception 
	 * 
	  * @Title: save
	  * @Description: 保存数据
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void save(PageData pd) throws Exception{
		StringBuffer field = new StringBuffer(); 
		StringBuffer text = new StringBuffer(); 
		String ids[] = pd.getString("ids").split(",");
		String vals[] = pd.getString("vals").split(",");

		System.out.println((int)Math.floor(ids.length/2));
		
		int num = (int)Math.floor(ids.length/2);
		for (int i = 0; i < num; i++) {
			text.append(vals[i]+",");
			field.append(ids[i]+",");
		}
		
		pd.put("field", field.substring(0, field.length()-1));
		pd.put("text", text.substring(0, text.length()-1));
		
		dao.save("CgxMapper.save", pd);
	}
	
	
	/**
	 * 
	  * @Title: update
	  * @Description: 更新信息
	  * @param @param pd
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void update(PageData pd) throws Exception{
		StringBuffer field = new StringBuffer(); 
		StringBuffer text = new StringBuffer(); 
		String ids[] = pd.getString("ids").split(",");
		String vals[] = pd.getString("vals").split(",");

		System.out.println((int)Math.floor(ids.length/2));
		
		int num = (int)Math.floor(ids.length/2);
		for (int i = 0; i < num; i++) {
			text.append(vals[i]+",");
			field.append(ids[i]+",");
		}
		
		pd.put("field", field.substring(0, field.length()-1));
		pd.put("text", text.substring(0, text.length()-1));
		
		dao.update("CgxMapper.update", pd);
	}
	
	/**
	 * 
	  * @Title: delete
	  * @Description: 删除信息
	  * @param @param pd
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void remove(PageData pd) throws Exception{
		dao.delete("CgxMapper.delete", pd);
	}


	public void scdcb(PageData pd) throws Exception {
		PageData p = (PageData)dao.findForObject("CgxMapper.findInfo", pd);
		String zdybm = GuidUtil.getTableName();
		p.put("lx", "dcb");
		p.put("zdybm", zdybm);
		p.put("yhids", pd.getString("yhids"));
		dao.save("CgxMapper.saveForm", p);
		
		/*
		 * 创建数据库表executeSql
		 */
		StringBuffer sb = new StringBuffer();
		sb.append("create table "+zdybm).append(" ( id varchar(50) not null comment '主键ID', yhid varchar(50) not null comment '用户ID', dcbid varchar(50) not null comment '调查表ID', ");
		
		String[] fields = p.getString("field").split(",");
		String[] texts = p.getString("text").split(",");
		for (int i = 0; i < fields.length; i++) {
			sb.append(fields[i]+" varchar(500) null comment '"+texts[i]+"', ");
		}
		sb.append(" status varchar(50) null comment '状态', ");
		sb.append(" tjsj varchar(50) null comment '提交时间', ").append(" xgsj varchar(50) null comment '修改时间', ");
		sb.append(" constraint PK_YRXX_CPB_BUILDER_FORM primary key clustered (id) )");
		
		System.out.println(sb);
		DatabaseUtil.executeSql(sb.toString());
		//查询数据
		String[] yhids = pd.getString("yhids").split(",");
		for (String yhid : yhids) {
			String sql = "insert into "+zdybm+" (id,yhid,dcbid,status,xgsj,tjsj) values ('"+GuidUtil.getGuid()+"','"+yhid+"','"+p.get("id")+"','N','"+DateTimeUtil.getDateTimeStr()+"','"+DateTimeUtil.getDateTimeStr()+"')";
			System.out.println(sql);
			DatabaseUtil.executeSql(sql);
		}
		
		dao.delete("CgxMapper.delete", pd);
	}

	
	@SuppressWarnings("unchecked")
	public List<PageData> findYhxx(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CgxMapper.findYhxx", pd);
	}


	public void saveBdxx(PageData pd) throws Exception {
		PageData p = (PageData)dao.findForObject("CgxMapper.findInfo", pd);
		String zdybm = GuidUtil.getTableName();
		p.put("lx", "bdxx");
		p.put("zdybm", zdybm);
		p.put("activiti_key", pd.get("key"));
		p.put("yhids", pd.getString("yhids"));
		dao.save("CgxMapper.saveForm", p);
		
		/*
		 * 创建数据库表executeSql
		 */
		StringBuffer sb = new StringBuffer();
		sb.append("create table "+zdybm).append(" ( id varchar(50) not null comment '主键ID', yhid varchar(50) not null comment '用户ID', dcbid varchar(50) not null comment '调查表ID', ");
		
		String[] fields = p.getString("field").split(",");
		String[] texts = p.getString("text").split(",");
		for (int i = 0; i < fields.length; i++) {
			sb.append(fields[i]+" varchar(500) null comment '"+texts[i]+"', ");
		}
		sb.append(" status varchar(50) null comment '状态', ");
		sb.append(" tjsj varchar(50) null comment '提交时间', ").append(" xgsj varchar(50) null comment '修改时间', ");
		sb.append(" constraint PK_YRXX_CPB_BUILDER_FORM primary key clustered (id) )");
		
		System.out.println(sb);
		DatabaseUtil.executeSql(sb.toString());
		
		dao.delete("CgxMapper.delete", pd);
		
		//添加菜单信息
		Menu menu = new Menu();
		menu.setMENU_ID(String.valueOf(Integer.parseInt(((PageData) dao.findForObject("MenuMapper.findMaxId", pd)).get("MID").toString())+1));
		menu.setMENU_NAME(p.getString("bt"));
		menu.setMENU_ICON("");
		menu.setMENU_ORDER("0");
		menu.setPARENT_ID("88");
		menu.setMENU_URL("dcbgl/findBdxx.do?id="+p.getString("id"));
		dao.save("MenuMapper.insertMenu", menu);
	}
	
	
	
	
}
