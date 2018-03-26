package org.scnydx.huliang.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.scnydx.huliang.beans.vo.HttpResult;
import org.scnydx.huliang.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: CSG
 * @Description: 请求访问日志
 * @Date: Create in 15:14 2018/2/1
 * @Modify by:
 */
@Aspect
@Configuration
public class RequestLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestLogAspect.class);

    //定义切点
    @Pointcut("execution(* org.scnydx.huliang.controller.*Controller.*(..))")
    public void executePoint(){}

    //切点切入
    @Around("executePoint()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString;

        //请求不同获取参数方式不同
        if("POST".equals(method.toUpperCase())){
            queryString = JsonUtil.getJSONString(request.getParameterMap());
        } else{
            queryString = request.getQueryString();
        }
        long threadId = Thread.currentThread().getId();
        logger.info("请求开始, thread: {},url {},method: {}, uri: {}, params: {}",threadId ,url, method, uri, queryString);

        //请求开始时间
        long startTime = System.currentTimeMillis();
        //执行请求内容
        Object object = joinPoint.proceed();
        long spendTime = System.currentTimeMillis() - startTime;

        //请求结束结果日志
        if(object == null){
            logger.info("请求结束，thread: {},spendTime {},controller: {}, method: {}, result: {}",
                    threadId ,spendTime + "ms", joinPoint.getTarget().getClass(), joinPoint.getSignature(), queryString);
            return null;
        } else if (HttpResult.class.isAssignableFrom(object.getClass()) || HttpResult.class.equals(object.getClass())) {
            HttpResult result = (HttpResult) object;
            logger.info("请求结束，thread: {},spendTime {},controller: {}, method: {}, result: {}",
                    threadId ,spendTime + "ms", joinPoint.getTarget().getClass(), joinPoint.getSignature(), queryString, JsonUtil.getJSONString(result));
            return result;
        } else if (object.getClass().equals(String.class)) {
            logger.info("请求结束，thread: {},spendTime {},controller: {}, method: {}, result: {}",
                    threadId, spendTime + "ms", joinPoint.getTarget().getClass(), joinPoint.getSignature(), queryString, JsonUtil.getJSONString(object));
            return object;
        } else {
            logger.info("请求结束，thread: {},spendTime {},controller: {}, method: {}, result: {}",
                    threadId ,spendTime + "ms", joinPoint.getTarget().getClass(), joinPoint.getSignature(), queryString);
            return object;
        }
    }
}
