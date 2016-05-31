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
		<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" rel="stylesheet" />
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.js"></script>
</head>

<script type="text/javascript">
	
	$(document).ready(function(){		
		if($("#menuId").val()!=""){
			var parentId = "${menu.parentId}";
			if(parentId==""){
				$("#parentId").attr("disabled",true);
			}else{
				$("#parentId").val(parentId);
			}
		}
		setMUR();
	}); 

	function setMUR(){
		if($("#parentId").val()=="0"){
			$("#menuUrl").attr("readonly",true);
			$("#menuUrl").val("");
		}else{
			$("#menuUrl").attr("readonly",false);
		}
	}

	//保存
	function save(){
		if($("#menuName").val()==""){
			$("#menuName").focus();
			return false;
		}
		if($("#menuOrder").val()==""){
			$("#menuOrder").focus();
			return false;
		}
		
		if(isNaN(Number($("#menuOrder").val()))){
			$("#menuOrder").focus();
			$("#menuOrder").val(1);
			return false;
		}
		
		 $.ajax({
            type: "POST",
            url: '${pageContext.request.contextPath}/menu/add.do',
            data: $('#menuForm').serialize(),
            success: function (result) {
            	$("#zhongxin").hide();
            	$("#zhongxin2").show();
            	art.dialog.opener.saveSuccess();
            },
            error: function(data) {
             }
        });
                
		
		//$("#menuForm").submit();
		//$("#zhongxin").hide();
		//$("#zhongxin2").show();
	}
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/artDialog.source.js?skin=blue"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/plugins/iframeTools.source.js"></script>

<body>
	<form  action="${pageContext.request.contextPath}/menu/add.do" name="menuForm" id="menuForm" method="post" >
		<input type="hidden" name="MENU_ID" id="menuId" value="${menu.MENU_ID }"/>
		<div id="zhongxin">
		<table>
			<tr class="info">
				<td align="right" width="25%">顶级菜单:</td>
				<td>
					<select name="PARENT_ID" id="parentId" class="input_txt" onchange="setMUR()"  title="菜单">
						<option value="0">顶级菜单</option>
						<c:forEach items="${menuList}" var="menu">
							<option value="${menu.MENU_ID }">${menu.MENU_NAME }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr class="info">
				<td align="right">菜单名称:</td><td><input type="text" name="MENU_NAME" id="menuName" placeholder="这里输入菜单名称" value="${menu.MENU_NAME }" title="名称"/></td>
			</tr>
			<tr class="info">
				<td align="right">链接地址:</td><td><input type="text" name="MENU_URL" id="menuUrl" placeholder="这里输入链接地址" value="${menu.MENU_URL }" title="地址"/></td>
			</tr>
			<tr class="info">
				<td align="right">序号:</td><td><input type="number" name="MENU_ORDER" id="menuOrder" placeholder="这里输入序号" value="${menu.MENU_ORDER}" title="序号"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><img src="${pageContext.request.contextPath}/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
	</form>
</body>
</html>