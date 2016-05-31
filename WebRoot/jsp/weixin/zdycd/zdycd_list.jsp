<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- jsp文件头和头部 -->
<%@ include file="../../system/admin/top.jsp"%>   

<style type="text/css">
<!--
.menu_titile{
      width: 281px;
      height:30px;
      line-height: 30px;
      font: "宋体" 18px bold;
      background-color:#fb0a0a;
      color:#fff;
      border-bottom: #fff solid 1px;
}
.menu_titile:hover,.menu_titile:active,.menu_titile:visited{
      width: 281px;
      height:30px;
      line-height: 30px;
      font: "宋体" 18px bold;
      background-color:#E02222;
      color:#fff;
}


.side_menu{
      width:256px;
      min-height: 28px;
      background-color:#fff;
      text-align: left;
      list-style: none;
      text-decoration: none;
      margin-bottom:0;
}  
.side_menu > li a{
      color:#000;
      cursor: pointer;
      text-decoration: none;
} 
.side_menu > li,.side_menu > li:link{
      position:relactive;
      cursor:pointer;
      width: 256px;
      height:28px;
      line-height: 28px;
      background-color: #fff;
}
.menu_li_hover,.menu_li_hover:link,.menu_li_hover:hover,.menu_li_hover:active,.menu_li_hover:visited{
      color:#000;
      position:relactive;
      cursor:pointer;
      width: 256px;
      height:28px;
      line-height: 28px;
      background-color: #f4f4f4;
      
}
.side_menu > li:hover,.side_menu > li:active,.side_menu > li:visited{
      cursor:pointer;
      background-color: #f4f4f4;
      padding:0;
      margin:0;
}
.side_menu > li:hover a.dot{
      position: absolute;
      top:50%;
      left: 25px;
}

.menuback{
	background-color: #E02222;
}
-->
</style>

<body>
		
<!-- 页面顶部¨ -->
<%@ include file="../../system/admin/head.jsp"%> 
		
<div class="container-fluid" id="main-container">

<a href="#" id="menu-toggler"><span></span></a><!-- menu toggler -->

<!-- 左侧菜单 -->
<%@ include file="../../system/admin/left.jsp"%> 
		
<div id="main-content" class="clearfix">

<div id="breadcrumbs">

<ul class="breadcrumb">
	<li><i class="icon-home"></i> <a>系统管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>
	<li class="active">系统用户</li>
</ul><!--.breadcrumb-->

<div id="nav-search">
</div><!--#nav-search-->

</div><!--#breadcrumbs-->



<div id="page-content" class="clearfix">
  <div class="row-fluid">


<div class="row-fluid" style="float: left;width: 29%;margin: 0.1%;margin-top: 0%;">
	 <div class="widget-box">
	  <div class="widget-header">
		<h4 class="smaller">菜单管理</h4>
		<div class="widget-toolbar">
			<a onclick="add('yjcd','1','${pd.yjcdSize}')" data-action="collapse" title="添加一级菜单" style="cursor: pointer;"><i class="icon-pencil"></i>添加</a>
			<a onclick="px('1');" data-action="close" style="cursor: pointer;"><i class="icon-only icon-align-justify"></i>排序</a>
			<a onclick="sameMenu();" data-action="close" style="cursor: pointer;"><i class="icon-ok"></i>发布</a>
		</div>
	  </div>
	  <div class="widget-body">
	   <div class="widget-main" style="min-height: 420px;">
	   		<div style="padding: 0px;margin: 0px;">
	   			<c:choose>
					<c:when test="${not empty yjcdLs}">
		   				<c:forEach items="${yjcdLs}" var="yjcdPd">
			   					<div id="yjcd_${yjcdPd.id }" class="menu_titile" style="color:#fff; font: 12px '宋体' bold; line-height: 30px;">
					   				<a onclick="showZdycd('yjcd','${yjcdPd.id }','${yjcdPd.cdmc }','${yjcdPd.ejcdSize }','${yjcdPd.lx }','${yjcdPd.twlx }','${yjcdPd.url }')" style="cursor: pointer;">
						   				<div style="float: left;margin-left: 12px;">${yjcdPd.cdmc }</div>
						   				<div style="float: right;margin-right: 12px;">
						   					<a onclick="add('ejcd','${yjcdPd.id }','${yjcdPd.ejcdSize }')" title="添加二级菜单" style="cursor: pointer;"><i class="icon-pencil"></i>添加</a>
						   					<a onclick="px('${yjcdPd.id }');" data-action="close" style="cursor: pointer;"><i class="icon-only icon-align-justify"></i>排序</a>
						   				</div>
					   				</a>
					   			</div>
				   			<c:choose>
								<c:when test="${not empty yjcdPd.ercdLs}">
				   					<ul class="side_menu">
				   						<c:forEach items="${yjcdPd.ercdLs}" var="ejcdPd">
				   							<li id="ejcd_${ejcdPd.id }" class=""><a onclick="showZdycd('ejcd','${ejcdPd.id }','${ejcdPd.cdmc }','0','${ejcdPd.lx }','${ejcdPd.twlx }','${ejcdPd.url }')" style="cursor: pointer;"><i class="dot">。</i>${ejcdPd.cdmc }</a></li>
				   						</c:forEach>
							   		</ul>
							   	</c:when>
			   				</c:choose>
		   				</c:forEach>
		   			</c:when>
		   		</c:choose>
	   		
	   		
	   		
