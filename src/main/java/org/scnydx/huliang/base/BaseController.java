package org.scnydx.huliang.base;

import com.github.pagehelper.Page;
import org.scnydx.huliang.contants.BusiException;
import org.scnydx.huliang.contants.ResultCode;
import org.scnydx.huliang.beans.vo.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @Author: CSG
 * @Description: 基础controller
 * @Date: Create in 16:39 2018/3/12
 * @Modify by:
 */
public class BaseController<T> {

    @Autowired
    private IBaseService<T> baseService;

    /**
     * 添加信息
     * @param t
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @RequestMapping("/insert")
    public HttpResult insert(T t, BindingResult bindingResult) throws Exception {
        validBindException(bindingResult);
        baseService.insert(t);
        return getHttpResult(ResultCode.INSERT_CODE);
    }

    @RequestMapping("/saveOrUpdate")
    public HttpResult saveOrUpdate(T t) {
        baseService.saveOrUpdate(t);
        return getHttpResult(ResultCode.DEFAULT_CODE);
    }

    /**
     * 添加信息
     * @param t
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @RequestMapping("/update")
    public HttpResult update(T t, BindingResult bindingResult) throws Exception {
        validBindException(bindingResult);
        baseService.update(t);
        return getHttpResult(ResultCode.UPDATE_CODE);
    }


    /**
     * 删除信息
     * @param t
     * @return
     */
    @RequestMapping("/delete")
    public HttpResult delete(T t) {
        baseService.delete(t);
        return getHttpResult(ResultCode.DELETE_CODE);
    }

    @RequestMapping("/findById")
    public HttpResult findById(T t) {
        T object = baseService.findById(getModelId(t));
        return getHttpResult(ResultCode.DEFAULT_CODE, object);
    }

    /**
     * 获取所有列表信息
     * @return
     */
    @RequestMapping("/findAll")
    public HttpResult findAll() {
        List<T> list = baseService.findAll();
        return getHttpResult(ResultCode.DEFAULT_CODE, list);
    }

    @RequestMapping("/findInfoPage")
    public HttpResult findInfoPage(@RequestParam(defaultValue = "1") int pageIndex,
                                   @RequestParam(defaultValue = "10") int pageSize, T t) {
        Page<T> page = baseService.findInfoPage(pageIndex, pageSize, t);
        return getHttpResult(ResultCode.DEFAULT_CODE, page.toPageInfo());
    }

    /**
     * 获取列表分页信息
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/findAllByPage")
    public HttpResult findAllByPage(@RequestParam(defaultValue = "1") int pageIndex,
                                    @RequestParam(defaultValue = "10") int pageSize) {
        Page<T> page = baseService.findAllByPage(pageIndex, pageSize);
        return getHttpResult(ResultCode.DEFAULT_CODE, page.toPageInfo());
    }



     /**
     * 获取验证异常为业务异常
     * @param bindingResult
     */
    public void validExceptionCaught(BindingResult bindingResult) throws Exception{
        List<ObjectError> errorList = bindingResult.getAllErrors();
        for (ObjectError error : errorList) {
            throw new BusiException(ResultCode.acquireResultCode(Integer.parseInt(error.getDefaultMessage())));
        }
    }

    /**
     * 检查绑定异常
     * @param bindingResult
     * @throws Exception
     */
    protected void validBindException(BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            validExceptionCaught(bindingResult);
        }
    }

    /**
     * 获取返回结果值
     * @param statusCode
     * @param statusMsg
     * @param data
     * @return
     */
    protected HttpResult getHttpResult(Integer statusCode, String statusMsg, Object data) {
        HttpResult httpResult =  new HttpResult();
        httpResult.setStatusCode(statusCode);
        httpResult.setStatusMsg(statusMsg);
        httpResult.setData(data);
        return httpResult;
    }

    /**
     * 获取model 对象的id值
     * @param t
     * @return
     */
    protected Object getModelId(T t) {
        Class clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                try {
                    return field.get(t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    protected HttpResult getHttpResult(Integer statusCode, Object data) {
        return getHttpResult(statusCode, null, data);
    }

    protected HttpResult getHttpResult(Integer statusCode) {
        return getHttpResult(statusCode, null);
    }

    protected HttpResult getHttpResult(ResultCode resultCode, Object data) {
        return getHttpResult(resultCode.getCode(), resultCode.getMessage(), data);
    }

    protected HttpResult getHttpResult(ResultCode resultCode) {
        return getHttpResult(resultCode, null);
    }
}
