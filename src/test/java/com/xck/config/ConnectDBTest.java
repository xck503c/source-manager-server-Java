package com.xck.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectDBTest {

    @Autowired
    DataSource dataSource;

    @Resource
    RedisSourceProperties oneSource;

    @Resource
    RedisSourceProperties twoSource;

    @Test
    public void connectDB(){
        System.out.println(((DruidDataSource)dataSource).getMaxActive());
    }

    @Test
    public void connectRedisOne(){
        System.out.println(oneSource.getRedisServerIp());
    }

    @Test
    public void connectRedisTwo(){
        System.out.println(twoSource.getMaxWaitMillis());
    }
}
