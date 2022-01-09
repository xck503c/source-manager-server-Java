package com.xck.model;

import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 账户信息
 */
public class User {

    private String userId;
    private String uesrName;
    private String userPwd;
    /* ip白名单 */
    private Set<String> whiteIps;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUesrName() {
        return uesrName;
    }

    public void setUesrName(String uesrName) {
        this.uesrName = uesrName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public void setWhiteIps(String ips) {
        if (StringUtils.isEmpty(ips)) return;

        if (whiteIps == null) {
            whiteIps = new HashSet<>();
        }
        String[] ipArr = ips.split(",");
        for (String ip : ipArr) {
            whiteIps.add(ip);
        }
    }

    public boolean isWhiteIps(String ip) {
        if (whiteIps != null && whiteIps.size() > 0) {
            return whiteIps.contains(ip);
        }
        return true;
    }
}
