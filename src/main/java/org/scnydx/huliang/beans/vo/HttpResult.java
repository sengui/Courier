package org.scnydx.huliang.beans.vo;

/**
 * @Author: CSG
 * @Description: http请求返回结果
 * @Date: Create in 15:48 2018/2/1
 * @Modify by:
 */
public class HttpResult {
    /**
     * 状态码
     */
    private int statusCode;

    /**
     * 状态备注说明
     */
    private String statusMsg;

    /**
     * 数据内容
     */
    private Object data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
