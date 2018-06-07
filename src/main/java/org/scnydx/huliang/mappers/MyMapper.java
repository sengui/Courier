package org.scnydx.huliang.mappers;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @Author: CSG
 * @Description: 自定义的mapper 通用类
 * @Date: Create in 16:34 2018/2/11
 * @Modify by:
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T>{


   /**
    * 获取所有列表的分页信息
    * @return
    */
   @SelectProvider(
           type = MySelectProvider.class,
           method = "dynamicSQL"
   )
   Page<T> selectAllPage();

   @SelectProvider(
           type = MySelectProvider.class,
           method = "dynamicSQL"
   )
   Page<T> selectByFilterPage(T t);

   @SelectProvider(
           type = MySelectProvider.class,
           method = "dynamicSQL"
   )
   List<T> selectByFilter(T t);

}
