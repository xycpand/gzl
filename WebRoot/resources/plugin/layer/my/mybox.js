function myTsk(msg){
	layer.open({
		offset: 50,
		skin: 'layui-layer-molv',
	    content: '<span style="color:#00a6f3;">'+msg+'</span>',
	    style: 'width:150px; height:120px; padding:8px; color:#00a6f3; border:none;',
	    btn: ['<div><span style="color:#fff">确定</span></div>']
	});
}