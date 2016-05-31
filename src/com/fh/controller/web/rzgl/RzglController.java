package com.fh.controller.web.rzgl;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.controller.system.menu.MenuService;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;

@Controller
@RequestMapping(value="/rzgl")
public class RzglController extends BaseController {
	
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name="rzglService")
	private RzglService rzglService;
	//===================================================================================================
	
	/**
	 * 查询日志信息列表
	 */
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpSession session,Page page) throws Exception{
		mv.clear();
		getHome(session, page);
		pd = this.getPageData();
		this.setPdmenu(pd);
		page.setPd(pd);
		List<PageData> list = rzglService.findList(page);
		
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("web/rzgl/rzxx_list");
		return mv;
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
