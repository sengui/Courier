package org.scnydx.huliang.base;

import com.github.pagehelper.Page;

import java.util.List;

/**
 * @Author: CSG
 * @Description: 基础的业务处理（增删改查）
 * @Date: Create in 17:37 2018/3/12
 * @Modify by:
 */
public interface IBaseService<T> {


    /**
     * 添加
     * @param t
     */
     void insert(T t);

    /**
     * 添加返回主键
     * @param t
     * @return
     */
     int insertKey(T t);

    /**
     * 更新
     * @param t
     */
     void update(T t);

    /**
     * 删除
     * @param t
     */
     void delete(T t);

    /**
     * 删除通过主键
     * @param id
     */
     void deleteById(Object id);

    /**
     * 获取信息通过ID
     * @param id
     * @return
     */
     T findById(Object id);

    /**
     * 获取所有列表信息
     * @return
     */
     List<T> findAll();

    /**
     * 获取所有分页信息
     * @param pageIndex
     * @param pageSize
     * @return
     */
     Page<T> findAllByPage(int pageIndex, int pageSize);
}
