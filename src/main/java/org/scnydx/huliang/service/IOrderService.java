package org.scnydx.huliang.service;

import com.github.pagehelper.Page;
import org.scnydx.huliang.base.IBaseService;
import org.scnydx.huliang.beans.po.Order;

import java.util.Map;

/**
 * @Author: CSG
 * @Description: 订单 service
 * @Date: Create in 15:52 2018/3/26
 * @Modify by:
 */
public interface IOrderService extends IBaseService<Order> {

    /**
     * 添加订单信息
     * @param order
     */
    void insertOrder(Order order);





}
