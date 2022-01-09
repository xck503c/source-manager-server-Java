package com.xck.model;

import com.xck.exception.TokenException;

/**
 * token信息
 *
 * @author xuchengkun
 * @date 2022/01/06 16:28
 **/
public class LoginInfo {

    /* user缓存信息 */
    private User user;

    private String userId;

    /* 时间戳使用的时候会持续刷新 */
    private long timestamp;

    /* 是否有效 */
    private boolean isValid;

    public LoginInfo(User user) {
        this.user = user;
        this.userId = user.getUserId();
        this.isValid = true;
        this.timestamp = System.currentTimeMillis();
    }

    public synchronized boolean isValid(long TOKEN_TIMEOUT, String ip) throws TokenException{
        if (isValid && System.currentTimeMillis() - timestamp > TOKEN_TIMEOUT) {
            isValid = false;
            throw new TokenException("token超时");
        }

        if (!isValid) return false;

        if (!user.isWhiteIps(ip)) {
            throw new TokenException("非法ip");
        }

        return true;
    }

    public synchronized void logout() {
        isValid = false;
        user = null;
    }

    public synchronized String getUserId() {
        return userId;
    }
}
