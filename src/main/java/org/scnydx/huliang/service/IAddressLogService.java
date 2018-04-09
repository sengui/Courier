package org.scnydx.huliang.service;

import org.scnydx.huliang.base.IBaseService;
import org.scnydx.huliang.beans.po.AddressLog;

import java.util.List;

/**
 * @Author: CSG
 * @Description: 地址记录service
 * @Date: Create in 14:59 2018/4/2
 * @Modify by:
 */
public interface IAddressLogService extends IBaseService<AddressLog> {

    /**
     * @Author: CSG
     * @Decription: 获取用户信息
     * @Date: Create in 16:00 2018/4/2
     * @Param:
     * @Return:
     */
    List<AddressLog> findListByUserIdAndType(Integer userId, String type);
}
