package org.scnydx.huliang.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 15:10 2018/3/1
 * @Modify by:
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{

    @Bean
    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(10000);
        return cacheManager;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(factory);
        //设置序列化工具
        setSerializer(template);
        template.afterPropertiesSet();
        return template;
    }
    /**
     * 序列化工具
     * @param template
     */
    private void setSerializer(RedisTemplate<Object, Object> template) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisSerializer<Object> redisSerializer = new RedisSerializer<Object>() {
            @Override
            public byte[] serialize(Object obj) throws SerializationException {
                if (obj == null) {
                    return null;
                }
                if (obj instanceof String) {
                    return  ((String)obj).getBytes(Charset.forName("UTF8"));
                } else {
                    return  String.valueOf(obj).getBytes(Charset.forName("UTF8"));
                }
            }
            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                return new String(bytes, Charset.forName("UTF8"));
            }
        };

        //设置对应的序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setKeySerializer(redisSerializer);
        template.setHashKeySerializer(redisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

    }
}
