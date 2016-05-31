﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- jsp文件头和头部 -->
<%@ include file="../../system/admin/top.jsp"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<body>
		
<!-- 页面顶部¨ -->
<%@ include file="../../system/admin/head.jsp"%> 
		
<div class="container-fluid" id="main-container">

<a href="#" id="menu-toggler"><span></span></a><!-- menu toggler -->

<!-- 左侧菜单 -->
<%@ include file="../../system/admin/left.jsp"%> 
	
<div id="main-content" class="clearfix">

<div id="breadcrumbs">

<ul class="breadcrumb">
	<li><i class="icon-home"></i> <a>调查表</a><span class="divider"><i class="icon-angle-right"></i></span></li>
	<li class="active">我的调查表</li>
</ul><!--.breadcrumb-->

<div id="nav-search">
</div><!--#nav-search-->

</div><!--#breadcrumbs-->


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="${pageContext.request.contextPath}/dcbgl/findMyDcbxxlistPage.do?isM1=${pdm.isM1 }&isM2=${pdm.isM2 }" method="post" name="userForm" id="userForm">
			<table>
				<tr>
					<td>&nbsp;&nbsp;<font color="#808080">标题：</font></td>
					<td><input type="text" name="bt" value="${pd.bt }" placeholder="这里输入标题" style="width:130px;"/></td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"><i id="nav-search-icon" class="icon-search"></i></button></td>
				</tr>
			</table>
			<!-- 检索  -->
			
			<c:forEach items="${list}" var="var" varStatus="status">
			<table id="table_report" class="table table-striped table-bordered table-hover" style="table-layout: fixed;">
				<thead>
					<tr>
						<th>序号</th>
						<th>标题</th>
						<th>到期时间</th>
						<c:set var="v" value="${var.text}"></c:set>
						<c:forEach items="${v}" var="v1">
							<th>${v1 }</th>
						</c:forEach>
						<th>状态</th>
						<th>提交人</th>
						<th>修改时间</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
					<tr>
						<td class='center' style="width: 30px;">${vs.index+1}</td>
						<td><a>${var.bt }</a></td>
						<td>${var.dqsj }</td>
						<c:set var="v" value="${var.field}"></c:set>
						<c:forEach items="${v}" var="v1" varStatus="idxStatus">
							<th style="overflow: auto;">${var.dcbPd[v1]}
								<c:if test="${fn:contains(v1,'file')}">  
                                      <button onclick="down('${var.dcbPd[v1]}')">下载</button>
                                </c:if>  
							</th>
							
						</c:forEach>
						<td style="width:60px;" class="center">
							<c:if test="${var.dcbPd.status=='N'}">未提交</c:if>
							<c:if test="${var.dcbPd.status=='Y'}">已提交</c:if>
						</td>
						<td>${var.dcbPd.yhmc }</td>
						<td>${var.dcbPd.tjsj}</td>
						<td style="width: 60px;">
							<div class='hidden-phone visible-desktop btn-group'>
								<c:if test="${var.dcbPd.status=='N'}">
									<a class='btn btn-mini btn-info' title="立即填写" onclick="saveMyDcbxx('${var.id }','${var.dcbPd.id }');">立即填写</a>	
								</c:if>
								<c:if test="${var.dcbPd.status=='Y'}">
									<a class='btn btn-mini btn-info' title="已填写">已填写</a>
								</c:if>
							</div>
						</td>
					</tr>
						
				</tbody>
			</table>
			</c:forEach>
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
	</div>
 
 
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!-- #main-content -->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script src="${pageContext.request.contextPath}/1.9.1/jquery.min.js"></script>
		<script type="text/javascript">window.jQuery || document.write("<script src='${pageContext.request.contextPath}/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/ace-elements.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/ace.min.js"></script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		<!--引入弹窗组件start-->
<!--		<script type="text/javascript" src="js/attention/zDialog/zDrag.js"></script>-->
<!--		<script type="text/javascript" src="js/attention/zDialog/zDialog.js"></script>-->
		<!--引入弹窗组件end-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/artDialog.source.js?skin=blue"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/plugins/iframeTools.source.js"></script>
		
		
		<script type="text/javascript">
		function saveSuccess(){
			var num = '${page.currentPage}';
		 	if(num == '0'){
		 		location.href = location.href;
		 	}else{
		 		nextPage(${page.currentPage});
		 	}
		}
			
		//检索
		function search(){
			$("#userForm").submit();
		}
		
		function down(url){
			$.ajax({ 
					type: 'POST',
					dataType:'json',
					url : '${pageContext.request.contextPath}/dcbgl/downxz.do?url='+url,
					success: function(r){
						//alert(r.msg);
						alert("下载成功");
			    	}
			    });
		}
		
		//修改
		function saveMyDcbxx(id,myid){
			art.dialog.open('${pageContext.request.contextPath}/dcbgl/toAddMyDcbxx.do?id='+id+'&myid='+myid,{
		   	 	title:'查看调查表统计',
				width:1000,
	    		height:560,
	    		lock: true
			});//打开子窗体
		}
		
		
		
		</script>
		
		<script type="text/javascript">
		
		$(function() {
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		</script>
		
		
	</body>
</html>

