package com.fh.controller.system.dqgl;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.menu.MenuService;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.util.DateTimeUtil;
import com.fh.util.GuidUtil;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;

/**
 * 地区管理
 * @author anxingtao
 *
 */
@Controller
@RequestMapping(value="/dqgl")
public class DqglController extends BaseController {
	
	@Resource(name="dqglService")
	private DqglService dqglService;
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
		List<PageData> list = dqglService.findList(page);
		
		mv.addObject("list", list);
		mv.addObject("pd", pd);
		mv.setViewName("system/dqgl/dqgl_list");
		return mv;
	}
	
	
	@RequestMapping(value="/findDqglForZtree")
	public void finddqglForZtree(HttpSession session,HttpServletResponse response) throws Exception{
		mv.clear();
		pd = this.getPageData();
		
		List<PageData> list = dqglService.findDqglForZtree(pd);
		
		this.writeJson(response, list);
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
		
		pd.put("flag", "");
		mv.addObject("pd", pd);
		mv.setViewName("system/dqgl/dqgl_add");
		return mv;
	}
	
	

	@RequestMapping(value="/save")
	public ModelAndView save(HttpSession session,Page page,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request); 
		
		pd.put("id", GuidUtil.getSmailGuid());
		pd.put("xgsj", DateTimeUtil.getDateTime());
		this.dqglService.save(pd);
		pd.put("flag", "add");
		mv.addObject("pd", pd);
		mv.setViewName("system/dqgl/dqgl_add");
		return mv;
	}
	
	
	@RequestMapping(value="/toedit")
	public ModelAndView toedit(HttpSession session,Page page) throws Exception{
		mv.clear();
		pd = this.getPageData();
		
		pd = this.dqglService.findInfo(pd);
		
		pd.put("flag", "");
		mv.addObject("pd", pd);
		mv.setViewName("system/dqgl/dqgl_edit");
		return mv;
	}
	
	@RequestMapping(value="/update")
	public ModelAndView update(HttpSession session,Page page,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request); 
		
		pd.put("xgsj", DateTimeUtil.getDateTime());
		this.dqglService.update(pd);
		pd.put("flag", "add");
		mv.addObject("pd", pd);
		mv.setViewName("system/dqgl/dqgl_edit");
		return mv;
	}
	
	
	@RequestMapping(value="/remove")
	public void remove(PrintWriter out){
		mv.clear();
		try{
			pd = this.getPageData();
			dqglService.remove(pd);
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
