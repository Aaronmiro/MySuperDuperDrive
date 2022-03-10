package com.udacity.jwdnd.course1.cloudstorage.models;

/**
 * @author Aaron
 * @date 1/15/22 11:08 AM
 */
public class FileForm {

    private String fileName;
    private Integer fileId;

    public FileForm(String fileName, Integer fileId) {
        this.fileName = fileName;
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }
}
