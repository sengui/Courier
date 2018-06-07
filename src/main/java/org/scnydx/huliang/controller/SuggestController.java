package org.scnydx.huliang.controller;

import org.scnydx.huliang.base.BaseController;
import org.scnydx.huliang.beans.po.Suggest;
import org.scnydx.huliang.beans.vo.HttpResult;
import org.scnydx.huliang.contants.ResultCode;
import org.scnydx.huliang.service.ISuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: CSG
 * @Description: 建议反馈 controller
 * @Date: Create in 15:05 2018/4/2
 * @Modify by:
 */
@RestController
@RequestMapping("/suggest")
public class SuggestController extends BaseController<Suggest> {

    @Autowired
    private ISuggestService suggestService;

    @RequestMapping("/findSuggestList")
    public HttpResult findSuggestList(String sugStatus) {
        List<Map<String, Object>> sugList = suggestService.findSuggestList(sugStatus);
        return this.getHttpResult(ResultCode.DEFAULT_CODE, sugList);
    }
}
