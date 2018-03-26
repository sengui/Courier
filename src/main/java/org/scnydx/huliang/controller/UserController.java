package org.scnydx.huliang.controller;

import org.scnydx.huliang.base.BaseController;
import org.scnydx.huliang.beans.po.User;
import org.scnydx.huliang.beans.vo.HttpResult;
import org.scnydx.huliang.contants.ResultCode;
import org.scnydx.huliang.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: CSG
 * @Description: 用户controller
 * @Date: Create in 11:00 2018/3/26
 * @Modify by:
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User> {

    @Autowired
    private IUserService userService;

    @RequestMapping("/getVerifyCode")
    public HttpResult getVerifyCode(String userPhone) {
        userService.sendVerifyCode(userPhone);
        return this.getHttpResult(ResultCode.DEFAULT_CODE);
    }
}
