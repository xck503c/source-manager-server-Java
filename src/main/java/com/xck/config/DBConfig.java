package com.xck.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.xck.util.CompositePropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Classname DBConfig
 * @Description 数据库和redis配置
 * @Date 2020/11/24 14:08
 * @Created by xck503c
 */
@Configuration
@PropertySource(value="file:${config.path}config/db.yml", encoding = "utf-8", factory = CompositePropertySourceFactory.class)
public class DBConfig {

    @Bean(name="dbSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DBSourceProperties dbSourceProperties(){
        return new DBSourceProperties();
    }

//    @Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
//    public DruidDataSource dataSource(DBSourceProperties dbSourceProperties){
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl(dbSourceProperties.getUrl());
//        dataSource.setDriverClassName(dbSourceProperties.getDriverClassName());
//        dataSource.setMaxActive(dbSourceProperties.getMaxActive());
//        dataSource.setInitialSize(dbSourceProperties.getInitialSize());
//        dataSource.setMinIdle(dbSourceProperties.getMinIdle());
//        dataSource.setMaxWait(dbSourceProperties.getMaxWait());
//        dataSource.setUsername(dbSourceProperties.getUsername());
//        dataSource.setPassword(dbSourceProperties.getPassword());
//
//        dataSource.setTimeBetweenEvictionRunsMillis(60000);
//        dataSource.setValidationQuery("SELECT 'x'");
//        dataSource.setTestWhileIdle(true);
//        dataSource.setTestOnBorrow(false);
//        dataSource.setTestOnReturn(false);
//        dataSource.setRemoveAbandoned(true);
//        dataSource.setRemoveAbandonedTimeout(180);
//        dataSource.setLogAbandoned(true);
//        return dataSource;
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        //参照StringRedisTemplate内部实现指定序列化器
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.setKeySerializer(keySerializer());
//        redisTemplate.setHashKeySerializer(keySerializer());
//        redisTemplate.setValueSerializer(valueSerializer());
//        redisTemplate.setHashValueSerializer(valueSerializer());
//        return redisTemplate;
//    }
//
//    private RedisSerializer<String> keySerializer(){
//        return new StringRedisSerializer();
//    }
//
//    //使用Jackson序列化器
//    private RedisSerializer<Object> valueSerializer(){
//        return new GenericJackson2JsonRedisSerializer();
//    }

//    @Bean
//    @ConfigurationProperties(prefix="spring.datasource")
//    public DataSource dataSource(){
//        return DruidDataSourceBuilder.create().build();
//    }
}
