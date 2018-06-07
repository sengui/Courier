var setting = {
	view : {
		dblClickExpand : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	edit : {
		enable : true,
		showRemoveBtn : false,
		showRenameBtn : false
	},
	async : {
		url : "/permission/findAllTreeList",
		enable : true,
		autoParam : []
	},
	callback : {
		beforeClick:beforeClick,
		onClick : onClick,
		onRightClick : OnRightClick,
		beforeDrag: beforeDrag,
		beforeDrop: beforeDrop,
		onDrop: onDrop,
		onAsyncSuccess:onAsyncSuccess
	}
};


/** 右键事件 **/
function OnRightClick(event, treeId, treeNode) {
	if (!treeNode && event.target.tagName.toLowerCase() != "button"
			&& $(event.target).parents("a").length == 0) {
		/*zTree.cancelSelectedNode();
		showRMenu("root", event.clientX, event.clientY);*/
		return ;
	} else if (treeNode && !treeNode.noR) {
		zTree.selectNode(treeNode);
		showRMenu(treeNode.pId,treeNode.permType , event.clientX, event.clientY);
	}
}

/** 显示菜单 **/
function showRMenu(pid,permType, x, y) {
	$("#rMenu ul").show();
	
	if(pid == null){
		$('#m_add').show();
		$('#m_mod').hide();
		$('#m_del').hide();
	}else {
		if(permType=='operation'){
			$('#m_add').hide();
			$('#m_mod').hide();
			$('#m_del').show();
		}else{
			$('#m_add').show();
			$('#m_mod').show();
			$('#m_del').show();
		}
	}
	
	y += document.body.scrollTop;
	x += document.body.scrollLeft;
	rMenu.css({
		"top" : y + "px",
		"left" : x + "px",
		"visibility" : "visible"
	});

	$("body").bind("mousedown", onBodyMouseDown);
}
/** 隐藏菜单 **/
function hideRMenu() {
	if (rMenu)
		rMenu.css({
			"visibility" : "hidden"
		});
	$("body").unbind("mousedown", onBodyMouseDown);
}

/**  **/
function onBodyMouseDown(event) {
	if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
		rMenu.css({
			"visibility" : "hidden"
		});
	}
}

var curMenu;
/**
 * 点击之前
 * @param e
 * @param treeId
 * @param treeNode
 */
function beforeClick(treeId, node) {
	if (node.isParent) {
		if (node.level === 0) {
			var pNode = curMenu;
			while (pNode && pNode.level !==0) {
				pNode = pNode.getParentNode();
			}
			if (pNode !== node) {
				var a = $("#" + pNode.tId + "_a");
				a.removeClass("cur");
				zTree.expandNode(pNode, false);
			}
			a = $("#" + node.tId + "_a");
			a.addClass("cur");
			curMenu = node;
		}
	}
	return true;
}

/** 左键事件 **/
function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("tree");
	zTree.expandNode(treeNode);
	loadPermId = treeNode.id;
	table.ajax.reload();
}

var zTree, rMenu;
$(document).ready(function() {
	$.fn.zTree.init($("#tree"), setting);
	zTree = $.fn.zTree.getZTreeObj("tree");
	rMenu = $("#rMenu");
});

/** 异步加载成功 **/
function onAsyncSuccess(event, treeId, treeNode, msg){
	var a = $("#" + zTree.getNodes()[0].tId + "_a");
	curMenu = zTree.getNodes()[0];
	a.addClass("cur");
	zTree.expandNode(zTree.getNodes()[0]);
}

var oldPid = 0;
/**
 * 在拖动之前
 * @param treeId
 * @param treeNodes
 * @returns {Boolean}
 */
function beforeDrag(treeId, treeNodes) {
	var treeNode = treeNodes[0];
	//根节点不能拖动
	if(treeNode.pId == null){
		return false;
	}
	oldPid = treeNode.pId;
	return true;
}

/**
 * 拖动结束之前
 * @param treeId
 * @param treeNodes
 * @param targetNode
 * @param moveType
 * @returns {Boolean}
 */
function beforeDrop(treeId, treeNodes, targetNode, moveType) {
	// 不能拖动到根节点
	if(targetNode.pId == null){
		layer.alert('不能拖动到根节点外!', {
			icon : 2
		});
		return false;
	}
	/*alert(treeId);
	alert(JSON.stringify(targetNode));
	alert(moveType);*/
	return true;
}

/**
 * 拖拽事件，处理后台数据
 * @param event
 * @param treeId
 * @param treeNodes
 * @param targetNode
 * @param moveType
 */
function onDrop(event, treeId, treeNodes, targetNode, moveType){
	var moveNode = treeNodes[0];
	//alert(JSON.stringify(moveNode));
	$.post('/permission/moveNode',{
		permId:moveNode.id,
		pid:oldPid,
		showPermId:targetNode.id,
		showPid:targetNode.pId,
		moveType:moveType
	},function(data){
	});
	
	//移动
	return false;
}

