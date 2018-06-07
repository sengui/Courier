package org.scnydx.huliang.controller;

import org.scnydx.huliang.base.BaseController;
import org.scnydx.huliang.beans.po.Version;
import org.scnydx.huliang.beans.vo.HttpResult;
import org.scnydx.huliang.contants.ResultCode;
import org.scnydx.huliang.service.IVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 14:15 2018/4/13
 * @Modify by:
 */
@RestController
@RequestMapping("/version")
public class VersionController extends BaseController<Version> {

    @Autowired
    private IVersionService versionService;

    @RequestMapping("/exam")
    public HttpResult exam(){
        Version version = versionService.findById(1);

        long nowTime = System.currentTimeMillis();
        if (version.getVerTime().getTime() > nowTime) {
            return this.getHttpResult(ResultCode.DEFAULT_CODE, true);
        }else{
            return this.getHttpResult(ResultCode.DEFAULT_CODE, false);
        }
    }
}
