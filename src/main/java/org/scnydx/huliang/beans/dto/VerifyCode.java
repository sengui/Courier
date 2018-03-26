package org.scnydx.huliang.beans.dto;

import java.io.Serializable;

/**
 * @Author: CSG
 * @Description: 验证码
 * @Date: Create in 11:24 2018/3/26
 * @Modify by:
 */
public class VerifyCode implements Serializable {

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 手机号
     */
    private String userPhone;

    /**
     * 最后操作时间
     */
    private long lastOperTime = System.currentTimeMillis();

    public VerifyCode() {
    }

    public VerifyCode(String verifyCode, String userPhone) {
        this.verifyCode = verifyCode;
        this.userPhone = userPhone;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public long getLastOperTime() {
        return lastOperTime;
    }

    public void setLastOperTime(long lastOperTime) {
        this.lastOperTime = lastOperTime;
    }
}
