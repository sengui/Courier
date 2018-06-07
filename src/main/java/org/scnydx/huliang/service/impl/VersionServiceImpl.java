package org.scnydx.huliang.service.impl;

import org.scnydx.huliang.base.BaseServiceImpl;
import org.scnydx.huliang.beans.po.Version;
import org.scnydx.huliang.service.IVersionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 14:15 2018/4/13
 * @Modify by:
 */
@Service
@Transactional(readOnly = true)
public class VersionServiceImpl extends BaseServiceImpl<Version> implements IVersionService {
}
