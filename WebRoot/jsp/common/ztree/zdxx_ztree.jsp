<!-- 
	参数管理里面的收入参数、扣发参数、补贴对应表获取tree的公共页面。 
-->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var setting = {
		check: {
			enable: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeExpand: beforeExpand,
			onExpand: onExpand,
			onClick: zTreeOnClick
		}
	};
	
	function beforeExpand() {
		//leftResize();
		// 防止右侧出现滚动条
	}
	
	function onExpand() {
		//leftResize();
		// 防止右侧出现滚动条
	}
	
	$(document).ready(function (){ 
		var treeNodes = '';
			$.ajax({
			    url : "${pageContext.request.contextPath}/zdxx/findZdxxForZtree.do",
			    data : {},
			    type : 'GET',
			    dataType : 'json',
			    success : function(json) {
				   treeNodes += "[{id:'1',pId:'0',name:'字典信息',urls:''},";
					$.each(json,function(i,n){
						treeNodes += "{";
						treeNodes += "id:'"+n.id+"'";
						treeNodes += ",pId:'"+n.pid+"'";
						treeNodes += ",name:'"+n.mc+"'";
						treeNodes += ",urls:'"+n.url+"'";
						treeNodes += "},";
					});
					treeNodes = treeNodes.substr(0,treeNodes.length-1)+"]"
					if(treeNodes != ']'){
						$.fn.zTree.init($("#treeDemo"), setting ,eval(treeNodes));
						expandFirst();
					}
			    },
			    error : function(xhr, status) {
			        alert('Sorry, there was a problem!');
			    },
			    complete : function(xhr, status) {
			    }
			});
			});
	
	
	/*右侧默认显示*/
	function rightDefault(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getNodesByFilter(filter2);
		if(nodes!=null&&nodes!=''){
			show(nodes[0].urls);
		}
	}
	
	//展开第一个节点
	function expandFirst(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getNodes();
		treeObj.expandNode(nodes[0],true,false,true,true);
		$("#m_id").val(nodes[0].id);
		$("#m_name").val(nodes[0].name);
		$("#m_jb").val(nodes[0].jb);
		show(nodes[0].id,nodes[0].name);
		//show(nodes[0].children[0].id,nodes[0].children[0].name);
	}
	
	/*展开全部*/
	function openAll(treeId){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		treeObj.expandAll(true);
	}
	
	/*折叠全部*/
	function closeAll(treeId){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var node = treeObj.getNodes()[0];
		treeObj.expandNode(node, true, false, false);
		var nodes = node.children;
		$.each(nodes,function(i,n){
			treeObj.expandNode(n, false, true, false,true);
		});
	}
	
	/*点击时操作*/
	function zTreeOnClick(event, treeId, treeNode) {
		show(treeNode.id);
		$("#m_id").val(treeNode.id);
		$("#m_name").val(treeNode.name);
		$("#m_jb").val(treeNode.jb);
	};
	
	//显示
	function show(id){
		$.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/zdxx/findZdxxForZtree.do?pid="+id,
            dataType: "json",
            success: function (result) {
            	var tt ='';
            		$.each(result,function(i,n){
            			tt+='<tr>';
            			tt+='<td class="center" style="width: 30px;">'+n.id+'</td>';
            			tt+='<td>'+n.mc+'</td>';
            			tt+='<td>'+n.px+'</td>';
            			tt+='<td>'+n.tjsj+'</td>';
            			tt+='<td>'+n.tjrmc+'</td>';
            			tt+='<td style="width:120px;">';
            				tt+='<a class="btn btn-mini btn-info" title="编辑" onclick="edit(\''+n.id+'\');"><i class="icon-edit"></i></a>&nbsp;';
            				tt+='<a class="btn btn-mini btn-danger" title="删除" onclick="del(\''+n.id+'\',\''+n.status+'\');"><i class="icon-trash"></i></a>';
            			tt+='</td>';
            			tt+='</tr>';
            		});
            		$("#mtb").html(tt);
            },
            error: function(data) {
             }
        });
	}
</script>
