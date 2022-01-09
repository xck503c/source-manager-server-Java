package com.xck.service;

import com.xck.dao.ConsumerDAO;
import com.xck.model.ConsumerType;
import com.xck.model.req.ConsumeTimeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 消费服务
 *
 * @author xuchengkun
 * @date 2022/01/08 22:55
 **/
@Service
public class ConsumerService {

    private Map<Integer, ConsumerType> consumerTypeMap = new HashMap<>();

    private Lock lock = new ReentrantLock();
    private long flushConsumerTypeTime;

    @Autowired
    private ConsumerDAO consumerDAO;

    public boolean isConsumerTypeExists(int type) {
        if (consumerTypeMap.isEmpty() || System.currentTimeMillis() - flushConsumerTypeTime > 10000) {
            try {
                lock.lock();
                if (consumerTypeMap.isEmpty() || System.currentTimeMillis() - flushConsumerTypeTime > 10000) {
                    consumerTypeMap.putAll(consumerDAO.selectConsumeTypes());
                    flushConsumerTypeTime = System.currentTimeMillis();
                }
            } finally {
                lock.unlock();
            }
        }

        return consumerTypeMap.containsKey(type);
    }

    public boolean addTimeConsume(ConsumeTimeReq req) {
        return consumerDAO.addTimeConsume(req);
    }

    public List<ConsumeTimeReq> selectTimeConsumes(){
        return consumerDAO.selectTimeConsumes();
    }
}