var isModify = false;//是否是修改

/** 添加权限 **/
function addPerm(){
	
	var focusTree = zTree.getSelectedNodes()[0];
	var pid = focusTree.id;
	$('input[name="pId"]').val(pid);
	$('#sysTypeId').val(focusTree.systemId);
	$('input[name="modId"]').val('');
	
	hideRMenu();
	isModify = false;
	$('#btn_reset').click();
	layer.open({
		type : 1,
		title : '添加权限',
		maxmin : false,
		shadeClose : false, //点击遮罩关闭层
		area : [ '60%', '' ],
		content : $('#ad_perm'),
		btn : [ '添加', '取消' ],
		yes : function(index, layero) {
			if($('#hasPermCode').is(':visible')){
				layer.msg('权限编码已经存在!', {
					icon : 2,
					time : 1000
				});
				return false;
			}
			$('#btn_submit').click();
			return false;
		},end: function(){ 
			$('#hasPermCode').hide();
			$('#operationItem').hide();
		}
	});
}

/** 修改权限 **/
function modPerm(){
	var focusTree = zTree.getSelectedNodes()[0];
	var id = focusTree.id;
	$('input[name="pId"]').val(focusTree.pId);
	$('#sysTypeId').val(focusTree.systemId);
	//$('#operationItem').hide();
	
	//alert(focusTree.permType);
	
	$('input[name="permType"]').removeAttr('checked');
	$("input:radio[value="+ focusTree.permType +"]").attr('checked','true');
	$("input:radio[value="+ focusTree.permType +"]").next().click();
	form.render('radio');
	
	
	
	//alert($('input[name="permType"]:checked').val());
	
	hideRMenu();
	isModify = true;
	$('#btn_reset').click();
	
	if(focusTree.permType == 'module'){
		var childrens = focusTree.children;
		for(var i = 0;i<childrens.length;i++){
			$('input[name="childOpeList"]').each(function(){
				if(childrens[i].name == $(this).attr('title')){
					$(this).attr('checked','true');
				}
			});
		}
	}
	
	layer.open({
		type : 1,
		title : '修改权限',
		maxmin : false,
		shadeClose : false, //点击遮罩关闭层
		area : [ '600px', '' ],
		content : $('#ad_perm'),
		btn : [ '修改', '取消' ],
		success:function(layero, index){
			$(layero).find('textarea[name="remark"]').val(focusTree.remark).keyup();
			$(layero).find('input[name="modId"]').val(id);
			$(layero).find('input[name="permName"]').val(focusTree.name);
			$(layero).find('input[name="permCode"]').val(focusTree.permCode);
			
		},
		yes : function(index, layero) {
			if($('#hasPermCode').is(':visible')){
				layer.msg('权限编码已经存在!', {
					icon : 2,
					time : 1000
				});
				return false;
			}
			$('#btn_submit').click();
			return false;
		},end: function(){ 
			$('#hasPermCode').hide();
			//$('#operationItem').show();
			$('input[name="permType"]').removeAttr('checked');
			$('input[name="permType"]').eq(0).attr('checked','true');
			form.render('radio');
			$('input[name="childOpeList"]').removeAttr('checked');
			$('#operationItem').hide();
		}
	});
}

/** 删除权限 **/
function delPerm(){
	var focusTree = zTree.getSelectedNodes()[0];
	var id = focusTree.id;
	hideRMenu();
	layer.confirm('确认要删除选中的权限吗？', {
		icon : 0,
	}, function(index) {
		$.post('/permission/delete', {
			"permId" : id
		}, function(data) {
			if (data.statusCode == 200) {
				zTree.removeNode(focusTree);
				layer.msg('已删除!', {
					icon : 1,
					time : 1000
				});
				table.ajax.reload();
			} else {
				layer.msg('删除失败!', {
					icon : 2,
					time : 1000
				});
			}
		});
	});
}
var form;
layui.use('form', function() {
	 form = layui.form;

	//监听提交
	form.on('submit(*)', function(data) {
		var nodeData = data;
		var nowSysTypeId = $('#sysTypeId').val();
		
		$("#ad_perm").ajaxSubmit({
			url:'/permission/saveOrUpdateAll',
			success:function(data){
				if (data.statusCode == 200 ){
					$('#btn_reset').click();
					$('textarea[name="remark"]').keyup();
					layer.closeAll();
					//table.ajax.reload();
					if(!isModify){
						addTreeNode(nodeData.field.permName,data.data.permId,nodeData.field.pid,nodeData.field.permCode,nodeData.field.remark,nowSysTypeId,nodeData.field.permType,data.data.childJson);
						//addChildNode(data.data, data.childJson);
						layer.msg('添加成功!', {
							icon : 1,
							time : 1000
						});
					}
					else{
						var node;
						if(tableModify){
							table.ajax.reload();
							node = zTree.getNodeByParam('id',nodeData.field.modId);
						}else{
							node = zTree.getSelectedNodes()[0];
						}
						node.name = nodeData.field.permName;
						node.permCode = nodeData.field.permCode;
						node.remark = nodeData.field.remark;
						node.permType = nodeData.field.permType;
						zTree.updateNode(node);
						zTree.removeChildNodes(node);
						
						if(nodeData.field.permType == 'module'){
							addChildNode(node,data.data);
						}
						
						layer.msg('修改成功!', {
							icon : 1,
							time : 1000
						});
					}
					table.ajax.reload();
				} else {
					if(!isModify)
						layer.msg('添加失败!', {
							icon : 2,
							time : 1000
						});
					else
						layer.msg('修改失败!', {
							icon : 2,
							time : 1000
						});
				}
			}
		});
			
		return false;
	});
		/*$.post('saveOrUpdatePerm.action',data.field, function(data) {
			
	});*/
	
	form.on('radio(permType)', function(data){
		if(data.value == 'module'){
			$('#operationItem').show();
		}else{
			$('#operationItem').hide();
		}
	}); 

});

