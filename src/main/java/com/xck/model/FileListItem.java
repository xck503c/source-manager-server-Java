package com.xck.model;

/**
 * @Classname FileListItem
 * @Description 文件列表的实体，用于页面展示
 * @Date 2020/11/25 17:56
 * @Created by xck503c
 */
public class FileListItem {

    private String absolutePath;
    private String fileName;

    public FileListItem(String absolutePath, String fileName) {
        this.absolutePath = absolutePath;
        this.fileName = fileName;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
