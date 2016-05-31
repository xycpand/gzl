package com.fh.controller.web.cgx;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

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
import com.fh.util.Json;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;

/**
 * 调查表草稿箱信息
 * @author anxingtao
 *
 */
@Controller
@RequestMapping(value="/cgx")
public class CgxController extends BaseController {
	
	@Resource(name="cgxService")
	private CgxService cgxService;
	@Resource(name="menuService")
	private MenuService menuService;
	
	//===================================================================================================
	
	/**
	 * 查询信息列表
	 */
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpSession session,Page page) throws Exception{
		mv.clear();
		getHome(session, page);
		pd = this.getPageData();
		this.setPdmenu(pd);
		
		page.setPd(pd);
		List<PageData> list = cgxService.findList(page);
		
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("web/cgx/cgx_list");
		return mv;
	}

	
	/**
	 * 
	  * @Title: findInfo
	  * @Description: 查询信息详情
	  * @param @param session
	  * @param @param request
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findInfo")
	public ModelAndView findInfo(HttpSession session,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request);
		
		PageData p = (PageData) cgxService.findInfo(pd);
		
		mv.addObject("pd", pd);
		mv.addObject("p", p);
		mv.setViewName("web/cgx/cgx_info");
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
		mv.setViewName("web/cgx/cgx_add");
		return mv;
	}
	
	

	/**
	 * 
	  * @Title: save
	  * @Description: 保存信息
	  * @param @param session
	  * @param @param page
	  * @param @param request
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(HttpSession session,Page page,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request); 
		pd.put("id", GuidUtil.getSmailGuid());
		pd.put("tjsj", DateTimeUtil.getDateTimeStr());
		pd.put("xgsj", DateTimeUtil.getDateTimeStr());
		pd.put("tjr", ((User)session.getAttribute(Const.SESSION_USER)).getUSER_ID());
		this.cgxService.save(pd);
		mv.addObject("pd", pd);
		mv.setViewName("web/cgx/cgx_add");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: toedit
	  * @Description: 跳转到编辑页面
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/toedit")
	public ModelAndView toedit(HttpSession session,Page page) throws Exception{
		mv.clear();
		pd = this.getPageData();
		
		pd = this.cgxService.findInfo(pd);
		
		mv.addObject("pd", pd);
		mv.setViewName("web/cgx/cgx_edit");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: update
	  * @Description: 更新信息
	  * @param @param session
	  * @param @param page
	  * @param @param request
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/update")
	public ModelAndView update(HttpSession session,Page page,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request); 
		pd.put("xgsj", DateTimeUtil.getDateTimeStr());
		pd.put("tjr", ((User)session.getAttribute(Const.SESSION_USER)).getUSER_ID());
		this.cgxService.update(pd);
		mv.addObject("pd", pd);
		mv.setViewName("web/cgx/cgx_edit");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: remove
	  * @Description: 删除信息
	  * @param @param out    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/remove")
	public void remove(PrintWriter out){
		mv.clear();
		try{
			pd = this.getPageData();
			cgxService.remove(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}

	
	/**
	 * @throws IOException 
	 * 
	  * @Title: scdcb
	  * @Description: 生成调查表
	  * @param @param out    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/scdcb")
	public void scdcb(HttpServletRequest request,HttpServletResponse response) throws IOException{
		pd = new PageData(request);
		Json json = new Json();
		try{
			cgxService.scdcb(pd);
			
			json.setMsg("调查表生成成功。");
			json.setSuccess(true);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		this.writeJson(response, json);
	}
	
	
	@RequestMapping(value="/findYhxx")
	public ModelAndView findYhxx(HttpServletRequest request,HttpServletResponse response) throws IOException{
		pd = new PageData(request);
		try{
			List<PageData> list = cgxService.findYhxx(pd);
			JSONArray arr = JSONArray.fromObject(list);
			String json = arr.toString();
			mv.addObject("zTreeNodes", json);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("web/cgx/cgx_yhxx");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: saveBdxx
	  * @Description: 保存表单信息
	  * @param @param request
	  * @param @param response
	  * @param @throws IOException    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/saveBdxx")
	public void saveBdxx(HttpServletRequest request,HttpServletResponse response) throws IOException{
		pd = new PageData(request);
		Json json = new Json();
		try{
			cgxService.saveBdxx(pd);
			
			json.setMsg("表单生成成功。");
			json.setSuccess(true);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		this.writeJson(response, json);
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
