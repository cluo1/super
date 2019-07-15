/**
 * 项目名称:sysman
 * 类名称:SysFile.java
 * 包名称:com.joyintech.sysman.entity
 *
 * 修改履历:
 *       日期                   修正者      主要内容
 *  Mon Aug 13 10:02:15 CST 2018       沈亚飞         初版做成
 *
 * Copyright (c) 2007-2018 兆尹科技
 */

package com.luo.entity;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Valid信息.
 */

/**
 * SysFile  实体类 <br>
 * 表 SYS_FILE 的实体类. <br>
 * @author 沈亚飞
 * @version 1.0
 */
public class SysFile implements Serializable {
    
    /**
     * 使用一个默认的UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    
    private String fileId;
        /**
     * 文件名称:文件显示名称。
     */
    
    
    private String fileName;
        /**
     * 文件大小
     */
 
    private Long fileSize;
    /**
     * 文件格式
     */
    
    private String fileType;
        /**
     * 文件路径
     */
    
    private String filePath;
        /**
     * 文件类别ID
     */
    
    private String classId;
        /**
     * 所属机构
     */
   
    private String orgId;
        /**
     * 文件使用类型
     */
    
    private String fileUseType;
        /**
     * 关键字
指定查询用的一个或多个关键字
     */
    private String keyWords;
        /**
     * 备注
     */
    

    private String remark;
        /**
     * 创建人
     */
    private String createUserNo;
        /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 更新人
     */
    private String updateUserNo;
        /**
     * 更新日期
     */
    private Date updateDate;
  
    /**
     * 取得文件ID的值
     * @return the fileId 的值
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * 设定文件ID 的值
     * @param fileId the 文件ID to set
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    /**
     * 取得文件名称:文件显示名称。的值
     * @return the fileName 的值
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设定文件名称:文件显示名称。 的值
     * @param fileName the 文件名称:文件显示名称。 to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * 取得文件大小的值
     * @return the fileSize 的值
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * 设定文件大小 的值
     * @param fileSize the 文件大小 to set
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
    /**
     * 取得文件格式的值
     * @return the fileType 的值
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设定文件格式 的值
     * @param fileType the 文件格式 to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    /**
     * 取得文件路径的值
     * @return the filePath 的值
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设定文件路径 的值
     * @param filePath the 文件路径 to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    /**
     * 取得文件类别ID的值
     * @return the classId 的值
     */
    public String getClassId() {
        return classId;
    }

    /**
     * 设定文件类别ID 的值
     * @param classId the 文件类别ID to set
     */
    public void setClassId(String classId) {
        this.classId = classId;
    }
    /**
     * 取得所属机构的值
     * @return the orgId 的值
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 设定所属机构 的值
     * @param orgId the 所属机构 to set
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    /**
     * 取得文件使用类型的值
     * @return the fileUseType 的值
     */
    public String getFileUseType() {
        return fileUseType;
    }

    /**
     * 设定文件使用类型 的值
     * @param fileUseType the 文件使用类型 to set
     */
    public void setFileUseType(String fileUseType) {
        this.fileUseType = fileUseType;
    }
    /**
     * 取得关键字
指定查询用的一个或多个关键字的值
     * @return the keyWords 的值
     */
    public String getKeyWords() {
        return keyWords;
    }

    /**
     * 设定关键字
指定查询用的一个或多个关键字 的值
     * @param keyWords the 关键字
指定查询用的一个或多个关键字 to set
     */
    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
    /**
     * 取得备注的值
     * @return the remark 的值
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设定备注 的值
     * @param remark the 备注 to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * 取得创建人的值
     * @return the createUserNo 的值
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 设定创建人 的值
     * @param createUserNo the 创建人 to set
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }
    /**
     * 取得创建日期的值
     * @return the createDate 的值
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设定创建日期 的值
     * @param createDate the 创建日期 to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    /**
     * 取得更新人的值
     * @return the updateUserNo 的值
     */
    public String getUpdateUserNo() {
        return updateUserNo;
    }

    /**
     * 设定更新人 的值
     * @param updateUserNo the 更新人 to set
     */
    public void setUpdateUserNo(String updateUserNo) {
        this.updateUserNo = updateUserNo;
    }
    /**
     * 取得更新日期的值
     * @return the updateDate 的值
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设定更新日期 的值
     * @param updateDate the 更新日期 to set
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
