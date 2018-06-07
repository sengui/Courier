package org.scnydx.huliang.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.scnydx.huliang.base.BaseController;
import org.scnydx.huliang.beans.po.User;
import org.scnydx.huliang.beans.vo.HttpResult;
import org.scnydx.huliang.beans.vo.VUser;
import org.scnydx.huliang.contants.BusiException;
import org.scnydx.huliang.contants.ResultCode;
import org.scnydx.huliang.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Author: CSG
 * @Description: 用户controller
 * @Date: Create in 11:00 2018/3/26
 * @Modify by:
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User> {

    @Autowired
    private IUserService userService;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @RequestMapping("/getVerifyCode")
    public HttpResult getVerifyCode(String userPhone) throws Exception{
        userService.sendVerifyCode(userPhone);
        return this.getHttpResult(ResultCode.DEFAULT_CODE);
    }

    @RequestMapping("/registerUser")
    public HttpResult registerUser(@Valid User user, String code, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()){
            throw new Exception();
        }
        userService.registerUser(user, code);
        return this.getHttpResult(ResultCode.DEFAULT_CODE);
    }

    @RequestMapping("/login")
    public HttpResult login(User user) throws Exception {
        User userInfo = userService.userLogin(user);
        return this.getHttpResult(ResultCode.DEFAULT_CODE, userInfo);
    }

    @RequestMapping("/isUserPhone")
    public HttpResult isUserPhone(String userPhone) {
        boolean result = userService.isUserPhone(userPhone);
        return this.getHttpResult(ResultCode.DEFAULT_CODE, result);
    }

    @RequestMapping("/updatePwd")
    public HttpResult updatePwd(User user){
        userService.updatePwd(user);
        return this.getHttpResult(ResultCode.DEFAULT_CODE);
    }

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public HttpResult uploadImg(HttpServletRequest request,
                                @RequestParam("file")MultipartFile file,
                                @RequestParam("userId")Integer userId) throws Exception{
        if (file != null) {
            //上传文件路径
            String path = request.getServletContext().getRealPath("/img/");
            //上传文件名
            String fileName = file.getOriginalFilename();
            String saveName = userId + "." + fileName.split("\\.")[1];

            File filePath = new File(path, saveName);
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdir();
            }
            //将上传文件保存起来
            file.transferTo(filePath);

            userService.updateUserPhoto(userId, saveName);
            return this.getHttpResult(ResultCode.DEFAULT_CODE);
        }else {
            return this.getHttpResult(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 用户登录
     * @param vUser
     * @param session
     * @param model
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @RequestMapping("/webLogin")
    public HttpResult webLogin(@Valid @ModelAttribute(name = "user") VUser vUser, BindingResult bindingResult, HttpSession session, Model model) throws Exception{

        if(bindingResult.hasErrors()){
            return this.getHttpResult(ResultCode.SYSTEM_ERROR, "必填项不能为空");
        }

        if (!vUser.getVerifyCode().toLowerCase().equals(session.getAttribute("verifyCode").toString().toLowerCase())) {
            return this.getHttpResult(ResultCode.SYSTEM_ERROR, "验证码不正确");
        }

        String loginResult = userService.login(vUser);
        if(!"success".equals(loginResult)){
            return this.getHttpResult(ResultCode.SYSTEM_ERROR, loginResult);
        }

        return this.getHttpResult(ResultCode.DEFAULT_CODE);

    }

    /**
     * 生成验证码
     * @param httpServletResponse
     * @param session
     * @throws Exception
     */
    @RequestMapping("/getCheckCode")
    public void getCheckCode(HttpServletResponse httpServletResponse, HttpSession session) throws Exception{
        //生成随机验证码
        String codeText = defaultKaptcha.createText();
        session.setAttribute("verifyCode", codeText);

        //生成验证码图片
        BufferedImage image = defaultKaptcha.createImage(codeText);
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");

        ServletOutputStream out = httpServletResponse.getOutputStream();
        ImageIO.write(image,"jpg", out);
        try {
            out.flush();
        }finally {
            out.close();
        }
    }
}
