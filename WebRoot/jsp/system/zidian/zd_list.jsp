<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		
		<meta http-equiv="X-UA-Compatible" content="IE=8" /> 

		<meta charset="utf-8" />
		<title></title>
		
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		
		<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
		<!--[if IE 7]><link rel="stylesheet" href="css/font-awesome-ie7.min.css" /><![endif]-->
<!--		<link rel="stylesheet" href="css/ace-ie.min.css" />-->
<!--		<link rel="stylesheet" href="css/ace.min.css" />-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ace-skins.min.css" />
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.js"></script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox.min.js"></script><!-- 确认窗口 -->
				
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

	
	//新增
	function add(PARENT_ID){
		art.dialog.open('${pageContext.request.contextPath}/zidian/toAdd.do?PARENT_ID='+PARENT_ID,{
	   	 	title:'新增',
			width:400,
    		height:350,
    		lock: true
		});//打开子窗体
	}
	
	function add1(PARENT_ID){
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="新增";
		 diag.URL = '${pageContext.request.contextPath}/zidian/toAdd.do?PARENT_ID='+PARENT_ID;
		 diag.Width = 400;
		 diag.Height = 350;
		 diag.CancelEvent = function(){ //关闭事件
			diag.close();
		 	//setTimeout("location.reload()",100);
		 	var num = '${page.currentPage}';
		 	if(num == '0'){
		 		location.href = location.href;
		 	}else{
		 		nextPage(${page.currentPage});
		 	}
		 };
		 diag.show();
	}
	
	function edit(ZD_ID){
	   	 art.dialog.open('${pageContext.request.contextPath}/zidian/toEdit.do?ZD_ID='+ZD_ID,{
	   	 	title:'编辑',
			width:400,
    		height:350,
    		lock: true
		});//打开子窗体
	}
	
	function edit1(ZD_ID){
	   	 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="编辑";
		 diag.URL = '${pageContext.request.contextPath}/zidian/toEdit.do?ZD_ID='+ZD_ID;
		 diag.Width = 400;
		 diag.Height = 350;
		 diag.CancelEvent = function(){ //关闭事件
			diag.close();
			nextPage(${page.currentPage});
		 };
		 diag.show();
	}
	
	function del(ZD_ID,TPDZ){
		var flag = false;
		if(confirm("确定要删除该数据吗?")){
			flag = true;
		}
		if(flag){
			var url = "${pageContext.request.contextPath}/zidian/del.do?ZD_ID="+ZD_ID+"&TPDZ="+TPDZ+"&guid="+new Date().getTime();
			$.get(url,function(data){
				if(data=="success"){
					//document.location.reload();
					nextPage(${page.currentPage});
				}else{
					alert("删除失败，请先删除其下级数据!"); 
				}
			});
		}
	}
	
</script>
</head>

<body>
<div id="page-content" class="clearfix">
<div class="row-fluid">
	
	<!-- 检索  -->
	<form action="${pageContext.request.contextPath}/zidian.do" method="post" name="userForm" id="userForm">
	<input name="PARENT_ID" id="PARENT_ID" type="hidden" value="${pd.PARENT_ID }" />
	<table>
		<tr>
			<td><font color="#808080">名称检索：</font></td>
			<td><input type="text" name="NAME" value="${pd.NAME }" placeholder="这里输入名称" style="width:130px;"/></td>
			<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"><i id="nav-search-icon" class="icon-search"></i></button></td>
			<c:if test="${pd.PARENT_ID != '0'}">
				<c:choose>
				<c:when test="${not empty varsList}">
				<td style="vertical-align:top;"><a href="<%=basePath%>/zidian.do?PARENT_ID=0" class="btn btn-mini btn-purple" title="查看">顶级<i class="icon-arrow-right  icon-on-right"></i></a></td>
				<c:forEach items="${varsList}" var="var" varStatus="vsd">
				<td style="vertical-align:top;"><a href="<%=basePath%>/zidian.do?PARENT_ID=${var.ZD_ID }" class="btn btn-mini btn-purple" title="查看">${var.NAME }<i class="icon-arrow-right  icon-on-right"></i></a></td>
				</c:forEach>
				</c:when>
				<c:otherwise>
				</c:otherwise>
				</c:choose>
			</c:if>
			
		</tr>
	</table>
	<!-- 检索  -->

	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
		<tr>
			<th class="center"  style="width: 50px;">序号</th>
			<th class='center'>编号</th>
			<th class='center'>名称</th>
			<th class='center'>编码</th>
			<th class='center'>级别</th>
			<th class='center'>操作</th>
		</tr>
		</thead>
		<c:choose>
			<c:when test="${not empty varList}">
				<c:forEach items="${varList}" var="var" varStatus="vs">
				<tr>
				<td class="center">${var.ORDY_BY }</td>
				<td class="center">${var.bh }</td>
				<td class='center'><a href="<%=basePath%>/zidian.do?PARENT_ID=${var.ZD_ID }" title="查看下级">${var.NAME }</a></td>
				<td class='center'>${var.P_BM }</td>
				<td class='center' style="width:35px;"><b class="green">${var.JB }</b></td>
				<td style="width: 63px;">
				<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${var.ZD_ID }')" ><i class='icon-edit'></i></a>
				<a class='btn btn-mini btn-danger' title="删除"  onclick="del('${var.ZD_ID }','${var.TPDZ }')"><i class='icon-trash'></i></a>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
				<td colspan="100" class="center">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;width:50px;"><a class="btn btn-small btn-success" onclick="add('${pd.PARENT_ID}');">新增</a></td>
				<c:if test="${pd.PARENT_ID != '0'}">
					<td style="vertical-align:top;" class="left"><a class="btn btn-small btn-info" onclick="location.href='${pageContext.request.contextPath}/zidian.do?PARENT_ID=${pdp.PARENT_ID }';">返回</a></td>
				</c:if>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
	
	</form>
</div>
</div>

</body>
</html>