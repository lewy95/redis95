package com.xzxy.lewy.redis95.common.config;

import com.xzxy.lewy.redis95.common.util.FastJson2JsonRedisSerializer;
import com.xzxy.lewy.redis95.common.util.RedisTemplate;
import com.xzxy.lewy.redis95.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@PropertySource("classpath:redis.properties")
@Slf4j
public class RedisConfig {

    @Value("${redis.hostName}")
    private String hostName;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.maxIdle}")
    private Integer maxIdle;

    @Value("${redis.timeout}")
    private Integer timeout;

    @Value("${redis.maxTotal}")
    private Integer maxTotal;

    @Value("${redis.maxWaitMillis}")
    private Integer maxWaitMillis;

    @Value("${redis.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;

    @Value("${redis.numTestsPerEvictionRun}")
    private Integer numTestsPerEvictionRun;

    @Value("${redis.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;

    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${redis.testWhileIdle}")
    private boolean testWhileIdle;

    /**
     * Jedis 初始化
     *
     * 在springboot2.0以后，redis默认的底层连接池已经由Jedis更换为Lettuce
     * 本项目底层连接依然使用的jedis，需要进行以下初始化
     */
    @Bean
    public JedisConnectionFactory JedisConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration ();
        redisStandaloneConfiguration.setHostName(hostName);
        redisStandaloneConfiguration.setPort(port);
        //由于我们使用了动态配置库,所以此处省略
        //redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofMillis(timeout));
        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());
        return factory;
    }

    /**
     * 实例化自定义的 RedisTemplate 组件
     *
     * 注意：这里的RedisConnectionFactory指明了连接池的类型
     * 它有两个常用子类：LettuceConnectionFactory 和 JedisConnectionFactory
     * 这里实际上就是注入的就是RedisConnectionFactory的某一个子类，需要自己决定
     */
    @Bean
    public RedisTemplate initRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("RedisTemplate 开始初始化！");
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();

        // 配置序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());// key序列化
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer());// value序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());// hash小键的序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());// hash值的序列化
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        // 注入
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        log.info("RedisTemplate 初始化成功！");

        return redisTemplate;
    }

    /**
     * 引入自定义序列化
     */
    @Bean
    public RedisSerializer fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<Object>(Object.class);
    }

    /**
     * 使用Lettuce作为redis的底层连接池，只需要使用LettuceConnectionFactory就行，spring已完成自动配置
     * 本项目暂时不用
     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
//        // 配置redisTemplate
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(connectionFactory);
//        // key序列化
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        // value序列化
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        // hash小键的序列化
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        // hash值的序列化
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//        // 开启事务
//        redisTemplate.setEnableTransactionSupport(true);
//        // 注入
//        redisTemplate.setConnectionFactory(connectionFactory);
//        return redisTemplate;
//    }

    /**
     * 注入封装RedisTemplate
     */
    @Bean(name = "redisUtil")
    public RedisUtil redisUtil(RedisTemplate redisTemplate) {
        log.info("RedisUtil 初始化成功！");
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }

}
