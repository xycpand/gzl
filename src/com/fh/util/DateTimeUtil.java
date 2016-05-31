/**    
 * 文件名：DateTimeUtil.java    
 *    
 * 版本信息：    
 * 日期：2014-2-17    
 * Copyright 足下 Corporation 2014     
 * 版权所有    
 *    
 */
package com.fh.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/** 
 * 
* @ClassName: DateTimeUtil
* @Description: 
* @author anxingtao
* @date 2014-8-27 下午6:09:42
*
 */
public class DateTimeUtil {
	static String[] weeks = { "日", "一", "二", "三", "四", "五", "六" };
	
	public static String getDateTimeStr()
	  {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currTime = new Date();
		String curTime = formatter.format(currTime);
	    return curTime;
	  }
	
	public static Date getDateTime() throws ParseException
	  {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currTime = new Date();
		Date curTime = formatter.parse(formatter.format(currTime));
	    return curTime;
	  }
	
	public static Date getDateTimeGsh() throws ParseException
	  {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date currTime = new Date();
		Date curTime = formatter.parse(formatter.format(currTime));
	    return curTime;
	  }
	
	
	
	public static String getDate()
	  {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date currTime = new Date();
		String curTime = formatter.format(currTime);
	    return curTime;
	  }
	
	 public static int getWeekNums(Date date) {  
		  GregorianCalendar g = new GregorianCalendar();  
		  g.setTime(date);  
		  return g.get(Calendar.WEEK_OF_YEAR);//获得周数  
		 }  
	 
	 public static String getWeeks(){
		 Calendar c = Calendar.getInstance();
//		  int week = c.get(Calendar.WEEK_OF_MONTH);//获取是本月的第几周
		  int day = c.get(Calendar.DAY_OF_WEEK);//获致是本周的第几天地, 1代表星期天...7代表星期六
//		  System.out.println("今天是本月的第" + week + "周");
//		  System.out.println("今天是星期" + weeks[day-1]);
		  String str = weeks[day-1];
		return str;
	 }
	 
	 public static String getWeekFirstDate(String date) {  
		 Calendar c=Calendar.getInstance();
		 c.set(Integer.parseInt(date.split("-")[0]),
	     Integer.parseInt(date.split("-")[1]) - 1,
	     Integer.parseInt(date.split("-")[2]));
	     DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	     c.add(Calendar.DAY_OF_WEEK,(-1)*c.get(Calendar.DAY_OF_WEEK)+1);
		return df.format(c.getTime());
	 }  
	 
	 
	 public static String getWeekEndDate(String date) {  
		 Calendar c=Calendar.getInstance();
		 c.set(Integer.parseInt(date.split("-")[0]),
	     Integer.parseInt(date.split("-")[1]) - 1,
	     Integer.parseInt(date.split("-")[2]));
	     DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	     c.add(Calendar.DAY_OF_WEEK,7-c.get(Calendar.DAY_OF_WEEK));
		return df.format(c.getTime());
	 }  
	 
	 public static void main(String[] args) {
	        Calendar c=Calendar.getInstance();
//	        c.add(Calendar.DAY_OF_MONTH,-6);
	        String date = getDate();
	  c.set(Integer.parseInt(date.split("-")[0]),
	    Integer.parseInt(date.split("-")[1]) - 1,
	    Integer.parseInt(date.split("-")[2]));
	        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
//	        c.set(Calendar.DAY_OF_WEEK,0);
	        c.add(Calendar.DAY_OF_WEEK,(-1)*c.get(Calendar.DAY_OF_WEEK)+1);
	        System.out.println("本周第一天："+df.format(c.getTime()));
	         c.add(Calendar.DAY_OF_WEEK,7-c.get(Calendar.DAY_OF_WEEK));
	        System.out.println("本周最后一天："+df.format(c.getTime()));
	    } 
	 
	 /**
	  * 
	 * @Title: compareDatesByCompareTo
	 * @Description: 时间日期的比较
	 * @param @param df
	 * @param @param oldDate
	 * @param @param newDate
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	  */
	 public static String compareDatesByCompareTo(DateFormat df, Date oldDate, Date newDate) {
		 String flag = "";
	        //how to check if date1 is equal to date2
	        if (oldDate.compareTo(newDate) == 0) {
//	            System.out.println(df.format(oldDate) + " and " + df.format(newDate) + " are equal to each other");
	        	flag = "dengyu";
	        }
	 
	        //checking if date1 is less than date 2
	        if (oldDate.compareTo(newDate) < 0) {
//	            System.out.println(df.format(oldDate) + " is less than " + df.format(newDate));
	        	flag = "xiaoyu";
	        }
	 
	        //how to check if date1 is greater than date2 in java
	        if (oldDate.compareTo(newDate) > 0) {
//	            System.out.println(df.format(oldDate) + " is greater than " + df.format(newDate));
	        	flag = "dayu";
	        }
			return flag;
	    }
	 
