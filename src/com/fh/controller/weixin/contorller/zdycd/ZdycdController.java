package com.fh.controller.weixin.contorller.zdycd;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.menu.MenuService;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.DateTimeUtil;
import com.fh.util.GuidUtil;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;

/** 
 * 类名称：UserController
 * 创建人：fuhang 
 * 创建时间：2014年6月28日
 * @version
 */
@Controller
@RequestMapping(value="/zdycd")
public class ZdycdController extends BaseController {
	
	@Resource(name="zdycdService")
	private ZdycdService zdycdService;
	@Resource(name="menuService")
	private MenuService menuService;
	
	

	/**
	 * 跳转到自定义菜单
	 * @param session
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpSession session,Page page) throws Exception{
		mv.clear();
		getHome(session, page);
		pd = this.getPageData();
		this.setPdmenu(pd);
		
		//获取一级菜单
		pd.put("cdjb", "yjcd");
		List<PageData> yjcdLs = (List<PageData>) this.zdycdService.findList(pd);
		for (PageData p : yjcdLs) {
			pd.put("pid", p.getString("id"));
			pd.put("cdjb", "ejcd");
			List<PageData> ercdLs = (List<PageData>) this.zdycdService.findList(pd);
			p.put("ercdLs", ercdLs);
			p.put("ejcdSize", ercdLs.size());
		}
		pd.put("yjcdSize", yjcdLs.size());
		mv.addObject("yjcdLs", yjcdLs);
		mv.addObject("pd", pd);
		mv.setViewName("weixin/zdycd/zdycd_list");
		return mv;
	}
	
	
	/**
	 * 跳转到添加页面
	 * @param session
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toadd")
	public ModelAndView toadd(HttpSession session,Page page) throws Exception{
		mv.clear();
		pd = this.getPageData();
		
		mv.addObject("pd", pd);
		mv.setViewName("weixin/zdycd/zdycd_add");
		return mv;
	}
	
	
	@RequestMapping(value="/save")
	public ModelAndView save(HttpSession session,Page page,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request); 
		pd.put("id", GuidUtil.getSmailGuid());
		pd.put("status", "N");
		pd.put("tjsj", DateTimeUtil.getDateTimeStr());
		pd.put("xgsj", DateTimeUtil.getDateTimeStr());
		pd.put("tjr", ((User)session.getAttribute(Const.SESSION_USER)).getUSER_ID());
		this.zdycdService.save(pd);
		mv.addObject("pd", pd);
		mv.setViewName("weixin/zdycd/zdycd_add");
		return mv;
	}
	
	
	@RequestMapping(value="/toedit")
	public ModelAndView toedit(HttpSession session,Page page) throws Exception{
		mv.clear();
		pd = this.getPageData();
		
		PageData p = this.zdycdService.findInfo(pd);
		
		mv.addObject("p", p);
		mv.addObject("pd", pd);
		mv.setViewName("weixin/zdycd/zdycd_edit");
		return mv;
	}
	
	
	@RequestMapping(value="/update")
	public ModelAndView update(HttpSession session,Page page,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request); 
		pd.put("xgsj", DateTimeUtil.getDateTimeStr());
		pd.put("tjr", ((User)session.getAttribute(Const.SESSION_USER)).getUSER_ID());
		this.zdycdService.update(pd);
		mv.addObject("pd", pd);
		mv.setViewName("weixin/zdycd/zdycd_edit");
		return mv;
	}
	
	
	@RequestMapping(value="/remove")
	public void remove(PrintWriter out){
		mv.clear();
		try{
			pd = this.getPageData();
			zdycdService.remove(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	
	@RequestMapping(value="/tomessage")
	public ModelAndView tomessage(HttpSession session,Page page) throws Exception{
		mv.clear();
		pd = this.getPageData();
		
		PageData p = this.zdycdService.findInfo(pd);
		
		mv.addObject("p", p);
		mv.addObject("pd", pd);
		mv.setViewName("weixin/zdycd/zdycd_message");
		return mv;
	}
	
	
	@RequestMapping(value="/saveMessage")
	public ModelAndView saveMessage(HttpSession session,Page page,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request); 
		pd.put("xgsj", DateTimeUtil.getDateTimeStr());
		pd.put("tjr", ((User)session.getAttribute(Const.SESSION_USER)).getUSER_ID());
		this.zdycdService.saveMessage(pd);
		mv.addObject("pd", pd);
		mv.setViewName("weixin/zdycd/zdycd_message");
		return mv;
	}
	
	@RequestMapping(value="/toPx")
	public ModelAndView toPx(HttpSession session,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request);
		
		List<PageData> ls = (List<PageData>) this.zdycdService.findList(pd);
		
		mv.addObject("ls", ls);
		mv.addObject("pd", pd);
		mv.setViewName("weixin/zdycd/zdycd_px");
		return mv;
	}
	
	@RequestMapping(value="/updatePx")
	public ModelAndView updatePx(HttpSession session,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request);
		
		this.zdycdService.updatePx(pd);
		
		mv.addObject("pd", pd);
		mv.setViewName("weixin/zdycd/zdycd_px");
		return mv;
	}
	
	
	@RequestMapping(value="/sameMenu")
	public void sameMenu(PrintWriter out,HttpServletRequest request){
		mv.clear();
		try{
			pd = new PageData(this.getRequest());
			boolean bol = zdycdService.sameMenu(pd);
			String result="";
			if (bol) {
				result="sucess";
			}else{
				result="error";
			}
			out.write(result);
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
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
