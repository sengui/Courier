package org.scnydx.huliang.service;

import org.scnydx.huliang.base.IBaseService;
import org.scnydx.huliang.beans.po.SearchLog;

import java.util.List;
import java.util.Map;

/**
 * @Author: CSG
 * @Description: 查询记录service
 * @Date: Create in 14:59 2018/4/2
 * @Modify by:
 */
public interface ISearchLogService extends IBaseService<SearchLog> {

    List<SearchLog> findListByUserId(Integer userId);

    List<Map<String, Object>> findListInfoByUserId(Integer userId);
}
