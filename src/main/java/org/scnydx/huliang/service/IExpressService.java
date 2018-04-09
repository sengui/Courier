package org.scnydx.huliang.service;

import com.github.pagehelper.Page;
import org.scnydx.huliang.base.IBaseService;
import org.scnydx.huliang.beans.po.Express;
import org.scnydx.huliang.contants.BusiException;

import java.util.Map;

/**
 * @Author: CSG
 * @Description: 快件service
 * @Date: Create in 15:48 2018/3/26
 * @Modify by:
 */
public interface IExpressService extends IBaseService<Express>{

    /**
     * 更新物流信息
     */
    void UpdateExpLogInfo(String type);

    /**
     * 获取我的订单的列表
     * @param expStatus
     * @param selectType
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<Map<String, Object>> findMyExpressList(Integer userId, String userPhone,String expStatus, String selectType, int pageIndex, int pageSize) throws BusiException;

    /**
     * 获取快件详细信息
     * @param expId
     * @return
     */
    Map<String,Object> findExpressInfo(Integer expId);

    /**
     * 查询代取件快递信息
     * @return
     */
    Page<Map<String, Object>> findWaitExpressList(int pageIndex, int pageSize);

    /**
     * 发送快件
     * @param express
     */
    void sendExpress(Express express);

}
