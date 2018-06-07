package org.scnydx.huliang.service;

import org.scnydx.huliang.base.IBaseService;
import org.scnydx.huliang.beans.po.Suggest;
import org.scnydx.huliang.dao.ISuggestDao;

import java.util.List;
import java.util.Map;

/**
 * @Author: CSG
 * @Description: 意见反馈service
 * @Date: Create in 15:00 2018/4/2
 * @Modify by:
 */
public interface ISuggestService extends IBaseService<Suggest> {


    List<Map<String, Object>> findSuggestList(String sugStatus);
}
