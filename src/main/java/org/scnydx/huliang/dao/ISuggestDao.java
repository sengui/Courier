package org.scnydx.huliang.dao;

import org.apache.ibatis.annotations.Select;
import org.scnydx.huliang.beans.po.Suggest;
import org.scnydx.huliang.mappers.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: CSG
 * @Description: 建议 dao
 * @Date: Create in 14:58 2018/4/2
 * @Modify by:
 */
public interface ISuggestDao extends MyMapper<Suggest>{

    @Select("select s.*,u.user_name from tb_suggest s inner join tb_user u on " +
            "s.user_id = u.user_id and s.sug_status = #{sugStatus} order by s.create_time desc ")
    List<Map<String, Object>> findSuggestList(String sugStatus);
}
