<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<li><i class="icon-home"></i> <a>表单信息</a><span class="divider"><i class="icon-angle-right"></i></span></li>
	<li class="active">${pp.bt }</li>
</ul><!--.breadcrumb-->

<div id="nav-search">
</div><!--#nav-search-->

</div><!--#breadcrumbs-->


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="${pageContext.request.contextPath}/dcbgl/findBdxx.do?id=${pp.id }&isM1=${pdm.isM1 }&isM2=${pdm.isM2 }" method="post" name="userForm" id="userForm">
			<table>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th>序号</th>
						<c:set var="v" value="${pp.text}"></c:set>
						<c:forEach items="${v}" var="v1">
							<th>${v1 }</th>
						</c:forEach>
						<c:if test="${pp.activiti_key!=''&&pp.activiti_key!=null}">
							<th>执行状态</th>
						</c:if>
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
								<c:set var="v" value="${pp.field}"></c:set>
								<c:forEach items="${v}" var="v1">
									<th>${var[v1] }</th>
								</c:forEach>
								<c:if test="${pp.activiti_key!=''&&pp.activiti_key!=null}">
									<td>
										<c:if test="${var.status=='N'}">流程未启动</c:if>
										<c:if test="${var.status=='1'}">流程执行中</c:if>
										<c:if test="${var.status=='2'}">流程已结束</c:if>	
									</td>
								</c:if>
								<td>${var.yhmc }</td>
								<td>${var.tjsj}</td>
								<td style="width: 60px;">
									<div class='hidden-phone visible-desktop btn-group'>
										<c:if test="${pp.activiti_key!=''&&pp.activiti_key!=null&&var.status=='N'}">
											<a class='btn btn-mini btn-info' title="启动工作流" onclick="startWorkflow('${pp.id}','${var.id }','${pp.activiti_key }');">启动工作流</a>
										</c:if>
										<c:if test="${pp.activiti_key!=''&&pp.activiti_key!=null&&var.status!='N'}">
											<a class='btn btn-mini btn-info' title="查看审批历史" onclick="findTaskInfo('${pp.id}','${var.id }','${pp.activiti_key }');">查看审批历史</a>
											<a class='btn btn-mini btn-info' title="查看流程图" onclick="findViewImage('${pp.id}','${var.id }','${pp.activiti_key }');">查看流程图</a>
										</c:if>
										<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${pp.id}','${var.id }');"><i class='icon-edit'></i></a>
										<a class='btn btn-mini btn-danger' title="删除" onclick="del('${pp.id}','${var.id }');"><i class='icon-trash'></i></a>
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
				<td style="vertical-align:top;"><a class="btn btn-small btn-success" onclick="add('${pp.id}');">新增</a></td>
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
		
		
		function add(id){
			 art.dialog.open('${pageContext.request.contextPath}/dcbgl/toaddBdxx.do?id='+id,{
		   	 	title:'查看调查表统计',
				width:1000,
	    		height:560,
	    		lock: true
			});//打开子窗体
		}
			
		//检索
		function search(){
			$("#userForm").submit();
		}
		
		
			//修改
		function edit(id,bdid){
			art.dialog.open('${pageContext.request.contextPath}/dcbgl/toeditBdxx.do?id='+id+'&bdid='+bdid,{
		   	 	title:'编辑',
				width:1000,
	    		height:560,
	    		lock: true
			});//打开子窗体
		}
		
		//删除
		function del(id,bdid){
			bootbox.confirm("确定要删除该记录?", function(result) {
				if(result) {
					var url = "${pageContext.request.contextPath}/dcbgl/removeBdxx.do?id="+id+"&bdid="+bdid;
					$.get(url,function(data){
						if(data=="success"){
							//document.location.reload();
							nextPage(${page.currentPage});
						}
					});
				}
			});
		}
		
		
		
		function startWorkflow(id,bdid,key){
			bootbox.confirm("确定要启动工作流吗?", function(result) {
				if(result) {
					var url = "${pageContext.request.contextPath}/workflow/startProcess.html?id="+id+"&bdid="+bdid+"&key="+key;
					$.get(url,function(data){
						if(data){
							//document.location.reload();
							nextPage(${page.currentPage});
						}
					});
				}
			});
		}
		
		
		
		//查看流程图
		function findViewImage(id,bdid,key){
			 art.dialog.open('${pageContext.request.contextPath}/workflow/getWFImage.do?key='+key+"&id="+id+"&bdid="+bdid,{
		   	 	title:'查看流程图',
				width:802,
	    		height:420,
	    		lock: true
			});//打开子窗体
		}
		
		
		
		function findTaskInfo(id,bdid,key){
			art.dialog.open('${pageContext.request.contextPath}/workflow/findBdxxTask.do?key='+key+'&id='+id+'&bdid='+bdid,{
		   	 	title:'查看任务流程',
				width:820,
	    		height:420,
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

