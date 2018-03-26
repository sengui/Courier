package org.scnydx.huliang.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: CSG
 * @Description: 工具类
 * @Date: Create in 12:21 2018/3/26
 * @Modify by:
 */
public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    /**
     * 获取验证码 (6位数)
     * @return
     */
    public static String getVerifyCode() {
        Integer code =  (int)((Math.random()*9 + 1)*100000);
        logger.info("验证码：%s", code.toString());
        return code.toString();
    }

    /*public static void main(String[] args) {
        System.out.println(Utils.getVerifyCode());
    }*/
}
