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
		<meta http-equiv="X-UA-Compatible" content="IE=8" /> 
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" rel="stylesheet" />
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.js"></script>
</head>

<script type="text/javascript">


$(document).ready(function(){  
	var label = '${pd.flag}';
	if(label=='upload'){
		$("#zhongxin").hide();
       	$("#zhongxin2").show();
       	art.dialog.opener.saveSuccess();
	}
});
	
	//保存
	function save(){
		if($("#NAME").val()==""){
			$("#NAME").focus();
			return false;
		}
		
		if($("#BIANMA").val()=="" || $("#BIANMA").val()=="此编码已存在!"){
			$("#BIANMA").focus();
			$("#BIANMA").val('');
			$("#BIANMA").css("background-color","white");
			return false;
		}
		
		if($("#ORDY_BY").val()==""){
			$("#ORDY_BY").focus();
			return false;
		}
		
		if(isNaN(Number($("#ORDY_BY").val()))){
			$("#ORDY_BY").focus();
			$("#ORDY_BY").val(1);
			return false;
		}
		
		has();
		
	}
	
	//判断编码是否存在
	function has(){
		var ZD_ID = $("#ZD_ID").val();
		var BIANMA = $("#BIANMA").val();
		var url = "${pageContext.request.contextPath}/zidian/has.do?BIANMA="+BIANMA+"&ZD_ID="+ZD_ID+"&tm="+new Date().getTime();
		$.get(url,function(data){
			if(data=="error"){
				$("#BIANMA").css("background-color","#D16E6C");
				
				setTimeout("$('#BIANMA').val('此编码已存在!')",500);
				
			}else{
				$("#Form").submit();
				/* $.ajax({
                    type: "POST",
                    url: 'zidian/save.do',
                    data: $('#Form').serialize(),
                    success: function (result) {
                    	$("#zhongxin").hide();
                    	$("#zhongxin2").show();
                    	art.dialog.opener.saveSuccess();
                    },
                    error: function(data) {
                     }
                });*/
			}
		});
	}
	
</script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/artDialog.source.js?skin=blue"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/plugins/iframeTools.source.js"></script>

<body>
	<form  action="${pageContext.request.contextPath}/zidian/save.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="ZD_ID" id="ZD_ID" value="${pd.ZD_ID }"/>
		<input type="hidden" name="PARENT_ID" id="PARENT_ID" value="${pd.PARENT_ID }"/>
		<div id="zhongxin">
		<table>
			<tr class="info">
				<td align="right" width="20%">编号:</td><td><input type="text" name="bh" id="bh" placeholder="这里输入编号" value="${pd.bh }" title="编号"/></td>
			</tr>
			<tr class="info">
				<td align="right">名称:</td><td><input type="text" name="NAME" id="NAME" placeholder="这里输入名称" value="${pd.NAME }" title="名称"/></td>
			</tr>
			<tr class="info">
				<td align="right">图片:</td>
				<td><img src="${pd.TPDZ_URL }" width="100px" height="100px">
				<input type="file" name="TPDZ" id="TPDZ"/></td>
			</tr>
			<tr class="info">
				<td align="right">编码:</td><td><input type="text" name="BIANMA" id="BIANMA" placeholder="这里输入编码" value="${pd.BIANMA }" title="编码"/></td>
			</tr>
			<tr class="info">
				<td align="right">序号:</td><td><input type="number" name="ORDY_BY" id="ORDY_BY" placeholder="这里输入序号" value="${pd.ORDY_BY}" title="序号"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		<input type="hidden" name="TPDZ" value="${pd.TPDZ }">
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><img src="${pageContext.request.contextPath}/images/jzx.gif" style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>
	</form>
</body>
<script type="text/javascript">
	var msg = '${msg}';
	if(msg == 'no'){
		$("#BIANMA").attr("readonly",true);
	}

</script>
</html>