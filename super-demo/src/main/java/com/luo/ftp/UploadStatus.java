package com.luo.ftp;

/**
 * Created by Administrator on 2015/8/6.
 */
public enum UploadStatus {
    Create_Directory_Fail(0,"远程服务器相应目录创建失败"), // 远程服务器相应目录创建失败
    Create_Directory_Success(1,"远程服务器闯将目录成功"), // 远程服务器闯将目录成功
    Upload_New_File_Success(2,"上传新文件成功"), // 上传新文件成功
    Upload_New_File_Failed(3,"上传新文件失败"), // 上传新文件失败
    File_Exits(4,"文件已经存在"), // 文件已经存在
    Remote_Bigger_Local(5,"远程文件大于本地文件"), // 远程文件大于本地文件
    Upload_From_Break_Success(6," 断点续传成功"), // 断点续传成功
    Upload_From_Break_Failed(7,"断点续传失败"), // 断点续传失败
    Delete_Remote_Faild(8,"删除远程文件失败"); // 删除远程文件失败

    private int code;

    private String description;


    private UploadStatus(int code , String description) {
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
