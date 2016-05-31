package com.fh.controller.weixin.contorller.gzhf;

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
 * 关注回复
 * @author anxingtao
 *
 */
@Controller
@RequestMapping(value="/gzhf")
public class GzhfController extends BaseController {
	
	@Resource(name="gzhfService")
	private GzhfService gzhfService;
	@Resource(name="menuService")
	private MenuService menuService;
	
	//===================================================================================================
	
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpSession session,Page page) throws Exception{
		mv.clear();
		getHome(session, page);
		pd = this.getPageData();
		this.setPdmenu(pd);
		
		page.setPd(pd);
		List<PageData> list = gzhfService.findList(page);
		
		mv.addObject("list", list);
		mv.addObject("pd", pd);
		mv.setViewName("weixin/gzhf/gzhf_list");
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
		mv.setViewName("weixin/gzhf/gzhf_add");
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
		this.gzhfService.save(pd);
		mv.addObject("pd", pd);
		mv.setViewName("weixin/gzhf/gzhf_add");
		return mv;
	}
	
	
	@RequestMapping(value="/toedit")
	public ModelAndView toedit(HttpSession session,Page page) throws Exception{
		mv.clear();
		pd = this.getPageData();
		
		PageData p = this.gzhfService.findInfo(pd);
		
		mv.addObject("p", p);
		mv.addObject("pd", pd);
		mv.setViewName("weixin/gzhf/gzhf_edit");
		return mv;
	}
	
	@RequestMapping(value="/update")
	public ModelAndView update(HttpSession session,Page page,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request); 
		pd.put("xgsj", DateTimeUtil.getDateTimeStr());
		pd.put("tjr", ((User)session.getAttribute(Const.SESSION_USER)).getUSER_ID());
		this.gzhfService.update(pd);
		mv.addObject("pd", pd);
		mv.setViewName("weixin/gzhf/gzhf_edit");
		return mv;
	}
	
	
	@RequestMapping(value="/remove")
	public void remove(PrintWriter out){
		mv.clear();
		try{
			pd = this.getPageData();
			gzhfService.remove(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}

	@RequestMapping(value="/updateStatus")
	public void updateStatus(PrintWriter out){
		mv.clear();
		try{
			pd = this.getPageData();
			gzhfService.updateStatus(pd);
			out.write("success");
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
