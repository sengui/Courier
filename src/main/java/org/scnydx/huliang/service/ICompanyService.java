package org.scnydx.huliang.service;

import org.scnydx.huliang.base.IBaseService;
import org.scnydx.huliang.beans.po.Company;

/**
 * @Author: CSG
 * @Description: 公司service
 * @Date: Create in 15:26 2018/3/26
 * @Modify by:
 */
public interface ICompanyService extends IBaseService<Company> {

    /**
     * 获取公司信息通过编号
     * @param comCode
     * @return
     */
    Company findByComCode(String comCode);
}
