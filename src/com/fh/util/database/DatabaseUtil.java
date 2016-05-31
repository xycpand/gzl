package com.fh.util.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.fh.util.PageData;


public class DatabaseUtil {
	
	private static String file = "jdbc.properties";
	public static String excludeKeys = "zdybm,html,code,bt,tjsj,xgsj,tjr,id,lx,bz,ids,vals,yhid,dqsj";
	
	 public static void main(String[] args) {   
		 Properties prop = readPts(file);
		 
	        String driver = prop.getProperty("jdbc.driverClassName");   
	        String url = prop.getProperty("jdbc.url");   
	        String user = prop.getProperty("jdbc.username");   
	        String password = prop.getProperty("jdbc.password");      
	        try {   
	            Class.forName(driver);   
	            Connection conn = DriverManager.getConnection(url, user, password);   
	            if (!conn.isClosed()) {   
	                System.out.println("Succeeded connecting to the Database!");   
	                Statement statement = conn.createStatement();   
	                
	                String sql = "select * from yrxx_cpb_builder_form";   
	                ResultSet rs = statement.executeQuery(sql);   
	                String name;   
	                while (rs.next()) {   
	                    name = rs.getString("html");   
	                    System.out.println(name);   
	                }   
	  
	            }   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	  
	        }    
	    }   
	
	
	 public static boolean executeSql(String sql){
		 boolean bol=false;
		 
		 Properties prop = readPts(file);
         String driver = prop.getProperty("jdbc.driverClassName");   
         String url = prop.getProperty("jdbc.url");   
         String user = prop.getProperty("jdbc.username");   
         String password = prop.getProperty("jdbc.password");      
         try {   
            Class.forName(driver);   
            Connection conn = DriverManager.getConnection(url, user, password);   
            if (!conn.isClosed()) {   
                System.out.println("Succeeded connecting to the Database!");   
                Statement statement = conn.createStatement();   
                bol = statement.execute(sql);   
	          }   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }
		return bol; 
	 }
	 
	 
	 
	 public static String findInfoSql(String sql,String key) throws SQLException{
		 String str="";
		 ResultSet rs=null;
		 Properties prop = readPts(file);
         String driver = prop.getProperty("jdbc.driverClassName");   
         String url = prop.getProperty("jdbc.url");   
         String user = prop.getProperty("jdbc.username");   
         String password = prop.getProperty("jdbc.password");      
         try {   
            Class.forName(driver);   
            Connection conn = DriverManager.getConnection(url, user, password);   
            if (!conn.isClosed()) {   
                System.out.println("Succeeded connecting to the Database!");   
                Statement statement = conn.createStatement();   
                rs=statement.executeQuery(sql); 
	          }   
	            while(rs.next()){
	            	str=rs.getString(key);
				}
            
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }finally{
	        	rs.close();
	        }
		return str; 
	 }
	 
	 
	 
	 
	 
	 public static Properties readPts(String file){
			Properties prop = new Properties();  
			 try {
				//读取属性文件a.properties
				prop.load(DatabaseUtil.class.getClassLoader().getResourceAsStream(file));
//				InputStream in = new BufferedInputStream (new FileInputStream(file));
//				prop.load(in);     ///加载属性列表
//				Iterator<String> it=prop.stringPropertyNames().iterator();
//				while(it.hasNext()){
//				    String key=it.next();
//				    System.out.println(key+":"+prop.getProperty(key));
//				}
//				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return prop;
		}
	 
}
