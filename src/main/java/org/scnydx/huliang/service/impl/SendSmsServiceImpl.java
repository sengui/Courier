package org.scnydx.huliang.service.impl;

import org.scnydx.huliang.service.ISendSmsService;
import org.scnydx.huliang.utils.SendSmsThread;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 13:15 2018/3/28
 * @Modify by:
 */
@Service
@PropertySource(value = "classpath:default-params.properties", encoding = "UTF-8")
public class SendSmsServiceImpl implements ISendSmsService {

    //开发者的AK
    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;

    /**
     * 短信签名
     */
    @Value("${aliyun.sms.signName}")
    private String signName;

    /**
     * 模块编号
     */
    @Value("${aliyun.sms.verifycode.template}")
    private String templateCode;

    private static ThreadPoolExecutor executor;

    private ThreadPoolExecutor getExecutor() {
        if (executor != null && !executor.isShutdown()) {
            return executor;
        }else {
            executor = new ThreadPoolExecutor(3, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
            return executor;
        }
    }

    @Override
    public void sendVerifyCodeSms(String phoneNum, String code) {
        SendSmsThread sendSmsThread = new SendSmsThread(accessKeyId, accessKeySecret, phoneNum, signName, templateCode, "{ \"code\":\""+ code +"\"}");
        this.getExecutor().submit(sendSmsThread);
    }
}
