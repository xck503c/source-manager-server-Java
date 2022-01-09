package com.xck.model.intercept;

import com.alibaba.fastjson.JSONObject;
import com.xck.exception.TokenException;
import com.xck.model.LoginInfo;
import com.xck.model.req.ReqResponse;
import com.xck.service.LoginService;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author xuchengkun
 * @date 2022/01/06 16:25
 **/
public class SessionIntercept implements HandlerInterceptor {

    private LoginService loginService;

    public SessionIntercept(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().print(JSONObject.toJSONString(ReqResponse.error(ReqResponse.TOKEN_ERR, "token不合法")));
            return false;
        }

        LoginInfo loginInfo;
        try {
            loginInfo = loginService.isLogin(token, request.getRemoteAddr());
        } catch (TokenException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().print(JSONObject.toJSONString(ReqResponse.error(ReqResponse.TOKEN_ERR, e.getMessage())));
            return false;
        }

        request.setAttribute("userId", loginInfo.getUserId());

        return true;
    }
}
