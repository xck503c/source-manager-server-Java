package com.xck.model;

/**
 * @Classname FileListItem
 * @Description 文件列表的实体，用于页面展示
 * @Date 2020/11/25 17:56
 * @Created by xck503c
 */
public class FileListItem {

    private String absolutePath; //文件的绝对路径(显示在页面上需要去除基础路径)
    private String fileName;
    private String modifyTime; //修改时间

    public FileListItem(String absolutePath, String fileName, String modifyTime) {
        this.absolutePath = absolutePath;
        this.fileName = fileName;
        this.modifyTime = modifyTime;
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

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}
