package com.xck.controller.main;

import com.xck.dao.UserDAO;
import com.xck.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname IndexController
 * @Description 索引页面
 * @Date 2020/11/26 09:10
 * @Created by xck503c
 */
@Controller
@RequestMapping("/main")
public class IndexController {

    @Autowired
    public UserDAO userDAO;

    @RequestMapping("/index")
    public String index(){
        return "main";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, ModelMap modelMap){
        String userId = request.getParameter("userid");
        String password = request.getParameter("password");

        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(password)){
            return "login";
        }

        User user = userDAO.findUser(userId);
        if(user == null){
            return "login";
        }

        System.out.println("登录: " + userId);

        return "index";
    }
}
