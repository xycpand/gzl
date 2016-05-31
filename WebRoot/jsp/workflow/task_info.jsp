<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.fh.util.PageData"%>
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
				<%
					PageData pd = (PageData)request.getAttribute("bdPd");
					PageData p = (PageData)request.getAttribute("p");
					String[] fields = p.getString("field").split(",");
					for(int i=0;i<fields.length;i++){
				%>
						var k = '<%=fields[i]%>';
						var v = '<%=pd.get(fields[i])%>';
						//alert(k+"::"+v+"++"+k.indexOf('textarea'));
						if(k.indexOf('input')!=-1||k.indexOf('textarea')!=-1){
							$("#"+k).val(v);
							$("#"+k).attr("disabled","disabled");
						}else if(k.indexOf('radio')!=-1){
							$("input[name='"+k+"'][value="+v+"]").attr("checked",true); 
							$("input[name='"+k+"'][value="+v+"]").attr("disabled","disabled");
						}else if(k.indexOf('select')!=-1){
							$("#"+k).find("option[value='"+v+"']").attr("selected",true);
							$("#"+k).find("option[value='"+v+"']").attr("disabled","disabled");
						}
						
				<%
					}
				%>
				
				
				var fields = '${p.field}'.split(',');
				for(var i = 0;i<fields.length;i++){
					//alert(v);
					//alert('${bdPd[v]}');
					//alert(t);
				}
			}); 
		
		</script>
		
	</head>
<body style="background: #fff;">
	<c:if test="${pd.label!='b'}">
	<form action="${pageContext.request.contextPath}/workflow/submitTask.do" name="cdForm" id="cdForm" method="post" enctype="multipart/form-data">
		
		${p.html }
		
		<table width="90%">
			<tr>
				<td width="20%" align="right">批注：</td>
				<td>
					<textarea rows="8" cols="64" style="" name="comment" id="comment"></textarea>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<input type="hidden" id="taskId" name="taskId" value="${pd.taskId }">
					<input type="hidden" id="id" name="id" value="${p.id }">
					<input type="hidden" id="bdid" name="bdid" value="${bdPd.id }">
					<!-- 使用连线的名称作为按钮 -->
			 		<c:forEach items="${outcomeList}" var="var" varStatus="status">
			 			<input type="submit" name="outcome" value="${var }" class="button_ok"/>
			 		</c:forEach>
				</td>
			</tr>
		</table>
	</form>
	
		<div class="row-fluid">
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>时间</th>
						<th>批注人</th>
						<th>批注信息</th>
					</tr>
				</thead>
				<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty commentList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${commentList}" var="var" varStatus="vs">
									
							<tr>
								<td><fmt:formatDate value="${var.time }" pattern="yyyy-MM-dd HH:mm:SS"/></td>
								<td>${var.userId }</td>
								<td>${var.fullMessage }</td>
							</tr>
						</c:forEach>
						</c:if>
						
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="3" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="3" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
				</td>
			</tr>
		</table>
		</div>
	</div>
	
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