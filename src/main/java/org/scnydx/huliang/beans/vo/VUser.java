package org.scnydx.huliang.beans.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author: CSG
 * @Description: 验证用户
 * @Date: Create in 17:29 2018/2/1
 * @Modify by:
 */
public class VUser {

    @NotBlank
    private String userId;

    @NotBlank
    private String userPwd;

    @NotBlank
    private String verifyCode;

    @NotBlank
    private String userType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
