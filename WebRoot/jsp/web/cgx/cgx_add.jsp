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
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
		
		<script src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/artDialog.source.js?skin=blue"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog/plugins/iframeTools.source.js"></script>
		
		
		<link rel='stylesheet prefetch' href='${pageContext.request.contextPath}/jsp/web/ui/css/form-builder.css'>
		<link rel='stylesheet prefetch' href='${pageContext.request.contextPath}/jsp/web/ui/css/form-render.min.css'>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/web/ui/css/style.css">
		<script src='${pageContext.request.contextPath}/jsp/web/ui/js/jquery-ui.min.js'></script>
		<script src='${pageContext.request.contextPath}/jsp/web/ui/js/form-builder.js'></script>
		<script src='${pageContext.request.contextPath}/jsp/web/ui/js/form-render.min.js'></script>
		<script src="${pageContext.request.contextPath}/jsp/web/ui/js/index.js"></script>
	
		<script type="text/javascript">
			!function(){
				laydate.skin('molv');//切换皮肤，请查看skins下面皮肤库
			}();

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
				var btnum = getByteLen(bt);
				
				if(btnum==0){
					art.dialog.alert("请输入标题。");
				}else{
					if($("#xml").val()==''){
						art.dialog.alert("您尚未设置调查表。");
						return;
					}
					//操作html
					var fbTemplate = document.getElementById('fb-template'),
				    formContainer = document.getElementById('rendered-form'),
				    formRenderOpts = {
				      container: $('form', formContainer)
				    };
			    	$(fbTemplate).formRender(formRenderOpts);
					$("#add_html").val($("#add_save_html").html());
					$("#add_my_form").html($("#add_save_html").html());
					
					var ids = [];
					var vals = [];
					$("label[for]").each(function(){
						if($(this).attr("for").indexOf('_')>0&&$(this).attr("for").indexOf('-')==-1){
							//alert($(this).html());
							//alert($(this).attr("for"));
							vals.push($(this).html());
							ids.push($(this).attr("for"));
						}
					})
					
					
					$.ajax({
                    type: "POST",
                    url: '${pageContext.request.contextPath}/cgx/save.do?ids='+ids.join(',')+'&vals='+vals.join(','),
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
<body style="background: #fff;">
	<form action="${pageContext.request.contextPath}/cgx/save.do" name="cdForm" id="cdForm" method="post" enctype="multipart/form-data">
		<div id="zhongxin">
		<table width="90%">
			<tr>
				<td width="20%" align="right">标题：</td><td><input type="text" name="bt" id="bt" style="width: 95%;height: 32px;" placeholder="这里输入标题" title="标题"/></td>
			</tr>
			<tr>
				<td width="20%" align="right">到期时间</td>
				<td>
					<input type="text" name="dqsj" id="dqsj" placeholder="请输入日期" style="width: 95%;height: 32px;" class="laydate-icon" onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				</td>
			</tr>
			<tr>
				<td width="20%" align="right">内容：</td>
				<td>
					<textarea rows="8" cols="8" name="bz" id="bz" style="width: 95%;height: 120px;"></textarea>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">取消</a>
				</td>
			</tr>
		</table>
		<input type="hidden" id="xml" name="code" value="">
		<input type="hidden" id="add_html" name="html" value="">
		<div style="display: none;" id="add_my_form">
		</div>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="${pageContext.request.contextPath}/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
	<hr style="border: 1px dashed #CCCCCC;"/>
	<div id="edit-form">
		<textarea id="fb-template">
		</textarea>
	</div>
	<div id="rendered-form">
		<form action="#" id="add_save_html"></form>
		<button class="btn btn-default edit-form">
			编辑
		</button>
	</div>
		
</body>
</html>