package com.xck.controller.scene3d;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname scene3d
 * @Description 制作室内全景
 * @Date 2020/11/29 10:24
 * @Created by xck503c
 */
@Controller
@RequestMapping("/scene3d")
public class Scene3DController {

    //十一
    @RequestMapping("/furniture3D")
    public String index(){
        return "furniture3D";
    }
}
