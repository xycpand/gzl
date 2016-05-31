package com.fh.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 获得Id等字符串
 * @author anxingtao
 */
public class GuidUtil {

	private static final Random ran = new Random();
	
	public static String getRandom(int i){
		StringBuffer sb = new StringBuffer();
		sb.append("2");
		for (int j = 0; j < i; j++) {
			sb.append(ran.nextInt(10));
		}
		return sb.toString();
	}
	
	public static String getGuid()
	  {
		StringBuffer now = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
	    int n = (int)(Math.random() * 90000.0D + 10000.0D);
	    return now.append(n)+"";
	  }
	
	public static String getSmailGuid() {
		StringBuffer cc = new StringBuffer();
		cc.append("6");
		String code = "0123456789";
		for(int i=0;i<8;i++) {
			cc.append(code.charAt(ran.nextInt(code.length())));
		}
		return cc.toString();
	}
	
	public static String getCodeNum() {
		StringBuffer cc = new StringBuffer();
		String code = "0123456789";
		for(int i=0;i<6;i++) {
			cc.append(code.charAt(ran.nextInt(code.length())));
		}
		return cc.toString();
	}
	
	public static String getStrGuid(){
		StringBuffer sb = new StringBuffer();
//		StringBuffer now = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
//		sb.append(now);
		String code = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(int i=0;i<8;i++) {
			sb.append(code.charAt(ran.nextInt(code.length())));
		}
		return sb.toString();
	}

	public static String getTableName() {
		String prefix = "yrxx_dcb_";
		StringBuffer now = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
	    int n = (int)(Math.random() * 90000.0D + 10000.0D);
	    String table = now.append(n)+"";
		return prefix+table;
	}
	
}
