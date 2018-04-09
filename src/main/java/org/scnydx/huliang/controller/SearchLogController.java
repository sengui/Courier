package org.scnydx.huliang.controller;

import org.scnydx.huliang.base.BaseController;
import org.scnydx.huliang.beans.po.SearchLog;
import org.scnydx.huliang.beans.vo.HttpResult;
import org.scnydx.huliang.contants.ResultCode;
import org.scnydx.huliang.service.ISearchLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: CSG
 * @Description: 查询记录 controller
 * @Date: Create in 15:04 2018/4/2
 * @Modify by:
 */
@RestController
@RequestMapping("/searchLog")
public class SearchLogController extends BaseController<SearchLog> {

    @Autowired
    private ISearchLogService searchLogService;

    @RequestMapping("/getMySearchList")
    public HttpResult getMySearchList(Integer userId) {
        //List<SearchLog> searchLogList = searchLogService.findListByUserId(userId);
        List<Map<String, Object>> searchLogInfo = searchLogService.findListInfoByUserId(userId);
        return this.getHttpResult(ResultCode.DEFAULT_CODE, searchLogInfo);
    }
}
