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
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.js"></script>
		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/datepicker.css" /><!-- 日期框 -->
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/artDialog.source.js?skin=blue"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/plugins/iframeTools.source.js"></script>
		
		
		<link rel='stylesheet prefetch' href='${pageContext.request.contextPath}/jsp/web/ui/css/form-builder.min.css'>
		<link rel='stylesheet prefetch' href='${pageContext.request.contextPath}/jsp/web/ui/css/form-render.min.css'>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/web/ui/css/style.css">
		<script src='${pageContext.request.contextPath}/jsp/web/ui/js/jquery-ui.min.js'></script>
		<script src='${pageContext.request.contextPath}/jsp/web/ui/js/form-builder.js'></script>
		<script src='${pageContext.request.contextPath}/jsp/web/ui/js/form-render.min.js'></script>
		<script src="${pageContext.request.contextPath}/jsp/web/ui/js/index.js"></script>
	
		<script type="text/javascript">
			$(document).ready(function(){  
				var label = '${pd.label}';
				if(label=='b'){
					$("#zhongxin").hide();
                   	$("#zhongxin2").show();
                   	art.dialog.opener.saveSuccess();
				}
			}); 
		
			function save(){
				$("#cdForm").submit();
			}
		</script>
		
	</head>
<body style="background: #fff;">
	<c:if test="${pd.label!='b'}">
	<form action="${pageContext.request.contextPath}/dcbgl/saveMyDcbxx.do" name="cdForm" id="cdForm" method="post" enctype="multipart/form-data">
		
		${p.html }
		
		<table width="90%">
			<tr>
				<td style="text-align: center;" colspan="2">
					<input type="hidden" id="id" name="id" value="${pd.myid }">
					<input type="hidden" id="dcbid" name="dcbid" value="${pd.id }">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">取消</a>
				</td>
			</tr>
		</table>
	</form>
	</c:if>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='${pageContext.request.contextPath}/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/ace-elements.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/ace.min.js"></script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		<!--引入弹窗组件start-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/attention/zDialog/zDrag.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/attention/zDialog/zDialog.js"></script>
		<!--引入弹窗组件end-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tips.js"></script><!--提示框-->
		
		
</body>
</html>