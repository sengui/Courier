package org.scnydx.huliang.contants;

/**
 * @Author: CSG
 * @Description: 业务异常
 * @Date: Create in 16:50 2018/3/12
 * @Modify by:
 */
public class BusiException extends Exception{

    private ResultCode resultCode;

    public BusiException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
