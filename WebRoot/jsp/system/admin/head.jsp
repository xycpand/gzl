<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String pathh = request.getContextPath();
	String basePathh = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathh+"/";
%>
<div class="navbar navbar-inverse">
		  <div class="navbar-inner">
		   <div class="container-fluid">
			  <a class="brand" href="login_index.do"><small><i class="icon-leaf"></i>自定义表单+Activiti工作流开发</small> </a>
			  <ul class="nav ace-nav pull-right">
					<li class="grey">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<i class="icon-tasks"></i>
							<span class="badge">0</span>
						</a>
<!--						<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-closer">-->
<!--							<li class="nav-header">-->
<!--								<i class="icon-ok"></i> 4 任务完成-->
<!--							</li>-->
<!--							-->
<!--							<li>-->
<!--								<a>-->
<!--									<div class="clearfix">-->
<!--										<span class="pull-left">软件更新</span>-->
<!--										<span class="pull-right">65%</span>-->
<!--									</div>-->
<!--									<div class="progress progress-mini"><div class="bar" style="width:65%"></div></div>-->
<!--								</a>-->
<!--							</li>-->
<!--							-->
<!--							<li>-->
<!--								<a>-->
<!--									<div class="clearfix">-->
<!--										<span class="pull-left">软件更新</span>-->
<!--										<span class="pull-right">35%</span>-->
<!--									</div>-->
<!--									<div class="progress progress-mini progress-danger"><div class="bar" style="width:35%"></div></div>-->
<!--								</a>-->
<!--							</li>-->
<!--							-->
<!--							<li>-->
<!--								<a>-->
<!--									<div class="clearfix">-->
<!--										<span class="pull-left">运行测试</span>-->
<!--										<span class="pull-right">15%</span>-->
<!--									</div>-->
<!--									<div class="progress progress-mini progress-warning"><div class="bar" style="width:15%"></div></div>-->
<!--								</a>-->
<!--							</li>-->
<!--							-->
<!--							<li>-->
<!--								<a>-->
<!--									<div class="clearfix">-->
<!--										<span class="pull-left">Bug 修复</span>-->
<!--										<span class="pull-right">90%</span>-->
<!--									</div>-->
<!--									<div class="progress progress-mini progress-success progress-striped active"><div class="bar" style="width:90%"></div></div>-->
<!--								</a>-->
<!--							</li>-->
<!--							-->
<!--							<li>-->
<!--								<a>-->
<!--									查看任务明细-->
<!--									<i class="icon-arrow-right"></i>-->
<!--								</a>-->
<!--							</li>-->
<!--						</ul>-->
					</li>
					<li class="purple">
						<a class="dropdown-toggle" data-toggle="dropdown">
							<i class="icon-bell-alt icon-animated-bell icon-only"></i>
							<span class="badge badge-important">0</span>
						</a>
<!--						<ul class="pull-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-closer">-->
<!--							<li class="nav-header">-->
<!--								<i class="icon-warning-sign"></i> 通知计划-->
<!--							</li>-->
							
<!--							<li>-->
<!--								<a>-->
<!--									<div class="clearfix">-->
<!--										<span class="pull-left"><i class="icon-comment btn btn-mini btn-pink"></i> 新消息</span>-->
<!--										<span class="pull-right badge badge-info">+12</span>-->
<!--									</div>-->
<!--								</a>-->
<!--							</li>-->
<!--							-->
<!--							<li>-->
<!--								<a>-->
<!--									<i class="icon-user btn btn-mini btn-primary"></i> 测试消息-->
<!--								</a>-->
<!--							</li>-->
<!--							-->
<!--							<li>-->
<!--								<a>-->
<!--									<div class="clearfix">-->
<!--										<span class="pull-left"><i class="icon-shopping-cart btn btn-mini btn-success"></i> 新订单</span>-->
<!--										<span class="pull-right badge badge-success">+8</span>-->
<!--									</div>-->
<!--								</a>-->
<!--							</li>-->
<!--							-->
<!--							<li>-->
<!--								<a>-->
<!--									<div class="clearfix">-->
<!--										<span class="pull-left"><i class="icon-twitter btn btn-mini btn-info"></i> 等待消息</span>-->
<!--										<span class="pull-right badge badge-info">+4</span>-->
<!--									</div>-->
<!--								</a>-->
<!--							</li>-->
<!--																-->
<!--							<li>-->
<!--								<a>-->
<!--									查看所有消息-->
<!--									<i class="icon-arrow-right"></i>-->
<!--								</a>-->
<!--							</li>-->
<!--						</ul>-->
					</li>
					<li class="green">
						<a class="dropdown-toggle" data-toggle="dropdown">
							<i class="icon-envelope-alt icon-animated-vertical icon-only"></i>
							<span class="badge badge-success">0</span>
						</a>
<!--						<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-closer">-->
<!--							<li class="nav-header">-->
<!--								<i class="icon-envelope"></i> 5 条信件-->
<!--							</li>-->
							
