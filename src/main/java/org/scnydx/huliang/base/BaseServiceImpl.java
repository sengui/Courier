package org.scnydx.huliang.base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.scnydx.huliang.mappers.MyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @Author: CSG
 * @Description: 基础的业务处理（增删改查）
 * @Date: Create in 17:37 2018/3/12
 * @Modify by:
 */
@Transactional(readOnly = true)
public class BaseServiceImpl<T> implements IBaseService<T>{

    @Autowired
    private MyMapper<T> myMapper;

    /**
     * 添加
     * @param t
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insert(T t) {
        myMapper.insert(t);
    }

    /**
     * 添加返回主键
     * @param t
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int insertKey(T t) {
        return myMapper.insertUseGeneratedKeys(t);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveOrUpdate(T t) {
        if (isUpdate(t)) {
            update(t);
        }else{
            insert(t);
        }
    }

    /**
     * 更新
     * @param t
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void update(T t) {
        myMapper.updateByPrimaryKeySelective(t);
    }

    /**
     * 删除
     * @param t
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(T t) {
        myMapper.delete(t);
    }

    /**
     * 删除通过主键
     * @param id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteById(Object id) {
        myMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取信息通过ID
     * @param id
     * @return
     */
    @Override
    public T findById(Object id) {
        return myMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取所有列表信息
     * @return
     */
    @Override
    public List<T> findAll() {
        return myMapper.selectAll();
    }

    /**
     * 获取所有分页信息
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public Page<T> findAllByPage(int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return myMapper.selectAllPage();
    }

    @Override
    public Page<T> findInfoPage(int pageIndex, int pageSize, T t) {
        PageHelper.startPage(pageIndex, pageSize);
        return myMapper.selectByFilterPage(t);
    }

    /**
     * 判断model 对象是否是更新
     * @param t
     * @return
     */
    protected boolean isUpdate(T t) {
        Class clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {

            //自增主键判断是否为空
            if (field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(GeneratedValue.class)) {
                field.setAccessible(true);
                try {
                    Object obj = field.get(t);
                    if(obj == null){
                        return false;
                    }else{
                        return true;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            //自维护主键判断数据库存不存在
            else if (field.isAnnotationPresent(Id.class)){
                field.setAccessible(true);
                try {
                    Object obj = field.get(t);

                    Object tObj = findById(obj);
                    if(tObj == null){
                        return false;
                    }else{
                        return true;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
