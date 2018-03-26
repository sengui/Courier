package org.scnydx.huliang.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author: CSG
 * @Description: json转化工具类
 *     使用此类注意地方
 *
 *     1. 将对象转化成JSON时候需要提供get,set方法
 *     2. 将JSON字符串转化为对象是需要提供空的构造函数
 *
 * @Date: Create in 15:55 2018/2/1
 * @Modify by:
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //把 null 转化为 “”
        objectMapper.getSerializerProvider().setNullValueSerializer(
                new JsonSerializer<Object>() {
                    @Override
                    public void serialize(
                            Object value, JsonGenerator jgen,
                            SerializerProvider provider) throws IOException {
                        jgen.writeString("");
                    }
                });
        objectMapper
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)//key允许无引号
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)//允许出现特殊字符和转义符
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);//允许出现单引号
    }

    /**
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
     *
     * (1)转换为普通JavaBean：readValue(json,Student.class)
     *
     * (2)转换为List,如List<Student>,将第二个参数传递为Student [].class.
     *
     * 然后使用Arrays.asList();方法把得到的数组转换为特定类型的List
     */
    public static <T> T getObjectFromJSONString(String jsonStr, Class<T> valueType) {
        try {
            return objectMapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * json数组转List
     */
    public static <T> T getColectionFromJsonString(String jsonStr, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 把JavaBean转换为json字符串
     */
    public static String getJSONString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null;
    }
}
