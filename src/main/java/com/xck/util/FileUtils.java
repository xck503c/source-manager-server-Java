package com.xck.util;

import com.xck.model.FileListItem;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname FileUtils
 * @Description 文件操作工具
 * @Date 2020/11/25 17:56
 * @Created by xck503c
 */
public class FileUtils {

    /**
     * 获取文件列表
     * @param dirPath
     * @return
     */
    public static List<FileListItem> getFileList(String dirPath){
        File dir = new File(dirPath);

        if(dir.isDirectory()){
            List<FileListItem> fileNames = new ArrayList<>();
            fileNames.add(new FileListItem(dirPath+"/../", "../"));
            File[] fileArr = dir.listFiles();
            if(fileArr!=null && fileArr.length>0){
                for(File file : fileArr){
                    fileNames.add(new FileListItem(file.getAbsolutePath(), file.getName()));
                }
            }

            return fileNames;
        }
        return null;
    }

    /**
     * 处理相对路径中的 ..
     * @param dirPath
     * @return
     */
    public static String dealRelative(String dirPath){
        if(StringUtils.isEmpty(dirPath)){
            return dirPath;
        }

        String[] pathPart = dirPath.split("/");
        if(pathPart == null || pathPart.length == 0){
            return dirPath;
        }

        for(int i=pathPart.length-1; i>0; i--){
            if("..".equals(pathPart[i])){
                pathPart[i] = "";
                pathPart[i-1] = "";
            }
        }

        StringBuilder path = new StringBuilder();
        for(int i=0; i<pathPart.length; i++){
            if(!StringUtils.isEmpty(pathPart[i])){
                path.append(pathPart[i]).append("/");
            }
        }
        path.delete(path.length()-1, path.length());

        return path.toString();
    }


}
