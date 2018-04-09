package org.scnydx.huliang.controller;

import org.scnydx.huliang.base.BaseController;
import org.scnydx.huliang.beans.po.AddressLog;
import org.scnydx.huliang.beans.vo.HttpResult;
import org.scnydx.huliang.contants.ResultCode;
import org.scnydx.huliang.service.IAddressLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: CSG
 * @Description: 地址记录 controller
 * @Date: Create in 15:03 2018/4/2
 * @Modify by:
 */
@RestController
@RequestMapping("/addressLog")
public class AddressLogController extends BaseController<AddressLog> {

    @Autowired
    private IAddressLogService addressLogService;

    @RequestMapping("/getUserAddressLog")
    public HttpResult getUserAddressLog(Integer userId, String type){
        List<AddressLog> addressLogList = addressLogService.findListByUserIdAndType(userId, type);
        return this.getHttpResult(ResultCode.DEFAULT_CODE, addressLogList);
    }
}
