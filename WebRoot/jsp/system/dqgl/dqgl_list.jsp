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
	<li><i class="icon-home"></i> <a>网站管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>
	<li class="active">地区管理</li>
</ul><!--.breadcrumb-->

<div id="nav-search">
</div><!--#nav-search-->

</div><!--#breadcrumbs-->


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
			<form action="${pageContext.request.contextPath}/dqgl/findList.do?isM1=${pdm.isM1 }&isM2=${pdm.isM2 }" method="post" name="userForm" id="userForm">
			<!-- 检索  -->
			<div style="border-bottom: 1px solid #eee;">
				<table>
					<tr>
						<td>&nbsp;&nbsp;<font color="#808080">地区名称：</font></td>
						<td><input type="text" name="dqgl" id="dqgl" value="${pd.dqgl }" style="width: 95%" placeholder="这里输入地区名称" title="地区名称"/></td>
						<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"><i id="nav-search-icon" class="icon-search"></i></button></td>
					</tr>
				</table>
			</div>
			<!-- 检索  -->
		
			<div style="padding-top: 10px;">
				<div style="float: left; width: 25%;">
					<div class="widget-box" style="">
						<div class="widget-header">
							<h4>菜单</h4>
							<span class="widget-toolbar">
								<a class='btn btn-mini btn-info' title="添加" onclick="add();">&nbsp;添加&nbsp; </a>
								<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
							</span>
						</div>
						<div class="widget-body">
							<div class="widget-body-inner">
								 <div class="widget-main">
									<div class="row-fluid">
										<ul id="treeDemo" class="ztree"></ul>
										<input type="hidden" id="m_id" value="" />
										<input type="hidden" id="m_name" value="" />
									</div>
								 </div>
							</div>
						</div>
					</div>
				</div>
				<div style="float: left; width: 75%">
					<div class="widget-box" style="">
						<div class="widget-header">
							<h4>菜单详情</h4>
							<span class="widget-toolbar">
								<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
							</span>
						</div>
						<div class="widget-body">
							<div class="widget-body-inner">
								 <div class="widget-main">
									<div class="row-fluid">
										<table id="table_report" class="table table-striped table-bordered table-hover">
											<thead>
												<tr>
													<th>序号</th>
													<th>地区名称</th>
													<th>排序</th>
													<th>修改时间</th>
													<th>操作</th>
												</tr>
											</thead>
																	
											<tbody id="mtb">
												
											<!-- 开始循环 -->	
											<c:choose>
												<c:when test="${not empty list}">
													<c:if test="${QX.cha == 1 }">
													<c:forEach items="${list}" var="var" varStatus="vs">
																
														<tr>
															<td class='center'>${vs.index+1}</td>
															<td><a>${var.name }</a></td>
															<td>${var.px }</td>
															<td>${var.xgsj }</td>
															<td style="">
																<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${var.id }');"><i class='icon-edit'></i></a>
																<a class='btn btn-mini btn-danger' title="删除" onclick="del('${var.id }');"><i class='icon-trash'></i></a>
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
									</div>
								 </div>
							</div>
						</div>
					</div>
				</div>
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
		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
		<%@include file="../../common/ztree/dqgl_ztree.jsp" %>

		<script type="text/javascript">
		function saveSuccess(){
			location.reload();
		}
			
		//检索
		function search(){
			$("#userForm").submit();
		}
		
		//新增
		function add(){
			var m_id = $("#m_id").val();
			var m_name = $("#m_name").val();
			if(m_id==''||m_id==null){
				alert("请在左侧选择菜单父节点。");
			}else{
				art.dialog.open('${pageContext.request.contextPath}/dqgl/toadd.do?pid='+m_id+'&pdqmc='+m_name,{
			   	 	title:'新增',
					width:640,
		    		height:420,
		    		lock: true
				});//打开子窗体
			}
		}
		
		
		//修改
		function edit(id){
			art.dialog.open('${pageContext.request.contextPath}/dqgl/toedit.do?id='+id,{
		   	 	title:'编辑',
				width:320,
	    		height:400,
	    		lock: true
			});//打开子窗体
		}
		
		//删除
		function del(id){
			bootbox.confirm("确定要删除该记录?", function(result) {
				if(result) {
					var url = "${pageContext.request.contextPath}/dqgl/remove.do?id="+id;
					$.get(url,function(data){
						if(data=="success"){
							document.location.reload();
							//nextPage(${page.currentPage});
						}
					});
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