	    public static String compareDatesByDateMethods(DateFormat df, Date oldDate, Date newDate) {
	    	 String flag = "";
	        //how to check if two dates are equals in java
	        if (oldDate.equals(newDate)) {
	            System.out.println(df.format(oldDate) + " and " + df.format(newDate) + " are equal to each other");
	            flag = "dengyu";
	        }
	 
	        //checking if date1 comes before date2
	        if (oldDate.before(newDate)) {
	            System.out.println(df.format(oldDate) + " comes before " + df.format(newDate));
	            flag = "xiaoyu";
	        }
	 
	        //checking if date1 comes after date2
	        if (oldDate.after(newDate)) {
	            System.out.println(df.format(oldDate) + " comes after " + df.format(newDate));
	        	flag = "dayu";
	        }
			return flag;
	    }
	 
	    public static String compareDatesByCalendarMethods(DateFormat df, Date oldDate, Date newDate) {
	    	 String flag = "";
	        //creating calendar instances for date comparision
	        Calendar oldCal = Calendar.getInstance();
	        Calendar newCal = Calendar.getInstance();
	 
	        oldCal.setTime(oldDate);
	        newCal.setTime(newDate);
	 
	        //how to check if two dates are equals in java using Calendar
	        if (oldCal.equals(newCal)) {
//	            System.out.println(df.format(oldDate) + " and " + df.format(newDate) + " are equal to each other");
	        	flag = "dengyu";
	        }
	 
	        //how to check if one date comes before another using Calendar
	        if (oldCal.before(newCal)) {
//	            System.out.println(df.format(oldDate) + " comes before " + df.format(newDate));
	        	flag = "xiaoyu";
	        }
	 
	        //how to check if one date comes after another using Calendar
	        if (oldCal.after(newCal)) {
//	            System.out.println(df.format(oldDate) + " comes after " + df.format(newDate));
	        	flag = "dayu";
	        }
			return flag;
	    }
	    
	    /**
	     * 
	    * @Title: getNowBeforeTime
	    * @Description: 计算多少分钟之前的时间
	    * @param @param 
	    * @param @return    设定文件
	    * @return String    返回类型
	    * @throws
	     */
	    public static String getNowBeforeTime(int minminute){
	    	Date now = new Date();
	    	Date now_10 = new Date(now.getTime() - minminute*60*1000); //10分钟前的时间
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
	    	String nowTime_10 = dateFormat.format(now_10);
			return nowTime_10;
	    }
	    
	    
	    public static String getBeforeDaty(int i){
	    	GregorianCalendar gc=new GregorianCalendar();
			gc.add(Calendar.DAY_OF_MONTH,-i);
			int year=gc.get(Calendar.YEAR);
			int month=gc.get(Calendar.MONTH)+1;
			int day=gc.get(Calendar.DAY_OF_MONTH);
			System.out.println("前150天是："+year+"-"+month+"-"+day);
			
			String m = "";
			String d = "";
			if(month<10){
				m="0"+month;
			}else{
				m=""+month;
			}
			if(day<10){
				d="0"+day;
			}else{
				d=""+day;
			}
			return year+"-"+m+"-"+d;
	    }
	    
	    public static void main1(String[] args) throws ParseException {
//	    	System.out.println(getNowBeforeTime(120));
//	    	
//			GregorianCalendar gc=new GregorianCalendar();
//			gc.add(Calendar.DAY_OF_MONTH,-10);
//			int year=gc.get(Calendar.YEAR);
//			int month=gc.get(Calendar.MONTH)+1;
//			int day=gc.get(Calendar.DAY_OF_MONTH);
//			System.out.println("前150天是："+year+"-"+month+"-"+day);
//			
//			System.out.println(DateTimeUtil.getDateTimeStr().substring(0,10));
////			
//			
//			System.out.println(getDateTimeGsh());
	    	
	    	
	    	System.out.println(getdatetimetounix(DateTimeUtil.getDateTimeStr()));
	    	
			System.out.println(DateTimeUtil.TimeStampDate("1415242236"));
			
			System.out.println(new Date().getTime());
		}
	    
	    public static String getdatetimetounix(String datetime){
	    	Timestamp appointTime=Timestamp.valueOf(datetime) ; 
	    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
	    	Date date = null;
			try {
				date = df.parse(String.valueOf(appointTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}  
	    	long s=date.getTime();
	    	System.out.println(s);
			return String.valueOf(s).substring(0, 10); 
	    }
	    
	    public static String TimeStampDate(String timestampString){    
	    	  Long timestamp = Long.parseLong(timestampString)*1000;    
	    	  String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));    
	    	  return date;    
	    	} 
	    
	    
	    
	    
}
