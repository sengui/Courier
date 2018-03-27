package org.scnydx.huliang.scheduler;

import org.scnydx.huliang.service.IExpressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @Author: CSG
 * @Description: 定时任务
 * @Date: Create in 9:47 2018/3/27
 * @Modify by:
 */
@Component
public class Task {
    private static final Logger log = LoggerFactory.getLogger(Task.class);

    @Autowired
    private IExpressService expressService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void getLogisticsInfo() throws Exception{
        log.info("-----------------开始执行获取快件物流信息-----------------");
        expressService.UpdateExpLogInfo();
        log.info("-----------------结束执行获取快件物流信息-----------------");
    }
}
