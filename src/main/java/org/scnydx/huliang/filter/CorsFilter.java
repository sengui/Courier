package org.scnydx.huliang.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: CSG
 * @Description: 设置header信息，实现跨域访问
 * @Date: Create in 17:07 2018/3/8
 * @Modify by:
 */
@WebFilter(filterName="corsFilter",urlPatterns="/*")
public class CorsFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);
    private String allowOrigin = "*";
    private String allowMethods = "GET,POST,PUT,DELETE,OPTIONS";
    private String allowCredentials = "true";
    private String allowHeaders = "x-requested-with";
    private String exposeHeaders;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 从 @Bean 获取初始信息
       /* allowOrigin = filterConfig.getInitParameter("allowOrigin");
        allowMethods = filterConfig.getInitParameter("allowMethods");
        allowCredentials = filterConfig.getInitParameter("allowCredentials");
        allowHeaders = filterConfig.getInitParameter("allowHeaders");*/
        exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
        allowOrigin = "*";
        allowMethods = "GET,POST,PUT,DELETE,OPTIONS";
        allowCredentials = "true";
        allowHeaders = "x-requested-with";

        logger.info("allowOrigin:"+allowOrigin);

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (!StringUtils.isEmpty(allowOrigin)) {
            List<String> allowOriginList = Arrays.asList(allowOrigin.split(","));
            if (allowOriginList.size() > 0) {
                String currentOrigin = request.getHeader("Origin");
                if (allowOriginList.contains(currentOrigin) || allowOriginList.contains("*")) {
                    response.setHeader("Access-Control-Allow-Origin", currentOrigin);
                }
            }
        }
        if (!StringUtils.isEmpty(allowMethods)) {
            response.setHeader("Access-Control-Allow-Methods", allowMethods);
        }
        if (!StringUtils.isEmpty(allowCredentials)) {
            response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
        }
        if (!StringUtils.isEmpty(allowHeaders)) {
            response.setHeader("Access-Control-Allow-Headers", allowHeaders);
        }
        if (!StringUtils.isEmpty(exposeHeaders)) {
            response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