<!--							<li>-->
<!--								<a>-->
<!--									<img alt="Alex's Avatar" class="msg-photo" src="avatars/avatar.png" />-->
<!--									<span class="msg-body">-->
<!--										<span class="msg-title">-->
<!--											<span class="blue">张三:</span>-->
<!--											你好，我们在哪里吃饭? ...-->
<!--										</span>-->
<!--										<span class="msg-time">-->
<!--											<i class="icon-time"></i> <span>1个月以前</span>-->
<!--										</span>-->
<!--									</span>-->
<!--								</a>-->
<!--							</li>-->
<!--							-->
<!--							<li>-->
<!--								<a>-->
<!--									<img alt="Susan's Avatar" class="msg-photo" src="avatars/avatar3.png" />-->
<!--									<span class="msg-body">-->
<!--										<span class="msg-title">-->
<!--											<span class="blue">李四:</span>-->
<!--											你在哪上班? ...-->
<!--										</span>-->
<!--										<span class="msg-time">-->
<!--											<i class="icon-time"></i> <span>20分钟前</span>-->
<!--										</span>-->
<!--									</span>-->
<!--								</a>-->
<!--							</li>-->
<!--							-->
<!--							<li>-->
<!--								<a>-->
<!--									<img alt="Bob's Avatar" class="msg-photo" src="avatars/avatar4.png" />-->
<!--									<span class="msg-body">-->
<!--										<span class="msg-title">-->
<!--											<span class="blue">王五:</span>-->
<!--											你好，我对你很感兴趣 ...-->
<!--										</span>-->
<!--										<span class="msg-time">-->
<!--											<i class="icon-time"></i> <span>下午 3:15 </span>-->
<!--										</span>-->
<!--									</span>-->
<!--								</a>-->
<!--							</li>-->
							
<!--							<li>-->
<!--								<a>-->
<!--									查看所有信件-->
<!--									<i class="icon-arrow-right"></i>-->
<!--								</a>-->
<!--							</li>									-->
<!--	-->
<!--						</ul>-->
					</li>
					<li class="light-blue user-profile">
						<a class="user-menu dropdown-toggle" data-toggle="dropdown">
							<img alt="FH" src="${pageContext.request.contextPath}/avatars/user.jpg" class="nav-user-photo" />
							<span id="user_info">
								
							</span>
							<i class="icon-caret-down"></i>
						</a>
						<ul id="user_menu" class="pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
							<li><a onclick="editUserH();" style="cursor:pointer;"><i class="icon-cog"></i> 修改资料</a></li>
<!--							<li><a><i class="icon-user"></i> 设置2</a></li>-->
							<li class="divider"></li>
							<li><a href="${pageContext.request.contextPath}/logout.do"><i class="icon-off"></i> 退出</a></li>
						</ul>
					</li>
			  </ul><!--/.ace-nav-->
		   </div><!--/.container-fluid-->
		  </div><!--/.navbar-inner-->
		</div><!--/.navbar-->
		
		<script type="text/javascript">
			
			var USER_ID;
		
			function getUname(){
				$.ajax({
					type: "POST",
					url: '${pageContext.request.contextPath}/head/getUname.do?tm='+new Date().getTime(),
			    	data: encodeURI(""),
					dataType:'json',
					//beforeSend: validateData,
					cache: false,
					success: function(data){
						//alert(data.list.length);
						 $.each(data.list, function(i, list){
							 
							 //登陆者资料
							 $("#user_info").html('<small>Welcome</small> '+list.NAME+'');
							 
							 //用户ID
							 USER_ID = list.USER_ID;
							 
						 });
					}
				});
			}
			getUname();
			
			
			function editUserH(){
				art.dialog.open('${pageContext.request.contextPath}/user/goMyEditU.do?USER_ID='+USER_ID+'&fx=head',{
					title:'资料',
					width:300,
      				height:320,
      				lock: true
				});//打开子窗体
			}
			
			
			//修改
			function editUserH1(){
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="资料";
				 diag.URL = '${pageContext.request.contextPath}/user/goMyEditU.do?USER_ID='+USER_ID+'&fx=head';
				 diag.Width = 300;
				 diag.Height = 320;
				 diag.CancelEvent = function(){ //关闭事件
					diag.close();
					//setTimeout("self.location.reload()",100);
				 };
				 diag.show();
			}
		</script>
			
<script type="text/javascript">
		
		
		//数据字典
		function zidian1(){
			
			 var diag = new Dialog();
			// diag.Drag=true;
			 diag.Modal = true;
			 diag.Title ="数据典字";
			 diag.URL = '${pageContext.request.contextPath}/zidian.do?PARENT_ID=0';
			 diag.Width = 700;
			 diag.Height = 400;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				//setTimeout("self.location.reload()",100);
			 };
			 diag.show();
			 
		}
		
		
		function menu(){
			art.dialog.open('${pageContext.request.contextPath}/menu.do',{
					title:'菜单编辑',
					width:720,
      				height:390,
      				lock: true
				});//打开子窗体
			}
			
		function zidian(){
				art.dialog.open('${pageContext.request.contextPath}/zidian.do?PARENT_ID=0',{
					title:'数据典字',
					width:720,
      				height:390,
      				lock: true
				});//打开子窗体
			}
		//菜单
		function menu1(){
			
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="菜单编辑";
			 diag.URL = '${pageContext.request.contextPath}/menu.do';
			 diag.Width = 720;
			 diag.Height = 390;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				//setTimeout("self.location.reload()",100);
			 };
			 diag.show();
			 
		}
		
		function hasmenu(){
			$.ajax({
				type: "POST",
				url: '${pageContext.request.contextPath}/head/getUname.do?tm='+new Date().getTime(),
		    	data: encodeURI(""),
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					//alert(data.list.length);
					 $.each(data.list, function(i, list){
						 
						 if(list.USERNAME != 'admin'){
							 $("#adminmenu").hide();
							 $("#adminzidian").hide();
						 }
						 
					 });
				}
			});
		}
		hasmenu();
		
</script>		
		
