package com.fh.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**   
 * 项目名称：idoon   
 * 类名称：UploadFile.java   
 * 类描述：   用于图片等文件的上传
 * 创建人：  huangshuai
 * 创建时间：2015-6-10 上午11:56:49   
 * @version       
 */

public class UploadFile {

//	/**
//	 * 图片上传
//	 * @param session
//	 * @param response
//	 * @param request
//	 * @param filePath ：返回的保存在数据库中的路径
//	 * @param savePath : 作为参数，传递过来保存在服务器的图片路径
//	 * @return
//	 * @throws IllegalStateException
//	 * @throws IOException
//	 */
//	public static String uploadFile(HttpServletRequest request,String savePath,UploadifyUtil up) throws IllegalStateException, IOException{
//		String targetFileName = "";
//		CommonsMultipartResolver  multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//		if(multipartResolver.isMultipart(request)){
//	         MultipartHttpServletRequest  multipartRequest = (MultipartHttpServletRequest)request;
//	         Iterator<String> iterator = multipartRequest.getFileNames();
//	         while(iterator.hasNext()){
//	            MultipartFile file  = multipartRequest.getFile(iterator.next());
//	            if(!file.isEmpty()){
//	            	targetFileName = up.generateFileName(file.getOriginalFilename());
//	            	
//	            	File f1 = new File(savePath + "/" + targetFileName);
//	            	if(!f1.exists()){
//	            		f1.mkdirs();
//	            	}
//			        file.transferTo(f1);
//	            }
//	         }
//	      }
//		return targetFileName;
//	}
	
	/**
	 * 删除图片
	 * @param request
	 * @param imagePath : 图片所在数据库存储的路径
	 * @param savePath : 服务器上的图片路径
	 */
	public static void deleteImage(String savePath){
		File file = new File(savePath);
		if(file.exists()){
			file.delete();
		}
	}
	
	/**
	 * 批量上传，返回List
	 * @param request
	 * @param savePath
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static List<PageData> uploadFile(HttpSession session,HttpServletRequest request) throws IllegalStateException, IOException{
		//savePath=up.system + up.user
		List<PageData> ls = new ArrayList<PageData>();
		CommonsMultipartResolver  multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
	         MultipartHttpServletRequest  multipartRequest = (MultipartHttpServletRequest)request;
	         Iterator<String> iterator = multipartRequest.getFileNames();
	         while(iterator.hasNext()){
	            MultipartFile file  = multipartRequest.getFile(iterator.next());
	            if(!file.isEmpty()){
	            	String path = session.getServletContext().getRealPath("")+"/";
//	            	String filePath = session.getServletContext().getRealPath("")+"/" + "upload/imgmanage/" + GuidUtil.getGuid() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
	            	String filePath = "";
	            	File f1 = new File(path);
	            	if(!f1.exists()){
	            		f1.mkdirs();
	            	}
	            	if(!file.isEmpty()){
	            		filePath = "upload/imgmanage/" + GuidUtil.getGuid() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
	            		file.transferTo(new File(path+filePath));
	            	}
			        PageData p = new PageData();
			        p.put("name", file.getName());
			        p.put("value", filePath);
			        ls.add(p);
	            }
	         }
	      }
		return ls;
	}
	
}