/**
 * 添加节点信息
 * @param name
 * @param id
 * @param pid
 */
function addTreeNode(name, id, pid,permCode, remark,sysTypeId,permType,childJson){
	var focusTree = zTree.getSelectedNodes()[0];
	var newNode = { name:name,id:id,pId:pid,permCode:permCode,remark:remark,sysTypeId:sysTypeId,permType:permType};
	
	var child = [];
	if(childJson != null && childJson.length > 0){
		for(var i=0;i<childJson.length;i++){
			var data = childJson[i];
			var newChildNode = { name:data.name,id:data.id,pId:data.pid,permCode:data.permCode,remark:data.remark,sysTypeId:data.sysTypeId,permType:data.permType};
			child[i] = newChildNode;
		}
		/*pTreeNode.isParent = true;
		zTree.updateNode(pTreeNode);*/
		newNode.children = child;
	}
	
	//alert(JSON.stringify(focusTree));
	if(focusTree){
		zTree.addNodes(focusTree, newNode);
	}
}

/**
 * 添加子节点信息
 * @param pid
 * @param childJson
 */
function addChildNode(pTreeNode, childJson){
	
	if(childJson != null && childJson.length > 0){
		for(var i=0;i<childJson.length;i++){
			var data = childJson[i];
			var newNode = { name:data.name,id:data.id,pId:data.pid,permCode:data.permCode,remark:data.remark,sysTypeId:data.sysTypeId,permType:data.permType};
			zTree.addNodes(pTreeNode, newNode);
		}
		/*pTreeNode.isParent = true;
		zTree.updateNode(pTreeNode);*/
	}
}

/** 备注长度 **/
function checkLength(which) {
	var maxChars = 100; //
	if (which.value.length > maxChars) {
		layer.open({
			icon : 2,
			title : '提示框',
			content : '您出入的字数超多限制!',
		});
		// 超过限制的字数了就将 文本框中的内容按规定的字数 截取
		which.value = which.value.substring(0, maxChars);
		return false;
	} else {
		var curr = maxChars - which.value.length; //100 减去 当前输入的
		document.getElementById("sy").innerHTML = curr.toString();
		return true;
	}
};

/** 权限编码 是否存在 **/
function hasPermCode(obj){
	$.post('/permission/findByIdAndSys',{
			permCode:$('input[name="permCode"]').val(),
			systemId:$('#sysTypeId').val()
		},
		function(data){
			if(data.statusCode == 200 && data.data != null){
				$('#hasPermCode').show();
			}else{
				$('#hasPermCode').hide();
			}
	});
}

var table = null;//table对象
var loadPermId;//加载的id

jQuery(function($) {
	initOperation();
	table = initTable();
	$('table th input:checkbox').on(
			'click',
			function() {
				var that = this;
				$(this).closest('table').find(
						'tr > td:first-child input:checkbox').each(
						function() {
							this.checked = that.checked;
							if(this.checked)
								$(this).closest('tr').addClass('selected');
							else
								$(this).closest('tr').removeClass('selected');
						});

			});
	
});

/** 初始化操作列表 **/
function initOperation(){
	$.post('/operation/findAll',{},function(data){
		var checkHtml = '';
		$.each(data.data,function(index,item){
			checkHtml += '<input type="checkbox" name="childOpeList" lay-skin="primary" value="'+ item.opeId +'" title="'+ item.opeName +'">';
		});
		$('#operationList').html(checkHtml);
		//form.render('checkbox', 'permForm');
	});
}

/**
 * 初始化列表
 */
