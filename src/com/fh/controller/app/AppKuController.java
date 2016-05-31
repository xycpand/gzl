/*package com.fh.controller.app; 

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jalan.controller.base.BaseController;
import com.jalan.controller.system.ku.KuService;
import com.jalan.util.AppUtil;
import com.jalan.util.DateUtil;
import com.jalan.util.PageData;
import com.jalan.util.PathUtil;
import com.jalan.util.StringUtil;

*//**
  * app-产品详情-接口类  //手机调用接口事例
  *    
  *     
  * 相关参数协议：
  * 00	操作失败
  * 01	操作成功
  * 02	用户名不存在
  * 03  用户名存在    
  * 04	密码错误
  * 05	请求协议参数不完整    
  * 06  用户名或密码错误
 *//*
@Controller
@RequestMapping(value="/appKu")
public class AppKuController extends BaseController {
    
	@Resource(name="appProductService")
	private AppKuService appProductService;
	
	@Resource(name="kuService")
	private KuService kuService;
	
	
	
	*//**
	 * 申请接口
	 *//*
	@RequestMapping(value="/shenqing")
	@ResponseBody
	public Object shenqing(){
		String result = "01";
		logBefore(logger, "申请加入");
		Map map = new HashMap();
		pd = this.getPageData();
		
		try {
			if (AppUtil.checkParam("shenqing", pd)) {
				
				pd.put("TYPE", "2");				//类型
				pd.put("KU_ID", this.get32UUID());	//ID
				pd.put("START_TIME", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString());
				pd.put("END_TIME","");
				pd.put("USERNAME","");
				pd.put("QZ",0);
				pd.put("ZT","1");
				kuService.save(pd);
				
				result = "01";
			}else {
				result = "05";
			}
		} catch (Exception e){
			logger.error(e.toString(), e);
		}finally{
			map.put("result", result);
			logAfter(logger);
		}
		
		return AppUtil.returnObject(pd, map);
	}
	
	*//**
	 * 检索列表接口
	 *//*
	@RequestMapping(value="/getList")
	@ResponseBody
	public Object getIntention(){
		String result = "00";
		logBefore(logger, "获取检索列表");
		Map map = new HashMap();
		pd = this.getPageData();
		
		try {
			if (AppUtil.checkParam("FJKEYWORDS", pd)) {
				
				String KEYWORDS = pd.getString("KEYWORDS");
				if(null != KEYWORDS){
					KEYWORDS = KEYWORDS.replaceAll(" ", "").replaceAll("-", "").replaceAll(",", "");
					pd.put("KEYWORDS", KEYWORDS);
				}
				
				int LIMIT = Integer.parseInt(pd.get("LIMIT").toString());
				int oLIMIT = LIMIT + 15;
				pd.put("LIMIT1", LIMIT);
				pd.put("LIMIT2", oLIMIT);
				
				List<PageData> kuList = new ArrayList<PageData>();
				List<PageData> kuListy = kuService.applistkufj(pd);
				
				for(int i=0; i<kuListy.size(); i++){
					
					pd = kuListy.get(i);
					int SQCOUNT = Integer.parseInt(kuService.getSq(pd).get("SQCOUNT").toString());
					pd.put("SQCOUNT", SQCOUNT);
					
					kuList.add(pd);
					
				}
				
				map.put("kuList", kuList);
				map.put("LIMIT", oLIMIT);
				
				result = "01";
				
			}else if(AppUtil.checkParam("KEYWORDS", pd)){//附近搜索
				
				String KEYWORDS = pd.getString("KEYWORDS");
				if(null != KEYWORDS){
					KEYWORDS = KEYWORDS.replaceAll(" ", "").replaceAll("-", "").replaceAll(",", "");
					pd.put("KEYWORDS", KEYWORDS);
				}
				
				int LIMIT = Integer.parseInt(pd.get("LIMIT").toString());
				int oLIMIT = LIMIT + 15;
				pd.put("LIMIT1", LIMIT);
				pd.put("LIMIT2", oLIMIT);
				
				List<PageData> kuList = new ArrayList<PageData>();
				List<PageData> kuListy = kuService.applistku(pd);
				
				for(int i=0; i<kuListy.size(); i++){
					
					pd = kuListy.get(i);
					int SQCOUNT = Integer.parseInt(kuService.getSq(pd).get("SQCOUNT").toString());
					pd.put("SQCOUNT", SQCOUNT);	//商圈数
					
					kuList.add(pd);
					
				}
				
				map.put("kuList", kuList);
				map.put("LIMIT", oLIMIT);
				
				result = "01";
				
			}else {
				result = "05";
			}
		} catch (Exception e){
			logger.error(e.toString(), e);
		}finally{
			map.put("result", result);
			logAfter(logger);
		}
		
		return AppUtil.returnObject(pd, map);
	}
	
	
	
	
	*//**
	 * 检索列表接口(商圈)
	 *//*
	@RequestMapping(value="/getListsq")
	@ResponseBody
	public Object getIntentionsq(){
		String result = "00";
		logBefore(logger, "获取检索列表");
		Map map = new HashMap();
		pd = this.getPageData();
		
		try {
			if (AppUtil.checkParam("FJKEYWORDS", pd)) {
				
				String KEYWORDS = pd.getString("KEYWORDS");
				if(null != KEYWORDS){
					KEYWORDS = KEYWORDS.replaceAll(" ", "").replaceAll("-", "").replaceAll(",", "");
					pd.put("KEYWORDS", KEYWORDS);
				}
				
				int LIMIT = Integer.parseInt(pd.get("LIMIT").toString());
				int oLIMIT = LIMIT + 15;
				pd.put("LIMIT1", LIMIT);
				pd.put("LIMIT2", oLIMIT);
				
				List<PageData> kuList = new ArrayList<PageData>();
				List<PageData> kuListy = kuService.applistkusq(pd);
				
				for(int i=0; i<kuListy.size(); i++){
					
					pd = kuListy.get(i);
					int SQCOUNT = Integer.parseInt(kuService.getSq(pd).get("SQCOUNT").toString());
					pd.put("SQCOUNT", SQCOUNT);
					
					kuList.add(pd);
					
				}
				
				map.put("kuList", kuList);
				map.put("LIMIT", oLIMIT);
				
				result = "01";
				
			}else {
				result = "05";
			}
		} catch (Exception e){
			logger.error(e.toString(), e);
		}finally{
			map.put("result", result);
			logAfter(logger);
		}
		
		return AppUtil.returnObject(pd, map);
	}
	
	
	
	*//**
	 * 同品牌检索列表接口
	 *//*
	@RequestMapping(value="/ppList")
	@ResponseBody
	public Object ppList(){
		String result = "00";
		logBefore(logger, "同品牌获取检索列表");
		Map map = new HashMap();
		pd = this.getPageData();
		
		try {
			if (AppUtil.checkParam("TPP", pd)) {
				
				String PINPAI = pd.getString("PINPAI");
				if(null != PINPAI){
					//PINPAI = PINPAI.replaceAll(" ", "").replaceAll("-", "").replaceAll(",", "");
					pd.put("PINPAI", PINPAI);
				}
				
				int LIMIT = Integer.parseInt(pd.get("LIMIT").toString());
				int oLIMIT = LIMIT + 15;
				pd.put("LIMIT1", LIMIT);
				pd.put("LIMIT2", oLIMIT);
				
				List<PageData> kuList = new ArrayList<PageData>();
				List<PageData> kuListy = kuService.applistpinpai(pd);
				
				for(int i=0; i<kuListy.size(); i++){
					
					pd = kuListy.get(i);
					int SQCOUNT = Integer.parseInt(kuService.getSq(pd).get("SQCOUNT").toString());
					pd.put("SQCOUNT", SQCOUNT);
					
					kuList.add(pd);
					
				}
				
				map.put("kuList", kuList);
				map.put("LIMIT", oLIMIT);
				
				result = "01";
			}else if(AppUtil.checkParam("TPPW", pd)){
				String PINPAI = pd.getString("PINPAI");
				if(null != PINPAI){
					//PINPAI = PINPAI.replaceAll(" ", "").replaceAll("-", "").replaceAll(",", "");
					pd.put("PINPAI", PINPAI);
				}
				
				int LIMIT = Integer.parseInt(pd.get("LIMIT").toString());
				int oLIMIT = LIMIT + 15;
				pd.put("LIMIT1", LIMIT);
				pd.put("LIMIT2", oLIMIT);
				
				List<PageData> kuList = new ArrayList<PageData>();
				List<PageData> kuListy = kuService.applistpinpaiwww(pd);
				
				for(int i=0; i<kuListy.size(); i++){
					
					pd = kuListy.get(i);
					int SQCOUNT = Integer.parseInt(kuService.getSq(pd).get("SQCOUNT").toString());
					pd.put("SQCOUNT", SQCOUNT);
					
					kuList.add(pd);
					
				}
				
				map.put("kuList", kuList);
				map.put("LIMIT", oLIMIT);
				
			}else {
				result = "05";
			}
		} catch (Exception e){
			logger.error(e.toString(), e);
		}finally{
			map.put("result", result);
			logAfter(logger);
		}
		
		return AppUtil.returnObject(pd, map);
	}
	
	
}
	
 */