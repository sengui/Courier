package org.scnydx.huliang.service;

import org.scnydx.huliang.base.IBaseService;
import org.scnydx.huliang.beans.po.User;
import org.scnydx.huliang.contants.BusiException;

/**
 * @Author: CSG
 * @Description: 用户 service
 * @Date: Create in 10:59 2018/3/26
 * @Modify by:
 */
public interface IUserService extends IBaseService<User> {

    /**
     * 发送验证码
     * @param userPhone
     */
    void sendVerifyCode(String userPhone);

    /**
     * 注册用户
     * @param user
     */
    void registerUser(User user, String code) throws BusiException;

    /**
     * 用户登录
     * @param user
     * @return
     * @throws Exception
     */
    User userLogin(User user) throws Exception;

    /**
     * 用户手机号是否已经存在
     * @param userPhone
     * @return
     */
    boolean isUserPhone(String userPhone);

    /**
     * 更新密码
     * @param user
     */
    void updatePwd(User user);
}
