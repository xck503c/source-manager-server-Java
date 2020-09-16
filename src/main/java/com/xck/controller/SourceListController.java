package com.xck.controller;

import com.xck.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/source")
public class SourceListController {

    @Value("${source.root.path}")
    private String sourceRootDir;

    @RequestMapping("/list")
    public String list(@RequestParam("") String id){
        if (id == "1") {
            FileUtils.getFileList(sourceRootDir);
        }
        return "pages/sourceList";
    }
}
