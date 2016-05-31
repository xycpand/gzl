package com.fh.util;

import com.fh.controller.system.menu.MenuService;
import com.fh.controller.system.role.RoleService;
import com.fh.controller.system.user.UserService;
import com.fh.controller.web.rzgl.RzglService;



/**
 * @author Administrator
 * 获取Spring容器中的service bean
 */
public final class ServiceHelper {
	
	public static Object getService(String serviceName){
		//WebApplicationContextUtils.
		return Const.WEB_APP_CONTEXT.getBean(serviceName);
	}
	
	public static UserService getUserService(){
		return (UserService) getService("userService");
	}
	
	public static RoleService getRoleService(){
		return (RoleService) getService("roleService");
	}
	
	public static MenuService getMenuService(){
		return (MenuService) getService("menuService");
	}
	
	public static RzglService getRzglService(){
		return (RzglService) getService("rzglService");
	}
}
