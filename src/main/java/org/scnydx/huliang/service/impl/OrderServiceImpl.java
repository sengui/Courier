package org.scnydx.huliang.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.scnydx.huliang.base.BaseServiceImpl;
import org.scnydx.huliang.beans.po.Express;
import org.scnydx.huliang.beans.po.Order;
import org.scnydx.huliang.dao.IExpressDao;
import org.scnydx.huliang.dao.IOrderDao;
import org.scnydx.huliang.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 15:52 2018/3/26
 * @Modify by:
 */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl extends BaseServiceImpl<Order> implements IOrderService {

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private IExpressDao expressDao;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertOrder(Order order) {
        orderDao.insertUseKey(order);
        if (!StringUtils.isEmpty(order.getUserId())) {
            Express express = new Express();
            express.setOrderId(order.getOrderId());
            express.setExpStatus("wait");
            expressDao.insert(express);
        }
    }

}
