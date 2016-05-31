package com.fh.controller.web.dcbgl;

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
import com.fh.controller.web.dcbgl.upload.FileUpload;
import com.fh.controller.web.dcbgl.upload.ParaUtil;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.FileUtil;
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
@RequestMapping(value="/dcbgl")
public class DcbglController extends BaseController {
	
	@Resource(name="dcbglService")
	private DcbglService dcbglService;
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
		List<PageData> list = dcbglService.findList(page);
		
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("web/dcbgl/dcbgl_list");
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
		
		PageData p = (PageData) dcbglService.findInfo(pd);
		
		mv.addObject("pd", pd);
		mv.addObject("p", p);
		mv.setViewName("web/dcbgl/dcbgl_info");
		return mv;
	}

	
	/**
	 * 
	  * @Title: findZdydcbList
	  * @Description: 查询自定义调查表统计信息
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findZdydcbList")
	public ModelAndView findZdydcbList(HttpSession session,Page page) throws Exception{
		mv.clear();
		pd = this.getPageData();
		
		PageData p = (PageData) dcbglService.findInfo(pd);
		
		pd.put("table", p.get("zdybm"));
		page.setPd(pd);
		List<PageData> list = dcbglService.findZdydcbList(page);
		
		mv.addObject("p", p);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("web/dcbgl/zdydcb_list");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: findMyDcbxxlistPage
	  * @Description: 查询我的调查表
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findMyDcbxxlistPage")
	public ModelAndView findMyDcbxxlistPage(HttpSession session,Page page) throws Exception{
		mv.clear();
		getHome(session, page);
		pd = this.getPageData();
		this.setPdmenu(pd);
		pd.put("yhid", ((User)session.getAttribute(Const.SESSION_USER)).getUSER_ID());
		page.setPd(pd);
		List<PageData> list = dcbglService.findMyDcbxxlistPage(page);
		for (PageData p : list) {
			pd.put("table", p.get("zdybm"));
			PageData dcbPd = dcbglService.findMyDcbInfo(pd);
			p.put("dcbPd", dcbPd);
		}
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("web/dcbgl/mydcb_list");
		return mv;
	}
	
	
	
	
	@RequestMapping(value="/toAddMyDcbxx")
	public ModelAndView toAddMyDcbxx(HttpSession session,Page page) throws Exception{
		mv.clear();
		pd = this.getPageData();
		
		PageData p = this.dcbglService.findInfo(pd);
		
		mv.addObject("p", p);
		mv.addObject("pd", pd);
		mv.setViewName("web/dcbgl/dcbgl_mydcbxx");
		return mv;
	}
	
	
	
	
	@RequestMapping(value="/saveMyDcbxx")
	public ModelAndView saveMyDcbxx(HttpSession session,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request);
		pd.putAll(FileUpload.saveLocalFile(request, ParaUtil.system+ParaUtil.zdydcb, pd));
		dcbglService.saveMyDcbxx(pd);
		
		pd.put("label", "b");
		mv.addObject("pd", pd);
		mv.setViewName("web/dcbgl/dcbgl_mydcbxx");
		return mv;
	}
	
	
		/**
		 * 查询信息列表
		 */
		@RequestMapping(value="/findBdxx")
		public ModelAndView findBdxx(HttpSession session,Page page) throws Exception{
			mv.clear();
			getHome(session, page);
			pd = this.getPageData();
			this.setPdmenu(pd);
			
			PageData pp = dcbglService.findInfo(pd);
			pd.put("yhid", ((User)session.getAttribute(Const.SESSION_USER)).getUSER_ID());
			pd.put("table", pp.get("zdybm"));
			page.setPd(pd);
			List<PageData> list = dcbglService.findBdxx(page);
			
			mv.addObject("pd", pd);
			mv.addObject("pp", pp);
			mv.addObject("list", list);
			mv.setViewName("web/dcbgl/bdxx_list");
			return mv;
		}
		
		
		/**
		 * 
		  * @Title: toaddBdxx
		  * @Description: 跳转到添加表单信息
		  * @param @param session
		  * @param @param page
		  * @param @return
		  * @param @throws Exception    设定文件
		  * @return ModelAndView    返回类型
		  * @throws
		 */
		@RequestMapping(value="/toaddBdxx")
		public ModelAndView toaddBdxx(HttpSession session,Page page) throws Exception{
			mv.clear();
			pd = this.getPageData();
			
			PageData p = this.dcbglService.findInfo(pd);
			
			mv.addObject("p", p);
			mv.addObject("pd", pd);
			mv.setViewName("web/dcbgl/bdxx_add");
			return mv;
		}
		
		
		@RequestMapping(value="/saveBdxx")
		public ModelAndView saveBdxx(HttpSession session,HttpServletRequest request) throws Exception{
			mv.clear();
			pd = new PageData(request);
			pd.put("yhid", ((User)session.getAttribute(Const.SESSION_USER)).getUSER_ID());
			this.dcbglService.saveBdxx(pd);
			
			pd.put("label", "b");
			mv.addObject("pd", pd);
			mv.setViewName("web/dcbgl/bdxx_add");
			return mv;
		}
		
		
		@RequestMapping(value="/removeBdxx")
		public void removeBdxx(PrintWriter out){
			mv.clear();
			try{
				pd = this.getPageData();
				dcbglService.removeBdxx(pd);
				out.write("success");
				out.close();
			} catch(Exception e){
				logger.error(e.toString(), e);
			}
		}
		
		
		
		@RequestMapping(value="/toeditBdxx")
		public ModelAndView toeditBdxx(HttpSession session,Page page) throws Exception{
			mv.clear();
			pd = this.getPageData();
			
			PageData p = this.dcbglService.findInfo(pd);
			pd.put("table", p.get("zdybm"));
			PageData bdPd = dcbglService.findBdxxInfo(pd);
			mv.addObject("p", p);
			mv.addObject("pd", pd);
			mv.addObject("bdPd", bdPd);
			mv.setViewName("web/dcbgl/bdxx_edit");
			return mv;
		}
		
		
		@RequestMapping(value="/updateBdxx")
		public ModelAndView updateBdxx(HttpSession session,HttpServletRequest request) throws Exception{
			mv.clear();
			pd = new PageData(request);
			
			this.dcbglService.updateBdxx(pd);
			
			pd.put("label", "b");
			mv.addObject("pd", pd);
			mv.setViewName("web/dcbgl/bdxx_add");
			return mv;
		}
		
		@RequestMapping(value = "/downxz")
		public void download(HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			mv.clear();
			PageData p = new PageData(request);
			Json json = new Json();
			String path=p.getString("url");
			int name  = path.lastIndexOf("/");
			String filename = path.substring(name+1, path.length());
			FileUtil.downFile(response, path, filename);
			//json.setMsg("下载成功。");
			//json.setSuccess(true);
			this.writeJson(response, json);
		}
		
		
		
		
		/**
		 * 
		  * @Title: remove
		  * @Description: 删除调查表
		  * @param @param out    设定文件
		  * @return void    返回类型
		  * @throws
		 */
		@RequestMapping(value="/remove")
		public void remove(PrintWriter out){
			mv.clear();
			try{
				pd = this.getPageData();
				dcbglService.remove(pd);
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
