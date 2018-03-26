package org.scnydx.huliang.controller;

import org.scnydx.huliang.base.BaseController;
import org.scnydx.huliang.beans.po.Company;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: CSG
 * @Description: 公司 controller
 * @Date: Create in 15:53 2018/3/26
 * @Modify by:
 */
@RestController
@RequestMapping("/company")
public class CompanyController extends BaseController<Company> {
}
