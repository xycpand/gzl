package com.fh.controller.system.wfuser;

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
@RequestMapping(value="/wfuser")
public class WfuserController extends BaseController {
	
	@Resource(name="wfuserService")
	private WfuserService wfuserService;
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
		List<PageData> list = wfuserService.findList(page);
		
		mv.addObject("list", list);
		mv.addObject("pd", pd);
		mv.setViewName("system/wfuser/wfuser_list");
		return mv;
	}
	
	
	@RequestMapping(value="/findWfuserForZtree")
	public void findWfuserForZtree(HttpSession session,HttpServletResponse response) throws Exception{
		mv.clear();
		pd = this.getPageData();
		
		List<PageData> list = wfuserService.findWfuserForZtree(pd);
		
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
		mv.setViewName("system/wfuser/wfuser_add");
		return mv;
	}
	
	

	@RequestMapping(value="/save")
	public ModelAndView save(HttpSession session,Page page,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request); 
		
		pd.put("id", GuidUtil.getSmailGuid());
		pd.put("tjsj", DateTimeUtil.getDateTime());
		pd.put("xgsj", DateTimeUtil.getDateTime());
		this.wfuserService.save(pd);
		pd.put("flag", "add");
		mv.addObject("pd", pd);
		mv.setViewName("system/wfuser/wfuser_add");
		return mv;
	}
	
	
	@RequestMapping(value="/toedit")
	public ModelAndView toedit(HttpSession session,Page page) throws Exception{
		mv.clear();
		pd = this.getPageData();
		
		pd = this.wfuserService.findInfo(pd);
		
		pd.put("flag", "");
		mv.addObject("pd", pd);
		mv.setViewName("system/wfuser/wfuser_edit");
		return mv;
	}
	
	@RequestMapping(value="/update")
	public ModelAndView update(HttpSession session,Page page,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request); 
		
		pd.put("xgsj", DateTimeUtil.getDateTime());
		this.wfuserService.update(pd);
		pd.put("flag", "add");
		mv.addObject("pd", pd);
		mv.setViewName("system/wfuser/wfuser_edit");
		return mv;
	}
	
	
	@RequestMapping(value="/remove")
	public void remove(PrintWriter out){
		mv.clear();
		try{
			pd = this.getPageData();
			
			pd.put("pid", pd.get("id"));
			List<PageData> list = wfuserService.findWfuserForZtree(pd);
			if(list.size()>0){
				out.write("请先删除下级人员名称。");
				out.close();
			}else{
				wfuserService.remove(pd);
				out.write("success");
				out.close();
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}

	
	
	@RequestMapping(value="/findUser")
	public void findUser(HttpServletResponse response,HttpServletRequest request) throws Exception{
		pd = new PageData(request); 
		List<PageData> list = wfuserService.findUser(pd);
		this.writeJson(response, list);
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
