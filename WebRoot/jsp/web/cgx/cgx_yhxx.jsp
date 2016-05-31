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
<link type="text/css" rel="stylesheet" href="css/main.css"/>
<link type="text/css" rel="stylesheet" href="js/zTree/zTreeStyle.css"/>
<!--<link rel="stylesheet" href="css/ace.min.css" />-->
<link rel="stylesheet" href="css/ace-responsive.min.css" />
<link rel="stylesheet" href="css/ace-skins.min.css" />
<style type="text/css">
footer{height:50px;position:fixed;bottom:0px;left:0px;width:100%;text-align: center;}
</style>

</head>
<body>
	<div id="zhongxin">
		<ul id="tree" class="tree" style="overflow:auto;"></ul>
	</div>
	<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
	
	
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/zTree/jquery.ztree-2.6.min.js"></script>
	
	<script type="text/javascript">
	var zTree;
	$(document).ready(function(){
			
			var setting = {
			    showLine: true,
			    checkable: true
			};
			var zn = '${zTreeNodes}';
			var zTreeNodes = eval(zn);
			zTree = $("#tree").zTree(setting, zTreeNodes);
		});
	</script>
	<script type="text/javascript">
	
		 function save(){
			   
				var nodes = zTree.getCheckedNodes();
				var tmpNode;
				var ids = "";
				for(var i=0; i<nodes.length; i++){
					tmpNode = nodes[i];
					if(i!=nodes.length-1){
						ids += tmpNode.id+",";
					}else{
						ids += tmpNode.id;
					}
				}
				
				var id = "${pd.id}";
				var url = "${pageContext.request.contextPath}/cgx/scdcb.do";
				var postData = {"id":id,"yhids":ids};
				
				$("#zhongxin").hide();
				$("#zhongxin2").show();
				$.post(url,postData,function(data){
					art.dialog.open.api.close();
					art.dialog.opener.saveSuccess();
				});
			 
		 }
	
	</script>
	
	<script type="text/javascript" src="<%=path %>/js/artDialog/artDialog.source.js?skin=blue"></script>
<script type="text/javascript" src="<%=path %>/js/artDialog/plugins/iframeTools.source.js"></script>
<script>
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
		
	<footer>
	<div style="width: 100%;" class="center">
<!--		<a class="btn btn-mini btn-primary" onclick="save();">保存</a>-->
<!--		<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>-->
	</div>
	</footer>
</body>
</html>