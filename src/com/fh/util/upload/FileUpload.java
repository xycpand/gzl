package com.fh.util.upload;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fh.util.FileUtil;
import com.fh.util.GuidUtil;
import com.fh.util.PageData;
import com.fh.util.Verify;


public class FileUpload {

	
	 /**
     * 本地保存文件
     */
    public static PageData saveLocalFile(HttpServletRequest request,String filePath,PageData pd) throws IOException {
    	CommonsMultipartResolver  multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
    	if(multipartResolver.isMultipart(request)){
             MultipartHttpServletRequest  multipartRequest = (MultipartHttpServletRequest)request;
             Iterator<String> iterator = multipartRequest.getFileNames();
             while(iterator.hasNext()){
                MultipartFile  file  = multipartRequest.getFile(iterator.next());
                if(!file.isEmpty()){
	            	//上传到阿里云OSS服务器
	            	String fileName = file.getOriginalFilename();
	            	String hz = fileName.substring(fileName.lastIndexOf("."));
	            	String key = filePath+GuidUtil.getGuid()+hz;
	            	
	            	String path=ParaUtil.localName+key;
	            	File f1 = new File(path);
	            	if(!f1.exists()){
	            		f1.mkdirs();
	            	}
	            	file.transferTo(f1);
			        pd.put(file.getName(),key);
	            }
             }
          }
		return pd;
    }
    
    
    /**
     * 本地更新
     * @param request
     * @param filePath
     * @param pd
     * @return
     * @throws IOException
     */
    public static PageData uploadLocalFile(HttpServletRequest request,String filePath,PageData pd) throws IOException {
    	CommonsMultipartResolver  multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
    	if(multipartResolver.isMultipart(request)){
             MultipartHttpServletRequest  multipartRequest = (MultipartHttpServletRequest)request;
             Iterator<String> iterator = multipartRequest.getFileNames();
             while(iterator.hasNext()){
                MultipartFile  file  = multipartRequest.getFile(iterator.next());
                if(!file.isEmpty()){
	            	//上传到本地服务器
                	String delPath=pd.getString(file.getName());
                	deleteLocalFile(delPath);
                	
	            	String fileName = file.getOriginalFilename();
	            	String hz = fileName.substring(fileName.lastIndexOf("."));
	            	String key = filePath+GuidUtil.getGuid()+hz;
	            	
	            	String path=ParaUtil.localName+key;
	            	File f1 = new File(path);
	            	if(!f1.exists()){
	            		f1.mkdirs();
	            	}
	            	file.transferTo(f1);
			        pd.put(file.getName(),key);
	            }
             }
          }
		return pd;
    }
    
    
    /**
     * 本地删除单一文件
     * @param filePath
     * @throws IOException
     */
    public static void deleteLocalFile(String key) throws IOException {
    	if(Verify.verifyIsNotNull(key)){
    		if(!key.contains(ParaUtil.zdyPath)){
	    		String path=ParaUtil.localName+key;
		    	File f1 = new File(path);
		    	if(f1.exists()){
		    		f1.delete();
		    		f1.deleteOnExit();
		    	}
	    	}
    	}
    }
    
    
    /**
     * 本地删除集合文件
     * @param filePath
     * @throws IOException
     */
    public static void deleteLocalFile(String key[]) throws IOException {
    	for (String string : key) {
    		if(Verify.verifyIsNotNull(string)){
    			if(!string.contains(ParaUtil.zdyPath)){
	    			String path=ParaUtil.localName+string;
		        	File f1 = new File(path);
		        	if(f1.exists()){
		        		f1.delete();
		        		f1.deleteOnExit();
		        	}
	    		}
    		}
		}
    }
    
    
    
    /**
     * 获取本地文件路径
     * @param filePath
     * @return
     * @throws IOException
     * @throws ParseException 
     */
    public static String findLocalFileUrl(String key) throws IOException {
    	String path="";
    	if (Verify.verifyIsNotNull(key)) {
			path=ParaUtil.cloudfile+key;
		}
		return path;
    }
    
    
    /**
     * 本地文件下载
     * @param filePath
     * @throws Exception 
     */
    public static void downloadLocalFile(HttpServletResponse response,String key,String filename) throws Exception {
    	String path="";
    	if(Verify.verifyIsNotNull(key)){
    		path=ParaUtil.localName+key;
    		FileUtil.downFile(response, path, filename);
    	}
    }
    
}
