package org.scnydx.huliang.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.scnydx.huliang.beans.po.Logistics;
import org.scnydx.huliang.mappers.MyMapper;

import java.util.List;

/**
 * @Author: CSG
 * @Description: 物流dao
 * @Date: Create in 15:24 2018/3/26
 * @Modify by:
 */
public interface ILogisticsDao extends MyMapper<Logistics> {

    @Delete("delete from tb_logistics where exp_id=#{expId}")
    void delLogisticsByExpId(Integer expId);

    @Select("select * from tb_logistics where exp_id = #{expId}")
    List<Logistics> findLogisticsByExpId(Integer expId);
}
