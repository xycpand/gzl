/**  
* @Title: CookieUtil.java
* @Package com.cloudsrich.bd.base.util
* @Description: 
* @author huangshuai
* @date 2015-4-30 下午05:46:35
* @version
*/
package com.fh.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;


/**
 * @ClassName: CookieUtil
 * @Description: 用户登录cookie类
 * @author huangshuai
 * @date 2015-6-9 下午09:50:07
 */
@Scope("prototype")
public class CookieUtil{
	
	//保存cookie时的cookieName
	//登录
	private final static String cookieDomainName = "idoon";
	//汽车
	private final static String cookieQcdbName = "cloudsrich_qcdb_cookie";
	//加密cookie时的网站自定码
	private final static String webKey = "jcxt1234567";
	//设置cookie有效期是两个星期，根据需要自定义
	private final static long cookieMaxAge = 60 * 60 * 24 * 7 * 2;
	
	
	/**
	* @Title: saveCookie
	* @Description: 保存Cookie到客户端，在登录action里面调用，
	* 传进来的参数为登陆时填写的用户名与密码
	* @return void
	* @throws
	 */
	public static void saveCookie(PageData pd, HttpServletResponse response){
		//cookie的有效期
		long validTime = System.currentTimeMillis() + (cookieMaxAge * 5000);
		//MD5加密用户详细信息
		try {
			String cookieValueWithMd5 = MD5.md5(pd.getString("dlmm") + ":" + pd.getString("dlmc") + ":" + validTime + ":" + webKey );
			//将要被保存的完整的Cookie值
			String cookieValue = pd.getString("dlmc") + ":" + validTime + ":" + cookieValueWithMd5;
			//开始保存Cookie
			Cookie cookie = new Cookie(cookieDomainName, cookieValue);
			//存两年(这个值应该大于或等于validTime)
			cookie.setMaxAge(60 * 60 * 24 * 365 * 2);
			//cookie有效路径是网站根目录
			cookie.setPath("/");
			//向客户端写入
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String readCookieAndLogon(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException,UnsupportedEncodingException{
		//根据cookieName取cookieValue
		Cookie cookies[] = request.getCookies();
		String cookieValue = null;
		if(cookies != null){
			for(int i = 0; i < cookies.length; i ++){
				if(cookieDomainName.equals(cookies[i].getName())){
					cookieValue = cookies[i].getValue();
					break;
				}
			}
		}
		//如果cookieValue为空,返回,
         if(cookieValue == null){
        	 return "";
         }
         String cookieValues[] = cookieValue.split(":");
         //判断是否在有效期内,过期就删除Cookie
         long validTimeInCookie = new Long(cookieValues[1]);
         String cookieUserName = cookieValues[0];
         if(validTimeInCookie < System.currentTimeMillis()){
              //删除Cookie
              clearCookie(response);
              response.setContentType("text/html;charset=utf-8");
              PrintWriter out = response.getWriter();
              out.println("你的Cookie已经失效,请重新登陆");
              out.close();
              return "";
       }
        return cookieUserName;
	}
	
	/**
	* @Title: clearCookie
	* @Description: 清除cookie
	* @param @param response    设定文件
	* @return void    返回类型
	* @throws
	 */
    public static void clearCookie( HttpServletResponse response){
	  Cookie cookie = new Cookie(cookieDomainName, null);
	  cookie.setMaxAge(0);
	  cookie.setPath("/");
	  response.addCookie(cookie);
    }
    
    
    
    /**
     * 保存汽车对比基本信息cookies
     * @param pd
     * @param response
     */
    public static void saveQcdbCookie(PageData pd, HttpServletResponse response){
		//cookie的有效期
		long validTime = System.currentTimeMillis() + (cookieMaxAge * 5000);
		//MD5加密用户详细信息
		try {
			String cookieValueWithMd5 = MD5.md5(pd.getString("id") + ":" + pd.getString("qcpzxxid") +":" + pd.getString("sph") +":" + pd.getString("bt") + ":" + pd.getString("pinpai") + ":" + validTime + ":" + webKey );
			//将要被保存的完整的Cookie值
			String cookieValue = pd.getString("id") + ":" + pd.getString("qcpzxxid") +":" + pd.getString("sph") +":" + pd.getString("bt") + ":"+ pd.getString("pinpai") + ":" + validTime + ":" + cookieValueWithMd5;
			//开始保存Cookie
			Cookie cookie = new Cookie(cookieQcdbName+pd.getString("id"), URLEncoder.encode(cookieValue,"UTF-8"));
			//存两年(这个值应该大于或等于validTime)
			cookie.setMaxAge(60 * 60 * 24 * 365 * 2);
			//cookie有效路径是网站根目录
			cookie.setPath("/");
			//向客户端写入
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    
    /**
     * 查询汽车对比信息
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     * @throws UnsupportedEncodingException
     */
    public static List<PageData> readCookieForQcdbxx(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException,UnsupportedEncodingException{
		//根据cookieName取cookieValue
		Cookie cookies[] = request.getCookies();
		List<PageData> list = new ArrayList<PageData>();
		if(cookies != null){
			for(int i = 0; i < cookies.length; i ++){
				if(cookies[i].getName().contains(cookieQcdbName)){
					 String cookieValues[] = URLDecoder.decode(cookies[i].getValue(), "UTF-8").split(":");
			         //判断是否在有效期内,过期就删除Cookie
			         long validTimeInCookie = new Long(cookieValues[5]);
			         if(validTimeInCookie < System.currentTimeMillis()){
			              //删除Cookie
			              clearCookie(response);
			       }else{
			    	   PageData p = new PageData();
			    	   p.put("id", cookieValues[0]);
			    	   p.put("qcpzxxid", cookieValues[1]);
			    	   p.put("pph", cookieValues[2]);
			    	   p.put("bt", cookieValues[3]);
			    	   p.put("pinpai", cookieValues[4]);
			    	   list.add(p);
			       }
				}
			}
		}
        return list;
	}
    
    
    /**
     * 清除汽车对比cookies
     * @param response
     */
    public static void clearQcdbCookie( HttpServletResponse response,String[] ids){
    	for (String id : ids) {
			if(Verify.verifyIsNotNull(id)){
				 Cookie cookie = new Cookie(cookieQcdbName+id, null);
			  	  cookie.setMaxAge(0);
			  	  cookie.setPath("/");
			  	  response.addCookie(cookie);
			}
		}
      }

}
