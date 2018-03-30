package org.scnydx.huliang.service;

import org.scnydx.huliang.base.IBaseService;
import org.scnydx.huliang.beans.po.Logistics;

import java.util.List;

/**
 * @Author: CSG
 * @Description: 物流service
 * @Date: Create in 15:50 2018/3/26
 * @Modify by:
 */
public interface ILogisticsService extends IBaseService<Logistics> {

    /**
     * 获取物流列表
     * @param expId
     * @return
     */
    List<Logistics> findLogisticsByExpId(Integer expId);
}
