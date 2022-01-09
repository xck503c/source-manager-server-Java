package com.xck.dao;

import com.xck.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class UserDAO {

    @Autowired
    SQLiteDataSource dataSource;

    /**
     * 根据userId获得账户信息
     * @param userId
     * @return
     */
    public User findUser(String userId){
        String sql = "select user_id, user_name, user_pwd, white_ips from user_info where user_id=?";

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            connection = dataSource.getConnection("root", "root");
            ps = connection.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setUserPwd(rs.getString("user_pwd"));
                user.setUesrName(rs.getString("user_name"));
                user.setWhiteIps(rs.getString("white_ips"));
                return user;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }

        return null;
    }
}
