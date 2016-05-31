<%
	String pathl = request.getContextPath();
	String basePathl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathl+"/";
%>
<%@ page language="java" pageEncoding="UTF-8"%>
		<div id="sidebar">

				<div id="sidebar-shortcuts">

					<div id="sidebar-shortcuts-large">

<!--						<button class="btn btn-small btn-success" onclick="alert('1');"><i class="icon-signal"></i></button>-->

<!--						<button class="btn btn-small btn-info" onclick="alert('2');"><i class="icon-pencil"></i></button>-->

						<button class="btn btn-small btn-warning" title="数据字典" id="adminzidian" onclick="zidian();"><i class="icon-book"></i></button>
						<button class="btn btn-small btn-danger" title="菜单管理" id="adminmenu" onclick="menu();"><i class="icon-cogs"></i></button>
					</div>

					<div id="sidebar-shortcuts-mini">

						<span class="btn btn-success"></span>

						<span class="btn btn-info"></span>

						<span class="btn btn-warning"></span>

						<span class="btn btn-danger"></span>

					</div>

				</div><!-- #sidebar-shortcuts -->


				<ul class="nav nav-list">

					<c:if test="${pdm.isM1 != 'index' }"><li></c:if>
					<c:if test="${pdm.isM1 == 'index' }"><li class="active"></c:if>
					  <a href="${pageContext.request.contextPath}/login_index.do?&isM1=index"><i class="icon-dashboard"></i><span>后台首页</span></a>
					</li>



			<c:forEach items="${menuList}" var="menu">
				<c:if test="${menu.hasMenu}">
				<c:if test="${pdm.isM1 != menu.MENU_ID }"><li></c:if>
				<c:if test="${pdm.isM1 == menu.MENU_ID }"><li class="active open"></c:if>
					  <a href="#" class="dropdown-toggle" >
						<i class="${menu.MENU_ICON == null ? 'icon-desktop' : menu.MENU_ICON}"></i>
						<span>${menu.MENU_NAME }</span>
						<b class="arrow icon-angle-down"></b>
					  </a>
					  <ul class="submenu">
				
					<c:forEach items="${menu.subMenu}" var="sub">
						<c:if test="${sub.hasMenu}">
						<c:choose>
							<c:when test="${not empty sub.MENU_URL}">
							<c:if test="${pdm.isM2 != sub.MENU_ID }"><li></c:if>
							<c:if test="${pdm.isM2 == sub.MENU_ID }"><li class="active"></c:if>
							<a href="${pageContext.request.contextPath}/${sub.MENU_URL }&isM1=${menu.MENU_ID }&isM2=${sub.MENU_ID }"><i class="icon-double-angle-right"></i>${sub.MENU_NAME }</a></li>
							</c:when>
							<c:otherwise>
							<li><a href="javascript:void(0);"><i class="icon-double-angle-right"></i>${sub.MENU_NAME }</a></li>
							</c:otherwise>
						</c:choose>
						</c:if>
					</c:forEach>
				
				  </ul>
				</li>
				</c:if>
			</c:forEach>





					

				</ul><!--/.nav-list-->



				<div id="sidebar-collapse"><i class="icon-double-angle-left"></i></div>





			</div><!--/#sidebar-->

