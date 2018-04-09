package org.scnydx.huliang.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.scnydx.huliang.beans.po.AddressLog;
import org.scnydx.huliang.mappers.MyMapper;

import java.util.List;

/**
 * @Author: CSG
 * @Description: 地址记录dao
 * @Date: Create in 14:57 2018/4/2
 * @Modify by:
 */
public interface IAddressLogDao extends MyMapper<AddressLog> {

    @Select("select * from tb_address_log where user_id = #{userId} and type = #{type}")
    List<AddressLog> findListByUserIdAndType(@Param("userId") Integer userId,@Param("type")String type);
}
