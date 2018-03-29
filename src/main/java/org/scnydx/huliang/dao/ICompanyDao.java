package org.scnydx.huliang.dao;

import org.apache.ibatis.annotations.Select;
import org.scnydx.huliang.beans.po.Company;
import org.scnydx.huliang.mappers.MyMapper;

/**
 * @Author: CSG
 * @Description: 公司dao
 * @Date: Create in 15:01 2018/3/26
 * @Modify by:
 */
public interface ICompanyDao extends MyMapper<Company> {

    @Select("select * from tb_company where com_code = #{comCode}")
    Company findByComCode(String comCode);
}
