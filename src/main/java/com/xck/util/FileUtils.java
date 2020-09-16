package com.xck.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<String> getFileList(String dirPath){
        File dir = new File(dirPath);

        List<String> fileNames = new ArrayList<>();
        if(dir.isDirectory()){
            String[] fileArr = dir.list();
            if(fileArr!=null && fileArr.length>0){
                for(String filename : fileArr){
                    fileNames.add(filename);
                }
            }
        }

        return fileNames;
    }
}
