package org.scnydx.huliang.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.scnydx.huliang.beans.po.Express;
import org.scnydx.huliang.mappers.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: CSG
 * @Description: 快件dao
 * @Date: Create in 15:03 2018/3/26
 * @Modify by:
 */
public interface IExpressDao extends MyMapper<Express> {

    @Select("select e.*,o.send_user_name,o.rec_user_name,o.send_user_phone,o.rec_user_phone," +
            "o.rec_user_area,o.rec_user_address,c.com_name,c.com_code from tb_express e " +
            "inner join tb_order o on e.order_id = o.order_id and e.exp_status = #{expStatus} " +
            "and (o.user_id = #{userId} or o.send_user_phone=#{userPhone}) " +
            "left join tb_company c on e.com_id = c.com_id ")
    Page<Map<String,Object>> findMySendExpressList(@Param("userId") Integer userId,@Param("userPhone") String userPhone ,@Param("expStatus") String expStatus);

    @Select("select e.*,o.send_user_name,o.rec_user_name,o.send_user_phone,o.rec_user_phone," +
            "o.rec_user_area,o.rec_user_address,c.com_name,c.com_code from tb_express e " +
            "inner join tb_order o on e.order_id = o.order_id and e.exp_status = #{expStatus} " +
            "and  o.rec_user_phone=#{userPhone} " +
            "left join tb_company c on e.com_id = c.com_id ")
    Page<Map<String,Object>> findMyRecExpressList(@Param("userPhone") String userPhone ,@Param("expStatus") String expStatus);

    @Select("select e.*,o.send_user_name,o.rec_user_name,o.send_user_phone,o.rec_user_phone," +
            "o.rec_user_area,o.rec_user_address,c.com_name,c.com_code from tb_express e " +
            "inner join tb_order o on e.order_id = o.order_id and e.exp_status = #{expStatus} " +
            "and (o.user_id = #{userId} or o.send_user_phone=#{userPhone} or o.rec_user_phone=#{userPhone}) " +
            "left join tb_company c on e.com_id = c.com_id ")
    Page<Map<String,Object>> findMyAllExpressList(@Param("userId") Integer userId,@Param("userPhone") String userPhone ,@Param("expStatus") String expStatus);

    @Select("select e.*,o.send_user_name,o.send_user_phone,o.send_user_area,o.send_user_address,o.rec_user_name," +
            "o.rec_user_phone,o.rec_user_area,o.rec_user_address,c.com_name,c.com_code from tb_express e " +
            "inner join tb_order o on e.order_id = o.order_id and e.exp_id = #{expId} " +
            "left join tb_company c on e.com_id = c.com_id")
    Map<String,Object> findExpressInfoByExpId(Integer expId);

    @Select("select e.*,o.send_user_name,o.send_user_phone,o.send_user_area,o.send_user_address," +
            "o.rec_user_name,o.rec_user_phone,o.rec_user_area,o.rec_user_address from tb_express e " +
            "inner join tb_order o on e.order_id = o.order_id and e.exp_status = 'wait'")
    Page<Map<String, Object>> findWaitExpressList();

    @Select("select e.*,o.send_user_name,o.send_user_phone,o.send_user_area,o.send_user_address," +
            "o.rec_user_name,o.rec_user_phone,o.rec_user_area,o.rec_user_address,c.com_name from tb_express e " +
            "inner join tb_order o on e.order_id = o.order_id and (e.exp_status = 'arrival' or e.exp_status = 'transit' ) " +
            "inner join tb_company c on e.com_id = c.com_id")
    Page<Map<String, Object>> findAllExpressPage();

    @Update("update tb_express set exp_status = 'transit', exp_code = #{expCode}, com_id = #{comId} " +
            "where exp_id = #{expId}")
    void sendExpress(Express express);

    @Select("select * from tb_express where exp_status = #{type}")
    List<Express> findListByExpType(String type);
}
