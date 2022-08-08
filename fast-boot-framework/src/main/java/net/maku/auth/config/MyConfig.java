package net.maku.auth.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.github.benmanes.caffeine.cache.CacheLoader;
import net.maku.auth.constant.CacheName;
import net.maku.auth.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

@Configuration
public class MyConfig {
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 创建基于Caffeine的Cache Manager
     *
     * @return
     */
    @Bean("caffeineCacheManager")
    @Primary   // 这个注解可以实现多个CacheManager的配置
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                // 设置初始容量
                .initialCapacity(5) //初始大小
                // 失效策略 写入后按时间失效
                .refreshAfterWrite(jwtProperties.getExpirationTime(), TimeUnit.SECONDS); //写入/更新之后0.5小时过期
        caffeineCacheManager.setCaffeine(caffeine);
        caffeineCacheManager.setCacheLoader(cacheLoader());
        caffeineCacheManager.setCacheNames(CacheName.getCacheNames());
        return caffeineCacheManager;
    }

    @Bean
    public CacheLoader<Object, Object> cacheLoader() {
        return new CacheLoader<Object, Object>() {
            @Override
            public Object load(Object key) throws Exception {
                return null;
            }

            // 达到配置文件中的refreshAfterWrite所指定的时候回处罚这个事件方法
            @Override
            public Object reload(Object key, Object oldValue) throws Exception {
                return oldValue;
            }
        };
    }

    /////////////////////////////////////////////////////////////////////  redis配置

    /**
     * 往容器中添加RedisTemplate对象，设置序列化方式
     * @param redisConnectionFactory
     * @return
     * @throws UnknownHostException
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(valueSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(valueSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 往容器中添加RedisCacheManager容器，并设置序列化方式
     * @param redisConnectionFactory
     * @return
     */
    @Bean("redisCacheManager")
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()));
        redisCacheConfiguration.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    /**
     * 使用Jackson序列化器
     * @return
     */
    private RedisSerializer<Object> valueSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        /**
         * 这一句必须要，作用是序列化时将对象全类名一起保存下来
         * 设置之后的序列化结果如下：
         *  [
         *   "com.dxy.cache.pojo.Dept",
         *   {
         *     "pid": 1,
         *     "code": "11",
         *     "name": "财务部1"
         *   }
         * ]
         *
         * 不设置的话，序列化结果如下，将无法反序列化
         *
         *  {
         *     "pid": 1,
         *     "code": "11",
         *     "name": "财务部1"
         *   }
         */
//                objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //因为上面那句代码已经被标记成作废，因此用下面这个方法代替，仅仅测试了一下，不知道是否完全正确
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }
}