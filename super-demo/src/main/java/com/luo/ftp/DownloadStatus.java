package com.luo.ftp;

/**
 * Created by Administrator on 2015/8/6.
 */
public enum DownloadStatus {

    Remote_File_Noexist(0,"远程文件不存在"), // 远程文件不存在
    //用于单个下载
    Download_New_Success(1,"下载文件成功"), // 下载文件成功
    Download_New_Failed(2,"下载文件失败"), // 下载文件失败
    Local_Bigger_Remote(3,"本地文件大于远程文件"), // 本地文件大于远程文件
    Download_From_Break_Success(4,"文件断点续传成功"), // 断点续传成功
    Download_From_Break_Failed(5,"文件断点续传失败"), // 断点续传失败
    //用于批量下载
    Download_Batch_Success(6,"文件批量下载成功"),
    Download_Batch_Failure(7,"文件批量下载失败"),
    Download_Batch_Failure_SUCCESS(8,"文件批量下载不完全成功");

    private int code;

    private String description;


    private DownloadStatus(int code , String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
