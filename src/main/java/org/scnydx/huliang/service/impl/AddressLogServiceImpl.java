package org.scnydx.huliang.service.impl;

import org.scnydx.huliang.base.BaseServiceImpl;
import org.scnydx.huliang.beans.po.AddressLog;
import org.scnydx.huliang.dao.IAddressLogDao;
import org.scnydx.huliang.service.IAddressLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 15:01 2018/4/2
 * @Modify by:
 */
@Service
@Transactional(readOnly = true)
public class AddressLogServiceImpl extends BaseServiceImpl<AddressLog> implements IAddressLogService {

    @Autowired
    private IAddressLogDao addressLogDao;

    @Override
    public List<AddressLog> findListByUserIdAndType(Integer userId, String type) {
        return addressLogDao.findListByUserIdAndType(userId, type);
    }
}
