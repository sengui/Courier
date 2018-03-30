package org.scnydx.huliang.controller;

import org.scnydx.huliang.base.BaseController;
import org.scnydx.huliang.beans.po.Logistics;
import org.scnydx.huliang.beans.vo.HttpResult;
import org.scnydx.huliang.contants.ResultCode;
import org.scnydx.huliang.service.ILogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: CSG
 * @Description: 物流 controller
 * @Date: Create in 15:55 2018/3/26
 * @Modify by:
 */
@RestController
@RequestMapping("/logistics")
public class LogisticsController extends BaseController<Logistics> {

    @Autowired
    private ILogisticsService logisticsService;


    @RequestMapping("/findLogisticsByExpId")
    public HttpResult findLogisticsByExpId(Integer expId){
        List<Logistics> logisticsList = logisticsService.findLogisticsByExpId(expId);
        return  this.getHttpResult(ResultCode.DEFAULT_CODE, logisticsList);
    }
}
