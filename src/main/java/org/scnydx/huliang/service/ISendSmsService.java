package org.scnydx.huliang.service;

/**
 * @Author: CSG
 * @Description: 发送短信业务
 * @Date: Create in 11:11 2018/3/28
 * @Modify by:
 */
public interface ISendSmsService {

    /**
     * 发送验证码
     * @param phoneNum
     * @param code
     */
    void sendVerifyCodeSms(String phoneNum, String code);
}
