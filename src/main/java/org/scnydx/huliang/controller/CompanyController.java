package org.scnydx.huliang.controller;

import org.scnydx.huliang.base.BaseController;
import org.scnydx.huliang.beans.po.Company;
import org.scnydx.huliang.beans.vo.HttpResult;
import org.scnydx.huliang.contants.ResultCode;
import org.scnydx.huliang.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ICompanyService companyService;

    @RequestMapping("/findByComCode")
    public HttpResult findByComCode(String comCode) {
        Company company = companyService.findByComCode(comCode);
        return this.getHttpResult(ResultCode.DEFAULT_CODE, company);
    }
}
