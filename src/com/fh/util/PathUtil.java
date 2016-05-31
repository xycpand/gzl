package com.fh.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 路径工具类
 * @author qss
 * 
 */
public class PathUtil {
	
	/**
	 * 图片访问路径
	 * @param pathType      图片类型     visit-访问；save-保存
	 * @param pathCategory  图片类别，如：话题图片-topic、话题回复图片-reply、商家图片
	 * @return
	 */
	public static String getPicturePath(String pathType, String pathCategory){
		String strResult = "";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		StringBuffer strBuf = new StringBuffer();
		
		if("visit".equals(pathType)){
			// http://192.168.1.156:80/
			/*
			strBuf.append(request.getScheme() + "://"	);
			strBuf.append(request.getServerName() + ":"	);
			strBuf.append(request.getServerPort() + "/"	);
			*/
		} else if("save".equals(pathType)){
			String projectPath = PublicUtil.getPorjectPath().replaceAll("\\\\", "/");
			projectPath = splitString(projectPath, "bin/");
			
			strBuf.append(projectPath);
			strBuf.append("webapps/ROOT/");
		}
		
	//	strBuf.append("lookingplas/");
	//	strBuf.append(pathCategory+"/");
		
		strResult = strBuf.toString();
		
		return strResult;
	}
	
	private static String splitString(String str, String param){
		String result = str;
		
		if(str.contains(param)){
			int start = str.indexOf(param);
			result = str.substring(0,start);
		}
		
		return result;
	}
	
	
	public static String PathAddress(){
		String strResult = "";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		StringBuffer strBuf = new StringBuffer();
		
		strBuf.append(request.getScheme() + "://"	);
		strBuf.append(request.getServerName() + ":"	);
		strBuf.append(request.getServerPort() + "/"	);
		
		strResult = strBuf.toString();//+"o/";//加入项目的名称
		
		return strResult;
	}
	
}
