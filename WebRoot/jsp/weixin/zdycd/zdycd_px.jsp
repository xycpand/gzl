<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'zdycd_px.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- END PAGE LEVEL STYLES -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui.css">
  <script src="${pageContext.request.contextPath}/js/jquery-ui/external/jquery/jquery.js"></script>
  <script src="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-ui/style.css">
 <style>
  #sortable { list-style-type: none; margin: 0; padding: 0; width: 60%; }
  #sortable li { margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; font-size: 1.4em; height: 18px; }
  #sortable li span { position: absolute; margin-left: -1.3em; }
  </style>
  <script>
  $(function() {
	  	var sortable = $("ul[id ='sortable']");
	    $("#sortable" ).sortable({
	      placeholder: "ui-state-highlight",
	      opacity: 0.5,//拖动的透明度
	        revert: true, //缓冲效果 
	        cursor: 'move', //拖动的时候鼠标样式 
	        connectWith: ".column",
	        stop: function(){ 
	        	$.ajax({
	                type: "POST",
	                url: '${pageContext.request.contextPath}/zdycd/updatePx.do?ids='+$("#sortable" ).sortable('toArray'),
	                dataType: "json",
	                success: function (result) {
	                },
	                error: function(data) {
	                 }
	            });
	        }
	    });
	    $( "#sortable" ).disableSelection();
	  });
	  </script>
</head>
<body>
 
<ul id="sortable">
	<c:forEach items="${ls }" var="var">
		<li class="ui-state-default" id="${var.id }" style="width: 300px"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>${var.cdmc }</li>
	</c:forEach>
</ul>
 
 
</body>
</html>
