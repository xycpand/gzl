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
				<!-- basic styles -->
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
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/artDialog.source.js?skin=blue"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/plugins/iframeTools.source.js"></script>
		
	
		<script type="text/javascript">
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
				var bt = $("#bt").val();
				var nr = $("#nr").val();
				var btnum = getByteLen(bt);
				var nrnum = getByteLen(nr);
				
				if(btnum==0){
					art.dialog.alert("请输入标题，用于展示。");
				}else if(nrnum==0){
					art.dialog.alert("请输入内容，用于关注回复内容。");
				}else{
					$.ajax({
                    type: "POST",
                    url: 'gzhf/save.do',
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
		</script>
		
	</head>
<body>
	<form action="${pageContext.request.contextPath}/zdycd/save.do" name="cdForm" id="cdForm" method="post" enctype="multipart/form-data">
		<div id="zhongxin">
		<table width="90%">
			<tr>
				<td width="20%" align="right">标题：</td><td><input type="text" name="bt" id="bt" style="width: 95%" placeholder="这里输入标题" title="标题"/></td>
			</tr>
			<tr>
				<td width="20%" align="right">内容：</td>
				<td>
					<textarea rows="8" cols="8" name="nr" id="nr"></textarea>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="${pageContext.request.contextPath}/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
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
		
		
		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
	    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js"> </script>
	    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript">
			//实例化编辑器
		    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		    UE.getEditor('editor');
		</script>
		
		
		<script type="text/javascript">
		$(window.parent.hangge());
		//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
		</script>
</body>
</html>