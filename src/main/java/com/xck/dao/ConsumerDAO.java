package com.xck.dao;

import com.xck.model.ConsumerType;
import com.xck.model.User;
import com.xck.model.req.ConsumeTimeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ConsumerDAO {

    @Autowired
    SQLiteDataSource dataSource;

    /**
     * 判断是否存在
     * @return
     */
    public Map<Integer, ConsumerType> selectConsumeTypes(){
        Map<Integer, ConsumerType> map = new HashMap<>();

        String sql = "select type_id, type_name, parent_type_id from consume_type";

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            connection = dataSource.getConnection("root", "root");
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                ConsumerType consumerType = new ConsumerType();
                consumerType.setTypeId(rs.getInt("type_id"));
                consumerType.setTypeName(rs.getString("type_name"));
                consumerType.setParentTypeId(rs.getInt("parent_type_id"));
                map.put(consumerType.getTypeId(), consumerType);
            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
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

        return map;
    }

    public List<ConsumeTimeReq> selectTimeConsumes(){
        List<ConsumeTimeReq> list = new ArrayList<>();

        String sql = "select user_id, consume_id, product_name, start_time, end_time, use_time, comment, consume_type, insert_time from user_consume_time_record";

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            connection = dataSource.getConnection("root", "root");
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                ConsumeTimeReq consumeTimeReq = new ConsumeTimeReq();
                consumeTimeReq.setUserId(rs.getString("user_id"));
                consumeTimeReq.setConsumeId(rs.getString("consume_id"));
                consumeTimeReq.setProductName(rs.getString("product_name"));
                consumeTimeReq.setStartTime(rs.getString("start_time"));
                consumeTimeReq.setEndTime(rs.getString("end_time"));
                consumeTimeReq.setUseTime(rs.getInt("use_time"));
                consumeTimeReq.setComment(rs.getString("comment"));
                consumeTimeReq.setConsumeType(rs.getInt("consume_type"));
                consumeTimeReq.setInsertTime(rs.getString("insert_time"));
                list.add(consumeTimeReq);
            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
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

        return list;
    }

    public boolean addTimeConsume(ConsumeTimeReq consumeTimeReq){
        boolean result = false;

        String sql = "INSERT INTO user_consume_time_record (id, user_id, consume_id, product_name, start_time, end_time, use_time, comment, consume_type, insert_time)" +
                " VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, datetime('now','+8 hour'));";

        Connection connection = null;
        PreparedStatement ps = null;
        try{
            connection = dataSource.getConnection("root", "root");
            ps = connection.prepareStatement(sql);
            ps.setString(1, consumeTimeReq.getUserId());
            ps.setString(2, consumeTimeReq.getConsumeId());
            ps.setString(3, consumeTimeReq.getProductName());
            ps.setString(4, consumeTimeReq.getStartTime());
            ps.setString(5, consumeTimeReq.getEndTime());
            ps.setInt(6, consumeTimeReq.getUseTime());
            ps.setString(7, consumeTimeReq.getComment());
            ps.setInt(8, consumeTimeReq.getConsumeType());
            ps.executeUpdate();
            result = true;
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
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

        return result;
    }
}
