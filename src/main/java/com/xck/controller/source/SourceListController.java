package com.xck.controller.source;

import com.xck.model.FileListItem;
import com.xck.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
    public String list(@RequestParam(required = false) String path, ModelMap modelMap) throws Exception{
        String tmpPath = path;
        //默认查看基础路径，如果路径中带有基础路径，则视为不合法的路径，自动去查询基础路径的文件
        if(StringUtils.isEmpty(tmpPath) || path.contains(sourceRootDir)){
            tmpPath = sourceRootDir;
        }else{
            //这里需要拿到绝对路径，所以进行拼接
            tmpPath = sourceRootDir + tmpPath;
        }
        tmpPath = FileUtils.dealRelative(tmpPath);
        if(!tmpPath.startsWith(sourceRootDir)){
            return "redirect:/main/index";
        }

        List<FileListItem> fileList = FileUtils.getFileList(tmpPath);
        if(fileList == null){ //文件
            return "redirect:/source/reader?path="+URLEncoder.encode(tmpPath, "utf-8");
        }

        for(int i=0; i<fileList.size(); i++){
            FileListItem item = fileList.get(i);
            String absolute = item.getAbsolutePath();
            absolute = absolute.replaceAll("\\\\", "/");
            //最后在页面上的路径签名需要带上斜杠，而sourceRootDir后面是不带斜杠，所以这里会保留
            item.setAbsolutePath(absolute.substring(sourceRootDir.length()));
        }

        modelMap.addAttribute("fileList", fileList);
        return "sourceList";
    }

    /**
     * 当资源是文件的时候跳转到这个方法，进行后续处理，看看是下载还是在线看。
     * @param path
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/reader")
    public String reader(@RequestParam String path, ModelMap modelMap) throws Exception{

        //文件不存在的处理
        File file = new File(path);
        if(!file.exists() || !file.isFile()){
            return "redirect:/main/index";
        }

        //调整到播放的页面进行播放
        if(file.getName().endsWith(".mp4")){
            //传递给前端的html5标签使用
            modelMap.addAttribute("path", path);
            return "videoReader";
        }
        if(file.getName().endsWith(".mp3")){
            //传递给前端的html5标签使用
            modelMap.addAttribute("path", path);
            return "audioReader";
        }

        //跳转到文件流传输的位置，可以直接下载
        return "redirect:/source/download?path="+URLEncoder.encode(path, "utf-8");
    }

    /**
     * 流获取，可以触发下载，如果是文本或者pdf也可以直接在浏览器上面看
     * @param path
     * @param response
     */
    @GetMapping("/download")
    public void download(@RequestParam String path, HttpServletResponse response){
        //文件不存在的处理
        File file = new File(path);
        if(!file.exists() || !file.isFile()){
            return;
        }

        FileInputStream input = null;
        byte[] data = null;
        try {
            input = new FileInputStream(file);
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

    /**
     * 资源上传，将资源上传到基础路径/download/，的目录下面
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public String upload(HttpServletRequest request) throws Exception{
        StandardMultipartHttpServletRequest multipartHttpServletRequest = (StandardMultipartHttpServletRequest)request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
        File file = new File(sourceRootDir + "/download/" + multipartFile.getOriginalFilename());
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }

        multipartFile.transferTo(file);

        return "redirect:/main/index";
    }
}
