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
		<meta charset="utf-8" />
		<title></title>
		
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		
		<meta http-equiv="X-UA-Compatible" content="IE=8" /> 
		
		<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
		<!--[if IE 7]><link rel="stylesheet" href="css/font-awesome-ie7.min.css" /><![endif]-->
<!--<link rel="stylesheet" href="css/ace-ie.min.css" />-->
<!--		<link rel="stylesheet" href="css/ace.min.css" />-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ace-skins.min.css" />
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.js"></script>

				
		<!--引入弹窗组件start-->
<!--		<script type="text/javascript" src="js/attention/zDialog/zDrag.js"></script>-->
<!--		<script type="text/javascript" src="js/attention/zDialog/zDialog.js"></script>-->
		<!--引入弹窗组件end-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/artDialog.source.js?skin=blue"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/plugins/iframeTools.source.js"></script>
		
<script type="text/javascript">
	
	function saveSuccess(){
		setTimeout("location.reload()",100);
	}
	
	
	//新增
	function addmenu(){
		art.dialog.open('${pageContext.request.contextPath}/menu/toAdd.do',{
			title:'新增菜单',
			width:340,
    		height:350,
    		lock: true
		});//打开子窗体
	}
	
	function addmenu1(){
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="新增菜单";
		 diag.URL = '${pageContext.request.contextPath}/menu/toAdd.do';
		 diag.Width = 300;
		 diag.Height = 350;
		 diag.CancelEvent = function(){ //关闭事件
			diag.close();
			setTimeout("location.reload()",100);
		 };
		 diag.show();
	}
	
	function editmenu(menuId){
	   	 art.dialog.open('${pageContext.request.contextPath}/menu/toEdit.do?MENU_ID='+menuId,{
	   	 	title:'编辑菜单',
			width:340,
    		height:350,
    		lock: true
		});//打开子窗体
	}
	
	function editmenu1(menuId){
	   	 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="编辑菜单";
		 diag.URL = '${pageContext.request.contextPath}/menu/toEdit.do?MENU_ID='+menuId;
		 diag.Width = 300;
		 diag.Height = 350;
		 diag.CancelEvent = function(){ //关闭事件
			diag.close();
			setTimeout("location.reload()",100);
		 };
		 diag.show();
	}
	
	//编辑顶部菜单图标
	function editTb(menuId){
		art.dialog.open('${pageContext.request.contextPath}/menu/toEditicon.do?MENU_ID='+menuId,{
	   	 	title:'编辑图标',
			width:500,
    		height:140,
    		lock: true
		});//打开子窗体
	}
	
	//编辑顶部菜单图标
	function editTb1(menuId){
	   	 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="编辑图标";
		 diag.URL = '${pageContext.request.contextPath}/menu/toEditicon.do?MENU_ID='+menuId;
		 diag.Width = 500;
		 diag.Height = 140;
		 diag.CancelEvent = function(){ //关闭事件
			diag.close();
			setTimeout("location.reload()",100);
		 };
		 diag.show();
	}
	
	function delmenu(menuId,isParent){
		var flag = false;
		if(isParent){
			if(confirm("确定要删除该菜单吗？其下子菜单将一并删除！")){
				flag = true;
			}
		}else{
			if(confirm("确定要删除该菜单吗？")){
				flag = true;
			}
		}
		if(flag){
			var url = "${pageContext.request.contextPath}/menu/del.do?MENU_ID="+menuId+"&guid="+new Date().getTime();
			$.get(url,function(data){
				document.location.reload();
			});
		}
	}
	
	function openClose(menuId,curObj,trIndex){
		var txt = $(curObj).text();
		if(txt=="展开"){
			$(curObj).text("折叠");
			$("#tr"+menuId).after("<tr id='tempTr"+menuId+"'><td colspan='5'>数据载入中</td></tr>");
			if(trIndex%2==0){
				$("#tempTr"+menuId).addClass("main_table_even");
			}
			var url = "${pageContext.request.contextPath}/menu/sub.do?MENU_ID="+menuId+"&guid="+new Date().getTime();
			$.get(url,function(data){
				if(data.length>0){
					var html = "";
					$.each(data,function(i){
						html = "<tr style='height:24px;line-height:24px;' name='subTr"+menuId+"'>";
						html += "<td></td>";
						html += "<td><span style='width:80px;display:inline-block;'></span>";
						if(i==data.length-1)
							html += "<img src='images/joinbottom.gif' style='vertical-align: middle;'/>";
						else
							html += "<img src='images/join.gif' style='vertical-align: middle;'/>";
						html += "<span style='width:100px;text-align:left;display:inline-block;'>"+this.MENU_NAME+"</span>";
						html += "</td>";
						html += "<td>"+this.MENU_URL+"</td>";
						html += "<td class='center'>"+this.MENU_ORDER+"</td>";
						html += "<td><a class='btn btn-mini btn-info' title='编辑' onclick='editmenu(\""+this.MENU_ID+"\")'><i class='icon-edit'></i></a> <a class='btn btn-mini btn-danger' title='删除' onclick='delmenu(\""+this.MENU_ID+"\",false)'><i class='icon-trash'></i></a></td>";
						html += "</tr>";
						$("#tempTr"+menuId).before(html);
					});
					$("#tempTr"+menuId).remove();
					if(trIndex%2==0){
						$("tr[name='subTr"+menuId+"']").addClass("main_table_even");
					}
				}else{
					$("#tempTr"+menuId+" > td").html("没有相关数据");
				}
			},"json");
		}else{
			$("#tempTr"+menuId).remove();
			$("tr[name='subTr"+menuId+"']").remove();
			$(curObj).text("展开");
		}
	}
</script>
</head>

<body>
	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
		<tr>
			<th class="center"  style="width: 50px;">序号</th>
			<th class='center'>名称</th>
			<th class='center'>资源路径</th>
			<th class='center'>排序</th>
			<th class='center'>操作</th>
		</tr>
		</thead>
		<c:choose>
			<c:when test="${not empty menuList}">
				<c:forEach items="${menuList}" var="menu" varStatus="vs">
				<tr id="tr${menu.MENU_ID }">
				<td class="center">${vs.index+1}</td>
				<td class='center'><i class="${menu.MENU_ICON }">&nbsp;</i>${menu.MENU_NAME }</td>
				<td>${menu.MENU_URL }</td>
				<td class='center'>${menu.MENU_ORDER }</td>
				<td style="width: 22%;">
				<a class='btn btn-mini btn-warning' onclick="openClose('${menu.MENU_ID }',this,${vs.index })" >展开</a>
				<a class='btn btn-mini btn-purple' title="图标" onclick="editTb('${menu.MENU_ID }')" ><i class='icon-picture'></i></a>
				<a class='btn btn-mini btn-info' title="编辑" onclick="editmenu('${menu.MENU_ID }')" ><i class='icon-edit'></i></a>
				<a class='btn btn-mini btn-danger' title="删除"  onclick="delmenu('${menu.MENU_ID }',true)"><i class='icon-trash'></i></a>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
				<td colspan="100">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	
	<div class="page_and_btn">
		<div>
			&nbsp;&nbsp;<a class="btn btn-small btn-success" onclick="addmenu();">新增</a>
		</div>
	</div>
	
</body>
</html>