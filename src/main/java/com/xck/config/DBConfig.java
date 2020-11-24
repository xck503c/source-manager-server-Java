package com.xck.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.xck.util.CompositePropertySourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * @Classname DBConfig
 * @Description 数据库配置
 * @Date 2020/11/24 14:08
 * @Created by xck503c
 */
@Configuration
@PropertySource(value="file:${config.path}config/db.yml", encoding = "utf-8", factory = CompositePropertySourceFactory.class)
public class DBConfig {

    @Autowired
    private DBSourceProperties dbSourceProperties;

    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dbSourceProperties.getUrl());
        dataSource.setDriverClassName(dbSourceProperties.getDriverClassName());
        dataSource.setMaxActive(dbSourceProperties.getMaxActive());
        dataSource.setInitialSize(dbSourceProperties.getInitialSize());
        dataSource.setMinIdle(dbSourceProperties.getMinIdle());
        dataSource.setMaxWait(dbSourceProperties.getMaxWait());
        dataSource.setUsername(dbSourceProperties.getUsername());
        dataSource.setPassword(dbSourceProperties.getPassword());

        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setValidationQuery("SELECT 'x'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(180);
        dataSource.setLogAbandoned(true);
        return dataSource;
    }

    @Bean(name = "oneSource")
    @ConfigurationProperties(prefix = "spring.datasource.redis.one")
    public RedisSourceProperties oneSource(){
        return new RedisSourceProperties();
    }

    @Bean(name = "twoSource")
    @ConfigurationProperties(prefix = "spring.datasource.redis.two")
    public RedisSourceProperties twoSource(){
        return new RedisSourceProperties();
    }



//    @Bean
//    @ConfigurationProperties(prefix="spring.datasource")
//    public DataSource dataSource(){
//        return DruidDataSourceBuilder.create().build();
//    }
}
