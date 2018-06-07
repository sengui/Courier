/** 列表通用js **/

var layerIndex;//弹框索引
var isUpdate=false;//是否是更新

var layWidth;
var layHeight;
var form;

//初始化数据
$(function() {
	layui.use('form', function() {
		form = layui.form;
		
		//弹框宽度
		layWidth = $('#ad_form').outerWidth(true);
		layWidth = Math.max(865,layWidth);
		layWidth = Math.min($(window).width()-300,layWidth)+'px';
		
		//弹框高度
		layHeight = $('#ad_form').outerHeight(true)+100;
		layHeight = Math.max(250,layHeight);
		layHeight = Math.min($(window).height()-150,layHeight)+'px';
		
		form.on('submit(*)', function(data) {
			submitForm();
			return false;
		});
	});
	table = initTable();

	$('table th input:checkbox').on(
			'click',
			function() {
				var that = this;
				$(this).closest('table').find(
						'tr > td:first-child input:checkbox').each(function() {
					this.checked = that.checked;
				});

			});
	if($('#kindTextName').length > 0 && $('#kindTextName').val() != ''){
		KindEditor.ready(function(K) {
			kindEditor = K;
		});
	}
});

/** 添加form **/
function addInfo(){
	layerIndex =layer.open({
		type : 1,
		title : '添加'+$('#formTitle').val(),
		maxmin : true,
		shadeClose : true, //点击阴影关闭弹框
		zIndex : 99,
		area : [ layWidth, layHeight ],
		content : $('#ad_form'),
		btn : [ '添加', '取消' ],
		success: function(layero, index){
            form.render();
			onloadKindEditor();
		},
		yes : function(index, layero) {
			$('#btn_submit').click();
			return false;
		},
		end:function(){
			clearForm();  
		}
	});
}

/** 编辑信息 **/
function updateInfo(obj,id, actNum){
	layerIndex =layer.open({
		type : 1,
		title : '编辑'+$('#formTitle').html(),
		maxmin : true,
		shadeClose : true, //
		zIndex : 99,
		area : [ layWidth, layHeight ],
		content : $('#ad_form'),
		btn : [ '修改', '取消' ],
		success: function(layero, index){
			isUpdate = true;
			fullForm(id, actNum);
			onloadKindEditor();
		},
		yes : function(index, layero) {
			$('#btn_submit').click();
			return false;
		},
		end:function(){
			clearForm();
		}
	});
}



/** 填充表单 **/
function fullForm(id,actNum){
    var jsonId = JSON.parse('{"'+  $("#idName").val() +'":"'+ id +'"}');
	$('#ad_form').loadForm({
		url:$('#getInfoUrl').val(),
		data:jsonId
	});
	$('#actNum').val(actNum);
	if($('input[name="modId"]').length>0){
		$('input[name="modId"]').val(id);
	}
	form.render();
}

/** 提交表单 **/
function submitForm(){
	if($('#isExist').length >0 && $('#isExist').is(':visible')){
		layer.alert('填写表单有误！');
		return;
	}

	jQuery('#ad_form').ajaxSubmit({
		url:$('#submitUrl').val(),
		type:'post',
		dataType:'json',
		success:function(data){
			addSuccess();
		}
	});
}

/** 成功后 **/
function addSuccess(){
	layer.close(layerIndex);
	table.ajax.reload();
	if(isUpdate)
		layer.msg('更新成功！',{icon:1,time : 1000});
	else
		layer.msg('添加成功！',{icon:1,time : 1000});
		
}

/** 删除  **/
function delInfo(obj,id,actNum){
    var jsonId = JSON.parse('{"'+  $("#idName").val() +'":"'+ id +'"}');
    jsonId.actNum = actNum;
	layer.confirm('确认要删除吗？',{icon:0,},function(index){
		$.post($('#delUrl').val(),jsonId,function(data){
			$(obj).parents("tr").remove();
			table.ajax.reload();
			layer.msg('已删除!', {
				icon : 1,
				time : 1000
			});
		});
	});
}

