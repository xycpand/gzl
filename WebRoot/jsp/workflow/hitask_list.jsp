<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- jsp文件头和头部 -->
<%@ include file="../system/admin/top.jsp"%>   

<body>
		
<!-- 页面顶部¨ -->
<%@ include file="../system/admin/head.jsp"%> 
		
<div class="container-fluid" id="main-container">

<a href="#" id="menu-toggler"><span></span></a><!-- menu toggler -->

<!-- 左侧菜单 -->
<%@ include file="../system/admin/left.jsp"%> 
		
<div id="main-content" class="clearfix">

<div id="breadcrumbs">

<ul class="breadcrumb">
	<li><i class="icon-home"></i> <a>任务管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>
	<li class="active">已办理历史任务</li>
</ul><!--.breadcrumb-->

<div id="nav-search">
</div><!--#nav-search-->

</div><!--#breadcrumbs-->


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
	
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th>流程对象ID</th>
						<th>流程标题</th>
						<th>流程申请人</th>
						<th>结束时间</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty myLs}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${myLs}" var="var" varStatus="vs">
									
							<tr>
								<td><a>${var.id }</a></td>
								<td>${var.p.bt }</td>
								<td>${var.p.tjrmc }</td>
								<td><fmt:formatDate value="${var.endTime }" pattern="yyyy-MM-dd HH:mm:SS"/></td>
								<td style="width: 180px;">
									<a class='btn btn-mini btn-info' title="办理任务" onclick="findTaskInfo('${var.businessKey }');">办理任务</a>&nbsp;
									<a class='btn btn-mini btn-info' title="查看流程图" onclick="findViewImage('${var.id }');">查看流程图</a>
								</td>
							</tr>
						</c:forEach>
						</c:if>
						
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="5" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="5" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
					
				
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
				</td>
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
		function findViewImage(id,imageName){
			 art.dialog.open('${pageContext.request.contextPath}/workflow/findViewImage.do?processInstanceId='+id,{
		   	 	title:'查看流程图',
				width:802,
	    		height:420,
	    		lock: true
			});//打开子窗体
		}
		
		
		function findTaskInfo(businessKey){
			art.dialog.open('${pageContext.request.contextPath}/workflow/findHiTask.do?businessKey='+businessKey,{
		   	 	title:'处理代办任务',
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

