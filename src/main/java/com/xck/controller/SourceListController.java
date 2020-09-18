package com.xck.controller;

import com.xck.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/source")
public class SourceListController {

    @Value("${source.root.path}")
    private String sourceRootDir; //共享目录根路径

    /**
     * 功能：共享目录访问，点击目录则前进，点击上层则后推，点击文件就下载
     * @param path
     * @param modelMap
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam("path") String path, ModelMap modelMap){
        List<String> fileList = new ArrayList<>();
        if(StringUtils.isEmpty(path)){
            fileList = FileUtils.getFileList(sourceRootDir);
        }else{
            if(!path.contains("..")){
                fileList = FileUtils.getFileList(path);
            }
        }

        modelMap.addAttribute("fileList", fileList)
                .addAttribute("");

        return "pages/sourceList";
    }
}