<!--	   			<div id="menuback" class="menu_titile" style="color:#fff; font: 12px '宋体' bold; line-height: 30px;">-->
<!--	   				<div style="float: left;margin-left: 12px;">第一页</div>-->
<!--	   				<div style="float: right;margin-right: 12px;"><a title="添加二级菜单" style="cursor: pointer;"><i class="icon-pencil"></i>添加</a></div>-->
<!--	   			</div>-->
<!--		   		<ul class="side_menu">-->
<!--		   			<li id="back"><i class="dot">。</i>第一个菜单</li>-->
<!--		   			<li><a><i class="dot">。</i>第二个菜单e</a></li>-->
<!--		   			<li><a><i class="dot">。</i>第三个菜单</a></li>-->
<!--		   			<li><a><i class="dot">。</i>第四个菜单</a></li>-->
<!--		   			<li><a><i class="dot">。</i>第五个菜单</a></li>-->
<!--		   		</ul>-->
<!--		   		<div class="menu_titile" style="color:#fff; font: 12px '宋体' bold; line-height: 30px;">-->
<!--	   				<div style="float: left;margin-left: 12px;">第一页</div>-->
<!--	   				<div style="float: right;margin-right: 12px;"><a title="添加二级菜单" style="cursor: pointer;">添加</a></div>-->
<!--	   			</div>-->
<!--		   		<ul class="side_menu">-->
<!--		   			<li id="back"><i class="dot">。</i>第一个菜单3</li>-->
<!--		   			<li><a><i class="dot">。</i>第二个菜单e</a></li>-->
<!--		   			<li><a><i class="dot">。</i>第三个菜单</a></li>-->
<!--		   			<li><a><i class="dot">。</i>第四个菜单</a></li>-->
<!--		   			<li><a><i class="dot">。</i>第五个菜单</a></li>-->
<!--		   		</ul>-->
<!--		   		<div class="menu_titile" style="color:#fff; font: 12px '宋体' bold; line-height: 30px;">&nbsp;&nbsp;收菜的稍等</div>-->
<!--		   		<ul class="side_menu">-->
<!--		   			<li id="back"><i class="dot">。</i>第一个菜单3</li>-->
<!--		   			<li><a><i class="dot">。</i>第二个菜单e</a></li>-->
<!--		   			<li><a><i class="dot">。</i>第三个菜单</a></li>-->
<!--		   			<li><a><i class="dot">。</i>第四个菜单</a></li>-->
<!--		   			<li><a><i class="dot">。</i>第五个菜单</a></li>-->
<!--		   		</ul>-->


	   		</div>
	   </div>
	  </div>
	 </div>
</div>

	<div class="row-fluid" style="float: left;width: 70%;">
	 <div class="widget-box">
	  <div class="widget-header">
		<h4 class="smaller" id="cdmc">操作</h4>
		<div class="widget-toolbar" id="cdcz">
			
		</div>
	  </div>
	  <div class="widget-body">
	   <div class="widget-main" style="min-height: 420px;" align="center" >
	   		<div id="cdcznr" style="margin-top: 180px;font-size: 18px;"></div>
	   </div>
	  </div>
	 </div>
