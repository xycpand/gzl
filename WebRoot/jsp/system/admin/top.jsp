<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>自定义表单+Activiti工作流开发</title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		
		<meta http-equiv="X-UA-Compatible" content="IE=8" /> 
		
		<!-- basic styles -->
		<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
		<!--[if IE 7]>
		  <link rel="stylesheet" href="css/font-awesome-ie7.min.css" />
		<![endif]-->
		<!-- page specific plugin styles -->
		
		<!-- 单选框 -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/chosen.css" />
		
		<!-- ace styles -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ace.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ace-skins.min.css" />
		
		<!--[if lt IE 9]>
		  <link rel="stylesheet" href="css/ace-ie.min.css" />
		<![endif]-->
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.js"></script><!--
		
		
		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/datepicker.css" /> 日期框 -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.min.css" type="text/css"></link>
		<!--引入弹窗组件start-->
<!--		<script type="text/javascript" src="js/zDialog/js/zDialog/zDrag.js"></script>-->
<!--		<script type="text/javascript" src="js/zDialog/js/zDialog/zDialog.js"></script>-->
		<!--引入弹窗组件end-->
		
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/artDialog.source.js?skin=blue"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/plugins/iframeTools.source.js"></script>
		
		
	</head>