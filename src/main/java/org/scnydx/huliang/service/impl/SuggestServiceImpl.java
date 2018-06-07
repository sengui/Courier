package org.scnydx.huliang.service.impl;

import org.scnydx.huliang.base.BaseServiceImpl;
import org.scnydx.huliang.beans.po.Suggest;
import org.scnydx.huliang.dao.ISuggestDao;
import org.scnydx.huliang.service.ISuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 15:02 2018/4/2
 * @Modify by:
 */
@Service
@Transactional(readOnly = true)
public class SuggestServiceImpl extends BaseServiceImpl<Suggest> implements ISuggestService {

    @Autowired
    private ISuggestDao suggestDao;

    @Override
    public List<Map<String, Object>> findSuggestList(String sugStatus) {
        return suggestDao.findSuggestList(sugStatus);
    }
}