</div>
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!-- #main-content -->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script src="${pageContext.request.contextPath}/1.9.1/jquery.min.js"></script>
		<script type="text/javascript">window.jQuery || document.write("<script src='${pageContext.request.contextPath}/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/ace-elements.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/ace.min.js"></script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		<!--引入弹窗组件start-->
<!--		<script type="text/javascript" src="js/attention/zDialog/zDrag.js"></script>-->
<!--		<script type="text/javascript" src="js/attention/zDialog/zDialog.js"></script>-->
		<!--引入弹窗组件end-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/artDialog.source.js?skin=blue"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/plugins/iframeTools.source.js"></script>
		
		
		<script type="text/javascript">
			function saveSuccess(){
				var num = '${page.currentPage}';
			 	if(num == '0'){
			 		location.href = location.href;
			 	}else{
			 		nextPage(${page.currentPage});
			 	}
			}
	
	
		//检索
		function search(){
			$("#userForm").submit();
		}
		
		//新增
		function add(lx,pid,size){
			if(lx=='yjcd'){
				if(size>=3){
					art.dialog.alert("最多只能添加三个一级菜单，当前已达设置上限。");
				}else{
					art.dialog.open('${pageContext.request.contextPath}/zdycd/toadd.do?pid='+pid+'&cdjb='+lx,{
				   	 	title:'新增',
						width:420,
			    		height:120,
			    		lock: true
					});//打开子窗体
				}
			}else if(lx=='ejcd'){
				if(size>=5){
					art.dialog.alert("同一个一级菜单下最多只能添加五个二级菜单，当前已达设置上限。");
				}else{
					art.dialog.open('${pageContext.request.contextPath}/zdycd/toadd.do?pid='+pid+'&cdjb='+lx,{
				   	 	title:'新增',
						width:420,
			    		height:120,
			    		lock: true
					});//打开子窗体
				}
			}
		}
		
		
		
		function showZdycd(lx,id,cdmc,size,cdlx,twlx,url){
			if(lx=='yjcd'){
				$("div[id^='yjcd_']").each(function(){
	    			$(this).removeClass("menuback");
				})
	    		$("div[id='yjcd_"+id+"']").addClass("menuback");
	    		
	    		$("#cdmc").html("一级菜单："+cdmc);
	    		var tt='<a onclick="edit('+id+');" data-action="collapse" style="cursor: pointer;"><i class="icon-edit"></i>编辑</a><a onclick="del('+id+','+size+');" data-action="collapse" style="cursor: pointer;"><i class="icon-remove"></i>删除</a>';
	    		$("#cdcz").html(tt);
	    		
    			if(size>0&&size<5){
	    			$("#cdcznr").html("已经为["+cdmc+"]添加了二级菜单，无法设置其他操作，你还可以添加["+(5-size)+"]个二级菜单。");
	    		}else if(size==5){
	    			$("#cdcznr").html("你已添加满5个二级菜单");
	    		}else if(size==0){
	    			if(cdlx==''){
		    			var tt = '<a onclick="saveMessage('+id+',\'fstw\');" data-action="collapse" style="cursor: pointer;"><i class="icon-edit"></i>发送图文</a>&nbsp;&nbsp;';
	    				tt+='<a onclick="saveMessage('+id+',\'tzlj\');" data-action="collapse" style="cursor: pointer;"><i class="icon-edit"></i>跳转链接</a>';
	    				$("#cdcznr").html(tt);	
		    		}else if(cdlx=='fstw'){
		    			var tt = "图文类型："+twlx+'<br />';
		    			
		    			tt+='重新设置：<a onclick="saveMessage('+id+',\'fstw\');" data-action="collapse" style="cursor: pointer;"><i class="icon-edit"></i>发送图文</a>&nbsp;&nbsp;';
	    				tt+='<a onclick="saveMessage('+id+',\'tzlj\');" data-action="collapse" style="cursor: pointer;"><i class="icon-edit"></i>跳转链接</a>';
	    				
		    			$("#cdcznr").html(tt);
		    		}else if(cdlx=='tzlj'){
		    			var tt = "跳转链接："+url+'<br />';
		    			
		    			tt+= '重新设置：<a onclick="saveMessage('+id+',\'fstw\');" data-action="collapse" style="cursor: pointer;"><i class="icon-edit"></i>发送图文</a>&nbsp;&nbsp;';
	    				tt+='<a onclick="saveMessage('+id+',\'tzlj\');" data-action="collapse" style="cursor: pointer;"><i class="icon-edit"></i>跳转链接</a>';
	    			
		    			$("#cdcznr").html(tt);
		    		}
	    		}
		    	
			}else if(lx=='ejcd'){
				$("li[id^='ejcd_']").each(function(){
	    			$(this).css('background-color','#fff');
				})
	    		$("li[id='ejcd_"+id+"']").css('background-color','#f4f4f4'); 
	    		
	    		$("#cdmc").html("二级菜单："+cdmc);
				var tt='<a onclick="edit('+id+');" data-action="collapse" style="cursor: pointer;"><i class="icon-edit"></i>编辑</a><a onclick="del('+id+','+size+');" data-action="collapse" style="cursor: pointer;"><i class="icon-remove"></i>删除</a>';
				$("#cdcz").html(tt);
				
	    		if(cdlx==''){
	    			var tt = '<a onclick="saveMessage('+id+',\'fstw\');" data-action="collapse" style="cursor: pointer;"><i class="icon-edit"></i>发送图文</a>&nbsp;&nbsp;';
	    			tt+='<a onclick="saveMessage('+id+',\'tzlj\');" data-action="collapse" style="cursor: pointer;"><i class="icon-edit"></i>跳转链接</a>';
	    			$("#cdcznr").html(tt);
	    		}else if(cdlx=='fstw'){
	    			var tt = '图文类型：'+twlx+'<br />';
	    			
	    			tt+= '重新设置：<a onclick="saveMessage('+id+',\'fstw\');" data-action="collapse" style="cursor: pointer;"><i class="icon-edit"></i>发送图文</a>&nbsp;&nbsp;';
	    			tt+='<a onclick="saveMessage('+id+',\'tzlj\');" data-action="collapse" style="cursor: pointer;"><i class="icon-edit"></i>跳转链接</a>';
	    			
	    			$("#cdcznr").html(tt);
	    		}else if(cdlx=='tzlj'){
	    			var tt = '跳转链接：'+url+'<br />';
	    			
	    			tt+= '重新设置：<a onclick="saveMessage('+id+',\'fstw\');" data-action="collapse" style="cursor: pointer;"><i class="icon-edit"></i>发送图文</a>&nbsp;&nbsp;';
	    			tt+='<a onclick="saveMessage('+id+',\'tzlj\');" data-action="collapse" style="cursor: pointer;"><i class="icon-edit"></i>跳转链接</a>';
	    			
	    			$("#cdcznr").html(tt);
	    		}
			}
		}
		
		
		
		
		function edit(id){
			art.dialog.open('${pageContext.request.contextPath}/zdycd/toedit.do?id='+id,{
		   	 	title:'编辑',
				width:420,
	    		height:120,
	    		lock: true
			});//打开子窗体
		}
		
		
		//删除
		function del(id,size){
			if(size>0){
				art.dialog.alert("请先删除下级菜单，再进行该项的操作。");
			}else{
				bootbox.confirm("确定要删除该记录?", function(result) {
					if(result) {
						var url = "${pageContext.request.contextPath}/zdycd/remove.do?id="+id;
						$.get(url,function(data){
							if(data=="success"){
								document.location.reload();
							}
						});
					}
				});
			}
		}
		
		
		function saveMessage(id,val){
			art.dialog.open('${pageContext.request.contextPath}/zdycd/tomessage.do?lx='+val+'&id='+id,{
		   	 	title:'设置菜单事件',
				width:420,
	    		height:120,
	    		lock: true
			});//打开子窗体
		}
		
		
		
		function px(pid){
			art.dialog.open('${pageContext.request.contextPath}/zdycd/toPx.do?pid='+pid,{
		   	 	title:'设置菜单事件',
				width:420,
	    		height:320,
	    		lock: true,
	    		button: [{
    		    	name: '关闭',
    		        callback: function () {
    		        	location.reload();
    		        },
    		        focus: true
    		    }]
			});//打开子窗体
		}
		
		
		function sameMenu(){
			art.dialog({
				id:'jdt',
				title:'正在同步自定义菜单。。。',
				lock: true
			});
			$.ajax({
                 type: "POST",
                 url: '${pageContext.request.contextPath}/zdycd/sameMenu.do',
                 data: '',
                 success: function (result) {
                 	art.dialog({id:'jdt'}).close();
                 	if(result=='sucess'){
                 		art.dialog.alert("自定义菜单同步成功。");
                 	}else if(result='error'){
                 		art.dialog.alert("自定义菜单与微信同步失败，请联系管理员。");
                 	}
                 },
                 	error: function(data) {
                  	}
             	});
			}
		</script>
		
		<script type="text/javascript">
		
		$(function() {
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		
		</script>
		
		
	</body>
</html>

