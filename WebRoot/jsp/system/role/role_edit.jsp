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
	
	
	//保存
	function save(){
		if($("#roleName").val()==""){
			$("#roleName").focus();
			return false;
		}
			$("#form1").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
	}
	
	
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/artDialog.source.js?skin=blue"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/plugins/iframeTools.source.js"></script>
<!-- <script>
		(function () {
			var api = art.dialog.open.api;	// 			art.dialog.open扩展方法
			if (!api) return;
			// 操作对话框
			api.title('编辑')
				// 自定义按钮
				.button({
						name: '确定',
						callback: function () {
						    //art.dialog.opener.addHf(ny);
							//art.dialog.close();
							save();
							return false;
						},
						focus: true
					},{
						  name: '取消',
						  callback: function () {
						}
					}
				);
			})();
		</script>
		 -->
	</head>
<body>
		<form action="${pageContext.request.contextPath}/role/edit.do" name="form1" id="form1"  method="post">
		<input type="hidden" name="ROLE_ID" id="id" value="${pd.ROLE_ID}"/>
			<div id="zhongxin">
			<table>
				<tr>
					<td align="right" width="20%">名称:</td><td><input type="text" name="ROLE_NAME" id="roleName" value="${pd.ROLE_NAME}" placeholder="这里输入名称" title="名称" /></td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2">
						<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
						<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">取消</a>
					</td>
				</tr>
			</table>
			</div>
		</form>
	
	<div id="zhongxin2" class="center" style="display:none"><img src="${pageContext.request.contextPath}/images/jzx.gif"  style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>
	
</body>
</html>
