package org.scnydx.huliang.controller;

import org.scnydx.huliang.base.BaseController;
import org.scnydx.huliang.beans.po.User;
import org.scnydx.huliang.beans.vo.HttpResult;
import org.scnydx.huliang.contants.BusiException;
import org.scnydx.huliang.contants.ResultCode;
import org.scnydx.huliang.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public HttpResult getVerifyCode(String userPhone) throws Exception{
        userService.sendVerifyCode(userPhone);
        return this.getHttpResult(ResultCode.DEFAULT_CODE);
    }

    @RequestMapping("/registerUser")
    public HttpResult registerUser(@Valid User user, String code, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()){
            throw new Exception();
        }
        userService.registerUser(user, code);
        return this.getHttpResult(ResultCode.DEFAULT_CODE);
    }

    @RequestMapping("/login")
    public HttpResult login(User user) throws Exception {
        User userInfo = userService.userLogin(user);
        return this.getHttpResult(ResultCode.DEFAULT_CODE, userInfo);
    }

    @RequestMapping("/isUserPhone")
    public HttpResult isUserPhone(String userPhone) {
        boolean result = userService.isUserPhone(userPhone);
        return this.getHttpResult(ResultCode.DEFAULT_CODE, result);
    }

    @RequestMapping("/updatePwd")
    public HttpResult updatePwd(User user){
        userService.updatePwd(user);
        return this.getHttpResult(ResultCode.DEFAULT_CODE);
    }
}
