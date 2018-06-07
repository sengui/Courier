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

    /**
     * 发送寄件信息
     * @param phoneNum
     * @param user
     * @param phone
     * @param address
     */
    void sendAdviceSms(String phoneNum, String user, String phone, String address);
}
