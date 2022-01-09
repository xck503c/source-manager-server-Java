package com.xck.controller;

import com.xck.dao.UserDAO;
import com.xck.exception.LoginException;
import com.xck.exception.TokenException;
import com.xck.model.User;
import com.xck.model.req.ReqResponse;
import com.xck.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录接口
 *
 * @author xuchengkun
 * @date 2022/01/06 10:50
 **/
@Controller
public class LoginController {

    @Autowired
    public LoginService loginService;

    @ResponseBody
    @RequestMapping("/login")
    public ReqResponse login(String userId, String password){

        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(password)){
            return ReqResponse.error(ReqResponse.LOGIN_FAIL, "账户名或密码错误");
        }

        String uuid = null;
        try {
            uuid = loginService.login(userId, password);
        } catch (LoginException e) {
            return ReqResponse.error(ReqResponse.LOGIN_FAIL, e.getMessage());
        }

        return ReqResponse.success("登录成功", uuid);
    }

    @ResponseBody
    @RequestMapping("/logout")
    public ReqResponse logout(HttpServletRequest request){
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(token)){
            return ReqResponse.error(ReqResponse.LOGIN_FAIL, "token不合法");
        }

        try {
            loginService.logout(token);
        } catch (TokenException e) {
            return ReqResponse.error(ReqResponse.TOKEN_ERR, e.getMessage());
        }
        return ReqResponse.success("登出成功");
    }
}
