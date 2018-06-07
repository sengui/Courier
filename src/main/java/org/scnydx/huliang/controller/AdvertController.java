package org.scnydx.huliang.controller;

import org.scnydx.huliang.base.BaseController;
import org.scnydx.huliang.beans.po.Advert;
import org.scnydx.huliang.beans.vo.HttpResult;
import org.scnydx.huliang.contants.ResultCode;
import org.scnydx.huliang.service.IAdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 23:52 2018/4/20
 * @Modify by:
 */
@RestController
@RequestMapping("/advert")
public class AdvertController extends BaseController<Advert> {

    @Autowired
    private IAdvertService advertService;

    @RequestMapping("/saveOrUpdateAdvert")
    public HttpResult saveOrUpdateAdvert(HttpServletRequest request,
                                         @RequestParam("file")MultipartFile file,Advert advert) throws IOException {

        if (file != null) {
            //上传文件路径
            String path = request.getServletContext().getRealPath("/img/");
            //上传文件名
            String fileName = file.getOriginalFilename();
            String saveName = new Date().getTime() + "." + fileName.split("\\.")[1];

            File filePath = new File(path, saveName);
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdir();
            }
            //将上传文件保存起来
            file.transferTo(filePath);

            advert.setAdImg(saveName);

        }

        advertService.saveOrUpdate(advert);
        return this.getHttpResult(ResultCode.DEFAULT_CODE);
    }
}
