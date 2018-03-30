package org.scnydx.huliang.plugins.mybatis;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.*;

/**
 * Mybatis拦截器，用于处理数据库返回下画线转驼峰
 */
@Intercepts(
        @Signature(
                type = ResultSetHandler.class,
                method = "handleResultSets",
                args = {Statement.class}
        )
)
@Component
public class CameHumpInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //先执行得到结果，再对结果进行处理
        List<Object> list = (List<Object>) invocation.proceed();
        for (Object object : list){
            //如果结果是Map类型，就对Map的 key进行转换
            if(object instanceof Map){
                processMap((Map<String, Object>) object);
            }else{
                break;
            }
        }
        return list;
    }

    /**
     * 处理Map类型
     * @param map
     */
    private void processMap(Map<String, Object> map){
        Set<String> keySet = new HashSet<String>(map.keySet());
        for (String key : keySet){
            //将以大写开头的字符串转换为小写， 如果包含下画线也会吃力为驼峰
            if ((key.charAt(0) >= 'A' && key.charAt(0) <= 'Z') || key.indexOf("_") >= 0){
                Object value = map.get(key);
                map.remove(key);
                map.put(underlineToCamelhump(key), value);
            }
        }
    }

    /**
     * 将下画线风格替换为驼峰风格
     * @param inputString
     * @return
     */
    private String underlineToCamelhump(String inputString){
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++){
            char c = inputString.charAt(i);
            if (c == '_'){
                if (sb.length() > 0){
                    nextUpperCase = true;
                }
            } else{
                if (nextUpperCase){
                    sb.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
        }

        return sb.toString();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
