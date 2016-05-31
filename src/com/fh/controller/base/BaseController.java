package com.fh.controller.base;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fh.entity.Page;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

public class BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;
	
	protected ModelAndView mv = this.getModelAndView();
	
	protected PageData pd = new PageData();
	

	
	/**
	 * 切换菜单
	 */
	public void setPdmenu(PageData pd){
		mv.addObject("pdm",pd);
	}
	
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		
		return new ModelAndView();
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		return request;
	}

	/**
	 * 得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		
		return UuidUtil.get32UUID();
	}
	
	/**
	 * 得到分页列表的信息 
	 */
	public Page getPage(){
		
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	
	
	public void writeJson(HttpServletResponse response,Object object) throws IOException{
		
		response.setContentType("text/html;charset=utf-8");
		ObjectMapper objMapper = new ObjectMapper();
		JsonGenerator jsonGenerator = objMapper.getJsonFactory()
				.createJsonGenerator(response.getOutputStream(),
						JsonEncoding.UTF8);
		jsonGenerator.writeObject(object);
		jsonGenerator.flush();
		jsonGenerator.close();
	
//	String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
//	response.setContentType("text/html;charset=utf-8");
//	response.getWriter().write(json);
//	response.getWriter().flush();
//	response.getWriter().close();
}

	
}
