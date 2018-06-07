$(function () {
    $('.userAuth').hide();
    judgeEle();
});

//判断是否显示
function judgeEle() {
    $('.userAuth').each(function () {
       var role = $(this).attr('shiro:hasRole');
       if(role != undefined && hasRole(role)){
           $(this).show();
       }

       var noRole = $(this).attr('shiro:hasAnyRoles');
        if(noRole != undefined && hasAnyRoles(noRole)){
            $(this).show();
        }

       var perm = $(this).attr('shiro:hasPermission');
        if(perm != undefined && hasPermission(perm)){
            $(this).show();
        }
    });
}

//是否有role
function hasRole(role) {
    if(role == undefined || role == ''){
        return false;
    }

    var roleList = getRoleList();
    if(roleList == null) {
        return false;
    }
    for(var i=0;i<roleList.length;i++){
        if(roleList[i] == role){
            return true;
        }
    }
    return false;
}

//没有存在这个角色
function  hasAnyRoles(role) {
    return !hasRole(role);
}

//是否有permission
function hasPermission(perm) {
    if(perm == undefined || perm == ''){
        return false;
    }

    var permList = getPermList();
    if(permList == null) {
        return false;
    }
    for(var i=0;i<permList.length;i++){
        if(permList[i] == perm){
            return true;
        }
    }
    return false;
}

/**
 * 获取用户的角色
 */
function getRoleList() {
    var dataStr = sessionStorage.getItem('userAuth');
    if(dataStr != null) {
        var data = JSON.parse(dataStr);
        return data.roles;
    }else {
        return null;
    }
}

/**
 * 获取用户的权限
 */
function getPermList() {
    var dataStr = sessionStorage.getItem('userAuth');
    if(dataStr != null) {
        var data = JSON.parse(dataStr);
        return data.perms;
    }else {
        return null;
    }
}