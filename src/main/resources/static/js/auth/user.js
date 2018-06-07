var setting = {
	check : {
		enable : true,
		chkStyle: "checkbox",
		chkboxType : {
			"Y" : "p",
			"N" : "s"
		}
	},
	view : {
		dblClickExpand : false
	},
	async : {
		url : "/permission/findAllTreeList",
		enable : true,
		autoParam : []
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeClick : beforeClick,
		onCheck  : onCheck 
	}
};

function beforeClick(treeId, treeNode) {
	zTree.expandNode(treeNode);
	return false;
}

/** 选择事件 **/
function onCheck (e, treeId, treeNode) {
	var  nodes = zTree.getCheckedNodes(true),permIds = '';
	for (var i = 0, l = nodes.length; i < l; i++) {
		if(!nodes[i].chkDisabled )
			permIds += nodes[i].id + ',';
	}
	if(permIds.length>0)
		permIds = permIds.substring(0, permIds.length - 1);
	
	$('#userPerms').val(permIds);
}

var zTree ;
$(document).ready(function() {
	$.fn.zTree.init($("#tree"), setting);
	zTree = $.fn.zTree.getZTreeObj("tree");
});

var isLook;
/** 查看权限 **/
function lookPerms(obj, id){
	var dataInfo  =  table.row($(obj).closest('tr')).data();
	
	zTree.checkAllNodes(false);
	zTree.expandAll(true);
	isLook = true;
	
	$('#userId').val(id);
	
	//选择权限信息
	$.post('/user/getUserHasPerms',{userId:id},function(data){
		var permIds = '';
		for(var i=0;i<data.data.length;i++){
			var permInfo = data.data[i];
			
			if(!permInfo.isRolePerm)
				permIds += permInfo.id + ',';
			
			var treeNode = zTree.getNodeByParam('id',permInfo.id,null);
			zTree.showNode(treeNode);
			zTree.checkNode(treeNode,true,false);
			treeNode.nocheck = true;
			if(permInfo.isRolePerm)
				treeNode.chkDisabled = true;
			zTree.updateNode(treeNode);
		}
		hideNodes(zTree.getNodes());
		if(permIds.length>0)
			permIds = permIds.substring(0, permIds.length - 1);
		
		$('#userPerms').val(permIds);
	});
	
    layer.open({
		type : 1,
		title : dataInfo.userName+'——权限信息',
		maxmin : true,
		shadeClose : false, //点击遮罩关闭层
		area : [ '300px', '' ],
		content : $('#showPerms'),
		btn : [ '编辑', '关闭' ],
		success : function(layero , index){
			layer.full(index);
		},
		yes : function(index, layero) {
			//modifyPerms(index, dataInfo.roleName);
			if(isLook){
				showModTree(zTree.getNodes());
				layer.title(dataInfo.userName +'——编辑权限',index);
				$(layero).find('.layui-layer-btn0').html('修改');
				isLook = false;
			}else{
				layer.confirm('确认要修改权限吗？', {
					icon : 0,
				}, function(index) {
					$.post('/user/updateUserPerm', {
						"userId" : $('#userId').val(),
						"userPerms" : $('#userPerms').val()
					}, function(data) {
						if (data.statusCode == 200) {
							layer.closeAll();
							layer.msg('修改成功!', {
								icon : 1,
								time : 1000
							});
						} else {
							layer.msg('修改失败!', {
								icon : 2,
								time : 1000
							});
						}
					});

				});
			}
			return false;
		},end : function(){
			//zTree.expandAll(false);
			showModTree(zTree.getNodes());
		}
	});
}


/** 隐藏节点 **/
function hideNodes(treeNodes){
	for(var i=0;i<treeNodes.length;i++){
		if(!treeNodes[i].nocheck){
			zTree.hideNode(treeNodes[i]);
		}else if(treeNodes[i].children){
			hideNodes(treeNodes[i].children);
		}
	}
}

/** 显示编辑模式 **/
function showModTree(treeNodes){
	for(var i=0;i<treeNodes.length;i++){
		if(treeNodes[i].nocheck){
			treeNodes[i].nocheck = false;
			zTree.updateNode(treeNodes[i]);
		}
		zTree.showNode(treeNodes[i]);
		if(treeNodes[i].children){
			showModTree(treeNodes[i].children);
		}
	}
}