/** 批量删除  **/
function deleteAll(){
	layer.confirm('确认要删除选中的项吗？', {
		icon : 0,
	}, function(index) {
		$('table td input[name="checkbox"]').each(function(){
			if($(this).prop('checked')){
				var id = $(this).val();
				var obj = $(this);
                var jsonId = JSON.parse('{"'+  $("#idName").val() +'":"'+ id +'"}');
				$.post($('#delUrl').val(), jsonId, function(data) {
					if (data.statusCode == 200)
						$(obj).parents("tr").remove();
				});
			}
				
		});
		layer.msg('已删除!', {
			icon : 1,
			time : 1000
		});
		table.ajax.reload();
	});
}

/** 清除表单 **/
function clearForm(){
	$('#ad_form').hide();
	if($('#kindTextName').length > 0 && $('#kindTextName').val() != ''){
		kindEditor.remove('textarea[name="'+ $('#kindTextName').val() +'"]');
	}
	document.getElementById("ad_form").reset(); 
	if($('input[name="modId"]').length>0){
		$('input[name="modId"]').val('');
	}
	if($('input[name="id"]').length>0){
		$('input[name="id"]').val('');
	}
	isUpdate = false;
	$('#btn_reset').click();
}

/** 截取textArea中的数据 **/
function subTextArea(str){
	if(str==null||str==undefined){
		return '';
	}
	if(str.length>30){
		return str.substr(0,25)+"...<a href='javascript:;' class='text-info' onclick='lookMore(\""+  str +"\");'>更多</a>";
	}
	return str;
}

/** 查看详情 **/
function lookMore(content){
	layer.open({
	  type: 1,
	  closeBtn: 0, //不显示关闭按钮
	  shadeClose: true, //开启遮罩关闭
	  content: content
	});
}

/** 处理录入时间 **/
function dealEntryTime(entryTime){
	if(entryTime == null||entryTime==''){
		return entryTime;
	}
	return entryTime.replace('T',' ');
}

// 检测是否存在
function examUnique(id) {
    var jsonId = JSON.parse('{"'+  $("#idName").val() +'":"'+ id +'"}');
	$.post($('#getInfoUrl').val(),jsonId, function (data) {
		if(data.statusCode == 200 && data.data != null){
			$('#isExist').show();
		}else{
            $('#isExist').hide();
		}
    } );
}

var editor;
var kindEditor;
/** 初始化编辑器 **/
function onloadKindEditor(){
	if($('#kindTextName').length > 0 && $('#kindTextName').val() != ''){
		editor = kindEditor.create('textarea[name="'+ $('#kindTextName').val() +'"]', {
			width: '100%',
			height: '200px',
			resizeType : 1,
			allowPreviewEmoticons : false,
			allowImageUpload : true,
			uploadJson : 'kindeditorUpload.action',
			fileManagerJson : 'kindeditorFileManager.action',
			allowFileManager: true,
			afterBlur:function(){
				//同步到textarea
				this.sync();
			}
		});
	}
	
}

if(hasRole('teacher')){
    $('#data-all').hide();
}else{
    $('#data-student').hide();
    $('#submitUrl').val('/user/saveOrUpdateUser');
    $('#delUrl').val('/stuCouRel/delete');
}

function refreshTable(obj) {
    if(obj.id == 'filterRole'){
        if($(obj).val() == 'student'){
            $('#filterYear').show();
        }else{
            $('#filterYear').val('');
            $('#filterYear').hide();
        }
    }
   table.ajax.reload();
}

/**
 * 初始化列表
 */
