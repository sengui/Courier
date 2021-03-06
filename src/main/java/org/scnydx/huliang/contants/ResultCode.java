package org.scnydx.huliang.contants;

/**
 * @Author: CSG
 * @Description: 结果编码
 * @Date: Create in 16:45 2018/3/12
 * @Modify by:
 */
public enum ResultCode {

    DEFAULT_CODE(200,"请求成功"),
    INSERT_CODE(200, "添加成功"),
    UPDATE_CODE(200, "更新成功"),
    DELETE_CODE(200, "删除成功"),

    SYSTEM_ERROR(500, "系统错误"),
    VERIFYCODE_ERROR(501, "验证码错误"),
    NOTUSER(502, "用户不存在"),
    PWD_ERROE(503, "密码错误"),
    SELECTTYPE_ERROR(505, "我的订单筛选错误");


    /**
     * 编码
     */
    private Integer code;

    /**
     * 消息信息
     */
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultCode acquireResultCode(int code) {
        for (ResultCode resultCode : ResultCode.values()) {
            if (resultCode.getCode() == code) {
                return resultCode;
            }
        }
        return null;
    }
}
