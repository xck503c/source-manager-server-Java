package com.xck.controller;

import com.xck.model.FileListItem;
import com.xck.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Classname SourceListController
 * @Description 资源访问Controller
 * @Date 2020/11/25 17:56
 * @Created by xck503c
 */
@Controller
@RequestMapping("/source")
public class SourceListController {

    @Value("${source.root.path}")
    private String sourceRootDir; //共享目录根路径

    /**
     * 功能：共享目录访问，点击目录则前进，点击上层则后退，点击文件就下载
     * @param modelMap
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam String path,  ModelMap modelMap) throws Exception{
        String tmpPath = path;
        //默认查看基础路径，如果路径中带有基础路径，则视为不合法的路径，自动去查询基础路径的文件
        if(StringUtils.isEmpty(tmpPath) || path.contains(sourceRootDir)){
            tmpPath = sourceRootDir;
        }else{
            tmpPath = sourceRootDir + tmpPath;
        }
        tmpPath = FileUtils.dealRelative(tmpPath);
        if(!tmpPath.startsWith(sourceRootDir)){
            tmpPath = sourceRootDir;
        }

        List<FileListItem> fileList = FileUtils.getFileList(tmpPath);
        if(fileList == null){ //文件
            return "redirect:/source/reader?path="+URLEncoder.encode(tmpPath, "utf-8");
        }

        for(int i=0; i<fileList.size(); i++){
            FileListItem item = fileList.get(i);
            String absolute = item.getAbsolutePath();
            absolute = absolute.replaceAll("\\\\", "/");
            item.setAbsolutePath(absolute.substring(sourceRootDir.length()));
        }

        modelMap.addAttribute("fileList", fileList);
        return "sourceList";
    }

    @RequestMapping("/reader")
    public void list(@RequestParam String path, HttpServletRequest request, HttpServletResponse response){
        FileInputStream input = null;
        byte[] data = null;
        try {
            input = new FileInputStream(path);
            data = new byte[input.available()];
            input.read(data);
            response.getOutputStream().write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(input!=null){
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
