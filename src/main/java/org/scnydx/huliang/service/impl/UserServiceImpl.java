package org.scnydx.huliang.service.impl;

import org.scnydx.huliang.base.BaseServiceImpl;
import org.scnydx.huliang.beans.dto.VerifyCode;
import org.scnydx.huliang.beans.po.User;
import org.scnydx.huliang.contants.Contants;
import org.scnydx.huliang.dao.IUserDao;
import org.scnydx.huliang.service.IUserService;
import org.scnydx.huliang.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 10:59 2018/3/26
 * @Modify by:
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void sendVerifyCode(String userPhone) {
        VerifyCode code = new VerifyCode(Utils.getVerifyCode(), userPhone);
        System.out.println("验证码：" + code.getVerifyCode());
        redisTemplate.opsForHash().put(Contants.VERIFYCODE, userPhone, code);
        //redisTemplate.opsForValue().set(userPhone, code);
    }
}
