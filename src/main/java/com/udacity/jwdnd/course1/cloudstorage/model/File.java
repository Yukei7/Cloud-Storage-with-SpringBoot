package com.udacity.jwdnd.course1.cloudstorage.model;

import java.util.Objects;

public class File {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private Integer userId;
    private byte[] filedata;

    public File(Integer fileId, String fileName, String contentType, Integer userId, byte[] filedata) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.userId = userId;
        this.filedata = filedata;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof File)) {
            return false;
        }
        File file = (File) o;
        return this.fileName.equals(file.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName);
    }
}
