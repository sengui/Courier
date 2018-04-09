package org.scnydx.huliang.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.scnydx.huliang.beans.po.Order;
import org.scnydx.huliang.mappers.MyMapper;
import tk.mybatis.mapper.provider.SpecialProvider;

/**
 * @Author: CSG
 * @Description: 订单 dao
 * @Date: Create in 15:25 2018/3/26
 * @Modify by:
 */
public interface IOrderDao extends MyMapper<Order> {

    @Options(
            useGeneratedKeys = true,
            keyProperty = "orderId"
    )
   @Insert("insert into tb_order(order_name,send_user_name,send_user_phone,send_user_area,send_user_address," +
           "rec_user_name,rec_user_phone,rec_user_area,rec_user_address,order_type,user_id) values(#{orderName},#{sendUserName},#{sendUserPhone}," +
           "#{sendUserArea},#{sendUserAddress},#{recUserName},#{recUserPhone},#{recUserArea},#{recUserAddress},#{orderType},#{userId})")
    int insertUseKey(Order order);
}
