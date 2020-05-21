package com.you.config;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 
    * @ClassName: RedisConfig  
    * @Description: redis配置类  
    * @author you  
    * @date 2020年3月23日  
    *
 */


@Configuration
@EnableCaching //启用缓存，这个注解很重要
//继承CachingConfigurerSupport,为了自定义生成KEY的策略,可以不继承
public class RedisConfig extends CachingConfigurerSupport
{
    /*
     * 引入lettuce客户端连接工厂
     */
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;
    //缓存过期时间
    private Duration timeToLive = Duration.ofDays(1);
    
    /**
     * 自定义缓存key的生成策略：包名+方法名+参数列表
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getName());  // 类目
                sb.append(method.getName());   // 方法名
                for (Object obj : params) {
                    sb.append(obj.toString());  // 参数名
                }
                return sb.toString();
            }
        };
    }

    /**
     * 
        * @Title: cacheManager  
        * @Description: 自定义缓存管理器  
        * @param @param factory
        * @param @return    参数  
        * @return CacheManager    返回类型  
        * @throws
     */
    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        //解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        config = config.entryTtl(this.timeToLive)  // 设置缓存的默认过期时间，使用Duration设置
                 // key采用String的序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                 // value序列化方式采用jackson
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                 // 不启用Redis的键前缀
                 //.disableKeyPrefix()
                 // 设置Redis的键前缀
                 //.prefixKeysWith("redis_test")
                .disableCachingNullValues();  // 不缓存空值
        
        // 设置一个初始化的缓存空间set集合
        Set<String> cacheNames =  new HashSet<>();
        cacheNames.add("my-redis-cache1");
        cacheNames.add("my-redis-cache2");

        // 对每个缓存空间使用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        // 自定义缓存空间名称
        configMap.put("my-redis-cache1", config);
        // 设置my-redis-cache2缓存空间的过期时间
        configMap.put("my-redis-cache2", config.entryTtl(Duration.ofSeconds(120)));
   
        // 使用自定义的缓存配置初始化一个cacheManager
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory) 
                .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    }
    
    /**
     * 
        * @Title: redisTemplate  
        * @Description: redisTemplate配置，redisTemplate模板提供给其他类对redis数据库进行操作  
        * @param @param lettuceConnectionFactory
        * @param @return    参数  
        * @return RedisTemplate<String,Object>    返回类型  
        * @throws
     */
    @Bean(name="redisTemplate")
    public RedisTemplate<String,Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisTemplate<String,Object> template = new RedisTemplate <>();
        template.setConnectionFactory(lettuceConnectionFactory);

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 在使用注解@Bean返回RedisTemplate的时候，同时配置hashKey与hashValue的序列化方式。
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}
