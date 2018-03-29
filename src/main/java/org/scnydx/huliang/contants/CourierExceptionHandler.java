package org.scnydx.huliang.contants;

import org.scnydx.huliang.beans.vo.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: CSG
 * @Description: 异常报错处理
 * @Date: Create in 10:32 2018/3/29
 * @Modify by:
 */
@ControllerAdvice
public class CourierExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CourierExceptionHandler.class);

    /**
     * 业务异常报错
     * @param exception
     * @return
     */
    @ExceptionHandler(BusiException.class)
    @ResponseBody
    public HttpResult BusiExceptionCaught(BusiException exception) {
        ResultCode resultCode = exception.getResultCode();
        log.info("业务报错：{}，{}", resultCode.getCode(), resultCode.getMessage());
        return getExceptionResult(resultCode);
    }

    /**
     * 系统异常
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public HttpResult ExceptionCaught(Exception exception) {
        log.error("系统异常：{}", exception.getMessage());
        return getExceptionResult(ResultCode.SYSTEM_ERROR);
    }


    /**
     * 获取异常结果
     * @param resultCode
     * @return
     */
    private HttpResult getExceptionResult(ResultCode resultCode){
        HttpResult httpResult = new HttpResult();
        httpResult.setStatusCode(resultCode.getCode());
        httpResult.setStatusMsg(resultCode.getMessage());
        return httpResult;
    }
}
