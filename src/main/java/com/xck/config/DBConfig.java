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
import org.sqlite.SQLiteDataSource;

/**
 * @Classname DBConfig
 * @Description 数据库和redis配置
 * @Date 2020/11/24 14:08
 * @Created by xck503c
 */
@Configuration
@PropertySource(value="file:${config.path}config/db.yml", encoding = "utf-8"
        , factory = CompositePropertySourceFactory.class)
public class DBConfig {

    @Bean(name="dbSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DBSourceProperties dbSourceProperties(){
        return new DBSourceProperties();
    }

    @Bean(name = "dataSource")
    public SQLiteDataSource dataSource(DBSourceProperties dbSourceProperties){
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(dbSourceProperties.getUrl());
        return dataSource;
    }
}
