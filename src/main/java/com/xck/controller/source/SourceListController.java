package com.xck.controller.source;

import com.alibaba.fastjson.JSONObject;
import com.xck.model.FileListItem;
import com.xck.model.req.ReqResponse;
import com.xck.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
     * 功能：共享目录访问，访问文件列表
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public ReqResponse list(@RequestParam(required = false) String path) throws Exception{
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
            return ReqResponse.error(ReqResponse.NO_ILLEGAL_PATH);
        }

        List<FileListItem> fileList = FileUtils.getFileList(tmpPath);
        if(fileList == null){ //文件
            return ReqResponse.error(ReqResponse.NO_DIR);
        }

        for(int i=0; i<fileList.size(); i++){
            FileListItem item = fileList.get(i);
            String absolute = item.getAbsolutePath();
            //路径统一正斜杠
            absolute = absolute.replaceAll("\\\\", "/");
            //最后在页面上的路径签名需要带上斜杠，而sourceRootDir后面是不带斜杠，所以这里会保留
            item.setAbsolutePath(absolute.substring(sourceRootDir.length()));
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fileList", fileList);
        jsonObject.put("curPath", path);
        return ReqResponse.success("成功", jsonObject.toJSONString());
    }

    /**
     * 流获取，可以触发下载，如果是文本或者pdf也可以直接在浏览器上面看
     * @param path
     * @param response
     */
    @GetMapping("/download")
    public void file(@RequestParam String path, HttpServletResponse response){
        //默认查看基础路径，如果路径中带有基础路径，则视为不合法的路径，自动去查询基础路径的文件
        if(StringUtils.isEmpty(path) || path.contains(sourceRootDir)){
            path = sourceRootDir;
        }else{
            //这里需要拿到绝对路径，所以进行拼接
            path = sourceRootDir + path;
        }

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
            //忽略，因为视频播放中途关闭这里会报错
        } finally {
            try {
                if(input!=null){
                    input.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * 资源上传，将资源上传到指定的目录下面
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/upload")
    public ReqResponse upload(String path, HttpServletRequest request) throws Exception{
        String dirPath = FileUtils.dealRelative(path);
        if(StringUtils.isEmpty(dirPath)){
            dirPath = sourceRootDir;
        }else{
            dirPath = sourceRootDir + "/" + dirPath;
        }

        StandardMultipartHttpServletRequest multipartHttpServletRequest = (StandardMultipartHttpServletRequest)request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
        File file = new File(dirPath + "/" + multipartFile.getOriginalFilename());
        if (file.exists()){
            return ReqResponse.error(ReqResponse.UPLOAD_FAIL, "存在同名文件");
        }

        if(!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }

        multipartFile.transferTo(file);

        return ReqResponse.success("上传成功");
    }

    @ResponseBody
    @GetMapping("/newFolder")
    public ReqResponse newFolder(String path, String folderName){
        String dirPath = FileUtils.dealRelative(path);
        if(StringUtils.isEmpty(dirPath)){
            dirPath = sourceRootDir;
        }else{
            dirPath = sourceRootDir + "/" + dirPath;
        }

        File file = new File(dirPath + "/" + folderName);
        if(file.exists()){
            return ReqResponse.error(ReqResponse.DIR_OPERATE_ERROR, "创建失败, 已存在同名文件夹");
        }

        file.mkdir();

        return ReqResponse.success("目录创建成功");
    }

    @ResponseBody
    @GetMapping("/delFolder")
    public ReqResponse delFolder(String path){
        String filePath = FileUtils.dealRelative(path);
        if(StringUtils.isEmpty(filePath)){
            return ReqResponse.success("无需要删除的文件");
        }else{
            filePath = sourceRootDir + "/" + filePath;
        }

        File file = new File(filePath);
        if(file.exists()){
            boolean delResult = file.delete();
            if (delResult) {
                return ReqResponse.success("删除成功");
            }else {
                return ReqResponse.error(ReqResponse.DIR_OPERATE_ERROR, "删除失败");
            }
        }

        return ReqResponse.success("无需要删除的文件");
    }
}
