package org.scnydx.huliang.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.scnydx.huliang.base.BaseServiceImpl;
import org.scnydx.huliang.beans.po.Express;
import org.scnydx.huliang.beans.po.Order;
import org.scnydx.huliang.dao.IExpressDao;
import org.scnydx.huliang.dao.IOrderDao;
import org.scnydx.huliang.service.IOrderService;
import org.scnydx.huliang.service.ISendSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource(value = "classpath:default-params.properties", encoding = "UTF-8")
public class OrderServiceImpl extends BaseServiceImpl<Order> implements IOrderService {

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private IExpressDao expressDao;

    @Autowired
    private ISendSmsService sendSmsService;

    @Value("${my.sms.adminPhone}")
    private String adminPhone;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertOrder(Order order) {
        orderDao.insertUseKey(order);
        sendSmsService.sendAdviceSms(adminPhone,order.getSendUserName(), order.getSendUserPhone(), order.getSendUserArea() + " " + order.getSendUserAddress());
        if (!StringUtils.isEmpty(order.getUserId())) {
            Express express = new Express();
            express.setOrderId(order.getOrderId());
            express.setExpStatus("wait");
            expressDao.insert(express);
        }
    }

}
