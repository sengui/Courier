package org.scnydx.huliang.service.impl;

import org.scnydx.huliang.base.BaseServiceImpl;
import org.scnydx.huliang.beans.dto.VerifyCode;
import org.scnydx.huliang.beans.po.User;
import org.scnydx.huliang.contants.BusiException;
import org.scnydx.huliang.contants.Contants;
import org.scnydx.huliang.contants.ResultCode;
import org.scnydx.huliang.dao.IUserDao;
import org.scnydx.huliang.service.ISendSmsService;
import org.scnydx.huliang.service.IUserService;
import org.scnydx.huliang.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

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

    @Autowired
    private ISendSmsService sendSmsService;

    @Override
    public void sendVerifyCode(String userPhone) {
        String code = Utils.getVerifyCode();
        redisTemplate.opsForValue().set(Contants.VERIFYCODE_PREFIX + userPhone, code,Contants.VERIFYCODE_EXPIRE, TimeUnit.MINUTES);
        //sendSmsService.sendVerifyCodeSms(userPhone,code );
        System.out.println("验证码：" + code);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void registerUser(User user, String code) throws BusiException {
        String verifyCode = (String) redisTemplate.opsForValue().get(Contants.VERIFYCODE_PREFIX+ user.getUserPhone());
        if (!code.equals(verifyCode)) {
            throw new BusiException(ResultCode.VERIFYCODE_ERROR);
        }

        userDao.insert(user);
    }

    @Override
    public User userLogin(User user) throws Exception {
        User userInfo = userDao.findByUserPhone(user.getUserPhone());
        //用户不存在
        if (userInfo == null) {
            throw new BusiException(ResultCode.NOTUSER);
        }
        //密码错误
        if (!userInfo.getUserPwd().equals(user.getUserPwd())) {
            throw new BusiException(ResultCode.PWD_ERROE);
        }

        return userInfo;
    }

    @Override
    public boolean isUserPhone(String userPhone) {
        User user = userDao.findByUserPhone(userPhone);
        if(user == null){
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updatePwd(User user) {
        userDao.updatePwd(user);
    }
}
