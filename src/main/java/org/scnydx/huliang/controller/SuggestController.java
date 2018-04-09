package org.scnydx.huliang.controller;

import org.scnydx.huliang.base.BaseController;
import org.scnydx.huliang.beans.po.Suggest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: CSG
 * @Description: 建议反馈 controller
 * @Date: Create in 15:05 2018/4/2
 * @Modify by:
 */
@RestController
@RequestMapping("/suggest")
public class SuggestController extends BaseController<Suggest> {
}
