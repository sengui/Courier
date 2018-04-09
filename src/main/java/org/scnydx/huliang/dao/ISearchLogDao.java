package org.scnydx.huliang.dao;

import org.apache.ibatis.annotations.Select;
import org.scnydx.huliang.beans.po.SearchLog;
import org.scnydx.huliang.mappers.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: CSG
 * @Description: 查询记录 dao
 * @Date: Create in 14:58 2018/4/2
 * @Modify by:
 */
public interface ISearchLogDao extends MyMapper<SearchLog>{

    @Select("select * from tb_search_log where user_id = #{userId}")
    List<SearchLog> findListByUserId(Integer userId);

    @Select("select s.search_code,s.com_code,s.remark,c.com_name from tb_search_log s " +
            "left join tb_company c on s.com_code = c.com_code")
    List<Map<String, Object>> findListInfoByUserId(Integer userId);

}
