package org.scnydx.huliang.service.impl;

import org.scnydx.huliang.base.BaseServiceImpl;
import org.scnydx.huliang.beans.po.Logistics;
import org.scnydx.huliang.dao.ILogisticsDao;
import org.scnydx.huliang.service.ILogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 15:51 2018/3/26
 * @Modify by:
 */
@Service
@Transactional(readOnly = true)
public class LogisticsServiceImpl extends BaseServiceImpl<Logistics> implements ILogisticsService {

    @Autowired
    private ILogisticsDao logisticsDao;

    @Override
    public List<Logistics> findLogisticsByExpId(Integer expId) {
        return logisticsDao.findLogisticsByExpId(expId);
    }
}
