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
		
<script type="text/javascript">
	
$(document).ready(function(){
	if($("#user_id").val()!=""){
		$("#loginname").attr("readonly","readonly");
		$("#loginname").css("color","gray");
	}
});
	
	//保存
	function save(){
		if($("#role_id").val()==""){
			$("#role_id").focus();
			return false;
		}
		if($("#loginname").val()=="" || $("#loginname").val()=="此用户名已存在!"){
			$("#loginname").focus();
			$("#loginname").val('');
			$("#loginname").css("background-color","white");
			return false;
		}else{
			$("#loginname").val(jQuery.trim($('#loginname').val()));
		}
		if($("#userId").val()=="" && $("#password").val()==""){
			$("#password").focus();
			return false;
		}
		if($("#password").val()!=$("#chkpwd").val()){
			alert("两次密码不相同!");
			$("#password").focus();
			return false;
		}
		if($("#name").val()==""){
			$("#name").focus();
			return false;
		}
		if($("#user_id").val()==""){
			hasU();
		}else{
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
	
	//判断用户名是否存在
	function hasU(){
		var USERNAME = $("#loginname").val();
		var url = "${pageContext.request.contextPath}/user/hasU.do?USERNAME="+USERNAME+"&tm="+new Date().getTime();
		$.get(url,function(data){
			if(data=="error"){
				$("#loginname").css("background-color","#D16E6C");
				
				setTimeout("$('#loginname').val('此用户名已存在!')",500);
				
			}else{
				$("#userForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
		});
	}
	
	function findCity(){
		$("#province").val($("#my_province").find("option:selected").attr("title"));
		$("#city").val("");
		$("#zdd").val("");
		var val = $("#my_province").find("option:selected").attr("value");
		$.ajax({
			url : '${pageContext.request.contextPath}/user/findDict.do?pid='+$("#my_province").find("option:selected").attr("title"),
			type:'POST',
			dataType : 'json',
			success : function(data) {
				if(data.length>0){
					$("#myTr").remove();
					var str='<tr id="myTr"><td></td><td>';
						str+='<select class="" name="cljg" onchange="findZdd()" id="my_city" data-placeholder="请选择城市" style="vertical-align:top;">';
						str+='<option value="'+val+'" title="">不限</option>';
					$(data).each(function(index){
						str+='<option value="'+data[index].bh+'"  title="'+data[index].zd_id+'">'+data[index].name+'</option>';
					})
					str+='</select></td></tr>';
					$("#my_table tr:last").before(str);
				}
			}
		});
	}
	
	
		function findZdd(){
		$("#city").val($("#my_city").find("option:selected").attr("title"));
		$("#zdd").val("");
		var val = $("#my_city").find("option:selected").attr("value");
		$.ajax({
			url : '${pageContext.request.contextPath}/user/findDict.do?pid='+$("#my_city").find("option:selected").attr("title"),
			type:'POST',
			dataType : 'json',
			success : function(data) {
				if(data.length){
					$("#myTr1").remove();
					var str='<tr id="myTr1"><td></td><td>';
						str+='<select class="" name="cljg" id="my_zdd" onchange="findSelect()" data-placeholder="请选择支队/大队" style="vertical-align:top;">';
						str+='<option value="'+val+'" title="">不限</option>';
					$(data).each(function(index){
						str+='<option value="'+data[index].bh +'" title="'+data[index].zd_id+'">'+data[index].name+'</option>';
					})
					str+='</select></td></tr>';
					$("#my_table tr:last").before(str);
				}
			}
		});
	}
	
	function findSelect(){
		$("#zdd").val($("#my_zdd").find("option:selected").attr("title"));
	}
	
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/artDialog.source.js?skin=blue"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/plugins/iframeTools.source.js"></script>
	</head>
<body>
	<form action="${pageContext.request.contextPath}/user/${msg }.do" name="userForm" id="userForm" method="post">
		<input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }"/>
		<div id="zhongxin">
		<table id="my_table">
			
			<c:if test="${fx != 'head'}">
			<c:if test="${pd.ROLE_ID != '1'}">	
			<tr class="info">
				<td align="right" width="25%">选择职位:</td>
				<td>
				<select class="chzn-select" name="ROLE_ID" id="role_id" data-placeholder="请选择职位" style="vertical-align:top;">
				<option value=""></option>
				<c:forEach items="${roleList}" var="role">
					<option value="${role.ROLE_ID }" <c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
				</c:forEach>
				</select>
				</td>
			</tr>
			</c:if>
			<c:if test="${pd.ROLE_ID == '1'}">
			<input name="ROLE_ID" id="role_id" value="1" type="hidden" />
			</c:if>
			</c:if>
			
			<c:if test="${fx == 'head'}">
				<input name="ROLE_ID" id="role_id" value="${pd.ROLE_ID }" type="hidden" />
			</c:if>
			
			<tr>
				<td align="right">用户名:</td><td><input type="text" name="USERNAME" id="loginname" value="${pd.USERNAME }" placeholder="这里输入用户名" title="用户名"/></td>
			</tr>
			<tr>
				<td align="right">密码:</td><td><input type="password" name="PASSWORD" id="password"  placeholder="输入密码" title="密码"/></td>
			</tr>
			<tr>
				<td align="right">确认密码:</td><td><input type="password" name="chkpwd" id="chkpwd"  placeholder="确认密码" title="确认密码" /></td>
			</tr>
			<tr>
				<td align="right">姓名:</td><td><input type="text" name="NAME" id="name"  value="${pd.NAME }" placeholder="这里输入姓名" title="姓名"/></td>
			</tr>
			<tr>
				<td align="right">备注:</td><td><input type="text" name="BZ" id="BZ"value="${pd.BZ }" placeholder="这里输入备注" title="备注"/></td>
			</tr>
			<tr>
				<td align="right">单位:</td><td>
					<select class="chzn-select" name="cljg" onchange="findCity()" id="my_province" data-placeholder="请选择省份" style="vertical-align:top;">
						<option value="" title="">不限</option>
						<option value="99d1e82e99b24606b96269f907063474" title="99d1e82e99b24606b96269f907063474">山东省</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">取消</a>
					<input type="hidden" id="province" name="province" value="">
					<input type="hidden" id="city" name="city" value="">
					<input type="hidden" id="zdd" name="zdd" value="">
				</td>
			</tr>
		</table>
		</div>
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="${pageContext.request.contextPath}/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
</body>
</html>