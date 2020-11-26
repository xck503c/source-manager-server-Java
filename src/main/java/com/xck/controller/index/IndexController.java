package com.xck.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname IndexController
 * @Description 索引页面
 * @Date 2020/11/26 09:10
 * @Created by xck503c
 */
@Controller
@RequestMapping("/main")
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
