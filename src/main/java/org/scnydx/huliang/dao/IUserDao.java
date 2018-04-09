package org.scnydx.huliang.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.scnydx.huliang.beans.po.User;
import org.scnydx.huliang.mappers.MyMapper;

/**
 * @Author: CSG
 * @Description: 用户dao
 * @Date: Create in 10:58 2018/3/26
 * @Modify by:
 */
public interface IUserDao extends MyMapper<User> {

    @Select("select * from tb_user where user_phone = #{userPhone}")
    User findByUserPhone(String userPhone);

    @Update("update tb_user set user_pwd = #{userPwd} where user_phone=#{userPhone}")
    void updatePwd(User user);

    @Update("update tb_user set user_photo = #{userPhoto} where user_id = #{userId}")
    void updateUserPhoto(@Param("userId")Integer userId, @Param("userPhoto")String userPhoto);
}
