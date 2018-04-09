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

    //每半小时更新正在运输快件
    @Scheduled(cron = "0 0/30 * * * ?")
    public void refreshRunningLogisticsInfo() throws Exception{
        log.info("-----------------开始更新正在运输快件物流信息-----------------");
        expressService.UpdateExpLogInfo("wait");
        log.info("-----------------结束更新正在运输快件物流信息-----------------");
    }


    //每3小时更新未发出快件信息
    @Scheduled(cron = "0 0 0/3 * * ?")
    public void refreshWaitLogisticsInfo() throws Exception{
        log.info("-----------------开始更新等待运输快件物流信息-----------------");
        expressService.UpdateExpLogInfo("transit");
        log.info("-----------------结束更新等待运输快件物流信息-----------------");
    }
}
