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
	<li><i class="icon-home"></i> <a>工作流</a><span class="divider"><i class="icon-angle-right"></i></span></li>
	<li class="active">流程定义</li>
</ul><!--.breadcrumb-->

<div id="nav-search">
</div><!--#nav-search-->

</div><!--#breadcrumbs-->


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="${pageContext.request.contextPath}/workflow/findDeploy.do?isM1=${pdm.isM1 }&isM2=${pdm.isM2 }" method="post" name="userForm" id="userForm">
<!--			<table>-->
<!--				<tr>-->
<!--					<td>&nbsp;&nbsp;<font color="#808080">流程部署ID：</font></td>-->
<!--					<td><input type="text" name="id" value="${pd.id }" placeholder="这里输入流程部署ID" style="width:130px;"/></td>-->
<!--					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"><i id="nav-search-icon" class="icon-search"></i></button></td>-->
<!--				</tr>-->
<!--			</table>-->
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th>ID</th>
						<th>名称</th>
						<th>流程定义的KEY</th>
						<th>流程定义的版本</th>
						<th>流程定义的规则文件名称</th>
						<th>流程定义的规则图片名称</th>
						<th>部署ID</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty pdList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${pdList}" var="var" varStatus="vs">
									
							<tr>
								<td class='center' style="width: 30px;">${var.id}</td>
								<td><a>${var.name }</a></td>
								<td>${var.key }</td>
								<td>${var.version }</td>
								<td>${var.resourceName }</td>
								<td>${var.diagramResourceName }</td>
								<td>${var.deploymentId }</td>
								<td style="width: 60px;">
									<a class='btn btn-mini btn-info' title="流程部署转流程模型" onclick="convert2Model('${var.id }');">流程部署转流程模型</a>&nbsp;
									<a class='btn btn-mini btn-info' title="查看流程图" onclick="findViewImage('${var.id }');">查看流程图</a>
								</td>
							</tr>
						</c:forEach>
						</c:if>
						
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="8" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="8" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
					
				
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<c:if test="${QX.add == 1 }">
<!--				<td style="vertical-align:top;"><a class="btn btn-small btn-success" onclick="add();">新增</a></td>-->
				</c:if>
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
		
		
		
		//反转流程
		function convert2Model(id){
			 art.dialog.open('${pageContext.request.contextPath}/workflow/convert2Model.do?definitionId='+id,{
		   	 	title:'查看流程图',
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

