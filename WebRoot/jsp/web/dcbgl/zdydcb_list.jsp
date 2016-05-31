<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!-- jsp文件头和头部 -->
<%@ include file="../../system/admin/top.jsp"%>   

<body>
	<div class="row-fluid">
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>序号</th>
						<c:set var="v" value="${p.text}"></c:set>
						<c:forEach items="${v}" var="var">
							<th>${var }</th>
						</c:forEach>
						<th>状态</th>
						<th>提交人</th>
						<th>提交时间</th>
					</tr>
				</thead>					
				<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty list}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${list}" var="var" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<c:set var="v" value="${p.field}"></c:set>
								<c:forEach items="${v}" var="v1">
									<th>${var[v1] }</th>
								</c:forEach>
								<td style="width:60px;" class="center">
									<c:if test="${var.status=='N'}">未提交</c:if>
									<c:if test="${var.status=='Y'}">已提交</c:if>
								</td>
								<td>${var.yhmc }</td>
								<td>${var.tjsj}</td>
							</tr>
						</c:forEach>
						</c:if>
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="${fn:length(list)+5}" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="${fn:length(list)+5}" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
	</div>
	
	
	<script src="${pageContext.request.contextPath}/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">window.jQuery || document.write("<script src='${pageContext.request.contextPath}/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/ace-elements.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/ace.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootbox.min.js"></script>
	
	<script type="text/javascript">	
		function saveSuccess(){
		 	location.href = location.href;
		}
		
		</script>
	</body>
</html>