function initTable() {
	var table = $('#data-table')
			.DataTable(
					{
						"serverSide" : true,
						"ajax" : {
							"url" : "/permission/findTableList",
							"type" : "post",
							"data" :function(data) {
								data.permId = loadPermId;
							},
                            "dataSrc": function (json) { // deal the data
                                json.recordsTotal=json.data.length;
                                json.recordsFiltered=json.data.length;
                                return json.data;
                            }
						},
						"columns" : [ {
							"data" : "permName"
						}, {
							"data" : "permCode"
						}, {
							"defaultContent" : ""
						}, {
							"data" : "systemName"
						}, {
							"defaultContent" : ""
						} ,{
							"data" : "remark"
						}, {
							"data" : "pId"
						},  {
							"data" : "systemId"
						},  {
							"defaultContent" : ""
						} ],
						"columnDefs" : [
								{
									"targets" : [2],
									"data" : "permType",
									"render" : function(data, type, full) {//全部列值可以通过full.列名获取,一般单个列值用data PS:这里的render是有多少列就执行多少次方法。。。不知道为啥
										var html = '';
										if(data == 'menu')
											html = '<span class="label label-success radius">菜单</span>';
										else if(data == 'module')
											html = '<span class="label label-info radius">模块</span>';
										else
											html = '<span class="label label-warning radius">操作</span>';
										
										if(full.pId == null || full.pId == 0)
											html = '<span class="label label-danger radius">系统</span>';
										
										return html;
									}
								},
                            {
                                "targets" : [4],
                                "data" : "createTime",
                                "render" : function(data, type, full) {//全部列值可以通过full.列名获取,一般单个列值用data PS:这里的render是有多少列就执行多少次方法。。。不知道为啥

                                    return new Date(parseInt(data)).Format('yyyy-MM-dd HH:mm');
                                }
                            },
                            {
									"targets" : [8],
									"data" : "permId",
									"render" : function(data, type, full) {//全部列值可以通过full.列名获取,一般单个列值用data PS:这里的render是有多少列就执行多少次方法。。。不知道为啥
										var html = '';
										if(full.permType != 'operation')
											html = ' <a href="javascript:;" title="修改" onclick="updateInfo(this,'
													+ full.id + ')" class="btn btn-xs btn-warning"><i class="fa fa-edit bigger-120"></i></a>';
										return html;
									}
								}, {
									"orderable" : false,
									"targets" : [ 0, 3 ]
								},// 是否排序
						{ "visible": false, "targets": [6, 7] }//是否可见
						],
						//"pagingType": "full_numbers",//分页格式
						"processing" : false,//等待加载效果
						"ordering" : false,//排序功能
						"width" : "100%",
						"bLengthChange" : false,
						"iDisplayLength" : 10,
						"searching" : false,
					});
	return table;
}

var tableModify = false;
/** 修改 **/
function updateInfo(obj,id){
	var data  =  table.row($(obj).closest('tr')).data();
	
	$('input[name="permType"]').removeAttr('checked');
	$("input:radio[value="+ data.permType +"]").attr('checked','true');
	$("input:radio[value="+ data.permType +"]").next().click();
	form.render('radio');
	
	tableModify = true;
	isModify = true;
	$('#btn_reset').click();
	
	if(data.permType == 'module'){
		var focusTree = zTree.getSelectedNodes()[0];
		var childrens = focusTree.children;
		for(var i = 0;i<childrens.length;i++){
			$('input[name="childOpeList"]').each(function(){
				if(childrens[i].name == $(this).attr('title')){
					$(this).attr('checked','true');
				}
			});
		}
	}
	
	layer.open({
		type : 1,
		title : '修改权限',
		maxmin : false,
		shadeClose : false, //点击遮罩关闭层
		area : [ '600px', '' ],
		content : $('#ad_perm'),
		btn : [ '修改', '取消' ],
		success:function(layero, index){
			$(layero).find('textarea[name="remark"]').val(data.remark).keyup();
			$(layero).find('input[name="modId"]').val(id);
			$(layero).find('input[name="permName"]').val(data.permName);
			$(layero).find('input[name="permCode"]').val(data.permCode);
			$(layero).find('input[name="pid"]').val(data.pid);
			$('#sysTypeId').val(data.systemId);
		},
		yes : function(index, layero) {
			if($('#hasPermCode').is(':visible')){
				layer.msg('权限编码已经存在!', {
					icon : 2,
					time : 1000
				});
				return false;
			}
			$('#btn_submit').click();
			return false;
		},end: function(){ 
			$('#hasPermCode').hide();
			tableModify = false;
			$('input[name="permType"]').removeAttr('checked');
			$('input[name="permType"]').eq(0).attr('checked','true');
			form.render('radio');
			$('input[name="childOpeList"]').removeAttr('checked');
			$('#operationItem').hide();
		}
	});
}