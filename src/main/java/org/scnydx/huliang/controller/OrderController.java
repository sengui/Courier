package org.scnydx.huliang.controller;

import org.scnydx.huliang.base.BaseController;
import org.scnydx.huliang.beans.po.Order;
import org.scnydx.huliang.beans.vo.HttpResult;
import org.scnydx.huliang.contants.ResultCode;
import org.scnydx.huliang.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: CSG
 * @Description: 订单 controller
 * @Date: Create in 15:56 2018/3/26
 * @Modify by:
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController<Order> {

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/insertOrder")
    public HttpResult insertOrder(Order order) {
        orderService.insertOrder(order);
        return this.getHttpResult(ResultCode.DEFAULT_CODE);
    }
}
