package com.xck.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectDBTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void connectDB(){
        System.out.println(((DruidDataSource)dataSource).getMaxActive());
    }

    @Test
    public void connectRedisCache(){
        redisTemplate.opsForValue().set("a", "b");
    }
}
