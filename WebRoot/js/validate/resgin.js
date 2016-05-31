// JavaScript Document

 $.validator.setDefaults({
			            invalidHandler: function(form, validator) {
			                $.each(validator.invalid,
			                function(key, value) {
			                    tmpkey = key;
			                    tmpval = value;
			                    validator.invalid = {};
			                    validator.invalid[tmpkey] = value;
			                    myTsk(value);
			                    return false;
			                });
			            },
			            errorPlacement: function(error, element) {},
			            onkeyup: false,
			            onfocusout: false,
			            focusInvalid: true
			        });


/*-------注册验证-----------*/
$().ready(function() {
	
	 $("#cdForm").validate({
		 /*errorPlacement: function(error, element) {
			if(element.is(":text")){
				error.appendTo( element.next().next() );
			}else if(element.is(":checkbox")){
				error.appendTo( element.next().next() );
			}else{
				error.appendTo( element.next() );
			}
		        
		},*/
		rules: {
		 dlmc:{
				 required: true,
				 remote:{
				    url: "findUser.html",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式   
				    data: {                     //要传递的数据
				        dlmc: function() {
				            return $("#dlmc").val();
				        }
				    }
				}
	 		},
/*		sjh:{
				required: true,
				rangelength:[11,11],
				digits: "只能输入整数",
				remote:{
				    url: "findPhone.html",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式   
				    data: {                     //要传递的数据
				        sjh: function() {
				            return $("#sjh").val();
				        }
				    }
				}
		},*/
		yzm:{
			required: true,
			rangelength:[0,6],
			digits: "只能输入整数",
			remote:{
			    url: "validateYzm.html",     //后台处理程序
			    type: "post",               //数据发送方式
			    dataType: "json",           //接受数据格式   
			    data: {                     //要传递的数据
			        sjh: function() {
			            return $("#yzm").val();
			        }
			    }
			}
	},
			dlmm: {
				required: true,
				rangelength:[6,16]
				},
			confirm_password: {
				required: true,
				equalTo: "#dlmm",   
				rangelength:[6,16]
				}
			},
			dzyx: {
			    required: true,
			    email: true
			},
			agree:{required:true
				},
			messages: {
				dlmc:{
					required: "请输入用户名",
					remote:"用户名已存在",
				},
				/*sjh:{
						required: "请输入手机号",
						rangelength: jQuery.format("请输入正确的手机号"),
						remote:"手机号已注册",
				},*/
				yzm:{
				required: "请输入验证码",
				rangelength: jQuery.format("请输入正确的验证码"),
				remote:"验证码错误",
				},
				dlmm: {
					required: "请输入密码",
					rangelength: jQuery.format("密码在6~16个字符之间"),
				},
				confirm_password: {
				required: "请输入确认密码",
				rangelength: jQuery.format("密码在6~16个字符之间"),
				equalTo: "两次输入密码不一致"
				},
				dzyx: {
				    required: "请输入Email地址",
				    email: "请输入正确的email地址"
				},
				agree: {
				    required: "请接受协议",
				},
		}
				
	});
});
