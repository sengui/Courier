package org.scnydx.huliang.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.scnydx.huliang.beans.po.Express;
import org.scnydx.huliang.mappers.MyMapper;

import java.util.Map;

/**
 * @Author: CSG
 * @Description: 快件dao
 * @Date: Create in 15:03 2018/3/26
 * @Modify by:
 */
public interface IExpressDao extends MyMapper<Express> {

    @Select("select e.*,o.send_user_name,o.rec_user_name,c.com_name,c.com_code from tb_express e " +
            "inner join tb_order o on e.order_id = o.order_id and e.exp_status = #{expStatus} " +
            "and (o.user_id = #{userId} or o.send_user_phone=#{userPhone}) " +
            "left join tb_company c on e.com_id = c.com_id ")
    Page<Map<String,Object>> findMySendExpressList(@Param("userId") Integer userId,@Param("userPhone") String userPhone ,@Param("expStatus") String expStatus);

    @Select("select e.*,o.send_user_name,o.rec_user_name,c.com_name,c.com_code from tb_express e " +
            "inner join tb_order o on e.order_id = o.order_id and e.exp_status = #{expStatus} " +
            "and  o.rec_user_phone=#{userPhone} " +
            "left join tb_company c on e.com_id = c.com_id ")
    Page<Map<String,Object>> findMyRecExpressList(@Param("userPhone") String userPhone ,@Param("expStatus") String expStatus);

    @Select("select e.*,o.send_user_name,o.rec_user_name,c.com_name,c.com_code from tb_express e " +
            "inner join tb_order o on e.order_id = o.order_id and e.exp_status = #{expStatus} " +
            "and (o.user_id = #{userId} or o.send_user_phone=#{userPhone} or o.rec_user_phone=#{userPhone}) " +
            "left join tb_company c on e.com_id = c.com_id ")
    Page<Map<String,Object>> findMyAllExpressList(@Param("userId") Integer userId,@Param("userPhone") String userPhone ,@Param("expStatus") String expStatus);
}
