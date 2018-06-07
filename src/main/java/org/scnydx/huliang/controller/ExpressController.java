package org.scnydx.huliang.controller;

import com.github.pagehelper.Page;
import org.scnydx.huliang.base.BaseController;
import org.scnydx.huliang.beans.po.Express;
import org.scnydx.huliang.beans.vo.HttpResult;
import org.scnydx.huliang.contants.BusiException;
import org.scnydx.huliang.contants.ResultCode;
import org.scnydx.huliang.service.IExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: CSG
 * @Description: 快件 controller
 * @Date: Create in 15:55 2018/3/26
 * @Modify by:
 */
@RestController
@RequestMapping("/express")
public class ExpressController extends BaseController<Express> {

    @Autowired
    private IExpressService expressService;

    @RequestMapping("/findExpressList")
    public HttpResult findExpressList(Integer userId, String userPhone,
                                      String expStatus, String selectType,
                                      @RequestParam(defaultValue = "1") int pageIndex,
                                      @RequestParam(defaultValue = "10") int pageSize) throws BusiException {

        Page<Map<String, Object>> expressList = expressService.findMyExpressList(userId,userPhone,
                expStatus, selectType, pageIndex, pageSize);
        return this.getHttpResult(ResultCode.DEFAULT_CODE, expressList.toPageInfo());
    }

    @RequestMapping("/findExpressInfo")
    public HttpResult findExpressInfo(Integer expId) {
        Map<String, Object> expMap = expressService.findExpressInfo(expId);
        return  this.getHttpResult(ResultCode.DEFAULT_CODE, expMap);
    }

    @RequestMapping("/findWaitExpressList")
    public HttpResult findWaitExpressList(@RequestParam(defaultValue = "1") int pageIndex,
                                          @RequestParam(defaultValue = "10") int pageSize) {
        Page<Map<String, Object>> expressList = expressService.findWaitExpressList(pageIndex, pageSize);
        return this.getHttpResult(ResultCode.DEFAULT_CODE, expressList.toPageInfo());
    }

    @RequestMapping("/findAllExpressPage")
    public HttpResult findAllExpressPage(@RequestParam(defaultValue = "1")int pageIndex,
                                         @RequestParam(defaultValue = "10")int pageSize) {
        Page<Map<String, Object>> expressPage = expressService.findAllExpressPage(pageIndex, pageSize);
        return this.getHttpResult(ResultCode.DEFAULT_CODE, expressPage.toPageInfo());
    }

    @RequestMapping("/sendExpress")
    public HttpResult sendExpress(Express express) {
        expressService.sendExpress(express);
        return this.getHttpResult(ResultCode.DEFAULT_CODE);
    }
}
