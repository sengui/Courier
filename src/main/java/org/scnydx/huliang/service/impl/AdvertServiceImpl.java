package org.scnydx.huliang.service.impl;

import org.scnydx.huliang.base.BaseServiceImpl;
import org.scnydx.huliang.beans.po.Advert;
import org.scnydx.huliang.service.IAdvertService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 23:51 2018/4/20
 * @Modify by:
 */
@Service
@Transactional(readOnly = true)
public class AdvertServiceImpl extends BaseServiceImpl<Advert> implements IAdvertService {
}
