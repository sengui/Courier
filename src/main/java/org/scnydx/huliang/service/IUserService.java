package org.scnydx.huliang.service;

import org.scnydx.huliang.base.IBaseService;
import org.scnydx.huliang.beans.po.User;

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
}
