package org.scnydx.huliang.service.impl;

import org.scnydx.huliang.base.BaseServiceImpl;
import org.scnydx.huliang.beans.po.Company;
import org.scnydx.huliang.service.ICompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 15:27 2018/3/26
 * @Modify by:
 */
@Service
@Transactional(readOnly = true)
public class CompanyServiceImpl extends BaseServiceImpl<Company> implements ICompanyService {
}
