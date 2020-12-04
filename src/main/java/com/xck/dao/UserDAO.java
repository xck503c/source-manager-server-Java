package com.xck.dao;

import com.xck.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class UserDAO {

    @Autowired
    DataSource dataSource;

    public User findUser(String userId){
        String sql = "select user_id, user_name, user_pwd from user_info where user_id='" + userId + "'";

        SQLiteDataSource ds = (SQLiteDataSource) dataSource;
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try{
            ds.getConnection("root", "root");
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()){
                User user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setUserPwd(rs.getString("user_pwd"));
                user.setUesrName(rs.getString("user_name"));
                return user;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
