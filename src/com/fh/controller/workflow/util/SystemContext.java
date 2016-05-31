package com.fh.controller.workflow.util;

public class SystemContext {
	private static ThreadLocal<String> currentUser = new ThreadLocal<String>();
	private static ThreadLocal<String> beforeUser = new ThreadLocal<String>();
	
	
	public static String getCurrentUser() {
		return currentUser.get();
	}

	public static void setCurrentUser(String _currentUser) {
		currentUser.set(_currentUser);
	}
	
	public static void removeCurrentUser() {
		currentUser.remove();
	}

	
	
	public static String getBeforeUser() {
		return beforeUser.get();
	}

	public static void setBeforeUser(String _beforeUser) {
		beforeUser.set(_beforeUser);
	}
	
	public static void removeBeforeUser() {
		beforeUser.remove();
	}
	
	
}
