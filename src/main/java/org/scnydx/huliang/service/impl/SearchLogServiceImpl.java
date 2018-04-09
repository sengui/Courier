package org.scnydx.huliang.service.impl;

import org.scnydx.huliang.base.BaseServiceImpl;
import org.scnydx.huliang.beans.po.SearchLog;
import org.scnydx.huliang.dao.ISearchLogDao;
import org.scnydx.huliang.service.ISearchLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 15:01 2018/4/2
 * @Modify by:
 */
@Service
@Transactional(readOnly = true)
public class SearchLogServiceImpl extends BaseServiceImpl<SearchLog> implements ISearchLogService {

    @Autowired
    private ISearchLogDao searchLogDao;

    @Override
    public List<SearchLog> findListByUserId(Integer userId) {
        return searchLogDao.findListByUserId(userId);
    }

    @Override
    public  List<Map<String, Object>> findListInfoByUserId(Integer userId) {
        return searchLogDao.findListInfoByUserId(userId);
    }
}
