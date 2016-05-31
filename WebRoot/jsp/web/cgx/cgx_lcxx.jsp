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
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
		<!--[if IE 7]>
		  <link rel="stylesheet" href="css/font-awesome-ie7.min.css" />
		<![endif]-->
		<!-- page specific plugin styles -->
		
		<!-- 下拉框-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/chosen.css" />
		
		<!-- ace styles -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ace.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ace-skins.min.css" />
		<!--[if lt IE 9]>
		  <link rel="stylesheet" href="css/ace-ie.min.css" />
		<![endif]-->
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.js"></script>
		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/datepicker.css" /><!-- 日期框 -->
		
		<script type="text/javascript" src="<%=path %>/js/artDialog/artDialog.source.js?skin=blue"></script>
		<script type="text/javascript" src="<%=path %>/js/artDialog/plugins/iframeTools.source.js"></script>
	
		<script type="text/javascript">
			$(document).ready(function(){  
				var label = '${pd.label}';
				if(label=='b'){
					$("#zhongxin").hide();
	               	$("#zhongxin2").show();
	               	art.dialog.opener.saveSuccess();
				}
				
				findProcessDefinitionForJson();
			}); 

			//保存
			function getByteLen(val) {   
				var len = 0;   
				for (var i = 0; i < val.length; i++) {   
					if (val[i].match(/[^\x00-\xff]/ig) != null) //全角   
						len += 2;   
					else   
						len += 1;   
					}   
				return len;   
			}
			
			function save(){
				var key = $("#key").val();
				var keynum = getByteLen(key);
				
				if(keynum==0){
					art.dialog.alert("请选择要执行的工作流。");
				}else{
					$.ajax({
                    type: "POST",
                    url: '${pageContext.request.contextPath}/cgx/saveBdxx.do',
                    data: $('#cdForm').serialize(),
                    success: function (result) {
                    	$("#zhongxin").hide();
                    	$("#zhongxin2").show();
                    	art.dialog.opener.saveSuccess();
                    },
                    error: function(data) {
                     }
                });
				}
			}
			
			
			
			function findProcessDefinitionForJson(){
				$.ajax({
					url : '${pageContext.request.contextPath}/workflow/findProcessDefinitionForJson.do',
					data : {
					},
					dataType : 'json',
					success : function(data) {
						var tt = '<option value="" selected="true" disabled="true">请选择</option>';
						$.each(data, function(i, n) {    
								tt+='<option value="'+n.key+'">'+n.name+'</option>' 
						}); 
						$("#key").html(tt);
					}
				});		
			}
			
			
		</script>
		
	</head>
<body style="background: #fff;">
	<form action="${pageContext.request.contextPath}/cgx/saveBdxx.do" name="cdForm" id="cdForm" method="post" enctype="multipart/form-data">
		<div id="zhongxin">
		<table width="90%">
			<tr>
				<td width="20%" align="right">选择部署流程：</td>
				<td>
					<select name="key" id="key" style="width:240px">
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<input type="hidden" id="id" name="id" value="<%=request.getParameter("id") %>">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="${pageContext.request.contextPath}/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
</body>
</html>