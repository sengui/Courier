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

/**  点击之前时间 **/
function beforeClick(treeId, treeNode) {
	//zTree.checkNode(treeNode, !treeNode.checked, true, true);
	zTree.expandNode(treeNode);
	return false;
}


/** 选择事件 **/
function onCheck (e, treeId, treeNode) {
	var  nodes = zTree.getCheckedNodes(true), v = "",permIds = '';
	for (var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
		permIds += nodes[i].id + ',';
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	if(permIds.length>0)
		permIds = permIds.substring(0, permIds.length - 1);
	var Obj = $("#rolePerms");
	Obj.attr("value", v);
	$('#roleIds').attr('value',permIds);
}

function showMenu() {
	var Obj = $("#rolePerms");
	var Offset = $("#rolePerms").offset();
	$("#menuContent").css({
		left : Offset.left + "px",
		top : Offset.top + Obj.outerHeight() + "px"
	}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "citySel"
			|| event.target.id == "menuContent" || $(event.target).parents(
			"#menuContent").length > 0)) {
		hideMenu();
	}
}




var showTreeSetting = {
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
		beforeClick : showBeforeClick,
		onCheck  : showOnCheck 
	}
};

var zTree, showTree ;
$(document).ready(function() {
	$.fn.zTree.init($("#tree"), setting);
	zTree = $.fn.zTree.getZTreeObj("tree");
	$.fn.zTree.init($("#showTree"), showTreeSetting);
	showTree = $.fn.zTree.getZTreeObj("showTree");
});

var isLook;
/** 查看权限 **/
function lookPerms(obj, id){
	var dataInfo  =  table.row($(obj).closest('tr')).data();
	
	showTree.checkAllNodes(false);
	showTree.expandAll(true);
	isLook = true;
	
	$('#roleId').val(id);
	
	//选择权限信息
	$.post('/role/getRoleHasPerms',{roleId:id},function(data){
		if(data.statusCode == 200) {
			var dataList = data.data;
            var permIds = '';
            for(var i=0;i<dataList.length;i++){
                var permInfo = dataList[i];
                permIds += permInfo.id + ',';

                var treeNode = showTree.getNodeByParam('id',permInfo.id,null);
                showTree.showNode(treeNode);
                showTree.checkNode(treeNode,true,false);
                treeNode.nocheck = true;
                showTree.updateNode(treeNode);
            }
            hideNodes(showTree.getNodes());
            if(permIds.length>0)
                permIds = permIds.substring(0, permIds.length - 1);

            $('#showRolePerms').val(permIds);
        }
	});
	
    layer.open({
		type : 1,
		title : dataInfo.roleName+'——权限信息',
		maxmin : true,
		shadeClose : false, //点击遮罩关闭层
		area : [ '300px', '' ],
		content : $('#showPerms'),
		btn : [ '编辑', '关闭' ],
		success:function(layero, index){
			layer.full(index);
			
		},
		yes : function(index, layero) {
			//modifyPerms(index, dataInfo.roleName);
			if(isLook){
				showModTree(showTree.getNodes());
				layer.title(dataInfo.roleName +'——编辑权限',index);
				$(layero).find('.layui-layer-btn0').html('修改');
				isLook = false;
			}else{
				layer.confirm('确认要修改权限吗？', {
					icon : 0,
				}, function(index) {
					$.post('/role/modRolePerms', {
						"roleId" : $('#roleId').val(),
						"rolePermList" : $('#showRolePerms').val()
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
			closeShowTree();
		}
	});
}


/** 隐藏节点 **/
function hideNodes(treeNodes){
	for(var i=0;i<treeNodes.length;i++){
		if(!treeNodes[i].nocheck){
			showTree.hideNode(treeNodes[i]);
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
			showTree.updateNode(treeNodes[i]);
		}
		showTree.showNode(treeNodes[i]);
		if(treeNodes[i].children){
			showModTree(treeNodes[i].children);
		}
	}
}

/** 关闭树信息 **/
function closeShowTree(){
	showModTree(showTree.getNodes());
}

function showBeforeClick(treeId, treeNode) {
	/*if(!treeNode.nocheck){
		showTree.checkNode(treeNode, !treeNode.checked, true, true);
	}*/
	showTree.expandNode(treeNode);
	return false;
}


/** 选择事件 **/
function showOnCheck (e, treeId, treeNode) {
	var  nodes = showTree.getCheckedNodes(true),permIds = '';
	for (var i = 0, l = nodes.length; i < l; i++) {
		permIds += nodes[i].id + ',';
	}
	if(permIds.length>0)
		permIds = permIds.substring(0, permIds.length - 1);
	$('#showRolePerms').val(permIds);
}