function initTable() {
	if(hasRole('teacher')){
		$('#data-all').hide();
        var table = $('#data-student')
            .DataTable(
                {
                    "serverSide" : true,
                    "ajax" : {
                        "url" : $('#getListUrl').val(),
                        "type" : "post",
                        "data" : function(data) {
                            data.actNum = $('#filterActNum').val();
                            data.searchName = $('#search').val();
                        },
                        "dataSrc": function (json) { // deal the data
                            json.recordsTotal=json.data.total;
                            json.recordsFiltered=json.data.total;
                            return json.data.list;
                        }
                    },
                    "columns" : [ {
                        "defaultContent" : ""
                    }, {
                        "data" : "userId"
                    }, {
                        "data" : "userName"
                    }, {
                        "data" : "userSex"
                    } ,{
                        "data" : "email"
                    }, {
                        "data" : "userTel"
                    },{
                        "data" : "className"
                    },{
                        "defaultContent" : ""
                    } ],
                    "columnDefs" : [
                        {
                            "targets" : [ 0 ],
                            "data" : "userId",
                            "render" : function(data, type, full) {//全部列值可以通过full.列名获取,一般单个列值用data PS:这里的render是有多少列就执行多少次方法。。。不知道为啥
                                return '<label><input type="checkbox" name="checkbox" value="'+ data +'" onchange="changeTrState(this);" class="ace"><span class="lbl"></span></label>';
                            }
                        },
                        {
                            "targets" : [ 7],
                            "data" : "userId",
                            "render" : function(data, type, full) {//全部列值可以通过full.列名获取,一般单个列值用data PS:这里的render是有多少列就执行多少次方法。。。不知道为啥
                                var html = '';
                                if(hasPermission('user_mod')){
                                    html += ' <a href="javascript:;" title="修改" onclick="updateInfo(this,\''
                                        + data
                                        + '\', full.actNum)" class="btn btn-xs btn-warning"><i class="fa fa-edit bigger-120"></i></a>';
                                }

                                if(hasPermission('user_del')){
                                    html += '<a href="javascript:;" title="删除" onclick="delInfo(this,\''
                                        + data
                                        + '\', full.actNum)" class="btn btn-xs btn-danger"><i class="fa fa-trash-o bigger-120"></i></a>';
                                }
                                return html;
                            }
                        }, {
                            "orderable" : false,
                            "targets" : [ 0, 3 ]
                        },// 是否排序
                        //{ "visible": false, "targets": [3, 5] }//是否可见
                    ],
                    //"pagingType": "full_numbers",//分页格式
                    "processing" : false,//等待加载效果
                    "ordering" : false,//排序功能
                    "width" : "100%",
                    "bLengthChange" : false,
                    "iDisplayLength" : 10,
                    "searching" : false
                });
        return table;
	}else{
        $('#data-student').hide();
        var table = $('#data-all')
            .DataTable(
                {
                    "serverSide" : true,
                    "ajax" : {
                        "url" : $('#getListUrl').val(),
                        "type" : "post",
                        "data" : function(data) {
                            data.unitId = $('#filterUnit').val();
                            data.depId = $('#filterDep').val();
                            data.roleId = $('#filterRole').val();
                            data.beforeYear = $('#filterYear').val();
                            data.searchName = $('#search').val();
                        },
                        "dataSrc": function (json) { // deal the data
                            json.recordsTotal=json.data.total;
                            json.recordsFiltered=json.data.total;
                            return json.data.list;
                        }
                    },
                    "columns" : [ {
                        "defaultContent" : ""
                    }, {
                        "data" : "userId"
                    }, {
                        "data" : "userName"
                    }, {
                        "defaultContent" : ""
                    }, {
                        "data" : "userSex"
                    } ,{
                        "data" : "email"
                    }, {
                        "data" : "userTel"
                    },{
                        "data" : "depName"
                    },{
                        "data" : "unitName"
                    },{
                        "defaultContent" : ""
                    } ],
                    "columnDefs" : [
                        {
                            "targets" : [ 0 ],
                            "data" : "userId",
                            "render" : function(data, type, full) {//全部列值可以通过full.列名获取,一般单个列值用data PS:这里的render是有多少列就执行多少次方法。。。不知道为啥
                                return '<label><input type="checkbox" name="checkbox" value="'+ data +'" onchange="changeTrState(this);" class="ace"><span class="lbl"></span></label>';
                            }
                        },
                        {
                            "targets" : [ 3 ],
                            "data" : "roleList",
                            "render" : function(data, type, full) {//全部列值可以通过full.列名获取,一般单个列值用data PS:这里的render是有多少列就执行多少次方法。。。不知道为啥
                                var html = '';
                                $.each(data,function (index, item) {
									html += '<span class="label label-info">' + item.roleName + '</span>';
                                })
                                return html;
                            }
                        },
                        {
                            "targets" : [ 9],
                            "data" : "userId",
                            "render" : function(data, type, full) {//全部列值可以通过full.列名获取,一般单个列值用data PS:这里的render是有多少列就执行多少次方法。。。不知道为啥
                                var html = '';
                                if(hasPermission('user_mod')){
                                    html += ' <a href="javascript:;" title="修改" onclick="updateInfo(this,\''
                                        + data
                                        + '\', null)" class="btn btn-xs btn-warning"><i class="fa fa-edit bigger-120"></i></a>';
                                }

                                if(hasPermission('user_del')){
                                    html += '<a href="javascript:;" title="删除" onclick="delInfo(this,\''
                                        + data
                                        + '\', null)" class="btn btn-xs btn-danger"><i class="fa fa-trash-o bigger-120"></i></a>';
                                }
                                return html;
                            }
                        }, {
                            "orderable" : false,
                            "targets" : [ 0, 3 ]
                        },// 是否排序
                        //{ "visible": false, "targets": [3, 5] }//是否可见
                    ],
                    //"pagingType": "full_numbers",//分页格式
                    "processing" : false,//等待加载效果
                    "ordering" : false,//排序功能
                    "width" : "100%",
                    "bLengthChange" : false,
                    "iDisplayLength" : 10,
                    "searching" : false
                });
        return table;
	}
}

