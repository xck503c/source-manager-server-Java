package com.xck.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectDBTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void connectDruidDB(){
        System.out.println(((DruidDataSource)dataSource).getMaxActive());
    }

    @Test
    public void connectRedisCache(){
        redisTemplate.opsForValue().set("a", "b");
    }

    @Test
    public void connectSQLiteDB() throws SQLException {
        SQLiteDataSource ds = (SQLiteDataSource) dataSource;
        Connection connection = ds.getConnection("root", "root");
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("select 'X'");
        while (rs.next()){
            System.out.println(rs.getString(1));
        }
        statement.close();
        connection.close();
    }

}
