package com.xck.service;

import com.xck.dao.UserDAO;
import com.xck.exception.LoginException;
import com.xck.exception.TokenException;
import com.xck.model.LoginInfo;
import com.xck.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 登录服务
 *
 * @author xuchengkun
 * @date 2022/01/06 16:33
 **/
@Service
public class LoginService {

    /* token有效时间 */
    public static long TOKEN_TIMEOUT = 10 * 60 * 60 * 1000;

    @Autowired
    public UserDAO userDAO;

    /* 登录token信息 */
    public Map<String, LoginInfo> tokenMap = new ConcurrentHashMap<>();
    /* 登录账户信息 */
    public Map<String, User> userMap = new ConcurrentHashMap<>();

    /**
     * 登录
     *
     * @param userId
     * @param pwd
     * @return
     */
    public String login(String userId, String pwd) throws LoginException{
        User user = userMap.get(userId);
        if (user == null) {
            user = userDAO.findUser(userId);
        }
        if (user == null) {
            throw new LoginException("账户名或密码错误");
        }

        if (!user.getUserPwd().equals(pwd)) {
            throw new LoginException("账户名或密码错误");
        }

        String uuid = UUID.randomUUID().toString();
        userMap.putIfAbsent(user.getUserId(), user);
        tokenMap.putIfAbsent(uuid, new LoginInfo(user));
        return uuid;
    }

    /**
     * 登出
     *
     * @param token
     */
    public void logout(String token) throws TokenException {
        LoginInfo loginInfo = tokenMap.remove(token);
        if (loginInfo == null) {
            throw new TokenException("未登录");
        }

        if (loginInfo != null) {
            loginInfo.logout();
        }
    }

    /**
     * 是否登录
     *
     * @param token
     * @return
     * @throws TokenException
     */
    public LoginInfo isLogin(String token, String ip) throws TokenException {
        LoginInfo loginInfo = tokenMap.get(token);
        if (loginInfo == null) {
            throw new TokenException("未登录");
        }

        if (!loginInfo.isValid(TOKEN_TIMEOUT, ip)) {
            tokenMap.remove(token);
        }
        return loginInfo;
    }
}