$.post('/unit/findAll',{},function (data) {
    if(data.statusCode == 200){
        var dataList = data.data;
        var html = '';
        $.each(dataList,function (index, item) {
            html += '<option value="'+ item.unitId +'">'+ item.unitName +'</option>';
        });
        $('#unitId').html(html);
    }
    getDepList();
});
$.post('/teachActivity/findListInfoBySessionUser',{},function (data) {
    if(data.statusCode == 200){
        var dataList = data.data;
        var html = '';
        $.each(dataList,function (index, item) {
            html += '<option value="'+ item.actNum +'">'+ item.className +'_'+ item.courseName +'</option>';
        });
        $('#actNum').html(html);
        var filterHtml = '<option value="">--请选择教学班--</option>' + html;
        $('#filterActNum').html(filterHtml);
    }
});

function getDepList() {
    var unitId = $('#unitId').val();
    var url;
    var json={};
    if(unitId == undefined || unitId == ''){
        url = '/department/findAll';
    } else{
        url = '/department/findByUnitId';
        json = {unitId : unitId};
    }
    $.post(url,json,function (data) {
        if(data.statusCode == 200){
            var dataList = data.data;
            var html = '';
            $.each(dataList,function (index, item) {
                html += '<option value="'+ item.depId +'">'+ item.depName +'</option>';
            });
            $('#depId').html(html);
        }
    });
}

layui.use('form',function () {
    var form = layui.form;

    form.on('select(unitId)', function(data){
        getDepList();
        form.render('select');
    });
});

if (hasAnyRoles('teacher')){
    $.post('/role/findNowUserRoleList',{},function(data){
        var checkHtml = '';
        var select = '<option value="">选择角色</option>';
        $.each(data.data,function(index,item){
            checkHtml += '<input type="checkbox" name="userRoles" value="'+ item.roleId +'" title="'+ item.roleName +'">';
            select += '<option value="'+ item.roleId +'">'+ item.roleName +'</option>';
        });
        $('#userRoles').html(checkHtml);
        //$('#roleList').html(select);
        //form.render('checkbox', 'userForm');
    });
}
