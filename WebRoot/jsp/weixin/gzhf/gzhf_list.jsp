﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- jsp文件头和头部 -->
<%@ include file="../../system/admin/top.jsp"%>   

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
	<li><i class="icon-home"></i> <a>微信服务</a><span class="divider"><i class="icon-angle-right"></i></span></li>
	<li class="active">关注回复</li>
</ul><!--.breadcrumb-->

<div id="nav-search">
</div><!--#nav-search-->

</div><!--#breadcrumbs-->


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="${pageContext.request.contextPath}/gzhf/findList.do?isM1=${pdm.isM1 }&isM2=${pdm.isM2 }" method="post" name="userForm" id="userForm">
			<table>
				<tr>
					<td>&nbsp;&nbsp;<font color="#808080">修改时间：</font></td>
					<td><input class="span10 date-picker" name="qsxgsj" value="${pd.qsxgsj}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始时间"/></td>
					<td><input class="span10 date-picker" name="jsxgsj" value="${pd.jsxgsj}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束时间"/></td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"><i id="nav-search-icon" class="icon-search"></i></button></td>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th>序号</th>
						<th>标题</th>
						<th>状态</th>
						<th>提交人</th>
						<th>修改时间</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty list}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${list}" var="var" varStatus="vs">
									
							<tr>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td><a>${var.bt }</a></td>
								<td style="width:60px;" class="center">
									<c:if test="${var.status=='Y'}">启用</c:if>
									<c:if test="${var.status=='N'}">停用</c:if>
								</td>
								<td>${var.tjrmc }</td>
								<td>${var.xgsj}</td>
								<td style="width: 60px;">
									<div class='hidden-phone visible-desktop btn-group'>
										
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
										</c:if>
										
										<c:if test="${QX.edit == 1 }">
										<a class='btn btn-mini btn-info' title="启用" onclick="updateStatus('${var.id }');">启用</a>
										<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${var.id }');"><i class='icon-edit'></i></a>
										</c:if>
										<c:choose>
											<c:when test="${user.USERNAME=='admin'}"></c:when>
											<c:otherwise>
												<c:if test="${QX.del == 1 }">
												 <a class='btn btn-mini btn-danger' title="删除" onclick="del('${var.id }','${var.status}');"><i class='icon-trash'></i></a>
												</c:if>
											</c:otherwise>
										</c:choose>
									</div>
								</td>
							</tr>
						
						</c:forEach>
						</c:if>
						
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="10" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="10" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
					
				
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<c:if test="${QX.add == 1 }">
				<td style="vertical-align:top;"><a class="btn btn-small btn-success" onclick="add();">新增</a></td>
				</c:if>
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
		
		//新增
		function add(){
			 art.dialog.open('${pageContext.request.contextPath}/gzhf/toadd.do',{
		   	 	title:'新增',
				width:320,
	    		height:400,
	    		lock: true
			});//打开子窗体
		}
		
		
		//修改
		function edit(id){
			art.dialog.open('${pageContext.request.contextPath}/gzhf/toedit.do?id='+id,{
		   	 	title:'编辑',
				width:320,
	    		height:400,
	    		lock: true
			});//打开子窗体
		}
		
		//删除
		function del(id,status){
			if(status=='Y'){
				art.dialog.alert("启用状态的数据不可进行删除。");
			}else{
				bootbox.confirm("确定要删除该记录?", function(result) {
					if(result) {
						var url = "${pageContext.request.contextPath}/gzhf/remove.do?id="+id;
						$.get(url,function(data){
							if(data=="success"){
								//document.location.reload();
								nextPage(${page.currentPage});
							}
						});
					}
				});
			}
		}
		
		function updateStatus(id){
			$.ajax({
                   type: "POST",
                   url: '${pageContext.request.contextPath}/gzhf/updateStatus.do?id='+id,
                   data: '',
                   success: function (result) {
                   	nextPage(${page.currentPage});
                   },
                   error: function(data) {
                    }
               });
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

